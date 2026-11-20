package com.electrifiedded.orespawn.world;

import com.electrifiedded.orespawn.Reference;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

/** Buffers world-generation writes per chunk and commits ordinary blocks directly to chunk sections. */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class GenerationBlockBuffer implements AutoCloseable {
    private static final boolean CUBIC_CHUNKS = net.minecraftforge.fml.common.Loader.isModLoaded("cubicchunks");
    private static final ThreadLocal<GenerationBlockBuffer> ACTIVE = new ThreadLocal<>();
    /** Per-dimension saved data holding writes for chunks that were not loaded yet; cached to skip the storage lookup. */
    private static final Map<World, DeferredWrites> DEFERRED = new IdentityHashMap<>();

    private final World world;
    private final GenerationBlockBuffer parent;
    private final Long2ObjectOpenHashMap<ChunkWrites> chunks = new Long2ObjectOpenHashMap<>();
    /** Structure code writes in tight loops, so most lookups hit the same chunk as the previous one. */
    private ChunkWrites lastChunk;
    private boolean closed;

    private GenerationBlockBuffer(World world, GenerationBlockBuffer parent) {
        this.world = world;
        this.parent = parent;
    }

    public static GenerationBlockBuffer begin(World world) {
        GenerationBlockBuffer buffer = new GenerationBlockBuffer(world, ACTIVE.get());
        ACTIVE.set(buffer);
        return buffer;
    }

    public static boolean queue(World world, BlockPos pos, IBlockState state, int flags) {
        return queue(world, pos.getX(), pos.getY(), pos.getZ(), state, flags);
    }

    public static boolean queue(World world, int x, int y, int z, IBlockState state, int flags) {
        GenerationBlockBuffer buffer = ACTIVE.get();
        if (buffer == null || buffer.world != world || y < WorldHeightCompat.minBuildY(world) || y >= world.getHeight()) {
            return false;
        }
        // Structure code configures chests and spawners immediately after placement.
        // Delaying tile-entity blocks until flush makes that lookup return null.
        if (state.getBlock().hasTileEntity(state)) {
            return false;
        }
        buffer.chunkFor(x >> 4, z >> 4, true).put(x & 15, y, z & 15, state, flags);
        return true;
    }

    /** Places a tile-entity block now and removes older buffered writes at its position. */
    public static boolean placeImmediately(World world, BlockPos pos, IBlockState state, int flags) {
        GenerationBlockBuffer buffer = ACTIVE.get();
        if (buffer != null && buffer.world == world && pos.getY() >= WorldHeightCompat.minBuildY(world) && pos.getY() < world.getHeight()) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            for (GenerationBlockBuffer current = buffer; current != null; current = current.parent) {
                ChunkWrites writes = current.chunkFor(x >> 4, z >> 4, false);
                if (writes != null) {
                    writes.remove(x & 15, y, z & 15);
                }
            }
            ChunkWrites deferred = getDeferredChunk(world, x >> 4, z >> 4);
            if (deferred != null) {
                deferred.remove(x & 15, y, z & 15);
                deferredFor(world, false).markDirty();
            }
        }
        return world.setBlockState(pos, state, flags);
    }

    public static IBlockState getBufferedState(World world, BlockPos pos) {
        return getBufferedState(world, pos.getX(), pos.getY(), pos.getZ());
    }

    public static IBlockState getBufferedState(World world, int x, int y, int z) {
        GenerationBlockBuffer buffer = ACTIVE.get();
        if (buffer == null || buffer.world != world || y < WorldHeightCompat.minBuildY(world) || y >= world.getHeight()) {
            return null;
        }
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        for (GenerationBlockBuffer current = buffer; current != null; current = current.parent) {
            ChunkWrites writes = current.chunkFor(chunkX, chunkZ, false);
            if (writes != null) {
                IBlockState state = writes.get(x & 15, y, z & 15);
                if (state != null) {
                    return state;
                }
            }
        }
        ChunkWrites deferred = getDeferredChunk(world, chunkX, chunkZ);
        return deferred == null ? null : deferred.get(x & 15, y, z & 15);
    }

    /** Applies writes from earlier neighboring generation before this chunk's own OreSpawn pass. */
    public static void prepareChunk(World world, int chunkX, int chunkZ) {
        Chunk chunk = world.getChunkProvider().getLoadedChunk(chunkX, chunkZ);
        if (chunk != null) {
            applyDeferred(world, chunk);
        }
    }

    public void flush() {
        if (closed) {
            return;
        }
        closed = true;
        if (parent == null) {
            ACTIVE.remove();
            commitPending();
        } else {
            ACTIVE.set(parent);
            for (ChunkWrites writes : chunks.values()) {
                parent.mergeChunk(writes);
            }
            chunks.clear();
            lastChunk = null;
        }
    }

    private ChunkWrites chunkFor(int chunkX, int chunkZ, boolean create) {
        ChunkWrites cached = lastChunk;
        if (cached != null && cached.chunkX == chunkX && cached.chunkZ == chunkZ) {
            return cached;
        }
        long key = chunkKey(chunkX, chunkZ);
        ChunkWrites writes = chunks.get(key);
        if (writes == null) {
            if (!create) {
                return null;
            }
            writes = new ChunkWrites(chunkX, chunkZ);
            chunks.put(key, writes);
        }
        lastChunk = writes;
        return writes;
    }

    private void mergeChunk(ChunkWrites writes) {
        long key = chunkKey(writes.chunkX, writes.chunkZ);
        ChunkWrites existing = chunks.get(key);
        if (existing == null) {
            chunks.put(key, writes);
        } else {
            existing.putAll(writes);
        }
    }

    private void commitPending() {
        if (chunks.isEmpty()) {
            return;
        }
        for (ChunkWrites writes : chunks.values()) {
            Chunk chunk = world.getChunkProvider().getLoadedChunk(writes.chunkX, writes.chunkZ);
            if (chunk == null) {
                defer(world, writes);
            } else {
                // Older deferred writes must land first so this pass's newer blocks win.
                applyDeferred(world, chunk);
                apply(world, chunk, writes);
            }
        }
        chunks.clear();
        lastChunk = null;
    }

    /**
     * A missing save file is cached as an empty, clean instance: otherwise every buffered lookup
     * would hit {@code MapStorage.getOrLoadData}, which checks the disk on each miss. Clean data is
     * never written, so no file appears until something is actually deferred.
     */
    private static DeferredWrites deferredFor(World world, boolean create) {
        DeferredWrites data = DEFERRED.get(world);
        if (data == null) {
            data = (DeferredWrites) world.getPerWorldStorage().getOrLoadData(DeferredWrites.class, DeferredWrites.NAME);
            if (data == null) {
                data = new DeferredWrites(DeferredWrites.NAME);
                world.getPerWorldStorage().setData(DeferredWrites.NAME, data);
            }
            DEFERRED.put(world, data);
        }
        return data;
    }

    private static void defer(World world, ChunkWrites writes) {
        DeferredWrites data = deferredFor(world, true);
        data.markDirty();
        Long2ObjectMap<ChunkWrites> worldChunks = data.chunks;
        long key = chunkKey(writes.chunkX, writes.chunkZ);
        ChunkWrites existing = worldChunks.get(key);
        if (existing == null) {
            writes.compact();
            worldChunks.put(key, writes);
        } else {
            existing.putAll(writes);
            existing.compact();
        }
    }

    private static ChunkWrites getDeferredChunk(World world, int chunkX, int chunkZ) {
        DeferredWrites data = deferredFor(world, false);
        return data == null ? null : data.chunks.get(chunkKey(chunkX, chunkZ));
    }

    private static void applyDeferred(World world, Chunk chunk) {
        DeferredWrites data = deferredFor(world, false);
        if (data == null || data.chunks.isEmpty()) {
            return;
        }
        ChunkWrites pending = data.chunks.remove(chunkKey(chunk.x, chunk.z));
        if (pending != null) {
            data.markDirty();
            apply(world, chunk, pending);
        }
    }

    /**
     * Writes plain blocks straight into chunk sections, then places everything that needs
     * vanilla side effects (tile entities, light sources, fluids, attachments) through
     * {@link World#setBlockState} in the original order, after their supports already exist.
     */
    private static void apply(World world, Chunk chunk, ChunkWrites writes) {
        ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int baseX = chunk.x << 4;
        int baseZ = chunk.z << 4;
        boolean hasSkyLight = world.provider.hasSkyLight();
        // Taller-than-vanilla worlds just have more sections; cubic worlds do not use chunk sections at all.
        boolean sectionsUsable = !CUBIC_CHUNKS;
        int sectionCount = storage.length;
        // Section 0 starts at the mod's minimum Y (-64) in extended-height worlds, not at 0.
        int minY = WorldHeightCompat.storageMinY();
        boolean directWrite = false;
        boolean needsSkylightRebuild = false;
        int[] fallback = null;
        int fallbackCount = 0;

        for (int slot = 0; slot < writes.size; slot++) {
            IBlockState state = writes.states[slot];
            if (state == null) {
                continue;
            }
            int packed = writes.packed[slot];
            int lx = packed & 15;
            int lz = (packed >> 4) & 15;
            int y = packed >> 8;
            int sectionIndex = (y - minY) >> 4;
            if (!sectionsUsable || sectionIndex < 0 || sectionIndex >= sectionCount) {
                if (fallback == null) {
                    fallback = new int[Math.min(writes.size - slot, 64)];
                } else if (fallbackCount == fallback.length) {
                    fallback = Arrays.copyOf(fallback, fallbackCount * 2);
                }
                fallback[fallbackCount++] = slot;
                continue;
            }
            ExtendedBlockStorage section = storage[sectionIndex];
            IBlockState oldState = section == null ? Blocks.AIR.getDefaultState() : section.get(lx, y & 15, lz);
            if (oldState == state) {
                continue;
            }
            pos.setPos(baseX + lx, y, baseZ + lz);
            if (!canWriteDirect(oldState) || !canWriteDirect(state)
                    || oldState.getLightValue(world, pos) != state.getLightValue(world, pos)) {
                if (fallback == null) {
                    fallback = new int[Math.min(writes.size - slot, 64)];
                } else if (fallbackCount == fallback.length) {
                    fallback = Arrays.copyOf(fallback, fallbackCount * 2);
                }
                fallback[fallbackCount++] = slot;
                continue;
            }
            if (!needsSkylightRebuild
                    && (y >= chunk.getHeightValue(lx, lz) - 1
                    || oldState.getLightOpacity(world, pos) != state.getLightOpacity(world, pos))) {
                needsSkylightRebuild = true;
            }
            if (section == null) {
                if (state.getMaterial() == Material.AIR) {
                    continue;
                }
                section = new ExtendedBlockStorage((sectionIndex << 4) + minY, hasSkyLight);
                storage[sectionIndex] = section;
            }
            section.set(lx, y & 15, lz, state);
            directWrite = true;
        }

        if (directWrite) {
            if (needsSkylightRebuild) {
                chunk.generateSkylightMap();
            }
            chunk.markDirty();
        }
        for (int i = 0; i < fallbackCount; i++) {
            int slot = fallback[i];
            int packed = writes.packed[slot];
            pos.setPos(baseX + (packed & 15), packed >> 8, baseZ + ((packed >> 4) & 15));
            world.setBlockState(pos, writes.states[slot], writes.flags[slot]);
        }
        if (directWrite) {
            syncWatchedChunk(world, chunk);
        }
    }

    private static void syncWatchedChunk(World world, Chunk chunk) {
        if (world.isRemote || !(world instanceof WorldServer)) {
            return;
        }
        PlayerChunkMapEntry entry = ((WorldServer) world).getPlayerChunkMap().getEntry(chunk.x, chunk.z);
        if (entry != null && entry.isSentToPlayers()) {
            entry.sendPacket(new SPacketChunkData(chunk, chunk.getBlockStorageArray().length >= 32 ? -1 : (1 << Math.max(16, chunk.getBlockStorageArray().length)) - 1));
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        World world = event.getWorld();
        Chunk chunk = event.getChunk();
        if (!world.isRemote && chunk.isTerrainPopulated()) {
            applyDeferred(world, chunk);
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        DEFERRED.remove(event.getWorld());
    }

    @Override
    public void close() {
        flush();
    }

    /** Survives restarts, so structures reaching into chunks generated later are completed then. */
    public static final class DeferredWrites extends WorldSavedData {
        static final String NAME = "orespawn_deferred_blocks";
        final Long2ObjectMap<ChunkWrites> chunks = new Long2ObjectOpenHashMap<>();

        public DeferredWrites(String name) {
            super(name);
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            chunks.clear();
            NBTTagList list = nbt.getTagList("chunks", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                ChunkWrites writes = new ChunkWrites(tag.getInteger("x"), tag.getInteger("z"));
                int[] packed = tag.getIntArray("pos");
                int[] states = tag.getIntArray("state");
                byte[] flags = tag.getByteArray("flags");
                int count = Math.min(packed.length, Math.min(states.length, flags.length));
                for (int j = 0; j < count; j++) {
                    IBlockState state = Block.getStateById(states[j]);
                    if (state.getBlock() == Blocks.AIR && states[j] != 0) {
                        continue; // block from a removed mod
                    }
                    int p = packed[j];
                    writes.put(p & 15, p >> 8, (p >> 4) & 15, state, flags[j]);
                }
                if (writes.size > 0) {
                    writes.compact();
                    chunks.put(chunkKey(writes.chunkX, writes.chunkZ), writes);
                }
            }
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            NBTTagList list = new NBTTagList();
            for (ChunkWrites writes : chunks.values()) {
                writes.compact();
                if (writes.size == 0) {
                    continue;
                }
                int[] states = new int[writes.size];
                for (int i = 0; i < writes.size; i++) {
                    states[i] = Block.getStateId(writes.states[i]);
                }
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger("x", writes.chunkX);
                tag.setInteger("z", writes.chunkZ);
                tag.setIntArray("pos", Arrays.copyOf(writes.packed, writes.size));
                tag.setIntArray("state", states);
                tag.setByteArray("flags", Arrays.copyOf(writes.flags, writes.size));
                list.appendTag(tag);
            }
            nbt.setTag("chunks", list);
            return nbt;
        }
    }

    private static long chunkKey(int x, int z) {
        return ((long) x & 0xffffffffL) | (((long) z & 0xffffffffL) << 32);
    }

    private static boolean canWriteDirect(IBlockState state) {
        Block block = state.getBlock();
        if (block.hasTileEntity(state)) {
            return false;
        }
        Material material = state.getMaterial();
        return material == Material.AIR || material == Material.WOOD || material == Material.LEAVES
                || material == Material.ROCK || material == Material.GROUND || material == Material.GRASS
                || material == Material.SAND || material == Material.CLAY || material == Material.IRON
                || material == Material.GLASS || material == Material.ICE || material == Material.PACKED_ICE
                || material == Material.CLOTH || material == Material.CRAFTED_SNOW;
    }

    /**
     * Writes for one chunk in first-insertion order (like a linked map), with a lazily built
     * per-section index for position lookups. Deferred chunks drop the index to save memory.
     */
    private static final class ChunkWrites {
        final int chunkX;
        final int chunkZ;
        int size;
        /** y << 8 | localZ << 4 | localX */
        int[] packed = new int[64];
        IBlockState[] states = new IBlockState[64];
        byte[] flags = new byte[64];
        /** index[section][localIndex] = slot + 1, or 0 when absent. */
        int[][] index;

        ChunkWrites(int chunkX, int chunkZ) {
            this.chunkX = chunkX;
            this.chunkZ = chunkZ;
        }

        IBlockState get(int lx, int y, int lz) {
            int[][] idx = index();
            int sec = sectionOf(y);
            int[] section = sec >= 0 && sec < idx.length ? idx[sec] : null;
            if (section == null) {
                return null;
            }
            int slot = section[localIndex(lx, y, lz)] - 1;
            return slot < 0 ? null : states[slot];
        }

        void put(int lx, int y, int lz, IBlockState state, int flag) {
            int[][] idx = index();
            int sec = sectionOf(y);
            if (sec >= idx.length) {
                idx = index = Arrays.copyOf(idx, sec + 1);
            }
            int[] section = idx[sec];
            if (section == null) {
                section = new int[4096];
                idx[sec] = section;
            }
            int local = localIndex(lx, y, lz);
            int slot = section[local] - 1;
            if (slot >= 0) {
                states[slot] = state;
                flags[slot] = (byte) flag;
                return;
            }
            if (size == packed.length) {
                int capacity = Math.max(16, size * 2);
                packed = Arrays.copyOf(packed, capacity);
                states = Arrays.copyOf(states, capacity);
                flags = Arrays.copyOf(flags, capacity);
            }
            packed[size] = (y << 8) | (lz << 4) | lx;
            states[size] = state;
            flags[size] = (byte) flag;
            section[local] = ++size;
        }

        void remove(int lx, int y, int lz) {
            int[][] idx = index();
            int sec = sectionOf(y);
            int[] section = sec >= 0 && sec < idx.length ? idx[sec] : null;
            if (section == null) {
                return;
            }
            int local = localIndex(lx, y, lz);
            int slot = section[local] - 1;
            if (slot >= 0) {
                states[slot] = null;
                section[local] = 0;
            }
        }

        void putAll(ChunkWrites other) {
            for (int slot = 0; slot < other.size; slot++) {
                IBlockState state = other.states[slot];
                if (state != null) {
                    int p = other.packed[slot];
                    put(p & 15, p >> 8, (p >> 4) & 15, state, other.flags[slot]);
                }
            }
        }

        /** Drops removed slots and the lookup index; used for long-lived deferred chunks. */
        void compact() {
            int write = 0;
            for (int read = 0; read < size; read++) {
                if (states[read] != null) {
                    packed[write] = packed[read];
                    states[write] = states[read];
                    flags[write] = flags[read];
                    write++;
                }
            }
            Arrays.fill(states, write, size, null);
            size = write;
            packed = Arrays.copyOf(packed, write);
            states = Arrays.copyOf(states, write);
            flags = Arrays.copyOf(flags, write);
            index = null;
        }

        private int[][] index() {
            int[][] idx = index;
            if (idx == null) {
                idx = new int[16][];
                for (int slot = 0; slot < size; slot++) {
                    if (states[slot] == null) {
                        continue;
                    }
                    int p = packed[slot];
                    int y = p >> 8;
                    int sec = sectionOf(y);
                    if (sec >= idx.length) {
                        idx = Arrays.copyOf(idx, sec + 1);
                    }
                    int[] section = idx[sec];
                    if (section == null) {
                        section = new int[4096];
                        idx[sec] = section;
                    }
                    section[localIndex(p & 15, y, (p >> 4) & 15)] = slot + 1;
                }
                index = idx;
            }
            return idx;
        }

        /** Lookup-index slot of the 16-high layer containing y, counted from the lowest storable Y. */
        private static int sectionOf(int y) {
            return WorldHeightCompat.sectionIndex(y);
        }

        private static int localIndex(int lx, int y, int lz) {
            return ((y & 15) << 8) | (lz << 4) | lx;
        }
    }
}

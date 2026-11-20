package com.electrifiedded.orespawn.world;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Section addressing that stays correct when Cave Biomes API (Caves Not Cliffs) extends the world height.
 * <p>
 * That mod is runtime-only, so it is reached by reflection. Its chunks keep the vanilla layout except that
 * section 0 starts at {@link #storageMinY()} (-64) instead of 0, in every dimension, so
 * {@code storage[y >> 4]} addresses the wrong blocks. Without the mod everything here reduces to vanilla.
 */
public final class WorldHeightCompat {
    private static final String API = "net.celestiald.cavebiomes.api.WorldHeightAPI";
    private static final MethodHandle MIN_Y;
    private static final MethodHandle USES_EXTENDED;

    static {
        MethodHandle minY = null;
        MethodHandle usesExtended = null;
        if (Loader.isModLoaded("cavebiomesapi")) {
            try {
                Class<?> api = Class.forName(API, false, WorldHeightCompat.class.getClassLoader());
                MethodHandles.Lookup lookup = MethodHandles.publicLookup();
                minY = lookup.findStatic(api, "getMinY", MethodType.methodType(int.class));
                usesExtended = lookup.findStatic(api, "usesExtendedHeight", MethodType.methodType(boolean.class, World.class));
            } catch (ReflectiveOperationException | LinkageError e) {
                minY = null;
                usesExtended = null;
            }
        }
        MIN_Y = minY;
        USES_EXTENDED = usesExtended;
    }

    private WorldHeightCompat() {
    }

    /** World Y of the first block of chunk section 0; read on every call because the range is configurable until startup ends. */
    public static int storageMinY() {
        if (MIN_Y != null) {
            try {
                return (int) MIN_Y.invokeExact();
            } catch (Throwable ignored) {
                // fall through to the vanilla layout
            }
        }
        return 0;
    }

    /** Lowest buildable Y of {@code world}: below zero only in the dimension that actually uses the extended range. */
    public static int minBuildY(World world) {
        if (USES_EXTENDED != null) {
            try {
                if ((boolean) USES_EXTENDED.invokeExact(world)) {
                    return storageMinY();
                }
            } catch (Throwable ignored) {
                // fall through to the vanilla floor
            }
        }
        return 0;
    }

    /** Index into {@code Chunk#getBlockStorageArray()} of the section containing {@code y}; may be out of range. */
    public static int sectionIndex(int y) {
        return (y - storageMinY()) >> 4;
    }

    /** World Y of the lowest block of the section stored at {@code index}. */
    public static int sectionBase(int index) {
        return (index << 4) + storageMinY();
    }
}

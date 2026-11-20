package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderOreSpawn4 implements IChunkGenerator {
   private World world;
   private Random random;
   private final Block[] cachedBlockIDs = new Block[256];
   private final byte[] cachedBlockMetadata = new byte[256];

   public ChunkProviderOreSpawn4(World par1World, long par2, boolean par4) {
      this.world = par1World;
      this.random = new Random(par2);

      for (int j = 0; j < 8; j++) {
         if (j == 0) {
            this.cachedBlockIDs[j] = Blocks.BEDROCK;
         } else if (j == 7) {
            this.cachedBlockIDs[j] = Blocks.GRASS;
         } else {
            this.cachedBlockIDs[j] = Blocks.DIRT;
         }
      }
   }

    public Chunk provideChunk(int par1, int par2) {
       return this.generateChunk(par1, par2);
    }

   public Chunk generateChunk(int par1, int par2) {
       this.random.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
       net.minecraft.world.chunk.ChunkPrimer primer = new net.minecraft.world.chunk.ChunkPrimer();

      for (int k = 0; k < this.cachedBlockIDs.length; k++) {
         Block block = this.cachedBlockIDs[k];
         if (block != null) {
             for (int i1 = 0; i1 < 16; i1++) {
                for (int j1 = 0; j1 < 16; j1++) {
                   primer.setBlockState(i1, k, j1, block.getStateFromMeta(this.cachedBlockMetadata[k]));
               }
            }
         }
      }

       Chunk chunk = new Chunk(this.world, primer, par1, par2);
       this.setChunkBiomes(chunk, par1, par2);
       this.addScragglyTrees(this.world, par1 * 16, par2 * 16, chunk);
      chunk.generateSkylightMap();
      return chunk;
   }

   private void setChunkBiomes(Chunk chunk, int chunkX, int chunkZ) {
      net.minecraft.world.biome.Biome[] biomes = this.world.getBiomeProvider().getBiomes(null, chunkX * 16, chunkZ * 16, 16, 16);
      byte[] biomeIds = chunk.getBiomeArray();
      for (int i = 0; i < biomeIds.length; i++) biomeIds[i] = (byte)net.minecraft.world.biome.Biome.getIdForBiome(biomes[i]);
   }

   public boolean chunkExists(int par1, int par2) {
      return true;
   }

    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
      int k = par2 * 16;
      int l = par3 * 16;
      this.random.setSeed(this.world.getSeed());
      long i1 = this.random.nextLong() / 2L * 2L + 1L;
      long j1 = this.random.nextLong() / 2L * 2L + 1L;
      this.random.setSeed(par2 * i1 + par3 * j1 ^ this.world.getSeed());
   }

   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
      return true;
   }

   public void flushToDisk() {
   }

   public boolean tick() {
      return false;
   }

   public boolean canSave() {
      return true;
   }

   public String makeString() {
      return "DangerDimension";
   }

   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
      Biome biomegenbase = this.world.getBiome(new BlockPos(par2, 64, par4));
      return biomegenbase.getSpawnableList(par1EnumCreatureType);
   }

   public BlockPos func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
      return null;
   }

   public int getLoadedChunkCount() {
      return 0;
   }

    public void recreateStructures(int par1, int par2) {
    }
    public void populate(int x, int z) { this.populate((IChunkProvider)null, x, z); }

    public boolean generateStructures(Chunk chunk, int x, int z) { return false; }
    public void recreateStructures(Chunk chunk, int x, int z) { }
    public BlockPos getNearestStructurePos(World world, String name, BlockPos pos, boolean findUnexplored) { return null; }
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType type, BlockPos pos) { return this.world.getBiome(pos).getSpawnableList(type); }

   public void addScragglyTrees(World world, int chunkX, int chunkZ, Chunk chunk) {
      int howmany = 1 + this.random.nextInt(10);
      if (OreSpawn.LessLag == 1) {
         howmany /= 2;
      }

      if (OreSpawn.LessLag == 2) {
         howmany /= 4;
      }

      if (howmany != 0) {
         for (int i = 0; i < howmany; i++) {
            int posX = 2 + chunkX + this.random.nextInt(12);
            int posZ = 2 + chunkZ + this.random.nextInt(12);

            for (int posY = 20; posY > 2; posY--) {
               if (OreSpawn.getBlockIDInChunk(chunk, posX, posY - 1, posZ) == Blocks.GRASS) {
                  this.ScragglyTreeWithBranches(world, posX, posY, posZ, chunk);
                  break;
               }
            }
         }
      }
   }

   public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz, Chunk chunk) {
      for (int k = 0; k < len; k++) {
         int ix = this.random.nextInt(2) - this.random.nextInt(2) + biasx;
         int iz = this.random.nextInt(2) - this.random.nextInt(2) + biasz;
         if (ix > 1) {
            ix = 1;
         }

         if (ix < -1) {
            ix = -1;
         }

         if (iz > 1) {
            iz = 1;
         }

         if (iz < -1) {
            iz = -1;
         }

         int iy = this.random.nextInt(3) > 0 ? 1 : 0;
         x += ix;
         z += iz;
         y += iy;
         Block bid = OreSpawn.getBlockIDInChunk(chunk, x, y, z);
         if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawn.MyAppleLeaves) {
            return;
         }

         OreSpawn.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.LOG, 0);

         for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
               if (this.random.nextInt(2) == 1) {
                  bid = OreSpawn.getBlockIDInChunk(chunk, x + m, y, z + n);
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, OreSpawn.MyAppleLeaves, 0);
                  }
               }
            }
         }

         if (this.random.nextInt(2) == 1) {
            bid = OreSpawn.getBlockIDInChunk(chunk, x, y + 1, z);
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawn.MyAppleLeaves, 0);
            }
         }
      }
   }

   public void ScragglyTreeWithBranches(World world, int x, int y, int z, Chunk chunk) {
      int i = 1 + this.random.nextInt(3);
      int j = i + this.random.nextInt(12);

      for (int k = 0; k < i; k++) {
         Block bid = OreSpawn.getBlockIDInChunk(chunk, x, y + k, z);
         if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawn.MyAppleLeaves) {
            return;
         }

         OreSpawn.setBlockIDWithMetadataInChunk(chunk, x, y + k, z, Blocks.LOG, 0);
      }

      y += i - 1;

      for (int var16 = i; var16 < j; var16++) {
         int ix = this.random.nextInt(2) - this.random.nextInt(2);
         int iz = this.random.nextInt(2) - this.random.nextInt(2);
         int iy = this.random.nextInt(4) > 0 ? 1 : 0;
         x += ix;
         z += iz;
         y += iy;
         Block bid = OreSpawn.getBlockIDInChunk(chunk, x, y, z);
         if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawn.MyAppleLeaves) {
            break;
         }

         OreSpawn.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.LOG, 0);
         if (this.random.nextInt(4) == 1) {
            this.makeScragglyBranch(
               world,
               x,
               y,
               z,
               this.random.nextInt(1 + j - var16),
               this.random.nextInt(2) - this.random.nextInt(2),
               this.random.nextInt(2) - this.random.nextInt(2),
               chunk
            );
         }

         for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
               if (this.random.nextInt(2) == 1) {
                  bid = OreSpawn.getBlockIDInChunk(chunk, x + m, y, z + n);
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, OreSpawn.MyAppleLeaves, 0);
                  }
               }
            }
         }

         if (this.random.nextInt(2) == 1) {
            bid = OreSpawn.getBlockIDInChunk(chunk, x, y + 1, z);
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, OreSpawn.MyAppleLeaves, 0);
            }
         }
      }
   }

   @Override
   public boolean isInsideStructure(net.minecraft.world.World world, String structureName, net.minecraft.util.math.BlockPos pos) {
      return false;
   }
}

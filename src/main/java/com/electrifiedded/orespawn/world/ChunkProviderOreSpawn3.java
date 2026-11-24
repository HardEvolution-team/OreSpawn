package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;

public class ChunkProviderOreSpawn3 implements IChunkGenerator {
   private Random rand;
   private NoiseGeneratorOctaves field_147431_j;
   private NoiseGeneratorOctaves field_147432_k;
   private NoiseGeneratorOctaves field_147429_l;
   private NoiseGeneratorPerlin field_147430_m;
   public NoiseGeneratorOctaves noiseGen5;
   public NoiseGeneratorOctaves noiseGen6;
   public NoiseGeneratorOctaves mobSpawnerNoise;
   private World world;
   private final boolean mapFeaturesEnabled;
   private WorldType field_147435_p;
   private final double[] field_147434_q;
   private final float[] parabolicField;
   private double[] stoneNoise = new double[256];
   // Same as vanilla ChunkGeneratorOverworld: let other mods (Village Names etc.) swap the generators.
   private MapGenBase caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenCaves(), net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);
   private MapGenStronghold strongholdGenerator = (MapGenStronghold)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenStronghold(), net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.STRONGHOLD);
   private net.minecraft.world.gen.structure.MapGenStructure villageGenerator = (net.minecraft.world.gen.structure.MapGenStructure)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenMoreVillages(), net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE);
   private MapGenMineshaft mineshaftGenerator = (MapGenMineshaft)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenMineshaft(), net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT);
   private MapGenScatteredFeature scatteredFeatureGenerator = (MapGenScatteredFeature)net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenScatteredFeature(), net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.SCATTERED_FEATURE);
   private MapGenBase ravineGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(new MapGenRavine(), net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE);
   private Biome[] biomesForGeneration;
   double[] field_147427_d;
   double[] field_147428_e;
   double[] field_147425_f;
   double[] field_147426_g;
   int[][] field_73219_j = new int[32][32];

   public ChunkProviderOreSpawn3(World par1World, long par2, boolean par4) {
      this.world = par1World;
      this.mapFeaturesEnabled = par4;
      this.field_147435_p = par1World.getWorldInfo().getTerrainType();
      this.rand = new Random(par2);
      this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
      this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
      this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
      this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
      this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
      this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
      this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
      this.field_147434_q = new double[825];
      this.parabolicField = new float[25];

      for (int j = -2; j <= 2; j++) {
         for (int k = -2; k <= 2; k++) {
            float f = 10.0F / MathHelper.sqrt(j * j + k * k + 0.2F);
            this.parabolicField[j + 2 + (k + 2) * 5] = f;
         }
      }

      NoiseGenerator[] noiseGens = new NoiseGenerator[]{
         this.field_147431_j, this.field_147432_k, this.field_147429_l, this.field_147430_m, this.noiseGen5, this.noiseGen6, this.mobSpawnerNoise
      };
// PORT-TODO: Cleanroom's noise hook requires a typed context, not the legacy generator array
      this.field_147431_j = (NoiseGeneratorOctaves)noiseGens[0];
      this.field_147432_k = (NoiseGeneratorOctaves)noiseGens[1];
      this.field_147429_l = (NoiseGeneratorOctaves)noiseGens[2];
      this.field_147430_m = (NoiseGeneratorPerlin)noiseGens[3];
      this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
      this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
      this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
   }

   public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_) {
      byte b0 = 63;
      this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
      this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);

      for (int k = 0; k < 4; k++) {
         int l = k * 5;
         int i1 = (k + 1) * 5;

         for (int j1 = 0; j1 < 4; j1++) {
            int k1 = (l + j1) * 33;
            int l1 = (l + j1 + 1) * 33;
            int i2 = (i1 + j1) * 33;
            int j2 = (i1 + j1 + 1) * 33;

            for (int k2 = 0; k2 < 32; k2++) {
               double d0 = 0.125;
               double d1 = this.field_147434_q[k1 + k2];
               double d2 = this.field_147434_q[l1 + k2];
               double d3 = this.field_147434_q[i2 + k2];
               double d4 = this.field_147434_q[j2 + k2];
               double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
               double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
               double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
               double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

               for (int l2 = 0; l2 < 8; l2++) {
                  double d9 = 0.25;
                  double d10 = d1;
                  double d11 = d2;
                  double d12 = (d3 - d1) * d9;
                  double d13 = (d4 - d2) * d9;

                  for (int i3 = 0; i3 < 4; i3++) {
                     int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                     short short1 = 256;
                     j3 -= short1;
                     double d14 = 0.25;
                     double d16 = (d11 - d10) * d14;
                     double d15 = d10 - d16;

                     for (int k3 = 0; k3 < 4; k3++) {
                        if ((d15 += d16) > 0.0) {
                           p_147424_3_[j3 += short1] = Blocks.STONE;
                        } else if (k2 * 8 + l2 < b0) {
                           p_147424_3_[j3 += short1] = Blocks.WATER;
                        } else {
                           p_147424_3_[j3 += short1] = null;
                        }
                     }

                     d10 += d12;
                     d11 += d13;
                  }

                  d1 += d5;
                  d2 += d6;
                  d3 += d7;
                  d4 += d8;
               }
            }
         }
      }
   }

   public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, Biome[] p_147422_5_) {
      double d0 = 0.03125;
      this.stoneNoise = this.field_147430_m.getRegion(this.stoneNoise, p_147422_1_ * 16, p_147422_2_ * 16, 16, 16, d0 * 2.0, d0 * 2.0, 1.0);

      for (int k = 0; k < 16; k++) {
         for (int l = 0; l < 16; l++) {
            Biome biomegenbase = p_147422_5_[l + k * 16];
             // Applied to the primer in generateChunk after terrain noise is built.
         }
      }
   }

   public Chunk provideChunk(int par1, int par2) {
       return this.generateChunk(par1, par2);
   }

   public Chunk generateChunk(int par1, int par2) {
      this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
      Block[] ablock = new Block[65536];
      byte[] abyte = new byte[65536];
      this.func_147424_a(par1, par2, ablock);
      this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
      this.replaceBlocksForBiome(par1, par2, ablock, abyte, this.biomesForGeneration);
       net.minecraft.world.chunk.ChunkPrimer primer = this.toPrimer(ablock);
       for (int x=0;x<16;x++) for (int z=0;z<16;z++) this.biomesForGeneration[z+x*16].genTerrainBlocks(this.world, this.rand, primer, par1*16+x, par2*16+z, this.stoneNoise[z+x*16]);
        this.caveGenerator.generate(this.world, par1, par2, primer);
        this.ravineGenerator.generate(this.world, par1, par2, primer);
      if (this.mapFeaturesEnabled) {
          this.villageGenerator.generate(this.world, par1, par2, primer);
          this.scatteredFeatureGenerator.generate(this.world, par1, par2, primer);
      }

       Chunk chunk = new Chunk(this.world, primer, par1, par2);
       OreSpawn.Chunker.generateOresInChunk(this.world, this.rand, par1 * 16, par2 * 16, chunk);
       this.setChunkBiomes(chunk, this.biomesForGeneration);
       chunk.generateSkylightMap();
      return chunk;
   }

   private void setChunkBiomes(Chunk chunk, Biome[] biomes) {
      byte[] biomeIds = chunk.getBiomeArray();
      for (int i = 0; i < biomeIds.length; i++) biomeIds[i] = (byte)Biome.getIdForBiome(biomes[i]);
   }

   private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_) {
      double d0 = 684.412;
      double d1 = 684.412;
      double d2 = 512.0;
      double d3 = 512.0;
      this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0, 200.0, 0.5);
      this.field_147427_d = this.field_147429_l
         .generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001, 4.277575000000001, 8.555150000000001);
      this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
      this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
      boolean flag1 = false;
      boolean flag = false;
      int l = 0;
      int i1 = 0;
      double d4 = 8.5;

      for (int j1 = 0; j1 < 5; j1++) {
         for (int k1 = 0; k1 < 5; k1++) {
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            byte b0 = 2;
            Biome biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

            for (int l1 = -b0; l1 <= b0; l1++) {
               for (int i2 = -b0; i2 <= b0; i2++) {
                  Biome biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                  float f3 = biomegenbase1.getBaseHeight();
                  float f4 = biomegenbase1.getHeightVariation();
                  if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0F) {
                     f3 = 1.0F + f3 * 2.0F;
                     f4 = 1.0F + f4 * 4.0F;
                  }

                  float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);
                  if (biomegenbase1.getBaseHeight() > biomegenbase.getBaseHeight()) {
                     f5 /= 2.0F;
                  }

                  f += f4 * f5;
                  f1 += f3 * f5;
                  f2 += f5;
               }
            }

            f /= f2;
            f1 /= f2;
            f = f * 0.9F + 0.1F;
            f1 = (f1 * 4.0F - 1.0F) / 8.0F;
            double d13 = this.field_147426_g[i1] / 8000.0;
            if (d13 < 0.0) {
               d13 = -d13 * 0.3;
            }

            d13 = d13 * 3.0 - 2.0;
            if (d13 < 0.0) {
               d13 /= 2.0;
               if (d13 < -1.0) {
                  d13 = -1.0;
               }

               d13 /= 1.4;
               d13 /= 2.0;
            } else {
               if (d13 > 1.0) {
                  d13 = 1.0;
               }

               d13 /= 8.0;
            }

            i1++;
            double d12 = f1;
            double d14 = f;
            d12 += d13 * 0.2;
            d12 = d12 * 8.5 / 8.0;
            double d5 = 8.5 + d12 * 4.0;

            for (int j2 = 0; j2 < 33; j2++) {
               double d6 = (j2 - d5) * 12.0 * 128.0 / 256.0 / d14;
               if (d6 < 0.0) {
                  d6 *= 4.0;
               }

               double d7 = this.field_147428_e[l] / 512.0;
               double d8 = this.field_147425_f[l] / 512.0;
               double d9 = (this.field_147427_d[l] / 10.0 + 1.0) / 2.0;
               double d10 = MathHelper.clampedLerp(d7, d8, d9) - d6;
               if (j2 > 29) {
                  double d11 = (j2 - 29) / 3.0F;
                  d10 = d10 * (1.0 - d11) + -10.0 * d11;
               }

               this.field_147434_q[l] = d10;
               l++;
            }
         }
      }
   }

   public boolean chunkExists(int par1, int par2) {
      return true;
   }

    public void populate(int par2, int par3) {
      BlockFalling.fallInstantly = true;
      int k = par2 * 16;
      int l = par3 * 16;
      Biome biomegenbase = this.world.getBiome(new BlockPos(k + 16, 64, l + 16));
      this.rand.setSeed(this.world.getSeed());
      long i1 = this.rand.nextLong() / 2L * 2L + 1L;
      long j1 = this.rand.nextLong() / 2L * 2L + 1L;
      this.rand.setSeed(par2 * i1 + par3 * j1 ^ this.world.getSeed());
      boolean flag = false;
      net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, par2, par3, flag);
      if (this.mapFeaturesEnabled) {
         net.minecraft.util.math.ChunkPos chunkPos = new net.minecraft.util.math.ChunkPos(par2, par3);
         this.mineshaftGenerator.generateStructure(this.world, this.rand, chunkPos);
         flag = this.villageGenerator.generateStructure(this.world, this.rand, chunkPos);
         this.strongholdGenerator.generateStructure(this.world, this.rand, chunkPos);
         this.scatteredFeatureGenerator.generateStructure(this.world, this.rand, chunkPos);
      }

      if (biomegenbase != net.minecraft.init.Biomes.DESERT
         && biomegenbase != net.minecraft.init.Biomes.DESERT_HILLS
         && !flag
         && this.rand.nextInt(4) == 0
          && TerrainGen.populate(this, this.world, this.rand, par2, par3, flag, EventType.LAKE)) {
         int k1 = k + this.rand.nextInt(16) + 8;
         int l1 = this.rand.nextInt(256);
         int i2 = l + this.rand.nextInt(16) + 8;
         new WorldGenLakes(Blocks.WATER).generate(this.world, this.rand, new BlockPos(k1, l1, i2));
      }

       if (TerrainGen.populate(this, this.world, this.rand, par2, par3, flag, EventType.LAVA) && !flag && this.rand.nextInt(8) == 0) {
         int k1 = k + this.rand.nextInt(16) + 8;
         int l1 = this.rand.nextInt(this.rand.nextInt(248) + 8);
         int i2 = l + this.rand.nextInt(16) + 8;
         if (l1 < 63 || this.rand.nextInt(10) == 0) {
            new WorldGenLakes(Blocks.LAVA).generate(this.world, this.rand, new BlockPos(k1, l1, i2));
         }
      }

       boolean doGen = TerrainGen.populate(this, this.world, this.rand, par2, par3, flag, EventType.DUNGEON);

      for (int k1 = 0; doGen && k1 < 8; k1++) {
         int l1 = k + this.rand.nextInt(16) + 8;
         int i2 = this.rand.nextInt(256);
         int j2 = l + this.rand.nextInt(16) + 8;
         new WorldGenDungeons().generate(this.world, this.rand, new BlockPos(l1, i2, j2));
      }

       biomegenbase.decorate(this.world, this.rand, new BlockPos(k, 0, l));
      WorldEntitySpawner.performWorldGenSpawning(this.world, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
      k += 8;
      l += 8;
       doGen = TerrainGen.populate(this, this.world, this.rand, par2, par3, flag, EventType.ICE);

      for (int var21 = 0; doGen && var21 < 16; var21++) {
         for (int l1 = 0; l1 < 16; l1++) {
             int i2 = this.world.getPrecipitationHeight(new BlockPos(k + var21, 0, l + l1)).getY();
             if (this.world.canBlockFreezeWater(new BlockPos(var21 + k, i2 - 1, l1 + l))) {
               this.world.setBlockState(new BlockPos(var21 + k, i2 - 1, l1 + l), (Blocks.ICE).getStateFromMeta(0), 2);
            }

             if (this.world.canSnowAt(new BlockPos(var21 + k, i2, l1 + l), true)) {
               this.world.setBlockState(new BlockPos(var21 + k, i2, l1 + l), (Blocks.SNOW_LAYER).getStateFromMeta(0), 2);
            }
         }
      }

      net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, par2, par3, flag);
      BlockFalling.fallInstantly = false;
   }

   private static java.lang.reflect.Field structureMapField;

   /** True when the XZ area overlaps any village piece of this dimension (height ignored). */
   @SuppressWarnings("unchecked")
   public boolean villageOverlaps(int minX, int minZ, int maxX, int maxZ) {
      if (!this.mapFeaturesEnabled) return false;
      try {
         if (structureMapField == null) {
            structureMapField = net.minecraftforge.fml.common.ObfuscationReflectionHelper.findField(net.minecraft.world.gen.structure.MapGenStructure.class, "field_75053_d");
         }
         java.util.Map<Long, net.minecraft.world.gen.structure.StructureStart> map =
            (java.util.Map<Long, net.minecraft.world.gen.structure.StructureStart>)structureMapField.get(this.villageGenerator);
         for (net.minecraft.world.gen.structure.StructureStart start : map.values()) {
            if (!start.isSizeableStructure()) continue;
            net.minecraft.world.gen.structure.StructureBoundingBox bb = start.getBoundingBox();
            if (bb.maxX < minX || bb.minX > maxX || bb.maxZ < minZ || bb.minZ > maxZ) continue;
            for (net.minecraft.world.gen.structure.StructureComponent c : start.getComponents()) {
               net.minecraft.world.gen.structure.StructureBoundingBox b = c.getBoundingBox();
               if (b.maxX >= minX && b.minX <= maxX && b.maxZ >= minZ && b.minZ <= maxZ) return true;
            }
         }
         return false;
      } catch (Throwable t) {
         for (int y = 40; y <= 140; y += 3) {
            if (this.villageGenerator.isInsideStructure(new BlockPos((minX + maxX) / 2, y, (minZ + maxZ) / 2))) return true;
         }
         return false;
      }
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
      return "MiningDimension";
   }

   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
      Biome biomegenbase = this.world.getBiome(new BlockPos(par2, 64, par4));
       return par1EnumCreatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.isInsideStructure(new BlockPos(par2, par3, par4))
         ? this.scatteredFeatureGenerator.getMonsters()
         : biomegenbase.getSpawnableList(par1EnumCreatureType);
   }

   public BlockPos func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
       return "Stronghold".equals(p_147416_2_) && this.strongholdGenerator != null
          ? this.strongholdGenerator.getNearestStructurePos(p_147416_1_, new BlockPos(p_147416_3_, p_147416_4_, p_147416_5_), false)
         : null;
   }

   public int getLoadedChunkCount() {
      return 0;
   }

    public void recreateStructures(int par1, int par2) {
      if (this.mapFeaturesEnabled) {
          this.mineshaftGenerator.generate(this.world, par1, par2, null);
          this.villageGenerator.generate(this.world, par1, par2, null);
          this.strongholdGenerator.generate(this.world, par1, par2, null);
          this.scatteredFeatureGenerator.generate(this.world, par1, par2, null);
       }
     }

    public boolean generateStructures(Chunk chunk, int x, int z) { return false; }
    public void recreateStructures(Chunk chunk, int x, int z) { this.recreateStructures(x, z); }
    public BlockPos getNearestStructurePos(World world, String name, BlockPos pos, boolean findUnexplored) {
       if (!this.mapFeaturesEnabled) return null;
       if ("Stronghold".equals(name)) return this.strongholdGenerator.getNearestStructurePos(world, pos, findUnexplored);
       if ("Village".equals(name)) return this.villageGenerator.getNearestStructurePos(world, pos, findUnexplored);
       if ("Mineshaft".equals(name)) return this.mineshaftGenerator.getNearestStructurePos(world, pos, findUnexplored);
       if ("Temple".equals(name)) return this.scatteredFeatureGenerator.getNearestStructurePos(world, pos, findUnexplored);
       return null;
    }
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType type, BlockPos pos) { return this.getPossibleCreatures(type, pos.getX(), pos.getY(), pos.getZ()); }
     private Chunk toChunk(Block[] ablock, byte[] abyte, int cx, int cz) { return new Chunk(this.world, this.toPrimer(ablock, abyte), cx, cz); }
     private net.minecraft.world.chunk.ChunkPrimer toPrimer(Block[] ablock) { return this.toPrimer(ablock, null); }
     private net.minecraft.world.chunk.ChunkPrimer toPrimer(Block[] ablock, byte[] metadata) {
        net.minecraft.world.chunk.ChunkPrimer primer = new net.minecraft.world.chunk.ChunkPrimer();
        for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) for (int y = 0; y < 256; y++) {
           int index = (x * 16 + z) * 256 + y;
           Block block = ablock[index];
           if (block != null) primer.setBlockState(x, y, z, block.getStateFromMeta(metadata == null ? 0 : metadata[index] & 255));
        }
        return primer;
     }

    @Override
    public boolean isInsideStructure(net.minecraft.world.World world, String structureName, net.minecraft.util.math.BlockPos pos) {
      if ("Stronghold".equals(structureName)) return this.strongholdGenerator.isInsideStructure(pos);
      if ("Mineshaft".equals(structureName)) return this.mineshaftGenerator.isInsideStructure(pos);
      if ("Village".equals(structureName)) return this.villageGenerator.isInsideStructure(pos);
      if ("Temple".equals(structureName)) return this.scatteredFeatureGenerator.isInsideStructure(pos);
      return false;
    }
}

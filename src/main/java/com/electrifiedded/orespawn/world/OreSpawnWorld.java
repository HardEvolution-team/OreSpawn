package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.item.ItemAppleSeed;
import com.electrifiedded.orespawn.item.ItemMagicApple;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.ExtraRegistry;
import com.electrifiedded.orespawn.util.SimpleChest;
import com.electrifiedded.orespawn.util.Trees;
import net.minecraftforge.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.oredict.OreDictionary;

public class OreSpawnWorld implements IWorldGenerator {
   private static final boolean CUBIC_CHUNKS = net.minecraftforge.fml.common.Loader.isModLoaded("cubicchunks");
   public static int recently_placed = 50;
   private static java.util.List<net.minecraft.block.state.IBlockState> universalOres;

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      if (!world.isRemote) {
         GenerationBlockBuffer.prepareChunk(world, chunkX, chunkZ);
         try (GenerationBlockBuffer ignored = GenerationBlockBuffer.begin(world)) {
          Chunk chunk = world.getChunk(chunkX, chunkZ);
          if (isOreSpawnDimension(world.provider.getDimension())) {
             this.generateUniversalOres(world, random, chunkX * 16, chunkZ * 16);
          }
         if (recently_placed > 0) {
            recently_placed--;
         }

         if (world.provider.getDimension() == OreSpawn.DimensionID) {
            this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
            boolean hugeTree = this.addHugeTree(world, random, chunkX * 16, chunkZ * 16, chunk);
            if (!hugeTree) {
               if (!this.addAppleTrees(world, random, chunkX * 16, chunkZ * 16, chunk)
                  && !this.addOtherTrees(world, random, chunkX * 16, chunkZ * 16)
                  && recently_placed == 0) {
                  this.addKingAltar(world, random, chunkX * 16, chunkZ * 16);
               }

               this.addVeggies(world, random, chunkX * 16, chunkZ * 16);
            }

            boolean rbd = false;
            rbd = this.addRubyDungeon(world, random, chunkX * 16, chunkZ * 16);
            if (!rbd) {
               this.addGenericDungeon(world, random, chunkX * 16, chunkZ * 16);
            }
         } else if (world.provider.getDimension() != OreSpawn.DimensionID2) {
            if (world.provider.getDimension() == OreSpawn.DimensionID3) {
               if (OreSpawn.MosquitoEnable != 0) {
                  this.addMosquitos(world, random, chunkX * 16, chunkZ * 16);
               }

               // Don't build OreSpawn features on top of village houses/roads.
               if (!nearVillage(chunkGenerator, chunkX * 16, chunkZ * 16)) {
               this.addAnts(world, random, chunkX * 16, chunkZ * 16, 4);
               this.addAppleTrees(world, random, chunkX * 16, chunkZ * 16, chunk);
               this.addGenericDungeon(world, random, chunkX * 16, chunkZ * 16);
               if (recently_placed == 0) {
                  this.addDamselInDistress(world, random, chunkX * 16, chunkZ * 16);
               }

               if (recently_placed == 0) {
                  this.addSpiderHangout(world, random, chunkX * 16, chunkZ * 16);
               }

               if (recently_placed == 0) {
                  this.addRedAntHangout(world, random, chunkX * 16, chunkZ * 16);
               }
               }
            } else if (world.provider.getDimension() == OreSpawn.DimensionID4) {
               if (recently_placed == 0 && random.nextInt(100) == 0 && this.D4BigSpaceCheck(world, chunkX * 16, 7, chunkZ * 16)) {
                  int i = random.nextInt(19);
                  if (i < 3) {
                     this.addD4Castle(world, random, chunkX * 16, chunkZ * 16);
                  } else if (i < 7) {
                     this.addD4GenericDungeon(world, random, chunkX * 16, chunkZ * 16);
                  } else {
                     if (i == 7) {
                        this.addD4EnderCastle(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 8) {
                        this.addD4IncaPyramid(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 9) {
                        this.addD4RobotLab(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 10) {
                        this.addD4Mini(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 11) {
                        this.addD4RubyDungeon(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 12) {
                        this.addD4CephadromeAltar(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 13) {
                        this.addD4Greenhouse(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 14) {
                        this.addD4NightmareRookery(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 15) {
                        this.addD4StinkyHouse(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 16) {
                        this.addD4WhiteHouse(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 17) {
                        this.addPumpkin(world, random, chunkX * 16, chunkZ * 16);
                     }

                     if (i == 18) {
                        this.addD4Rainbow(world, random, chunkX * 16, chunkZ * 16);
                     }
                  }
               }

               int i = random.nextInt(300);
               if (i == 0) {
                  this.addD4CloudShark(world, random, chunkX * 16, chunkZ * 16);
               }

               this.addUnstableAnts(world, random, chunkX * 16, chunkZ * 16);
               this.addIslands(world, random, chunkX * 16, chunkZ * 16);
               this.addD4Rocks(world, random, chunkX * 16, chunkZ * 16);
            } else if (world.provider.getDimension() == OreSpawn.DimensionID5) {
               if (!this.addFairyTree(world, random, chunkX * 16, chunkZ * 16)) {
                  this.addCrystalTermites(world, random, chunkX * 16, chunkZ * 16);
                  if (recently_placed == 0) {
                     if (!this.addRotatorStation(world, random, chunkX * 16, chunkZ * 16)
                        && !this.addUrchinSpawner(world, random, chunkX * 16, chunkZ * 16)
                        && !this.addCrystalHauntedHouse(world, random, chunkX * 16, chunkZ * 16)
                        && !this.addRoundRotator(world, random, chunkX * 16, chunkZ * 16)) {
                        this.addCrystalBattleTower(world, random, chunkX * 16, chunkZ * 16);
                     }

                     this.addIrukandji(world, random, chunkX * 16, chunkZ * 16);
                  }
               }

               this.addCrystalChestsAndSpawners(world, random, chunkX * 16, chunkZ * 16);
                if (world.rand.nextInt(4) == 1) {
                  this.addRocks(world, random, chunkX * 16, chunkZ * 16);
               }
            } else if (world.provider.getDimension() == OreSpawn.DimensionID6) {
               this.addButterfliesAndMoths(world, random, chunkX * 16, chunkZ * 16);
               this.addVeggies(world, random, chunkX * 16, chunkZ * 16);
               this.addAnts(world, random, chunkX * 16, chunkZ * 16, 2);
            } else {
               switch (world.provider.getDimension()) {
                  case -1:
                     this.generateNether(world, random, chunkX * 16, chunkZ * 16);
                     break;
                   case 0:
                      this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
                      this.generateChunkOres(world, random, chunkX * 16, chunkZ * 16);
                      this.generateOverworldTrees(world, random, chunkX * 16, chunkZ * 16, chunk);
                      break;
                  case 1:
                     this.generateEnd(world, random, chunkX * 16, chunkZ * 16);
               }
            }
         } else {
            this.generateRuby(world, random, chunkX * 16, chunkZ * 16);
            if (OreSpawn.LessOre == 0) {
               this.generateRuby(world, random, chunkX * 16, chunkZ * 16);
               this.generateRuby(world, random, chunkX * 16, chunkZ * 16);

               for (int i = 0; i < 45; i++) {
                  int randPosX = chunkX * 16 + random.nextInt(16);
                  int randPosY = random.nextInt(128);
                  int randPosZ = chunkZ * 16 + random.nextInt(16);
                  if (randPosY < 50) {
                     new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 7).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
                  }
               }

               for (int ix = 0; ix < 25; ix++) {
                  int randPosX = chunkX * 16 + random.nextInt(16);
                  int randPosY = random.nextInt(128);
                  int randPosZ = chunkZ * 16 + random.nextInt(16);
                  if (randPosY < 50) {
                     new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 4).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
                  }
               }
            }

            if (recently_placed == 0 && random.nextInt(95) == 1) {
               int ixx = random.nextInt(7);
               if (ixx == 0) {
                  this.addBasiliskMaze(world, random, chunkX * 16, chunkZ * 16);
               }

               if (ixx == 1) {
                  this.addKyuubiDungeon(world, random, chunkX * 16, chunkZ * 16);
               }

               if (ixx == 2) {
                  this.addBeeHive(world, random, chunkX * 16, chunkZ * 16);
               }

               if (ixx == 3) {
                  this.addShadowDungeon(world, random, chunkX * 16, chunkZ * 16);
               }

               if (ixx == 4) {
                  this.addAlienWTF(world, random, chunkX * 16, chunkZ * 16);
               }

               if (ixx == 5) {
                  this.addEnderKnight(world, random, chunkX * 16, chunkZ * 16);
               }

               if (ixx == 6) {
                  this.addLeonNest(world, random, chunkX * 16, chunkZ * 16);
               }
            } else {
               this.addGenericDungeon(world, random, chunkX * 16, chunkZ * 16);
            }

            this.addLavaAndWater(world, random, chunkX * 16, chunkZ * 16);
            this.addAnts(world, random, chunkX * 16, chunkZ * 16, 2);
            this.addAnts(world, random, chunkX * 16, chunkZ * 16, 2);
            this.addMosquitos(world, random, chunkX * 16, chunkZ * 16);
            this.addMosquitos(world, random, chunkX * 16, chunkZ * 16);
            this.addVeggies(world, random, chunkX * 16, chunkZ * 16);
            this.addRocks(world, random, chunkX * 16, chunkZ * 16);
         }
         }
      }
   }

   private static boolean nearVillage(IChunkGenerator gen, int x, int z) {
      // Features may reach ~16 blocks out of their chunk; keep a margin around village pieces.
      return gen instanceof ChunkProviderOreSpawn3 && ((ChunkProviderOreSpawn3)gen).villageOverlaps(x - 16, z - 16, x + 31, z + 31);
   }

   private static boolean isOreSpawnDimension(int dimension) {
      return dimension >= OreSpawn.DimensionID && dimension <= OreSpawn.DimensionID6;
   }

   /** Adds Forge ores and every dried egg to OreSpawn's stone-based dimensions. */
   private void generateUniversalOres(World world, Random random, int chunkX, int chunkZ) {
      java.util.List<net.minecraft.block.state.IBlockState> ores = universalOreStates();
      if (ores.isEmpty()) return;

      int attempts = world.provider.getDimension() == OreSpawn.DimensionID3 ? 72 : 18;
      if (OreSpawn.LessOre != 0) attempts = Math.max(1, attempts / 3);
      for (int i = 0; i < attempts; i++) {
         net.minecraft.block.state.IBlockState ore = ores.get(random.nextInt(ores.size()));
         BlockPos origin = new BlockPos(chunkX + random.nextInt(16), 4 + random.nextInt(124), chunkZ + random.nextInt(16));
         int clump = ore.getBlock() instanceof com.electrifiedded.orespawn.block.OreGenericEgg ? 1 + random.nextInt(3) : 2 + random.nextInt(6);
         BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(origin);
         for (int j = 0; j < clump; j++) {
            if (j > 0) pos.move(net.minecraft.util.EnumFacing.random(random));
            net.minecraft.block.state.IBlockState host = OreSpawn.getBlockStateFast(world, pos);
            Block block = host.getBlock();
            if (block == Blocks.STONE || block == Blocks.NETHERRACK || block == Blocks.END_STONE) {
               OreSpawn.setBlockStateFast(world, pos.toImmutable(), ore, 2);
            }
         }
      }
   }

   private static java.util.List<net.minecraft.block.state.IBlockState> universalOreStates() {
      if (universalOres != null) return universalOres;
      java.util.LinkedHashMap<String, net.minecraft.block.state.IBlockState> states = new java.util.LinkedHashMap<>();
      for (String oreName : OreDictionary.getOreNames()) {
         if (!oreName.startsWith("ore")) continue;
         for (ItemStack stack : OreDictionary.getOres(oreName, false)) {
            if (stack.isEmpty()) continue;
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block == Blocks.AIR) continue;
            int meta = stack.getMetadata();
            if (meta == OreDictionary.WILDCARD_VALUE) meta = 0;
            try {
               net.minecraft.block.state.IBlockState state = block.getStateFromMeta(meta);
               if (!block.hasTileEntity(state)) {
                  ResourceLocation name = block.getRegistryName();
                  states.put((name == null ? block.toString() : name.toString()) + ':' + meta, state);
               }
            } catch (RuntimeException ignored) {
            }
         }
      }
      for (Block block : net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS) {
         ResourceLocation name = block.getRegistryName();
         if (name == null || !name.getPath().toLowerCase(java.util.Locale.ROOT).contains("ore")) continue;
         net.minecraft.block.state.IBlockState state = block.getDefaultState();
         net.minecraft.block.material.Material material = state.getMaterial();
         if (block != Blocks.AIR && !block.hasTileEntity(state)
               && (material == net.minecraft.block.material.Material.ROCK
                  || material == net.minecraft.block.material.Material.IRON)) {
            states.put(name.toString(), state);
         }
      }
      for (Block block : ExtraRegistry.spawnBlocks()) {
         states.put(block.getRegistryName().toString(), block.getDefaultState());
      }
      universalOres = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(states.values()));
      return universalOres;
   }

   private void generateEnd(World world, Random random, int chunkX, int chunkZ) {
      this.addEndAnts(world, random, chunkX, chunkZ);
      int i = world.rand.nextInt(4);
      if (i == 0) {
         this.addEndKnights(world, random, chunkX, chunkZ);
      }

      if (i == 1) {
         this.addEndReapers(world, random, chunkX, chunkZ);
      }

      if (i == 2) {
         this.addHospital(world, random, chunkX, chunkZ);
      }

      if (i == 3) {
         this.addEnderCastle(world, random, chunkX, chunkZ);
      }
   }

   private void generateNether(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.MosquitoEnable != 0) {
         this.addNetherMosquitos(world, random, chunkX, chunkZ);
      }

      this.addNetherAnts(world, random, chunkX, chunkZ);
      int patchy = 15 + random.nextInt(10);
      if (OreSpawn.LessOre != 0) {
         patchy /= 3;
      }

      for (int i = 0; i < patchy; i++) {
         int randPosX = 3 + chunkX + random.nextInt(13);
         int randPosY = random.nextInt(108) + 10;
         int randPosZ = 3 + chunkZ + random.nextInt(13);
         new WorldGenMinable(OreSpawn.MyLavafoamBlock.getDefaultState(), 6,
            net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.NETHERRACK))
            .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
      }

      patchy = 5 + random.nextInt(5);
      if (OreSpawn.LessOre != 0) {
         patchy /= 3;
      }

      for (int i = 0; i < patchy; i++) {
         int randPosX = 3 + chunkX + random.nextInt(13);
         int randPosY = random.nextInt(108) + 10;
         int randPosZ = 3 + chunkZ + random.nextInt(13);
         new WorldGenMinable(OreSpawn.MyOreRubyBlock.getDefaultState(), 2,
            net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.NETHERRACK))
            .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
      }
   }

   public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
      boolean ahh = false;
      this.addStrawberries(world, random, chunkX, chunkZ);
      this.addCorn(world, random, chunkX, chunkZ);
      this.addTomatoes(world, random, chunkX, chunkZ);
      this.addVeggies(world, random, chunkX, chunkZ);
      this.addButterfliesAndMoths(world, random, chunkX, chunkZ);
      if (OreSpawn.MosquitoEnable != 0) {
         this.addMosquitos(world, random, chunkX, chunkZ);
      }

      if (OreSpawn.DisableOverworldDungeons == 0 && world.provider.getDimension() == 0 && recently_placed == 0) {
         int i = world.rand.nextInt(6);
         if (i == 0) {
            this.addPlayPool(world, random, chunkX, chunkZ);
         }

         if (i == 1) {
            this.addWaterDragonLair(world, random, chunkX, chunkZ);
         }

         if (i == 2) {
            this.addGoldFishBowl(world, random, chunkX, chunkZ);
         }

         if (i == 3) {
            this.addGirlfriendIsland(world, random, chunkX, chunkZ);
         }

         if (i == 4) {
            this.addMonsterIsland(world, random, chunkX, chunkZ);
         }

         if (i == 5) {
            this.addFrogPond(world, random, chunkX, chunkZ);
         }

         ahh = this.addANest(world, random, chunkX, chunkZ);
         if (!ahh) {
            ahh = this.addHauntedHouse(world, random, chunkX, chunkZ);
         }

         if (!ahh) {
            ahh = this.addLeafMonster(world, random, chunkX, chunkZ);
         }

         if (!ahh) {
            ahh = this.addSpitBug(world, random, chunkX, chunkZ);
         }

         if (!ahh) {
            ahh = this.addIgloo(world, random, chunkX, chunkZ);
         }

         if (!ahh) {
            ahh = this.addBouncyCastle(world, random, chunkX, chunkZ);
         }

         if (!ahh) {
            ahh = this.addRubberDuckyPond(world, random, chunkX, chunkZ);
         }
      }

      this.addAnts(world, random, chunkX, chunkZ, 4);
      net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
      if (isBiome(b, "River") || isBiome(b, "Extreme Hills") || isBiome(b, "Desert")) {
         this.addRocks(world, random, chunkX, chunkZ);
      }
   }

   public void generateRuby(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.Ruby_stats.rate > 0) {
         int patchy = OreSpawn.Ruby_stats.rate + random.nextInt(7);

         for (int i = 0; i < patchy; i++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.Ruby_stats.maxdepth && randPosY >= OreSpawn.Ruby_stats.mindepth) {
               for (int m = randPosY; m > 5; m--) {
                  Block bid = world.getBlockState(new BlockPos(randPosX, m, randPosZ)).getBlock();
                  if (bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
                     bid = world.getBlockState(new BlockPos(randPosX, m - 1, randPosZ)).getBlock();
                     if (bid == Blocks.STONE) {
                        OreSpawn.setBlockFast(world, randPosX, m - 1, randPosZ, OreSpawn.MyOreRubyBlock, 0, 2);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

    public void generateChunkOres(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.SpawnOres_stats.rate > 0) {
         int patchy = OreSpawn.SpawnOres_stats.rate + random.nextInt(20);
         if (random.nextInt(20) == 0) {
            patchy += 30;
         }

         if (OreSpawn.LessOre != 0) {
            patchy /= 3;
         }

         for (int i = 0; i < patchy; i++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.SpawnOres_stats.maxdepth && randPosY >= OreSpawn.SpawnOres_stats.mindepth) {
               if (random.nextInt(104) < 7) {
                  int j = random.nextInt(7);
                  Block b = Blocks.AIR;
                  switch (j) {
                     case 0:
                        b = OreSpawn.MyBrutalflySpawnBlock;
                        break;
                     case 1:
                        b = OreSpawn.MyNastysaurusSpawnBlock;
                        break;
                     case 2:
                        b = OreSpawn.MyPointysaurusSpawnBlock;
                        break;
                     case 3:
                        b = OreSpawn.MyCricketSpawnBlock;
                        break;
                     case 4:
                        b = OreSpawn.MyFrogSpawnBlock;
                        break;
                     case 5:
                        b = OreSpawn.MySpiderDriverSpawnBlock;
                        break;
                     case 6:
                        b = OreSpawn.MyCrabSpawnBlock;
                  }

                  new WorldGenMinable(b.getDefaultState(), OreSpawn.SpawnOres_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               } else {
                  int j = random.nextInt(98);
                  Block b = Blocks.AIR;
                  switch (j) {
                     case 0:
                        b = OreSpawn.MySpiderSpawnBlock;
                        break;
                     case 1:
                        b = OreSpawn.MyBatSpawnBlock;
                        break;
                     case 2:
                        b = OreSpawn.MyCowSpawnBlock;
                        break;
                     case 3:
                        b = OreSpawn.MyPigSpawnBlock;
                        break;
                     case 4:
                        b = OreSpawn.MySquidSpawnBlock;
                        break;
                     case 5:
                        b = OreSpawn.MyChickenSpawnBlock;
                        break;
                     case 6:
                        b = OreSpawn.MyCreeperSpawnBlock;
                        break;
                     case 7:
                        b = OreSpawn.MySkeletonSpawnBlock;
                        break;
                     case 8:
                        b = OreSpawn.MyZombieSpawnBlock;
                        break;
                     case 9:
                        b = OreSpawn.MySlimeSpawnBlock;
                        break;
                     case 10:
                        b = OreSpawn.MyGhastSpawnBlock;
                        break;
                     case 11:
                        b = OreSpawn.MyZombiePigmanSpawnBlock;
                        break;
                     case 12:
                        b = OreSpawn.MyEndermanSpawnBlock;
                        break;
                     case 13:
                        b = OreSpawn.MyCaveSpiderSpawnBlock;
                        break;
                     case 14:
                        b = OreSpawn.MySilverfishSpawnBlock;
                        break;
                     case 15:
                        b = OreSpawn.MyMagmaCubeSpawnBlock;
                        break;
                     case 16:
                        b = OreSpawn.MyWitchSpawnBlock;
                        break;
                     case 17:
                        b = OreSpawn.MySheepSpawnBlock;
                        break;
                     case 18:
                        b = OreSpawn.MyWolfSpawnBlock;
                        break;
                     case 19:
                        b = OreSpawn.MyMooshroomSpawnBlock;
                        break;
                     case 20:
                        b = OreSpawn.MyOcelotSpawnBlock;
                        break;
                     case 21:
                        b = OreSpawn.MyBlazeSpawnBlock;
                        break;
                     case 22:
                        b = OreSpawn.MyWitherSkeletonSpawnBlock;
                        break;
                     case 23:
                        b = OreSpawn.MyEnderDragonSpawnBlock;
                        break;
                     case 24:
                        b = OreSpawn.MySnowGolemSpawnBlock;
                        break;
                     case 25:
                        b = OreSpawn.MyIronGolemSpawnBlock;
                        break;
                     case 26:
                        b = OreSpawn.MyWitherBossSpawnBlock;
                        break;
                     case 27:
                        b = OreSpawn.MyGirlfriendSpawnBlock;
                        break;
                     case 28:
                        b = OreSpawn.MyRedCowSpawnBlock;
                        break;
                     case 29:
                        b = OreSpawn.MyGoldCowSpawnBlock;
                        break;
                     case 30:
                        b = OreSpawn.MyEnchantedCowSpawnBlock;
                        break;
                     case 31:
                        b = OreSpawn.MyMOTHRASpawnBlock;
                        break;
                     case 32:
                        b = OreSpawn.MyAloSpawnBlock;
                        break;
                     case 33:
                        b = OreSpawn.MyCryoSpawnBlock;
                        break;
                     case 34:
                        b = OreSpawn.MyCamaSpawnBlock;
                        break;
                     case 35:
                        b = OreSpawn.MyVeloSpawnBlock;
                        break;
                     case 36:
                        b = OreSpawn.MyHydroSpawnBlock;
                        break;
                     case 37:
                        b = OreSpawn.MyBasilSpawnBlock;
                        break;
                     case 38:
                        b = OreSpawn.MyDragonflySpawnBlock;
                        break;
                     case 39:
                        b = OreSpawn.MyEmperorScorpionSpawnBlock;
                        break;
                     case 40:
                        b = OreSpawn.MyScorpionSpawnBlock;
                        break;
                     case 41:
                        b = OreSpawn.MyCaveFisherSpawnBlock;
                        break;
                     case 42:
                        b = OreSpawn.MySpyroSpawnBlock;
                        break;
                     case 43:
                        b = OreSpawn.MyBaryonyxSpawnBlock;
                        break;
                     case 44:
                        b = OreSpawn.MyGammaMetroidSpawnBlock;
                        break;
                     case 45:
                        b = OreSpawn.MyCockateilSpawnBlock;
                        break;
                     case 46:
                        b = OreSpawn.MyKyuubiSpawnBlock;
                        break;
                     case 47:
                        b = OreSpawn.MyAlienSpawnBlock;
                        break;
                     case 48:
                        b = OreSpawn.MyAttackSquidSpawnBlock;
                        break;
                     case 49:
                        b = OreSpawn.MyWaterDragonSpawnBlock;
                        break;
                     case 50:
                        b = OreSpawn.MyKrakenSpawnBlock;
                        break;
                     case 51:
                        b = OreSpawn.MyLizardSpawnBlock;
                        break;
                     case 52:
                        b = OreSpawn.MyCephadromeSpawnBlock;
                        break;
                     case 53:
                        b = OreSpawn.MyDragonSpawnBlock;
                        break;
                     case 54:
                        b = OreSpawn.MyBeeSpawnBlock;
                        break;
                     case 55:
                        b = OreSpawn.MyHorseSpawnBlock;
                        break;
                     case 56:
                        b = OreSpawn.MyTrooperBugSpawnBlock;
                        break;
                     case 57:
                        b = OreSpawn.MySpitBugSpawnBlock;
                        break;
                     case 58:
                        b = OreSpawn.MyStinkBugSpawnBlock;
                        break;
                     case 59:
                        b = OreSpawn.MyOstrichSpawnBlock;
                        break;
                     case 60:
                        b = OreSpawn.MyGazelleSpawnBlock;
                        break;
                     case 61:
                        b = OreSpawn.MyChipmunkSpawnBlock;
                        break;
                     case 62:
                        b = OreSpawn.MyCreepingHorrorSpawnBlock;
                        break;
                     case 63:
                        b = OreSpawn.MyTerribleTerrorSpawnBlock;
                        break;
                     case 64:
                        b = OreSpawn.MyCliffRacerSpawnBlock;
                        break;
                     case 65:
                        b = OreSpawn.MyTriffidSpawnBlock;
                        break;
                     case 66:
                        b = OreSpawn.MyPitchBlackSpawnBlock;
                        break;
                     case 67:
                        b = OreSpawn.MyLurkingTerrorSpawnBlock;
                        break;
                     case 68:
                        b = OreSpawn.MyGodzillaPartSpawnBlock;
                        break;
                     case 69:
                        b = OreSpawn.MyGodzillaSpawnBlock;
                        break;
                     case 70:
                        b = OreSpawn.MySmallWormSpawnBlock;
                        break;
                     case 71:
                        b = OreSpawn.MyMediumWormSpawnBlock;
                        break;
                     case 72:
                        b = OreSpawn.MyLargeWormSpawnBlock;
                        break;
                     case 73:
                        b = OreSpawn.MyCassowarySpawnBlock;
                        break;
                     case 74:
                        b = OreSpawn.MyCloudSharkSpawnBlock;
                        break;
                     case 75:
                        b = OreSpawn.MyGoldFishSpawnBlock;
                        break;
                     case 76:
                        b = OreSpawn.MyLeafMonsterSpawnBlock;
                        break;
                     case 77:
                        b = OreSpawn.MyTshirtSpawnBlock;
                        break;
                     case 78:
                        b = OreSpawn.MyEnderKnightSpawnBlock;
                        break;
                     case 79:
                        b = OreSpawn.MyEnderReaperSpawnBlock;
                        break;
                     case 80:
                        b = OreSpawn.MyBeaverSpawnBlock;
                        break;
                     case 81:
                        b = OreSpawn.MyTRexSpawnBlock;
                        break;
                     case 82:
                        b = OreSpawn.MyHerculesSpawnBlock;
                        break;
                     case 83:
                        b = OreSpawn.MyMantisSpawnBlock;
                        break;
                     case 84:
                        b = OreSpawn.MyStinkySpawnBlock;
                        break;
                     case 85:
                        b = OreSpawn.MyBoyfriendSpawnBlock;
                        break;
                     case 86:
                        b = OreSpawn.MyTheKingPartSpawnBlock;
                        break;
                     case 87:
                        b = OreSpawn.MyEasterBunnySpawnBlock;
                        break;
                     case 88:
                        b = OreSpawn.MyCaterKillerSpawnBlock;
                        break;
                     case 89:
                        b = OreSpawn.MyMolenoidSpawnBlock;
                        break;
                     case 90:
                        b = OreSpawn.MySeaMonsterSpawnBlock;
                        break;
                     case 91:
                        b = OreSpawn.MySeaViperSpawnBlock;
                        break;
                     case 92:
                        b = OreSpawn.MyLeonSpawnBlock;
                        break;
                     case 93:
                        b = OreSpawn.MyHammerheadSpawnBlock;
                        break;
                     case 94:
                        b = OreSpawn.MyRubberDuckySpawnBlock;
                        break;
                     case 95:
                        b = OreSpawn.MyVillagerSpawnBlock;
                        break;
                     case 96:
                        b = OreSpawn.MyCriminalSpawnBlock;
                        break;
                     case 97:
                        b = OreSpawn.MyTheQueenPartSpawnBlock;
                  }

                  new WorldGenMinable(b.getDefaultState(), OreSpawn.SpawnOres_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }
      }

      if (OreSpawn.Uranium_stats.rate > 0) {
         int patchyx = OreSpawn.Uranium_stats.rate + random.nextInt(9);
         if (OreSpawn.LessOre != 0) {
            patchyx /= 3;
         }

         for (int ix = 0; ix < patchyx; ix++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.Uranium_stats.maxdepth && randPosY >= OreSpawn.Uranium_stats.mindepth) {
               new WorldGenMinable(OreSpawn.MyOreUraniumBlock.getDefaultState(),  OreSpawn.Uranium_stats.clumpsize)
                  .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
            }
         }
      }

      if (OreSpawn.Titanium_stats.rate > 0) {
         int patchyx = OreSpawn.Titanium_stats.rate + random.nextInt(9);
         if (OreSpawn.LessOre != 0) {
            patchyx /= 3;
         }

         for (int ixx = 0; ixx < patchyx; ixx++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.Titanium_stats.maxdepth && randPosY >= OreSpawn.Titanium_stats.mindepth) {
               new WorldGenMinable(OreSpawn.MyOreTitaniumBlock.getDefaultState(),  OreSpawn.Titanium_stats.clumpsize)
                  .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
            }
         }
      }

      if (OreSpawn.Amethyst_stats.rate > 0) {
         int patchyx = OreSpawn.Amethyst_stats.rate + random.nextInt(12);
         if (OreSpawn.LessOre != 0) {
            patchyx /= 3;
         }

         for (int ixxx = 0; ixxx < patchyx; ixxx++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.Amethyst_stats.maxdepth && randPosY >= OreSpawn.Amethyst_stats.mindepth) {
               new WorldGenMinable(OreSpawn.MyOreAmethystBlock.getDefaultState(),  OreSpawn.Amethyst_stats.clumpsize)
                  .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
            }
         }
      }

      if (OreSpawn.Salt_stats.rate > 0) {
         int patchyx = OreSpawn.Salt_stats.rate + random.nextInt(9);
         if (OreSpawn.LessOre != 0) {
            patchyx /= 3;
         }

         for (int ixxxx = 0; ixxxx < patchyx; ixxxx++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.Salt_stats.maxdepth && randPosY >= OreSpawn.Salt_stats.mindepth) {
               new WorldGenMinable(OreSpawn.MyOreSaltBlock.getDefaultState(),  OreSpawn.Salt_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
            }
         }
      }

      int patchyx = 4 + random.nextInt(4);
      if (OreSpawn.LessOre != 0) {
         patchyx /= 2;
      }

      for (int ixxxxx = 0; ixxxxx < patchyx; ixxxxx++) {
         int randPosX = 3 + chunkX + random.nextInt(10);
         int randPosY = random.nextInt(128);
         int randPosZ = 3 + chunkZ + random.nextInt(10);
         if (randPosY <= 50 && randPosY >= 5) {
            new WorldGenMinable(OreSpawn.RedAntTroll.getDefaultState(), 4).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
         }
      }

      patchyx = 4 + random.nextInt(4);
      if (OreSpawn.LessOre != 0) {
         patchyx /= 2;
      }

      for (int ixxxxxx = 0; ixxxxxx < patchyx; ixxxxxx++) {
         int randPosX = 3 + chunkX + random.nextInt(10);
         int randPosY = random.nextInt(128);
         int randPosZ = 3 + chunkZ + random.nextInt(10);
         if (randPosY <= 50 && randPosY >= 5) {
            new WorldGenMinable(OreSpawn.TermiteTroll.getDefaultState(), 4).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
         }
      }

      if (OreSpawn.Ruby_stats.rate > 0) {
         patchyx = OreSpawn.Ruby_stats.rate + random.nextInt(5);

         for (int ixxxxxxx = 0; ixxxxxxx < patchyx; ixxxxxxx++) {
            int randPosX = 3 + chunkX + random.nextInt(10);
            int randPosY = random.nextInt(128);
            int randPosZ = 3 + chunkZ + random.nextInt(10);
            if (randPosY <= OreSpawn.Ruby_stats.maxdepth && randPosY >= OreSpawn.Ruby_stats.mindepth) {
               for (int m = randPosY; m > 5; m--) {
                  Block bid = world.getBlockState(new BlockPos(randPosX, m, randPosZ)).getBlock();
                  if (bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
                     bid = world.getBlockState(new BlockPos(randPosX, m - 1, randPosZ)).getBlock();
                     if (bid == Blocks.STONE) {
                        OreSpawn.setBlockFast(world, randPosX, m - 1, randPosZ, OreSpawn.MyOreRubyBlock, 0, 2);
                        break;
                     }
                  }
               }
            }
         }
      }

      if (OreSpawn.LessOre == 0) {
         if (OreSpawn.Diamond_stats.rate > 0) {
            for (int ixxxxxxxx = 0; ixxxxxxxx < OreSpawn.Diamond_stats.rate; ixxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.Diamond_stats.maxdepth && randPosY >= OreSpawn.Diamond_stats.mindepth) {
                  new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(),  OreSpawn.Diamond_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }

         if (OreSpawn.BlkDiamond_stats.rate > 0) {
            for (int ixxxxxxxxx = 0; ixxxxxxxxx < OreSpawn.BlkDiamond_stats.rate; ixxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkDiamond_stats.maxdepth && randPosY >= OreSpawn.BlkDiamond_stats.mindepth) {
                  new WorldGenMinable(Blocks.DIAMOND_BLOCK.getDefaultState(),  OreSpawn.BlkDiamond_stats.clumpsize)
                     .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }

         if (OreSpawn.Emerald_stats.rate > 0) {
            for (int ixxxxxxxxxx = 0; ixxxxxxxxxx < OreSpawn.Emerald_stats.rate; ixxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.Emerald_stats.maxdepth && randPosY >= OreSpawn.Emerald_stats.mindepth) {
                  new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(),  OreSpawn.Emerald_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }

         if (OreSpawn.BlkEmerald_stats.rate > 0) {
            for (int ixxxxxxxxxxx = 0; ixxxxxxxxxxx < OreSpawn.BlkEmerald_stats.rate; ixxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkEmerald_stats.maxdepth && randPosY >= OreSpawn.BlkEmerald_stats.mindepth) {
                  new WorldGenMinable(Blocks.EMERALD_BLOCK.getDefaultState(),  OreSpawn.BlkEmerald_stats.clumpsize)
                     .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }

         if (OreSpawn.Gold_stats.rate > 0) {
            for (int ixxxxxxxxxxxx = 0; ixxxxxxxxxxxx < OreSpawn.Gold_stats.rate; ixxxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.Gold_stats.maxdepth && randPosY >= OreSpawn.Gold_stats.mindepth) {
                  new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(),  OreSpawn.Gold_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }

         if (OreSpawn.BlkGold_stats.rate > 0) {
            for (int ixxxxxxxxxxxxx = 0; ixxxxxxxxxxxxx < OreSpawn.BlkGold_stats.rate; ixxxxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkGold_stats.maxdepth && randPosY >= OreSpawn.BlkGold_stats.mindepth) {
                  new WorldGenMinable(Blocks.GOLD_BLOCK.getDefaultState(),  OreSpawn.BlkGold_stats.clumpsize).generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }

         if (OreSpawn.BlkRuby_stats.rate > 0) {
            for (int ixxxxxxxxxxxxxx = 0; ixxxxxxxxxxxxxx < OreSpawn.BlkRuby_stats.rate; ixxxxxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkRuby_stats.maxdepth && randPosY >= OreSpawn.BlkRuby_stats.mindepth) {
                  new WorldGenMinable(OreSpawn.MyBlockRubyBlock.getDefaultState(),  OreSpawn.BlkRuby_stats.clumpsize)
                     .generate(world, random, new BlockPos(randPosX, randPosY, randPosZ));
               }
            }
         }
      }
   }

   public void addStrawberries(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(20) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX, 64, chunkZ));
         if (world.provider.getDimension() == OreSpawn.DimensionID
            || isBiome(b, "Forest")
            || isBiome(b, "ForestHills")
            || isBiome(b, "Birch Forest Hills")
            || isBiome(b, "Birch Forest")) {
            for (int i = 0; i < 5; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyStrawberryPlant, 0, 2);
                     break;
                  }
               }
            }
         }
      }
   }

   public boolean addHauntedHouse(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(285) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Plains") || isBiome(b, "Taiga") || isBiome(b, "Swampland")) {
            for (int i = 0; i < 5; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.MyDungeon.makeHauntedHouse(world, posX, posY, posZ);
                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean addANest(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(230) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Forest")
            || isBiome(b, "ForestHills")
            || isBiome(b, "Jungle")
            || isBiome(b, "JungleHills")
            || isBiome(b, "Birch Forest")
            || isBiome(b, "Birch Forest Hills")) {
            for (int i = 0; i < 5; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     if (random.nextInt(2) == 0) {
                        OreSpawn.MyDungeon.makeSmallBeeHive(world, posX, posY, posZ);
                     } else {
                        OreSpawn.MyDungeon.makeMantisHive(world, posX, posY, posZ);
                     }

                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public void addCorn(World world, Random random, int chunkX, int chunkZ) {
      int is_all_air = 1;
      int nc = 6;
      if (random.nextInt(35) == 1) {
         if (OreSpawn.LessLag == 1) {
            nc = 5;
         }

         if (OreSpawn.LessLag == 2) {
            nc = 3;
         }

         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (world.provider.getDimension() == OreSpawn.DimensionID
            || world.provider.getDimension() == OreSpawn.DimensionID3
            || isBiome(b, "Plains")) {
            for (int j = 0; j < nc; j++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               boolean var15 = true;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     for (int i = 1; i < 10; i++) {
                        if (!world.isAirBlock(new BlockPos(posX, posY + i, posZ))) {
                           var15 = false;
                        }
                     }

                     if (!var15) {
                        break;
                     }

                     int corn_height = random.nextInt(5);
                     if (++corn_height == 1) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyCornPlant1, 0, 2);
                     }

                     if (corn_height == 2) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyCornPlant2, 0, 2);
                        OreSpawn.setBlockFast(world, posX, posY + 1, posZ, OreSpawn.MyCornPlant1, 0, 2);
                     }

                     if (corn_height <= 2) {
                        break;
                     }

                     OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyCornPlant2, 0, 2);

                     for (int var14 = 1; var14 < corn_height; var14++) {
                        OreSpawn.setBlockFast(world, posX, posY + var14, posZ, OreSpawn.MyCornPlant4, 0, 2);
                     }

                     OreSpawn.setBlockFast(world, posX, posY + corn_height, posZ, OreSpawn.MyCornPlant1, 0, 2);
                     break;
                  }
               }
            }
         }
      }
   }

   public void addTomatoes(World world, Random random, int chunkX, int chunkZ) {
      int is_all_air = 1;
      if (random.nextInt(70) == 1) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (world.provider.getDimension() == OreSpawn.DimensionID
            || world.provider.getDimension() == OreSpawn.DimensionID3
            || isBiome(b, "Plains")) {
            for (int j = 0; j < 5; j++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               boolean var14 = true;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     for (int i = 1; i < 10; i++) {
                        if (!world.isAirBlock(new BlockPos(posX, posY + i, posZ))) {
                           var14 = false;
                        }
                     }

                     if (!var14) {
                        break;
                     }

                     int corn_height = random.nextInt(3);
                     if (++corn_height == 1) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyTomatoPlant1, 0, 2);
                     }

                     if (corn_height == 2) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyTomatoPlant2, 0, 2);
                        OreSpawn.setBlockFast(world, posX, posY + 1, posZ, OreSpawn.MyTomatoPlant1, 0, 2);
                     }

                     if (corn_height <= 2) {
                        break;
                     }

                     OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyTomatoPlant3, 0, 2);

                     for (int var13 = 1; var13 < corn_height; var13++) {
                        OreSpawn.setBlockFast(world, posX, posY + var13, posZ, OreSpawn.MyTomatoPlant4, 0, 2);
                     }

                     OreSpawn.setBlockFast(world, posX, posY + corn_height, posZ, OreSpawn.MyTomatoPlant1, 0, 2);
                     break;
                  }
               }
            }
         }
      }
   }

   public void addButterfliesAndMoths(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(10 + OreSpawn.LessLag * 2) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (world.provider.getDimension() == OreSpawn.DimensionID
            || world.provider.getDimension() == OreSpawn.DimensionID6
            || isBiome(b, "Forest")
            || isBiome(b, "ForestHills")
            || isBiome(b, "River")
            || isBiome(b, "Jungle")
            || isBiome(b, "JungleHills")
            || isBiome(b, "Swampland")
            || isBiome(b, "Birch Forest")
            || isBiome(b, "Birch Forest Hills")
            || isBiome(b, "Roofed Forest")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     which = random.nextInt(3);
                     if (which == 0) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyButterflyPlant, 0, 2);
                     } else if (which == 1) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyMothPlant, 0, 2);
                     } else {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyFireflyPlant, 0, 2);
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   public void addPlayPool(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(350) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Ocean")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.WATER) {
                     OreSpawn.MyDungeon.makePlayPool(world, posX, posY, posZ);
                     recently_placed = 50;
                     return;
                  }
               }
            }
         }
      }
   }

   public void addFrogPond(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(350) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Plains")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.MyDungeon.makeFrogPond(world, posX, posY - 1, posZ);
                     recently_placed = 50;
                     return;
                  }
               }
            }
         }
      }
   }

   public void addGoldFishBowl(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(350) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Ocean")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.WATER) {
                     OreSpawn.MyDungeon.makeGoldFishBowl(world, posX, posY - 1, posZ);
                     recently_placed = 50;
                     return;
                  }
               }
            }
         }
      }
   }

   public boolean addLeafMonster(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(275) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Plains")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.MyDungeon.makeLeafMonsterDungeon(world, posX, posY, posZ);
                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean addRubberDuckyPond(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(275) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Plains")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.MyDungeon.makeRubberDuckyPond(world, posX, posY, posZ);
                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean addSpitBug(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(190) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Swampland")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.MyDungeon.makeSpitBugLair(world, posX, posY, posZ);
                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean addIgloo(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(220) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Ice Plains")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.SNOW) {
                     OreSpawn.MyDungeon.makeIgloo(world, posX, posY - 2, posZ);
                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean addBouncyCastle(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(230) != 0) {
         return false;
      } else {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Desert")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.SAND) {
                     OreSpawn.MyDungeon.makeBouncyCastle(world, posX, posY - 1, posZ);
                     recently_placed = 50;
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean addDamselInDistress(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(250) != 0) {
         return false;
      } else {
         for (int i = 0; i < 4; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);
            int which = 0;

            for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS
                  && this.quickSpaceCheck(world, posX, posY - 1, posZ)) {
                  OreSpawn.MyDungeon.makeDamselInDistress(world, posX, posY - 1, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addSpiderHangout(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(350) != 0) {
         return false;
      } else if (OreSpawn.SpiderDriverEnable == 0) {
         return false;
      } else {
         for (int i = 0; i < 4; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);
            int which = 0;

            for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS
                  && this.quickSpaceCheck(world, posX, posY - 1, posZ)) {
                  OreSpawn.MyDungeon.makeSpiderHangout(world, posX, posY - 1, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addRedAntHangout(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(250) != 0) {
         return false;
      } else {
         for (int i = 0; i < 4; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);
            int which = 0;

            for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS
                  && this.quickSpaceCheck(world, posX, posY - 1, posZ)) {
                  OreSpawn.MyDungeon.makeRedAntHangout(world, posX, posY - 1, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void addWaterDragonLair(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(350) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Ocean")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.WATER) {
                     OreSpawn.MyDungeon.makeWaterDragonLair(world, posX, posY - 1, posZ);
                     recently_placed = 50;
                     return;
                  }
               }
            }
         }
      }
   }

   public void addGirlfriendIsland(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(300) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Ocean")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.WATER) {
                     OreSpawn.MyDungeon.makeGirlfriendIsland(world, posX, posY - 1, posZ);
                     recently_placed = 50;
                     return;
                  }
               }
            }
         }
      }
   }

   public void addMonsterIsland(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(300) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (isBiome(b, "Ocean")) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);
               int which = 0;

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.WATER) {
                     OreSpawn.MyDungeon.makeMonsterIsland(world, posX, posY - 1, posZ);
                     recently_placed = 50;
                     return;
                  }
               }
            }
         }
      }
   }

   public void addMosquitos(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(25 + OreSpawn.LessLag * 2) == 0) {
         if (world.provider.getDimension() != OreSpawn.DimensionID && world.provider.getDimension() != OreSpawn.DimensionID3
            || random.nextInt(3) == 0) {
            net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX, 64, chunkZ));
            if (world.provider.getDimension() == OreSpawn.DimensionID
               || world.provider.getDimension() == OreSpawn.DimensionID2
               || world.provider.getDimension() == OreSpawn.DimensionID3
               || isBiome(b, "Jungle")
               || isBiome(b, "Swampland")) {
               for (int i = 0; i < 2; i++) {
                  int posX = chunkX + random.nextInt(16);
                  int posZ = chunkZ + random.nextInt(16);

                  for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                     if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyMosquitoPlant, 0, 2);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   public void addNetherMosquitos(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(25) == 0) {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = 90; posY > 20; posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyMosquitoPlant, 0, 2);
                  break;
               }
            }
         }
      }
   }

   public void addNetherAnts(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.RedAntEnable != 0) {
         if (random.nextInt(25) == 0) {
            for (int i = 0; i < 3; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = 90; posY > 20; posY--) {
                  if (world.isAirBlock(new BlockPos(posX, posY, posZ))
                     && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.NETHERRACK) {
                     OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.MyRedAntBlock, 0, 2);
                     break;
                  }
               }
            }
         }
      }
   }

   public void addAnts(World world, Random random, int chunkX, int chunkZ, int redfreq) {
      if (OreSpawn.RedAntEnable != 0 || OreSpawn.BlackAntEnable != 0 || OreSpawn.RainbowAntEnable != 0 || OreSpawn.UnstableAntEnable != 0) {
         if (redfreq < 2) {
            redfreq = 2;
         }

         if (random.nextInt(30 + OreSpawn.LessLag * 4) == 0) {
            for (int i = 0; i < 4; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     if (random.nextInt(redfreq) == 0) {
                        int which = random.nextInt(4);
                        if (which == 0 && OreSpawn.RedAntEnable != 0) {
                           OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.MyRedAntBlock, 0, 2);
                        }

                        if (which == 1 && OreSpawn.RainbowAntEnable != 0) {
                           OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.MyRainbowAntBlock, 0, 2);
                        }

                        if (which == 2 && OreSpawn.UnstableAntEnable != 0) {
                           OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.MyUnstableAntBlock, 0, 2);
                        }

                        if (which == 3 && OreSpawn.TermiteEnable != 0) {
                           OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.TermiteBlock, 0, 2);
                        }
                     } else if (OreSpawn.BlackAntEnable != 0) {
                        OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.MyAntBlock, 0, 2);
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   public void addEndAnts(World world, Random random, int chunkX, int chunkZ) {
   }

   public void addEndKnights(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(25) == 0) {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 10 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ))
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.END_STONE
                  && this.quickSpaceCheck(world, posX, posY, posZ)) {
                  OreSpawn.MyDungeon.makeEnderKnightDungeon(world, posX, posY, posZ);
                  return;
               }
            }
         }
      }
   }

   public void addEndReapers(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(25) == 0) {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 10 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ))
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.END_STONE
                  && this.quickSpaceCheck(world, posX, posY, posZ)) {
                  OreSpawn.MyDungeon.makeEnderReaperGraveyard(world, posX, posY, posZ);
                  return;
               }
            }
         }
      }
   }

   public void addHospital(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(25) == 0) {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 10 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ))
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.END_STONE
                  && this.quickSpaceCheck(world, posX, posY, posZ)) {
                  OreSpawn.MyDungeon.makeEnderDragonHospital(world, posX, posY, posZ);
                  return;
               }
            }
         }
      }
   }

   public void addEnderCastle(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(50) == 0) {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 10 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ))
                  && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.END_STONE
                  && this.quickBigSpaceCheck(world, posX, posY, posZ)) {
                  OreSpawn.MyDungeon.makeEnderCastle(world, posX, posY, posZ);
                  return;
               }
            }
         }
      }
   }

   public void addUnstableAnts(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.UnstableAntEnable != 0) {
         if (random.nextInt(30) == 0) {
            for (int i = 0; i < 3; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = 20; posY > 2 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.MyUnstableAntBlock, 0, 2);
                     break;
                  }
               }
            }
         }
      }
   }

   public void addCrystalTermites(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.TermiteEnable != 0) {
         if (random.nextInt(40) == 0) {
            for (int i = 0; i < 3; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
                     OreSpawn.setBlockFast(world, posX, posY - 1, posZ, OreSpawn.CrystalTermiteBlock, 0, 2);
                     break;
                  }
               }
            }
         }
      }
   }

   public boolean addRotatorStation(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.RotatorEnable == 0) {
         return false;
      } else if (random.nextInt(150) != 0) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
                  OreSpawn.MyDungeon.makeRotatorStation(world, posX, posY, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addRoundRotator(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.RotatorEnable == 0) {
         return false;
      } else if (random.nextInt(150) != 0) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
                  OreSpawn.MyDungeon.makeRoundRotator(world, posX, posY, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addUrchinSpawner(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.UrchinEnable == 0) {
         return false;
      } else if (random.nextInt(180) != 0) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
                  OreSpawn.MyDungeon.makeUrchinSpawner(world, posX, posY, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addCrystalHauntedHouse(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(230) != 0) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
                  OreSpawn.MyDungeon.makeCrystalHauntedHouse(world, posX, posY, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addCrystalBattleTower(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(280) != 0) {
         return false;
      } else {
         for (int i = 0; i < 3; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
                  OreSpawn.MyDungeon.makeCrystalBattleTower(world, posX, posY, posZ);
                  recently_placed = 50;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void addIrukandji(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.IrukandjiEnable != 0) {
         if (random.nextInt(80) == 0) {
            for (int i = 0; i < 3; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
                  if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.WATER) {
                     OreSpawn.setBlockFast(world, posX, posY, posZ, Blocks.MOB_SPAWNER, 0, 2);
                     TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(posX, posY, posZ));
                     if (tileentitymobspawner != null) {
                        com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Irukandji");
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   public void addCrystalChestsAndSpawners(World world, Random random, int chunkX, int chunkZ) {
      Block bid = Blocks.AIR;

      for (int i = 0; i < 3; i++) {
         int posX = 1 + chunkX + random.nextInt(14);
         int posZ = 1 + chunkZ + random.nextInt(14);
         int posY = 25;
         if (world.isAirBlock(new BlockPos(posX, posY, posZ))) {
            bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.AIR) {
               bid = world.getBlockState(new BlockPos(posX + 1, posY, posZ)).getBlock();
               if (bid == Blocks.AIR) {
                  this.addCrystalChest(world, posX, posY, posZ, 5);
               } else {
                  bid = world.getBlockState(new BlockPos(posX - 1, posY, posZ)).getBlock();
                  if (bid == Blocks.AIR) {
                     this.addCrystalChest(world, posX, posY, posZ, 4);
                  } else {
                     bid = world.getBlockState(new BlockPos(posX, posY, posZ + 1)).getBlock();
                     if (bid == Blocks.AIR) {
                        this.addCrystalChest(world, posX, posY, posZ, 2);
                     } else {
                        bid = world.getBlockState(new BlockPos(posX, posY, posZ - 1)).getBlock();
                        if (bid == Blocks.AIR) {
                           this.addCrystalChest(world, posX, posY, posZ, 3);
                        }
                     }
                  }
               }
            }
            break;
         }
      }
   }

   public void addCrystalChest(World world, int x, int y, int z, int dir) {
      int i = world.rand.nextInt(3);
      if (i == 0) {
         OreSpawn.setBlockFast(world, x, y, z, Blocks.CHEST, 0, 2);
         world.setBlockState(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).getBlock().getStateFromMeta(dir), 3);
         TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(x, y, z));
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, Trees.CrystalChestContentsList, chest, 1 + world.rand.nextInt(3));
         }
      } else {
         OreSpawn.setBlockFast(world, x, y, z, Blocks.MOB_SPAWNER, 0, 2);
         TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(x, y, z));
         if (tileentitymobspawner != null) {
            int t = world.rand.nextInt(2);
            if (t == 0) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
            }

            if (t == 1) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
            }
         }
      }
   }

   public void addIslands(World world, Random random, int chunkX, int chunkZ) {
      int posX = 2 + chunkX + random.nextInt(12);
      int posZ = 2 + chunkZ + random.nextInt(12);
      if (random.nextInt(10 + OreSpawn.LessLag * 2) == 1) {
         for (int posY = 20; posY > 2 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
            if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
               OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyIslandBlock, 0, 2);
               break;
            }
         }
      }
   }

   public boolean addAppleTrees(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
      int freq = Math.abs(chunkX / 16) + Math.abs(chunkZ / 16);
      int howmany = 2;
      int which = 0;
      boolean added = false;
      freq %= 15;
      howmany += random.nextInt(2 + (15 - freq) / 2);
      which = random.nextInt(10);
      if (random.nextInt(15 + freq) != 0) {
         return false;
      } else {
         if (OreSpawn.LessLag == 1) {
            howmany /= 2;
         }

         if (OreSpawn.LessLag == 2) {
            howmany /= 4;
            if (howmany < 1) {
               return false;
            }
         }

         for (int i = 0; i < howmany; i++) {
            int posX = 2 + chunkX + random.nextInt(12);
            int posZ = 2 + chunkZ + random.nextInt(12);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                  ItemAppleSeed a = (ItemAppleSeed)OreSpawn.MyAppleSeed;
                  if (which < 8) {
                     a.makeTree(world, posX, posY - 1, posZ, OreSpawn.MyAppleLeaves, chunk);
                  }

                  if (which == 8) {
                     a.makeTree(world, posX, posY - 1, posZ, OreSpawn.MyCherryLeaves, chunk);
                  }

                  if (which == 9) {
                     a.makeTree(world, posX, posY - 1, posZ, OreSpawn.MyPeachLeaves, chunk);
                  }

                  added = true;
                  break;
               }
            }
         }

         return added;
      }
   }

    public boolean addHugeTree(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
       return addHugeTree(world, random, chunkX, chunkZ, chunk, false, true, -1);
    }

    private boolean addHugeTree(World world, Random random, int chunkX, int chunkZ, Chunk chunk, boolean forceGeneration, boolean allowTreasureTrees, int logType) {
       int made_one = 0;
       if (!forceGeneration && random.nextInt(50) != 0) {
         return false;
      } else if (OreSpawn.LessLag == 1 && random.nextInt(2) != 0) {
         return false;
      } else if (OreSpawn.LessLag == 2 && random.nextInt(4) != 0) {
         return false;
      } else {
         for (int i = 0; i < 3 && made_one == 0; i++) {
            int posX = 4 + chunkX + random.nextInt(8);
            int posZ = 4 + chunkZ + random.nextInt(8);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && made_one == 0 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                  ItemMagicApple a = (ItemMagicApple)OreSpawn.MagicApple;
                   int tree_type = logType >= 0 ? logType : random.nextInt(4);
                  int tree_radius = 6 - random.nextInt(2);
                  boolean no_critters = false;
                  Block leaf_type = Blocks.LEAVES;
                  if (random.nextInt(100) > 25) {
                     no_critters = true;
    }

                   int rand_treetype = random.nextInt(100);
                   if (!allowTreasureTrees && rand_treetype == 0) rand_treetype = 1;
                  if (rand_treetype > 75) {
                     if (tree_type != 3 && random.nextInt(20) == 0) {
                        leaf_type = OreSpawn.MyAppleLeaves;
                     }

                     a.MakeBigSquareTree(
                        world, posX, posY - 1, posZ, Blocks.LOG, leaf_type, Blocks.MOSSY_COBBLESTONE, tree_type, tree_radius, no_critters, chunk
                     );
                  } else if (rand_treetype == 0) {
                     int var17 = 6;
                     no_critters = true;
                     if (random.nextInt(2) == 0) {
                        a.MakeBigSquareTree(
                           world, posX, posY - 1, posZ, Blocks.GOLD_BLOCK, Blocks.EMERALD_BLOCK, Blocks.DIAMOND_BLOCK, -1, var17, no_critters, chunk
                        );
                     } else {
                        a.MakeBigSquareTree(
                           world,
                           posX,
                           posY - 1,
                           posZ,
                           Blocks.OBSIDIAN,
                           OreSpawn.MyBlockRubyBlock,
                           OreSpawn.MyBlockAmethystBlock,
                           -1,
                           var17,
                           no_critters,
                           chunk
                        );
                     }
                  } else if (rand_treetype > 15) {
                     tree_radius = 6 - random.nextInt(3);
                     a.MakeBigCircularTree(
                        world, posX, posY - 1, posZ, Blocks.LOG, leaf_type, Blocks.MOSSY_COBBLESTONE, tree_type, tree_radius, no_critters, chunk
                     );
                  } else {
                     tree_radius = 6 - random.nextInt(3);
                     a.MakeBigRoundTree(world, posX, posY - 1, posZ, Blocks.LOG, leaf_type, Blocks.MOSSY_COBBLESTONE, tree_type, tree_radius, chunk);
                  }

                  made_one = 1;
                  break;
               }
            }
         }

         return made_one != 0;
      }
    }

    /** Generates large legacy trees in suitable Overworld biomes when enabled in the Forge config. */
    private void generateOverworldTrees(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
       if (!OreSpawn.OverworldTreesEnabled || random.nextInt(OreSpawn.OverworldTreeChance) != 0) return;

       int posX = chunkX + random.nextInt(16);
       int posZ = chunkZ + random.nextInt(16);
       BlockPos ground = world.getHeight(new BlockPos(posX, 0, posZ)).down();
       Biome biome = world.getBiome(ground);

       if (net.minecraftforge.common.BiomeDictionary.hasType(biome, net.minecraftforge.common.BiomeDictionary.Type.JUNGLE)) {
          addHugeTree(world, random, chunkX, chunkZ, chunk, true, false, 3);
       } else if (net.minecraftforge.common.BiomeDictionary.hasType(biome, net.minecraftforge.common.BiomeDictionary.Type.CONIFEROUS)) {
          addHugeTree(world, random, chunkX, chunkZ, chunk, true, false, 1);
       } else if (biome == net.minecraft.init.Biomes.BIRCH_FOREST || biome == net.minecraft.init.Biomes.BIRCH_FOREST_HILLS) {
          addHugeTree(world, random, chunkX, chunkZ, chunk, true, false, 2);
       } else if (net.minecraftforge.common.BiomeDictionary.hasType(biome, net.minecraftforge.common.BiomeDictionary.Type.FOREST)) {
          addHugeTree(world, random, chunkX, chunkZ, chunk, true, false, 0);
       }
    }

   public void addVeggies(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(15) == 0) {
         net.minecraft.world.biome.Biome b = world.getBiome(new BlockPos(chunkX + 8, 64, chunkZ + 8));
         if (world.provider.getDimension() == OreSpawn.DimensionID
            || world.provider.getDimension() == OreSpawn.DimensionID2
            || world.provider.getDimension() == OreSpawn.DimensionID6
            || isBiome(b, "River")
            || isBiome(b, "Swampland")) {
            for (int i = 0; i < 8; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     int what = random.nextInt(6);
                     if (what == 0) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, Blocks.CARROTS, 0, 2);
                     } else if (what == 1) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, Blocks.POTATOES, 0, 2);
                     } else if (what == 2) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyRadishPlant, 0, 2);
                     } else if (what == 3) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyLettucePlant1, 0, 2);
                     } else if (what == 4) {
                        if (random.nextInt(10) == 0) {
                           OreSpawn.setBlockFast(world, posX, posY, posZ, Blocks.MELON_STEM, 0, 2);
                        }
                     } else if (random.nextInt(50) == 1 && OreSpawn.enableduplicatortree != 0) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, OreSpawn.MyDT, 0, 2);
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   public void addRocks(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(5) == 0) {
         if (OreSpawn.RockEnable != 0) {
            int howmany = 3 + random.nextInt(10);

            for (int i = 0; i < howmany; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = surfaceStart(world, posX, posZ); posY > 40 && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR; posY--) {
                  Block bid = world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock();
                  if (bid == Blocks.GRASS || bid == Blocks.SAND || bid == OreSpawn.CrystalGrass) {
                     this.spawnCreature(world, "Rock", posX, posY, posZ);
                     break;
                  }
               }
            }
         }
      }
   }

   public void addD4Rocks(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(7) == 0) {
         if (OreSpawn.RockEnable != 0) {
            int howmany = 3 + random.nextInt(10);

            for (int i = 0; i < howmany; i++) {
               int posX = chunkX + random.nextInt(16);
               int posZ = chunkZ + random.nextInt(16);

               for (int posY = 20; posY > 5 && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.AIR; posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     this.spawnCreature(world, "Rock", posX, posY, posZ);
                     break;
                  }
               }
            }
         }
      }
   }

   public boolean addFairyTree(World world, Random random, int chunkX, int chunkZ) {
      int posX = chunkX + 8;
      int posZ = chunkZ + 8;
      if (random.nextInt(5) != 0) {
         return false;
      } else {
         for (int posY = surfaceStart(world, posX, posZ); posY > 40 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
            if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == OreSpawn.CrystalGrass) {
               for (int i = -8; i <= 8; i++) {
                  for (int j = -8; j <= 8; j++) {
                     Block bid = world.getBlockState(new BlockPos(posX + i, posY, posZ + j)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               for (int var11 = -2; var11 <= 2; var11++) {
                  for (int jx = -2; jx <= 2; jx++) {
                     Block bid = world.getBlockState(new BlockPos(posX + var11, posY - 1, posZ + jx)).getBlock();
                     if (bid != OreSpawn.CrystalGrass) {
                        return false;
                     }
                  }
               }

               if (random.nextInt(5) != 1) {
                  OreSpawn.OreSpawnTrees.FairyTree(world, posX, posY - 1, posZ);
               } else {
                  OreSpawn.OreSpawnTrees.FairyCastleTree(world, posX, posY, posZ);
               }

               recently_placed = 50;
               break;
            }
         }

         return true;
      }
   }

   public boolean addRubyDungeon(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(15) != 0) {
         return false;
      } else {
         for (int i = 0; i < 8; i++) {
            int posX = chunkX + random.nextInt(8);
            int posZ = chunkZ + random.nextInt(8);

            for (int posY = 50; posY > 5; posY--) {
               if (world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.LAVA) {
                  OreSpawn.RubyDungeon.makeDungeon(world, posX, posY, posZ);
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean addGenericDungeon(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(16) != 0) {
         return false;
      } else if (OreSpawn.LessLag == 1 && random.nextInt(2) != 0) {
         return false;
      } else if (OreSpawn.LessLag == 2 && random.nextInt(4) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(4);
         int posZ = chunkZ + random.nextInt(4);
         int posY = 5 + random.nextInt(40);
         OreSpawn.MyDungeon.makeDungeon(world, posX, posY, posZ);
         return true;
      }
   }

   public boolean addBeeHive(World world, Random random, int chunkX, int chunkZ) {
      int lowestY = 128;
      int lowestX = chunkX;
      int lowestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 30 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.GRASS) {
                  if (posY < lowestY) {
                     lowestY = posY;
                     lowestX = posX;
                     lowestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && lowestY > 40) {
         OreSpawn.MyDungeon.makeBeeHive(world, lowestX, lowestY + 3, lowestZ);
         recently_placed = 50;
         return true;
      } else {
         return false;
      }
   }

   public boolean addAlienWTF(World world, Random random, int chunkX, int chunkZ) {
      int lowestY = 128;
      int lowestX = chunkX;
      int lowestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 30 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.GRASS) {
                  if (posY < lowestY) {
                     lowestY = posY;
                     lowestX = posX;
                     lowestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && lowestY > 40) {
         OreSpawn.MyDungeon.makeAlienWTFDungeon(world, lowestX, lowestY, lowestZ);
         recently_placed = 50;
         return true;
      } else {
         return false;
      }
   }

   public boolean addEnderKnight(World world, Random random, int chunkX, int chunkZ) {
      int lowestY = 128;
      int lowestX = chunkX;
      int lowestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 30 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.GRASS) {
                  if (posY < lowestY) {
                     lowestY = posY;
                     lowestX = posX;
                     lowestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && lowestY > 40) {
         OreSpawn.MyDungeon.makeEnderKnightDungeon(world, lowestX, lowestY, lowestZ);
         recently_placed = 50;
         return true;
      } else {
         return false;
      }
   }

   public boolean addLeonNest(World world, Random random, int chunkX, int chunkZ) {
      int highestY = 30;
      int highestX = chunkX;
      int highestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 80 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.GRASS) {
                  if (posY > highestY) {
                     highestY = posY + 1;
                     highestX = posX;
                     highestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && highestY > 80) {
         OreSpawn.MyDungeon.makeLeonNest(world, highestX, highestY, highestZ);
         recently_placed = 50;
         return true;
      } else {
         return false;
      }
   }

   public boolean addShadowDungeon(World world, Random random, int chunkX, int chunkZ) {
      int lowestY = 128;
      int lowestX = chunkX;
      int lowestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 30 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() == Blocks.GRASS) {
                  if (posY < lowestY) {
                     lowestY = posY;
                     lowestX = posX;
                     lowestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && lowestY > 40) {
         OreSpawn.MyDungeon.makeShadowDungeon(world, lowestX, lowestY, lowestZ);
         recently_placed = 50;
         return true;
      } else {
         return false;
      }
   }

   public boolean addD4RubyDungeon(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               OreSpawn.RubyDungeon.makeDungeon(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4CephadromeAltar(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               OreSpawn.MyDungeon.makeCephadromeAltar(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4Castle(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -20; x < 33; x++) {
                  for (int z = -4; z < 33; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               if (random.nextInt(2) == 1) {
                  OreSpawn.MyDungeon.makeEnormousCastle(world, posX, posY, posZ);
               } else {
                  OreSpawn.MyDungeon.makeEnormousCastleQ(world, posX, posY, posZ);
               }

               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4Greenhouse(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -2; x < 25; x++) {
                  for (int z = -4; z < 25; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeGreenhouseDungeon(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4NightmareRookery(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -5; x < 25; x++) {
                  for (int z = -4; z < 5; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeNightmareRookery(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4StinkyHouse(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -8; x < 20; x++) {
                  for (int z = -8; z < 20; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeStinkyHouse(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4WhiteHouse(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -20; x < 30; x++) {
                  for (int z = -20; z < 300; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeWhiteHouse(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4EnderCastle(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -5; x < 25; x++) {
                  for (int z = -5; z < 25; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeEnderCastle(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4IncaPyramid(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -10; x < 50; x++) {
                  for (int z = -10; z < 40; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 18, posZ + z)).getBlock();
                     if (bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeIncaPyramid(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4RobotLab(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               for (int x = -5; x < 60; x++) {
                  for (int z = -5; z < 70; z++) {
                     bid = world.getBlockState(new BlockPos(posX + x, posY + 4, posZ + z)).getBlock();
                     if (bid != Blocks.AIR
                        && bid != Blocks.LOG
                        && bid != OreSpawn.MyAppleLeaves
                        && bid != OreSpawn.MyScaryLeaves
                        && bid != Blocks.AIR) {
                        return false;
                     }
                  }
               }

               OreSpawn.MyDungeon.makeRobotLab(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4Mini(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               OreSpawn.MyDungeon.makeMiniDungeon(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addPumpkin(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(2) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               OreSpawn.MyDungeon.makePumpkin(world, posX, posY + 1, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public boolean addD4CloudShark(World world, Random random, int chunkX, int chunkZ) {
      int posX = 4 + chunkX + random.nextInt(8);
      int posZ = 4 + chunkZ + random.nextInt(8);
      OreSpawn.MyDungeon.makeCloudSharkDungeon(world, posX, 150 + world.rand.nextInt(10), posZ);
      return true;
   }

   public boolean addD4Rainbow(World world, Random random, int chunkX, int chunkZ) {
      int posX = 4 + chunkX + random.nextInt(8);
      int posZ = 4 + chunkZ + random.nextInt(8);
      OreSpawn.MyDungeon.makeRainbow(world, posX, 70 + world.rand.nextInt(20), posZ);
      recently_placed = 50;
      return true;
   }

   public boolean addD4GenericDungeon(World world, Random random, int chunkX, int chunkZ) {
      if (OreSpawn.LessLag != 0 && random.nextInt(4) != 0) {
         return false;
      } else {
         int posX = chunkX + random.nextInt(8);
         int posZ = chunkZ + random.nextInt(8);

         for (int posY = 20; posY > 4; posY--) {
            Block bid = world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
            if (bid == Blocks.GRASS) {
               OreSpawn.MyDungeon.makeDungeon(world, posX, posY, posZ);
               recently_placed = 50;
               return true;
            }
         }

         return false;
      }
   }

   public void addLavaAndWater(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(5) == 0) {
         for (int i = 0; i < 6; i++) {
            int posX = chunkX + random.nextInt(16);
            int posZ = chunkZ + random.nextInt(16);

            for (int posY = surfaceStart(world, posX, posZ); posY > 75 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                  Block bid = world.getBlockState(new BlockPos(posX, posY - 2, posZ)).getBlock();
                  if (bid != Blocks.DIRT && bid != Blocks.STONE) {
                     break;
                  }

                  int air = 0;
                  int non_air = 0;
                  bid = world.getBlockState(new BlockPos(posX + 1, posY - 1, posZ)).getBlock();
                  if (bid == Blocks.AIR) {
                     air++;
                  }

                  if (bid == Blocks.DIRT || bid == Blocks.STONE || bid == Blocks.GRASS) {
                     non_air++;
                  }

                  bid = world.getBlockState(new BlockPos(posX - 1, posY - 1, posZ)).getBlock();
                  if (bid == Blocks.AIR) {
                     air++;
                  }

                  if (bid == Blocks.DIRT || bid == Blocks.STONE || bid == Blocks.GRASS) {
                     non_air++;
                  }

                  bid = world.getBlockState(new BlockPos(posX, posY - 1, posZ + 1)).getBlock();
                  if (bid == Blocks.AIR) {
                     air++;
                  }

                  if (bid == Blocks.DIRT || bid == Blocks.STONE || bid == Blocks.GRASS) {
                     non_air++;
                  }

                  bid = world.getBlockState(new BlockPos(posX, posY - 1, posZ - 1)).getBlock();
                  if (bid == Blocks.AIR) {
                     air++;
                  }

                  if (bid == Blocks.DIRT || bid == Blocks.STONE || bid == Blocks.GRASS) {
                     non_air++;
                  }

                  if (air != 0 && non_air != 0) {
                     int what = random.nextInt(2);
                     if (what == 0) {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, Blocks.FLOWING_WATER, 0, 3);
                        OreSpawn.setBlockFast(world, posX, posY - 1, posZ, Blocks.WATER, 0, 3);
                        OreSpawn.setBlockFast(world, posX, posY - 2, posZ, Blocks.WATER, 0, 3);
                     } else {
                        OreSpawn.setBlockFast(world, posX, posY, posZ, Blocks.FLOWING_LAVA, 0, 3);
                        OreSpawn.setBlockFast(world, posX, posY - 1, posZ, Blocks.LAVA, 0, 3);
                        OreSpawn.setBlockFast(world, posX, posY - 2, posZ, Blocks.LAVA, 0, 3);
                     }

                     return;
                  }
                  break;
               }
            }
         }
      }
   }

   public boolean addOtherTrees(World world, Random random, int chunkX, int chunkZ) {
      int nc = 5;
      int count = 0;
      if (random.nextInt(30) != 0) {
         return false;
      } else {
         if (OreSpawn.LessLag == 1) {
            if (random.nextInt(2) != 0) {
               return false;
            }

            nc = 4;
         }

         if (OreSpawn.LessLag == 2) {
            if (random.nextInt(4) != 0) {
               return false;
            }

            nc = 3;
         }

         if (world.provider.getDimension() == OreSpawn.DimensionID) {
            int dir = 0;
            int what = random.nextInt(2);

            for (int i = 0; i < nc; i++) {
               int posX = 3 + chunkX + random.nextInt(10);
               int posZ = 3 + chunkZ + random.nextInt(10);

               for (int posY = surfaceStart(world, posX, posZ); posY > 50 && world.isAirBlock(new BlockPos(posX, posY, posZ)); posY--) {
                  if (world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                     count++;
                     if (what == 0) {
                        OreSpawn.OreSpawnTrees.WindTree(world, posX, posY - 1, posZ, dir);
                        if (count >= 4) {
                           return true;
                        }
                     } else {
                        OreSpawn.OreSpawnTrees.SkyTree(world, posX, posY - 1, posZ);
                        if (count >= 3) {
                           return true;
                        }
                     }
                     break;
                  }
               }
            }
         }

         return count > 0;
      }
   }

   public boolean addKingAltar(World world, Random random, int chunkX, int chunkZ) {
      if (random.nextInt(2000) != 1) {
         return false;
      } else {
         for (int i = 0; i < 8; i++) {
            int posX = 3 + chunkX + random.nextInt(10);
            int posZ = 3 + chunkZ + random.nextInt(10);

            for (int posY = surfaceStart(world, posX, posZ); posY > 50 && isSurfaceScanOpen(world, posX, posY, posZ); posY--) {
               if (world.isAirBlock(new BlockPos(posX, posY, posZ)) && world.getBlockState(new BlockPos(posX, posY - 1, posZ)).getBlock() == Blocks.GRASS) {
                  if (!this.quickReallyBigSpaceCheck(world, posX, posY - 1, posZ)) {
                     return false;
                  }

                  if (random.nextInt(2) == 0) {
                     OreSpawn.MyDungeon.makeKingAltar(world, posX, posY - 1, posZ);
                  } else {
                     OreSpawn.MyDungeon.makeQueenAltar(world, posX, posY - 1, posZ);
                  }

                  recently_placed = 100;
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void addBasiliskMaze(World world, Random random, int chunkX, int chunkZ) {
      int lowestY = 128;
      int lowestX = chunkX;
      int lowestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 30 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() != Blocks.AIR) {
                  if (posY < lowestY) {
                     lowestY = posY;
                     lowestX = posX;
                     lowestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && lowestY > 40) {
         OreSpawn.BMaze.buildBasiliskMaze(world, lowestX, lowestY - 2, lowestZ);
         recently_placed = 50;
      }
   }

   public void addKyuubiDungeon(World world, Random random, int chunkX, int chunkZ) {
      int lowestY = 128;
      int lowestX = chunkX;
      int lowestZ = chunkZ;
      int found = 0;

      for (int i = 0; i < 16; i += 3) {
         for (int j = 0; j < 16; j += 3) {
            int posX = chunkX + i;
            int posZ = chunkZ + j;

            for (int posY = surfaceStart(world, posX, posZ); posY > 30 && isSurfaceScanOpen(world, posX, posY + 1, posZ); posY--) {
               if (world.getBlockState(new BlockPos(posX, posY + 1, posZ)).getBlock() == Blocks.AIR && world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() != Blocks.AIR) {
                  if (posY < lowestY) {
                     lowestY = posY;
                     lowestX = posX;
                     lowestZ = posZ;
                     found = 1;
                  }
                  break;
               }
            }
         }
      }

      if (found != 0 && lowestY > 40) {
         OreSpawn.MyDungeon.makeKyuubiDungeon(world, lowestX, lowestY - 2, lowestZ);
         recently_placed = 50;
      }
   }

   /**
    * Vanilla biomes are matched by name exactly as in the original mod. Biomes added by other
    * mods or world generators never match those names, so they are matched by the Forge
    * biome dictionary type that the vanilla biome stands for.
    */
   private static boolean isBiome(Biome biome, String vanillaName) {
      ResourceLocation id = biome.getRegistryName();
      if (id == null || "minecraft".equals(id.getNamespace())) {
         return com.electrifiedded.archaiclib.legacy.world.LegacyBiomes.getName(biome).equals(vanillaName);
      }
      switch (vanillaName) {
         case "Plains":
            return hasType(biome, Type.PLAINS) && !hasType(biome, Type.SNOWY) && !hasType(biome, Type.SAVANNA);
         case "Ocean":
            return hasType(biome, Type.OCEAN) && !hasType(biome, Type.SNOWY);
         case "River":
            return hasType(biome, Type.RIVER) && !hasType(biome, Type.SNOWY);
         case "Swampland":
            return hasType(biome, Type.SWAMP);
         case "Ice Plains":
            return hasType(biome, Type.SNOWY) && hasType(biome, Type.PLAINS);
         case "Desert":
            return hasType(biome, Type.SANDY) && hasType(biome, Type.HOT) && !hasType(biome, Type.MESA) && !hasType(biome, Type.BEACH);
         case "Extreme Hills":
            return hasType(biome, Type.MOUNTAIN) && !hasType(biome, Type.SNOWY);
         case "Taiga":
            return hasType(biome, Type.CONIFEROUS) && !hasType(biome, Type.SNOWY);
         case "Jungle":
         case "JungleHills":
            return hasType(biome, Type.JUNGLE);
         case "Roofed Forest":
            return hasType(biome, Type.FOREST) && hasType(biome, Type.SPOOKY);
         case "Forest":
         case "ForestHills":
         case "Birch Forest":
         case "Birch Forest Hills":
            return hasType(biome, Type.FOREST) && !hasType(biome, Type.CONIFEROUS) && !hasType(biome, Type.JUNGLE)
               && !hasType(biome, Type.SNOWY) && !hasType(biome, Type.SPOOKY);
         default:
            return false;
      }
   }

   private static boolean hasType(Biome biome, Type type) {
      return BiomeDictionary.hasType(biome, type);
   }

   private boolean quickSpaceCheck(World world, int posX, int posY, int posZ) {
      return isLayerEmpty(world, posX - 2, posX + 9, posY + 4, posZ - 2, posZ + 9, false);
   }

   private boolean quickBigSpaceCheck(World world, int posX, int posY, int posZ) {
      return isLayerEmpty(world, posX - 5, posX + 24, posY + 8, posZ - 5, posZ + 24, false);
   }

   private boolean quickReallyBigSpaceCheck(World world, int posX, int posY, int posZ) {
      return isLayerEmpty(world, posX - 5, posX + 54, posY + 8, posZ - 5, posZ + 54, false);
   }

   private boolean D4BigSpaceCheck(World world, int posX, int posY, int posZ) {
      return isLayerEmpty(world, posX - 25, posX + 39, posY + 4, posZ - 25, posZ + 29, true);
   }

   /**
    * Checks one horizontal layer chunk by chunk instead of one world lookup per block.
    * Unloaded chunks count as empty so the check never forces neighbor generation;
    * writes landing there are deferred by {@link GenerationBlockBuffer}.
    */
   private static boolean isLayerEmpty(World world, int minX, int maxX, int y, int minZ, int maxZ, boolean allowTrees) {
      if (y < WorldHeightCompat.minBuildY(world) || y >= world.getHeight()) {
         return true;
      }
      int sectionIndex = WorldHeightCompat.sectionIndex(y);
      for (int cx = minX >> 4; cx <= maxX >> 4; cx++) {
         for (int cz = minZ >> 4; cz <= maxZ >> 4; cz++) {
            Chunk chunk = world.getChunkProvider().getLoadedChunk(cx, cz);
            if (chunk == null) {
               continue;
            }
            net.minecraft.world.chunk.storage.ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
            if (sectionIndex < 0 || sectionIndex >= storage.length) {
               continue;
            }
            net.minecraft.world.chunk.storage.ExtendedBlockStorage section = storage[sectionIndex];
            int x0 = Math.max(minX, cx << 4);
            int x1 = Math.min(maxX, (cx << 4) + 15);
            int z0 = Math.max(minZ, cz << 4);
            int z1 = Math.min(maxZ, (cz << 4) + 15);
            for (int x = x0; x <= x1; x++) {
               for (int z = z0; z <= z1; z++) {
                  net.minecraft.block.state.IBlockState state = GenerationBlockBuffer.getBufferedState(world, x, y, z);
                  if (state == null) {
                     state = section == null ? Blocks.AIR.getDefaultState() : section.get(x & 15, y & 15, z & 15);
                  }
                  Block bid = state.getBlock();
                  if (bid != Blocks.AIR
                     && !(allowTrees && (bid == Blocks.LOG || bid == OreSpawn.MyAppleLeaves || bid == OreSpawn.MyScaryLeaves))) {
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   /** First Y for a downward surface scan: just above the column's highest block, however tall the terrain is. */
   private static int surfaceStart(World world, int x, int z) {
      Chunk chunk = world.getChunk(x >> 4, z >> 4);
      if (CUBIC_CHUNKS) {
         return world.getHeight(new BlockPos(x, 0, z)).getY() + 1;
      }
      int lx = x & 15;
      int lz = z & 15;
      net.minecraft.world.chunk.storage.ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
      for (int s = storage.length - 1; s >= 0; s--) {
         net.minecraft.world.chunk.storage.ExtendedBlockStorage section = storage[s];
         if (section == null || section.isEmpty()) {
            continue;
         }
         for (int ly = 15; ly >= 0; ly--) {
            if (section.get(lx, ly, lz).getMaterial() != net.minecraft.block.material.Material.AIR) {
               return WorldHeightCompat.sectionBase(s) + ly + 1;
            }
         }
      }

      return 1;
   }

   /** Surface scans may pass through air and foliage, but never into terrain, caves or water below it. */
   private static boolean isSurfaceScanOpen(World world, int x, int y, int z) {
      net.minecraft.block.material.Material material = world.getBlockState(new BlockPos(x, y, z)).getMaterial();
      return material == net.minecraft.block.material.Material.AIR
         || material == net.minecraft.block.material.Material.LEAVES
         || material == net.minecraft.block.material.Material.PLANTS
         || material == net.minecraft.block.material.Material.VINE
         || material == net.minecraft.block.material.Material.SNOW;
   }

   private Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(par1, par0World);
      if (var8 != null) {
         if (par2 > 0.0) {
            par2 += 0.5;
         }

         if (par2 < 0.0) {
            par2 -= 0.5;
         }

         if (par6 > 0.0) {
            par6 += 0.5;
         }

         if (par6 < 0.0) {
            par6 -= 0.5;
         }

         var8.setLocationAndAngles(par2, par4 + 0.01, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }
}

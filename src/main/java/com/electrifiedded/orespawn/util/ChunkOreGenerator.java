package com.electrifiedded.orespawn.util;

import com.electrifiedded.orespawn.OreSpawn;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/** Spawn-block and ore veins that OreSpawn dimensions carve into a freshly generated chunk. */
public class ChunkOreGenerator {
   public void generateOresInChunk(World world, Random random, int chunkX, int chunkZ, Chunk chunk) {
      if (OreSpawn.SpawnOres_stats.rate > 0) {
         int patchy = OreSpawn.SpawnOres_stats.rate + random.nextInt(30);
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

                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, b, OreSpawn.SpawnOres_stats.clumpsize);
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

                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, b, OreSpawn.SpawnOres_stats.clumpsize);
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
               this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.MyOreUraniumBlock, OreSpawn.Uranium_stats.clumpsize);
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
               this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.MyOreTitaniumBlock, OreSpawn.Titanium_stats.clumpsize);
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
               this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.MyOreAmethystBlock, OreSpawn.Amethyst_stats.clumpsize);
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
               this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.MyOreSaltBlock, OreSpawn.Salt_stats.clumpsize);
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
            this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.RedAntTroll, 4);
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
            this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.TermiteTroll, 4);
         }
      }

      if (OreSpawn.LessOre == 0) {
         if (OreSpawn.Diamond_stats.rate > 0) {
            for (int ixxxxxxx = 0; ixxxxxxx < OreSpawn.Diamond_stats.rate; ixxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.Diamond_stats.maxdepth && randPosY >= OreSpawn.Diamond_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, Blocks.DIAMOND_ORE, OreSpawn.Diamond_stats.clumpsize);
               }
            }
         }

         if (OreSpawn.BlkDiamond_stats.rate > 0) {
            for (int ixxxxxxxx = 0; ixxxxxxxx < OreSpawn.BlkDiamond_stats.rate; ixxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkDiamond_stats.maxdepth && randPosY >= OreSpawn.BlkDiamond_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, Blocks.DIAMOND_BLOCK, OreSpawn.BlkDiamond_stats.clumpsize);
               }
            }
         }

         if (OreSpawn.Emerald_stats.rate > 0) {
            for (int ixxxxxxxxx = 0; ixxxxxxxxx < OreSpawn.Emerald_stats.rate; ixxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.Emerald_stats.maxdepth && randPosY >= OreSpawn.Emerald_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, Blocks.EMERALD_ORE, OreSpawn.Emerald_stats.clumpsize);
               }
            }
         }

         if (OreSpawn.BlkEmerald_stats.rate > 0) {
            for (int ixxxxxxxxxx = 0; ixxxxxxxxxx < OreSpawn.BlkEmerald_stats.rate; ixxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkEmerald_stats.maxdepth && randPosY >= OreSpawn.BlkEmerald_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, Blocks.EMERALD_BLOCK, OreSpawn.BlkEmerald_stats.clumpsize);
               }
            }
         }

         if (OreSpawn.Gold_stats.rate > 0) {
            for (int ixxxxxxxxxxx = 0; ixxxxxxxxxxx < OreSpawn.Gold_stats.rate; ixxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.Gold_stats.maxdepth && randPosY >= OreSpawn.Gold_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, Blocks.GOLD_ORE, OreSpawn.Gold_stats.clumpsize);
               }
            }
         }

         if (OreSpawn.BlkGold_stats.rate > 0) {
            for (int ixxxxxxxxxxxx = 0; ixxxxxxxxxxxx < OreSpawn.BlkGold_stats.rate; ixxxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkGold_stats.maxdepth && randPosY >= OreSpawn.BlkGold_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, Blocks.GOLD_BLOCK, OreSpawn.BlkGold_stats.clumpsize);
               }
            }
         }

         if (OreSpawn.BlkRuby_stats.rate > 0) {
            for (int ixxxxxxxxxxxxx = 0; ixxxxxxxxxxxxx < OreSpawn.BlkRuby_stats.rate; ixxxxxxxxxxxxx++) {
               int randPosX = 3 + chunkX + random.nextInt(10);
               int randPosY = random.nextInt(128);
               int randPosZ = 3 + chunkZ + random.nextInt(10);
               if (randPosY <= OreSpawn.BlkRuby_stats.maxdepth && randPosY >= OreSpawn.BlkRuby_stats.mindepth) {
                  this.generateBlockOre(world, random, randPosX, randPosY, randPosZ, chunk, OreSpawn.MyBlockRubyBlock, OreSpawn.BlkRuby_stats.clumpsize);
               }
            }
         }
      }
   }

   public boolean generateBlockOre(World par1World, Random par2Random, int par3, int par4, int par5, Chunk chunk, Block newbid, int numberOfBlocks) {
      float f = par2Random.nextFloat() * (float) Math.PI;
      double d0 = par3 + 8 + MathHelper.sin(f) * numberOfBlocks / 8.0F;
      double d1 = par3 + 8 - MathHelper.sin(f) * numberOfBlocks / 8.0F;
      double d2 = par5 + 8 + MathHelper.cos(f) * numberOfBlocks / 8.0F;
      double d3 = par5 + 8 - MathHelper.cos(f) * numberOfBlocks / 8.0F;
      double d4 = par4 + par2Random.nextInt(3) - 2;
      double d5 = par4 + par2Random.nextInt(3) - 2;

      for (int l = 0; l <= numberOfBlocks; l++) {
         double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
         double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
         double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
         double d9 = par2Random.nextDouble() * numberOfBlocks / 16.0;
         double d10 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0;
         double d11 = (MathHelper.sin(l * (float) Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0;
         int i1 = MathHelper.floor(d6 - d10 / 2.0);
         int j1 = MathHelper.floor(d7 - d11 / 2.0);
         int k1 = MathHelper.floor(d8 - d10 / 2.0);
         int l1 = MathHelper.floor(d6 + d10 / 2.0);
         int i2 = MathHelper.floor(d7 + d11 / 2.0);
         int j2 = MathHelper.floor(d8 + d10 / 2.0);

         for (int k2 = i1; k2 <= l1; k2++) {
            double d12 = (k2 + 0.5 - d6) / (d10 / 2.0);
            if (d12 * d12 < 1.0) {
               for (int l2 = j1; l2 <= i2; l2++) {
                  double d13 = (l2 + 0.5 - d7) / (d11 / 2.0);
                  if (d12 * d12 + d13 * d13 < 1.0) {
                     for (int i3 = k1; i3 <= j2; i3++) {
                        double d14 = (i3 + 0.5 - d8) / (d10 / 2.0);
                        Block bid = OreSpawn.getBlockIDInChunk(chunk, k2, l2, i3);
                        if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && bid == Blocks.STONE) {
                           OreSpawn.setBlockIDWithMetadataInChunk(chunk, k2, l2, i3, newbid, 0);
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}

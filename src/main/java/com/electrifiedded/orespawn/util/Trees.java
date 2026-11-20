package com.electrifiedded.orespawn.util;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.world.World;

public class Trees {
   public static final SimpleChest[] CrystalChestContentsList = new SimpleChest[]{
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalTermiteBlock), 0, 1, 5, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalFlowerRedBlock), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalFlowerBlueBlock), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalFlowerGreenBlock), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalFlowerYellowBlock), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalPlanksBlock), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalWorkbenchBlock), 0, 1, 1, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalFurnaceBlock), 0, 1, 1, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.MyTigersEyeBlock), 0, 1, 10, 5),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalStone), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalRat), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalFairy), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalCoal), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalGrass), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalCrystal), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalTorch), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.MyCrystalLeaves), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.MyCrystalLeaves2), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.MyCrystalLeaves3), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.MyCrystalTreeLog), 0, 1, 10, 10),
      new SimpleChest(Item.getItemFromBlock(OreSpawn.TigersEye), 0, 1, 10, 5),
      new SimpleChest(OreSpawn.MyCrystalWoodSword, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalWoodAxe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalWoodShovel, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalWoodPickaxe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalWoodHoe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalPinkSword, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalPinkAxe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalPinkShovel, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalPinkPickaxe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalPinkHoe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyTigersEyeSword, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.MyTigersEyeAxe, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.MyTigersEyeShovel, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.MyTigersEyePickaxe, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.MyTigersEyeHoe, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.MyCrystalStoneSword, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalStoneAxe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalStoneShovel, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalStonePickaxe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyCrystalStoneHoe, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.MyTigersEyeIngot, 0, 1, 5, 5),
      new SimpleChest(OreSpawn.MyCrystalPinkIngot, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.MyCrystalApple, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.MyPeacockFeather, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.MyPeacock, 0, 1, 10, 20),
      new SimpleChest(OreSpawn.MyRawPeacock, 0, 1, 10, 20),
      new SimpleChest(OreSpawn.MyRice, 0, 1, 10, 20),
      new SimpleChest(OreSpawn.MyQuinoa, 0, 1, 10, 20),
      new SimpleChest(OreSpawn.CrystalPinkHelmet, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.CrystalPinkBody, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.CrystalPinkLegs, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.CrystalPinkBoots, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.TigersEyeHelmet, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.TigersEyeBody, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.TigersEyeLegs, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.TigersEyeBoots, 0, 1, 1, 5),
      new SimpleChest(OreSpawn.PeacockFeatherHelmet, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.PeacockFeatherBody, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.PeacockFeatherLegs, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.PeacockFeatherBoots, 0, 1, 1, 10),
      new SimpleChest(OreSpawn.RotatorEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.VortexEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.PeacockEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.DungeonBeastEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.FairyEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.RatEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.FlounderEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.WhaleEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.IrukandjiEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.SkateEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.UrchinEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.GhostEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.GhostSkellyEgg, 0, 1, 5, 10),
      new SimpleChest(OreSpawn.MySkateBow, 0, 1, 1, 2),
      new SimpleChest(OreSpawn.MyIrukandjiArrow, 0, 5, 10, 2),
      new SimpleChest(OreSpawn.MyIrukandji, 0, 2, 8, 5),
      new SimpleChest(OreSpawn.MyUltimateBow, 0, 1, 1, 2),
      new SimpleChest(OreSpawn.MyUltimateSword, 0, 1, 1, 2),
      new SimpleChest(Items.IRON_INGOT, 0, 1, 4, 10),
      new SimpleChest(Item.getItemFromBlock(Blocks.LOG), 0, 1, 4, 10),
      new SimpleChest(Items.GOLDEN_APPLE, 0, 1, 5, 2)
   };

   private void WindTreeBranch(World world, int x, int y, int z, int length, int dirx, int dirz) {
      for (int i = 1; i <= length; i++) {
         OreSpawn.setBlockFast(world, x + i * dirx, y, z + i * dirz, Blocks.LOG, 0, 2);
          if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx, y + 1, z + i * dirz)).getBlock()) {
            OreSpawn.setBlockFast(world, x + i * dirx, y + 1, z + i * dirz, Blocks.LEAVES, 0, 2);
         }

         if (i < length / 3 && Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx, y + 2, z + i * dirz)).getBlock()) {
            OreSpawn.setBlockFast(world, x + i * dirx, y + 2, z + i * dirz, Blocks.LEAVES, 0, 2);
         }

         if (i > length / 3) {
            if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx + dirz, y, z + i * dirz + dirx)).getBlock()) {
               OreSpawn.setBlockFast(world, x + i * dirx + dirz, y, z + i * dirz + dirx, Blocks.LEAVES, 0, 2);
            }

            if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx - dirz, y, z + i * dirz - dirx)).getBlock()) {
               OreSpawn.setBlockFast(world, x + i * dirx - dirz, y, z + i * dirz - dirx, Blocks.LEAVES, 0, 2);
            }
         }
      }

      if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + (length + 1) * dirx, y, z + (length + 1) * dirz)).getBlock()) {
         OreSpawn.setBlockFast(world, x + (length + 1) * dirx, y, z + (length + 1) * dirz, Blocks.LEAVES, 0, 2);
      }

      if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + (length + 2) * dirx, y, z + (length + 2) * dirz)).getBlock()) {
         OreSpawn.setBlockFast(world, x + (length + 2) * dirx, y, z + (length + 2) * dirz, Blocks.LEAVES, 0, 2);
      }
   }

   public void WindTree(World world, int x, int y, int z, int dir) {
      if (dir >= 0 && dir <= 3) {
         int dirx = 1;
         int dirz = 0;
         if (dir == 1) {
            dirx = -1;
            dirz = 0;
         }

         if (dir == 2) {
            dirx = 0;
            dirz = 1;
         }

         if (dir == 3) {
            dirx = 0;
            dirz = -1;
         }

         Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
         if (bid == Blocks.GRASS || bid == Blocks.DIRT) {
            int height = world.rand.nextInt(8) + 40;
            int width = world.rand.nextInt(4) + 8;

            for (int j = 0; j < height; j++) {
               OreSpawn.setBlockFast(world, x, j + y, z, Blocks.LOG, 0, 2);
               if (j > height / 5) {
                  OreSpawn.setBlockFast(world, x + dirx, j + y, z + dirz, Blocks.LEAVES, 0, 2);
                  if (j > height / 4 && j % 4 == 0) {
                     this.WindTreeBranch(world, x, j + y, z, height - j, dirx, dirz);
                  }
               }
            }

            OreSpawn.setBlockFast(world, x, y + height, z, Blocks.LEAVES, 0, 2);
         }
      }
   }

   private void SkyTreeBranch(World world, int x, int y, int z, int length, int dirx, int dirz) {
      for (int i = 1; i < length; i++) {
         OreSpawn.setBlockFast(world, x + i * dirx, y, z + i * dirz, OreSpawn.MySkyTreeLog, 0, 2);
         if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx, y + 1, z + i * dirz)).getBlock()) {
            OreSpawn.setBlockFast(world, x + i * dirx, y + 1, z + i * dirz, Blocks.LEAVES, 0, 2);
         }

         if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx + dirz, y, z + i * dirz + dirx)).getBlock()) {
            OreSpawn.setBlockFast(world, x + i * dirx + dirz, y, z + i * dirz + dirx, Blocks.LEAVES, 0, 2);
         }

         if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + i * dirx - dirz, y, z + i * dirz - dirx)).getBlock()) {
            OreSpawn.setBlockFast(world, x + i * dirx - dirz, y, z + i * dirz - dirx, Blocks.LEAVES, 0, 2);
         }
      }

      if (Blocks.AIR == OreSpawn.getBlockStateFast(world, new BlockPos(x + length * dirx, y, z + length * dirz)).getBlock()) {
         OreSpawn.setBlockFast(world, x + length * dirx, y, z + length * dirz, Blocks.LEAVES, 0, 2);
      }
   }

   public void SkyTree(World world, int x, int y, int z) {
      Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
      if (bid == Blocks.GRASS || bid == Blocks.DIRT) {
         int height = world.rand.nextInt(15) + 190;
         if (height - y >= 20) {
            int width = world.rand.nextInt(10) + 25;

            for (int j = y; j <= height; j++) {
               OreSpawn.setBlockFast(world, x, j, z, OreSpawn.MySkyTreeLog, 0, 2);
            }

            OreSpawn.setBlockFast(world, x, height + 1, z, Blocks.LEAVES, 0, 2);
            this.SkyTreeBranch(world, x, height, z, width, 1, 0);
            this.SkyTreeBranch(world, x, height, z, width, -1, 0);
            this.SkyTreeBranch(world, x, height, z, width, 0, 1);
            this.SkyTreeBranch(world, x, height, z, width, 0, -1);
            height -= 5;
            height -= world.rand.nextInt(4);
            width /= 3;
            this.SkyTreeBranch(world, x, height, z, width, 1, 0);
            this.SkyTreeBranch(world, x, height, z, width, -1, 0);
            this.SkyTreeBranch(world, x, height, z, width, 0, 1);
            this.SkyTreeBranch(world, x, height, z, width, 0, -1);
         }
      }
   }

   public void DuplicatorTree(World world, int x, int y, int z) {
      Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y - 1, z)).getBlock();
      if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
         bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y - 2, z)).getBlock();
         if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
            bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y - 3, z)).getBlock();
            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
               return;
            }

            int var18 = y - 3;
         } else {
            int var17 = y - 2;
         }
      } else {
         int realy = y - 1;
         bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 1, z)).getBlock();
         if (bid != OreSpawn.MyDT) {
            OreSpawn.setBlockFast(world, x, realy + 1, z, OreSpawn.MyDT, 0, 2);
         } else {
            bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 2, z)).getBlock();
            if (bid != OreSpawn.MyDT) {
               OreSpawn.setBlockFast(world, x, realy + 2, z, OreSpawn.MyDT, 0, 2);
            } else {
               bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 3, z)).getBlock();
               if (bid != OreSpawn.MyDT) {
                  OreSpawn.setBlockFast(world, x, realy + 3, z, OreSpawn.MyDT, 0, 2);
               } else {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 4, z)).getBlock();
                  if (bid != OreSpawn.MyAppleLeaves) {
                     OreSpawn.setBlockFast(world, x, realy + 4, z, OreSpawn.MyAppleLeaves, 0, 2);
                  } else {
                     for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                           if (j != 0 || i != 0) {
                              bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + i, realy + 3, z + j)).getBlock();
                              if (bid != OreSpawn.MyAppleLeaves) {
                                 OreSpawn.setBlockFast(world, x + i, realy + 3, z + j, OreSpawn.MyAppleLeaves, 0, 2);
                                 return;
                              }
                           }
                        }
                     }

                     Block bidm = Blocks.AIR;

                     for (int tries = 0; tries < 20 && (bidm == Blocks.AIR || bidm == OreSpawn.MyDT); tries++) {
                        int var13 = world.rand.nextInt(5) - 2;
                        int jx = world.rand.nextInt(5) - 2;
                        bidm = OreSpawn.getBlockStateFast(world, new BlockPos(x + var13, realy + 1, z + jx)).getBlock();
                        int meta = OreSpawn.getBlockStateFast(world, new BlockPos(x + var13, realy + 1, z + jx)).getBlock().getMetaFromState(OreSpawn.getBlockStateFast(world, new BlockPos(x + var13, realy + 1, z + jx)));
                        if (bidm != Blocks.AIR && bidm != OreSpawn.MyDT) {
                           for (int k = 0; k < 20; k++) {
                              var13 = world.rand.nextInt(5) - 2;
                              jx = world.rand.nextInt(5) - 2;
                              bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + var13, realy + 1, z + jx)).getBlock();
                              if (bid == Blocks.AIR) {
                                 world.setBlockState(new BlockPos(x + var13, realy + 1, z + jx), (bidm).getStateFromMeta(meta), 2);
                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void make_leaves(World world, int x, int y, int z) {
      for (int l1 = -3; l1 <= 3; l1++) {
         for (int l2 = -3; l2 <= 3; l2++) {
            for (int l3 = 0; l3 <= 2; l3++) {
               Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + l1, y + l3, z + l2)).getBlock();
               if (bid == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawn.MyExperienceLeaves, 0, 2);
               }
            }
         }
      }
   }

   private void grow_small_branch(World world, int x, int y, int z, int xdir, int zdir, int xxdir, int zzdir) {
      int i2 = 0;
      int k2 = 0;
      int j2 = 0;
      int i = x;
      int j = y;
      int k = z;
      int grow = 4 + world.rand.nextInt(2);

      for (int n = 0; n < grow; n++) {
         OreSpawn.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
         this.make_leaves(world, i, j, k);
         j++;
         i += xdir;
         k += zdir;
         i2 = i;
         k2 = k;
      }

      grow = 4 + world.rand.nextInt(3);

      for (int var22 = 0; var22 < grow; var22++) {
         OreSpawn.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
         this.make_leaves(world, i, j, k);
         i += xdir;
         k += zdir;
      }

      grow = 4 + world.rand.nextInt(3);

      for (int var23 = 0; var23 < grow; var23++) {
         OreSpawn.setBlockFast(world, i2, j, k2, Blocks.LOG, 0, 2);
         this.make_leaves(world, i2, j, k2);
         i2 += xxdir;
         k2 += zzdir;
      }

      j2 = --j;
      grow = 3 + world.rand.nextInt(3);

      for (int var24 = 0; var24 < grow; var24++) {
         OreSpawn.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
         this.make_leaves(world, i, j, k);
         i += xdir;
         k += zdir;
         j--;
      }

      grow = 3 + world.rand.nextInt(3);

      for (int var25 = 0; var25 < grow; var25++) {
         OreSpawn.setBlockFast(world, i2, j2, k2, Blocks.LOG, 0, 2);
         this.make_leaves(world, i2, j2, k2);
         i2 += xxdir;
         k2 += zzdir;
         j2--;
      }
   }

   private void grow_branch(World world, int x, int y, int z, int xdir, int zdir, int xxdir, int zzdir) {
      int i2 = 0;
      int k2 = 0;
      int j2 = 0;
      int i = x;
      int j = y;
      int k = z;
      int grow = 5 + world.rand.nextInt(4);

      for (int n = 0; n < grow; n++) {
         OreSpawn.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
         this.make_leaves(world, i, j, k);
         j++;
         i += xdir;
         k += zdir;
         i2 = i;
         k2 = k;
      }

      grow = 6 + world.rand.nextInt(5);

      for (int var22 = 0; var22 < grow; var22++) {
         OreSpawn.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
         this.make_leaves(world, i, j, k);
         i += xdir;
         k += zdir;
      }

      grow = 6 + world.rand.nextInt(5);

      for (int var23 = 0; var23 < grow; var23++) {
         OreSpawn.setBlockFast(world, i2, j, k2, Blocks.LOG, 0, 2);
         this.make_leaves(world, i2, j, k2);
         i2 += xxdir;
         k2 += zzdir;
      }

      j2 = --j;
      grow = 4 + world.rand.nextInt(4);

      for (int var24 = 0; var24 < grow; var24++) {
         OreSpawn.setBlockFast(world, i, j, k, Blocks.LOG, 0, 2);
         this.make_leaves(world, i, j, k);
         i += xdir;
         k += zdir;
         j--;
      }

      grow = 4 + world.rand.nextInt(4);

      for (int var25 = 0; var25 < grow; var25++) {
         OreSpawn.setBlockFast(world, i2, j2, k2, Blocks.LOG, 0, 2);
         this.make_leaves(world, i2, j2, k2);
         i2 += xxdir;
         k2 += zzdir;
         j2--;
      }
   }

   public void ExperienceTree(World world, int x, int y, int z) {
      Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
      if (bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.SOUL_SAND) {
         for (int j = 1; j < 6; j++) {
            for (int i = 0; i < 2; i++) {
               for (int k = 0; k < 2; k++) {
                  OreSpawn.setBlockFast(world, x + i, y + j, z + k, Blocks.LOG, 0, 2);
               }
            }
         }

         this.grow_branch(world, x, y + 6, z, 0, 1, 1, 1);
         this.grow_branch(world, x + 1, y + 6, z, 1, 0, 1, -1);
         this.grow_branch(world, x, y + 6, z + 1, -1, 0, -1, 1);
         this.grow_branch(world, x + 1, y + 6, z + 1, 0, -1, -1, -1);

         for (int var12 = 7; var12 < 19; var12++) {
            for (int i = 0; i < 2; i++) {
               for (int k = 0; k < 2; k++) {
                  OreSpawn.setBlockFast(world, x + i, y + var12, z + k, Blocks.LOG, 0, 2);
               }
            }
         }

         this.grow_small_branch(world, x, y + 19, z, 0, 1, -1, 1);
         this.grow_small_branch(world, x + 1, y + 19, z, 1, 0, 1, 1);
         this.grow_small_branch(world, x, y + 19, z + 1, -1, 0, -1, -1);
         this.grow_small_branch(world, x + 1, y + 19, z + 1, 0, -1, 1, -1);
         int grow = 5 + world.rand.nextInt(6);

         for (int var13 = 19; var13 < 19 + grow; var13++) {
            for (int i = 0; i < 2; i++) {
               for (int k = 0; k < 2; k++) {
                  OreSpawn.setBlockFast(world, x + i, y + var13, z + k, Blocks.LOG, 0, 2);
                  this.make_leaves(world, x + i, y + var13, z + k);
               }
            }
         }
      }
   }

   public void SmallTree(World world, int x, int y, int z) {
      Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y - 1, z)).getBlock();
      if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
         bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y - 2, z)).getBlock();
         if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
            bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y - 3, z)).getBlock();
            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
               OreSpawn.setBlockFast(world, x, y, z, Blocks.AIR, 0, 2);
               return;
            }

            int var14 = y - 3;
         } else {
            int var13 = y - 2;
         }
      } else {
         int realy = y - 1;
         bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 1, z)).getBlock();
         if (bid == Blocks.AIR) {
            OreSpawn.setBlockFast(world, x, realy + 1, z, OreSpawn.MySkyTreeLog, 0, 2);
         }

         if (world.rand.nextInt(2) == 1) {
            bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 2, z)).getBlock();
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x, realy + 2, z, OreSpawn.MySkyTreeLog, 0, 2);
            }

            if (world.rand.nextInt(2) == 1) {
               bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 3, z)).getBlock();
               if (bid == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, x, realy + 3, z, OreSpawn.MySkyTreeLog, 0, 2);
               }
            } else {
               realy--;
            }
         } else {
            realy -= 2;
         }

         bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, realy + 4, z)).getBlock();
         if (bid == Blocks.AIR) {
            OreSpawn.setBlockFast(world, x, realy + 4, z, OreSpawn.MyAppleLeaves, 0, 2);
         }

         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + i, realy + 3, z + j)).getBlock();
               if (bid == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, x + i, realy + 3, z + j, OreSpawn.MyAppleLeaves, 0, 2);
               }
            }
         }
      }
   }

   public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
      for (int k = 0; k < len; k++) {
         int ix = world.rand.nextInt(2) - world.rand.nextInt(2) + biasx;
         int iz = world.rand.nextInt(2) - world.rand.nextInt(2) + biasz;
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

         int iy = world.rand.nextInt(3) > 0 ? 1 : 0;
         x += ix;
         z += iz;
         y += iy;
         Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
         if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawn.MyAppleLeaves) {
            return;
         }

         OreSpawn.setBlockFast(world, x, y, z, Blocks.LOG, 0, 2);

         for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
               if (world.rand.nextInt(2) == 1) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + m, y, z + n)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, x + m, y, z + n, OreSpawn.MyAppleLeaves, 0, 2);
                  }
               }
            }
         }

         if (world.rand.nextInt(2) == 1) {
            bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y + 1, z)).getBlock();
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x, y + 1, z, OreSpawn.MyAppleLeaves, 0, 2);
            }
         }
      }
   }

   public void ScragglyTreeWithBranches(World world, int x, int y, int z) {
      int i = 1 + world.rand.nextInt(3);
      int j = i + world.rand.nextInt(12);

      for (int k = 0; k < i; k++) {
         Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y + k, z)).getBlock();
         if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawn.MyAppleLeaves) {
            return;
         }

         OreSpawn.setBlockFast(world, x, y + k, z, Blocks.LOG, 0, 2);
      }

      y += i - 1;

      for (int var15 = i; var15 < j; var15++) {
         int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
         int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
         int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
         x += ix;
         z += iz;
         y += iy;
         Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
         if (bid != Blocks.AIR && bid != Blocks.LOG && bid != OreSpawn.MyAppleLeaves) {
            break;
         }

         OreSpawn.setBlockFast(world, x, y, z, Blocks.LOG, 0, 2);
         if (world.rand.nextInt(4) == 1) {
            this.makeScragglyBranch(
               world,
               x,
               y,
               z,
               world.rand.nextInt(1 + j - var15),
               world.rand.nextInt(2) - world.rand.nextInt(2),
               world.rand.nextInt(2) - world.rand.nextInt(2)
            );
         }

         for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
               if (world.rand.nextInt(2) == 1) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + m, y, z + n)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, x + m, y, z + n, OreSpawn.MyAppleLeaves, 0, 2);
                  }
               }
            }
         }

         if (world.rand.nextInt(2) == 1) {
            bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y + 1, z)).getBlock();
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x, y + 1, z, OreSpawn.MyAppleLeaves, 0, 2);
            }
         }
      }
   }

   public void FairyTree(World world, int x, int y, int z) {
      for (int j = 1; j < 6; j++) {
         for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
               OreSpawn.setBlockFast(world, x + i, y + j, z + k, OreSpawn.MyCrystalTreeLog, 0, 2);
            }
         }
      }

      this.grow_crystal_branch(world, x, y + 5, z, 0, 1, 1, 1, -1);
      this.grow_crystal_branch(world, x + 1, y + 5, z, 1, 0, 1, -1, -1);
      this.grow_crystal_branch(world, x, y + 5, z + 1, -1, 0, -1, 1, -1);
      this.grow_crystal_branch(world, x + 1, y + 5, z + 1, 0, -1, -1, -1, -1);
      this.grow_crystal_branch(world, x, y + 6, z, 0, 1, -1, 1, -1);
      this.grow_crystal_branch(world, x + 1, y + 6, z, 1, 0, 1, 1, -1);
      this.grow_crystal_branch(world, x, y + 6, z + 1, -1, 0, -1, -1, -1);
      this.grow_crystal_branch(world, x + 1, y + 6, z + 1, 0, -1, 1, -1, -1);
      int grow = 5 + world.rand.nextInt(5);

      for (int var13 = 6; var13 < 6 + grow; var13++) {
         for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
               OreSpawn.setBlockFast(world, x + i, y + var13, z + k, OreSpawn.MyCrystalTreeLog, 0, 2);
               this.make_crystal_leaves(world, x + i, y + var13, z + k);
            }
         }
      }

      world.setBlockState(new BlockPos(x - 1, y + 1, z), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(x - 1, y + 1, z));
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Fairy");
      }

      world.setBlockState(new BlockPos(x + 2, y + 1, z), (Blocks.CHEST).getStateFromMeta(0), 2);
      TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(x + 2, y + 1, z));
      if (chest != null) {
         SimpleChest.fill(world.rand, CrystalChestContentsList, chest, 1 + world.rand.nextInt(5));
      }
   }

   private void make_crystal_leaves(World world, int x, int y, int z) {
      for (int l1 = -2; l1 <= 2; l1++) {
         for (int l2 = -2; l2 <= 2; l2++) {
            for (int l3 = 0; l3 <= 1; l3++) {
               Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + l1, y + l3, z + l2)).getBlock();
               if (bid == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawn.MyCrystalLeaves3, 0, 2);
               }
            }
         }
      }
   }

   private void make_crystal_castle_leaves(World world, int x, int y, int z) {
      for (int l1 = -1; l1 <= 1; l1++) {
         for (int l2 = -1; l2 <= 1; l2++) {
            for (int l3 = 0; l3 <= 1; l3++) {
               Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + l1, y + l3, z + l2)).getBlock();
               if (bid == Blocks.AIR) {
                  if (l3 != 0) {
                     OreSpawn.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawn.MyCrystalLeaves3, 0, 2);
                  } else {
                     OreSpawn.setBlockFast(world, x + l1, y + l3, z + l2, OreSpawn.MyCrystalLeaves2, 0, 2);
                  }
               }
            }
         }
      }
   }

   private void grow_crystal_branch(World world, int x, int y, int z, int xdir, int zdir, int xxdir, int zzdir, int ydir) {
      int i2 = 0;
      int k2 = 0;
      int j2 = 0;
      int i = x;
      int j = y;
      int k = z;
      int grow = 4 + world.rand.nextInt(4);
      if (OreSpawn.LessLag == 1) {
         grow--;
      }

      if (OreSpawn.LessLag == 2) {
         grow -= 2;
      }

      for (int n = 0; n < grow; n++) {
         OreSpawn.setBlockFast(world, i, j, k, OreSpawn.MyCrystalTreeLog, 0, 2);
         this.make_crystal_leaves(world, i, j, k);
         j++;
         i += xdir;
         k += zdir;
         i2 = i;
         k2 = k;
      }

      grow = 5 + world.rand.nextInt(5);
      if (OreSpawn.LessLag == 1) {
         grow--;
      }

      if (OreSpawn.LessLag == 2) {
         grow -= 2;
      }

      for (int var20 = 0; var20 < grow; var20++) {
         OreSpawn.setBlockFast(world, i, j, k, OreSpawn.MyCrystalTreeLog, 0, 2);
         this.make_crystal_leaves(world, i, j, k);
         i += xdir;
         k += zdir;
      }

      grow = 5 + world.rand.nextInt(5);
      if (OreSpawn.LessLag == 1) {
         grow--;
      }

      if (OreSpawn.LessLag == 2) {
         grow -= 2;
      }

      for (int var21 = 0; var21 < grow; var21++) {
         OreSpawn.setBlockFast(world, i2, j, k2, OreSpawn.MyCrystalTreeLog, 0, 2);
         this.make_crystal_leaves(world, i2, j, k2);
         i2 += xxdir;
         k2 += zzdir;
      }

      j2 = --j;
      grow = 4 + world.rand.nextInt(4);
      if (OreSpawn.LessLag == 1) {
         grow--;
      }

      if (OreSpawn.LessLag == 2) {
         grow -= 2;
      }

      for (int var22 = 0; var22 < grow; var22++) {
         OreSpawn.setBlockFast(world, i, j, k, OreSpawn.MyCrystalTreeLog, 0, 2);
         this.make_crystal_leaves(world, i, j, k);
         i += xdir;
         k += zdir;
         j += ydir;
      }

      grow = 4 + world.rand.nextInt(4);
      if (OreSpawn.LessLag == 1) {
         grow--;
      }

      if (OreSpawn.LessLag == 2) {
         grow -= 2;
      }

      for (int var23 = 0; var23 < grow; var23++) {
         OreSpawn.setBlockFast(world, i2, j2, k2, OreSpawn.MyCrystalTreeLog, 0, 2);
         this.make_crystal_leaves(world, i2, j2, k2);
         i2 += xxdir;
         k2 += zzdir;
         j2 += ydir;
      }
   }

   public void addSomething(World world, int x, int y, int z) {
      int i = world.rand.nextInt(3);
      if (i == 1) {
         world.setBlockState(new BlockPos(x, y + 1, z), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(x, y + 1, z));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Fairy");
         }
      }

      if (i == 2) {
         world.setBlockState(new BlockPos(x, y + 1, z), (Blocks.CHEST).getStateFromMeta(0), 2);
         TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(x, y + 1, z));
         if (chest != null) {
            SimpleChest.fill(world.rand, CrystalChestContentsList, chest, 1 + world.rand.nextInt(5));
         }
      }
   }

   public void FairyCastleTree(World world, int x, int y, int z) {
      int nc = 6;
      if (OreSpawn.LessLag == 1) {
         nc--;
      }

      if (OreSpawn.LessLag == 2) {
         nc -= 2;
      }

      int j = 3 + world.rand.nextInt(3);
      int spread = 0;

      for (int iter = 0; iter < nc; iter++) {
         int grow = 4 + world.rand.nextInt(3);
         int width = 1 + world.rand.nextInt(3);
         int randy = world.rand.nextInt(3) - 1;

         for (int i = -width; i <= width; i++) {
            for (int k = -width; k <= width; k++) {
               OreSpawn.setBlockFast(world, x + i + spread, y + j + randy, z + k, OreSpawn.MyCrystalTreeLog, 0, 2);
               if (i == -width || i == width || k == -width || k == width) {
                  this.make_crystal_castle_leaves(world, x + i + spread, y + j + randy, z + k);
               }

               if (iter != 0 && i == 0 && k == 0) {
                  this.addSomething(world, x + i + spread, y + j + randy, z + k);
               }

               if (i == -width && (k == -width || k == width)) {
                  OreSpawn.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k, OreSpawn.CrystalTorch, 0, 2);
               }

               if (i == width && (k == -width || k == width)) {
                  OreSpawn.setBlockFast(world, x + i + spread, y + j + randy + 1, z + k, OreSpawn.CrystalTorch, 0, 2);
               }
            }
         }

         if (iter != 0) {
            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var14 = -width; var14 <= width; var14++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var14 - spread, y + j + randy, z + k, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var14 == -width || var14 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var14 - spread, y + j + randy, z + k);
                  }

                  if (var14 == 0 && k == 0) {
                     this.addSomething(world, x + var14 - spread, y + j + randy, z + k);
                  }

                  if (var14 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var14 - spread, y + j + randy + 1, z + k, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var14 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var14 - spread, y + j + randy + 1, z + k, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }

            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var15 = -width; var15 <= width; var15++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var15, y + j + randy, z + k + spread, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var15 == -width || var15 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var15, y + j + randy, z + k + spread);
                  }

                  if (var15 == 0 && k == 0) {
                     this.addSomething(world, x + var15, y + j + randy, z + k + spread);
                  }

                  if (var15 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var15, y + j + randy + 1, z + k + spread, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var15 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var15, y + j + randy + 1, z + k + spread, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }

            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var16 = -width; var16 <= width; var16++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var16, y + j + randy, z + k - spread, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var16 == -width || var16 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var16, y + j + randy, z + k - spread);
                  }

                  if (var16 == 0 && k == 0) {
                     this.addSomething(world, x + var16, y + j + randy, z + k - spread);
                  }

                  if (var16 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var16, y + j + randy + 1, z + k - spread, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var16 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var16, y + j + randy + 1, z + k - spread, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }
         }

         if (iter >= 2) {
            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var17 = -width; var17 <= width; var17++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var17 + spread, y + j + randy, z + k + spread, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var17 == -width || var17 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var17 + spread, y + j + randy, z + k + spread);
                  }

                  if (var17 == 0 && k == 0) {
                     this.addSomething(world, x + var17 + spread, y + j + randy, z + k + spread);
                  }

                  if (var17 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var17 + spread, y + j + randy + 1, z + k + spread, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var17 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var17 + spread, y + j + randy + 1, z + k + spread, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }

            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var18 = -width; var18 <= width; var18++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var18 - spread, y + j + randy, z + k - spread, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var18 == -width || var18 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var18 - spread, y + j + randy, z + k - spread);
                  }

                  if (var18 == 0 && k == 0) {
                     this.addSomething(world, x + var18 - spread, y + j + randy, z + k - spread);
                  }

                  if (var18 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var18 - spread, y + j + randy + 1, z + k - spread, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var18 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var18 - spread, y + j + randy + 1, z + k - spread, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }

            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var19 = -width; var19 <= width; var19++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var19 - spread, y + j + randy, z + k + spread, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var19 == -width || var19 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var19 - spread, y + j + randy, z + k + spread);
                  }

                  if (var19 == 0 && k == 0) {
                     this.addSomething(world, x + var19 - spread, y + j + randy, z + k + spread);
                  }

                  if (var19 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var19 - spread, y + j + randy + 1, z + k + spread, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var19 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var19 - spread, y + j + randy + 1, z + k + spread, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }

            width = 1 + world.rand.nextInt(3 + iter);
            randy = world.rand.nextInt(3) - 1;

            for (int var20 = -width; var20 <= width; var20++) {
               for (int k = -width; k <= width; k++) {
                  OreSpawn.setBlockFast(world, x + var20 + spread, y + j + randy, z + k - spread, OreSpawn.MyCrystalTreeLog, 0, 2);
                  if (var20 == -width || var20 == width || k == -width || k == width) {
                     this.make_crystal_castle_leaves(world, x + var20 + spread, y + j + randy, z + k - spread);
                  }

                  if (var20 == 0 && k == 0) {
                     this.addSomething(world, x + var20 + spread, y + j + randy, z + k - spread);
                  }

                  if (var20 == -width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var20 + spread, y + j + randy + 1, z + k - spread, OreSpawn.CrystalTorch, 0, 2);
                  }

                  if (var20 == width && (k == -width || k == width)) {
                     OreSpawn.setBlockFast(world, x + var20 + spread, y + j + randy + 1, z + k - spread, OreSpawn.CrystalTorch, 0, 2);
                  }
               }
            }
         }

         j += grow;
         if (iter == 0) {
            spread = 3;
         }

         spread += grow;
      }
   }
}

package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InstantGarden extends Item {
   public InstantGarden(int i) {

      this.setCreativeTab(CreativeTabs.REDSTONE);
   }
   public InstantGarden(String name) { this(0); setRegistryName(name); setTranslationKey(name); }
   @Override public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float x, float y, float z) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), x, y, z) ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10
   ) {
      int deltax = 0;
      int deltaz = 0;
      int bid = 0;
      int dirx = 0;
      int dirz = 0;
      int height = 10;
      int width = 7;
      int length = 18;
      if (cposx < 0) {
         dirx = -1;
      }

      if (cposz < 0) {
         dirz = -1;
      }

      int pposx = (int)(Player.posX + 0.99 * dirx);
      int pposy = (int)Player.posY;
      int pposz = (int)(Player.posZ + 0.99 * dirz);
      if (cposx - pposx != 0 && cposz - pposz != 0) {
         return false;
      } else {
         int x = cposx;
         int y = pposy;
         int z = cposz;
         if (cposx - pposx < 0) {
            deltax = -1;
         }

         if (cposx - pposx > 0) {
            deltax = 1;
         }

         if (cposz - pposz < 0) {
            deltaz = -1;
         }

         if (cposz - pposz > 0) {
            deltaz = 1;
         }

         if (deltax == 0 && deltaz == 0) {
            return false;
         } else if (deltax != 0 && deltaz != 0) {
            return false;
         } else {
            Player.world.playSound(null, Player.posX, Player.posY, Player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.5F);
            if (world.isRemote) {
               return true;
            } else {
               for (int i = 0; i < height; i++) {
                  for (int k = 0; k < length; k++) {
                     for (int j = -width; j <= width; j++) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax), (Blocks.AIR).getStateFromMeta(0), 2);
                        if (i == 0) {
                           world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i - 1, z + k * deltaz + j * deltax), (Blocks.GRASS).getStateFromMeta(0), 2);
                        }
                     }
                  }
               }

               for (int k = 1; k < length - 1; k++) {
                  int var28 = 0;

                  for (int jx = -width; jx <= width; jx++) {
                     if (var28 == 1) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (OreSpawn.MyRadishPlant).getStateFromMeta(0), 2);
                     }

                     if (var28 == 2) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (OreSpawn.MyLettucePlant1).getStateFromMeta(0), 2);
                     }

                     if (var28 == 3) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (Blocks.CARROTS).getStateFromMeta(0), 2);
                     }

                     if (var28 == 4) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.WATER).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 2, z + k * deltaz + jx * deltax), (Blocks.COBBLESTONE).getStateFromMeta(0), 2);
                     }

                     if (var28 == 5) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (Blocks.POTATOES).getStateFromMeta(0), 2);
                     }

                     if (var28 == 6) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (Blocks.WHEAT).getStateFromMeta(0), 2);
                     }

                     if (var28 == 7) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (OreSpawn.MyTomatoPlant1).getStateFromMeta(0), 2);
                     }

                     if (var28 == 8) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.WATER).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 2, z + k * deltaz + jx * deltax), (Blocks.COBBLESTONE).getStateFromMeta(0), 2);
                     }

                     if (var28 == 9) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (OreSpawn.MyCornPlant1).getStateFromMeta(0), 2);
                     }

                     if (var28 == 10) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (OreSpawn.MyStrawberryPlant).getStateFromMeta(0), 2);
                     }

                     if (var28 == 11) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 2, z + k * deltaz + jx * deltax), (Blocks.COBBLESTONE).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (Blocks.REEDS).getStateFromMeta(0), 2);
                     }

                     if (var28 == 12) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.WATER).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 2, z + k * deltaz + jx * deltax), (Blocks.COBBLESTONE).getStateFromMeta(0), 2);
                     }

                     if (var28 == 13) {
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y - 1, z + k * deltaz + jx * deltax), (Blocks.SOUL_SAND).getStateFromMeta(0), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + jx * deltaz, y, z + k * deltaz + jx * deltax), (Blocks.MELON_STEM).getStateFromMeta(0), 2);
                     }

                     var28++;
                  }
               }

               if (!Player.capabilities.isCreativeMode) {
                  par1ItemStack.shrink(1);
               }

               return true;
            }
         }
      }
   }


}

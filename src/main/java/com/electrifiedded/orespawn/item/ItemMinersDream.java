package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public class ItemMinersDream extends Item {
   public ItemMinersDream(int i) {
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setMaxStackSize(16);
      this.setRegistryName("miners_dream");
      this.setTranslationKey("miners_dream");
   }

   @Override
   public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                     EnumFacing facing, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(),
         facing.getIndex(), hitX, hitY, hitZ) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10
   ) {
      int deltax = 0;
      int deltaz = 0;
      int dirx = 0;
      int dirz = 0;
      int height = 5;
      int width = 5;
      int length = 64;
      int torches = 5;
      int solid_count = 0;
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
                     solid_count = 0;

                     for (int j = -width; j <= width; j++) {
                        Block bid = world.getBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax)).getBlock();
                        if (bid == Blocks.STONE
                           || bid == Blocks.DIRT
                           || bid == Blocks.GRAVEL
                           || bid == Blocks.FLOWING_WATER
                           || bid == Blocks.WATER
                           || bid == Blocks.FLOWING_LAVA
                           || bid == Blocks.LAVA
                           || bid == Blocks.NETHERRACK
                           || bid == Blocks.END_STONE
                           || bid == OreSpawn.CrystalStone) {
                           world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax), (Blocks.AIR).getStateFromMeta(0), 2);
                        }

                        if (i == height - 1) {
                           bid = world.getBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax)).getBlock();
                           if (bid != Blocks.AIR) {
                              solid_count++;
                           }

                           if (bid == Blocks.AIR
                              || bid == Blocks.GRAVEL
                              || bid == Blocks.SAND
                              || bid == Blocks.FLOWING_WATER
                              || bid == Blocks.WATER
                              || bid == Blocks.FLOWING_LAVA
                              || bid == Blocks.LAVA) {
                              if (world.provider.getDimension() == OreSpawn.DimensionID5) {
                                 world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax), (OreSpawn.CrystalStone).getStateFromMeta(0), 2);
                              } else {
                                 world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax), (Blocks.COBBLESTONE).getStateFromMeta(0), 2);
                              }
                           }
                        }
                     }

                     if (i == height - 1 && solid_count == 0) {
                        for (int var30 = -width; var30 <= width; var30++) {
                           world.setBlockState(new BlockPos(x + k * deltax + var30 * deltaz, y + i + 1, z + k * deltaz + var30 * deltax), (Blocks.AIR).getStateFromMeta(0), 2);
                        }
                     }
                  }
               }

               for (int k = 0; k < length; k += torches) {
                  Block bidx = world.getBlockState(new BlockPos(x + k * deltax, y - 1, z + k * deltaz)).getBlock();
                  if ((
                        bidx == Blocks.STONE
                           || bidx == Blocks.DIRT
                           || bidx == Blocks.GRAVEL
                           || bidx == Blocks.NETHERRACK
                           || bidx == Blocks.END_STONE
                           || bidx == Blocks.BEDROCK
                     )
                     && world.isAirBlock(new BlockPos(x + k * deltax, y, z + k * deltaz))) {
                     world.setBlockState(new BlockPos(x + k * deltax, y, z + k * deltaz), (OreSpawn.ExtremeTorch).getStateFromMeta(0), 2);
                  }

                  if (bidx == OreSpawn.CrystalStone && world.isAirBlock(new BlockPos(x + k * deltax, y, z + k * deltaz))) {
                     world.setBlockState(new BlockPos(x + k * deltax, y, z + k * deltaz), (OreSpawn.CrystalTorch).getStateFromMeta(0), 2);
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

package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class InstantShelter extends Item {
   public InstantShelter(int i) {

      this.setCreativeTab(CreativeTabs.REDSTONE);
   }
   public InstantShelter(String name) { this(0); setRegistryName(name); setTranslationKey(name); }
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
      int stuffdir = 0;
      int length = 3;
      int width = 3;
      int height = 3;
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
         int y = pposy - 1;
         if (cposx - pposx < 0) {
            deltax = -1;
            stuffdir = 3;
         }

         if (cposx - pposx > 0) {
            deltax = 1;
            stuffdir = 2;
         }

         if (cposz - pposz < 0) {
            deltaz = -1;
            stuffdir = 5;
         }

         if (cposz - pposz > 0) {
            deltaz = 1;
            stuffdir = 4;
         }

         if (deltax == 0 && deltaz == 0) {
            return false;
         } else if (deltax != 0 && deltaz != 0) {
            return false;
         } else {
            int x = pposx;
            int z = pposz;
            Player.world.playSound(null, Player.posX, Player.posY, Player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.5F);
            if (world.isRemote) {
               return true;
            } else {
               for (int i = -width; i <= width; i++) {
                  for (int j = -length; j <= length; j++) {
                     for (int k = 0; k <= height + 1; k++) {
                        if (k == height + 1) {
                           world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.PLANKS).getDefaultState());
                        } else if (k == 0) {
                           world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.COBBLESTONE).getDefaultState());
                        } else if (i != width && j != length && i != -width && j != -length) {
                           world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState());
                        } else if (k == height) {
                           world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.GLASS).getDefaultState());
                        } else if ((k == 1 || k == 2) && i == deltax * width && j == deltaz * length) {
                           world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState());
                        } else {
                           world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.PLANKS).getDefaultState());
                        }
                     }
                  }
               }

               int var30 = 2;
               int kx = 1;
               int j = length - 1;
               world.setBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax), (Blocks.FURNACE).getDefaultState());
               world.setBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax), world.getBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax)).getBlock().getStateFromMeta(stuffdir), 3);
               var30 = 1;
               world.setBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax), (Blocks.CRAFTING_TABLE).getDefaultState());
               var30 = 0;
               world.setBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax), (Blocks.CHEST).getDefaultState());
               world.setBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax), world.getBlockState(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax)).getBlock().getStateFromMeta(stuffdir), 3);
               TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(x + var30 * deltax + j * deltaz, y + kx, z + var30 * deltaz + j * deltax));
               if (chest != null) {
                  chest.setInventorySlotContents(0, new ItemStack(Items.COMPASS));
                  chest.setInventorySlotContents(1, new ItemStack(Items.MAP));
                  chest.setInventorySlotContents(2, new ItemStack(Items.PORKCHOP, 8));
                  chest.setInventorySlotContents(3, new ItemStack(Blocks.TORCH, 32));
                  chest.setInventorySlotContents(4, new ItemStack(Items.COAL, 16));
                  chest.setInventorySlotContents(5, new ItemStack(Items.BED));
                  chest.setInventorySlotContents(6, new ItemStack(Items.BED));
                  chest.setInventorySlotContents(7, new ItemStack(Items.OAK_DOOR));
                  chest.setInventorySlotContents(8, new ItemStack(Items.IRON_PICKAXE));
                  chest.setInventorySlotContents(9, new ItemStack(Items.IRON_SWORD));
                  chest.setInventorySlotContents(10, new ItemStack(Items.IRON_AXE));
                  chest.setInventorySlotContents(11, new ItemStack(Items.BUCKET));
                  chest.setInventorySlotContents(12, new ItemStack(OreSpawn.MyOreSaltBlock, 4));
                  chest.setInventorySlotContents(13, new ItemStack(Blocks.CHEST));
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

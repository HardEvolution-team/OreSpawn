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

public class ZooCage extends Item {
   private int cage_size = 2;

   public ZooCage(int i, int j) {

      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.cage_size = j;
   }
   public ZooCage(String name, int size) { this(0, size); setRegistryName(name); setTranslationKey(name); }
   @Override public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float x, float y, float z) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), x, y, z) ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10
   ) {
      int bid = 0;
      int dirx = 0;
      int dirz = 0;
      int width;
      int length;
      int height = width = length = this.cage_size / 2 + 1;
      if (cposx < 0) {
         dirx = -1;
      }

      if (cposz < 0) {
         dirz = -1;
      }

      int x = (int)(Player.posX + 0.99 * dirx);
      int y = (int)Player.posY - 1;
      int z = (int)(Player.posZ + 0.99 * dirz);
      Player.world.playSound(null, Player.posX, Player.posY, Player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.5F);
      if (world.isRemote) {
         return true;
      } else {
         for (int i = -width; i <= width; i++) {
            for (int j = -length; j <= length; j++) {
               for (int k = 0; k <= height + 1; k++) {
                  if (k == height + 1) {
                     world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.QUARTZ_BLOCK).getDefaultState());
                  } else if (k == 0) {
                     world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.QUARTZ_BLOCK).getDefaultState());
                  } else if (i != width && j != length && i != -width && j != -length) {
                     world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState());
                  } else {
                     world.setBlockState(new BlockPos(x + i, y + k, z + j), (Blocks.GLASS).getDefaultState());
                  }
               }
            }
         }

         if (!Player.capabilities.isCreativeMode) {
            par1ItemStack.shrink(1);
         }

         return true;
      }
   }


}

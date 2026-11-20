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
import net.minecraft.world.chunk.Chunk;

public class ItemAppleSeed extends Item {
    public ItemAppleSeed(int i) {
       this("apple_seed");
    }

    public ItemAppleSeed(String name) {
       setRegistryName(name);
       setTranslationKey(name);
       this.setCreativeTab(CreativeTabs.DECORATIONS);
       this.setMaxStackSize(64);
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10
   ) {
      if (!world.isRemote) {
         Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
         if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
            return false;
         }

         if (this == OreSpawn.MyAppleSeed) {
            this.makeTree(world, x, y, z, OreSpawn.MyAppleLeaves, null);
         } else if (this == OreSpawn.MyCherrySeed) {
            this.makeTree(world, x, y, z, OreSpawn.MyCherryLeaves, null);
         } else {
            this.makeTree(world, x, y, z, OreSpawn.MyPeachLeaves, null);
         }
      }

      if (!par2EntityPlayer.capabilities.isCreativeMode) {
         par1ItemStack.shrink(1);
      }

      return true;
   }

   public void makeTree(World world, int x, int y, int z, Block blkid, Chunk chunk) {
      Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(x, y, z)).getBlock();
      if (bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.SOUL_SAND) {
         int h1 = 12;
         int h2 = 6;
         int h3 = 9;
         int h4 = 6;
         int h5 = 14;
         int w1 = 5;
         int w2 = 3;
         if (blkid == OreSpawn.MyPeachLeaves) {
            h1 = 10;
            h2 = 5;
            h3 = 7;
            h4 = 5;
            h5 = 12;
            w1 = 4;
            w2 = 2;
         }

         if (blkid == OreSpawn.MyCherryLeaves) {
            h1 = 8;
            h2 = 3;
            h3 = 5;
            h4 = 3;
            h5 = 10;
            w1 = 3;
            w2 = 1;
         }

         for (int j = 1; j < h1; j++) {
            world.setBlockState(new BlockPos(x, y + j, z), (Blocks.LOG).getStateFromMeta(0), 2);
         }

         for (int var19 = 1; var19 < w1; var19++) {
            OreSpawn.setBlockSuperFast(world, x + var19, y + h2, z, Blocks.LOG, 0, 2, chunk);
         }

         for (int var20 = 1; var20 < w1; var20++) {
            OreSpawn.setBlockSuperFast(world, x - var20, y + h2, z, Blocks.LOG, 0, 2, chunk);
         }

         for (int var21 = 1; var21 < w1; var21++) {
            OreSpawn.setBlockSuperFast(world, x, y + h2, z + var21, Blocks.LOG, 0, 2, chunk);
         }

         for (int var22 = 1; var22 < w1; var22++) {
            OreSpawn.setBlockSuperFast(world, x, y + h2, z - var22, Blocks.LOG, 0, 2, chunk);
         }

         for (int var23 = 1; var23 < w2; var23++) {
            OreSpawn.setBlockSuperFast(world, x + var23, y + h3, z, Blocks.LOG, 0, 2, chunk);
         }

         for (int var24 = 1; var24 < w2; var24++) {
            OreSpawn.setBlockSuperFast(world, x - var24, y + h3, z, Blocks.LOG, 0, 2, chunk);
         }

         for (int var25 = 1; var25 < w2; var25++) {
            OreSpawn.setBlockSuperFast(world, x, y + h3, z + var25, Blocks.LOG, 0, 2, chunk);
         }

         for (int var26 = 1; var26 < w2; var26++) {
            OreSpawn.setBlockSuperFast(world, x, y + h3, z - var26, Blocks.LOG, 0, 2, chunk);
         }

         for (int i = h4; i < h5; i++) {
            int width = 6;
            if (i > 8) {
               width = 5;
            }

            if (i > 10) {
               width = 4;
            }

            if (blkid != OreSpawn.MyAppleLeaves) {
               width--;
            }

            for (int var27 = -width; var27 <= width; var27++) {
               for (int k = -width; k <= width; k++) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(x + k, y + i, z + var27)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockSuperFast(world, x + k, y + i, z + var27, blkid, 0, 2, chunk);
                  }
               }
            }
         }
      }
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, net.minecraft.util.math.BlockPos pos,
         net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), hitX, hitY, hitZ)
         ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }
}

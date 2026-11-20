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

public class ItemExperienceTreeSeed extends Item {
   public ItemExperienceTreeSeed(int i) {
      this("experience_tree_seed");
   }

   public ItemExperienceTreeSeed(String name) {
      setRegistryName(name);
      setTranslationKey(name);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setMaxStackSize(64);
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10
   ) {
      if (!world.isRemote) {
         Block bid = world.getBlockState(new BlockPos(x, y, z)).getBlock();
         if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.SOUL_SAND) {
            return false;
         }

         world.setBlockState(new BlockPos(x, y + 1, z), (OreSpawn.MyExperiencePlant).getStateFromMeta(0), 2);
      } else {
         for (int j1 = 0; j1 < 10; j1++) {
            world.spawnParticle(
               net.minecraft.util.EnumParticleTypes.VILLAGER_HAPPY,
               x + world.rand.nextFloat(),
               y + 1.0 + world.rand.nextFloat(),
               z + world.rand.nextFloat(),
               0.0,
               0.0,
               0.0
            );
         }
      }

      if (!par2EntityPlayer.capabilities.isCreativeMode) {
         par1ItemStack.shrink(1);
      }

      return true;
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, net.minecraft.util.math.BlockPos pos,
         net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), hitX, hitY, hitZ)
         ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }
}

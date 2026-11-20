package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWaterBall extends Item {
   public ItemWaterBall(int i) {
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setRegistryName("water_ball");
      this.setTranslationKey("water_ball");
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (!par3EntityPlayer.capabilities.isCreativeMode) {
         par1ItemStack.shrink(1);
      }

      par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.bow"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
      if (!par2World.isRemote) {
         par2World.spawnEntity(new WaterBall(par2World, par3EntityPlayer));
      }

      return par1ItemStack;
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return new net.minecraft.util.ActionResult<>(net.minecraft.util.EnumActionResult.SUCCESS, onItemRightClick(player.getHeldItem(hand), world, player));
   }


}

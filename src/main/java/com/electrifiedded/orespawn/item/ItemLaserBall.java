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

public class ItemLaserBall extends Item {
   public ItemLaserBall(int i) {
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setRegistryName("laser_ball");
      this.setTranslationKey("laser_ball");
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (!par3EntityPlayer.capabilities.isCreativeMode) {
         par1ItemStack.shrink(1);
      }

      par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("fireworks.launch"), net.minecraft.util.SoundCategory.NEUTRAL, 3.0F, 1.0F);
      if (!par2World.isRemote) {
         par2World.spawnEntity(new LaserBall(par2World, par3EntityPlayer));
      }

      return par1ItemStack;
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return new net.minecraft.util.ActionResult<>(net.minecraft.util.EnumActionResult.SUCCESS, onItemRightClick(player.getHeldItem(hand), world, player));
   }


}

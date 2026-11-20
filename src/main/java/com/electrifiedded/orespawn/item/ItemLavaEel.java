package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemLavaEel extends ItemFood {
   public ItemLavaEel(int par1, int par2, float par3, boolean par4) {
      super(par2, par3, par4);
      this.setAlwaysEdible();
   }

   public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
      if (!par2World.isRemote) {
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.FIRE_RESISTANCE, 600, 0));
      }
   }


}

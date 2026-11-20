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

public class ItemSunFish extends ItemFood {
   public ItemSunFish(int par1, int par2, float par3, boolean par4) {
      super(par2, par3, par4);
      this.setAlwaysEdible();
   }

   public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
      if (!par2World.isRemote && this == OreSpawn.MySunFish) {
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.FIRE_RESISTANCE, 6000, 0));
      }

      if (!par2World.isRemote && this == OreSpawn.MyButterCandy) {
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.SPEED, 2000, 0));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.JUMP_BOOST, 2000, 0));
      }

      if (!par2World.isRemote && this == OreSpawn.MyBacon) {
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.REGENERATION, 2000, 0));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 2000, 0));
      }

      if (!par2World.isRemote && this == OreSpawn.MyCrystalApple) {
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.REGENERATION, 3000, 0));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 3000, 0));
      }

      if (!par2World.isRemote && this == OreSpawn.MyLove) {
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.REGENERATION, 6000, 3));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 6000, 2));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.FIRE_RESISTANCE, 6000, 2));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.RESISTANCE, 6000, 1));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.SPEED, 5000, 0));
         par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.JUMP_BOOST, 5000, 0));
      }
   }


}

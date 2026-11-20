package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class NightmareSword extends ItemSword {
   public NightmareSword(int par1, ToolMaterial par2EnumToolMaterial) {
      super(par2EnumToolMaterial);

      this.setMaxDamage(1200);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 1);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.KNOCKBACK, 3);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.FIRE_ASPECT, 1);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.KNOCKBACK, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 1);
         stack.addEnchantment(net.minecraft.init.Enchantments.KNOCKBACK, 3);
         stack.addEnchantment(net.minecraft.init.Enchantments.FIRE_ASPECT, 1);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public String getMaterialName() {
      return "Uranium/Titanium";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 5000;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class EmeraldPickaxe extends ItemPickaxe {
   private int weaponDamage = 10;

   public EmeraldPickaxe(int par1, ToolMaterial par2) {
      super(par2);

      this.setMaxDamage(1300);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.SILK_TOUCH, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.SILK_TOUCH, 1);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public int getDamageVsEntity(Entity par1Entity) {
      return this.weaponDamage;
   }

   public int getDamageVsEntity() {
      return this.weaponDamage;
   }

   public String getMaterialName() {
      return "Emerald";
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

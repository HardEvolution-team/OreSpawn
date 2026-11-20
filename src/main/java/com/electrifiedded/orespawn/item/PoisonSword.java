package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class PoisonSword extends ItemSword {
   private int weaponDamage;
   private final ToolMaterial toolMaterial;

   public PoisonSword(int par1, ToolMaterial par2EnumToolMaterial) {
      super(par2EnumToolMaterial);
      this.toolMaterial = par2EnumToolMaterial;
      this.weaponDamage = 15;

      this.setMaxDamage(1300);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 1);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.SHARPNESS, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 1);
      }
   }

   public String getMaterialName() {
      return "Emerald";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      int var2x = 5;
      if (par2EntityLiving != null) {
         var2x = 10 + par2EntityLiving.world.rand.nextInt(10);
         par2EntityLiving.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.POISON, var2x * 20, 0));
         var2x = 10 + par2EntityLiving.world.rand.nextInt(10);
         par2EntityLiving.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.WITHER, var2x * 20, 0));
         var2x = 10 + par2EntityLiving.world.rand.nextInt(10);
         par2EntityLiving.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.WEAKNESS, var2x * 20, 0));
      }

      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 3000;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }

   // 1.7 applied these enchantments while blocking with the sword; 1.12 swords cannot block,
   // so they are applied while the sword is held instead.
   @Override
   public void onUpdate(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
      super.onUpdate(stack, world, entity, slot, selected);
      if (selected && !world.isRemote) onUsingTick(stack, (EntityPlayer) null, 0);
   }
}

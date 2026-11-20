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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class Slice extends SpecialSword {
   public Slice(ToolMaterial material, String name) {
      super(material, name);

      this.setMaxDamage(2600);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 5);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.BANE_OF_ARTHROPODS, 1);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.SHARPNESS, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 5);
         stack.addEnchantment(net.minecraft.init.Enchantments.BANE_OF_ARTHROPODS, 1);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      return entity != null && (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      if (entityLiving instanceof EntityPlayer && !entityLiving.world.isRemote) {
         EntityPlayer p = (EntityPlayer)entityLiving;
         double xzoff = 2.0;
         double yoff = 1.55;
         BerthaHit lb = new BerthaHit(p.world, p);
         lb.setLocationAndAngles(
            p.posX - xzoff * Math.sin(Math.toRadians(p.renderYawOffset)),
            p.posY + yoff,
            p.posZ + xzoff * Math.cos(Math.toRadians(p.renderYawOffset)),
            p.renderYawOffset,
            p.rotationPitch
         );
         lb.motionX *= 2.0;
         lb.motionY *= 2.0;
         lb.motionZ *= 2.0;
         p.world.spawnEntity(lb);
         stack.damageItem(1, p);
      }

      return false;
   }

   public String getMaterialName() {
      return "Uranium/Titanium";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 9000;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

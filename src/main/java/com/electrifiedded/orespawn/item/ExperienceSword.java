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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import com.electrifiedded.orespawn.item.armor.ItemOreSpawnArmor;

public class ExperienceSword extends SpecialSword {
   private int weaponDamage;
   private final ToolMaterial toolMaterial;
   private World worldObj = null;
   private World worldObjr = null;

   public ExperienceSword(ToolMaterial par2EnumToolMaterial, String name) {
      super(par2EnumToolMaterial, name);
      this.toolMaterial = par2EnumToolMaterial;
      this.weaponDamage = 15;

      this.setMaxDamage(1400);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 2);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 3);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.SHARPNESS, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 2);
         stack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 3);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      EntityLivingBase e = null;
      ItemOreSpawnArmor ia = null;
      EntityPlayer p = null;
      this.onUsingTick(stack, (EntityPlayer)null, 0);
      if (this.worldObj == null && !par2World.isRemote) {
         this.worldObj = par2World;
      }

      if (this.worldObjr == null && par2World.isRemote) {
         this.worldObjr = par2World;
      }

      if (par2World.rand.nextInt(60) == 1 && par3Entity instanceof EntityPlayer) {
         e = (EntityLivingBase)par3Entity;
         p = (EntityPlayer)e;

          for (EntityEquipmentSlot slot : new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET}) {
             ItemStack is = p.getItemStackFromSlot(slot);
             if (!is.isEmpty()) {
               Item it = is.getItem();
               if (it != null && it instanceof ItemOreSpawnArmor) {
                  ia = (ItemOreSpawnArmor)it;
                   if (ia.getArmorMaterialId() == 4) {
                      switch (ia.getArmorType()) {
                        case 0:
                           if (!par2World.isRemote && p != null && par2World.rand.nextInt(10) == 1) {
                              p.addExperience(1);
                           }

                           par2World.spawnParticle(
                              net.minecraft.util.EnumParticleTypes.PORTAL,
                              e.posX,
                              e.posY + 1.5,
                              e.posZ,
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian()
                           );
                           break;
                        case 1:
                           if (!par2World.isRemote && p != null && par2World.rand.nextInt(20) == 1) {
                              p.addExperience(1);
                           }

                           par2World.spawnParticle(
                              net.minecraft.util.EnumParticleTypes.PORTAL,
                              e.posX,
                              e.posY + 1.25,
                              e.posZ,
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian()
                           );
                           break;
                        case 2:
                           if (!par2World.isRemote && p != null && par2World.rand.nextInt(30) == 1) {
                              p.addExperience(1);
                           }

                           par2World.spawnParticle(
                              net.minecraft.util.EnumParticleTypes.PORTAL,
                              e.posX,
                              e.posY + 0.75,
                              e.posZ,
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian()
                           );
                           break;
                        case 3:
                           if (!par2World.isRemote && p != null && par2World.rand.nextInt(40) == 1) {
                              p.addExperience(1);
                           }

                           par2World.spawnParticle(
                              net.minecraft.util.EnumParticleTypes.PORTAL,
                              e.posX,
                              e.posY + 0.25,
                              e.posZ,
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian(),
                              par2World.rand.nextGaussian()
                           );
                     }
                  }
               }
            }
         }
      }
   }

   public int getDamageVsEntity(Entity par1Entity) {
      return this.weaponDamage;
   }

   public String getMaterialName() {
      return "Emerald";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      float i = 0.0F;
      EntityPlayer p = null;
      EntityLiving l = null;
      if (par3EntityLiving instanceof EntityPlayer) {
         p = (EntityPlayer)par3EntityLiving;
      }

      if (par2EntityLiving != null && par2EntityLiving instanceof EntityLiving) {
         i = 10.0F;
      }

      if (i > 0.0F && p != null) {
         p.addExperience((int)i);
      }

      if (p != null) {
         i = p.experienceLevel / 2;
         if (i > 0.0F && par2EntityLiving != null) {
            par2EntityLiving.attackEntityFrom(DamageSource.causePlayerDamage(p), i);
         }
      }

      if (this.worldObjr != null && par2EntityLiving != null) {
         for (int j = 0; j <= i / 2.0F; j++) {
            this.worldObjr
               .spawnParticle(
                  net.minecraft.util.EnumParticleTypes.PORTAL,
                  par2EntityLiving.posX,
                  par2EntityLiving.posY + 1.0,
                  par2EntityLiving.posZ,
                  this.worldObjr.rand.nextGaussian(),
                  this.worldObjr.rand.nextGaussian(),
                  this.worldObjr.rand.nextGaussian()
               );
         }
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
}

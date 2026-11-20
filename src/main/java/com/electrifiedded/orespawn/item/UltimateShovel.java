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
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class UltimateShovel extends ItemSpade {
   public UltimateShovel(int par1, ToolMaterial par2) {
      super(par2);

      this.setMaxDamage(3000);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.EFFICIENCY, 5);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.EFFICIENCY, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.EFFICIENCY, 5);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if (entity != null && OreSpawn.ultimate_sword_pvp == 0) {
         if (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend) {
            return true;
         }

         if (entity instanceof EntityTameable) {
            EntityTameable t = (EntityTameable)entity;
            if (t.isTamed()) {
               return true;
            }
         }
      }

      return false;
   }

   public int getDamageVsEntity(Entity par1Entity) {
      if (par1Entity instanceof Girlfriend) {
         return 1;
      } else {
         return par1Entity instanceof EntityPlayer ? 1 : 5;
      }
   }

   public String getMaterialName() {
      return "Uranium/Titanium";
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNetherLost extends Item {
   public ItemNetherLost(int par1) {

      this.setMaxDamage(3000);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 2);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.SHARPNESS, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, 2);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      EntityLivingBase e = null;
      EntityPlayer p = null;
      this.onUsingTick(stack, (EntityPlayer)null, 0);
      if (par2World != null) {
         if (par3Entity != null && par3Entity instanceof EntityLivingBase) {
            e = (EntityLivingBase)par3Entity;
            if (e instanceof EntityPlayer) {
               p = (EntityPlayer)e;
               ItemStack is = p.getHeldItemMainhand();
               if (is != null) {
                  Item it = is.getItem();
                  if (it != null && it instanceof ItemNetherLost && par2World.provider.getDimension() == -1) {
                     Block i = par2World.getBlockState(new BlockPos((int)p.posX, (int)p.posY - 1, (int)p.posZ)).getBlock();
                     if (i == Blocks.NETHERRACK) {
                        par2World.setBlockState(new BlockPos((int)p.posX, (int)p.posY - 1, (int)p.posZ), (Blocks.QUARTZ_BLOCK).getDefaultState());
                     }
                  }
               }
            }
         }
      }
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 3000;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

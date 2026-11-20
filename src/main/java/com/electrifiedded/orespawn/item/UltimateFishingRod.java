package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimateFishingRod extends Item {
   public UltimateFishingRod(int par1) {
      this.setMaxDamage(3000);
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public UltimateFishingRod(String name) {
      this(0);
      setRegistryName(name);
      setTranslationKey(name);
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      ItemStack stack = player.getHeldItem(hand);
      onItemRightClick(stack, world, player);
      return new net.minecraft.util.ActionResult<>(net.minecraft.util.EnumActionResult.SUCCESS, stack);
   }

   @Override
   public void onUpdate(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, 0);
   }

   public boolean isFull3D() {
      return true;
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 2);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.UNBREAKING, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 2);
      }
   }

   public boolean shouldRotateAroundWhenRendering() {
      return true;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (par3EntityPlayer.fishEntity != null) {
         int var4 = par3EntityPlayer.fishEntity.handleHookRetraction();
         par1ItemStack.damageItem(var4, par3EntityPlayer);
         par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
      } else {
         par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.bow"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
         if (!par2World.isRemote) {
            par2World.spawnEntity(new UltimateFishHook(par2World, par3EntityPlayer));
         }

         par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
      }

      return par1ItemStack;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

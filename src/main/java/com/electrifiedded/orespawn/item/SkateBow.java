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
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SkateBow extends Item {
   public SkateBow(int par1) {

      this.setMaxDamage(300);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
   }

   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
      int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
      boolean flag = par3EntityPlayer.capabilities.isCreativeMode
         || EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.INFINITY, par1ItemStack) > 0;
       if (flag || par3EntityPlayer.inventory.hasItemStack(new ItemStack(OreSpawn.MyIrukandjiArrow))) {
         float f = var6 / 20.0F;
         f = (f * f + f * 2.0F) / 3.0F;
         if (f < 0.1) {
            return;
         }

         if (f > 1.75F) {
            f = 1.75F;
         }

         IrukandjiArrow var8 = new IrukandjiArrow(par2World, par3EntityPlayer, f);
         if (par2World.rand.nextInt(20) == 1) {
            var8.setIsCritical(true);
         }

         int var10 = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.PUNCH, par1ItemStack);
         if (var10 > 0) {
            var8.setKnockbackStrength(var10);
         }

         if (EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FLAME, par1ItemStack) > 0) {
            var8.setFire(100);
         }

         par1ItemStack.damageItem(1, par3EntityPlayer);
         par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.bow"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
         if (!flag) {
             par3EntityPlayer.inventory.clearMatchingItems(OreSpawn.MyIrukandjiArrow, -1, 1, null);
         }

         if (!par2World.isRemote) {
            par2World.spawnEntity(var8);
         }
      }
   }

   public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      return par1ItemStack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 9000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.BOW;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setActiveHand(net.minecraft.util.EnumHand.MAIN_HAND);
      return par1ItemStack;
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      ItemStack stack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new net.minecraft.util.ActionResult<>(net.minecraft.util.EnumActionResult.SUCCESS, stack);
   }

   public int getItemEnchantability() {
      return 50;
   }

   @Override
   public void onPlayerStoppedUsing(ItemStack stack, World world, net.minecraft.entity.EntityLivingBase entity, int timeLeft) {
      if (entity instanceof EntityPlayer) {
         onPlayerStoppedUsing(stack, world, (EntityPlayer) entity, timeLeft);
      }
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

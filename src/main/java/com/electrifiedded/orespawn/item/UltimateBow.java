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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimateBow extends ItemBow {
   public UltimateBow(int par1) {

      this.setMaxDamage(1000);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public UltimateBow(String name) {
      this(0);
      setRegistryName(name);
      setTranslationKey(name);
      setMaxStackSize(1);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.POWER, 5);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.FLAME, 3);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.PUNCH, 2);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.INFINITY, 1);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.INFINITY, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.POWER, 5);
         stack.addEnchantment(net.minecraft.init.Enchantments.FLAME, 3);
         stack.addEnchantment(net.minecraft.init.Enchantments.PUNCH, 2);
         stack.addEnchantment(net.minecraft.init.Enchantments.INFINITY, 1);
      }
   }

   @Override
   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase user, int par4) {
      if (!(user instanceof EntityPlayer)) {
         return;
      }
      EntityPlayer par3EntityPlayer = (EntityPlayer)user;
      EntityArrow var8 = new UltimateArrow(par2World, par3EntityPlayer, 3.0F);
      if (par2World.rand.nextInt(4) == 1) {
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
      var8.pickupStatus = net.minecraft.entity.projectile.EntityArrow.PickupStatus.CREATIVE_ONLY;
      if (!par2World.isRemote) {
         par2World.spawnEntity(var8);
      }
   }

   public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      return par1ItemStack;
   }

   @Override
   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 9000;
   }

   @Override
   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.BOW;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setActiveHand(net.minecraft.util.EnumHand.MAIN_HAND);
      return par1ItemStack;
   }

   @Override
   public void onUsingTick(ItemStack stack, EntityLivingBase user, int count) {
      if (user instanceof EntityPlayer) {
         onUsingTick(stack, (EntityPlayer) user, count);
      }
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


}

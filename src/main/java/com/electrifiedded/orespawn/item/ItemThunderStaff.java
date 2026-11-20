package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemThunderStaff extends Item {
   private int ticker = 50;

   public ItemThunderStaff(int i) {
      this.setMaxDamage(50);
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setRegistryName("thunder_staff");
      this.setTranslationKey("thunder_staff");
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (par1ItemStack.getMaxDamage() - par1ItemStack.getMetadata() <= 1) {
         return par1ItemStack;
      } else {
         double xzoff = 1.0;
         double yoff = 1.55;
          ThunderBolt lb = new ThunderBolt(par2World, par3EntityPlayer);
          lb.shoot(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 0.0F, 1.5F, 1.0F);
         lb.setLocationAndAngles(
            par3EntityPlayer.posX - xzoff * Math.sin(Math.toRadians(par3EntityPlayer.renderYawOffset + 45.0F)),
            par3EntityPlayer.posY + yoff,
            par3EntityPlayer.posZ + xzoff * Math.cos(Math.toRadians(par3EntityPlayer.renderYawOffset + 45.0F)),
            par3EntityPlayer.renderYawOffset,
            par3EntityPlayer.rotationPitch
         );
         lb.motionX *= 3.0;
         lb.motionY *= 3.0;
         lb.motionZ *= 3.0;
         par2World.spawnEntity(lb);
         par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
         par3EntityPlayer.addVelocity(
            Math.cos(Math.toRadians(par3EntityPlayer.renderYawOffset - 90.0F)) * 0.5,
            0.15,
            Math.sin(Math.toRadians(par3EntityPlayer.renderYawOffset - 90.0F)) * 0.5
         );
         par1ItemStack.damageItem(1, par3EntityPlayer);
         return par1ItemStack;
      }
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      ItemStack stack = player.getHeldItem(hand);
      int damage = stack.getItemDamage();
      ItemStack result = onItemRightClick(stack, world, player);
      return new net.minecraft.util.ActionResult<>(result.getItemDamage() == damage ? net.minecraft.util.EnumActionResult.FAIL : net.minecraft.util.EnumActionResult.SUCCESS, result);
   }

   public void onUpdate(ItemStack par1ItemStack, World world, Entity par3Entity, int par4, boolean par5) {
      if (world.isRaining() && world.isThundering()) {
         if (this.ticker > 0) {
            this.ticker--;
         }

         if (this.ticker <= 0 && par1ItemStack.getMetadata() > 0) {
            par1ItemStack.setItemDamage(par1ItemStack.getMetadata() - 1);
            this.ticker = 50;
         }
      }
   }

   public String getMaterialName() {
      return "Unknown";
   }


}

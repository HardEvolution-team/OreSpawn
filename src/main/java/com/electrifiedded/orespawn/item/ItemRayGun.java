package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemRayGun extends Item {
   public ItemRayGun(int i) {
      this.setMaxDamage(50);
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setRegistryName("ray_gun");
      this.setTranslationKey("ray_gun");
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (par1ItemStack.getMaxDamage() - par1ItemStack.getMetadata() <= 1) {
         return par1ItemStack;
      } else {
         par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("fireworks.launch"), net.minecraft.util.SoundCategory.NEUTRAL, 3.5F, 0.5F);
         if (!par2World.isRemote) {
            Vec3d look = par3EntityPlayer.getLookVec();
            LaserBall lb = new LaserBall(par2World, par3EntityPlayer);
            lb.setSpecial();
            lb.setPosition(par3EntityPlayer.posX + look.x, par3EntityPlayer.posY + par3EntityPlayer.getEyeHeight() + look.y, par3EntityPlayer.posZ + look.z);
            lb.shoot(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 0.0F, 4.5F, 1.0F);
            par2World.spawnEntity(lb);
         }

         par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
         par3EntityPlayer.addVelocity(
            Math.cos(Math.toRadians(par3EntityPlayer.renderYawOffset - 90.0F)) * 1.5,
            0.3,
            Math.sin(Math.toRadians(par3EntityPlayer.renderYawOffset - 90.0F)) * 1.5
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

   public String getMaterialName() {
      return "Unknown";
   }


}

package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSquidZooka extends Item {
   public ItemSquidZooka(int i) {

      this.setMaxDamage(100);
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (par1ItemStack.getMaxDamage() - par1ItemStack.getMetadata() <= 1) {
         return par1ItemStack;
      } else {
         par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 0.5F);
         if (!par2World.isRemote) {
            double xzoff = 2.5;
            double yoff = 1.65;
            Entity e = spawnCreature(
               par2World,
               "Attack Squid",
               par3EntityPlayer.posX - xzoff * Math.sin(Math.toRadians(par3EntityPlayer.renderYawOffset + 15.0F)),
               par3EntityPlayer.posY + yoff,
               par3EntityPlayer.posZ + xzoff * Math.cos(Math.toRadians(par3EntityPlayer.renderYawOffset + 15.0F))
            );
            if (e instanceof AttackSquid) {
               AttackSquid a = (AttackSquid)e;
               a.setWasShot();
            }

            float f = 3.6F;
            e.motionX = -MathHelper.sin(par3EntityPlayer.rotationYaw / 180.0F * (float) Math.PI)
               * MathHelper.cos(par3EntityPlayer.rotationPitch / 180.0F * (float) Math.PI)
               * f;
            e.motionZ = MathHelper.cos(par3EntityPlayer.rotationYaw / 180.0F * (float) Math.PI)
               * MathHelper.cos(par3EntityPlayer.rotationPitch / 180.0F * (float) Math.PI)
               * f;
            e.motionY = -MathHelper.sin(par3EntityPlayer.rotationPitch / 180.0F * (float) Math.PI) * f;
            e.motionX = e.motionX + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
            e.motionY = e.motionY + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
            e.motionZ = e.motionZ + (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.05;
         }

         par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
         par3EntityPlayer.addVelocity(
            Math.cos(Math.toRadians(par3EntityPlayer.renderYawOffset - 90.0F)) * 0.45,
            0.1,
            Math.sin(Math.toRadians(par3EntityPlayer.renderYawOffset - 90.0F)) * 0.45
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

   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(par1, par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
      }

      return var8;
   }

   public String getMaterialName() {
      return "Unknown";
   }


}

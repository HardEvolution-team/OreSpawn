package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.AntRobot;
import com.electrifiedded.orespawn.entity.SpiderRobot;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWrench extends Item {
   public ItemWrench(int i) {
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setMaxDamage(100);
      this.setRegistryName("wrench");
      this.setTranslationKey("wrench");
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if (entity != null && entity instanceof SpiderRobot && entity.getControllingPassenger() == null) {
         EntityLiving e = (EntityLiving)entity;
         float h = e.getMaxHealth() - e.getHealth();
         e.setDead();
         this.dropItem(player.world, e, OreSpawn.SpiderRobotKit, 1, (int)h);

         for (int var3x = 0; var3x < 8; var3x++) {
            float f1 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            float f2 = 0.25F + player.world.rand.nextFloat() * 2.0F;
            float f3 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            f2 = 0.25F + player.world.rand.nextFloat() * 2.0F;
            f3 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            f2 = 0.25F + player.world.rand.nextFloat() * 2.0F;
            f3 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, 0.0, 0.0);
         }

         player.world.playSound(null, player.posX, player.posY, player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 1.5F);
      } else {
         if (entity == null || !(entity instanceof AntRobot) || entity.getControllingPassenger() != null) {
            return false;
         }

         AntRobot e = (AntRobot)entity;
         if (e.getOwned() == 0) {
            if (e.getHealth() / e.getMaxHealth() > 0.5F) {
               return false;
            }

            e.setOwned();
         }

         float h = e.getMaxHealth() - e.getHealth();
         e.setDead();
         this.dropItem(player.world, e, OreSpawn.AntRobotKit, 1, (int)h);

         for (int var3x = 0; var3x < 8; var3x++) {
            float f1 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            float f2 = 0.25F + player.world.rand.nextFloat() * 2.0F;
            float f3 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            f2 = 0.25F + player.world.rand.nextFloat() * 2.0F;
            f3 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            f2 = 0.25F + player.world.rand.nextFloat() * 2.0F;
            f3 = player.world.rand.nextFloat() * 3.0F - player.world.rand.nextFloat() * 3.0F;
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, 0.0, 0.0);
         }

         player.world.playSound(null, player.posX, player.posY, player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 1.5F);
      }

      stack.damageItem(2, player);
      if (stack.getCount() <= 0) {
         player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
      }

      return true;
   }

   private void dropItem(World world, EntityLiving e, Item index, int par1, int par2) {
      if (!world.isRemote) {
         ItemStack is = new ItemStack(index, par1, 0);
         is.setItemDamage(par2);
         EntityItem var3x = new EntityItem(world, e.posX, e.posY + 1.0, e.posZ, is);
         world.spawnEntity(var3x);
      }
   }


}

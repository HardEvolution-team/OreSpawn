package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemZooKeeper extends Item {
   public ItemZooKeeper(int i) {
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setMaxDamage(1);
   }
   public ItemZooKeeper(String name) { this(0); setRegistryName(name); setTranslationKey(name); setMaxStackSize(1); }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
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
      if (entity != null && entity instanceof EntityLiving) {
         EntityLiving e = (EntityLiving)entity;
         e.enablePersistence();
         stack.damageItem(2, player);
         if (stack.getCount() <= 0) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
         }

         return true;
      } else {
         return false;
      }
   }


}

package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCreeperLauncher extends Item {
   public ItemCreeperLauncher(int i) {
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setMaxDamage(1);
   }

   public ItemCreeperLauncher(String name) {
      this(0);
      setRegistryName(name);
      setTranslationKey(name);
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if (entity != null && entity instanceof EntityCreeper) {
         for (int var3x = 0; var3x < 6; var3x++) {
            float f1 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
            float f2 = 0.25F + player.world.rand.nextFloat() * 6.0F;
            float f3 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
            player.world
               .spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, f2 / 4.0F, 0.0);
            f1 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
            f2 = 0.25F + player.world.rand.nextFloat() * 6.0F;
            f3 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
            player.world
               .spawnParticle(
                  net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, f2 / 4.0F, 0.0
               );
            f1 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
            f2 = 0.25F + player.world.rand.nextFloat() * 6.0F;
            f3 = player.world.rand.nextFloat() - player.world.rand.nextFloat();
            player.world
               .spawnParticle(
                  net.minecraft.util.EnumParticleTypes.REDSTONE, (float)entity.posX + f1, (float)entity.posY + f2, (float)entity.posZ + f3, 0.0, f2 / 4.0F, 0.0
               );
         }

         player.world.playSound(null, player.posX, player.posY, player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("fireworks.launch"), net.minecraft.util.SoundCategory.NEUTRAL, 2.0F, 1.2F);
         EntityLiving e = (EntityLiving)entity;
         e.addVelocity(0.0, 4.5, 0.0);
         if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
         }

         return true;
      } else {
         return false;
      }
   }


}

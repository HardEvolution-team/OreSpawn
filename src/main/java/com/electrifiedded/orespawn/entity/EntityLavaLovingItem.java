package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityLavaLovingItem extends EntityItem {
   public EntityLavaLovingItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
      super(par1World, par2, par4, par6, par8ItemStack);
       this.ticksExisted = 300;
      this.isImmuneToFire = true;
      this.hurtResistantTime = 300;
   }

   public void noFire() {
       this.ticksExisted = 300;
      this.isImmuneToFire = true;
      this.hurtResistantTime = 300;
   }

   public void yesFire() {
       this.ticksExisted = 0;
      this.isImmuneToFire = false;
      this.hurtResistantTime = 0;
   }

   protected void dealFireDamage(float par1) {
      if (!this.isImmuneToFire) {
         this.attackEntityFrom(DamageSource.IN_FIRE, par1);
      }
   }
}

package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.MobEffects;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class InkSack extends EntityThrowable {
   private float my_rotation = 0.0F;
   private int my_index = 65;

   public InkSack(World par1World) {
      super(par1World);
   }

   public InkSack(World par1World, int par2) {
      super(par1World);
   }

   public InkSack(World par1World, EntityLiving par2EntityLiving) {
      super(par1World, par2EntityLiving);
   }

   public InkSack(World par1World, EntityLiving par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
   }

   public InkSack(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   public int getInkSackIndex() {
      return this.my_index;
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (par1MovingObjectPosition.entityHit != null) {
         float var2 = 1.0F;
         if (par1MovingObjectPosition.entityHit instanceof EntityCreeper) {
            var2 = 4.0F;
         }

         if (par1MovingObjectPosition.entityHit instanceof WaterDragon) {
            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof AttackSquid) {
            return;
         }

         par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
         if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase && this.world.rand.nextInt(2) == 0) {
            ((EntityLivingBase)par1MovingObjectPosition.entityHit)
               .addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100 + 50 * this.world.rand.nextInt(8), 0));
         }
      }

      for (int var3 = 0; var3 < 4; var3++) {
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
               this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
               this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
               this.posZ + this.rand.nextFloat(),
               0.0,
               0.0,
               0.0
            );
      }

      this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.splash"), 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
      if (!this.world.isRemote) {
         this.setDead();
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.my_rotation += 30.0F;

      while (this.my_rotation > 360.0F) {
         this.my_rotation -= 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch = this.my_rotation;
   }
}

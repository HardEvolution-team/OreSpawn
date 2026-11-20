package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ThunderBolt extends EntityThrowable {
   public ThunderBolt(World par1World) {
      super(par1World);
   }

   public ThunderBolt(World par1World, EntityLivingBase par3EntityPlayer) {
      super(par1World, par3EntityPlayer);
   }

   public ThunderBolt(World par1World, EntityLivingBase par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
   }

   public ThunderBolt(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (par1MovingObjectPosition.entityHit != null) {
         float var2 = 40.0F;
         if (MyUtils.isRoyalty(par1MovingObjectPosition.entityHit)) {
            this.setDead();
            return;
         }

         par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2 / 2.0F);
         par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), var2 / 2.0F);
         par1MovingObjectPosition.entityHit.setFire(1);
      }

      int mx = 20;

      for (int var3 = 0; var3 < mx; var3++) {
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
               this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
               this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
               this.posZ + this.rand.nextFloat(),
               0.0,
               0.0,
               0.0
            );
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_LARGE,
               this.posX + this.rand.nextFloat() - this.rand.nextFloat(),
               this.posY + this.rand.nextFloat() - this.rand.nextFloat(),
               this.posZ + this.rand.nextFloat() - this.rand.nextFloat(),
               0.0,
               0.0,
               0.0
            );
         this.world
            .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
               this.posX,
               this.posY,
               this.posZ,
               this.world.rand.nextGaussian(),
               this.world.rand.nextGaussian(),
               this.world.rand.nextGaussian()
            );
      }

      this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
      if (!this.world.isRemote) {
         this.world
            .createExplosion(this, this.posX, this.posY, this.posZ, 3.0F, this.world.getGameRules().getBoolean("mobGriefing"));
      }

      this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX, this.posY + 1.0, this.posZ, false));
      this.setDead();
   }

   public void onUpdate() {
      super.onUpdate();
      int mx = 4;

      for (int i = 0; i < mx; i++) {
         this.world
            .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
               this.posX,
               this.posY,
               this.posZ,
               this.world.rand.nextGaussian() / 10.0,
               this.world.rand.nextGaussian() / 10.0,
               this.world.rand.nextGaussian() / 10.0
            );
      }
   }
}

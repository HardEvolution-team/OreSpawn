package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class LaserBall extends EntityThrowable {
   private float my_rotation = 0.0F;
   private int my_index = 81;
   private int is_special = 0;
   private int is_iceball = 0;
   private int is_acid = 0;
   private int is_irukandji = 0;
   private int ticksalive = 0;

   public LaserBall(World par1World) {
      super(par1World);
   }

   public LaserBall(World par1World, int par2) {
      super(par1World);
   }

   public LaserBall(World par1World, EntityLivingBase par2EntityLiving) {
      super(par1World, par2EntityLiving);
      this.shoot(par2EntityLiving, par2EntityLiving.rotationPitch, par2EntityLiving.rotationYaw, 0.0F, 1.5F, 1.0F);
   }

   public LaserBall(World par1World, EntityLivingBase par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
      this.shoot(par2EntityLiving, par2EntityLiving.rotationPitch, par2EntityLiving.rotationYaw, 0.0F, 1.5F, 1.0F);
   }

   public LaserBall(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   public int getLaserBallIndex() {
      return this.my_index;
   }

   public void setSpecial() {
      this.is_special = 1;
   }

   public void setIceBall() {
      this.is_iceball = 1;
   }

   public void setAcid() {
      this.is_acid = 1;
   }

   public void setIrukandji() {
      this.is_irukandji = 1;
      this.is_acid = 1;
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (par1MovingObjectPosition.entityHit != null) {
         float var2 = 16.0F;
         if (this.is_irukandji != 0) {
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 100.0F);
            this.setDead();
            return;
         }

         if (this.is_acid != 0) {
            if (par1MovingObjectPosition.entityHit instanceof TrooperBug) {
               this.setDead();
               return;
            }

            if (par1MovingObjectPosition.entityHit instanceof SpitBug) {
               this.setDead();
               return;
            }
         }

         if (this.is_iceball == 0 && this.is_acid == 0) {
            if (par1MovingObjectPosition.entityHit instanceof Robot2) {
               this.setDead();
               return;
            }

            if (par1MovingObjectPosition.entityHit instanceof Robot3) {
               this.setDead();
               return;
            }

            if (par1MovingObjectPosition.entityHit instanceof Robot4) {
               this.setDead();
               return;
            }

            if (par1MovingObjectPosition.entityHit instanceof Robot5) {
               this.setDead();
               return;
            }

            if (par1MovingObjectPosition.entityHit instanceof GiantRobot) {
               this.setDead();
               return;
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof Dragon && this.is_acid == 0) {
            Dragon d = (Dragon)par1MovingObjectPosition.entityHit;
            if (d.getControllingPassenger() != null) {
               this.setDead();
               return;
            }

            if (d.getDragonType() != 0 && this.is_iceball != 0) {
               this.setDead();
               return;
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityPlayer && this.is_acid == 0) {
            EntityPlayer dx = (EntityPlayer)par1MovingObjectPosition.entityHit;
            if (dx.getRidingEntity() != null) {
               this.setDead();
               return;
            }
         }

         par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
         if (this.is_iceball == 0) {
            par1MovingObjectPosition.entityHit.setFire(1);
         }
      } else if (this.is_irukandji != 0 && !this.world.isRemote) {
         this.dropItem(OreSpawn.MyIrukandji, 1);
      }

      if (this.is_acid == 0) {
         int mx = 10;
         if (this.is_special != 0) {
            mx = 20;
         }

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
         if (!this.world.isRemote && (this.is_special != 0 || this.is_iceball != 0)) {
            this.world
               .createExplosion(
                  this, this.posX, this.posY, this.posZ, 3.0F, this.world.getGameRules().getBoolean("mobGriefing")
               );
         }
      }

      this.setDead();
   }

   public void onUpdate() {
      this.ticksalive++;
      if (this.ticksalive > 200) {
         this.setDead();
      } else {
         super.onUpdate();
         this.my_rotation += 50.0F;

         while (this.my_rotation > 360.0F) {
            this.my_rotation -= 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch = this.my_rotation;
         if (this.is_acid == 0) {
            int mx = 4;
            if (this.is_special != 0) {
               mx = 10;
            }

            if (this.is_iceball != 0 && this.is_special == 0) {
               mx = 2;
            }

            for (int i = 0; i < mx; i++) {
               this.world
                  .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                     this.posX,
                     this.posY,
                     this.posZ,
                     this.world.rand.nextGaussian() / 2.0,
                     this.world.rand.nextGaussian() / 2.0,
                     this.world.rand.nextGaussian() / 2.0
                  );
               if (this.is_iceball == 0) {
                  this.world
                     .spawnParticle(EnumParticleTypes.REDSTONE,
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
      }
   }
}

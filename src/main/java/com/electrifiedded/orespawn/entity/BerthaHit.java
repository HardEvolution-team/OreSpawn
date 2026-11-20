package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BerthaHit extends EntityThrowable {
   private int hit_type = 0;

   public BerthaHit(World par1World) {
      super(par1World);
   }

   public BerthaHit(World par1World, int par2) {
      super(par1World);
   }

   public BerthaHit(World par1World, EntityLivingBase par2EntityLiving) {
      super(par1World, par2EntityLiving);
      this.setSize(0.33F, 0.33F);
      this.setLocationAndAngles(
         par2EntityLiving.posX,
         par2EntityLiving.posY + par2EntityLiving.getEyeHeight(),
         par2EntityLiving.posZ,
         par2EntityLiving.rotationYaw,
         par2EntityLiving.rotationPitch
      );
      this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.posY -= 0.1;
      this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.setPosition(this.posX, this.posY, this.posZ);
      
      float f = 0.4F;
      this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
         * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
         * f;
      this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
         * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
         * f;
      this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * f;
      this.shoot(this.motionX, this.motionY, this.motionZ, 1.5F, 0.1F);
   }

   public BerthaHit(World par1World, EntityLivingBase par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
   }

   public BerthaHit(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   public void setHitType(int i) {
      this.hit_type = i;
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (!this.isDead) {
         if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null) {
            Entity e = par1MovingObjectPosition.entityHit;
            if (OreSpawn.big_bertha_pvp == 0 && e instanceof EntityPlayer || e instanceof Girlfriend || e instanceof Boyfriend) {
               this.setDead();
               return;
            }

            if (OreSpawn.big_bertha_pvp == 0 && e instanceof EntityTameable) {
               EntityTameable t = (EntityTameable)e;
               if (t.isTamed()) {
                  this.setDead();
                  return;
               }
            }

            if (this.hit_type == 0 && this.getDistanceSq(this.getThrower()) < 81.0 && e != this.getThrower()) {
               e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), OreSpawn.bertha_stats.damage);
               e.setFire(10);
               double ks = 2.25;
               double inair = 0.35;
               float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
               if (e.isDead) {
                  inair *= 2.0;
               }

               e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }

            if (this.hit_type == 2 && this.getDistanceSq(this.getThrower()) < 101.0 && e != this.getThrower()) {
               e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), OreSpawn.royal_stats.damage);
               double ks = 1.5;
               double inair = 0.25;
               float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
               if (e.isDead) {
                  inair *= 2.0;
               }

               e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }

            if (this.hit_type == 3 && this.getDistanceSq(this.getThrower()) < 64.0 && e != this.getThrower()) {
               e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), OreSpawn.hammy_stats.damage);
               double ks = 1.25;
               double inair = 0.65;
               float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
               if (e.isDead) {
                  inair *= 2.0;
               }

               e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
               if (!this.world.isRemote && this.hit_type == 3 && this.getDistanceSq(this.getThrower()) < 64.0) {
                  this.world
                     .newExplosion(
                        (Entity)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        1.5F,
                        true,
                        this.world.getGameRules().getBoolean("mobGriefing")
                     );
               }
            }
         } else if (!this.world.isRemote && this.hit_type == 3 && this.getDistanceSq(this.getThrower()) < 64.0) {
            this.world
               .newExplosion(
                  (Entity)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  2.1F,
                  true,
                  this.world.getGameRules().getBoolean("mobGriefing")
               );
         }

         this.setDead();
      }
   }

   public void onUpdate() {
      super.onUpdate();
   }
}

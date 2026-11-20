package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.MobEffects;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class SpiderDriver extends EntitySpider {
   private GenericTargetSorter TargetSorter = null;
   private int attackTime;

   public SpiderDriver(World par1World) {
      super(par1World);
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(3, new MyEntityAIWander(this, 0.65F));
      this.tasks.addTask(4, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
   }

   protected boolean canDespawn() {
      return this.isNoDespawnRequired() ? false : this.getRidingEntity() == null;
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   protected Entity findPlayerToAttack() {
      double d0 = 16.0;
      return this.world.getClosestPlayerToEntity(this, d0);
   }

   protected void updateAITasks() {
      if (!this.isDead) {
         super.updateAITasks();
         if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 0 && this.getRidingEntity() == null) {
            EntityLivingBase e = this.findSpiderRobot();
            if (e != null) {
               this.faceEntity(e, 10.0F, 10.0F);
               if (this.getDistanceSq(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F)) {
                  this.startRiding(e);
               } else {
                  this.getNavigator().tryMoveToEntityLiving(e, 0.55);
               }
            }
         }

         if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(4) == 0 && this.getRidingEntity() != null) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               this.faceEntity(e, 10.0F, 10.0F);
               if (!(this.getDistanceSq(e) < (11.0F + e.width / 2.0F) * (11.0F + e.width / 2.0F)) && this.getRidingEntity() instanceof SpiderRobot) {
                  SpiderRobot sp = (SpiderRobot)this.getRidingEntity();
                  double d1 = e.posZ - this.posZ;
                  double d2 = e.posX - this.posX;
                  double dd = Math.atan2(d1, d2);
                  sp.goThisWay(0.35 * Math.cos(dd), 0.35 * Math.sin(dd));
               }
            }
         }
      }
   }

   protected void attackEntity(Entity par1Entity, float par2) {
      if (this.attackTime <= 0
         && par2 < 2.0F
         && par1Entity.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY
         && par1Entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
         this.attackTime = 16;
         this.attackEntityAsMob(par1Entity);
         if (this.world.rand.nextInt(2) == 0) {
            ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 0));
         }
      }
   }

   public int getTotalArmorValue() {
      return this.getRidingEntity() != null ? 8 : 20;
   }

   private EntityLivingBase findSpiderRobot() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(SpiderRobot.class, this.getEntityBoundingBox().grow(25.0, 15.0, 25.0));
         Collections.sort(var5, this.TargetSorter);
         Iterator var2 = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;

         while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (var4.getControllingPassenger() == null) {
               return var4;
            }
         }

         return null;
      }
   }

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
         return false;
      } else if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof SpiderRobot) {
         return false;
      } else if (par1EntityLiving instanceof SpiderDriver) {
         return false;
      } else if (par1EntityLiving instanceof EntitySpider) {
         return false;
      } else if (par1EntityLiving instanceof EntityCaveSpider) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityPlayer) {
         EntityPlayer p = (EntityPlayer)par1EntityLiving;
         return !p.capabilities.isCreativeMode;
      } else {
         return !(this.getDistanceSq(par1EntityLiving) < 36.0);
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(35.0, 15.0, 35.0));
         Collections.sort(var5, this.TargetSorter);
         Iterator var2 = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;

         while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (this.isSuitableTarget(var4, false)) {
               return var4;
            }
         }

         return null;
      }
   }

   public boolean getCanSpawnHere() {
      SpiderRobot target = null;
      target = (SpiderRobot)this.world.findNearestEntityWithinAABB(SpiderRobot.class, this.getEntityBoundingBox().grow(24.0, 12.0, 24.0), this);
      return target != null ? true : super.getCanSpawnHere();
   }
}

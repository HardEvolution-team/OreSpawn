package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class CreepingHorror extends EntityMob {
   private GenericTargetSorter TargetSorter = null;
   private float moveSpeed = 0.25F;

   public CreepingHorror(World par1World) {
      super(par1World);
      this.setSize(0.75F, 0.5F);
      this.experienceValue = 5;
      this.isImmuneToFire = true;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));
      this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0, false));
      this.tasks.addTask(3, new MyEntityAIWanderALot(this, 10, 1.0));
      this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(5, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.TargetSorter = new GenericTargetSorter(this);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.CreepingHorror_stats.attack);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public int mygetMaxHealth() {
      return OreSpawn.CreepingHorror_stats.health;
   }

   public int getTotalArmorValue() {
      return OreSpawn.CreepingHorror_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (!this.isNoDespawnRequired()) {
         long t = this.world.getWorldTime();
         t %= 24000L;
         if (t <= 11000L) {
            if (this.world.rand.nextInt(500) == 1) {
               this.setDead();
            }
         }
      }
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("creepinghorror_living");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("creepinghorror_hit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("creepinghorror_dead");
   }

   protected float getSoundVolume() {
      return 0.65F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      int i = this.world.rand.nextInt(3);
      if (i == 0) {
         return Items.ROTTEN_FLESH;
      } else {
         return i == 1 ? Items.BONE : Items.STRING;
      }
   }

   protected void updateAITasks() {
      if (!this.isDead) {
         super.updateAITasks();
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (this.world.rand.nextInt(5) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               this.getNavigator().tryMoveToEntityLiving(e, 1.25);
               if (this.getDistanceSq(e) < 5.0 && (this.rand.nextInt(12) == 0 || this.rand.nextInt(14) == 1)) {
                  this.attackEntityAsMob(e);
               }
            }
         }
      }
   }

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof CreepingHorror) {
         return false;
      } else if (par1EntityLiving instanceof RockBase) {
         return false;
      } else if (par1EntityLiving instanceof EnderReaper) {
         return false;
      } else if (par1EntityLiving instanceof LeafMonster) {
         return false;
      } else if (par1EntityLiving instanceof Dragon) {
         return false;
      } else if (par1EntityLiving instanceof TerribleTerror) {
         return false;
      } else if (par1EntityLiving instanceof LurkingTerror) {
         return false;
      } else if (par1EntityLiving instanceof PitchBlack) {
         return false;
      } else if (par1EntityLiving instanceof Firefly) {
         return false;
      }   else {
         if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
               return false;
            }
         }

         return true;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 4.0, 16.0));
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
      if (!this.isValidLightLevel()) {
         return false;
      } else {
         return this.world.isDaytime()
            ? false
            : this.world.provider.getDimension() == OreSpawn.DimensionID6 || !(this.posY > 15.0);
      }
   }

   protected boolean canDespawn() {
      return this.isNoDespawnRequired() ? false : this.world.isDaytime();
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

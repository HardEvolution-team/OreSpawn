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

public class Cryolophosaurus extends EntityMob {
   private GenericTargetSorter TargetSorter = null;
   private float moveSpeed = 0.25F;

   public Cryolophosaurus(World par1World) {
      super(par1World);
      this.setSize(0.75F, 0.75F);
      this.experienceValue = 10;
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
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Cryolophosaurus_stats.attack);
   }

   protected void entityInit() {
      super.entityInit();
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public int mygetMaxHealth() {
      return OreSpawn.Cryolophosaurus_stats.health;
   }

   public int getTotalArmorValue() {
      return OreSpawn.Cryolophosaurus_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.rand.nextInt(6) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("cryo_living") : null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("cryo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("cryo_death");
   }

   protected float getSoundVolume() {
      return 0.75F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      int i = this.world.rand.nextInt(10);
      if (i == 0) {
         return Items.CHICKEN;
      } else if (i == 1) {
         return OreSpawn.UraniumNugget;
      } else {
         return i == 2 ? OreSpawn.TitaniumNugget : null;
      }
   }

   public void initCreature() {
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
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
      } else if (par1EntityLiving instanceof Alosaurus) {
         return false;
      } else if (par1EntityLiving instanceof TRex) {
         return false;
      } else if (par1EntityLiving instanceof Cryolophosaurus) {
         return false;
      } else if (par1EntityLiving instanceof Ghost) {
         return false;
      } else if (par1EntityLiving instanceof GhostSkelly) {
         return false;
      } else if (par1EntityLiving instanceof CaveFisher) {
         return false;
      } else if (par1EntityLiving instanceof GammaMetroid) {
         return false;
      } else if (par1EntityLiving instanceof EntityButterfly) {
         return false;
      } else if (par1EntityLiving instanceof Firefly) {
         return false;
      } else if (par1EntityLiving instanceof EntityMosquito) {
         return false;
      } else if (par1EntityLiving instanceof RockBase) {
         return false;
      } else {
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
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(9.0, 2.0, 9.0));
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
      return !this.isValidLightLevel() ? false : !this.world.isDaytime() || !(this.posY > 50.0);
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

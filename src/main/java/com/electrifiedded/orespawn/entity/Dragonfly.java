package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Dragonfly extends EntityAnimal {
   private BlockPos currentFlightTarget = null;
   private GenericTargetSorter TargetSorter = null;

   public Dragonfly(World par1World) {
      super(par1World);
      this.setSize(1.5F, 0.5F);
      this.experienceValue = 5;
      this.isImmuneToFire = false;
      this.isImmuneToFire = true;
      this.TargetSorter = new GenericTargetSorter(this);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
   }

   protected void entityInit() {
      super.entityInit();
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   protected float getSoundVolume() {
      return 0.25F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("dragonfly_living");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("dragonfly_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("dragonfly_death");
   }

   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   public int mygetMaxHealth() {
      return 10;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionY *= 0.6;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.25, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   protected void updateAITasks() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 50;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.rand.nextInt(300) != 0
            && !(this.getDistanceSq(currentFlightTarget) < 2.1F)) {
            if (this.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               EntityLivingBase e = null;
               e = this.findSomethingToAttack();
               if (e != null) {
                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                  if (this.getDistanceSq(e) < 6.0) {
                     this.attackEntityAsMob(e);
                  }
               }
            }
         } else {
            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               zdir = this.rand.nextInt(5) + 5;
               xdir = this.rand.nextInt(5) + 5;
               if (this.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(5) - 2, (int)this.posZ + zdir);
               bid = this.world
                  .getBlockState(this.currentFlightTarget).getBlock();
               if (bid == Blocks.AIR
                  && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())
                  )
                {
                  bid = Blocks.STONE;
               }
            }
         }

         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.30000000149011613;
         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.20000000149011612;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.30000000149011613;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 1.0F;
         this.rotationYaw += var8 / 4.0F;
      }
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = super.attackEntityFrom(par1DamageSource, par2);
      Entity e = par1DamageSource.getTrueSource();
      if (e != null && this.currentFlightTarget != null) {
         this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);
      }

      return ret;
   }

   public boolean getCanSpawnHere() {
      return this.posY < 50.0 ? false : this.world.isDaytime();
   }

   public void initCreature() {
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
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityAnt) {
         return true;
      } else if (par1EntityLiving instanceof EntityButterfly) {
         return true;
      } else if (par1EntityLiving instanceof Cockateil) {
         return true;
      } else if (par1EntityLiving instanceof EntityMosquito) {
         return true;
      } else {
         return par1EntityLiving instanceof Firefly ? true : par1EntityLiving instanceof EntityHorse && OreSpawn.DragonflyHorseFriendly == 0;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 6.0, 10.0));
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

   protected Item getDropItem() {
      int i = this.world.rand.nextInt(6);
      if (i == 0) {
         return Items.GOLD_NUGGET;
      } else if (i == 1) {
         return OreSpawn.UraniumNugget;
      } else {
         return i == 2 ? OreSpawn.TitaniumNugget : null;
      }
   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }
}

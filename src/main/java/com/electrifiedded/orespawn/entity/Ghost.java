package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.passive.EntityAmbientCreature;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.world.World;



public class Ghost extends EntityAmbientCreature {

   private BlockPos currentFlightTarget = null;



   public Ghost(World par1World) {

      super(par1World);

      this.setSize(0.5F, 1.5F);

      this.experienceValue = 5;

      this.noClip = true;

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);

   }



   protected void entityInit() {

      super.entityInit();

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   protected float getSoundVolume() {

      return 0.3F;

   }



   protected float getSoundPitch() {

      return 1.5F;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.world.rand.nextInt(2) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("ghost_sound") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return null;

   }



   protected SoundEvent getDeathSound() {

      return null;

   }



   public boolean canBePushed() {

      return false;

   }



   protected void collideWithEntity(Entity par1Entity) {

   }



   protected void collideWithNearbyEntities() {

   }



   public int mygetMaxHealth() {

      return 2;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onUpdate() {

      if (this.isNoDespawnRequired()) {

         this.noClip = false;

      }



      super.onUpdate();

      this.motionY *= 0.65;

   }



   protected void updateAITasks() {

      int i = 0;

      int j = 0;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.world.rand.nextInt(40) == 1

            || this.getDistanceSq(currentFlightTarget) < 2.0F) {

            EntityPlayer target = null;

            target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(16.0, 16.0, 16.0), this);

            if (target != null) {

               this.currentFlightTarget = new BlockPos((int)target.posX + this.rand.nextInt(3) - this.rand.nextInt(3),

                     (int)target.posY + 1,

                     (int)target.posZ + this.rand.nextInt(3) - this.rand.nextInt(3)

                  );

            } else {

               for (i = 0; i < 3; i++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY + i, (int)this.posZ)).getBlock();

                  if (bid == Blocks.AIR) {

                     break;

                  }

               }



               for (j = -1; j >= -3; j--) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY + j, (int)this.posZ)).getBlock();

                  if (bid != Blocks.AIR) {

                     break;

                  }

               }



               this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(10) - this.rand.nextInt(10),

                     (int)this.posY + i + j + this.rand.nextInt(4) + 1,

                     (int)this.posZ + this.rand.nextInt(10) - this.rand.nextInt(10)

                  );

            }

         }



         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;

         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;

         this.motionX = this.motionX + (Math.signum(var1) * 0.1 - this.motionX) * 0.05;

         this.motionY = this.motionY + (Math.signum(var3) * 0.7 - this.motionY) * 0.1;

         this.motionZ = this.motionZ + (Math.signum(var5) * 0.1 - this.motionZ) * 0.05;

         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;

         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);

         this.moveVertical = 0.05F;

         this.rotationYaw += var8 / 6.0F;

      }

   }



   protected boolean canTriggerWalking() {

      return false;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

   }



   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {

   }



   public boolean doesEntityNotTriggerPressurePlate() {

      return true;

   }



   public boolean getCanSpawnHere() {

      for (int k = -2; k < 2; k++) {

         for (int j = -2; j < 2; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Ghost")) {

                     return true;

                  }

               }

            }

         }

      }



      return !this.world.isDaytime();

   }



   public void initCreature() {

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      return par1DamageSource.getDamageType().equals("inWall") ? ret : super.attackEntityFrom(par1DamageSource, par2);

   }

}


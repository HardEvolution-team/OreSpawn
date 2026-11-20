package com.electrifiedded.orespawn.entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMosquito extends EntityAmbientCreature {
   private BlockPos currentFlightTarget = null;

   public EntityMosquito(World par1World) {
      super(par1World);
      this.setSize(0.2F, 0.2F);
      this.experienceValue = 5;
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
      return 0.4F;
   }

   protected float getSoundPitch() {
      return 1.5F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("mosquito");
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
      super.onUpdate();
      this.motionY *= 0.6F;
   }

   protected void updateAITasks() {
      int keep_trying = 50;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.rand.nextInt(20) == 0
            || this.getDistanceSq(currentFlightTarget) < 3.0F) {
            EntityPlayer target = null;
            if (OreSpawn.OreSpawnRand.nextInt(4) == 0) {
               target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(10.0, 6.0, 10.0), this);
               if (target != null) {
                  this.currentFlightTarget = new BlockPos((int)target.posX, (int)target.posY + 2, (int)target.posZ);
               } else {
                  for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
                     this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(6) - this.rand.nextInt(6),
                           (int)this.posY + this.rand.nextInt(6) - 2,
                           (int)this.posZ + this.rand.nextInt(6) - this.rand.nextInt(6)
                        );
                     bid = this.world
                        .getBlockState(this.currentFlightTarget).getBlock();
                  }
               }
            } else {
               for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
                  this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(6) - this.rand.nextInt(6),
                        (int)this.posY + this.rand.nextInt(6) - 2,
                        (int)this.posZ + this.rand.nextInt(6) - this.rand.nextInt(6)
                     );
                  bid = this.world
                     .getBlockState(this.currentFlightTarget).getBlock();
               }
            }
         }

         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.1F;
         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.1F;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.1F;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.3F;
         this.rotationYaw += var8;
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
      return true;
   }

   public void initCreature() {
   }
}

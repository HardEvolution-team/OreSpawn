package com.electrifiedded.orespawn.entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CliffRacer extends EntityAnimal {
   private BlockPos currentFlightTarget = null;

   public CliffRacer(World par1World) {
      super(par1World);
      this.setSize(0.75F, 0.5F);
      this.experienceValue = 5;
      this.isImmuneToFire = false;
      this.isImmuneToFire = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   protected float getSoundVolume() {
      return 0.45F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("cliffracer");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return null;
   }

   protected SoundEvent getDeathSound() {
      return null;
   }

   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   public int mygetMaxHealth() {
      return 5;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionY *= 0.6;
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)
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

         if (this.rand.nextInt(300) == 0
            || this.getDistanceSq(currentFlightTarget) < 2.1F) {
            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               zdir = this.rand.nextInt(10) + 5;
               xdir = this.rand.nextInt(10) + 5;
               if (this.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(11) - 5, (int)this.posZ + zdir);
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

         double var1 = this.currentFlightTarget.getX() + 0.4 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.4 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.4 - this.motionX) * 0.3;
         this.motionY = this.motionY + (Math.signum(var3) * 0.7 - this.motionY) * 0.2;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.4 - this.motionZ) * 0.3;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.75F;
         this.rotationYaw += var8 / 6.0F;
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

   public boolean getCanSpawnHere() {
      return !(this.posY < 50.0);
   }

   protected Item getDropItem() {
      int i = this.world.rand.nextInt(8);
      if (i == 0) {
         return Items.CHICKEN;
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

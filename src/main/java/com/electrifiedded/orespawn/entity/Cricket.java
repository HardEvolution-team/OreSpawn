package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.List;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class Cricket extends EntityAnimal {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Cricket.class, DataSerializers.BYTE);

   public double moveSpeed = 0.15F;
   private int singing = 0;
   private int jumpcount = 0;

   public Cricket(World par1World) {
      super(par1World);
      this.setSize(0.1F, 0.1F);
      this.experienceValue = 1;
      this.tasks.addTask(0, new EntityAIPanic(this, 1.4));
      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 8, 1.0));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, (byte)0);
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public int getSinging() {
      return this.dataManager.get(DW_20);
   }

   public void setSinging(int par1) {
      this.dataManager.set(DW_20,  (byte)par1);
   }

   private void jumpAround() {
      this.motionY = this.motionY + (0.55F + Math.abs(this.world.rand.nextFloat() * 0.35F));
      this.posY += 0.25;
      float f = 0.3F + Math.abs(this.world.rand.nextFloat() * 0.25F);
      float d = (float)(this.world.rand.nextFloat() * Math.PI * 2.0);
      this.motionX = this.motionX + f * Math.sin(d);
      this.motionZ = this.motionZ + f * Math.cos(d);
      this.isAirBorne = true;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.singing != 0) {
            this.singing--;
            if (this.singing <= 0) {
               this.setSinging(0);
            }
         }

         if (this.jumpcount > 0) {
            this.jumpcount--;
         }

         if (this.jumpcount == 0 && this.world.rand.nextInt(50) == 1) {
            this.jumpAround();
            this.jumpcount = 50;
         }
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public int mygetMaxHealth() {
      return 3;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      if (!this.world.isRemote) {
         if (this.world.rand.nextInt(2) == 0) {
            return null;
         }

         this.singing = 40;
         this.setSinging(this.singing);
      }

      return OreSpawnSoundEvents.get("cricket");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return null;
   }

   protected SoundEvent getDeathSound() {
      return null;
   }

   protected float getSoundVolume() {
      return 0.7F;
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
   }

   protected void dropFewItems(boolean par1, int par2) {
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

   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }

   public boolean getCanSpawnHere() {
      return this.posY < 30.0 ? false : this.findBuddies() <= 5;
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Cricket.class, this.getEntityBoundingBox().grow(20.0, 10.0, 20.0));
      return var5.size();
   }
}

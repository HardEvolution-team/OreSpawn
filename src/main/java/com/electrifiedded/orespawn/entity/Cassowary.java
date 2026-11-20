package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Cassowary extends EntityAnimal {
   private float moveSpeed = 0.25F;

   public Cassowary(World par1World) {
      super(par1World);
      this.setSize(0.5F, 1.2F);
      this.moveSpeed = 0.25F;
      this.isImmuneToFire = true;
      this.experienceValue = 5;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F));
      this.tasks.addTask(6, new MyEntityAIWander(this, 1.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public int mygetMaxHealth() {
      return 10;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Items.CHICKEN;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = 0;
      var3 = this.rand.nextInt(3);
      var3 += 2;

      for (int var4 = 0; var4 < var3; var4++) {
         this.dropItem(Items.CHICKEN, 1);
      }
   }

   protected void handleJumpWater() {
      if (this.world.rand.nextInt(200) == 1) {
         this.setRevengeTarget(null);
      }

      super.handleJumpWater();
   }

   public boolean getCanSpawnHere() {
      return this.world.isDaytime();
   }

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else {
         return !this.isNoDespawnRequired();
      }
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public Cassowary spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Cassowary(this.world);
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;
   }
}

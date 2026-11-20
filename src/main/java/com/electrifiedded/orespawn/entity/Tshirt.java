package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Tshirt extends EntityAnimal {
   private float moveSpeed = 0.0F;

   public Tshirt(World par1World) {
      super(par1World);
      this.setSize(4.0F, 4.0F);
      this.experienceValue = 40;
      this.isImmuneToFire = true;
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
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public void onUpdate() {
      super.onUpdate();
   }

   public int mygetMaxHealth() {
      return 1;
   }

   public int getTotalArmorValue() {
      return 0;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return null;
   }

   protected SoundEvent getDeathSound() {
      return null;
   }

   protected float getSoundVolume() {
      return 1.0F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      return Items.EMERALD;
   }

   public void initCreature() {
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
   }

   public boolean getCanSpawnHere() {
      if (!this.world.isDaytime()) {
         return false;
      } else if (this.posY < 50.0) {
         return false;
      } else {
         Tshirt target = null;
         target = (Tshirt)this.world.findNearestEntityWithinAABB(Tshirt.class, this.getEntityBoundingBox().grow(20.0, 8.0, 20.0), this);
         return target == null;
      }
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return null;
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}

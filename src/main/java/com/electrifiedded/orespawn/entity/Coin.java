package com.electrifiedded.orespawn.entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Coin extends EntityAnimal {
   private float moveSpeed = 0.0F;

   public Coin(World par1World) {
      super(par1World);
      this.setSize(1.5F, 1.5F);
      this.experienceValue = 10;
      this.isImmuneToFire = true;
      this.tasks.addTask(0, new EntityAILookIdle(this));
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
      return null;
   }

   private void dropItemRand(Item index, int par1) {
      EntityItem var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         this.posY + 1.0,
         this.posZ + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         new ItemStack(index, par1, 0)
      );
      this.world.spawnEntity(var3);
   }

   protected void dropFewItems(boolean par1, int par2) {
      int i = this.world.rand.nextInt(10);
      Item j = OreSpawn.MyEmeraldSword;
      if (i == 0) {
         j = Items.DIAMOND;
      }

      if (i == 1) {
         j = OreSpawn.UraniumNugget;
      }

      if (i == 2) {
         j = OreSpawn.TitaniumNugget;
      }

      if (i == 3) {
         j = Items.EMERALD;
      }

      if (i == 4) {
         j = OreSpawn.MyEmeraldAxe;
      }

      if (i == 5) {
         j = OreSpawn.MyEmeraldShovel;
      }

      if (i == 6) {
         j = OreSpawn.MyEmeraldPickaxe;
      }

      if (i == 7) {
         j = OreSpawn.MyEmeraldHoe;
      }

      if (i == 8) {
         j = OreSpawn.CoinEgg;
      }

      this.dropItemRand(j, 1);
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
         Coin target = null;
         target = (Coin)this.world.findNearestEntityWithinAABB(Coin.class, this.getEntityBoundingBox().grow(20.0, 8.0, 20.0), this);
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

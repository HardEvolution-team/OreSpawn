package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.init.MobEffects;

import net.minecraft.util.SoundEvent;



import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.EntityAgeable;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIAvoidEntity;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMate;

import net.minecraft.entity.ai.EntityAIMoveIndoors;

import net.minecraft.entity.ai.EntityAIPanic;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.passive.EntityAnimal;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.potion.Potion;

import net.minecraft.potion.PotionEffect;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.AxisAlignedBB;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class StinkBug extends EntityAnimal {

   private float moveSpeed = 0.15F;



   public StinkBug(World par1World) {

      super(par1World);

      this.setSize(0.55F, 0.55F);

      this.isImmuneToFire = true;

      this.experienceValue = 2;

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMate(this, 1.0));

      this.tasks.addTask(4, new EntityAIPanic(this, 1.5));

      this.tasks.addTask(5, new EntityAIAvoidEntity(this, EntityPlayer.class, 4.0F, 1.0, 1.4F));

      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));

      this.tasks.addTask(8, new MyEntityAIWanderALot(this, 10, 1.0));

      this.tasks.addTask(9, new EntityAILookIdle(this));

      this.tasks.addTask(10, new EntityAIMoveIndoors(this));

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



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   protected void handleJumpWater() {

      if (!this.isDead) {

         if (this.world.rand.nextInt(200) == 1) {

            this.setRevengeTarget(null);

         }



         super.handleJumpWater();

      }

   }



   public boolean ableToCauseSkullDrop() {

      return true;

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (this.isDead) {

         return false;

      } else {

         ret = super.attackEntityFrom(par1DamageSource, par2);

         if (this.getHealth() <= 0.0F || this.isDead) {

            AxisAlignedBB bb = new AxisAlignedBB(

               this.posX - 8.0,

               this.posY - 5.0,

               this.posZ - 8.0,

               this.posX + 8.0,

               this.posY + 10.0,

               this.posZ + 8.0

            );

            List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, bb);

            Iterator var2x = var5.iterator();

            EntityLivingBase var3 = null;



            while (var2x.hasNext()) {

               var3 = (EntityLivingBase)var2x.next();

               if (var3 != null) {

                  var3.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 0));

               }

            }

         }



         return ret;

      }

   }



   public boolean canBreatheUnderwater() {

      return false;

   }



   public int mygetMaxHealth() {

      return 5;

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

      return OreSpawnSoundEvents.get("fart");

   }



   protected float getSoundVolume() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return OreSpawn.MyDeadStinkBug;

   }



   public boolean getCanSpawnHere() {

      for (int k = -3; k < 3; k++) {

         for (int j = -3; j < 3; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Stink Bug")) {

                     return true;

                  }

               }

            }

         }

      }



      return !(this.posY < 50.0);

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



   public StinkBug spawnBabyAnimal(EntityAgeable par1EntityAgeable) {

      return new StinkBug(this.world);

   }



   public boolean isWheat(ItemStack par1ItemStack) {

      return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;

   }



   public boolean isBreedingItem(ItemStack par1ItemStack) {

      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;

   }

}



package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.math.BlockPos;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAIPanic;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.world.World;



public class LeafMonster extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(LeafMonster.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.25F;



   public LeafMonster(World par1World) {

      super(par1World);

      this.setSize(1.0F, 2.5F);

      this.experienceValue = 5;

      this.isImmuneToFire = true;


      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.LeafMonster_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

   }



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

   }



   public int mygetMaxHealth() {

      return OreSpawn.LeafMonster_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.LeafMonster_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

      float i = MathHelper.ceil(par1 - 3.0F);

      if (i > 0.0F) {

         if (i > 2.0F) {

            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.big_fall"), 1.0F, 1.0F);

            i = 2.0F;

         } else {

            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.small_fall"), 1.0F, 1.0F);

         }



         this.attackEntityFrom(DamageSource.FALL, i);

      }

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

      if (this.getAttacking() == 0) {

         int px = (int)this.posX;

         int py = (int)this.posY;

         int pz = (int)this.posZ;

         this.posX = px;

         this.posY = py;

         this.posZ = pz;

         if (this.posX > 0.0) {

            this.posX += 0.5;

         }



         if (this.posZ > 0.0) {

            this.posZ += 0.5;

         }



         if (this.posX < 0.0) {

            this.posX -= 0.5;

         }



         if (this.posZ < 0.0) {

            this.posZ -= 0.5;

         }



         this.rotationPitch = 0.0F;

         px = (int)this.rotationYawHead;

         px /= 90;

         this.rotationYaw = this.rotationYawHead = px * 90;

      }

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("leaves_hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("leaves_death");

   }



   protected float getSoundVolume() {

      return 0.65F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      int i = this.world.rand.nextInt(3);

      if (i == 0) {

         return Item.getItemFromBlock(Blocks.LOG);

      } else {

         return i == 1 ? Item.getItemFromBlock(Blocks.LEAVES) : Items.ROTTEN_FLESH;

      }

   }



   protected void updateAITasks() {

      super.updateAITasks();

      if (!this.isDead) {

         if (this.world.rand.nextInt(100) == 1) {

            this.setRevengeTarget(null);

         }



         if (this.world.rand.nextInt(4) == 1) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               this.setAttacking(1);

               this.getNavigator().tryMoveToEntityLiving(e, 1.25);

               if (this.getDistanceSq(e) < 5.0 && (this.rand.nextInt(8) == 0 || this.rand.nextInt(10) == 1)) {

                  this.attackEntityAsMob(e);

               }

            } else {

               this.setAttacking(0);

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

      } else if (par1EntityLiving instanceof EntityAnt) {

         return true;

      } else if (par1EntityLiving instanceof EntityButterfly) {

         return true;

      } else if (par1EntityLiving instanceof EntityLunaMoth) {

         return true;

      } else {

         if (par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            if (!p.capabilities.isCreativeMode) {

               return true;

            }

         }



         return false;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(4.0, 6.0, 4.0));

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

      for (int k = -3; k < 3; k++) {

         for (int j = -3; j < 3; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Leaf Monster")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else {

         if (this.world.provider.getDimension() == OreSpawn.DimensionID4) {

            if (this.posY > 20.0) {

               return false;

            }

         } else if (this.posY < 50.0) {

            return false;

         }



         return this.findBuddies() <= 4;

      }

   }



   private int findBuddies() {

      List<Entity> var5 = this.world.getEntitiesWithinAABB(LeafMonster.class, this.getEntityBoundingBox().grow(20.0, 10.0, 20.0));

      return var5.size();

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }

}



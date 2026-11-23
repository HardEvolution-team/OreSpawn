package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class Hammerhead extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Hammerhead.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.35F;

   private EntityLivingBase rt = null;



   public Hammerhead(World par1World) {

      super(par1World);

      this.setSize(3.0F, 5.0F);

      this.experienceValue = 350;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Hammerhead_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.Hammerhead_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.Hammerhead_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.rand.nextInt(3) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("hammerhead_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("alo_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("hammerhead_death");

   }



   protected float getSoundVolume() {

      return 1.2F;

   }



   protected float getSoundPitch() {

      return 0.9F;

   }



   protected Item getDropItem() {

      return Items.BEEF;

   }



   private void dropItemRand(Item index, int par1) {

      EntityItem var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         this.posY + 2.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         new ItemStack(index, par1, 0)

      );

      this.world.spawnEntity(var3);

   }



   protected void dropFewItems(boolean par1, int par2) {

      for (int var4 = 0; var4 < 8; var4++) {

         this.dropItemRand(Items.EXPERIENCE_BOTTLE, 1);

      }



      for (int var41 = 0; var41 < 10; var41++) {

         this.dropItemRand(OreSpawn.MyExperienceCatcher, 1);

      }



      for (int var5 = 0; var5 < 16; var5++) {

         this.dropItemRand(OreSpawn.CreeperLauncher, 1);

      }



      for (int var6 = 0; var6 < 4; var6++) {

         this.dropItemRand(Item.getItemFromBlock(OreSpawn.CreeperRepellent), 1);

      }



      for (int var7 = 0; var7 < 6; var7++) {

         this.dropItemRand(Items.BEEF, 1);

      }



      for (int var8 = 0; var8 < 2; var8++) {

         this.dropItemRand(OreSpawn.MyExperienceTreeSeed, 1);

      }



      if (this.world.rand.nextInt(3) == 1) {

         this.dropItemRand(OreSpawn.MyHammy, 1);

      }

   }



   public void initCreature() {

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (!super.attackEntityAsMob(par1Entity)) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            double ks = 1.1;

            double inair = 0.85;

            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);

            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {

               inair *= 2.0;

            }



            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);

         }



         return true;

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (!par1DamageSource.getDamageType().equals("cactus")) {

         ret = super.attackEntityFrom(par1DamageSource, par2);

         Entity e = par1DamageSource.getTrueSource();

         if (e != null && e instanceof EntityLivingBase) {

            this.rt = (EntityLivingBase)e;

         }

      }



      return ret;

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.world.rand.nextInt(3) == 1) {

            EntityLivingBase e = null;

            e = this.rt;

            if (OreSpawn.PlayNicely != 0) {

               e = null;

            }



            if (e != null) {

               if (e.isDead || this.world.rand.nextInt(250) == 1) {

                  e = null;

                  this.rt = null;

               }



               if (e != null && !this.getEntitySenses().canSee(e)) {

                  e = null;

               }

            }



            if (e == null) {

               e = this.findSomethingToAttack();

            }



            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < (7.0F + e.width / 2.0F) * (7.0F + e.width / 2.0F)) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(3) == 1 || this.world.rand.nextInt(4) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.25);

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

      } else if (par1EntityLiving instanceof Hammerhead) {

         return false;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else {

         return par1EntityLiving instanceof EntityMob ? true : MyUtils.isAttackableNonMob(par1EntityLiving);

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(18.0, 9.0, 18.0));

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



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

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

                  if (s != null && s.equalsIgnoreCase("Hammerhead")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.posY < 50.0) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else {

         for (int var10 = -1; var10 < 1; var10++) {

            for (int j = -1; j < 1; j++) {

               for (int ix = 1; ix < 6; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         Hammerhead target = null;

         target = (Hammerhead)this.world.findNearestEntityWithinAABB(Hammerhead.class, this.getEntityBoundingBox().grow(16.0, 8.0, 16.0), this);

         return target == null;

      }

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

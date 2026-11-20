package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;

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

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.item.Item;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class Urchin extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Urchin.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private float moveSpeed = 0.3F;

   private int was_spawnered = 0;



   public Urchin(World par1World) {

      super(par1World);

      this.setSize(1.35F, 2.1F);

      this.experienceValue = 20;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0));

      this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(3, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Urchin_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

      if (this.renderdata == null) {

         this.renderdata = new RenderInfo();

      }



      this.renderdata.rf1 = 0.0F;

      this.renderdata.rf2 = 0.0F;

      this.renderdata.rf3 = 0.0F;

      this.renderdata.rf4 = 0.0F;

      this.renderdata.ri1 = 0;

      this.renderdata.ri2 = 0;

      this.renderdata.ri3 = 0;

      this.renderdata.ri4 = 0;

   }



   protected boolean canDespawn() {

      return this.isNoDespawnRequired() ? false : this.was_spawnered == 0;

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

      if (!this.isNoDespawnRequired()) {

         if (this.was_spawnered == 0) {

            long t = this.world.getWorldTime();

            t %= 24000L;

            if (t < 12000L && this.world.rand.nextInt(400) == 1) {

               this.setDead();

            }

         }

      }

   }



   public int mygetMaxHealth() {

      return OreSpawn.Urchin_stats.health;

   }



   public RenderInfo getRenderInfo() {

      return this.renderdata;

   }



   public void setRenderInfo(RenderInfo r) {

      this.renderdata.rf1 = r.rf1;

      this.renderdata.rf2 = r.rf2;

      this.renderdata.rf3 = r.rf3;

      this.renderdata.rf4 = r.rf4;

      this.renderdata.ri1 = r.ri1;

      this.renderdata.ri2 = r.ri2;

      this.renderdata.ri3 = r.ri3;

      this.renderdata.ri4 = r.ri4;

   }



   public int getTotalArmorValue() {

      return OreSpawn.Urchin_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

      if (this.world.rand.nextInt(3) == 1) {

         this.world

            .spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY + 0.75, this.posZ, 0.0, this.world.rand.nextFloat() / 10.0F, 0.0

            );

         if (this.isInWater() && this.world.rand.nextInt(5) == 1) {

            this.attackEntityAsMob(this);

            this.world

               .spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 1.75, this.posZ, 0.0, this.world.rand.nextFloat() / 10.0F, 0.0

               );

            this.world

               .spawnParticle(EnumParticleTypes.SMOKE_LARGE,

                  this.posX,

                  this.posY + 1.75,

                  this.posZ,

                  0.0,

                  this.world.rand.nextFloat() / 10.0F,

                  0.0

               );

            this.world

               .spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 2.0, this.posZ, 0.0, this.world.rand.nextFloat() / 10.0F, 0.0

               );

            this.world

               .spawnParticle(EnumParticleTypes.SMOKE_LARGE,

                  this.posX,

                  this.posY + 2.0,

                  this.posZ,

                  0.0,

                  this.world.rand.nextFloat() / 10.0F,

                  0.0

               );

         }

      }

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return OreSpawnSoundEvents.get("kyuubi_living");

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("glasshit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("glassdead");

   }



   protected float getSoundVolume() {

      return 1.1F;

   }



   protected float getSoundPitch() {

      return 1.25F;

   }



   protected Item getDropItem() {

      int i = this.world.rand.nextInt(3);

      if (i == 1) {

         return OreSpawn.MyCrystalPinkIngot;

      } else {

         return i == 2 ? OreSpawn.MyCrystalApple : null;

      }

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      par1Entity.setFire(5);

      return super.attackEntityAsMob(par1Entity);

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.world.rand.nextInt(8) == 0) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               if (this.getDistanceSq(e) < 8.0) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(7) == 0 || this.world.rand.nextInt(8) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.2);

               }

            } else {

               this.setAttacking(0);

            }

         }

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (!par1DamageSource.getDamageType().equals("cactus")) {

         ret = super.attackEntityFrom(par1DamageSource, par2);

      }



      return ret;

   }



   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {

      if (par1EntityLiving == null) {

         return false;

      } else if (par1EntityLiving == this) {

         return false;

      } else if (!par1EntityLiving.isEntityAlive()) {

         return false;

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof Vortex) {

         return false;

      } else if (par1EntityLiving instanceof Rotator) {

         return false;

      } else if (par1EntityLiving instanceof Peacock) {

         return false;

      } else if (par1EntityLiving instanceof CrystalCow) {

         return false;

      } else if (par1EntityLiving instanceof Irukandji) {

         return false;

      } else if (par1EntityLiving instanceof Skate) {

         return false;

      } else if (par1EntityLiving instanceof Whale) {

         return false;

      } else if (par1EntityLiving instanceof Flounder) {

         return false;

      } else if (par1EntityLiving instanceof Urchin) {

         return false;

      } else {

         if (par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            if (p.capabilities.isCreativeMode) {

               return false;

            }

         }



         return true;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 3.0, 16.0));

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

      int sc = 0;



      for (int k = -2; k <= 2; k++) {

         for (int j = -2; j <= 2; j++) {

            for (int i = 1; i < 4; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Crystal Urchin")) {

                     this.was_spawnered = 1;

                     return true;

                  }

               }

            }

         }

      }



      for (int var10 = -1; var10 <= 1; var10++) {

         for (int j = -1; j <= 1; j++) {

            Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + 1, (int)this.posZ + var10)).getBlock();

            if (bid == Blocks.AIR) {

               sc++;

            }

         }

      }



      if (sc < 6) {

         return false;

      } else if (!this.isValidLightLevel()) {

         return false;

      } else {

         long t = this.world.getWorldTime();

         t %= 24000L;

         return t >= 13000L;

      }

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}



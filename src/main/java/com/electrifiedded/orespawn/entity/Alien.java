package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.EnumParticleTypes;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.init.MobEffects;

import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



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

import net.minecraft.potion.Potion;

import net.minecraft.potion.PotionEffect;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class Alien extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Alien.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int hurt_timer = 0;

   private double moveSpeed = 0.65;

   private int closest = 99999;

   private int tx = 0;

   private int ty = 0;

   private int tz = 0;



   public Alien(World par1World) {

      super(par1World);

      this.setSize(1.1F, 3.25F);

      this.experienceValue = 100;

      this.isImmuneToFire = true;

      this.isImmuneToFire = false;

      this.jumpMovementFactor = 0.6F;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 10, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Alien_stats.attack);

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

      return !this.isNoDespawnRequired();

   }



   public int mygetMaxHealth() {

      return OreSpawn.Alien_stats.health;

   }



   protected void jump() {

      super.jump();

      this.motionY += 0.25;

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

      return OreSpawn.Alien_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

      if (this.world.isRemote) {

         float f = 1.7F + Math.abs(this.world.rand.nextFloat() * 0.75F);

         if (this.world.rand.nextInt(20) == 1) {

            this.world

               .spawnParticle(EnumParticleTypes.DRIP_LAVA,

                  this.posX - f * Math.sin(Math.toRadians(this.rotationYawHead)),

                  this.posY + 1.6,

                  this.posZ + f * Math.cos(Math.toRadians(this.rotationYawHead)),

                  0.0,

                  0.0,

                  0.0

               );

         }

      }

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int getAlienHealth() {

      return (int)this.getHealth();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.world.rand.nextInt(4) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("alien_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("alien_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("alien_death");

   }



   protected float getSoundVolume() {

      return 1.0F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.SPIDER_EYE;

   }



   private void dropItemRand(Item index, int par1) {

      EntityItem var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         new ItemStack(index, par1, 0)

      );

      this.world.spawnEntity(var3);

   }



   protected void dropFewItems(boolean par1, int par2) {

      int var5 = 5 + this.world.rand.nextInt(6);



      for (int var4 = 0; var4 < var5; var4++) {

         this.dropItemRand(Items.SPIDER_EYE, 1);

      }



      var5 = 5 + this.world.rand.nextInt(6);



      for (int var5x = 0; var5x < var5; var5x++) {

         this.dropItemRand(Items.FLINT, 1);

      }



      this.dropItemRand(Items.MAP, 1);

      this.dropItemRand(Items.CLOCK, 1);

      this.dropItemRand(Items.COMPASS, 1);

   }



   public void initCreature() {

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (!super.attackEntityAsMob(par1Entity)) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            int var2 = 6;

            if (this.world.getDifficulty() == EnumDifficulty.EASY) {

               var2 = 8;

               if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {

                  var2 = 10;

               } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {

                  var2 = 12;

               }

            }



            if (par1Entity instanceof EntityLivingBase && this.world.rand.nextInt(5) == 1) {

               ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 5, 0));

            }



            double ks = 1.1;

            double inair = 0.1;

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

      if (par1DamageSource.getDamageType().equals("cactus")) {

         return false;

      } else {

         if (this.hurt_timer <= 0) {

            ret = super.attackEntityFrom(par1DamageSource, par2);

         }



         Entity e = par1DamageSource.getTrueSource();

         if (e != null && e instanceof EntityLiving) {

            this.setAttackTarget((EntityLiving)e);

            this.setAttackTarget((EntityLivingBase)e);

            this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);

            ret = true;

         }



         return ret;

      }

   }



   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {

      int found = 0;



      for (int i = -dy; i <= dy; i++) {

         for (int j = -dz; j <= dz; j++) {

            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();

            if (bid == Blocks.TORCH || bid == OreSpawn.ExtremeTorch) {

               int d = dx * dx + j * j + i * i;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + dx;

                  this.ty = y + i;

                  this.tz = z + j;

                  found++;

               }

            }



            bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock();

            if (bid == Blocks.TORCH || bid == OreSpawn.ExtremeTorch) {

               int d = dx * dx + j * j + i * i;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x - dx;

                  this.ty = y + i;

                  this.tz = z + j;

                  found++;

               }

            }

         }

      }



      for (int var12 = -dx; var12 <= dx; var12++) {

         for (int j = -dz; j <= dz; j++) {

            Block bidx = this.world.getBlockState(new BlockPos(x + var12, y + dy, z + j)).getBlock();

            if (bidx == Blocks.TORCH || bidx == OreSpawn.ExtremeTorch) {

               int d = dy * dy + j * j + var12 * var12;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var12;

                  this.ty = y + dy;

                  this.tz = z + j;

                  found++;

               }

            }



            bidx = this.world.getBlockState(new BlockPos(x + var12, y - dy, z + j)).getBlock();

            if (bidx == Blocks.TORCH || bidx == OreSpawn.ExtremeTorch) {

               int d = dy * dy + j * j + var12 * var12;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var12;

                  this.ty = y - dy;

                  this.tz = z + j;

                  found++;

               }

            }

         }

      }



      for (int var13 = -dx; var13 <= dx; var13++) {

         for (int j = -dy; j <= dy; j++) {

            Block bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z + dz)).getBlock();

            if (bidxx == Blocks.TORCH || bidxx == OreSpawn.ExtremeTorch) {

               int d = dz * dz + j * j + var13 * var13;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var13;

                  this.ty = y + j;

                  this.tz = z + dz;

                  found++;

               }

            }



            bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z - dz)).getBlock();

            if (bidxx == Blocks.TORCH || bidxx == OreSpawn.ExtremeTorch) {

               int d = dz * dz + j * j + var13 * var13;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var13;

                  this.ty = y + j;

                  this.tz = z - dz;

                  found++;

               }

            }

         }

      }



      return found != 0;

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.hurt_timer > 0) {

            this.hurt_timer--;

         }



         if (this.world.rand.nextInt(8) == 0) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < 16.0) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {

                     this.attackEntityAsMob(e);

                  }

               }



               this.getNavigator().tryMoveToEntityLiving(e, 1.2);

            } else {

               this.setAttacking(0);

            }

         } else if (this.rand.nextInt(30) == 0 && OreSpawn.PlayNicely == 0) {

            this.closest = 99999;

            this.tx = this.ty = this.tz = 0;



            for (int i = 2; i < 15 && !this.scan_it((int)this.posX, (int)this.posY, (int)this.posZ, i, i, i); i++) {

               if (i >= 10) {

                  i++;

               }

            }



            if (this.closest < 99999) {

               this.getNavigator().tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);

               if (this.closest < 27 && this.world.getGameRules().getBoolean("mobGriefing")) {

                  this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getStateFromMeta(0), 2);

               }

            }

         }



         if (this.world.rand.nextInt(40) == 1 && this.getHealth() < this.mygetMaxHealth()) {

            this.heal(1.0F);

         }

      }

   }



   private boolean isSuitableTarget(EntityLivingBase var4, boolean par2) {

      if (var4 == null) {

         return false;

      } else if (var4 == this) {

         return false;

      } else if (!var4.isEntityAlive()) {

         return false;

      } else if (var4 instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)var4;

         return !p.capabilities.isCreativeMode;

      } else {

         return false;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0, 4.0, 12.0));

         Collections.sort(var5, this.TargetSorter);

         Iterator var2 = var5.iterator();

         Entity var3 = null;

         EntityLivingBase var4 = null;

         EntityLivingBase e = this.getAttackTarget();

         if (e != null && e.isEntityAlive()) {

            return e;

         } else {

            this.setAttackTarget(null);



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

                  if (s != null && s.equalsIgnoreCase("Alien")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.provider.getDimension() == OreSpawn.DimensionID4) {

         return true;

      } else if (this.posY > 50.0) {

         return false;

      } else {

         for (int var10 = -1; var10 < 2; var10++) {

            for (int j = -1; j < 2; j++) {

               for (int ix = 1; ix < 4; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         return true;

      }

   }

}



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

import net.minecraft.util.math.MathHelper;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class Robot4 extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Robot4.class, DataSerializers.BYTE);

    private static final DataParameter<Byte> DW_21 = EntityDataManager.<Byte>createKey(Robot4.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int reload_ticker = 0;

   private int was_attacked_ticker = 0;

   private float moveSpeed = 0.34F;



   public Robot4(World par1World) {

      super(par1World);

      this.setSize(2.5F, 4.0F);

      this.experienceValue = 120;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0));

      this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 0.9F, false));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Robot4_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

      this.dataManager.register(DW_21, (byte)0);

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



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.Robot4_stats.health;

   }



   public int getRobot4Health() {

      return (int)this.getHealth();

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

      return OreSpawn.Robot4_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   protected void jump() {

      this.motionY += 0.25;

      super.jump();

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

      if (this.world.isRemote) {

         if (this.rand.nextInt(3) == 1) {

            this.world

               .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,

                  this.posX - 1.25 * Math.sin(Math.toRadians(this.rotationYaw + 180.0F)),

                  this.posY + 3.0 + this.world.rand.nextFloat(),

                  this.posZ + 1.25 * Math.cos(Math.toRadians(this.rotationYaw + 180.0F)),

                  0.0,

                  this.world.rand.nextFloat() / 2.0,

                  0.0

               );

         }



         if (this.getAttacking() != 0) {

            this.world

               .spawnParticle(EnumParticleTypes.REDSTONE,

                  this.posX - 1.55 * Math.sin(Math.toRadians(this.rotationYaw + 35.0F)),

                  this.posY + 2.25 + this.world.rand.nextFloat(),

                  this.posZ + 1.55 * Math.cos(Math.toRadians(this.rotationYaw + 35.0F)),

                  0.0,

                  this.world.rand.nextFloat(),

                  0.0

               );

         }

      }

   }



   public int getAttackStrength(Entity par1Entity) {

      int var2 = 0;

      if (this.world.getDifficulty() == EnumDifficulty.EASY) {

         var2 = 15;

         if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {

            var2 = 20;

         } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {

            var2 = 25;

         }

      }



      return var2;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.rand.nextInt(4) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("robot_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("robot_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("robot_death");

   }



   protected float getSoundVolume() {

      return 1.0F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.QUARTZ;

   }



   private ItemStack dropItemRand(Item index, int par1) {

      EntityItem var3 = null;

      ItemStack is = new ItemStack(index, par1, 0);

      var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),

         is

      );

      if (var3 != null) {

         this.world.spawnEntity(var3);

      }



      return is;

   }



   protected void dropFewItems(boolean par1, int par2) {

      ItemStack is = null;

      int var5 = 5 + this.world.rand.nextInt(10);



      for (int var4 = 0; var4 < var5; var4++) {

         this.dropItemRand(OreSpawn.MyLaserBall, 4);

      }



      this.dropItemRand(OreSpawn.MyRayGun, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int i = 10 + this.world.rand.nextInt(15);



      for (int var8 = 0; var8 < i; var8++) {

         int var3 = this.world.rand.nextInt(15);

         switch (var3) {

            case 0:

               is = this.dropItemRand(Items.REDSTONE, 1);

               break;

            case 1:

               is = this.dropItemRand(Items.REPEATER, 1);

               break;

            case 2:

               is = this.dropItemRand(Items.COMPARATOR, 1);

               break;

            case 3:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 1);

               break;

            case 4:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.DISPENSER), 1);

               break;

            case 5:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.STICKY_PISTON), 1);

               break;

            case 6:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.PISTON), 1);

               break;

            case 7:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.LEVER), 1);

               break;

            case 8:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 1);

               break;

            case 9:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), 1);

         }

      }

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

         double ks = 2.0;

         double inair = 0.12;

         float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);

         if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {

            inair *= 2.0;

         }



         par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);

      }



      return super.attackEntityAsMob(par1Entity);

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.reload_ticker > 0) {

            this.reload_ticker--;

         }



         if (this.was_attacked_ticker > 0) {

            this.was_attacked_ticker--;

         }



         if (this.reload_ticker == 0 && this.world.rand.nextInt(8) == 1) {

            EntityLivingBase e = null;

            if (this.world.rand.nextInt(50) == 1) {

               this.setAttackTarget(null);

            }



            e = this.getAttackTarget();

            if (e != null && !e.isEntityAlive()) {

               this.setAttackTarget(null);

               e = null;

            }



            if (e == null) {

               e = this.findSomethingToAttack();

            }



            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < 256.0) {

                  if (this.getDistanceSq(e) < (3.0F + e.width / 2.0F) * (3.0F + e.width / 2.0F)) {

                     this.attackEntityAsMob(e);

                  } else {

                     double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);

                     double rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);

                     double pi = 3.1415926545;

                     double rdd = Math.abs(rr - rhdir) % (pi * 2.0);

                     if (rdd > pi) {

                        rdd -= pi * 2.0;

                     }



                     rdd = Math.abs(rdd);

                     if (rdd < 0.5) {

                        double yoff = 2.0;

                        double xzoff = 1.75;

                        LaserBall var2 = new LaserBall(

                           this.world,

                           e.posX - this.posX,

                           e.posY - (this.posY + yoff),

                           e.posZ - this.posZ

                        );

                        var2.setLocationAndAngles(

                           this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw + 45.0F)),

                           this.posY + yoff,

                           this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw + 45.0F)),

                           this.rotationYaw,

                           this.rotationPitch

                        );

                        double var3 = e.posX - var2.posX;

                        double var5 = e.posY - var2.posY;

                        double var7 = e.posZ - var2.posZ;

                        float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;

                        var2.shoot(var3, var5 + var9, var7, 2.0F, 4.0F);

                        if (this.getDistanceSq(e) > 65.0) {

                           var2.setSpecial();

                           this.reload_ticker = 30;

                           this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.firework_rocket.launch"), net.minecraft.util.SoundCategory.NEUTRAL, 3.5F, 0.5F);

                        } else {

                           this.reload_ticker = 10;

                           this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.firework_rocket.launch"), net.minecraft.util.SoundCategory.NEUTRAL, 2.5F, 1.0F);

                        }



                        this.world.spawnEntity(var2);

                     }



                     this.setAttacking(1);

                  }



                  this.getNavigator().tryMoveToEntityLiving(e, 0.75);

               }

            }

         }



         if (this.reload_ticker <= 0 && this.was_attacked_ticker <= 0) {

            this.setAttacking(0);

         }

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (!par1DamageSource.getDamageType().equals("cactus")) {

         if (this.getShielding() == 0 && this.was_attacked_ticker == 0) {

            this.was_attacked_ticker = 65;

            this.setAttacking(1);

            ret = super.attackEntityFrom(par1DamageSource, par2);

            Entity e = par1DamageSource.getTrueSource();

            if (e != null && e instanceof EntityLiving) {

               this.setAttackTarget((EntityLiving)e);

               this.setAttackTarget((EntityLivingBase)e);

               this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);

               ret = true;

            }



            return ret;

         } else {

            return false;

         }

      } else {

         return false;

      }

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

      } else if (par1EntityLiving instanceof EntityMob) {

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

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 4.0, 16.0));

         Collections.sort(var5, this.TargetSorter);



         for (Entity var3 : var5) {

            EntityLivingBase var4 = (EntityLivingBase)var3;

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



   public final int getShielding() {

      return this.dataManager.get(DW_21);

   }



   public final void setShielding(int par1) {

      this.dataManager.set(DW_21,  (byte)par1);

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

                  if (s != null && s.equalsIgnoreCase("Robo-Warrior")) {

                     return true;

                  }

               }

            }

         }

      }



      if (this.posY < 50.0) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else {

         for (int var10 = -1; var10 < 1; var10++) {

            for (int j = -1; j <= 1; j++) {

               for (int ix = 1; ix < 6; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR && bid != Blocks.TALLGRASS) {

                     return false;

                  }

               }

            }

         }



         return this.isValidLightLevel();

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

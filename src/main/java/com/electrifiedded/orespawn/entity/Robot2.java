package com.electrifiedded.orespawn.entity;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



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

import net.minecraft.world.World;



public class Robot2 extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Robot2.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int just_for_fun = 0;

   private float moveSpeed = 0.3F;



   public Robot2(World par1World) {

      super(par1World);

      this.setSize(3.0F, 6.2F);

      this.experienceValue = 100;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 14, 1.0));

      this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 0.9F, false));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Robot2_stats.attack);

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



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.Robot2_stats.health;

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

      return OreSpawn.Robot2_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   protected void jump() {

      this.motionY += 0.25;

      super.jump();

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

      return Item.getItemFromBlock(Blocks.IRON_BLOCK);

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

      int var5 = 2 + this.world.rand.nextInt(8);



      for (int var4 = 0; var4 < var5; var4++) {

         this.dropItemRand(Item.getItemFromBlock(Blocks.IRON_BLOCK), 1);

      }



      var5 = 5 + this.world.rand.nextInt(6);



      for (int var8 = 0; var8 < var5; var8++) {

         this.dropItemRand(Items.IRON_INGOT, 1);

      }



      int i = 5 + this.world.rand.nextInt(10);



      for (int var9 = 0; var9 < i; var9++) {

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

      return super.attackEntityAsMob(par1Entity);

   }



   protected void destroyBlock(EntityLivingBase e) {

      double x = e.posX + this.world.rand.nextFloat() - this.world.rand.nextFloat();

      double y = e.posY - 1.0;

      double z = e.posZ + this.world.rand.nextFloat() - this.world.rand.nextFloat();

      Block bid = this.world.getBlockState(new BlockPos((int)x, (int)y, (int)z)).getBlock();

      if (bid != Blocks.OBSIDIAN) {

         if (bid != Blocks.BEDROCK) {

            if (bid != Blocks.QUARTZ_BLOCK) {

               if (bid != Blocks.MOB_SPAWNER) {

                  if (bid != Blocks.REDSTONE_BLOCK) {

                     if (bid != Blocks.IRON_BLOCK) {

                        if (bid != Blocks.CHEST) {

                           if (bid != Blocks.AIR && this.world.getGameRules().getBoolean("mobGriefing")) {

                              this.world.setBlockState(new BlockPos((int)x, (int)y, (int)z), Blocks.AIR.getDefaultState());

                           }

                        }

                     }

                  }

               }

            }

         }

      }

   }



   protected void destroyNearbyBlocks() {

      for (int i = 0; i < 50; i++) {

         double x = this.posX + this.world.rand.nextFloat() * 6.5 - this.world.rand.nextFloat() * 6.5;

         double y = this.posY + 0.1 + this.world.rand.nextFloat() * 8.5;

         double z = this.posZ + this.world.rand.nextFloat() * 6.5 - this.world.rand.nextFloat() * 6.5;

         Block bid = this.world.getBlockState(new BlockPos((int)x, (int)y, (int)z)).getBlock();

         if (bid != Blocks.OBSIDIAN

            && bid != Blocks.BEDROCK

            && bid != Blocks.QUARTZ_BLOCK

            && bid != Blocks.MOB_SPAWNER

            && bid != Blocks.REDSTONE_BLOCK

            && bid != Blocks.IRON_BLOCK

            && bid != Blocks.CHEST

            && bid != Blocks.AIR

            && this.world.getGameRules().getBoolean("mobGriefing")) {

            this.world.setBlockState(new BlockPos((int)x, (int)y, (int)z), Blocks.AIR.getDefaultState());

         }

      }

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.world.rand.nextInt(6) == 1 && OreSpawn.PlayNicely == 0) {

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

               double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);

               double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);

               double pi = 3.1415926545;

               double rdd = Math.abs(rr - rhdir) % (pi * 2.0);

               if (rdd > pi) {

                  rdd -= pi * 2.0;

               }



               rdd = Math.abs(rdd);

               this.faceEntity(e, 10.0F, 10.0F);

               if (rdd < 1.25) {

                  if (this.getDistanceSq(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {

                     this.setAttacking(1);

                     if (this.world.rand.nextInt(5) == 0 || this.world.rand.nextInt(6) == 1) {

                        this.attackEntityAsMob(e);



                        for (int i = 0; i < 6; i++) {

                           this.destroyBlock(e);

                        }

                     }



                     this.destroyNearbyBlocks();

                  }

               } else {

                  this.setAttacking(0);

               }



               this.getNavigator().tryMoveToEntityLiving(e, 1.0);

            } else {

               this.setAttacking(0);

            }

         }



         if (this.getAttacking() == 0 && OreSpawn.PlayNicely == 0) {

            if (this.world.rand.nextInt(450) == 1) {

               this.just_for_fun = 50;

            }



            if (this.just_for_fun > 0) {

               this.just_for_fun--;

            }



            if (this.just_for_fun > 0) {

               this.setAttacking(1);

               if (this.world.rand.nextInt(3) == 1) {

                  this.destroyNearbyBlocks();

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

         Entity e = par1DamageSource.getTrueSource();

         if (e != null && e instanceof EntityLiving) {

            this.setAttackTarget((EntityLiving)e);

            this.setAttackTarget((EntityLivingBase)e);

            this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);

         }



         return ret;

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

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(14.0, 3.0, 14.0));

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

                  if (s != null && s.equalsIgnoreCase("Robo-Pounder")) {

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

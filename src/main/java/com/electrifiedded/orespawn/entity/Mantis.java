package com.electrifiedded.orespawn.entity;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import net.minecraft.util.math.BlockPos;

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

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.passive.EntitySquid;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;



public class Mantis extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Mantis.class, DataSerializers.BYTE);



   private BlockPos currentFlightTarget = null;

   private GenericTargetSorter TargetSorter = null;

   private int stuck_count = 0;

   private int lastX = 0;

   private int lastZ = 0;

   private Entity rt = null;



   public Mantis(World par1World) {

      super(par1World);

      this.setSize(2.5F, 3.25F);

      this.experienceValue = 100;

      this.isImmuneToFire = false;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32F);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Mantis_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

   }



   protected float getSoundVolume() {

      return 0.35F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

       return OreSpawnSoundEvents.get("beebuzz");

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("dragonfly_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("alo_death");

   }



   public boolean canBePushed() {

      return true;

   }



   protected void collideWithEntity(Entity par1Entity) {

   }



   public int mygetMaxHealth() {

      return OreSpawn.Mantis_stats.health;

   }



   protected Item getDropItem() {

      return Item.getItemFromBlock(Blocks.YELLOW_FLOWER);

   }



   private void dropItemRand(Item index, int par1) {

      EntityItem var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         new ItemStack(index, par1, 0)

      );

      this.world.spawnEntity(var3);

   }



   protected void dropFewItems(boolean par1, int par2) {

      this.dropItemRand(OreSpawn.MyMantisClaw, 1);

      this.dropItemRand(OreSpawn.MyMantisClaw, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int var4 = 2 + this.world.rand.nextInt(10);



      for (int i = 0; i < var4; i++) {

         this.dropItemRand(Items.GOLD_NUGGET, 1);

      }



      var4 = 1 + this.world.rand.nextInt(3);



      for (int var8 = 0; var8 < var4; var8++) {

         this.dropItemRand(OreSpawn.UraniumNugget, 1);

      }



      var4 = 1 + this.world.rand.nextInt(3);



      for (int var9 = 0; var9 < var4; var9++) {

         this.dropItemRand(OreSpawn.TitaniumNugget, 1);

      }



      var4 = 2 + this.world.rand.nextInt(3);



      for (int var10 = 0; var10 < var4; var10++) {

         this.dropItemRand(Items.DIAMOND, 1);

      }

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onUpdate() {

      super.onUpdate();

      this.motionY *= 0.6;

      if (this.isInWater() && this.world.rand.nextInt(20) == 1) {

         this.attackEntityAsMob(this);

      }

   }



   public boolean canSeeTarget(double pX, double pY, double pZ) {

      return this.world

            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)

         == null;

   }



   protected void updateAITasks() {

      int xdir = 1;

      int zdir = 1;

      int keep_trying = 50;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {

            this.stuck_count++;

         } else {

            this.stuck_count = 0;

            this.lastX = (int)this.posX;

            this.lastZ = (int)this.posZ;

         }



         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.stuck_count <= 50

            && this.rand.nextInt(300) != 0

            && !(this.getDistanceSq(currentFlightTarget) < 2.1F)) {

            if (this.rand.nextInt(8) == 0) {

               EntityLivingBase e = null;

               e = (EntityLivingBase)this.rt;

               if (e != null && e.isDead) {

                  e = null;

               }



               if (e == null) {

                  e = this.findSomethingToAttack();

               }



               if (e != null) {

                  this.setAttacking(1);

                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY + 1, (int)e.posZ);

                  if (this.getDistanceSq(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.setAttacking(0);

               }

            }

         } else {

            Block bid = Blocks.STONE;



            for (this.stuck_count = 0; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {

               zdir = this.rand.nextInt(9) + 4;

               xdir = this.rand.nextInt(9) + 4;

               if (this.rand.nextInt(2) == 0) {

                  zdir = -zdir;

               }



               if (this.rand.nextInt(2) == 0) {

                  xdir = -xdir;

               }



               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(6) - 3, (int)this.posZ + zdir);

               bid = this.world

                  .getBlockState(this.currentFlightTarget).getBlock();

               if (bid == Blocks.AIR

                  && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())

                  )

                {

                  bid = Blocks.STONE;

               }

            }

         }



         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;

         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;

         this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.30000000149011613;

         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.20000000149011612;

         this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.30000000149011613;

         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;

         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);

         this.moveVertical = 1.0F;

         this.rotationYaw += var8 / 4.0F;

         if (this.world.rand.nextInt(100) == 1) {

            this.heal(1.0F);

         }

      }

   }



   protected boolean canTriggerWalking() {

      return true;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

   }



   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {

   }



   public boolean doesEntityNotTriggerPressurePlate() {

      return false;

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = super.attackEntityFrom(par1DamageSource, par2);

      Entity e = par1DamageSource.getTrueSource();

      if (e != null && e instanceof EntityLivingBase && this.currentFlightTarget != null) {

         this.rt = e;

         this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);

      }



      return ret;

   }



   public boolean getCanSpawnHere() {

      for (int k = -2; k <= 2; k++) {

         for (int j = -2; j <= 2; j++) {

            for (int i = 1; i < 4; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Mantis")) {

                     return true;

                  }

               }

            }

         }

      }



      for (int var10 = -2; var10 < 2; var10++) {

         for (int j = -2; j < 2; j++) {

            for (int ix = 1; ix < 6; ix++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

               if (bid != Blocks.AIR) {

                  return false;

               }

            }

         }

      }



      if (this.world.provider.getDimension() == OreSpawn.DimensionID6 && this.world.rand.nextInt(6) != 0) {

         return false;

      } else if (this.posY < 50.0) {

         return false;

      } else if (!this.world.isDaytime()) {

         return false;

      } else {

         Mantis target = null;

         target = (Mantis)this.world.findNearestEntityWithinAABB(Mantis.class, this.getEntityBoundingBox().grow(32.0, 16.0, 32.0), this);

         return target == null;

      }

   }



   public int getTotalArmorValue() {

      return OreSpawn.Mantis_stats.defense;

   }



   public void initCreature() {

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

      } else if (par1EntityLiving.isInWater()) {

         return false;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else if (par1EntityLiving instanceof Mantis) {

         return false;

      } else if (par1EntityLiving instanceof Irukandji) {

         return false;

      } else if (par1EntityLiving instanceof Skate) {

         return false;

      } else if (par1EntityLiving instanceof Flounder) {

         return false;

      } else if (par1EntityLiving instanceof Whale) {

         return false;

      } else if (par1EntityLiving instanceof EntitySquid) {

         return false;

      } else if (par1EntityLiving instanceof WaterDragon) {

         return false;

      } else if (par1EntityLiving instanceof AttackSquid) {

         return false;

      } else if (par1EntityLiving instanceof TerribleTerror) {

         return false;

      } else if (par1EntityLiving instanceof LurkingTerror) {

         return false;

      } else if (par1EntityLiving instanceof CloudShark) {

         return false;

      } else if (par1EntityLiving instanceof Rotator) {

         return false;

      } else if (par1EntityLiving instanceof Bee) {

         return false;

      } else if (par1EntityLiving instanceof Mothra) {

         return false;

      } else if (par1EntityLiving instanceof EntityMob) {

         return true;

      } else if (par1EntityLiving instanceof EntityButterfly) {

         return true;

      } else if (par1EntityLiving instanceof Cockateil) {

         return true;

      } else if (par1EntityLiving instanceof Fairy) {

         return true;

      } else {

         if (par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            if (!p.capabilities.isCreativeMode) {

               return true;

            }

         }



         return MyUtils.isAttackableNonMob(par1EntityLiving);

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 8.0, 16.0));

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

}



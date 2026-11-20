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

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

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



public class Molenoid extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Molenoid.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.35F;



   public Molenoid(World par1World) {

      super(par1World);

      this.setSize(3.9F, 2.6F);

      this.experienceValue = 40;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Molenoid_stats.attack);

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

      return OreSpawn.Molenoid_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.Molenoid_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.rand.nextInt(3) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("molenoid_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("molenoid_hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("molenoid_death");

   }



   protected float getSoundVolume() {

      return 1.1F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.BEEF;

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

      this.dropItemRand(OreSpawn.MolenoidNose, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);



      for (int var4 = 0; var4 < 10; var4++) {

         this.dropItemRand(Items.GOLD_NUGGET, 1);

      }



      for (int var41 = 0; var41 < 6; var41++) {

         this.dropItemRand(Items.BEEF, 1);

      }

   }



   public void initCreature() {

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      return par1DamageSource.getDamageType().equals("inWall") ? false : super.attackEntityFrom(par1DamageSource, par2);

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (!super.attackEntityAsMob(par1Entity)) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            double ks = 0.8;

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



   protected void updateAITasks() {

      EntityLivingBase e = null;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.world.rand.nextInt(4) == 0) {

            e = this.findSomethingToAttack();

            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {

                  this.setAttacking(1);

                  if (!(this.getDistanceSq(e) < 16.0) || this.world.rand.nextInt(4) != 0 && this.world.rand.nextInt(5) != 1) {

                     if (OreSpawn.PlayNicely == 0) {

                        int j = 1 + this.world.rand.nextInt(4);



                        for (int k = 0; k < j; k++) {

                           double dx = e.posX;

                           double dz = e.posZ;

                           dx += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0;

                           dz += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0;



                           for (int i = 4; i > -3; i--) {

                              if (this.world.getBlockState(new BlockPos((int)dx, (int)e.posY + i + 1, (int)dz)).getBlock() == Blocks.AIR

                                 && this.world.getBlockState(new BlockPos((int)dx, (int)e.posY + i, (int)dz)).getBlock() != Blocks.AIR) {

                                 this.world.setBlockState(new BlockPos((int)dx, (int)e.posY + i + 1, (int)dz), OreSpawn.MyMoleDirtBlock.getDefaultState());

                                 break;

                              }

                           }

                        }

                     }

                  } else {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.25);

               }

            } else {

               this.setAttacking(0);

            }

         }



         if (!this.world.isRemote) {

            if (this.world.rand.nextInt(2) == 0) {

               double spd = 0.0;

               spd = this.motionX * this.motionX + this.motionZ * this.motionZ;

               spd = Math.sqrt(spd);

               if (spd > this.moveSpeed) {

                  spd = this.moveSpeed;

               }



               int odds = (int)(100.0 * spd / this.moveSpeed);

               if (odds > 0 && this.world.rand.nextInt(100) < odds && OreSpawn.PlayNicely == 0) {

                  double dx = this.posX + 6.0 * Math.sin(Math.toRadians(this.rotationYawHead));

                  double dz = this.posZ - 6.0 * Math.cos(Math.toRadians(this.rotationYawHead));

                  dx += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0;

                  dz += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0;



                  for (int ix = 4; ix > -4; ix--) {

                     if (this.world.getBlockState(new BlockPos((int)dx, (int)this.posY + ix + 1, (int)dz)).getBlock() == Blocks.AIR

                        && this.world.getBlockState(new BlockPos((int)dx, (int)this.posY + ix, (int)dz)).getBlock() != Blocks.AIR) {

                        this.world.setBlockState(new BlockPos((int)dx, (int)this.posY + ix + 1, (int)dz), OreSpawn.MyMoleDirtBlock.getDefaultState());

                        break;

                     }

                  }

               }

            }



            double dx = this.posX - 3.0 * Math.sin(Math.toRadians(this.rotationYawHead));

            double dz = this.posZ + 3.0 * Math.cos(Math.toRadians(this.rotationYawHead));

            dx += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0;

            dz += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0;

            int dir = 1;

            if (e != null) {

               if ((int)e.posY > (int)this.posY) {

                  dir = 2;

               }



               if ((int)e.posY < (int)this.posY) {

                  dir = 0;

               }

            }



            if (OreSpawn.PlayNicely == 0) {

               for (int ixx = dir; ixx < dir + 3; ixx++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)dx, (int)this.posY + ixx, (int)dz)).getBlock();

                  if ((

                        bid == Blocks.DIRT

                           || bid == Blocks.GRASS

                           || bid == Blocks.GRAVEL

                           || bid == Blocks.SAND

                           || bid == Blocks.LEAVES

                     )

                     && this.world.getGameRules().getBoolean("mobGriefing")) {

                     this.world.setBlockState(new BlockPos((int)dx, (int)this.posY + ixx, (int)dz), Blocks.AIR.getDefaultState());

                  }



                  if (bid == OreSpawn.MyMoleDirtBlock) {

                     this.world.setBlockState(new BlockPos((int)dx, (int)this.posY + ixx, (int)dz), Blocks.AIR.getDefaultState());

                  }

               }

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

      } else if (!this.MyCanSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else if (par1EntityLiving instanceof Molenoid) {

         return false;

      } else {

         return par1EntityLiving instanceof EntityMob ? true : MyUtils.isAttackableNonMob(par1EntityLiving);

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0, 6.0, 12.0));

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

                  if (s != null && s.equalsIgnoreCase("Molenoid")) {

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

               for (int ix = 1; ix < 4; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         Molenoid target = null;

         target = (Molenoid)this.world.findNearestEntityWithinAABB(Molenoid.class, this.getEntityBoundingBox().grow(16.0, 8.0, 16.0), this);

         return target == null;

      }

   }



   public boolean MyCanSee(EntityLivingBase e) {

      double xzoff = 2.0;

      int nblks = 10;

      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));

      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));

      float startx = (float)cx;

      float starty = (float)(this.posY + 1.0);

      float startz = (float)cz;

      float dx = (float)((e.posX - startx) / 10.0);

      float dy = (float)((e.posY + e.height / 2.0F - starty) / 10.0);

      float dz = (float)((e.posZ - startz) / 10.0);

      if (Math.abs(dx) > 1.0) {

         dy /= Math.abs(dx);

         dz /= Math.abs(dx);

         nblks = (int)(nblks * Math.abs(dx));

         if (dx > 1.0F) {

            dx = 1.0F;

         }



         if (dx < -1.0F) {

            dx = -1.0F;

         }

      }



      if (Math.abs(dy) > 1.0) {

         dx /= Math.abs(dy);

         dz /= Math.abs(dy);

         nblks = (int)(nblks * Math.abs(dy));

         if (dy > 1.0F) {

            dy = 1.0F;

         }



         if (dy < -1.0F) {

            dy = -1.0F;

         }

      }



      if (Math.abs(dz) > 1.0) {

         dy /= Math.abs(dz);

         dx /= Math.abs(dz);

         nblks = (int)(nblks * Math.abs(dz));

         if (dz > 1.0F) {

            dz = 1.0F;

         }



         if (dz < -1.0F) {

            dz = -1.0F;

         }

      }



      for (int i = 0; i < nblks; i++) {

         startx += dx;

         starty += dy;

         startz += dz;

         Block bid = this.world.getBlockState(new BlockPos((int)startx, (int)starty, (int)startz)).getBlock();

         if (bid != Blocks.AIR

            && bid != OreSpawn.MyMoleDirtBlock

            && bid != Blocks.DIRT

            && bid != Blocks.GRASS

            && bid != Blocks.TALLGRASS

            && bid != Blocks.SAND

            && bid != Blocks.GRAVEL) {

            return false;

         }

      }



      return true;

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}



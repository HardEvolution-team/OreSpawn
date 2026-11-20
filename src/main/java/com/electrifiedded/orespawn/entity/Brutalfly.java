package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.EnumParticleTypes;

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

import net.minecraft.entity.EntityList;

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.entity.projectile.EntitySmallFireball;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class Brutalfly extends EntityMob {

   private BlockPos currentFlightTarget = null;

   private int lastX = 0;

   private int lastZ = 0;

   private int lastY = 0;

   private int stuck_count = 0;

   private int wing_sound = 0;

   private int health_ticker = 100;

   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.35F;



   public Brutalfly(World par1World) {

      super(par1World);

      this.setSize(5.0F, 2.0F);

      this.experienceValue = 100;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Brutalfly_stats.attack);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   protected void entityInit() {

      super.entityInit();

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   public int getTotalArmorValue() {

      return OreSpawn.Brutalfly_stats.defense;

   }



   public int getBrutalflyHealth() {

      return (int)this.getHealth();

   }



   protected float getSoundVolume() {

      return 1.5F;

   }



   protected float getSoundPitch() {

      return 1.0F;

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

      return OreSpawnSoundEvents.get("random.explode");

   }



   public boolean canBePushed() {

      return true;

   }



   protected void collideWithEntity(Entity par1Entity) {

   }



   protected void collideWithNearbyEntities() {

   }



   public int mygetMaxHealth() {

      return OreSpawn.Brutalfly_stats.health;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onUpdate() {

      super.onUpdate();

      this.motionY *= 0.6;

      this.wing_sound++;

      if (this.wing_sound > 30) {

         if (!this.world.isRemote) {

             this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mothrawings"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F);

         }



         this.wing_sound = 0;

      }



      this.health_ticker--;

      if (this.health_ticker <= 0) {

         if (this.getHealth() < this.mygetMaxHealth()) {

            this.heal(1.0F);

         }



         this.health_ticker = 100;

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

      int keep_trying = 30;

      int shoot = 3;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.lastX == (int)this.posX && this.lastY == (int)this.posY && this.lastZ == (int)this.posZ) {

            this.stuck_count++;

         } else {

            this.stuck_count = 0;

            this.lastX = (int)this.posX;

            this.lastY = (int)this.posY;

            this.lastZ = (int)this.posZ;

         }



         if (this.world.getDifficulty() == EnumDifficulty.HARD) {

            shoot = 2;

         }



         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.stuck_count > 30

            || this.world.rand.nextInt(200) == 0

            || this.getDistanceSq(currentFlightTarget) < 9.0F) {

            int down = 0;

            int dist = 20;



            for (int i = -5; i <= 5; i += 5) {

               for (int j = -5; j <= 5; j += 5) {

                  for (int k = 1; k < 20; k++) {

                     Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY - k, (int)this.posZ + i)).getBlock();

                     if (bid != Blocks.AIR) {

                        if (k < dist) {

                           dist = k;

                        }

                        break;

                     }

                  }

               }

            }



            if (dist > 10) {

               down = dist - 10 + 1;

            }



            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {

               int var19 = 1;

               int var20 = 1;

               if (this.world.rand.nextInt(2) == 0) {

                  var19 = -1;

               }



               if (this.world.rand.nextInt(2) == 0) {

                  var20 = -1;

               }



               int newz = this.rand.nextInt(20) + 8;

               newz *= var20;

               int newx = this.rand.nextInt(20) + 8;

               newx *= var19;

               this.currentFlightTarget = new BlockPos((int)this.posX + newx,

                     (int)this.posY + this.world.rand.nextInt(7) - 1 - down,

                     (int)this.posZ + newz

                  );

               bid = this.world

                  .getBlockState(this.currentFlightTarget).getBlock();

               if (bid == Blocks.AIR

                  && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())

                  )

                {

                  bid = Blocks.STONE;

               }

            }



            this.stuck_count = 0;

         }



         if (this.world.rand.nextInt(6) == 0) {

            EntityPlayer target = null;

            target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(30.0, 20.0, 30.0), this);

            if (target != null) {

               if (!target.capabilities.isCreativeMode) {

                  if (this.getEntitySenses().canSee(target)) {

                     this.currentFlightTarget = new BlockPos((int)target.posX, (int)target.posY + 4, (int)target.posZ);

                     if (this.rand.nextInt(shoot) == 0) {

                        this.attackWithSomething(target);

                     }

                  }

               } else {

                  target = null;

               }

            }



            if (target == null && this.world.rand.nextInt(3) == 0) {

               EntityLivingBase e = null;

               e = this.findSomethingToAttack();

               if (e != null) {

                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY + 5, (int)e.posZ);

                  if (this.getDistanceSq(e) > 25.0) {

                     if (this.world.rand.nextInt(shoot) == 0) {

                        this.attackWithSomething(e);

                     }

                  } else {

                     this.attackEntityAsMob(e);

                  }

               }

            }

         }



         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;

         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;

         this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.30001;

         this.motionY = this.motionY + (Math.signum(var3) * 0.7 - this.motionY) * 0.20001;

         this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.30001;

         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;

         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);

         this.moveVertical = 1.0F;

         this.rotationYaw += var8 / 8.0F;

      }

   }



   protected boolean canTriggerWalking() {

      return false;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

   }



   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {

   }



   public boolean doesEntityNotTriggerPressurePlate() {

      return true;

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      Entity e = par1DamageSource.getTrueSource();

      if (e != null && e instanceof Brutalfly) {

         return false;

      } else {

         ret = super.attackEntityFrom(par1DamageSource, par2);

         if (e != null && this.currentFlightTarget != null) {

            this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY + 2, (int)e.posZ);

         }



         return ret;

      }

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

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

                  if (s != null && s.equalsIgnoreCase("Brutalfly")) {

                     return true;

                  }

               }

            }

         }

      }



      if (this.posY < 70.0) {

         return false;

      } else if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else {

         for (int var10 = -4; var10 < 4; var10++) {

            for (int j = -3; j < 3; j++) {

               for (int ix = 1; ix < 10; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         Brutalfly target = null;

         target = (Brutalfly)this.world.findNearestEntityWithinAABB(Brutalfly.class, this.getEntityBoundingBox().grow(64.0, 32.0, 64.0), this);

         return target == null;

      }

   }



   public void initCreature() {

   }



   private void dropItemRand(Item index, int par1) {

      EntityItem var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(8) - OreSpawn.OreSpawnRand.nextInt(8),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(8) - OreSpawn.OreSpawnRand.nextInt(8),

         new ItemStack(index, par1, 0)

      );

      this.world.spawnEntity(var3);

   }



   protected void dropFewItems(boolean par1, int par2) {

      for (int i = 0; i < 20; i++) {

         float var1x = (this.rand.nextFloat() - 0.5F) * 8.0F;

         float var2x = (this.rand.nextFloat() - 0.5F) * 4.0F;

         float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;

         this.world

            .spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + var1x, this.posY + 2.0 + var2x, this.posZ + var3, 0.0, 0.0, 0.0);

      }



      for (int var4 = 0; var4 < 53; var4++) {

         this.dropItemRand(Items.GOLD_NUGGET, 1);

      }



      for (int var8 = 0; var8 < 20; var8++) {

         spawnCreature(this.world, "Butterfly", this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5);

      }

   }



   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {

      Entity var8 = null;

      if (par0World == null) {

         return null;

      } else {

         var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);

         if (var8 != null) {

            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);

            par0World.spawnEntity(var8);

            ((EntityLiving)var8).playLivingSound();

         }



         return var8;

      }

   }



   private void attackWithSomething(EntityLivingBase par1) {

      double xzoff = 2.25;

      double yoff = 0.0;

      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));

      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));

      if (this.world.getDifficulty() == EnumDifficulty.EASY) {

         EntitySmallFireball sf = new EntitySmallFireball(

            this.world, this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz

         );

         sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);

         sf.setPosition(cx, this.posY + yoff, cz);

         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

         this.world.spawnEntity(sf);

      } else if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {

         if (this.world.rand.nextInt(2) == 0) {

            EntitySmallFireball sf = new EntitySmallFireball(

               this.world, this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz

            );

            sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);

            sf.setPosition(cx, this.posY + yoff, cz);

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

            this.world.spawnEntity(sf);

         } else {

            BetterFireball bf = new BetterFireball(

               this.world, this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz

            );

            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);

            bf.setPosition(cx, this.posY + yoff, cz);

            bf.setNotMe();

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.fuse"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

            this.world.spawnEntity(bf);

         }

      } else {

         BetterFireball bf = new BetterFireball(

            this.world, this, par1.posX - cx, par1.posY + 0.55 - (this.posY + yoff), par1.posZ - cz

         );

         bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);

         bf.setPosition(cx, this.posY + yoff, cz);

         bf.setNotMe();

         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.fuse"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

         this.world.spawnEntity(bf);

      }



      if (this.getHealth() < this.mygetMaxHealth()) {

         this.heal(1.0F);

      }

   }



   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {

      if (par1EntityLiving == null) {

         return false;

      } else if (par1EntityLiving == this) {

         return false;

      } else if (!par1EntityLiving.isEntityAlive()) {

         return false;

      } else if (par1EntityLiving instanceof Brutalfly) {

         return false;

      } else if (par1EntityLiving instanceof Mothra) {

         return false;

      } else if (par1EntityLiving instanceof Vortex) {

         return false;

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof EntityMob) {

         return true;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else {

         return false;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(25.0, 20.0, 25.0));

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



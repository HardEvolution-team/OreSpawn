package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Ostrich extends EntityCannonFodder {
   private float moveSpeed = 0.2F;
   private RenderInfo renderdata = new RenderInfo();
   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   private double boatYawHead;
   private double velocityX;
   private double velocityY;
   private double velocityZ;
   float deltasmooth = 0.0F;
   private int didjump = 0;

   public Ostrich(World par1World) {
      super(par1World);
      this.setSize(0.85F, 2.1F);
      this.stepHeight = 1.0F;
      this.moveSpeed = 0.38F;
      this.isImmuneToFire = true;
      this.setSitting(false);
      this.experienceValue = 10;
      this.renderdata = new RenderInfo();
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));
      this.tasks.addTask(3, new MyEntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.9F));
      this.tasks.addTask(4, new EntityAITempt(this, 1.2F, Items.APPLE, false));
      this.tasks.addTask(5, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 5.0F));
      this.tasks.addTask(8, new MyEntityAIWander(this, 1.0F));
      this.tasks.addTask(9, new EntityAILookIdle(this));
      this.tasks.addTask(10, new EntityAIMoveIndoors(this));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.setSitting(false);
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

   @Override
   public void onUpdate() {
      if (this.isTamed() && this.getOwnerId() == null && this.hasCustomName()) {
         try {
            this.setOwnerId(java.util.UUID.fromString(this.getCustomNameTag()));
            this.setCustomNameTag("");
         } catch (IllegalArgumentException ignored) {
         }
      }
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (this.isTamed() && this.getOwnerId() == null && this.hasCustomName()) {
         try {
            this.setOwnerId(java.util.UUID.fromString(this.getCustomNameTag()));
            this.setCustomNameTag("");
         } catch (IllegalArgumentException ignored) {
         }
      }
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if (!par1DamageSource.getDamageType().equals("cactus")) {
         return super.attackEntityFrom(par1DamageSource, par2);
      }

      return false;
   }

   protected void handleJumpWater() {
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (this.world.rand.nextInt(250) == 0) {
            this.heal(1.0F);
         }

         if (this.getControllingPassenger() == null) {
            super.handleJumpWater();
         }
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public int mygetMaxHealth() {
      return 25;
   }

   public int getOstrichHealth() {
      return (int)this.getHealth();
   }

   @Override
   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if (var2 != null && var2.getCount() <= 0) {
         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
         var2 = null;
      }

       if (var2 != null && var2.getItem() == Items.APPLE && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.isTamed()) {
            if (!this.world.isRemote) {
               if (this.rand.nextInt(2) == 0) {
                   this.setTamed(true);
                   this.setOwnerId(par1EntityPlayer.getUniqueID());
                  this.playTameEffect(true);
                  this.world.setEntityState(this, (byte)7);
                  this.heal(this.mygetMaxHealth() - this.getHealth());
               } else {
                  this.playTameEffect(false);
                  this.world.setEntityState(this, (byte)6);
               }
            }
         } else if (this.isOwner(par1EntityPlayer)) {
            if (this.world.isRemote) {
               this.playTameEffect(true);
               this.world.setEntityState(this, (byte)7);
            }

            if (this.mygetMaxHealth() > this.getHealth()) {
               this.heal(this.mygetMaxHealth() - this.getHealth());
            }
         }

         if (!par1EntityPlayer.capabilities.isCreativeMode) {
            var2.shrink(1);
            if (var2.getCount() <= 0) {
               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            }
         }

         return true;
      } else if (this.isTamed()
         && var2 != null
         && var2.getItem() == Item.getItemFromBlock(Blocks.DEADBUSH)
         && par1EntityPlayer.getDistanceSq(this) < 16.0
         && this.isOwner(par1EntityPlayer)) {
         if (!this.world.isRemote) {
            this.setTamed(false);
            this.setCustomNameTag("");
            this.playTameEffect(false);
            this.world.setEntityState(this, (byte)6);
         }

         if (!par1EntityPlayer.capabilities.isCreativeMode) {
            var2.shrink(1);
            if (var2.getCount() <= 0) {
               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            }
         }

         return true;
       } else if (this.isTamed()
          && var2 != null
          && var2.getItem() == Items.NAME_TAG
          && par1EntityPlayer.getDistanceSq(this) < 16.0
          && this.isOwner(par1EntityPlayer)) {
          this.setCustomNameTag(var2.getDisplayName());
          if (!par1EntityPlayer.capabilities.isCreativeMode) {
             var2.shrink(1);
             if (var2.getCount() <= 0) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
             }
          }

          return true;
       } else if (var2 != null && this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.world.isRemote) {
            if (!this.isSitting()) {
               Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - 1, (int)this.posZ)).getBlock();
               if (bid == Blocks.SAND
                  || bid == Blocks.GRAVEL
                  || bid == Blocks.DIRT
                  || bid == Blocks.FARMLAND
                  || bid == Blocks.GRASS) {
                  this.setSitting(true);
               }
            } else {
               this.setSitting(false);
            }
         }

         return true;
        } else if (var2 == null && this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 16.0) {
          par1EntityPlayer.startRiding(this);
          this.setSitting(false);

         return true;
      } else {
         return false;
      }
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.isSitting() ? null : null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("cryo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("cryo_death");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Items.FEATHER;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = 0;
      if (this.isTamed()) {
         var3 = this.rand.nextInt(5);
         var3 += 2;

         for (int var4 = 0; var4 < var3; var4++) {
            this.dropItem(Item.getItemFromBlock(Blocks.RED_FLOWER), 1);
         }
      } else {
         super.dropFewItems(par1, par2);
      }
   }

   protected float getSoundPitch() {
      return this.isChild()
         ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.5F
         : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F;
   }

   public boolean shouldRiderSit() {
      return true;
   }

   @Override
   public boolean canPassengerSteer() {
      return this.getControllingPassenger() instanceof EntityPlayer;
   }

   @Override
   public Entity getControllingPassenger() {
      for (Entity passenger : this.getPassengers()) {
         if (passenger instanceof EntityPlayer) return passenger;
      }
      return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
   }

   @Override
   protected boolean canFitPassenger(Entity passenger) {
      if (!(passenger instanceof EntityPlayer)) return false;
      for (Entity current : this.getPassengers()) if (current instanceof EntityPlayer) return false;
      return true;
   }

   @Override
   public void fall(float distance, float damageMultiplier) {
   }

   public int getTrackingRange() {
      return 128;
   }

   public int getUpdateFrequency() {
      return 10;
   }

   public boolean sendsVelocityUpdates() {
      return true;
   }

   protected void jump() {
      this.motionY += 0.25;
      super.jump();
   }

   public double getMountedYOffset() {
      return 1.4;
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else if (!this.world.isDaytime()) {
         return false;
      } else if (this.world.rand.nextInt(4) != 1) {
         return false;
      } else {
         Ostrich target = null;
         target = (Ostrich)this.world.findNearestEntityWithinAABB(Ostrich.class, this.getEntityBoundingBox().grow(16.0, 6.0, 16.0), this);
         return target == null;
      }
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
      this.boatPosRotationIncrements = 10;
      this.boatX = par1;
      this.boatY = par3;
      this.boatZ = par5;
      this.boatYaw = par7;
      this.boatPitch = par8;
      this.boatYawHead = par9;
      this.motionX = this.velocityX;
      this.motionY = this.velocityY;
      this.motionZ = this.velocityZ;
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double par1, double par3, double par5) {
      this.velocityX = this.motionX = par1;
      this.velocityY = this.motionY = par3;
      this.velocityZ = this.motionZ = par5;
   }

   public void onLivingUpdate() {
      if (this.getControllingPassenger() instanceof EntityPlayer) {
         EntityPlayer rider = (EntityPlayer)this.getControllingPassenger();
         float forward = com.electrifiedded.orespawn.network.RiderInputState.getForward(rider);
         if (Math.abs(forward) < 0.001F) forward = rider.moveForward;
         this.rotationYaw = rider.rotationYaw;
         this.renderYawOffset = this.rotationYaw;
         this.rotationYawHead = this.rotationYaw;
         double speed = forward >= 0.0F ? 0.55 : 0.25;
         this.motionX = -Math.sin(Math.toRadians(this.rotationYaw)) * speed * forward;
         this.motionZ = Math.cos(Math.toRadians(this.rotationYaw)) * speed * forward;
         if (com.electrifiedded.orespawn.network.RiderInputState.isFlyingUp(rider) && this.onGround) this.motionY = 0.65;
         else if (!this.onGround) this.motionY -= 0.08;
         this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         this.motionY *= 0.98;
         this.setPosition(this.posX, this.posY, this.posZ);
         return;
      }

      if (this.getControllingPassenger() == null) {
         super.onLivingUpdate();
         return;
      }

      List list = null;
      Entity listEntity = null;
      double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
      double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
      double obstruction_factor = 0.0;
      double relative_g = 0.0;
      double max_speed = 0.75;
      double gh = 1.0;
      double rt = 0.0;
      double pi = 3.1415926545;
      double deltav = 0.0;
      int dist = 2;
      if (!this.isDead) {
         if (this.getControllingPassenger() == null) {
            float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
            this.rotationYaw += var8 / 5.0F;
         }

         if (this.world.isRemote) {
            if (this.boatPosRotationIncrements > 0) {
               double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
               double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
               double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
               this.setPosition(d4, d5, d11);
               this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
               double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
               if (this.getControllingPassenger() != null) {
                  d10 = MathHelper.wrapDegrees((double)this.getControllingPassenger().rotationYaw - this.rotationYaw);
               }

               this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
               this.setRotation(this.rotationYaw, this.rotationPitch);
               this.boatPosRotationIncrements--;
            }
         } else if (this.getControllingPassenger() != null) {
            EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();
            if (this.motionX < -2.0) {
               this.motionX = -2.0;
            }

            if (this.motionX > 2.0) {
               this.motionX = 2.0;
            }

            if (this.motionZ < -2.0) {
               this.motionZ = -2.0;
            }

            if (this.motionZ > 2.0) {
               this.motionZ = 2.0;
            }

            double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            obstruction_factor = 0.0;
            dist = 1 + (int)(velocity * 10.0);

            for (int k = 0; k < dist; k++) {
               for (int i = 1; i < dist * 2; i++) {
                  double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                  double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                  Block bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - 1 + k, (int)(this.posZ + dz))).getBlock();
                  if (bid != Blocks.AIR) {
                     obstruction_factor += 0.075;
                  }
               }
            }

            this.motionY += obstruction_factor;
            this.posY += obstruction_factor;
            if (this.motionY > 4.0) {
               this.motionY = 4.0;
            }

            double d4 = this.getControllingPassenger().rotationYaw;
            d4 %= 360.0;

            while (d4 < 0.0) {
               d4 += 360.0;
            }

            double d5 = this.rotationYaw;
            d5 %= 360.0;

            while (d5 < 0.0) {
               d5 += 360.0;
            }

            relative_g = (d4 - d5) % 180.0;

            while (relative_g < 0.0) {
               relative_g += 180.0;
            }

            if (relative_g > 90.0) {
               relative_g -= 180.0;
            }

            if (velocity > 0.01) {
               d4 = 1.85 - velocity;
               d4 = Math.abs(d4);
               if (d4 < 0.01) {
                  d4 = 0.01;
               }

               if (d4 > 0.9) {
                  d4 = 0.9;
               }

               this.rotationYaw = this.getControllingPassenger().rotationYaw + (float)(relative_g * d4);
            } else {
               this.rotationYaw = this.getControllingPassenger().rotationYaw;
            }

            this.rotationPitch = 2.0F * (float)velocity;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            double rr = Math.atan2(this.getControllingPassenger().motionZ, this.getControllingPassenger().motionX);
            double rhm = Math.atan2(this.motionZ, this.motionX);
            double rhdir = Math.toRadians((this.getControllingPassenger().rotationYaw + 90.0F) % 360.0F);
            rt = 0.0;
            pi = 3.1415926545;
            deltav = 0.0;
             float im = pp.moveForward;
             if (Math.abs(im) < 0.001F) {
                im = com.electrifiedded.orespawn.network.RiderInputState.getForward(pp);
             }
             if (com.electrifiedded.orespawn.network.RiderInputState.isFlyingUp(pp)) {
               if (this.didjump == 0) {
                  this.motionY++;
                  this.motionY += velocity * 6.0;
                  this.didjump = 20;
               }
            } else if (this.didjump > 0) {
               this.didjump--;
            }

            double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
            if (rdv > pi) {
               rdv -= pi * 2.0;
            }

            rdv = Math.abs(rdv);
            if (Math.abs(newvelocity) < 0.01) {
               rdv = 0.0;
            }

            if (rdv > 1.5) {
               newvelocity = -newvelocity;
            }

            if (Math.abs(im) > 0.001F) {
               if (im > 0.0F) {
                  deltav = 0.045;
                  if (this.deltasmooth < 0.0F) {
                     this.deltasmooth = 0.0F;
                  }

                  this.deltasmooth = (float)(this.deltasmooth + deltav / 10.0);
                  if (this.deltasmooth > deltav) {
                     this.deltasmooth = (float)deltav;
                  }
               } else {
                  max_speed = 0.25;
                  deltav = -0.03;
                  if (this.deltasmooth > 0.0F) {
                     this.deltasmooth = 0.0F;
                  }

                  this.deltasmooth = (float)(this.deltasmooth + deltav / 10.0);
                  if (this.deltasmooth < deltav) {
                     this.deltasmooth = (float)deltav;
                  }
               }

               newvelocity += this.deltasmooth;
               if (newvelocity >= 0.0) {
                  if (newvelocity > max_speed) {
                     newvelocity = max_speed;
                  }

                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
               } else {
                  if (newvelocity < -max_speed) {
                     newvelocity = -max_speed;
                  }

                  newvelocity = -newvelocity;
                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
               }
            } else if (newvelocity >= 0.0) {
               this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
               this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
            } else {
               this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
               this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
            }

            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionY -= 0.25;
            this.motionX *= 0.95;
            this.motionY *= 0.85;
            this.motionZ *= 0.95;
            if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {
               if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
            }
         }
      }
   }

   public void updateRiderPosition() {
      if (this.getControllingPassenger() != null) {
         float f = -0.15F;
         this.getControllingPassenger()
            .setPosition(
               this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
               this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(),
               this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw))
            );
      }
   }

   protected void playTameEffect(boolean par1) {
      net.minecraft.util.EnumParticleTypes s = net.minecraft.util.EnumParticleTypes.HEART;
      if (!par1) {
         s = net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL;
      }

      for (int i = 0; i < 20; i++) {
         double d0 = this.rand.nextGaussian() * 0.08;
         double d1 = this.rand.nextGaussian() * 0.08;
         double d2 = this.rand.nextGaussian() * 0.08;
         this.world
            .spawnParticle(
               s,
               this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
               this.posY + 0.5 + this.rand.nextFloat() * 1.5,
               this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,
               d0,
               d1,
               d2
            );
      }
   }

   protected void fall(float par1) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else if (this.getControllingPassenger() != null) {
         return false;
      } else {
         return this.isNoDespawnRequired() ? false : !this.isTamed();
      }
   }

   @Override
   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public Ostrich spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Ostrich(this.world);
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

    @Override
    public void updatePassenger(net.minecraft.entity.Entity passenger) {
        if (this.isPassenger(passenger) && passenger == this.getControllingPassenger()) {
            this.updateRiderPosition();
        } else {
            super.updatePassenger(passenger);
        }
    }
}

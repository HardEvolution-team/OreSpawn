package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class RockBase extends EntityLiving {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(RockBase.class, DataSerializers.VARINT);

   public int rock_type = 0;
   private double dx;
   private double dz;

   public RockBase(World par1World) {
      super(par1World);
      this.setSize(0.25F, 0.15F);
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.dx = this.dz = 0.0;
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, 0);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      Entity e = par1DamageSource.getTrueSource();
      if (par1DamageSource.getDamageType().equals("inWall")) {
         return false;
      } else {
         if (e != null && e instanceof EntityLivingBase) {
            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.item.pickup"), 0.75F, 2.25F);
         }

         return super.attackEntityFrom(par1DamageSource, par2);
      }
   }

   public int getRockType() {
      return this.dataManager.get(DW_20);
   }

   public void setRockType(int par1) {
      if (this.world != null) {
         if (!this.world.isRemote) {
            this.dataManager.set(DW_20,  par1);
         }
      }
   }

   public void placeRock(int par1) {
      this.rock_type = par1;
      this.setRockType(par1);
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1 + this.rock_type / 4);
      this.setHealth(1 + this.rock_type / 4);
   }

   public int getTotalArmorValue() {
      return 0;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   public void onUpdate() {
      if (this.dx == 0.0 && this.dz == 0.0) {
         this.dx = this.posX;
         this.dz = this.posZ;
      }

      if (this.isResting()) {
         this.restTick();
      } else {
         super.onUpdate();
      }
      this.rotationPitch = 0.0F;
      this.rotationYaw = this.rotationYawHead = 0.0F;
      if (this.world.isRemote) {
         this.rock_type = this.getRockType();
      }

      if (!this.world.isRemote && this.rock_type == 0) {
         if (this.world.provider.getDimension() != OreSpawn.DimensionID5) {
            this.rock_type = 1;
            if (this.world.rand.nextInt(10) == 0) {
               this.rock_type = 2;
            }

            if (this.world.rand.nextInt(20) == 0) {
               this.rock_type = 3;
            }

            if (this.world.rand.nextInt(30) == 0) {
               this.rock_type = 4;
            }

            if (this.world.rand.nextInt(40) == 0) {
               this.rock_type = 5;
            }

            if (this.world.rand.nextInt(50) == 0) {
               this.rock_type = 6;
            }

            if (this.world.rand.nextInt(100) == 0) {
               this.rock_type = 7;
            }

            if (this.world.rand.nextInt(200) == 0) {
               this.rock_type = 8;
            }

            if (this.world.rand.nextInt(500) == 0) {
               this.rock_type = 9;
            }

            if (this.world.rand.nextInt(500) == 0) {
               this.rock_type = 10;
            }

            if (this.world.rand.nextInt(500) == 0) {
               this.rock_type = 11;
            }

            if (this.world.rand.nextInt(1000) == 0) {
               this.rock_type = 12;
            }
         } else {
            this.rock_type = 9;
            if (this.world.rand.nextInt(3) == 0) {
               this.rock_type = 10;
            }

            if (this.world.rand.nextInt(5) == 0) {
               this.rock_type = 11;
            }

            if (this.world.rand.nextInt(10) == 0) {
               this.rock_type = 12;
            }
         }

         this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1 + this.rock_type / 4);
         this.setHealth(1 + this.rock_type / 4);
      }

      if (!this.world.isRemote) {
         this.setRockType(this.rock_type);
      }

      if (this.world.isRemote) {
         if (this.rock_type == 9 && this.world.rand.nextInt(20) == 0) {
            this.world
               .spawnParticle(EnumParticleTypes.FLAME,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F,
                  this.world.rand.nextFloat() / 10.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F
               );
         }

         if (this.rock_type == 10 && this.world.rand.nextInt(20) == 0) {
            this.world
               .spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                  this.posX,
                  this.posY + 0.25,
                  this.posZ,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F,
                  this.world.rand.nextFloat() / 2.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F
               );
         }

         if (this.rock_type == 11 && this.world.rand.nextInt(20) == 0) {
            this.world
               .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F,
                  this.world.rand.nextFloat() / 10.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F
               );
         }

         if (this.rock_type == 12 && this.world.rand.nextInt(20) == 0) {
            this.world
               .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                  this.posX,
                  this.posY + 0.25,
                  this.posZ,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F,
                  this.world.rand.nextFloat() / 5.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0F
               );
         }
      }
   }

   // Rocks never move on their own: run living physics/AI only when disturbed (pushed, hit, moved by the
   // server, in water) and once a second otherwise, so a rock left without ground still falls.
   @Override
   public void onLivingUpdate() {
      if (this.isStill() && (this.ticksExisted + this.getEntityId()) % 20 != 0) {
         return;
      }

      super.onLivingUpdate();
   }

   private boolean isStill() {
      return this.onGround && !this.inWater && this.hurtTime == 0 && this.newPosRotationIncrements == 0
         && this.motionX * this.motionX + this.motionZ * this.motionZ < 1.0E-6 && Math.abs(this.motionY) < 0.1;
   }

   // A rock at rest skips the whole living tick (fire, air, potions and the LivingUpdateEvent every mod
   // listens to, e.g. radiation) on 19 ticks of 20. Anything that could change it (damage, burning, death,
   // not yet typed on the server) takes the full tick.
   private boolean isResting() {
      return this.isStill() && this.hurtResistantTime == 0 && this.deathTime == 0 && this.getHealth() > 0.0F
         && !this.isBurning() && (this.rock_type != 0 || this.world.isRemote)
         && (this.ticksExisted + this.getEntityId()) % 20 != 0;
   }

   private void restTick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationYawHead = this.rotationYawHead;
      this.prevRenderYawOffset = this.renderYawOffset;
      // ticksExisted is advanced by World.updateEntityWithOptionalForce before onUpdate
      if (this.posY < -64.0) {
         this.outOfWorld();
      }
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
      return null;
   }

   protected float getSoundVolume() {
      return 0.65F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      return null;
   }

   public boolean canDespawn() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return !(this.posY < 50.0);
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean canBePushed() {
      return true;
   }

   public void performHurtAnimation() {
      this.hurtTime = this.maxHurtTime = 0;
      this.attackedAtYaw = 0.0F;
   }

   protected void onDeathUpdate() {
      this.setDead();
   }

   public void onDeath(DamageSource par1DamageSource) {
      this.setDead();
      if (this.rock_type == 1) {
         this.dropItemRand(OreSpawn.MySmallRock, 1);
      }

      if (this.rock_type == 2) {
         this.dropItemRand(OreSpawn.MyRock, 1);
      }

      if (this.rock_type == 3) {
         this.dropItemRand(OreSpawn.MyRedRock, 1);
      }

      if (this.rock_type == 4) {
         this.dropItemRand(OreSpawn.MyGreenRock, 1);
      }

      if (this.rock_type == 5) {
         this.dropItemRand(OreSpawn.MyBlueRock, 1);
      }

      if (this.rock_type == 6) {
         this.dropItemRand(OreSpawn.MyPurpleRock, 1);
      }

      if (this.rock_type == 7) {
         this.dropItemRand(OreSpawn.MySpikeyRock, 1);
      }

      if (this.rock_type == 8) {
         this.dropItemRand(OreSpawn.MyTNTRock, 1);
      }

      if (this.rock_type == 9) {
         this.dropItemRand(OreSpawn.MyCrystalRedRock, 1);
      }

      if (this.rock_type == 10) {
         this.dropItemRand(OreSpawn.MyCrystalGreenRock, 1);
      }

      if (this.rock_type == 11) {
         this.dropItemRand(OreSpawn.MyCrystalBlueRock, 1);
      }

      if (this.rock_type == 12) {
         this.dropItemRand(OreSpawn.MyCrystalTNTRock, 1);
      }
   }

   private ItemStack dropItemRand(Item index, int par1) {
      EntityItem var3 = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3 = new EntityItem(
         this.world,
         this.posX + (OreSpawn.OreSpawnRand.nextFloat() - OreSpawn.OreSpawnRand.nextFloat()) / 3.0F,
         this.posY + 0.25,
         this.posZ + (OreSpawn.OreSpawnRand.nextFloat() - OreSpawn.OreSpawnRand.nextFloat()) / 3.0F,
         is
      );
      if (var3 != null) {
         this.world.spawnEntity(var3);
      }

      return is;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("ButterflyType", this.rock_type);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.rock_type = par1NBTTagCompound.getInteger("ButterflyType");
   }
}

package com.electrifiedded.orespawn.entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.MobEffects;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class PurplePower extends EntityLiving {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(PurplePower.class, DataSerializers.VARINT);

   private BlockPos currentFlightTarget = null;
   private GenericTargetSorter TargetSorter = null;
   private int purple_type = 0;

   public PurplePower(World par1World) {
      super(par1World);
      this.setSize(0.75F, 0.75F);
      this.experienceValue = 35;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.TargetSorter = new GenericTargetSorter(this);
      this.noClip = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(500.0);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, 0);
   }

   public void setPurpleType(int par1) {
      if (this.world != null) {
         if (!this.world.isRemote) {
            this.purple_type = par1;
            this.dataManager.set(DW_20,  par1);
         }
      }
   }

   public int getPurpleType() {
      return this.dataManager.get(DW_20);
   }

   protected boolean canDespawn() {
      return false;
   }

   protected float getSoundVolume() {
      return 0.75F;
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
      return null;
   }

   public boolean canBePushed() {
      return false;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   public int mygetMaxHealth() {
      return 1000;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      EntityLivingBase e = null;
      super.onUpdate();
      this.motionY *= 0.6;
      if (this.getPurpleType() == 0) {
         if (this.world.isRemote && this.world.rand.nextInt(4) == 1) {
            this.world
               .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                  this.posX,
                  this.posY + 1.25,
                  this.posZ,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 2.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 2.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 2.0F
               );
         }
      } else if (this.world.isRemote && this.world.rand.nextInt(6) == 1) {
         this.world
            .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
               this.posX,
               this.posY + 0.65F,
               this.posZ,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0F
            );
      }

      if (this.world.isRemote) {
         this.purple_type = this.getPurpleType();
      } else {
         this.setPurpleType(this.purple_type);
      }

      if (!this.world.isRemote && this.world.rand.nextInt(2500) == 1) {
         if (this.getPurpleType() == 10) {
            this.world
               .newExplosion(
                  (Entity)null,
                  this.posX,
                  this.posY + 0.25,
                  this.posZ,
                  9.1F,
                  true,
                  this.world.getGameRules().getBoolean("mobGriefing")
               );
         }

         this.setDead();
      }
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.55, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   protected void updateAITasks() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 50;
      EntityLivingBase e = null;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.rand.nextInt(300) != 0
            && !(this.getDistanceSq(currentFlightTarget) < 2.1F)) {
            if (this.rand.nextInt(7) == 2 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               e = this.findSomethingToAttack();
               if (e != null) {
                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + e.height / 2.0F), (int)e.posZ);
                  if (this.getDistanceSq(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F)) {
                     this.attackEntityAsMob(e);
                     this.setDead();
                  }
               }
            }
         } else {
            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               zdir = this.rand.nextInt(10) + 8;
               xdir = this.rand.nextInt(10) + 8;
               if (this.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(20) - 10, (int)this.posZ + zdir);
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

         if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
         }

         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.4 - this.motionX) * 0.2;
         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.20000000149011612;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.4 - this.motionZ) * 0.2;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.75F;
         this.rotationYaw += var8 / 4.0F;
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
      float dm = par2;
      if (e != null && e instanceof EntityArrow) {
         return false;
      } else {
         if (par2 > 10.0F) {
            dm = 10.0F;
         }

         ret = super.attackEntityFrom(par1DamageSource, dm);
         if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + e.height / 2.0F), (int)e.posZ);
         }

         return ret;
      }
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   public int getTotalArmorValue() {
      return 25;
   }

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
         return false;
      } else if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityPlayer) {
         EntityPlayer p = (EntityPlayer)par1EntityLiving;
         return p.capabilities.isCreativeMode ? false : this.getPurpleType() <= 0 || this.getPurpleType() == 10;
      } else {
         if (this.getPurpleType() != 0 && this.getPurpleType() != 10 && par1EntityLiving instanceof EntityTameable) {
            EntityTameable e = (EntityTameable)par1EntityLiving;
            if (e.isTamed()) {
               return false;
            }
         }

         return !MyUtils.isRoyalty(par1EntityLiving);
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(32.0, 24.0, 32.0));
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

   public boolean attackEntityAsMob(Entity par1Entity) {
      boolean var4 = false;
      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
         EntityLivingBase e = (EntityLivingBase)par1Entity;
         if (this.getPurpleType() != 0 && this.getPurpleType() != 10) {
            e.setHealth(e.getHealth() * 15.0F / 16.0F);
            var4 = e.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
            if (this.getPurpleType() == 1) {
               e.setFire(10);
            }

            if (this.getPurpleType() == 2) {
               e.addPotionEffect(new PotionEffect(MobEffects.POISON, 50, 0));
            }

            if (this.getPurpleType() == 3) {
               e.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 50, 0));
            }
         } else {
            e.setHealth(e.getHealth() / 4.0F - 1.0F);
            var4 = e.attackEntityFrom(DamageSource.causeMobDamage(this), e.getMaxHealth() / 8.0F);
            if (this.getPurpleType() == 10) {
               this.world
                  .newExplosion(
                     (Entity)null,
                     e.posX,
                     e.posY - 0.25,
                     e.posZ,
                     9.1F,
                     true,
                     this.world.getGameRules().getBoolean("mobGriefing")
                  );
            }
         }
      }

      return var4;
   }

   protected Item getDropItem() {
      return null;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("PurpleType", this.purple_type);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.purple_type = par1NBTTagCompound.getInteger("PurpleType");
   }
}

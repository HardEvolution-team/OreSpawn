package com.electrifiedded.orespawn.entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
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
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Irukandji extends EntityMob {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Irukandji.class, DataSerializers.BYTE);

   private GenericTargetSorter TargetSorter = null;
   private EntityLivingBase buddy = null;
   private float moveSpeed = 0.15F;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public Irukandji(World par1World) {
      super(par1World);
      this.setSize(0.25F, 0.25F);
      this.experienceValue = 50;
      this.isImmuneToFire = true;
      this.isImmuneToFire = false;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new MyEntityAIWander(this, 1.0F));
      this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(3, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Irukandji_stats.attack);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, (byte)0);
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public int mygetMaxHealth() {
      return OreSpawn.Irukandji_stats.health;
   }

   public int getTotalArmorValue() {
      return OreSpawn.Irukandji_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public int getAttackStrength(Entity par1Entity) {
      return 2;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("little_splt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("ratdead");
   }

   protected float getSoundVolume() {
      return 0.25F;
   }

   protected float getSoundPitch() {
      return 2.0F;
   }

   protected Item getDropItem() {
      return OreSpawn.MyIrukandji;
   }

   public void initCreature() {
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      if (par1EntityPlayer != null && par1EntityPlayer.getHeldItemMainhand() == null) {
         par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), 200.0F);
      }

      return false;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      if (this.isDead) {
         return false;
      } else {
         Entity e = par1DamageSource.getTrueSource();
         if (e != null && e instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)e;
            if (p.getHeldItemMainhand() == null) {
               p.attackEntityFrom(DamageSource.causeMobDamage(this), 200.0F);
               return false;
            }
         }

         if (e != null && e instanceof EntityLiving) {
            if (e instanceof Irukandji) {
               return false;
            }

            this.setAttackTarget((EntityLiving)e);
            this.setAttackTarget((EntityLivingBase)e);
            this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);
            ret = true;
         }

         return super.attackEntityFrom(par1DamageSource, par2);
      }
   }

   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
      int found = 0;

      for (int i = -dy; i <= dy; i++) {
         for (int j = -dz; j <= dz; j++) {
            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
            if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
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
            if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
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
            if (bidx == Blocks.WATER || bidx == Blocks.FLOWING_WATER) {
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
            if (bidx == Blocks.WATER || bidx == Blocks.FLOWING_WATER) {
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
            if (bidxx == Blocks.WATER || bidxx == Blocks.FLOWING_WATER) {
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
            if (bidxx == Blocks.WATER || bidxx == Blocks.FLOWING_WATER) {
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
         if (!this.isInWater() && this.world.rand.nextInt(10) == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 12; i++) {
               int j = i;
               if (i > 5) {
                  j = 5;
               }

               if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) {
                  break;
               }

               if (i >= 5) {
                  i++;
               }
            }

            if (this.closest < 99999) {
               this.getNavigator().tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.33);
            } else {
               if (this.world.rand.nextInt(25) == 1) {
                  this.heal(-1.0F);
               }

               if (this.getHealth() <= 0.0F) {
                  this.setDead();
                  return;
               }
            }
         }

         if (this.world.rand.nextInt(8) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               if (this.getDistanceSq(e) < 3.0) {
                  this.setAttacking(1);
                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
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

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
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
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(6.0, 4.0, 6.0));
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

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Irukandji.class, this.getEntityBoundingBox().grow(16.0, 8.0, 16.0));
      return var5.size();
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else if (!this.world.isDaytime()) {
         return false;
      } else {
         return this.world.rand.nextInt(60) != 1 ? false : this.findBuddies() <= 2;
      }
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}

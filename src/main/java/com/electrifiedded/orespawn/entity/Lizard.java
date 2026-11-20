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
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Lizard extends EntityCannonFodder {
    private static final DataParameter<Byte> DW_23 = EntityDataManager.<Byte>createKey(Lizard.class, DataSerializers.BYTE);

   private GenericTargetSorter TargetSorter = null;
   public boolean should_despawn = true;
   private EntityLivingBase buddy = null;
   private int follow_time = 0;
   private float moveSpeed = 0.3F;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public Lizard(World par1World) {
      super(par1World);
      this.moveSpeed = 0.3F;
      this.setSize(1.5F, 1.25F);
      this.experienceValue = 15;
      this.isImmuneToFire = true;
      this.isImmuneToFire = false;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));
      this.tasks.addTask(2, new EntityAIMate(this, 1.0));
      this.tasks.addTask(3, new EntityAITempt(this, 1.25, Items.DYE, false));
      this.tasks.addTask(4, new MyEntityAIWanderALot(this, 16, 1.0));
      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(5, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
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
      this.dataManager.register(DW_23, (byte)0);
   }

   @Override
   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public int mygetMaxHealth() {
      return 30;
   }

   @Override
   public int getTotalArmorValue() {
      return 5;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      Entity e = par1DamageSource.getTrueSource();
      if (!par1DamageSource.getDamageType().equals("cactus")) {
         ret = super.attackEntityFrom(par1DamageSource, par2);
         if (e != null && e instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase)e);
         }
      }

      this.follow_time = 0;
      return ret;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("alo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("alo_death");
   }

   protected float getSoundVolume() {
      return 1.0F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      return null;
   }

   @Override
   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if (var2 != null && var2.getCount() <= 0) {
         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
         var2 = null;
      }

       if (super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND)) {
         return true;
      } else if (var2 != null && var2.getItem() == Items.DYE && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.world.isRemote) {
            this.buddy = par1EntityPlayer;
            this.follow_time = 3000 + this.world.rand.nextInt(2000);
         }

         this.playTameEffect(true);
         if (!par1EntityPlayer.capabilities.isCreativeMode) {
            var2.shrink(1);
            if (var2.getCount() <= 0) {
               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            }
         }

         return true;
      } else {
         if (!this.world.isRemote) {
            this.buddy = null;
            this.follow_time = 0;
         }

         this.playTameEffect(false);
         return true;
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

   @Override
   protected void updateAITasks() {
      if (!this.isDead) {
         super.updateAITasks();
         if (this.follow_time > 0) {
            this.follow_time--;
            this.should_despawn = false;
         } else {
            this.should_despawn = true;
         }

         if (!this.isInWater() && this.world.rand.nextInt(100) == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 14; i++) {
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
            }
         }

         if (this.getHealth() < this.mygetMaxHealth() && this.world.rand.nextInt(300) == 1) {
            this.heal(1.0F);
         }

         if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(10) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               this.follow_time = 0;
               if (this.getDistanceSq(e) < 12.0) {
                  this.setAttacking(1);
                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                     this.attackEntityAsMob(e);
                  }
               } else {
                  this.getNavigator().tryMoveToEntityLiving(e, 1.2);
               }
            } else {
               if (this.buddy != null && !this.buddy.isDead && this.world.rand.nextInt(15) == 1) {
                  this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0);
               }

               this.setAttacking(0);
            }
         }

         if (this.buddy != null && !this.buddy.isDead && this.follow_time > 0 && this.world.rand.nextInt(20) == 1) {
            this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0);
         }
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      float i = 6.0F;
      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
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
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof AttackSquid) {
         return true;
      } else if (par1EntityLiving instanceof EntitySpider) {
         return true;
      } else if (par1EntityLiving instanceof EntityCaveSpider) {
         return true;
      } else if (par1EntityLiving instanceof EntityChicken) {
         return true;
      } else {
         if (par1EntityLiving instanceof Lizard && this.world.rand.nextInt(10) == 1 && this.follow_time <= 0) {
            this.buddy = par1EntityLiving;
         }

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
         if (this.world.rand.nextInt(100) == 0) {
            this.setAttackTarget(null);
         }

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
      return this.dataManager.get(DW_23);
   }

   public final void setAttacking(int par1) {
      this.dataManager.set(DW_23,  (byte)par1);
   }

   public boolean getCanSpawnHere() {
      return !(this.posY < 50.0);
   }

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else if (this.isNoDespawnRequired()) {
         return false;
      } else {
         return this.isTamed() ? false : this.should_despawn;
      }
   }

   @Override
   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public Lizard spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Lizard(this.world);
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
}

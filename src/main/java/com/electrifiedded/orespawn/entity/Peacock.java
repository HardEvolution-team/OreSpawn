package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Peacock extends EntityAnimal {
   private float moveSpeed = 0.38F;
   int my_blink = 0;
   int blinkcount = 0;
   int blinker = 0;
   private GenericTargetSorter TargetSorter = null;

   public Peacock(World par1World) {
      super(par1World);
      this.setSize(0.65F, 1.2F);
      this.isImmuneToFire = true;
      this.experienceValue = 8;
      this.my_blink = 20 + this.rand.nextInt(50);
      this.blinkcount = 0;
      this.blinker = 0;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 12.0F, 1.2F, 1.6F));
      this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(5, new MyEntityAIWander(this, 1.0F));
      this.tasks.addTask(6, new EntityAILookIdle(this));
      if (OreSpawn.PlayNicely == 0) {
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, Termite.class, 6, true, false, null));
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public int getBlink() {
      return this.blinker;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      this.blinkcount++;
      if (this.blinkcount > this.my_blink) {
         this.blinkcount = 0;
         if (this.blinker > 0) {
            this.blinker = 0;
            this.my_blink = 50 + this.world.rand.nextInt(300);
         } else {
            this.blinker = 1;
            this.my_blink = 25 + this.world.rand.nextInt(100);
         }
      }
   }

   public boolean getCanSpawnHere() {
      for (int k = -1; k < 1; k++) {
         for (int j = -1; j < 1; j++) {
            for (int i = 1; i < 3; i++) {
               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
               if (bid != Blocks.AIR) {
                  return false;
               }
            }
         }
      }

      long t = this.world.getWorldTime();
      t %= 24000L;
      if (t > 12000L) {
         return false;
      } else {
         return this.posY < 50.0 || this.posY > 100.0 ? false : this.findBuddies() <= 2;
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public int mygetMaxHealth() {
      return 15;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.world.rand.nextInt(8) != 1 ? null : OreSpawnSoundEvents.get("peacocklive");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("peacockhit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("peacockdead");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return OreSpawn.MyRawPeacock;
   }

   protected void dropFewItems(boolean par1, int par2) {
      this.dropItem(OreSpawn.MyRawPeacock, 1);
      if (this.world.rand.nextInt(3) == 1) {
         this.dropItem(OreSpawn.MyRawPeacock, 1);
      }

      if (this.world.rand.nextInt(2) == 1) {
         this.dropItem(OreSpawn.MyPeacockFeather, 1);
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0F);
   }

   private ItemStack LayAnEgg(Item index, int par1) {
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

   protected void updateAITasks() {
      if (this.world.rand.nextInt(200) == 1) {
         this.setRevengeTarget(null);
      }

      super.updateAITasks();
      if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
         if (this.world.rand.nextInt(10) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               if (this.getDistanceSq(e) < 4.0) {
                  this.attackEntityAsMob(e);
               } else {
                  this.getNavigator().tryMoveToEntityLiving(e, 1.2);
               }
            }
         }

         if (this.world.rand.nextInt(5000) == 1) {
            this.LayAnEgg(OreSpawn.PeacockEgg, 1 + this.world.rand.nextInt(3));
         }
      }
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
      } else {
         return !this.getEntitySenses().canSee(par1EntityLiving) ? false : par1EntityLiving instanceof Termite;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 2.0, 10.0));
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

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else {
         return !this.isNoDespawnRequired();
      }
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public Peacock spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Peacock(this.world);
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Peacock.class, this.getEntityBoundingBox().grow(16.0, 10.0, 16.0));
      return var5.size();
   }
}

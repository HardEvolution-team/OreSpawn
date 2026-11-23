package com.electrifiedded.orespawn.entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Kyuubi extends EntityMob {
   private GenericTargetSorter TargetSorter = null;
   private float moveSpeed = 0.25F;

   public Kyuubi(World par1World) {
      super(par1World);
      this.setSize(0.5F, 1.25F);
      this.experienceValue = 30;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));
      this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0, false));
      this.tasks.addTask(3, new EntityAIWander(this, 1.0));
      this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.tasks.addTask(5, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.TargetSorter = new GenericTargetSorter(this);
   }

   protected void entityInit() {
      super.entityInit();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Kyuubi_stats.attack);
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public int mygetMaxHealth() {
      return OreSpawn.Kyuubi_stats.health;
   }

   public int getTotalArmorValue() {
      return OreSpawn.Kyuubi_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.world.rand.nextInt(10) == 1) {
         this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
         this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
         this.setFire(5);
         if (this.isInWater()) {
            this.attackEntityAsMob(this);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 1.75, this.posZ, 0.0, 0.0, 0.0);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 1.75, this.posZ, 0.0, 0.0, 0.0);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 2.0, this.posZ, 0.0, 0.0, 0.0);
         }
      }
   }

   public int getAttackStrength(Entity par1Entity) {
      return 3;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("kyuubi_living");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("alo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("alo_death");
   }

   protected float getSoundVolume() {
      return 0.75F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      int i = this.world.rand.nextInt(6);
      if (i == 0) {
         return Items.GOLD_NUGGET;
      } else if (i == 1) {
         return OreSpawn.UraniumNugget;
      } else {
         return i == 2 ? OreSpawn.TitaniumNugget : null;
      }
   }

   public void initCreature() {
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
   }

   protected void updateAITasks() {
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         super.updateAITasks();
         if (this.world.rand.nextInt(10) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               this.faceEntity(e, 10.0F, 10.0F);
               this.getNavigator().tryMoveToEntityLiving(e, 1.25);
               if (this.getDistanceSq(e) < 64.0 && (this.rand.nextInt(6) == 0 || this.rand.nextInt(8) == 1)) {
                  EntitySmallFireball var2 = new EntitySmallFireball(
                     this.world,
                     this,
                     e.posX - this.posX,
                     e.posY + 0.75 - (this.posY + 1.25),
                     e.posZ - this.posZ
                  );
                  var2.setLocationAndAngles(this.posX, this.posY + 1.25, this.posZ, this.rotationYaw, this.rotationPitch);
                  this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                  this.world.spawnEntity(var2);
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
      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityMob) {
         return false;
      } else if (par1EntityLiving instanceof EntityPigZombie) {
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
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0, 4.0, 12.0));
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

   public boolean getCanSpawnHere() {
      return true;
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
      for (int var4 = 0; var4 < 10; var4++) {
         this.dropItemRand(Items.COAL, 1);
      }

      for (int var41 = 0; var41 < 3; var41++) {
         this.dropItemRand(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 1);
      }

      for (int var5 = 0; var5 < 4; var5++) {
         this.dropItemRand(Item.getItemFromBlock(Blocks.QUARTZ_BLOCK), 1);
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

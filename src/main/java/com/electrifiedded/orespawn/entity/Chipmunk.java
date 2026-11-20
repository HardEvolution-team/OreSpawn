package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Chipmunk extends EntityCannonFodder {
   private float moveSpeed = 0.38F;

   public Chipmunk(World par1World) {
      super(par1World);
      this.setSize(0.35F, 0.35F);
      this.moveSpeed = 0.38F;
      this.isImmuneToFire = true;
      this.setSitting(false);
      this.experienceValue = 5;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));
      this.tasks.addTask(3, new MyEntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.6F));
      this.tasks.addTask(4, new EntityAITempt(this, 1.2F, Items.APPLE, false));
      this.tasks.addTask(5, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(6, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 5.0F));
      this.tasks.addTask(9, new MyEntityAIWanderALot(this, 10, 1.0));
      this.tasks.addTask(10, new EntityAILookIdle(this));
      this.tasks.addTask(11, new EntityAIMoveIndoors(this));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.setSitting(false);
   }

   @Override
   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
      float i = MathHelper.ceil(par1 - 3.0F);
      if (i > 0.0F) {
         if (i > 3.0F) {
            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.big_fall"), 1.0F, 1.0F);
         } else {
            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.small_fall"), 1.0F, 1.0F);
         }

         if (i > 2.0F) {
            i = 2.0F;
         }

         this.attackEntityFrom(DamageSource.FALL, i);
      }
   }

   protected void handleJumpWater() {
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (this.world.rand.nextInt(250) == 0) {
            this.heal(1.0F);
         }

         if (!this.world.isRemote && this.world.rand.nextInt(600) == 1) {
            Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - 1, (int)this.posZ)).getBlock();
            if ((bid == Blocks.DIRT || bid == Blocks.FARMLAND) && this.world.getGameRules().getBoolean("mobGriefing")) {
               this.world.setBlockState(new BlockPos((int)this.posX, (int)this.posY - 1, (int)this.posZ), Blocks.AIR.getStateFromMeta(0), 2);
            }
         }

         super.handleJumpWater();
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public int mygetMaxHealth() {
      return 5;
   }

   public int getChipmunkHealth() {
      return (int)this.getHealth();
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
      } else if (var2 != null && var2.getItem() == Items.APPLE && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.isTamed()) {
            if (!this.world.isRemote) {
               if (this.rand.nextInt(2) == 0) {
                  this.setTamed(true);
                  this.setCustomNameTag(par1EntityPlayer.getUniqueID().toString());
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
      } else if (this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.isSitting()) {
            this.setSitting(true);
         } else {
            this.setSitting(false);
         }

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
      return OreSpawnSoundEvents.get("scorpion_hit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("cryo_death");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Items.WHEAT;
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

   public boolean getCanSpawnHere() {
      return this.posY < 50.0 ? false : this.findBuddies() <= 2;
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Chipmunk.class, this.getEntityBoundingBox().grow(20.0, 10.0, 20.0));
      return var5.size();
   }

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else {
         return this.isNoDespawnRequired() ? false : !this.isTamed();
      }
   }

   @Override
   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public Chipmunk spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Chipmunk(this.world);
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

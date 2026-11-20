package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class VelocityRaptor extends EntityCannonFodder {
   private float moveSpeed = 0.55F;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public VelocityRaptor(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.6F);
      this.isImmuneToFire = true;
      this.setSitting(false);
      this.experienceValue = 5;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 1.5F, 10.0F, 2.0F));
      this.tasks.addTask(3, new MyEntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(4, new EntityAITempt(this, 1.25, Items.APPLE, false));
      this.tasks.addTask(5, new EntityAIPanic(this, 1.6F));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(7, new MyEntityAIWander(this, 0.9F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.tasks.addTask(9, new EntityAIMoveIndoors(this));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.setSitting(false);
   }

   public boolean getCanSpawnHere() {
      return this.posY < 50.0 ? false : this.world.isDaytime();
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

   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
      int found = 0;

      for (int i = -dy; i <= dy; i++) {
         for (int j = -dz; j <= dz; j++) {
            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
            if (bid == Blocks.TALLGRASS
               || bid == Blocks.YELLOW_FLOWER
               || bid == Blocks.RED_FLOWER
               || bid == Blocks.DEADBUSH
               || bid == Blocks.DOUBLE_PLANT) {
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
            if (bid == Blocks.TALLGRASS
               || bid == Blocks.YELLOW_FLOWER
               || bid == Blocks.RED_FLOWER
               || bid == Blocks.DEADBUSH
               || bid == Blocks.DOUBLE_PLANT) {
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
            if (bidx == Blocks.TALLGRASS
               || bidx == Blocks.YELLOW_FLOWER
               || bidx == Blocks.RED_FLOWER
               || bidx == Blocks.DEADBUSH
               || bidx == Blocks.DOUBLE_PLANT) {
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
            if (bidx == Blocks.TALLGRASS
               || bidx == Blocks.YELLOW_FLOWER
               || bidx == Blocks.RED_FLOWER
               || bidx == Blocks.DEADBUSH
               || bidx == Blocks.DOUBLE_PLANT) {
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
            if (bidxx == Blocks.TALLGRASS
               || bidxx == Blocks.YELLOW_FLOWER
               || bidxx == Blocks.RED_FLOWER
               || bidxx == Blocks.DEADBUSH
               || bidxx == Blocks.DOUBLE_PLANT) {
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
            if (bidxx == Blocks.TALLGRASS
               || bidxx == Blocks.YELLOW_FLOWER
               || bidxx == Blocks.RED_FLOWER
               || bidxx == Blocks.DEADBUSH
               || bidxx == Blocks.DOUBLE_PLANT) {
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

   protected void handleJumpWater() {
      super.handleJumpWater();
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (!this.isSitting()
            && (
               this.world.rand.nextInt(20) == 0 && this.getVHealth() < this.mygetMaxHealth()
                  || this.world.rand.nextInt(250) == 0
            )
            && OreSpawn.PlayNicely == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 10; i++) {
               int j = i;
               if (i > 2) {
                  j = 2;
               }

               if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) {
                  break;
               }

               if (i >= 5) {
                  i++;
               }
            }

            if (this.closest < 99999) {
               this.getNavigator().tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);
               if (this.closest < 12) {
                  if (this.world.getGameRules().getBoolean("mobGriefing")) {
                     this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getStateFromMeta(0), 2);
                  }

                  this.heal(2.0F);
                  this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.player.burp"), 0.5F, this.world.rand.nextFloat() * 0.2F + 1.5F);
               }
            }
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
      return this.isTamed() ? 20 : 10;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public int getVHealth() {
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
               par1EntityPlayer.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6F);
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
            this.setHealth(this.mygetMaxHealth());
            this.setCustomNameTag("");
            this.playTameEffect(false);
            this.world.setEntityState(this, (byte)6);
         } else {
            par1EntityPlayer.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3F);
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
      } else if (this.isTamed() && par1EntityPlayer.getDistanceSq(this) < 16.0 && this.isOwner(par1EntityPlayer)) {
         if (!this.isSitting()) {
            this.setSitting(true);
         } else {
            this.setSitting(false);
         }

         if (this.world.isRemote) {
            par1EntityPlayer.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3F);
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
      return OreSpawnSoundEvents.get("cryo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("cryo_death");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return null;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = 0;
      if (this.isTamed()) {
         var3 = this.rand.nextInt(5);
         var3 += 2;

         for (int var4 = 0; var4 < var3; var4++) {
            this.dropItem(Item.getItemFromBlock(Blocks.RED_FLOWER), 1);
         }
      }
   }

   protected float getSoundPitch() {
      return this.isChild()
         ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.5F
         : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      float p2 = par2;
      if (par2 > 10.0F) {
         p2 = 10.0F;
      }

      return super.attackEntityFrom(par1DamageSource, p2);
   }

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else {
         return this.isTamed() ? false : !this.isNoDespawnRequired();
      }
   }

   @Override
   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public VelocityRaptor spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new VelocityRaptor(this.world);
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

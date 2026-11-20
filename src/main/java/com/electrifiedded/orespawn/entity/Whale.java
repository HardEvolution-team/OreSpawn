package com.electrifiedded.orespawn.entity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Whale extends EntityAnimal {
   private float moveSpeed = 0.35F;
   private int spray = 0;
   private int spray_timer = 0;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public Whale(World par1World) {
      super(par1World);
      this.setSize(1.5F, 2.5F);
      this.moveSpeed = 0.35F;
      this.isImmuneToFire = true;
      this.experienceValue = 40;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new EntityAITempt(this, 1.2F, Items.FISH, false));
      this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
      this.tasks.addTask(6, new MyEntityAIWander(this, 1.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (this.spray == 0) {
         if (this.spray_timer > 0) {
            this.spray_timer--;
         }

         if (this.spray_timer == 0) {
            this.spray_timer = 250 + this.world.rand.nextInt(250);
            this.spray = 25 + this.world.rand.nextInt(25);
         }
      }

      if (this.world.isRemote && this.spray > 0) {
         for (int i = 0; i < 20; i++) {
            double d = this.world.rand.nextDouble() * 0.75;
            d *= d;
            double dir = this.world.rand.nextDouble() * 2.0 * Math.PI;
            dir -= Math.PI;
            double dx = Math.cos(dir) * d / 2.0;
            double dz = Math.sin(dir) * d / 2.0;
            dir++;
            if (i < 10) {
               this.world
                  .spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                     this.posX + dx,
                     this.posY + 1.0 + d,
                     this.posZ + dz,
                     Math.cos(dir) * this.world.rand.nextFloat() / 4.0,
                     this.world.rand.nextFloat() * 2.0F,
                     Math.sin(dir) * this.world.rand.nextFloat() / 4.0
                  );
            } else {
               this.world
                  .spawnParticle(EnumParticleTypes.WATER_SPLASH,
                     this.posX + dx,
                     this.posY + 1.0 + d,
                     this.posZ + dz,
                     Math.cos(dir) * this.world.rand.nextFloat() / 4.0,
                     this.world.rand.nextFloat() * 2.0F,
                     Math.sin(dir) * this.world.rand.nextFloat() / 4.0
                  );
            }
         }

         this.spray--;
      }

      if (this.world.rand.nextInt(200) == 1) {
         this.heal(1.0F);
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public int mygetMaxHealth() {
      return 100;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("splash");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("little_splat");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("big_splat");
   }

   protected float getSoundVolume() {
      return 0.9F;
   }

   protected float getSoundPitch() {
      return 0.5F;
   }

   protected Item getDropItem() {
      return Items.FISH;
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
      int var3 = 0;
      var3 = this.rand.nextInt(25);
      var3 += 20;

      for (int var4 = 0; var4 < var3; var4++) {
         this.dropItemRand(Items.FISH, 1);
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

   protected void handleJumpWater() {
      super.handleJumpWater();
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (!this.isInWater() && this.world.rand.nextInt(20) == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 11; i++) {
               int j = i;
               if (i > 4) {
                  j = 4;
               }

               if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) {
                  break;
               }

               if (i >= 5) {
                  i++;
               }
            }

            if (this.closest < 99999) {
               this.getNavigator().tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.0);
            } else {
               if (this.world.rand.nextInt(25) == 1) {
                  this.heal(-4.0F);
               }

               if (this.getHealth() <= 0.0F) {
                  this.setDead();
                  return;
               }
            }
         }

         if (this.isInWater() && this.world.rand.nextInt(50) == 0) {
            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("splash"), 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);
            this.heal(1.0F);
         }
      }
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Whale.class, this.getEntityBoundingBox().grow(32.0, 8.0, 32.0));
      return var5.size();
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else if (!this.world.isDaytime()) {
         return false;
      } else {
         return this.world.rand.nextInt(50) != 1 ? false : this.findBuddies() <= 0;
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

   public Whale spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Whale(this.world);
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;
   }
}

package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class WormMedium extends EntityMob {
   public int upcount = 0;
   public int downcount = 0;

   public WormMedium(World par1World) {
      super(par1World);
      this.setSize(0.5F, 2.0F);
      this.experienceValue = 0;
      this.noClip = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.WormMedium_stats.attack);
   }

   protected void entityInit() {
      super.entityInit();
   }

   protected boolean canDespawn() {
      return false;
   }

   protected float getSoundVolume() {
      return 0.5F;
   }

   protected float getSoundPitch() {
      return 1.5F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("little_splat");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("big_splat");
   }

   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   protected void collideWithNearbyEntities() {
   }

   public int mygetMaxHealth() {
      return OreSpawn.WormMedium_stats.health;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      Block bid = Blocks.AIR;
      EntityPlayer target = null;
      WormSmall worms = null;
      super.onLivingUpdate();
      if (!this.world.isRemote) {
         worms = (WormSmall)this.world.findNearestEntityWithinAABB(WormSmall.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0), this);
         if (worms == null) {
            target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0), this);
         }

         if ((worms != null || target == null) && OreSpawn.PlayNicely == 0) {
            this.upcount = this.world.rand.nextInt(50);
            this.downcount = 0;
            bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY + 3, (int)this.posZ)).getBlock();
            if (bid == Blocks.TALLGRASS) {
               bid = Blocks.AIR;
            }

            if (bid != Blocks.AIR) {
               if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                  this.setDead();
               }

               this.motionY += 0.1F;
               this.posY += 0.05F;
            }
         } else if (this.upcount > 0) {
            this.upcount--;
            if (this.upcount == 0) {
               this.downcount = 100 + this.world.rand.nextInt(150);
            }

            if (target != null) {
               this.pointAtEntity(target);
            }

            bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)(this.posY + 0.25), (int)this.posZ)).getBlock();
            if (bid == Blocks.TALLGRASS) {
               bid = Blocks.AIR;
            }

            if (bid != Blocks.AIR) {
               if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                  this.setDead();
               }

               this.motionY += 0.2F;
               this.posY += 0.1F;
            }
         } else {
            if (this.downcount > 0) {
               this.downcount--;
            } else {
               this.upcount = 25 + this.world.rand.nextInt(75);
            }

            bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY + 3, (int)this.posZ)).getBlock();
            if (bid == Blocks.TALLGRASS) {
               bid = Blocks.AIR;
            }

            if (bid != Blocks.AIR) {
               if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                  this.setDead();
               }

               this.motionY += 0.1F;
               this.posY += 0.05F;
            }
         }

         this.motionY -= 0.01;
         this.motionX = 0.0;
         this.motionZ = 0.0;
         this.moveVertical = 0.0F;
      }
   }

   public void onUpdate() {
      if (this.isNoDespawnRequired()) {
         this.noClip = false;
      }

      super.onUpdate();
      this.motionY *= 0.65;
   }

   public void pointAtEntity(EntityLivingBase e) {
      double d1 = e.posX - this.posX;
      double d2 = e.posZ - this.posZ;
      float d = (float)Math.atan2(d2, d1);
      float f2 = (float)(d * 180.0 / Math.PI) - 90.0F;
      this.rotationYaw = this.rotationYawHead = f2;
   }

   public int getTotalArmorValue() {
      return OreSpawn.WormMedium_stats.defense;
   }

   protected void updateAITasks() {
      int bid = 0;
      EntityPlayer target = null;
      WormSmall worms = null;
      if (!this.isDead) {
         super.updateAITasks();
         if (OreSpawn.PlayNicely == 0) {
            worms = (WormSmall)this.world.findNearestEntityWithinAABB(WormSmall.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0), this);
            if (worms == null) {
               target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(2.25, 8.0, 2.25), this);
               if (target != null && target.capabilities.isCreativeMode) {
                  target = null;
               }

               if (target != null) {
                  this.pointAtEntity(target);
                  if (this.upcount > 0 && this.world.rand.nextInt(15) == 1 && !target.capabilities.isCreativeMode) {
                     super.attackEntityAsMob(target);
                     if (this.world.rand.nextInt(6) == 1) {
                        ItemStack boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET);
                        if (boots != null) {
                           target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, net.minecraft.item.ItemStack.EMPTY);
                           bid = boots.getMaxDamage() - boots.getMetadata();
                           if (bid > 15) {
                              bid /= 15;
                           } else {
                              bid = 1;
                           }

                           boots.damageItem(bid, this);
                           EntityItem var3 = new EntityItem(
                              this.world,
                              this.posX + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),
                              this.posY + 3.0,
                              this.posZ + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),
                              boots
                           );
                           this.world.spawnEntity(var3);
                        } else {
                           boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS);
                           if (boots != null) {
                              target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, net.minecraft.item.ItemStack.EMPTY);
                              bid = boots.getMaxDamage() - boots.getMetadata();
                              if (bid > 15) {
                                 bid /= 15;
                              } else {
                                 bid = 1;
                              }

                              boots.damageItem(bid, this);
                              EntityItem var3 = new EntityItem(
                                 this.world,
                                 this.posX + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),
                                 this.posY + 3.0,
                                 this.posZ + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),
                                 boots
                              );
                              this.world.spawnEntity(var3);
                           }
                        }
                     }
                  }
               }
            }
         }
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

   public boolean getCanSpawnHere() {
      return !this.world.isDaytime();
   }

   public void initCreature() {
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      return par1DamageSource.getDamageType().equals("inWall") ? ret : super.attackEntityFrom(par1DamageSource, par2);
   }

   protected Item getDropItem() {
      return Items.ROTTEN_FLESH;
   }

   private void dropItemRand(Item index, int par1) {
      EntityItem var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(3) - OreSpawn.OreSpawnRand.nextInt(3),
         this.posY + 2.5 + this.world.rand.nextInt(3),
         this.posZ + OreSpawn.OreSpawnRand.nextInt(3) - OreSpawn.OreSpawnRand.nextInt(3),
         new ItemStack(index, par1, 0)
      );
      this.world.spawnEntity(var3);
   }

   protected void dropFewItems(boolean par1, int par2) {
      for (int var4 = 0; var4 < 2; var4++) {
         this.dropItemRand(Items.ROTTEN_FLESH, 1);
      }

      for (int var41 = 0; var41 < 2; var41++) {
         this.dropItemRand(Items.LEATHER, 1);
      }
   }
}

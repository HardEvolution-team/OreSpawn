package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityCreature;

import net.minecraft.entity.EntityList;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class WormLarge extends EntityMob {

   private int wormsSpawned = 0;



   public WormLarge(World par1World) {

      super(par1World);

      this.setSize(1.55F, 2.5F);

      this.experienceValue = 2050;

      this.noClip = true;

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.WormLarge_stats.attack);

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

      return 1.0F;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("big_splat");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("alo_death");

   }



   public boolean canBePushed() {

      return true;

   }



   protected void collideWithEntity(Entity par1Entity) {

   }



   protected void collideWithNearbyEntities() {

   }



   public int mygetMaxHealth() {

      return OreSpawn.WormLarge_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.WormLarge_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void pointAtEntity(EntityLivingBase e) {

      double d1 = e.posX - this.posX;

      double d2 = e.posZ - this.posZ;

      float d = (float)Math.atan2(d2, d1);

      float f2 = (float)(d * 180.0 / Math.PI) - 90.0F;

      this.rotationYaw = this.rotationYawHead = f2;

   }



   public void onLivingUpdate() {

      EntityPlayer target = null;

      WormMedium worms = null;

      EntityCreature newent = null;

      super.onLivingUpdate();

      worms = (WormMedium)this.world.findNearestEntityWithinAABB(WormMedium.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0), this);

      if (worms == null) {

         target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0), this);

      }



      if ((worms != null || target == null) && OreSpawn.PlayNicely == 0) {

         this.noClip = true;

         Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)(this.posY + 3.5), (int)this.posZ)).getBlock();

         if (bid == Blocks.TALLGRASS) {

            bid = Blocks.AIR;

         }



         if (bid != Blocks.AIR) {

            this.motionY += 0.1F;

            this.posY += 0.05F;

            if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {

               this.setDead();

            }

         }

      } else {

         if (target != null) {

            this.pointAtEntity(target);

         }



         Block bidx = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();

         if (bidx == Blocks.TALLGRASS) {

            bidx = Blocks.AIR;

         }



         if (bidx != Blocks.AIR) {

            this.motionY += 0.25;

            this.posY += 0.1F;

         } else {

            this.noClip = false;

         }

      }



      if (this.noClip) {

         this.motionY -= 0.01;

         this.motionX = 0.0;

         this.motionZ = 0.0;

         this.moveVertical = 0.0F;

      }



      if (!this.world.isRemote) {

         if (this.wormsSpawned == 0) {

            this.wormsSpawned = 1;



            for (int i = 0; i < 20; i++) {

               newent = (EntityCreature)spawnCreature(

                  this.world,

                  "Small Worm",

                  this.posX + this.world.rand.nextInt(6) - this.world.rand.nextInt(6),

                  this.posY,

                  this.posZ + this.world.rand.nextInt(6) - this.world.rand.nextInt(6)

               );

               newent = (EntityCreature)spawnCreature(

                  this.world,

                  "Medium Worm",

                  this.posX + this.world.rand.nextInt(5) - this.world.rand.nextInt(5),

                  this.posY,

                  this.posZ + this.world.rand.nextInt(5) - this.world.rand.nextInt(5)

               );

            }

         }

      }

   }



   public void onUpdate() {

      if (this.isNoDespawnRequired()) {

         this.noClip = false;

      }



      super.onUpdate();

      this.motionY *= 0.85;

   }



   protected void updateAITasks() {

      int bid = 0;

      EntityPlayer target = null;

      WormMedium worms = null;

      if (!this.isDead) {

         if (!this.noClip) {

            super.updateAITasks();

         }



         if (OreSpawn.PlayNicely == 0) {

            worms = (WormMedium)this.world.findNearestEntityWithinAABB(WormMedium.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0), this);

            if (worms == null) {

               target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(8.0, 6.0, 8.0), this);

               if (target != null && target.capabilities.isCreativeMode) {

                  target = null;

               }



               if (target != null) {

                  this.pointAtEntity(target);

                  this.getNavigator().tryMoveToXYZ(target.posX, target.posY, target.posZ, 1.0);

                  if (this.world.rand.nextInt(10) == 1 && this.getDistance(target) < 3.0) {

                     super.attackEntityAsMob(target);

                     if (this.world.rand.nextInt(4) == 1) {

                        ItemStack boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.HEAD);

                        if (boots != null) {

                           target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, net.minecraft.item.ItemStack.EMPTY);

                           bid = boots.getMaxDamage() - boots.getMetadata();

                           if (bid > 10) {

                              bid /= 10;

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

                           boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.CHEST);

                           if (boots != null) {

                              target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, net.minecraft.item.ItemStack.EMPTY);

                              bid = boots.getMaxDamage() - boots.getMetadata();

                              if (bid > 10) {

                                 bid /= 10;

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



                     if (this.world.rand.nextInt(4) == 1) {

                        ItemStack boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.MAINHAND);

                        if (boots != null) {

                           target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, net.minecraft.item.ItemStack.EMPTY);

                           bid = boots.getMaxDamage() - boots.getMetadata();

                           if (bid > 10) {

                              bid /= 10;

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



   protected boolean canTriggerWalking() {

      return false;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

      if (!this.noClip) {

         super.fall(par1, damageMultiplier);

      }

   }



   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {

      if (!this.noClip) {

         super.updateFallState(par1, par3, state, pos);

      }

   }



   public boolean doesEntityNotTriggerPressurePlate() {

      return true;

   }



   public boolean getCanSpawnHere() {

      for (int k = -3; k < 3; k++) {

         for (int j = -3; j < 3; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Large Worm")) {

                     this.wormsSpawned = 1;

                     return true;

                  }

               }

            }

         }

      }



      if (this.posY < 50.0) {

         return false;

      } else {

         WormLarge target = null;

         target = (WormLarge)this.world.findNearestEntityWithinAABB(WormLarge.class, this.getEntityBoundingBox().grow(32.0, 8.0, 32.0), this);

         if (target != null) {

            return false;

         } else {

            for (int ix = -6; ix <= 6; ix++) {

               for (int j = -6; j <= 6; j++) {

                  for (int var13 = -2; var13 >= -8; var13--) {

                     Block bid = this.world.getBlockState(new BlockPos((int)this.posX + ix, (int)this.posY + var13, (int)this.posZ + j)).getBlock();

                     if (bid == Blocks.AIR) {

                        return false;

                     }

                  }

               }

            }



            for (int var10 = -6; var10 <= 6; var10++) {

               for (int j = -6; j <= 6; j++) {

                  for (int var14 = 2; var14 <= 8; var14++) {

                     Block bid = this.world

                        .getBlockState(new BlockPos((int)this.posX + var10, (int)this.posY + var14, (int)this.posZ + j)).getBlock();

                     if (bid != Blocks.AIR) {

                        return false;

                     }

                  }

               }

            }



            return true;

         }

      }

   }



   public void initCreature() {

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      return par1DamageSource.getDamageType().equals("inWall") ? ret : super.attackEntityFrom(par1DamageSource, par2);

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      par1NBTTagCompound.setInteger("wormsSpawned", this.wormsSpawned);

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.wormsSpawned = par1NBTTagCompound.getInteger("wormsSpawned");

   }



   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {

      Entity var8 = null;

      var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);

      if (var8 != null) {

         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);

         par0World.spawnEntity(var8);

      }



      return var8;

   }



   protected Item getDropItem() {

      return Items.ROTTEN_FLESH;

   }



   private void dropItemRand(Item index, int par1) {

      EntityItem var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         this.posY + 2.5 + this.world.rand.nextInt(4),

         this.posZ + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         new ItemStack(index, par1, 0)

      );

      this.world.spawnEntity(var3);

   }



   protected void dropFewItems(boolean par1, int par2) {

      this.dropItemRand(OreSpawn.WormTooth, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);



      for (int var4 = 0; var4 < 6; var4++) {

         this.dropItemRand(Items.ROTTEN_FLESH, 1);

      }



      for (int var41 = 0; var41 < 6; var41++) {

         this.dropItemRand(Items.LEATHER, 1);

      }



      for (int var5 = 0; var5 < 8; var5++) {

         this.dropItemRand(Item.getItemFromBlock(Blocks.DIRT), 1);

      }



      for (int var6 = 0; var6 < 16; var6++) {

         this.dropItemRand(Items.GOLD_NUGGET, 1);

      }



      for (int var7 = 0; var7 < 5; var7++) {

         this.dropItemRand(Items.DIAMOND, 1);

      }



      for (int var8 = 0; var8 < 4; var8++) {

         this.dropItemRand(OreSpawn.UraniumNugget, 1);

      }



      for (int var9 = 0; var9 < 4; var9++) {

         this.dropItemRand(OreSpawn.TitaniumNugget, 1);

      }

   }

}



package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
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
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Spyro extends EntityTameable {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(Spyro.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(Spyro.class, DataSerializers.VARINT);

   private BlockPos currentFlightTarget;
   private GenericTargetSorter TargetSorter = null;
   public int activity = 1;
   private int owner_flying = 0;
   private boolean target_in_sight = false;
   private float moveSpeed = 0.3F;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public Spyro(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.5F);
      this.moveSpeed = 0.3F;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.setSitting(false);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 0.3F, 0.4F));
      this.tasks.addTask(3, new MyEntityAIFollowOwner(this, 1.15F, 12.0F, 2.0F));
      this.tasks.addTask(4, new EntityAITempt(this, 1.25, Items.BEEF, false));
      this.tasks.addTask(5, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(7, new MyEntityAIWander(this, 0.75F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.tasks.addTask(9, new EntityAIMoveIndoors(this));
      this.TargetSorter = new GenericTargetSorter(this);
      this.experienceValue = 35;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0);
   }

   protected void entityInit() {
      super.entityInit();
      this.activity = 1;
      this.dataManager.register(DW_21, this.activity);
      this.dataManager.register(DW_20, 1);
      this.setSitting(false);
      this.setTamed(false);
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("SpyroActivity", this.dataManager.get(DW_21));
      par1NBTTagCompound.setInteger("SpyroFire", this.dataManager.get(DW_20));
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.activity = par1NBTTagCompound.getInteger("SpyroActivity");
      this.dataManager.set(DW_21,  this.activity);
      this.dataManager.set(DW_20,  par1NBTTagCompound.getInteger("SpyroFire"));
   }

   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
      int found = 0;

      for (int i = -dy; i <= dy; i++) {
         for (int j = -dz; j <= dz; j++) {
            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
            if (bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
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
            if (bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
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
            if (bidx == Blocks.LAVA || bidx == Blocks.FLOWING_LAVA) {
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
            if (bidx == Blocks.LAVA || bidx == Blocks.FLOWING_LAVA) {
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
            if (bidxx == Blocks.LAVA || bidxx == Blocks.FLOWING_LAVA) {
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
            if (bidxx == Blocks.LAVA || bidxx == Blocks.FLOWING_LAVA) {
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

   public int getActivity() {
      return this.dataManager.get(DW_21);
   }

   public void setActivity(int par1) {
      this.activity = par1;
      this.dataManager.set(DW_21,  this.activity);
   }

   public int getSpyroFire() {
      return this.dataManager.get(DW_20);
   }

   public void setSpyroFire(int par1) {
      this.dataManager.set(DW_20,  par1);
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public int mygetMaxHealth() {
      return 200;
   }

   public int getSpyroHealth() {
      return (int)this.getHealth();
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if (var2 != null && var2.getCount() <= 0) {
         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
         var2 = null;
      }

      if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.isTamed()) {
            if (!this.world.isRemote) {
               if (this.world.rand.nextInt(2) == 1) {
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
            this.setHealth(this.mygetMaxHealth());
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
         && var2.getItem() == Item.getItemFromBlock(Blocks.ICE)
         && par1EntityPlayer.getDistanceSq(this) < 16.0
         && this.isOwner(par1EntityPlayer)) {
         if (!this.world.isRemote) {
            this.playTameEffect(true);
            this.world.setEntityState(this, (byte)6);
            this.setSpyroFire(0);
            par1EntityPlayer.sendMessage(new TextComponentString("Baby Dragon fireballs extinguished."));
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
         && var2.getItem() == Items.DIAMOND
         && par1EntityPlayer.getDistanceSq(this) < 16.0
         && this.isOwner(par1EntityPlayer)
         && !this.world.isRemote) {
         Entity ent = null;
         Dragon d = null;
         ent = spawnCreature(this.world, "Dragon", this.posX, this.posY, this.posZ);
         if (ent != null) {
            d = (Dragon)ent;
            if (this.isTamed()) {
               d.setTamed(true);
               d.setCustomNameTag(par1EntityPlayer.getUniqueID().toString());
            }

            this.setDead();
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
         && var2.getItem() == Items.FLINT_AND_STEEL
         && par1EntityPlayer.getDistanceSq(this) < 16.0
         && this.isOwner(par1EntityPlayer)) {
         if (!this.world.isRemote) {
            this.playTameEffect(true);
            this.world.setEntityState(this, (byte)6);
            this.setSpyroFire(1);
            par1EntityPlayer.sendMessage(new TextComponentString("Baby Dragon fireballs lit!"));
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

         return true;
      } else {
         return super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND);
      }
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.BEEF;
   }

   protected boolean canDespawn() {
      return this.isNoDespawnRequired() ? false : !this.isTamed();
   }

   public int getTotalArmorValue() {
      return 5;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      if (this.isSitting()) {
         return null;
      } else {
         return this.getActivity() != 2 ? null : OreSpawnSoundEvents.get("roar");
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("cryo_death");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Items.BEEF;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = 0;
      if (this.isTamed()) {
         var3 = this.world.rand.nextInt(4);
         var3++;

         for (int var4 = 0; var4 < var3; var4++) {
            this.dropItem(Items.BEEF, 1);
         }
      }
   }

   protected float getSoundPitch() {
      return this.isChild()
         ? (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.1F + 1.5F
         : (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.1F + 1.0F;
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return !this.world.isDaytime() ? false : !(this.posY < 50.0);
   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }

   public float getAttackStrength(Entity par1Entity) {
      return 4.0F;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      float var2 = this.getAttackStrength(par1Entity);
      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      if (!par1DamageSource.getDamageType().equals("cactus")) {
         ret = super.attackEntityFrom(par1DamageSource, par2);
      }

      return ret;
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (this.isInWater()) {
         this.motionY += 0.07;
      }

      if (!this.world.isRemote) {
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.world.rand.nextInt(100000) == 1 && !this.isNoDespawnRequired()) {
            Entity ent = null;
            Dragon d = null;
            ent = spawnCreature(this.world, "Dragon", this.posX, this.posY, this.posZ);
            if (ent != null) {
               d = (Dragon)ent;
               if (this.isTamed()) {
                  d.setTamed(true);
                  d.setCustomNameTag(this.getCustomNameTag());
               }

               this.setDead();
               return;
            }
         }

         if (this.activity == 2) {
            if (this.posY < this.currentFlightTarget.getY() + 2.0) {
               this.motionY *= 0.7;
            } else if (this.posY > this.currentFlightTarget.getY() - 2.0) {
               this.motionY *= 0.5;
            } else {
               this.motionY *= 0.61;
            }
         }

         if (this.activity == 1 && this.isTamed() && this.getOwner() != null) {
            EntityLivingBase e = this.getOwner();
            if (this.getDistanceSq(e) > 256.0) {
               this.setActivity(2);
            }
         }

         this.do_movement();
      }
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   protected void handleJumpWater() {
      int fly = 0;
      if (this.activity == 1) {
         super.handleJumpWater();
      }

      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (!this.world.isRemote) {
            if (!this.isSitting()) {
               if (this.activity == 0) {
                  this.activity = 1;
               }

               if (this.world.rand.nextInt(20) == 0) {
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

                     if (i >= 6) {
                        i++;
                     }
                  }

                  if (this.closest < 99999) {
                     this.setActivity(1);
                     this.getNavigator().tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.0);
                     if (this.isNotColliding()) {
                        this.heal(1.0F);
                        this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("splash"), 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);
                     }
                  }
               }

               if (this.world.rand.nextInt(100) == 1 && !this.target_in_sight) {
                  this.activity = 1;
                  if (this.world.rand.nextInt(8) == 1) {
                     this.activity = 2;
                  }

                  this.setActivity(this.activity);
               }

               this.owner_flying = 0;
               if (this.isTamed() && this.getOwner() != null) {
                  EntityPlayer e = (EntityPlayer)this.getOwner();
                  if (e.capabilities.isFlying) {
                     fly = 1;
                  }

                  if (fly == 1) {
                     this.owner_flying = 1;
                     this.setActivity(2);
                  }
               }
            }
         }
      }
   }

   protected void updateAITasks() {
      if (!this.isDead) {
         super.updateAITasks();
         if (this.world.rand.nextInt(100) == 1 && this.getHealth() < this.mygetMaxHealth()) {
            this.heal(1.0F);
         }
      }
   }

   private void do_movement() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 50;
      int do_new = 0;
      double ox = 0.0;
      double oy = 0.0;
      double oz = 0.0;
      int has_owner = 0;
      EntityLivingBase e = null;
      if (this.currentFlightTarget == null) {
         do_new = 1;
         this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
      }

      if (!this.isSitting()) {
         if (this.activity != 1) {
            if (this.getActivity() == 2 && this.world.rand.nextInt(300) == 0) {
               do_new = 1;
            }

            if (this.isTamed() && this.getOwner() != null) {
               e = this.getOwner();
               has_owner = 1;
               ox = e.posX;
               oy = e.posY;
               oz = e.posZ;
               if (this.getDistanceSq(e) > 100.0) {
                  do_new = 1;
               }

               if (this.owner_flying != 0 && this.getDistanceSq(e) > 36.0) {
                  do_new = 1;
               }
            }

            if (this.world.rand.nextInt(6) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               e = this.findSomethingToAttack();
               if (e != null) {
                  if (this.isTamed() && this.getHealth() / this.mygetMaxHealth() < 0.25F) {
                     this.setActivity(2);
                     this.target_in_sight = false;
                     do_new = 0;
                     this.currentFlightTarget = new BlockPos((int)(this.posX + (this.posX - e.posX)),
                           (int)(this.posY + 1.0),
                           (int)(this.posZ + (this.posZ - e.posZ))
                        );
                  } else {
                     this.setActivity(2);
                     this.target_in_sight = true;
                     this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                     this.getNavigator().tryMoveToEntityLiving(e, 1.25);
                     do_new = 0;
                     if (this.getDistanceSq(e) < (3.0F + e.width / 2.0F) * (3.0F + e.width / 2.0F)) {
                        this.attackEntityAsMob(e);
                     } else if (this.getDistanceSq(e) < 64.0
                        && !this.isInWater()
                        && (this.getSpyroFire() == 1 && this.world.rand.nextInt(10) == 0 || this.world.rand.nextInt(15) == 1)
                        )
                      {
                        EntitySmallFireball var2 = new EntitySmallFireball(
                           this.world,
                           this,
                           e.posX - this.posX,
                           e.posY + 0.25 - (this.posY + 1.25),
                           e.posZ - this.posZ
                        );
                        var2.setLocationAndAngles(this.posX, this.posY + 1.25, this.posZ, this.rotationYaw, this.rotationPitch);
                        this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(var2);
                     }
                  }
               } else {
                  this.target_in_sight = false;
               }
            }

            if (this.getDistanceSq(currentFlightTarget) < 2.1F
               && this.getActivity() != 3) {
               do_new = 1;
            }

            if (do_new != 0 && !this.target_in_sight) {
               for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
                  int gox = (int)this.posX;
                  int goy = (int)this.posY;
                  int goz = (int)this.posZ;
                  if (has_owner == 1) {
                     gox = (int)ox;
                     goy = (int)oy;
                     goz = (int)oz;
                     if (this.owner_flying == 0) {
                        zdir = this.world.rand.nextInt(4) + 6;
                        xdir = this.world.rand.nextInt(4) + 6;
                     } else {
                        zdir = this.world.rand.nextInt(6);
                        xdir = this.world.rand.nextInt(6);
                     }
                  } else {
                     zdir = this.world.rand.nextInt(5) + 6;
                     xdir = this.world.rand.nextInt(5) + 6;
                  }

                  if (this.world.rand.nextInt(2) == 0) {
                     zdir = -zdir;
                  }

                  if (this.world.rand.nextInt(2) == 0) {
                     xdir = -xdir;
                  }

                  this.currentFlightTarget = new BlockPos(gox + xdir, goy + this.world.rand.nextInt(9 + this.owner_flying * 2) - 4, goz + zdir);
                  bid = this.world
                     .getBlockState(this.currentFlightTarget).getBlock();
                  if (bid == Blocks.AIR
                     && !this.canSeeTarget(
                        this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ()
                     )) {
                     bid = Blocks.STONE;
                  }
               }
            }

            double speed_factor = 0.5;
            double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
            double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
            double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
            if (this.owner_flying != 0) {
               speed_factor = 1.75;
               if (this.isTamed() && this.getOwner() != null) {
                  e = this.getOwner();
                  if (this.getDistanceSq(e) > 49.0) {
                     speed_factor = 3.5;
                  }
               }
            }

            this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.15 * speed_factor;
            this.motionY = this.motionY + (Math.signum(var3) * 0.7 - this.motionY) * 0.21 * speed_factor;
            this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.15 * speed_factor;
            float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
            this.moveVertical = (float)(0.75 * speed_factor);
            this.rotationYaw += var8 / 3.0F;
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
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof Spyro) {
         return false;
      } else {
         return par1EntityLiving instanceof Mothra ? true : par1EntityLiving instanceof EntityMob;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0, 6.0, 12.0));
         Collections.sort(var5, this.TargetSorter);
         Iterator var2 = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;

         while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (this.isSuitableTarget(var4, false) && this.canSeeTarget(var4.posX, var4.posY, var4.posZ)) {
               return var4;
            }
         }

         return null;
      }
   }

   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         ((EntityLiving)var8).playLivingSound();
      }

      return var8;
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

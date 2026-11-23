package com.electrifiedded.orespawn.entity;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.Enchantments;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.enchantment.Enchantment;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityAgeable;

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMate;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAITempt;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.passive.EntityTameable;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.entity.projectile.EntitySmallFireball;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class WaterDragon extends EntityTameable {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(WaterDragon.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int stream_count = 0;

   private int hurt_timer = 0;

   private float moveSpeed = 0.25F;

   private int closest = 99999;

   private int tx = 0;

   private int ty = 0;

   private int tz = 0;



   public WaterDragon(World par1World) {

      super(par1World);

      this.moveSpeed = 0.25F;

      this.setSize(1.25F, 1.9F);

      this.experienceValue = 100;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMate(this, 1.0));

      this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));

      this.tasks.addTask(3, new EntityAITempt(this, 1.2F, Items.FISH, false));

      this.tasks.addTask(4, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(6, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.WaterDragon_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

      if (this.renderdata == null) {

         this.renderdata = new RenderInfo();

      }



      this.renderdata.rf1 = 0.0F;

      this.renderdata.rf2 = 0.0F;

      this.renderdata.rf3 = 0.0F;

      this.renderdata.rf4 = 0.0F;

      this.renderdata.ri1 = 0;

      this.renderdata.ri2 = 0;

      this.renderdata.ri3 = 0;

      this.renderdata.ri4 = 0;

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

      if (var2 != null && var2.getCount() <= 0) {

         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

         var2 = null;

      }



       if (super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND)) {

         return true;

      } else if (var2 != null && var2.getItem() == Items.FISH && par1EntityPlayer.getDistanceSq(this) < 25.0) {

         if (!this.isTamed()) {

            if (!this.world.isRemote) {

               if (this.rand.nextInt(3) == 0) {

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

         && par1EntityPlayer.getDistanceSq(this) < 25.0

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

      } else if (this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 25.0) {

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



   protected boolean canDespawn() {

      if (this.isChild()) {

         this.enablePersistence();

         return false;

      } else {

         return this.isNoDespawnRequired() ? false : !this.isTamed();

      }

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.WaterDragon_stats.health;

   }



   public RenderInfo getRenderInfo() {

      return this.renderdata;

   }



   public void setRenderInfo(RenderInfo r) {

      this.renderdata.rf1 = r.rf1;

      this.renderdata.rf2 = r.rf2;

      this.renderdata.rf3 = r.rf3;

      this.renderdata.rf4 = r.rf4;

      this.renderdata.ri1 = r.ri1;

      this.renderdata.ri2 = r.ri2;

      this.renderdata.ri3 = r.ri3;

      this.renderdata.ri4 = r.ri4;

   }



   public int getTotalArmorValue() {

      return OreSpawn.WaterDragon_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

      if (this.isInWater()) {

         this.moveSpeed = 0.55F;

      } else {

         this.moveSpeed = 0.25F;

      }

   }



   public int getWaterDragonHealth() {

      return (int)this.getHealth();

   }



   public int getAttackStrength(Entity par1Entity) {

      int var2 = 4;

      if (this.world.getDifficulty() == EnumDifficulty.EASY) {

         var2 = 6;

         if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {

            var2 = 8;

         } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {

            var2 = 10;

         }

      }



      return var2;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("waterdragon_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("waterdragon_death");

   }



   protected float getSoundVolume() {

      return 1.0F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.FISH;

   }



   private ItemStack dropItemRand(Item index, int par1) {

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



   protected void dropFewItems(boolean par1, int par2) {

      ItemStack is = null;

      this.dropItemRand(OreSpawn.MyWaterDragonScale, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int var5 = 9 + this.world.rand.nextInt(6);



      for (int var4 = 0; var4 < var5; var4++) {

         this.dropItemRand(Items.FISH, 1);

      }



      int var7 = this.world.rand.nextInt(20);

      switch (var7) {

         case 0:

            is = this.dropItemRand(OreSpawn.MyUltimateAxe, 1);

            break;

         case 1:

            is = this.dropItemRand(Items.IRON_INGOT, 1);

            break;

         case 2:

            is = this.dropItemRand(OreSpawn.MyUltimatePickaxe, 1);

            break;

         case 3:

            is = this.dropItemRand(Items.IRON_SWORD, 1);

            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));

            }

            break;

         case 4:

            is = this.dropItemRand(Items.IRON_SHOVEL, 1);

            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

            }

            break;

         case 5:

            is = this.dropItemRand(Items.IRON_PICKAXE, 1);

            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));

            }

            break;

         case 6:

            is = this.dropItemRand(Items.IRON_AXE, 1);

            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

            }

            break;

         case 7:

            is = this.dropItemRand(Items.IRON_HOE, 1);

            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

            }

            break;

         case 8:

            is = this.dropItemRand(Items.IRON_HELMET, 1);

            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));

            }

            break;

         case 9:

            is = this.dropItemRand(Items.IRON_CHESTPLATE, 1);

            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }

            break;

         case 10:

            is = this.dropItemRand(Items.IRON_LEGGINGS, 1);

            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }

            break;

         case 11:

            is = this.dropItemRand(Items.IRON_BOOTS, 1);

            if (this.world.rand.nextInt(6) == 1) {

               is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));

            }



            if (this.world.rand.nextInt(2) == 1) {

               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

            }

            break;

         case 12:

            is = this.dropItemRand(OreSpawn.MyUltimateShovel, 1);

            break;

         case 13:

            this.dropItemRand(Item.getItemFromBlock(Blocks.IRON_BLOCK), 1);

         case 14:

      }

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawn.WaterDragon_stats.attack);

      if (!var4) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            double ks = 1.1;

            double inair = 0.14;

            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);

            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {

               inair *= 2.0;

            }



            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);

         }



         return true;

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (par1DamageSource.getDamageType().equals("cactus")) {

         return false;

      } else {

         Entity e = par1DamageSource.getTrueSource();

         if (e != null && e instanceof WaterDragon) {

            return false;

         } else if (e != null && e instanceof AttackSquid) {

            return false;

         } else if (e != null && e instanceof WaterBall) {

            return false;

         } else {

            if (this.hurt_timer <= 0) {

               ret = super.attackEntityFrom(par1DamageSource, par2);

               this.hurt_timer = 10;

            }



            if (e != null && e instanceof EntityLiving) {

               if (e instanceof AttackSquid) {

                  return false;

               }



               if (e instanceof WaterDragon) {

                  return false;

               }



               this.setAttackTarget((EntityLiving)e);

               this.setAttackTarget((EntityLivingBase)e);

               this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);

            }



            return ret;

         }

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

         if (this.hurt_timer > 0) {

            this.hurt_timer--;

         }



         if (!this.isInWater() && this.world.rand.nextInt(25) == 0 && !this.isSitting()) {

            this.closest = 99999;

            this.tx = this.ty = this.tz = 0;



            for (int i = 1; i < 12; i++) {

               int j = i;

               if (i > 10) {

                  j = 10;

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

               if (this.world.rand.nextInt(50) == 1) {

                  this.heal(-1.0F);

               }



               if (this.getHealth() <= 0.0F) {

                  this.setDead();

                  return;

               }

            }

         }



         if (this.world.rand.nextInt(200) == 0) {

            this.setAttackTarget(null);

         }



         if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 1) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F)) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.0);

                  this.watercanon(e);

               }

            } else {

               this.setAttacking(0);

            }

         }



         if (this.world.rand.nextInt(100) == 1 && this.isInWater() && this.getHealth() < this.mygetMaxHealth()) {

            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("splash"), 1.5F, this.world.rand.nextFloat() * 0.2F + 0.9F);

            this.heal(1.0F);

         }

      }

   }



   private void watercanon(EntityLivingBase e) {

      double yoff = 1.75;

      double xzoff = 1.5;

      if (this.stream_count > 0) {

         this.setAttacking(2);

         if (this.rand.nextInt(15) == 1) {

            EntitySmallFireball var2 = new EntitySmallFireball(

               this.world,

               this,

               e.posX - this.posX,

               e.posY + 0.75 - (this.posY + yoff),

               e.posZ - this.posZ

            );

            var2.setLocationAndAngles(

               this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),

               this.posY + yoff,

               this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYawHead)),

               this.rotationYaw,

               this.rotationPitch

            );

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

            this.world.spawnEntity(var2);

         }



         WaterBall var2 = new WaterBall(

            this.world,

            e.posX - this.posX,

            e.posY + 0.75 - (this.posY + yoff),

            e.posZ - this.posZ

         );

         var2.setLocationAndAngles(

            this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),

            this.posY + yoff,

            this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),

            this.rotationYawHead,

            this.rotationPitch

         );

         double var3 = e.posX - var2.posX;

         double var5 = e.posY + 0.25 - var2.posY;

         double var7 = e.posZ - var2.posZ;

         float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;

         var2.shoot(var3, var5 + var9, var7, 1.4F, 5.0F);

         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

         this.world.spawnEntity(var2);

         this.stream_count--;

      } else {

         this.setAttacking(0);

      }



      if (this.stream_count <= 0 && this.rand.nextInt(4) == 1) {

         this.stream_count = 8;

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

      } else if (par1EntityLiving instanceof WaterDragon) {

         return false;

      } else if (par1EntityLiving instanceof EntityMob) {

         return true;

      } else if (this.isTamed()) {

         return false;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else {

         return MyUtils.isAttackableNonMob(par1EntityLiving);

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else if (this.isChild()) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(14.0, 4.0, 14.0));

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



   public int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

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

                  if (s != null && s.equalsIgnoreCase("Water Dragon")) {

                     return true;

                  }

               }

            }

         }

      }



      WaterDragon target = null;

      if (this.posY < 50.0) {

         return false;

      } else if (!this.world.isDaytime()) {

         return false;

      } else {

         target = (WaterDragon)this.world.findNearestEntityWithinAABB(WaterDragon.class, this.getEntityBoundingBox().grow(16.0, 5.0, 16.0), this);

         return target == null;

      }

   }



   public EntityAgeable createChild(EntityAgeable entityageable) {

      return this.spawnBabyAnimal(entityageable);

   }



   public WaterDragon spawnBabyAnimal(EntityAgeable par1EntityAgeable) {

      WaterDragon w = new WaterDragon(this.world);

      if (this.isTamed()) {

         this.setCustomNameTag(this.getCustomNameTag());

         w.setTamed(true);

      }



      return w;

   }



   public boolean isWheat(ItemStack par1ItemStack) {

      return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;

   }



   public boolean isBreedingItem(ItemStack par1ItemStack) {

      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;

   }



   public boolean canBreatheUnderwater() {

      return true;

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

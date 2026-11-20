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

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class SeaMonster extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(SeaMonster.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int hurt_timer = 0;

   private float moveSpeed = 0.25F;

   private int closest = 99999;

   private int tx = 0;

   private int ty = 0;

   private int tz = 0;



   public SeaMonster(World par1World) {

      super(par1World);

      this.moveSpeed = 0.25F;

      this.setSize(1.25F, 2.5F);

      this.experienceValue = 150;

      this.isImmuneToFire = true;

      this.isImmuneToFire = false;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.SeaMonster_stats.attack);

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



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.SeaMonster_stats.health;

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

      return OreSpawn.SeaMonster_stats.defense;

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



   public int getSeaMonsterHealth() {

      return (int)this.getHealth();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.rand.nextInt(3) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("seamonster_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("seamonster_hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("seamonster_death");

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

      this.dropItemRand(OreSpawn.SeaMonsterScale, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int var5 = 9 + this.world.rand.nextInt(6);



      for (int var4 = 0; var4 < var5; var4++) {

         this.dropItemRand(Items.FISH, 1);

      }



      int var7 = this.world.rand.nextInt(20);

      switch (var7) {

         case 1:

            is = this.dropItemRand(Items.IRON_INGOT, 1);

         case 2:

         case 12:

         default:

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

         case 13:

            this.dropItemRand(Item.getItemFromBlock(Blocks.IRON_BLOCK), 1);

      }

   }



   public void initCreature() {

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (!super.attackEntityAsMob(par1Entity)) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            double ks = 0.6;

            double inair = 0.1;

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

         if (this.hurt_timer <= 0) {

            ret = super.attackEntityFrom(par1DamageSource, par2);

            this.hurt_timer = 8;

         }



         if (e != null && e instanceof EntityLiving) {

            if (e instanceof SeaMonster) {

               return false;

            }



            this.setAttackTarget((EntityLiving)e);

            this.setAttackTarget((EntityLivingBase)e);

            this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);

         }



         return ret;

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



         if (!this.isInWater() && this.world.rand.nextInt(25) == 0) {

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

               if (this.world.rand.nextInt(40) == 1) {

                  this.heal(-1.0F);

               }



               if (this.getHealth() <= 0.0F) {

                  this.setDead();

                  return;

               }

            }

         }



         if (this.world.rand.nextInt(5) == 1) {

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

               }

            } else {

               this.setAttacking(0);

            }

         }



         if (this.world.rand.nextInt(120) == 1 && this.isInWater() && this.getHealth() < this.mygetMaxHealth()) {

            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("splash"), 1.5F, this.world.rand.nextFloat() * 0.2F + 0.9F);

            this.heal(1.0F);

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

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else if (par1EntityLiving instanceof SeaMonster) {

         return false;

      } else {

         return par1EntityLiving instanceof EntityMob ? true : MyUtils.isAttackableNonMob(par1EntityLiving);

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 4.0, 16.0));

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



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

   }



   public boolean getCanSpawnHere() {

      SeaMonster target = null;



      for (int k = -3; k < 3; k++) {

         for (int j = -3; j < 3; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Sea Monster")) {

                     return true;

                  }

               }

            }

         }

      }



      if (this.posY < 50.0) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else if (!this.isValidLightLevel()) {

         return false;

      } else {

         target = (SeaMonster)this.world.findNearestEntityWithinAABB(SeaMonster.class, this.getEntityBoundingBox().grow(16.0, 5.0, 16.0), this);

         return target == null;

      }

   }



   public boolean canBreatheUnderwater() {

      return true;

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}



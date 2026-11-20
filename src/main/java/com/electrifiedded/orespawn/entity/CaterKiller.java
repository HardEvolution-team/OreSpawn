package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;

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

import net.minecraft.entity.EntityList;

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

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

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class CaterKiller extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(CaterKiller.class, DataSerializers.BYTE);

    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(CaterKiller.class, DataSerializers.VARINT);



   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.35F;

   int foundmob = 0;

   int ticker = 0;

   private int closest = 99999;

   private int tx = 0;

   private int ty = 0;

   private int tz = 0;



   public CaterKiller(World par1World) {

      super(par1World);

      if (OreSpawn.PlayNicely == 0) {

         this.setSize(2.9F, 4.6F);

      } else {

         this.setSize(1.45F, 2.3F);

      }



      this.experienceValue = 200;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.CaterKiller_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

      this.dataManager.register(DW_21, OreSpawn.PlayNicely);

   }



   public int getPlayNicely() {

      return this.dataManager.get(DW_21);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      Entity e = null;

      Boolean ret = super.attackEntityFrom(par1DamageSource, par2);

      e = par1DamageSource.getTrueSource();

      if (e != null && e instanceof EntityLiving) {

         this.setAttackTarget((EntityLiving)e);

      }



      return ret;

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.CaterKiller_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.CaterKiller_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.rand.nextInt(3) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("caterkiller_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("caterkiller_hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("caterkiller_death");

   }



   protected float getSoundVolume() {

      return 1.5F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.BEEF;

   }



   private ItemStack dropItemRand(Item index, int par1) {

      EntityItem var3 = null;

      ItemStack is = new ItemStack(index, par1, 0);

      var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         is

      );

      if (var3 != null) {

         this.world.spawnEntity(var3);

      }



      return is;

   }



   protected void dropFewItems(boolean par1, int par2) {

      ItemStack is = null;

      this.dropItemRand(OreSpawn.CaterKillerJaw, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);



      for (int var4 = 0; var4 < 10; var4++) {

         this.dropItemRand(Items.LEATHER, 1);

      }



      for (int var7 = 0; var7 < 6; var7++) {

         this.dropItemRand(Items.BEEF, 1);

      }



      int i = 1 + this.world.rand.nextInt(5);



      for (int var8 = 0; var8 < i; var8++) {

         int var3 = this.world.rand.nextInt(20);

         switch (var3) {

            case 0:

               is = this.dropItemRand(OreSpawn.MyUltimateSword, 1);

               break;

            case 1:

               is = this.dropItemRand(OreSpawn.MyRuby, 1);

               break;

            case 2:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 1);

               break;

            case 3:

               is = this.dropItemRand(OreSpawn.MyRubySword, 1);

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

               is = this.dropItemRand(OreSpawn.MyRubyShovel, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 5:

               is = this.dropItemRand(OreSpawn.MyRubyPickaxe, 1);

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

               is = this.dropItemRand(OreSpawn.MyRubyAxe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 7:

               is = this.dropItemRand(OreSpawn.MyRubyHoe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 8:

               is = this.dropItemRand(OreSpawn.RubyHelmet, 1);

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

               is = this.dropItemRand(OreSpawn.RubyBody, 1);

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

               is = this.dropItemRand(OreSpawn.RubyLegs, 1);

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

               is = this.dropItemRand(OreSpawn.RubyBoots, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 12:

               is = this.dropItemRand(OreSpawn.MyUltimateBow, 1);

         }

      }



      for (int var9 = 0; var9 < 25; var9++) {

         spawnCreature(this.world, "Butterfly", this.posX, this.posY + 1.0, this.posZ);

      }

   }



   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {

      Entity var8 = null;

      if (par0World == null) {

         return null;

      } else {

         var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);

         if (var8 != null) {

            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);

            par0World.spawnEntity(var8);

            ((EntityLiving)var8).playLivingSound();

         }



         return var8;

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

            double ks = 1.2;

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



   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {

      int found = 0;



      for (int i = -dy; i <= dy; i++) {

         for (int j = -dz; j <= dz; j++) {

            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();

            if (bid == Blocks.LEAVES

               || bid == Blocks.VINE

               || bid == Blocks.LOG

               || bid == OreSpawn.MyDT

               || bid == Blocks.LOG2

               || bid == Blocks.LEAVES2

               || bid == OreSpawn.MyAppleLeaves

               || bid == OreSpawn.MyExperienceLeaves

               || bid == OreSpawn.MyScaryLeaves

               || bid == OreSpawn.MyPeachLeaves

               || bid == OreSpawn.MyCherryLeaves) {

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

            if (bid == Blocks.LEAVES

               || bid == Blocks.VINE

               || bid == Blocks.LOG

               || bid == OreSpawn.MyDT

               || bid == Blocks.LOG2

               || bid == Blocks.LEAVES2

               || bid == OreSpawn.MyAppleLeaves

               || bid == OreSpawn.MyExperienceLeaves

               || bid == OreSpawn.MyScaryLeaves

               || bid == OreSpawn.MyPeachLeaves

               || bid == OreSpawn.MyCherryLeaves) {

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

            if (bidx == Blocks.LEAVES

               || bidx == Blocks.VINE

               || bidx == Blocks.LOG

               || bidx == OreSpawn.MyDT

               || bidx == Blocks.LOG2

               || bidx == Blocks.LEAVES2

               || bidx == OreSpawn.MyAppleLeaves

               || bidx == OreSpawn.MyExperienceLeaves

               || bidx == OreSpawn.MyScaryLeaves

               || bidx == OreSpawn.MyPeachLeaves

               || bidx == OreSpawn.MyCherryLeaves) {

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

            if (bidx == Blocks.LEAVES

               || bidx == Blocks.VINE

               || bidx == Blocks.LOG

               || bidx == OreSpawn.MyDT

               || bidx == Blocks.LOG2

               || bidx == Blocks.LEAVES2

               || bidx == OreSpawn.MyAppleLeaves

               || bidx == OreSpawn.MyExperienceLeaves

               || bidx == OreSpawn.MyScaryLeaves

               || bidx == OreSpawn.MyPeachLeaves

               || bidx == OreSpawn.MyCherryLeaves) {

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

            if (bidxx == Blocks.LEAVES

               || bidxx == Blocks.VINE

               || bidxx == Blocks.LOG

               || bidxx == OreSpawn.MyDT

               || bidxx == Blocks.LOG2

               || bidxx == Blocks.LEAVES2

               || bidxx == OreSpawn.MyAppleLeaves

               || bidxx == OreSpawn.MyExperienceLeaves

               || bidxx == OreSpawn.MyScaryLeaves

               || bidxx == OreSpawn.MyPeachLeaves

               || bidxx == OreSpawn.MyCherryLeaves) {

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

            if (bidxx == Blocks.LEAVES

               || bidxx == Blocks.VINE

               || bidxx == Blocks.LOG

               || bidxx == OreSpawn.MyDT

               || bidxx == Blocks.LOG2

               || bidxx == Blocks.LEAVES2

               || bidxx == OreSpawn.MyAppleLeaves

               || bidxx == OreSpawn.MyExperienceLeaves

               || bidxx == OreSpawn.MyScaryLeaves

               || bidxx == OreSpawn.MyPeachLeaves

               || bidxx == OreSpawn.MyCherryLeaves) {

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

         this.dataManager.set(DW_21,  OreSpawn.PlayNicely);

         if (this.getHealth() + 1.0F < this.getMaxHealth()) {

            this.ticker++;

            if (this.ticker > 2400) {

               spawnCreature(this.world, "Brutalfly", this.posX, this.posY + 4.0, this.posZ);

               this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);



               for (int i = 0; i < 10; i++) {

                  spawnCreature(

                     this.world,

                     "Butterfly",

                     this.posX,

                     this.posY + 1.0 + this.world.rand.nextInt(4),

                     this.posZ

                  );

               }



               this.setDead();

               return;

            }

         }



         if (this.isInWeb) {

            for (int i = -2; i <= 2; i++) {

               for (int j = -1; j < 5; j++) {

                  for (int k = -2; k <= 2; k++) {

                     if (this.world.getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k)).getBlock()

                        == Blocks.WEB) {

                        this.world

                           .setBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k), Blocks.AIR.getDefaultState());

                        this.world.setBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k), world.getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k)).getBlock().getStateFromMeta(0), 3);

                     }

                  }

               }

            }



            this.isInWeb = false;

         }



         if (this.world.rand.nextInt(4) == 0) {

            EntityLivingBase e = this.getAttackTarget();

            if (e != null && !e.isEntityAlive()) {

               this.setAttackTarget(null);

               e = null;

            }



            if (this.world.rand.nextInt(200) == 0) {

               this.setAttackTarget(null);

            }



            if (e == null) {

               e = this.findSomethingToAttack();

            }



            if (e != null) {

               this.foundmob = 1;

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < (5.0F + e.width / 2.0F) * (5.0F + e.width / 2.0F)) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(3) == 0 || this.world.rand.nextInt(4) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.setAttacking(0);

                  this.getNavigator().tryMoveToEntityLiving(e, 1.25);

                  if (this.world.rand.nextInt(4) == 0) {

                     double dx = e.posX;

                     double dz = e.posZ;

                     dx += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0;

                     dz += (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0;



                     for (int i = 2; i > -2; i--) {

                        if (this.world.getBlockState(new BlockPos((int)dx, (int)e.posY + i + 1, (int)dz)).getBlock() == Blocks.AIR

                           && this.world.getBlockState(new BlockPos((int)dx, (int)e.posY + i, (int)dz)).getBlock() != Blocks.AIR) {

                           this.world.setBlockState(new BlockPos((int)dx, (int)e.posY + i + 1, (int)dz), Blocks.WEB.getDefaultState());

                           break;

                        }

                     }

                  }

               }

            } else {

               this.setAttacking(0);

               this.foundmob = 0;

            }

         }



         if ((

               this.world.rand.nextInt(8) == 0 && this.getHealth() < this.mygetMaxHealth()

                  || this.world.rand.nextInt(30) == 0

            )

            && OreSpawn.PlayNicely == 0) {

            this.closest = 99999;

            this.tx = this.ty = this.tz = 0;



            for (int ix = 1; ix < 13; ix++) {

               int j = ix;

               if (ix > 9) {

                  j = 9;

               }



               if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, ix, j, ix)) {

                  break;

               }



               if (ix >= 9) {

                  ix++;

               }

            }



            if (this.closest < 99999) {

               if (this.foundmob == 0) {

                  this.getNavigator().tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);

               }



               if (this.closest < 81) {

                  if (this.world.getGameRules().getBoolean("mobGriefing")) {

                     this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getStateFromMeta(0), 2);

                  }



                  this.heal(2.0F);

                  if (this.world.rand.nextInt(20) == 1) {

                     this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.player.burp"), 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);

                  }

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

      } else if (!this.MyCanSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else if (par1EntityLiving instanceof CaterKiller) {

         return false;

      } else {

         return par1EntityLiving instanceof EntityMob ? true : MyUtils.isAttackableNonMob(par1EntityLiving);

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(20.0, 8.0, 20.0));

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



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

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

                  if (s != null && s.equalsIgnoreCase("CaterKiller")) {

                     return true;

                  }

               }

            }

         }

      }



      if (this.posY < 50.0) {

         return false;

      } else if (this.world.rand.nextInt(10) != 0) {

         return false;

      } else if (!this.world.isDaytime()) {

         return false;

      } else {

         for (int var10 = -1; var10 < 2; var10++) {

            for (int j = -1; j < 2; j++) {

               for (int ix = 1; ix < 5; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR

                     && bid != Blocks.LEAVES

                     && bid != Blocks.LEAVES2

                     && bid != Blocks.LOG

                     && bid != Blocks.LOG2) {

                     return false;

                  }

               }

            }

         }



         CaterKiller target = null;

         target = (CaterKiller)this.world.findNearestEntityWithinAABB(CaterKiller.class, this.getEntityBoundingBox().grow(48.0, 16.0, 48.0), this);

         return target == null;

      }

   }



   public boolean MyCanSee(EntityLivingBase e) {

      double xzoff = 2.5;

      int nblks = 10;

      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));

      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));

      float startx = (float)cx;

      float starty = (float)(this.posY + 3.0);

      float startz = (float)cz;

      float dx = (float)((e.posX - startx) / 10.0);

      float dy = (float)((e.posY + e.height / 2.0F - starty) / 10.0);

      float dz = (float)((e.posZ - startz) / 10.0);

      if (Math.abs(dx) > 1.0) {

         dy /= Math.abs(dx);

         dz /= Math.abs(dx);

         nblks = (int)(nblks * Math.abs(dx));

         if (dx > 1.0F) {

            dx = 1.0F;

         }



         if (dx < -1.0F) {

            dx = -1.0F;

         }

      }



      if (Math.abs(dy) > 1.0) {

         dx /= Math.abs(dy);

         dz /= Math.abs(dy);

         nblks = (int)(nblks * Math.abs(dy));

         if (dy > 1.0F) {

            dy = 1.0F;

         }



         if (dy < -1.0F) {

            dy = -1.0F;

         }

      }



      if (Math.abs(dz) > 1.0) {

         dy /= Math.abs(dz);

         dx /= Math.abs(dz);

         nblks = (int)(nblks * Math.abs(dz));

         if (dz > 1.0F) {

            dz = 1.0F;

         }



         if (dz < -1.0F) {

            dz = -1.0F;

         }

      }



      for (int i = 0; i < nblks; i++) {

         startx += dx;

         starty += dy;

         startz += dz;

         Block bid = this.world.getBlockState(new BlockPos((int)startx, (int)starty, (int)startz)).getBlock();

         if (bid != Blocks.AIR && bid != Blocks.WEB && bid != Blocks.TALLGRASS && bid != Blocks.LEAVES) {

            return false;

         }

      }



      return true;

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}



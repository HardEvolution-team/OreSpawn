package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.Enchantments;

import net.minecraft.init.SoundEvents;

import net.minecraft.init.MobEffects;

import net.minecraft.util.SoundEvent;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.enchantment.Enchantment;

import net.minecraft.entity.Entity;

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

import net.minecraft.potion.Potion;

import net.minecraft.potion.PotionEffect;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class Basilisk extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Basilisk.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private int hurt_timer = 0;

   private float moveSpeed = 0.4F;



   public Basilisk(World par1World) {

      super(par1World);

      this.setSize(1.6F, 3.5F);

      this.experienceValue = 150;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 20, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Basilisk_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.Basilisk_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.Basilisk_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   protected void jump() {

      this.motionY += 0.25;

      super.jump();

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

      if (!this.isDead) {

         if (this.rand.nextInt(200) == 0) {

            this.heal(1.0F);

         }

      }

   }



   public int getBasiliskHealth() {

      return (int)this.getHealth();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.rand.nextInt(2) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("basilisk_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("alo_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("emperorscorpion_death");

   }



   protected float getSoundVolume() {

      return 1.0F;

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

         this.posX + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         is

      );

      if (var3 != null) {

         this.world.spawnEntity(var3);

      }



      return is;

   }



   protected void dropFewItems(boolean par1, int par2) {

      ItemStack is = null;

      this.dropItemRand(OreSpawn.MyBasiliskScale, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int i = 12 + this.world.rand.nextInt(6);



      for (int var4 = 0; var4 < i; var4++) {

         this.dropItemRand(Items.EMERALD, 1);

      }



      i = 8 + this.world.rand.nextInt(5);



      for (int var7 = 0; var7 < i; var7++) {

         this.dropItemRand(Items.CHICKEN, 1);

      }



      i = 3 + this.world.rand.nextInt(5);



      for (int var8 = 0; var8 < i; var8++) {

         int var3 = this.world.rand.nextInt(15);

         switch (var3) {

            case 1:

               is = this.dropItemRand(Items.EMERALD, 1);

               break;

            case 2:

               is = this.dropItemRand(Item.getItemFromBlock(Blocks.EMERALD_BLOCK), 1);

               break;

            case 3:

               is = this.dropItemRand(OreSpawn.MyEmeraldSword, 1);

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

               is = this.dropItemRand(OreSpawn.MyEmeraldShovel, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 5:

               is = this.dropItemRand(OreSpawn.MyEmeraldPickaxe, 1);

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

               is = this.dropItemRand(OreSpawn.MyEmeraldAxe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 7:

               is = this.dropItemRand(OreSpawn.MyEmeraldHoe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 8:

               is = this.dropItemRand(OreSpawn.EmeraldHelmet, 1);

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

               is = this.dropItemRand(OreSpawn.EmeraldBody, 1);

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

               is = this.dropItemRand(OreSpawn.EmeraldLegs, 1);

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

               is = this.dropItemRand(OreSpawn.EmeraldBoots, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

         }

      }

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (!super.attackEntityAsMob(par1Entity)) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            int var2 = 8;

            if (this.world.getDifficulty() == EnumDifficulty.EASY) {

               var2 = 10;

            }



            if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {

               var2 = 12;

            } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {

               var2 = 14;

            }



            if (this.world.rand.nextInt(3) == 0) {

               ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 20, 0));

            }



            double ks = 1.5;

            double inair = 0.15;

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

      if (this.hurt_timer > 0) {

         return false;

      } else {

         this.hurt_timer = 30;

         return super.attackEntityFrom(par1DamageSource, par2);

      }

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.hurt_timer > 0) {

            this.hurt_timer--;

         }



         if (this.world.rand.nextInt(5) == 0) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(3) == 0 || this.world.rand.nextInt(4) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.25);

               }



               if (e instanceof EntityLivingBase) {

                  e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 5));

               }

            } else {

               this.setAttacking(0);

            }

         }



         if (this.world.rand.nextInt(75) == 1 && this.getHealth() < this.mygetMaxHealth()) {

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

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof Basilisk) {

         return false;

      } else if (par1EntityLiving instanceof LeafMonster) {

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

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(24.0, 7.0, 24.0));

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

                  if (s != null && s.equalsIgnoreCase("Basilisk")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else {

         for (int var10 = -1; var10 < 2; var10++) {

            for (int j = -1; j < 2; j++) {

               for (int ix = 1; ix < 5; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         Basilisk target = null;

         target = (Basilisk)this.world.findNearestEntityWithinAABB(Basilisk.class, this.getEntityBoundingBox().grow(20.0, 6.0, 20.0), this);

         return target == null;

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

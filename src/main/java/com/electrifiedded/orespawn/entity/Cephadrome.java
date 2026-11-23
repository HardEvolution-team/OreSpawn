package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.math.BlockPos;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.Enchantments;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import net.minecraftforge.fml.relauncher.Side;

import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.enchantment.Enchantment;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityCreature;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.boss.EntityDragon;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.passive.EntityTameable;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class Cephadrome extends EntityCreature {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Cephadrome.class, DataSerializers.BYTE);

    private static final DataParameter<Byte> DW_21 = EntityDataManager.<Byte>createKey(Cephadrome.class, DataSerializers.BYTE);



   private int boatPosRotationIncrements;

   private double boatX;

   private double boatY;

   private double boatZ;

   private double boatYaw;

   private double boatPitch;

   private double boatYawHead;

   private int damage_counter = 100;

   private int updateit = 1;

   private int color = 1;

   private int playing = 0;

   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int hurt_timer = 0;

   private int wasfed;

   private int shouldattack = 0;

   private int wing_sound = 0;

   private int hit_by_player = 0;

   private int badmood = 0;

   private float moveSpeed = 0.25F;



   public Cephadrome(World par1World) {

      super(par1World);

      this.setSize(2.5F, 2.25F);

      this.experienceValue = 200;

      this.isImmuneToFire = true;

      this.isImmuneToFire = false;

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 9.0F));

      this.tasks.addTask(3, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

      if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

   }



   public Cephadrome(World par1World, double par2, double par4, double par6) {

      this(par1World);

      this.setPosition(par2, par4 + this.getYOffset(), par6);

      this.motionX = 0.0;

      this.motionY = 0.0;

      this.motionZ = 0.0;

      this.prevPosX = par2;

      this.prevPosY = par4;

      this.prevPosZ = par6;

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(70.0);

   }



   public boolean shouldRiderSit() {

      return true;

   }



   public int getTrackingRange() {

      return 128;

   }



   public int getUpdateFrequency() {

      return 10;

   }



   public boolean sendsVelocityUpdates() {

      return true;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

   }



   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {

   }



   protected boolean canTriggerWalking() {

      return true;

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

      this.dataManager.register(DW_21, (byte)0);

      this.setActivity(0);

      this.setAttacking(0);

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



   public int mygetMaxHealth() {

      return 300;

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

      return 16;

   }



   protected void jump() {

      super.jump();

      this.motionY += 0.1;

   }



   public boolean ableToCauseSkullDrop() {

      return true;

   }



   @Override



   public net.minecraft.util.SoundEvent getAmbientSound() {

       return this.getActivity() != 1 && this.rand.nextInt(6) == 1 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mothrawings") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("alo_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("alo_death");

   }



   protected float getSoundVolume() {

      return 1.5F;

   }



   public float getSoundPitch() {

      return 1.0F;

   }



   public boolean canBePushed() {

      return false;

   }



   public double getMountedYOffset() {

      return 2.5;

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

      int i = 4 + this.world.rand.nextInt(6);



      for (int var4 = 0; var4 < i; var4++) {

         this.dropItemRand(OreSpawn.UraniumNugget, 1);

      }



      i = 4 + this.world.rand.nextInt(6);



      for (int var7 = 0; var7 < i; var7++) {

         this.dropItemRand(OreSpawn.TitaniumNugget, 1);

      }



      i = 1 + this.world.rand.nextInt(5);



      for (int var8 = 0; var8 < i; var8++) {

         int var3 = this.world.rand.nextInt(20);

         switch (var3) {

            case 0: {

               ItemStack is = this.dropItemRand(OreSpawn.MyRubySword, 1);

               break;

            }

            case 1:

               ItemStack var21 = this.dropItemRand(Items.DIAMOND, 1);

               break;

            case 2:

               ItemStack var20 = this.dropItemRand(OreSpawn.MyThunderStaff, 1);

               break;

            case 3:

               ItemStack var19 = this.dropItemRand(OreSpawn.MyRubySword, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var19.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 4:

               ItemStack var18 = this.dropItemRand(OreSpawn.MyRubyShovel, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var18.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var18.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 5:

               ItemStack var17 = this.dropItemRand(OreSpawn.MyRubyPickaxe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var17.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var17.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var17.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 6:

               ItemStack var16 = this.dropItemRand(OreSpawn.MyRubyAxe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var16.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var16.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 7:

               ItemStack var15 = this.dropItemRand(OreSpawn.MyRubyHoe, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var15.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var15.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 8:

               ItemStack var14 = this.dropItemRand(OreSpawn.RubyHelmet, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var14.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 9:

               ItemStack var13 = this.dropItemRand(OreSpawn.RubyBody, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var13.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 10:

               ItemStack var12 = this.dropItemRand(OreSpawn.RubyLegs, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var12.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 11:

               ItemStack var11 = this.dropItemRand(OreSpawn.RubyBoots, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var11.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var11.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 12:

            case 13:

            case 14:

            case 15:

            case 16:

            case 17: {

               ItemStack is = this.dropItemRand(OreSpawn.MyRuby, 1);

            }

         }

      }

   }



   public int getCephadromeHealth() {

      return (int)this.getHealth();

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      double ks = 2.5;

      double inair = 0.35;

      float iskraken = 1.0F;

      boolean ret = false;

      if (par1Entity != null && par1Entity instanceof EntityDragon) {

         EntityDragon dr = (EntityDragon)par1Entity;

         DamageSource var21 = null;

         var21 = DamageSource.causeExplosionDamage((net.minecraft.entity.EntityLivingBase)null);

         var21.setExplosion();

         if (this.world.rand.nextInt(6) == 1) {

            dr.attackEntityFromPart(dr.dragonPartHead, var21, 70.0F);

         } else {

            dr.attackEntityFromPart(dr.dragonPartBody, var21, 70.0F);

         }



         ret = true;

      } else if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

         if (par1Entity instanceof Kraken) {

            iskraken = 1.5F;

         }



         ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), iskraken * 70.0F);

         float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);

         if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {

            inair *= 2.0;

         }



         par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);

      }



      return ret;

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (this.hurt_timer > 0) {

         return false;

      } else {

         if (!par1DamageSource.getDamageType().equals("cactus")) {

            ret = super.attackEntityFrom(par1DamageSource, par2);

            this.hurt_timer = 25;

            Entity e = par1DamageSource.getTrueSource();

            if (e != null && e instanceof EntityLivingBase) {

               this.setAttackTarget((EntityLivingBase)e);

               this.setAttackTarget((EntityLivingBase)e);

               this.getNavigator().tryMoveToEntityLiving((EntityLivingBase)e, 1.2);

               ret = true;

            }



            if (e != null && e instanceof EntityPlayer && this.getHealth() < this.getMaxHealth() * 9.0F / 10.0F) {

               this.hit_by_player = 1;

            }

         }



         return ret;

      }

   }



   public double getHorizontalDistanceSqToEntity(Entity par1Entity) {

      double d0 = this.posX - par1Entity.posX;

      double d2 = this.posZ - par1Entity.posZ;

      return d0 * d0 + d2 * d2;

   }



   public void updateAITasks() {

      EntityLivingBase e = null;

      double maxdist = 10.0;

      if (!this.isDead) {

         if (this.updateit > 0) {

            this.updateit--;

         }



         if (this.hurt_timer > 0) {

            this.hurt_timer--;

         }



         if (this.updateit <= 0 && !this.world.isRemote) {

            this.updateit = 30;

            if (this.getControllingPassenger() != null) {

               this.setActivity(1);

            } else {

               this.setActivity(0);

            }

         }



         if (this.world.rand.nextInt(100) == 1 && this.getHealth() < this.mygetMaxHealth()) {

            this.heal(2.0F);

         }



         if (this.getActivity() == 0) {

            super.updateAITasks();

         }



         if (this.world.rand.nextInt(7) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {

            e = this.getAttackTarget();

            if (e != null && !e.isEntityAlive()) {

               this.setAttackTarget(null);

               e = null;

            }



            if (e == null) {

               e = this.findSomethingToAttack();

            }



            if (e != null) {

               if (this.getActivity() == 0) {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.7);

                  maxdist = 6.0;

               }



               this.faceEntity(e, 10.0F, 10.0F);

               this.setAttacking(1);

               if (this.getDistanceSq(e) < (maxdist + e.width / 2.0F) * (maxdist + e.width / 2.0F)) {

                  this.attackEntityAsMob(e);

               } else if (e instanceof Kraken

                  && this.getHorizontalDistanceSqToEntity(e) < (maxdist + e.width / 2.0F) * (maxdist + e.width / 2.0F)) {

                  this.attackEntityAsMob(e);

               }

            } else if (this.getAttacking() != 0) {

               this.setAttacking(0);

            }

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

      } else if (par1EntityLiving instanceof Cephadrome) {

         return false;

      } else if (par1EntityLiving instanceof EntityMob) {

         return true;

      } else if (par1EntityLiving instanceof Mothra) {

         return true;

      } else if (par1EntityLiving instanceof Leon) {

         EntityTameable et = (EntityTameable)par1EntityLiving;

         return !et.isTamed();

      } else if (par1EntityLiving instanceof GammaMetroid) {

         EntityTameable et = (EntityTameable)par1EntityLiving;

         return !et.isTamed();

      } else if (par1EntityLiving instanceof WaterDragon) {

         EntityTameable et = (EntityTameable)par1EntityLiving;

         return !et.isTamed();

      } else if (par1EntityLiving instanceof EntityDragon) {

         return true;

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         if (p.capabilities.isCreativeMode) {

            return false;

         } else if (this.hit_by_player != 0) {

            return true;

         } else if (this.badmood != 0) {

            return true;

         } else if (this.shouldattack > 0) {

            this.shouldattack = 0;

            return true;

         } else {

            return false;

         }

      } else {

         return false;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 20.0, 16.0));

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

                  if (s != null && s.equalsIgnoreCase("Cephadrome")) {

                     this.badmood = 1;

                     return true;

                  }

               }

            }

         }

      }



      if (!this.world.isDaytime()) {

         return false;

      } else if (this.posY < 50.0) {

         return false;

      } else {

         for (int var10 = -2; var10 < 2; var10++) {

            for (int j = -2; j < 2; j++) {

               for (int ix = 1; ix < 5; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         Cephadrome target = null;

         target = (Cephadrome)this.world.findNearestEntityWithinAABB(Cephadrome.class, this.getEntityBoundingBox().grow(16.0, 6.0, 16.0), this);

         return target == null;

      }

   }



   @SideOnly(Side.CLIENT)

   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {

      super.setPositionAndRotationDirect((double)par1, (double)par3, (double)par5, (float)par7, (float)par8, par9, true);

      this.boatPosRotationIncrements = par9;

      this.boatX = par1;

      this.boatY = par3;

      this.boatZ = par5;

      this.boatYaw = par7;

      this.boatPitch = par8;

      this.boatYawHead = par7;

   }



   @SideOnly(Side.CLIENT)

   public void setVelocity(double par1, double par3, double par5) {

      super.setVelocity(par1, par3, par5);

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

      if (this.getActivity() == 1) {

         this.wing_sound++;

         if (this.wing_sound > 22) {

            if (!this.world.isRemote) {

                this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mothrawings"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 1.0F);

            }



            this.wing_sound = 0;

         }

      }



      if (OreSpawn.PlayNicely == 0) {

         this.wasfed = 1;

      }

   }



   public void onLivingUpdate() {

      List list = null;

      Entity listEntity = null;

      double d6 = this.rand.nextFloat() * 2.0F - 1.0F;

      double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;

      double obstruction_factor = 0.0;

      double relative_g = 0.0;

      double max_speed = 1.15;

      double gh = 1.0;

      double rt = 0.0;

      double pi = 3.1415926545;

      double deltav = 0.0;

      int dist = 2;

      if (this.getActivity() == 0) {

         super.onLivingUpdate();

      } else if (this.isDead) {

         super.onLivingUpdate();

         return;

      }



      if (!this.isDead) {

         if (this.world.isRemote) {

            if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {

               double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;

               double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;

               double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;

               this.setPosition(d4, d5, d11);

               this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);

               double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);

               if (this.getControllingPassenger() != null) {

                  d10 = MathHelper.wrapDegrees((double)this.getControllingPassenger().rotationYaw - this.rotationYaw);

               }



               this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);

               this.setRotation(this.rotationYaw, this.rotationPitch);

               this.boatPosRotationIncrements--;

            }

         } else if (this.getActivity() != 0) {

            if (this.getControllingPassenger() != null) {

               EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();

               if (this.motionX < -2.0) {

                  this.motionX = -2.0;

               }



               if (this.motionX > 2.0) {

                  this.motionX = 2.0;

               }



               if (this.motionZ < -2.0) {

                  this.motionZ = -2.0;

               }



               if (this.motionZ > 2.0) {

                  this.motionZ = 2.0;

               }



               double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

               gh = 1.55;

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();

               if (bid != Blocks.AIR) {

                  this.motionY += 0.07;

                  this.posY += 0.1;

               } else {

                  this.motionY -= 0.018;

               }



               obstruction_factor = 0.0;

               int var85 = 2;

               var85 += (int)(velocity * 6.0);



               for (int k = 1; k < var85; k++) {

                  for (int i = 1; i < var85 * 2; i++) {

                     double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));

                     double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));

                     bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + dz))).getBlock();

                     if (bid != Blocks.AIR) {

                        obstruction_factor += 0.04;

                     }

                  }

               }



               this.motionY += obstruction_factor * 0.09;

               this.posY += obstruction_factor * 0.09;

               if (this.motionY > 2.0) {

                  this.motionY = 2.0;

               }



               double d4 = this.getControllingPassenger().rotationYaw;

               d4 %= 360.0;



               while (d4 < 0.0) {

                  d4 += 360.0;

               }



               double d5 = this.rotationYaw;

               d5 %= 360.0;



               while (d5 < 0.0) {

                  d5 += 360.0;

               }



               relative_g = (d4 - d5) % 180.0;



               while (relative_g < 0.0) {

                  relative_g += 180.0;

               }



               if (relative_g > 90.0) {

                  relative_g -= 180.0;

               }



               if (velocity > 0.1) {

                  d4 = 1.5 - velocity;

                  d4 = Math.abs(d4);

                  if (d4 < 0.01) {

                     d4 = 0.01;

                  }



                  if (d4 > 0.9) {

                     d4 = 0.9;

                  }



                  this.rotationYaw = this.getControllingPassenger().rotationYaw + (float)(relative_g * d4);

               } else {

                  this.rotationYaw = this.getControllingPassenger().rotationYaw;

               }



               relative_g = Math.abs(relative_g) * velocity;

               if (relative_g > 50.0) {

                  relative_g = 0.0;

               }



               if (this.motionY > 0.0) {

                  this.rotationPitch = 360.0F - 2.0F * (float)velocity;

               } else {

                  this.rotationPitch = 2.0F * (float)velocity;

               }



               this.setRotation(this.rotationYaw, this.rotationPitch);

               double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

               double rr = Math.atan2(this.getControllingPassenger().motionZ, this.getControllingPassenger().motionX);

               double rhm = Math.atan2(this.motionZ, this.motionX);

               double rhdir = Math.toRadians((this.getControllingPassenger().rotationYaw + 90.0F) % 360.0F);

               rt = 0.0;

               pi = 3.1415926545;

               deltav = 0.0;

               double im = com.electrifiedded.orespawn.network.RiderInputState.getForward(pp);

                if (com.electrifiedded.orespawn.network.RiderInputState.isFlyingUp(pp)) {

                  this.motionY += 0.04;

                  this.motionY += velocity * 0.05;

               }



               double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);

               if (rdv > pi) {

                  rdv -= pi * 2.0;

               }



               rdv = Math.abs(rdv);

               if (Math.abs(newvelocity) < 0.01) {

                  rdv = 0.0;

               }



               if (rdv > 1.5) {

                  newvelocity = -newvelocity;

               }



               if (Math.abs(im) > 0.001F) {

                  if (im > 0.0) {

                     deltav = 0.03;

                     if (max_speed > 0.85) {

                        deltav += 0.05;

                     }

                  } else {

                     max_speed = 0.35;

                     deltav = -0.03;

                  }



                  newvelocity += deltav;

                  if (newvelocity >= 0.0) {

                     if (newvelocity > max_speed) {

                        newvelocity = max_speed;

                     }



                     this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;

                     this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;

                  } else {

                     if (newvelocity < -max_speed) {

                        newvelocity = -max_speed;

                     }



                     newvelocity = -newvelocity;

                     this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;

                     this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;

                  }

               } else if (newvelocity >= 0.0) {

                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;

                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;

               } else {

                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);

                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);

               }

            }



            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            this.motionX *= 0.985;

            this.motionY *= 0.94;

            this.motionZ *= 0.985;

            if (!this.world.isRemote) {

               list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2.25, 2.0, 2.25));

               if (list != null && !list.isEmpty()) {

                  for (int l = 0; l < list.size(); l++) {

                     listEntity = (Entity)list.get(l);

                     if (listEntity != this.getControllingPassenger() && !listEntity.isDead && listEntity.canBePushed()) {

                        listEntity.applyEntityCollision(this);

                     }

                  }

               }

            }



            if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {

               if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();

            }

         }



         if (this.getActivity() == 1) {

            this.updateAITasks();

         }

      }

   }



   public void updateRiderPosition() {

      if (this.getControllingPassenger() != null) {

         float f = 0.75F;

         this.getControllingPassenger()

            .setPosition(

               this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),

               this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(),

               this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw))

            );

      }

   }



   protected void playTameEffect(boolean par1) {

      net.minecraft.util.EnumParticleTypes s = net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK;

      if (!par1) {

         s = net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL;

      }



      for (int i = 0; i < 20; i++) {

         double d0 = this.rand.nextGaussian() * 0.08;

         double d1 = this.rand.nextGaussian() * 0.08;

         double d2 = this.rand.nextGaussian() * 0.08;

         this.world

            .spawnParticle(

               s,

               this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,

               this.posY + 0.5 + this.rand.nextFloat() * 1.5,

               this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 2.5F,

               d0,

               d1,

               d2

            );

      }

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

      if (var2 != null && var2.getCount() <= 0) {

         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

         var2 = null;

      }



      if (var2 != null

         && (var2.getItem() == Items.BEEF || var2.getItem() == Items.CHICKEN || var2.getItem() == Items.PORKCHOP)

         && par1EntityPlayer.getDistanceSq(this) < 25.0) {

         if (!this.world.isRemote) {

            this.heal(this.mygetMaxHealth() - this.getHealth());

         }



         this.wasfed = 1;

         this.shouldattack = 0;

         this.playTameEffect(true);

         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

               ItemStack var3 = null;

            }

         }



         return false;

      } else if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger() != par1EntityPlayer) {

         return true;

      } else {

         if (var2 == null && par1EntityPlayer.getDistanceSq(this) < 25.0 && !this.world.isRemote) {

            if (this.wasfed == 0) {

               this.getNavigator().tryMoveToEntityLiving(par1EntityPlayer, 1.2);

               this.shouldattack = 1;

               return false;

            }



            par1EntityPlayer.startRiding(this);

            this.wasfed = 0;

         }



         return true;

      }

   }



   public int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public void setAttacking(int par1) {

      if (this.world == null || !this.world.isRemote) {

         this.dataManager.set(DW_20,  (byte)par1);

      }

   }



   public int getActivity() {

      return this.dataManager.get(DW_21);

   }



   public void setActivity(int par1) {

      if (this.world == null || !this.world.isRemote) {

         this.dataManager.set(DW_21,  (byte)par1);

      }

   }



   protected boolean canDespawn() {

      return this.isNoDespawnRequired() ? false : this.getControllingPassenger() == null;

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      par1NBTTagCompound.setInteger("CephaWasFed", this.wasfed);

      par1NBTTagCompound.setInteger("CephaAttacking", this.getAttacking());

      par1NBTTagCompound.setInteger("CephaActivity", this.getActivity());

      par1NBTTagCompound.setInteger("CephaHitByPlayer", this.hit_by_player);

      par1NBTTagCompound.setInteger("CephaBadMood", this.badmood);

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.wasfed = par1NBTTagCompound.getInteger("CephaWasFed");

      this.hit_by_player = par1NBTTagCompound.getInteger("CephaHitByPlayer");

      this.badmood = par1NBTTagCompound.getInteger("CephaBadMood");

      this.setAttacking(par1NBTTagCompound.getInteger("CephaAttacking"));

      this.setActivity(par1NBTTagCompound.getInteger("CephaActivity"));

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

    /** 1.12 only reports a controlling passenger when asked to; legacy code expects the riding player. */
    @Override
    public net.minecraft.entity.Entity getControllingPassenger() {
        for (net.minecraft.entity.Entity passenger : this.getPassengers()) {
            if (passenger instanceof net.minecraft.entity.player.EntityPlayer) return passenger;
        }
        return null;
    }

    /** The server simulates this mount, as in 1.7, and the client follows its position updates. */
    @Override
    public boolean canPassengerSteer() {
        return false;
    }

    @Override
    public void updatePassenger(net.minecraft.entity.Entity passenger) {
        if (this.isPassenger(passenger) && passenger == this.getControllingPassenger()) {
            this.updateRiderPosition();
        } else {
            super.updatePassenger(passenger);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int increments, boolean teleport) {
        this.setPositionAndRotation2(x, y, z, yaw, pitch, increments);
    }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

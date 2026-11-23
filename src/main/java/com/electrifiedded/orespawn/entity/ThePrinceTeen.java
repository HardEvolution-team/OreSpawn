package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class ThePrinceTeen extends EntityTameable {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(ThePrinceTeen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(ThePrinceTeen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_22 = EntityDataManager.<Integer>createKey(ThePrinceTeen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_23 = EntityDataManager.<Integer>createKey(ThePrinceTeen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_24 = EntityDataManager.<Integer>createKey(ThePrinceTeen.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_25 = EntityDataManager.<Integer>createKey(ThePrinceTeen.class, DataSerializers.VARINT);

   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   private double boatYawHead;
   private int updateit = 1;
   private int playing = 0;
   private GenericTargetSorter TargetSorter = null;
   private RenderInfo renderdata = new RenderInfo();
   private int hurt_timer = 0;
   private int wing_sound = 0;
   private BlockPos currentFlightTarget = null;
   private boolean target_in_sight = false;
   private int owner_flying = 0;
   private int flyaway = 0;
   private float moveSpeed = 0.32F;
   private float deltasmooth = 0.0F;
   private int which_attack = 0;
   private int fireballticker = 0;
   private int head1ext = 0;
   private int head2ext = 0;
   private int head3ext = 0;
   private int head1dir = 1;
   private int head2dir = 1;
   private int head3dir = 1;
   private int kill_count = 0;
   private int day_count = 0;
   private int is_day = 0;

   public ThePrinceTeen(World par1World) {
      super(par1World);
      this.setSize(3.25F, 4.25F);
      this.experienceValue = 300;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.setSitting(false);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 1.1F, 12.0F, 2.0F));
      this.tasks.addTask(2, new EntityAITempt(this, 1.25, Items.BEEF, false));
      this.tasks.addTask(3, new MyEntityAIWander(this, 0.75F));
      this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 9.0F));
      this.tasks.addTask(5, new EntityAILookIdle(this));
      this.tasks.addTask(6, new EntityAIMoveIndoors(this));
      if (OreSpawn.PlayNicely == 0) {
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.MOB_SELECTOR));
      }

      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
      if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
      this.TargetSorter = new GenericTargetSorter(this);
      this.renderdata = new RenderInfo();
   }

   public ThePrinceTeen(World par1World, double par2, double par4, double par6) {
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
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0);
   }

   public boolean shouldRiderSit() {
      return true;
   }

   public int getTrackingRange() {
      return 64;
   }

   public int getUpdateFrequency() {
      return 10;
   }

   public boolean sendsVelocityUpdates() {
      return true;
   }

   public int getHead1Ext() {
      return this.dataManager.get(DW_22);
   }

   public int getHead2Ext() {
      return this.dataManager.get(DW_23);
   }

   public int getHead3Ext() {
      return this.dataManager.get(DW_25);
   }

   public void setHead1Ext(int par1) {
      if (this.world == null || !this.world.isRemote) {
         this.dataManager.set(DW_22,  par1);
      }
   }

   public void setHead2Ext(int par1) {
      if (this.world == null || !this.world.isRemote) {
         this.dataManager.set(DW_23,  par1);
      }
   }

   public void setHead3Ext(int par1) {
      if (this.world == null || !this.world.isRemote) {
         this.dataManager.set(DW_25,  par1);
      }
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
      this.dataManager.register(DW_20, 0);
      this.dataManager.register(DW_21, 0);
      this.dataManager.register(DW_24, 1);
      this.dataManager.register(DW_22, 0);
      this.dataManager.register(DW_23, 0);
      this.dataManager.register(DW_25, 0);
      this.setActivity(0);
      this.setAttacking(0);
      this.setTamed(false);
      this.setThePrinceTeenFire(1);
      this.noClip = false;
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
      return 1500;
   }

   public int getThePrinceTeenHealth() {
      return (int)this.getHealth();
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
      return 18;
   }

   protected void jump() {
      super.jump();
      this.motionY += 0.25;
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   @Override

   public net.minecraft.util.SoundEvent getAmbientSound() {
      if (this.isSitting()) {
         return null;
      } else {
         return this.getActivity() == 1 && this.getControllingPassenger() == null ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("roar") : null;
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("alo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("alo_death");
   }

   protected float getSoundVolume() {
      return 0.6F;
   }

   public float getSoundPitch() {
      return 0.75F;
   }

   public boolean canBePushed() {
      return false;
   }

   public double getMountedYOffset() {
      return 2.75;
   }

   protected Item getDropItem() {
      return OreSpawn.ThePrinceEgg;
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
      this.dropItemRand(OreSpawn.ThePrinceEgg, 1);
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      double ks = 1.75;
      double inair = 0.1;
      float iskraken = 1.0F;
      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
         if (par1Entity instanceof Kraken) {
            iskraken = 2.0F;
         }

         par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), iskraken * 45.0F);
         float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
         if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
            inair *= 2.0;
         }

         par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
         if (par1Entity instanceof EntityLiving) {
            EntityLiving e = (EntityLiving)par1Entity;
            if (e.getHealth() <= 0.0F) {
               this.kill_count++;
            }
         }
      }

      return true;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      Entity e = null;
      if (this.hurt_timer > 0) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("cactus")) {
         return ret;
      } else if (par1DamageSource.getDamageType().equals("inFire")) {
         return ret;
      } else if (par1DamageSource.getDamageType().equals("onFire")) {
         return ret;
      } else if (par1DamageSource.getDamageType().equals("lava")) {
         return ret;
      } else if (par1DamageSource.getDamageType().equals("inWall")) {
         return ret;
      } else {
         this.setSitting(false);
         this.setActivity(1);
         e = par1DamageSource.getTrueSource();
         if (e != null && e instanceof BetterFireball) {
            e.setDead();
            return ret;
         } else if (e != null && e instanceof EntitySmallFireball) {
            e.setDead();
            return ret;
         } else if (e != null && e instanceof ThePrinceTeen) {
            return false;
         } else if (e != null && e instanceof Spyro) {
            return false;
         } else {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 20;
            if (e != null && e instanceof EntityLivingBase) {
                if (this.isTamed() && e instanceof EntityPlayer) {
                   return ret;
                }

               this.setAttackTarget((EntityLivingBase)e);
               this.setAttackTarget((EntityLivingBase)e);
               this.getNavigator().tryMoveToEntityLiving((EntityLivingBase)e, 1.2);
               ret = true;
            }

            return ret;
         }
      }
   }

   public void updateAITasks() {
      EntityLivingBase e = null;
      super.updateAITasks();
      if (!this.isSitting()
         && this.getActivity() == 0
         && this.getControllingPassenger() == null
         && this.world.getDifficulty() != EnumDifficulty.PEACEFUL
         && this.world.rand.nextInt(10) == 1) {
         e = this.findSomethingToAttack();
         if (e != null) {
            this.setActivity(1);
         } else {
            this.setAttacking(0);
         }
      }

      if (this.kill_count > 25 && this.day_count > 10) {
         Entity ent = null;
         ThePrinceAdult d = null;
         ent = spawnCreature(this.world, "The Young Adult Prince", this.posX, this.posY, this.posZ);
         if (ent != null) {
            d = (ThePrinceAdult)ent;
            if (this.isTamed()) {
               d.setTamed(true);
               d.setCustomNameTag(this.getCustomNameTag());
            }

            this.setDead();
         }
      }

      if (this.is_day == 0) {
         this.is_day = 1;
         if (!this.world.isDaytime()) {
            this.is_day = -1;
         }
      } else {
         if (this.is_day == -1 && this.world.isDaytime()) {
            this.day_count++;
         }

         this.is_day = 1;
         if (!this.world.isDaytime()) {
            this.is_day = -1;
         }
      }
   }

   public void always_do() {
      EntityPlayer p = null;
      if (this.world.rand.nextInt(250) == 1 && this.getHealth() < this.mygetMaxHealth()) {
         this.heal(2.0F);
      }

      if (this.world.rand.nextInt(250) == 0) {
         this.setAttackTarget(null);
      }

      if (!this.isSitting()) {
         this.owner_flying = 0;
         if (this.isTamed() && this.getOwner() != null && this.getControllingPassenger() == null && !this.isSitting()) {
            p = (EntityPlayer)this.getOwner();
            if (p.capabilities.isFlying) {
               this.owner_flying = 1;
               this.setActivity(1);
            }
         }

         if (this.world.rand.nextInt(50) == 1 && !this.isSitting() && !this.target_in_sight && this.getControllingPassenger() == null) {
            if (this.world.rand.nextInt(15) == 1) {
               this.setActivity(1);
            } else {
               this.setActivity(0);
            }
         }
      }
   }

   public void fly_with_rider() {
      EntityLivingBase e = null;
      if (!this.isDead) {
         if (!this.isSitting()) {
            if (!this.world.isRemote) {
               if (this.world.rand.nextInt(5) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                  e = this.findSomethingToAttack();
                  if (e != null) {
                     this.setAttacking(1);
                     if (this.getDistanceSq(e) < (8.0F + e.width / 2.0F) * (8.0F + e.width / 2.0F)) {
                        this.attackEntityAsMob(e);
                     } else if (this.getDistanceSq(e) > 100.0 && this.getDistanceSq(e) < 625.0 && !this.isInWater() && this.getThePrinceTeenFire() != 0) {
                        this.shoot_something(e.posX, e.posY, e.posZ);
                     }
                  } else {
                     this.setAttacking(0);
                  }
               }
            }
         }
      }
   }

   protected void handleJumpWater() {
      if (this.getControllingPassenger() == null) {
         super.handleJumpWater();
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
      } else if (MyUtils.isRoyalty(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityMob) {
         return true;
      } else if (par1EntityLiving instanceof Mothra) {
         return true;
      } else if (par1EntityLiving instanceof Kraken) {
         return true;
      } else if (par1EntityLiving instanceof Leon) {
         Leon l = (Leon)par1EntityLiving;
         return !l.isTamed();
      } else if (par1EntityLiving instanceof WaterDragon) {
         WaterDragon l = (WaterDragon)par1EntityLiving;
         return !l.isTamed();
      } else if (par1EntityLiving instanceof GammaMetroid) {
         GammaMetroid l = (GammaMetroid)par1EntityLiving;
         return !l.isTamed();
      } else {
         return false;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(25.0, 20.0, 25.0));
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

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return false;
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
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
      EntityLivingBase e = null;
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (this.getActivity() != 0) {
         this.noClip = true;
      } else {
         this.noClip = false;
      }

      if (!this.world.isRemote) {
         if (this.world.rand.nextInt(10) == 1) {
            int i = this.world.rand.nextInt(3);
            if (i == 0) {
               this.head1dir = 2;
            }

            if (i == 1) {
               this.head1dir = -2;
            }

            if (i == 2) {
               this.head1dir = 0;
            }
         }

         if (this.world.rand.nextInt(10) == 1) {
            int ix = this.world.rand.nextInt(3);
            if (ix == 0) {
               this.head2dir = 2;
            }

            if (ix == 1) {
               this.head2dir = -2;
            }

            if (ix == 2) {
               this.head2dir = 0;
            }
         }

         if (this.world.rand.nextInt(10) == 1) {
            int ixx = this.world.rand.nextInt(3);
            if (ixx == 0) {
               this.head3dir = 2;
            }

            if (ixx == 1) {
               this.head3dir = -2;
            }

            if (ixx == 2) {
               this.head3dir = 0;
            }
         }

         this.head1ext = this.head1ext + this.head1dir;
         if (this.head1ext < 0) {
            this.head1ext = 0;
         }

         if (this.head1ext > 60) {
            this.head1ext = 60;
         }

         this.head2ext = this.head2ext + this.head2dir;
         if (this.head2ext < 0) {
            this.head2ext = 0;
         }

         if (this.head2ext > 60) {
            this.head2ext = 60;
         }

         this.head3ext = this.head3ext + this.head3dir;
         if (this.head3ext < 0) {
            this.head3ext = 0;
         }

         if (this.head3ext > 60) {
            this.head3ext = 60;
         }

         this.setHead1Ext(this.head1ext);
         this.setHead2Ext(this.head2ext);
         this.setHead3Ext(this.head3ext);
      }

      if (this.hurt_timer > 0) {
         this.hurt_timer--;
      }

      if (this.getActivity() == 1) {
         this.wing_sound++;
         if (this.wing_sound > 20) {
            if (!this.world.isRemote) {
                this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mothrawings"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 1.0F);
            }

            this.wing_sound = 0;
         }
      }

      if (this.isInWater()) {
         this.motionY += 0.07;
      }

      if (!this.world.isRemote) {
         if (this.getActivity() == 0 && this.isTamed() && this.getOwner() != null && !this.isSitting()) {
            e = this.getOwner();
            if (this.getDistanceSq(e) > 400.0) {
               this.setActivity(1);
            }
         }
      }
   }

   private void fly_without_rider() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 10;
      int do_new = 0;
      double ox = 0.0;
      double oy = 0.0;
      double oz = 0.0;
      int has_owner = 0;
      EntityLivingBase e = null;
      double speed_factor = 0.5;
      double var1 = 0.0;
      double var3 = 0.0;
      double var5 = 0.0;
      double gh = 1.25;
      double obstruction_factor = 0.0;
      double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      int toofar = 0;
      if (this.currentFlightTarget == null) {
         do_new = 1;
         this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
      }

      if (this.getControllingPassenger() == null) {
         if (this.isTamed() && this.getOwner() != null) {
            e = this.getOwner();
            has_owner = 1;
            ox = e.posX;
            oy = e.posY;
            oz = e.posZ;
            if (this.getDistanceSq(e) > 400.0) {
               toofar = 1;
               this.target_in_sight = false;
               this.setAttacking(0);
               this.setSitting(false);
               this.flyaway = 0;
               do_new = 1;
            }
         }

         if (!this.isSitting()) {
            if (this.posY < this.currentFlightTarget.getY() + 2.0) {
               this.motionY *= 0.7;
            } else if (this.posY > this.currentFlightTarget.getY() - 2.0) {
               this.motionY *= 0.5;
            } else {
               this.motionY *= 0.61;
            }

            if (this.world.rand.nextInt(300) == 1) {
               do_new = 1;
            }

            if (this.flyaway > 0) {
               this.flyaway--;
            }

            if (toofar == 0
               && this.flyaway == 0
               && this.world.getDifficulty() != EnumDifficulty.PEACEFUL
               && this.world.rand.nextInt(7) == 1) {
               e = this.getAttackTarget();
               if (e != null && !e.isEntityAlive()) {
                  this.setAttackTarget(null);
                  e = null;
               }

               if (e == null) {
                  e = this.findSomethingToAttack();
               }

               if (e != null) {
                  if (this.isTamed() && this.getHealth() / this.mygetMaxHealth() < 0.25F) {
                     this.setActivity(1);
                     this.setAttacking(0);
                     this.target_in_sight = false;
                     do_new = 0;
                     this.currentFlightTarget = new BlockPos((int)(this.posX + (this.posX - e.posX)),
                           (int)(this.posY + 1.0),
                           (int)(this.posZ + (this.posZ - e.posZ))
                        );
                  } else {
                     this.setActivity(1);
                     this.setAttacking(1);
                     this.target_in_sight = true;
                     this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                     do_new = 0;
                     if (this.getDistanceSq(e) < (8.0F + e.width / 2.0F) * (8.0F + e.width / 2.0F)) {
                        this.attackEntityAsMob(e);
                        this.flyaway = 5 + this.world.rand.nextInt(15);
                        do_new = 1;
                     } else if (this.getDistanceSq(e) < 400.0
                        && !this.isInWater()
                        && this.getThePrinceTeenFire() != 0
                        && this.world.rand.nextInt(2) == 1) {
                        this.shoot_something(e.posX, e.posY, e.posZ);
                     }
                  }
               } else {
                  this.target_in_sight = false;
                  this.flyaway = 0;
                  this.setAttacking(0);
               }
            }

            if (this.getDistanceSq(currentFlightTarget) < 2.1F) {
               do_new = 1;
            }

            if (do_new != 0 && !this.target_in_sight || do_new != 0 && this.flyaway != 0) {
               for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
                  int gox = (int)this.posX;
                  int goy = (int)this.posY;
                  int goz = (int)this.posZ;
                  if (has_owner == 1) {
                     gox = (int)ox;
                     goy = (int)oy;
                     goz = (int)oz;
                     if (this.owner_flying == 0) {
                        zdir = this.world.rand.nextInt(14) + 5;
                        xdir = this.world.rand.nextInt(14) + 5;
                     } else {
                        zdir = this.world.rand.nextInt(6);
                        xdir = this.world.rand.nextInt(6);
                     }
                  } else {
                     zdir = this.world.rand.nextInt(10) + 16;
                     xdir = this.world.rand.nextInt(10) + 16;
                  }

                  if (this.world.rand.nextInt(2) == 1) {
                     zdir = -zdir;
                  }

                  if (this.world.rand.nextInt(2) == 1) {
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

            obstruction_factor = 0.0;
            int dist = 2;
            dist += (int)(velocity * 4.0);

            for (int k = 1; k < dist; k++) {
               for (int i = 1; i < dist * 2; i++) {
                  double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                  double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                  Block bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + dz))).getBlock();
                  if (bid != Blocks.AIR) {
                     obstruction_factor += 0.05;
                  }
               }
            }

            this.motionY += obstruction_factor * 0.05;
            this.posY += obstruction_factor * 0.05;
            speed_factor = 0.5;
            var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
            var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
            var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
            if (this.owner_flying != 0) {
               speed_factor = 1.75;
               if (this.isTamed() && this.getOwner() != null) {
                  e = this.getOwner();
                  if (this.getDistanceSq(e) > 64.0) {
                     speed_factor = 3.5;
                  }
               }
            }

            this.motionX = this.motionX + (Math.signum(var1) - this.motionX) * 0.15 * speed_factor;
            this.motionY = this.motionY + (Math.signum(var3) - this.motionY) * 0.21 * speed_factor;
            this.motionZ = this.motionZ + (Math.signum(var5) - this.motionZ) * 0.15 * speed_factor;
            float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
            this.moveVertical = (float)(0.75 * speed_factor);
            this.rotationYaw += var8 / 4.0F;
            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         }
      }
   }

   public void onLivingUpdate() {
      List list = null;
      Entity listEntity = null;
      double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
      double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
      double obstruction_factor = 0.0;
      double relative_g = 0.0;
      double max_speed = 0.95;
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
               this.rotationYawHead = this.rotationYaw;
               this.boatPosRotationIncrements--;
            }
         } else {
            if (this.getActivity() != 0) {
               if (this.fireballticker > 0) {
                  this.fireballticker--;
               }

               if (this.getControllingPassenger() == null) {
                  this.fly_without_rider();
               } else {
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
                  gh = 1.25;
                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
                  if (bid != Blocks.AIR) {
                     this.motionY += 0.03;
                     this.posY += 0.1;
                  } else {
                     this.motionY -= 0.018;
                  }

                  obstruction_factor = 0.0;
                  int var101 = 3;
                  var101 += (int)(velocity * 7.0);

                  for (int k = 1; k < var101; k++) {
                     for (int i = 1; i < var101 * 2; i++) {
                        double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                        double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                        bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + dz))).getBlock();
                        if (bid != Blocks.AIR) {
                           obstruction_factor += 0.05;
                        }
                     }
                  }

                  this.motionY += obstruction_factor * 0.07;
                  this.posY += obstruction_factor * 0.07;
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

                  if (velocity > 0.01) {
                     d4 = 1.85 - velocity;
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

                  this.rotationPitch = 2.0F * (float)velocity;
                  this.setRotation(this.rotationYaw, this.rotationPitch);
                  double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                  double rr = Math.atan2(this.getControllingPassenger().motionZ, this.getControllingPassenger().motionX);
                  double rhm = Math.atan2(this.motionZ, this.motionX);
                  double rhdir = Math.toRadians((this.getControllingPassenger().rotationYaw + 90.0F) % 360.0F);
                  rt = 0.0;
                  pi = 3.1415926545;
                  deltav = 0.0;
                  float im = com.electrifiedded.orespawn.network.RiderInputState.getForward(pp);
                   if (com.electrifiedded.orespawn.network.RiderInputState.isFlyingUp(pp)) {
                     this.motionY += 0.035;
                     this.motionY += velocity * 0.046;
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
                     if (im > 0.0F) {
                        deltav = 0.025;
                        if (max_speed > 1.0) {
                           deltav += 0.05;
                        }

                        if (this.deltasmooth < 0.0F) {
                           this.deltasmooth = 0.0F;
                        }

                        this.deltasmooth = (float)(this.deltasmooth + deltav / 10.0);
                        if (this.deltasmooth > deltav) {
                           this.deltasmooth = (float)deltav;
                        }
                     } else {
                        max_speed = 0.35;
                        deltav = -0.02;
                        if (this.deltasmooth > 0.0F) {
                           this.deltasmooth = 0.0F;
                        }

                        this.deltasmooth = (float)(this.deltasmooth + deltav / 10.0);
                        if (this.deltasmooth < deltav) {
                           this.deltasmooth = (float)deltav;
                        }
                     }

                     newvelocity += this.deltasmooth;
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

                  if (this.fireballticker == 0 && Math.abs(com.electrifiedded.orespawn.network.RiderInputState.getStrafing(pp)) > 0.001F) {
                     double yoff = 1.5;
                     double xzoff = 7.5;
                     this.which_attack++;
                     if (this.which_attack > 2) {
                        this.which_attack = 0;
                     }

                     if (this.which_attack == 0) {
                        yoff += this.getHead1Ext() * 0.04F;
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw - 10.0F));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw - 10.0F));
                        BetterFireball bf = new BetterFireball(this.world, this, 0.0, 0.0, 0.0);
                        bf.setNotMe();
                        bf.setPosition(cx, this.posY + yoff, cz);
                        cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                        cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                        double cy = -Math.sin(Math.toRadians(pp.rotationPitch));
                        double d3 = MathHelper.sqrt(cx * cx + cy * cy + cz * cz);
                        bf.accelerationX = cx / d3 * 0.1;
                        bf.accelerationY = cy / d3 * 0.1;
                        bf.accelerationZ = cz / d3 * 0.1;
                        bf.motionX = this.motionX;
                        bf.motionY = this.motionY;
                        bf.motionZ = this.motionZ;
                        bf.posX = bf.posX - this.motionX * 3.0;
                        bf.posY = bf.posY - this.motionY * 3.0;
                        bf.posZ = bf.posZ - this.motionZ * 3.0;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.fuse"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(bf);
                     }

                     if (this.which_attack == 1) {
                        yoff += this.getHead3Ext() * 0.04F;
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw + 10.0F));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw + 10.0F));
                        IceBall var2 = new IceBall(this.world, cx, this.posY + yoff, cz);
                        var2.setLocationAndAngles(cx, this.posY + yoff, cz, pp.rotationYaw + 90.0F, pp.rotationPitch);
                        var2.setIceMaker(1);
                        double var3 = Math.cos(Math.toRadians(pp.rotationYaw + 90.0F));
                        double var5 = -Math.sin(Math.toRadians(pp.rotationPitch));
                        double var77 = Math.sin(Math.toRadians(pp.rotationYaw + 90.0F));
                        float var9 = MathHelper.sqrt(var3 * var3 + var77 * var77) * 0.2F;
                        var2.shoot(var3, var5 + var9, var77, 1.4F, 5.0F);
                        var2.posX = var2.posX - this.motionX * 3.0;
                        var2.posY = var2.posY - this.motionY * 3.0;
                        var2.posZ = var2.posZ - this.motionZ * 3.0;
                        var2.motionX *= 2.0;
                        var2.motionY *= 2.0;
                        var2.motionZ *= 2.0;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.firework_rocket.launch"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(var2);
                     }

                     if (this.which_attack == 2) {
                        yoff += this.getHead2Ext() * 0.04F;
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        ThunderBolt lb = new ThunderBolt(this.world, pp);
                        lb.setLocationAndAngles(cx, this.posY + yoff, cz, pp.rotationYaw + 90.0F, pp.rotationPitch);
                        lb.motionX *= 3.0;
                        lb.motionY *= 3.0;
                        lb.motionZ *= 3.0;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(lb);
                     }

                     this.fireballticker = 10;
                  }

                  this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                  this.motionX *= 0.985;
                  this.motionY *= 0.94;
                  this.motionZ *= 0.985;
                  if (!this.world.isRemote) {
                     list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(3.25, 4.0, 3.25));
                     if (list != null && !list.isEmpty()) {
                        for (int l = 0; l < list.size(); l++) {
                           listEntity = (Entity)list.get(l);
                           if (listEntity != this.getControllingPassenger() && !listEntity.isDead && listEntity.canBePushed()) {
                              listEntity.applyEntityCollision(this);
                           }
                        }
                     }
                  }

                  this.fly_with_rider();
                  if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {
                     if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
                  }
               }
            }

            this.always_do();
         }
      }
   }

   public void updateRiderPosition() {
      if (this.getControllingPassenger() != null) {
         float f = 0.65F;
         this.getControllingPassenger()
            .setPosition(
               this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
               this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(),
               this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw))
            );
      }
   }

   protected void playTameEffect(boolean par1) {
      net.minecraft.util.EnumParticleTypes s = net.minecraft.util.EnumParticleTypes.HEART;
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

      if (var2 != null && var2.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK) && par1EntityPlayer.getDistanceSq(this) < 25.0) {
         if (!this.world.isRemote) {
             this.setTamed(true);
             this.setOwnerId(par1EntityPlayer.getUniqueID());
            this.playTameEffect(true);
            this.world.setEntityState(this, (byte)7);
            this.heal(this.mygetMaxHealth() - this.getHealth());
            this.kill_count = 1000;
            this.day_count = 1000;
         }

         if (!par1EntityPlayer.capabilities.isCreativeMode) {
            var2.shrink(1);
            if (var2.getCount() <= 0) {
               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            }
         }

         return true;
      } else {
         if (this.isTamed()) {
            if (!this.isOwner(par1EntityPlayer)) {
               return false;
            }

            if (var2 == null && par1EntityPlayer.getDistanceSq(this) < 25.0) {
               if (!this.world.isRemote) {
                  par1EntityPlayer.startRiding(this);
                  this.setActivity(1);
                  this.setSitting(false);
               }

               return true;
            }

            if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq(this) < 25.0) {
               if (this.world.isRemote) {
                  this.playTameEffect(true);
                  this.world.setEntityState(this, (byte)7);
               }

               if (this.mygetMaxHealth() > this.getHealth()) {
                  this.heal(this.mygetMaxHealth() - this.getHealth());
               }

               if (!par1EntityPlayer.capabilities.isCreativeMode) {
                  var2.shrink(1);
                  if (var2.getCount() <= 0) {
                     par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                  }
               }

               return true;
            }

            if (var2 != null && par1EntityPlayer.getDistanceSq(this) < 25.0 && var2.getItem() instanceof ItemFood) {
               if (!this.world.isRemote) {
                  ItemFood var3 = (ItemFood)var2.getItem();
                  if (this.mygetMaxHealth() > this.getHealth()) {
                     this.heal(var3.getHealAmount(var2) * 10);
                  }

                  this.playTameEffect(true);
                  this.world.setEntityState(this, (byte)7);
               }

               if (!par1EntityPlayer.capabilities.isCreativeMode) {
                  var2.shrink(1);
                  if (var2.getCount() <= 0) {
                     par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                  }
               }

               return true;
            }

            if (var2 != null && var2.getItem() == Item.getItemFromBlock(Blocks.ICE) && par1EntityPlayer.getDistanceSq(this) < 25.0) {
               if (!this.world.isRemote) {
                  this.playTameEffect(true);
                  this.world.setEntityState(this, (byte)6);
                  this.setThePrinceTeenFire(0);
                  String healthMessage = new String();
                  healthMessage = String.format("Fireballs extinguished.");
                  par1EntityPlayer.sendMessage(new TextComponentString(healthMessage));
               }

               if (!par1EntityPlayer.capabilities.isCreativeMode) {
                  var2.shrink(1);
                  if (var2.getCount() <= 0) {
                     par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                  }
               }

               return true;
            }

            if (var2 != null && var2.getItem() == Items.FLINT_AND_STEEL && par1EntityPlayer.getDistanceSq(this) < 25.0) {
               if (!this.world.isRemote) {
                  this.playTameEffect(true);
                  this.world.setEntityState(this, (byte)6);
                  this.setThePrinceTeenFire(1);
                  String healthMessage = new String();
                  healthMessage = String.format("Fireballs lit!");
                  par1EntityPlayer.sendMessage(new TextComponentString(healthMessage));
               }

               if (!par1EntityPlayer.capabilities.isCreativeMode) {
                  var2.shrink(1);
                  if (var2.getCount() <= 0) {
                     par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                  }
               }

               return true;
            }

            if (var2 != null && var2.getItem() == Items.DIAMOND && par1EntityPlayer.getDistanceSq(this) < 25.0 && !this.world.isRemote) {
               Entity ent = null;
               ThePrince d = null;
               ent = spawnCreature(this.world, "The Prince", this.posX, this.posY, this.posZ);
               if (ent != null) {
                  d = (ThePrince)ent;
                  if (this.isTamed()) {
                     d.setTamed(true);
                     d.setCustomNameTag(par1EntityPlayer.getUniqueID().toString());
                     d.set_ok_to_grow();
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
            }

            if (this.isTamed()
               && var2 != null
               && var2.getItem() == Items.NAME_TAG
               && par1EntityPlayer.getDistanceSq(this) < 25.0
               && this.isOwner(par1EntityPlayer)) {
               this.setCustomNameTag(var2.getDisplayName());
               if (!par1EntityPlayer.capabilities.isCreativeMode) {
                  var2.shrink(1);
                  if (var2.getCount() <= 0) {
                     par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                  }
               }

               return true;
            }

            if (var2 != null && par1EntityPlayer.getDistanceSq(this) < 16.0) {
               if (!this.isSitting()) {
                  this.setSitting(true);
                  this.setActivity(0);
               } else {
                  this.setSitting(false);
                  this.setActivity(0);
               }

               return true;
            }
         }

         return false;
      }
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.BEEF;
   }

   public int getAttacking() {
      return this.dataManager.get(DW_20);
   }

   public void setAttacking(int par1) {
      if (this.world == null || !this.world.isRemote) {
         this.dataManager.set(DW_20,  par1);
      }
   }

   public int getActivity() {
      return this.dataManager.get(DW_21);
   }

   public void setActivity(int par1) {
      if (this.world == null || !this.world.isRemote) {
         this.dataManager.set(DW_21,  par1);
      }
   }

   public int getThePrinceTeenFire() {
      return this.dataManager.get(DW_24);
   }

   public void setThePrinceTeenFire(int par1) {
      if (!this.world.isRemote) {
         this.dataManager.set(DW_24,  par1);
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

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return null;
   }

   protected boolean canDespawn() {
      if (this.isNoDespawnRequired()) {
         return false;
      } else {
         return this.getControllingPassenger() != null ? false : !this.isTamed();
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("ThePrinceTeenAttacking", this.getAttacking());
      par1NBTTagCompound.setInteger("ThePrinceTeenActivity", this.getActivity());
      par1NBTTagCompound.setInteger("ThePrinceTeenFire", this.getThePrinceTeenFire());
      par1NBTTagCompound.setInteger("SpyroKill", this.kill_count);
      par1NBTTagCompound.setInteger("SpyroDay", this.day_count);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (this.isTamed() && this.getOwnerId() == null && this.hasCustomName()) {
         try {
            this.setOwnerId(java.util.UUID.fromString(this.getCustomNameTag()));
            this.setCustomNameTag("");
         } catch (IllegalArgumentException ignored) {
         }
      }
      this.setAttacking(par1NBTTagCompound.getInteger("ThePrinceTeenAttacking"));
      this.setActivity(par1NBTTagCompound.getInteger("ThePrinceTeenActivity"));
      this.setThePrinceTeenFire(par1NBTTagCompound.getInteger("ThePrinceTeenFire"));
      this.kill_count = par1NBTTagCompound.getInteger("SpyroKill");
      this.day_count = par1NBTTagCompound.getInteger("SpyroDay");
   }

   private void shoot_something(double x, double y, double z) {
      double rr = 0.0;
      double rhdir = 0.0;
      double rdd = 0.0;
      double pi = 3.1415926545;
      int which = this.world.rand.nextInt(3);
      if (which == 0) {
         rr = Math.atan2(z - this.posZ, x - this.posX);
         rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
         rdd = Math.abs(rr - rhdir) % (pi * 2.0);
         if (rdd > pi) {
            rdd -= pi * 2.0;
         }

         rdd = Math.abs(rdd);
         if (rdd < 0.5) {
            this.firecanon(x, y, z);
         }
      } else if (which == 1) {
         rr = Math.atan2(z - this.posZ, x - this.posX);
         rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
         rdd = Math.abs(rr - rhdir) % (pi * 2.0);
         if (rdd > pi) {
            rdd -= pi * 2.0;
         }

         rdd = Math.abs(rdd);
         if (rdd < 0.5) {
            this.firecanonl(x, y, z);
         }
      } else {
         rr = Math.atan2(z - this.posZ, x - this.posX);
         rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
         rdd = Math.abs(rr - rhdir) % (pi * 2.0);
         if (rdd > pi) {
            rdd -= pi * 2.0;
         }

         rdd = Math.abs(rdd);
         if (rdd < 0.5) {
            this.firecanoni(x, y, z);
         }
      }
   }

   private void firecanon(double x, double y, double z) {
      double yoff = 3.5;
      double xzoff = 6.0;
      BetterFireball bf = null;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      bf = new BetterFireball(this.world, this, x - cx + r1, y + 0.25 - (this.posY + yoff) + r2, z - cz + r3);
      bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
      bf.setPosition(cx, this.posY + yoff, cz);
      bf.setBig();
      this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(bf);
   }

   private void firecanonl(double x, double y, double z) {
      double yoff = 3.5;
      double xzoff = 6.0;
      double var3x = 0.0;
      double var5x = 0.0;
      double var7 = 0.0;
      float var9 = 0.0F;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      ThunderBolt lb = new ThunderBolt(this.world, cx, this.posY + yoff, cz);
      lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
      var3x = x - lb.posX;
      var5x = y + 0.25 - lb.posY;
      var7 = z - lb.posZ;
      var9 = MathHelper.sqrt(var3x * var3x + var7 * var7) * 0.2F;
      lb.shoot(var3x, var5x + var9, var7, 1.4F, 4.0F);
      lb.motionX *= 3.0;
      lb.motionY *= 3.0;
      lb.motionZ *= 3.0;
      this.world.spawnEntity(lb);
   }

   private void firecanoni(double x, double y, double z) {
      double yoff = 3.5;
      double xzoff = 6.0;
      double var3x = 0.0;
      double var5x = 0.0;
      double var7 = 0.0;
      float var9 = 0.0F;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
      float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
      IceBall lb = new IceBall(this.world, cx, this.posY + yoff, cz);
      lb.setIceMaker(1);
      lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
      var3x = x - lb.posX;
      var5x = y + 0.25 - lb.posY;
      var7 = z - lb.posZ;
      var9 = MathHelper.sqrt(var3x * var3x + var7 * var7) * 0.2F;
      lb.shoot(var3x, var5x + var9, var7, 1.4F, 4.0F);
      lb.motionX *= 3.0;
      lb.motionY *= 3.0;
      lb.motionZ *= 3.0;
      this.world.spawnEntity(lb);
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

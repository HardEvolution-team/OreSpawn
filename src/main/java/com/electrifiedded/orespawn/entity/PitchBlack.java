package com.electrifiedded.orespawn.entity;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.boss.EntityDragon;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;



public class PitchBlack extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(PitchBlack.class, DataSerializers.BYTE);

    private static final DataParameter<Byte> DW_21 = EntityDataManager.<Byte>createKey(PitchBlack.class, DataSerializers.BYTE);

    private static final DataParameter<Integer> DW_22 = EntityDataManager.<Integer>createKey(PitchBlack.class, DataSerializers.VARINT);



   private BlockPos currentFlightTarget = null;

   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private float MyMoveSpeed = 0.2F;

   private int damage_ticker = 0;

   private int wing_sound = 0;



   public PitchBlack(World par1World) {

      super(par1World);

      this.setSize(2.0F, 3.0F);

      this.experienceValue = 200;

      this.isImmuneToFire = false;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.MyMoveSpeed = 0.2F;

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.MyMoveSpeed + 0.1F * this.getPitchBlackScale());

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.getPitchBlackScale() * OreSpawn.PitchBlack_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

      this.dataManager.register(DW_21, (byte)0);

      this.dataManager.register(DW_22, 0);

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

      float t = 0.5F;

      if (this.world != null) {

         if (this.world.rand.nextInt(4) == 1) {

            t = 1.0F;

         }



         if (this.world.rand.nextInt(8) == 2) {

            t = 2.0F;

         }



         if (this.world.rand.nextInt(32) == 3) {

            t = 3.0F;

         }



         if (this.world.rand.nextInt(64) == 4) {

            t = 4.0F;

         }

      } else {

         if (OreSpawn.OreSpawnRand.nextInt(4) == 1) {

            t = 1.0F;

         }



         if (OreSpawn.OreSpawnRand.nextInt(8) == 2) {

            t = 2.0F;

         }



         if (OreSpawn.OreSpawnRand.nextInt(32) == 3) {

            t = 3.0F;

         }



         if (OreSpawn.OreSpawnRand.nextInt(64) == 4) {

            t = 4.0F;

         }

      }



      if (OreSpawn.NightmareSize == 1) {

         t = 0.5F;

      }



      if (OreSpawn.NightmareSize == 2) {

         t = 1.0F;

      }



      if (OreSpawn.NightmareSize == 3) {

         t = 2.0F;

      }



      if (OreSpawn.NightmareSize == 4) {

         t = 3.0F;

      }



      if (OreSpawn.NightmareSize == 5) {

         t = 4.0F;

      }



      this.setPitchBlackScale(t);

      this.experienceValue = (int)(100.0F * t);

      this.isImmuneToFire = true;

      this.setSize(2.5F * this.getPitchBlackScale(), 3.5F * this.getPitchBlackScale());

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.setPitchBlackScale(par1NBTTagCompound.getFloat("Fscale"));

      this.setSize(2.5F * this.getPitchBlackScale(), 3.5F * this.getPitchBlackScale());

      this.experienceValue = (int)(100.0F * this.getPitchBlackScale());

      this.isImmuneToFire = true;

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      par1NBTTagCompound.setFloat("Fscale", this.getPitchBlackScale());

   }



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

   }



   public final int getActivity() {

      return this.dataManager.get(DW_21);

   }



   public final void setActivity(int par1) {

      this.dataManager.set(DW_21,  (byte)par1);

   }



   public float getPitchBlackScale() {

      int i = this.dataManager.get(DW_22);

      float f = i;

      return f / 10.0F;

   }



   public void setPitchBlackScale(float par1) {

      float f = par1 * 10.0001F;

      int i = (int)f;

      this.dataManager.set(DW_22,  i);

   }



   public int getTotalArmorValue() {

      return OreSpawn.PitchBlack_stats.defense + (int)(2.0F * this.getPitchBlackScale());

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



   protected boolean canDespawn() {

      return this.isNoDespawnRequired() ? false : this.world.isDaytime();

   }



   protected float getSoundVolume() {

      return 0.75F;

   }



   protected float getSoundPitch() {

      return 1.0F - 0.7F * (4.0F / this.getPitchBlackScale());

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.world.rand.nextInt(5) != 2 ? null : OreSpawnSoundEvents.get("pitchblack_living");

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("pitchblack_hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("pitchblack_dead");

   }



   public int mygetMaxHealth() {

      return (int)(OreSpawn.PitchBlack_stats.health * this.getPitchBlackScale());

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onUpdate() {

      this.MyMoveSpeed = 0.2F;

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.MyMoveSpeed + 0.1F * this.getPitchBlackScale());

      super.onUpdate();

      this.setSize(2.5F * this.getPitchBlackScale(), 3.5F * this.getPitchBlackScale());

      this.wing_sound++;

      if (this.wing_sound > 20) {

         if (!this.world.isRemote) {

             this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mothrawings"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F);

         }



         this.wing_sound = 0;

      }



      this.motionY *= 0.6;

      if (!this.world.isRemote && this.world.rand.nextInt(250) == 1) {

         this.heal(1.0F + this.getPitchBlackScale());

         if (this.world.rand.nextInt(5) == 0) {

            Block bid = Blocks.AIR;

            if (this.posY > 10.0) {

               for (int i = 0; i < 10; i++) {

                  bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - i, (int)this.posZ)).getBlock();

                  if (bid != Blocks.AIR) {

                     break;

                  }

               }

            } else {

               bid = Blocks.STONE;

            }



            if (bid != Blocks.AIR) {

               Entity e = null;

               e = this.findSomethingToAttack();

               if (e == null) {

                  this.setActivity(0);

               }

            }

         } else {

            this.setActivity(1);

            this.getNavigator().setPath(null, 0.0);

         }

      }



      if (this.getActivity() == 0 && this.world.rand.nextInt(10) == 1) {

         Entity e = null;

         e = this.findSomethingToAttack();

         if (e != null) {

            this.setActivity(1);

            this.getNavigator().setPath(null, 0.0);

         }

      }

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      boolean var4 = false;

      if (par1Entity != null && par1Entity instanceof EntityDragon) {

         EntityDragon dr = (EntityDragon)par1Entity;

         DamageSource var21 = null;

         var21 = DamageSource.causeExplosionDamage((net.minecraft.entity.EntityLivingBase)null);

         var21.setExplosion();

         if (this.world.rand.nextInt(8) == 1) {

            dr.attackEntityFromPart(dr.dragonPartHead, var21, OreSpawn.PitchBlack_stats.attack * this.getPitchBlackScale());

         } else {

            dr.attackEntityFromPart(dr.dragonPartBody, var21, OreSpawn.PitchBlack_stats.attack * this.getPitchBlackScale());

         }



         var4 = true;

      } else {

         var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawn.PitchBlack_stats.attack * this.getPitchBlackScale());

         if (var4 && par1Entity != null && par1Entity instanceof EntityLivingBase) {

            double ks = 1.15 * this.getPitchBlackScale();

            double inair = 0.08 * this.getPitchBlackScale();

            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);

            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {

               inair *= 2.0;

            }



            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);

         }

      }



      return var4;

   }



   public boolean canSeeTarget(double pX, double pY, double pZ) {

      return this.world

            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)

         == null;

   }



   protected void updateAITasks() {

      int xdir = 1;

      int zdir = 1;

      int keep_trying = 50;

      if (this.damage_ticker > 0) {

         this.damage_ticker--;

      }



      if (this.getActivity() == 0) {

         super.updateAITasks();

      } else if (!this.isDead) {

         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.getActivity() != 0) {

            if (this.rand.nextInt(150) != 0

               && !(this.getDistanceSq(currentFlightTarget) < 2.1F)) {

               if (this.rand.nextInt(8) == 0) {

                  Entity e = null;

                  e = this.findSomethingToAttack();

                  if (e != null) {

                     double d1 = 5.0 + e.width / 2.0F;

                     d1 += this.getPitchBlackScale();

                     d1 *= d1;

                     this.setAttacking(1);

                     if (e instanceof EntityDragon && d1 < 100.0) {

                        d1 = 100.0;

                     }



                     if (e instanceof Godzilla && d1 < 100.0) {

                        d1 = 100.0;

                     }



                     if (e instanceof GodzillaHead && d1 < 100.0) {

                        d1 = 100.0;

                     }



                     this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 2.0), (int)e.posZ);

                     if (this.getDistanceSq(e) < d1) {

                        this.attackEntityAsMob(e);

                     }

                  } else {

                     this.setAttacking(0);

                  }

               }

            } else {

               for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying > 0; keep_trying--) {

                  zdir = this.rand.nextInt(20) + 5 * (int)this.getPitchBlackScale();

                  xdir = this.rand.nextInt(20) + 5 * (int)this.getPitchBlackScale();

                  if (this.rand.nextInt(2) == 0) {

                     zdir = -zdir;

                  }



                  if (this.rand.nextInt(2) == 0) {

                     xdir = -xdir;

                  }



                  this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(11) - 5, (int)this.posZ + zdir);

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



            double var1 = this.currentFlightTarget.getX() + 0.4 - this.posX;

            double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

            double var5 = this.currentFlightTarget.getZ() + 0.4 - this.posZ;

            double myspeed = 0.5F + this.getPitchBlackScale() / 10.0F;

            this.motionX = this.motionX + (Math.signum(var1) * myspeed - this.motionX) * 0.33;

            this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.20000000149011612;

            this.motionZ = this.motionZ + (Math.signum(var5) * myspeed - this.motionZ) * 0.33;

            float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;

            float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);

            this.moveVertical = 0.1F + (float)myspeed;

            this.rotationYaw += var8 / 5.0F;

         }

      }

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



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      if (this.damage_ticker > 0) {

         return ret;

      } else {

         this.damage_ticker = 20;

         ret = super.attackEntityFrom(par1DamageSource, par2);

         Entity e = par1DamageSource.getTrueSource();

         if (e != null && this.currentFlightTarget != null) {

            this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 2.0), (int)e.posZ);

         }



         this.setActivity(1);

         this.getNavigator().setPath(null, 0.0);

         return ret;

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

                  if (s != null && s.equalsIgnoreCase("Nightmare")) {

                     Float t = this.getPitchBlackScale();

                     if (t > 1.0F) {

                        t = 1.0F;

                     }



                     this.setPitchBlackScale(t);

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

         if (this.world.provider.getDimension() == OreSpawn.DimensionID6) {

            PitchBlack target = null;

            target = (PitchBlack)this.world.findNearestEntityWithinAABB(PitchBlack.class, this.getEntityBoundingBox().grow(16.0, 16.0, 16.0), this);

            if (target != null) {

               return false;

            }

         }



         if (this.getPitchBlackScale() < 1.1F) {

            return true;

         } else {

            int ix = 1;

            if (this.getPitchBlackScale() > 3.1F) {

               ix = 2;

            }



            int iy = ix * 3;



            for (int var13 = -ix; var13 <= ix; var13++) {

               for (int j = -ix; j <= ix; j++) {

                  for (int ixx = 1; ixx <= iy; ixx++) {

                     Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ixx, (int)this.posZ + var13)).getBlock();

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



   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {

      if (par1EntityLiving == null) {

         return false;

      } else if (par1EntityLiving == this) {

         return false;

      } else if (!(par1EntityLiving instanceof EntityLivingBase)) {

         return false;

      } else if (!par1EntityLiving.isEntityAlive()) {

         return false;

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof PitchBlack) {

         return false;

      } else if (par1EntityLiving instanceof EnderReaper) {

         return false;

      } else if (par1EntityLiving instanceof LeafMonster) {

         return false;

      } else if (par1EntityLiving instanceof TerribleTerror) {

         return false;

      } else if (par1EntityLiving instanceof LurkingTerror) {

         return false;

      } else if (par1EntityLiving instanceof CreepingHorror) {

         return false;

       } else if (par1EntityLiving instanceof Island) {

          return false;

       } else if (par1EntityLiving instanceof IslandToo) {

          return false;

       } else if (par1EntityLiving instanceof Triffid) {

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



   private Entity findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         double d1 = 16.0 + this.getPitchBlackScale() * 6.0F;

         double d2 = 10.0 + this.getPitchBlackScale() * 4.0F;

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(d1, d2, d1));

         Collections.sort(var5, this.TargetSorter);

         Iterator var2 = var5.iterator();

         EntityLivingBase var3 = null;



         while (var2.hasNext()) {

            var3 = (EntityLivingBase)var2.next();

            if (this.isSuitableTarget(var3, false)) {

               return var3;

            }

         }



         return null;

      }

   }



   protected Item getDropItem() {

      return OreSpawn.MyNightmareScale;

   }



   private ItemStack dropItemRand(Item index, int par1) {

      EntityItem var3 = null;

      ItemStack is = new ItemStack(index, par1, 0);

      var3 = new EntityItem(

         this.world,

         this.posX

            + OreSpawn.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()

            - OreSpawn.OreSpawnRand.nextInt(5) * this.getPitchBlackScale(),

         this.posY + 1.0,

         this.posZ

            + OreSpawn.OreSpawnRand.nextInt(5) * this.getPitchBlackScale()

            - OreSpawn.OreSpawnRand.nextInt(5) * this.getPitchBlackScale(),

         is

      );

      if (var3 != null) {

         this.world.spawnEntity(var3);

      }



      return is;

   }



   protected void dropFewItems(boolean par1, int par2) {

      int i = 3 + this.world.rand.nextInt(2 + (int)(5.0F * this.getPitchBlackScale()));



      for (int var4 = 0; var4 < i; var4++) {

         this.dropItemRand(Items.ROTTEN_FLESH, 1);

         int j = this.world.rand.nextInt(10);

         if (j == 0) {

            this.dropItemRand(Items.FEATHER, 1);

         }



         if (j == 1) {

            this.dropItemRand(Items.STRING, 1);

         }



         if (j == 2) {

            this.dropItemRand(Items.FLINT, 1);

         }



         if (j == 3) {

            this.dropItemRand(Items.BEEF, 1);

         }

      }



      this.dropItemRand(OreSpawn.MyNightmareScale, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      i = 2 + (int)this.getPitchBlackScale() + this.world.rand.nextInt(2 + (int)(5.0F * this.getPitchBlackScale()));



      for (int var7 = 0; var7 < i; var7++) {

         this.dropItemRand(OreSpawn.ZooKeeper, 1);

      }

   }

}



package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;



public class LurkingTerror extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(LurkingTerror.class, DataSerializers.BYTE);



   private BlockPos currentFlightTarget = null;

   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();



   public LurkingTerror(World par1World) {

      super(par1World);

      this.setSize(1.75F, 1.25F);

      this.experienceValue = 20;

      this.isImmuneToFire = false;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.LurkingTerror_stats.attack);

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

      return this.isNoDespawnRequired() ? false : this.getAttacking() == 0;

   }



   public int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

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



   protected float getSoundVolume() {

      return 0.55F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return OreSpawnSoundEvents.get("lurkinghorror_living");

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("lurkinghorror_hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("lurkinghorror_dead");

   }



   public int mygetMaxHealth() {

      return OreSpawn.LurkingTerror_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.LurkingTerror_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onUpdate() {

      super.onUpdate();

      this.motionY *= 0.6;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);

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

      if (!this.isDead) {

         super.updateAITasks();

         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.rand.nextInt(120) != 0

            && !(this.getDistanceSq(currentFlightTarget) < 2.1F)) {

            if (this.rand.nextInt(9) == 0) {

               EntityLivingBase e = null;

               e = this.findSomethingToAttack();

               if (e != null) {

                  this.setAttacking(1);

                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);

                  if (this.getDistanceSq(e) < 6.0) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.setAttacking(0);

               }

            }

         } else {

            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {

               zdir = this.rand.nextInt(10) + 2;

               xdir = this.rand.nextInt(10) + 2;

               if (this.rand.nextInt(2) == 0) {

                  zdir = -zdir;

               }



               if (this.rand.nextInt(2) == 0) {

                  xdir = -xdir;

               }



               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(5) - 2, (int)this.posZ + zdir);

               bid = this.world

                  .getBlockState(this.currentFlightTarget).getBlock();

               if (bid == Blocks.AIR

                  && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())

                  )

                {

                  bid = Blocks.STONE;

               }

            }

         }



         double var1 = this.currentFlightTarget.getX() + 0.4 - this.posX;

         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

         double var5 = this.currentFlightTarget.getZ() + 0.4 - this.posZ;

         this.motionX = this.motionX + (Math.signum(var1) * 0.4 - this.motionX) * 0.30000000149011613;

         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.20000000149011612;

         this.motionZ = this.motionZ + (Math.signum(var5) * 0.4 - this.motionZ) * 0.30000000149011613;

         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;

         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);

         this.moveVertical = 0.75F;

         this.rotationYaw += var8 / 4.0F;

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

      boolean ret = super.attackEntityFrom(par1DamageSource, par2);

      Entity e = par1DamageSource.getTrueSource();

      if (e != null && this.currentFlightTarget != null) {

         this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);

      }



      return ret;

   }



   public boolean getCanSpawnHere() {

      LurkingTerror target = null;



      for (int k = -2; k < 2; k++) {

         for (int j = -2; j < 2; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Lurking Terror")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (!this.world.isDaytime()) {

         return false;

      } else if (this.world.rand.nextInt(2) != 1) {

         return false;

      } else if (this.world.provider.getDimension() == OreSpawn.DimensionID6 && this.world.rand.nextInt(6) != 0) {

         return false;

      } else {

         target = (LurkingTerror)this.world.findNearestEntityWithinAABB(LurkingTerror.class, this.getEntityBoundingBox().grow(32.0, 16.0, 32.0), this);

         return target != null ? false : !(this.posY < 10.0);

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

      } else if (par1EntityLiving instanceof LurkingTerror) {

         return false;

      } else if (par1EntityLiving instanceof RockBase) {

         return false;

      } else if (par1EntityLiving instanceof EnderReaper) {

         return false;

      } else if (par1EntityLiving instanceof LeafMonster) {

         return false;

      } else if (par1EntityLiving instanceof TerribleTerror) {

         return false;

      } else if (par1EntityLiving instanceof Mothra) {

         return false;

      } else if (par1EntityLiving instanceof CloudShark) {

         return false;

      } else if (par1EntityLiving instanceof Rotator) {

         return false;

      } else if (par1EntityLiving instanceof Bee) {

         return false;

      } else if (par1EntityLiving instanceof Mantis) {

         return false;

      } else if (par1EntityLiving instanceof CreepingHorror) {

         return false;

      } else if (par1EntityLiving instanceof Triffid) {

         return false;

      } else if (par1EntityLiving instanceof PitchBlack) {

         return false;

      } else if (par1EntityLiving instanceof Dragon) {

         return false;

       } else if (par1EntityLiving instanceof Island) {

          return false;

       } else if (par1EntityLiving instanceof IslandToo) {

          return false;

       } else if (par1EntityLiving instanceof EntityButterfly) {

         return false;

      } else if (par1EntityLiving instanceof Firefly) {

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



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0, 8.0, 12.0));

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



   protected Item getDropItem() {

      int i = this.world.rand.nextInt(3);

      if (i == 0) {

         return Items.BEEF;

      } else {

         return i == 1 ? Items.FLINT : Items.FEATHER;

      }

   }

}



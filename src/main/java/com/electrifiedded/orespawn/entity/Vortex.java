package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;

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

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;



public class Vortex extends EntityMob {

   private BlockPos currentFlightTarget = null;

   private GenericTargetSorter TargetSorter = null;

   private int winded = 0;

   private int busy_fighting = 0;

   private int was_spawnered = 0;



   public Vortex(World par1World) {

      super(par1World);

      this.setSize(2.0F, 4.0F);

      this.experienceValue = 200;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35F);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Vortex_stats.attack);

   }



   protected boolean canDespawn() {

      if (this.isNoDespawnRequired()) {

         return false;

      } else {

         return this.busy_fighting != 0 ? false : this.was_spawnered == 0;

      }

   }



   protected float getSoundVolume() {

      return 0.75F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return OreSpawnSoundEvents.get("vortexlive");

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return null;

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("vortexlive");

   }



   public boolean canBePushed() {

      return true;

   }



   protected void collideWithEntity(Entity par1Entity) {

   }



   public int mygetMaxHealth() {

      return OreSpawn.Vortex_stats.health;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onUpdate() {

      EntityLivingBase e = null;

      super.onUpdate();

      this.motionY *= 0.6;

      this.busy_fighting = 0;

      e = this.findSomethingToAttack();

      if (e != null) {

         this.busy_fighting = 1;

         if (this.world.isRemote) {

            for (int i = 0; i < 20; i++) {

               double d = this.world.rand.nextDouble() * 3.5;

               d *= d;

               double dir = this.world.rand.nextDouble() * 2.0 * Math.PI;

               dir -= Math.PI;

               double dx = Math.cos(dir) * d / 2.0;

               double dz = Math.sin(dir) * d / 2.0;

               dir++;

               this.world

                  .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,

                     this.posX + dx,

                     this.posY + 0.75 + d,

                     this.posZ + dz,

                     Math.cos(dir) * this.world.rand.nextFloat() / 4.0,

                     this.world.rand.nextFloat() / 2.0F,

                     Math.sin(dir) * this.world.rand.nextFloat() / 4.0

                  );

            }

         }

      }



      if (this.world.rand.nextInt(200) == 1) {

         this.heal(1.0F);

      }



      if (!this.isNoDespawnRequired()) {

         if (this.busy_fighting == 0) {

            if (this.was_spawnered == 0) {

               long t = this.world.getWorldTime();

               t %= 24000L;

               if (t < 12000L && this.world.rand.nextInt(500) == 1) {

                  this.setDead();

               }

            }

         }

      }

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

      EntityLivingBase e = null;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.winded > 0) {

            this.winded--;

         }



         if (this.rand.nextInt(300) == 0

            || this.getDistanceSq(currentFlightTarget) < 2.1F) {

            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {

               zdir = this.rand.nextInt(14) + 10;

               xdir = this.rand.nextInt(14) + 10;

               if (this.rand.nextInt(2) == 0) {

                  zdir = -zdir;

               }



               if (this.rand.nextInt(2) == 0) {

                  xdir = -xdir;

               }



               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(6) - 3, (int)this.posZ + zdir);

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



         e = this.findSomethingToAttack();

         if (e != null) {

            this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);

            double d = this.getDistanceSq(e);

            if (d < 81.0 && this.winded == 0) {

               double a = Math.atan2(this.posZ - e.posZ, this.posX - e.posX);

               double pm = 1.0;

               if (e instanceof EntityPlayer) {

                  pm = 2.0;

               }



               e.addVelocity(Math.cos(a) * (10.0 - Math.sqrt(d)) * 0.1F, (10.0 - Math.sqrt(d)) * 0.05F * pm, Math.sin(a) * (10.0 - Math.sqrt(d)) * 0.1F);

            }



            if (this.getDistanceSq(e) < (4.0F + e.width / 2.0F) * (4.0F + e.width / 2.0F) && this.rand.nextInt(8) == 2) {

               this.attackEntityAsMob(e);

            }

         }



         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;

         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;

         this.motionX = this.motionX + (Math.signum(var1) * 0.4 - this.motionX) * 0.2;

         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.20000000149011612;

         this.motionZ = this.motionZ + (Math.signum(var5) * 0.4 - this.motionZ) * 0.2;

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

      return true;

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      Entity e = par1DamageSource.getTrueSource();

      ret = super.attackEntityFrom(par1DamageSource, par2);

      if (e != null && this.currentFlightTarget != null) {

         this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY, (int)e.posZ);

      }



      this.winded = 20;

      return ret;

   }



   public int getTotalArmorValue() {

      return OreSpawn.Vortex_stats.defense;

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

                  if (s != null && s.equalsIgnoreCase("Vortex")) {

                     this.was_spawnered = 1;

                     return true;

                  }

               }

            }

         }

      }



      for (int var11 = -2; var11 <= 2; var11++) {

         for (int j = -2; j <= 2; j++) {

            for (int ix = 1; ix < 4; ix++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var11)).getBlock();

               if (bid != Blocks.AIR) {

                  return false;

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.posY < 50.0) {

         return false;

      } else {

         long t = this.world.getWorldTime();

         t %= 24000L;

         if (t < 12000L) {

            return false;

         } else if (this.world.rand.nextInt(2) != 1) {

            return false;

         } else {

            Vortex target = null;

            target = (Vortex)this.world.findNearestEntityWithinAABB(Vortex.class, this.getEntityBoundingBox().grow(20.0, 16.0, 20.0), this);

            return target == null;

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

      } else {

         if (par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            if (p.capabilities.isCreativeMode) {

               return false;

            }

         }



         if (par1EntityLiving instanceof Vortex) {

            return false;

         } else if (par1EntityLiving instanceof Rotator) {

            return false;

         } else if (par1EntityLiving instanceof Mothra) {

            return false;

         } else if (par1EntityLiving instanceof Brutalfly) {

            return false;

         } else if (par1EntityLiving instanceof Peacock) {

            return false;

         } else if (par1EntityLiving instanceof CrystalCow) {

            return false;

         } else if (par1EntityLiving instanceof Irukandji) {

            return false;

         } else if (par1EntityLiving instanceof Skate) {

            return false;

         } else if (par1EntityLiving instanceof Whale) {

            return false;

         } else {

            return par1EntityLiving instanceof Flounder ? false : !(par1EntityLiving instanceof Urchin);

         }

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(16.0, 10.0, 16.0));

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



   private ItemStack dropItemRand(Item index, int par1) {

      EntityItem var3 = null;

      ItemStack is = new ItemStack(index, par1, 0);

      var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(6) - OreSpawn.OreSpawnRand.nextInt(6),

         this.posY + 1.0 + this.world.rand.nextInt(10),

         this.posZ + OreSpawn.OreSpawnRand.nextInt(6) - OreSpawn.OreSpawnRand.nextInt(6),

         is

      );

      if (var3 != null) {

         this.world.spawnEntity(var3);

      }



      return is;

   }



   protected void dropFewItems(boolean par1, int par2) {

      this.dropItemRand(OreSpawn.VortexEye, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int i = 5 + this.world.rand.nextInt(7);



      for (int var4 = 0; var4 < i; var4++) {

         int var3 = this.world.rand.nextInt(10);

         if (var3 == 0) {

            this.dropItemRand(Items.STICK, 1);

         }



         if (var3 == 1) {

            this.dropItemRand(OreSpawn.MyTigersEyeIngot, 1);

         }



         if (var3 == 2) {

            this.dropItemRand(OreSpawn.MyCrystalPinkIngot, 1);

         }



         if (var3 == 3) {

            this.dropItemRand(Items.IRON_INGOT, 1);

         }



         if (var3 == 4) {

            this.dropItemRand(OreSpawn.UraniumNugget, 1);

         }



         if (var3 == 6) {

            this.dropItemRand(OreSpawn.TitaniumNugget, 1);

         }



         if (var3 == 7) {

            this.dropItemRand(OreSpawn.MyIrukandji, 1);

         }



         if (var3 == 8) {

            this.dropItemRand(Item.getItemFromBlock(OreSpawn.CrystalCoal), 1);

         }

      }

   }



   protected Item getDropItem() {

      return OreSpawn.FairyEgg;

   }

}



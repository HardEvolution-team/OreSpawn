package com.electrifiedded.orespawn.entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.MobEffects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrownRock extends EntityThrowable {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(EntityThrownRock.class, DataSerializers.VARINT);

   private int rock_type = 0;
   private int myage = 0;
   private float my_rotation = 0.0F;

   public EntityThrownRock(World par1World) {
      super(par1World);
   }

   public EntityThrownRock(World par1World, int par2) {
      super(par1World);
   }

   public EntityThrownRock(World par1World, EntityLivingBase par2EntityLiving) {
      super(par1World, par2EntityLiving);
      this.shoot(par2EntityLiving, par2EntityLiving.rotationPitch, par2EntityLiving.rotationYaw, 0.0F, 1.5F, 1.0F);
   }

   public EntityThrownRock(World par1World, EntityLivingBase par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
      this.shoot(par2EntityLiving, par2EntityLiving.rotationPitch, par2EntityLiving.rotationYaw, 0.0F, 1.5F, 1.0F);
      this.rock_type = par3;
   }

   public EntityThrownRock(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, 0);
   }

   public int getRockType() {
      return this.dataManager.get(DW_20);
   }

   public void setRockType(int par1) {
      if (this.world != null) {
         if (!this.world.isRemote) {
            this.rock_type = par1;
            this.dataManager.set(DW_20,  par1);
         }
      }
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (!this.isDead) {
         if (!this.world.isRemote) {
            if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null) {
               Entity e = par1MovingObjectPosition.entityHit;
               if (this.rock_type == 1 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 2.0F);
                  double ks = 0.1;
                  double inair = 0.025;
                  float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inair *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
               }

               if (this.rock_type == 2 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 5.0F);
                  double ks = 0.2;
                  double inair = 0.025;
                  float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inair *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
               }

               if (this.rock_type == 3 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 5.0F);
                  double ks = 0.2;
                  double inair = 0.025;
                  float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inair *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                  e.setFire(20);
               }

               if (this.rock_type == 4 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 5.0F);
                  double ks = 0.2;
                  double inair = 0.025;
                  float f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inair *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
                  }
               }

               if (this.rock_type == 5 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 10.0F);
                  double ksx = 0.1;
                  double inairx = 0.025;
                  float f3x = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3x) * ksx, inairx, Math.sin(f3x) * ksx);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 0));
                  }
               }

               if (this.rock_type == 6 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 20.0F);
                  double ksxx = 0.2;
                  double inairxx = 0.025;
                  float f3xx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xx) * ksxx, inairxx, Math.sin(f3xx) * ksxx);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                  }
               }

               if (this.rock_type == 7 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 40.0F);
                  double ksxxx = 0.2;
                  double inairxxx = 0.025;
                  float f3xxx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xxx) * ksxxx, inairxxx, Math.sin(f3xxx) * ksxxx);
               }

               if (this.rock_type == 8 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 40.0F);
                  double ksxxx = 0.5;
                  double inairxxx = 0.055;
                  float f3xxx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xxx) * ksxxx, inairxxx, Math.sin(f3xxx) * ksxxx);
                  this.world
                     .newExplosion(
                        (Entity)null,
                        e.posX,
                        e.posY + 0.25,
                        e.posZ,
                        2.1F,
                        true,
                        this.world.getGameRules().getBoolean("mobGriefing")
                     );
               }

               if (this.rock_type == 9 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 150.0F);
                  double ksxxx = 0.2;
                  double inairxxx = 0.025;
                  float f3xxx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xxx) * ksxxx, inairxxx, Math.sin(f3xxx) * ksxxx);
                  e.setFire(50);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                  }
               }

               if (this.rock_type == 10 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 150.0F);
                  double ksxxxx = 0.2;
                  double inairxxxx = 0.025;
                  float f3xxxx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxxxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xxxx) * ksxxxx, inairxxxx, Math.sin(f3xxxx) * ksxxxx);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 0));
                  }

                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                  }
               }

               if (this.rock_type == 11 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 150.0F);
                  double ksxxxxx = 0.2;
                  double inairxxxxx = 0.025;
                  float f3xxxxx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxxxxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xxxxx) * ksxxxxx, inairxxxxx, Math.sin(f3xxxxx) * ksxxxxx);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
                  }

                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                  }
               }

               if (this.rock_type == 12 && e != this.getThrower()) {
                  e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.getThrower()), 250.0F);
                  double ksxxxxxx = 0.2;
                  double inairxxxxxx = 0.025;
                  float f3xxxxxx = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                  if (e.isDead) {
                     inairxxxxxx *= 2.0;
                  }

                  e.addVelocity(Math.cos(f3xxxxxx) * ksxxxxxx, inairxxxxxx, Math.sin(f3xxxxxx) * ksxxxxxx);
                  if (e instanceof EntityLivingBase) {
                     ((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
                  }

                  this.world
                     .newExplosion(
                        (Entity)null,
                        e.posX,
                        e.posY + 0.25,
                        e.posZ,
                        5.1F,
                        true,
                        this.world.getGameRules().getBoolean("mobGriefing")
                     );
               }
            } else if (this.rock_type != 0) {
               int played = 0;
               int x = par1MovingObjectPosition.getBlockPos().getX();
               int y = par1MovingObjectPosition.getBlockPos().getY();
               int z = par1MovingObjectPosition.getBlockPos().getZ();

               for (int i = -1; i <= 1; i++) {
                  for (int j = -1; j <= 1; j++) {
                     for (int k = -1; k <= 1; k++) {
                        Block bid = this.world.getBlockState(new BlockPos(x + i, y + j, z + k)).getBlock();
                        if (bid == Blocks.GLASS || bid == Blocks.GLASS_PANE || bid == Blocks.GLASS) {
                           if (!this.world.isRemote) {
                              this.world.setBlockState(new BlockPos(x + i, y + j, z + k), Blocks.AIR.getDefaultState());
                           }

                           if (played == 0) {
                              this.world.playSound(null, x, y, z, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("glassdead"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F);
                              played++;
                           }
                        }
                     }
                  }
               }

               if (!this.world.isRemote) {
                  if (this.rock_type == 1) {
                     this.dropItem(OreSpawn.MySmallRock, 1);
                  }

                  if (this.rock_type == 2) {
                     this.dropItem(OreSpawn.MyRock, 1);
                  }

                  if (this.rock_type == 3) {
                     this.dropItem(OreSpawn.MyRedRock, 1);
                  }

                  if (this.rock_type == 4) {
                     this.dropItem(OreSpawn.MyGreenRock, 1);
                  }

                  if (this.rock_type == 5) {
                     this.dropItem(OreSpawn.MyBlueRock, 1);
                  }

                  if (this.rock_type == 6) {
                     this.dropItem(OreSpawn.MyPurpleRock, 1);
                  }

                  if (this.rock_type == 7) {
                     this.dropItem(OreSpawn.MySpikeyRock, 1);
                  }

                  if (this.rock_type == 8) {
                     this.dropItem(OreSpawn.MyTNTRock, 1);
                  }

                  if (this.rock_type == 9) {
                     this.dropItem(OreSpawn.MyCrystalRedRock, 1);
                  }

                  if (this.rock_type == 10) {
                     this.dropItem(OreSpawn.MyCrystalGreenRock, 1);
                  }

                  if (this.rock_type == 11) {
                     this.dropItem(OreSpawn.MyCrystalBlueRock, 1);
                  }

                  if (this.rock_type == 12) {
                     this.dropItem(OreSpawn.MyCrystalTNTRock, 1);
                  }
               }
            }

            this.setDead();
         }
      }
   }

   public void onUpdate() {
      int x = (int)this.posX;
      int y = (int)this.posY;
      int z = (int)this.posZ;
      super.onUpdate();
      this.my_rotation += 30.0F;
      this.my_rotation %= 360.0F;
      this.rotationPitch = this.prevRotationPitch = this.my_rotation;
      this.myage++;
      if (this.myage > 1000) {
         this.setDead();
      }

      if (this.world.isRemote) {
         this.rock_type = this.getRockType();
      } else {
         this.setRockType(this.rock_type);
      }

      Block bid = this.world.getBlockState(new BlockPos(x, y, z)).getBlock();
      if (bid == Blocks.WATER
         && this.motionY < -0.15F
         && this.motionY > -0.55F
         && (float)(this.motionX * this.motionX + this.motionZ * this.motionZ) > 0.5F) {
         this.motionY = -(this.motionY * 3.0 / 4.0);
         this.motionX = this.motionX * 3.0 / 4.0;
         this.motionZ = this.motionZ * 3.0 / 4.0;
      }
   }
}

package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IrukandjiArrow extends EntityArrow {
    private static final DataParameter<Byte> DW_16 = EntityDataManager.<Byte>createKey(IrukandjiArrow.class, DataSerializers.BYTE);

   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private int inTile = 0;
   private int inData = 0;
   private boolean inGround = false;
   private int ticksInGround;
   private int ticksInAir = 0;
   private int knockbackStrength;

   public IrukandjiArrow(World par1World) {
      super(par1World);
   }

   public IrukandjiArrow(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   public IrukandjiArrow(World par1World, EntityLiving par2EntityLiving, EntityLivingBase par3EntityLiving, float par4, float par5) {
      super(par1World, par2EntityLiving);
      this.shootingEntity = par2EntityLiving;
      this.posY = par2EntityLiving.posY + par2EntityLiving.getEyeHeight() - 0.1;
      double d0 = par3EntityLiving.posX - par2EntityLiving.posX;
      double d1 = par3EntityLiving.getEntityBoundingBox().minY + (double)(par3EntityLiving.height / 3.0F) - this.posY;
      double d2 = par3EntityLiving.posZ - par2EntityLiving.posZ;
      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
      if (d3 >= 1.0E-7) {
         float f2 = (float)(Math.atan2(d2, d0) * 180.0 / Math.PI) - 90.0F;
         float f3 = (float)(-(Math.atan2(d1, d3) * 180.0 / Math.PI));
         double d4 = d0 / d3;
         double d5 = d2 / d3;
         this.setLocationAndAngles(par2EntityLiving.posX + d4, this.posY, par2EntityLiving.posZ + d5, f2, f3);
         this.shoot(d0, d1 + d3 * 0.2F, d2, par4, par5);
      }
   }

   public IrukandjiArrow(World par1World, EntityPlayer par3EntityPlayer, float par3) {
      super(par1World, par3EntityPlayer);
      this.shoot(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 0.0F, par3 * 3.0F, 1.0F);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_16, (byte)0);
   }

   @Override
   protected net.minecraft.item.ItemStack getArrowStack() {
      return com.electrifiedded.orespawn.OreSpawn.MyIrukandjiArrow != null
         ? new net.minecraft.item.ItemStack(com.electrifiedded.orespawn.OreSpawn.MyIrukandjiArrow)
         : new net.minecraft.item.ItemStack(net.minecraft.init.Items.ARROW);
   }

   public void shoot(double par1, double par3, double par5, float par7, float par8) {
      float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
      par1 /= var9;
      par3 /= var9;
      par5 /= var9;
      par1 *= par7;
      par3 *= par7;
      par5 *= par7;
      this.motionX = par1;
      this.motionY = par3;
      this.motionZ = par5;
      float var10 = MathHelper.sqrt(par1 * par1 + par5 * par5);
      this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0 / Math.PI);
      this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0 / Math.PI);
      this.ticksInGround = 0;
   }

   public void onUpdate() {
      super.onEntityUpdate();
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float var1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
         this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / Math.PI);
      }

      Block var16 = this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock();
      if (var16 != Blocks.AIR) {
         AxisAlignedBB var2 = this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getCollisionBoundingBox(this.world, new BlockPos(this.xTile, this.yTile, this.zTile));
         if (var2 != Block.NULL_AABB && var2.offset(this.xTile, this.yTile, this.zTile).contains(new Vec3d(this.posX, this.posY, this.posZ))) {
            this.inGround = true;
         }
      }

      if (this.arrowShake > 0) {
         this.arrowShake--;
      }

      if (this.inGround) {
         Block var18 = this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock();
         int var19 = this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock().getMetaFromState(world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)));
         if (var18 != Blocks.AIR && var19 == this.inData) {
            this.ticksInGround++;
            if (this.ticksInGround == 50 && !this.world.isRemote) {
               this.dropItem(OreSpawn.MyIrukandjiArrow, 1);
               this.setDead();
            }
         } else {
            this.inGround = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         }
      } else {
         this.ticksInAir++;
         Vec3d var17 = new Vec3d(this.posX, this.posY, this.posZ);
         Vec3d var3 = new Vec3d(
            this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ
         );
          RayTraceResult var4 = this.world.rayTraceBlocks(var17, var3, false);
          if (var4 != null && var4.typeOfHit == RayTraceResult.Type.BLOCK
             && this.world.getBlockState(var4.getBlockPos()).getMaterial().isLiquid()) {
             var4 = null;
          }
         var17 = new Vec3d(this.posX, this.posY, this.posZ);
         var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (var4 != null) {
            var3 = new Vec3d(var4.hitVec.x, var4.hitVec.y, var4.hitVec.z);
         }

         Entity var5 = null;
         List var6 = this.world
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
         double var7 = 0.0;

         for (int var9 = 0; var9 < var6.size(); var9++) {
            Entity var10 = (Entity)var6.get(var9);
            if (var10.canBeCollidedWith() && (var10 != this.shootingEntity || this.ticksInAir >= 5) && !(var10 instanceof Elevator)) {
               if (var10 instanceof Cephadrome) {
                  Cephadrome c = (Cephadrome)var10;
                  if (c.getControllingPassenger() != null) {
                     continue;
                  }
               }

               if (var10 instanceof Dragon) {
                  Dragon c = (Dragon)var10;
                  if (c.getControllingPassenger() != null) {
                     continue;
                  }
               }

               if (var10 instanceof EntityHorse) {
                  EntityHorse c = (EntityHorse)var10;
                  if (c.getControllingPassenger() != null) {
                     continue;
                  }
               }

               float var11 = 0.3F;
               AxisAlignedBB var12 = var10.getEntityBoundingBox().grow(var11, var11, var11);
               RayTraceResult var13 = var12.calculateIntercept(var17, var3);
               if (var13 != null) {
                  double var14 = var17.distanceTo(var13.hitVec);
                  if (var14 < var7 || var7 == 0.0) {
                     var5 = var10;
                     var7 = var14;
                  }
               }
            }
         }

         if (var5 != null) {
            var4 = new RayTraceResult(var5);
         }

         if (var4 != null) {
            if (var4.entityHit != null) {
               float var23 = 100.0F;
               if (OreSpawn.ultimate_sword_pvp == 0) {
                  if (var4.entityHit instanceof EntityPlayer || var4.entityHit instanceof Girlfriend || var4.entityHit instanceof Boyfriend) {
                     EntityLivingBase e = (EntityLivingBase)var4.entityHit;
                     this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.hit"), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                     this.setDead();
                     return;
                  }

                  if (var4.entityHit instanceof EntityTameable) {
                     EntityTameable t = (EntityTameable)var4.entityHit;
                     if (t.isTamed()) {
                        this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.hit"), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                        this.setDead();
                        return;
                     }
                  }
               }

               if (this.getIsCritical()) {
                  var23 += this.rand.nextInt((int)var23 / 2 + 2);
               }

               DamageSource var21 = null;
               if (this.shootingEntity == null) {
                  var21 = DamageSource.causeArrowDamage(this, this);
               } else {
                  var21 = DamageSource.causeArrowDamage(this, this.shootingEntity);
               }

               if (this.isBurning()) {
                  var4.entityHit.setFire(5);
               }

               if (var4.entityHit.attackEntityFrom(var21, var23)) {
                  if (var4.entityHit instanceof EntityLiving) {
                     EntityLiving var24 = (EntityLiving)var4.entityHit;
                     if (!this.world.isRemote) {
                        var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
                     }

                     if (this.knockbackStrength > 0) {
                        float var26 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                        if (var26 > 0.0F) {
                           var4.entityHit
                              .addVelocity(
                                 this.motionX * this.knockbackStrength * 0.6F / var26, 0.1, this.motionZ * this.knockbackStrength * 0.6F / var26
                              );
                        }
                     }

                     if (this.shootingEntity != null
                        && var4.entityHit != this.shootingEntity
                        && var4.entityHit instanceof EntityPlayer
                        && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                     }
                  }

                  this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.hit"), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                  this.setDead();
               } else {
                  this.motionX *= -0.10000000149;
                  this.motionY *= -0.10000000149;
                  this.motionZ *= -0.10000000149;
                  this.rotationYaw += 180.0F;
                  this.prevRotationYaw += 180.0F;
                  this.ticksInAir = 0;
               }
            } else {
               this.xTile = var4.getBlockPos().getX();
               this.yTile = var4.getBlockPos().getY();
               this.zTile = var4.getBlockPos().getZ();
               this.inData = this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock().getMetaFromState(world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)));
               this.motionX = (float)(var4.hitVec.x - this.posX);
               this.motionY = (float)(var4.hitVec.y - this.posY);
               this.motionZ = (float)(var4.hitVec.z - this.posZ);
               float var20 = MathHelper.sqrt(
                  this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ
               );
               this.posX = this.posX - this.motionX / var20 * 0.05;
               this.posY = this.posY - this.motionY / var20 * 0.05;
               this.posZ = this.posZ - this.motionZ / var20 * 0.05;
               this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.hit"), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
               this.inGround = true;
               this.arrowShake = 7;
               this.setIsCritical(false);
            }
         }

         if (this.getIsCritical()) {
            for (int var221 = 0; var221 < 4; var221++) {
               this.world
                  .spawnParticle(EnumParticleTypes.CRIT,
                     this.posX + this.motionX * var221 / 4.0,
                     this.posY + this.motionY * var221 / 4.0,
                     this.posZ + this.motionZ * var221 / 4.0,
                     -this.motionX,
                     -this.motionY + 0.2,
                     -this.motionZ
                  );
            }
         }

         this.posX = this.posX + this.motionX;
         this.posY = this.posY + this.motionY;
         this.posZ = this.posZ + this.motionZ;
         float var20 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
         this.rotationPitch = (float)(Math.atan2(this.motionY, var20) * 180.0 / Math.PI);

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
         float var22 = 0.99F;
         float var11 = 0.05F;
         if (this.isInWater()) {
            for (int var25 = 0; var25 < 4; var25++) {
               float var26 = 0.25F;
               this.world
                  .spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                     this.posX - this.motionX * var26,
                     this.posY - this.motionY * var26,
                     this.posZ - this.motionZ * var26,
                     this.motionX,
                     this.motionY,
                     this.motionZ
                  );
            }

            var22 = 0.8F;
         }

         this.motionX *= var22;
         this.motionY *= var22;
         this.motionZ *= var22;
         this.motionY -= var11;
         this.setPosition(this.posX, this.posY, this.posZ);
         this.doBlockCollisions();
      }
   }

   public void setKnockbackStrength(int par1) {
      this.knockbackStrength = par1;
   }

   public void setDamage(double par1) {
   }

   public double getDamage() {
      return 100.0;
   }
}

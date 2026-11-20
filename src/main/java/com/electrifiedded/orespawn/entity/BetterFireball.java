package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import com.electrifiedded.orespawn.util.MyUtils;

public class BetterFireball extends EntityFireball {
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private boolean inGround = false;
   public EntityLivingBase shootingEntity;
   private int ticksAlive;
   private int ticksInAir = 0;
   public double accelerationX;
   public double accelerationY;
   public double accelerationZ;
   public int explosionPower = 1;
   private int notme = 0;
   private boolean small = false;

   public BetterFireball(World par1World) {
      super(par1World);
      this.setSize(1.0F, 1.0F);
   }

   protected void entityInit() {
   }

   public BetterFireball(World par1World, EntityLivingBase par2EntityLiving, double par3, double par5, double par7) {
      super(par1World);
      this.shootingEntity = par2EntityLiving;
      this.setSize(1.0F, 1.0F);
      this.setLocationAndAngles(
         par2EntityLiving.posX,
         par2EntityLiving.posY,
         par2EntityLiving.posZ,
         par2EntityLiving.rotationYaw,
         par2EntityLiving.rotationPitch
      );
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionX = this.motionY = this.motionZ = 0.0;
      double var9 = MathHelper.sqrt(par3 * par3 + par5 * par5 + par7 * par7);
      this.accelerationX = par3 / var9 * 0.1;
      this.accelerationY = par5 / var9 * 0.1;
      this.accelerationZ = par7 / var9 * 0.1;
   }

   public void setNotMe() {
      this.notme = 1;
   }

   public void setBig() {
      this.explosionPower = 2;
   }

   public void setReallyBig() {
      this.explosionPower = 4;
   }

   public void setSmall() {
      this.small = true;
      this.setSize(0.3125F, 0.3125F);
   }

   public void onUpdate() {
      if (this.ticksAlive < 600 && this.ticksInAir < 600) {
         if (!this.world.isRemote
            || (this.shootingEntity == null || !this.shootingEntity.isDead)
               && this.world.isBlockLoaded(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ))) {
            super.onUpdate();
            this.setFire(1);
            if (this.inGround) {
               Block var1 = this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock();
               if (var1 != Blocks.AIR) {
                  this.ticksAlive++;
               }

               this.inGround = false;
               this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
               this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
               this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            } else {
               this.ticksInAir++;
            }

            Vec3d var15 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult var3 = this.world.rayTraceBlocks(var15, var2);
            var15 = new Vec3d(this.posX, this.posY, this.posZ);
            var2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var3 != null) {
               var2 = new Vec3d(var3.hitVec.x, var3.hitVec.y, var3.hitVec.z);
            }

            Entity var4 = null;
            List var5 = this.world
               .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
            double var6 = 0.0;

            for (int var8 = 0; var8 < var5.size(); var8++) {
               Entity var9 = (Entity)var5.get(var8);
               if (this.shootingEntity == var9) {
                  var3 = null;
                  break;
               }

               if (var9 instanceof BetterFireball) {
                  var3 = null;
                  break;
               }

               if (var9 instanceof GodzillaHead) {
                  var3 = null;
                  break;
               }

               if (MyUtils.isRoyalty(var9)) {
                  var3 = null;
                  break;
               }

               if (this.notme != 0 && (var9 instanceof EntityPlayer || var9 instanceof Dragon || var9 instanceof Mothra)) {
                  var3 = null;
                  break;
               }

               if (var9.canBeCollidedWith() && (!var9.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
                  AxisAlignedBB var11 = var9.getEntityBoundingBox().grow(0.3F, 0.3F, 0.3F);
                  RayTraceResult var12 = var11.calculateIntercept(var15, var2);
                  if (var12 != null) {
                     double var13 = var15.distanceTo(var12.hitVec);
                     if (var13 < var6 || var6 == 0.0) {
                        var4 = var9;
                        var6 = var13;
                     }
                  }
               }
            }

            if (var4 != null) {
               var3 = new RayTraceResult(var4);
            }

            if (var3 != null) {
               this.onImpact(var3);
            }

            this.posX = this.posX + this.motionX;
            this.posY = this.posY + this.motionY;
            this.posZ = this.posZ + this.motionZ;
            float var16 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) + 90.0F;
            this.rotationPitch = (float)(Math.atan2(var16, this.motionY) * 180.0 / Math.PI) - 90.0F;

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
            float var17 = 0.95F;
            if (this.isInWater()) {
               for (int var19 = 0; var19 < 4; var19++) {
                  float var18 = 0.25F;
                  this.world
                     .spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE,
                        this.posX - this.motionX * var18,
                        this.posY - this.motionY * var18,
                        this.posZ - this.motionZ * var18,
                        this.motionX,
                        this.motionY,
                        this.motionZ
                     );
               }

               var17 = 1.0F;
            }

            this.motionX = this.motionX + this.accelerationX;
            this.motionY = this.motionY + this.accelerationY;
            this.motionZ = this.motionZ + this.accelerationZ;
            this.motionX *= var17;
            this.motionY *= var17;
            this.motionZ *= var17;
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
            this.setPosition(this.posX, this.posY, this.posZ);
         } else {
            this.setDead();
         }
      } else {
         this.setDead();
      }
   }

   protected void onImpact(RayTraceResult hit) {
      if (!this.world.isRemote) {
         if (hit.entityHit != null) {
            if (hit.entityHit instanceof BetterFireball) {
               return;
            }

            if (hit.entityHit instanceof Mothra) {
               return;
            }

            if (this.notme != 0 && (hit.entityHit instanceof Dragon || hit.entityHit instanceof EntityPlayer)) {
               this.setDead();
               return;
            }

            Entity e = hit.entityHit;
            if (e instanceof EntityLiving) {
               EntityLiving el = (EntityLiving)e;
               if (el.width * el.height > 30.0F
                  && !MyUtils.isRoyalty(el)
                  && !(el instanceof Godzilla)
                  && !(el instanceof GodzillaHead)
                  && !(el instanceof PitchBlack)
                  && !(el instanceof Kraken)) {
                  el.setHealth(el.getHealth() / 2.0F);
               }
            }

            if (!this.small) {
               hit.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 10.0F);
               hit.entityHit.setFire(5);
            } else {
               hit.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F);
               hit.entityHit.setFire(5);
            }
         } else {
            int i = hit.getBlockPos().getX();
            int j = hit.getBlockPos().getY();
            int k = hit.getBlockPos().getZ();
            switch (hit.sideHit.getIndex()) {
               case 0:
                  --j;
                  break;
               case 1:
                  ++j;
                  break;
               case 2:
                  --k;
                  break;
               case 3:
                  ++k;
                  break;
               case 4:
                  --i;
                  break;
               case 5:
                  ++i;
            }

            if (this.world.isAirBlock(new BlockPos(i, j, k))) {
               this.world.setBlockState(new BlockPos(i, j, k), Blocks.FIRE.getDefaultState());
            }
         }

         if (!this.small) {
            this.world
               .newExplosion(
                  (Entity)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.explosionPower,
                  true,
                  this.world.getGameRules().getBoolean("mobGriefing")
               );
         }

         this.setDead();
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("ExplosionPower", this.explosionPower);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if (par1NBTTagCompound.hasKey("ExplosionPower")) {
         this.explosionPower = par1NBTTagCompound.getInteger("ExplosionPower");
      }
   }
}

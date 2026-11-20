package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MyEntityAIFollowOwner extends EntityAIBase {
   private EntityTameable thePet;
   private EntityLivingBase theOwner;
   World theWorld;
   private float followSpeed;
   private PathNavigate petPathfinder;
   private int timeToRecalcPath;
   float maxDist;
   float minDist;
   private boolean oldWaterCost;

   public MyEntityAIFollowOwner(EntityTameable par1EntityTameable, float par2, float par3, float par4) {
      this.thePet = par1EntityTameable;
      this.theWorld = par1EntityTameable.world;
      this.followSpeed = par2;
      this.petPathfinder = par1EntityTameable.getNavigator();
      this.minDist = par4;
      this.maxDist = par3;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.thePet.getOwner();
      if (var1 == null) {
         return false;
      } else {
         this.theOwner = var1;
         if (this.thePet.isSitting()) {
            return false;
         } else if (this.thePet instanceof Girlfriend && OreSpawn.valentines_day != 0) {
            return false;
         } else {
            return this.thePet != null
                  && (this.thePet.posY < 60.0 || !this.thePet.world.isDaytime())
                  && this.thePet.getDistanceSq(var1) > this.maxDist / 2.0F * (this.maxDist / 2.0F)
               ? true
               : !(this.thePet.getDistanceSq(var1) < this.maxDist * this.maxDist);
         }
      }
   }

   public boolean shouldContinueExecuting() {
      if (this.thePet.isSitting()) {
         return false;
      } else if (this.petPathfinder.noPath()) {
         return false;
      } else {
         if (this.thePet != null && this.thePet instanceof EntityTameable) {
            EntityTameable gf = this.thePet;
            EntityLivingBase var1 = gf.getOwner();
            if (var1 != null
               && (int)gf.posZ == (int)var1.posZ
               && (int)gf.posX == (int)var1.posX
               && (int)gf.posY < (int)var1.posY + 2
               && (int)gf.posY > (int)var1.posY - 2) {
               return false;
            }
         }

         return this.thePet.getDistanceSq(this.theOwner) > this.minDist * this.minDist;
      }
   }

   public void startExecuting() {
      this.timeToRecalcPath = 0;
      // PORT-TODO: 1.12 PathNavigate has no avoidsWater flag; water cost handled by node processor
      this.oldWaterCost = false;
   }

   public void resetTask() {
      this.theOwner = null;
      this.petPathfinder.clearPath();
   }

   public void updateTask() {
      this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
      if (!this.thePet.isSitting() && --this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed) && this.thePet.getDistanceSq(this.theOwner) >= 144.0) {
            int var1 = MathHelper.floor(this.theOwner.posX) - 2;
            int var2 = MathHelper.floor(this.theOwner.posZ) - 2;
            int var3 = MathHelper.floor(this.theOwner.getEntityBoundingBox().minY);

            for (int var4 = 0; var4 <= 4; var4++) {
               for (int var5 = 0; var5 <= 4; var5++) {
                if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3)
                   && this.theWorld.getBlockState(new BlockPos(var1 + var4, var3 - 1, var2 + var5)).isSideSolid(this.theWorld, new BlockPos(var1 + var4, var3 - 1, var2 + var5), net.minecraft.util.EnumFacing.UP)
                   && !this.theWorld.getBlockState(new BlockPos(var1 + var4, var3, var2 + var5)).isNormalCube()
                   && !this.theWorld.getBlockState(new BlockPos(var1 + var4, var3 + 1, var2 + var5)).isNormalCube()) {
                     this.thePet.setLocationAndAngles(var1 + var4 + 0.5F, var3, var2 + var5 + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
                     this.petPathfinder.clearPath();
                     return;
                  }
               }
            }
         }
      }
   }
}

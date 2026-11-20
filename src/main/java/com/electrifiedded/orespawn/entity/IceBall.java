package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.math.BlockPos;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class IceBall extends LaserBall {
   private int my_index = 84;
   private int icemaker = 0;

   public IceBall(World par1World) {
      super(par1World);
      super.setIceBall();
   }

   public IceBall(World par1World, int par2) {
      super(par1World);
      super.setIceBall();
   }

   public IceBall(World par1World, EntityLivingBase par2EntityLiving) {
      super(par1World, par2EntityLiving);
      super.setIceBall();
   }

   public IceBall(World par1World, EntityLivingBase par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
      super.setIceBall();
   }

   public IceBall(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
      super.setIceBall();
   }

   public int getIceBallIndex() {
      return this.my_index;
   }

   public void setIceMaker(int i) {
      this.icemaker = i;
   }

   @Override
   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (par1MovingObjectPosition.entityHit != null && MyUtils.isRoyalty(par1MovingObjectPosition.entityHit)) {
         this.setDead();
      } else {
         super.onImpact(par1MovingObjectPosition);
         if (this.icemaker != 0) {
            for (int i = 0; i < 5; i++) {
               int x = this.world.rand.nextInt(4);
               if (this.world.rand.nextInt(2) == 1) {
                  x = -x;
               }

               int y = this.world.rand.nextInt(4);
               if (this.world.rand.nextInt(2) == 1) {
                  y = -y;
               }

               int z = this.world.rand.nextInt(4);
               if (this.world.rand.nextInt(2) == 1) {
                  z = -z;
               }

               x = (int)(x + par1MovingObjectPosition.hitVec.x);
               y = (int)(y + par1MovingObjectPosition.hitVec.y);
               z = (int)(z + par1MovingObjectPosition.hitVec.z);
               this.world.setBlockState(new BlockPos(x, y, z), Blocks.ICE.getDefaultState());
            }
         }

         this.setDead();
      }
   }
}

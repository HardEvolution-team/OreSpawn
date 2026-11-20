package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLunaMoth extends EntityButterfly {
   private BlockPos currentFlightTarget = null;
   public int moth_type = 0;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public EntityLunaMoth(World par1World) {
      super(par1World);
      this.moth_type = OreSpawn.OreSpawnRand.nextInt(4);
      this.setSize(0.5F, 0.5F);
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
   }

   @Override
   protected void collideWithEntity(Entity par1Entity) {
   }

   @Override
   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.motionY *= 0.6;
   }

   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
      int found = 0;

      for (int i = -dy; i <= dy; i++) {
         for (int j = -dz; j <= dz; j++) {
            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
            if (bid == Blocks.TORCH || bid == OreSpawn.ExtremeTorch) {
               int d = dx * dx + j * j + i * i;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + dx;
                  this.ty = y + i;
                  this.tz = z + j;
                  found++;
               }
            }

            bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock();
            if (bid == Blocks.TORCH || bid == OreSpawn.ExtremeTorch) {
               int d = dx * dx + j * j + i * i;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x - dx;
                  this.ty = y + i;
                  this.tz = z + j;
                  found++;
               }
            }
         }
      }

      for (int var12 = -dx; var12 <= dx; var12++) {
         for (int j = -dz; j <= dz; j++) {
            Block bidx = this.world.getBlockState(new BlockPos(x + var12, y + dy, z + j)).getBlock();
            if (bidx == Blocks.TORCH || bidx == OreSpawn.ExtremeTorch) {
               int d = dy * dy + j * j + var12 * var12;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var12;
                  this.ty = y + dy;
                  this.tz = z + j;
                  found++;
               }
            }

            bidx = this.world.getBlockState(new BlockPos(x + var12, y - dy, z + j)).getBlock();
            if (bidx == Blocks.TORCH || bidx == OreSpawn.ExtremeTorch) {
               int d = dy * dy + j * j + var12 * var12;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var12;
                  this.ty = y - dy;
                  this.tz = z + j;
                  found++;
               }
            }
         }
      }

      for (int var13 = -dx; var13 <= dx; var13++) {
         for (int j = -dy; j <= dy; j++) {
            Block bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z + dz)).getBlock();
            if (bidxx == Blocks.TORCH || bidxx == OreSpawn.ExtremeTorch) {
               int d = dz * dz + j * j + var13 * var13;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var13;
                  this.ty = y + j;
                  this.tz = z + dz;
                  found++;
               }
            }

            bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z - dz)).getBlock();
            if (bidxx == Blocks.TORCH || bidxx == OreSpawn.ExtremeTorch) {
               int d = dz * dz + j * j + var13 * var13;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var13;
                  this.ty = y + j;
                  this.tz = z - dz;
                  found++;
               }
            }
         }
      }

      return found != 0;
   }

   @Override
   protected void updateAITasks() {
      int keep_trying = 25;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.rand.nextInt(100) != 0
            && !(this.getDistanceSq(currentFlightTarget) < 4.0F)) {
            if (!this.world.isDaytime() && this.rand.nextInt(10) == 0) {
               this.closest = 99999;
               this.tx = this.ty = this.tz = 0;

               for (int i = 2; i < 15 && !this.scan_it((int)this.posX, (int)this.posY, (int)this.posZ, i, i, i); i++) {
                  if (i >= 6) {
                     i++;
                  }
               }

               if (this.closest < 99999) {
                  this.currentFlightTarget = new BlockPos(this.tx, this.ty + 1, this.tz);
               }
            }
         } else {
            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(10) - this.rand.nextInt(10),
                     (int)this.posY + this.rand.nextInt(6) - 2,
                     (int)this.posZ + this.rand.nextInt(10) - this.rand.nextInt(10)
                  );
               bid = this.world
                  .getBlockState(this.currentFlightTarget).getBlock();
            }
         }

         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.1F;
         this.motionY = this.motionY + (Math.signum(var3) * 0.68 - this.motionY) * 0.1F;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.1F;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.75F;
         this.rotationYaw += var8;
      }
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   @Override
   public boolean doesEntityNotTriggerPressurePlate() {
      return true;
   }

   @Override
   public boolean getCanSpawnHere() {
      Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();
      if (bid != Blocks.AIR) {
         return false;
      } else if (this.world.isDaytime()) {
         return false;
      } else {
         return this.world.provider.getDimension() == OreSpawn.DimensionID4 ? true : !(this.posY < 50.0);
      }
   }

   @Override
   public void initCreature() {
   }
}

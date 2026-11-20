package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class Island extends EntityAnimal {
   private float dir = 0.0F;
   private float speed = 0.1F;
   private int radius = 5;
   private int depth = 3;
   private int timer = 73;
   private int just_spawned = 1;
   private int ticker = 0;
   private int once = 1;
   private double myX;
   private double myY;
   private double myZ;
   private int dirchange;

   public Island(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.5F);
      this.ticker = par1World.rand.nextInt(50);
      this.dirchange = this.world.rand.nextInt(2500);
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionX = this.motionY = this.motionZ = 0.0;
      if (!this.world.isRemote) {
         if (this.once != 0) {
            this.myX = this.posX;
            this.myY = this.posY;
            this.myZ = this.posZ;
            this.once = 0;
         }

         if (this.just_spawned != 0) {
            this.dir = this.world.rand.nextFloat() * (float) Math.PI;
            if (this.world.rand.nextInt(2) == 1) {
               this.dir *= -1.0F;
            }

            if (this.world.rand.nextInt(40) != 1) {
               this.radius = 3 + this.world.rand.nextInt(4);
               this.depth = 2 + this.world.rand.nextInt(3);
               this.speed = this.world.rand.nextFloat() / 50.0F * OreSpawn.IslandSpeedFactor;
            } else {
               this.radius = 6 + this.world.rand.nextInt(5);
               this.depth = 3 + this.world.rand.nextInt(4);
               this.speed = this.world.rand.nextFloat() / 200.0F * OreSpawn.IslandSpeedFactor;
            }

            this.create_island();
            this.ticker = this.world.rand.nextInt(50);
            this.dirchange = this.world.rand.nextInt(10000);
         }

         this.ticker++;
         if (this.ticker >= this.timer) {
            this.update_island();
            this.ticker = 0;
         }

         this.dirchange--;
         if (this.dirchange <= 0) {
            this.dirchange = this.world.rand.nextInt(5000);
            this.dir = this.world.rand.nextFloat() * (float) Math.PI;
            if (this.world.rand.nextInt(2) == 1) {
               this.dir *= -1.0F;
            }
         }

         this.just_spawned = 0;
      }
   }

   public void onLivingUpdate() {
      if (this.world.isRemote) {
         super.onLivingUpdate();
      }
   }

   protected void handleJumpWater() {
   }

   protected void updateAITasks() {
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   protected boolean canDespawn() {
      return false;
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.just_spawned = par1NBTTagCompound.getInteger("JustSpawned");
      this.depth = par1NBTTagCompound.getInteger("Idepth");
      this.radius = par1NBTTagCompound.getInteger("Iradius");
      this.speed = par1NBTTagCompound.getFloat("Ispeed");
      this.dir = par1NBTTagCompound.getFloat("Idir");
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("JustSpawned", this.just_spawned);
      par1NBTTagCompound.setInteger("Idepth", this.depth);
      par1NBTTagCompound.setInteger("Iradius", this.radius);
      par1NBTTagCompound.setFloat("Ispeed", this.speed);
      par1NBTTagCompound.setFloat("Idir", this.dir);
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return null;
   }

   private void create_island() {
      double deltadir = 0.10471975333333333;
      double deltamag = 0.35F;
      int ixlast = 0;
      int izlast = 0;
      int xoff = 0;
      int zoff = 0;

      for (int i = 0; i < this.depth; i++) {
         izlast = 0;
         ixlast = 0;

         for (double curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
            double tradius = this.radius;
            tradius /= i + 1;

            for (double h = 0.75; h < tradius; h += deltamag) {
               int ix = (int)(this.posX + Math.cos(curdir + this.dir) * h);
               int iz = (int)(this.posZ + Math.sin(curdir + this.dir) * h);
               if (ix != ixlast || iz != izlast) {
                  ixlast = ix;
                  izlast = iz;
                  if (i == 0) {
                     Block bid;
                     if ((bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY - i + 1, iz)).getBlock()) == Blocks.AIR) {
                        if (this.world.rand.nextInt(5000) == 1) {
                           this.world.setBlockState(new BlockPos(ix, (int)this.posY - i + 1, iz), (Blocks.LAVA).getDefaultState());
                        } else {
                           this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.MYCELIUM);
                           if (this.world.rand.nextInt(20) == 1
                              && this.world.getBlockState(new BlockPos(ix, (int)this.posY - i + 2, iz)).getBlock() == Blocks.AIR) {
                              if (this.world.rand.nextInt(2) == 1) {
                                 this.world.setBlockState(new BlockPos(ix, (int)this.posY - i + 2, iz), (Blocks.BROWN_MUSHROOM).getDefaultState());
                              } else {
                                 this.world.setBlockState(new BlockPos(ix, (int)this.posY - i + 2, iz), (Blocks.RED_MUSHROOM).getDefaultState());
                              }
                           }
                        }
                     } else if (bid == Blocks.BEDROCK) {
                        this.setDead();
                        return;
                     }
                  } else if (this.world.rand.nextInt(10) == 1) {
                     this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.DIAMOND_ORE);
                  } else {
                     this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.END_STONE);
                  }
               }
            }
         }
      }

      if (this.posX < 0.0) {
         xoff = -1;
      }

      if (this.posZ < 0.0) {
         zoff = -1;
      }

      this.world.setBlockState(new BlockPos((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff), (Blocks.AIR).getDefaultState());
      this.FastSetBlock((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff, Blocks.AIR);
   }

   private void update_island() {
      double deltadir = 0.10471975333333333;
      double deltamag = 0.35F;
      double pi2 = 1.57079632675;
      int ixlast = 0;
      int izlast = 0;
      int xoff = 0;
      int zoff = 0;
      this.myX = this.myX + this.speed * Math.cos(this.dir);
      this.myZ = this.myZ + this.speed * Math.sin(this.dir);
      int mx = (int)this.myX;
      int mz = (int)this.myZ;
      int px = (int)this.posX;
      int pz = (int)this.posZ;
      if (mx != px || mz != pz) {
         for (int i = 0; i < this.depth; i++) {
            izlast = 0;
            ixlast = 0;

            for (double curdir = -3.3; curdir < 3.3; curdir += deltadir / 2.0) {
               double tradius = this.radius;
               tradius /= i + 1;
               double h = 0.75;

               while (h < tradius) {
                  h += deltamag;
               }

               h -= deltamag;
               if (h < 0.75) {
                  h = 0.75;
               }

               for (; h < tradius + deltamag; h += deltamag / 2.0) {
                  int ix = (int)(this.posX + Math.cos(curdir + this.dir) * h);
                  int iz = (int)(this.posZ + Math.sin(curdir + this.dir) * h);
                  if (ix != ixlast || iz != izlast) {
                     ixlast = ix;
                     izlast = iz;
                     if (i == 0) {
                        Block bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY + 1 + 1, iz)).getBlock();
                        if (bid == Blocks.RED_MUSHROOM || bid == Blocks.BROWN_MUSHROOM) {
                           this.FastSetBlock(ix, (int)this.posY + 1 + 1, iz, Blocks.AIR);
                        }
                     }

                     this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.AIR);
                  }
               }
            }
         }

         if (this.posX < 0.0) {
            xoff = -1;
         }

         if (this.posZ < 0.0) {
            zoff = -1;
         }

         this.world.setBlockState(new BlockPos((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff), (Blocks.END_STONE).getDefaultState());
         this.posX = (int)this.myX;
         if (this.myX < 0.0) {
            this.posX -= 0.5;
         } else {
            this.posX += 0.5;
         }

         this.posZ = (int)this.myZ;
         if (this.myZ < 0.0) {
            this.posZ -= 0.5;
         } else {
            this.posZ += 0.5;
         }

         for (int var33 = 0; var33 < this.depth; var33++) {
            izlast = 0;
            ixlast = 0;

            for (double curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
               double tradius = this.radius;
               tradius /= var33 + 1;
               double h = 0.75;

               while (h < tradius) {
                  h += deltamag;
               }

               h -= deltamag * 3.0;
               if (h < 0.75) {
                  h = 0.75;
               }

               for (; h < tradius; h += deltamag) {
                  int ix = (int)(this.posX + Math.cos(curdir + this.dir) * h);
                  int iz = (int)(this.posZ + Math.sin(curdir + this.dir) * h);
                  if (ix != ixlast || iz != izlast) {
                     ixlast = ix;
                     izlast = iz;
                     if (var33 == 0) {
                        Block bid;
                        if ((bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY - var33 + 1, iz)).getBlock()) == Blocks.AIR) {
                           if (this.world.rand.nextInt(5000) == 1) {
                              this.world.setBlockState(new BlockPos(ix, (int)this.posY - var33 + 1, iz), (Blocks.LAVA).getDefaultState());
                           } else {
                              this.FastSetBlock(ix, (int)this.posY - var33 + 1, iz, Blocks.MYCELIUM);
                              if (this.world.rand.nextInt(20) == 1
                                 && this.world.getBlockState(new BlockPos(ix, (int)this.posY - var33 + 2, iz)).getBlock() == Blocks.AIR) {
                                 if (this.world.rand.nextInt(2) == 1) {
                                    this.world.setBlockState(new BlockPos(ix, (int)this.posY - var33 + 2, iz), (Blocks.BROWN_MUSHROOM).getDefaultState());
                                 } else {
                                    this.world.setBlockState(new BlockPos(ix, (int)this.posY - var33 + 2, iz), (Blocks.RED_MUSHROOM).getDefaultState());
                                 }
                              }
                           }
                        } else if (bid == Blocks.BEDROCK) {
                           this.setDead();
                           return;
                        }
                     } else {
                        Block bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY - var33 + 1, iz)).getBlock();
                        if (bid == Blocks.STONE) {
                           if (!this.world.isRemote) {
                              this.world.createExplosion(this, ix, this.posY - var33 + 1.0, iz, 5.0F, true);
                           }
                        } else if (this.world.rand.nextInt(10) == 1) {
                           this.FastSetBlock(ix, (int)this.posY - var33 + 1, iz, Blocks.DIAMOND_ORE);
                        } else {
                           this.FastSetBlock(ix, (int)this.posY - var33 + 1, iz, Blocks.END_STONE);
                        }
                     }
                  }
               }
            }
         }

         int var40 = 0;
         if (this.posX < 0.0) {
            var40 = -1;
         }

         int var41 = 0;
         if (this.posZ < 0.0) {
            var41 = -1;
         }

         this.world.setBlockState(new BlockPos((int)this.posX + var40, (int)this.posY, (int)this.posZ + var41), (Blocks.AIR).getDefaultState());
         this.FastSetBlock((int)this.posX + var40, (int)this.posY, (int)this.posZ + var41, Blocks.AIR);
      }

      if (this.world.rand.nextInt(2 + 2000 / this.timer) == 1) {
         AxisAlignedBB bb = new AxisAlignedBB(
            this.posX - 10.0,
            this.posY - 5.0,
            this.posZ - 10.0,
            this.posX + 10.0,
            this.posY + 5.0,
            this.posZ + 10.0
         );
         List<Entity> var5 = this.world.getEntitiesWithinAABB(Triffid.class, bb);
         Iterator var2 = var5.iterator();
         if (!var2.hasNext()) {
            EntityCreature newent = (EntityCreature)spawnCreature(
               this.world, "Triffid", this.posX, this.posY + 2.01, this.posZ
            );
         }
      }
   }

   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(par1, par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }

   protected Item getDropItem() {
      return Item.getItemFromBlock(OreSpawn.MyIslandBlock);
   }

   public void FastSetBlock(int ix, int iy, int iz, Block id) {
      OreSpawn.setBlockFast(this.world, ix, iy, iz, id, 0, 3);
   }
}

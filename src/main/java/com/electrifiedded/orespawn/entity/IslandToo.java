package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class IslandToo extends EntityAnimal {
   private int dir = 0;
   private float speed = 0.1F;
   private int width = 5;
   private int depth = 3;
   private int length = 10;
   private int timer = 42;
   private int just_spawned = 1;
   private int ticker = 0;
   private int once = 1;
   private double myX;
   private double myY;
   private double myZ;
   private int dirchange = 0;
   private int blocktype = 0;

   public IslandToo(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.5F);
      this.ticker = par1World.rand.nextInt(50);
      this.dirchange = this.world.rand.nextInt(5000);
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
            this.dir = this.world.rand.nextInt(4);
            if (this.world.rand.nextInt(40) != 1) {
               this.width = 1 + this.world.rand.nextInt(5 * OreSpawn.IslandSizeFactor);
               this.length = this.width;
               this.depth = 1 + this.world.rand.nextInt(4);
               this.speed = this.world.rand.nextFloat() / 40.0F * OreSpawn.IslandSpeedFactor;
               if (this.length * this.width * this.depth <= 64) {
                  this.speed *= 2.0F;
               }

               if (this.length * this.width * this.depth <= 32) {
                  this.speed *= 2.0F;
               }
            } else {
               this.width = 5 + this.world.rand.nextInt(8 * OreSpawn.IslandSizeFactor);
               this.length = this.width;
               this.depth = 3 + this.world.rand.nextInt(6);
               this.speed = this.world.rand.nextFloat() / 150.0F * OreSpawn.IslandSpeedFactor;
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
            this.dir = this.world.rand.nextInt(4);
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
      this.width = par1NBTTagCompound.getInteger("Iwidth");
      this.depth = par1NBTTagCompound.getInteger("Idepth");
      this.length = par1NBTTagCompound.getInteger("Ilength");
      this.speed = par1NBTTagCompound.getFloat("Ispeed");
      this.dir = par1NBTTagCompound.getInteger("Idir");
      this.blocktype = par1NBTTagCompound.getInteger("Iblocktype");
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("JustSpawned", this.just_spawned);
      par1NBTTagCompound.setInteger("Iwidth", this.width);
      par1NBTTagCompound.setInteger("Idepth", this.depth);
      par1NBTTagCompound.setInteger("Ilength", this.length);
      par1NBTTagCompound.setFloat("Ispeed", this.speed);
      par1NBTTagCompound.setInteger("Idir", this.dir);
      par1NBTTagCompound.setInteger("Iblocktype", this.blocktype);
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return null;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      int xoff = 0;
      int zoff = 0;
      int ix = (int)this.posX;
      int iz = (int)this.posZ;
      if (ix < 0) {
         boolean var8 = true;
         this.posX = ix;
         this.posX -= 0.5;
      } else {
         this.posX = ix;
         this.posX += 0.5;
      }

      if (iz < 0) {
         boolean var9 = true;
         this.posZ = iz;
         this.posZ -= 0.5;
      } else {
         this.posZ = iz;
         this.posZ += 0.5;
      }

      super.attackEntityFrom(par1DamageSource, par2);
      return ret;
   }

   private void create_island() {
      int xoff = 0;
      int zoff = 0;
      if (this.posX < 0.0) {
         xoff = 1;
      }

      if (this.posZ < 0.0) {
         zoff = 1;
      }

      for (int k = 0; k <= this.depth; k++) {
         int il = this.length / (this.depth - k + 1);
         if (il < 1) {
            il = 1;
         }

         for (int i = -il; i <= il; i++) {
            for (int j = -il; j <= il; j++) {
               int ix = (int)this.posX + j - xoff;
               int iz = (int)this.posZ + i - zoff;
               if (k == this.depth) {
                  Block bid;
                  if ((bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY + k, iz)).getBlock()) == Blocks.AIR) {
                     if (this.world.rand.nextInt(5000) == 1) {
                        this.world.setBlockState(new BlockPos(ix, (int)this.posY + k, iz), (Blocks.WATER).getDefaultState());
                     } else {
                        this.FastSetBlock(ix, (int)this.posY + k, iz, Blocks.GRASS);
                        if (this.world.rand.nextInt(30) == 1) {
                           if (this.world.getBlockState(new BlockPos(ix, (int)this.posY + k + 1, iz)).getBlock() == Blocks.AIR) {
                              if (this.world.rand.nextInt(2) == 1) {
                                 this.world.setBlockState(new BlockPos(ix, (int)this.posY + k + 1, iz), (OreSpawn.MyFlowerPinkBlock).getDefaultState());
                              } else {
                                 this.world.setBlockState(new BlockPos(ix, (int)this.posY + k + 1, iz), (OreSpawn.MyFlowerBlueBlock).getDefaultState());
                              }
                           }
                        } else if (this.world.rand.nextInt(100) == 1
                           && this.world.getBlockState(new BlockPos(ix, (int)this.posY + k + 1, iz)).getBlock() == Blocks.AIR) {
                           OreSpawn.OreSpawnTrees.SmallTree(this.world, ix, (int)this.posY + k + 1, iz);
                        }
                     }
                  } else if (bid == Blocks.BEDROCK) {
                     this.setDead();
                     return;
                  }
               } else {
                  this.mySetBlock(ix, (int)this.posY + k, iz);
               }
            }
         }
      }

      this.world.setBlockState(new BlockPos((int)this.posX - xoff, (int)this.posY, (int)this.posZ - zoff), (Blocks.AIR).getDefaultState());
   }

   private void mySetBlock(int ix, int iy, int iz) {
      Block bid = Blocks.STONE;
      if (this.blocktype == 0) {
         this.blocktype = 1 + this.world.rand.nextInt(8);
      }

      if (this.blocktype == 1 && this.world.rand.nextInt(5) == 1) {
         bid = Blocks.COAL_ORE;
      }

      if (this.blocktype == 2 && this.world.rand.nextInt(10) == 1) {
         bid = Blocks.IRON_ORE;
      }

      if (this.blocktype == 3 && this.world.rand.nextInt(20) == 1) {
         bid = Blocks.EMERALD_ORE;
      }

      if (this.blocktype == 4 && this.world.rand.nextInt(30) == 1) {
         bid = OreSpawn.MyOreTitaniumBlock;
      }

      if (this.blocktype == 5 && this.world.rand.nextInt(30) == 1) {
         bid = OreSpawn.MyOreUraniumBlock;
      }

      if (this.blocktype == 6 && this.world.rand.nextInt(30) == 1) {
         bid = OreSpawn.MyOreRubyBlock;
      }

      if (this.blocktype == 7 && this.world.rand.nextInt(30) == 1) {
         bid = OreSpawn.MyOreAmethystBlock;
      }

      if (this.blocktype == 8 && this.world.rand.nextInt(20) == 1) {
         bid = Blocks.GOLD_ORE;
      }

      if (bid == Blocks.STONE) {
         if (this.world.rand.nextInt(3000) == 1) {
            bid = OreSpawn.MyEnderPearlBlock;
         }

         if (this.world.rand.nextInt(3000) == 2) {
            bid = OreSpawn.MyEyeOfEnderBlock;
         }

         if (this.world.rand.nextInt(3000) == 3) {
            bid = OreSpawn.MyBlockAmethystBlock;
         }

         if (this.world.rand.nextInt(3000) == 4) {
            bid = OreSpawn.MyBlockRubyBlock;
         }

         if (this.world.rand.nextInt(3000) == 5) {
            bid = OreSpawn.MyBlockUraniumBlock;
         }

         if (this.world.rand.nextInt(3000) == 6) {
            bid = OreSpawn.MyBlockTitaniumBlock;
         }

         if (this.world.rand.nextInt(3000) == 7) {
            bid = Blocks.GOLD_BLOCK;
         }

         if (this.world.rand.nextInt(3000) == 8) {
            bid = Blocks.DIAMOND_BLOCK;
         }
      }

      this.FastSetBlock(ix, iy, iz, bid);
   }

   private void update_island() {
      int xoff = 0;
      int zoff = 0;
      if (this.dir == 0) {
         this.myZ = this.myZ - this.speed;
      } else if (this.dir == 1) {
         this.myZ = this.myZ + this.speed;
      } else if (this.dir == 2) {
         this.myX = this.myX + this.speed;
      } else {
         this.myX = this.myX - this.speed;
      }

      int ke = 0;
      int ks = 0;
      int je = 0;
      int js = 0;
      int mx = (int)this.myX;
      int mz = (int)this.myZ;
      int px = (int)this.posX;
      int pz = (int)this.posZ;
      if (mx != px || mz != pz) {
         byte var26;
         byte var28;
         byte var30;
         byte var32;
         if (this.dir == 0) {
            var30 = 1;
            var32 = 1;
            var26 = -1;
            var28 = 1;
         } else if (this.dir == 1) {
            var30 = -1;
            var32 = -1;
            var26 = -1;
            var28 = 1;
         } else if (this.dir == 2) {
            var30 = -1;
            var32 = 1;
            var26 = -1;
            var28 = -1;
         } else {
            var30 = -1;
            var32 = 1;
            var26 = 1;
            var28 = 1;
         }

         if (this.posX < 0.0) {
            xoff = 1;
         }

         if (this.posZ < 0.0) {
            zoff = 1;
         }

         for (int i = 0; i <= this.depth; i++) {
            int il = this.length / (this.depth - i + 1);
            if (il < 1) {
               il = 1;
            }

            for (int j = var30 * il; j <= var32 * il; j++) {
               for (int k = var26 * il; k <= var28 * il; k++) {
                  int ix = (int)this.posX + k - xoff;
                  int iz = (int)this.posZ + j - zoff;
                  if (i == this.depth) {
                     Block bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY + i + 1, iz)).getBlock();
                     if (bid == OreSpawn.MyFlowerPinkBlock
                        || bid == OreSpawn.MyFlowerBlueBlock
                        || bid == OreSpawn.MyFlowerBlackBlock
                        || bid == OreSpawn.MyFlowerScaryBlock) {
                        this.FastSetBlock(ix, (int)this.posY + i + 1, iz, Blocks.AIR);
                     }

                     if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                        this.world.setBlockState(new BlockPos(ix, (int)this.posY + i, iz), (Blocks.AIR).getDefaultState());
                     }

                     if (bid == OreSpawn.MySkyTreeLog) {
                        this.world.setBlockState(new BlockPos(ix, (int)this.posY + i + 1, iz), (Blocks.AIR).getDefaultState());
                        bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY + i + 2, iz)).getBlock();
                        if (bid == OreSpawn.MySkyTreeLog) {
                           this.world.setBlockState(new BlockPos(ix, (int)this.posY + i + 2, iz), (Blocks.AIR).getDefaultState());
                           bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY + i + 3, iz)).getBlock();
                           if (bid == OreSpawn.MySkyTreeLog) {
                              this.world.setBlockState(new BlockPos(ix, (int)this.posY + i + 3, iz), (Blocks.AIR).getDefaultState());
                           }
                        }
                     }

                     bid = this.world.getBlockState(new BlockPos(ix, (int)this.posY + i, iz)).getBlock();
                     if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                        this.world.setBlockState(new BlockPos(ix, (int)this.posY + i, iz), (Blocks.AIR).getDefaultState());
                     }
                  }

                  this.FastSetBlock(ix, (int)this.posY + i, iz, Blocks.AIR);
               }
            }
         }

         this.mySetBlock((int)this.posX - xoff, (int)this.posY, (int)this.posZ - zoff);
         this.posX = mx;
         if (this.myX < 0.0) {
            this.posX -= 0.5;
         } else {
            this.posX += 0.5;
         }

         this.posZ = mz;
         if (this.myZ < 0.0) {
            this.posZ -= 0.5;
         } else {
            this.posZ += 0.5;
         }

         if (this.dir == 0) {
            var30 = -1;
            var32 = -1;
            var26 = -1;
            var28 = 1;
         } else if (this.dir == 1) {
            var30 = 1;
            var32 = 1;
            var26 = -1;
            var28 = 1;
         } else if (this.dir == 2) {
            var30 = -1;
            var32 = 1;
            var26 = 1;
            var28 = 1;
         } else {
            var30 = -1;
            var32 = 1;
            var26 = -1;
            var28 = -1;
         }

         int var38 = 0;
         int var37 = 0;
         if (this.posX < 0.0) {
            var37 = 1;
         }

         if (this.posZ < 0.0) {
            var38 = 1;
         }

         this.world.setBlockState(new BlockPos((int)this.posX - var37, (int)this.posY, (int)this.posZ - var38), (Blocks.AIR).getDefaultState());

         for (int var23 = 0; var23 <= this.depth; var23++) {
            int il = this.length / (this.depth - var23 + 1);
            if (il < 1) {
               il = 1;
            }

            for (int j = var30 * il; j <= var32 * il; j++) {
               for (int k = var26 * il; k <= var28 * il; k++) {
                  int ix = (int)this.posX + k - var37;
                  int iz = (int)this.posZ + j - var38;
                  if (var23 == this.depth) {
                     Block bidx;
                     if ((bidx = this.world.getBlockState(new BlockPos(ix, (int)this.posY + var23, iz)).getBlock()) == Blocks.AIR) {
                        if (this.world.rand.nextInt(5000) == 1) {
                           this.world.setBlockState(new BlockPos(ix, (int)this.posY + var23, iz), (Blocks.WATER).getDefaultState());
                        } else {
                           this.FastSetBlock(ix, (int)this.posY + var23, iz, Blocks.GRASS);
                           if (this.world.rand.nextInt(30) == 1) {
                              if (this.world.getBlockState(new BlockPos(ix, (int)this.posY + var23 + 1, iz)).getBlock() == Blocks.AIR) {
                                 if (this.world.rand.nextInt(2) == 1) {
                                    this.world.setBlockState(new BlockPos(ix, (int)this.posY + var23 + 1, iz), (OreSpawn.MyFlowerPinkBlock).getDefaultState());
                                 } else {
                                    this.world.setBlockState(new BlockPos(ix, (int)this.posY + var23 + 1, iz), (OreSpawn.MyFlowerBlueBlock).getDefaultState());
                                 }
                              }
                           } else if (this.world.rand.nextInt(100) == 1
                              && this.world.getBlockState(new BlockPos(ix, (int)this.posY + var23 + 1, iz)).getBlock() == Blocks.AIR) {
                              OreSpawn.OreSpawnTrees.SmallTree(this.world, ix, (int)this.posY + var23 + 1, iz);
                           }
                        }
                     } else if (bidx == Blocks.BEDROCK) {
                        this.setDead();
                        return;
                     }
                  } else {
                     Block bidx = this.world.getBlockState(new BlockPos(ix, (int)this.posY + var23, iz)).getBlock();
                     if (bidx == Blocks.END_STONE) {
                        if (!this.world.isRemote) {
                           this.world.createExplosion(this, ix, this.posY + var23, iz, 5.0F, true);
                        }
                     } else {
                        this.mySetBlock(ix, (int)this.posY + var23, iz);
                     }
                  }
               }
            }
         }

         this.world.setBlockState(new BlockPos((int)this.posX - var37, (int)this.posY, (int)this.posZ - var38), (Blocks.AIR).getDefaultState());
      }
   }

   protected Item getDropItem() {
      return Item.getItemFromBlock(OreSpawn.MyIslandBlock);
   }

   public void FastSetBlock(int ix, int iy, int iz, Block id) {
      OreSpawn.setBlockFast(this.world, ix, iy, iz, id, 0, 3);
   }
}

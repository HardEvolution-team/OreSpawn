package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Saplings that turn into a tree on a random tick: the three crystal saplings and the experience sapling.
 */
public class BlockCrystalPlant extends BlockOreSpawnPlant {
   public enum Tree { TALL_CRYSTAL, SCRAGGLY_CRYSTAL, TALL_BLUE_CRYSTAL, EXPERIENCE }

   private final Tree tree;

   public BlockCrystalPlant(String name, Tree tree) {
      super(name);
      this.tree = tree;
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   @Override
   protected boolean canSustain(IBlockState state) {
      Block bid = state.getBlock();
      return bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND
         || (tree != Tree.EXPERIENCE && bid == OreSpawn.CrystalGrass);
   }

   @Override
   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
      boolean xp = tree == Tree.EXPERIENCE;
      if (rand.nextInt(xp ? 20 : 30) != 1) return;
      for (int j1 = 0; j1 < (xp ? 20 : 10); j1++) {
         world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
            pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      super.updateTick(world, pos, state, rand);
      if (world.isRemote || world.getBlockState(pos).getBlock() != this) return;
      if (world.rand.nextInt(tree == Tree.EXPERIENCE ? 10 : 5) != 1) return;
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
      int x = pos.getX(), y = pos.getY(), z = pos.getZ();
      switch (tree) {
         case TALL_CRYSTAL: TallCrystalTree(world, x, y, z); break;
         case SCRAGGLY_CRYSTAL: ScragglyCrystalTreeWithBranches(world, x, y, z); break;
         case TALL_BLUE_CRYSTAL: TallCrystalTreeBlue(world, x, y, z); break;
         case EXPERIENCE: OreSpawn.OreSpawnTrees.ExperienceTree(world, x, y - 1, z); break;
      }
   }

   public void TallCrystalTree(World world, int x, int y, int z) {
      int i = 10 + world.rand.nextInt(12);
      int j = i + world.rand.nextInt(18);

      for (int k = 0; k < i; k++) {
         Block bid = world.getBlockState(new BlockPos(x, y + k, z)).getBlock();
         if (k >= 1 && bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves) {
            return;
         }

         OreSpawn.setBlockFast(world, x, y + k, z, OreSpawn.MyCrystalTreeLog, 0, 2);
      }

      y += i - 1;

      for (int var14 = i; var14 < j; var14++) {
         Block bid = world.getBlockState(new BlockPos(x, ++y, z)).getBlock();
         if (bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves) {
            break;
         }

         OreSpawn.setBlockFast(world, x, y, z, OreSpawn.MyCrystalTreeLog, 0, 2);
         if (var14 % 4 == 0) {
            for (int m = -1; m < 2; m++) {
               for (int n = -1; n < 2; n++) {
                  if (world.rand.nextInt(2) == 1) {
                     bid = world.getBlockState(new BlockPos(x + m, y, z + n)).getBlock();
                     if (bid == Blocks.AIR) {
                        OreSpawn.setBlockFast(world, x + m, y, z + n, OreSpawn.MyCrystalLeaves, 0, 2);
                     }
                  }
               }
            }
         }
      }

      y++;

      for (int m = -1; m < 2; m++) {
         for (int nx = -1; nx < 2; nx++) {
            if (world.rand.nextInt(2) == 1) {
               Block bidx = world.getBlockState(new BlockPos(x + m, y, z + nx)).getBlock();
               if (bidx == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, x + m, y, z + nx, OreSpawn.MyCrystalTreeLog, 0, 2);
               }
            }
         }
      }

      for (int var16 = -3; var16 < 4; var16++) {
         for (int nxx = -3; nxx < 4; nxx++) {
            Block bidx = world.getBlockState(new BlockPos(x + var16, y, z + nxx)).getBlock();
            if (bidx == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x + var16, y, z + nxx, OreSpawn.MyCrystalLeaves, 0, 2);
            }
         }
      }

      y++;

      for (int var17 = -1; var17 < 2; var17++) {
         for (int nxxx = -1; nxxx < 2; nxxx++) {
            Block bidx = world.getBlockState(new BlockPos(x + var17, y, z + nxxx)).getBlock();
            if (bidx == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x + var17, y, z + nxxx, OreSpawn.MyCrystalLeaves, 0, 2);
            }
         }
      }
   }

   public void makeScragglyCrystalBranch(World world, int x, int y, int z, int len, int biasx, int biasz) {
      for (int k = 0; k < len; k++) {
         int ix = world.rand.nextInt(2) - world.rand.nextInt(2) + biasx;
         int iz = world.rand.nextInt(2) - world.rand.nextInt(2) + biasz;
         if (ix > 1) {
            ix = 1;
         }

         if (ix < -1) {
            ix = -1;
         }

         if (iz > 1) {
            iz = 1;
         }

         if (iz < -1) {
            iz = -1;
         }

         int iy = world.rand.nextInt(3) > 0 ? 1 : 0;
         x += ix;
         z += iz;
         y += iy;
         Block bid = world.getBlockState(new BlockPos(x, y, z)).getBlock();
         if (bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves2) {
            return;
         }

         OreSpawn.setBlockFast(world, x, y, z, OreSpawn.MyCrystalTreeLog, 0, 2);

         for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
               if (world.rand.nextInt(2) == 1) {
                  bid = world.getBlockState(new BlockPos(x + m, y, z + n)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, x + m, y, z + n, OreSpawn.MyCrystalLeaves2, 0, 2);
                  }
               }
            }
         }

         if (world.rand.nextInt(2) == 1) {
            bid = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x, y + 1, z, OreSpawn.MyCrystalLeaves2, 0, 2);
            }
         }
      }
   }

   public void ScragglyCrystalTreeWithBranches(World world, int x, int y, int z) {
      int i = 1 + world.rand.nextInt(2);
      int j = i + world.rand.nextInt(8);

      for (int k = 0; k < i; k++) {
         Block bid = world.getBlockState(new BlockPos(x, y + k, z)).getBlock();
         if (k >= 1 && bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves2) {
            return;
         }

         OreSpawn.setBlockFast(world, x, y + k, z, OreSpawn.MyCrystalTreeLog, 0, 2);
      }

      y += i - 1;

      for (int var15 = i; var15 < j; var15++) {
         int ix = world.rand.nextInt(2) - world.rand.nextInt(2);
         int iz = world.rand.nextInt(2) - world.rand.nextInt(2);
         int iy = world.rand.nextInt(4) > 0 ? 1 : 0;
         x += ix;
         z += iz;
         y += iy;
         Block bid = world.getBlockState(new BlockPos(x, y, z)).getBlock();
         if (bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves2) {
            break;
         }

         OreSpawn.setBlockFast(world, x, y, z, OreSpawn.MyCrystalTreeLog, 0, 2);
         if (world.rand.nextInt(4) == 1) {
            this.makeScragglyCrystalBranch(
               world,
               x,
               y,
               z,
               world.rand.nextInt(1 + j - var15),
               world.rand.nextInt(2) - world.rand.nextInt(2),
               world.rand.nextInt(2) - world.rand.nextInt(2)
            );
         }

         for (int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
               if (world.rand.nextInt(2) == 1) {
                  bid = world.getBlockState(new BlockPos(x + m, y, z + n)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, x + m, y, z + n, OreSpawn.MyCrystalLeaves2, 0, 2);
                  }
               }
            }
         }

         if (world.rand.nextInt(2) == 1) {
            bid = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();
            if (bid == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x, y + 1, z, OreSpawn.MyCrystalLeaves2, 0, 2);
            }
         }
      }
   }

   public void TallCrystalTreeBlue(World world, int x, int y, int z) {
      int i = 5 + world.rand.nextInt(6);
      int j = 2 + i + world.rand.nextInt(12);

      for (int k = 0; k < i; k++) {
         Block bid = world.getBlockState(new BlockPos(x, y + k, z)).getBlock();
         if (k >= 1 && bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves3) {
            return;
         }

         OreSpawn.setBlockFast(world, x, y + k, z, OreSpawn.MyCrystalTreeLog, 0, 2);
      }

      y += i - 1;

      for (int var14 = i; var14 < j; var14++) {
         Block bid = world.getBlockState(new BlockPos(x, ++y, z)).getBlock();
         if (bid != Blocks.AIR && bid != OreSpawn.MyCrystalTreeLog && bid != OreSpawn.MyCrystalLeaves3) {
            break;
         }

         OreSpawn.setBlockFast(world, x, y, z, OreSpawn.MyCrystalTreeLog, 0, 2);
         if (var14 % 3 == 0) {
            for (int m = -1; m < 2; m++) {
               for (int n = -1; n < 2; n++) {
                  if (world.rand.nextInt(2) == 1) {
                     bid = world.getBlockState(new BlockPos(x + m, y, z + n)).getBlock();
                     if (bid == Blocks.AIR) {
                        OreSpawn.setBlockFast(world, x + m, y, z + n, OreSpawn.MyCrystalLeaves3, 0, 2);
                     }
                  }
               }
            }
         }
      }

      y++;

      for (int m = -1; m < 2; m++) {
         for (int nx = -1; nx < 2; nx++) {
            if (world.rand.nextInt(2) == 1) {
               Block bidx = world.getBlockState(new BlockPos(x + m, y, z + nx)).getBlock();
               if (bidx == Blocks.AIR) {
                  OreSpawn.setBlockFast(world, x + m, y, z + nx, OreSpawn.MyCrystalTreeLog, 0, 2);
               }
            }
         }
      }

      for (int var16 = -3; var16 < 4; var16++) {
         for (int nxx = -3; nxx < 4; nxx++) {
            Block bidx = world.getBlockState(new BlockPos(x + var16, y, z + nxx)).getBlock();
            if (bidx == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x + var16, y, z + nxx, OreSpawn.MyCrystalLeaves3, 0, 2);
            }
         }
      }

      y++;

      for (int var17 = -1; var17 < 2; var17++) {
         for (int nxxx = -1; nxxx < 2; nxxx++) {
            Block bidx = world.getBlockState(new BlockPos(x + var17, y, z + nxxx)).getBlock();
            if (bidx == Blocks.AIR) {
               OreSpawn.setBlockFast(world, x + var17, y, z + nxxx, OreSpawn.MyCrystalLeaves3, 0, 2);
            }
         }
      }
   }
}

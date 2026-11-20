package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * OreSpawn flowers. Pink and blue flowers turn into their black / scary night forms after sunset
 * (and back at sunrise) on random ticks and neighbour updates.
 */
public class MyBlockFlower extends BlockOreSpawnPlant {
   private static final AxisAlignedBB FLOWER_AABB = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.6, 0.7);

   public MyBlockFlower(String name) {
      super(name);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   @Override
   protected boolean canSustain(IBlockState state) {
      Block b = state.getBlock();
      return b == Blocks.GRASS || b == Blocks.DIRT || b == Blocks.FARMLAND || b == OreSpawn.CrystalGrass;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return FLOWER_AABB;
   }

   @Override
   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
      super.neighborChanged(state, world, pos, block, fromPos);
      checkFlowerChange(world, pos);
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      super.updateTick(world, pos, state, rand);
      checkFlowerChange(world, pos);
   }

   private void checkFlowerChange(World world, BlockPos pos) {
      if (world.isRemote || world.getBlockState(pos).getBlock() != this) return;
      boolean night = world.getWorldTime() % 24000L > 12000L;
      Block swap = null;
      if (night) {
         if (this == OreSpawn.MyFlowerPinkBlock) swap = OreSpawn.MyFlowerBlackBlock;
         if (this == OreSpawn.MyFlowerBlueBlock) swap = OreSpawn.MyFlowerScaryBlock;
      } else {
         if (this == OreSpawn.MyFlowerBlackBlock) swap = OreSpawn.MyFlowerPinkBlock;
         if (this == OreSpawn.MyFlowerScaryBlock) swap = OreSpawn.MyFlowerBlueBlock;
      }
      if (swap != null) world.setBlockState(pos, swap.getDefaultState());
   }
}

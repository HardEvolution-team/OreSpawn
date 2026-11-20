package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.RegistryHandler;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Loose dirt left behind by Molenoids: slows walkers and crumbles away on random ticks. */
public class MoleDirtBlock extends Block {
   private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.875, 1.0);

   public MoleDirtBlock(String name) {
      super(Material.GROUND);
      setHardness(0.6F);
      setSoundType(SoundType.GROUND);
      setHarvestLevel("shovel", 0);
      setTickRandomly(true);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(RegistryHandler.tabForBlock(name));
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (!world.isRemote) {
         world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), 2);
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
      return COLLISION_AABB;
   }

   @Override
   public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
      entity.motionX *= 0.3;
      entity.motionZ *= 0.3;
   }
}

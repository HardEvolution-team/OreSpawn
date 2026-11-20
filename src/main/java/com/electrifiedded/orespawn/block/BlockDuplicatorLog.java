package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.RegistryHandler;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Duplicator Tree log: random ticks keep growing the duplicator tree (config DuplicatorTreeEnable). */
public class BlockDuplicatorLog extends Block {
   public BlockDuplicatorLog(String name) {
      super(Material.WOOD);
      setHardness(0.2F);
      setSoundType(SoundType.WOOD);
      setHarvestLevel("axe", 0);
      setTickRandomly(true);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(RegistryHandler.tabForBlock(name));
   }

   @Override
   public int tickRate(World world) {
      return 1;
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (!world.isRemote && OreSpawn.enableduplicatortree != 0) {
         OreSpawn.OreSpawnTrees.DuplicatorTree(world, pos.getX(), pos.getY(), pos.getZ());
      }
   }

   @Override
   public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
      return true;
   }

   @Override
   public boolean isWood(IBlockAccess world, BlockPos pos) {
      return true;
   }
}

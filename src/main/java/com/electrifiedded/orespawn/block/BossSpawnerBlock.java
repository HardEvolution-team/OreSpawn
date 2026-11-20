package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry;
import com.electrifiedded.orespawn.entity.TheKing;
import com.electrifiedded.orespawn.entity.TheQueen;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The King / The Queen spawner: 100 ticks after being placed (or when broken) it disappears and
 * summons its boss 8 blocks above itself in guard mode, unless the boss is disabled in the config.
 */
public class BossSpawnerBlock extends Block {
   private static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
   private final boolean queen;

   public BossSpawnerBlock(String name, boolean queen) {
      super(Material.PLANTS);
      this.queen = queen;
      setHardness(0.0F);
      setLightLevel(0.9F);
      setSoundType(SoundType.PLANT);
      setTickRandomly(true);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(CreativeTabs.DECORATIONS);
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos pos) {
      return world.getBlockState(pos.down()).getMaterial().isSolid();
   }

   @Override
   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      if (!world.isRemote) {
         world.scheduleUpdate(pos, this, 100);
      }
   }

   @Override
   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
      if (rand.nextInt(20) != 1) return;
      for (int i = 0; i < 20; i++) {
         world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
            pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (world.isRemote || world.getBlockState(pos).getBlock() != this) return;
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
      world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
      summon(world, pos);
   }

   @Override
   public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
      if (!world.isRemote) {
         summon(world, pos);
      }
   }

   private void summon(World world, BlockPos pos) {
      if ((queen ? OreSpawn.TheQueenEnable : OreSpawn.TheKingEnable) == 0) return;
      Entity boss = OreSpawnEntityRegistry.create(queen ? "The Queen" : "The King", world);
      if (boss == null) return;
      boss.setLocationAndAngles(pos.getX(), pos.getY() + 8, pos.getZ(), world.rand.nextFloat() * 360.0F, 0.0F);
      OreSpawnEntityRegistry.initialize(boss, world);
      if (boss instanceof TheKing) ((TheKing) boss).setGuardMode(1);
      if (boss instanceof TheQueen) ((TheQueen) boss).setGuardMode(1);
      world.spawnEntity(boss);
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
      return NULL_AABB;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }
}

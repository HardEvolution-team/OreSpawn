package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.RegistryHandler;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Pink crystal and Tiger's Eye clusters: cross-rendered, sparkling, XP below y=40. */
public class OreCrystalCrystal extends Block {
   private final boolean tigersEye;

   public OreCrystalCrystal(String name, float light, float hardness, float resistance, boolean tigersEye) {
      super(Material.ROCK);
      this.tigersEye = tigersEye;
      setHardness(hardness);
      setResistance(resistance);
      setLightLevel(light);
      setSoundType(SoundType.STONE);
      setHarvestLevel("pickaxe", 0);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(RegistryHandler.tabForBlock(name));
   }

   @Override
   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
      if (rand.nextInt(20) == 0) {
         OreCrystal.spawnSparkle(world, pos, rand, tigersEye ? EnumParticleTypes.FLAME : EnumParticleTypes.FIREWORKS_SPARK);
      }
   }

   @Override
   public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
      if (!tigersEye && !world.isRemote && world.rand.nextInt(10) == 1) {
         world.newExplosion((Entity) null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.0F, true,
            world.getGameRules().getBoolean("mobGriefing"));
      }
   }

   @Override
   public int quantityDropped(Random random) {
      return tigersEye ? random.nextInt(2) : 1;
   }

   @Override
   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      return OreCrystal.deepOreXp(pos);
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

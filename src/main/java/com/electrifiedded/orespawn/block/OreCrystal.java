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

/** Crystal Coal: cross-rendered glowing ore that may blow up when mined and gives XP below y=40. */
public class OreCrystal extends Block {
   public OreCrystal(String name, float light, float hardness, float resistance) {
      super(Material.ROCK);
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
      if (rand.nextInt(5) != 0) return;
      for (int i = 0; i < 5; i++) {
         EnumParticleTypes type;
         switch (rand.nextInt(3)) {
            case 0: type = EnumParticleTypes.FLAME; break;
            case 1: type = EnumParticleTypes.SMOKE_NORMAL; break;
            default: type = EnumParticleTypes.REDSTONE; break;
         }
         spawnSparkle(world, pos, rand, type);
      }
   }

   static void spawnSparkle(World world, BlockPos pos, Random rand, EnumParticleTypes type) {
      world.spawnParticle(type, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
         (rand.nextFloat() - rand.nextFloat()) / 4.0F,
         (rand.nextFloat() - rand.nextFloat()) / 4.0F,
         (rand.nextFloat() - rand.nextFloat()) / 4.0F);
   }

   @Override
   public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
      if (!world.isRemote && world.rand.nextInt(3) == 1) {
         world.newExplosion((Entity) null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.5F, true,
            world.getGameRules().getBoolean("mobGriefing"));
      }
   }

   @Override
   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      return deepOreXp(pos);
   }

   static int deepOreXp(BlockPos pos) {
      if (pos.getY() >= 40) return 0;
      return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(10);
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

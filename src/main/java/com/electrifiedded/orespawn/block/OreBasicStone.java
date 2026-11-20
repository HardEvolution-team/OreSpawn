package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.RegistryHandler;
import com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Crystal-dimension stone family (crystal stone, rat/fairy crystal, red ant / termite troll blocks).
 * Breaking a creature variant releases a swarm of the matching creature, as in the original.
 */
public class OreBasicStone extends Block {
   private final String creature;
   private final int minCount;
   private final int extraCount;

   public OreBasicStone(String name, float hardness, float resistance) {
      this(name, hardness, resistance, null, 0, 0);
   }

   public OreBasicStone(String name, float hardness, float resistance, String creature, int minCount, int extraCount) {
      this(Material.ROCK, "pickaxe", name, hardness, resistance, creature, minCount, extraCount);
   }

   protected OreBasicStone(Material material, String tool, String name, float hardness, float resistance,
                           String creature, int minCount, int extraCount) {
      super(material);
      this.creature = creature;
      this.minCount = minCount;
      this.extraCount = extraCount;
      setHardness(hardness);
      setResistance(resistance);
      setSoundType(SoundType.STONE);
      setHarvestLevel(tool, 0);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(RegistryHandler.tabForBlock(name));
   }

   @Override
   public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
      if (world.isRemote || creature == null) return;
      int num = minCount + world.rand.nextInt(extraCount);
      for (int i = 0; i < num; i++) {
         spawnCreature(world, creature,
            pos.getX() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
            pos.getY() + 0.01,
            pos.getZ() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
      }
   }

   public static Entity spawnCreature(World world, String name, double x, double y, double z) {
      Entity e = OreSpawnEntityRegistry.create(name, world);
      if (e != null) {
         e.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
         OreSpawnEntityRegistry.initialize(e, world);
         world.spawnEntity(e);
      }
      return e;
   }

   // The original was see-through everywhere except the Crystal dimension, where it was flagged opaque
   // for render speed. Here it is always cutout, but faces between two crystal stones are culled like
   // glass: same look, and the Crystal dimension stays cheap to render.
   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return true;
   }

   @Override
   public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.SOLID;
   }

   @Override
   public boolean causesSuffocation(IBlockState state) {
      return true;
   }

   @Override
   public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
      return true;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      if (world.getBlockState(pos.offset(side)).getBlock() instanceof OreBasicStone) return false;
      return super.shouldSideBeRendered(state, world, pos, side);
   }
}

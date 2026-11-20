package com.electrifiedded.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSixSlice extends Block {
    public static final PropertyInteger USED = PropertyInteger.create("used", 0, 5);

    protected BlockSixSlice(String name, Material material) {
        super(material);
        setRegistryName(name);
        setTranslationKey(name);
        setDefaultState(blockState.getBaseState().withProperty(USED, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return shape(state, 0.25D);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return shape(state, 0.1875D);
    }

    private AxisAlignedBB shape(IBlockState state, double height) {
        return new AxisAlignedBB((1 + state.getValue(USED) * 2) / 16.0D, 0.0D, 0.0625D,
            0.9375D, height, 0.9375D);
    }

    @Override public boolean isOpaqueCube(IBlockState state) { return false; }
    @Override public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolid(world, pos.down(), net.minecraft.util.EnumFacing.UP);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (!canPlaceBlockAt(world, pos)) world.setBlockToAir(pos);
    }

    protected void useSlice(World world, BlockPos pos, IBlockState state) {
        int used = state.getValue(USED);
        if (used < 5) world.setBlockState(pos, state.withProperty(USED, used + 1), 3);
        else world.setBlockToAir(pos);
    }

    @Override public int getMetaFromState(IBlockState state) { return state.getValue(USED); }
    @Override public IBlockState getStateFromMeta(int meta) { return getDefaultState().withProperty(USED, Math.min(5, meta)); }
    @Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, USED); }
    @Override public int quantityDropped(java.util.Random random) { return 0; }
}

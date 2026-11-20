package com.electrifiedded.orespawn.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOreSpawnPlant extends BlockBush {
    public BlockOreSpawnPlant(String name) {
        setRegistryName(name);
        setTranslationKey(name);
        setHardness(0.0F);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canSustain(world.getBlockState(pos.down()));
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return canSustain(world.getBlockState(pos.down()));
    }

    protected boolean canSustain(IBlockState state) {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT
            || state.getBlock() == Blocks.SOUL_SAND || state.getBlock() instanceof CrystalGrass;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}

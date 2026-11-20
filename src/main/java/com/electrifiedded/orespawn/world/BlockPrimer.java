package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

/** ChunkPrimer facade writing into a 1.7-style Block[] terrain array. */
public class BlockPrimer extends ChunkPrimer {
    private final Block[] data;

    public BlockPrimer(Block[] data) {
        this.data = data;
    }

    @Override
    public void setBlockState(int x, int y, int z, IBlockState state) {
        if (x >= 0 && x < 16 && z >= 0 && z < 16 && y >= 0 && y < 256) {
            data[(x * 16 + z) * 256 + y] = state == null || state.getBlock() == Blocks.AIR ? null : state.getBlock();
        }
    }

    @Override
    public IBlockState getBlockState(int x, int y, int z) {
        if (x >= 0 && x < 16 && z >= 0 && z < 16 && y >= 0 && y < 256) {
            Block b = data[(x * 16 + z) * 256 + y];
            return b == null ? Blocks.AIR.getDefaultState() : b.getDefaultState();
        }
        return Blocks.AIR.getDefaultState();
    }
}

package com.electrifiedded.orespawn.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPizza extends BlockSixSlice {
    public BlockPizza() {
        super("pizza", Material.CAKE);
        setHardness(0.5F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.canEat(false)) {
            player.getFoodStats().addStats(4, 0.2F);
            useSlice(world, pos, state);
        }
        return true;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        onBlockActivated(world, pos, world.getBlockState(pos), player, EnumHand.MAIN_HAND,
            EnumFacing.UP, 0, 0, 0);
    }
}

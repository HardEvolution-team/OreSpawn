package com.electrifiedded.orespawn.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDuctTape extends BlockSixSlice {
    public BlockDuctTape() {
        super("duct_tape", Material.CLOTH);
        setHardness(1.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        // Each slice repairs a sixth of the held item's durability.
        if (stack.getCount() == 1 && stack.getMaxDamage() > 0 && stack.getItemDamage() > 0) {
            stack.setItemDamage(Math.max(0, stack.getItemDamage() - Math.max(1, stack.getMaxDamage() / 6)));
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

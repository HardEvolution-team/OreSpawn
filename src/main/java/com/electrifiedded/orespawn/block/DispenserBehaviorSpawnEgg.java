package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.item.ItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

final class DispenserBehaviorSpawnEgg extends BehaviorDefaultDispenseItem {
    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        EnumFacing facing = source.getBlockState().getValue(BlockDispenser.FACING);
        double x = source.getX() + facing.getXOffset();
        double y = source.getBlockPos().getY() + facing.getYOffset() + 0.2D;
        double z = source.getZ() + facing.getZOffset();
        if (((ItemSpawnEgg) stack.getItem()).spawn(source.getWorld(), x, y, z) != null) {
            stack.shrink(1);
        }
        return stack;
    }
}

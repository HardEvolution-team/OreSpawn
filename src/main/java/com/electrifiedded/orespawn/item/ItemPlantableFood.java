package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ItemPlantableFood extends ItemFood {
    private final Supplier<Block> crop;
    private final Supplier<Block> soil;

    public ItemPlantableFood(String name, int food, float saturation, Supplier<Block> crop, Supplier<Block> soil) {
        super(food, saturation, false);
        this.crop = crop;
        this.soil = soil;
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(RegistryHandler.tabForItem(name));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        BlockPos target = pos.up();
        Block cropBlock = crop.get();
        if (facing != EnumFacing.UP || world.getBlockState(pos).getBlock() != soil.get()
                || !player.canPlayerEdit(target, facing, stack) || !world.isAirBlock(target)
                || !cropBlock.canPlaceBlockAt(world, target)) {
            return EnumActionResult.FAIL;
        }
        if (!world.isRemote) {
            IBlockState cropState = cropBlock.getDefaultState();
            if (!world.setBlockState(target, cropState, 3)) {
                return EnumActionResult.FAIL;
            }
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        }
        return EnumActionResult.SUCCESS;
    }
}

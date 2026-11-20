package com.electrifiedded.orespawn.block;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.creativetab.CreativeTabs;

public class BlockAppleLeaves extends BlockOreSpawnLeaves {
    public BlockAppleLeaves() {
        super("apple_leaves");
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubBlocks(tab, items);
    }
}

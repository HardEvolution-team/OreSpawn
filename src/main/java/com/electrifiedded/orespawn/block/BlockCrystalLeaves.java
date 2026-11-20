package com.electrifiedded.orespawn.block;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCrystalLeaves extends BlockBase {
    public BlockCrystalLeaves(String name) {
        super(name, Material.LEAVES);
        setHardness(0.2F);
        setResistance(1.0F);
        setLightOpacity(1);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubBlocks(tab, items);
    }
}
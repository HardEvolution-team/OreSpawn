package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;

public class ItemBase extends Item {
    public ItemBase(String name) {
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(64);
    }

    public ItemBase(String name, int maxStackSize) {
        this(name);
        setMaxStackSize(maxStackSize);
    }
}
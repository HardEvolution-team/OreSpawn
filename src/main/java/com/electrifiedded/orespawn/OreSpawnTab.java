package com.electrifiedded.orespawn;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class OreSpawnTab extends CreativeTabs {
    public OreSpawnTab() {
        super("orespawn");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(OreSpawn.MyRuby);
    }
}
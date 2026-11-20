package com.electrifiedded.orespawn.item.food;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood {
    public ItemFoodBase(String name, int healAmount, float saturation, boolean isWolfFood) {
        super(healAmount, saturation, isWolfFood);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.FOOD);
    }
}
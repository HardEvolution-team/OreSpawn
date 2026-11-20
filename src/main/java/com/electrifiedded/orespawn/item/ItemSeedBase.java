package com.electrifiedded.orespawn.item;

import net.minecraft.block.Block;

import java.util.function.Supplier;

public class ItemSeedBase extends ItemPlantableFood {
    public ItemSeedBase(String name, Supplier<Block> crop, Supplier<Block> soil) {
        super(name, 0, 0.0F, crop, soil);
    }
}

package com.electrifiedded.orespawn.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;
import java.util.function.Supplier;

public class BlockOreSpawnCrop extends BlockCrops {
    private final Supplier<Item> seed;
    private final Supplier<Item> crop;
    private final int baseDrop;
    private final int randomDrop;

    public BlockOreSpawnCrop(String name, Supplier<Item> seed, Supplier<Item> crop, int baseDrop, int randomDrop) {
        this.seed = seed;
        this.crop = crop;
        this.baseDrop = baseDrop;
        this.randomDrop = randomDrop;
        setRegistryName(name);
        setTranslationKey(name);
    }

    @Override
    protected Item getSeed() {
        return seed.get();
    }

    @Override
    protected Item getCrop() {
        return crop.get();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (!isMaxAge(state)) {
            return 1;
        }
        return baseDrop + (randomDrop > 0 ? random.nextInt(randomDrop) : 0);
    }
}

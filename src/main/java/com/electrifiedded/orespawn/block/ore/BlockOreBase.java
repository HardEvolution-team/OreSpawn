package com.electrifiedded.orespawn.block.ore;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Common base for the OreSpawn overworld ores. Original ores need any pickaxe (no tier requirement). */
public class BlockOreBase extends Block {
    public BlockOreBase(String name, float hardness, float resistance) {
        super(Material.ROCK);
        setHardness(hardness);
        setResistance(resistance);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 0);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    /** 5 + rand(5) + rand(extra) XP, optionally only below y=40 (uranium/titanium). */
    protected static int oreXp(BlockPos pos, int extra, boolean deepOnly) {
        if (deepOnly && pos.getY() >= 40) return 0;
        return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(extra);
    }

    /** Ruby / Amethyst: drop 1-2 gems regardless of fortune, 5-13 XP. */
    public static class Gem extends BlockOreBase {
        private final java.util.function.Supplier<Item> gem;

        public Gem(String name, java.util.function.Supplier<Item> gem) {
            super(name, 10.0F, 4.0F);
            this.gem = gem;
        }

        @Override
        public Item getItemDropped(IBlockState state, Random rand, int fortune) {
            return gem.get();
        }

        @Override
        public int quantityDroppedWithBonus(int fortune, Random random) {
            return 1 + random.nextInt(2);
        }

        @Override
        public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
            return oreXp(pos, 5, false);
        }
    }
}

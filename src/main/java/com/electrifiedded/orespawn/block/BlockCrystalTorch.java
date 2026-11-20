package com.electrifiedded.orespawn.block;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/** Crystal torches sparkle instead of smoking. */
public class BlockCrystalTorch extends BlockTorch {
    public BlockCrystalTorch() {
        setRegistryName("crystal_torch");
        setTranslationKey("crystal_torch");
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(0.0F);
        setLightLevel(0.99F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (world.rand.nextInt(4) != 1) {
            return;
        }
        double[] flame = BlockExtremeTorch.flamePosition(state, pos);
        world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, flame[0], flame[1], flame[2],
            (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F, world.rand.nextFloat() / 8.0F,
            (world.rand.nextFloat() - world.rand.nextFloat()) / 8.0F);
        world.spawnParticle(EnumParticleTypes.FLAME, flame[0], flame[1], flame[2],
            (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F, world.rand.nextFloat() / 10.0F,
            (world.rand.nextFloat() - world.rand.nextFloat()) / 60.0F);
    }
}

package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/** Placing an extreme torch on a block of eyes of ender summons a Cephadrome nearby and consumes the torch. */
public class BlockExtremeTorch extends BlockTorch {
    public BlockExtremeTorch() {
        setRegistryName("extreme_torch");
        setTranslationKey("extreme_torch");
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(0.0F);
        setLightLevel(1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double[] flame = flamePosition(state, pos);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, flame[0], flame[1], flame[2], 0.0, 0.0, 0.0);
        world.spawnParticle(EnumParticleTypes.FLAME, flame[0], flame[1], flame[2], 0.0, 0.0, 0.0);
        world.spawnParticle(EnumParticleTypes.REDSTONE, flame[0], flame[1], flame[2], 0.0, 0.0, 0.0);
    }

    /** Flame tip of a torch, matching vanilla's offsets for wall torches. */
    static double[] flamePosition(IBlockState state, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.7;
        double z = pos.getZ() + 0.5;
        if (facing.getAxis().isHorizontal()) {
            EnumFacing back = facing.getOpposite();
            x += 0.27 * back.getXOffset();
            y += 0.22;
            z += 0.27 * back.getZOffset();
        }
        return new double[]{x, y, z};
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (world.getBlockState(pos.down()).getBlock() != OreSpawn.MyEyeOfEnderBlock) {
            return;
        }
        BlockPos spot = findSummonSpot(world, pos);
        if (spot == null) {
            return;
        }
        if (world.isRemote) {
            Random rand = world.rand;
            for (int i = 0; i < 16; i++) {
                double px = pos.getX() + rand.nextFloat() - rand.nextFloat();
                double py = pos.getY() + rand.nextFloat();
                double pz = pos.getZ() + rand.nextFloat() - rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, 0.0, 0.0, 0.0);
                world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, px, py, pz, 0.0, 0.0, 0.0);
                world.spawnParticle(EnumParticleTypes.REDSTONE, px, py, pz, 0.0, 0.0, 0.0);
            }
            return;
        }
        Entity cephadrome = OreSpawnEntityRegistry.create("Cephadrome", world);
        if (cephadrome != null) {
            cephadrome.setLocationAndAngles(spot.getX() + 0.5, spot.getY() + 0.01, spot.getZ() + 0.5, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(cephadrome);
            OreSpawnEntityRegistry.initialize(cephadrome, world);
        }
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.2F + 0.9F);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }

    /** Up to 100 tries for a standing spot about four blocks away diagonally, within two blocks of height. */
    private static BlockPos findSummonSpot(World world, BlockPos torch) {
        Random rand = world.rand;
        for (int tries = 0; tries < 100; tries++) {
            int x = torch.getX() + (rand.nextBoolean() ? 4 : -4) + rand.nextInt(3) - rand.nextInt(3);
            int z = torch.getZ() + (rand.nextBoolean() ? 4 : -4) + rand.nextInt(3) - rand.nextInt(3);
            for (int y = torch.getY() - 2; y <= torch.getY() + 2; y++) {
                BlockPos spot = new BlockPos(x, y, z);
                if (world.getBlockState(spot.down()).getMaterial().isSolid()
                        && world.getBlockState(spot).getBlock() == Blocks.AIR
                        && world.getBlockState(spot.up()).getBlock() == Blocks.AIR) {
                    return spot;
                }
            }
        }
        return null;
    }
}

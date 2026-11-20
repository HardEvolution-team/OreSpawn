package com.electrifiedded.orespawn.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Lavafoam: slippery, smouldering block that shoves living things off its sides
 * (hard shoves hurt) and gives XP when mined in the Nether.
 */
public class BlockLavafoam extends Block {
    private static final float INSET = 0.0125F;
    private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(INSET, 0.0, INSET, 1.0 - INSET, 1.0, 1.0 - INSET);

    public BlockLavafoam() {
        super(Material.ROCK);
        setHardness(5.0F);
        setResistance(5.0F);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 0);
        setTickRandomly(true);
        this.slipperiness = 1.1F;
        setTranslationKey("lavafoam");
        setRegistryName("lavafoam");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public int tickRate(World world) {
        return 10;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (rand.nextInt(20) == 0) {
            BlockStorage.sparkle(world, pos, rand, 10);
        }
    }

    // Slightly inset sides so walking into the block counts as touching it.
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return COLLISION_AABB;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!(entity instanceof EntityLivingBase)) return;
        double pi = Math.PI;
        double pi2 = pi / 2.0;
        double pi4 = pi / 4.0;
        double d = Math.atan2(entity.posX - (pos.getX() + 0.5F), entity.posZ - (pos.getZ() + 0.5F));
        if (d < 0.0) d += pi * 2.0;
        if (d > pi2 - pi4 && d < pi2 + pi4) {
            entity.motionX = 0.45F;
            entity.motionZ *= 1.35F;
        } else if (d > pi - pi4 && d < pi + pi4) {
            entity.motionZ = -0.45F;
            entity.motionX *= 1.35F;
        } else if (d > pi + pi2 - pi4 && d < pi + pi2 + pi4) {
            entity.motionX = -0.45F;
            entity.motionZ *= 1.35F;
        } else {
            entity.motionZ = 0.45F;
            entity.motionX *= 1.35F;
        }
        double speed = Math.sqrt(entity.motionZ * entity.motionZ + entity.motionX * entity.motionX);
        if (speed > 1.0) {
            entity.attackEntityFrom(DamageSource.FALL, (float) speed);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        if (world instanceof World && ((World) world).provider.getDimension() == -1) {
            return 5 + RANDOM.nextInt(5) + RANDOM.nextInt(5);
        }
        return 0;
    }
}

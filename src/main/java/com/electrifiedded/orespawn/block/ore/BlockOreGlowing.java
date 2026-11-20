package com.electrifiedded.orespawn.block.ore;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Uranium / Titanium ore: drops itself, sparkles red (like redstone ore) for a few display ticks after
 * being touched, clicked or walked on, and gives XP only when mined below y=40.
 * The original kept the glow state in the shared Block instance; here it is remembered per position.
 */
public class BlockOreGlowing extends BlockOreBase {
    private final int glowTicks;
    private BlockPos glowPos;
    private int glowCount;

    public BlockOreGlowing(String name, float hardness, float resistance, int glowTicks) {
        super(name, hardness, resistance);
        this.glowTicks = glowTicks;
    }

    @Override
    public int tickRate(World world) {
        return 30;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        glow(world, pos);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        glow(world, pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        glow(world, pos);
        return false;
    }

    private void glow(World world, BlockPos pos) {
        if (!world.isRemote) return;
        glowPos = pos.toImmutable();
        glowCount = glowTicks;
        sparkle(world, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (glowCount > 0 && pos.equals(glowPos)) {
            sparkle(world, pos);
            glowCount--;
        }
    }

    // Same placement logic as vanilla redstone ore particles.
    private static void sparkle(World world, BlockPos pos) {
        Random rand = world.rand;
        double off = 0.0625;
        for (int i = 0; i < 6; i++) {
            double x = pos.getX() + rand.nextFloat();
            double y = pos.getY() + rand.nextFloat();
            double z = pos.getZ() + rand.nextFloat();
            if (i == 0 && !world.getBlockState(pos.up()).isOpaqueCube()) y = pos.getY() + 1 + off;
            if (i == 1 && !world.getBlockState(pos.down()).isOpaqueCube()) y = pos.getY() - off;
            if (i == 2 && !world.getBlockState(pos.south()).isOpaqueCube()) z = pos.getZ() + 1 + off;
            if (i == 3 && !world.getBlockState(pos.north()).isOpaqueCube()) z = pos.getZ() - off;
            if (i == 4 && !world.getBlockState(pos.east()).isOpaqueCube()) x = pos.getX() + 1 + off;
            if (i == 5 && !world.getBlockState(pos.west()).isOpaqueCube()) x = pos.getX() - off;
            if (x < pos.getX() || x > pos.getX() + 1 || y < 0.0 || y > pos.getY() + 1
                    || z < pos.getZ() || z > pos.getZ() + 1) {
                world.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        return oreXp(pos, 10, true);
    }
}

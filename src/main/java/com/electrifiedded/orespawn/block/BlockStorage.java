package com.electrifiedded.orespawn.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Storage blocks. Uranium/Titanium glow and smoulder (original BlockUranium/BlockTitanium);
 * Ruby/Amethyst/Mobzilla Scale glow faintly and let light through (original BlockRuby), and standing
 * on Mobzilla Scale gives Strength.
 */
public class BlockStorage extends Block {
    private boolean sparkles;
    private boolean givesStrength;

    public BlockStorage(String name, float hardness, float resistance, float light) {
        super(Material.ROCK);
        setHardness(hardness);
        setResistance(resistance);
        setLightLevel(light);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 0);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    /** Uranium / Titanium block. */
    public static BlockStorage metal(String name, float light) {
        BlockStorage b = new BlockStorage(name, 5.0F, 5.0F, light);
        b.sparkles = true;
        return b;
    }

    /** Ruby / Amethyst / Mobzilla Scale block. */
    public static BlockStorage gem(String name, boolean givesStrength) {
        BlockStorage b = new BlockStorage(name, 4.0F, 4.0F, 0.4F);
        // Original was non-opaque (light passes through) but rendered as a full cube: keep culling, drop opacity.
        b.lightOpacity = 0;
        b.givesStrength = givesStrength;
        return b;
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        strengthen(entity);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        strengthen(entity);
    }

    private void strengthen(Entity entity) {
        if (givesStrength && !entity.world.isRemote && entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 0));
        }
    }

    @Override
    public int tickRate(World world) {
        return sparkles ? 100 : super.tickRate(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (sparkles && rand.nextInt(20) == 0) {
            sparkle(world, pos, rand, 3);
        }
    }

    /**
     * Particles on the exposed faces (vanilla redstone-ore placement). {@code choices}: 3 = flame/smoke/redstone
     * each a third of the time; 10 = smoke or redstone one time in ten each (lavafoam).
     */
    static void sparkle(World world, BlockPos pos, Random rand, int choices) {
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
                int which = rand.nextInt(choices);
                EnumParticleTypes type = null;
                if (choices == 3) {
                    type = which == 0 ? EnumParticleTypes.FLAME : which == 1 ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.REDSTONE;
                } else if (which == 1) {
                    type = EnumParticleTypes.SMOKE_NORMAL;
                } else if (which == 2) {
                    type = EnumParticleTypes.REDSTONE;
                }
                if (type != null) world.spawnParticle(type, x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }
}

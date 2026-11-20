package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * All OreSpawn leaves (1.7.10 BlockAppleLeaves, BlockScaryLeaves, BlockCrystalLeaves and
 * BlockExperienceLeaves). Leaves never drop themselves except by shears; instead they drop
 * fruit when broken or decaying, and occasionally drop fruit below them while attached to wood.
 */
public class BlockOreSpawnLeaves extends Block implements IShearable {
    private enum Kind { APPLE, SCARY, CHERRY, PEACH, CRYSTAL, EXPERIENCE }

    private final String type;
    private final Kind kind;

    public BlockOreSpawnLeaves(String name) {
        super(Material.LEAVES);
        type = name;
        kind = kindOf(name);
        setRegistryName(name);
        setTranslationKey(name);
        setHardness(name.equals("cherry_leaves") || name.equals("peach_leaves") ? 0.15F
            : name.equals("crystal_leaves2") || name.equals("crystal_leaves3") ? 0.25F : 0.2F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
    }

    private static Kind kindOf(String name) {
        switch (name) {
            case "apple_leaves": return Kind.APPLE;
            case "scary_leaves": return Kind.SCARY;
            case "cherry_leaves": return Kind.CHERRY;
            case "peach_leaves": return Kind.PEACH;
            case "experience_leaves": return Kind.EXPERIENCE;
            default:
                if (name.startsWith("crystal_leaves")) return Kind.CRYSTAL;
                throw new IllegalArgumentException("Unknown OreSpawn leaves: " + name);
        }
    }

    @Override public boolean isOpaqueCube(IBlockState state) { return false; }
    @Override public boolean isFullCube(IBlockState state) { return false; }
    @Override public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) { return true; }
    @Override public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.CUTOUT_MIPPED; }

    /**
     * Fast graphics skips faces between leaves of the same kind, like the legacy FastGraphicsLeaves switch.
     * Vanilla leaves report themselves opaque exactly when the client uses fast graphics.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.util.EnumFacing side) {
        if (Blocks.LEAVES.getDefaultState().isOpaqueCube() && world.getBlockState(pos.offset(side)).getBlock() == this) {
            return false;
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }

    /** Breaking by hand (or decay) rolls the fruit table, like the original dropBlockAsItemWithChance. */
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        rollFruit(drops, world instanceof World ? ((World) world).rand : RANDOM);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (world.isRemote) return;
        boolean islands = world.provider.getDimension() == OreSpawn.DimensionID4;
        boolean appleLike = kind == Kind.APPLE || kind == Kind.CRYSTAL;
        int radius = appleLike && islands ? 1 : 2;
        if (!hasWood(world, pos, radius)) {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), 2);
            return;
        }

        long time = world.getWorldTime() % 24000L;
        if (kind == Kind.EXPERIENCE) {
            dropExperience(world, pos, time);
            return;
        }
        if (kind == Kind.SCARY && time < 12000L) {
            OreSpawn.setBlockFast(world, pos.getX(), pos.getY(), pos.getZ(), OreSpawn.MyAppleLeaves, 0, 3);
        }
        int chance = appleLike && islands ? 100 : 20;
        if (world.isAirBlock(pos.down()) && world.rand.nextInt(chance) == 3) {
            NonNullList<ItemStack> fruit = NonNullList.create();
            rollFruit(fruit, world.rand);
            for (ItemStack stack : fruit) spawnAsEntity(world, pos.down(), stack);
        }
        if (kind == Kind.APPLE && islands && time > 12000L) {
            OreSpawn.setBlockFast(world, pos.getX(), pos.getY(), pos.getZ(), OreSpawn.MyScaryLeaves, 0, 3);
        }
    }

    private static boolean hasWood(World world, BlockPos pos, int radius) {
        if (!world.isAreaLoaded(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            return true;
        }
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();
        for (int x = -radius; x <= radius; x++) for (int y = -radius; y <= 0; y++) for (int z = -radius; z <= radius; z++) {
            if (Math.abs(x) + Math.abs(y) + Math.abs(z) > 3) continue;
            check.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
            IBlockState state = world.getBlockState(check);
            if (state.getBlock().canSustainLeaves(state, world, check)) return true;
        }
        return false;
    }

    private void rollFruit(NonNullList<ItemStack> drops, Random rand) {
        switch (kind) {
            case APPLE:
                if (rand.nextInt(25) == 1) drops.add(new ItemStack(Items.APPLE));
                if (rand.nextInt(500) == 2) drops.add(new ItemStack(Items.GOLDEN_APPLE, 1, 0));
                if (rand.nextInt(1000) == 3) drops.add(new ItemStack(Items.GOLDEN_APPLE, 1, 1));
                if (rand.nextInt(10000) == 4) add(drops, OreSpawn.MagicApple);
                break;
            case CHERRY:
                if (rand.nextInt(25) == 1) add(drops, OreSpawn.MyCherry);
                break;
            case PEACH:
                if (rand.nextInt(25) == 1) add(drops, OreSpawn.MyPeach);
                break;
            case CRYSTAL:
                if (rand.nextInt(100) == 1) add(drops, OreSpawn.MyCrystalApple);
                if (rand.nextInt(50) == 1) add(drops, Item.getItemFromBlock(type.endsWith("2") ? OreSpawn.MyCrystalPlant2
                    : type.endsWith("3") ? OreSpawn.MyCrystalPlant3 : OreSpawn.MyCrystalPlant));
                break;
            default:
                break;
        }
    }

    private static void add(NonNullList<ItemStack> drops, Item item) {
        if (item != null) drops.add(new ItemStack(item));
    }

    /** Between dusk and dawn experience leaves drop bottles o' enchanting and throw splashing ones. */
    private static void dropExperience(World world, BlockPos pos, long time) {
        if (time < 14000L || time > 22000L) return;
        if (world.rand.nextInt(65) == 1 && world.isAirBlock(pos.up())) {
            spawnAsEntity(world, pos.up(2), new ItemStack(Items.EXPERIENCE_BOTTLE));
        }
        if (world.rand.nextInt(75) == 1 && world.isAirBlock(pos.down())) {
            EntityExpBottle bottle = new EntityExpBottle(world, pos.getX() + 0.5, pos.getY() - 1, pos.getZ() + 0.5);
            bottle.shoot((world.rand.nextFloat() - world.rand.nextFloat()) / 2.0F, -0.1F,
                (world.rand.nextFloat() - world.rand.nextFloat()) / 2.0F, 0.4F, 5.0F);
            world.spawnEntity(bottle);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (kind != Kind.EXPERIENCE) return;
        long time = world.getWorldTime() % 24000L;
        if (time < 13000L || time > 23000L) return;
        int rate = time < 14000L ? (14000 - (int) time) / 2 : time > 22000L ? (int) (time - 22000L) / 2 : 0;
        if (world.rand.nextInt(200 + rate) == 1 && world.isAirBlock(pos.up())) {
            for (int i = 0; i < 10; i++) {
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5,
                    world.rand.nextGaussian(), Math.abs(world.rand.nextGaussian()), world.rand.nextGaussian());
            }
        }
        if (world.rand.nextInt(40 + rate) == 1 && world.isAirBlock(pos.down())) {
            for (int i = 0; i < 4; i++) {
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() - 1.25, pos.getZ() + 0.5,
                    world.rand.nextFloat() - world.rand.nextFloat(), -Math.abs(world.rand.nextFloat()),
                    world.rand.nextFloat() - world.rand.nextFloat());
            }
        }
    }

    @Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { return true; }
    @Override public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this));
    }
}

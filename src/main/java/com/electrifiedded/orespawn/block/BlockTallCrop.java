package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTallCrop extends BlockOreSpawnPlant {
    private final int stage;

    public BlockTallCrop(String name, int stage) {
        super(name);
        this.stage = stage;
        setTickRandomly(stage < 4);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        net.minecraft.block.Block below = world.getBlockState(pos.down()).getBlock();
        return isFamily(below) || below == Blocks.FARMLAND || below == Blocks.GRASS || below == Blocks.DIRT
            || (family().equals("quinoa") && below == OreSpawn.CrystalGrass);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canBlockStay(world, pos, getDefaultState());
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!canBlockStay(world, pos, state)) {
            world.destroyBlock(pos, true);
            return;
        }
        if (!world.isRemote && stage < 4 && random.nextInt(5) == 0) {
            net.minecraft.block.Block next = stageBlock(stage + 1);
            if (next != null) world.setBlockState(pos, next.getDefaultState(), 2);
            if (stage >= 2 && world.isAirBlock(pos.up()) && columnHeight(world, pos) < maxHeight()) {
                world.setBlockState(pos.up(), stageBlock(1).getDefaultState(), 2);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return cropItem();
    }

    @Override
    public int quantityDropped(Random random) {
        if (stage != 4) return stage == 1 ? 1 : 0;
        String family = family();
        if (family.equals("corn")) return 1 + random.nextInt(2);
        if (family.equals("tomato")) return 2 + random.nextInt(4);
        if (family.equals("quinoa")) return 3 + random.nextInt(3);
        return 2 + random.nextInt(3);
    }

    private int columnHeight(World world, BlockPos pos) {
        int height = 1;
        while (isFamily(world.getBlockState(pos.down(height)).getBlock())) height++;
        while (isFamily(world.getBlockState(pos.up(height)).getBlock())) height++;
        return height;
    }

    // Original re-rolled the target height on every growth tick: corn 4-7, tomato 3-5, quinoa 2-4.
    private int maxHeight() {
        switch (family()) {
            case "lettuce": return 1;
            case "corn": return 4 + OreSpawn.OreSpawnRand.nextInt(4);
            case "tomato": return 3 + OreSpawn.OreSpawnRand.nextInt(3);
            case "quinoa": return 2 + OreSpawn.OreSpawnRand.nextInt(3);
            default: return 4;
        }
    }

    private boolean isFamily(net.minecraft.block.Block block) {
        return block instanceof BlockTallCrop && ((BlockTallCrop) block).family().equals(family());
    }

    private String family() {
        return getRegistryName().getPath().split("_plant")[0];
    }

    private net.minecraft.block.Block stageBlock(int value) {
        switch (family()) {
            case "corn": return value == 1 ? OreSpawn.MyCornPlant1 : value == 2 ? OreSpawn.MyCornPlant2 : value == 3 ? OreSpawn.MyCornPlant3 : OreSpawn.MyCornPlant4;
            case "tomato": return value == 1 ? OreSpawn.MyTomatoPlant1 : value == 2 ? OreSpawn.MyTomatoPlant2 : value == 3 ? OreSpawn.MyTomatoPlant3 : OreSpawn.MyTomatoPlant4;
            case "quinoa": return value == 1 ? OreSpawn.MyQuinoaPlant1 : value == 2 ? OreSpawn.MyQuinoaPlant2 : value == 3 ? OreSpawn.MyQuinoaPlant3 : OreSpawn.MyQuinoaPlant4;
            case "lettuce": return value == 1 ? OreSpawn.MyLettucePlant1 : value == 2 ? OreSpawn.MyLettucePlant2 : value == 3 ? OreSpawn.MyLettucePlant3 : OreSpawn.MyLettucePlant4;
            default: return null;
        }
    }

    private Item cropItem() {
        switch (family()) {
            case "corn": return OreSpawn.MyCornCob;
            case "tomato": return OreSpawn.MyTomato;
            case "quinoa": return OreSpawn.MyQuinoa;
            case "lettuce": return OreSpawn.MyLettuce;
            default: return Item.getItemFromBlock(this);
        }
    }
}

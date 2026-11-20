package com.electrifiedded.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class BlockSkyTreeLog extends Block {
    public BlockSkyTreeLog() {
        super(Material.WOOD);
        setRegistryName("sky_tree_log");
        setTranslationKey("sky_tree_log");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setHardness(0.2F);
        setResistance(1.0F);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);
    }

    @Override
    public net.minecraft.item.Item getItemDropped(IBlockState state, java.util.Random rand, int fortune) {
        return net.minecraft.item.Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(java.util.Random random) {
        return 1;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, net.minecraft.util.NonNullList<net.minecraft.item.ItemStack> items) {
        items.add(new net.minecraft.item.ItemStack(this, 1, 0));
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public void onPlayerDestroy(World world, BlockPos pos, IBlockState state) {
        if (world.isRemote) return;
        ArrayDeque<Node> pending = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        pending.add(new Node(pos, 0));
        visited.add(pos);

        while (!pending.isEmpty()) {
            Node node = pending.removeFirst();
            if (node.depth > 1000) continue;
            for (int x = -1; x <= 1; x++) for (int y = -1; y <= 1; y++) for (int z = -1; z <= 1; z++) {
                if (x == 0 && y == 0 && z == 0) continue;
                BlockPos found = node.pos.add(x, y, z);
                if (!visited.add(found) || world.getBlockState(found).getBlock() != this) continue;
                world.setBlockToAir(found);
                spawnAsEntity(world, found, new net.minecraft.item.ItemStack(this));
                pending.addLast(new Node(found, node.depth + 1));
            }
        }
    }

    private static final class Node {
        private final BlockPos pos;
        private final int depth;

        private Node(BlockPos pos, int depth) {
            this.pos = pos;
            this.depth = depth;
        }
    }
}

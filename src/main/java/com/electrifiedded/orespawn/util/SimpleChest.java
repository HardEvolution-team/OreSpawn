package com.electrifiedded.orespawn.util;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import java.util.Random;

/** 1.7-style weighted chest content list helper (replacement of vanilla WeightedRandomChestContent). */
public class SimpleChest {
    public final ItemStack stack;
    public final int min;
    public final int max;
    public final int weight;

    public SimpleChest(ItemStack stack, int min, int max, int weight) {
        this.stack = stack;
        this.min = min;
        this.max = max;
        this.weight = weight;
    }

    public SimpleChest(Item item, int min, int max, int weight) {
        this(new ItemStack(item), min, max, weight);
    }

    public SimpleChest(net.minecraft.block.Block block, int min, int max, int weight) {
        this(new ItemStack(block), min, max, weight);
    }

    public SimpleChest(Item item, int metadata, int min, int max, int weight) {
        this(new ItemStack(item, 1, metadata), min, max, weight);
    }
    public static void fill(Random rand, SimpleChest[] contents, TileEntityChest chest, int rolls) {
        for (int i = 0; i < rolls; i++) {
            int total = 0;
            for (SimpleChest c : contents) total += c.weight;
            int roll = rand.nextInt(total);
            for (SimpleChest c : contents) {
                roll -= c.weight;
                if (roll < 0) {
                    if (c.stack.getItem() != Items.AIR) {
                        ItemStack s = c.stack.copy();
                        int count = c.min + (c.max > c.min ? rand.nextInt(c.max - c.min + 1) : 0);
                        s.setCount(Math.max(1, count));
                        chest.setInventorySlotContents(rand.nextInt(chest.getSizeInventory()), s);
                    }
                    break;
                }
            }
        }
    }
}

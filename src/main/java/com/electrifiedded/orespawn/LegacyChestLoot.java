package com.electrifiedded.orespawn;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Vanilla chest additions from the legacy ChestGenHooks block (dungeonChest, pyramidJungleChest,
 * pyramidDesertyChest). Dungeon weights are doubled because 1.12 doubled the weights of that pool.
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class LegacyChestLoot {
    private LegacyChestLoot() {}

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        if (name.equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
            add(event, OreSpawn.MyRuby, 6);
            add(event, OreSpawn.MyAmethyst, 6);
            add(event, OreSpawn.MyThunderStaff, 4);
        } else if (name.equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
            add(event, OreSpawn.MyRuby, 3);
            add(event, OreSpawn.MyAmethyst, 3);
            add(event, OreSpawn.AntRobotKit, 3);
        } else if (name.equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
            add(event, OreSpawn.MyRuby, 2);
            add(event, OreSpawn.MyAmethyst, 2);
            add(event, OreSpawn.SpiderRobotKit, 2);
        }
    }

    private static void add(LootTableLoadEvent event, Item item, int weight) {
        LootPool pool = event.getTable().getPool("main");
        if (pool == null || item == null || item.getRegistryName() == null) return;
        pool.addEntry(new LootEntryItem(item, weight, 0, new LootFunction[0], new LootCondition[0],
            Reference.MOD_ID + ":" + item.getRegistryName().getPath()));
    }
}

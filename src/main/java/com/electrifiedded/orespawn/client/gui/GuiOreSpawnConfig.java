package com.electrifiedded.orespawn.client.gui;

import com.electrifiedded.orespawn.OreSpawnMod;
import com.electrifiedded.orespawn.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiOreSpawnConfig extends GuiConfig {
    public GuiOreSpawnConfig(GuiScreen parentScreen) {
        super(parentScreen, configElements(), Reference.MOD_ID, false, false, "OreSpawn Configuration");
    }

    private static List<IConfigElement> configElements() {
        List<IConfigElement> elements = new ArrayList<>();
        if (OreSpawnMod.config == null) return elements;
        List<String> categories = new ArrayList<>();
        String[] visibleCategories = {
            "general.ids", "general.tweaks", "balance.armor", "balance.weapons",
            "mobs.enabled", "mobs.frequency", "mobs.stats",
            "worldgen.ores", "worldgen.structures", "worldgen.trees"
        };
        for (String category : visibleCategories) {
            if (OreSpawnMod.config.hasCategory(category)) categories.add(category);
        }
        java.util.Collections.sort(categories);
        for (String category : categories) {
            elements.add(new ConfigElement(OreSpawnMod.config.getCategory(category)));
        }
        return elements;
    }
}

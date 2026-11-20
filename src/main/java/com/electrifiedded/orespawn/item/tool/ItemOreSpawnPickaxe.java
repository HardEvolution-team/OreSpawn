package com.electrifiedded.orespawn.item.tool;
import com.electrifiedded.orespawn.entity.*;
import net.minecraft.creativetab.CreativeTabs;

import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item;

public class ItemOreSpawnPickaxe extends ItemPickaxe {
    private final ToolMaterial material;
    private final String name;

    public ItemOreSpawnPickaxe(ToolMaterial material, String name) {
        super(material);
        this.material = material;
        this.name = name;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
    }
}

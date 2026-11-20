package com.electrifiedded.orespawn.item.tool;
import com.electrifiedded.orespawn.entity.*;
import net.minecraft.creativetab.CreativeTabs;

import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item;

public class ItemOreSpawnAxe extends ItemAxe {
    private final ToolMaterial material;
    private final String name;

    public ItemOreSpawnAxe(ToolMaterial material, String name) {
        super(material, material.getAttackDamage() + 5.0F, -3.0F);
        this.material = material;
        this.name = name;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxStackSize(1);
    }
}

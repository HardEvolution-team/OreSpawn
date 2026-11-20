package com.electrifiedded.orespawn.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemEnchantedSword extends ItemSword {
    private final Enchantment[] enchantments;
    private final int[] levels;

    public ItemEnchantedSword(ToolMaterial material, String name, Enchantment[] enchantments, int[] levels) {
        super(material);
        this.enchantments = enchantments;
        this.levels = levels;
        setRegistryName(name);
        setTranslationKey(name);
        setCreativeTab(CreativeTabs.COMBAT);
    }

    private void apply(ItemStack stack) {
        for (int i = 0; i < enchantments.length; i++) {
            if (EnchantmentHelper.getEnchantmentLevel(enchantments[i], stack) < levels[i]) stack.addEnchantment(enchantments[i], levels[i]);
        }
    }

    @Override public void onCreated(ItemStack stack, World world, EntityPlayer player) { apply(stack); }
    @Override public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) { apply(stack); }
}

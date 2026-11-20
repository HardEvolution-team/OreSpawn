package com.electrifiedded.orespawn.item.armor;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class OreSpawnArmorMaterials {
    public static final ItemArmor.ArmorMaterial ULTIMATE = EnumHelper.addArmorMaterial("ULTIMATE", "orespawn:ultimate", 200, new int[]{6, 12, 10, 6}, 100, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F);
    public static final ItemArmor.ArmorMaterial MOBZILLA = EnumHelper.addArmorMaterial("MOBZILLA", "orespawn:mobzilla", 1000, new int[]{7, 13, 11, 7}, 150, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 10.0F);
    public static final ItemArmor.ArmorMaterial LAVAEEL = EnumHelper.addArmorMaterial("LAVAEEL", "orespawn:lavaeel", 40, new int[]{2, 7, 5, 2}, 35, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    public static final ItemArmor.ArmorMaterial MOTHSCALE = EnumHelper.addArmorMaterial("MOTHSCALE", "orespawn:mothscale", 50, new int[]{2, 7, 5, 2}, 50, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F);
    public static final ItemArmor.ArmorMaterial EMERALD = EnumHelper.addArmorMaterial("EMERALD", "orespawn:emerald", 60, new int[]{3, 8, 6, 3}, 40, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    public static final ItemArmor.ArmorMaterial EXPERIENCE = EnumHelper.addArmorMaterial("EXPERIENCE", "orespawn:experience", 70, new int[]{5, 9, 7, 4}, 50, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F);
    public static final ItemArmor.ArmorMaterial RUBY = EnumHelper.addArmorMaterial("RUBY", "orespawn:ruby", 90, new int[]{4, 9, 8, 4}, 40, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F);
    public static final ItemArmor.ArmorMaterial AMETHYST = EnumHelper.addArmorMaterial("AMETHYST", "orespawn:amethyst", 100, new int[]{4, 8, 7, 3}, 40, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    public static final ItemArmor.ArmorMaterial PINK = EnumHelper.addArmorMaterial("PINK", "orespawn:pink", 50, new int[]{3, 7, 5, 2}, 40, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    public static final ItemArmor.ArmorMaterial TIGERSEYE = EnumHelper.addArmorMaterial("TIGERSEYE", "orespawn:tigerseye", 80, new int[]{4, 8, 7, 4}, 55, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F);
    public static final ItemArmor.ArmorMaterial PEACOCK = EnumHelper.addArmorMaterial("PEACOCK", "orespawn:peacock", 40, new int[]{2, 5, 4, 2}, 30, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    public static final ItemArmor.ArmorMaterial ROYAL = EnumHelper.addArmorMaterial("ROYAL", "orespawn:royal", 2000, new int[]{8, 14, 12, 8}, 200, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 10.0F);
    public static final ItemArmor.ArmorMaterial LAPIS = EnumHelper.addArmorMaterial("LAPIS", "orespawn:lapis", 60, new int[]{2, 7, 5, 2}, 60, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    public static final ItemArmor.ArmorMaterial QUEEN = EnumHelper.addArmorMaterial("QUEEN", "orespawn:queen", 1500, new int[]{9, 16, 14, 9}, 150, net.minecraft.init.SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 8.0F);
}

package com.electrifiedded.orespawn.item.armor;
import com.electrifiedded.orespawn.entity.*;
import net.minecraft.creativetab.CreativeTabs;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.util.ArmorStats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemOreSpawnArmor extends ItemArmor {
    private final int armorMaterial;

    /**
     * @param armorType 0=helmet, 1=chest, 2=legs, 3=boots
     * @param armorMaterial material id (0..13, see ITEM_MATERIAL_TO_NAME)
     */
    public ItemOreSpawnArmor(ArmorMaterial material, int armorMaterial, EntityEquipmentSlot slot, String name) {
        super(material, (armorMaterial == 10 || armorMaterial == 11) ? 2 : armorMaterial == 13 ? 3 : 1, slot);
        this.armorMaterial = armorMaterial;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
        setMaxStackSize(1);
    }

    public int getArmorMaterialId() { return this.armorMaterial; }

    public int getArmorType() {
        switch (getEquipmentSlot()) {
            case HEAD: return 0;
            case CHEST: return 1;
            case LEGS: return 2;
            case FEET: return 3;
            default: return 0;
        }
    }

    private ArmorStats stats() {
        switch (this.armorMaterial) {
            case 0: return OreSpawn.Ultimate_armorstats;
            case 1: return OreSpawn.LavaEel_armorstats;
            case 2: return OreSpawn.MothScale_armorstats;
            case 3: return OreSpawn.Emerald_armorstats;
            case 4: return OreSpawn.Experience_armorstats;
            case 5: return OreSpawn.Ruby_armorstats;
            case 6: return OreSpawn.Amethyst_armorstats;
            case 7: return OreSpawn.Pink_armorstats;
            case 8: return OreSpawn.TigersEye_armorstats;
            case 9: return OreSpawn.Peacock_armorstats;
            case 10: return OreSpawn.Mobzilla_armorstats;
            case 11: return OreSpawn.Royal_armorstats;
            case 12: return OreSpawn.Lapis_armorstats;
            case 13: return OreSpawn.Queen_armorstats;
            default: return null;
        }
    }

    private void applyEnchantments(ItemStack stack, boolean force) {
        ArmorStats a = stats();
        if (a == null) return;
        int type = getArmorType();
        int sum = a.e_aquaaffinity + a.e_blastprotection + a.e_featherfalling + a.e_fireprotection + a.e_projectileprotection + a.e_protection + a.e_respiration + a.e_unbreaking;
        if (!force && sum <= 0) return;
        if (!force) {
            int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.AQUA_AFFINITY, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
            if (lvl <= 0) lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FEATHER_FALLING, stack);
            if (lvl > 0) return;
        }
        if (a.e_protection != 0) stack.addEnchantment(Enchantments.PROTECTION, a.e_protection);
        if (a.e_fireprotection != 0) stack.addEnchantment(Enchantments.FIRE_PROTECTION, a.e_fireprotection);
        if (a.e_blastprotection != 0) stack.addEnchantment(Enchantments.BLAST_PROTECTION, a.e_blastprotection);
        if (a.e_projectileprotection != 0) stack.addEnchantment(Enchantments.PROJECTILE_PROTECTION, a.e_projectileprotection);
        if (a.e_unbreaking != 0) stack.addEnchantment(Enchantments.UNBREAKING, a.e_unbreaking);
        if (type == 3 && a.e_featherfalling != 0) stack.addEnchantment(Enchantments.FEATHER_FALLING, a.e_featherfalling);
        if (type == 0) {
            if (a.e_respiration != 0) stack.addEnchantment(Enchantments.RESPIRATION, a.e_respiration);
            if (a.e_aquaaffinity != 0) stack.addEnchantment(Enchantments.AQUA_AFFINITY, a.e_aquaaffinity);
        }
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer playerEntity) {
        applyEnchantments(itemStack, true);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        applyEnchantments(stack, false);
    }

    private static final String[] TEX = {"ultimate", "lavaeel", "mothscale", "emerald", "experience", "ruby", "amethyst", "pink", "tigerseye", "peacock", "mobzilla", "royal", "lapis", "queen"};

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        int suffix = (getArmorType() == 2) ? 2 : 1;
        return "orespawndedsport:" + TEX[this.armorMaterial] + "_" + suffix + ".png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player == null) return;
        ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        int type = this.armorMaterial;
        if ((type == 11 || type == 9)) {
            if (!boots.isEmpty() && ((boots.getItem() == OreSpawn.RoyalBoots && OreSpawn.RoyalGlideEnable != 0) || boots.getItem() == OreSpawn.PeacockFeatherBoots)) {
                if (player.motionY < -0.1) player.motionY = -0.1;
                player.fallDistance = 0.0F;
            }
        }
        if (type == 13) {
            if (!boots.isEmpty() && boots.getItem() == OreSpawn.QueenBoots && OreSpawn.RoyalGlideEnable != 0) {
                if (player.motionY < -0.25) player.motionY = -0.25;
                player.fallDistance = 0.0F;
            }
        }
    }
}

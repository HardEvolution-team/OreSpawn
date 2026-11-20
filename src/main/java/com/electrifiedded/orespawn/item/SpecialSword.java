package com.electrifiedded.orespawn.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;

/** Common 1.12 item setup for the legacy special melee weapons. */
public abstract class SpecialSword extends ItemSword {
    protected SpecialSword(ToolMaterial material, String name) {
        super(material);
        setRegistryName(name);
        setTranslationKey(name);
        setMaxStackSize(1);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
        Multimap<String, AttributeModifier> modifiers = super.getItemAttributeModifiers(slot);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            modifiers.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4D, 0));
        }
        return modifiers;
    }
}

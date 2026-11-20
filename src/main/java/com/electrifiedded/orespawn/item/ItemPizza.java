package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.RegistryHandler;
import net.minecraft.item.ItemBlock;

public class ItemPizza extends ItemBlock {
    public ItemPizza() {
        super(RegistryHandler.PIZZA);
        setRegistryName("pizza_item");
        setTranslationKey("pizza_item");
        setMaxStackSize(1);
    }
}

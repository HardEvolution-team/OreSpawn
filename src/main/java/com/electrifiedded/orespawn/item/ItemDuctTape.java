package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.RegistryHandler;
import net.minecraft.item.ItemBlock;

public class ItemDuctTape extends ItemBlock {
    public ItemDuctTape() {
        super(RegistryHandler.DUCT_TAPE);
        setRegistryName("duct_tape_item");
        setTranslationKey("duct_tape_item");
        setMaxStackSize(1);
    }
}

package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.ExtraRegistry;
import net.minecraft.init.Blocks;

public class ItemMothSeed extends ItemSeedBase {
    public ItemMothSeed() {
        super("moth_seed", () -> ExtraRegistry.BLOCK_MYMOTHPLANT, () -> Blocks.FARMLAND);
    }
}

package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.ExtraRegistry;
import net.minecraft.init.Blocks;

public class ItemStrawberrySeed extends ItemSeedBase {
    public ItemStrawberrySeed() {
        super("strawberry_seed", () -> ExtraRegistry.BLOCK_MYSTRAWBERRYPLANT, () -> Blocks.FARMLAND);
    }
}

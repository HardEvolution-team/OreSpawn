package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.ExtraRegistry;
import net.minecraft.init.Blocks;

public class ItemButterflySeed extends ItemSeedBase {
    public ItemButterflySeed() {
        super("butterfly_seed", () -> ExtraRegistry.BLOCK_MYBUTTERFLYPLANT, () -> Blocks.FARMLAND);
    }
}

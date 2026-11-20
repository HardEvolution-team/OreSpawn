package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.ExtraRegistry;
import net.minecraft.init.Blocks;

public class ItemFireflySeed extends ItemSeedBase {
    public ItemFireflySeed() {
        super("firefly_seed", () -> ExtraRegistry.BLOCK_MYFIREFLYPLANT, () -> Blocks.FARMLAND);
    }
}

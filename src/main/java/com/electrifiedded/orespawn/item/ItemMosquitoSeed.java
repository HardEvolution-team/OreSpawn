package com.electrifiedded.orespawn.item;

import com.electrifiedded.orespawn.ExtraRegistry;
import net.minecraft.init.Blocks;

public class ItemMosquitoSeed extends ItemSeedBase {
    public ItemMosquitoSeed() {
        super("mosquito_seed", () -> ExtraRegistry.BLOCK_MYMOSQUITOPLANT, () -> Blocks.FARMLAND);
    }
}

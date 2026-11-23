package com.electrifiedded.orespawn.util;
import com.electrifiedded.orespawn.entity.*;

import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/** 1.7 -> 1.12 spawner entity assignment with OreSpawn entity name translation. */
public final class SpawnerCompat {
    public static void set(TileEntityMobSpawner te, String name) {
        String id = name.toLowerCase(java.util.Locale.ROOT).replace(' ', '_').replace("'", "").replace("?", "");
        ResourceLocation rl = new ResourceLocation("orespawndedsport", id);
        if (!ForgeRegistries.ENTITIES.containsKey(rl)) {
            rl = new ResourceLocation(id); // vanilla entity
        }
        te.getSpawnerBaseLogic().setEntityId(rl);
    }

    private SpawnerCompat() {}
}

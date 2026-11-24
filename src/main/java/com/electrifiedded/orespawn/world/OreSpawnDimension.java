package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;

import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class OreSpawnDimension {
    public static DimensionType UTOPIA;
    public static DimensionType EXTREME;
    public static DimensionType VILLAGE_MANIA;
    public static DimensionType ISLANDS;
    public static DimensionType CRYSTAL;
    public static DimensionType CHAOS;

    private static boolean free(int id) {
        return id > 1 && !DimensionManager.isDimensionRegistered(id);
    }

    /** Picks the first base id whose 6 consecutive ids are all free, so other mods' dimensions never clash. */
    private static void resolveIds() {
        int base = Math.max(2, OreSpawn.BaseDimensionID);
        for (int tries = 0; tries < 10000; tries++, base++) {
            boolean ok = true;
            for (int i = 0; i < 6 && ok; i++) ok = free(base + i);
            if (ok) break;
        }
        if (base != OreSpawn.BaseDimensionID) {
            org.apache.logging.log4j.LogManager.getLogger("orespawndedsport")
                .warn("OreSpawn dimension base id {} is taken by another mod; using {} instead", OreSpawn.BaseDimensionID, base);
        }
        OreSpawn.DimensionID = base;
        OreSpawn.DimensionID2 = base + 1;
        OreSpawn.DimensionID3 = base + 2;
        OreSpawn.DimensionID4 = base + 3;
        OreSpawn.DimensionID5 = base + 4;
        OreSpawn.DimensionID6 = base + 5;
    }

    public static void registerDimensions() {
        resolveIds();
        UTOPIA = register(OreSpawn.DimensionID, "orespawn", WorldProviderOreSpawn.class);
        EXTREME = register(OreSpawn.DimensionID2, "orespawn2", WorldProviderOreSpawn2.class);
        VILLAGE_MANIA = register(OreSpawn.DimensionID3, "orespawn3", WorldProviderOreSpawn3.class);
        ISLANDS = register(OreSpawn.DimensionID4, "orespawn4", WorldProviderOreSpawn4.class);
        CRYSTAL = register(OreSpawn.DimensionID5, "orespawn5", WorldProviderOreSpawn5.class);
        CHAOS = register(OreSpawn.DimensionID6, "orespawn6", WorldProviderOreSpawn6.class);
    }

    private static DimensionType register(int id, String name, Class<? extends net.minecraft.world.WorldProvider> providerClass) {
        if (id == -1 || id == 0 || id == 1) {
            throw new IllegalStateException("OreSpawn dimension ID conflicts with vanilla: " + id);
        }
        if (DimensionManager.isDimensionRegistered(id)) {
            throw new IllegalStateException("OreSpawn dimension ID is already registered: " + id);
        }
        DimensionType type = DimensionType.register(name, "_" + name, id, providerClass, true);
        DimensionManager.registerDimension(id, type);
        return type;
    }
}

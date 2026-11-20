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

    public static void registerDimensions() {
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

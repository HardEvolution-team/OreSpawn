package com.electrifiedded.orespawn.world;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.Reference;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class OreSpawnBiomes {
    public static final Biome UTOPIA = biome("Utopia", 0.1F, 0.2F, 0);
    public static final Biome VILLAGES = biome("Villages", 0.1F, 0.2F, 3);
    public static final Biome ISLANDS = biome("Islands", 0.1F, 0.2F, 4);
    public static final Biome CRYSTAL = biome("Crystal", 0.1F, 0.5F, 5);
    public static final Biome CHAOS = biome("Chaos", 0.1F, 0.2F, 6);

    private OreSpawnBiomes() {}

    private static Biome biome(String name, float height, float variation, int profile) {
        BiomeGenUtopianPlains biome = new BiomeGenUtopianPlains(
            new Biome.BiomeProperties(name).setBaseHeight(height).setHeightVariation(variation)
                .setTemperature(0.7F).setRainfall(0.5F), profile);
        biome.setRegistryName(new ResourceLocation(Reference.MOD_ID, name.toLowerCase().replace(' ', '_')));
        return biome;
    }

    public static Biome forDimension(int dimension) {
        if (dimension == OreSpawn.DimensionID2) return Biomes.EXTREME_HILLS;
        if (dimension == OreSpawn.DimensionID3) return VILLAGES;
        if (dimension == OreSpawn.DimensionID4) return ISLANDS;
        if (dimension == OreSpawn.DimensionID5) return CRYSTAL;
        if (dimension == OreSpawn.DimensionID6) return CHAOS;
        return UTOPIA;
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(UTOPIA, VILLAGES, ISLANDS, CRYSTAL, CHAOS);
        BiomeManager.addVillageBiome(VILLAGES, true);
    }
}

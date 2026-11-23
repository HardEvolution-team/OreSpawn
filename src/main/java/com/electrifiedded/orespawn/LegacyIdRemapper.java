package com.electrifiedded.orespawn;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/** Worlds saved while the port still used the modid "orespawn": maps those ids to {@link Reference#MOD_ID}. */
public final class LegacyIdRemapper {
    private static final String OLD = "orespawn";

    private static <T extends IForgeRegistryEntry<T>> void remap(RegistryEvent.MissingMappings<T> e) {
        for (RegistryEvent.MissingMappings.Mapping<T> m : e.getAllMappings()) {
            if (!OLD.equals(m.key.getNamespace())) continue;
            T v = net.minecraftforge.registries.RegistryManager.ACTIVE.getRegistry(e.getRegistry().getRegistrySuperType()).getValue(new ResourceLocation(Reference.MOD_ID, m.key.getPath()));
            if (v != null) m.remap(v);
        }
    }

    @SubscribeEvent public static void blocks(RegistryEvent.MissingMappings<Block> e) { remap(e); }
    @SubscribeEvent public static void items(RegistryEvent.MissingMappings<Item> e) { remap(e); }
    @SubscribeEvent public static void biomes(RegistryEvent.MissingMappings<Biome> e) { remap(e); }
    @SubscribeEvent public static void sounds(RegistryEvent.MissingMappings<net.minecraft.util.SoundEvent> e) { remap(e); }
    @SubscribeEvent public static void entities(RegistryEvent.MissingMappings<EntityEntry> e) { remap(e); }
}

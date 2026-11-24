package com.electrifiedded.orespawn.world;

import net.minecraft.world.gen.structure.MapGenVillage;

/**
 * Village generator of the Village Mania dimension. Uses the plain vanilla 1.12 placement
 * (spacing 32 / separation 8, biome check against the dimension's village biome) so villages
 * stay whole and mods that hook vanilla villages behave the same as in the overworld.
 * The old 1.7.10 dense-spacing override never matched a 1.12 method and was dead code.
 */
public class MapGenMoreVillages extends MapGenVillage {
}

package com.electrifiedded.orespawn.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/** Forge entry point for OreSpawn's legacy chunk decoration. */
public class OreSpawnWorldGenerator implements IWorldGenerator {
    private final OreSpawnWorld legacyWorldGenerator = new OreSpawnWorld();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!world.isRemote) legacyWorldGenerator.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }
}

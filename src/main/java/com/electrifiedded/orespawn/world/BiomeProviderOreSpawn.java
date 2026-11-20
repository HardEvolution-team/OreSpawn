package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

public class BiomeProviderOreSpawn extends BiomeProvider {
    private final Biome biome;

    public BiomeProviderOreSpawn() {
        this(0);
    }

    public BiomeProviderOreSpawn(int dimension) {
        this(OreSpawnBiomes.forDimension(dimension));
    }

    public BiomeProviderOreSpawn(Biome biome) {
        this.biome = biome;
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return biome;
    }

    @Override
    public Biome getBiome(BlockPos pos, Biome defaultBiome) {
        return biome;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] list, int x, int z, int width, int height) {
        if (list == null || list.length < width * height) list = new Biome[width * height];
        java.util.Arrays.fill(list, 0, width * height, biome);
        return list;
    }

    @Override
    public Biome[] getBiomes(Biome[] list, int x, int z, int width, int height) {
        return getBiomesForGeneration(list, x, z, width, height);
    }

    @Override
    public Biome[] getBiomes(Biome[] list, int x, int z, int width, int height, boolean cacheFlag) {
        return getBiomesForGeneration(list, x, z, width, height);
    }

    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
        return allowed.contains(biome);
    }

    @Override
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> allowed, Random random) {
        if (!allowed.contains(biome)) return null;
        return new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1));
    }

    public net.minecraft.world.gen.IChunkGenerator createChunkGenerator(World world) {
        return new ChunkProviderOreSpawn(world, world.getSeed(), true);
    }
}

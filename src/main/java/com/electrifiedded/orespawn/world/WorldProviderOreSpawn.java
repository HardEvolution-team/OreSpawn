package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;

public class WorldProviderOreSpawn extends WorldProvider {
    public String getSaveFolder() {
        return "OreSpawn";
    }

    @Override
    protected void init() {
        this.biomeProvider = new BiomeProviderOreSpawn(OreSpawn.DimensionID);
        this.hasSkyLight = true;
    }

    @Override
    public net.minecraft.world.gen.IChunkGenerator createChunkGenerator() {
        return new ChunkProviderOreSpawn(this.world, this.world.getSeed(), true);
    }

    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(0.8, 0.9, 1.0);
    }

    public boolean canRespawnHere() {
        return true;
    }

    public boolean isSurfaceWorld() {
        return true;
    }

    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return super.calculateCelestialAngle(worldTime, partialTicks);
    }

    public DimensionType getDimensionType() {
        return OreSpawnDimension.UTOPIA;
    }
}

package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;

public class WorldProviderOreSpawn6 extends WorldProvider {
    @Override
    protected void init() {
        this.biomeProvider = new BiomeProviderOreSpawn(OreSpawn.DimensionID6);
        this.hasSkyLight = true;
    }

    @Override
    public net.minecraft.world.gen.IChunkGenerator createChunkGenerator() {
        return new ChunkProviderOreSpawn6(this.world, this.world.getSeed());
    }
    public String getSaveFolder() {
        return "OreSpawn6";
    }

    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
        return new Vec3d(1.0, 1.0, 0.8);
    }

    public DimensionType getDimensionType() {
        return OreSpawnDimension.CHAOS;
    }

    public boolean canRespawnHere() { return true; }
    public boolean isSurfaceWorld() { return true; }

}

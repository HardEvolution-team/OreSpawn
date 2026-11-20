package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/** Ant and termite nests: while it is not raining they release a swarm into free air above. */
public class BlockAnt extends BlockBase {
    public BlockAnt(String name) {
        // The original nests extended BlockGrass without setting a hardness, so they break instantly.
        super(name, Material.GRASS);
        setHardness(0.0F);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (world.isRemote || world.isRaining() || !world.isAirBlock(pos.up())) {
            return;
        }
        String entity = nestEntity();
        if (entity == null) {
            return;
        }
        int count = 2 + OreSpawn.OreSpawnRand.nextInt(6);
        for (int i = 0; i < count; i++) {
            Entity spawned = OreSpawnEntityRegistry.create(entity, world);
            if (spawned != null) {
                spawned.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5, world.rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(spawned);
                OreSpawnEntityRegistry.initialize(spawned, world);
            }
        }
    }

    private String nestEntity() {
        if (this == OreSpawn.MyAntBlock) return OreSpawn.BlackAntEnable != 0 ? "Ant" : null;
        if (this == OreSpawn.MyRedAntBlock) return OreSpawn.RedAntEnable != 0 ? "Red Ant" : null;
        if (this == OreSpawn.MyUnstableAntBlock) return OreSpawn.UnstableAntEnable != 0 ? "Unstable Ant" : null;
        if (this == OreSpawn.TermiteBlock || this == OreSpawn.CrystalTermiteBlock) return OreSpawn.TermiteEnable != 0 ? "Termite" : null;
        return OreSpawn.RainbowAntEnable != 0 ? "Rainbow Ant" : null;
    }
}

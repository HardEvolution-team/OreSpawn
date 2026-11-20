package com.electrifiedded.orespawn.block.ore;

import com.electrifiedded.orespawn.entity.EntityAnt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Salt ore: drops itself (smelts into 8 salt) and burns ants that touch it. */
public class BlockOreSalt extends BlockOreBase {
    public BlockOreSalt() {
        super("ore_salt", 5.0F, 2.0F);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        hurtAnt(entity);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        hurtAnt(entity);
    }

    private static void hurtAnt(Entity entity) {
        if (entity instanceof EntityAnt) {
            entity.attackEntityFrom(DamageSource.CACTUS, 5.0F);
        }
    }
}

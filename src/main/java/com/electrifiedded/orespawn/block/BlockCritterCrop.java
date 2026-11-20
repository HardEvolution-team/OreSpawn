package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Butterfly / Moth / Firefly / Mosquito plants: farmland crops that release their creature as they grow
 * (the riper the plant, the more often). Only seeds drop.
 */
public class BlockCritterCrop extends BlockOreSpawnCrop {
   public enum Kind { BUTTERFLY, MOTH, FIREFLY, MOSQUITO }

   private final Kind kind;

   public BlockCritterCrop(String name, Supplier<Item> seed, Kind kind) {
      super(name, seed, () -> Items.AIR, 1, 0);
      this.kind = kind;
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      super.updateTick(world, pos, state, rand);
      if (world.isRemote) return;
      IBlockState now = world.getBlockState(pos);
      if (now.getBlock() != this) return;
      if (kind == Kind.MOSQUITO ? OreSpawn.MosquitoEnable == 0 : world.isRaining()) return;
      int rate = (kind == Kind.FIREFLY ? 6 : 7) - getAge(now);
      if (rate > 1 && OreSpawn.OreSpawnRand.nextInt(rate) != 0) return;
      if (!world.isAirBlock(pos.up())) return;
      switch (kind) {
         case BUTTERFLY:
            if (world.isDaytime() && OreSpawn.ButterflyEnable != 0) spawn(world, pos, "Butterfly", 1);
            break;
         case MOTH:
            if (!world.isDaytime() && OreSpawn.MothEnable != 0) spawn(world, pos, "Moth", 1);
            break;
         case FIREFLY:
            if (!world.isDaytime() && OreSpawn.FireflyEnable != 0) spawn(world, pos, "Firefly", 2 + world.rand.nextInt(5));
            break;
         case MOSQUITO:
            spawn(world, pos, "Mosquito", 2 + OreSpawn.OreSpawnRand.nextInt(5));
            break;
      }
   }

   private static void spawn(World world, BlockPos pos, String creature, int count) {
      for (int i = 0; i < count; i++) {
         OreBasicStone.spawnCreature(world, creature, pos.getX() + 0.5, pos.getY() + 1.01, pos.getZ() + 0.5);
      }
   }
}

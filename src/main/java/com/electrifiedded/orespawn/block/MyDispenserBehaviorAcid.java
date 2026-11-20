package com.electrifiedded.orespawn.block;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

final class MyDispenserBehaviorAcid extends BehaviorProjectileDispense {
   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, net.minecraft.item.ItemStack stack) {
      return new Acid(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
   }
}

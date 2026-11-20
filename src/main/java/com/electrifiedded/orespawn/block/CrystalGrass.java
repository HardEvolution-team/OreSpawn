package com.electrifiedded.orespawn.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

/** Crystal-dimension grass: same see-through rendering as crystal stone, sustains any plant. */
public class CrystalGrass extends OreBasicStone {
   public CrystalGrass(String name) {
      super(Material.GRASS, "shovel", name, 0.6F, 2.0F, null, 0, 0);
   }

   @Override
   public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
      return true;
   }
}

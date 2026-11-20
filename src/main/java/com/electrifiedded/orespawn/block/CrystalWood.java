package com.electrifiedded.orespawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * See-through crystal building blocks: Crystal Planks (original CrystalWood) and the Pink Crystal block
 * (original BlockCrystal). Not full cubes, so mobs do not spawn on them, as in the original.
 */
public class CrystalWood extends Block {
   public CrystalWood(String name, Material material, String tool, SoundType sound, float hardness, float resistance, float light) {
      super(material);
      setHardness(hardness);
      setResistance(resistance);
      setLightLevel(light);
      setSoundType(sound);
      setHarvestLevel(tool, 0);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
   }

   public static CrystalWood planks() {
      return new CrystalWood("crystal_planks", Material.WOOD, "axe", SoundType.WOOD, 1.5F, 4.0F, 0.0F);
   }

   public static CrystalWood pinkCrystal() {
      return new CrystalWood("crystal_pink_block", Material.ROCK, "pickaxe", SoundType.STONE, 4.0F, 4.0F, 0.4F);
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.SOLID;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }
}

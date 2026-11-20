package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;

public class CrystalShovel extends ItemTool {
   public static final Set blocksEffectiveAgainst = Sets.newHashSet(
      new Block[]{
         Blocks.GRASS,
         Blocks.DIRT,
         Blocks.SAND,
         Blocks.GRAVEL,
         Blocks.SNOW,
         Blocks.SNOW_LAYER,
         Blocks.CLAY,
         Blocks.SOUL_SAND,
         Blocks.MYCELIUM,
         OreSpawn.CrystalGrass
      }
   );

   public CrystalShovel(int par1, ToolMaterial par2) {
      super(1.0F, -3.0F, par2, blocksEffectiveAgainst);

      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public boolean canHarvestBlock(Block par1Block) {
      return par1Block == Blocks.SNOW;
   }


}

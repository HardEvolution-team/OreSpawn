package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;

public class CrystalPickaxe extends ItemPickaxe {
   public CrystalPickaxe(int par1, ToolMaterial par2) {
      super(par2);

      this.setCreativeTab(CreativeTabs.TOOLS);
   }


}

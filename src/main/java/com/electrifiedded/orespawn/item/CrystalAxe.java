package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item.ToolMaterial;

public class CrystalAxe extends ItemAxe {
   public CrystalAxe(int par1, ToolMaterial par2) {
       super(par2, par2.getAttackDamage() + 5.0F, -3.0F);

      this.setCreativeTab(CreativeTabs.TOOLS);
   }


}

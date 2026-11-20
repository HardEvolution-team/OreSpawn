package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;

public class CrystalSword extends ItemSword {
   private final ToolMaterial toolMaterial;

   public CrystalSword(int par1, ToolMaterial par2EnumToolMaterial) {
      super(par2EnumToolMaterial);
      this.toolMaterial = par2EnumToolMaterial;

      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 300;
   }


}

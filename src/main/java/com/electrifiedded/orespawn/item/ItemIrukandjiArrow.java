package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemIrukandjiArrow extends Item {
   public ItemIrukandjiArrow(int i) {
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setRegistryName("irukandji_arrow");
      this.setTranslationKey("irukandji_arrow");
   }


}

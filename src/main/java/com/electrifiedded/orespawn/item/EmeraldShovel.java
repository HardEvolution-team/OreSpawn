package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.Item.ToolMaterial;

public class EmeraldShovel extends ItemSpade {
   public EmeraldShovel(int par1, ToolMaterial par2) {
      super(par2);

      this.setMaxDamage(1300);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public int getDamageVsEntity(Entity par1Entity) {
      return 5;
   }

   public String getMaterialName() {
      return "Emerald";
   }


}

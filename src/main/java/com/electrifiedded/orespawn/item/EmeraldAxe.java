package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item.ToolMaterial;

public class EmeraldAxe extends ItemAxe {
   private int weaponDamage = 10;

   public EmeraldAxe(int par1, ToolMaterial par2) {
       super(par2, par2.getAttackDamage() + 5.0F, -3.0F);

      this.setMaxDamage(1300);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public int getDamageVsEntity(Entity par1Entity) {
      return this.weaponDamage;
   }

   public String getMaterialName() {
      return "Emerald";
   }


}

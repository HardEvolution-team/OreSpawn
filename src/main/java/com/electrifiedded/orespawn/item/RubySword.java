package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;

public class RubySword extends ItemSword {
   private int weaponDamage;
   private final ToolMaterial toolMaterial;

   public RubySword(int par1, ToolMaterial par2EnumToolMaterial) {
      super(par2EnumToolMaterial);
      this.toolMaterial = par2EnumToolMaterial;
      this.weaponDamage = 18;

      this.setMaxDamage(1500);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public int getDamageVsEntity(Entity par1Entity) {
      return this.weaponDamage;
   }

   public String getMaterialName() {
      return "Ruby";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 4000;
   }


}

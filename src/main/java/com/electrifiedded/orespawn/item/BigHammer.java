package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class BigHammer extends SpecialSword {
   private int weaponDamage;
   private final ToolMaterial toolMaterial;

   public BigHammer(ToolMaterial par2EnumToolMaterial, String name) {
      super(par2EnumToolMaterial, name);
      this.toolMaterial = par2EnumToolMaterial;
      this.weaponDamage = 15;

      this.setMaxDamage(9000);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public String getMaterialName() {
      return "AMETHYST";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      int var2x = 5;
      if (par2EntityLiving != null && !par2EntityLiving.world.isRemote) {
         par2EntityLiving.addVelocity(0.0, Math.abs(par2EntityLiving.world.rand.nextFloat() * 2.0F / 3.0F), 0.0);
      }

      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 3000;
   }


}

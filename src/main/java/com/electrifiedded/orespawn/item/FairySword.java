package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class FairySword extends SpecialSword {
   private int weaponDamage;
   private final ToolMaterial toolMaterial;

   public FairySword(ToolMaterial par2EnumToolMaterial, String name) {
      super(par2EnumToolMaterial, name);
      this.toolMaterial = par2EnumToolMaterial;
      this.weaponDamage = 15;

      this.setMaxDamage(1300);
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public String getMaterialName() {
      return "Fairy";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      int var2x = 5;
      if (par2EntityLiving != null && !par2EntityLiving.world.isRemote) {
         int num = 1 + par2EntityLiving.world.rand.nextInt(3);

         for (int i = 0; i < num; i++) {
            Fairy r = null;
            r = (Fairy)spawnCreature(
               par2EntityLiving.world,
               0,
               "Fairy",
               par2EntityLiving.posX
                  + (par2EntityLiving.world.rand.nextFloat() - par2EntityLiving.world.rand.nextFloat()) * 0.5,
               par2EntityLiving.posY + par2EntityLiving.world.rand.nextFloat() + 0.01,
               par2EntityLiving.posZ
                  + (par2EntityLiving.world.rand.nextFloat() - par2EntityLiving.world.rand.nextFloat()) * 0.5
            );
            if (r != null) {
               r.setOwner(par3EntityLiving);
            }
         }
      }

      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public static Entity spawnCreature(World par0World, int par1, String name, double par2, double par4, double par6) {
      Entity var8 = null;
      if (name == null) {
         var8 = EntityList.createEntityByID(par1, par0World);
      } else {
         var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(name, par0World);
      }

      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 3000;
   }


}

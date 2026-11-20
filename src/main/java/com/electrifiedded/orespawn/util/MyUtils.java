package com.electrifiedded.orespawn.util;
import net.minecraft.inventory.EntityEquipmentSlot;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnConstants;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;

public class MyUtils {
   public static boolean isRoyalty(Entity e) {
      if (!(e instanceof EntityLivingBase)) {
         return false;
      } else if (e instanceof ThePrince) {
         return true;
      } else if (e instanceof ThePrinceTeen) {
         return true;
      } else if (e instanceof ThePrinceAdult) {
         return true;
      } else if (e instanceof ThePrincess) {
         return true;
      } else if (e instanceof TheKing) {
         return true;
      } else if (e instanceof KingHead) {
         return true;
      } else if (e instanceof TheQueen) {
         return true;
      } else {
         return e instanceof QueenHead ? true : e instanceof PurplePower;
      }
   }

   public static boolean isAttackableNonMob(EntityLivingBase par1EntityLiving) {
      if (par1EntityLiving instanceof EntityMob) {
         return true;
      } else if (par1EntityLiving instanceof Mothra) {
         return true;
      } else if (par1EntityLiving instanceof Leon) {
         return true;
      } else if (par1EntityLiving instanceof Dragon) {
         return true;
      } else if (par1EntityLiving instanceof Spyro) {
         return true;
      } else if (isRoyalty(par1EntityLiving)) {
         return true;
      } else if (par1EntityLiving instanceof GammaMetroid) {
         return true;
      } else if (par1EntityLiving instanceof Cephadrome) {
         return true;
      } else if (par1EntityLiving instanceof WaterDragon) {
         return true;
      } else if (par1EntityLiving instanceof Girlfriend) {
         return true;
      } else if (par1EntityLiving instanceof Boyfriend) {
         return true;
      } else {
         return par1EntityLiving instanceof EntityVillager ? true : par1EntityLiving instanceof Stinky;
      }
   }

   public static boolean isIgnoreable(EntityLivingBase par1EntityLiving) {
      if (par1EntityLiving instanceof RockBase) {
         return true;
      } else if (par1EntityLiving instanceof EntityAnt) {
         return true;
      } else if (par1EntityLiving instanceof EntityButterfly) {
         return true;
      } else if (par1EntityLiving instanceof EntityMosquito) {
         return true;
      } else if (par1EntityLiving instanceof Dragonfly) {
         return true;
      } else if (par1EntityLiving instanceof Firefly) {
         return true;
      } else if (par1EntityLiving instanceof Cricket) {
         return true;
      } else if (par1EntityLiving instanceof Cockateil) {
         return true;
      } else if (par1EntityLiving instanceof Termite) {
         return true;
      } else if (par1EntityLiving instanceof Ghost) {
         return true;
      } else {
         return par1EntityLiving instanceof GhostSkelly ? true : par1EntityLiving instanceof Elevator;
      }
   }

   /** Slot mapping identical to 1.7.10: 0 = held item, 1 = feet, 2 = legs, 3 = chest, 4 = head. */
   public static net.minecraft.item.ItemStack getSlot(EntityLivingBase e, int slot) {
      switch (slot) {
         case 0: return e.getHeldItemMainhand();
         case 1: return e.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET);
         case 2: return e.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS);
         case 3: return e.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.CHEST);
         case 4: return e.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.HEAD);
      }
      return net.minecraft.item.ItemStack.EMPTY;
   }

   public static void setSlot(EntityLivingBase e, int slot, net.minecraft.item.ItemStack stack) {
      switch (slot) {
         case 0: e.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.MAINHAND, stack); return;
         case 1: e.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, stack); return;
         case 2: e.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS, stack); return;
         case 3: e.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.CHEST, stack); return;
         case 4: e.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.HEAD, stack); return;
      }
   }

   public static String spawnerName(net.minecraft.tileentity.TileEntityMobSpawner te) {
      try {
         net.minecraft.entity.Entity e = te.getSpawnerBaseLogic().getCachedEntity();
         if (e != null) {
            String s = String.valueOf(net.minecraft.entity.EntityList.getKey(e));
            int c = s.indexOf(58);
            return c >= 0 ? s.substring(c + 1) : s;
         }
      } catch (Throwable ignored) {
      }
      return "";
   }
}

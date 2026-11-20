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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRock extends Item {
   public ItemRock(int i) {

      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public ItemRock(String name) {
      this(0);
      setRegistryName(name);
      setTranslationKey(name);
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      ItemStack stack = player.getHeldItem(hand);
      onItemRightClick(stack, world, player);
      return new net.minecraft.util.ActionResult<>(net.minecraft.util.EnumActionResult.SUCCESS, stack);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (!par3EntityPlayer.capabilities.isCreativeMode) {
         par1ItemStack.shrink(1);
      }

      par2World.playSound(null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.bow"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
      if (!par2World.isRemote) {
         if (this == OreSpawn.MySmallRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 1));
         }

         if (this == OreSpawn.MyRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 2));
         }

         if (this == OreSpawn.MyRedRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 3));
         }

         if (this == OreSpawn.MyGreenRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 4));
         }

         if (this == OreSpawn.MyBlueRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 5));
         }

         if (this == OreSpawn.MyPurpleRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 6));
         }

         if (this == OreSpawn.MySpikeyRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 7));
         }

         if (this == OreSpawn.MyTNTRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 8));
         }

         if (this == OreSpawn.MyCrystalRedRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 9));
         }

         if (this == OreSpawn.MyCrystalGreenRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 10));
         }

         if (this == OreSpawn.MyCrystalBlueRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 11));
         }

         if (this == OreSpawn.MyCrystalTNTRock) {
            par2World.spawnEntity(new EntityThrownRock(par2World, par3EntityPlayer, 12));
         }
      }

      return par1ItemStack;
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10
   ) {
      if (!world.isRemote) {
         if (x < 0) {
            x++;
         }

         if (z < 0) {
            z++;
         }

         Entity e = this.spawnCreature(world, "Rock", x, y + 1.01, z);
         if (e != null) {
            RockBase r = (RockBase)e;
            if (this == OreSpawn.MySmallRock) {
               r.placeRock(1);
            }

            if (this == OreSpawn.MyRock) {
               r.placeRock(2);
            }

            if (this == OreSpawn.MyRedRock) {
               r.placeRock(3);
            }

            if (this == OreSpawn.MyGreenRock) {
               r.placeRock(4);
            }

            if (this == OreSpawn.MyBlueRock) {
               r.placeRock(5);
            }

            if (this == OreSpawn.MyPurpleRock) {
               r.placeRock(6);
            }

            if (this == OreSpawn.MySpikeyRock) {
               r.placeRock(7);
            }

            if (this == OreSpawn.MyTNTRock) {
               r.placeRock(8);
            }

            if (this == OreSpawn.MyCrystalRedRock) {
               r.placeRock(9);
            }

            if (this == OreSpawn.MyCrystalGreenRock) {
               r.placeRock(10);
            }

            if (this == OreSpawn.MyCrystalBlueRock) {
               r.placeRock(11);
            }

            if (this == OreSpawn.MyCrystalTNTRock) {
               r.placeRock(12);
            }
         }
      }

      if (!par2EntityPlayer.capabilities.isCreativeMode) {
         par1ItemStack.shrink(1);
      }

      return true;
   }

   private Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(par1, par0World);
      if (var8 != null) {
         if (par2 > 0.0) {
            par2 += 0.5;
         }

         if (par2 < 0.0) {
            par2 -= 0.5;
         }

         if (par6 > 0.0) {
            par6 += 0.5;
         }

         if (par6 < 0.0) {
            par6 -= 0.5;
         }

         var8.setLocationAndAngles(par2, par4 + 0.01, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, net.minecraft.util.math.BlockPos pos,
         net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), hitX, hitY, hitZ)
         ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }
}

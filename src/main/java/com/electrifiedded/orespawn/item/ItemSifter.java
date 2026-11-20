package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSifter extends Item {
   public ItemSifter(int i) {

      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setMaxDamage(600);
   }
   public ItemSifter(String name) { this(0); setRegistryName(name); setTranslationKey(name); setMaxStackSize(1); }
   @Override public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float x, float y, float z) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), x, y, z) ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }

   private void dropItemRand(Item index, int par1, World world, int x, int y, int z) {
      EntityItem var3x = new EntityItem(
         world,
         x + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2) + 0.5,
         y + 1.1,
         z + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2) + 0.5,
         new ItemStack(index, par1, 0)
      );
      world.spawnEntity(var3x);
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10
   ) {
      if (par3World.isRemote) {
         return true;
      } else {
         Block bid = par3World.getBlockState(new BlockPos(par4, par5, par6)).getBlock();
         Block bid2 = par3World.getBlockState(new BlockPos(par4, par5 + 1, par6)).getBlock();
         if (bid2 == Blocks.FLOWING_WATER) {
            bid = Blocks.WATER;
         }

         if (bid2 == Blocks.WATER) {
            bid = Blocks.WATER;
         }

         if (bid == Blocks.WATER) {
            int i = par3World.rand.nextInt(160);
            switch (i) {
               case 0:
                  this.dropItemRand(Items.FISH, 1, par3World, par4, par5, par6);
                  break;
               case 1:
                  this.dropItemRand(OreSpawn.MyGreenFish, 1, par3World, par4, par5, par6);
                  break;
               case 2:
                  this.dropItemRand(OreSpawn.MyBlueFish, 1, par3World, par4, par5, par6);
                  break;
               case 3:
                  this.dropItemRand(OreSpawn.MyPinkFish, 1, par3World, par4, par5, par6);
                  break;
               case 4:
                  this.dropItemRand(OreSpawn.MyRockFish, 1, par3World, par4, par5, par6);
                  break;
               case 5:
                  this.dropItemRand(OreSpawn.MyWoodFish, 1, par3World, par4, par5, par6);
                  break;
               case 6:
                  this.dropItemRand(OreSpawn.MyGreyFish, 1, par3World, par4, par5, par6);
                  break;
               case 7:
                  this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                  break;
               case 8:
                  this.dropItemRand(Items.IRON_INGOT, 1, par3World, par4, par5, par6);
                  break;
               case 9:
                  this.dropItemRand(Items.GOLD_NUGGET, 1, par3World, par4, par5, par6);
                  break;
               case 10:
                  this.dropItemRand(OreSpawn.MyItemShoes, 1, par3World, par4, par5, par6);
                  break;
               case 11:
                  this.dropItemRand(OreSpawn.MyItemShoes_1, 1, par3World, par4, par5, par6);
                  break;
               case 12:
                  this.dropItemRand(OreSpawn.MyItemShoes_2, 1, par3World, par4, par5, par6);
                  break;
               case 13:
                  this.dropItemRand(OreSpawn.MyItemShoes_3, 1, par3World, par4, par5, par6);
                  break;
               case 14:
                  this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                  break;
               case 15:
                  this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                  break;
               case 16:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.STONE), 1, par3World, par4, par5, par6);
                  break;
               case 17:
                  this.dropItemRand(Items.BUCKET, 1, par3World, par4, par5, par6);
                  break;
               case 18:
                  this.dropItemRand(Items.WATER_BUCKET, 1, par3World, par4, par5, par6);
                  break;
               case 19:
                  if (par3World.rand.nextInt(3) == 1) {
                     this.dropItemRand(Items.EMERALD, 1, par3World, par4, par5, par6);
                  } else {
                     this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                  }
                  break;
               case 20:
                  if (par3World.rand.nextInt(3) == 1) {
                     this.dropItemRand(OreSpawn.MyRuby, 1, par3World, par4, par5, par6);
                  } else {
                     this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                  }
                  break;
               case 21:
                  if (par3World.rand.nextInt(3) == 1) {
                     this.dropItemRand(OreSpawn.MyAmethyst, 1, par3World, par4, par5, par6);
                  } else {
                     this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                  }
                  break;
               case 22:
                  this.dropItemRand(OreSpawn.MyMothScale, 1, par3World, par4, par5, par6);
                  break;
               case 23:
                  this.dropItemRand(OreSpawn.UraniumNugget, 1, par3World, par4, par5, par6);
                  break;
               case 24:
                  this.dropItemRand(OreSpawn.TitaniumNugget, 1, par3World, par4, par5, par6);
                  break;
               case 25:
                  if (par3World.rand.nextInt(2) == 1) {
                     this.dropItemRand(Items.DIAMOND, 1, par3World, par4, par5, par6);
                  } else {
                     this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                  }
                  break;
               case 26:
                  this.dropItemRand(Items.IRON_INGOT, 1, par3World, par4, par5, par6);
                  break;
               case 27:
                  this.dropItemRand(Items.GOLD_NUGGET, 1, par3World, par4, par5, par6);
                  break;
               case 28:
                  this.dropItemRand(Items.REDSTONE, 1, par3World, par4, par5, par6);
                  break;
               case 29:
                  this.dropItemRand(Items.COAL, 1, par3World, par4, par5, par6);
                  break;
               case 30:
                  this.dropItemRand(OreSpawn.MyItemShoes, 1, par3World, par4, par5, par6);
                  break;
               case 31:
                  this.dropItemRand(OreSpawn.MyItemShoes_1, 1, par3World, par4, par5, par6);
                  break;
               case 32:
                  this.dropItemRand(OreSpawn.MyItemShoes_2, 1, par3World, par4, par5, par6);
                  break;
               case 33:
                  this.dropItemRand(OreSpawn.MyItemShoes_3, 1, par3World, par4, par5, par6);
                  break;
               case 34:
                  this.dropItemRand(Items.FISH, 1, par3World, par4, par5, par6);
                  break;
               case 35:
                  this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                  break;
               case 36:
                  this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                  break;
               case 37:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.STONE), 1, par3World, par4, par5, par6);
                  break;
               case 38:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.STONE_BUTTON), 1, par3World, par4, par5, par6);
                  break;
               case 39:
                  this.dropItemRand(Items.BUCKET, 1, par3World, par4, par5, par6);
                  break;
               case 40:
                  this.dropItemRand(Items.WATER_BUCKET, 1, par3World, par4, par5, par6);
            }
         }

         if (bid == Blocks.SAND) {
            int i = par3World.rand.nextInt(60);
            switch (i) {
               case 0:
                  this.dropItemRand(Items.IRON_HORSE_ARMOR, 1, par3World, par4, par5, par6);
                  break;
               case 1:
                  this.dropItemRand(Items.SHEARS, 1, par3World, par4, par5, par6);
                  break;
               case 2:
                  this.dropItemRand(Items.CARROT_ON_A_STICK, 1, par3World, par4, par5, par6);
                  break;
               case 3:
                  this.dropItemRand(Items.POISONOUS_POTATO, 1, par3World, par4, par5, par6);
                  break;
               case 4:
                  this.dropItemRand(Items.ITEM_FRAME, 1, par3World, par4, par5, par6);
                  break;
               case 5:
                  this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                  break;
               case 6:
                  this.dropItemRand(Items.COMPASS, 1, par3World, par4, par5, par6);
                  break;
               case 7:
                  this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                  break;
               case 8:
                  this.dropItemRand(Items.SADDLE, 1, par3World, par4, par5, par6);
                  break;
               case 9:
                  this.dropItemRand(Items.IRON_HELMET, 1, par3World, par4, par5, par6);
                  break;
               case 10:
                  this.dropItemRand(Items.IRON_CHESTPLATE, 1, par3World, par4, par5, par6);
                  break;
               case 11:
                  this.dropItemRand(Items.IRON_LEGGINGS, 1, par3World, par4, par5, par6);
                  break;
               case 12:
                  this.dropItemRand(Items.IRON_BOOTS, 1, par3World, par4, par5, par6);
                  break;
               case 13:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.SAND), 1, par3World, par4, par5, par6);
            }
         }

         if (bid == Blocks.GRAVEL) {
            int i = par3World.rand.nextInt(60);
            switch (i) {
               case 0:
                  this.dropItemRand(Items.FLINT, 1, par3World, par4, par5, par6);
                  break;
               case 1:
                  this.dropItemRand(OreSpawn.MySalt, 1, par3World, par4, par5, par6);
                  break;
               case 2:
                  this.dropItemRand(Items.FLINT_AND_STEEL, 1, par3World, par4, par5, par6);
                  break;
               case 3:
                  this.dropItemRand(Items.SPIDER_EYE, 1, par3World, par4, par5, par6);
                  break;
               case 4:
                  this.dropItemRand(Items.ITEM_FRAME, 1, par3World, par4, par5, par6);
                  break;
               case 5:
                  this.dropItemRand(Items.FEATHER, 1, par3World, par4, par5, par6);
                  break;
               case 6:
                  this.dropItemRand(Items.STRING, 1, par3World, par4, par5, par6);
                  break;
               case 7:
                  this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                  break;
               case 8:
                  this.dropItemRand(Items.LEAD, 1, par3World, par4, par5, par6);
                  break;
               case 9:
                  this.dropItemRand(Items.NAME_TAG, 1, par3World, par4, par5, par6);
                  break;
               case 10:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.SAND), 1, par3World, par4, par5, par6);
                  break;
               case 11:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
            }
         }

         if (bid == Blocks.DIRT) {
            int i = par3World.rand.nextInt(60);
            switch (i) {
               case 0:
                  this.dropItemRand(Items.STRING, 1, par3World, par4, par5, par6);
                  break;
               case 1:
                  this.dropItemRand(OreSpawn.MySalt, 1, par3World, par4, par5, par6);
                  break;
               case 2:
                  this.dropItemRand(Items.SHEARS, 1, par3World, par4, par5, par6);
                  break;
               case 3:
                  this.dropItemRand(Items.STICK, 1, par3World, par4, par5, par6);
                  break;
               case 4:
                  this.dropItemRand(Items.BOWL, 1, par3World, par4, par5, par6);
                  break;
               case 5:
                  this.dropItemRand(Items.FLOWER_POT, 1, par3World, par4, par5, par6);
                  break;
               case 6:
                  this.dropItemRand(Items.SIGN, 1, par3World, par4, par5, par6);
                  break;
               case 7:
                  this.dropItemRand(Items.BRICK, 1, par3World, par4, par5, par6);
                  break;
               case 8:
                  this.dropItemRand(Items.PAPER, 1, par3World, par4, par5, par6);
                  break;
               case 9:
                  this.dropItemRand(Items.BONE, 1, par3World, par4, par5, par6);
                  break;
               case 10:
                  this.dropItemRand(Items.GLASS_BOTTLE, 1, par3World, par4, par5, par6);
                  break;
               case 11:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.SAND), 1, par3World, par4, par5, par6);
                  break;
               case 12:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                  break;
               case 13:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.DIRT), 1, par3World, par4, par5, par6);
            }
         }

         if (bid == Blocks.GRASS) {
            int i = par3World.rand.nextInt(60);
            switch (i) {
               case 0:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 1, par3World, par4, par5, par6);
                  break;
               case 1:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.RED_FLOWER), 1, par3World, par4, par5, par6);
                  break;
               case 2:
                  this.dropItemRand(Item.getItemFromBlock(OreSpawn.MyFlowerPinkBlock), 1, par3World, par4, par5, par6);
                  break;
               case 3:
                  this.dropItemRand(Item.getItemFromBlock(OreSpawn.MyFlowerBlueBlock), 1, par3World, par4, par5, par6);
                  break;
               case 4:
                  this.dropItemRand(Item.getItemFromBlock(OreSpawn.MyFlowerBlackBlock), 1, par3World, par4, par5, par6);
                  break;
               case 5:
                  this.dropItemRand(Item.getItemFromBlock(OreSpawn.MyFlowerScaryBlock), 1, par3World, par4, par5, par6);
               case 6:
                  this.dropItemRand(Items.WHEAT, 1, par3World, par4, par5, par6);
                  break;
               case 7:
                  this.dropItemRand(Items.PUMPKIN_SEEDS, 1, par3World, par4, par5, par6);
                  break;
               case 8:
                  this.dropItemRand(Items.MELON_SEEDS, 1, par3World, par4, par5, par6);
                  break;
               case 9:
                  this.dropItemRand(Items.CARROT, 1, par3World, par4, par5, par6);
                  break;
               case 10:
                  this.dropItemRand(Items.POTATO, 1, par3World, par4, par5, par6);
                  break;
               case 11:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.DEADBUSH), 1, par3World, par4, par5, par6);
                  break;
               case 12:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.GRAVEL), 1, par3World, par4, par5, par6);
                  break;
               case 13:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.DIRT), 1, par3World, par4, par5, par6);
                  break;
               case 14:
                  this.dropItemRand(Item.getItemFromBlock(Blocks.GRASS), 1, par3World, par4, par5, par6);
            }
         }

         par1ItemStack.damageItem(1, par2EntityPlayer);
         return true;
      }
   }

   public String getMaterialName() {
      return "Unknown";
   }


}

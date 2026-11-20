package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.world.World;

public class GenericDungeon {
   private final com.electrifiedded.orespawn.util.SimpleChest[] RainbowContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MagicApple, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CloudSharkEgg, 0, 4, 10, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 2, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 2, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 3, 10, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EXPERIENCE_BOTTLE, 0, 4, 10, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] WhiteHouseContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyCornDog, 0, 6, 12, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UraniumNugget, 0, 2, 6, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TitaniumNugget, 0, 2, 6, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethyst, 0, 2, 6, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRuby, 0, 2, 6, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CriminalEgg, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EMERALD, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.PORKCHOP, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_PORKCHOP, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_INGOT, 0, 6, 16, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] RubberDuckyContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyDeadStinkBug, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFireFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySunFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySparkFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyGreenFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBlueFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPinkFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRockFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyWoodFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyGreyFish, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubberDuckyEgg, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPeacockFeather, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FEATHER, 0, 6, 16, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] StinkyHouseContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyDeadStinkBug, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.StinkyEgg, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.StinkBugEgg, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COAL, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 3, 10, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] NightmareRookeryContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyDeadStinkBug, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.MyFlowerBlackBlock), 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.MyFlowerScaryBlock), 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.PitchBlackEgg, 0, 4, 10, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AntRobotKit, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpiderRobotKit, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EXPERIENCE_BOTTLE, 0, 4, 10, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] MonsterIslandContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.CreeperRepellent), 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.KrakenRepellent), 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DYE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.PORKCHOP, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BEEF, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.CHICKEN, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FISH, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EXPERIENCE_BOTTLE, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRawBacon, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRawPeacock, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.LOG), 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] GreenhouseContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GreenGoo, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.CreeperRepellent), 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FLOWER_POT, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.SAPLING), 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.LEAVES), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DIRT), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.LOG), 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] CrystalBattleTowerRatContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_PORKCHOP, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BEEF, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_CHICKEN, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_FISH, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBLT, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySalad, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyCornDog, 0, 4, 10, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] CrystalBattleTowerDungeonBeastContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DYE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySquidZooka, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_NUGGET, 0, 5, 15, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] CrystalBattleTowerUrchinContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFairySword, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] CrystalBattleTowerRotatorContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRatSword, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] CrystalBattleTowerVortexContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalCoal), 0, 6, 10, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.CrystalCoal), 0, 6, 10, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyTigersEyeSword, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.MyTigersEyeBlock), 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPoisonSword, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] RobotContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.REDSTONE, 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.REPEATER, 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.MINECART, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FIRE_CHARGE, 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.HOPPER_MINECART, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.RAIL), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DETECTOR_RAIL), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.STICKY_PISTON), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.PISTON), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.REDSTONE_TORCH), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.TNT), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.RAIL), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.LEVER), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AntRobotKit, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpiderRobotKit, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_DOOR, 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.REDSTONE_TORCH), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.WOODEN_BUTTON), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.IRON_BARS), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COMPARATOR, 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.ACTIVATOR_RAIL), 0, 1, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRayGun, 0, 1, 1, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] IncaPyramidContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_SWORD, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_BOOTS, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_LEGGINGS, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_HELMET, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_CHESTPLATE, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.RED_FLOWER), 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_NUGGET, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_INGOT, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EXPERIENCE_BOTTLE, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyCornCob, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceCatcher, 0, 4, 10, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.GOLD_BLOCK), 0, 4, 10, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] DamselContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_PICKAXE, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_SWORD, 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_PORKCHOP, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BEEF, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_CHICKEN, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_FISH, 0, 3, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBLT, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySalad, 0, 4, 10, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyCornDog, 0, 4, 10, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] EnderCastleContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.ENDER_CHEST), 0, 2, 4, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 0, 2, 4, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DRAGON_EGG), 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.MyEnderPearlBlock), 0, 3, 6, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.MyEyeOfEnderBlock), 0, 3, 6, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceCatcher, 0, 4, 10, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_PEARL, 0, 2, 4, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_EYE, 0, 2, 4, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] BouncyContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FISH, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.RED_FLOWER), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_PEARL, 0, 2, 4, 20)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] SpitBugContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FISH, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.InstantGarden, 0, 2, 4, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.InstantShelter, 0, 2, 4, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] GraveContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_EYE, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.RED_FLOWER), 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_PEARL, 0, 6, 16, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] HospitalContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.ENDER_CHEST), 0, 2, 4, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 0, 2, 4, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DRAGON_EGG), 0, 1, 1, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.MyEnderPearlBlock), 0, 3, 6, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_PEARL, 0, 2, 4, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_EYE, 0, 2, 4, 35)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] MiniContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_APPLE, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyCrystalApple, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBacon, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFireFish, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.InstantGarden, 0, 2, 4, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.InstantShelter, 0, 2, 4, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] LeafMonsterContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FLOWER_POT, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.SAPLING), 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FLOWER_POT, 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.SAPLING), 0, 6, 16, 35),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.LEAVES), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DIRT), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.LOG), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPoisonSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] CloudSharkContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FISH, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BONE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.STRING, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.PAPER, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceTreeSeed, 0, 1, 2, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] WaterDragonContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FISH, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimatePickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceCatcher, 0, 4, 10, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.IRON_BLOCK), 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] SquidContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DYE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySquidZooka, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_NUGGET, 0, 5, 15, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] KnightContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.PAPER, 0, 2, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.PLANKS), 0, 4, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_EYE, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_PEARL, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] AlienWTFContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 0, 1, 2, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRuby, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethyst, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyIngotUranium, 0, 1, 2, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyIngotTitanium, 0, 1, 2, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateBow, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyNightmareSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceCatcher, 0, 4, 10, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRayGun, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CageEmpty, 0, 1, 10, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyCornDog, 0, 1, 10, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBacon, 0, 1, 5, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPopcornBag, 0, 2, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFireFish, 0, 2, 8, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] shadowContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GLOWSTONE_DUST, 0, 2, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.NETHER_WART, 0, 4, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BLAZE_ROD, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BLAZE_POWDER, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FIRE_CHARGE, 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DYE, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRuby, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceTreeSeed, 0, 2, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyElevator, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyNightmareSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPoisonSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRatSword, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRubySword, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBigHammer, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySquidZooka, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyIngotTitanium, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyIngotUranium, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateSword, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateBow, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EnderReaperEgg, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.PitchBlackEgg, 0, 2, 8, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] kyuubiContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.REDSTONE, 0, 2, 8, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.QUARTZ, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COAL, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyNightmareSword, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPoisonSword, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.KyuubiEgg, 0, 2, 8, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] blazeContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BLAZE_ROD, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BLAZE_POWDER, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FIRE_CHARGE, 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.FLINT_AND_STEEL, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.SPAWN_EGG, 61, 2, 8, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] beeContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.SUGAR, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_NUGGET, 0, 5, 15, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.PAPER, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFairySword, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyButterCandy, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceCatcher, 0, 4, 10, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BeeEgg, 0, 2, 8, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] mantisContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyMantisClaw, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_NUGGET, 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UraniumNugget, 0, 1, 3, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TitaniumNugget, 0, 1, 3, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MantisEgg, 0, 2, 4, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ROTTEN_FLESH, 0, 6, 16, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND, 0, 1, 3, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] level1ContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EMERALD, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MinersDream, 0, 4, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldBoots, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] level2ContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EXPERIENCE_BOTTLE, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EXPERIENCE_BOTTLE, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CreeperLauncher, 0, 2, 10, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrystalPinkBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFairySword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceBoots, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] level3ContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySquidZooka, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRatSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethyst, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DYE, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeHelmet, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeBody, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeLegs, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TigersEyeBoots, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystBoots, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] level4ContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRuby, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MagicApple, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRayGun, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.CreeperRepellent), 0, 4, 10, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Item.getItemFromBlock(OreSpawn.KrakenRepellent), 0, 4, 10, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceCatcher, 0, 4, 10, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ZooKeeper, 0, 10, 16, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRubyPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRubyShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRubyHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRubyAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyRubySword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyThunderStaff, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubyBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubyLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubyHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubyBoots, 0, 1, 1, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] level5ContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyNightmareSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPoisonSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WitherSkeletonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EnderDragonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SnowGolemEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.IronGolemEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WitherBossEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RedCowEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GoldCowEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EnchantedCowEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MOTHRAEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AloEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CryoEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CamaEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.VeloEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.HydroEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BasilEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.DragonflyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmperorScorpionEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ScorpionEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CaveFisherEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpyroEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BaryonyxEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CockateilEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GammaMetroidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.KyuubiEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AlienEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AttackSquidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WaterDragonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CephadromeEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.KrakenEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LizardEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.DragonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BeeEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TrooperBugEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpitBugEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.StinkBugEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.OstrichEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GazelleEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ChipmunkEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CreepingHorrorEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TerribleTerrorEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CliffRacerEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TriffidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.PitchBlackEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LurkingTerrorEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SmallWormEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MediumWormEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LargeWormEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TRexEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GodzillaEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MantisEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.HerculesEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.VortexEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RatEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.DungeonBeastEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.FairyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WhaleEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SkateEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.IrukandjiEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.Robot1Egg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.Robot2Egg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.Robot3Egg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.Robot4Egg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.Robot5Egg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CriminalEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CoinEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BoyfriendEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EasterBunnyEgg, 0, 1, 4, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MolenoidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SeaMonsterEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SeaViperEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CaterKillerEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LeonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.HammerheadEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubberDuckyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.NastysaurusEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.PointysaurusEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BrutalflyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CricketEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.FrogEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AntRobotKit, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpiderRobotKit, 0, 1, 1, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.JefferyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpiderDriverEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrabEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CassowaryEgg, 0, 1, 4, 15)
   };
   private final com.electrifiedded.orespawn.util.SimpleChest[] chestContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBacon, 0, 6, 12, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyButterCandy, 0, 6, 12, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.EMERALD, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyEmeraldSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmeraldBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyMothScale, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MothScaleBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MothScaleLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MothScaleHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MothScaleBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyLavaEel, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LavaEelBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WitherSkeletonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EnderDragonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SnowGolemEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.IronGolemEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WitherBossEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RedCowEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GoldCowEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EnchantedCowEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MOTHRAEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AloEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CryoEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CamaEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.VeloEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.HydroEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BasilEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.DragonflyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.EmperorScorpionEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ScorpionEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CaveFisherEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpyroEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BaryonyxEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CockateilEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GammaMetroidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.KyuubiEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AlienEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AttackSquidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.WaterDragonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CephadromeEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.KrakenEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LizardEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.DragonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BeeEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TrooperBugEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpitBugEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.StinkBugEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.OstrichEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.GazelleEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ChipmunkEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CreepingHorrorEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TerribleTerrorEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CliffRacerEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TriffidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.PitchBlackEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LurkingTerrorEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SmallWormEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MediumWormEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LargeWormEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CassowaryEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MolenoidEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SeaMonsterEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SeaViperEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CaterKillerEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.LeonEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.HammerheadEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.RubberDuckyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.NastysaurusEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.PointysaurusEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.BrutalflyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CricketEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.FrogEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.JefferyEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.SpiderDriverEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CrabEgg, 0, 1, 4, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CageEmpty, 0, 3, 10, 20)
   };
   private int[] king = new int[]{
      -1,
      -1,
      24,
      3,
      -1,
      24,
      5,
      -1,
      17,
      12,
      -1,
      16,
      15,
      -1,
      15,
      14,
      -1,
      15,
      6,
      3,
      5,
      -1,
      14,
      6,
      4,
      3,
      -1,
      14,
      5,
      -1,
      14,
      5,
      -1,
      12,
      9,
      -1,
      11,
      11,
      -1,
      8,
      17,
      -1,
      5,
      23,
      -1,
      3,
      27,
      -1,
      2,
      29,
      -1,
      1,
      31,
      -1,
      0,
      33,
      -1,
      13,
      6,
      -1,
      12,
      9,
      -1,
      11,
      3,
      1,
      2,
      1,
      4,
      -1,
      10,
      3,
      2,
      2,
      3,
      2,
      -1,
      10,
      2,
      4,
      2,
      3,
      2,
      -1,
      9,
      2,
      5,
      2,
      4,
      6,
      -1,
      9,
      2,
      5,
      2,
      6,
      4,
      -1,
      8,
      2,
      6,
      1,
      -1,
      8,
      2,
      5,
      2,
      -1,
      8,
      2,
      5,
      2,
      -1,
      8,
      2,
      5,
      2,
      -1,
      15,
      2,
      -1,
      -1,
      -1
   };
   private int[] queen = new int[]{
      -1,
      -1,
      24,
      3,
      -1,
      24,
      5,
      -1,
      17,
      12,
      -1,
      16,
      15,
      -1,
      15,
      14,
      -1,
      15,
      6,
      3,
      5,
      -1,
      14,
      6,
      4,
      3,
      -1,
      14,
      5,
      -1,
      14,
      5,
      -1,
      12,
      9,
      -1,
      11,
      11,
      -1,
      8,
      17,
      -1,
      5,
      23,
      -1,
      3,
      27,
      -1,
      2,
      29,
      -1,
      1,
      31,
      -1,
      0,
      33,
      -1,
      13,
      6,
      -1,
      12,
      9,
      -1,
      11,
      3,
      1,
      2,
      1,
      4,
      -1,
      10,
      3,
      2,
      2,
      3,
      2,
      -1,
      10,
      2,
      4,
      2,
      3,
      2,
      -1,
      9,
      2,
      5,
      2,
      4,
      6,
      -1,
      9,
      2,
      5,
      2,
      6,
      4,
      -1,
      8,
      2,
      6,
      1,
      -1,
      8,
      2,
      5,
      2,
      -1,
      8,
      2,
      5,
      2,
      -1,
      8,
      2,
      5,
      2,
      -1,
      15,
      2,
      -1,
      -1,
      -1
   };
   private int[] blkcolors = new int[]{14, 1, 4, 5, 3, 11, 10, 6};

   private void setThisBlock(World world, int cposx, int cposy, int cposz) {
      if (world.rand.nextInt(2) == 1) {
         this.FastSetBlock(world, cposx, cposy, cposz, Blocks.MOSSY_COBBLESTONE);
      } else {
         this.FastSetBlock(world, cposx, cposy, cposz, Blocks.COBBLESTONE);
      }
   }

   private TileEntityChest getChestTileEntity(World world, int cposx, int cposy, int cposz) {
      TileEntityChest chest = null;
      TileEntity t = null;
      t = world.getTileEntity(new BlockPos(cposx, cposy, cposz));
      return t != null && t instanceof TileEntityChest ? (TileEntityChest)t : null;
   }

   private TileEntityMobSpawner getSpawnerTileEntity(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner chest = null;
      TileEntity t = null;
      t = world.getTileEntity(new BlockPos(cposx, cposy, cposz));
      return t != null && t instanceof TileEntityMobSpawner ? (TileEntityMobSpawner)t : null;
   }

   public void makeDungeon(World world, int cposx, int cposy, int cposz) {
      int width = 12;
      int height = 6;

      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
            }
         }
      }

      for (int var12 = 0; var12 < width; var12++) {
         int j = 0;

         for (int k = 0; k < width; k++) {
            this.FastSetBlock(world, cposx + var12, cposy + j, cposz + k, Blocks.MOSSY_COBBLESTONE);
         }
      }

      for (int var13 = 0; var13 < width; var13++) {
         int j = height - 1;

         for (int k = 0; k < width; k++) {
            this.setThisBlock(world, cposx + var13, cposy + j, cposz + k);
         }
      }

      for (int var14 = 0; var14 < width; var14++) {
         for (int j = 0; j < height; j++) {
            int k = 0;
            this.setThisBlock(world, cposx + var14, cposy + j, cposz + k);
            k = width - 1;
            this.setThisBlock(world, cposx + var14, cposy + j, cposz + k);
         }
      }

      for (int k = 0; k < width; k++) {
         for (int j = 0; j < height; j++) {
            int var15 = 0;
            this.setThisBlock(world, cposx + var15, cposy + j, cposz + k);
            var15 = width - 1;
            this.setThisBlock(world, cposx + var15, cposy + j, cposz + k);
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      TileEntityMobSpawner tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 1, cposz + width / 2);
      if (tileentitymobspawner != null) {
         int t = world.rand.nextInt(12);
         if (t == 0) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Scorpion");
         }

         if (t == 1) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Alien");
         }

         if (t == 2) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cryolophosaurus");
         }

         if (t == 3) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "WTF?");
         }

         if (t == 4) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Kyuubi");
         }

         if (t == 5) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Bee");
         }

         if (t == 6) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
         }

         if (t == 7) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
         }

         if (t == 8) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
         }

         if (t == 9) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
         }

         if (t == 10) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
         }

         if (t == 11) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
         }
      }

      TileEntityChest chest = null;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, this.chestContentsList, chest, 5 + world.rand.nextInt(7));
      }
   }

   public void FastSetBlock(World world, int ix, int iy, int iz, Block id) {
      OreSpawn.setBlockFast(world, ix, iy, iz, id, 0, 2);
   }

   public void makeEnormousCastle(World world, int cposx, int cposy, int cposz) {
      int width = 28;
      int height = 16;
      int platformwidth = 11;
      int level = 0;
      if (!world.isRemote) {
         level = 1 + world.rand.nextInt(6);
         if (level <= 3 && world.rand.nextInt(3) != 1) {
            level += 3;
         }

         for (int i = -20; i < width + 4; i++) {
            for (int j = 1; j < height + 10; j++) {
               for (int k = -4; k < width + 4; k++) {
                  this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
               }
            }
         }

         for (int var15 = 0; var15 < width; var15++) {
            int j = 0;

            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + var15, cposy + j, cposz + k, Blocks.STONE);
            }
         }

         for (int var16 = 0; var16 < width; var16++) {
            int j = height;

            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + var16, cposy + j, cposz + k, Blocks.BEDROCK);
            }
         }

         for (int var17 = 0; var17 < width; var17++) {
            for (int j = 1; j < height; j++) {
               int k = 0;
               this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.IRON_BARS);
               k = width - 1;
               this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
         }

         for (int k = 0; k < width; k++) {
            for (int j = 1; j < height; j++) {
               int var18 = 0;
               this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.IRON_BARS);
               var18 = width - 1;
               this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + 1), (OreSpawn.ExtremeTorch).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width - 2), (OreSpawn.ExtremeTorch).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + 1), (OreSpawn.ExtremeTorch).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width - 2), (OreSpawn.ExtremeTorch).getDefaultState(), 3);

         for (int var20 = -4; var20 < width + 4; var20++) {
            for (int var46 = -4; var46 < width + 4; var46++) {
               if (var20 < 0 || var46 < 0 || var20 >= width || var46 >= width) {
                  this.FastSetBlock(world, cposx + var20, cposy, cposz + var46, Blocks.STONE);
               }

               if (var20 == -4 || var46 == -4 || var20 == width + 3 || var46 == width + 3) {
                  this.FastSetBlock(world, cposx + var20, cposy + 1, cposz + var46, Blocks.NETHER_BRICK_FENCE);
               }
            }
         }

         TileEntityMobSpawner tileentitymobspawner = null;

         for (int j = 0; j < 4; j++) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + 1 + j, cposz - 3), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
            }

            OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + 1 + j, cposz + width + 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
            }

            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + 2, cposy + 1 + j, cposz - 3), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
            }

            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + 2, cposy + 1 + j, cposz + width + 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 4, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         }

         this.buildLevel(world, cposx + 1, cposy + height, cposz + 1, width - 2, 10, 4, "Cloud Shark", 1, -1, 5, 1, level);
         int var31 = height + 10;
         if (level >= 2) {
            this.buildLevel(world, cposx + 1, cposy + var31, cposz + 1, width - 2, 10, 4, "Lurking Terror", 0, 0, 4, 2, level);
         }

         var31 += 10;
         if (level >= 3) {
            this.buildLevel(world, cposx + 2, cposy + var31, cposz + 2, width - 4, 9, 4, "Rotator", 1, 1, 4, 3, level);
         }

         var31 += 9;
         if (level >= 4) {
            this.buildLevel(world, cposx + 2, cposy + var31, cposz + 2, width - 4, 9, 3, "Bee", 0, 0, 4, 4, level);
         }

         var31 += 9;
         if (level >= 5) {
            this.buildLevel(world, cposx + 3, cposy + var31, cposz + 3, width - 6, 8, 3, "Mantis", 1, 1, 4, 5, level);
         }

         var31 += 8;
         if (level >= 6) {
            this.buildLevel(world, cposx + 3, cposy + var31, cposz + 3, width - 6, 16, 3, "Mothra", 0, 0, 3, 6, level);
         }

         var31 += 16;

         for (int var21 = 0; var21 < platformwidth; var21++) {
            var31 = height;

            for (int var47 = -(platformwidth / 2); var47 <= platformwidth / 2; var47++) {
               this.FastSetBlock(world, cposx + var21 - 20, cposy + var31, cposz + var47 + width / 2, Blocks.QUARTZ_BLOCK);
               if ((var21 == 0 || var21 == platformwidth - 1 || var47 == -(platformwidth / 2) || var47 == platformwidth / 2)
                  && (var21 != 0 || var47 < -1 || var47 > 1)) {
                  this.FastSetBlock(world, cposx + var21 - 20, cposy + var31 + 1, cposz + var47 + width / 2, Blocks.NETHER_BRICK_FENCE);
               }
            }
         }

         for (int var22 = -10; var22 <= -3; var22++) {
            var31 = height;

            for (int var48 = -2; var48 < 3; var48++) {
               if (var22 != -3 && var22 != -10) {
                  this.FastSetBlock(world, cposx + var22, cposy + var31, cposz + var48 + width / 2, Blocks.QUARTZ_BLOCK);
                  if (var48 == -2 || var48 == 2) {
                     this.FastSetBlock(world, cposx + var22, cposy + var31 + 1, cposz + var48 + width / 2, Blocks.NETHER_BRICK_FENCE);
                  }
               } else if (var48 != -2 && var48 != 2) {
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 1, cposz + var48 + width / 2, Blocks.AIR);
               } else {
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 1, cposz + var48 + width / 2, Blocks.NETHERRACK);
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 2, cposz + var48 + width / 2, Blocks.NETHERRACK);
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 3, cposz + var48 + width / 2, Blocks.FIRE);
               }
            }
         }

         int var23 = -21;

         for (int var39 = height; var39 >= 0; var39--) {
            for (int var49 = -2; var49 < 3; var49++) {
               for (int t = 0; t < 6; t++) {
                  this.FastSetBlock(world, cposx + var23, cposy + var39 + t + 1, cposz + var49 + width / 2, Blocks.AIR);
               }

               if (var39 == 0) {
                  if (var49 != -2 && var49 != 2) {
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 1, cposz + var49 + width / 2, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 1, cposz + var49 + width / 2, Blocks.NETHERRACK);
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 2, cposz + var49 + width / 2, Blocks.NETHERRACK);
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 3, cposz + var49 + width / 2, Blocks.FIRE);
                  }
               } else {
                  this.FastSetBlock(world, cposx + var23, cposy + var39, cposz + var49 + width / 2, Blocks.QUARTZ_BLOCK);
                  if (var49 == -2 || var49 == 2) {
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 1, cposz + var49 + width / 2, Blocks.NETHER_BRICK_FENCE);
                  }
               }
            }

            var23--;
         }

         if (level >= 6) {
            int span = width * 3;

            for (int tries = 0; tries < 100; tries++) {
               int var40 = -1;
               var23 = world.rand.nextInt(span);
               int var50 = world.rand.nextInt(span);
               if (var23 < span / 4 || var23 > span * 3 / 4 || var50 < span / 4 || var50 > span * 3 / 4) {
                  var23 -= span / 2;
                  var50 -= span / 2;
                  OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var23 + width / 2, cposy + var40, cposz + var50 + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
                  tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var23 + width / 2, cposy + var40, cposz + var50 + width / 2);
                  if (tileentitymobspawner != null) {
                     com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
                  }
               }
            }
         }
      }
   }

   public void buildLevel(
      World world, int cposx, int cposy, int cposz, int width, int height, int pw, String critter, int stepside, int stepoff, int holelen, int decor, int level
   ) {
      for (int i = -pw; i < width + pw; i++) {
         for (int j = 1; j < height; j++) {
            for (int k = -pw; k < width + pw; k++) {
               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
            }
         }
      }

      for (int var19 = 0; var19 < width; var19++) {
         int j = 0;

         for (int k = 0; k < width; k++) {
            this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, Blocks.BEDROCK);
         }
      }

      for (int var20 = 0; var20 < width; var20++) {
         int j = height;

         for (int k = 0; k < width; k++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k, Blocks.BEDROCK);
         }
      }

      for (int var21 = 0; var21 < width; var21++) {
         for (int j = 1; j < height; j++) {
            int k = 0;
            this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.BEDROCK);
            k = width - 1;
            this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.BEDROCK);
         }
      }

      for (int k = 0; k < width; k++) {
         for (int j = 1; j < height; j++) {
            Block blk = Blocks.BEDROCK;
            if (k == 0 || k == width - 1) {
               blk = Blocks.GOLD_BLOCK;
            }

            int var22 = 0;
            this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
            var22 = width - 1;
            this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
         }
      }

      for (int var24 = -pw; var24 < width + pw; var24++) {
         for (int var40 = -pw; var40 < width + pw; var40++) {
            if (var24 < 0 || var40 < 0 || var24 >= width || var40 >= width) {
               this.FastSetBlock(world, cposx + var24, cposy, cposz + var40, Blocks.STONE);
            }

            if (var24 == -pw || var40 == -pw || var24 == width + (pw - 1) || var40 == width + (pw - 1)) {
               this.FastSetBlock(world, cposx + var24, cposy + 1, cposz + var40, Blocks.NETHER_BRICK_FENCE);
            }
         }
      }

      int var25 = -(height / 2);
      var25 += width / 2;

      for (int j = 1; j < height; j++) {
         if (stepside != 0) {
            int var41 = -1;
            this.FastSetBlock(world, cposx + var25, cposy + j, cposz + var41, Blocks.STONE);
         } else {
            this.FastSetBlock(world, cposx + var25, cposy + j, cposz + width, Blocks.STONE);
         }

         var25++;
      }

      if (stepoff >= 0) {
         int var43;
         if (stepside == 0) {
            int var42 = -1;
            var43 = var42 - stepoff;
         } else {
            var43 = width + stepoff;
         }

         var25 = width / 2;
         int var33 = 0;

         for (int l = 0; l < holelen; l++) {
            this.FastSetBlock(world, cposx + var25 + l, cposy + var33, cposz + var43, Blocks.AIR);
         }
      }

      TileEntityMobSpawner tileentitymobspawner = null;

      for (int var34 = 0; var34 < 4; var34++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + (pw - 2), cposy + var34 + 1, cposz + width + (pw - 2)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz + width + (pw - 2));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }
      }

      this.addLevelDecorations(world, cposx, cposy, cposz, width, height, decor, level);
   }

   public void addLevelDecorations(World world, int cposx, int cposy, int cposz, int width, int height, int decor, int difficulty) {
      TileEntityMobSpawner tileentitymobspawner = null;
      int reward = 1;
      String critter = "Alosaurus";
      if (decor == 6) {
         this.FastSetBlock(world, cposx, cposy + height, cposz, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx, cposy + height + 1, cposz, Blocks.FIRE);
         this.FastSetBlock(world, cposx, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx, cposy + height + 1, cposz + width - 1, Blocks.FIRE);
         this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz, Blocks.FIRE);
         this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz + width - 1, Blocks.FIRE);
         this.FastSetBlock(world, cposx + width / 2, cposy + height, cposz + width / 2, Blocks.AIR);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < 5; j++) {
               for (int k = 1; k < width - 1; k++) {
                  this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.DIRT);
               }
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 4, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
         }

         for (int j = 0; j < 10; j++) {
            this.FastSetBlock(world, cposx + 1, cposy + j, cposz + 1, Blocks.AIR);
         }

         this.fill_chests(world, cposx, cposy + 4, cposz, width, height, decor, reward);
      }

      if (decor == 5) {
         if (difficulty == 5) {
            critter = "Alosaurus";
            reward = 1;
         }

         if (difficulty == 6) {
            critter = "T. Rex";
            reward = 2;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
         this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 4) {
         if (difficulty == 4) {
            critter = "Alosaurus";
            reward = 1;
         }

         if (difficulty == 5) {
            critter = "T. Rex";
            reward = 2;
         }

         if (difficulty == 6) {
            critter = "Basilisk";
            reward = 3;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
         this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 3) {
         if (difficulty == 3) {
            critter = "Alosaurus";
            reward = 1;
         }

         if (difficulty == 4) {
            critter = "T. Rex";
            reward = 2;
         }

         if (difficulty == 5) {
            critter = "Basilisk";
            reward = 3;
         }

         if (difficulty == 6) {
            critter = "Hercules Beetle";
            reward = 4;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
         this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 2) {
         if (difficulty == 2) {
            critter = "Alosaurus";
            reward = 1;
         }

         if (difficulty == 3) {
            critter = "T. Rex";
            reward = 2;
         }

         if (difficulty == 4) {
            critter = "Basilisk";
            reward = 3;
         }

         if (difficulty == 5) {
            critter = "Hercules Beetle";
            reward = 4;
         }

         if (difficulty == 6) {
            critter = "Jumpy Bug";
            reward = 5;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
         this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 1) {
         if (difficulty == 1) {
            critter = "Alosaurus";
         }

         if (difficulty == 2) {
            critter = "T. Rex";
         }

         if (difficulty == 3) {
            critter = "Basilisk";
         }

         if (difficulty == 4) {
            critter = "Hercules Beetle";
         }

         if (difficulty == 5) {
            critter = "Jumpy Bug";
         }

         if (difficulty == 6) {
            critter = "Hammerhead";
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 - 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 + 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, difficulty);
      }
   }

   private void fill_chests(World world, int cposx, int cposy, int cposz, int width, int height, int decor, int reward) {
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.level1ContentsList;
      if (reward == 2) {
         chestContents = this.level2ContentsList;
      }

      if (reward == 3) {
         chestContents = this.level3ContentsList;
      }

      if (reward == 4) {
         chestContents = this.level4ContentsList;
      }

      if (reward == 5) {
         chestContents = this.level5ContentsList;
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width / 2)).getBlock().getStateFromMeta(5), 3);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + 1, cposz + width / 2);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.ThePrinceEgg, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width / 2)).getBlock().getStateFromMeta(4), 3);
      chest = this.getChestTileEntity(world, cposx + width - 2, cposy + 1, cposz + width / 2);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.RoyalHelmet, 1, 0));
            chest.setInventorySlotContents(2, new ItemStack(OreSpawn.RoyalBody, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1)).getBlock().getStateFromMeta(3), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + 1);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.RoyalLegs, 1, 0));
            chest.setInventorySlotContents(2, new ItemStack(OreSpawn.RoyalBoots, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width - 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width - 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width - 2)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + width - 2);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.MyRoyal, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }
   }

   public void makeRotatorStation(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 4, cposz), (OreSpawn.CrystalStone).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 5, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 5, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 6, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 6, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 7, cposz), (OreSpawn.CrystalStone).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 8, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 8, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx, cposy + 8, cposz)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx, cposy + 8, cposz);
      if (chest != null) {
         chest.setInventorySlotContents(1, new ItemStack(OreSpawn.RotatorEgg, 1 + world.rand.nextInt(5), 0));
         chest.setInventorySlotContents(2, new ItemStack(OreSpawn.CrystalCoal, 4 + world.rand.nextInt(16), 0));
         chest.setInventorySlotContents(3, new ItemStack(OreSpawn.CrystalCoal, 4 + world.rand.nextInt(16), 0));
      }
   }

   public void makeBeeHive(World world, int cposx, int cposy, int cposz) {
      int width = 10;
      int height = 30;
      if (!world.isRemote) {
         for (int i = 0; i < width; i++) {
            for (int j = 0; j < 5; j++) {
               for (int k = 0; k < width; k++) {
                  this.FastSetBlock(world, cposx + i, cposy - j, cposz + k, Blocks.AIR);
               }
            }
         }

         for (int var12 = 0; var12 < width; var12++) {
            int j = height;

            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + var12, cposy - j, cposz + k, Blocks.COAL_ORE);
            }
         }

         Block blk = Blocks.COAL_ORE;

         for (int var13 = 0; var13 < width; var13++) {
            for (int k = 0; k < width; k++) {
               for (int j = 1; j < height; j++) {
                  if (k != 0 && var13 != 0 && k != width - 1 && var13 != width - 1) {
                     this.FastSetBlock(world, cposx + var13, cposy - j, cposz + k, Blocks.AIR);
                  } else {
                     blk = Blocks.COAL_ORE;
                     if ((j & 1) == 1) {
                        blk = Blocks.GOLD_ORE;
                     }

                     this.FastSetBlock(world, cposx + var13, cposy - j, cposz + k, blk);
                  }
               }
            }
         }

         TileEntityMobSpawner tileentitymobspawner = null;

         for (int jx = 0; jx < 4; jx++) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - 2 - jx * (height / 4), cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy - 2 - jx * (height / 4), cposz + width / 2);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Bee");
            }
         }

         this.fill_beehive_chests(world, cposx, cposy, cposz, width, height);
      }
   }

   private void fill_beehive_chests(World world, int cposx, int cposy, int cposz, int width, int height) {
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.beeContentsList;

      for (int j = 2; j < height - 1; j += 2) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy - j, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy - j, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + 1, cposy - j, cposz + width / 2)).getBlock().getStateFromMeta(5), 3);
         chest = this.getChestTileEntity(world, cposx + 1, cposy - j, cposz + width / 2);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy - j, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy - j, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width - 2, cposy - j, cposz + width / 2)).getBlock().getStateFromMeta(4), 3);
         chest = this.getChestTileEntity(world, cposx + width - 2, cposy - j, cposz + width / 2);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - j, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - j, cposz + 1), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - j, cposz + 1)).getBlock().getStateFromMeta(3), 3);
         chest = this.getChestTileEntity(world, cposx + width / 2, cposy - j, cposz + 1);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - j, cposz + width - 2), (Blocks.CHEST).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - j, cposz + width - 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy - j, cposz + width - 2)).getBlock().getStateFromMeta(2), 3);
         chest = this.getChestTileEntity(world, cposx + width / 2, cposy - j, cposz + width - 2);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 1 + world.rand.nextInt(5));
         }
      }
   }

   public void makeHauntedHouse(World world, int cposx, int cposy, int cposz) {
      int deltax = 0;
      int deltaz = 0;
      int bid = 0;
      int dirx = 0;
      int dirz = 0;
      int stuffdir = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      int length = 3;
      int width = 3;
      int height = 3;
      int var22 = 1;
      int var28 = 2;
      int x = cposx;
      int z = cposz;
      int y = cposy;
      if (!world.isRemote) {
         for (int i = -width; i <= width; i++) {
            for (int j = -length; j <= length; j++) {
               for (int k = 0; k <= height + 1; k++) {
                  if (k == height + 1) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.PLANKS).getDefaultState(), 3);
                  } else if (k == 0) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.COBBLESTONE).getDefaultState(), 3);
                  } else if (i != width && j != length && i != -width && j != -length) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState(), 3);
                  } else if (k == height) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.GLASS).getDefaultState(), 3);
                  } else if ((k == 1 || k == 2) && i == var22 * width && j == deltaz * length) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState(), 3);
                  } else {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.PLANKS).getDefaultState(), 3);
                  }
               }
            }
         }

         int var23 = 2;
         int kx = 1;
         int j = length - 1;
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), (Blocks.FURNACE).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), OreSpawn.getBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22)).getBlock().getStateFromMeta(var28), 3);
         var23 = 1;
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), (Blocks.CRAFTING_TABLE).getDefaultState(), 3);
         var23 = 0;
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), (Blocks.CHEST).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), OreSpawn.getBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22)).getBlock().getStateFromMeta(var28), 3);
         TileEntityChest chest = this.getChestTileEntity(world, x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22);
         if (chest != null) {
            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(0, new ItemStack(Items.COMPASS));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(1, new ItemStack(Items.MAP));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(2, new ItemStack(Items.COOKED_PORKCHOP, 8));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(3, new ItemStack(Item.getItemFromBlock(Blocks.TORCH), 32));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(4, new ItemStack(Items.COAL, 16));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(5, new ItemStack(Items.BED));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(6, new ItemStack(Items.BED));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(7, new ItemStack(Items.OAK_DOOR));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(8, new ItemStack(Items.IRON_PICKAXE));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(9, new ItemStack(Items.IRON_SWORD));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(10, new ItemStack(Items.IRON_AXE));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(11, new ItemStack(Items.BUCKET));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(12, new ItemStack(OreSpawn.MyOreSaltBlock, 4));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(13, new ItemStack(Blocks.CHEST));
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 1, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost Pumpkin Skelly");
         }
      }
   }

   public void makeMantisHive(World world, int cposx, int cposy, int cposz) {
      int width = 13;
      TileEntityMobSpawner tileentitymobspawner = null;
      if (!world.isRemote) {
         for (int i = 0; i < width; i++) {
            for (int j = 0; j < 20; j++) {
               for (int k = 0; k < width; k++) {
                  this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
               }
            }
         }

         int yoff = 0;
         int zoff = 0;

         int xoff;
         for (xoff = 0; width > 0; width -= 2) {
            for (int var14 = 0; var14 < width; var14++) {
               for (int k = 0; k < width; k++) {
                  if (k != 0 && k != width - 1 && var14 != 0 && var14 != width - 1) {
                     this.FastSetBlock(world, cposx + var14 + xoff, cposy - yoff, cposz + k + zoff, Blocks.AIR);
                  } else {
                     Block blk = Blocks.GOLD_ORE;
                     if ((yoff & 1) != 0) {
                        blk = Blocks.EMERALD_ORE;
                     }

                     this.FastSetBlock(world, cposx + var14 + xoff, cposy - yoff, cposz + k + zoff, blk);
                  }
               }
            }

            if (width <= 11 && width >= 7) {
               this.fill_mantishive_chests(world, cposx + xoff, cposy - yoff, cposz + zoff, width, 0);
            }

            xoff++;
            zoff++;
            yoff++;
         }

         xoff--;
         zoff--;
         yoff--;

         for (int j = 4; j < 7; j++) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + xoff, cposy + j - yoff, cposz + yoff), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + xoff, cposy + j - yoff, cposz + yoff);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Mantis");
            }
         }
      }
   }

   private void fill_mantishive_chests(World world, int cposx, int cposy, int cposz, int width, int height) {
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.mantisContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + height, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + height, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + 1, cposy + height, cposz + width / 2)).getBlock().getStateFromMeta(5), 3);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + height, cposz + width / 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + height, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + height, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + height, cposz + width / 2)).getBlock().getStateFromMeta(4), 3);
      chest = this.getChestTileEntity(world, cposx + width - 2, cposy + height, cposz + width / 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + 1), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + 1)).getBlock().getStateFromMeta(3), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + width - 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + width - 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + width - 2)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + width - 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }
   }

   public void makeKyuubiDungeon(World world, int cposx, int cposy, int cposz) {
      int width = 5;
      int height = 5;
      int depth = 20;
      int length = 12;
      int rwidth = 30;
      int rheight = 18;
      int rlength = 20;
      if (!world.isRemote) {
         for (int i = 0; i < width; i++) {
            for (int j = 0; j < 5; j++) {
               for (int k = 0; k < width; k++) {
                  this.FastSetBlock(world, cposx + i, cposy - j, cposz + k, Blocks.AIR);
               }
            }
         }

         int j = height;

         for (int var19 = 0; var19 < width; var19++) {
            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, Blocks.SANDSTONE);
            }
         }

         this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2, Blocks.AIR);
         Block blk = Blocks.SANDSTONE;

         for (int var20 = 0; var20 < width; var20++) {
            for (int k = 0; k < width; k++) {
               for (int var27 = 0; var27 < height; var27++) {
                  if (k != 0 && k != width - 1 && var20 != 0 && var20 != width - 1) {
                     this.FastSetBlock(world, cposx + var20, cposy + var27, cposz + k, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, cposx + var20, cposy + var27, cposz + k, blk);
                  }
               }
            }
         }

         blk = Blocks.STONE;

         for (int var21 = 0; var21 < width; var21++) {
            for (int k = 0; k < width; k++) {
               for (int var28 = -1; var28 > -depth; var28--) {
                  if (k != 0 && k != width - 1 && var21 != 0 && var21 != width - 1) {
                     this.FastSetBlock(world, cposx + var21, cposy + var28, cposz + k, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, cposx + var21, cposy + var28, cposz + k, blk);
                  }
               }
            }
         }

         for (int var22 = 1; var22 < width - 1; var22++) {
            for (int k = 1; k < width - 1; k++) {
               for (int var29 = -depth; var29 > -(depth + 2); var29--) {
                  this.FastSetBlock(world, cposx + var22, cposy + var29, cposz + k, Blocks.WATER);
               }
            }
         }

         for (int var23 = 1; var23 < width - 1; var23++) {
            for (int k = 1; k < width - 1; k++) {
               j = -(depth + 2);
               this.FastSetBlock(world, cposx + var23, cposy + j, cposz + k, Blocks.STONE);
            }
         }

         int x = cposx + width + length - 2;
         int z = cposz - rwidth / 2;
         int y = cposy - depth;
         blk = Blocks.NETHERRACK;

         for (int var24 = 0; var24 < rlength; var24++) {
            for (int k = 0; k < rwidth; k++) {
               for (int var31 = 0; var31 < rheight; var31++) {
                  if (k != 0 && k != rwidth - 1 && var31 != 0 && var31 != rheight - 1 && var24 != 0 && var24 != rlength - 1) {
                     this.FastSetBlock(world, x + var24, y + var31, z + k, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, x + var24, y + var31, z + k, blk);
                  }
               }
            }
         }

         x = cposx + width - 1;
         z = cposz;
         y = cposy - depth;

         for (int var25 = 0; var25 < length; var25++) {
            for (int k = 0; k < width; k++) {
               for (int var32 = 0; var32 < width; var32++) {
                  if (k != 0 && k != width - 1 && var32 != 0 && var32 != width - 1) {
                     this.FastSetBlock(world, x + var25, y + var32, z + k, Blocks.AIR);
                  } else {
                     blk = Blocks.STONE;
                     if (var32 > 0 && var32 < width - 1) {
                        blk = Blocks.LAVA;
                     }

                     this.FastSetBlock(world, x + var25, y + var32, z + k, blk);
                  }
               }
            }
         }

         x = cposx + width + length - 2;
         z = cposz - rwidth / 2;
         y = cposy - depth;
         this.addlavasquare(world, x + 2, ++y, z + 2);
         this.addlavasquare(world, x + 4, y, z + 6);
         this.addlavasquare(world, x + 12, y, z + 10);
         this.addlavasquare(world, x + 6, y, z + 15);
         this.addlavasquare(world, x + 3, y, z + 22);
         this.addkyuubi(world, x + rlength / 4, y, z + rwidth * 3 / 4 - 3);
         this.addblaze(world, x + rlength * 2 / 3 - 3, y, z + rwidth / 4 - 2);
         this.FastSetBlock(world, x + 7, y, z + 1, Blocks.FIRE);
         this.FastSetBlock(world, x + 5, y, z + 9, Blocks.FIRE);
         this.FastSetBlock(world, x + 2, y, z + 12, Blocks.FIRE);
         this.FastSetBlock(world, x + 16, y, z + 18, Blocks.FIRE);
         this.FastSetBlock(world, x + 2, y, z + 27, Blocks.FIRE);
         this.FastSetBlock(world, x + 18, y, z + 28, Blocks.FIRE);
      }
   }

   private void addlavasquare(World world, int x, int y, int z) {
      this.FastSetBlock(world, x - 1, y, z, Blocks.NETHERRACK);
      this.FastSetBlock(world, x + 1, y, z, Blocks.NETHERRACK);
      this.FastSetBlock(world, x, y, z + 1, Blocks.NETHERRACK);
      this.FastSetBlock(world, x, y, z - 1, Blocks.NETHERRACK);
      this.FastSetBlock(world, x, y, z, Blocks.LAVA);
   }

   private void addkyuubi(World world, int x, int y, int z) {
      int width = 9;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      TileEntityMobSpawner tileentitymobspawner = null;
      chestContents = this.kyuubiContentsList;

      for (int i = 0; i < width; i++) {
         for (int k = 0; k < width; k++) {
            if (k != 0 && k != width - 1 && i != 0 && i != width - 1) {
               this.FastSetBlock(world, x + i, y, z + k, Blocks.LAVA);
            } else {
               this.FastSetBlock(world, x + i, y, z + k, Blocks.NETHER_BRICK);
            }
         }
      }

      int var12 = 7;

      for (int var13 = 0; var13 < var12; var13++) {
         for (int kx = 0; kx < var12; kx++) {
            if (kx != 0 && kx != var12 - 1 && var13 != 0 && var13 != var12 - 1) {
               this.FastSetBlock(world, x + var13 + 1, y + 1, z + kx + 1, Blocks.LAVA);
            } else {
               this.FastSetBlock(world, x + var13 + 1, y + 1, z + kx + 1, Blocks.NETHER_BRICK);
            }
         }
      }

      for (int j = 0; j < 3; j++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(x + 4, y + j + 2, z + 4), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, x + 4, y + j + 2, z + 4);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Kyuubi");
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(x + 4, y + 5, z + 4), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(x + 4, y + 5, z + 4), OreSpawn.getBlockStateFast(world, new BlockPos(x + 4, y + 5, z + 4)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, x + 4, y + 5, z + 4);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 7 + world.rand.nextInt(7));
      }
   }

   private void addblaze(World world, int x, int y, int z) {
      int width = 7;
      int height = 4;
      int xx = x;
      int yy = y;
      int zz = z;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      TileEntityMobSpawner tileentitymobspawner = null;
      chestContents = this.blazeContentsList;

      for (int i = 0; i < width; i++) {
         for (int k = 0; k < width; k++) {
            for (int j = 0; j < height; j++) {
               this.FastSetBlock(world, xx + i, yy + j, zz + k, Blocks.OBSIDIAN);
            }
         }
      }

      xx++;
      yy += height;
      zz++;
      int var16 = 5;
      int var19 = 1;

      for (int var22 = 0; var22 < var16; var22++) {
         for (int k = 0; k < var16; k++) {
            for (int j = 0; j < var19; j++) {
               this.FastSetBlock(world, xx + var22, yy + j, zz + k, Blocks.OBSIDIAN);
            }
         }
      }

      xx++;
      yy += var19;
      zz++;
      var16 = 3;
      var19 = 6;

      for (int var23 = 0; var23 < var16; var23++) {
         for (int k = 0; k < var16; k++) {
            for (int j = 0; j < var19; j++) {
               this.FastSetBlock(world, xx + var23, yy + j, zz + k, Blocks.OBSIDIAN);
            }
         }
      }

      xx++;
      yy += var19;
      zz++;
      var16 = 1;
      var19 = 5;

      for (int var24 = 0; var24 < var16; var24++) {
         for (int k = 0; k < var16; k++) {
            for (int j = 0; j < var19; j++) {
               this.FastSetBlock(world, xx + var24, yy + j, zz + k, Blocks.OBSIDIAN);
            }
         }
      }

      for (int j = 0; j < 2; j++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(xx - 1, yy + var19 + j - 3, zz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, xx - 1, yy + var19 + j - 3, zz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Blaze");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(xx + 1, yy + var19 + j - 3, zz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, xx + 1, yy + var19 + j - 3, zz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Blaze");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(xx, yy + var19 + j - 3, zz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, xx, yy + var19 + j - 3, zz - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Blaze");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(xx, yy + var19 + j - 3, zz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, xx, yy + var19 + j - 3, zz + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Blaze");
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(x, y + 4, z + 3), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(x, y + 4, z + 3), OreSpawn.getBlockStateFast(world, new BlockPos(x, y + 4, z + 3)).getBlock().getStateFromMeta(4), 3);
      chest = this.getChestTileEntity(world, x, y + 4, z + 3);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(x + 3, y + 4, z), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(x + 3, y + 4, z), OreSpawn.getBlockStateFast(world, new BlockPos(x + 3, y + 4, z)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, x + 3, y + 4, z);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(x + 3, y + 4, z + 6), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(x + 3, y + 4, z + 6), OreSpawn.getBlockStateFast(world, new BlockPos(x + 3, y + 4, z + 6)).getBlock().getStateFromMeta(3), 3);
      chest = this.getChestTileEntity(world, x + 3, y + 4, z + 6);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(x + 6, y + 4, z + 3), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(x + 6, y + 4, z + 3), OreSpawn.getBlockStateFast(world, new BlockPos(x + 6, y + 4, z + 3)).getBlock().getStateFromMeta(5), 3);
      chest = this.getChestTileEntity(world, x + 6, y + 4, z + 3);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
      }
   }

   public void makeSmallBeeHive(World world, int cposx, int cposy, int cposz) {
      int width = 7;
      int height = 21;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      if (!world.isRemote) {
         for (int i = -3; i < width + 3; i++) {
            for (int j = height * 2 / 3; j < height; j++) {
               for (int k = -3; k < width + 3; k++) {
                  this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
               }
            }
         }

         for (int var14 = 0; var14 < width; var14++) {
            for (int k = 0; k < width; k++) {
               int j = height * 2 / 3;
               this.FastSetBlock(world, cposx + var14, cposy + j, cposz + k, Blocks.SPONGE);
               int blk = world.rand.nextInt(height / 3);
               blk *= 2;
               blk -= Math.abs(var14 - width / 2);
               blk -= Math.abs(k - width / 2);
               if (blk < 1) {
                  blk = 1;
               }

               if (var14 == width / 2 && k == width / 2) {
                  blk = height * 2 / 3;
               }

               for (int var20 = 0; var20 < blk; var20++) {
                  this.FastSetBlock(world, cposx + var14, cposy + height * 2 / 3 - var20, cposz + k, Blocks.MOSSY_COBBLESTONE);
               }
            }
         }

         int jx = height * 2 / 3;

         for (int blkx = 0; blkx < height / 6; blkx++) {
            jx++;

            for (int var15 = 0; var15 < width; var15++) {
               for (int k = 0; k < width; k++) {
                  if (k != 0 && var15 != 0 && k != width - 1 && var15 != width - 1) {
                     this.FastSetBlock(world, cposx + var15, cposy + jx, cposz + k, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, cposx + var15, cposy + jx, cposz + k, Blocks.SPONGE);
                  }
               }
            }

            jx++;

            for (int var16 = -1; var16 < width + 1; var16++) {
               for (int kx = -1; kx < width + 1; kx++) {
                  if (kx != -1 && var16 != -1 && kx != width && var16 != width) {
                     this.FastSetBlock(world, cposx + var16, cposy + jx, cposz + kx, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, cposx + var16, cposy + jx, cposz + kx, Blocks.SPONGE);
                  }
               }
            }
         }

         jx++;

         for (int var17 = 0; var17 < width; var17++) {
            for (int kxx = 0; kxx < width; kxx++) {
               this.FastSetBlock(world, cposx + var17, cposy + jx, cposz + kxx, Blocks.SPONGE);
            }
         }

         jx = height * 2 / 3 + 1;

         for (int var18 = -1; var18 < 1; var18++) {
            for (int kxx = 2; kxx < 4; kxx++) {
               this.FastSetBlock(world, cposx + var18, cposy + jx, cposz + kxx, Blocks.AIR);
               this.FastSetBlock(world, cposx + var18, cposy + jx + 1, cposz + kxx, Blocks.AIR);
               this.FastSetBlock(world, cposx + var18, cposy + jx + 2, cposz + kxx, Blocks.AIR);
            }
         }

         for (int var34 = 0; var34 < 3; var34++) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + var34 + jx, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + var34 + jx, cposz + 1);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Bee");
            }
         }

         chestContents = this.beeContentsList;
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + jx, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + jx, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + jx, cposz + width / 2)).getBlock().getStateFromMeta(5), 3);
         chest = this.getChestTileEntity(world, cposx + width / 2, cposy + jx, cposz + width / 2);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 7 + world.rand.nextInt(5));
         }
      }
   }

   public void makeShadowDungeon(World world, int cposx, int cposy, int cposz) {
      int totalwidth = 19;
      TileEntityMobSpawner tileentitymobspawner = null;
      String whichmob = null;
      if (!world.isRemote) {
         int yoff = 0;
         int zoff = 0;
         int xoff = 0;

         for (int width = totalwidth; width > 0; width -= 2) {
            for (int i = 0; i < width; i++) {
               for (int k = 0; k < width; k++) {
                  if (k != 0 && k != width - 1 && i != 0 && i != width - 1) {
                     this.FastSetBlock(world, cposx + i + xoff, cposy - yoff, cposz + k + zoff, Blocks.AIR);
                  } else {
                     Block blk = Blocks.OBSIDIAN;
                     if ((yoff & 1) != 0) {
                        blk = Blocks.BEDROCK;
                     }

                     if (k >= width / 2 - 1 && k <= width / 2 + 1 || i >= width / 2 - 1 && i <= width / 2 + 1) {
                        blk = Blocks.SOUL_SAND;
                     }

                     this.FastSetBlock(world, cposx + i + xoff, cposy - yoff, cposz + k + zoff, blk);
                  }
               }
            }

            if (width <= 15 && width >= 9) {
               if ((yoff & 1) != 0) {
                  this.fill_shadow_chests(world, cposx + xoff, cposy - yoff, cposz + zoff, width, 0);
                  whichmob = "Ender Reaper";
               } else {
                  whichmob = "Nightmare";
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + xoff + 1, cposy - yoff, cposz + zoff + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + xoff + 1, cposy - yoff, cposz + zoff + 1);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, whichmob);
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + xoff + width - 2, cposy - yoff, cposz + zoff + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + xoff + width - 2, cposy - yoff, cposz + zoff + 1);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, whichmob);
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + xoff + 1, cposy - yoff, cposz + zoff + width - 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + xoff + 1, cposy - yoff, cposz + zoff + width - 2);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, whichmob);
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + xoff + width - 2, cposy - yoff, cposz + zoff + width - 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + xoff + width - 2, cposy - yoff, cposz + zoff + width - 2);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, whichmob);
               }
            }

            xoff++;
            zoff++;
            yoff++;
         }

         yoff = 0;
         zoff = 0;
         xoff = 0;

         for (int var18 = totalwidth; var18 > 0; var18 -= 2) {
            for (int i = 0; i < var18; i++) {
               for (int kx = 0; kx < var18; kx++) {
                  if (kx != 0 && kx != var18 - 1 && i != 0 && i != var18 - 1) {
                     this.FastSetBlock(world, cposx + i + xoff, cposy + yoff, cposz + kx + zoff, Blocks.AIR);
                  } else {
                     Block blkx = Blocks.OBSIDIAN;
                     if ((yoff & 1) != 0) {
                        blkx = Blocks.BEDROCK;
                     }

                     this.FastSetBlock(world, cposx + i + xoff, cposy + yoff, cposz + kx + zoff, blkx);
                  }
               }
            }

            xoff++;
            zoff++;
            yoff++;
         }
      }
   }

   private void fill_shadow_chests(World world, int cposx, int cposy, int cposz, int width, int height) {
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.shadowContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + height, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + height, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + 1, cposy + height, cposz + width / 2)).getBlock().getStateFromMeta(5), 3);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + height, cposz + width / 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + height, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + height, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + height, cposz + width / 2)).getBlock().getStateFromMeta(4), 3);
      chest = this.getChestTileEntity(world, cposx + width - 2, cposy + height, cposz + width / 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + 1), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + 1)).getBlock().getStateFromMeta(3), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + width - 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + width - 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height, cposz + width - 2)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + height, cposz + width - 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(7));
      }
   }

   public void makeAlienWTFDungeon(World world, int cposx, int cposy, int cposz) {
      int width = 5;
      int height = 5;
      int xwidth = 3;
      int zwidth = 6;
      int depth = 20;
      cposy -= depth - 3;

      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
               if (i != 0 && j != 0 && k != 0 && i != width - 1 && j != height - 1 && k != width - 1) {
                  this.FastSetBlock(world, cposx + i - 2, cposy + j, cposz + k - 2, Blocks.AIR);
               } else {
                  this.FastSetBlock(world, cposx + i - 2, cposy + j, cposz + k - 2, Blocks.LAPIS_ORE);
               }
            }
         }
      }

      int s = 0;
      cposx--;
      cposz--;

      for (int j = 3; j < depth; j++) {
         for (int var22 = 0; var22 < 4; var22++) {
            for (int kx = 0; kx < 4; kx++) {
               Block blk = Blocks.AIR;
               if (var22 == 0 || kx == 0 || var22 == 3 || kx == 3) {
                  blk = Blocks.LAPIS_ORE;
               }

               OreSpawn.setBlockFast(world, cposx + var22, cposy + j, cposz + kx, blk, 0, 2);
            }
         }

         switch (s) {
            case 0:
               OreSpawn.setBlockFast(world, cposx + 1, cposy + j, cposz + 1, Blocks.STONE, 0, 2);
               break;
            case 1:
               OreSpawn.setBlockFast(world, cposx + 2, cposy + j, cposz + 1, Blocks.STONE, 0, 2);
               break;
            case 2:
               OreSpawn.setBlockFast(world, cposx + 2, cposy + j, cposz + 2, Blocks.STONE, 0, 2);
               break;
            default:
               OreSpawn.setBlockFast(world, cposx + 1, cposy + j, cposz + 2, Blocks.STONE, 0, 2);
         }

         if (++s > 3) {
            s = 0;
         }
      }

      this.makePart(world, ++cposx, cposy, ++cposz + 7, 9, 5, 1, 1, 1);

      for (int var23 = 0; var23 < xwidth; var23++) {
         for (int kx = 0; kx < zwidth; kx++) {
            for (int var28 = 0; var28 < 4; var28++) {
               Block blk = Blocks.AIR;
               if (var28 == 0 || var28 == 3) {
                  blk = Blocks.LAPIS_ORE;
               }

               if (var23 == 0 || var23 == xwidth - 1) {
                  blk = Blocks.LAPIS_ORE;
               }

               this.FastSetBlock(world, cposx + var23, cposy + var28, cposz + kx + 2, blk);
            }
         }
      }

      this.makePart(world, cposx + 7, cposy, cposz, 11, 6, 1, -1, 2);
      int var37 = 6;
      int var40 = 3;

      for (int var24 = 0; var24 < var37; var24++) {
         for (int kx = 0; kx < var40; kx++) {
            for (int var29 = 0; var29 < 4; var29++) {
               Block blkx = Blocks.AIR;
               if (var29 == 0 || var29 == 3) {
                  blkx = Blocks.LAPIS_ORE;
               }

               if (kx == 0 || kx == var40 - 1) {
                  blkx = Blocks.LAPIS_ORE;
               }

               this.FastSetBlock(world, cposx + var24 + 2, cposy + var29, cposz - kx, blkx);
            }
         }
      }

      this.makePart(world, cposx - 7, cposy, cposz, 13, 7, -1, 1, 3);
      var37 = 6;
      var40 = 3;

      for (int var25 = 0; var25 < var37; var25++) {
         for (int kx = 0; kx < var40; kx++) {
            for (int var30 = 0; var30 < 4; var30++) {
               Block blkxx = Blocks.AIR;
               if (var30 == 0 || var30 == 3) {
                  blkxx = Blocks.LAPIS_ORE;
               }

               if (kx == 0 || kx == var40 - 1) {
                  blkxx = Blocks.LAPIS_ORE;
               }

               this.FastSetBlock(world, cposx - var25 - 2, cposy + var30, cposz + kx, blkxx);
            }
         }
      }

      this.makePart(world, cposx, cposy, cposz - 7, 15, 8, -1, -1, 4);
      var37 = 3;
      var40 = 6;

      for (int var26 = 0; var26 < var37; var26++) {
         for (int kx = 0; kx < var40; kx++) {
            for (int var31 = 0; var31 < 4; var31++) {
               Block blkxxx = Blocks.AIR;
               if (var31 == 0 || var31 == 3) {
                  blkxxx = Blocks.LAPIS_ORE;
               }

               if (var26 == 0 || var26 == var37 - 1) {
                  blkxxx = Blocks.LAPIS_ORE;
               }

               this.FastSetBlock(world, cposx - var26, cposy + var31, cposz - kx - 2, blkxxx);
            }
         }
      }
   }

   private void makePart(World world, int cposx, int cposy, int cposz, int width, int height, int dx, int dz, int difficulty) {
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + i * dx, cposy + j, cposz + k * dz, Blocks.AIR);
            }
         }
      }

      for (int var17 = 0; var17 < width; var17++) {
         int j = 0;

         for (int k = 0; k < width; k++) {
            Block blk = Blocks.QUARTZ_BLOCK;
            if (var17 == width / 2 || k == width / 2) {
               blk = Blocks.OBSIDIAN;
            }

            this.FastSetBlock(world, cposx + var17 * dx, cposy + j, cposz + k * dz, blk);
         }
      }

      for (int var18 = 0; var18 < width; var18++) {
         int j = height;

         for (int k = 0; k < width; k++) {
            Block blk = Blocks.OBSIDIAN;
            this.FastSetBlock(world, cposx + var18 * dx, cposy + j, cposz + k * dz, blk);
         }
      }

      for (int var19 = 0; var19 < width; var19++) {
         for (int j = 0; j < height; j++) {
            Block blk = Blocks.OBSIDIAN;
            int k = 0;
            this.FastSetBlock(world, cposx + var19 * dx, cposy + j, cposz + k * dz, blk);
            k = width - 1;
            this.FastSetBlock(world, cposx + var19 * dx, cposy + j, cposz + k * dz, blk);
         }
      }

      for (int k = 0; k < width; k++) {
         for (int j = 0; j < height; j++) {
            int var20 = 0;
            this.FastSetBlock(world, cposx + var20 * dx, cposy + j, cposz + k * dz, Blocks.OBSIDIAN);
            var20 = width - 1;
            this.FastSetBlock(world, cposx + var20 * dx, cposy + j, cposz + k * dz, Blocks.OBSIDIAN);
         }
      }

      for (int j = 0; j < difficulty; j++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + dx * width / 2, cposy + j + 2, cposz + dz * width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + dx * width / 2, cposy + j + 2, cposz + dz * width / 2);
         if (tileentitymobspawner != null) {
            int t = world.rand.nextInt(2);
            if (t == 0) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Alien");
            }

            if (t == 1) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "WTF?");
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + dx * width / 2 + dx, cposy + j + 2, cposz + dz * width / 2 + dz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + dx * width / 2 + dx, cposy + j + 2, cposz + dz * width / 2 + dz);
         if (tileentitymobspawner != null) {
            int tx = world.rand.nextInt(2);
            if (tx == 0) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Alien");
            }

            if (tx == 1) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "WTF?");
            }
         }
      }

      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.AlienWTFContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width * dx / 2, cposy + 1, cposz + dz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + width * dx / 2, cposy + 1, cposz + dz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }

      if (difficulty > 1) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width * dx / 2, cposy + 1, cposz + (width - 2) * dz), (Blocks.CHEST).getStateFromMeta(0), 2);
         chest = this.getChestTileEntity(world, cposx + width * dx / 2, cposy + 1, cposz + (width - 2) * dz);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
         }
      }

      if (difficulty > 2) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + dx, cposy + 1, cposz + width / 2 * dz), (Blocks.CHEST).getStateFromMeta(0), 2);
         chest = this.getChestTileEntity(world, cposx + dx, cposy + 1, cposz + width / 2 * dz);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
         }
      }

      if (difficulty > 3) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + (width - 2) * dx, cposy + 1, cposz + width / 2 * dz), (Blocks.CHEST).getStateFromMeta(0), 2);
         chest = this.getChestTileEntity(world, cposx + (width - 2) * dx, cposy + 1, cposz + width / 2 * dz);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
         }
      }
   }

   public void makeEnderKnightDungeon(World world, int cposx, int cposy, int cposz) {
      int height = 6;
      int zwidth = 5;
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int i = 0; i < 4; i++) {
         for (int k = 0; k < 5; k++) {
            for (int j = 0; j < 5; j++) {
               this.FastSetBlock(world, cposx, cposy + j, cposz + k, Blocks.AIR);
            }
         }

         cposx++;
      }

      int var30 = 5;

      for (int k = 0; k < var30; k++) {
         for (int j = 0; j < height; j++) {
            Block blk = Blocks.OBSIDIAN;
            if (k == 2 && j >= 1 && j <= 3) {
               blk = Blocks.AIR;
            }

            this.FastSetBlock(world, cposx, cposy + j, cposz + k, blk);
         }
      }

      cposx++;
      cposz--;
      var30 = 7;

      for (int var26 = 0; var26 < var30; var26++) {
         for (int j = 0; j < height; j++) {
            Block blk = Blocks.AIR;
            if (j == 0 || j == height - 1) {
               blk = Blocks.OBSIDIAN;
            }

            if (j == 0 && var26 > 0 && var26 < var30 - 1) {
               blk = Blocks.END_STONE;
            }

            if (var26 == 0 || var26 == var30 - 1) {
               blk = Blocks.OBSIDIAN;
            }

            this.FastSetBlock(world, cposx, cposy + j, cposz + var26, blk);
         }

         if (var26 == 1 || var26 == 2 || var26 == var30 - 3 || var26 == var30 - 2) {
            this.makeShelves(world, cposx, cposy + 1, cposz + var26);
         }
      }

      cposz--;

      for (int m = 0; m < 5; m++) {
         cposx++;
         var30 = 9;

         for (int var27 = 0; var27 < var30; var27++) {
            for (int j = 0; j < height; j++) {
               Block blkx = Blocks.AIR;
               if (j == 0 || j == height - 1) {
                  blkx = Blocks.OBSIDIAN;
               }

               if (j == 0 && var27 > 0 && var27 < var30 - 1) {
                  blkx = Blocks.END_STONE;
               }

               if (var27 == 0 || var27 == var30 - 1) {
                  blkx = Blocks.OBSIDIAN;
               }

               this.FastSetBlock(world, cposx, cposy + j, cposz + var27, blkx);
            }

            if (var27 == 1 || var27 == 2 || var27 == var30 - 3 || var27 == var30 - 2) {
               this.makeShelves(world, cposx, cposy + 1, cposz + var27);
            }

            if (m == 2 && var27 == 4) {
               OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz + var27), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz + var27);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz + var27), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + var27);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
               }
            }
         }
      }

      cposz++;
      cposx++;
      var30 = 7;

      for (int var28 = 0; var28 < var30; var28++) {
         for (int j = 0; j < height; j++) {
            Block blkxx = Blocks.AIR;
            if (j == 0 || j == height - 1) {
               blkxx = Blocks.OBSIDIAN;
            }

            if (j == 0 && var28 > 0 && var28 < var30 - 1) {
               blkxx = Blocks.END_STONE;
            }

            if (var28 == 0 || var28 == var30 - 1) {
               blkxx = Blocks.OBSIDIAN;
            }

            this.FastSetBlock(world, cposx, cposy + j, cposz + var28, blkxx);
         }

         if (var28 == 1 || var28 == 2 || var28 == var30 - 3 || var28 == var30 - 2) {
            this.makeShelves(world, cposx, cposy + 1, cposz + var28);
         }
      }

      cposz++;
      cposx++;
      var30 = 5;

      for (int var29 = 0; var29 < var30; var29++) {
         for (int j = 0; j < height; j++) {
            Block blkxxx = Blocks.OBSIDIAN;
            this.FastSetBlock(world, cposx, cposy + j, cposz + var29, blkxxx);
         }
      }
   }

   private void makeShelves(World world, int cposx, int cposy, int cposz) {
      int i = world.rand.nextInt(4);
      Block blk = Blocks.AIR;
      if (i == 0) {
         TileEntityChest chest = null;
         com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
         chestContents = this.KnightContentsList;
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
         chest = this.getChestTileEntity(world, cposx, cposy, cposz);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
         }
      }

      if (i == 1) {
         blk = Blocks.BOOKSHELF;
         int k = 1 + world.rand.nextInt(4);

         for (int j = 0; j < k; j++) {
            this.FastSetBlock(world, cposx, cposy + j, cposz, blk);
         }
      }

      if (i == 2) {
         blk = Blocks.WEB;
         int k = 1 + world.rand.nextInt(4);

         for (int j = 0; j < k; j++) {
            this.FastSetBlock(world, cposx, cposy + j, cposz, blk);
         }
      }
   }

   public void makePlayPool(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.SquidContentsList;

      for (int i = 0; i < 4; i++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + i, cposy + 16, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + i, cposy + 16, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Attack Squid");
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 17, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + 17, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + 17, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }

      for (int var9 = 0; var9 < 4; var9++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var9, cposy + 18, cposz), (Blocks.WATER).getStateFromMeta(0), 3);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 18, cposz), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 4, cposy + 18, cposz), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
   }

   public void makeWaterDragonLair(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.WaterDragonContentsList;
      float radius = 10.0F;

      for (float currad = 0.0F; currad < radius; currad += 0.33F) {
         for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
            float curx = (float)(currad * Math.cos(Math.toRadians(curdeg)));
            float curz = (float)(currad * Math.sin(Math.toRadians(curdeg)));
            Block blk = Blocks.BEDROCK;
            if (currad > 5.0F && currad < 6.0F) {
               blk = Blocks.IRON_BLOCK;
            }

            this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 7, (int)(cposz + curz + 0.5F), blk);
         }
      }

      for (int i = 1; i < 10; i++) {
         this.FastSetBlock(world, (int)(cposx + i + 0.5F), cposy + 7, (int)(cposz + 0.5F), Blocks.IRON_BLOCK);
         this.FastSetBlock(world, (int)(cposx - i + 0.5F), cposy + 7, (int)(cposz + 0.5F), Blocks.IRON_BLOCK);
         this.FastSetBlock(world, (int)(cposx + 0.5F), cposy + 7, (int)(cposz + i + 0.5F), Blocks.IRON_BLOCK);
         this.FastSetBlock(world, (int)(cposx + 0.5F), cposy + 7, (int)(cposz - i + 0.5F), Blocks.IRON_BLOCK);
      }

      this.FastSetBlock(world, (int)(cposx + 0.5F), cposy + 7, (int)(cposz + 0.5F), Blocks.AIR);
      this.FastSetBlock(world, (int)(cposx + 1 + 0.5F), cposy + 7, (int)(cposz + 0.5F), Blocks.GLOWSTONE);
      this.FastSetBlock(world, (int)(cposx - 1 + 0.5F), cposy + 7, (int)(cposz + 0.5F), Blocks.GLOWSTONE);
      this.FastSetBlock(world, (int)(cposx + 0.5F), cposy + 7, (int)(cposz + 1 + 0.5F), Blocks.GLOWSTONE);
      this.FastSetBlock(world, (int)(cposx + 0.5F), cposy + 7, (int)(cposz - 1 + 0.5F), Blocks.GLOWSTONE);
      float var24 = 10.0F;

      for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
         float curx = (float)(var24 * Math.cos(Math.toRadians(curdeg)));
         float curz = (float)(var24 * Math.sin(Math.toRadians(curdeg)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 1, (int)(cposz + curz + 0.5F), Blocks.GLOWSTONE);
         Block blk = Blocks.LAPIS_BLOCK;
         if (world.rand.nextInt(2) == 0) {
            blk = OreSpawn.MyWaterDragonSpawnBlock;
         }

         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 2, (int)(cposz + curz + 0.5F), blk);
         blk = Blocks.LAPIS_BLOCK;
         if (world.rand.nextInt(2) == 0) {
            blk = OreSpawn.MyWaterDragonSpawnBlock;
         }

         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 3, (int)(cposz + curz + 0.5F), blk);
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 4, (int)(cposz + curz + 0.5F), Blocks.GLOWSTONE);
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 5, (int)(cposz + curz + 0.5F), Blocks.BEDROCK);
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 6, (int)(cposz + curz + 0.5F), Blocks.BEDROCK);
      }

      for (int var18 = -3; var18 <= 3; var18++) {
         for (int j = -3; j <= 3; j++) {
            this.FastSetBlock(world, cposx + var18, cposy, cposz + j, Blocks.SAND);
            this.FastSetBlock(world, cposx + var18, cposy - 1, cposz + j, Blocks.STONE);
         }
      }

      for (int var19 = -2; var19 <= 2; var19++) {
         for (int j = -2; j <= 2; j++) {
            this.FastSetBlock(world, cposx + var19, cposy + 3, cposz + j, Blocks.LEAVES);
         }
      }

      this.FastSetBlock(world, cposx, cposy + 4, cposz, Blocks.LEAVES);
      this.FastSetBlock(world, cposx, cposy + 3, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx, cposy + 2, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx, cposy + 1, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx + 1, cposy + 3, cposz + 1, Blocks.LOG);
      this.FastSetBlock(world, cposx - 1, cposy + 3, cposz - 1, Blocks.LOG);
      this.FastSetBlock(world, cposx + 1, cposy + 3, cposz - 1, Blocks.LOG);
      this.FastSetBlock(world, cposx - 1, cposy + 3, cposz + 1, Blocks.LOG);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Water Dragon");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Water Dragon");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Water Dragon");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Water Dragon");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz - 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }
   }

   public void makeCloudSharkDungeon(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.CloudSharkContentsList;
      this.FastSetBlock(world, cposx, cposy, cposz, Blocks.GLOWSTONE);
      this.FastSetBlock(world, cposx, cposy - 1, cposz, Blocks.GLOWSTONE);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy, cposz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy, cposz - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }
   }

   public void makeLeafMonsterDungeon(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.LeafMonsterContentsList;

      for (int i = -2; i < 6; i++) {
         for (int k = -3; k < 2; k++) {
            for (int j = 0; j < 4; j++) {
               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
            }
         }
      }

      for (int var21 = 0; var21 < 4; var21++) {
         for (int k = 0; k < 4; k++) {
            for (int j = -1; j > -5; j--) {
               Block blk = Blocks.LOG;
               Block bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + var21, cposy + j, cposz + k)).getBlock();
               if (bid == Blocks.AIR || bid == Blocks.TALLGRASS) {
                  this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, blk);
               }
            }
         }
      }

      for (int var22 = 0; var22 < 4; var22++) {
         for (int k = 0; k < 4; k++) {
            for (int jx = 0; jx < 10; jx++) {
               Block blk = Blocks.LOG;
               if (jx < 2 && (k == 0 || k == 1) && (var22 == 1 || var22 == 2)) {
                  blk = Blocks.AIR;
               }

               if (k == 1 && (var22 == 1 || var22 == 2)) {
                  blk = Blocks.AIR;
               }

               if (k != 2 || var22 != 1 && var22 != 2) {
                  this.FastSetBlock(world, cposx + var22, cposy + jx, cposz + k, blk);
               }
            }
         }
      }

      for (int var23 = 0; var23 < 4; var23++) {
         for (int k = 0; k < 4; k++) {
            for (int jx = 0; jx < 10; jx++) {
               if (k == 2 && (var23 == 1 || var23 == 2)) {
                  Block blkx = Blocks.LADDER;
                  OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var23, cposy + jx, cposz + k), (blkx).getStateFromMeta(2), 3);
               }
            }
         }
      }

      this.FastSetBlock(world, cposx + 1, cposy + 2, cposz - 1, Blocks.LEAVES);
      this.FastSetBlock(world, cposx + 2, cposy + 2, cposz - 1, Blocks.LEAVES);

      for (int var24 = -3; var24 < 7; var24++) {
         for (int k = -3; k < 7; k++) {
            int jxx = 9;
            if (var24 < 0 || var24 > 3 || k < 0 || k > 3) {
               Block blkx = Blocks.LOG;
               if (var24 == -3 || var24 == 6 || k == -3 || k == 6) {
                  blkx = Blocks.LEAVES;
               }

               this.FastSetBlock(world, cposx + var24, cposy + jxx, cposz + k, blkx);
            }
         }
      }

      for (int var25 = -3; var25 < 7; var25++) {
         for (int kx = -3; kx < 7; kx++) {
            for (int jxx = 10; jxx < 13; jxx++) {
               Block blkx = Blocks.AIR;
               if (var25 == -3 || var25 == 6 || kx == -3 || kx == 6) {
                  blkx = Blocks.LEAVES;
               }

               this.FastSetBlock(world, cposx + var25, cposy + jxx, cposz + kx, blkx);
            }
         }
      }

      for (int var26 = -2; var26 < 6; var26++) {
         for (int kx = -2; kx < 6; kx++) {
            int jxx = 13;
            Block blkx = Blocks.AIR;
            if (var26 == -2 || var26 == 5 || kx == -2 || kx == 5) {
               blkx = Blocks.LOG;
            }

            if (var26 == -1 || var26 == 4 || kx == -1 || kx == 4) {
               blkx = Blocks.LEAVES;
            }

            this.FastSetBlock(world, cposx + var26, cposy + jxx, cposz + kx, blkx);
         }
      }

      for (int var27 = -1; var27 < 5; var27++) {
         for (int kx = -1; kx < 5; kx++) {
            int jxxx = 14;
            Block blkxx = Blocks.LEAVES;
            this.FastSetBlock(world, cposx + var27, cposy + jxxx, cposz + kx, blkxx);
         }
      }

      for (int var28 = 0; var28 < 4; var28++) {
         for (int kx = 0; kx < 4; kx++) {
            int jxxx = 15;
            Block blkxx = Blocks.LOG;
            this.FastSetBlock(world, cposx + var28, cposy + jxxx, cposz + kx, blkxx);
         }
      }

      for (int var29 = 1; var29 < 3; var29++) {
         for (int kx = 1; kx < 3; kx++) {
            int jxxx = 16;
            Block blkxx = Blocks.LEAVES;
            this.FastSetBlock(world, cposx + var29, cposy + jxxx, cposz + kx, blkxx);
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 2, cposy + 10, cposz - 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 2, cposy + 10, cposz - 2);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Leaf Monster");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 5, cposy + 10, cposz + 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 5, cposy + 10, cposz + 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Leaf Monster");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 2, cposy + 10, cposz + 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 2, cposy + 10, cposz + 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Leaf Monster");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 5, cposy + 10, cposz - 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 5, cposy + 10, cposz - 2);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Leaf Monster");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 10, cposz + 5), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + 10, cposz + 5), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + 10, cposz + 5);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 12 + world.rand.nextInt(5));
      }
   }

   public void makeMiniDungeon(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.MiniContentsList;

      for (int i = 0; i < 10; i++) {
         for (int k = 0; k < 10; k++) {
            for (int j = 0; j < 7; j++) {
               Block blk = Blocks.AIR;
               if (i == 0 || k == 0 || i == 9 || k == 9) {
                  blk = Blocks.IRON_BARS;
               }

               if (i == 0 && k == 0) {
                  blk = Blocks.COBBLESTONE;
               }

               if (i == 9 && k == 9) {
                  blk = Blocks.COBBLESTONE;
               }

               if (i == 0 && k == 9) {
                  blk = Blocks.COBBLESTONE;
               }

               if (i == 9 && k == 0) {
                  blk = Blocks.COBBLESTONE;
               }

               if (j == 0) {
                  blk = Blocks.COBBLESTONE;
               }

               if (j == 6 && (i == 0 || k == 0 || i == 9 || k == 9)) {
                  blk = Blocks.COBBLESTONE;
               }

               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
            }
         }
      }

      for (int var16 = 1; var16 < 9; var16++) {
         for (int k = 1; k < 9; k++) {
            int j = 7;
            Block blkx = Blocks.AIR;
            if (var16 == 1 || var16 == 8 || k == 1 || k == 8) {
               blkx = Blocks.GRASS;
            }

            this.FastSetBlock(world, cposx + var16, cposy + j, cposz + k, blkx);
         }
      }

      for (int var17 = 2; var17 < 8; var17++) {
         for (int k = 2; k < 8; k++) {
            int j = 8;
            Block blkx = Blocks.AIR;
            if (var17 == 2 || var17 == 7 || k == 2 || k == 7) {
               blkx = Blocks.GRASS;
            }

            this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, blkx);
         }
      }

      int var18 = -6;
      int j = 1;
      int k = 3;

      for (int m = 0; m < 6; m++) {
         this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.PLANKS);
         this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k + 1, Blocks.PLANKS);
         this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k + 2, Blocks.PLANKS);
         this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k + 3, Blocks.PLANKS);
         this.FastSetBlock(world, cposx + var18, cposy + j + 1, cposz + k, Blocks.OAK_FENCE);
         this.FastSetBlock(world, cposx + var18, cposy + j + 1, cposz + k + 3, Blocks.OAK_FENCE);
         this.FastSetBlock(world, cposx + var18, cposy + j + 2, cposz + k, Blocks.TORCH);
         this.FastSetBlock(world, cposx + var18, cposy + j + 2, cposz + k + 3, Blocks.TORCH);
         var18++;
         j++;
      }

      for (int var19 = 3; var19 < 7; var19++) {
         for (int var47 = 3; var47 < 7; var47++) {
            int var33 = 9;
            Block blkx = Blocks.AIR;
            if (var19 == 3 || var19 == 6 || var47 == 3 || var47 == 6) {
               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var19, cposy + var33, cposz + var47), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var19, cposy + var33, cposz + var47);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Butterfly");
               }
            }
         }
      }

      int var48 = 0;
      int var20 = 0;

      for (j = 7; j < 11; j++) {
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.COBBLESTONE);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + j, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
      }

      var48 = 9;
      var20 = 9;

      for (j = 7; j < 11; j++) {
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.COBBLESTONE);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + j, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Butterfly");
      }

      var20 = 0;
      var48 = 9;

      for (j = 7; j < 11; j++) {
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.COBBLESTONE);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + j, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
      }

      var20 = 9;
      var48 = 0;

      for (j = 7; j < 11; j++) {
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + var48, Blocks.COBBLESTONE);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + j, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + j, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Butterfly");
      }

      var48 = 1;
      var20 = 1;
      int var38 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + var38, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
      }

      var48 = 8;
      var20 = 8;
      var38 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + var38, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Terrible Terror");
      }

      var20 = 8;
      var48 = 1;
      var38 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + var38, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Butterfly");
      }

      var20 = 1;
      var48 = 8;
      var38 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + var38, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Butterfly");
      }

      var20 = 4;
      var48 = 4;
      var38 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + var38, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
      }

      var20 = 5;
      var48 = 5;
      var38 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var20, cposy + var38, cposz + var48), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var20, cposy + var38, cposz + var48);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 3, cposy + 1, cposz + 3), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + 3, cposy + 1, cposz + 3);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }
   }

   public void makeGoldFishBowl(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      int j = 1;
      Block blk = Blocks.GLASS;

      for (int i = 0; i < 5; i++) {
         for (int k = 0; k < 5; k++) {
            this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
         }
      }

      int var27 = 2;

      for (int var17 = -1; var17 < 6; var17++) {
         for (int k = -1; k < 6; k++) {
            blk = Blocks.SAND;
            if (var17 == -1 || k == -1 || var17 == 5 || k == 5) {
               blk = Blocks.GLASS;
            }

            this.FastSetBlock(world, cposx + var17, cposy + var27, cposz + k, blk);
         }
      }

      var27 = 3;

      for (int var18 = -1; var18 < 6; var18++) {
         for (int k = -1; k < 6; k++) {
            blk = Blocks.WATER;
            if (var18 == -1 || k == -1 || var18 == 5 || k == 5) {
               blk = Blocks.GLASS;
            }

            this.FastSetBlock(world, cposx + var18, cposy + var27, cposz + k, blk);
         }
      }

      int k = 0;
      int var19 = 0;
      blk = Blocks.GLOWSTONE;
      this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + k, blk);
      int var36 = 4;
      var19 = 4;
      this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + var36, blk);
      var36 = 4;
      var19 = 0;
      this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + var36, blk);
      var36 = 0;
      var19 = 4;
      this.FastSetBlock(world, cposx + var19, cposy + var27, cposz + var36, blk);
      var27 = 4;

      for (int var23 = -1; var23 < 6; var23++) {
         for (int var39 = -1; var39 < 6; var39++) {
            blk = Blocks.WATER;
            if (var23 == -1 || var39 == -1 || var23 == 5 || var39 == 5) {
               blk = Blocks.GLASS;
            }

            this.FastSetBlock(world, cposx + var23, cposy + var27, cposz + var39, blk);
         }
      }

      for (int var30 = 5; var30 < 8; var30++) {
         for (int var24 = -1; var24 < 6; var24++) {
            for (int var40 = -1; var40 < 6; var40++) {
               blk = Blocks.AIR;
               if (var24 == -1 || var40 == -1 || var24 == 5 || var40 == 5) {
                  blk = Blocks.GLASS;
               }

               this.FastSetBlock(world, cposx + var24, cposy + var30, cposz + var40, blk);
            }
         }
      }

      var27 = 8;
      blk = Blocks.GLASS;

      for (int var25 = 0; var25 < 5; var25++) {
         for (int var41 = 0; var41 < 5; var41++) {
            this.FastSetBlock(world, cposx + var25, cposy + var27, cposz + var41, blk);
         }
      }

      var19 = 2;
      var36 = 2;
      var27 = 6;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var19, cposy + var27, cposz + var36), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var19, cposy + var27, cposz + var36);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Gold Fish");
      }
   }

   public void makeEnderReaperGraveyard(World world, int cposx, int cposy, int cposz) {
      int width = 11;
      int length = 13;
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int j = 1; j < 5; j++) {
         for (int i = 0; i < width; i++) {
            for (int k = 0; k < length; k++) {
               if (OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy - j, cposz + k)).getBlock() == Blocks.AIR) {
                  this.FastSetBlock(world, cposx + i, cposy - j, cposz + k, Blocks.END_STONE);
               }
            }
         }
      }

      int var20 = 0;
      Block blk = Blocks.END_STONE;

      for (int i = 0; i < width; i++) {
         for (int kx = 0; kx < length; kx++) {
            this.FastSetBlock(world, cposx + i, cposy + var20, cposz + kx, blk);
         }
      }

      for (int var21 = 1; var21 < 5; var21++) {
         for (int var15 = 0; var15 < width; var15++) {
            for (int kx = 0; kx < length; kx++) {
               blk = Blocks.AIR;
               if (var15 == 0 || kx == 0 || var15 == width - 1 || kx == length - 1) {
                  blk = Blocks.IRON_BARS;
               }

               this.FastSetBlock(world, cposx + var15, cposy + var21, cposz + kx, blk);
            }
         }
      }

      int var16 = 1;
      int kx = 1;
      var20 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var16, cposy + var20, cposz + kx), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var16, cposy + var20, cposz + kx);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      var16 = width - 2;
      kx = length - 2;
      var20 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var16, cposy + var20, cposz + kx), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var16, cposy + var20, cposz + kx);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      int var18 = 1;
      kx = length - 2;
      var20 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var18, cposy + var20, cposz + kx), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + var20, cposz + kx);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      var18 = width - 2;
      int var31 = 1;
      var20 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var18, cposy + var20, cposz + var31), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + var20, cposz + var31);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      this.makeAGrave(world, cposx, cposy, cposz, 1, 6);
      this.makeAGrave(world, cposx, cposy, cposz, 3, 4);
      this.makeAGrave(world, cposx, cposy, cposz, 5, 4);
      this.makeAGrave(world, cposx, cposy, cposz, 7, 4);
      this.makeAGrave(world, cposx, cposy, cposz, 3, 8);
      this.makeAGrave(world, cposx, cposy, cposz, 5, 8);
      this.makeAGrave(world, cposx, cposy, cposz, 7, 8);
      this.makeAGrave(world, cposx, cposy, cposz, 9, 6);
   }

   public void makeAGrave(World world, int cposx, int cposy, int cposz, int xoff, int zoff) {
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.GraveContentsList;
      this.FastSetBlock(world, cposx + xoff, cposy + 1, cposz + zoff - 1, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + xoff, cposy, cposz + zoff + 1, Blocks.OBSIDIAN);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + xoff, cposy, cposz + zoff), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + xoff, cposy, cposz + zoff);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(3));
      }
   }

   public void makeUrchinSpawner(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      int patchy = 3;

      for (int i = 0; i < patchy; i++) {
         Block bid = OreSpawn.CrystalStone;
         if (i == 1) {
            bid = OreSpawn.CrystalCrystal;
         }

         if (i == 2) {
            bid = OreSpawn.TigersEye;
         }

         float dx = world.rand.nextFloat() - world.rand.nextFloat();
         float dz = world.rand.nextFloat() - world.rand.nextFloat();
         float dy = 0.5F + world.rand.nextFloat() / 2.0F;
         int width = world.rand.nextInt(2);
         int length = 10 + width * 3 + world.rand.nextInt(5);
         if (i != 0) {
            length /= 2;
         }

         float rx = cposx;
         float ry = cposy;
         float rz = cposz;

         for (int iy = 0; iy <= length; iy++) {
            for (int ix = 0; ix <= width; ix++) {
               for (int iz = 0; iz <= width; iz++) {
                  OreSpawn.setBlockFast(world, (int)(rx + ix), (int)ry, (int)(rz + iz), bid, 0, 2);
               }
            }

            ry += dy;
            rx += dx;
            rz += dz;
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Crystal Urchin");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Crystal Urchin");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Crystal Urchin");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy, cposz), (Blocks.AIR).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy - 1, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy - 1, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx, cposy - 1, cposz)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx, cposy - 1, cposz);
      if (chest != null) {
         chest.setInventorySlotContents(1, new ItemStack(OreSpawn.UrchinEgg, 1 + world.rand.nextInt(5), 0));
         chest.setInventorySlotContents(2, new ItemStack(OreSpawn.CrystalCoal, 4 + world.rand.nextInt(16), 0));
         chest.setInventorySlotContents(3, new ItemStack(OreSpawn.CrystalCoal, 4 + world.rand.nextInt(16), 0));
      }
   }

   public void makeSpitBugLair(World world, int cposx, int cposy, int cposz) {
      int green = 5;
      int dark_green = 13;
      int width = 9;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.SpitBugContentsList;

      for (int i = 0; i < width; i++) {
         OreSpawn.setBlockFast(world, cposx + i, cposy + width - i + 2, cposz, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
         OreSpawn.setBlockFast(world, cposx + i, cposy + width - i + 1, cposz, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
         OreSpawn.setBlockFast(world, cposx + i, cposy + width - i, cposz, Blocks.MOSSY_COBBLESTONE, 0, 2);
         OreSpawn.setBlockFast(world, cposx - i, cposy + width - i + 2, cposz, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
         OreSpawn.setBlockFast(world, cposx - i, cposy + width - i + 1, cposz, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
         OreSpawn.setBlockFast(world, cposx - i, cposy + width - i, cposz, Blocks.MOSSY_COBBLESTONE, 0, 2);
      }

      OreSpawn.setBlockFast(world, cposx, cposy + width + 3, cposz, Blocks.EMERALD_ORE, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + width + 2, cposz, Blocks.EMERALD_ORE, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + width + 1, cposz, Blocks.EMERALD_ORE, 0, 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + width + 0, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + width + 0, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spit Bug");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + width - 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + width - 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spit Bug");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + width - 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + width - 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spit Bug");
      }

      int k = 0;

      for (int var15 = 0; var15 < width; var15++) {
         for (int j = -var15; j <= var15; j++) {
            OreSpawn.setBlockFast(world, cposx - width + var15 + 1, cposy, cposz + j, Blocks.STAINED_HARDENED_CLAY, green, 2);
            OreSpawn.setBlockFast(world, cposx + width - var15 - 1, cposy, cposz + j, Blocks.STAINED_HARDENED_CLAY, green, 2);
            if (j != -var15 && j != var15) {
               OreSpawn.setBlockFast(world, cposx - width + var15 + 1, cposy + 1, cposz + j, Blocks.AIR, 0, 2);
               OreSpawn.setBlockFast(world, cposx + width - var15 - 1, cposy + 1, cposz + j, Blocks.AIR, 0, 2);
               OreSpawn.setBlockFast(world, cposx - width + var15 + 1, cposy + 2, cposz + j, Blocks.AIR, 0, 2);
               OreSpawn.setBlockFast(world, cposx + width - var15 - 1, cposy + 2, cposz + j, Blocks.AIR, 0, 2);
            } else {
               OreSpawn.setBlockFast(world, cposx - width + var15 + 1, cposy + 1, cposz + j, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
               OreSpawn.setBlockFast(world, cposx + width - var15 - 1, cposy + 1, cposz + j, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
               OreSpawn.setBlockFast(world, cposx - width + var15 + 1, cposy + 2, cposz + j, Blocks.STONEBRICK, 3, 2);
               OreSpawn.setBlockFast(world, cposx + width - var15 - 1, cposy + 2, cposz + j, Blocks.STONEBRICK, 3, 2);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(4));
      }
   }

   public void makeIgloo(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      float currad = 6.0F;

      for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
         float curx = (float)(currad * Math.cos(Math.toRadians(curdeg)));
         float curz = (float)(currad * Math.sin(Math.toRadians(curdeg)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 1, (int)(cposz + curz + 0.5F), Blocks.SNOW);
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 2, (int)(cposz + curz + 0.5F), Blocks.ICE);
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 3, (int)(cposz + curz + 0.5F), Blocks.SNOW);
      }

      currad = 5.0F;

      for (float var15 = 0.0F; var15 < 360.0F; var15 += 5.0F) {
         float curx = (float)(currad * Math.cos(Math.toRadians(var15)));
         float curz = (float)(currad * Math.sin(Math.toRadians(var15)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 4, (int)(cposz + curz + 0.5F), Blocks.ICE);
      }

      currad = 4.0F;

      for (float var16 = 0.0F; var16 < 360.0F; var16 += 5.0F) {
         float curx = (float)(currad * Math.cos(Math.toRadians(var16)));
         float curz = (float)(currad * Math.sin(Math.toRadians(var16)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 5, (int)(cposz + curz + 0.5F), Blocks.SNOW);
      }

      currad = 3.0F;

      for (float var17 = 0.0F; var17 < 360.0F; var17 += 10.0F) {
         float curx = (float)(currad * Math.cos(Math.toRadians(var17)));
         float curz = (float)(currad * Math.sin(Math.toRadians(var17)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 5, (int)(cposz + curz + 0.5F), Blocks.ICE);
      }

      currad = 2.0F;

      for (float var18 = 0.0F; var18 < 360.0F; var18 += 15.0F) {
         float curx = (float)(currad * Math.cos(Math.toRadians(var18)));
         float curz = (float)(currad * Math.sin(Math.toRadians(var18)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 5, (int)(cposz + curz + 0.5F), Blocks.SNOW);
      }

      currad = 1.0F;

      for (float var19 = 0.0F; var19 < 360.0F; var19 += 15.0F) {
         float curx = (float)(currad * Math.cos(Math.toRadians(var19)));
         float curz = (float)(currad * Math.sin(Math.toRadians(var19)));
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + 5, (int)(cposz + curz + 0.5F), Blocks.ICE);
      }

      this.FastSetBlock(world, (int)(cposx - 6.0F + 0.5F), cposy, (int)(cposz + 0.5F), Blocks.PLANKS);
      this.FastSetBlock(world, (int)(cposx - 6.0F + 0.5F), cposy + 1, (int)(cposz + 0.5F), Blocks.AIR);
      this.FastSetBlock(world, (int)(cposx - 6.0F + 0.5F), cposy + 2, (int)(cposz + 0.5F), Blocks.AIR);
       this.placeDoorBlock(world, (int)(cposx - 6.0F + 0.5F), cposy + 1, (int)(cposz + 0.5F), 2, Blocks.OAK_DOOR);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + 1, cposz - 4), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + 1, cposz - 4);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 1, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 1, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 3, cposy + 1, cposz + 4), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 3, cposy + 1, cposz + 4);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost Pumpkin Skelly");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + 1, cposz - 3), (Blocks.CHEST).getStateFromMeta(2), 2);
      chest = this.getChestTileEntity(world, cposx - 3, cposy + 1, cposz - 3);
      if (chest != null) {
         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(0, new ItemStack(Items.COMPASS));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(1, new ItemStack(Items.MAP));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(2, new ItemStack(Items.COOKED_PORKCHOP, 8));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(3, new ItemStack(Blocks.TORCH, 32));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(4, new ItemStack(Items.COAL, 16));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(5, new ItemStack(Items.BED));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(6, new ItemStack(Items.BED));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(7, new ItemStack(Items.OAK_DOOR));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(8, new ItemStack(Items.IRON_PICKAXE));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(9, new ItemStack(Items.IRON_SWORD));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(10, new ItemStack(Items.IRON_AXE));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(11, new ItemStack(Items.BUCKET));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(13, new ItemStack(Blocks.CHEST));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(14, new ItemStack(Items.GOLD_NUGGET, 6));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(15, new ItemStack(Items.GOLD_NUGGET, 8));
         }

         if (world.rand.nextInt(2) == 0) {
            chest.setInventorySlotContents(16, new ItemStack(Items.GOLD_NUGGET, 10));
         }
      }
   }

   public void makeEnderDragonHospital(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.HospitalContentsList;

      for (int i = 0; i < 10; i++) {
         for (int k = 0; k < 10; k++) {
            for (int j = 0; j < 7; j++) {
               Block blk = Blocks.AIR;
               if (i == 0 || k == 0 || i == 9 || k == 9) {
                  blk = Blocks.IRON_BARS;
               }

               if (i == 0 && k == 0) {
                  blk = Blocks.OBSIDIAN;
               }

               if (i == 9 && k == 9) {
                  blk = Blocks.OBSIDIAN;
               }

               if (i == 0 && k == 9) {
                  blk = Blocks.OBSIDIAN;
               }

               if (i == 9 && k == 0) {
                  blk = Blocks.OBSIDIAN;
               }

               if (j == 0) {
                  blk = Blocks.END_STONE;
               }

               if (j == 6 && (i == 0 || k == 0 || i == 9 || k == 9)) {
                  blk = Blocks.END_STONE;
               }

               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
            }
         }
      }

      for (int var17 = 1; var17 < 9; var17++) {
         for (int k = 1; k < 9; k++) {
            int j = 7;
            Block blkx = Blocks.AIR;
            if (var17 == 1 || var17 == 8 || k == 1 || k == 8) {
               blkx = OreSpawn.MyEyeOfEnderBlock;
            }

            this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, blkx);
         }
      }

      for (int var18 = 2; var18 < 8; var18++) {
         for (int k = 2; k < 8; k++) {
            int j = 8;
            Block blkx = Blocks.AIR;
            if (var18 == 2 || var18 == 7 || k == 2 || k == 7) {
               blkx = OreSpawn.MyEyeOfEnderBlock;
            }

            this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, blkx);
         }
      }

      for (int var19 = 3; var19 < 7; var19++) {
         for (int k = 3; k < 7; k++) {
            int j = 9;
            Block blkx = Blocks.AIR;
            if (var19 == 3 || var19 == 6 || k == 3 || k == 6) {
               blkx = OreSpawn.MyEyeOfEnderBlock;
            }

            this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, blkx);
         }
      }

      int var20 = -6;
      int j = 1;
      int k = 3;

      for (int m = 0; m < 6; m++) {
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k, Blocks.END_STONE);
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k + 1, Blocks.END_STONE);
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k + 2, Blocks.END_STONE);
         this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k + 3, Blocks.END_STONE);
         this.FastSetBlock(world, cposx + var20, cposy + j + 1, cposz + k, Blocks.IRON_BARS);
         this.FastSetBlock(world, cposx + var20, cposy + j + 1, cposz + k + 3, Blocks.IRON_BARS);
         this.FastSetBlock(world, cposx + var20, cposy + j + 2, cposz + k, Blocks.GLOWSTONE);
         this.FastSetBlock(world, cposx + var20, cposy + j + 2, cposz + k + 3, Blocks.GLOWSTONE);
         var20++;
         j++;
      }

      this.FastSetBlock(world, cposx + 0, cposy + 7, cposz + 0, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 0, cposy + 7, cposz + 9, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 9, cposy + 7, cposz + 0, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 9, cposy + 7, cposz + 9, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 0, cposy + 8, cposz + 0, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 0, cposy + 8, cposz + 9, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 9, cposy + 8, cposz + 0, Blocks.OBSIDIAN);
      this.FastSetBlock(world, cposx + 9, cposy + 8, cposz + 9, Blocks.OBSIDIAN);
      EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(world);
      entityendercrystal.setLocationAndAngles(cposx + 0.5F, cposy + 9, cposz + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);
      world.spawnEntity(entityendercrystal);
      this.FastSetBlock(world, cposx, cposy + 9, cposz, Blocks.BEDROCK);
      entityendercrystal = new EntityEnderCrystal(world);
      entityendercrystal.setLocationAndAngles(cposx + 0.5F, cposy + 9, cposz + 9.5F, world.rand.nextFloat() * 360.0F, 0.0F);
      world.spawnEntity(entityendercrystal);
      this.FastSetBlock(world, cposx, cposy + 9, cposz + 9, Blocks.BEDROCK);
      entityendercrystal = new EntityEnderCrystal(world);
      entityendercrystal.setLocationAndAngles(cposx + 9.5F, cposy + 9, cposz + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);
      world.spawnEntity(entityendercrystal);
      this.FastSetBlock(world, cposx + 9, cposy + 9, cposz, Blocks.BEDROCK);
      entityendercrystal = new EntityEnderCrystal(world);
      entityendercrystal.setLocationAndAngles(cposx + 9.5F, cposy + 9, cposz + 9.5F, world.rand.nextFloat() * 360.0F, 0.0F);
      world.spawnEntity(entityendercrystal);
      this.FastSetBlock(world, cposx + 9, cposy + 9, cposz + 9, Blocks.BEDROCK);
      int var21 = 3;
      int var45 = 3;
      int var33 = 9;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      var21 = 3;
      var45 = 6;
      var33 = 9;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      var21 = 6;
      var45 = 3;
      var33 = 9;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      var21 = 6;
      var45 = 6;
      var33 = 9;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      var21 = 1;
      var45 = 1;
      var33 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
      }

      var21 = 1;
      var45 = 8;
      var33 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
      }

      var21 = 8;
      var45 = 1;
      var33 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
      }

      var21 = 8;
      var45 = 8;
      var33 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var21, cposy + var33, cposz + var45), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var21, cposy + var33, cposz + var45);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 4, cposy + 1, cposz + 4), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + 4, cposy + 1, cposz + 4);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
      }
   }

   public void makeCrystalHauntedHouse(World world, int cposx, int cposy, int cposz) {
      int deltax = 0;
      int deltaz = 0;
      int bid = 0;
      int dirx = 0;
      int dirz = 0;
      int stuffdir = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      int length = 3;
      int width = 3;
      int height = 3;
      int var22 = 1;
      int var28 = 2;
      int x = cposx;
      int z = cposz;
      int y = cposy;
      if (!world.isRemote) {
         for (int i = -width; i <= width; i++) {
            for (int j = -length; j <= length; j++) {
               for (int k = 0; k <= height + 1; k++) {
                  if (k == height + 1) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (OreSpawn.CrystalPlanksBlock).getDefaultState(), 3);
                  } else if (k == 0) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (OreSpawn.CrystalStone).getDefaultState(), 3);
                  } else if (i != width && j != length && i != -width && j != -length) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState(), 3);
                  } else if (k == height) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.GLASS).getDefaultState(), 3);
                  } else if ((k == 1 || k == 2) && i == var22 * width && j == deltaz * length) {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (Blocks.AIR).getDefaultState(), 3);
                  } else {
                     OreSpawn.setBlockStateFast(world, new BlockPos(x + i, y + k, z + j), (OreSpawn.CrystalPlanksBlock).getDefaultState(), 3);
                  }
               }
            }
         }

         int var23 = 2;
         int kx = 1;
         int j = length - 1;
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), (OreSpawn.CrystalFurnaceBlock).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), OreSpawn.getBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22)).getBlock().getStateFromMeta(var28), 3);
         var23 = 1;
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), (OreSpawn.CrystalWorkbenchBlock).getDefaultState(), 3);
         var23 = 0;
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), (Blocks.CHEST).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22), OreSpawn.getBlockStateFast(world, new BlockPos(x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22)).getBlock().getStateFromMeta(var28), 3);
         TileEntityChest chest = this.getChestTileEntity(world, x + var23 * var22 + j * deltaz, y + kx, z + var23 * deltaz + j * var22);
         if (chest != null) {
            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(0, new ItemStack(Items.COMPASS));
            }

            if (world.rand.nextInt(3) != 0) {
               chest.setInventorySlotContents(2, new ItemStack(OreSpawn.MyPeacock, 8));
            }

            if (world.rand.nextInt(3) != 0) {
               chest.setInventorySlotContents(3, new ItemStack(OreSpawn.CrystalTorch, 32));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(4, new ItemStack(OreSpawn.CrystalCoal, 16));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(5, new ItemStack(Items.BED));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(6, new ItemStack(Items.BED));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(7, new ItemStack(Items.OAK_DOOR));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(8, new ItemStack(OreSpawn.MyCrystalPinkPickaxe));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(9, new ItemStack(OreSpawn.MyCrystalPinkSword));
            }

            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(10, new ItemStack(OreSpawn.MyCrystalPinkAxe));
            }

            chest.setInventorySlotContents(11, new ItemStack(OreSpawn.KrakenRepellent));
            if (world.rand.nextInt(2) == 0) {
               chest.setInventorySlotContents(13, new ItemStack(Blocks.CHEST));
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 1, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost Pumpkin Skelly");
         }
      }
   }

   public void makeBouncyCastle(World world, int cposx, int cposy, int cposz) {
      int deltax = 0;
      int deltaz = 0;
      Block bid = Blocks.AIR;
      int meta = 0;
      int dirx = 0;
      int dirz = 0;
      int stuffdir = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.BouncyContentsList;
      int length = 4;
      int width = 4;
      int height = 5;
      boolean var24 = true;
      int var27 = 2;
      if (!world.isRemote) {
         for (int i = -width; i <= width; i++) {
            for (int j = -length; j <= length; j++) {
               for (int k = 0; k < height; k++) {
                  bid = Blocks.AIR;
                  int var26 = 0;
                  if (k == height - 1 || k == 0) {
                     bid = OreSpawn.MyLavafoamBlock;
                  }

                  if (i == -width || i == width) {
                     bid = OreSpawn.MyLavafoamBlock;
                  }

                  if (j == -length || j == length) {
                     bid = OreSpawn.MyLavafoamBlock;
                  }

                  if ((i == -width || i == width) && (j == -length || j == length)) {
                     bid = Blocks.STAINED_HARDENED_CLAY;
                     var26 = 14;
                  }

                  if ((k == 1 || k == 2) && i == 0 && j == -length) {
                     var26 = 0;
                     bid = Blocks.AIR;
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + k, cposz + j, bid, var26, 2);
               }
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 3, cposz + length - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz + length - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Silverfish");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz + length - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + length - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 3, cposz + length - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz + length - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 1, cposy + 3, cposz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 3, cposz - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Silverfish");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 3, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 1, cposy + 3, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 3, cposz + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - width + 1, cposy + 3, cposz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 3, cposz - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Silverfish");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - width + 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 3, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - width + 1, cposy + 3, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 3, cposz + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 1, cposy + 3, cposz + length - 1), (Blocks.CHEST).getStateFromMeta(2), 2);
         chest = this.getChestTileEntity(world, cposx + width - 1, cposy + 3, cposz + length - 1);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
         }
      }
   }

   public void makeEnderCastle(World world, int cposx, int cposy, int cposz) {
      int width = 22;
      int height = 12;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      TileEntityMobSpawner tileentitymobspawner = null;
      chestContents = this.EnderCastleContentsList;
      Block bid = Blocks.OBSIDIAN;

      for (int i = -3; i <= width + 3; i++) {
         for (int k = -3; k <= width + 3; k++) {
            for (int j = 0; j <= 1; j++) {
               bid = Blocks.AIR;
               if (j == 0) {
                  bid = Blocks.OBSIDIAN;
               }

               if (j == 1 && (i == -3 || i == width + 3 || k == width + 3 | k == -3)) {
                  bid = Blocks.IRON_BARS;
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
         }
      }

      for (int var16 = 0; var16 <= width; var16++) {
         for (int k = 0; k <= width; k++) {
            for (int j = 1; j <= height; j++) {
               bid = Blocks.AIR;
               if (var16 == 0 || var16 == width || k == width | k == 0) {
                  bid = Blocks.BEDROCK;
               }

               if (j == height && bid == Blocks.BEDROCK && (var16 + k & 1) == 0) {
                  bid = Blocks.AIR;
               }

               if (j == height - 2 && bid == Blocks.BEDROCK && (var16 + k & 1) == 0) {
                  int which = world.rand.nextInt(4);
                  if (which == 0) {
                     bid = OreSpawn.MyEnderKnightSpawnBlock;
                  }

                  if (which == 1) {
                     bid = OreSpawn.MyEnderReaperSpawnBlock;
                  }

                  if (which == 2) {
                     bid = OreSpawn.MyEndermanSpawnBlock;
                  }

                  if (which == 3) {
                     bid = OreSpawn.MyEnderDragonSpawnBlock;
                  }
               }

               if (j == 7 && bid == Blocks.BEDROCK && (var16 + k & 1) != 0) {
                  bid = OreSpawn.MyEyeOfEnderBlock;
               }

               OreSpawn.setBlockFast(world, cposx + var16, cposy + j, cposz + k, bid, 0, 2);
            }
         }
      }

      for (int var17 = -1; var17 <= width + 1; var17++) {
         for (int k = -1; k <= width + 1; k++) {
            for (int j = 1; j <= height - 1; j++) {
               bid = Blocks.AIR;
               if (j == 6 || j > 8) {
                  if (var17 == -1 || var17 == width + 1 || k == width + 1 | k == -1) {
                     bid = Blocks.BEDROCK;
                  }

                  if (j == 6 && bid != Blocks.AIR && world.rand.nextInt(2) == 1) {
                     OreSpawn.setBlockFast(world, cposx + var17, cposy + j - 1, cposz + k, OreSpawn.MyEnderPearlBlock, 0, 2);
                     if (world.rand.nextInt(3) == 1) {
                        OreSpawn.setBlockFast(world, cposx + var17, cposy + j - 2, cposz + k, OreSpawn.MyEnderPearlBlock, 0, 2);
                     }
                  }
               }

               if (j == 7) {
                  if (var17 == -1 || var17 == width + 1 || k == width + 1 | k == -1) {
                     bid = Blocks.BEDROCK;
                  }

                  if (bid == Blocks.BEDROCK && (var17 + k & 1) == 0) {
                     bid = Blocks.AIR;
                  }
               }

               if (bid != Blocks.AIR) {
                  OreSpawn.setBlockFast(world, cposx + var17, cposy + j, cposz + k, bid, 0, 2);
               }
            }
         }
      }

      this.makeAColumn(world, cposx - 2, cposy, cposz - 2, height + 1, 0);
      this.makeAColumn(world, cposx + width - 2, cposy, cposz - 2, height + 1, 1);
      this.makeAColumn(world, cposx - 2, cposy, cposz + width - 2, height + 1, 2);
      this.makeAColumn(world, cposx + width - 2, cposy, cposz + width - 2, height + 1, 3);
      int j = 8;

      for (int var18 = 1; var18 <= width - 1; var18++) {
         for (int k = 1; k <= width - 1; k++) {
            bid = Blocks.OBSIDIAN;
            if (var18 == width / 2 || k == width / 2 || var18 == k || var18 == width - k) {
               bid = Blocks.BEDROCK;
            }

            OreSpawn.setBlockFast(world, cposx + var18, cposy + j, cposz + k, bid, 0, 2);
         }
      }

      int var28 = 9;

      for (int var19 = -2; var19 <= 2; var19++) {
         for (int k = -2; k <= 2; k++) {
            bid = Blocks.LAVA;
            OreSpawn.setBlockFast(world, cposx + var19 + width / 2, cposy + var28, cposz + k + width / 2, bid, 0, 2);
         }
      }

      for (int m = -1; m <= 1; m++) {
         OreSpawn.setBlockFast(world, cposx + width / 2 + m, cposy + var28, cposz + width / 2 + 3, Blocks.BEDROCK, 0, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 + m, cposy + var28, cposz + width / 2 - 3, Blocks.BEDROCK, 0, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 + 3, cposy + var28, cposz + width / 2 + m, Blocks.BEDROCK, 0, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 - 3, cposy + var28, cposz + width / 2 + m, Blocks.BEDROCK, 0, 2);
      }

      OreSpawn.setBlockFast(world, cposx + width / 2 - 2, cposy + var28, cposz + width / 2 - 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 + 2, cposy + var28, cposz + width / 2 + 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 2, cposy + var28, cposz + width / 2 + 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 + 2, cposy + var28, cposz + width / 2 - 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28, cposz + width / 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + var28 + 1, cposz + width / 2), (Blocks.ENDER_CHEST).getStateFromMeta(2), 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 2, cposz + width / 2, Blocks.OBSIDIAN, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 3, cposz + width / 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 1, cposy + var28 + 3, cposz + width / 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 + 1, cposy + var28 + 3, cposz + width / 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 3, cposz + width / 2 - 1, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 3, cposz + width / 2 + 1, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 1, cposy + var28 + 4, cposz + width / 2, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 + 1, cposy + var28 + 4, cposz + width / 2, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 4, cposz + width / 2 - 1, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 4, cposz + width / 2 + 1, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 4, cposz + width / 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 5, cposz + width / 2, Blocks.BEDROCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + var28 + 6, cposz + width / 2, Blocks.DRAGON_EGG, 0, 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 5, cposy + var28, cposz + width / 2 + 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28, cposz + width / 2 + 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 + 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 + 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 5, cposy + var28, cposz + width / 2 + 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28, cposz + width / 2 + 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 + 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 + 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 5, cposy + var28, cposz + width / 2 - 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28, cposz + width / 2 - 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 - 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 5, cposy + var28 + 1, cposz + width / 2 - 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 5, cposy + var28, cposz + width / 2 - 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28, cposz + width / 2 - 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 - 5), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 5, cposy + var28 + 1, cposz + width / 2 - 5);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
      }

      var28 = 4;

      for (int var20 = 1; var20 <= width - 1; var20++) {
         for (int k = 1; k <= width - 1; k++) {
            bid = Blocks.AIR;
            if (var20 <= 5 || k <= 5 || var20 >= width - 5 || k >= width - 5) {
               bid = Blocks.BEDROCK;
            }

            if (bid != Blocks.AIR) {
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28, cposz + k, bid, 0, 2);
            }

            if (var20 == 5 && k >= 5 && k <= width - 5) {
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
            }

            if (var20 == width - 5 && k >= 5 && k <= width - 5) {
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
            }

            if (k == 5 && var20 >= 5 && var20 <= width - 5) {
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
            }

            if (k == width - 5 && var20 >= 5 && var20 <= width - 5) {
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 1, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 2, cposz + k, Blocks.IRON_BARS, 0, 2);
               OreSpawn.setBlockFast(world, cposx + var20, cposy + var28 + 3, cposz + k, Blocks.IRON_BARS, 0, 2);
            }
         }
      }

      bid = Blocks.BEDROCK;
      var28 = 3;
      int k = width / 2;
      int var21 = width - 6;

      for (int var44 = -1; var44 <= 1; var44++) {
         OreSpawn.setBlockFast(world, cposx + var21, cposy + var28, cposz + k + var44, bid, 0, 2);
      }

      var28 = 2;
      k = width / 2;
      var21 = width - 7;

      for (int var45 = -1; var45 <= 1; var45++) {
         OreSpawn.setBlockFast(world, cposx + var21, cposy + var28, cposz + k + var45, bid, 0, 2);
      }

      var28 = 1;
      k = width / 2;
      var21 = width - 8;

      for (int var46 = -1; var46 <= 1; var46++) {
         OreSpawn.setBlockFast(world, cposx + var21, cposy + var28, cposz + k + var46, bid, 0, 2);
      }

      var28 = 4;
      var21 = width - 5;

      for (int var47 = -1; var47 <= 1; var47++) {
         OreSpawn.setBlockFast(world, cposx + var21, cposy + var28 + 1, cposz + k + var47, Blocks.AIR, 0, 2);
         OreSpawn.setBlockFast(world, cposx + var21, cposy + var28 + 2, cposz + k + var47, Blocks.AIR, 0, 2);
         OreSpawn.setBlockFast(world, cposx + var21, cposy + var28 + 3, cposz + k + var47, Blocks.AIR, 0, 2);
      }

      var28 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + var28, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + var28, cposz + width / 2);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Reaper");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + var28 + 1, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + var28 + 1, cposz + width / 2);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ender Knight");
      }

      var28 = 5;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + var28, cposz + width / 2 - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + var28, cposz + width / 2 - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "CaveFisher");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + var28, cposz + width / 2 + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + var28, cposz + width / 2 + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "CaveFisher");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + var28, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(2), 2);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + var28, cposz + width / 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + var28, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + var28, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "CaveFisher");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + var28, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + var28, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "CaveFisher");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + var28, cposz + 1), (Blocks.CHEST).getStateFromMeta(3), 2);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + var28, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + var28, cposz + width - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + var28, cposz + width - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "CaveFisher");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + var28, cposz + width - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + var28, cposz + width - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "CaveFisher");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + var28, cposz + width - 1), (Blocks.CHEST).getStateFromMeta(4), 2);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + var28, cposz + width - 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(5));
      }
   }

   private void makeAColumn(World world, int cposx, int cposy, int cposz, int height, int dir) {
      int width = 4;
      int halfwidth = 2;
      int step = dir;

      for (int i = -2; i <= width + 2; i++) {
         for (int k = -2; k <= width + 2; k++) {
            int j = height + 2;
            OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, Blocks.OBSIDIAN, 0, 2);
         }
      }

      for (int var14 = -2; var14 <= width + 2; var14++) {
         for (int k = -2; k <= width + 2; k++) {
            Block bid = Blocks.AIR;
            if (var14 == -2 || var14 == width + 2 || k == width + 2 | k == -2) {
               bid = Blocks.OBSIDIAN;
            }

            int j = height + 3;
            if (bid != Blocks.AIR && (var14 + k & 1) == 0) {
               bid = Blocks.AIR;
            }

            OreSpawn.setBlockFast(world, cposx + var14, cposy + j, cposz + k, bid, 0, 2);
         }
      }

      int var15;
      for (var15 = 0; var15 <= width; var15++) {
         for (int k = 0; k <= width; k++) {
            for (int j = 1; j <= height + 2; j++) {
               Block bidx = Blocks.AIR;
               if (var15 == 0 || var15 == width || k == width | k == 0) {
                  bidx = Blocks.OBSIDIAN;
               }

               if ((j % 3 == 0 || j % 3 == 1) && j != height + 2 && bidx == Blocks.OBSIDIAN && (var15 == halfwidth || k == halfwidth)) {
                  bidx = Blocks.IRON_BARS;
               }

               OreSpawn.setBlockFast(world, cposx + var15, cposy + j, cposz + k, bidx, 0, 2);
            }
         }
      }

      if (dir == 0) {
         for (int j = 1; j <= 2; j++) {
            OreSpawn.setBlockFast(world, cposx + width, cposy + j, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width - 1, cposy + j, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width, cposy + j, cposz + width - 1, Blocks.AIR, 0, 2);
         }

         for (int var19 = 9; var19 <= 10; var19++) {
            OreSpawn.setBlockFast(world, cposx + width, cposy + var19, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width - 1, cposy + var19, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width, cposy + var19, cposz + width - 1, Blocks.AIR, 0, 2);
         }
      }

      if (dir == 1) {
         for (int j = 1; j <= 2; j++) {
            OreSpawn.setBlockFast(world, cposx, cposy + j, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + 1, cposy + j, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx, cposy + j, cposz + width - 1, Blocks.AIR, 0, 2);
         }

         for (int var21 = 9; var21 <= 10; var21++) {
            OreSpawn.setBlockFast(world, cposx, cposy + var21, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + 1, cposy + var21, cposz + width, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx, cposy + var21, cposz + width - 1, Blocks.AIR, 0, 2);
         }

         step = dir + 1;
         if (step > 3) {
            step = 0;
         }
      }

      if (dir == 2) {
         for (int j = 1; j <= 2; j++) {
            OreSpawn.setBlockFast(world, cposx + width, cposy + j, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width - 1, cposy + j, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width, cposy + j, cposz + 1, Blocks.AIR, 0, 2);
         }

         for (int var23 = 9; var23 <= 10; var23++) {
            OreSpawn.setBlockFast(world, cposx + width, cposy + var23, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width - 1, cposy + var23, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + width, cposy + var23, cposz + 1, Blocks.AIR, 0, 2);
         }

         if (++step > 3) {
            step = 0;
         }

         if (++step > 3) {
            step = 0;
         }
      }

      if (dir == 3) {
         for (int j = 1; j <= 2; j++) {
            OreSpawn.setBlockFast(world, cposx, cposy + j, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + 1, cposy + j, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx, cposy + j, cposz + 1, Blocks.AIR, 0, 2);
         }

         for (int var25 = 9; var25 <= 10; var25++) {
            OreSpawn.setBlockFast(world, cposx, cposy + var25, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx + 1, cposy + var25, cposz, Blocks.AIR, 0, 2);
            OreSpawn.setBlockFast(world, cposx, cposy + var25, cposz + 1, Blocks.AIR, 0, 2);
         }

         if (++step > 3) {
            step = 0;
         }

         if (++step > 3) {
            step = 0;
         }
      }

      Block bidxx = Blocks.NETHER_BRICK;
      int k = 0;

      for (int j = 1; j <= height + 2; j++) {
         if (step == 0) {
            k = 1;
            var15 = 1;
         }

         if (step == 1) {
            var15 = 1;
            k = 3;
         }

         if (step == 2) {
            var15 = 3;
            k = 3;
         }

         if (step == 3) {
            var15 = 3;
            k = 1;
         }

         if (++step > 3) {
            step = 0;
         }

         OreSpawn.setBlockFast(world, cposx + var15, cposy + j, cposz + k, bidxx, 0, 2);
      }
   }

   public void makeDamselInDistress(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int stuffdir = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.DamselContentsList;
      int length = 4;
      int width = 4;
      int height = 5;
      int var37 = 2;
      if (!world.isRemote) {
         for (int i = -width; i <= width; i++) {
            for (int j = -length; j <= length; j++) {
               for (int k = 0; k < height; k++) {
                  bid = Blocks.AIR;
                  int var35 = 0;
                  if (k == 0) {
                     bid = Blocks.COBBLESTONE;
                  }

                  if (i == -width || i == width) {
                     bid = Blocks.COBBLESTONE;
                  }

                  if (j == -length || j == length) {
                     bid = Blocks.COBBLESTONE;
                  }

                  if (bid == Blocks.COBBLESTONE && world.rand.nextInt(8) == 1) {
                     bid = Blocks.MOSSY_COBBLESTONE;
                  }

                  if ((k == 1 || k == 2 || k == 3) && (i == 0 || i == -1 || i == 1) && j == -length) {
                     var35 = 0;
                     bid = Blocks.AIR;
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + k, cposz + j, bid, var35, 2);
               }
            }
         }

         int var36 = 0;

         for (int var19 = -width + 1; var19 <= width - 1; var19++) {
            for (int j = -length; j <= length - 1; j++) {
               bid = Blocks.COBBLESTONE;
               if (world.rand.nextInt(8) == 1) {
                  bid = Blocks.MOSSY_COBBLESTONE;
               }

               OreSpawn.setBlockFast(world, cposx + var19, cposy + height, cposz + j, bid, var36, 2);
            }
         }

         for (int var20 = -width + 2; var20 <= width - 2; var20++) {
            for (int j = -length; j <= length - 2; j++) {
               int k = height + 1;
               bid = Blocks.COBBLESTONE;
               if (world.rand.nextInt(8) == 1) {
                  bid = Blocks.MOSSY_COBBLESTONE;
               }

               OreSpawn.setBlockFast(world, cposx + var20, cposy + k, cposz + j, bid, var36, 2);
            }
         }

         int k = height;
         int j = -length;

         for (int m = width; m >= 0; m--) {
            for (int var21 = m; var21 >= 0; var21--) {
               bid = Blocks.COBBLESTONE;
               if (world.rand.nextInt(8) == 1) {
                  bid = Blocks.MOSSY_COBBLESTONE;
               }

               OreSpawn.setBlockFast(world, cposx + var21, cposy + k, cposz + j, bid, var36, 2);
               bid = Blocks.COBBLESTONE;
               if (world.rand.nextInt(8) == 1) {
                  bid = Blocks.MOSSY_COBBLESTONE;
               }

               OreSpawn.setBlockFast(world, cposx - var21, cposy + k, cposz + j, bid, var36, 2);
            }

            k++;
         }

         for (int var22 = -width + 1; var22 < width; var22++) {
            for (int var26 = 1; var26 < height; var26++) {
               k = length - 3;
               OreSpawn.setBlockFast(world, cposx - var22, cposy + var26, cposz + k, Blocks.IRON_BARS, 0, 2);
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - width + 1, cposy + 1, cposz - length + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - width + 1, cposy + 1, cposz - length + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 1, cposy + 1, cposz - length + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width - 1, cposy + 1, cposz - length + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 1, cposy + 1, cposz + length - 1), (Blocks.CHEST).getStateFromMeta(2), 2);
         chest = this.getChestTileEntity(world, cposx + width - 1, cposy + 1, cposz + length - 1);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
         }

         Entity var8 = null;
         var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create("Girlfriend", world);
         if (var8 != null) {
            var8.setLocationAndAngles(cposx - width + 2, cposy + 1, cposz + length - 1, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(var8);
         }
      }
   }

   public void makeIncaPyramid(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      int width = 21;
      int depth = 11;
      int height = 9;
      int basewidth = 41;
      int basedepth = 31;
      int baseheight = 10;
      if (!world.isRemote) {
         for (int j = 0; j < baseheight; j++) {
            for (int i = 0; i < basewidth - j * 2; i++) {
               for (int k = 0; k < basedepth - j * 2; k++) {
                  int var70 = 0;
                  bid = Blocks.AIR;
                  if (i == 0 || k == 0 || i == basewidth - j * 2 - 1 || k == basedepth - j * 2 - 1) {
                     bid = Blocks.STONE;
                     if (world.rand.nextInt(2) == 0) {
                        bid = Blocks.COBBLESTONE;
                     }

                     if (world.rand.nextInt(4) == 0) {
                        bid = Blocks.MOSSY_COBBLESTONE;
                     }
                  }

                  if (j == 0) {
                     bid = Blocks.STONEBRICK;
                  }

                  if (k == 1 && j % 3 == 2 && i != 0 && i != basewidth - j * 2 - 1) {
                     bid = Blocks.TORCH;
                     var70 = 3;
                  }

                  OreSpawn.setBlockFast(world, cposx + i + j, cposy + j, cposz + k + j, bid, var70, 2);
                  if (k == basedepth - j * 2 - 1 && j % 3 == 2 && i != 0 && i != basewidth - j * 2 - 1) {
                     var70 = 4;
                     OreSpawn.setBlockFast(world, cposx + i + j, cposy + j, cposz + k + j - 1, Blocks.TORCH, var70, 2);
                  }
               }
            }
         }

         int var72 = 0;

         for (int m = 0; m < baseheight * 2 - 1; m++) {
            int i = -baseheight + m;

            for (int p = -2; p <= 2; p++) {
               int k = basedepth / 2;
               k += p;
               int var33 = m / 2;
               if (p >= -1 && p <= 1) {
                  if (m % 2 == 1) {
                     bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var33 + 1, cposz + k)).getBlock();
                     if (bid == Blocks.AIR) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var33 + 1, cposz + k, Blocks.STONE_SLAB, var72, 2);
                     }
                  }
               } else {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var33 + 1, cposz + k)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, cposx + i, cposy + var33 + 1, cposz + k, Blocks.STONEBRICK, var72, 2);
                     if (m == 0 || m == baseheight * 2 - 2) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var33 + 2, cposz + k, Blocks.TORCH, var72, 2);
                     }
                  }
               }

               while (var33 >= 0) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var33, cposz + k)).getBlock();
                  if (bid != Blocks.AIR) {
                     break;
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + var33, cposz + k, Blocks.STONE, var72, 2);
                  var33--;
               }
            }
         }

         var72 = 0;

         for (int var49 = 0; var49 < baseheight * 2 - 1; var49++) {
            int i = basewidth + baseheight - var49 - 1;

            for (int p = -2; p <= 2; p++) {
               int k = basedepth / 2;
               k += p;
               int var34 = var49 / 2;
               if (p >= -1 && p <= 1) {
                  if (var49 % 2 == 1) {
                     bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var34 + 1, cposz + k)).getBlock();
                     if (bid == Blocks.AIR) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var34 + 1, cposz + k, Blocks.STONE_SLAB, var72, 2);
                     }
                  }
               } else {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var34 + 1, cposz + k)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, cposx + i, cposy + var34 + 1, cposz + k, Blocks.STONEBRICK, var72, 2);
                     if (var49 == 0 || var49 == baseheight * 2 - 2) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var34 + 2, cposz + k, Blocks.TORCH, var72, 2);
                     }
                  }
               }

               while (var34 >= 0) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var34, cposz + k)).getBlock();
                  if (bid != Blocks.AIR) {
                     break;
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + var34, cposz + k, Blocks.STONE, var72, 2);
                  var34--;
               }
            }
         }

         var72 = 0;

         for (int var50 = 0; var50 < baseheight * 2 - 1; var50++) {
            int k = -baseheight + var50;

            for (int p = -2; p <= 2; p++) {
               int i = basewidth / 2;
               i += p;
               int var35 = var50 / 2;
               if (p >= -1 && p <= 1) {
                  if (var50 % 2 == 1) {
                     bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var35 + 1, cposz + k)).getBlock();
                     if (bid == Blocks.AIR) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var35 + 1, cposz + k, Blocks.STONE_SLAB, var72, 2);
                     }
                  }
               } else {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var35 + 1, cposz + k)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, cposx + i, cposy + var35 + 1, cposz + k, Blocks.STONEBRICK, var72, 2);
                     if (var50 == 0 || var50 == baseheight * 2 - 2) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var35 + 2, cposz + k, Blocks.TORCH, var72, 2);
                     }
                  }
               }

               while (var35 >= 0) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var35, cposz + k)).getBlock();
                  if (bid != Blocks.AIR) {
                     break;
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + var35, cposz + k, Blocks.STONE, var72, 2);
                  var35--;
               }
            }
         }

         var72 = 0;

         for (int var51 = 0; var51 < baseheight * 2 - 1; var51++) {
            int k = basedepth + baseheight - var51 - 1;

            for (int p = -2; p <= 2; p++) {
               int i = basewidth / 2;
               i += p;
               int var36 = var51 / 2;
               if (p >= -1 && p <= 1) {
                  if (var51 % 2 == 1) {
                     bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var36 + 1, cposz + k)).getBlock();
                     if (bid == Blocks.AIR) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var36 + 1, cposz + k, Blocks.STONE_SLAB, var72, 2);
                     }
                  }
               } else {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var36 + 1, cposz + k)).getBlock();
                  if (bid == Blocks.AIR) {
                     OreSpawn.setBlockFast(world, cposx + i, cposy + var36 + 1, cposz + k, Blocks.STONEBRICK, var72, 2);
                     if (var51 == 0 || var51 == baseheight * 2 - 2) {
                        OreSpawn.setBlockFast(world, cposx + i, cposy + var36 + 2, cposz + k, Blocks.TORCH, var72, 2);
                     }
                  }
               }

               while (var36 >= 0) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var36, cposz + k)).getBlock();
                  if (bid != Blocks.AIR) {
                     break;
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + var36, cposz + k, Blocks.STONE, var72, 2);
                  var36--;
               }
            }
         }

         cposx += baseheight;
         cposy += baseheight;
         cposz += baseheight;

         for (int var37 = 0; var37 < height; var37++) {
            for (int i = 0; i < width; i++) {
               for (int k = 0; k < depth; k++) {
                  bid = Blocks.AIR;
                  var72 = 0;
                  if (i == 0 || k == 0 || i == width - 1 || k == depth - 1) {
                     bid = Blocks.STONE;
                     if (world.rand.nextInt(2) == 0) {
                        bid = Blocks.COBBLESTONE;
                     }

                     if (world.rand.nextInt(4) == 0) {
                        bid = Blocks.MOSSY_COBBLESTONE;
                     }
                  }

                  if (var37 == 0 || var37 == height - 1) {
                     bid = Blocks.STONEBRICK;
                  }

                  if (var37 == 1 || var37 == 2 || var37 == 3) {
                     if ((k == 0 || k == depth - 1) && i >= width / 2 - 1 && i <= width / 2 + 1) {
                        if (var37 == 3) {
                           bid = Blocks.OAK_FENCE;
                        } else {
                           bid = Blocks.AIR;
                        }
                     }

                     if ((i == 0 || i == width - 1) && k >= depth / 2 - 1 && k <= depth / 2 + 1) {
                        if (var37 == 3) {
                           bid = Blocks.OAK_FENCE;
                        } else {
                           bid = Blocks.AIR;
                        }
                     }
                  }

                  if ((var37 == height - 3 || var37 == height - 2) && (i + k) % 2 == 1) {
                     if (var37 == height - 3) {
                        if (bid != Blocks.AIR) {
                           bid = Blocks.LIT_REDSTONE_LAMP;
                        }
                     } else {
                        bid = Blocks.AIR;
                     }
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + var37, cposz + k, bid, var72, 2);
               }
            }
         }

         Block var69 = Blocks.STONE_SLAB;
         var72 = 0;
         int var38 = height;

         for (int i = -1; i <= width; i++) {
            for (int k = -1; k <= depth; k++) {
               if ((i == -1 || k == -1 || i == width || k == depth) && (i + k & 1) == 1) {
                  OreSpawn.setBlockFast(world, cposx + i, cposy + var38, cposz + k, var69, var72, 2);
               }
            }
         }

         this.makepoolalter(world, cposx + 1, cposy, cposz + 1);
         this.makepoolalter(world, cposx + width - 2, cposy, cposz + depth - 2);
         this.makepoolalter(world, cposx + 1, cposy, cposz + depth - 2);
         this.makepoolalter(world, cposx + width - 2, cposy, cposz + 1);
         this.makepoolalter(world, cposx + width / 2, cposy, cposz + depth / 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + 2, cposz + depth / 2 - 1), (OreSpawn.CreeperRepellent).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + 2, cposz + depth / 2 + 1), (OreSpawn.CreeperRepellent).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + 2, cposz + depth / 2 + 1), (OreSpawn.CreeperRepellent).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + 2, cposz + depth / 2 - 1), (OreSpawn.CreeperRepellent).getStateFromMeta(0), 2);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 2, cposy + 1, cposz + depth / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 2, cposy + 1, cposz + depth / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Molenoid");
         }

         OreSpawn.setBlockFast(world, cposx + width / 2 + 2, cposy + 1, cposz + depth / 2, Blocks.TRAPDOOR, 3, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 + 2, cposy, cposz + depth / 2, Blocks.AIR, 0, 2);
         int var32 = cposx + width / 2 + 2;
         int kx = cposz + depth / 2;

         for (int var39 = 1; var39 < baseheight; var39++) {
            OreSpawn.setBlockFast(world, var32, cposy - var39, kx + 1, Blocks.COBBLESTONE, 0, 2);
            OreSpawn.setBlockFast(world, var32, cposy - var39, kx, Blocks.LADDER, 2, 2);
         }

         this.makeincagraves(world, cposx - baseheight, cposy - baseheight, cposz - baseheight, basewidth, basedepth);
      }
   }

   private void makepoolalter(World world, int cposx, int cposy, int cposz) {
      for (int i = -1; i <= 1; i++) {
         for (int k = -1; k <= 1; k++) {
            OreSpawn.setBlockFast(world, cposx + i, cposy + 1, cposz + k, Blocks.COBBLESTONE, 0, 2);
         }
      }

      OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz, Blocks.WATER, 0, 2);
   }

   private void makeincagraves(World world, int cposx, int cposy, int cposz, int width, int depth) {
      for (int i = 5; i < width - 5; i += 6) {
         this.makeincagrave(world, cposx + i, cposy, cposz + 5, 1);
      }

      for (int var8 = 5; var8 < width - 5; var8 += 6) {
         this.makeincagrave(world, cposx + var8, cposy, cposz + 10, 1);
      }

      for (int var9 = 5; var9 < width - 5; var9 += 6) {
         this.makeincagrave(world, cposx + var9, cposy, cposz + 20, 3);
      }

      for (int var10 = 5; var10 < width - 5; var10 += 6) {
         this.makeincagrave(world, cposx + var10, cposy, cposz + 25, 3);
      }
   }

   private void makeincagrave(World world, int cposx, int cposy, int cposz, int dir) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.IncaPyramidContentsList;
      if (dir == 1) {
         OreSpawn.setBlockFast(world, cposx - 1, cposy, cposz, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy + 1, cposz, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy, cposz + 1, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy + 1, cposz + 1, Blocks.YELLOW_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy, cposz + 2, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy + 1, cposz + 2, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy, cposz, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy, cposz + 1, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz + 1, Blocks.YELLOW_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy, cposz + 2, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz + 2, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz, Blocks.STONE, 0, 2);
         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz + 1, Blocks.STONE_SLAB, 0, 2);
         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz + 2, Blocks.STONE_SLAB, 0, 2);
         if (world.rand.nextInt(3) == 1) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost");
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz - 1), (Blocks.CHEST).getStateFromMeta(2), 2);
         chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
         }
      }

      if (dir == 3) {
         OreSpawn.setBlockFast(world, cposx - 1, cposy, cposz, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy + 1, cposz, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy, cposz - 1, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy + 1, cposz - 1, Blocks.YELLOW_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy, cposz - 2, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx - 1, cposy + 1, cposz - 2, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy, cposz, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy, cposz - 1, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz - 1, Blocks.YELLOW_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy, cposz - 2, Blocks.GRASS, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz - 2, Blocks.RED_FLOWER, 0, 2);
         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz, Blocks.STONE, 0, 2);
         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz - 1, Blocks.STONE_SLAB, 0, 2);
         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz - 2, Blocks.STONE_SLAB, 0, 2);
         if (world.rand.nextInt(3) == 1) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost");
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(2), 2);
         chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz + 1);
         if (chest != null) {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
         }
      }
   }

   public void makeRobotLab(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int width = 10;
      int length = 20;
      int height = 5;
      if (!world.isRemote) {
         for (int j = 0; j <= height; j++) {
            for (int i = 0; i < width; i++) {
               for (int k = 0; k < length; k++) {
                  bid = Blocks.AIR;
                  if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                     bid = Blocks.QUARTZ_BLOCK;
                  }

                  if (j == 0) {
                     bid = Blocks.QUARTZ_BLOCK;
                     if (i == width / 2 || i == width / 2 - 1) {
                        bid = Blocks.IRON_BLOCK;
                     }
                  }

                  if (j == height) {
                     bid = Blocks.QUARTZ_BLOCK;
                     if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                        bid = Blocks.AIR;
                     }
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
               }
            }
         }

         OreSpawn.setBlockFast(world, cposx + width / 2, cposy + 1, cposz, Blocks.AIR, 0, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2, cposy + 2, cposz, Blocks.AIR, 0, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 - 1, cposy + 1, cposz, Blocks.AIR, 0, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 - 1, cposy + 2, cposz, Blocks.AIR, 0, 2);
          this.placeDoorBlock(world, cposx + width / 2, cposy + 1, cposz, 3, Blocks.IRON_DOOR);
          this.placeDoorBlock(world, cposx + width / 2 - 1, cposy + 1, cposz, 3, Blocks.IRON_DOOR);
         OreSpawn.setBlockFast(world, cposx + width / 2 - 2, cposy + 2, cposz - 1, Blocks.STONE_BUTTON, 4, 2);
         OreSpawn.setBlockFast(world, cposx + width / 2 + 1, cposy + 2, cposz - 1, Blocks.STONE_BUTTON, 4, 2);
         this.makerobomain(world, cposx, cposy, cposz + length - 1);
         this.makerobopillar(world, cposx, cposy, cposz + length / 3, 0);
         this.makerobopillar(world, cposx, cposy, cposz + length * 2 / 3, 0);
         this.makerobopillar(world, cposx, cposy, cposz + (length - 1), 0);
         this.makerobopillar(world, cposx + width - 1, cposy, cposz + length / 3, 1);
         this.makerobopillar(world, cposx + width - 1, cposy, cposz + length * 2 / 3, 1);
         this.makerobopillar(world, cposx + width - 1, cposy, cposz + (length - 1), 1);
      }
   }

   private void makerobopillar(World world, int cposx, int cposy, int cposz, int dir) {
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int j = 0; j < 5; j++) {
         for (int i = -1; i < 2; i++) {
            for (int k = -1; k < 2; k++) {
               Block bid = Blocks.QUARTZ_BLOCK;
               if (j == 2 || j == 3) {
                  if (k == 0 && (i == -1 || i == 1)) {
                     bid = Blocks.REDSTONE_BLOCK;
                  }

                  if (i == 0 && (k == -1 || k == 1)) {
                     bid = Blocks.REDSTONE_BLOCK;
                  }
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
         }
      }

      if (dir == 0) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 1, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Robo-Sniper");
         }
      }

      if (dir == 1) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 1, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Robo-Sniper");
         }
      }
   }

   public void makerobomain(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int width = 30;
      int length = 30;
      int height = 9;
      cposx -= 10;

      for (int j = 0; j <= height; j++) {
         for (int i = 0; i < width; i++) {
            for (int k = 0; k < length; k++) {
               bid = Blocks.AIR;
               if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               if (j == 0) {
                  bid = Blocks.QUARTZ_BLOCK;
                  if (i == width / 2 || i == width / 2 - 1) {
                     bid = Blocks.IRON_BLOCK;
                  }
               }

               if (j == height) {
                  bid = Blocks.QUARTZ_BLOCK;
                  if (i == 0 || k == 0 || i == width - 1 || k == length - 1) {
                     bid = Blocks.AIR;
                  }
               }

               if ((j == 1 || j == 2 || j == 3) && k == 0 && i >= width / 3 && i < width * 2 / 3) {
                  bid = Blocks.AIR;
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
         }
      }

      this.makeroboaltar(world, cposx + width / 2 - 4, cposy, cposz + 6);
      this.makeroborailway(world, cposx + 3, cposy, cposz + 10);
      this.makeroboassemblyline(world, cposx + width - 4, cposy, cposz + 4);
      this.makerobotreasureroom(world, cposx + 9, cposy, cposz + 18);
      this.makerobotower(world, cposx + width / 2 - 6, cposy + height, cposz + length / 2 - 6);
   }

   public void makerobotower(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;

      for (int j = 0; j < 2; j++) {
         for (int i = 0; i < 12; i++) {
            for (int k = 0; k < 12; k++) {
               bid = Blocks.AIR;
               if (j == 1) {
                  if (i == 0 || k == 0 || i == 11 || k == 11) {
                     bid = Blocks.IRON_BARS;
                  }

                  if (i == 0 && (k == 0 || k == 11)) {
                     bid = Blocks.REDSTONE_BLOCK;
                  }

                  if (i == 11 && (k == 0 || k == 11)) {
                     bid = Blocks.REDSTONE_BLOCK;
                  }
               }

               if (j == 0) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
         }
      }

      this.makerobopillar(world, cposx + 4, cposy + 1, cposz + 4, 1);
      this.makerobopillar(world, cposx + 7, cposy + 1, cposz + 7, 0);
      this.makerobopillar(world, cposx + 4, cposy + 1, cposz + 7, 1);
      this.makerobopillar(world, cposx + 7, cposy + 1, cposz + 4, 0);

      for (int var11 = 5; var11 < 35; var11++) {
         for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 3; k++) {
               bid = Blocks.AIR;
               if (var11 < 15) {
                  bid = Blocks.QUARTZ_BLOCK;
               } else if (var11 < 25) {
                  bid = Blocks.QUARTZ_BLOCK;
                  if (k == 2) {
                     bid = Blocks.IRON_BARS;
                  }
               } else {
                  bid = Blocks.QUARTZ_BLOCK;
                  if (k == 1) {
                     bid = Blocks.IRON_BARS;
                  }

                  if (k == 2) {
                     bid = Blocks.AIR;
                  }
               }

               OreSpawn.setBlockFast(world, cposx + i + 5, cposy + var11, cposz + k + 5, bid, 0, 2);
            }
         }
      }
   }

   public void makeroboaltar(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      TileEntityMobSpawner tileentitymobspawner = null;
      bid = Blocks.IRON_BLOCK;

      for (int i = 0; i < 8; i++) {
         for (int k = 0; k < 8; k++) {
            OreSpawn.setBlockFast(world, cposx + i, cposy, cposz + k, bid, 0, 2);
         }
      }

      bid = Blocks.QUARTZ_BLOCK;

      for (int var10 = 0; var10 < 6; var10++) {
         for (int k = 0; k < 6; k++) {
            OreSpawn.setBlockFast(world, cposx + var10 + 1, cposy + 1, cposz + k + 1, bid, 0, 2);
         }
      }

      OreSpawn.setBlockFast(world, cposx + 2, cposy + 1, cposz + 2, Blocks.REDSTONE_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 2, cposy + 2, cposz + 2, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 5, cposy + 1, cposz + 5, Blocks.REDSTONE_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 5, cposy + 2, cposz + 5, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 5, cposy + 1, cposz + 2, Blocks.REDSTONE_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 5, cposy + 2, cposz + 2, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 2, cposy + 1, cposz + 5, Blocks.REDSTONE_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 2, cposy + 2, cposz + 5, Blocks.TORCH, 0, 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 3, cposy + 2, cposz + 3), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 3, cposy + 2, cposz + 3);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Robo-Pounder");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 4, cposy + 2, cposz + 4), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 4, cposy + 2, cposz + 4);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Robo-Pounder");
      }
   }

   public void makeroborailway(World world, int cposx, int cposy, int cposz) {
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 0, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 0, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 1, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 1, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 2, Blocks.GOLDEN_RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz + 2, Blocks.LEVER, 5, 2);
      OreSpawn.setBlockFast(world, cposx + 2, cposy + 1, cposz + 2, Blocks.LEVER, 5, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 2, Blocks.GOLDEN_RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 3, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 3, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 4, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 4, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 5, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 5, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 6, Blocks.GOLDEN_RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz + 6, Blocks.LEVER, 5, 2);
      OreSpawn.setBlockFast(world, cposx + 2, cposy + 1, cposz + 6, Blocks.LEVER, 5, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 6, Blocks.GOLDEN_RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 7, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 7, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 8, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 8, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 9, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 9, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 10, Blocks.GOLDEN_RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz + 10, Blocks.LEVER, 5, 2);
      OreSpawn.setBlockFast(world, cposx + 2, cposy + 1, cposz + 10, Blocks.LEVER, 5, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 10, Blocks.GOLDEN_RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 11, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 11, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 0, cposy + 1, cposz + 12, Blocks.RAIL, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 3, cposy + 1, cposz + 12, Blocks.RAIL, 0, 2);
   }

   public void makeroboassemblyline(World world, int cposx, int cposy, int cposz) {
      for (int k = 0; k < 24; k++) {
         if (k % 3 == 1) {
            OreSpawn.setBlockFast(world, cposx - 2, cposy + 1, cposz + k, Blocks.QUARTZ_STAIRS, 1, 2);
            OreSpawn.setBlockFast(world, cposx, cposy + 2, cposz + k, Blocks.STICKY_PISTON, 3, 2);
            OreSpawn.setBlockFast(world, cposx, cposy + 3, cposz + k, Blocks.CARPET, 0, 2);
         }

         if (k % 3 == 0) {
            OreSpawn.setBlockFast(world, cposx, cposy + 2, cposz + k, Blocks.LEVER, 13, 2);
         }

         OreSpawn.setBlockFast(world, cposx, cposy + 1, cposz + k, Blocks.QUARTZ_BLOCK, 0, 2);
         OreSpawn.setBlockFast(world, cposx + 1, cposy + 1, cposz + k, Blocks.QUARTZ_BLOCK, 0, 2);
      }
   }

   public void makerobotreasureroom(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      TileEntityMobSpawner tileentitymobspawner = null;
      chestContents = this.RobotContentsList;

      for (int j = 1; j < 7; j++) {
         for (int i = 0; i < 12; i++) {
            for (int k = 0; k < 8; k++) {
               bid = Blocks.AIR;
               if (i == 0 || k == 0 || i == 11 || k == 7) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               if (j == 2 && i == 11) {
                  bid = Blocks.IRON_BARS;
               }

               if (j == 3 && bid != Blocks.AIR) {
                  bid = Blocks.IRON_BARS;
               }

               if ((j == 1 || j == 2 || j == 3) && k == 0 && (i == 1 || i == 2)) {
                  bid = Blocks.AIR;
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 10, cposy + 1, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 10, cposy + 1, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Robo-Warrior");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 8, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(2), 2);
      chest = this.getChestTileEntity(world, cposx + 8, cposy + 1, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 6, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(2), 2);
      chest = this.getChestTileEntity(world, cposx + 6, cposy + 1, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
      }
   }

   public void makeKingAltar(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int width = 51;
      int length = 51;
      int height = 48;
      if (!world.isRemote) {
         for (int j = 0; j <= height + 10; j++) {
            for (int i = -5; i < width + 5; i++) {
               for (int k = -5; k < length + 5; k++) {
                  bid = Blocks.AIR;
                  OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
               }
            }
         }

         int var17 = 0;

         for (int i = 0; i < width; i++) {
            for (int k = 0; k < length; k++) {
               Block var24 = Blocks.GRASS;
               OreSpawn.setBlockFast(world, cposx + i, cposy + var17, cposz + k, var24, 0, 2);

               for (int v = 1; v < 10; v++) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var17 - v, cposz + k)).getBlock();
                  if (bid == Blocks.AIR || bid == Blocks.TALLGRASS || bid == Blocks.WATER) {
                     bid = Blocks.DIRT;
                     OreSpawn.setBlockFast(world, cposx + i, cposy + var17 - v, cposz + k, bid, 0, 2);
                  }
               }
            }
         }

         this.makekingcolumn(world, cposx + 1, cposy + 1, cposz + 1);
         this.makekingcolumn(world, cposx + width - 8, cposy + 1, cposz + length - 8);
         this.makekingcolumn(world, cposx + 1, cposy + 1, cposz + length - 8);
         this.makekingcolumn(world, cposx + width - 8, cposy + 1, cposz + 1);
         var17 = height - 1;
         bid = Blocks.QUARTZ_BLOCK;

         for (int var15 = 0; var15 < width; var15++) {
            for (int k = 0; k < length; k++) {
               OreSpawn.setBlockFast(world, cposx + var15, cposy + var17, cposz + k, bid, 0, 2);
            }
         }

         var17 = height;
         bid = Blocks.QUARTZ_BLOCK;

         for (int var16 = -1; var16 <= width; var16++) {
            for (int k = -1; k <= length; k++) {
               OreSpawn.setBlockFast(world, cposx + var16, cposy + var17, cposz + k, bid, 0, 2);
            }
         }

         this.makekingbackground(world, cposx + 4, cposy + 10, cposz + 9);
         this.makekingcenteraltar(world, cposx + width / 2, cposy, cposz + length / 2);
      }
   }

   private void makekingcolumn(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int width = 5;
      int length = 5;
      int height = 44;
      if (!world.isRemote) {
         int j = 0;
         bid = Blocks.QUARTZ_BLOCK;

         for (int i = 0; i < width + 2; i++) {
            for (int k = 0; k < length + 2; k++) {
               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, meta, 2);
               OreSpawn.setBlockFast(world, cposx + i, cposy + j + height + 1, cposz + k, bid, meta, 2);
            }
         }

         cposx++;
         cposz++;
         cposy++;

         for (int var18 = 0; var18 < height; var18++) {
            for (int var17 = 0; var17 < width; var17++) {
               for (int k = 0; k < length; k++) {
                  bid = Blocks.AIR;
                  if (var17 == 0 || k == 0 || var17 == width - 1 || k == length - 1) {
                     bid = Blocks.QUARTZ_BLOCK;
                  }

                  if (var18 % 4 == 0 && bid != Blocks.AIR && (var17 == 2 || k == 2)) {
                     bid = Blocks.GOLD_BLOCK;
                  }

                  if (var18 % 4 == 1 && bid != Blocks.AIR) {
                     if (var17 == 1 || k == 1) {
                        bid = Blocks.GOLD_BLOCK;
                     }

                     if (var17 == 3 || k == 3) {
                        bid = Blocks.GOLD_BLOCK;
                     }
                  }

                  if (var18 % 4 == 2 && bid != Blocks.AIR) {
                     if (var17 == 1 || k == 1) {
                        bid = Blocks.GOLD_BLOCK;
                     }

                     if (var17 == 3 || k == 3) {
                        bid = Blocks.GOLD_BLOCK;
                     }

                     if (var17 == 2 || k == 2) {
                        bid = Blocks.EMERALD_BLOCK;
                     }
                  }

                  if (var18 % 4 == 3 && bid != Blocks.AIR) {
                     if (var17 == 1 || k == 1) {
                        bid = Blocks.GOLD_BLOCK;
                     }

                     if (var17 == 3 || k == 3) {
                        bid = Blocks.GOLD_BLOCK;
                     }
                  }

                  int var22 = 0;
                  if (bid == Blocks.QUARTZ_BLOCK) {
                     var22 = 2;
                  }

                  OreSpawn.setBlockFast(world, cposx + var17, cposy + var18, cposz + k, bid, var22, 2);
               }
            }
         }
      }
   }

   private void makekingbackground(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int curz = 0;
      int cury = 0;
      int height = 33;
      int width = 33;
      bid = Blocks.STONE;

      for (int m = 0; m < this.king.length; m++) {
         int v = this.king[m];
         if (v < 0) {
            for (bid = Blocks.STONE; curz < width; curz++) {
               OreSpawn.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
            }

            cury++;
            curz = 0;
         } else {
            for (int n = 0; n < v; n++) {
               OreSpawn.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
               curz++;
            }

            if (bid == Blocks.STONE) {
               bid = Blocks.QUARTZ_BLOCK;
            } else {
               bid = Blocks.STONE;
            }
         }
      }

      for (int i = 0; i < width; i++) {
         OreSpawn.setBlockFast(world, cposx, cposy - 1, cposz + i, Blocks.GOLD_BLOCK, 0, 2);
      }

      for (int var17 = 0; var17 < width; var17++) {
         OreSpawn.setBlockFast(world, cposx, cposy + height, cposz + var17, Blocks.GOLD_BLOCK, 0, 2);
      }

      for (int var18 = -1; var18 <= height; var18++) {
         OreSpawn.setBlockFast(world, cposx, cposy + var18, cposz - 1, Blocks.GOLD_BLOCK, 0, 2);
      }

      for (int var19 = -1; var19 <= height; var19++) {
         OreSpawn.setBlockFast(world, cposx, cposy + var19, cposz + width, Blocks.GOLD_BLOCK, 0, 2);
      }

      OreSpawn.setBlockFast(world, cposx, cposy - 2, cposz - 2, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 1, cposz + width + 1, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy - 2, cposz + width + 1, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 1, cposz - 2, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy - 1, cposz - 2, OreSpawn.CrystalTorch, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 2, cposz + width + 1, OreSpawn.CrystalTorch, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy - 1, cposz + width + 1, OreSpawn.CrystalTorch, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 2, cposz - 2, OreSpawn.CrystalTorch, 0, 2);
   }

   private void makekingcenteraltar(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int width = 10;
      int length = 10;
      int j = 0;
      bid = Blocks.QUARTZ_BLOCK;

      for (int i = -width; i <= width; i++) {
         for (int k = -length; k <= length; k++) {
            OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
         }
      }

      int var62 = 6;
      int var74 = 20;
      int var25 = 0;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var13 = -var62; var13 <= var62; var13++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var13, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 20;
      var74 = 6;
      var25 = 0;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var14 = -var62; var14 <= var62; var14++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var14, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 8;
      var74 = 8;
      var25 = 1;

      for (int var15 = -var62; var15 <= var62; var15++) {
         for (int k = -var74; k <= var74; k++) {
            bid = Blocks.QUARTZ_BLOCK;
            OreSpawn.setBlockFast(world, cposx + var15, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 4;
      var74 = 18;
      var25 = 1;

      for (int var16 = -var62; var16 <= var62; var16++) {
         for (int k = -var74; k <= var74; k++) {
            bid = Blocks.QUARTZ_BLOCK;
            if (var16 == var62 && (k == -var74 || k == var74)) {
               bid = Blocks.LAPIS_BLOCK;
            }

            if (var16 == -var62 && (k == -var74 || k == var74)) {
               bid = Blocks.LAPIS_BLOCK;
            }

            OreSpawn.setBlockFast(world, cposx + var16, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 18;
      var74 = 4;
      var25 = 1;

      for (int var17 = -var62; var17 <= var62; var17++) {
         for (int k = -var74; k <= var74; k++) {
            bid = Blocks.QUARTZ_BLOCK;
            if (var17 == var62 && (k == -var74 || k == var74)) {
               bid = Blocks.LAPIS_BLOCK;
            }

            if (var17 == -var62 && (k == -var74 || k == var74)) {
               bid = Blocks.LAPIS_BLOCK;
            }

            OreSpawn.setBlockFast(world, cposx + var17, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 7;
      var74 = 7;
      var25 = 2;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var18 = -var62; var18 <= var62; var18++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var18, cposy + var25, cposz + k, bid, 0, 2);
            if (var18 == var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var18, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }

            if (var18 == -var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var18, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }
         }
      }

      var62 = 3;
      var74 = 17;
      var25 = 2;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var19 = -var62; var19 <= var62; var19++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var19, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 17;
      var74 = 3;
      var25 = 2;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var20 = -var62; var20 <= var62; var20++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var20, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 6;
      var74 = 6;
      var25 = 3;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var21 = -var62; var21 <= var62; var21++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var21, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 2;
      var74 = 16;
      var25 = 3;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var22 = -var62; var22 <= var62; var22++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var22, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 16;
      var74 = 2;
      var25 = 3;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var23 = -var62; var23 <= var62; var23++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var23, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 2;
      var74 = 2;
      var25 = 4;
      bid = Blocks.QUARTZ_BLOCK;

      for (int var24 = -var62; var24 <= var62; var24++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var24, cposy + var25, cposz + k, bid, 0, 2);
            if (var24 == var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var24, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }

            if (var24 == -var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var24, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var25, cposz), (Blocks.CHEST).getDefaultState(), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var25, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx, cposy + var25, cposz)).getBlock().getStateFromMeta(2), 3);
      TileEntityChest chest = this.getChestTileEntity(world, cposx, cposy + var25, cposz);
      if (chest != null) {
         chest.setInventorySlotContents(13, new ItemStack(OreSpawn.TheKingEgg));
      }
   }

   public void makeLeonNest(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int rad = 10;
      int dist = 0;
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int j = 0; j <= rad; j++) {
         for (int i = -rad; i <= rad; i++) {
            for (int k = -rad; k <= rad; k++) {
               bid = Blocks.AIR;
               dist = j * j + i * i + k * k;
               dist = (int)Math.sqrt(dist);
               if (dist <= rad) {
                  if (dist >= rad - 2) {
                     int which = world.rand.nextInt(6);
                     if (which == 0) {
                        bid = Blocks.LEAVES;
                     }

                     if (which == 1) {
                        bid = Blocks.LOG;
                     }

                     if (which == 2) {
                        bid = Blocks.PLANKS;
                     }

                     if (which == 3) {
                        bid = Blocks.DIRT;
                     }

                     if (which == 4) {
                        bid = Blocks.COBBLESTONE;
                     }

                     if (which == 5) {
                        bid = Blocks.MOSSY_COBBLESTONE;
                     }
                  }

                  OreSpawn.setBlockFast(world, cposx + i, cposy - j, cposz + k, bid, 0, 2);
               }
            }
         }
      }

      for (int var15 = 1; var15 <= 5; var15++) {
         for (int i = -rad; i <= rad; i++) {
            for (int kx = -rad; kx <= rad; kx++) {
               bid = Blocks.AIR;
               OreSpawn.setBlockFast(world, cposx + i, cposy + var15, cposz + kx, bid, 0, 2);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy - (rad - 4), cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy - (rad - 4), cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Leonopteryx");
      }
   }

   public void makeCephadromeAltar(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int width = 4;
      int length = 4;
      int j = 0;
      bid = Blocks.COBBLESTONE;

      for (int i = -width; i <= width; i++) {
         for (int k = -length; k <= length; k++) {
            OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
         }
      }

      int var40 = 3;
      int var46 = 3;
      int var18 = 1;
      bid = Blocks.COBBLESTONE;

      for (int var12 = -var40; var12 <= var40; var12++) {
         for (int k = -var46; k <= var46; k++) {
            bid = Blocks.COBBLESTONE;
            if (k == 0 || var12 == 0) {
               bid = Blocks.STONEBRICK;
            }

            if ((k == -var46 || k == var46) && (var12 == -var40 || var12 == var40)) {
               bid = Blocks.STONEBRICK;
            }

            OreSpawn.setBlockFast(world, cposx + var12, cposy + var18, cposz + k, bid, 0, 2);
         }
      }

      var40 = 3;
      var46 = 3;
      var18 = 2;

      for (int var13 = -var40; var13 <= var40; var13++) {
         for (int k = -var46; k <= var46; k++) {
            bid = Blocks.AIR;
            if ((k == -var46 || k == var46) && (var13 == -var40 || var13 == var40)) {
               bid = Blocks.STONEBRICK;
            }

            OreSpawn.setBlockFast(world, cposx + var13, cposy + var18, cposz + k, bid, 0, 2);
         }
      }

      var40 = 3;
      var46 = 3;
      var18 = 3;

      for (int var14 = -var40; var14 <= var40; var14++) {
         for (int k = -var46; k <= var46; k++) {
            bid = Blocks.AIR;
            if ((k == -var46 || k == var46) && (var14 == -var40 || var14 == var40)) {
               bid = Blocks.END_STONE;
            }

            OreSpawn.setBlockFast(world, cposx + var14, cposy + var18, cposz + k, bid, 0, 2);
         }
      }

      var40 = 3;
      var46 = 3;
      var18 = 4;

      for (int var15 = -var40; var15 <= var40; var15++) {
         for (int k = -var46; k <= var46; k++) {
            bid = Blocks.AIR;
            if ((k == -var46 || k == var46) && (var15 == -var40 || var15 == var40)) {
               bid = OreSpawn.ExtremeTorch;
            }

            OreSpawn.setBlockFast(world, cposx + var15, cposy + var18, cposz + k, bid, 0, 2);
         }
      }

      var40 = 2;
      var46 = 2;
      var18 = 2;
      bid = Blocks.COBBLESTONE;

      for (int var16 = -var40; var16 <= var40; var16++) {
         for (int k = -var46; k <= var46; k++) {
            bid = Blocks.COBBLESTONE;
            if (k == 0 || var16 == 0) {
               bid = Blocks.STONEBRICK;
            }

            if ((k == -var46 || k == var46) && (var16 == -var40 || var16 == var40)) {
               bid = Blocks.STONEBRICK;
            }

            OreSpawn.setBlockFast(world, cposx + var16, cposy + var18, cposz + k, bid, 0, 2);
         }
      }

      var40 = 1;
      var46 = 1;
      var18 = 3;
      bid = Blocks.COBBLESTONE;

      for (int var17 = -var40; var17 <= var40; var17++) {
         for (int k = -var46; k <= var46; k++) {
            bid = Blocks.COBBLESTONE;
            if (k == 0 && var17 == 0) {
               bid = OreSpawn.MyEyeOfEnderBlock;
            }

            if ((k == -var46 || k == var46) && (var17 == -var40 || var17 == var40)) {
               bid = Blocks.END_STONE;
            }

            OreSpawn.setBlockFast(world, cposx + var17, cposy + var18, cposz + k, bid, 0, 2);
         }
      }
   }

   public void makeCrystalBattleTower(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      float radius = 10.0F;

      for (int j = 0; j <= 20; j++) {
         Block blk = OreSpawn.CrystalStone;
         if (j % 5 == 0) {
            for (float currad = 0.0F; currad < radius; currad += 0.33F) {
               for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
                  float curx = (float)(currad * Math.cos(Math.toRadians(curdeg)));
                  float curz = (float)(currad * Math.sin(Math.toRadians(curdeg)));
                  this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + j, (int)(cposz + curz + 0.5F), blk);
               }
            }
         } else {
            float currad = 10.0F;

            for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
               float curx = (float)(currad * Math.cos(Math.toRadians(curdeg)));
               float curz = (float)(currad * Math.sin(Math.toRadians(curdeg)));
               blk = OreSpawn.CrystalStone;
               if (j % 5 >= 1 && j % 5 <= 3 && (curdeg < 10.0F || curdeg > 350.0F)) {
                  blk = Blocks.AIR;
               }

               this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + j, (int)(cposz + curz + 0.5F), blk);
            }
         }
      }

      radius = 10.0F;

      for (int var18 = 21; var18 <= 22; var18++) {
         Block blk = OreSpawn.CrystalCrystal;
         float currad = 10.0F;

         for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
            float curx = (float)(currad * Math.cos(Math.toRadians(curdeg)));
            float curz = (float)(currad * Math.sin(Math.toRadians(curdeg)));
            this.FastSetBlock(world, (int)(cposx + curx + 0.5F), cposy + var18, (int)(cposz + curz + 0.5F), blk);
         }
      }

      int var19 = 1;
      chestContents = this.CrystalBattleTowerRatContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rat");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
      }

      var19 = 6;
      chestContents = this.CrystalBattleTowerDungeonBeastContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
      }

      var19 = 11;
      chestContents = this.CrystalBattleTowerUrchinContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Crystal Urchin");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Crystal Urchin");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
      }

      var19 = 16;
      chestContents = this.CrystalBattleTowerRotatorContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
      }

      var19 = 21;
      chestContents = this.CrystalBattleTowerVortexContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Vortex");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + var19 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Vortex");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var19, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + var19, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(6));
      }
   }

   public void makeGirlfriendIsland(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.DamselContentsList;

      for (int i = -5; i <= 5; i++) {
         int k = 3;
         if (i == -5 || i == 5) {
            k = 1;
         }

         if (i == -4 || i == 4) {
            k = 2;
         }

         if (i == -3 || i == 3) {
            k = 2;
         }

         for (int j = -k; j <= k; j++) {
            this.FastSetBlock(world, cposx + i, cposy, cposz + j, Blocks.SAND);
            this.FastSetBlock(world, cposx + i, cposy - 1, cposz + j, Blocks.STONE);
         }
      }

      for (int var12 = -2; var12 <= 2; var12++) {
         for (int j = -2; j <= 2; j++) {
            this.FastSetBlock(world, cposx + var12, cposy + 3, cposz + j, Blocks.LEAVES);
         }
      }

      this.FastSetBlock(world, cposx, cposy + 4, cposz, Blocks.LEAVES);
      this.FastSetBlock(world, cposx, cposy + 3, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx, cposy + 2, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx, cposy + 1, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx + 1, cposy + 3, cposz + 1, Blocks.LOG);
      this.FastSetBlock(world, cposx - 1, cposy + 3, cposz - 1, Blocks.LOG);
      this.FastSetBlock(world, cposx + 1, cposy + 3, cposz - 1, Blocks.LOG);
      this.FastSetBlock(world, cposx - 1, cposy + 3, cposz + 1, Blocks.LOG);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Girlfriend");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Boyfriend");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Gold Fish");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Gold Fish");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz - 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }
   }

   public void makeGreenhouseDungeon(World world, int cposx, int cposy, int cposz) {
      int height = 7;
      int width = 15;
      int length = 23;
      int t = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.GreenhouseContentsList;

      for (int i = 0; i < length; i++) {
         for (int k = 0; k < width; k++) {
            for (int j = 0; j < height; j++) {
               Block blk = Blocks.AIR;
               if (i == 0 || k == 0 || i == length - 1 || k == width - 1) {
                  blk = Blocks.GLASS;
               }

               if (j == height - 1) {
                  blk = Blocks.IRON_BLOCK;
                  if (i % 4 == 3 && k % 4 == 3) {
                     blk = Blocks.GLOWSTONE;
                  }

                  if (k % 4 == 1) {
                     blk = Blocks.GLASS;
                  }
               }

               if (j == 0) {
                  blk = Blocks.GRASS;
                  if (i != 0 && k != 0 && i != length - 1 && k != width - 1 && i % 3 == 2) {
                     blk = Blocks.WATER;
                  }
               }

               if (j == 1 && i != 0 && k != 0 && i != length - 1 && k != width - 1 && i % 3 != 2 && world.rand.nextInt(3) != 1) {
                  blk = Blocks.SOUL_SAND;
                  this.FastSetBlock(world, cposx + i, cposy + j - 1, cposz + k, blk);
                  t = world.rand.nextInt(20);
                  blk = Blocks.AIR;
                  if (t == 0) {
                     blk = Blocks.YELLOW_FLOWER;
                  }

                  if (t == 1) {
                     blk = Blocks.RED_FLOWER;
                  }

                  if (t == 2) {
                     blk = Blocks.BROWN_MUSHROOM;
                  }

                  if (t == 3) {
                     blk = Blocks.RED_MUSHROOM;
                  }

                  if (t == 4) {
                     blk = Blocks.WHEAT;
                  }

                  if (t == 5) {
                     blk = Blocks.CARROTS;
                  }

                  if (t == 6) {
                     blk = Blocks.POTATOES;
                  }

                  if (t == 7) {
                     blk = Blocks.REEDS;
                  }

                  if (t == 9) {
                     blk = OreSpawn.MyCornPlant1;
                  }

                  if (t == 10) {
                     blk = OreSpawn.MyTomatoPlant1;
                  }

                  if (t == 11) {
                     blk = OreSpawn.MyStrawberryPlant;
                  }

                  if (t == 12) {
                     blk = OreSpawn.MyButterflyPlant;
                  }

                  if (t == 13) {
                     blk = OreSpawn.MyMothPlant;
                  }

                  if (t == 14) {
                     blk = OreSpawn.MyRadishPlant;
                  }

                  if (t == 15) {
                     blk = OreSpawn.MyLettucePlant1;
                  }

                  if (t == 16) {
                     blk = OreSpawn.MyFlowerPinkBlock;
                  }

                  if (t == 17) {
                     blk = OreSpawn.MyFlowerBlueBlock;
                  }

                  if (t == 18) {
                     blk = OreSpawn.MyQuinoaPlant1;
                  }

                  if (t == 19) {
                     blk = OreSpawn.MyRicePlant;
                  }
               }

               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, blk);
            }
         }
      }

      for (int var17 = 0; var17 < length; var17++) {
         for (int k = 0; k < width; k++) {
            for (int j = height; j <= height + 6; j++) {
               this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.AIR);
            }
         }
      }

      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + 1, cposz, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2, cposy + 2, cposz, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 1, cposy + 1, cposz, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 1, cposy + 2, cposz, Blocks.AIR, 0, 2);
       this.placeDoorBlock(world, cposx + width / 2, cposy + 1, cposz, 3, Blocks.IRON_DOOR);
       this.placeDoorBlock(world, cposx + width / 2 - 1, cposy + 1, cposz, 3, Blocks.IRON_DOOR);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 2, cposy + 2, cposz, Blocks.STONE, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 + 1, cposy + 2, cposz, Blocks.STONE, 0, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 - 2, cposy + 2, cposz - 1, Blocks.STONE_BUTTON, 4, 2);
      OreSpawn.setBlockFast(world, cposx + width / 2 + 1, cposy + 2, cposz - 1, Blocks.STONE_BUTTON, 4, 2);
      int var18 = length / 2;
      int k = width / 2;
      int j = height + 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var18, cposy + j, cposz + k), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + j, cposz + k);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Triffid");
      }

      j = height + 2;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var18, cposy + j, cposz + k), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var18, cposy + j, cposz + k);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Triffid");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var18, cposy + height, cposz + k), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + var18, cposy + height, cposz + k);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(5));
      }
   }

   public void makeMonsterIsland(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      String monster = "Sea Viper";
      chestContents = this.MonsterIslandContentsList;
      if (world.rand.nextInt(2) == 0) {
         monster = "Sea Monster";
      }

      for (int i = -5; i <= 5; i++) {
         int k = 3;
         if (i == -5 || i == 5) {
            k = 1;
         }

         if (i == -4 || i == 4) {
            k = 2;
         }

         if (i == -3 || i == 3) {
            k = 2;
         }

         for (int j = -k; j <= k; j++) {
            this.FastSetBlock(world, cposx + i, cposy, cposz + j, Blocks.SAND);
            this.FastSetBlock(world, cposx + i, cposy - 1, cposz + j, Blocks.STONE);
         }
      }

      for (int var13 = -2; var13 <= 2; var13++) {
         for (int j = -2; j <= 2; j++) {
            this.FastSetBlock(world, cposx + var13, cposy + 3, cposz + j, Blocks.LEAVES);
         }
      }

      this.FastSetBlock(world, cposx, cposy + 4, cposz, Blocks.LEAVES);
      this.FastSetBlock(world, cposx, cposy + 3, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx, cposy + 2, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx, cposy + 1, cposz, Blocks.LOG);
      this.FastSetBlock(world, cposx + 1, cposy + 3, cposz + 1, Blocks.LOG);
      this.FastSetBlock(world, cposx - 1, cposy + 3, cposz - 1, Blocks.LOG);
      this.FastSetBlock(world, cposx + 1, cposy + 3, cposz - 1, Blocks.LOG);
      this.FastSetBlock(world, cposx - 1, cposy + 3, cposz + 1, Blocks.LOG);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, monster);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 3, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 3, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, monster);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz + 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, monster);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 3, cposz - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 3, cposz - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, monster);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz - 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz - 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx, cposy + 1, cposz + 1);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
      }
   }

   public void makeNightmareRookery(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      String monster = "Nightmare";
      chestContents = this.NightmareRookeryContentsList;
      int h = 0;
      int k = 0;
      int j = 0;
      int i = 0;

      for (int var14 = -5; var14 <= 20; var14++) {
         k += world.rand.nextInt(3) - 1;
         h = world.rand.nextInt(20) + 1;

         for (int var16 = 0; var16 < h; var16++) {
            this.FastSetBlock(world, cposx + var14, cposy + var16, cposz + k, Blocks.STONE);
            if (world.rand.nextInt(var16 + 5) == 1) {
               this.FastSetBlock(world, cposx + var14 + 1, cposy + var16, cposz + k, Blocks.STONE);
            }

            if (world.rand.nextInt(var16 + 5) == 1) {
               this.FastSetBlock(world, cposx + var14 - 1, cposy + var16, cposz + k, Blocks.STONE);
            }

            if (world.rand.nextInt(var16 + 5) == 1) {
               this.FastSetBlock(world, cposx + var14, cposy + var16, cposz + k + 1, Blocks.STONE);
            }

            if (world.rand.nextInt(var16 + 5) == 1) {
               this.FastSetBlock(world, cposx + var14, cposy + var16, cposz + k - 1, Blocks.STONE);
            }

            if (var16 >= 18) {
               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var14, cposy + var16 + 2, cposz + k), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var14, cposy + var16 + 2, cposz + k);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, monster);
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var14, cposy + var16 + 1, cposz + k), (Blocks.CHEST).getStateFromMeta(0), 2);
               chest = this.getChestTileEntity(world, cposx + var14, cposy + var16 + 1, cposz + k);
               if (chest != null) {
                  com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
               }
               break;
            }
         }
      }

      for (int var15 = -5; var15 <= 20; var15++) {
         k += world.rand.nextInt(3) - 1;
         h = world.rand.nextInt(20) + 1;

         for (int var17 = 0; var17 < h; var17++) {
            this.FastSetBlock(world, cposx + var15, cposy + var17, cposz + k, Blocks.STONE);
            if (world.rand.nextInt(var17 + 5) == 1) {
               this.FastSetBlock(world, cposx + var15 + 1, cposy + var17, cposz + k, Blocks.STONE);
            }

            if (world.rand.nextInt(var17 + 5) == 1) {
               this.FastSetBlock(world, cposx + var15 - 1, cposy + var17, cposz + k, Blocks.STONE);
            }

            if (world.rand.nextInt(var17 + 5) == 1) {
               this.FastSetBlock(world, cposx + var15, cposy + var17, cposz + k + 1, Blocks.STONE);
            }

            if (world.rand.nextInt(var17 + 5) == 1) {
               this.FastSetBlock(world, cposx + var15, cposy + var17, cposz + k - 1, Blocks.STONE);
            }

            if (var17 >= 18) {
               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var15, cposy + var17 + 2, cposz + k), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
               tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var15, cposy + var17 + 2, cposz + k);
               if (tileentitymobspawner != null) {
                  com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, monster);
               }

               OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var15, cposy + var17 + 1, cposz + k), (Blocks.CHEST).getStateFromMeta(0), 2);
               chest = this.getChestTileEntity(world, cposx + var15, cposy + var17 + 1, cposz + k);
               if (chest != null) {
                  com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 4 + world.rand.nextInt(5));
               }
               break;
            }
         }
      }
   }

   public void makeStinkyHouse(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.StinkyHouseContentsList;
      int height = 2;
      int width = 9;
      int length = 12;
      int yardwidth = 16;
      int yardlength = 24;

      for (int i = 0; i <= yardlength; i++) {
         for (int k = 0; k <= yardwidth; k++) {
            Block bid = Blocks.AIR;
            if (i == 0 || i == yardlength || k == 0 || k == yardwidth) {
               bid = Blocks.OAK_FENCE;
            }

            if (bid == Blocks.OAK_FENCE && world.rand.nextInt(3) == 1) {
               bid = Blocks.AIR;
            }

            if (bid == Blocks.AIR && world.rand.nextInt(10) == 1) {
               bid = Blocks.DEADBUSH;
            }

            if (bid != Blocks.AIR) {
               this.FastSetBlock(world, cposx + i - 5, cposy + 1, cposz + k - 4, bid);
            }
         }
      }

      for (int var17 = 0; var17 <= length; var17++) {
         for (int k = 0; k <= width; k++) {
            for (int j = 0; j <= height; j++) {
               Block bidx = Blocks.AIR;
               if (var17 == 0 || var17 == length || k == 0 || k == width) {
                  bidx = Blocks.PLANKS;
               }

               if (bidx == Blocks.PLANKS && j == 1 && (var17 == 1 || var17 == length - 1 || k == 1 || k == width - 1)) {
                  bidx = Blocks.GLASS_PANE;
               }

               if (j == height) {
                  bidx = Blocks.PLANKS;
               }

               if (world.rand.nextInt(10) == 1) {
                  bidx = Blocks.AIR;
               }

               if ((j == 0 || j == 1) && var17 == 0 && (k == width / 2 || k == width / 2 + 1)) {
                  bidx = Blocks.AIR;
               }

               this.FastSetBlock(world, cposx + var17, cposy + j + 1, cposz + k, bidx);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + 1, cposz + 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + 1, cposz + 2);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Stink Bug");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + length - 2, cposy + 1, cposz + width - 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + length - 2, cposy + 1, cposz + width - 2);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Stinky");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + length / 2, cposy + 1, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + length / 2, cposy + 1, cposz + width / 2);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 8 + world.rand.nextInt(5));
      }
   }

   public void makeRubberDuckyPond(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.RubberDuckyContentsList;

      for (int i = 0; i < 2; i++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + i, cposy + 6, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + i, cposy + 6, cposz);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rubber Ducky");
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 5, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 5, cposz), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + 5, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 8 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 4, cposz), (Blocks.GLASS).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 4, cposz), (Blocks.GLASS).getStateFromMeta(0), 2);

      for (int var11 = 0; var11 < 2; var11++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var11, cposy + 3, cposz), (Blocks.WATER).getStateFromMeta(0), 3);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 3, cposz), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + 3, cposz), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);

      for (int var12 = 0; var12 < 12; var12++) {
         for (int k = 0; k < 11; k++) {
            bid = Blocks.WATER;
            if (var12 == 0 || k == 0 || var12 == 11 || k == 10) {
               bid = Blocks.SAND;
            }

            this.FastSetBlock(world, cposx + var12 - 5, cposy, cposz + k - 5, bid);
            bid = Blocks.AIR;
            this.FastSetBlock(world, cposx + var12 - 5, cposy + 1, cposz + k - 5, bid);
            this.FastSetBlock(world, cposx + var12 - 5, cposy + 2, cposz + k - 5, bid);
         }
      }
   }

   public void makeWhiteHouse(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      this.makefountain(world, cposx - 5, cposy, cposz - 15);
      this.makefountain(world, cposx + 15, cposy, cposz - 15);
      this.makewalkway(world, cposx + 7, cposy, cposz - 15);
      this.makewhbase(world, cposx - 4, cposy, cposz - 6);
      this.makewhwalls(world, cposx - 3, cposy + 2, cposz - 5);
      this.makewhroof(world, cposx - 4, cposy, cposz - 6);
      this.makewhinterior(world, cposx - 1, cposy + 2, cposz - 3);
   }

   private void makefountain(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;

      for (int i = 0; i < 7; i++) {
         for (int k = 0; k < 5; k++) {
            for (int j = 0; j < 15; j++) {
               bid = Blocks.WATER;
               if (i == 0 || k == 0 || i == 6 || k == 4) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               if (j == 0) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               if (j == 1 && i == 3 && k == 2) {
                  bid = Blocks.GLOWSTONE;
               }

               if (j > 1) {
                  bid = Blocks.AIR;
                  if (j <= 4 && i == 3 && k == 2) {
                     bid = Blocks.QUARTZ_BLOCK;
                  }
               }

               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, bid);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 3, cposy + 5, cposz + 2), (Blocks.WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + 5, cposz + 2), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 4, cposy + 5, cposz + 2), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
   }

   private void makewalkway(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;

      for (int i = 0; i < 3; i++) {
         for (int k = 0; k < 10; k++) {
            for (int j = 0; j < 15; j++) {
               bid = Blocks.QUARTZ_BLOCK;
               if (j == 1) {
                  bid = Blocks.AIR;
                  if (k > 6) {
                     bid = Blocks.QUARTZ_BLOCK;
                  }
               }

               if (j > 1) {
                  bid = Blocks.AIR;
               }

               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, bid);
            }
         }
      }
   }

   private void makewhbase(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;

      for (int i = 0; i < 25; i++) {
         for (int k = 0; k < 25; k++) {
            bid = Blocks.QUARTZ_BLOCK;
            this.FastSetBlock(world, cposx + i, cposy + 1, cposz + k, bid);
            if ((i == 0 || i == 24) && (k == 0 || k == 24)) {
               this.FastSetBlock(world, cposx + i, cposy + 2, cposz + k, OreSpawn.CrystalTorch);
            }
         }
      }

      for (int var9 = 1; var9 < 24; var9++) {
         for (int kx = 1; kx < 24; kx++) {
            bid = Blocks.QUARTZ_BLOCK;
            this.FastSetBlock(world, cposx + var9, cposy + 2, cposz + kx, bid);
         }
      }
   }

   private void makewhwalls(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;

      for (int i = 0; i < 23; i++) {
         for (int k = 0; k < 23; k++) {
            for (int j = 0; j < 6; j++) {
               bid = Blocks.AIR;
               if (i == 0 || k == 0 || i == 22 || k == 22) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               if (j != 0 && bid != Blocks.AIR) {
                  if (k == 22) {
                     if ((j & 1) == 1) {
                        if ((i & 1) == 0 || (k & 1) == 0) {
                           bid = Blocks.GLASS_PANE;
                        }
                     } else if ((i & 1) == 1 || (k & 1) == 1) {
                        bid = Blocks.GLASS_PANE;
                     }
                  } else if (k != 0) {
                     if ((j & 1) == 1) {
                        if (i == 2 || k == 2 || i == 20 || k == 20) {
                           bid = Blocks.GLASS_PANE;
                        }
                     } else if (i == 1 || k == 1 || i == 21 || k == 21) {
                        bid = Blocks.GLASS_PANE;
                     }

                     if (j > 0 && j < 5 && k > 7 && k < 15) {
                        bid = Blocks.GLASS_PANE;
                     }
                  } else if ((j & 1) == 1) {
                     if (i == 2 || k == 2 || i == 20 || k == 20) {
                        bid = Blocks.GLASS_PANE;
                     }
                  } else if (i == 1 || k == 1 || i == 21 || k == 21) {
                     bid = Blocks.GLASS_PANE;
                  }
               }

               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, bid);
            }
         }
      }

      OreSpawn.setBlockFast(world, cposx + 11, cposy, cposz, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + 11, cposy + 1, cposz, Blocks.AIR, 0, 2);
       this.placeDoorBlock(world, cposx + 11, cposy, cposz, 3, Blocks.IRON_DOOR);
      OreSpawn.setBlockFast(world, cposx + 12, cposy + 1, cposz - 1, Blocks.STONE_BUTTON, 4, 2);
   }

   private void makewhroof(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;

      for (int j = 0; j < 13; j++) {
         for (int i = 0; i < 25 - 2 * j; i++) {
            for (int k = 0; k < 25 - 2 * j; k++) {
               bid = Blocks.AIR;
               if (i == 0 || k == 0 || i == 24 - 2 * j || k == 24 - 2 * j) {
                  bid = Blocks.QUARTZ_BLOCK;
               }

               if (j == 0 && bid != Blocks.AIR && (i + k & 1) == 1) {
                  bid = Blocks.EMERALD_BLOCK;
               }

               if (j == 12) {
                  bid = Blocks.EMERALD_BLOCK;
               }

               this.FastSetBlock(world, cposx + i + j, cposy + 8 + j, cposz + k + j, bid);
               if ((i == 0 || i == 24 - 2 * j) && (k == 0 || k == 24 - 2 * j)) {
                  this.FastSetBlock(world, cposx + i + j, cposy + 8 + j + 1, cposz + k + j, OreSpawn.CrystalTorch);
               }
            }
         }
      }

      bid = Blocks.OAK_FENCE;
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 11, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 10, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 9, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 8, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 7, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 6, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 5, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 4, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 3, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 2, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 1, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 0, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 11, cposy + 8 + 0, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 13, cposy + 8 + 0, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 0, cposz + 11, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 0, cposz + 13, bid);
      bid = OreSpawn.CrystalTorch;
      this.FastSetBlock(world, cposx + 11, cposy + 8 + 1, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 13, cposy + 8 + 1, cposz + 12, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 1, cposz + 11, bid);
      this.FastSetBlock(world, cposx + 12, cposy + 8 + 1, cposz + 13, bid);
   }

   private void makewhinterior(World world, int cposx, int cposy, int cposz) {
      int zoff = 1;
      int xoff = 0;

      for (int i = 0; i < 8; i++) {
         OreSpawn.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff, Blocks.QUARTZ_STAIRS, 3, 2);
         OreSpawn.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff + 1, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff + 2, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + xoff + i, cposy, cposz + zoff + 3, Blocks.QUARTZ_STAIRS, 2, 2);
      }

      int var22 = 11;

      for (int var14 = 0; var14 < 8; var14++) {
         OreSpawn.setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff, Blocks.QUARTZ_STAIRS, 3, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff + 1, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff + 2, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var14, cposy, cposz + zoff + 3, Blocks.QUARTZ_STAIRS, 2, 2);
      }

      int var19 = 7;
      var22 = 0;

      for (int var15 = 0; var15 < 8; var15++) {
         OreSpawn.setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19, Blocks.QUARTZ_STAIRS, 3, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19 + 1, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19 + 2, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var15, cposy, cposz + var19 + 3, Blocks.QUARTZ_STAIRS, 2, 2);
      }

      var22 = 11;

      for (int var16 = 0; var16 < 8; var16++) {
         OreSpawn.setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19, Blocks.QUARTZ_STAIRS, 3, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19 + 1, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19 + 2, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var16, cposy, cposz + var19 + 3, Blocks.QUARTZ_STAIRS, 2, 2);
      }

      var19 = 13;
      var22 = 0;

      for (int var17 = 0; var17 < 8; var17++) {
         OreSpawn.setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19, Blocks.QUARTZ_STAIRS, 3, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19 + 1, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19 + 2, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var17, cposy, cposz + var19 + 3, Blocks.QUARTZ_STAIRS, 2, 2);
      }

      var22 = 11;

      for (int var18 = 0; var18 < 8; var18++) {
         OreSpawn.setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19, Blocks.QUARTZ_STAIRS, 3, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19 + 1, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19 + 2, Blocks.PISTON_EXTENSION, 1, 2);
         OreSpawn.setBlockFast(world, cposx + var22 + var18, cposy, cposz + var19 + 3, Blocks.QUARTZ_STAIRS, 2, 2);
      }

      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.WhiteHouseContentsList;
      var19 = 18;
      var22 = 2;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy + 1, cposz + var19), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Criminal");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy, cposz + var19), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }

      var22 = 6;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy + 1, cposz + var19), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Criminal");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy, cposz + var19), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }

      var22 = 12;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy + 1, cposz + var19), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Criminal");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy, cposz + var19), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }

      var22 = 16;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy + 1, cposz + var19), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var22, cposy + 1, cposz + var19);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Criminal");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var22, cposy, cposz + var19), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = this.getChestTileEntity(world, cposx + var22, cposy, cposz + var19);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 3 + world.rand.nextInt(5));
      }
   }

   public void makeQueenAltar(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int width = 51;
      int length = 51;
      int height = 48;
      if (!world.isRemote) {
         for (int j = 0; j <= height + 10; j++) {
            for (int i = -5; i < width + 5; i++) {
               for (int k = -5; k < length + 5; k++) {
                  bid = Blocks.AIR;
                  OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
               }
            }
         }

         int var17 = 0;

         for (int i = 0; i < width; i++) {
            for (int k = 0; k < length; k++) {
               Block var24 = Blocks.GRASS;
               OreSpawn.setBlockFast(world, cposx + i, cposy + var17, cposz + k, var24, 0, 2);

               for (int v = 1; v < 10; v++) {
                  bid = OreSpawn.getBlockStateFast(world, new BlockPos(cposx + i, cposy + var17 - v, cposz + k)).getBlock();
                  if (bid == Blocks.AIR || bid == Blocks.TALLGRASS || bid == Blocks.WATER) {
                     bid = Blocks.DIRT;
                     OreSpawn.setBlockFast(world, cposx + i, cposy + var17 - v, cposz + k, bid, 0, 2);
                  }
               }
            }
         }

         this.makequeencolumn(world, cposx + 1, cposy + 1, cposz + 1);
         this.makequeencolumn(world, cposx + width - 8, cposy + 1, cposz + length - 8);
         this.makequeencolumn(world, cposx + 1, cposy + 1, cposz + length - 8);
         this.makequeencolumn(world, cposx + width - 8, cposy + 1, cposz + 1);
         var17 = height - 1;
         bid = Blocks.OBSIDIAN;

         for (int var15 = 0; var15 < width; var15++) {
            for (int k = 0; k < length; k++) {
               OreSpawn.setBlockFast(world, cposx + var15, cposy + var17, cposz + k, bid, 0, 2);
            }
         }

         var17 = height;
         bid = Blocks.OBSIDIAN;

         for (int var16 = -1; var16 <= width; var16++) {
            for (int k = -1; k <= length; k++) {
               OreSpawn.setBlockFast(world, cposx + var16, cposy + var17, cposz + k, bid, 0, 2);
            }
         }

         this.makequeenbackground(world, cposx + 4, cposy + 10, cposz + 9);
         this.makequeencenteraltar(world, cposx + width / 2, cposy, cposz + length / 2);
      }
   }

   private void makequeencolumn(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int width = 5;
      int length = 5;
      int height = 44;
      if (!world.isRemote) {
         int j = 0;
         bid = Blocks.OBSIDIAN;

         for (int i = 0; i < width + 2; i++) {
            for (int k = 0; k < length + 2; k++) {
               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, meta, 2);
               OreSpawn.setBlockFast(world, cposx + i, cposy + j + height + 1, cposz + k, bid, meta, 2);
            }
         }

         cposx++;
         cposz++;
         cposy++;

         for (int var18 = 0; var18 < height; var18++) {
            for (int var17 = 0; var17 < width; var17++) {
               for (int k = 0; k < length; k++) {
                  bid = Blocks.AIR;
                  if (var17 == 0 || k == 0 || var17 == width - 1 || k == length - 1) {
                     bid = Blocks.OBSIDIAN;
                  }

                  if (var18 % 4 == 0 && bid != Blocks.AIR && (var17 == 2 || k == 2)) {
                     bid = Blocks.REDSTONE_BLOCK;
                  }

                  if (var18 % 4 == 1 && bid != Blocks.AIR) {
                     if (var17 == 1 || k == 1) {
                        bid = Blocks.REDSTONE_BLOCK;
                     }

                     if (var17 == 3 || k == 3) {
                        bid = Blocks.REDSTONE_BLOCK;
                     }
                  }

                  if (var18 % 4 == 2 && bid != Blocks.AIR) {
                     if (var17 == 1 || k == 1) {
                        bid = Blocks.REDSTONE_BLOCK;
                     }

                     if (var17 == 3 || k == 3) {
                        bid = Blocks.REDSTONE_BLOCK;
                     }

                     if (var17 == 2 || k == 2) {
                        bid = OreSpawn.MyBlockAmethystBlock;
                     }
                  }

                  if (var18 % 4 == 3 && bid != Blocks.AIR) {
                     if (var17 == 1 || k == 1) {
                        bid = Blocks.REDSTONE_BLOCK;
                     }

                     if (var17 == 3 || k == 3) {
                        bid = Blocks.REDSTONE_BLOCK;
                     }
                  }

                  int var22 = 0;
                  OreSpawn.setBlockFast(world, cposx + var17, cposy + var18, cposz + k, bid, var22, 2);
               }
            }
         }
      }
   }

   private void makequeenbackground(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int curz = 0;
      int cury = 0;
      int height = 33;
      int width = 33;
      bid = Blocks.STONE;

      for (int m = 0; m < this.queen.length; m++) {
         int v = this.queen[m];
         if (v < 0) {
            for (bid = Blocks.STONE; curz < width; curz++) {
               OreSpawn.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
            }

            cury++;
            curz = 0;
         } else {
            for (int n = 0; n < v; n++) {
               OreSpawn.setBlockFast(world, cposx, cposy + cury, cposz + curz, bid, 0, 2);
               curz++;
            }

            if (bid == Blocks.STONE) {
               bid = OreSpawn.MyBlockRubyBlock;
            } else {
               bid = Blocks.STONE;
            }
         }
      }

      for (int i = 0; i < width; i++) {
         OreSpawn.setBlockFast(world, cposx, cposy - 1, cposz + i, Blocks.DIAMOND_BLOCK, 0, 2);
      }

      for (int var17 = 0; var17 < width; var17++) {
         OreSpawn.setBlockFast(world, cposx, cposy + height, cposz + var17, Blocks.DIAMOND_BLOCK, 0, 2);
      }

      for (int var18 = -1; var18 <= height; var18++) {
         OreSpawn.setBlockFast(world, cposx, cposy + var18, cposz - 1, Blocks.DIAMOND_BLOCK, 0, 2);
      }

      for (int var19 = -1; var19 <= height; var19++) {
         OreSpawn.setBlockFast(world, cposx, cposy + var19, cposz + width, Blocks.DIAMOND_BLOCK, 0, 2);
      }

      OreSpawn.setBlockFast(world, cposx, cposy - 2, cposz - 2, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 1, cposz + width + 1, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy - 2, cposz + width + 1, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 1, cposz - 2, Blocks.DIAMOND_BLOCK, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy - 1, cposz - 2, OreSpawn.CrystalTorch, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 2, cposz + width + 1, OreSpawn.CrystalTorch, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy - 1, cposz + width + 1, OreSpawn.CrystalTorch, 0, 2);
      OreSpawn.setBlockFast(world, cposx, cposy + height + 2, cposz - 2, OreSpawn.CrystalTorch, 0, 2);
   }

   private void makequeencenteraltar(World world, int cposx, int cposy, int cposz) {
      Block bid = Blocks.AIR;
      int meta = 0;
      int width = 10;
      int length = 10;
      int j = 0;
      bid = Blocks.OBSIDIAN;

      for (int i = -width; i <= width; i++) {
         for (int k = -length; k <= length; k++) {
            OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, bid, 0, 2);
         }
      }

      int var62 = 6;
      int var74 = 20;
      int var25 = 0;
      bid = Blocks.OBSIDIAN;

      for (int var13 = -var62; var13 <= var62; var13++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var13, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 20;
      var74 = 6;
      var25 = 0;
      bid = Blocks.OBSIDIAN;

      for (int var14 = -var62; var14 <= var62; var14++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var14, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 8;
      var74 = 8;
      var25 = 1;

      for (int var15 = -var62; var15 <= var62; var15++) {
         for (int k = -var74; k <= var74; k++) {
            bid = Blocks.OBSIDIAN;
            OreSpawn.setBlockFast(world, cposx + var15, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 4;
      var74 = 18;
      var25 = 1;

      for (int var16 = -var62; var16 <= var62; var16++) {
         for (int k = -var74; k <= var74; k++) {
            bid = Blocks.OBSIDIAN;
            if (var16 == var62 && (k == -var74 || k == var74)) {
               bid = OreSpawn.MyBlockAmethystBlock;
            }

            if (var16 == -var62 && (k == -var74 || k == var74)) {
               bid = OreSpawn.MyBlockAmethystBlock;
            }

            OreSpawn.setBlockFast(world, cposx + var16, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 18;
      var74 = 4;
      var25 = 1;

      for (int var17 = -var62; var17 <= var62; var17++) {
         for (int k = -var74; k <= var74; k++) {
            bid = Blocks.OBSIDIAN;
            if (var17 == var62 && (k == -var74 || k == var74)) {
               bid = OreSpawn.MyBlockAmethystBlock;
            }

            if (var17 == -var62 && (k == -var74 || k == var74)) {
               bid = OreSpawn.MyBlockAmethystBlock;
            }

            OreSpawn.setBlockFast(world, cposx + var17, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 7;
      var74 = 7;
      var25 = 2;
      bid = Blocks.OBSIDIAN;

      for (int var18 = -var62; var18 <= var62; var18++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var18, cposy + var25, cposz + k, bid, 0, 2);
            if (var18 == var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var18, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }

            if (var18 == -var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var18, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }
         }
      }

      var62 = 3;
      var74 = 17;
      var25 = 2;
      bid = Blocks.OBSIDIAN;

      for (int var19 = -var62; var19 <= var62; var19++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var19, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 17;
      var74 = 3;
      var25 = 2;
      bid = Blocks.OBSIDIAN;

      for (int var20 = -var62; var20 <= var62; var20++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var20, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 6;
      var74 = 6;
      var25 = 3;
      bid = Blocks.OBSIDIAN;

      for (int var21 = -var62; var21 <= var62; var21++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var21, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 2;
      var74 = 16;
      var25 = 3;
      bid = Blocks.OBSIDIAN;

      for (int var22 = -var62; var22 <= var62; var22++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var22, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 16;
      var74 = 2;
      var25 = 3;
      bid = Blocks.OBSIDIAN;

      for (int var23 = -var62; var23 <= var62; var23++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var23, cposy + var25, cposz + k, bid, 0, 2);
         }
      }

      var62 = 2;
      var74 = 2;
      var25 = 4;
      bid = Blocks.OBSIDIAN;

      for (int var24 = -var62; var24 <= var62; var24++) {
         for (int k = -var74; k <= var74; k++) {
            OreSpawn.setBlockFast(world, cposx + var24, cposy + var25, cposz + k, bid, 0, 2);
            if (var24 == var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var24, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }

            if (var24 == -var62 && (k == -var74 || k == var74)) {
               OreSpawn.setBlockFast(world, cposx + var24, cposy + var25 + 1, cposz + k, OreSpawn.CrystalTorch, 0, 2);
            }
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var25, cposz), (Blocks.CHEST).getDefaultState(), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var25, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx, cposy + var25, cposz)).getBlock().getStateFromMeta(2), 3);
      TileEntityChest chest = this.getChestTileEntity(world, cposx, cposy + var25, cposz);
      if (chest != null) {
         chest.setInventorySlotContents(13, new ItemStack(OreSpawn.TheQueenEgg));
      }
   }

   public void makeFrogPond(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Frog");
      }

      for (int i = -3; i <= 3; i++) {
         for (int j = -3; j <= 3; j++) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + i, cposy, cposz + j), (Blocks.WATER).getStateFromMeta(0), 3);
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz), (Blocks.WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 1, cposz), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz - 1), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 1, cposz + 1), (Blocks.FLOWING_WATER).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 2, cposz), (Blocks.WATERLILY).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 2, cposz), (Blocks.WATERLILY).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz - 1), (Blocks.WATERLILY).getStateFromMeta(0), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 2, cposz + 1), (Blocks.WATERLILY).getStateFromMeta(0), 3);
   }

   public void makePumpkin(World world, int cposx, int cposy, int cposz) {
      int width = 14;
      int depth = 12;
      int height = 14;
      int dark_green = 13;
      int orange = 1;
      int which_color = 0;
      Block blk = Blocks.AIR;
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            for (int k = 0; k < depth; k++) {
               which_color = 0;
               blk = Blocks.AIR;
               if (j == 0 || j == height - 1) {
                  blk = Blocks.STAINED_HARDENED_CLAY;
                  which_color = orange;
               }

               if (i == 0 || i == width - 1) {
                  blk = Blocks.STAINED_HARDENED_CLAY;
                  which_color = orange;
               }

               if (k == 0 || k == depth - 1) {
                  blk = Blocks.STAINED_HARDENED_CLAY;
                  which_color = orange;
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, blk, which_color, 2);
            }
         }
      }

      int var16 = width / 2 - 1;
      int k = 0;
      int j = 11;
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + j, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 4, cposy + j, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 5, cposy + j, cposz + k, Blocks.AIR, 0, 2);
      int var25 = 10;
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 5, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 9;
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 5, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 8;
      OreSpawn.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 7;
      OreSpawn.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 4;
      OreSpawn.setBlockFast(world, cposx + var16 + 1, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 3;
      OreSpawn.setBlockFast(world, cposx + var16 + 1, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 2;
      OreSpawn.setBlockFast(world, cposx + var16 + 1, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 3, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 + 4, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var25 = 1;
      OreSpawn.setBlockFast(world, cposx + var16 + 2, cposy + var25, cposz + k, Blocks.AIR, 0, 2);
      var16 = width / 2;
      int var48 = 0;
      var25 = 11;
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 5, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 10;
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 5, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 9;
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 5, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 8;
      OreSpawn.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 7;
      OreSpawn.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 4;
      OreSpawn.setBlockFast(world, cposx + var16 - 1, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 3;
      OreSpawn.setBlockFast(world, cposx + var16 - 1, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 2;
      OreSpawn.setBlockFast(world, cposx + var16 - 1, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 3, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      OreSpawn.setBlockFast(world, cposx + var16 - 4, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var25 = 1;
      OreSpawn.setBlockFast(world, cposx + var16 - 2, cposy + var25, cposz + var48, Blocks.AIR, 0, 2);
      var48 = depth / 2 - 1;

      for (int var42 = 0; var42 < 4; var42++) {
         for (int var18 = 0; var18 < 3; var18++) {
            OreSpawn.setBlockFast(world, cposx + width / 2 - var18 - var42, cposy + height + var42, cposz + var48, Blocks.STAINED_HARDENED_CLAY, dark_green, 2);
         }
      }

      for (int var43 = 0; var43 < 5; var43++) {
         for (int var19 = 0; var19 < 2; var19++) {
            for (int var50 = 0; var50 < 2; var50++) {
               OreSpawn.setBlockFast(world, cposx + width / 2 + var19 - 1, cposy + var43 + 1, cposz + depth / 2 + var50 - 1, Blocks.PLANKS, 0, 2);
            }
         }
      }

      var25 = 5;

      for (int var20 = 0; var20 < 2; var20++) {
         for (int var51 = 0; var51 < 2; var51++) {
            OreSpawn.setBlockFast(world, cposx + width / 2 + var20 - 1, cposy + var25 + 1, cposz + depth / 2 + var51 - 1, Blocks.NETHERRACK, 0, 2);
         }
      }

      var25 = 6;
      int var52 = 0;

      for (int var21 = 0; var21 < 2; var21++) {
         OreSpawn.setBlockFast(world, cposx + width / 2 + var21 - 1, cposy + var25 + 1, cposz + depth / 2 + var52 - 1, Blocks.FIRE, 0, 2);
      }

      var25 = 6;
      var52 = 1;
      int var22 = 0;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + var22 - 1, cposy + var25 + 1, cposz + depth / 2 + var52 - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + var22 - 1, cposy + var25 + 1, cposz + depth / 2 + var52 - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost Pumpkin Skelly");
      }

      var22 = 1;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + var22 - 1, cposy + var25 + 1, cposz + depth / 2 + var52 - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + var22 - 1, cposy + var25 + 1, cposz + depth / 2 + var52 - 1);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Ghost Pumpkin Skelly");
      }
   }

   public void makeRoundRotator(World world, int cposx, int cposy, int cposz) {
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      float radius = 6.0F;

      for (float curdeg = 0.0F; curdeg < 360.0F; curdeg += 5.0F) {
         float curx = (float)(radius * Math.cos(Math.toRadians(curdeg)));
         float cury = (float)(radius * Math.sin(Math.toRadians(curdeg)));
         Block blk = Blocks.BEDROCK;
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), (int)(cposy + 6 + cury + 0.5F), cposz, blk);
      }

      radius = 2.0F;

      for (float var19 = 0.0F; var19 < 360.0F; var19 += 5.0F) {
         float curx = (float)(radius * Math.cos(Math.toRadians(var19)));
         float cury = (float)(radius * Math.sin(Math.toRadians(var19)));
         Block blk = OreSpawn.MyCrystalPinkBlock;
         this.FastSetBlock(world, (int)(cposx + curx + 0.5F), (int)(cposy + 6 + cury + 0.5F), cposz, blk);
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 6 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 6 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 6 - 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 6 - 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 6 - 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 1, cposy + 6 - 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + 6 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 1, cposy + 6 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Rotator");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 5, cposy + 6, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 5, cposy + 6, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 5, cposy + 6, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 5, cposy + 6, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 6 - 5, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 6 - 5, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 6 + 5, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx, cposy + 6 + 5, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Dungeon Beast");
      }

      Block blk = OreSpawn.CrystalCoal;
      this.FastSetBlock(world, cposx + 1, cposy + 6, cposz, blk);
      this.FastSetBlock(world, cposx - 1, cposy + 6, cposz, blk);
      this.FastSetBlock(world, cposx, cposy + 6 + 1, cposz, blk);
      this.FastSetBlock(world, cposx, cposy + 6 - 1, cposz, blk);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 6, cposz), (Blocks.CHEST).getDefaultState(), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + 6, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx, cposy + 6, cposz)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx, cposy + 6, cposz);
      if (chest != null) {
         chestContents = this.CrystalBattleTowerVortexContentsList;
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 6 + world.rand.nextInt(6));
      }
   }

   public void makeRainbow(World world, int cposx, int cposy, int cposz) {
      int width = 12;
      int depth = 1;
      int blk_color = 0;
      TileEntityMobSpawner tileentitymobspawner = null;
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      int var45 = 0;
      int j = 35;
      int var35 = 12;
      int var40 = 1;

      for (int i = -var35; i < var35; i++) {
         for (int k = -var40; k <= var40; k++) {
            OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, Blocks.STAINED_HARDENED_CLAY, var45, 2);
         }
      }

      int k = 0;

      for (int var16 = -var35 + 1; var16 < var35; var16 += 3) {
         OreSpawn.setBlockFast(world, cposx + var16, cposy + j, cposz + k, Blocks.WATER, 0, 2);
         OreSpawn.setBlockFast(world, cposx + var16, cposy + j - 1, cposz + k, Blocks.FLOWING_WATER, 0, 2);
      }

      var35 = 13;
      var40 = 2;
      int var23 = 26;

      for (int var17 = -var35; var17 < var35; var17++) {
         for (int var29 = -var40; var29 <= var40; var29++) {
            Block blk = Blocks.AIR;
            if (var17 == -var35 || var17 == var35 - 1) {
               blk = Blocks.STAINED_HARDENED_CLAY;
            }

            if (var29 == -var40 || var29 == var40) {
               blk = Blocks.STAINED_HARDENED_CLAY;
            }

            OreSpawn.setBlockFast(world, cposx + var17, cposy + var23, cposz + var29, blk, var45, 2);
         }
      }

      var35 = 14;
      var40 = 3;
      var23 = 27;

      for (int var18 = -var35; var18 < var35; var18++) {
         for (int var30 = -var40; var30 <= var40; var30++) {
            Block blkx = Blocks.AIR;
            if (var18 == -var35 || var18 == var35 - 1) {
               blkx = Blocks.STAINED_HARDENED_CLAY;
            }

            if (var30 == -var40 || var30 == var40) {
               blkx = Blocks.STAINED_HARDENED_CLAY;
            }

            OreSpawn.setBlockFast(world, cposx + var18, cposy + var23, cposz + var30, blkx, var45, 2);
         }
      }

      var35 = 13;
      var40 = 2;
      var23 = 28;

      for (int var19 = -var35; var19 < var35; var19++) {
         for (int var31 = -var40; var31 <= var40; var31++) {
            Block blkxx = Blocks.AIR;
            if (var19 == -var35 || var19 == var35 - 1) {
               blkxx = Blocks.STAINED_HARDENED_CLAY;
            }

            if (var31 == -var40 || var31 == var40) {
               blkxx = Blocks.STAINED_HARDENED_CLAY;
            }

            OreSpawn.setBlockFast(world, cposx + var19, cposy + var23, cposz + var31, blkxx, var45, 2);
         }
      }

      var23 = 29;
      var35 = 12;
      var40 = 1;

      for (int var20 = -var35; var20 < var35; var20++) {
         for (int var32 = -var40; var32 <= var40; var32++) {
            OreSpawn.setBlockFast(world, cposx + var20, cposy + var23, cposz + var32, Blocks.STAINED_HARDENED_CLAY, var45, 2);
         }
      }

      var23 = 30;

      for (int m = 3; m < 11; m++) {
         var45 = this.blkcolors[m - 3];

         for (int var21 = 0; var21 < m; var21++) {
            OreSpawn.setBlockFast(world, cposx + m, cposy + var23 + var21, cposz, Blocks.STAINED_HARDENED_CLAY, var45, 2);
            OreSpawn.setBlockFast(world, cposx - (m + 1), cposy + var23 + var21, cposz, Blocks.STAINED_HARDENED_CLAY, var45, 2);
         }

         for (int var22 = -(m + 1); var22 <= m; var22++) {
            OreSpawn.setBlockFast(world, cposx + var22, cposy + var23 + m, cposz, Blocks.STAINED_HARDENED_CLAY, var45, 2);
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + var23, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + var23, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + var23, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + var23, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + var23 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + var23 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + var23 + 1, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + var23 + 1, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 2, cposy + var23 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + 2, cposy + var23 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + var23 + 2, cposz), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
      tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + var23 + 2, cposz);
      if (tileentitymobspawner != null) {
         com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Cloud Shark");
      }

      chestContents = this.RainbowContentsList;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var23, cposz), (Blocks.CHEST).getDefaultState(), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx, cposy + var23, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx, cposy + var23, cposz)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx, cposy + var23, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + var23, cposz), (Blocks.CHEST).getDefaultState(), 3);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 1, cposy + var23, cposz), OreSpawn.getBlockStateFast(world, new BlockPos(cposx - 1, cposy + var23, cposz)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx - 1, cposy + var23, cposz);
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 10 + world.rand.nextInt(5));
      }
   }

   public void makeEnormousCastleQ(World world, int cposx, int cposy, int cposz) {
      int width = 28;
      int height = 16;
      int platformwidth = 11;
      int level = 0;
      if (!world.isRemote) {
         level = 1 + world.rand.nextInt(6);
         if (level <= 3 && world.rand.nextInt(3) != 1) {
            level += 3;
         }

         for (int i = -20; i < width + 4; i++) {
            for (int j = 1; j < height + 10; j++) {
               for (int k = -4; k < width + 4; k++) {
                  this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
               }
            }
         }

         for (int var15 = 0; var15 < width; var15++) {
            int j = 0;

            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + var15, cposy + j, cposz + k, Blocks.OBSIDIAN);
            }
         }

         for (int var16 = 0; var16 < width; var16++) {
            int j = height;

            for (int k = 0; k < width; k++) {
               this.FastSetBlock(world, cposx + var16, cposy + j, cposz + k, Blocks.BEDROCK);
            }
         }

         for (int var17 = 0; var17 < width; var17++) {
            for (int j = 1; j < height; j++) {
               int k = 0;
               this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.IRON_BARS);
               k = width - 1;
               this.FastSetBlock(world, cposx + var17, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
         }

         for (int k = 0; k < width; k++) {
            for (int j = 1; j < height; j++) {
               int var18 = 0;
               this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.IRON_BARS);
               var18 = width - 1;
               this.FastSetBlock(world, cposx + var18, cposy + j, cposz + k, Blocks.IRON_BARS);
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + 1), (OreSpawn.ExtremeTorch).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width - 2), (OreSpawn.ExtremeTorch).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + 1), (OreSpawn.ExtremeTorch).getDefaultState(), 3);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width - 2), (OreSpawn.ExtremeTorch).getDefaultState(), 3);

         for (int var20 = -4; var20 < width + 4; var20++) {
            for (int var46 = -4; var46 < width + 4; var46++) {
               if (var20 < 0 || var46 < 0 || var20 >= width || var46 >= width) {
                  this.FastSetBlock(world, cposx + var20, cposy, cposz + var46, Blocks.OBSIDIAN);
               }

               if (var20 == -4 || var46 == -4 || var20 == width + 3 || var46 == width + 3) {
                  this.FastSetBlock(world, cposx + var20, cposy + 1, cposz + var46, Blocks.NETHER_BRICK_FENCE);
               }
            }
         }

         TileEntityMobSpawner tileentitymobspawner = null;

         for (int j = 0; j < 4; j++) {
            OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + 1 + j, cposz - 3), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
            }

            OreSpawn.setBlockStateFast(world, new BlockPos(cposx - 3, cposy + 1 + j, cposz + width + 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - 3, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
            }

            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + 2, cposy + 1 + j, cposz - 3), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz - 3);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
            }

            OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + 2, cposy + 1 + j, cposz + width + 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
            tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + 2, cposy + 1 + j, cposz + width + 2);
            if (tileentitymobspawner != null) {
               com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Lurking Terror");
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 4, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         }

         this.buildLevelQ(world, cposx + 1, cposy + height, cposz + 1, width - 2, 10, 4, "Rotator", 1, -1, 5, 1, level);
         int var31 = height + 10;
         if (level >= 2) {
            this.buildLevelQ(world, cposx + 1, cposy + var31, cposz + 1, width - 2, 10, 4, "Bee", 0, 0, 4, 2, level);
         }

         var31 += 10;
         if (level >= 3) {
            this.buildLevelQ(world, cposx + 2, cposy + var31, cposz + 2, width - 4, 9, 4, "Mantis", 1, 1, 4, 3, level);
         }

         var31 += 9;
         if (level >= 4) {
            this.buildLevelQ(world, cposx + 2, cposy + var31, cposz + 2, width - 4, 9, 3, "Mothra", 0, 0, 4, 4, level);
         }

         var31 += 9;
         if (level >= 5) {
            this.buildLevelQ(world, cposx + 3, cposy + var31, cposz + 3, width - 6, 8, 3, "Brutalfly", 1, 1, 4, 5, level);
         }

         var31 += 8;
         if (level >= 6) {
            this.buildLevelQ(world, cposx + 3, cposy + var31, cposz + 3, width - 6, 16, 3, "Vortex", 0, 0, 3, 6, level);
         }

         var31 += 16;

         for (int var21 = 0; var21 < platformwidth; var21++) {
            var31 = height;

            for (int var47 = -(platformwidth / 2); var47 <= platformwidth / 2; var47++) {
               this.FastSetBlock(world, cposx + var21 - 20, cposy + var31, cposz + var47 + width / 2, OreSpawn.MyBlockAmethystBlock);
               if ((var21 == 0 || var21 == platformwidth - 1 || var47 == -(platformwidth / 2) || var47 == platformwidth / 2)
                  && (var21 != 0 || var47 < -1 || var47 > 1)) {
                  this.FastSetBlock(world, cposx + var21 - 20, cposy + var31 + 1, cposz + var47 + width / 2, Blocks.NETHER_BRICK_FENCE);
               }
            }
         }

         for (int var22 = -10; var22 <= -3; var22++) {
            var31 = height;

            for (int var48 = -2; var48 < 3; var48++) {
               if (var22 != -3 && var22 != -10) {
                  this.FastSetBlock(world, cposx + var22, cposy + var31, cposz + var48 + width / 2, OreSpawn.MyBlockAmethystBlock);
                  if (var48 == -2 || var48 == 2) {
                     this.FastSetBlock(world, cposx + var22, cposy + var31 + 1, cposz + var48 + width / 2, Blocks.NETHER_BRICK_FENCE);
                  }
               } else if (var48 != -2 && var48 != 2) {
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 1, cposz + var48 + width / 2, Blocks.AIR);
               } else {
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 1, cposz + var48 + width / 2, Blocks.NETHERRACK);
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 2, cposz + var48 + width / 2, Blocks.NETHERRACK);
                  this.FastSetBlock(world, cposx + var22, cposy + var31 + 3, cposz + var48 + width / 2, Blocks.FIRE);
               }
            }
         }

         int var23 = -21;

         for (int var39 = height; var39 >= 0; var39--) {
            for (int var49 = -2; var49 < 3; var49++) {
               for (int t = 0; t < 6; t++) {
                  this.FastSetBlock(world, cposx + var23, cposy + var39 + t + 1, cposz + var49 + width / 2, Blocks.AIR);
               }

               if (var39 == 0) {
                  if (var49 != -2 && var49 != 2) {
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 1, cposz + var49 + width / 2, Blocks.AIR);
                  } else {
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 1, cposz + var49 + width / 2, Blocks.NETHERRACK);
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 2, cposz + var49 + width / 2, Blocks.NETHERRACK);
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 3, cposz + var49 + width / 2, Blocks.FIRE);
                  }
               } else {
                  this.FastSetBlock(world, cposx + var23, cposy + var39, cposz + var49 + width / 2, OreSpawn.MyBlockAmethystBlock);
                  if (var49 == -2 || var49 == 2) {
                     this.FastSetBlock(world, cposx + var23, cposy + var39 + 1, cposz + var49 + width / 2, Blocks.NETHER_BRICK_FENCE);
                  }
               }
            }

            var23--;
         }

         if (level >= 6) {
            int span = width * 3;

            for (int tries = 0; tries < 100; tries++) {
               int var40 = -1;
               var23 = world.rand.nextInt(span);
               int var50 = world.rand.nextInt(span);
               if (var23 < span / 4 || var23 > span * 3 / 4 || var50 < span / 4 || var50 > span * 3 / 4) {
                  var23 -= span / 2;
                  var50 -= span / 2;
                  OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var23 + width / 2, cposy + var40, cposz + var50 + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
                  tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var23 + width / 2, cposy + var40, cposz + var50 + width / 2);
                  if (tileentitymobspawner != null) {
                     com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
                  }
               }
            }
         }
      }
   }

   public void buildLevelQ(
      World world, int cposx, int cposy, int cposz, int width, int height, int pw, String critter, int stepside, int stepoff, int holelen, int decor, int level
   ) {
      for (int i = -pw; i < width + pw; i++) {
         for (int j = 1; j < height; j++) {
            for (int k = -pw; k < width + pw; k++) {
               this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
            }
         }
      }

      for (int var19 = 0; var19 < width; var19++) {
         int j = 0;

         for (int k = 0; k < width; k++) {
            this.FastSetBlock(world, cposx + var19, cposy + j, cposz + k, Blocks.BEDROCK);
         }
      }

      for (int var20 = 0; var20 < width; var20++) {
         int j = height;

         for (int k = 0; k < width; k++) {
            this.FastSetBlock(world, cposx + var20, cposy + j, cposz + k, Blocks.BEDROCK);
         }
      }

      for (int var21 = 0; var21 < width; var21++) {
         for (int j = 1; j < height; j++) {
            int k = 0;
            this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.BEDROCK);
            k = width - 1;
            this.FastSetBlock(world, cposx + var21, cposy + j, cposz + k, Blocks.BEDROCK);
         }
      }

      for (int k = 0; k < width; k++) {
         for (int j = 1; j < height; j++) {
            Block blk = Blocks.BEDROCK;
            if (k == 0 || k == width - 1) {
               blk = OreSpawn.MyBlockRubyBlock;
            }

            int var22 = 0;
            this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
            var22 = width - 1;
            this.FastSetBlock(world, cposx + var22, cposy + j, cposz + k, blk);
         }
      }

      for (int var24 = -pw; var24 < width + pw; var24++) {
         for (int var40 = -pw; var40 < width + pw; var40++) {
            if (var24 < 0 || var40 < 0 || var24 >= width || var40 >= width) {
               this.FastSetBlock(world, cposx + var24, cposy, cposz + var40, Blocks.OBSIDIAN);
            }

            if (var24 == -pw || var40 == -pw || var24 == width + (pw - 1) || var40 == width + (pw - 1)) {
               this.FastSetBlock(world, cposx + var24, cposy + 1, cposz + var40, Blocks.NETHER_BRICK_FENCE);
            }
         }
      }

      int var25 = -(height / 2);
      var25 += width / 2;

      for (int j = 1; j < height; j++) {
         if (stepside != 0) {
            int var41 = -1;
            this.FastSetBlock(world, cposx + var25, cposy + j, cposz + var41, Blocks.OBSIDIAN);
         } else {
            this.FastSetBlock(world, cposx + var25, cposy + j, cposz + width, Blocks.OBSIDIAN);
         }

         var25++;
      }

      if (stepoff >= 0) {
         int var43;
         if (stepside == 0) {
            int var42 = -1;
            var43 = var42 - stepoff;
         } else {
            var43 = width + stepoff;
         }

         var25 = width / 2;
         int var33 = 0;

         for (int l = 0; l < holelen; l++) {
            this.FastSetBlock(world, cposx + var25 + l, cposy + var33, cposz + var43, Blocks.AIR);
         }
      }

      TileEntityMobSpawner tileentitymobspawner = null;

      for (int var34 = 0; var34 < 4; var34++) {
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz - (pw - 1));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx - (pw - 1), cposy + var34 + 1, cposz + width + (pw - 2));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz - (pw - 1));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width + (pw - 2), cposy + var34 + 1, cposz + width + (pw - 2)), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width + (pw - 2), cposy + var34 + 1, cposz + width + (pw - 2));
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }
      }

      this.addLevelDecorationsQ(world, cposx, cposy, cposz, width, height, decor, level);
   }

   public void addLevelDecorationsQ(World world, int cposx, int cposy, int cposz, int width, int height, int decor, int difficulty) {
      TileEntityMobSpawner tileentitymobspawner = null;
      int reward = 1;
      String critter = "T. Rex";
      if (decor == 6) {
         this.FastSetBlock(world, cposx, cposy + height, cposz, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx, cposy + height + 1, cposz, Blocks.FIRE);
         this.FastSetBlock(world, cposx, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx, cposy + height + 1, cposz + width - 1, Blocks.FIRE);
         this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz, Blocks.FIRE);
         this.FastSetBlock(world, cposx + width - 1, cposy + height, cposz + width - 1, Blocks.NETHERRACK);
         this.FastSetBlock(world, cposx + width - 1, cposy + height + 1, cposz + width - 1, Blocks.FIRE);
         this.FastSetBlock(world, cposx + width / 2, cposy + height, cposz + width / 2, Blocks.AIR);
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 - 1, cposy + height + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2 + 1, cposy + height + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 - 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + height + 2, cposz + width / 2 + 1);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }

         for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < 5; j++) {
               for (int k = 1; k < width - 1; k++) {
                  this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.DIRT);
               }
            }
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 4, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 4, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Large Worm");
         }

         for (int j = 0; j < 10; j++) {
            this.FastSetBlock(world, cposx + 1, cposy + j, cposz + 1, Blocks.AIR);
         }

         this.fill_chests(world, cposx, cposy + 4, cposz, width, height, decor, reward);
      }

      if (decor == 5) {
         if (difficulty == 5) {
            critter = "T. Rex";
            reward = 1;
         }

         if (difficulty == 6) {
            critter = "Nastysaurus";
            reward = 2;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
         this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 4) {
         if (difficulty == 4) {
            critter = "T. Rex";
            reward = 1;
         }

         if (difficulty == 5) {
            critter = "Nastysaurus";
            reward = 2;
         }

         if (difficulty == 6) {
            critter = "Basilisk";
            reward = 3;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
         this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 3) {
         if (difficulty == 3) {
            critter = "T. Rex";
            reward = 1;
         }

         if (difficulty == 4) {
            critter = "Nastysaurus";
            reward = 2;
         }

         if (difficulty == 5) {
            critter = "Basilisk";
            reward = 3;
         }

         if (difficulty == 6) {
            critter = "Hercules Beetle";
            reward = 4;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + width - 2, cposy, cposz + width - 2, Blocks.AIR);
         this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 2) {
         if (difficulty == 2) {
            critter = "T. Rex";
            reward = 1;
         }

         if (difficulty == 3) {
            critter = "Nastysaurus";
            reward = 2;
         }

         if (difficulty == 4) {
            critter = "Basilisk";
            reward = 3;
         }

         if (difficulty == 5) {
            critter = "Hercules Beetle";
            reward = 4;
         }

         if (difficulty == 6) {
            critter = "Jumpy Bug";
            reward = 5;
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + 1, cposy, cposz + 1, Blocks.AIR);
         this.FastSetBlock(world, cposx + width - 2, cposy + height, cposz + width - 2, Blocks.AIR);
         this.fill_chests(world, cposx, cposy, cposz, width, height, decor, reward);
      }

      if (decor == 1) {
         if (difficulty == 1) {
            critter = "T. Rex";
         }

         if (difficulty == 2) {
            critter = "Nastysaurus";
         }

         if (difficulty == 3) {
            critter = "Basilisk";
         }

         if (difficulty == 4) {
            critter = "Hercules Beetle";
         }

         if (difficulty == 5) {
            critter = "Jumpy Bug";
         }

         if (difficulty == 6) {
            critter = "CaterKiller";
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 2, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 2, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 3, cposz + width / 2), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + width / 2, cposy + 3, cposz + width / 2);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, critter);
         }

         for (int j = 1; j < 5; j++) {
            this.FastSetBlock(world, cposx + width / 2 - 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2 + 1, cposy + j, cposz + width / 2, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 - 1, Blocks.BEDROCK);
            this.FastSetBlock(world, cposx + width / 2, cposy + j, cposz + width / 2 + 1, Blocks.BEDROCK);
         }

         this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + width / 2 + 1, cposy + 1, cposz + width / 2 - 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + width / 2 - 1, cposy + 1, cposz + width / 2 + 1, OreSpawn.MyRTPBlock);
         this.FastSetBlock(world, cposx + 1, cposy + height, cposz + 1, Blocks.AIR);
         this.fill_chestsQ(world, cposx, cposy, cposz, width, height, decor, difficulty);
      }
   }

   private void fill_chestsQ(World world, int cposx, int cposy, int cposz, int width, int height, int decor, int reward) {
      TileEntityChest chest = null;
      com.electrifiedded.orespawn.util.SimpleChest[] chestContents = null;
      chestContents = this.level1ContentsList;
      if (reward == 2) {
         chestContents = this.level2ContentsList;
      }

      if (reward == 3) {
         chestContents = this.level3ContentsList;
      }

      if (reward == 4) {
         chestContents = this.level4ContentsList;
      }

      if (reward == 5) {
         chestContents = this.level5ContentsList;
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + 1, cposy + 1, cposz + width / 2)).getBlock().getStateFromMeta(5), 3);
      chest = this.getChestTileEntity(world, cposx + 1, cposy + 1, cposz + width / 2);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.ThePrincessEgg, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width / 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width / 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width - 2, cposy + 1, cposz + width / 2)).getBlock().getStateFromMeta(4), 3);
      chest = this.getChestTileEntity(world, cposx + width - 2, cposy + 1, cposz + width / 2);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.QueenHelmet, 1, 0));
            chest.setInventorySlotContents(2, new ItemStack(OreSpawn.QueenBody, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1)).getBlock().getStateFromMeta(3), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + 1);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.QueenLegs, 1, 0));
            chest.setInventorySlotContents(2, new ItemStack(OreSpawn.QueenBoots, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width - 2), (Blocks.CHEST).getStateFromMeta(0), 2);
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width - 2), OreSpawn.getBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + width - 2)).getBlock().getStateFromMeta(2), 3);
      chest = this.getChestTileEntity(world, cposx + width / 2, cposy + 1, cposz + width - 2);
      if (chest != null) {
         if (reward == 6) {
            chest.setInventorySlotContents(1, new ItemStack(OreSpawn.MyRoyal, 1, 0));
         } else {
            com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, chestContents, chest, 5 + world.rand.nextInt(7));
         }
      }
   }

   public void makeSpiderHangout(World world, int cposx, int cposy, int cposz) {
      Entity var8 = null;
      TileEntityMobSpawner tileentitymobspawner = null;

      for (int i = 0; i < 20; i++) {
         for (int j = -1; j < 20; j++) {
            for (int k = 0; k < 20; k++) {
               Block blk = Blocks.AIR;
               if (j == -1) {
                  blk = Blocks.STONE;
               }

               if (j == 0) {
                  blk = Blocks.GRAVEL;
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, blk, 0, 2);
            }
         }
      }

      for (int j = 1; j < 4; j++) {
         int k = 0;
         int var11 = 0;
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var11, cposy + j, cposz + k), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + k);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spider Driver");
         }

         int var17 = 19;
         var11 = 19;
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var11, cposy + j, cposz + var17), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + var17);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spider Driver");
         }

         var17 = 0;
         var11 = 19;
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var11, cposy + j, cposz + var17), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + var17);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spider Driver");
         }

         var17 = 19;
         var11 = 0;
         OreSpawn.setBlockStateFast(world, new BlockPos(cposx + var11, cposy + j, cposz + var17), (Blocks.MOB_SPAWNER).getStateFromMeta(0), 2);
         tileentitymobspawner = this.getSpawnerTileEntity(world, cposx + var11, cposy + j, cposz + var17);
         if (tileentitymobspawner != null) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Spider Driver");
         }
      }

      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create("Robot Spider", world);
      if (var8 != null) {
         var8.setLocationAndAngles(cposx + 10, cposy + 1, cposz + 10, world.rand.nextFloat() * 360.0F, 0.0F);
         world.spawnEntity(var8);
      }
   }

   public void makeRedAntHangout(World world, int cposx, int cposy, int cposz) {
      Entity var8 = null;

      for (int i = 0; i < 16; i++) {
         for (int j = -1; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
               Block blk = Blocks.AIR;
               if (j == -1) {
                  blk = Blocks.STONE;
               }

               if (j == 0) {
                  blk = Blocks.GRAVEL;
                  if ((i < 3 || i > 12) && (k < 3 || k > 12)) {
                     blk = OreSpawn.MyRedAntBlock;
                  }
               }

               OreSpawn.setBlockFast(world, cposx + i, cposy + j, cposz + k, blk, 0, 2);
            }
         }
      }

      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create("Robot Red Ant", world);
      if (var8 != null) {
         var8.setLocationAndAngles(cposx + 8, cposy + 1, cposz + 8, world.rand.nextFloat() * 360.0F, 0.0F);
         world.spawnEntity(var8);
      }
   }

   private void placeDoorBlock(World world, int x, int y, int z, int direction, BlockDoor door) {
      EnumFacing facing;
      switch (direction & 3) {
         case 0:
            facing = EnumFacing.SOUTH;
            break;
         case 1:
            facing = EnumFacing.WEST;
            break;
         case 2:
            facing = EnumFacing.NORTH;
            break;
         default:
            facing = EnumFacing.EAST;
      }
      net.minecraft.block.state.IBlockState state = door.getDefaultState()
         .withProperty(BlockDoor.FACING, facing)
         .withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT)
         .withProperty(BlockDoor.POWERED, false)
         .withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER);
      BlockPos pos = new BlockPos(x, y, z);
      OreSpawn.setBlockStateFast(world, pos, state, 2);
      OreSpawn.setBlockStateFast(world, pos.up(), state.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
   }
}

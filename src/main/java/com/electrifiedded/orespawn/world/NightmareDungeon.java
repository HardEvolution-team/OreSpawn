package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.world.World;

public class NightmareDungeon {
   private final com.electrifiedded.orespawn.util.SimpleChest[] chestContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CageEmpty, 0, 3, 10, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceBody, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceLegs, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceHelmet, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.ExperienceBoots, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyExperienceSword, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateBody, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateLegs, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateHelmet, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UltimateBoots, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateSword, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimatePickaxe, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateShovel, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateHoe, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateAxe, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateBow, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBertha, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySlice, 0, 1, 1, 25),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethyst, 0, 2, 8, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyBacon, 0, 6, 12, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyButterCandy, 0, 6, 12, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystPickaxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystShovel, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystHoe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystAxe, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyAmethystSword, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystBody, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystLegs, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystHelmet, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.AmethystBoots, 0, 1, 1, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyThunderStaff, 0, 1, 1, 5)
   };

   private void setThisBlock(World world, int cposx, int cposy, int cposz) {
      if (world.rand.nextInt(2) == 1) {
         this.FastSetBlock(world, cposx, cposy, cposz, Blocks.BEDROCK);
      } else {
         this.FastSetBlock(world, cposx, cposy, cposz, Blocks.OBSIDIAN);
      }
   }

   public void makeDungeon(World world, int cposx, int cposy, int cposz) {
      int width = 25;
      int height = 12;

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
            this.FastSetBlock(world, cposx + var12, cposy + j, cposz + k, OreSpawn.MyRTPBlock);
         }
      }

      for (int var13 = 0; var13 < width; var13++) {
         int j = height - 1;

         for (int k = 0; k < width; k++) {
            this.FastSetBlock(world, cposx + var13, cposy + j, cposz + k, Blocks.BEDROCK);
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
      TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(cposx + width / 2, cposy + 1, cposz + width / 2));
      if (tileentitymobspawner != null) {
         if (world.rand.nextInt(2) == 1) {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Emperor Scorpion");
         } else {
            com.electrifiedded.orespawn.util.SpawnerCompat.set(tileentitymobspawner, "Nightmare");
         }
      }

      TileEntityChest chest = null;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = (TileEntityChest)world.getTileEntity(new BlockPos(cposx + width / 2 + 1, cposy + 1, cposz + width / 2 + 1));
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, this.chestContentsList, chest, 4 + world.rand.nextInt(7));
      }

      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = (TileEntityChest)world.getTileEntity(new BlockPos(cposx + width / 2 - 1, cposy + 1, cposz + width / 2 - 1));
      if (chest != null) {
         com.electrifiedded.orespawn.util.SimpleChest.fill(world.rand, this.chestContentsList, chest, 4 + world.rand.nextInt(7));
      }
   }

   public void FastSetBlock(World world, int ix, int iy, int iz, Block id) {
      OreSpawn.setBlockFast(world, ix, iy, iz, id, 0, 3);
   }
}

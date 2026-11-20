package com.electrifiedded.orespawn.world;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import com.electrifiedded.orespawn.util.SimpleChest;
import net.minecraft.world.World;

public class RubyBirdDungeon {
   private final SimpleChest[] chestContentsList = new SimpleChest[]{
      new SimpleChest(OreSpawn.CageEmpty, 0, 3, 10, 20),
      new SimpleChest(OreSpawn.MyRuby, 0, 2, 8, 15),
      new SimpleChest(OreSpawn.MyBacon, 0, 6, 12, 20),
      new SimpleChest(OreSpawn.MyButterCandy, 0, 6, 12, 20),
      new SimpleChest(OreSpawn.MyRubyPickaxe, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.MyRubyShovel, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.MyRubyHoe, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.MyRubyAxe, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.MyRubySword, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.RubyBody, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.RubyLegs, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.RubyHelmet, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.RubyBoots, 0, 1, 1, 15),
      new SimpleChest(OreSpawn.MyThunderStaff, 0, 1, 1, 5)
   };

   private void setThisBlock(World world, int cposx, int cposy, int cposz) {
      if (world.rand.nextInt(20) == 1) {
         this.FastSetBlock(world, cposx, cposy, cposz, OreSpawn.MyOreRubyBlock);
      } else if (world.rand.nextInt(2) == 1) {
         this.FastSetBlock(world, cposx, cposy, cposz, Blocks.MOSSY_COBBLESTONE);
      } else {
         this.FastSetBlock(world, cposx, cposy, cposz, Blocks.COBBLESTONE);
      }
   }

   public void makeDungeon(World world, int cposx, int cposy, int cposz) {
      int width = 10;
      int height = 5;

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
      TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(new BlockPos(cposx + width / 2, cposy + 1, cposz + width / 2));
      if (tileentitymobspawner != null) {
         tileentitymobspawner.getSpawnerBaseLogic().setEntityId(new net.minecraft.util.ResourceLocation(com.electrifiedded.orespawn.OreSpawnMod.MOD_ID, "ruby_bird"));
      }

      TileEntityChest chest = null;
      OreSpawn.setBlockStateFast(world, new BlockPos(cposx + width / 2, cposy + 1, cposz + 1), (Blocks.CHEST).getStateFromMeta(0), 2);
      chest = (TileEntityChest)world.getTileEntity(new BlockPos(cposx + width / 2, cposy + 1, cposz + 1));
      if (chest != null) {
         SimpleChest.fill(world.rand, this.chestContentsList, chest, 4 + world.rand.nextInt(7));
      }
   }

   public void FastSetBlock(World world, int ix, int iy, int iz, Block id) {
      OreSpawn.setBlockFast(world, ix, iy, iz, id, 0, 2);
   }
}

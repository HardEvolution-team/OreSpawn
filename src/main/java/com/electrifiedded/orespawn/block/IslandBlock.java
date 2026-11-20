package com.electrifiedded.orespawn.block;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import javax.annotation.Nullable;

public class IslandBlock extends BlockReed {
   private static final AxisAlignedBB ISLAND_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

   public IslandBlock(String registryName) {
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTranslationKey(registryName);
      this.setRegistryName(registryName);
      this.setLightLevel(0.9F);
   }

   @Nullable
   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos) {
      return NULL_AABB;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
      randomDisplayTick(world, pos.getX(), pos.getY(), pos.getZ(), random);
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, net.minecraft.world.IBlockAccess source, BlockPos pos) {
      return ISLAND_AABB;
   }

   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos pos) {
      return world.getBlockState(pos.down()).getMaterial().isSolid();
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return par1World.getBlockState(new BlockPos(par2, par3 - 1, par4)).getMaterial().isSolid();
   }

   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if (par1World.rand.nextInt(20) == 1) {
         for (int j1 = 0; j1 < 20; j1++) {
            par1World.spawnParticle(
               net.minecraft.util.EnumParticleTypes.VILLAGER_HAPPY,
               par2 + par1World.rand.nextFloat(),
               (double)par3 + par1World.rand.nextFloat(),
               par4 + par1World.rand.nextFloat(),
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      int isok = 1;
      if (!par1World.isRemote) {
         int n = 1 + par1World.rand.nextInt(3);
         int m = 64;
         if (OreSpawn.IslandSizeFactor == 2) {
            m = 55;
         }

         if (OreSpawn.IslandSizeFactor == 1) {
            m = 45;
         }

         for (int i = 0; i < n; i++) {
            int height = 12 + par1World.rand.nextInt(m);
            boolean var14 = true;

            for (int k = -10; k <= 10; k++) {
               for (int j = -10; j <= 10; j++) {
                  Block bid = par1World.getBlockState(new BlockPos(par2 + j, par3 + height, par4 + k)).getBlock();
                  if (bid != Blocks.AIR) {
                     var14 = false;
                     break;
                  }
               }
            }

            if (var14) {
               if (par1World.rand.nextInt(25) == 1) {
                  spawnCreature(par1World, "Island", par2, par3 + height, par4);
               } else {
                  spawnCreature(par1World, "IslandToo", par2, par3 + height, par4);
               }
            }
         }

         par1World.setBlockState(new BlockPos(par2, par3, par4), (Blocks.AIR).getStateFromMeta(0), 2);
         par1World.setBlockState(new BlockPos(par2, par3 + 1, par4), (Blocks.AIR).getStateFromMeta(0), 2);
      }
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Item.getItemFromBlock(this);
   }

   @Override
   public net.minecraft.item.ItemStack getItem(World world, BlockPos pos, IBlockState state) {
      return new net.minecraft.item.ItemStack(this);
   }

   public int quantityDropped(Random par1Random) {
      return 1;
   }

   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(par1, par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }



    @Override
    public void updateTick(World world, BlockPos pos, net.minecraft.block.state.IBlockState state, Random random) {
        updateTick(world, pos.getX(), pos.getY(), pos.getZ(), random);
    }
}

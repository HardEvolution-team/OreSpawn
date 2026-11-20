package com.electrifiedded.orespawn.block;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

final class MyDispenserBehaviorRock extends BehaviorProjectileDispense {
   public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
      World world = par1IBlockSource.getWorld();
      IPosition iposition = BlockDispenser.getDispensePosition(par1IBlockSource);
       EnumFacing enumfacing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);
       IProjectile iprojectile = this.getProjectileEntity(world, iposition, par2ItemStack);
      iprojectile.shoot(enumfacing.getXOffset(), enumfacing.getYOffset() + 0.1F, enumfacing.getZOffset(), this.getProjectileVelocity(), this.getProjectileInaccuracy());
      EntityThrownRock r = (EntityThrownRock)iprojectile;
      if (par2ItemStack.getItem() == OreSpawn.MySmallRock) {
         r.setRockType(1);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyRock) {
         r.setRockType(2);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyRedRock) {
         r.setRockType(3);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyGreenRock) {
         r.setRockType(4);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyBlueRock) {
         r.setRockType(5);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyPurpleRock) {
         r.setRockType(6);
      }

      if (par2ItemStack.getItem() == OreSpawn.MySpikeyRock) {
         r.setRockType(7);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyTNTRock) {
         r.setRockType(8);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyCrystalRedRock) {
         r.setRockType(9);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyCrystalGreenRock) {
         r.setRockType(10);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyCrystalBlueRock) {
         r.setRockType(11);
      }

      if (par2ItemStack.getItem() == OreSpawn.MyCrystalTNTRock) {
         r.setRockType(12);
      }

      world.spawnEntity((Entity)iprojectile);
      par2ItemStack.splitStack(1);
      return par2ItemStack;
   }

   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, net.minecraft.item.ItemStack stack) {
      return new EntityThrownRock(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
   }
}

package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public class ItemElevator extends Item {
   public ItemElevator(int par1) {
      this.setCreativeTab(CreativeTabs.TRANSPORTATION);
      this.setMaxStackSize(1);
      if (this.getRegistryName() == null) this.setRegistryName("elevator");
      this.setTranslationKey("elevator");
   }

   @Override
   public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                     EnumFacing facing, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(),
         facing.getIndex(), hitX, hitY, hitZ) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10
   ) {
      if (par3World.isRemote) {
         return true;
      } else {
         Elevator elevator = (Elevator)com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create("Hoverboard", par3World);
         elevator.setLocationAndAngles(par4 + 0.5F, par5 + 1.2F, par6 + 0.5F, par3World.rand.nextFloat() * 360.0F, 0.0F);
         par3World.spawnEntity(elevator);
         if (!par2EntityPlayer.capabilities.isCreativeMode) {
            par1ItemStack.shrink(1);
         }

         return true;
      }
   }


}

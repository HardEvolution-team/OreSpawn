package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ExperienceCatcher extends Item {
   public ExperienceCatcher(int i) {

      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public ExperienceCatcher(String name) {
      this(0);
      setRegistryName(name);
      setTranslationKey(name);
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
      net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing facing, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(),
         facing.getIndex(), hitX, hitY, hitZ) ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10
   ) {
      par2EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
      if (!par2EntityPlayer.world.isRemote) {
         AxisAlignedBB bb = new AxisAlignedBB(x - 0.5 + par8, y, z - 0.5 + par10, x + 0.5 + par8, y + 2.0, z + 0.5 + par10);

         for (Entity var3x : world.getEntitiesWithinAABB(EntityXPOrb.class, bb)) {
            if (var3x instanceof EntityXPOrb) {
               EntityXPOrb ex = (EntityXPOrb)var3x;
               if (ex.getXpValue() >= 3 && world.rand.nextInt(5) != 1) {
                  var3x.setDead();
                  EntityItem var4x = null;
                  ItemStack is = new ItemStack(Items.EXPERIENCE_BOTTLE, 1, 0);
                  var4x = new EntityItem(par2EntityPlayer.world, par8 + x, y + 1.0, par10 + z, is);
                  if (var4x != null) {
                     par2EntityPlayer.world.spawnEntity(var4x);
                  }

                  is = new ItemStack(Items.STRING, 1, 0);
                  var4x = new EntityItem(par2EntityPlayer.world, par8 + x, y + 1.0, par10 + z, is);
                  if (var4x != null) {
                     par2EntityPlayer.world.spawnEntity(var4x);
                  }

                  is = new ItemStack(Items.STICK, 1, 0);
                  var4x = new EntityItem(par2EntityPlayer.world, par8 + x, y + 1.0, par10 + z, is);
                  if (var4x != null) {
                     par2EntityPlayer.world.spawnEntity(var4x);
                  }

                  if (!par2EntityPlayer.capabilities.isCreativeMode) {
                     par1ItemStack.shrink(1);
                  }

                  return true;
               }
            }
         }

         EntityItem var4x = null;
         ItemStack isx = new ItemStack(OreSpawn.MyExperienceCatcher, 1, 0);
         var4x = new EntityItem(par2EntityPlayer.world, par8 + x, y + 1.0, par10 + z, isx);
         if (var4x != null) {
            par2EntityPlayer.world.spawnEntity(var4x);
         }

         par1ItemStack.shrink(1);
      }

      return true;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
      return par1ItemStack;
   }

   @Override
   public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return new net.minecraft.util.ActionResult<>(net.minecraft.util.EnumActionResult.SUCCESS, onItemRightClick(player.getHeldItem(hand), world, player));
   }
}

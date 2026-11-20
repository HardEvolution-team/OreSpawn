package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;

public class UltimateHoe extends ItemHoe {
   public UltimateHoe(int par1, ToolMaterial par2) {
      super(par2);

      this.setMaxDamage(3000);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.EFFICIENCY, 2);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.EFFICIENCY, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.EFFICIENCY, 2);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public boolean onItemUse(
       ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos, EnumFacing facing, float par8, float par9, float par10
    ) {
       if (!par2EntityPlayer.canPlayerEdit(pos, facing, par1ItemStack)) {
         return false;
      } else {
          int par4 = pos.getX(), par5 = pos.getY(), par6 = pos.getZ();
          Block i1 = par3World.getBlockState(pos).getBlock();
          boolean air = par3World.isAirBlock(pos.up());
          if (facing != EnumFacing.DOWN && air && (i1 == Blocks.GRASS || i1 == Blocks.DIRT)) {
            Block block = Blocks.SOUL_SAND;
             par3World.playSound(
                null, pos,
                block.getSoundType().getBreakSound(),
                SoundCategory.BLOCKS,
               (block.getSoundType().getVolume() + 1.0F) / 2.0F,
               block.getSoundType().getPitch() * 0.8F
            );
            if (par3World.isRemote) {
               return true;
            } else {
               for (int i = -1; i <= 1; i++) {
                  for (int k = -1; k <= 1; k++) {
                     for (int j = -1; j <= 1; j++) {
                        i1 = par3World.getBlockState(new BlockPos(par4 + i, par5 + j, par6 + k)).getBlock();
                        air = par3World.isAirBlock(new BlockPos(par4 + i, par5 + j + 1, par6 + k));
                        if (air && (i1 == Blocks.GRASS || i1 == Blocks.DIRT)) {
                           par3World.setBlockState(new BlockPos(par4 + i, par5 + j, par6 + k), (block).getStateFromMeta(7), 2);
                        }
                     }
                  }
               }

               par1ItemStack.damageItem(1, par2EntityPlayer);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public String getMaterialName() {
      return "Uranium/Titanium";
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
         net.minecraft.util.EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos, face, hitX, hitY, hitZ)
         ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

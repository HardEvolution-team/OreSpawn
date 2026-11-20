package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRandomDungeon extends Item {
   Random rand = OreSpawn.OreSpawnRand;

   public ItemRandomDungeon(int i) {

      this.setCreativeTab(CreativeTabs.REDSTONE);
   }
   public ItemRandomDungeon(String name) { this(0); setRegistryName(name); setTranslationKey(name); setMaxStackSize(1); }
   @Override public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float x, float y, float z) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), x, y, z) ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.FORTUNE, 2);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.FORTUNE, 2);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public boolean onItemUse(
      ItemStack par1ItemStack,
      EntityPlayer par2EntityPlayer,
      World world,
      int clickedX,
      int clickedY,
      int clickedZ,
      int par7,
      float par8,
      float par9,
      float par10
   ) {
      Block var1x = world.getBlockState(new BlockPos(clickedX, clickedY, clickedZ)).getBlock();
      if (var1x != Blocks.STONE && var1x != Blocks.COBBLESTONE && var1x != Blocks.GRASS && var1x != Blocks.DIRT) {
         return false;
      } else if (clickedY < 40) {
         return false;
      } else {
         if (!world.isRemote) {
            world.setBlockState(new BlockPos(clickedX, clickedY + 1, clickedZ), (OreSpawn.MyDungeonSpawnerBlock).getStateFromMeta(0), 2);
         }

         if (!par2EntityPlayer.capabilities.isCreativeMode) {
            par1ItemStack.shrink(1);
         }

         return true;
      }
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

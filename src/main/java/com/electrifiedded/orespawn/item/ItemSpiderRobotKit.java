package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpiderRobotKit extends Item {
   private final boolean spider;

   public ItemSpiderRobotKit(String name, boolean spider) {
      this.spider = spider;
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setMaxStackSize(1);
      this.setMaxDamage(100);
      this.setRegistryName(name);
      this.setTranslationKey(name);
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
                                                        net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face,
                                                        float hitX, float hitY, float hitZ) {
      ItemStack stack = player.getHeldItem(hand);
      BlockPos spawnPos = pos.offset(face);
      boolean result = use(stack, player, world, spawnPos);
      return result ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.FAIL;
   }

   private boolean use(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos pos) {
      if (par3World.isRemote) {
         return true;
      } else {
         String name = spider ? "Robot Spider" : "Robot Red Ant";

         Entity ent = spawnCreature(par3World, 0, name, pos.getX() + 0.5, pos.getY() + 0.01, pos.getZ() + 0.5);
         if (ent != null) {
             EntityLiving e = (EntityLiving)ent;
             float maxHealth = 40.0F;
             if (OreSpawn.AntRobot_stats != null && ent instanceof AntRobot) maxHealth = OreSpawn.AntRobot_stats.health;
             if (OreSpawn.SpiderRobot_stats != null && ent instanceof SpiderRobot) maxHealth = OreSpawn.SpiderRobot_stats.health;
             e.setHealth(Math.min(maxHealth, this.getMaxDamage() - this.getDamage(par1ItemStack)));
            if (ent instanceof EntityLiving && par1ItemStack.hasDisplayName()) {
               ((EntityLiving)ent).setCustomNameTag(par1ItemStack.getDisplayName());
            }

            par3World.playSound(null, par2EntityPlayer.posX, par2EntityPlayer.posY, par2EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, par3World.rand.nextFloat() * 0.2F + 0.9F);
             if (ent instanceof AntRobot) {
                AntRobot a = (AntRobot)ent;
                a.setOwned();
             }
             par2EntityPlayer.startRiding(ent);
         }

         if (!par2EntityPlayer.capabilities.isCreativeMode) {
            par1ItemStack.shrink(1);
         }

         return true;
      }
   }

   public static Entity spawnCreature(World par0World, int par1, String name, double par2, double par4, double par6) {
      Entity var8 = null;
      if (name == null) {
         var8 = EntityList.createEntityByID(par1, par0World);
      } else {
         var8 = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create(name, par0World);
      }

      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }


}

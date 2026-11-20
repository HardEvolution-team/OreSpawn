package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StepAccross extends Item {
   public StepAccross(int i) {

      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public boolean onItemUse(
      ItemStack par1ItemStack, EntityPlayer Player, World world, int cposx, int cposy, int cposz, int par7, float par8, float par9, float par10
   ) {
      int deltax = 0;
      int deltaz = 0;
      int length = 33;
      int x = cposx;
      int y = cposy + 1;
      int z = cposz;
      float f = Player.renderYawOffset;
      f += 22.5F;
      f %= 360.0F;
      f /= 45.0F;
      switch ((int)f) {
         case 0:
            deltax = 0;
            deltaz = 1;
            break;
         case 1:
            deltax = -1;
            deltaz = 1;
            break;
         case 2:
            deltax = -1;
            deltaz = 0;
            break;
         case 3:
            deltax = -1;
            deltaz = -1;
            break;
         case 4:
            deltax = 0;
            deltaz = -1;
            break;
         case 5:
            deltax = 1;
            deltaz = -1;
            break;
         case 6:
            deltax = 1;
            deltaz = 0;
            break;
         case 7:
            deltax = 1;
            deltaz = 1;
      }

      if (deltax == 0 && deltaz == 0) {
         return false;
      } else {
         Player.world.playSound(null, Player.posX, Player.posY, Player.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.5F);
         if (world.isRemote) {
            for (int var3x = 0; var3x < 6; var3x++) {
               world.spawnParticle(
                  net.minecraft.util.EnumParticleTypes.SMOKE_LARGE,
                  x + world.rand.nextFloat() - world.rand.nextFloat(),
                  y + world.rand.nextFloat(),
                  z + world.rand.nextFloat() - world.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
               world.spawnParticle(
                  net.minecraft.util.EnumParticleTypes.EXPLOSION_LARGE,
                  x + world.rand.nextFloat() - world.rand.nextFloat(),
                  y + world.rand.nextFloat(),
                  z + world.rand.nextFloat() - world.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
               world.spawnParticle(
                  net.minecraft.util.EnumParticleTypes.REDSTONE,
                  x + world.rand.nextFloat() - world.rand.nextFloat(),
                  y + world.rand.nextFloat(),
                  z + world.rand.nextFloat() - world.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
            }

            return true;
         } else {
            for (int k = 1; k < length; k++) {
               Block bid = world.getBlockState(new BlockPos(x + k * deltax, y - 1, z + k * deltaz)).getBlock();
               if (bid != Blocks.AIR) {
                  break;
               }

               world.setBlockState(new BlockPos(x + k * deltax, y - 1, z + k * deltaz), (Blocks.COBBLESTONE).getStateFromMeta(0), 2);
               if ((k - 1) % 8 == 0) {
                  bid = world.getBlockState(new BlockPos(x + k * deltax, y, z + k * deltaz)).getBlock();
                  if (bid == Blocks.AIR) {
                     world.setBlockState(new BlockPos(x + k * deltax, y, z + k * deltaz), (OreSpawn.ExtremeTorch).getStateFromMeta(0), 2);
                  }
               }
            }

            if (!Player.capabilities.isCreativeMode) {
               par1ItemStack.shrink(1);
            }

            return true;
         }
      }
   }

   @Override
   public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, net.minecraft.util.math.BlockPos pos,
         net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing face, float hitX, float hitY, float hitZ) {
      return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(), face.getIndex(), hitX, hitY, hitZ)
         ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.PASS;
   }
}

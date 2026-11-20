package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** Random teleport pad (dungeon mazes): stepping on it throws the player ~16 blocks away to a safe spot. */
public class RTPBlock extends Block {
   public RTPBlock(String name) {
      super(Material.ROCK);
      setSoundType(SoundType.STONE);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(RegistryHandler.tabForBlock(name));
   }

   @Override
   public void onEntityWalk(World world, BlockPos pos, Entity entity) {
      if (world.isRemote || !(entity instanceof EntityPlayer)) return;
      EntityPlayer player = (EntityPlayer) entity;
      BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();
      int x = 0, y = 0, z = 0;
      boolean found = false;
      for (int tries = 0; tries < 1000 && !found; tries++) {
         x = pos.getX() + (world.rand.nextInt(2) == 0 ? 16 : -16) + world.rand.nextInt(8) - world.rand.nextInt(8);
         z = pos.getZ() + (world.rand.nextInt(2) == 0 ? 16 : -16) + world.rand.nextInt(8) - world.rand.nextInt(8);
         for (y = pos.getY() - 4; y <= pos.getY() + 4; y++) {
            if (world.getBlockState(check.setPos(x, y - 1, z)).getMaterial().isSolid()
                  && world.getBlockState(check.setPos(x, y, z)).getBlock() == Blocks.AIR
                  && world.getBlockState(check.setPos(x, y + 1, z)).getBlock() == Blocks.AIR) {
               found = true;
               break;
            }
         }
      }
      if (!found) return;
      if (player instanceof EntityPlayerMP) {
         ((EntityPlayerMP) player).connection.setPlayerLocation(x + 0.5, y, z + 0.5, player.rotationYaw, 0.0F);
      } else {
         player.setLocationAndAngles(x + 0.5, y, z + 0.5, player.rotationYaw, 0.0F);
      }
      if (world instanceof WorldServer) {
         WorldServer ws = (WorldServer) world;
         ws.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + 0.5, y + 2.25, z + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
         ws.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, x + 0.5, y + 2.25, z + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
         ws.spawnParticle(EnumParticleTypes.REDSTONE, x + 0.5, y + 2.25, z + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
      }
      world.playSound(null, x + 0.5, y, z + 0.5, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, 1.5F);
   }
}

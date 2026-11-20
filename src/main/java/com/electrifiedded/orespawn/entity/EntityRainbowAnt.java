package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.world.OreSpawnTeleporter;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class EntityRainbowAnt extends EntityAnt {
   public EntityRainbowAnt(World par1World) {
      super(par1World);
      this.setSize(0.1F, 0.1F);
      this.experienceValue = 0;
      this.tasks.addTask(0, new EntityAIPanic(this, 1.4F));
      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 9, 1.0));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   @Override
   public boolean interact(EntityPlayer par1EntityPlayer) {
      if (par1EntityPlayer == null) {
         return false;
      } else if (!(par1EntityPlayer instanceof EntityPlayerMP)) {
         return false;
      } else {
         ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
         if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            var2 = null;
         }

         if (var2 != null) {
            return false;
         } else {
            if (par1EntityPlayer.dimension != OreSpawn.DimensionID3) {
               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()
                  .getPlayerList()
                  .transferPlayerToDimension(
                     (EntityPlayerMP)par1EntityPlayer,
                     OreSpawn.DimensionID3,
                     new OreSpawnTeleporter(
                        net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawn.DimensionID3), OreSpawn.DimensionID3, this.world
                     )
                  );
            } else {
               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()
                  .getPlayerList()
                  .transferPlayerToDimension(
                     (EntityPlayerMP)par1EntityPlayer, 0, new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.world)
                  );
            }

            return true;
         }
      }
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}

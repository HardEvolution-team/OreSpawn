package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.world.OreSpawnTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import com.electrifiedded.orespawn.OreSpawn;

import java.util.List;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityAnt extends EntityAnimal {
   public double moveSpeed = 0.15F;
   private static final ResourceLocation texture1 = new ResourceLocation("orespawndedsport", "ant.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawndedsport", "red_ant.png");
   private static final ResourceLocation texture3 = new ResourceLocation("orespawndedsport", "rainbow_ant.png");
   private static final ResourceLocation texture4 = new ResourceLocation("orespawndedsport", "unstableant.png");
   private static final ResourceLocation texture5 = new ResourceLocation("orespawndedsport", "termite.png");

   public EntityAnt(World par1World) {
      super(par1World);
      this.setSize(0.1F, 0.1F);
      this.experienceValue = 0;
      this.tasks.addTask(0, new EntityAIPanic(this, 1.4));
      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 9, 1.0));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   public ResourceLocation getTexture(EntityAnt a) {
      if (a instanceof EntityRedAnt) {
         return texture2;
      } else if (a instanceof EntityRainbowAnt) {
         return texture3;
      } else if (a instanceof EntityUnstableAnt) {
         return texture4;
      } else {
         return a instanceof Termite ? texture5 : texture1;
      }
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

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
            if (par1EntityPlayer.dimension != OreSpawn.DimensionID) {
               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()
                  .getPlayerList()
                  .transferPlayerToDimension(
                     (EntityPlayerMP)par1EntityPlayer,
                     OreSpawn.DimensionID,
                     new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawn.DimensionID), OreSpawn.DimensionID, this.world)
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

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public int mygetMaxHealth() {
      return 1;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return null;
   }

   protected SoundEvent getDeathSound() {
      return null;
   }

   protected float getSoundVolume() {
      return 0.0F;
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
   }

   protected void dropFewItems(boolean par1, int par2) {
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }

   public boolean getCanSpawnHere() {
      return this.posY < 50.0 ? false : this.findBuddies() <= 4;
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityAnt.class, this.getEntityBoundingBox().grow(20.0, 10.0, 20.0));
      return var5.size();
   }

   public void handleJumpWater() {
      if (this.world.rand.nextInt(200) == 1) {
         this.setRevengeTarget(null);
      }

      super.handleJumpWater();
   }
}

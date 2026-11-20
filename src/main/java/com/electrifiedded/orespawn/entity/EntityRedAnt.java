package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.world.OreSpawnTeleporter;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityRedAnt extends EntityAnt {
   int attack_delay = 20;

   public EntityRedAnt(World par1World) {
      super(par1World);
      this.setSize(0.2F, 0.2F);
      this.moveSpeed = 0.2F;
      this.experienceValue = 1;
      this.tasks.addTask(0, new EntityAIPanic(this, 1.4F));
       this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0, false));
      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 10, 1.0));
      if (OreSpawn.PlayNicely == 0) {
          this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 4, true, false, null));
      }
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
   }

   @Override
   public int mygetMaxHealth() {
      return 2;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      if (OreSpawn.OreSpawnRand.nextInt(15) != 0) {
         return false;
      } else {
         return this.world.getDifficulty() == EnumDifficulty.PEACEFUL ? false : par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
      }
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
            if (par1EntityPlayer.dimension != OreSpawn.DimensionID2) {
               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()
                  .getPlayerList()
                  .transferPlayerToDimension(
                     (EntityPlayerMP)par1EntityPlayer,
                     OreSpawn.DimensionID2,
                     new OreSpawnTeleporter(
                        net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawn.DimensionID2), OreSpawn.DimensionID2, this.world
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
   public void onUpdate() {
      super.onUpdate();
      if (!this.isDead) {
         if (this.attack_delay > 0) {
            this.attack_delay--;
         }

         if (this.attack_delay <= 0) {
            this.attack_delay = 20;
            if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               if (OreSpawn.PlayNicely == 0) {
                  EntityLivingBase e = this.world.getClosestPlayerToEntity(this, 1.5);
                  if (e != null) {
                     this.attackEntityAsMob(e);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}

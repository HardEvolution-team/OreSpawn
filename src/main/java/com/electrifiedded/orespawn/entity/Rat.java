package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

import net.minecraft.entity.ai.EntityAIPanic;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.passive.EntityTameable;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.World;



public class Rat extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Rat.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.25F;

   private String myowner = null;



   public Rat(World par1World) {

      super(par1World);

      this.setSize(0.25F, 0.5F);

      this.experienceValue = 5;

      this.isImmuneToFire = true;

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIPanic(this, 1.35F));

      this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 1.0, false));

      this.tasks.addTask(3, new MyEntityAIWanderALot(this, 10, 1.0));

      this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(5, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Rat_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

   }



   protected boolean canDespawn() {

      return this.isNoDespawnRequired() ? false : this.myowner == null;

   }



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

   }



   public int mygetMaxHealth() {

      return OreSpawn.Rat_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.Rat_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   protected void jump() {

      super.jump();

      this.motionY += 0.25;

      this.posY += 0.25;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return OreSpawnSoundEvents.get("ratlive");

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("rathit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("ratdead");

   }



   protected float getSoundVolume() {

      return 0.45F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.ROTTEN_FLESH;

   }



   public void initCreature() {

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.world.rand.nextInt(200) == 1) {

            this.setRevengeTarget(null);

         }



         if (this.world.rand.nextInt(5) == 1) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               this.setAttacking(1);

               this.getNavigator().tryMoveToEntityLiving(e, 1.25);

               if (this.getDistanceSq(e) < 4.0 && (this.rand.nextInt(8) == 0 || this.rand.nextInt(7) == 1)) {

                  this.attackEntityAsMob(e);

               }

            } else {

               this.setAttacking(0);

               if (this.myowner != null) {

                  EntityPlayer p = this.world.getPlayerEntityByName(this.myowner);

                  if (p != null) {

                     if (this.getDistanceSq(p) > 64.0) {

                        this.getNavigator().tryMoveToEntityLiving(p, 1.75);

                     }



                     if (this.getDistanceSq(p) > 256.0) {

                        this.setPosition(

                           p.posX + this.world.rand.nextFloat() - this.world.rand.nextFloat(),

                           p.posY,

                           p.posZ + this.world.rand.nextFloat() - this.world.rand.nextFloat()

                        );

                     }

                  }

               }

            }

         }



         if (this.world.rand.nextInt(250) == 1) {

            this.heal(1.0F);

         }

      }

   }



   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {

      if (par1EntityLiving == null) {

         return false;

      } else if (par1EntityLiving == this) {

         return false;

      } else if (!par1EntityLiving.isEntityAlive()) {

         return false;

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof Irukandji) {

         return false;

      } else if (par1EntityLiving instanceof Skate) {

         return false;

      } else if (par1EntityLiving instanceof Whale) {

         return false;

      } else if (par1EntityLiving instanceof Flounder) {

         return false;

      } else if (par1EntityLiving instanceof Rat) {

         return false;

      } else if (par1EntityLiving instanceof Ghost) {

         return false;

      } else if (par1EntityLiving instanceof GhostSkelly) {

         return false;

      } else if (par1EntityLiving instanceof DungeonBeast) {

         return false;

      } else {

         if (par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            if (p.capabilities.isCreativeMode) {

               return false;

            }



            if (this.myowner != null) {

               if (this.myowner.equals(p.getUniqueID().toString())) {

                  return false;

               }



               if (OreSpawn.RatPlayerFriendly != 0) {

                  return false;

               }

            }

         }



         if (this.myowner != null && par1EntityLiving instanceof EntityTameable) {

            EntityTameable e = (EntityTameable)par1EntityLiving;

            if (OreSpawn.RatPetFriendly != 0 && e.isTamed()) {

               return false;

            }



            if (e.getCustomNameTag() != null && this.myowner.equals(e.getCustomNameTag())) {

               return false;

            }

         }



         return true;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(9.0, 2.0, 9.0));

         Collections.sort(var5, this.TargetSorter);

         Iterator var2 = var5.iterator();

         Entity var3 = null;

         EntityLivingBase var4 = null;



         while (var2.hasNext()) {

            var3 = (Entity)var2.next();

            var4 = (EntityLivingBase)var3;

            if (this.isSuitableTarget(var4, false)) {

               return var4;

            }

         }



         return null;

      }

   }



   public void setOwner(EntityLivingBase e) {

      EntityPlayer p = null;

      if (e != null && e instanceof EntityPlayer) {

         p = (EntityPlayer)e;

         String s = p.getUniqueID().toString();

         if (s != null) {

            this.myowner = s;

         }

      }

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      if (this.myowner == null) {

         this.myowner = "null";

      }



      par1NBTTagCompound.setString("MyOwner", this.myowner);

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.myowner = par1NBTTagCompound.getString("MyOwner");

      if (this.myowner != null && this.myowner.equals("null")) {

         this.myowner = null;

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      return par1DamageSource.getDamageType().equals("inWall") ? ret : super.attackEntityFrom(par1DamageSource, par2);

   }



   public boolean getCanSpawnHere() {

      int sc = 0;



      for (int k = -2; k < 2; k++) {

         for (int j = -2; j < 2; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Rat")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else {

         if (this.world.provider.getDimension() == OreSpawn.DimensionID5) {

            if (this.posY > 50.0) {

               return false;

            }



            for (int var10 = -1; var10 <= 1; var10++) {

               for (int j = -1; j <= 1; j++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + 1, (int)this.posZ + var10)).getBlock();

                  if (bid == Blocks.AIR) {

                     sc++;

                  }

               }

            }



            if (sc < 4) {

               return false;

            }

         }



         return this.findBuddies() <= 8;

      }

   }



   private int findBuddies() {

      List<Entity> var5 = this.world.getEntitiesWithinAABB(Rat.class, this.getEntityBoundingBox().grow(20.0, 10.0, 20.0));

      return var5.size();

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

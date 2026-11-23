package com.electrifiedded.orespawn.entity;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityAgeable;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMate;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAITempt;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.passive.EntityTameable;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.EnumSkyBlock;

import net.minecraft.world.World;



public class GammaMetroid extends EntityTameable {

   private GenericTargetSorter TargetSorter = null;

   private float moveSpeed = 0.15F;

   private int closest = 99999;

   private int tx = 0;

   private int ty = 0;

   private int tz = 0;



   public GammaMetroid(World par1World) {

      super(par1World);

      this.moveSpeed = 0.15F;

      this.setSize(1.5F, 1.5F);

      this.experienceValue = 20;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMate(this, 1.0));

      this.tasks.addTask(2, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));

      this.tasks.addTask(3, new EntityAITempt(this, 1.2F, Items.IRON_INGOT, false));

      this.tasks.addTask(4, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(6, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.GammaMetroid_stats.attack);

   }



   protected boolean canDespawn() {

      if (this.isChild()) {

         this.enablePersistence();

         return false;

      } else {

         return this.isTamed() ? false : !this.isNoDespawnRequired();

      }

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawn.GammaMetroid_stats.attack);

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

      if (var2 != null && var2.getCount() <= 0) {

         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

         var2 = null;

      }



       if (super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND)) {

         return true;

      } else if (var2 != null && var2.getItem() == Items.IRON_INGOT && par1EntityPlayer.getDistanceSq(this) < 25.0) {

         if (!this.isTamed()) {

            if (!this.world.isRemote) {

               if (this.rand.nextInt(3) == 0) {

                  this.setTamed(true);

                  this.setCustomNameTag(par1EntityPlayer.getUniqueID().toString());

                  this.playTameEffect(true);

                  this.world.setEntityState(this, (byte)7);

                  this.heal(this.mygetMaxHealth() - this.getHealth());

               } else {

                  this.playTameEffect(false);

                  this.world.setEntityState(this, (byte)6);

               }

            }

         } else if (this.isOwner(par1EntityPlayer)) {

            if (this.world.isRemote) {

               this.playTameEffect(true);

               this.world.setEntityState(this, (byte)7);

            }



            if (this.mygetMaxHealth() > this.getHealth()) {

               this.heal(this.mygetMaxHealth() - this.getHealth());

            }

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == Item.getItemFromBlock(Blocks.DEADBUSH)

         && par1EntityPlayer.getDistanceSq(this) < 25.0

         && this.isOwner(par1EntityPlayer)) {

         if (!this.world.isRemote) {

            this.setTamed(false);

            this.setCustomNameTag("");

            this.playTameEffect(false);

            this.world.setEntityState(this, (byte)6);

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == Items.NAME_TAG

         && par1EntityPlayer.getDistanceSq(this) < 16.0

         && this.isOwner(par1EntityPlayer)) {

         this.setCustomNameTag(var2.getDisplayName());

         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 25.0) {

         if (!this.isSitting()) {

            this.setSitting(true);

         } else {

            this.setSitting(false);

         }



         return true;

      } else {

         return false;

      }

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.GammaMetroid_stats.health;

   }



   public int getTotalArmorValue() {

      return OreSpawn.GammaMetroid_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.world.rand.nextInt(5) == 1 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("wtf_living") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("duck_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("alo_death");

   }



   protected float getSoundVolume() {

      return 1.5F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.IRON_INGOT;

   }



   private void dropItemRand(Item index, int par1) {

      EntityItem var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),

         new ItemStack(index, par1, 0)

      );

      this.world.spawnEntity(var3);

   }



   protected void dropFewItems(boolean par1, int par2) {

      int i = 5 + OreSpawn.OreSpawnRand.nextInt(10);



      for (int var4 = 0; var4 < i; var4++) {

         this.dropItemRand(Items.GOLD_NUGGET, 1);

      }



      i = 6 + OreSpawn.OreSpawnRand.nextInt(10);



      for (int var5 = 0; var5 < i; var5++) {

         this.dropItemRand(Items.IRON_INGOT, 1);

      }

   }



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 0) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) <= 9.0) {

                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.25);

               }

            }

         }

      }

   }



   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {

      if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {

         return false;

      } else if (par1EntityLiving == null) {

         return false;

      } else if (par1EntityLiving == this) {

         return false;

      } else if (!par1EntityLiving.isEntityAlive()) {

         return false;

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof GammaMetroid) {

         return false;

      } else if (par1EntityLiving instanceof EntityMob) {

         return false;

      } else if (this.isTamed()) {

         return false;

      } else {

         if (par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            if (p.capabilities.isCreativeMode) {

               return false;

            }

         }



         return true;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else if (this.isChild()) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 3.0, 10.0));

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



   protected boolean isValidLightLevel() {

      int i = MathHelper.floor(this.posX);

      int j = MathHelper.floor(this.getEntityBoundingBox().minY);

      int k = MathHelper.floor(this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(i, j, k)) > this.rand.nextInt(32)) {

         return false;

      } else {

         int l = this.world.getLightFromNeighbors(new BlockPos(i, j, k));

         if (this.world.isThundering()) {
            l = this.world.getLightFromNeighbors(new BlockPos(i, j, k));
         }



         return l <= this.rand.nextInt(8);

      }

   }



   public boolean getCanSpawnHere() {

      for (int k = -3; k < 3; k++) {

         for (int j = -3; j < 3; j++) {

            for (int i = 0; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("WTF?")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.provider.getDimension() == OreSpawn.DimensionID4) {

         return true;

      } else if (this.posY > 50.0) {

         return false;

      } else {

         for (int var10 = -1; var10 < 1; var10++) {

            for (int j = -1; j < 1; j++) {

               for (int ix = 1; ix < 4; ix++) {

                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + ix, (int)this.posZ + var10)).getBlock();

                  if (bid != Blocks.AIR) {

                     return false;

                  }

               }

            }

         }



         return true;

      }

   }



   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {

      int found = 0;



      for (int i = -dy; i <= dy; i++) {

         for (int j = -dz; j <= dz; j++) {

            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();

            if (bid == Blocks.STONE) {

               int d = dx * dx + j * j + i * i;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + dx;

                  this.ty = y + i;

                  this.tz = z + j;

                  found++;

               }

            }



            bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock();

            if (bid == Blocks.STONE) {

               int d = dx * dx + j * j + i * i;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x - dx;

                  this.ty = y + i;

                  this.tz = z + j;

                  found++;

               }

            }

         }

      }



      for (int var12 = -dx; var12 <= dx; var12++) {

         for (int j = -dz; j <= dz; j++) {

            Block bidx = this.world.getBlockState(new BlockPos(x + var12, y + dy, z + j)).getBlock();

            if (bidx == Blocks.STONE) {

               int d = dy * dy + j * j + var12 * var12;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var12;

                  this.ty = y + dy;

                  this.tz = z + j;

                  found++;

               }

            }



            bidx = this.world.getBlockState(new BlockPos(x + var12, y - dy, z + j)).getBlock();

            if (bidx == Blocks.STONE) {

               int d = dy * dy + j * j + var12 * var12;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var12;

                  this.ty = y - dy;

                  this.tz = z + j;

                  found++;

               }

            }

         }

      }



      for (int var13 = -dx; var13 <= dx; var13++) {

         for (int j = -dy; j <= dy; j++) {

            Block bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z + dz)).getBlock();

            if (bidxx == Blocks.STONE) {

               int d = dz * dz + j * j + var13 * var13;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var13;

                  this.ty = y + j;

                  this.tz = z + dz;

                  found++;

               }

            }



            bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z - dz)).getBlock();

            if (bidxx == Blocks.STONE) {

               int d = dz * dz + j * j + var13 * var13;

               if (d < this.closest) {

                  this.closest = d;

                  this.tx = x + var13;

                  this.ty = y + j;

                  this.tz = z - dz;

                  found++;

               }

            }

         }

      }



      return found != 0;

   }



   protected void handleJumpWater() {

      if (!this.isDead) {

         super.handleJumpWater();

         if ((

               this.world.rand.nextInt(20) == 0 && this.getHealth() < this.mygetMaxHealth()

                  || this.world.rand.nextInt(100) == 0

            )

            && OreSpawn.PlayNicely == 0

            && !this.isSitting()) {

            this.closest = 99999;

            this.tx = this.ty = this.tz = 0;



            for (int i = 1; i < 6; i++) {

               int j = i;

               if (i > 2) {

                  j = 2;

               }



               if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) {

                  break;

               }



               if (i >= 4) {

                  i++;

               }

            }



            if (this.closest < 99999) {

               this.getNavigator().tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);

               if (this.closest < 12) {

                  if (this.world.getGameRules().getBoolean("mobGriefing")) {

                     this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getStateFromMeta(0), 2);

                  }



                  this.heal(1.0F);

                  this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.player.burp"), 0.5F, this.world.rand.nextFloat() * 0.2F + 1.5F);

               }

            }

         }

      }

   }



   public EntityAgeable createChild(EntityAgeable entityageable) {

      return this.spawnBabyAnimal(entityageable);

   }



   public GammaMetroid spawnBabyAnimal(EntityAgeable par1EntityAgeable) {

      GammaMetroid w = new GammaMetroid(this.world);

      if (this.isTamed()) {

         this.setCustomNameTag(this.getCustomNameTag());

         w.setTamed(true);

      }



      return w;

   }



   public boolean isWheat(ItemStack par1ItemStack) {

      return par1ItemStack != null && par1ItemStack.getItem() == Items.IRON_INGOT;

   }



   public boolean isBreedingItem(ItemStack par1ItemStack) {

      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;

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

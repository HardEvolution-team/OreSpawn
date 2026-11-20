package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.ResourceLocation;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityAgeable;

import net.minecraft.entity.EntityList;

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMate;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAITempt;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.passive.EntitySquid;

import net.minecraft.entity.passive.EntityTameable;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class RubberDucky extends EntityTameable {

    private static final DataParameter<Byte> DW_22 = EntityDataManager.<Byte>createKey(RubberDucky.class, DataSerializers.BYTE);

    private static final DataParameter<Byte> DW_23 = EntityDataManager.<Byte>createKey(RubberDucky.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   public boolean should_despawn = true;

   private EntityLivingBase buddy = null;

   private float moveSpeed = 0.22F;

   private int killcount = 0;

   private int died = 0;

   private RenderInfo renderdata = new RenderInfo();

   private int closest = 99999;

   private int tx = 0;

   private int ty = 0;

   private int tz = 0;



   public RubberDucky(World par1World) {

      super(par1World);

      this.moveSpeed = 0.22F;

      this.setSize(0.33F, 0.5F);

      this.experienceValue = 15;

      this.isImmuneToFire = true;

      this.isImmuneToFire = false;

      this.renderdata = new RenderInfo();

      this.TargetSorter = new GenericTargetSorter(this);

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMate(this, 1.0));

      this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 2.0F, 10.0F, 2.0F));

      this.tasks.addTask(2, new EntityAIMate(this, 1.0));

      this.tasks.addTask(3, new EntityAITempt(this, 1.25, Items.FISH, false));

      this.tasks.addTask(4, new MyEntityAIWanderALot(this, 16, 1.0));

      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 6.0F));

      this.tasks.addTask(5, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_23, (byte)0);

      this.dataManager.register(DW_22, (byte)0);

      this.setSitting(false);

      if (this.getGrowingAge() < 0) {

         this.setGrowingAge(-this.getGrowingAge());

      }



      if (this.renderdata == null) {

         this.renderdata = new RenderInfo();

      }



      this.renderdata.rf1 = 0.0F;

      this.renderdata.rf2 = 0.0F;

      this.renderdata.rf3 = 0.0F;

      this.renderdata.rf4 = 0.0F;

      this.renderdata.ri1 = 0;

      this.renderdata.ri2 = 0;

      this.renderdata.ri3 = 0;

      this.renderdata.ri4 = 0;

   }



   public RenderInfo getRenderInfo() {

      return this.renderdata;

   }



   public void setRenderInfo(RenderInfo r) {

      this.renderdata.rf1 = r.rf1;

      this.renderdata.rf2 = r.rf2;

      this.renderdata.rf3 = r.rf3;

      this.renderdata.rf4 = r.rf4;

      this.renderdata.ri1 = r.ri1;

      this.renderdata.ri2 = r.ri2;

      this.renderdata.ri3 = r.ri3;

      this.renderdata.ri4 = r.ri4;

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

      if (this.isInWater()) {

         this.motionY += 0.1F;

         if (this.motionY < -0.05F) {

            this.motionY = -0.05F;

         }

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      Entity w = null;

      w = par1DamageSource.getTrueSource();

      ret = super.attackEntityFrom(par1DamageSource, par2);

      this.setSitting(false);

      if (!this.world.isRemote

         && w != null

         && w instanceof EntityPlayer

         && (this.isDead || this.getHealth() <= 0.0F)

         && this.died == 0) {

         this.died = 1;

         this.killcount++;

         this.setKillCount(this.killcount);

         if (this.killcount < 10) {

            for (int m = 0; m < 20; m++) {

               int i = this.world.rand.nextInt(3);

               if (this.world.rand.nextInt(2) == 1) {

                  i = -i;

               }



               int k = this.world.rand.nextInt(3);

               if (this.world.rand.nextInt(2) == 1) {

                  k = -k;

               }



               for (int j = 3; j > -3; j--) {

                  if (this.world.getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j + 1, (int)this.posZ + k)).getBlock()

                        == Blocks.AIR

                     && this.world.getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + j, (int)this.posZ + k)).getBlock()

                        != Blocks.AIR) {

                     Entity e = spawnCreature(

                        this.world, "Rubber Ducky", (int)this.posX + i + 1, (int)this.posY + j + 1, (int)this.posZ + k

                     );

                     if (e != null) {

                        RubberDucky d = (RubberDucky)e;

                        d.setKillCount(this.killcount);

                     }



                     return ret;

                  }

               }

            }

         }

      }



      return ret;

   }



   public int mygetMaxHealth() {

      return 5;

   }



   public int getTotalArmorValue() {

      return 1;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

   }



   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {

      Entity var8 = null;

      var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);

      if (var8 != null) {

         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);

         par0World.spawnEntity(var8);

         ((EntityLiving)var8).playLivingSound();

      }



      return var8;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.world.rand.nextInt(10) == 1 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("duck_hurt") : null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("duck_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("duck_hurt");

   }



   protected float getSoundVolume() {

      return 0.8F;

   }



   protected float getSoundPitch() {

      return 1.2F;

   }



   protected Item getDropItem() {

      if (this.world.rand.nextInt(2) == 1) {

         return Items.FEATHER;

      } else {

         return this.world.rand.nextInt(2) == 1 ? OreSpawn.RubberDuckyEgg : null;

      }

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

      if (var2 != null && var2.getCount() <= 0) {

         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

         var2 = null;

      }



       if (super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND)) {

         return true;

      } else if (var2 != null && var2.getItem() == Items.FISH && par1EntityPlayer.getDistanceSq(this) < 16.0) {

         if (!this.isTamed()) {

            if (!this.world.isRemote) {

               if (this.rand.nextInt(2) == 0) {

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

         && par1EntityPlayer.getDistanceSq(this) < 16.0

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

      } else if (this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 16.0) {

         if (!this.isSitting() && this.getKillCount() < 5) {

            this.setSitting(true);

         } else {

            this.setSitting(false);

         }



         return true;

      } else {

         return true;

      }

   }



   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {

      int found = 0;



      for (int i = -dy; i <= dy; i++) {

         for (int j = -dz; j <= dz; j++) {

            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();

            if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {

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

            if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {

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

            if (bidx == Blocks.WATER || bidx == Blocks.FLOWING_WATER) {

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

            if (bidx == Blocks.WATER || bidx == Blocks.FLOWING_WATER) {

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

            if (bidxx == Blocks.WATER || bidxx == Blocks.FLOWING_WATER) {

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

            if (bidxx == Blocks.WATER || bidxx == Blocks.FLOWING_WATER) {

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



   protected void updateAITasks() {

      if (!this.isDead) {

         super.updateAITasks();

         if (!this.isInWater() && this.world.rand.nextInt(50) == 0) {

            this.closest = 99999;

            this.tx = this.ty = this.tz = 0;



            for (int i = 1; i < 14; i++) {

               int j = i;

               if (i > 5) {

                  j = 5;

               }



               if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) {

                  break;

               }



               if (i >= 5) {

                  i++;

               }

            }



            if (this.closest < 99999) {

               this.getNavigator().tryMoveToXYZ(this.tx, this.ty - 1, this.tz, 1.33);

            }

         }



         if (this.killcount > 0 && this.world.rand.nextInt(200) == 1) {

            this.killcount--;

            this.setKillCount(this.killcount);

         }



         if (this.getHealth() < this.mygetMaxHealth() && this.world.rand.nextInt(300) == 1) {

            this.heal(1.0F);

         }



         if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 1) {

            EntityLivingBase e = this.findSomethingToAttack();

            if (e != null) {

               if (this.getDistanceSq(e) < 12.0) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {

                     this.attackEntityAsMob(e);

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.2);

               }

            } else {

               if (this.buddy != null && !this.buddy.isDead && this.world.rand.nextInt(15) == 1) {

                  this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0);

               }



               this.setAttacking(0);

            }

         }



         if (this.buddy != null && !this.buddy.isDead && this.world.rand.nextInt(20) == 1) {

            this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0);

         }

      }

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      float i = 1.0F;

      if (this.getKillCount() >= 5) {

         i = 2.0F;

      }



      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);

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

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof AttackSquid) {

         return true;

      } else if (par1EntityLiving instanceof EntitySquid) {

         return true;

      } else {

         if (par1EntityLiving instanceof RubberDucky && this.world.rand.nextInt(10) == 1) {

            this.buddy = par1EntityLiving;

         }



         if (this.getKillCount() >= 5 && par1EntityLiving instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)par1EntityLiving;

            return !p.capabilities.isCreativeMode;

         } else {

            return false;

         }

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8.0, 4.0, 8.0));

         Collections.sort(var5, this.TargetSorter);

         Iterator var2 = var5.iterator();

         Entity var3 = null;

         EntityLivingBase var4 = null;

         EntityLivingBase e = this.getAttackTarget();

         if (e != null && e.isEntityAlive()) {

            return e;

         } else {

            this.setAttackTarget(null);

            this.buddy = null;



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

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      par1NBTTagCompound.setInteger("Killcount", this.killcount);

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.killcount = par1NBTTagCompound.getInteger("Killcount");

      this.setKillCount(this.killcount);

   }



   public final int getAttacking() {

      return this.dataManager.get(DW_23);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_23,  (byte)par1);

   }



   public final int getKillCount() {

      return this.dataManager.get(DW_22);

   }



   public final void setKillCount(int par1) {

      this.dataManager.set(DW_22,  (byte)par1);

      this.killcount = par1;

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

                  if (s != null && s.equalsIgnoreCase("Rubber Ducky")) {

                     return true;

                  }

               }

            }

         }

      }



      return this.posY < 50.0 ? false : this.world.isDaytime();

   }



   protected boolean canDespawn() {

      if (this.isChild()) {

         this.enablePersistence();

         return false;

      } else if (this.isNoDespawnRequired()) {

         return false;

      } else {

         return this.isTamed() ? false : this.should_despawn;

      }

   }



   public EntityAgeable createChild(EntityAgeable entityageable) {

      return this.spawnBabyAnimal(entityageable);

   }



   public RubberDucky spawnBabyAnimal(EntityAgeable par1EntityAgeable) {

      return new RubberDucky(this.world);

   }



   public boolean isWheat(ItemStack par1ItemStack) {

      return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;

   }



   public boolean isBreedingItem(ItemStack par1ItemStack) {

      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}



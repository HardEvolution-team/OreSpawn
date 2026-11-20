package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.util.EnumParticleTypes;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;

import net.minecraft.util.math.BlockPos;



import java.util.UUID;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import net.minecraft.entity.ai.attributes.IAttributeInstance;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.util.EntityDamageSourceIndirect;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;



public class EnderReaper extends EntityMob {

    private static final DataParameter<Byte> DW_18 = EntityDataManager.<Byte>createKey(EnderReaper.class, DataSerializers.BYTE);



   private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");

   private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15F, 0)

      .setSaved(false);

   private int teleportDelay;

   private int stareTimer;

   private Entity lastEntityToAttack;



   public EnderReaper(World par1World) {

      super(par1World);

      this.setSize(0.7F, 2.9F);

      this.stepHeight = 1.0F;

   }



   @Override
   protected void initEntityAI() {
      // The original ran on the 1.7 "old AI" (findPlayerToAttack + attackEntity), which no longer exists.
      this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
      this.tasks.addTask(2, new net.minecraft.entity.ai.EntityAIAttackMelee(this, 1.0, false));
      this.tasks.addTask(7, new net.minecraft.entity.ai.EntityAIWanderAvoidWater(this, 1.0, 0.0F));
      this.tasks.addTask(8, new net.minecraft.entity.ai.EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, new net.minecraft.entity.ai.EntityAILookIdle(this));
      this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAIBase() {
         @Override
         public boolean shouldExecute() {
            if (getAttackTarget() == null) {
               Entity e = findPlayerToAttack();
               if (e instanceof net.minecraft.entity.EntityLivingBase) setAttackTarget((net.minecraft.entity.EntityLivingBase) e);
            }
            return false;
         }
      });
   }

   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(OreSpawn.EnderReaper_stats.health);

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.37);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.EnderReaper_stats.attack);

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_18, (byte)(new Byte((byte)0)));

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

   }



   protected Entity findPlayerToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 81.0);

         if (entityplayer != null) {

            if (this.shouldAttackPlayer(entityplayer)) {

               if (this.stareTimer == 0) {

                  this.world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mob.endermen.stare"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F);

               }



               if (this.stareTimer++ == 5) {

                  this.stareTimer = 0;

               }



               this.setScreaming(true);

               return entityplayer;

            }



            this.stareTimer = 0;

            this.setScreaming(false);

         }



         return null;

      }

   }



   private boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer) {

      ItemStack itemstack = par1EntityPlayer.inventory.armorInventory.get(3);

      if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) {

         return false;

      } else {

         Vec3d vec3 = par1EntityPlayer.getLook(1.0F).normalize();

         Vec3d vec31 = new Vec3d(

            this.posX - par1EntityPlayer.posX,

            this.getEntityBoundingBox().minY + this.height / 2.0F - (par1EntityPlayer.posY + par1EntityPlayer.getEyeHeight()),

            this.posZ - par1EntityPlayer.posZ

         );

         double d0 = vec31.length();

         vec31 = vec31.normalize();

         double d1 = vec3.dotProduct(vec31);

         return d1 > 1.0 - 0.025 / d0 ? par1EntityPlayer.canEntityBeSeen(this) : false;

      }

   }



   public void onLivingUpdate() {

      if (this.isWet()) {

         this.attackEntityFrom(DamageSource.DROWN, 1.0F);

      }



      if (this.lastEntityToAttack != this.getAttackTarget()) {

         IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

         attributeinstance.removeModifier(attackingSpeedBoostModifier);

         if (this.getAttackTarget() != null) {

            attributeinstance.applyModifier(attackingSpeedBoostModifier);

         }

      }



      this.lastEntityToAttack = this.getAttackTarget();



      for (int i = 0; i < 2; i++) {

         this.world

            .spawnParticle(EnumParticleTypes.PORTAL,

               this.posX + (this.rand.nextDouble() - 0.5) * this.width,

               this.posY + this.rand.nextDouble() * this.height - 0.25,

               this.posZ + (this.rand.nextDouble() - 0.5) * this.width,

               (this.rand.nextDouble() - 0.5) * 2.0,

               -this.rand.nextDouble(),

               (this.rand.nextDouble() - 0.5) * 2.0

            );

      }



      if (this.world.isDaytime() && !this.world.isRemote) {

         float f = this.getBrightness();

         if (f > 0.5F && this.world.canSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ))) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) { this.setAttackTarget(null); this.setScreaming(false); this.teleportRandomly(); }

      }



      if (this.isWet() || this.isBurning()) {

         this.setScreaming(false);

         this.teleportRandomly();

      }



      this.isJumping = false;

      if (this.getAttackTarget() != null) {

         this.faceEntity(this.getAttackTarget(), 100.0F, 100.0F);

      }



      if (!this.world.isRemote && this.isEntityAlive()) {

         if (this.getAttackTarget() != null) {

            if (this.getAttackTarget() instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.getAttackTarget())) {

               if (this.getAttackTarget().getDistanceSq(this) < 16.0) {

                  this.teleportRandomly();

               }



               this.teleportDelay = 0;

            } else if (this.getAttackTarget().getDistanceSq(this) > 256.0 && this.teleportDelay++ >= 30 && this.teleportToEntity(this.getAttackTarget())) {

               this.teleportDelay = 0;

            }

         } else {

            this.setScreaming(false);

            this.teleportDelay = 0;

         }

      }



      super.onLivingUpdate();

   }



   protected boolean teleportRandomly() {

      double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;

      double d1 = this.posY + (this.rand.nextInt(64) - 32);

      double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;

      return this.teleportTo(d0, d1, d2);

   }



   protected boolean teleportToEntity(Entity par1Entity) {

      Vec3d vec3 = new Vec3d(

         this.posX - par1Entity.posX,

         this.getEntityBoundingBox().minY + this.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(),

         this.posZ - par1Entity.posZ

      );

      vec3 = vec3.normalize();

      double d0 = 16.0;

      double d1 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.x * d0;

      double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.y * d0;

      double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3.z * d0;

      return this.teleportTo(d1, d2, d3);

   }



   protected boolean teleportTo(double par1, double par3, double par5) {

      double d3 = this.posX;

      double d4 = this.posY;

      double d5 = this.posZ;

      this.posX = par1;

      this.posY = par3;

      this.posZ = par5;

      boolean flag = false;

      int i = MathHelper.floor(this.posX);

      int j = MathHelper.floor(this.posY);

      int k = MathHelper.floor(this.posZ);

      if (this.world.isBlockLoaded(new BlockPos(i, j, k))) {

         boolean flag1 = false;



         while (!flag1 && j > 0) {

            Block l = this.world.getBlockState(new BlockPos(i, j - 1, k)).getBlock();

            if (l != Blocks.AIR && l.getDefaultState().getMaterial().blocksMovement()) {

               flag1 = true;

            } else {

               this.posY--;

               j--;

            }

         }



         if (flag1) {

            this.setPosition(this.posX, this.posY, this.posZ);

            if (this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox())) {

               flag = true;

            }

         }

      }



      if (!flag) {

         this.setPosition(d3, d4, d5);

         return false;

      } else {

         short short1 = 128;



         for (int lx = 0; lx < short1; lx++) {

            double d6 = lx / (short1 - 1.0);

            float f = (this.rand.nextFloat() - 0.5F) * 0.2F;

            float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;

            float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;

            double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;

            double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * this.height;

            double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;

            this.world.spawnParticle(EnumParticleTypes.PORTAL, d7, d8, d9, f, f1, f2);

         }



         this.world.playSound(null, d3, d4, d5, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.endermen.teleport"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F);

         this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.endermen.teleport"), 1.0F, 1.0F);

         return true;

      }

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return this.isScreaming() ? SoundEvents.ENTITY_ENDERMEN_SCREAM : SoundEvents.ENTITY_ENDERMEN_AMBIENT;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("mob.endermen.hit");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("mob.endermen.death");

   }



   protected Item getDropItem() {

      return Items.ENDER_EYE;

   }



   public int getTotalArmorValue() {

      return OreSpawn.EnderReaper_stats.defense;

   }



   protected void dropFewItems(boolean par1, int par2) {

      Item j = this.getDropItem();

      if (j != null) {

         int k = this.rand.nextInt(2 + par2);



         for (int l = 0; l < k; l++) {

            this.dropItem(j, 1);

         }

      }

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      if (this.getIsInvulnerable()) {

         return false;

      } else {

         this.setScreaming(true);

         if (par1DamageSource instanceof EntityDamageSourceIndirect) {

            for (int i = 0; i < 16; i++) {

               if (this.teleportRandomly()) {

                  return true;

               }

            }



            return super.attackEntityFrom(par1DamageSource, par2);

         } else {

            return super.attackEntityFrom(par1DamageSource, par2);

         }

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

                  if (s != null && s.equalsIgnoreCase("Ender Reaper")) {

                     return true;

                  }

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else if (this.posY < 30.0) {

         return false;

      } else {

         EnderReaper target = null;

         target = (EnderReaper)this.world.findNearestEntityWithinAABB(EnderReaper.class, this.getEntityBoundingBox().grow(16.0, 8.0, 16.0), this);

         return target == null;

      }

   }



   public boolean isScreaming() {

      return this.dataManager.get(DW_18) > 0;

   }



   public void setScreaming(boolean par1) {

      this.dataManager.set(DW_18,  (byte)(par1 ? 1 : 0));

   }

}



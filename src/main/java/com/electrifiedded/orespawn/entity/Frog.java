package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Frog extends EntityAnimal {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Frog.class, DataSerializers.BYTE);

   private GenericTargetSorter TargetSorter = null;
   public double moveSpeed = 0.1F;
   private int singing = 0;
   private int jumpcount = 0;

   public Frog(World par1World) {
      super(par1World);
      this.setSize(0.75F, 0.75F);
      this.experienceValue = 5;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
      this.tasks.addTask(2, new MyEntityAIWander(this, 1.0F));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, (byte)0);
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public int getSinging() {
      return this.dataManager.get(DW_20);
   }

   public void setSinging(int par1) {
      this.dataManager.set(DW_20,  (byte)par1);
   }

   private void jumpAround() {
      this.motionY = this.motionY + (0.75F + Math.abs(this.world.rand.nextFloat() * 0.55F));
      this.posY += 0.35F;
      float f = 0.7F + Math.abs(this.world.rand.nextFloat() * 0.75F);
      float d = (float)Math.toRadians(this.rotationYaw);
      this.motionX = this.motionX - f * Math.sin(d);
      this.motionZ = this.motionZ + f * Math.cos(d);
      this.isAirBorne = true;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.singing != 0) {
            this.singing--;
            if (this.singing <= 0) {
               this.setSinging(0);
            }
         }

         if (this.jumpcount > 0) {
            this.jumpcount--;
         }

         if (this.jumpcount == 0 && this.world.rand.nextInt(70) == 1) {
            this.jumpAround();
            this.jumpcount = 50;
         }
      }
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      if (par1EntityPlayer != null && par1EntityPlayer.isSneaking() && par1EntityPlayer.inventory.getCurrentItem() == null) {
         World world = par1EntityPlayer.world;
         this.setDead();
         par1EntityPlayer.world.playSound(null, par1EntityPlayer.posX, par1EntityPlayer.posY, par1EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, world.rand.nextFloat() * 0.2F + 0.9F);
         if (!world.isRemote) {
            if (world.rand.nextInt(2) == 0) {
               Boyfriend ent = null;
               ent = (Boyfriend)spawnCreature(world, "Boyfriend", this.posX, this.posY + 0.01, this.posZ);
               if (ent != null) {
                  ent.setPrince(1 + world.rand.nextInt(2));
               }
            } else {
               Girlfriend ent = null;
               ent = (Girlfriend)spawnCreature(world, "Girlfriend", this.posX, this.posY + 0.01, this.posZ);
               if (ent != null) {
                  ent.setPrincess(1 + world.rand.nextInt(2));
               }
            }
         } else {
            for (int var3 = 0; var3 < 16; var3++) {
               world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                  (float)this.posX + world.rand.nextFloat() - world.rand.nextFloat(),
                  (float)this.posY + world.rand.nextFloat(),
                  (float)this.posZ + world.rand.nextFloat() - world.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
               world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                  (float)this.posX + world.rand.nextFloat() - world.rand.nextFloat(),
                  (float)this.posY + world.rand.nextFloat(),
                  (float)this.posZ + world.rand.nextFloat() - world.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
               world.spawnParticle(EnumParticleTypes.REDSTONE,
                  (float)this.posX + world.rand.nextFloat() - world.rand.nextFloat(),
                  (float)this.posY + world.rand.nextFloat(),
                  (float)this.posZ + world.rand.nextFloat() - world.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }

      return false;
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public int mygetMaxHealth() {
      return 8;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      if (!this.world.isRemote) {
         if (this.world.rand.nextInt(2) == 0) {
            return null;
         }

         this.singing = 35;
         this.setSinging(this.singing);
      }

      return OreSpawnSoundEvents.get("frog");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("scorpion_hit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("big_splat");
   }

   protected float getSoundVolume() {
      return 0.7F;
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
   }

   private void dropItemRand(Item index, int par1) {
      EntityItem var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         this.posY + 1.0,
         this.posZ + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         new ItemStack(index, par1, 0)
      );
      this.world.spawnEntity(var3);
   }

   protected void dropFewItems(boolean par1, int par2) {
      for (int i = 0; i < 4; i++) {
         this.dropItemRand(Items.SLIME_BALL, 1);
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
      if (par1Entity.isDead) {
         this.heal(1.0F);
      }

      return var4;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      ret = super.attackEntityFrom(par1DamageSource, par2);
      if (!this.world.isRemote && this.jumpcount <= 0) {
         this.jumpAround();
         this.jumpcount = 25;
      }

      return ret;
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.25, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Frog.class, this.getEntityBoundingBox().grow(20.0, 8.0, 20.0));
      return var5.size();
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else if (!this.world.isDaytime()) {
         return false;
      } else {
         return this.world.provider.getDimension() == OreSpawn.DimensionID5 && this.world.rand.nextInt(20) != 1
            ? false
            : this.findBuddies() <= 5;
      }
   }

   protected void updateAITasks() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 50;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            EntityLivingBase e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
               this.getNavigator().tryMoveToEntityLiving(e, 1.25);
               if (this.getDistanceSq(e) < 6.0) {
                  this.attackEntityAsMob(e);
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
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityAnt) {
         return true;
      } else if (par1EntityLiving instanceof EntityButterfly) {
         return true;
      } else if (par1EntityLiving instanceof Cricket) {
         return true;
      } else if (par1EntityLiving instanceof EntityMosquito) {
         return true;
      } else {
         return par1EntityLiving instanceof Firefly ? true : par1EntityLiving instanceof WormSmall;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8.0, 3.0, 8.0));
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
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

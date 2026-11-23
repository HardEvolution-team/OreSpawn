package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Godzilla extends EntityMob {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Godzilla.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(Godzilla.class, DataSerializers.VARINT);

   private GenericTargetSorter TargetSorter = null;
   private float moveSpeed = 0.75F;
   private int hurt_timer = 0;
   private int jumped = 0;
   private int jump_timer = 0;
   private int ticker = 0;
   private RenderInfo renderdata = new RenderInfo();
   private int stream_count = 8;
   private MyEntityAIWanderALot wander = null;
   private int head_found = 0;
   private int large_unknown_detected = 0;

   public Godzilla(World par1World) {
      super(par1World);
      this.ignoreFrustumCheck = true;
      if (OreSpawn.PlayNicely == 0) {
         this.setSize(9.9F, 25.0F);
      } else {
         this.setSize(2.475F, 6.25F);
      }

      this.experienceValue = 10000;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0, false));
      this.wander = new MyEntityAIWanderALot(this, 15, 1.0);
      this.tasks.addTask(2, this.wander);
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 50.0F));
      this.tasks.addTask(4, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.TargetSorter = new GenericTargetSorter(this);
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
   }

   @Override
   public boolean isInRangeToRenderDist(double distance) {
      return distance < 1048576.0D;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Godzilla_stats.attack);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, (byte)0);
      this.dataManager.register(DW_21, OreSpawn.PlayNicely);
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

   public int getPlayNicely() {
      return this.dataManager.get(DW_21);
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

   protected boolean canDespawn() {
      return this.isNoDespawnRequired() ? false : OreSpawn.PlayNicely != 0;
   }

   public int mygetMaxHealth() {
      return OreSpawn.Godzilla_stats.health;
   }

   public int getTotalArmorValue() {
      return this.large_unknown_detected != 0 ? 25 : OreSpawn.Godzilla_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      double xzoff = 0.0;
      double myoff = 20.0;
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
      if (this.isAirBorne) {
         this.getNavigator().setPath(null, 0.0);
      }
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.world.rand.nextInt(5) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("godzilla_living") : null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("alo_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("godzilla_death");
   }

   protected float getSoundVolume() {
      return 1.65F;
   }

   protected float getSoundPitch() {
      return 1.1F;
   }

   protected Item getDropItem() {
      return null;
   }

   protected void jump() {
      while (this.rotationYaw < 0.0F) {
         this.rotationYaw += 360.0F;
      }

      while (this.rotationYawHead < 0.0F) {
         this.rotationYawHead += 360.0F;
      }

      while (this.rotationYaw > 360.0F) {
         this.rotationYaw -= 360.0F;
      }

      while (this.rotationYawHead > 360.0F) {
         this.rotationYawHead -= 360.0F;
      }

      this.motionY += 0.45F;
      this.posY += 0.5;
      float f = 0.2F + Math.abs(this.world.rand.nextFloat() * 0.45F);
      this.motionX = this.motionX + f * Math.cos(Math.toRadians(this.rotationYawHead + 90.0F));
      this.motionZ = this.motionZ + f * Math.sin(Math.toRadians(this.rotationYawHead + 90.0F));
      this.isAirBorne = true;
      this.getNavigator().setPath(null, 0.0);
   }

   protected void jumpAtEntity(EntityLivingBase e) {
      this.motionY++;
      this.posY++;
      double d1 = e.posX - this.posX;
      double d2 = e.posZ - this.posZ;
      float d = (float)Math.atan2(d2, d1);
      float f2 = (float)(d * 180.0 / Math.PI) - 90.0F;
      this.rotationYaw = f2;
      d1 = Math.sqrt(d1 * d1 + d2 * d2);
      this.motionX = this.motionX + d1 * 0.05 * Math.cos(d);
      this.motionZ = this.motionZ + d1 * 0.05 * Math.sin(d);
      this.isAirBorne = true;
      this.getNavigator().setPath(null, 0.0);
   }

   private double getHorizontalDistanceSqToEntity(Entity e) {
      double d1 = e.posZ - this.posZ;
      double d2 = e.posX - this.posX;
      return d1 * d1 + d2 * d2;
   }

   public double MygetDistanceSqToEntity(Entity par1Entity) {
      double d0 = this.posX - par1Entity.posX;
      double d1 = par1Entity.posY - this.posY;
      double d2 = this.posZ - par1Entity.posZ;
      if (d1 > 0.0 && d1 < 20.0) {
         d1 = 0.0;
      }

      if (d1 > 20.0) {
         d1 -= 10.0;
      }

      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   protected void updateAITasks() {
      EntityLivingBase e = null;
      int xzrange = 9;
      if (!this.isDead) {
         if (!this.world.isRemote) {
            this.dataManager.set(DW_21,  OreSpawn.PlayNicely);
            super.updateAITasks();
            this.ticker++;
            if (this.ticker > 30000) {
               this.ticker = 0;
            }

            if (this.ticker % 100 == 0) {
               this.stream_count = 8;
            }

            if (this.hurt_timer > 0) {
               this.hurt_timer--;
            }

            if (this.jump_timer > 0) {
               this.jump_timer--;
            }

            OreSpawn.godzilla_has_spawned = 1;
            if (this.world.rand.nextInt(200) == 0) {
               this.setAttackTarget(null);
            }

            if (OreSpawn.PlayNicely == 0) {
               if (this.motionY < -0.95) {
                  this.jumped = 1;
               }

               if (this.motionY < -1.5) {
                  this.jumped = 2;
               }

               if (this.jumped != 0 && this.motionY > -0.1) {
                  double df = 1.0;
                  if (this.jumped == 2) {
                     df = 1.5;
                  }

                  this.doJumpDamage(this.posX, this.posY, this.posZ, 10.0, OreSpawn.Godzilla_stats.attack * df, 0);
                  this.doJumpDamage(this.posX, this.posY, this.posZ, 15.0, OreSpawn.Godzilla_stats.attack / 2 * df, 0);
                  this.doJumpDamage(this.posX, this.posY, this.posZ, 25.0, OreSpawn.Godzilla_stats.attack / 4 * df, 0);
                  this.jumped = 0;
               }
            }

            int var23 = 12;
            if (this.getAttacking() != 0) {
               var23 = 16;
            }

            int k = -3 + this.ticker % 30;
            if (OreSpawn.PlayNicely == 0) {
               for (int i = -var23; i <= var23; i++) {
                  for (int j = -var23; j <= var23; j++) {
                     Block bid = this.world.getBlockState(new BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j)).getBlock();
                     if (this.isCrushable(bid)) {
                        this.world
                           .setBlockState(new BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j), Blocks.AIR.getDefaultState());
                        if (this.world.rand.nextInt(15) == 1) {
                           this.dropItemRand(Item.getItemFromBlock(bid), 1);
                        }
                     } else {
                        if (bid == Blocks.GRASS && this.world.getGameRules().getBoolean("mobGriefing")) {
                           this.world
                              .setBlockState(new BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j), Blocks.DIRT.getDefaultState());
                        }

                        if (bid == Blocks.FARMLAND && this.world.getGameRules().getBoolean("mobGriefing")) {
                           this.world
                              .setBlockState(new BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j), Blocks.DIRT.getDefaultState());
                        }
                     }
                  }
               }
            }

            double dx = this.posX + 16.0 * Math.sin(Math.toRadians(this.rotationYawHead));
            double dz = this.posZ - 16.0 * Math.cos(Math.toRadians(this.rotationYawHead));
            k = -3 + this.ticker % 12;
            if (OreSpawn.PlayNicely == 0) {
               for (int i = -var23; i <= var23; i++) {
                  for (int jx = -var23; jx <= var23; jx++) {
                     Block bid = this.world.getBlockState(new BlockPos((int)dx + i, (int)this.posY + k, (int)dz + jx)).getBlock();
                     if (this.isCrushable(bid)) {
                        this.world.setBlockState(new BlockPos((int)dx + i, (int)this.posY + k, (int)dz + jx), Blocks.AIR.getDefaultState());
                        if (this.world.rand.nextInt(15) == 1) {
                           this.dropItemRandAt(Item.getItemFromBlock(bid), 1, dx, dz);
                        }
                     } else {
                        if (bid == Blocks.GRASS && this.world.getGameRules().getBoolean("mobGriefing")) {
                           this.world.setBlockState(new BlockPos((int)dx + i, (int)this.posY + k, (int)dz + jx), Blocks.DIRT.getDefaultState());
                        }

                        if (bid == Blocks.FARMLAND && this.world.getGameRules().getBoolean("mobGriefing")) {
                           this.world.setBlockState(new BlockPos((int)dx + i, (int)this.posY + k, (int)dz + jx), Blocks.DIRT.getDefaultState());
                        }
                     }
                  }
               }
            }

            if (OreSpawn.PlayNicely == 0 && k == 0) {
               this.doJumpDamage(dx, this.posY, dz, 15.0, OreSpawn.Godzilla_stats.attack / 2, 1);
            }

            if (this.world.rand.nextInt(5 - this.large_unknown_detected) == 1) {
               e = this.getAttackTarget();
               if (OreSpawn.PlayNicely != 0) {
                  e = null;
               }

               if (e != null) {
                  if (!e.isEntityAlive()) {
                     this.setAttackTarget(null);
                     e = null;
                  } else if (e instanceof Godzilla || e instanceof GodzillaHead) {
                     this.setAttackTarget(null);
                     e = null;
                  }
               }

               if (e == null) {
                  e = this.findSomethingToAttack();
                  if (this.head_found == 0) {
                     EntityLiving newent = (EntityLiving)spawnCreature(
                        this.world, "MobzillaHead", this.posX, this.posY + 20.0, this.posZ
                     );
                  }
               }

               if (e != null) {
                  this.wander.setBusy(1);
                  this.faceEntity(e, 10.0F, 10.0F);
                  if (this.world.rand.nextInt(65) == 1 && this.MygetDistanceSqToEntity(e) > 300.0) {
                     this.doLightningAttack(e);
                  } else if (this.world.rand.nextInt(20 - this.large_unknown_detected * 5) == 1 && this.jump_timer == 0) {
                     this.jumpAtEntity(e);
                     this.jump_timer = 30;
                  } else if (this.MygetDistanceSqToEntity(e) < 300.0F + e.width / 2.0F * (e.width / 2.0F)) {
                     this.setAttacking(1);
                     this.getNavigator().tryMoveToEntityLiving(e, 1.0);
                     if (this.world.rand.nextInt(4 - this.large_unknown_detected) == 0
                        || this.world.rand.nextInt(3 - this.large_unknown_detected) == 1) {
                        this.attackEntityAsMob(e);
                     }
                  } else {
                     this.getNavigator().tryMoveToEntityLiving(e, 1.0);
                     if (this.getHorizontalDistanceSqToEntity(e) > 625.0) {
                        if (this.stream_count > 0) {
                           this.setAttacking(1);
                           double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                           double rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                           double pi = 3.1415926545;
                           double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                           if (rdd > pi) {
                              rdd -= pi * 2.0;
                           }

                           rdd = Math.abs(rdd);
                           if (rdd < 0.5) {
                              this.firecanon(e);
                           }
                        } else {
                           this.setAttacking(0);
                        }
                     } else {
                        this.setAttacking(0);
                     }
                  }
               } else {
                  this.setAttacking(0);
                  this.wander.setBusy(0);
                  this.stream_count = 8;
               }
            }

            if (this.world.rand.nextInt(35) == 1 && this.getHealth() < this.mygetMaxHealth()) {
               this.heal(5.0F);
            }
         }
      }
   }

   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
      }

      return var8;
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
      } else if (par1EntityLiving instanceof Godzilla) {
         return false;
      } else if (par1EntityLiving instanceof GodzillaHead) {
         return false;
      } else if (par1EntityLiving instanceof EntityCreeper) {
         return false;
      } else if (par1EntityLiving instanceof EntityZombie) {
         return false;
      } else if (par1EntityLiving instanceof EntitySpider) {
         return false;
      } else if (par1EntityLiving instanceof EntitySkeleton) {
         return false;
      } else if (par1EntityLiving instanceof Ghost) {
         return false;
      } else if (par1EntityLiving instanceof GhostSkelly) {
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

   private boolean isVillagerTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else {
         return !this.getEntitySenses().canSee(par1EntityLiving) ? false : par1EntityLiving instanceof EntityVillager;
      }
   }

   private EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock) {
      AxisAlignedBB bb = new AxisAlignedBB(X - dist, Y - 10.0, Z - dist, X + dist, Y + 10.0, Z + dist);
      List var5x = this.world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
      Collections.sort(var5x, this.TargetSorter);
      Iterator var2 = var5x.iterator();
      Entity var3x = null;
      EntityLivingBase var4 = null;

      while (var2.hasNext()) {
         var3x = (Entity)var2.next();
         var4 = (EntityLivingBase)var3x;
         if (var4 != null
            && var4 != this
            && var4.isEntityAlive()
            && !(var4 instanceof Godzilla)
            && !(var4 instanceof GodzillaHead)
            && !(var4 instanceof Ghost)
            && !(var4 instanceof GhostSkelly)) {
            DamageSource var21 = null;
            var21 = DamageSource.causeExplosionDamage((net.minecraft.entity.EntityLivingBase)null);
            var21.setExplosion();
            var4.attackEntityFrom(var21, (float)damage / 2.0F);
            var4.attackEntityFrom(DamageSource.FALL, (float)damage / 2.0F);
            this.world.playSound(null, var4.posX, var4.posY, var4.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.85F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
            if (knock != 0) {
               double ks = 3.5;
               double inair = 0.75;
               float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
               var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
         }
      }

      return null;
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         this.head_found = 1;
         return null;
      } else {
         List var5 = null;
         Iterator var2 = null;
         Entity var3 = null;
         EntityLivingBase var4 = null;
         EntityLivingBase ret = null;
         int vf = 0;
         var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(64.0, 40.0, 64.0));
         if (var5 == null) {
            return null;
         } else {
            Collections.sort(var5, this.TargetSorter);
            var2 = var5.iterator();
            this.head_found = 0;

            while (var2.hasNext()) {
               var3 = (Entity)var2.next();
               var4 = (EntityLivingBase)var3;
               if (var4 instanceof GodzillaHead) {
                  this.head_found = 1;
               }

               if (vf == 0 && this.isVillagerTarget(var4, false)) {
                  ret = var4;
                  vf = 1;
               }

               if (ret == null && vf == 0 && this.isSuitableTarget(var4, false)) {
                  ret = var4;
               }
            }

            return ret;
         }
      }
   }

   public boolean getCanSpawnHere() {
      if (!this.isValidLightLevel()) {
         return false;
      } else if (this.world.isDaytime()) {
         return false;
      } else if (this.posY < 50.0) {
         return false;
      } else if (OreSpawn.godzilla_has_spawned != 0) {
         return false;
      } else if (this.world.rand.nextInt(40) != 1) {
         return false;
      } else {
         for (int k = -8; k <= 8; k++) {
            for (int j = -8; j <= 8; j++) {
               for (int i = 5; i < 15; i++) {
                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                  if (bid != Blocks.AIR) {
                     return false;
                  }
               }
            }
         }

         Godzilla target = null;
         target = (Godzilla)this.world.findNearestEntityWithinAABB(Godzilla.class, this.getEntityBoundingBox().grow(64.0, 16.0, 64.0), this);
         if (target != null) {
            return false;
         } else {
            if (!this.world.isRemote) {
               OreSpawn.godzilla_has_spawned = 1;
            }

            return true;
         }
      }
   }

   public final int getAttacking() {
      return this.dataManager.get(DW_20);
   }

   public final void setAttacking(int par1) {
      this.dataManager.set(DW_20,  (byte)par1);
   }

   private ItemStack dropItemRand(Item index, int par1) {
      EntityItem var3 = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(10) - OreSpawn.OreSpawnRand.nextInt(10),
         this.posY + 4.0 + this.world.rand.nextInt(10),
         this.posZ + OreSpawn.OreSpawnRand.nextInt(10) - OreSpawn.OreSpawnRand.nextInt(10),
         is
      );
      if (var3 != null) {
         this.world.spawnEntity(var3);
      }

      return is;
   }

   private ItemStack dropItemRandAt(Item index, int par1, double dx, double dz) {
      EntityItem var3x = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3x = new EntityItem(
         this.world,
         dx + OreSpawn.OreSpawnRand.nextInt(10) - OreSpawn.OreSpawnRand.nextInt(10),
         this.posY + 4.0 + this.world.rand.nextInt(6),
         dz + OreSpawn.OreSpawnRand.nextInt(10) - OreSpawn.OreSpawnRand.nextInt(10),
         is
      );
      if (var3x != null) {
         this.world.spawnEntity(var3x);
      }

      return is;
   }

   private boolean isCrushable(Block bid) {
      if (bid == null) {
         return false;
      } else if (!this.world.getGameRules().getBoolean("mobGriefing")) {
         return false;
      } else if (bid == Blocks.GRASS) {
         return false;
      } else if (bid == Blocks.DIRT) {
         return false;
      } else if (bid == Blocks.STONE) {
         return false;
      } else if (bid == Blocks.FARMLAND) {
         return false;
      } else if (bid == Blocks.WATER) {
         return false;
      } else if (bid == Blocks.FLOWING_WATER) {
         return false;
      } else if (bid == Blocks.LAVA) {
         return false;
      } else if (bid == Blocks.FLOWING_LAVA) {
         return false;
      } else if (bid == Blocks.BEDROCK) {
         return false;
      } else if (bid == Blocks.OBSIDIAN) {
         return false;
      } else if (bid == Blocks.SAND) {
         return false;
      } else if (bid == Blocks.GRAVEL) {
         return false;
      } else if (bid == Blocks.IRON_BLOCK) {
         return false;
      } else if (bid == Blocks.DIAMOND_BLOCK) {
         return false;
      } else if (bid == Blocks.EMERALD_BLOCK) {
         return false;
      } else if (bid == Blocks.GOLD_BLOCK) {
         return false;
      } else if (bid == Blocks.NETHERRACK) {
         return false;
      } else if (bid == Blocks.END_STONE) {
         return false;
      } else if (bid == OreSpawn.MyBlockAmethystBlock) {
         return false;
      } else if (bid == OreSpawn.MyBlockRubyBlock) {
         return false;
      } else if (bid == OreSpawn.MyBlockUraniumBlock) {
         return false;
      } else if (bid == OreSpawn.MyBlockTitaniumBlock) {
         return false;
      } else {
         return bid == OreSpawn.CrystalStone ? false : bid != OreSpawn.CrystalGrass;
      }
   }

   private void firecanon(EntityLivingBase e) {
      double yoff = 19.0;
      double xzoff = 22.0;
      BetterFireball bf = null;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      if (this.stream_count > 0) {
         bf = new BetterFireball(
            this.world, this, e.posX - cx, e.posY + e.height / 2.0F - (this.posY + yoff), e.posZ - cz
         );
         bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
         bf.setPosition(cx, this.posY + yoff, cz);
         bf.setBig();
         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.fuse"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
         this.world.spawnEntity(bf);

         for (int i = 0; i < 5; i++) {
            float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            bf = new BetterFireball(
               this.world,
               this,
               e.posX - cx + r1,
               e.posY + e.height / 2.0F - (this.posY + yoff) + r2,
               e.posZ - cz + r3
            );
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
            bf.setPosition(cx, this.posY + yoff, cz);
            if (this.world.rand.nextInt(2) == 1) {
               bf.setSmall();
            }

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(bf);
         }

         this.stream_count--;
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
         float s = par1Entity.height * par1Entity.width;
         if (s > 30.0F
            && !MyUtils.isRoyalty(par1Entity)
            && !(par1Entity instanceof Godzilla)
            && !(par1Entity instanceof GodzillaHead)
            && !(par1Entity instanceof PitchBlack)
            && !(par1Entity instanceof Kraken)) {
            EntityLivingBase e = (EntityLivingBase)par1Entity;
            e.setHealth(e.getHealth() / 2.0F);
            e.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawn.Godzilla_stats.attack * 10.0F);
            this.large_unknown_detected = 1;
         }
      }

      if (par1Entity != null && par1Entity instanceof EntityDragon) {
         EntityDragon dr = (EntityDragon)par1Entity;
         DamageSource var21 = null;
         var21 = DamageSource.causeExplosionDamage((net.minecraft.entity.EntityLivingBase)null);
         var21.setExplosion();
         if (this.world.rand.nextInt(6) == 1) {
            dr.attackEntityFromPart(dr.dragonPartHead, var21, OreSpawn.Godzilla_stats.attack / 2.0F);
         } else {
            dr.attackEntityFromPart(dr.dragonPartBody, var21, OreSpawn.Godzilla_stats.attack / 2.0F);
         }
      }

      if (!super.attackEntityAsMob(par1Entity)) {
         return false;
      } else {
         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            double ks = 3.2;
            double inair = 0.3;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
               inair *= 2.0;
            }

            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
         }

         return true;
      }
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      float dm = par2;
      float s = 0.0F;
      if (this.hurt_timer > 0) {
         return false;
      } else {
         if (par2 > 750.0F) {
            dm = 750.0F;
         }

         Entity e = par1DamageSource.getTrueSource();
         if (e != null && e instanceof EntityLivingBase) {
            EntityLivingBase enl = (EntityLivingBase)e;
            s = enl.height * enl.width;
            if (s > 30.0F
               && !MyUtils.isRoyalty(enl)
               && !(enl instanceof Godzilla)
               && !(enl instanceof GodzillaHead)
               && !(enl instanceof PitchBlack)
               && !(enl instanceof Kraken)) {
               dm /= 10.0F;
               this.hurt_timer = 50;
               this.large_unknown_detected = 1;
            }
         }

         if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, dm);
            this.hurt_timer = 20;
            e = par1DamageSource.getTrueSource();
            if (e != null && e instanceof EntityLivingBase && !(e instanceof GodzillaHead) && !(e instanceof Godzilla)) {
               this.setAttackTarget((EntityLivingBase)e);
               this.setAttackTarget((EntityLivingBase)e);
               this.getNavigator().tryMoveToEntityLiving((EntityLivingBase)e, 1.2);
            }
         }

         return ret;
      }
   }

   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
   }

   private void doLightningAttack(EntityLivingBase e) {
      if (e != null) {
         float var2 = 100.0F;
         e.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
         e.setFire(5);

         for (int var3 = 0; var3 < 20; var3++) {
            this.world
               .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                  e.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                  e.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                  e.posZ + this.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
            this.world
               .spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                  e.posX + this.rand.nextFloat() - this.rand.nextFloat(),
                  e.posY + this.rand.nextFloat() - this.rand.nextFloat(),
                  e.posZ + this.rand.nextFloat() - this.rand.nextFloat(),
                  0.0,
                  0.0,
                  0.0
               );
            this.world
               .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                  e.posX,
                  e.posY,
                  e.posZ,
                  this.world.rand.nextGaussian(),
                  this.world.rand.nextGaussian(),
                  this.world.rand.nextGaussian()
               );
         }

         this.world.playSound(null, e.posX, e.posY, e.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
         if (!this.world.isRemote) {
            this.world
               .createExplosion(this, e.posX, e.posY, e.posZ, 3.0F, this.world.getGameRules().getBoolean("mobGriefing"));
         }

         this.world.addWeatherEffect(new EntityLightningBolt(this.world, e.posX, e.posY + 1.0, e.posZ, false));
         this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX, this.posY + 15.0, this.posZ, false));
      }
   }

   protected void dropFewItems(boolean par1, int par2) {
      ItemStack is = null;
      this.dropItemRand(Items.ITEM_FRAME, 1);
      int var5 = 50 + this.world.rand.nextInt(30);

      for (int var4 = 0; var4 < var5; var4++) {
         this.dropItemRand(OreSpawn.MyGodzillaScale, 1);
      }

      var5 = 100 + this.world.rand.nextInt(160);

      for (int var9 = 0; var9 < var5; var9++) {
         this.dropItemRand(Items.BEEF, 1);
      }

      var5 = 50 + this.world.rand.nextInt(60);

      for (int var10 = 0; var10 < var5; var10++) {
         this.dropItemRand(Items.BONE, 1);
      }

      int i = 25 + this.world.rand.nextInt(15);

      for (int var11 = 0; var11 < i; var11++) {
         int var3 = this.world.rand.nextInt(80);
         switch (var3) {
            case 0:
               is = this.dropItemRand(OreSpawn.MyUltimateSword, 1);
               break;
            case 1:
               is = this.dropItemRand(Items.DIAMOND, 1);
               break;
            case 2:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 1);
               break;
            case 3:
               is = this.dropItemRand(Items.DIAMOND_SWORD, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 4:
               is = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 5:
               is = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 6:
               is = this.dropItemRand(Items.DIAMOND_AXE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 7:
               is = this.dropItemRand(Items.DIAMOND_HOE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 8:
               is = this.dropItemRand(Items.DIAMOND_HELMET, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 9:
               is = this.dropItemRand(Items.DIAMOND_CHESTPLATE, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 10:
               is = this.dropItemRand(Items.DIAMOND_LEGGINGS, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 11:
               is = this.dropItemRand(Items.DIAMOND_BOOTS, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 12:
               is = this.dropItemRand(OreSpawn.MyUltimateBow, 1);
               break;
            case 13:
               is = this.dropItemRand(OreSpawn.MyUltimateAxe, 1);
               break;
            case 14:
               is = this.dropItemRand(Items.IRON_INGOT, 1);
               break;
            case 15:
               is = this.dropItemRand(OreSpawn.MyUltimatePickaxe, 1);
               break;
            case 16:
               is = this.dropItemRand(Items.IRON_SWORD, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 17:
               is = this.dropItemRand(Items.IRON_SHOVEL, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 18:
               is = this.dropItemRand(Items.IRON_PICKAXE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 19:
               is = this.dropItemRand(Items.IRON_AXE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 20:
               is = this.dropItemRand(Items.IRON_HOE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 21:
               is = this.dropItemRand(Items.IRON_HELMET, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 22:
               is = this.dropItemRand(Items.IRON_CHESTPLATE, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 23:
               is = this.dropItemRand(Items.IRON_LEGGINGS, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 24:
               is = this.dropItemRand(Items.IRON_BOOTS, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 25:
               is = this.dropItemRand(OreSpawn.MyUltimateShovel, 1);
               break;
            case 26:
               this.dropItemRand(Item.getItemFromBlock(Blocks.IRON_BLOCK), 1);
               break;
            case 27:
               is = this.dropItemRand(Items.GOLD_NUGGET, 1);
               break;
            case 28:
               is = this.dropItemRand(Items.GOLD_INGOT, 1);
               break;
            case 29:
               is = this.dropItemRand(Items.GOLDEN_CARROT, 1);
               break;
            case 30:
               is = this.dropItemRand(Items.GOLDEN_SWORD, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 31:
               is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 32:
               is = this.dropItemRand(Items.GOLDEN_PICKAXE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 33:
               is = this.dropItemRand(Items.GOLDEN_AXE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 34:
               is = this.dropItemRand(Items.GOLDEN_HOE, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 35:
               is = this.dropItemRand(Items.GOLDEN_HELMET, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 36:
               is = this.dropItemRand(Items.GOLDEN_CHESTPLATE, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 37:
               is = this.dropItemRand(Items.GOLDEN_LEGGINGS, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 38:
               is = this.dropItemRand(Items.GOLDEN_BOOTS, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 39:
               this.dropItemRand(Items.GOLDEN_APPLE, 1);
               break;
            case 40:
               this.dropItemRand(Item.getItemFromBlock(Blocks.GOLD_BLOCK), 1);
               break;
            case 41:
               EntityItem var33 = null;
               is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
               var33 = new EntityItem(
                  this.world,
                  this.posX + OreSpawn.OreSpawnRand.nextInt(3) - OreSpawn.OreSpawnRand.nextInt(3),
                  this.posY + 1.0,
                  this.posZ + OreSpawn.OreSpawnRand.nextInt(3) - OreSpawn.OreSpawnRand.nextInt(3),
                  is
               );
               if (var33 != null) {
                  this.world.spawnEntity(var33);
               }
               break;
            case 42:
               is = this.dropItemRand(OreSpawn.MyExperienceSword, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 43:
               is = this.dropItemRand(OreSpawn.ExperienceHelmet, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 44:
               is = this.dropItemRand(OreSpawn.ExperienceBody, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 45:
               is = this.dropItemRand(OreSpawn.ExperienceLegs, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 46:
               is = this.dropItemRand(OreSpawn.ExperienceBoots, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 47:
               is = this.dropItemRand(OreSpawn.MyAmethystSword, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 48:
               is = this.dropItemRand(OreSpawn.MyAmethystShovel, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 49:
               is = this.dropItemRand(OreSpawn.MyAmethystPickaxe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 50:
               is = this.dropItemRand(OreSpawn.MyAmethystAxe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 51:
               is = this.dropItemRand(OreSpawn.MyAmethystHoe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 52:
               is = this.dropItemRand(Item.getItemFromBlock(OreSpawn.MyBlockAmethystBlock), 1);
               break;
            case 53:
               is = this.dropItemRand(OreSpawn.AmethystHelmet, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 54:
               is = this.dropItemRand(OreSpawn.AmethystBody, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 55:
               is = this.dropItemRand(OreSpawn.AmethystLegs, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 56:
               is = this.dropItemRand(OreSpawn.AmethystBoots, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 57:
               is = this.dropItemRand(OreSpawn.RubyHelmet, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 58:
               is = this.dropItemRand(OreSpawn.RubyBody, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 59:
               is = this.dropItemRand(OreSpawn.RubyLegs, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 60:
               is = this.dropItemRand(OreSpawn.RubyBoots, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 61:
               is = this.dropItemRand(OreSpawn.MyRubySword, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 62:
               is = this.dropItemRand(OreSpawn.MyRubyShovel, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 63:
               is = this.dropItemRand(OreSpawn.MyRubyPickaxe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 64:
               is = this.dropItemRand(OreSpawn.MyRubyAxe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 65:
               is = this.dropItemRand(OreSpawn.MyRubyHoe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 66:
               is = this.dropItemRand(Item.getItemFromBlock(OreSpawn.MyBlockRubyBlock), 1);
               break;
            case 67:
               is = this.dropItemRand(OreSpawn.UltimateHelmet, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 68:
               is = this.dropItemRand(OreSpawn.UltimateBody, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 69:
               is = this.dropItemRand(OreSpawn.UltimateLegs, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 70:
               is = this.dropItemRand(OreSpawn.UltimateBoots, 1);
               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }
               break;
            case 71:
               is = this.dropItemRand(OreSpawn.MyUltimateShovel, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
            case 72:
            default:
               break;
            case 73:
               is = this.dropItemRand(OreSpawn.MyUltimatePickaxe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 74:
               is = this.dropItemRand(OreSpawn.MyUltimateAxe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
               break;
            case 75:
               is = this.dropItemRand(OreSpawn.MyUltimateHoe, 1);
               if (this.world.rand.nextInt(2) == 1) {
                  is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
               }

               if (this.world.rand.nextInt(6) == 1) {
                  is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
               }
         }
      }
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

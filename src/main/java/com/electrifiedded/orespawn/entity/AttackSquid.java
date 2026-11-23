package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
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
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AttackSquid extends EntityMob {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(AttackSquid.class, DataSerializers.BYTE);

   private GenericTargetSorter TargetSorter = null;
   private EntityLivingBase buddy = null;
   private float moveSpeed = 0.25F;
   private int wasshot = 0;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public AttackSquid(World par1World) {
      super(par1World);
      this.setSize(1.0F, 1.25F);
      this.experienceValue = 15;
      this.isImmuneToFire = true;
      this.isImmuneToFire = false;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new MyEntityAIWanderALot(this, 16, 1.0));
      this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(3, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.AttackSquid_stats.attack);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, (byte)0);
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public void setWasShot() {
      this.wasshot = 250;
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public int mygetMaxHealth() {
      return OreSpawn.AttackSquid_stats.health;
   }

   public int getTotalArmorValue() {
      return OreSpawn.AttackSquid_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public int getAttackStrength(Entity par1Entity) {
      return 2;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("squid_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("squid_death");
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

   protected float getSoundVolume() {
      return 1.0F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      return Items.FISH;
   }

   private ItemStack dropItemRand(Item index, int par1) {
      EntityItem var3 = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         this.posY + 1.0,
         this.posZ + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         is
      );
      if (var3 != null) {
         this.world.spawnEntity(var3);
      }

      return is;
   }

   protected void dropFewItems(boolean par1, int par2) {
      ItemStack is = null;
      int var4 = this.world.rand.nextInt(50);
      switch (var4) {
         case 0:
            is = this.dropItemRand(Items.GOLD_NUGGET, 1);
            break;
         case 1:
            is = this.dropItemRand(Items.GOLD_INGOT, 1);
            break;
         case 2:
            is = this.dropItemRand(Items.GOLDEN_CARROT, 1);
            break;
         case 3:
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
         case 4:
            is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
            if (this.world.rand.nextInt(2) == 1) {
               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
            }

            if (this.world.rand.nextInt(6) == 1) {
               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
            }
            break;
         case 5:
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
         case 6:
            is = this.dropItemRand(Items.GOLDEN_AXE, 1);
            if (this.world.rand.nextInt(2) == 1) {
               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
            }

            if (this.world.rand.nextInt(6) == 1) {
               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
            }
            break;
         case 7:
            is = this.dropItemRand(Items.GOLDEN_HOE, 1);
            if (this.world.rand.nextInt(2) == 1) {
               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
            }

            if (this.world.rand.nextInt(6) == 1) {
               is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
            }
            break;
         case 8:
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
               is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));
            }

            if (this.world.rand.nextInt(6) == 1) {
               is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
            }
            break;
         case 9:
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
         case 10:
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
         case 11:
            is = this.dropItemRand(Items.GOLDEN_BOOTS, 1);
            if (this.world.rand.nextInt(6) == 1) {
               is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
            }

            if (this.world.rand.nextInt(2) == 1) {
               is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
            }
            break;
         case 12:
            this.dropItemRand(Items.GOLDEN_APPLE, 1);
            break;
         case 13:
            this.dropItemRand(Item.getItemFromBlock(Blocks.GOLD_BLOCK), 1);
            break;
         case 14:
            EntityItem var3 = null;
            is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
            var3 = new EntityItem(
               this.world,
               this.posX + OreSpawn.OreSpawnRand.nextInt(3) - OreSpawn.OreSpawnRand.nextInt(3),
               this.posY + 1.0,
               this.posZ + OreSpawn.OreSpawnRand.nextInt(3) - OreSpawn.OreSpawnRand.nextInt(3),
               is
            );
            if (var3 != null) {
               this.world.spawnEntity(var3);
            }
            break;
         case 15:
         case 16:
         case 17:
            this.dropItemRand(Items.DYE, 1);
      }

      int i = 1 + this.world.rand.nextInt(3);

      for (int var7 = 0; var7 < i; var7++) {
         this.dropItemRand(Items.FISH, 1);
      }
   }

   public void initCreature() {
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return super.attackEntityAsMob(par1Entity);
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
      if (this.wasshot == 0) {
         super.fall(par1, damageMultiplier);
      }
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      if (this.isDead) {
         return false;
      } else {
         Entity e = par1DamageSource.getTrueSource();
         if (e != null && e instanceof AttackSquid) {
            return false;
         } else if (e != null && e instanceof WaterBall) {
            return false;
         } else if (e != null && e instanceof WaterDragon) {
            return false;
         } else {
            if (e != null && e instanceof EntityLiving) {
               if (e instanceof AttackSquid) {
                  return false;
               }

               if (e instanceof WaterDragon) {
                  return false;
               }

               this.setAttackTarget((EntityLiving)e);
               this.setAttackTarget((EntityLivingBase)e);
               this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);
               ret = true;
            }

            ret = super.attackEntityFrom(par1DamageSource, par2);
            if ((this.getHealth() <= 0.0F || this.isDead)
               && this.world.provider.getDimension() != OreSpawn.DimensionID5
               && !this.world.isRemote
               && e != null
               && e instanceof EntityPlayer
               && this.world.rand.nextInt(15) == 1
               && OreSpawn.KrakenEnable != 0
               && this.wasshot == 0) {
               int j = 1 + this.world.rand.nextInt(3);

               for (int i = 0; i < j; i++) {
                  EntityCreature newent = (EntityCreature)spawnCreature(
                     this.world,
                     "The Kraken",
                     this.posX + this.world.rand.nextInt(4) - this.world.rand.nextInt(4),
                     170.0,
                     this.posZ + this.world.rand.nextInt(4) - this.world.rand.nextInt(4)
                  );
               }
            }

            return ret;
         }
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
         if (this.wasshot > 0) {
            this.wasshot--;
            if (this.wasshot == 0) {
               this.setDead();
               return;
            }
         }

         if (!this.isInWater() && this.world.rand.nextInt(10) == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 12; i++) {
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
            } else {
               if (this.world.rand.nextInt(25) == 1) {
                  this.heal(-1.0F);
               }

               if (this.getHealth() <= 0.0F) {
                  this.setDead();
                  return;
               }
            }
         }

         if (this.world.rand.nextInt(10) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
               if (this.getDistanceSq(e) < 9.0) {
                  this.setAttacking(1);
                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                     this.attackEntityAsMob(e);
                  }
               } else {
                  this.getNavigator().tryMoveToEntityLiving(e, 1.2);
                  this.watercanon(e);
               }
            } else {
               if (this.buddy != null) {
                  this.getNavigator().tryMoveToEntityLiving(this.buddy, 1.0);
               }

               this.setAttacking(0);
            }
         }
      }
   }

   private void watercanon(EntityLivingBase e) {
      double yoff = 1.0;
      double xzoff = 1.2;
      if (this.world.rand.nextInt(5) == 1) {
         if (this.rand.nextInt(3) == 1) {
            InkSack var2 = new InkSack(
               this.world,
               e.posX - this.posX,
               e.posY + 0.75 - (this.posY + yoff),
               e.posZ - this.posZ
            );
            var2.setLocationAndAngles(
               this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),
               this.posY + yoff,
               this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),
               this.rotationYawHead,
               this.rotationPitch
            );
            double var3 = e.posX - this.posX;
            double var5 = e.posY + 0.25 - var2.posY;
            double var7 = e.posZ - this.posZ;
            float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;
            var2.shoot(var3, var5 + var9, var7, 1.4F, 5.0F);
            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(var2);
         } else {
            WaterBall var2 = new WaterBall(
               this.world,
               e.posX - this.posX,
               e.posY + 0.75 - (this.posY + yoff),
               e.posZ - this.posZ
            );
            var2.setLocationAndAngles(
               this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)),
               this.posY + yoff,
               this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)),
               this.rotationYawHead,
               this.rotationPitch
            );
            double var3 = e.posX - this.posX;
            double var5 = e.posY + 0.25 - var2.posY;
            double var7 = e.posZ - this.posZ;
            float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;
            var2.shoot(var3, var5 + var9, var7, 1.4F, 5.0F);
            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(var2);
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
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityPlayer) {
         EntityPlayer p = (EntityPlayer)par1EntityLiving;
         return !p.capabilities.isCreativeMode;
      } else if (par1EntityLiving instanceof Girlfriend) {
         return true;
      } else if (par1EntityLiving instanceof Boyfriend) {
         return true;
      } else if (par1EntityLiving instanceof EntityZombie) {
         return true;
      } else if (par1EntityLiving instanceof EntityVillager) {
         return true;
      } else if (par1EntityLiving instanceof EntitySpider) {
         return true;
      } else if (par1EntityLiving instanceof EntityCaveSpider) {
         return true;
      } else if (par1EntityLiving instanceof Ghost) {
         return false;
      } else if (par1EntityLiving instanceof GhostSkelly) {
         return false;
      } else if (par1EntityLiving instanceof Lizard) {
         return true;
      } else if (par1EntityLiving instanceof AttackSquid) {
         if (this.world.rand.nextInt(5) == 1) {
            this.buddy = par1EntityLiving;
         }

         return false;
      } else {
         return this.wasshot != 0;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 4.0, 10.0));
         Collections.sort(var5, this.TargetSorter);
         Iterator var2 = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;
         EntityLivingBase e = this.getAttackTarget();
         if (e != null && e.isEntityAlive()) {
            return e;
         } else {
            this.setAttackTarget(null);

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

   public final int getAttacking() {
      return this.dataManager.get(DW_20);
   }

   public final void setAttacking(int par1) {
      this.dataManager.set(DW_20,  (byte)par1);
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("WasShot", this.wasshot);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.wasshot = par1NBTTagCompound.getInteger("WasShot");
   }

   public boolean getCanSpawnHere() {
      super.getCanSpawnHere();
      return this.posY < 50.0 ? false : this.world.isDaytime();
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

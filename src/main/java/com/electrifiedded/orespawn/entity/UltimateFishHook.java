package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFishFood.FishType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class UltimateFishHook extends EntityFishHook implements net.minecraftforge.fml.common.registry.IThrowableEntity {
   /** Compat for 1.7 WeightedRandomFishable (gone in 1.12, fishing moved to loot tables). */
   public static class WeightedRandomFishable extends WeightedRandom.Item {
      private ItemStack itemStack;
      private float maxDamagePercent;
      private boolean enchantable;
      public WeightedRandomFishable(ItemStack stack, int weight) {
         super(weight);
         this.itemStack = stack;
      }
      public WeightedRandomFishable setMaxDamagePercent(float p) {
         this.maxDamagePercent = p;
         return this;
      }
      public WeightedRandomFishable setEnchantable() {
         this.enchantable = true;
         return this;
      }
      public ItemStack getItemStack(java.util.Random rand) {
         ItemStack s = this.itemStack.copy();
         if (this.maxDamagePercent > 0.0F && s.isItemStackDamageable()) {
            s.setItemDamage((int)(s.getMaxDamage() * rand.nextFloat() * this.maxDamagePercent));
         }
         if (this.enchantable) {
            EnchantmentHelper.addRandomEnchantment(rand, s, 30, true);
         }
         return s;
      }
   }
   private static final List field_146039_d = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Items.LEATHER_BOOTS), 10).setMaxDamagePercent(0.9F),
      new WeightedRandomFishable(new ItemStack(Items.LEATHER), 10),
      new WeightedRandomFishable(new ItemStack(Items.BONE), 10),
      new WeightedRandomFishable(new ItemStack(Items.POTIONITEM), 10),
      new WeightedRandomFishable(new ItemStack(Items.STRING), 5),
      new WeightedRandomFishable(new ItemStack(Items.FISHING_ROD), 2).setMaxDamagePercent(0.9F),
      new WeightedRandomFishable(new ItemStack(Items.BOWL), 10),
      new WeightedRandomFishable(new ItemStack(Items.STICK), 5),
      new WeightedRandomFishable(new ItemStack(Items.DYE, 10, 0), 1),
      new WeightedRandomFishable(new ItemStack(Blocks.TRIPWIRE_HOOK), 10),
      new WeightedRandomFishable(new ItemStack(Items.ROTTEN_FLESH), 10)
   );
   private static final List field_146041_e = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Blocks.WATERLILY), 1),
      new WeightedRandomFishable(new ItemStack(Items.NAME_TAG), 1),
      new WeightedRandomFishable(new ItemStack(Items.SADDLE), 1),
      new WeightedRandomFishable(new ItemStack(Items.BOW), 1).setMaxDamagePercent(0.25F).setEnchantable(),
      new WeightedRandomFishable(new ItemStack(Items.FISHING_ROD), 1).setMaxDamagePercent(0.25F).setEnchantable(),
      new WeightedRandomFishable(new ItemStack(Items.BOOK), 1).setEnchantable()
   );
   private static final List field_146036_f = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Items.FISH, 1, FishType.COD.getMetadata()), 60),
      new WeightedRandomFishable(new ItemStack(Items.FISH, 1, FishType.SALMON.getMetadata()), 25),
      new WeightedRandomFishable(new ItemStack(Items.FISH, 1, FishType.CLOWNFISH.getMetadata()), 2),
      new WeightedRandomFishable(new ItemStack(Items.FISH, 1, FishType.PUFFERFISH.getMetadata()), 13)
   );
   private static final List orespawn_lava_fish = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(OreSpawn.MySunspotUrchin), 25),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyLavaEel), 10),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MySunFish), 15),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MySparkFish), 10),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyFireFish), 15)
   );
   private static final List orespawn_fish = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyBlueFish), 25),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyPinkFish), 10),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyRockFish), 15),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyWoodFish), 10),
      new WeightedRandomFishable(new ItemStack(OreSpawn.MyGreyFish), 15)
   );
   private int field_146037_g;
   private int field_146048_h;
   private int field_146050_i;
   private Block field_146046_j;
   private boolean inGround;
   private int ticksInGround;
   private int ticksInAir;
   private int fish_on_hook;
   private int fish_wait_time;
   private int ticks_catchable;
   private float fish_direction;
   public Entity caughtEntity;
   private int field_146055_aB;
   private double field_146056_aC;
   private double field_146057_aD;
   private double field_146058_aE;
   private double field_146059_aF;
   private double field_146060_aG;
   @SideOnly(Side.CLIENT)
   private double field_146061_aH;
   @SideOnly(Side.CLIENT)
   private double field_146052_aI;
   @SideOnly(Side.CLIENT)
   private double field_146053_aJ;
   private int fishing_in_lava = 0;
   public EntityPlayer angler;
   private int field_146044_a;

   public UltimateFishHook(World par1World) {
      this(par1World, par1World.playerEntities.isEmpty() ? null : par1World.playerEntities.get(0));
      this.field_146037_g = -1;
      this.field_146048_h = -1;
      this.field_146050_i = -1;
      this.setSize(0.25F, 0.25F);
      this.ignoreFrustumCheck = true;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
   }

   @SideOnly(Side.CLIENT)
   public UltimateFishHook(World par1World, double par2, double par4, double par6, EntityPlayer par8EntityPlayer) {
      this(par1World);
      this.setPosition(par2, par4, par6);
      this.ignoreFrustumCheck = true;
      this.angler = par8EntityPlayer;
      par8EntityPlayer.fishEntity = this;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
   }

   public UltimateFishHook(World par1World, EntityPlayer par2EntityPlayer) {
      super(par1World, par2EntityPlayer);
      this.field_146037_g = -1;
      this.field_146048_h = -1;
      this.field_146050_i = -1;
      this.ignoreFrustumCheck = true;
      this.angler = par2EntityPlayer;
      this.angler.fishEntity = this;
      this.setSize(0.25F, 0.25F);
      this.setLocationAndAngles(
         par2EntityPlayer.posX,
         par2EntityPlayer.posY + 1.62 - 1.62 + par2EntityPlayer.getEyeHeight(),
         par2EntityPlayer.posZ,
         par2EntityPlayer.rotationYaw,
         par2EntityPlayer.rotationPitch
      );
      this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.posY -= 0.1F;
      this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.setPosition(this.posX, this.posY, this.posZ);
      
      float f = 0.4F;
      this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
         * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
         * f;
      this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
         * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
         * f;
      this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * f;
      this.func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
   }

   protected void entityInit() {
      super.entityInit();
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
   }

   @Override
   public EntityPlayer getAngler() {
      return this.angler != null ? this.angler : super.getAngler();
   }

   @Override
   public Entity getThrower() {
      return this.angler;
   }

   @Override
   public void setThrower(Entity thrower) {
      if (thrower instanceof EntityPlayer) {
         this.angler = (EntityPlayer)thrower;
         this.angler.fishEntity = this;
      }
   }

   public void func_146035_c(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
      float f2 = MathHelper.sqrt(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
      p_146035_1_ /= f2;
      p_146035_3_ /= f2;
      p_146035_5_ /= f2;
      p_146035_1_ += this.rand.nextGaussian() * 0.0075F * p_146035_8_;
      p_146035_3_ += this.rand.nextGaussian() * 0.0075F * p_146035_8_;
      p_146035_5_ += this.rand.nextGaussian() * 0.0075F * p_146035_8_;
      p_146035_1_ *= p_146035_7_;
      p_146035_3_ *= p_146035_7_;
      p_146035_5_ *= p_146035_7_;
      this.motionX = p_146035_1_;
      this.motionY = p_146035_3_;
      this.motionZ = p_146035_5_;
      float f3 = MathHelper.sqrt(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
      this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0 / Math.PI);
      this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_146035_3_, f3) * 180.0 / Math.PI);
      this.ticksInGround = 0;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double par1) {
      double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      d1 *= 64.0;
      return par1 < d1 * d1;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotationDirect(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
      this.setPositionAndRotation2(par1, par3, par5, par7, par8, par9);
   }

   private void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
      this.field_146056_aC = par1;
      this.field_146057_aD = par3;
      this.field_146058_aE = par5;
      this.field_146059_aF = par7;
      this.field_146060_aG = par8;
      this.field_146055_aB = par9;
      this.motionX = this.field_146061_aH;
      this.motionY = this.field_146052_aI;
      this.motionZ = this.field_146053_aJ;
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double par1, double par3, double par5) {
      this.field_146061_aH = this.motionX = par1;
      this.field_146052_aI = this.motionY = par3;
      this.field_146053_aJ = this.motionZ = par5;
   }

    private boolean isAABBInMaterial(AxisAlignedBB box, Material material) {
       int x0 = MathHelper.floor(box.minX);
       int x1 = MathHelper.ceil(box.maxX);
       int y0 = MathHelper.floor(box.minY);
       int y1 = MathHelper.ceil(box.maxY);
       int z0 = MathHelper.floor(box.minZ);
       int z1 = MathHelper.ceil(box.maxZ);
       for (int x = x0; x < x1; x++)
          for (int y = y0; y < y1; y++)
             for (int z = z0; z < z1; z++)
                if (this.world.getBlockState(new BlockPos(x, y, z)).getMaterial() == material) return true;
       return false;
    }

    public void onUpdate() {
      if (this.angler == null) {
         if (!this.world.isRemote) {
            this.setDead();
         }
         return;
      }

      if (this.field_146055_aB > 0) {
         double d7 = this.posX + (this.field_146056_aC - this.posX) / this.field_146055_aB;
         double d8 = this.posY + (this.field_146057_aD - this.posY) / this.field_146055_aB;
         double d9 = this.posZ + (this.field_146058_aE - this.posZ) / this.field_146055_aB;
         double d1 = MathHelper.wrapDegrees(this.field_146059_aF - this.rotationYaw);
         this.rotationYaw = (float)(this.rotationYaw + d1 / this.field_146055_aB);
         this.rotationPitch = (float)(this.rotationPitch + (this.field_146060_aG - this.rotationPitch) / this.field_146055_aB);
         this.field_146055_aB--;
         this.setPosition(d7, d8, d9);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      } else {
         if (!this.world.isRemote) {
            ItemStack itemstack = this.angler.getHeldItemMainhand();
            if (this.angler.isDead
               || !this.angler.isEntityAlive()
               || itemstack == null
               || itemstack.getItem() != OreSpawn.MyUltimateFishingRod
               || this.getDistanceSq(this.angler) > 1024.0) {
               this.setDead();
               this.angler.fishEntity = null;
               return;
            }

            if (this.caughtEntity != null) {
               if (!this.caughtEntity.isDead) {
                  this.posX = this.caughtEntity.posX;
                  this.posY = this.caughtEntity.getEntityBoundingBox().minY + this.caughtEntity.height * 0.8;
                  this.posZ = this.caughtEntity.posZ;
                  return;
               }

               this.caughtEntity = null;
            }
         }

         if (this.field_146044_a > 0) {
            this.field_146044_a--;
         }

         if (this.inGround) {
            if (this.world.getBlockState(new BlockPos(this.field_146037_g, this.field_146048_h, this.field_146050_i)).getBlock() == this.field_146046_j) {
               this.ticksInGround++;
               if (this.ticksInGround == 1200) {
                  this.setDead();
               }

               return;
            }

            this.inGround = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         } else {
            this.ticksInAir++;
         }

         Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
         Vec3d vec3 = new Vec3d(
            this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ
         );
         RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec31, vec3);
         vec31 = new Vec3d(this.posX, this.posY, this.posZ);
         vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (movingobjectposition != null) {
            vec3 = new Vec3d(
               movingobjectposition.hitVec.x,
               movingobjectposition.hitVec.y,
               movingobjectposition.hitVec.z
            );
         }

         Entity entity = null;
         List list = this.world
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0, 1.0, 1.0));
         double d0 = 0.0;

         for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity)list.get(i);
            if (entity1.canBeCollidedWith() && (entity1 != this.angler || this.ticksInAir >= 5)) {
               float f = 0.3F;
               AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(f, f, f);
               RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec31, vec3);
               if (movingobjectposition1 != null) {
                  double d2 = vec31.distanceTo(movingobjectposition1.hitVec);
                  if (d2 < d0 || d0 == 0.0) {
                     entity = entity1;
                     d0 = d2;
                  }
               }
            }
         }

         if (entity != null) {
            movingobjectposition = new RayTraceResult(entity);
         }

         if (movingobjectposition != null) {
            if (movingobjectposition.entityHit != null) {
               if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.angler), 0.0F)) {
                  this.caughtEntity = movingobjectposition.entityHit;
               }
            } else {
               this.inGround = true;
            }
         }

         if (!this.inGround) {
            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            float f5 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.rotationPitch = (float)(Math.atan2(this.motionY, f5) * 180.0 / Math.PI);

            while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
               this.prevRotationPitch -= 360.0F;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
               this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
               this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
               this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f6 = 0.92F;
            if (this.onGround || this.collidedHorizontally) {
               f6 = 0.5F;
            }

            byte b0 = 5;
            double d10 = 0.0;

            for (int j = 0; j < b0; j++) {
               double d3 = this.getEntityBoundingBox().minY
                  + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (j + 0) / b0
                  - 0.125
                  + 0.125;
               double d4 = this.getEntityBoundingBox().minY
                  + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (j + 1) / b0
                  - 0.125
                  + 0.125;
               AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(
                  this.getEntityBoundingBox().minX,
                  d3,
                  this.getEntityBoundingBox().minZ,
                  this.getEntityBoundingBox().maxX,
                  d4,
                  this.getEntityBoundingBox().maxZ
               );
               if (this.isAABBInMaterial(axisalignedbb1, Material.WATER)) {
                   d10 += 1.0 / b0;
                }

                if (this.isAABBInMaterial(axisalignedbb1, Material.LAVA)) {
                  d10 += 1.0 / b0;
               }
            }

            if (!this.world.isRemote && d10 > 0.0) {
               WorldServer worldserver = (WorldServer)this.world;
               int k = 1;
                if (this.rand.nextFloat() < 0.25F
                   && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY) + 1, MathHelper.floor(this.posZ)))) {
                   k = 2;
                }

                if (this.rand.nextFloat() < 0.5F
                   && !this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY) + 1, MathHelper.floor(this.posZ)))) {
                   k--;
                }

               if (this.fish_on_hook > 0) {
                  this.fish_on_hook--;
                  if (this.fish_on_hook <= 0) {
                     this.fish_wait_time = 0;
                     this.ticks_catchable = 0;
                  }
               } else if (this.ticks_catchable > 0) {
                  this.ticks_catchable -= k;
                  if (this.ticks_catchable <= 0) {
                     this.motionY -= 0.2F;
                     this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.splash"), 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                      float f1 = MathHelper.floor(this.getEntityBoundingBox().minY);
                      worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, this.posX, f1 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0, (double)this.width, 0.2F, new int[0]);
                      worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_WAKE, this.posX, f1 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), (double)this.width, 0.0, (double)this.width, 0.2F, new int[0]);
                      this.fish_on_hook = MathHelper.getInt(this.rand, 10, 30);
                  } else {
                     this.fish_direction = (float)(this.fish_direction + this.rand.nextGaussian() * 4.0);
                     float f1 = this.fish_direction * (float) (Math.PI / 180.0);
                     float f7 = MathHelper.sin(f1);
                     float f2 = MathHelper.cos(f1);
                     double d11 = this.posX + f7 * this.ticks_catchable * 0.1F;
                     double d5 = MathHelper.floor(this.getEntityBoundingBox().minY) + 1.0F;
                     double d6 = this.posZ + f2 * this.ticks_catchable * 0.1F;
                      if (this.rand.nextFloat() < 0.15F) {
                         worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, d11, d5 - 0.1, d6, 1, (double)f7, 0.1, (double)f2, 0.0, new int[0]);
                      }

                      float f3 = f7 * 0.04F;
                      float f4 = f2 * 0.04F;
                      worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_WAKE, d11, d5, d6, 0, (double)f4, 0.01, (double)(-f3), 1.0, new int[0]);
                      worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_WAKE, d11, d5, d6, 0, (double)(-f4), 0.01, (double)f3, 1.0, new int[0]);
                  }
               } else if (this.fish_wait_time > 0) {
                  this.fish_wait_time -= k;
                  float f1 = 0.15F;
                  if (this.fish_wait_time < 20) {
                     f1 = (float)(f1 + (20 - this.fish_wait_time) * 0.05);
                  } else if (this.fish_wait_time < 40) {
                     f1 = (float)(f1 + (40 - this.fish_wait_time) * 0.02);
                  } else if (this.fish_wait_time < 60) {
                     f1 = (float)(f1 + (60 - this.fish_wait_time) * 0.01);
                  }

                  if (this.rand.nextFloat() < f1) {
                     float f7 = MathHelper.nextFloat(this.rand, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
                     float f2 = MathHelper.nextFloat(this.rand, 25.0F, 60.0F);
                     double d11 = this.posX + MathHelper.sin(f7) * f2 * 0.1F;
                     double d5 = MathHelper.floor(this.getEntityBoundingBox().minY) + 1.0F;
                     double d6 = this.posZ + MathHelper.cos(f7) * f2 * 0.1F;
                      worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, d11, d5, d6, 2 + this.rand.nextInt(2), 0.1, 0.0, 0.1, 0.0, new int[0]);
                  }

                  if (this.fish_wait_time <= 0) {
                     this.fish_direction = MathHelper.nextFloat(this.rand, 0.0F, 360.0F);
                     this.ticks_catchable = MathHelper.getInt(this.rand, 100, 200);
                  }
               } else {
                  this.fish_wait_time = MathHelper.getInt(this.rand, 50, 300);
                  this.fish_wait_time = this.fish_wait_time - EnchantmentHelper.getFishingSpeedBonus(this.angler.getHeldItemMainhand()) * 20 * 5;
               }

               if (this.fish_on_hook > 0) {
                  this.motionY = this.motionY
                     - this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2;
               }
            }

            double d2 = d10 * 2.0 - 1.0;
            this.motionY += 0.04F * d2;
            if (d10 > 0.0) {
               f6 = (float)(f6 * 0.9);
               this.motionY *= 0.8;
            }

            this.motionX *= f6;
            this.motionY *= f6;
            this.motionZ *= f6;
            this.setPosition(this.posX, this.posY, this.posZ);
         }
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("xTile", (short)this.field_146037_g);
      par1NBTTagCompound.setShort("yTile", (short)this.field_146048_h);
      par1NBTTagCompound.setShort("zTile", (short)this.field_146050_i);
      par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_146046_j));
      par1NBTTagCompound.setByte("shake", (byte)this.field_146044_a);
      par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.field_146037_g = par1NBTTagCompound.getShort("xTile");
      this.field_146048_h = par1NBTTagCompound.getShort("yTile");
      this.field_146050_i = par1NBTTagCompound.getShort("zTile");
      this.field_146046_j = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
      this.field_146044_a = par1NBTTagCompound.getByte("shake") & 255;
      this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
   }

   @SideOnly(Side.CLIENT)
   public float getShadowSize() {
      return 0.0F;
   }

   public int handleHookRetraction() {
      if (this.world.isRemote || this.angler == null) {
         return 0;
      } else {
         byte b0 = 0;
         if (this.caughtEntity != null) {
            double d0 = this.angler.posX - this.posX;
            double d2 = this.angler.posY - this.posY;
            double d4 = this.angler.posZ - this.posZ;
            double d6 = MathHelper.sqrt(d0 * d0 + d2 * d2 + d4 * d4);
            double d8 = 0.1;
            this.caughtEntity.motionX += d0 * d8;
            this.caughtEntity.motionY = this.caughtEntity.motionY + (d2 * d8 + MathHelper.sqrt(d6) * 0.08);
            this.caughtEntity.motionZ += d4 * d8;
            b0 = 3;
         } else if (this.fish_on_hook > 0) {
            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + 1.25, this.posZ, this.func_146033_f());
            double d1 = this.angler.posX - this.posX;
            double d3 = this.angler.posY - this.posY;
            double d5 = this.angler.posZ - this.posZ;
            double d7 = MathHelper.sqrt(d1 * d1 + d3 * d3 + d5 * d5);
            double d9 = 0.1;
            entityitem.motionX = d1 * d9;
            entityitem.motionY = d3 * d9 + MathHelper.sqrt(d7) * 0.08;
            entityitem.motionZ = d5 * d9;
            entityitem.setEntityInvulnerable(false); // PORT-TODO: rely on EntityItem default lava immunity drops being invulnerable not needed
            this.world.spawnEntity(entityitem);
            this.angler
               .world
               .spawnEntity(
                  new EntityXPOrb(
                     this.angler.world,
                     this.angler.posX,
                     this.angler.posY + 0.5,
                     this.angler.posZ + 0.5,
                     this.rand.nextInt(6) + 1
                  )
               );
            b0 = 1;
         }

         if (this.inGround) {
            b0 = 2;
         }

         this.setDead();
         this.angler.fishEntity = null;
         return b0;
      }
   }

   private ItemStack func_146033_f() {
      float f = this.world.rand.nextFloat();
      int i = EnchantmentHelper.getFishingLuckBonus(this.angler.getHeldItemMainhand());
      int j = EnchantmentHelper.getFishingSpeedBonus(this.angler.getHeldItemMainhand());
      float f1 = 0.1F - i * 0.025F - j * 0.01F;
      float f2 = 0.05F + i * 0.01F - j * 0.01F;
      f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
      f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
      Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();
      if (bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
         this.angler.addStat(net.minecraft.stats.StatList.FISH_CAUGHT, 1);
         return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, orespawn_lava_fish)).getItemStack(this.rand);
      } else if (f < f1) {
         this.angler.addStat(net.minecraft.stats.StatList.FISH_CAUGHT, 1);
         return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146039_d)).getItemStack(this.rand);
      } else {
         f -= f1;
         if (f < f2) {
            this.angler.addStat(net.minecraft.stats.StatList.FISH_CAUGHT, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146041_e)).getItemStack(this.rand);
         } else {
            float f3 = this.world.rand.nextFloat();
            this.angler.addStat(net.minecraft.stats.StatList.FISH_CAUGHT, 1);
            return f3 < 0.5F
               ? ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, field_146036_f)).getItemStack(this.rand)
               : ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, orespawn_fish)).getItemStack(this.rand);
         }
      }
   }

   public void setDead() {
      super.setDead();
      if (this.angler != null) {
         this.angler.fishEntity = null;
      }
   }
}

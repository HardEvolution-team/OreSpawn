package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Elevator extends EntityLiving {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_22 = EntityDataManager.<Integer>createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_23 = EntityDataManager.<Integer>createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DW_24 = EntityDataManager.<Float>createKey(Elevator.class, DataSerializers.FLOAT);

   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   private double velocityX;
   private double velocityY;
   private double velocityZ;
   private int damage_counter = 100;
   private int exploding = 0;
   private int color = 1;
   private int playing = 0;
   private static final ResourceLocation texture1 = new ResourceLocation("orespawndedsport", "elevator1.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawndedsport", "elevator2.png");
   private static final ResourceLocation texture3 = new ResourceLocation("orespawndedsport", "elevator3.png");
   private static final ResourceLocation texture4 = new ResourceLocation("orespawndedsport", "elevator4.png");
   private static final ResourceLocation texture5 = new ResourceLocation("orespawndedsport", "elevator5.png");
   private static final ResourceLocation texture6 = new ResourceLocation("orespawndedsport", "elevator6.png");
   private static final ResourceLocation texture7 = new ResourceLocation("orespawndedsport", "elevator7.png");
   private static final ResourceLocation texture8 = new ResourceLocation("orespawndedsport", "elevator8.png");
   private static final ResourceLocation texture9 = new ResourceLocation("orespawndedsport", "elevator9.png");
   private static final ResourceLocation texture10 = new ResourceLocation("orespawndedsport", "elevator10.png");

   public Elevator(World par1World) {
      super(par1World);
      this.setSize(1.25F, 1.0F);
      if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
   }

   public Elevator(World par1World, double par2, double par4, double par6) {
      this(par1World);
      this.setPosition(par2, par4 + this.getYOffset(), par6);
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.prevPosX = par2;
      this.prevPosY = par4;
      this.prevPosZ = par6;
   }

   public ResourceLocation getTexture() {
      switch (this.getColor()) {
         case 1:
            return texture1;
         case 2:
            return texture2;
         case 3:
            return texture3;
         case 4:
            return texture4;
         case 5:
            return texture5;
         case 6:
            return texture6;
         case 7:
            return texture7;
         case 8:
            return texture8;
         case 9:
            return texture9;
         case 10:
            return texture10;
         default:
            return texture1;
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.33F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   protected boolean canDespawn() {
      return false;
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public int getTrackingRange() {
      return 128;
   }

   public int getUpdateFrequency() {
      return 10;
   }

   public boolean sendsVelocityUpdates() {
      return true;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_22, new Integer(0));
      this.dataManager.register(DW_23, new Integer(1));
      this.dataManager.register(DW_24, new Float(0.0F));
      this.dataManager.register(DW_20, new Integer(0));
      this.dataManager.register(DW_21, new Integer(0));
      this.enablePersistence();
   }

   public boolean canBePushed() {
      return true;
   }

   public double getMountedYOffset() {
      return 0.5;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean p = par1DamageSource.getTrueSource() instanceof EntityPlayer;
      if (this.getControllingPassenger() != null && !p) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("inWall")) {
         return false;
      } else if (!this.world.isRemote && !this.isDead) {
         this.setForwardDirection(-this.getForwardDirection());
         this.setTimeSinceHit(10);
         this.setDamageTaken(this.getDamageTaken() + par2 * 10.0F);
         this.markVelocityChanged();
         boolean flag = par1DamageSource.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getTrueSource()).capabilities.isCreativeMode;
         if (flag || this.getDamageTaken() > 40.0F) {
            if (this.getControllingPassenger() != null) {
               this.getControllingPassenger().dismountRidingEntity();
            }

            if (!flag) {
               this.dropItem(OreSpawn.MyElevator, 1);
            }

            this.setDead();
         }

         return true;
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void performHurtAnimation() {
      this.setForwardDirection(-this.getForwardDirection());
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
      if (this.getControllingPassenger() != null) {
         this.boatPosRotationIncrements = par9 + 8;
      } else {
         this.boatPosRotationIncrements = 6;
      }

      this.boatX = par1;
      this.boatY = par3;
      this.boatZ = par5;
      this.boatYaw = par7;
      this.boatPitch = par8;
      this.motionX = this.velocityX;
      this.motionY = this.velocityY;
      this.motionZ = this.velocityZ;
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double par1, double par3, double par5) {
      this.velocityX = this.motionX = par1;
      this.velocityY = this.motionY = par3;
      this.velocityZ = this.motionZ = par5;
   }

   @Override
   public void onLivingUpdate() {
      if (this.getControllingPassenger() == null) {
         super.onLivingUpdate();
      }

      this.setFire(0);
   }

   public void onUpdate() {
      List list = null;
      double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
      double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
      double obstruction_factor = 0.0;
      double relative_g = 0.0;
      double max_speed = 0.85;
      double gh = 0.75;
      int dist = 2;
      if (!this.isDead) {
         this.isAirBorne = true;
         if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
         }

         if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
         }

         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         if (velocity > 0.15 && this.getControllingPassenger() != null) {
            double d4 = Math.cos(Math.toRadians(this.rotationYaw + 270.0F));
            double d5 = Math.sin(Math.toRadians(this.rotationYaw + 270.0F));
            Block bid = Blocks.AIR;

            int i;
            for (i = 1; i < 10; i++) {
               bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - i, (int)this.posZ)).getBlock();
               if (bid != Blocks.AIR) {
                  break;
               }
            }

            for (int j = 0; j < 1.0 + velocity * 10.0; j++) {
               d6 = this.rand.nextFloat() * 2.0F - 1.0F;
               d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
               if (this.rand.nextBoolean()) {
                  double d8 = this.posX - d4 * d6 * 0.8 + d5 * d7;
                  double d9 = this.posZ - d5 * d6 * 0.8 - d4 * d7;
                  if (this.rand.nextBoolean()) {
                     this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d8, this.posY - 0.25, d9, this.motionX, this.motionY, this.motionZ);
                  } else {
                     this.world.spawnParticle(EnumParticleTypes.REDSTONE, d8, this.posY - 0.25, d9, this.motionX, this.motionY, this.motionZ);
                  }
               } else {
                  double d8 = this.posX + d4 + d5 * d6 * 0.7;
                  double d9 = this.posZ + d5 - d4 * d6 * 0.7;
                  if (this.rand.nextBoolean()) {
                     this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d8, this.posY - 0.225, d9, this.motionX, this.motionY, this.motionZ);
                  } else {
                     this.world.spawnParticle(EnumParticleTypes.REDSTONE, d8, this.posY - 0.225, d9, this.motionX, this.motionY, this.motionZ);
                  }
               }

               if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                  for (int k = 0; k < 5; k++) {
                     this.world
                        .spawnParticle(EnumParticleTypes.WATER_SPLASH,
                           this.posX + this.rand.nextFloat(),
                           this.posY - i + 1.25,
                           this.posZ + this.rand.nextFloat(),
                           this.motionX / 2.0,
                           this.motionY + velocity,
                           this.motionZ / 2.0
                        );
                  }
               }
            }
         }

         if (this.playing > 0) {
            this.playing--;
         }

         if (this.getControllingPassenger() != null && this.playing == 0 && this.world.rand.nextInt(80) == 1) {
            this.world.playSound(null, this.getControllingPassenger().posX, this.getControllingPassenger().posY, this.getControllingPassenger().posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("hover"), net.minecraft.util.SoundCategory.NEUTRAL, 0.45F, 1.0F);
            this.playing = 55;
         }

         if (!this.world.isRemote) {
            if (this.exploding > 0) {
               this.exploding--;
            }

            if (this.exploding == 0 && velocity > 0.65 && this.world.rand.nextInt(20000) == 1) {
               this.exploding = 45;
               this.playing = 50;
            }

            this.setExploding(this.exploding);
         } else {
            this.exploding = this.getExploding();
         }

         if (this.getExploding() > 0 && this.getControllingPassenger() != null) {
            if (this.world.rand.nextInt(10) == 1) {
               this.world.playSound(null, this.getControllingPassenger().posX, this.getControllingPassenger().posY, this.getControllingPassenger().posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.55F, 0.75F + this.world.rand.nextFloat());
            }

            for (int i = 0; i < 15; i++) {
               this.world
                  .spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                     (int)(this.posX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0F),
                     (int)(this.posY + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0F),
                     (int)(this.posZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0F),
                     this.motionX,
                     0.0,
                     this.motionZ
                  );
               this.world
                  .spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
                     (int)(this.posX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0F),
                     (int)(this.posY + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0F),
                     (int)(this.posZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0F),
                     this.motionX,
                     0.0,
                     this.motionZ
                  );
               this.world
                  .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                     (int)(this.posX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0F),
                     (int)(this.posY + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0F),
                     (int)(this.posZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0F),
                     this.motionX,
                     0.0,
                     this.motionZ
                  );
               this.world
                  .spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                     (int)(this.posX + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0F),
                     (int)(this.posY + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0F),
                     (int)(this.posZ + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0F),
                     this.motionX,
                     0.0,
                     this.motionZ
                  );
            }
         }

         if (this.world.isRemote) {
            if (this.getControllingPassenger() == null) {
               Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
               if (bid != Blocks.AIR) {
                  this.motionY += 0.06;
                  this.posY += 0.07;
                  this.boatY += 0.07;
               } else {
                  this.motionY -= 0.003;
               }
             } else if (this.getControllingPassenger() instanceof EntityPlayer) {
                com.electrifiedded.orespawn.OreSpawnMod.proxy.sendRiderInput((EntityPlayer)this.getControllingPassenger());
             }

            if (this.boatPosRotationIncrements > 0) {
               double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
               double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
               double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
               this.setPosition(d4, d5, d11);
               this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
               double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
               if (this.getControllingPassenger() != null) {
                  d10 = MathHelper.wrapDegrees((double)this.getControllingPassenger().rotationYaw - this.rotationYaw);
               }

               this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
               this.setRotation(this.rotationYaw, this.rotationPitch);
               this.boatPosRotationIncrements--;
            } else {
               double d4 = this.posX + this.motionX;
               double d5 = this.posY + this.motionY;
               double d11 = this.posZ + this.motionZ;
               this.setPosition(d4, d5, d11);
               this.motionX *= 0.99;
               this.motionY *= 0.95;
               this.motionZ *= 0.99;
            }
         } else {
            if (this.getControllingPassenger() != null) {
               gh = 1.25;
            }

            Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
            if (bid != Blocks.AIR) {
               this.motionY += 0.06;
               this.posY += 0.1;
               if (bid == Blocks.TALLGRASS
                  && this.getControllingPassenger() != null
                  && this.world.rand.nextInt(200) == 1
                  && this.world.getGameRules().getBoolean("mobGriefing")) {
                  this.world.setBlockState(new BlockPos((int)this.posX, (int)(this.posY - gh), (int)this.posZ), Blocks.AIR.getDefaultState());
               }

               if (bid == Blocks.GRASS
                  && this.getControllingPassenger() != null
                  && this.world.rand.nextInt(200) == 1
                  && this.world.getGameRules().getBoolean("mobGriefing")) {
                  this.world.setBlockState(new BlockPos((int)this.posX, (int)(this.posY - gh), (int)this.posZ), Blocks.DIRT.getDefaultState());
               }
            } else {
               this.motionY -= 0.01;
            }

            if (this.getControllingPassenger() == null) {
               if (this.getControllingPassenger() == null) {
                  this.motionX = 0.0;
                  this.motionZ = 0.0;
               }
            } else {
               EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();
               obstruction_factor = 0.0;
               int var85 = 3;
               var85 += (int)(velocity * 8.0);

               for (int k = 1; k < var85; k++) {
                  for (int i = 1; i < var85 * 2; i++) {
                     double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                     double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                     bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + dz))).getBlock();
                     if (bid != Blocks.AIR) {
                        obstruction_factor += 0.05;
                     }
                  }
               }

               this.motionY += obstruction_factor * 0.11;
               this.posY += obstruction_factor * 0.11;
               double d4 = this.getControllingPassenger().rotationYaw;
               d4 %= 360.0;

               while (d4 < 0.0) {
                  d4 += 360.0;
               }

               double d5 = this.rotationYaw;
               d5 %= 360.0;

               while (d5 < 0.0) {
                  d5 += 360.0;
               }

               relative_g = (d4 - d5) % 180.0;

               while (relative_g < 0.0) {
                  relative_g += 180.0;
               }

               if (relative_g > 90.0) {
                  relative_g -= 180.0;
               }

               if (velocity > 0.01) {
                  d4 = 1.85 - velocity;
                  d4 = Math.abs(d4);
                  if (d4 < 0.01) {
                     d4 = 0.01;
                  }

                  if (d4 > 0.9) {
                     d4 = 0.9;
                  }

                  this.rotationYaw = this.getControllingPassenger().rotationYaw + (float)(relative_g * d4);
               } else {
                  this.rotationYaw = this.getControllingPassenger().rotationYaw;
               }

               relative_g = Math.abs(relative_g) * velocity;
               if (relative_g > 50.0) {
                  relative_g = 0.0;
               }

               this.rotationPitch = 10.0F * (float)velocity;
               this.setRotation(this.rotationYaw, this.rotationPitch);
               double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
               if (this.exploding != 0) {
                  newvelocity -= 0.05;
                  if (newvelocity < 0.0) {
                     newvelocity = 0.0;
                  }
               }

               double rr = Math.atan2(this.getControllingPassenger().motionZ, this.getControllingPassenger().motionX);
               double rhm = Math.atan2(this.motionZ, this.motionX);
               double rhdir = Math.toRadians((this.getControllingPassenger().rotationYaw + 90.0F) % 360.0F);
               double rt = 0.0;
               double pi = 3.1415926545;
               double deltav = 0.0;
               float im = com.electrifiedded.orespawn.network.RiderInputState.getForward(pp);
                if (com.electrifiedded.orespawn.network.RiderInputState.isFlyingUp(pp)) {
                  max_speed++;
               }

               double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
               if (rdv > pi) {
                  rdv -= pi * 2.0;
               }

               rdv = Math.abs(rdv);
               if (Math.abs(newvelocity) < 0.01) {
                  rdv = 0.0;
               }

               if (rdv > 1.5) {
                  newvelocity = -newvelocity;
               }

               if (Math.abs(im) > 0.001F) {
                  if (im > 0.0F) {
                     deltav = 0.025;
                     if (max_speed > 1.0) {
                        deltav += 0.15;
                     }
                  } else {
                     max_speed = 0.35;
                     deltav = -0.02;
                  }

                  newvelocity += deltav;
                  if (newvelocity >= 0.0) {
                     if (newvelocity > max_speed) {
                        newvelocity = max_speed;
                     }

                     this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                     this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                  } else {
                     if (newvelocity < -max_speed) {
                        newvelocity = -max_speed;
                     }

                     newvelocity = -newvelocity;
                     this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                     this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                  }
               } else if (newvelocity >= 0.0) {
                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
               } else {
                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
               }
            }

            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            if (this.collidedHorizontally && velocity > 0.75) {
               this.setDead();
               int p = this.world.rand.nextInt(10);

               for (int k = 0; k < 6 + p; k++) {
                  this.dropItem(Items.STICK, 1);
               }

               for (int var84 = 0; var84 < 2; var84++) {
                  this.dropItem(Items.DIAMOND, 1);
               }
            } else {
               this.motionX *= 0.98;
               this.motionY *= 0.94;
               this.motionZ *= 0.98;
            }

            list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.25, 0.0, 0.25));
            if (list != null && !list.isEmpty()) {
               for (int l = 0; l < list.size(); l++) {
                  Entity entity = (Entity)list.get(l);
                  if (entity != this.getControllingPassenger() && entity.canBePushed() && !(entity instanceof Girlfriend) && !(entity instanceof Boyfriend)) {
                     entity.applyEntityCollision(this);
                  }
               }
            }

            if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {
               if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
            }
         }
      }
   }

   public void updateRiderPosition() {
      if (this.getControllingPassenger() != null) {
         this.getControllingPassenger().setPosition(this.posX, this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(), this.posZ);
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setInteger("HoverColor", this.getColor());
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.color = par1NBTTagCompound.getInteger("HoverColor");
      if (this.color < 1) {
         this.color = 1;
      }

      if (this.color > 10) {
         this.color = 10;
      }

      this.setColor(this.color);
   }

   public float getShadowSize() {
      return 0.25F;
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if (var2 != null && var2.getCount() <= 0) {
         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
         var2 = null;
      }

      if (var2 != null && var2.getItem() == OreSpawn.MyUltimateSword && par1EntityPlayer.getDistanceSq(this) < 16.0) {
         if (!this.world.isRemote) {
            this.color++;
            if (this.color > 10) {
               this.color = 1;
            }

            this.setColor(this.color);
         }

         return true;
      } else if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger() != par1EntityPlayer) {
         return true;
      } else {
         if (!this.world.isRemote) {
            par1EntityPlayer.startRiding(this);
         }

         return true;
      }
   }

   public void setDamageTaken(float f) {
      this.dataManager.set(DW_24,  f);
   }

   public float getDamageTaken() {
      return this.dataManager.get(DW_24);
   }

   public void setTimeSinceHit(int par1) {
      this.dataManager.set(DW_22,  par1);
   }

   public int getTimeSinceHit() {
      return this.dataManager.get(DW_22);
   }

   public void setForwardDirection(int par1) {
      this.dataManager.set(DW_23,  par1);
   }

   public int getForwardDirection() {
      return this.dataManager.get(DW_23);
   }

   public void setExploding(int par1) {
      this.dataManager.set(DW_20,  par1);
   }

   public int getExploding() {
      return this.dataManager.get(DW_20);
   }

   public void setColor(int par1) {
      this.dataManager.set(DW_21,  par1);
   }

   public int getColor() {
      return this.dataManager.get(DW_21);
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

    /** 1.12 only reports a controlling passenger when asked to; legacy code expects the riding player. */
    @Override
    public net.minecraft.entity.Entity getControllingPassenger() {
        for (net.minecraft.entity.Entity passenger : this.getPassengers()) {
            if (passenger instanceof net.minecraft.entity.player.EntityPlayer) return passenger;
        }
        return null;
    }

    /** The server simulates this mount, as in 1.7, and the client follows its position updates. */
    @Override
    public boolean canPassengerSteer() {
        return false;
    }

    @Override
    public void updatePassenger(net.minecraft.entity.Entity passenger) {
        if (this.isPassenger(passenger) && passenger == this.getControllingPassenger()) {
            this.updateRiderPosition();
        } else {
            super.updatePassenger(passenger);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int increments, boolean teleport) {
        this.setPositionAndRotation2(x, y, z, yaw, pitch, increments);
    }
}

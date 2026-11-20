package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Fairy extends EntityAmbientCreature {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(Fairy.class, DataSerializers.VARINT);

   private static final ResourceLocation texture0 = new ResourceLocation("orespawn", "fairytexture.png");
   private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "fairytexture2.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "fairytexture3.png");
   private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "fairytexture4.png");
   private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "fairytexture5.png");
   private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "fairytexture6.png");
   private static final ResourceLocation texture6 = new ResourceLocation("orespawn", "fairytexture7.png");
   private static final ResourceLocation texture7 = new ResourceLocation("orespawn", "fairytexture8.png");
   private static final ResourceLocation texture8 = new ResourceLocation("orespawn", "fairytexture9.png");
   int my_blink = 0;
   int blinker = 0;
   int myspace = 0;
   public int fairy_type = 0;
   private int force_sync = 10;
   private BlockPos currentFlightTarget = null;
   private String myowner = null;
   private GenericTargetSorter TargetSorter = null;

   public Fairy(World par1World) {
      super(par1World);
      this.my_blink = 20 + this.rand.nextInt(20);
      this.setSize(0.4F, 0.8F);
      if (par1World != null) {
         this.fairy_type = par1World.rand.nextInt(9);
      }
      this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.tasks.addTask(1, new EntityAILookIdle(this));
      this.TargetSorter = new GenericTargetSorter(this);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
   }

   public ResourceLocation getTexture(Fairy a) {
      if (this.fairy_type == 8) {
         return texture8;
      } else if (this.fairy_type == 7) {
         return texture7;
      } else if (this.fairy_type == 6) {
         return texture6;
      } else if (this.fairy_type == 5) {
         return texture5;
      } else if (this.fairy_type == 4) {
         return texture4;
      } else if (this.fairy_type == 3) {
         return texture3;
      } else if (this.fairy_type == 2) {
         return texture2;
      } else {
         return this.fairy_type == 1 ? texture1 : texture0;
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, this.fairy_type);
   }

   public void setOwner(EntityLivingBase e) {
      EntityPlayer p = null;
      if (e != null && e instanceof EntityPlayer) {
         p = (EntityPlayer)e;
         String s = p.getName();
         if (s != null) {
            this.myowner = s;
         }
      }
   }

   public float getBlink() {
      return this.blinker < this.my_blink / 2 ? 240.0F : 0.0F;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return this.world.getDifficulty() == EnumDifficulty.PEACEFUL ? false : par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
   }

   public int getTotalArmorValue() {
      return 4;
   }

   protected float getSoundVolume() {
      return 0.25F;
   }

   protected float getSoundPitch() {
      return 1.7F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("rat_hit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("big_splat");
   }

   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   protected void collideWithNearbyEntities() {
   }

   public int mygetMaxHealth() {
      return 40;
   }

   protected Item getDropItem() {
      return Item.getItemFromBlock(OreSpawn.CrystalTorch);
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionY *= 0.600000023841;
      this.blinker++;
      if (this.blinker > this.my_blink) {
         this.blinker = 0;
      }

      this.force_sync--;
      if (this.force_sync < 0) {
         this.force_sync = 10;
         if (this.world.isRemote) {
            this.fairy_type = this.dataManager.get(DW_20);
         } else {
            this.dataManager.set(DW_20,  this.fairy_type);
         }
      }

      long t = this.world.getWorldTime();
      t %= 24000L;
      if (t >= 12000L) {
         if (this.world.isRemote && this.world.rand.nextInt(5) == 0 && this.getBlink() > 1.0F) {
            this.world
               .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                  this.posX,
                  this.posY - 0.15F,
                  this.posZ,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 8.0F,
                  -this.world.rand.nextFloat() / 8.0F,
                  (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 8.0F
               );
         }
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      if (this.myowner == null) {
         this.myowner = "null";
      }

      par1NBTTagCompound.setString("MyOwner", this.myowner);
      par1NBTTagCompound.setInteger("FairyType", this.fairy_type);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.myowner = par1NBTTagCompound.getString("MyOwner");
      if (this.myowner != null && this.myowner.equals("null")) {
         this.myowner = null;
      }

      this.fairy_type = par1NBTTagCompound.getInteger("fairyType");
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
      } else {
         return !this.getEntitySenses().canSee(par1EntityLiving) ? false : par1EntityLiving instanceof EntityMob;
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8.0, 8.0, 8.0));
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

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.25, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   protected void updateAITasks() {
      int keep_trying = 25;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.world.rand.nextInt(200) != 0
            && !(this.getDistanceSq(currentFlightTarget) < 2.5F)) {
            if (this.world.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               EntityLivingBase e = null;
               e = this.findSomethingToAttack();
               if (e != null) {
                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                  if (this.getDistanceSq(e) < 6.0) {
                     this.attackEntityAsMob(e);
                  }
               }
            } else if (this.myowner != null) {
               EntityPlayer p = this.world.getPlayerEntityByName(this.myowner);
               if (p != null) {
                  if (this.getDistanceSq(p) > 64.0) {
                     this.currentFlightTarget = new BlockPos((int)p.posX + this.world.rand.nextInt(3) - this.world.rand.nextInt(3),
                           (int)(p.posY + 1.0),
                           (int)p.posZ + this.world.rand.nextInt(3) - this.world.rand.nextInt(3)
                        );
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
         } else {
            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               int zdir = this.world.rand.nextInt(8);
               int xdir = this.world.rand.nextInt(8);
               if (this.world.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.world.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + this.world.rand.nextInt(5) - 2, (int)this.posZ + zdir
                  );
               bid = this.world
                  .getBlockState(this.currentFlightTarget).getBlock();
               if (bid == Blocks.AIR
                  && !this.canSeeTarget(this.currentFlightTarget.getX(), this.currentFlightTarget.getY(), this.currentFlightTarget.getZ())
                  )
                {
                  bid = Blocks.STONE;
               }
            }
         }

         if (this.world.rand.nextInt(250) == 1) {
            this.heal(1.0F);
         }

         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.2 - this.motionX) * 0.1;
         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.1;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.2 - this.motionZ) * 0.1;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.2F;
         this.rotationYaw += var8 / 4.0F;
      }
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return true;
   }

   public boolean getCanSpawnHere() {
      int sc = 0;

      for (int k = -1; k <= 1; k++) {
         for (int j = -1; j <= 1; j++) {
            Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY, (int)this.posZ + k)).getBlock();
            if (bid == Blocks.AIR) {
               sc++;
            }
         }
      }

      return sc < 6 ? false : !(this.posY < 50.0);
   }

   public void initCreature() {
   }

   protected boolean canDespawn() {
      return this.isNoDespawnRequired() ? false : this.myowner == null;
   }
}

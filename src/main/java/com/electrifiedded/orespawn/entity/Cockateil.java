package com.electrifiedded.orespawn.entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Cockateil extends EntityAnimal {
    private static final DataParameter<Integer> DW_22 = EntityDataManager.<Integer>createKey(Cockateil.class, DataSerializers.VARINT);

   private BlockPos currentFlightTarget = null;
   public int birdtype;
   private boolean killedByPlayer = false;
   private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "bird1.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "bird2.png");
   private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "bird3.png");
   private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "bird4.png");
   private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "bird5.png");
   private static final ResourceLocation texture6 = new ResourceLocation("orespawn", "bird6.png");
   private int stuck_count = 0;
   private int lastX = 0;
   private int lastZ = 0;
   private int flyup = 0;

   public Cockateil(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.5F);
      this.experienceValue = 2;
      this.isImmuneToFire = false;
      this.isImmuneToFire = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
   }

   public ResourceLocation getTexture() {
      this.birdtype = this.getBirdType();
      switch (this.birdtype) {
         case 0:
            return texture1;
         case 1:
            return texture2;
         case 2:
            return texture3;
         case 3:
            return texture4;
         case 4:
            return texture5;
         case 5:
            return texture6;
         default:
            return null;
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.birdtype = this.rand.nextInt(6);
      this.dataManager.register(DW_22, this.birdtype);
   }

   protected boolean canDespawn() {
      return !this.isNoDespawnRequired();
   }

   public int getBirdType() {
      return this.dataManager.get(DW_22);
   }

   public void setBirdType(int par1) {
      this.dataManager.set(DW_22,  par1);
   }

   protected float getSoundVolume() {
      return 0.55F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.world.isDaytime() && !this.world.isRaining() ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("birds") : null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   public boolean canBePushed() {
      return true;
   }

   public int mygetMaxHealth() {
      return 2;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      Entity e = par1DamageSource.getTrueSource();
      if (e != null && e instanceof EntityPlayer) {
         this.killedByPlayer = true;
      }

      return super.attackEntityFrom(par1DamageSource, par2);
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.currentFlightTarget == null) {
         this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
      } else if (this.posY < this.currentFlightTarget.getY()) {
         this.motionY *= 0.7;
      } else {
         this.motionY *= 0.5;
      }
   }

   public int getAttackStrength(Entity par1Entity) {
      return 1;
   }

   public void setFlyUp() {
      this.flyup = 2;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   protected void updateAITasks() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 35;
      int stayup = 0;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.world.provider.getDimension() == OreSpawn.DimensionID4) {
            stayup = 2;
         }

         if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            this.stuck_count++;
         } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastZ = (int)this.posZ;
         }

         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.stuck_count > 40
            || this.rand.nextInt(250) == 0
            || this.getDistanceSq(currentFlightTarget) < 4.1F) {
            Block bid = Blocks.STONE;

            for (this.stuck_count = 0; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               zdir = this.rand.nextInt(8) + 5 - this.flyup * 2;
               xdir = this.rand.nextInt(8) + 5 - this.flyup * 2;
               if (this.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               this.currentFlightTarget = new BlockPos((int)this.posX + xdir,
                     (int)this.posY + this.rand.nextInt(9 + stayup) - 5 + this.flyup,
                     (int)this.posZ + zdir
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

         double var1 = this.currentFlightTarget.getX() + 0.3 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.3 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.3 - this.motionX) * 0.25;
         this.motionY = this.motionY + (Math.signum(var3) * 0.699999 - this.motionY) * 0.200000001;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.3 - this.motionZ) * 0.25;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.8F;
         this.rotationYaw += var8 / 3.0F;
      }
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public boolean getCanSpawnHere() {
      if (!this.world.isDaytime()) {
         return false;
      } else {
         return this.world.provider.getDimension() == OreSpawn.DimensionID4 ? true : !(this.posY < 50.0);
      }
   }

   protected Item getDropItem() {
      this.birdtype = this.getBirdType();
      return this.birdtype == 5 && this.killedByPlayer && this.world.rand.nextInt(3) == 1 ? OreSpawn.MyRuby : Items.FEATHER;
   }

   public void initCreature() {
   }

   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("BirdType", this.getBirdType());
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.birdtype = par1NBTTagCompound.getInteger("BirdType");
      this.setBirdType(this.birdtype);
   }
}

package com.electrifiedded.orespawn.entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Firefly extends EntityAmbientCreature {
   private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "fireflytexture.png");
   int my_blink = 0;
   int blinker = 0;
   int myspace = 0;
   private BlockPos currentFlightTarget = null;

   public Firefly(World par1World) {
      super(par1World);
      this.my_blink = 20 + this.rand.nextInt(20);
      this.setSize(0.4F, 0.8F);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
   }

   public ResourceLocation getTexture(Firefly a) {
      return texture1;
   }

   protected void entityInit() {
      super.entityInit();
   }

   public float getBlink() {
      return this.blinker < this.my_blink / 2 ? 240.0F : 0.0F;
   }

   protected float getSoundVolume() {
      return 0.0F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return null;
   }

   protected SoundEvent getDeathSound() {
      return null;
   }

   public boolean canBePushed() {
      return true;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   protected void collideWithNearbyEntities() {
   }

   public int mygetMaxHealth() {
      return 1;
   }

   protected Item getDropItem() {
      return Item.getItemFromBlock(OreSpawn.ExtremeTorch);
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

      if (!this.isNoDespawnRequired()) {
         long t = this.world.getWorldTime();
         t %= 24000L;
         if (t <= 11000L) {
            if (this.world.rand.nextInt(500) == 1) {
               this.setDead();
            }
         }
      }
   }

   protected void updateAITasks() {
      int keep_trying = 25;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.rand.nextInt(40) == 0
            || this.getDistanceSq(currentFlightTarget) < 2.0F) {
            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(4) - this.rand.nextInt(4),
                     (int)this.posY + this.rand.nextInt(4) - 2,
                     (int)this.posZ + this.rand.nextInt(4) - this.rand.nextInt(4)
                  );
               bid = this.world
                  .getBlockState(this.currentFlightTarget).getBlock();
            }
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
      Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();
      if (bid != Blocks.AIR) {
         return false;
      } else if (this.world.isDaytime()) {
         return false;
      } else if (this.findBuddies() > 10) {
         return false;
      } else {
         return this.world.provider.getDimension() == OreSpawn.DimensionID4 ? true : !(this.posY < 50.0);
      }
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Firefly.class, this.getEntityBoundingBox().grow(20.0, 8.0, 20.0));
      return var5.size();
   }

   public void initCreature() {
   }

   protected boolean canDespawn() {
      return !this.world.isDaytime() ? false : !this.isNoDespawnRequired();
   }
}

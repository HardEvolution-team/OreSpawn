package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.world.OreSpawnTeleporter;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.SoundEvent;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.OreSpawn;



import java.util.Collections;

import java.util.Iterator;

import java.util.List;

import net.minecraft.block.Block;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.passive.EntityAmbientCreature;

import net.minecraft.entity.passive.EntityHorse;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.init.Blocks;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.server.MinecraftServer;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.ResourceLocation;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class EntityButterfly extends EntityAmbientCreature {

    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(EntityButterfly.class, DataSerializers.VARINT);



   private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "butterfly.png");

   private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "butterfly2.png");

   private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "butterfly3.png");

   private static final ResourceLocation texture4 = new ResourceLocation("orespawn", "butterfly4.png");

   private static final ResourceLocation texture5 = new ResourceLocation("orespawn", "eyemoth.png");

   private static final ResourceLocation texture6 = new ResourceLocation("orespawn", "lunamoth.png");

   private static final ResourceLocation texture7 = new ResourceLocation("orespawn", "darkmoth.png");

   private static final ResourceLocation texture8 = new ResourceLocation("orespawn", "firemoth.png");

   private static final ResourceLocation texture9 = new ResourceLocation("orespawn", "vbutterfly1.png");

   public int butterfly_type = 0;

   private int attack_delay = 0;

   private GenericTargetSorter TargetSorter = null;

   private int force_sync = 25;

   private BlockPos currentFlightTarget = null;



   public EntityButterfly(World par1World) {

      super(par1World);

      this.butterfly_type = OreSpawn.OreSpawnRand.nextInt(4);

      this.setSize(0.4F, 0.4F);

      this.TargetSorter = new GenericTargetSorter(this);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1F);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);

   }



   public ResourceLocation getTexture(EntityButterfly a) {

      if (a instanceof Mothra) {

         return texture5;

      } else if (a instanceof EntityLunaMoth) {

         if (((EntityLunaMoth)a).moth_type == 1) {

            return texture5;

         } else if (((EntityLunaMoth)a).moth_type == 2) {

            return texture7;

         } else {

            return ((EntityLunaMoth)a).moth_type == 3 ? texture8 : texture6;

         }

      } else if (this.butterfly_type == 1) {

         return this.world.provider.getDimension() == OreSpawn.DimensionID4 ? texture9 : texture2;

      } else if (this.butterfly_type == 2) {

         return texture3;

      } else {

         return this.butterfly_type == 3 ? texture4 : texture1;

      }

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, this.butterfly_type);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

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

      return 2;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   protected void updateAITasks() {

      int keep_trying = 25;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.currentFlightTarget == null) {

            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);

         }



         if (this.rand.nextInt(100) != 0

            && !(this.getDistanceSq(currentFlightTarget) < 4.0F)) {

            if (this.rand.nextInt(10) == 0

               && this.world.provider.getDimension() == OreSpawn.DimensionID4

               && this.butterfly_type == 1

               && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {

               EntityLivingBase e = null;

               e = this.findSomethingToAttack();

               if (e != null) {

                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);

                  if (this.getDistanceSq(e) < 6.0) {

                     this.attackEntityAsMob(e);

                  }

               }

            }

         } else {

            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {

               this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),

                     (int)this.posY + this.rand.nextInt(6) - 2,

                     (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7)

                  );

               bid = this.world

                  .getBlockState(this.currentFlightTarget).getBlock();

            }

         }



         double var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;

         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;

         double var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;

         this.motionX = this.motionX + (Math.signum(var1) * 0.5 - this.motionX) * 0.1F;

         this.motionY = this.motionY + (Math.signum(var3) * 0.7F - this.motionY) * 0.1F;

         this.motionZ = this.motionZ + (Math.signum(var5) * 0.5 - this.motionZ) * 0.1F;

         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;

         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);

         this.moveVertical = 0.5F;

         this.rotationYaw += var8;

      }

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      if (OreSpawn.OreSpawnRand.nextInt(2) != 0) {

         return false;

      } else {

         return this.world.getDifficulty() == EnumDifficulty.PEACEFUL ? false : par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);

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

      } else if (par1EntityLiving instanceof EntityPlayer) {

         EntityPlayer p = (EntityPlayer)par1EntityLiving;

         return !p.capabilities.isCreativeMode;

      } else {

         return par1EntityLiving instanceof EntityHorse;

      }

   }



   private EntityLivingBase findSomethingToAttack() {

      List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(8.0, 5.0, 8.0));

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



   public void onUpdate() {

      super.onUpdate();

      this.motionY *= 0.6F;

      this.force_sync--;

      if (this.force_sync < 0) {

         this.force_sync = 25;

         if (this.world.isRemote) {

            this.butterfly_type = this.dataManager.get(DW_20);

         } else {

            this.dataManager.set(DW_20,  this.butterfly_type);

         }

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



   public boolean interact(EntityPlayer par1EntityPlayer) {

      if (par1EntityPlayer == null) {

         return false;

      } else if (!(par1EntityPlayer instanceof EntityPlayerMP)) {

         return false;

      } else {

         ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

         if (var2 != null && var2.getCount() <= 0) {

            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            var2 = null;

         }



         if (var2 != null) {

            return false;

         } else {

            if (par1EntityPlayer.dimension != OreSpawn.DimensionID6) {

               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()

                  .getPlayerList()

                  .transferPlayerToDimension(

                     (EntityPlayerMP)par1EntityPlayer,

                     OreSpawn.DimensionID6,

                     new OreSpawnTeleporter(

                        net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawn.DimensionID6), OreSpawn.DimensionID6, this.world

                     )

                  );

            } else {

               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()

                  .getPlayerList()

                  .transferPlayerToDimension(

                     (EntityPlayerMP)par1EntityPlayer, 0, new OreSpawnTeleporter(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.world)

                  );

            }



            return true;

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

                  if (s != null && s.equalsIgnoreCase("Butterfly")) {

                     this.butterfly_type = 1;

                     return true;

                  }

               }

            }

         }

      }



      Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();

      if (bid != Blocks.AIR) {

         return false;

      } else if (!this.world.isDaytime()) {

         return false;

      } else {

         return this.world.provider.getDimension() == OreSpawn.DimensionID4 ? true : !(this.posY < 50.0);

      }

   }



   public void initCreature() {

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      par1NBTTagCompound.setInteger("ButterflyType", this.butterfly_type);

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.butterfly_type = par1NBTTagCompound.getInteger("ButterflyType");

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}



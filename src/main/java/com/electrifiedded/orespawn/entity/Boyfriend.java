package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.item.armor.ItemOreSpawnArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.util.math.BlockPos;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import net.minecraft.init.Enchantments;

import net.minecraft.init.SoundEvents;

import net.minecraft.util.SoundEvent;



import net.minecraft.block.Block;

import net.minecraft.enchantment.Enchantment;

import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityAgeable;

import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.IRangedAttackMob;

import net.minecraft.entity.SharedMonsterAttributes;

import net.minecraft.entity.ai.EntityAIAttackRangedBow;

import net.minecraft.entity.ai.EntityAILookIdle;

import net.minecraft.entity.ai.EntityAIMoveIndoors;

import net.minecraft.entity.ai.EntityAIOpenDoor;

import net.minecraft.entity.ai.EntityAIPanic;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAITempt;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.monster.EntityCreeper;

import net.minecraft.entity.monster.IMob;

import net.minecraft.entity.passive.EntityTameable;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.entity.projectile.EntityArrow;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemArmor;

import net.minecraft.item.ItemFood;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.potion.Potion;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.text.TextComponentString;

import net.minecraft.util.DamageSource;

import net.minecraft.util.math.MathHelper;

import net.minecraft.util.ResourceLocation;

import net.minecraft.world.World;



public class Boyfriend extends EntityTameable implements IRangedAttackMob {
   @Override public void setSwingingArms(boolean swingingArms) {}
   public int attackTime = 0;
   private int swingCooldown = 0;

    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(Boyfriend.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(Boyfriend.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> DW_22 = EntityDataManager.<Integer>createKey(Boyfriend.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> DW_23 = EntityDataManager.<Integer>createKey(Boyfriend.class, DataSerializers.VARINT);

    private static final DataParameter<Integer> DW_24 = EntityDataManager.<Integer>createKey(Boyfriend.class, DataSerializers.VARINT);



   public int which_guy = 0;

   public int which_wet_guy = 0;

   public int wet_count = 0;

   private int auto_heal = 200;

   private int force_sync = 50;

   private int fight_sound_ticker = 0;

   private int taunt_sound_ticker = 0;

   private int had_target = 0;

   private int voice = 0;

   private float moveSpeed = 0.3F;

   private int voice_enable = 1;

   public int passenger = 0;

   private int is_prince = 0;

   private static final ResourceLocation DryTexture0 = new ResourceLocation("orespawndedsport", "boyfriend0.png");

   private static final ResourceLocation DryTexture1 = new ResourceLocation("orespawndedsport", "boyfriend1.png");

   private static final ResourceLocation DryTexture2 = new ResourceLocation("orespawndedsport", "boyfriend2.png");

   private static final ResourceLocation DryTexture3 = new ResourceLocation("orespawndedsport", "boyfriend3.png");

   private static final ResourceLocation DryTexture4 = new ResourceLocation("orespawndedsport", "boyfriend4.png");

   private static final ResourceLocation DryTexture5 = new ResourceLocation("orespawndedsport", "boyfriend5.png");

   private static final ResourceLocation DryTexture6 = new ResourceLocation("orespawndedsport", "boyfriend6.png");

   private static final ResourceLocation DryTexture7 = new ResourceLocation("orespawndedsport", "boyfriend7.png");

   private static final ResourceLocation DryTexture8 = new ResourceLocation("orespawndedsport", "boyfriend8.png");

   private static final ResourceLocation DryTexture9 = new ResourceLocation("orespawndedsport", "boyfriend9.png");

   private static final ResourceLocation DryTexture10 = new ResourceLocation("orespawndedsport", "boyfriend10.png");

   private static final ResourceLocation DryTexture11 = new ResourceLocation("orespawndedsport", "boyfriend11.png");

   private static final ResourceLocation DryTexture12 = new ResourceLocation("orespawndedsport", "boyfriend12.png");

   private static final ResourceLocation DryTexture13 = new ResourceLocation("orespawndedsport", "boyfriend13.png");

   private static final ResourceLocation DryTexture14 = new ResourceLocation("orespawndedsport", "boyfriend14.png");

   private static final ResourceLocation DryTexture15 = new ResourceLocation("orespawndedsport", "boyfriend15.png");

   private static final ResourceLocation DryTexture16 = new ResourceLocation("orespawndedsport", "boyfriend16.png");

   private static final ResourceLocation DryTexture17 = new ResourceLocation("orespawndedsport", "boyfriend17.png");

   private static final ResourceLocation DryTexture18 = new ResourceLocation("orespawndedsport", "boyfriend18.png");

   private static final ResourceLocation DryTexture19 = new ResourceLocation("orespawndedsport", "boyfriend19.png");

   private static final ResourceLocation DryTexture20 = new ResourceLocation("orespawndedsport", "boyfriend20.png");

   private static final ResourceLocation DryTexture21 = new ResourceLocation("orespawndedsport", "boyfriend21.png");

   private static final ResourceLocation DryTexture22 = new ResourceLocation("orespawndedsport", "boyfriend22.png");

   private static final ResourceLocation DryTexture23 = new ResourceLocation("orespawndedsport", "boyfriend23.png");

   private static final ResourceLocation DryTexture24 = new ResourceLocation("orespawndedsport", "boyfriend24.png");

   private static final ResourceLocation DryTexture25 = new ResourceLocation("orespawndedsport", "boyfriend25.png");

   private static final ResourceLocation DryTexture26 = new ResourceLocation("orespawndedsport", "boyfriend26.png");

   private static final ResourceLocation DryTexture27 = new ResourceLocation("orespawndedsport", "boyfriend27.png");

   private static final ResourceLocation WetTexture0 = new ResourceLocation("orespawndedsport", "swimshorts0.png");

   private static final ResourceLocation WetTexture1 = new ResourceLocation("orespawndedsport", "swimshorts1.png");

   private static final ResourceLocation WetTexture2 = new ResourceLocation("orespawndedsport", "swimshorts2.png");

   private static final ResourceLocation WetTexture3 = new ResourceLocation("orespawndedsport", "swimshorts3.png");

   private static final ResourceLocation WetTexture4 = new ResourceLocation("orespawndedsport", "swimshorts4.png");

   private static final ResourceLocation WetTexture5 = new ResourceLocation("orespawndedsport", "swimshorts5.png");

   private static final ResourceLocation WetTexture6 = new ResourceLocation("orespawndedsport", "swimshorts6.png");

   private static final ResourceLocation WetTexture7 = new ResourceLocation("orespawndedsport", "swimshorts7.png");

   private static final ResourceLocation WetTexture8 = new ResourceLocation("orespawndedsport", "swimshorts8.png");

   private static final ResourceLocation WetTexture9 = new ResourceLocation("orespawndedsport", "swimshorts9.png");

   private static final ResourceLocation WetTexture10 = new ResourceLocation("orespawndedsport", "swimshorts10.png");

   private static final ResourceLocation WetTexture11 = new ResourceLocation("orespawndedsport", "swimshorts11.png");

   private static final ResourceLocation WetTexture12 = new ResourceLocation("orespawndedsport", "swimshorts12.png");

   private static final ResourceLocation WetTexture13 = new ResourceLocation("orespawndedsport", "swimshorts13.png");

   private static final ResourceLocation WetTexture14 = new ResourceLocation("orespawndedsport", "swimshorts14.png");

   private static final ResourceLocation WetTexture15 = new ResourceLocation("orespawndedsport", "swimshorts15.png");

   private static final ResourceLocation WetTexture16 = new ResourceLocation("orespawndedsport", "swimshorts16.png");

   private static final ResourceLocation WetTexture17 = new ResourceLocation("orespawndedsport", "swimshorts17.png");

   private static final ResourceLocation PrinceTexture1 = new ResourceLocation("orespawndedsport", "frogprince.png");

   private static final ResourceLocation PrinceTexture2 = new ResourceLocation("orespawndedsport", "frogprince2.png");



   public Boyfriend(World par1World) {

      super(par1World);

      this.which_guy = this.rand.nextInt(28);

      this.voice = this.rand.nextInt(10);

      this.which_wet_guy = this.rand.nextInt(18);

      this.setSize(0.5F, 1.6F);

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.setSitting(false);

      this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 1.4F, 12.0F, 1.5F));

      this.tasks.addTask(2, new EntityAITempt(this, 1.25, Items.COOKED_BEEF, false));

      // ranged attack handled in combat code

      this.tasks.addTask(5, new EntityAISwimming(this));

      this.tasks.addTask(6, new EntityAIPanic(this, 1.5));

      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));

      this.tasks.addTask(8, new MyEntityAIWander(this, 0.75F));

      this.tasks.addTask(9, new EntityAILookIdle(this));

      this.tasks.addTask(10, new EntityAIOpenDoor(this, true));

      this.tasks.addTask(11, new EntityAIMoveIndoors(this));

      if (OreSpawn.PlayNicely == 0) {

         this.targetTasks.addTask(2, new MyEntityAINearestAttackableTarget(this, EntityCreeper.class, 20.0F, 0, true, true, IMob.MOB_SELECTOR));

      }



      if (OreSpawn.PlayNicely == 0) {

         this.targetTasks.addTask(3, new MyEntityAINearestAttackableTarget(this, EntityLiving.class, 15.0F, 0, true, true, IMob.MOB_SELECTOR));

      }



      if (OreSpawn.PlayNicely == 0) {

         this.targetTasks.addTask(4, new MyEntityAIJealousy(this, Boyfriend.class, 6.0F, 5, true));

      }



      if (OreSpawn.PlayNicely == 0) {

         this.targetTasks.addTask(5, new MyEntityAIJealousy(this, Boyfriend.class, 3.0F, 15, true));

      }



      this.experienceValue = 0;

   }



   protected void entityInit() {

      super.entityInit();

      this.which_guy = this.rand.nextInt(28);

      this.dataManager.register(DW_20, this.which_guy);

      this.wet_count = 0;

      this.which_wet_guy = this.rand.nextInt(18);

      this.dataManager.register(DW_22, this.which_wet_guy);

      this.voice = this.rand.nextInt(10);

      this.dataManager.register(DW_21, this.voice);

      this.dataManager.register(DW_23, this.voice_enable);

      this.dataManager.register(DW_24, this.is_prince);

      this.auto_heal = 200;

      this.force_sync = 50;

      this.fight_sound_ticker = 0;

      this.taunt_sound_ticker = 0;

      this.had_target = 0;

      this.setSitting(false);

   }



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);

   }



   public int getTotalArmorValue() {

      int i = 0;



      for (ItemStack itemstack : java.util.Arrays.asList(new net.minecraft.item.ItemStack[]{this.getHeldItemMainhand(), this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.OFFHAND), this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.HEAD), this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.CHEST), this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS), this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET)})) {

         if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {

            int l = ((ItemArmor)itemstack.getItem()).damageReduceAmount;

            i += l;

         }

      }



      if (i < 8) {

         i = 8;

      }



      if (i > 23) {

         i = 23;

      }



      return i;

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

      this.passenger = 0;

      if (this.isTamed() && !this.isSitting()) {

         Entity e = this.getOwner();

         if (e != null && e instanceof EntityPlayer) {

            EntityPlayer p = (EntityPlayer)e;

            Entity r = e.getRidingEntity();

            if (r != null && r instanceof Elevator) {

               float f = -0.45F;

               this.setPosition(

                  r.posX - f * Math.sin(Math.toRadians(r.rotationYaw)),

                  r.posY,

                  r.posZ + f * Math.cos(Math.toRadians(r.rotationYaw))

               );

               this.rotationYaw = r.rotationYaw;

               this.rotationPitch = r.rotationPitch;

               this.limbSwing = this.limbSwingAmount = 0.0F;

               this.fallDistance = 0.0F;

               this.passenger = 1;

            }

         }

      }

   }



   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

      super.writeEntityToNBT(par1NBTTagCompound);

      par1NBTTagCompound.setInteger("GirlType", this.getTameSkin());

      par1NBTTagCompound.setInteger("WetGirlType", this.getWetTameSkin());

      par1NBTTagCompound.setInteger("GirlVoice", this.dataManager.get(DW_21));

      par1NBTTagCompound.setInteger("GirlVoiceEnable", this.dataManager.get(DW_23));

      par1NBTTagCompound.setInteger("IsPrince", this.dataManager.get(DW_24));

   }



   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

      super.readEntityFromNBT(par1NBTTagCompound);

      this.which_guy = par1NBTTagCompound.getInteger("GirlType");

      this.setTameSkin(this.which_guy);

      this.which_wet_guy = par1NBTTagCompound.getInteger("WetGirlType");

      this.setWetTameSkin(this.which_wet_guy);

      if (this.isTamed() && this.getOwnerId() == null && this.hasCustomName()) {
         try {
            this.setOwnerId(java.util.UUID.fromString(this.getCustomNameTag()));
            this.setCustomNameTag("");
         } catch (IllegalArgumentException ignored) {
         }
      }

      this.voice = par1NBTTagCompound.getInteger("GirlVoice");

      this.dataManager.set(DW_21,  this.voice);

      this.voice_enable = par1NBTTagCompound.getInteger("GirlVoiceEnable");

      this.dataManager.set(DW_23,  this.voice_enable);

      this.is_prince = par1NBTTagCompound.getInteger("IsPrince");

      this.dataManager.set(DW_24,  this.is_prince);

   }



   protected void handleJumpWater() {

      super.handleJumpWater();

      ItemStack stack = this.getCurrentEquippedItem();

      Entity victim = this.getAttackTarget();

      if (OreSpawn.PlayNicely != 0) {

         victim = null;

      }



      if (this.world.rand.nextInt(100) == 1) {

         this.setRevengeTarget(null);

      }



      if (stack != null && !this.isSitting()) {

         if (victim != null) {

            if (victim instanceof EntityLivingBase && this.getHeldItemMainhand() != null) {

               if (this.getDistance(victim) < 4.0F || stack.getItem() == OreSpawn.MyBertha && this.getDistance(victim) < 10.0F) {

                  this.attackTime--;

                  if (this.attackTime <= 0) {

                     this.attackTime = 25;

                     this.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);

                     this.attackTargetEntityWithCurrentItem(victim);

                     this.fight_sound_ticker--;

                     if (this.fight_sound_ticker <= 0) {

                        if (!this.world.isRemote && this.voice_enable != 0) {

                           this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("b_fight"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, this.getSoundPitch());

                        }



                        this.fight_sound_ticker = 3;

                     }



                     this.had_target = 1;

                  }

               } else if (this.getDistance(victim) < 7.0F && stack.getItem() != OreSpawn.MyUltimateBow) {

                  this.taunt_sound_ticker--;

                  if (this.taunt_sound_ticker <= 0) {

                     if (!this.world.isRemote && this.voice_enable != 0) {

                        this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("b_taunt"), net.minecraft.util.SoundCategory.NEUTRAL, 0.5F, this.getSoundPitch());

                     }



                     this.taunt_sound_ticker = 300;

                  }



                  this.getNavigator().tryMoveToEntityLiving(victim, 1.25);

               }

            }

         } else {

            this.fight_sound_ticker = 0;

            this.attackTime = 0;

            if (this.had_target != 0) {

               this.had_target = 0;

               if (!this.world.isRemote && this.voice_enable != 0) {

                  this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("b_woohoo"), net.minecraft.util.SoundCategory.NEUTRAL, 0.4F, this.getSoundPitch());

               }

            }

         }

      }

   }



   public void setPrince(int par1) {

      this.is_prince = par1;

   }



   public ResourceLocation getTexture() {

      if (this.wet_count <= 0) {

         int txture = this.getTameSkin();

         if (this.is_prince == 1) {

            return PrinceTexture1;

         }



         if (this.is_prince == 2) {

            return PrinceTexture2;

         }



         if (txture == 0) {

            return DryTexture0;

         }



         if (txture == 1) {

            return DryTexture1;

         }



         if (txture == 2) {

            return DryTexture2;

         }



         if (txture == 3) {

            return DryTexture3;

         }



         if (txture == 4) {

            return DryTexture4;

         }



         if (txture == 5) {

            return DryTexture5;

         }



         if (txture == 6) {

            return DryTexture6;

         }



         if (txture == 7) {

            return DryTexture7;

         }



         if (txture == 8) {

            return DryTexture8;

         }



         if (txture == 9) {

            return DryTexture9;

         }



         if (txture == 10) {

            return DryTexture10;

         }



         if (txture == 11) {

            return DryTexture11;

         }



         if (txture == 12) {

            return DryTexture12;

         }



         if (txture == 13) {

            return DryTexture13;

         }



         if (txture == 14) {

            return DryTexture14;

         }



         if (txture == 15) {

            return DryTexture15;

         }



         if (txture == 16) {

            return DryTexture16;

         }



         if (txture == 17) {

            return DryTexture17;

         }



         if (txture == 18) {

            return DryTexture18;

         }



         if (txture == 19) {

            return DryTexture19;

         }



         if (txture == 20) {

            return DryTexture20;

         }



         if (txture == 21) {

            return DryTexture21;

         }



         if (txture == 22) {

            return DryTexture22;

         }



         if (txture == 23) {

            return DryTexture23;

         }



         if (txture == 24) {

            return DryTexture24;

         }



         if (txture == 25) {

            return DryTexture25;

         }



         if (txture == 26) {

            return DryTexture26;

         }



         if (txture == 27) {

            return DryTexture27;

         }

      } else {

         int temp = this.getWetTameSkin();

         if (temp == 0) {

            return WetTexture0;

         }



         if (temp == 1) {

            return WetTexture1;

         }



         if (temp == 2) {

            return WetTexture2;

         }



         if (temp == 3) {

            return WetTexture3;

         }



         if (temp == 4) {

            return WetTexture4;

         }



         if (temp == 5) {

            return WetTexture5;

         }



         if (temp == 6) {

            return WetTexture6;

         }



         if (temp == 7) {

            return WetTexture7;

         }



         if (temp == 8) {

            return WetTexture8;

         }



         if (temp == 9) {

            return WetTexture9;

         }



         if (temp == 10) {

            return WetTexture10;

         }



         if (temp == 11) {

            return WetTexture11;

         }



         if (temp == 12) {

            return WetTexture12;

         }



         if (temp == 13) {

            return WetTexture13;

         }



         if (temp == 14) {

            return WetTexture14;

         }



         if (temp == 15) {

            return WetTexture15;

         }



         if (temp == 16) {

            return WetTexture16;

         }



         if (temp == 17) {

            return WetTexture17;

         }

      }



      return null;

   }



   public int getTameSkin() {

      return this.dataManager.get(DW_20);

   }



   public int getVoice() {

      return this.dataManager.get(DW_21);

   }



   public void setTameSkin(int par1) {

      this.dataManager.set(DW_20,  par1);

      this.which_guy = par1;

   }



   public int getWetTameSkin() {

      return this.dataManager.get(DW_22);

   }



   public void setWetTameSkin(int par1) {

      this.dataManager.set(DW_22,  par1);

      this.which_wet_guy = par1;

   }



   public boolean ableToCauseSkullDrop() {

      return true;

   }



   public boolean canBreatheUnderwater() {

      return true;

   }



   @Override
   public void fall(float par1, float damageMultiplier) {

      float i = MathHelper.ceil(par1 - 3.0F);

      if (i > 0.0F) {

         if (i > 3.0F) {

            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.big_fall"), 1.0F, 1.0F);

            i = 3.0F;

         } else {

            this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.small_fall"), 1.0F, 1.0F);

         }



         this.attackEntityFrom(DamageSource.FALL, i);

      }

   }



   public int mygetMaxHealth() {

      return 80;

   }



   public void onLivingUpdate() {

      this.updateArmSwingProgress();

      super.onLivingUpdate();

       if (this.isInWater() || this.isWet()) {

         this.wet_count = 500;

      } else if (this.wet_count > 0) {

         this.wet_count--;

      }



      this.auto_heal--;

      if (this.auto_heal <= 0) {

         if (this.mygetMaxHealth() > this.getBoyfriendHealth()) {

            this.heal(1.0F);

         }



         this.auto_heal = 150;

      }



      this.force_sync--;

      if (this.force_sync <= 0) {

         this.force_sync = 20;

         if (!this.world.isRemote) {

            this.dataManager.set(DW_21,  this.voice);

            this.dataManager.set(DW_23,  this.voice_enable);

            this.dataManager.set(DW_24,  this.is_prince);

            this.setSitting(this.isSitting());

         } else {

            this.voice = this.getVoice();

            this.voice_enable = this.dataManager.get(DW_23);

            this.is_prince = this.dataManager.get(DW_24);

         }

      }

   }



   public int getBoyfriendHealth() {

      return (int)this.getHealth();

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {
      return interact(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND);
   }

   private boolean interact(EntityPlayer par1EntityPlayer, net.minecraft.util.EnumHand hand) {

      ItemStack var2 = par1EntityPlayer.getHeldItem(hand);

      if (var2.isEmpty()) {
         var2 = null;
      }

      if (var2 != null && var2.getCount() <= 0) {

         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

         var2 = null;

      }



      if (var2 != null

         && (var2.getItem() == Items.COOKED_BEEF || var2.getItem() == OreSpawn.MyPeacock)

         && par1EntityPlayer.getDistanceSq(this) < 16.0) {

         if (!this.isTamed()) {

            if (!this.world.isRemote) {

               if (this.world.rand.nextInt(3) == 0) {

                  this.setTamed(true);

                  this.setOwnerId(par1EntityPlayer.getUniqueID());

                  this.playTameEffect(true);

                  this.world.setEntityState(this, (byte)7);

                  this.heal(this.mygetMaxHealth() - this.getHealth());

               } else {

                  this.playTameEffect(false);

                  this.world.setEntityState(this, (byte)6);

               }

            }

         } else if (this.isOwner(par1EntityPlayer)) {

            if (this.world.isRemote) {

               this.playTameEffect(true);

               this.world.setEntityState(this, (byte)7);

            }



            if (this.mygetMaxHealth() > this.getHealth()) {

               this.heal(this.mygetMaxHealth() - this.getHealth());

            }

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == Item.getItemFromBlock(Blocks.DEADBUSH)

         && par1EntityPlayer.getDistanceSq(this) < 16.0

         && this.isOwner(par1EntityPlayer)) {

         if (!this.world.isRemote) {

            this.setTamed(false);

            this.setOwnerId(null);

            this.playTameEffect(false);

            this.world.setEntityState(this, (byte)6);

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == OreSpawn.MyRuby

         && par1EntityPlayer.getDistanceSq(this) < 16.0

         && this.isOwner(par1EntityPlayer)) {

         if (!this.world.isRemote) {

            this.voice_enable = 0;

            this.dataManager.set(DW_23,  this.voice_enable);

            this.playTameEffect(true);

            this.world.setEntityState(this, (byte)7);

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == OreSpawn.MyAmethyst

         && par1EntityPlayer.getDistanceSq(this) < 16.0

         && this.isOwner(par1EntityPlayer)) {

         if (!this.world.isRemote) {

            this.voice_enable = 1;

            this.dataManager.set(DW_23,  this.voice_enable);

            this.playTameEffect(true);

            this.world.setEntityState(this, (byte)7);

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && (var2.getItem() == Items.LEATHER || var2.getItem() == OreSpawn.MyPeacockFeather)

         && par1EntityPlayer.getDistanceSq(this) < 16.0

         && this.isOwner(par1EntityPlayer)) {

         if (!this.world.isRemote) {

            if (this.wet_count <= 0 && !this.isInWater()
               && !this.isInsideOfMaterial(net.minecraft.block.material.Material.WATER)) {

               this.which_guy++;

               if (this.which_guy > 27) {

                  this.which_guy = 0;

               }



               this.setTameSkin(this.which_guy);

               this.world.setEntityState(this, (byte)7);

            } else {

               this.which_wet_guy++;

               if (this.which_wet_guy > 17) {

                  this.which_wet_guy = 0;

               }



               this.setWetTameSkin(this.which_wet_guy);

               this.world.setEntityState(this, (byte)7);

               if (this.isInWater() || this.isInsideOfMaterial(net.minecraft.block.material.Material.WATER)) {

                  this.wet_count = 500;

               }

            }

         }



         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed() && var2 != null && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq(this) < 16.0) {

         if (var2.getItem() instanceof ItemFood) {

            if (!this.world.isRemote) {

               ItemFood var3 = (ItemFood)var2.getItem();

               if (this.mygetMaxHealth() > this.getHealth()) {

                  this.heal(var3.getHealAmount(var2) * 5);

               }



               this.playTameEffect(true);

               this.world.setEntityState(this, (byte)7);

            }



            if (!par1EntityPlayer.capabilities.isCreativeMode) {

               var2.shrink(1);

               if (var2.getCount() <= 0) {

                  par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

               }

            }

         } else {

            if (!this.world.isRemote) {

               this.playTameEffect(true);

               this.world.setEntityState(this, (byte)7);

            }



            ItemStack var3 = this.getCurrentEquippedItem();

            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, var2);

            if (var2.getItem() == Items.DIAMOND) {

               this.setSitting(true);

            } else {

               this.setSitting(false);

            }



            if (var3 != null) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, var3);

            } else {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

               Item itm = var2.getItem();

               if (itm instanceof ItemOreSpawnArmor) {

                  if (itm == OreSpawn.EmeraldHelmet || itm == OreSpawn.AmethystHelmet || itm == OreSpawn.UltimateHelmet) {

                     ItemStack v4 = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                     this.setItemStackToSlot(EntityEquipmentSlot.HEAD, var2);

                     this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, v4);

                  }



                  if (itm == OreSpawn.EmeraldBody || itm == OreSpawn.AmethystBody || itm == OreSpawn.UltimateBody) {

                     ItemStack v4 = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

                     this.setItemStackToSlot(EntityEquipmentSlot.CHEST, var2);

                     this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, v4);

                  }



                  if (itm == OreSpawn.EmeraldLegs || itm == OreSpawn.AmethystLegs || itm == OreSpawn.UltimateLegs) {

                     ItemStack v4 = this.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

                     this.setItemStackToSlot(EntityEquipmentSlot.LEGS, var2);

                     this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, v4);

                  }



                  if (itm == OreSpawn.EmeraldBoots || itm == OreSpawn.AmethystBoots || itm == OreSpawn.UltimateBoots) {

                     ItemStack v4 = this.getItemStackFromSlot(EntityEquipmentSlot.FEET);

                     this.setItemStackToSlot(EntityEquipmentSlot.FEET, var2);

                     this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, v4);

                  }

               }

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK)

         && par1EntityPlayer.getDistanceSq(this) < 16.0) {

         this.setSitting(false);

         this.setTamed(true);

         this.setOwnerId(par1EntityPlayer.getUniqueID());

         this.playTameEffect(true);

         this.world.setEntityState(this, (byte)7);

         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed()

         && var2 != null

         && var2.getItem() == Items.NAME_TAG

         && par1EntityPlayer.getDistanceSq(this) < 16.0

         && this.isOwner(par1EntityPlayer)) {

         this.setCustomNameTag(var2.getDisplayName());

         if (!par1EntityPlayer.capabilities.isCreativeMode) {

            var2.shrink(1);

            if (var2.getCount() <= 0) {

               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);

            }

         }



         return true;

      } else if (this.isTamed() && var2 == null && par1EntityPlayer.getDistanceSq(this) < 16.0 && this.isOwner(par1EntityPlayer)) {

         ItemStack var3x = this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

         int it = 0;

         if (var3x == null) {

            var3x = com.electrifiedded.orespawn.util.MyUtils.getSlot(this, ++it);

         }



         if (var3x == null) {

            var3x = com.electrifiedded.orespawn.util.MyUtils.getSlot(this, ++it);

         }



         if (var3x == null) {

            var3x = com.electrifiedded.orespawn.util.MyUtils.getSlot(this, ++it);

         }



         if (var3x == null) {

            var3x = com.electrifiedded.orespawn.util.MyUtils.getSlot(this, ++it);

         }



         if (var3x != null) {

            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, var3x);

            com.electrifiedded.orespawn.util.MyUtils.setSlot(this, it, net.minecraft.item.ItemStack.EMPTY);

            this.setSitting(false);

            if (!this.world.isRemote) {

               this.world.setEntityState(this, (byte)6);

            }

         } else if (!this.world.isRemote) {

            this.setSitting(false);

            this.playTameEffect(true);

            this.world.setEntityState(this, (byte)7);

            String healthMessage = new String();

            healthMessage = String.format("I have %d health. Thanks for asking!", this.getBoyfriendHealth());

            par1EntityPlayer.sendMessage(new TextComponentString(healthMessage));

         }



         return true;

      } else {

         return super.processInteract(par1EntityPlayer, hand);

      }

   }



   public boolean isWheat(ItemStack par1ItemStack) {

      return par1ItemStack != null && (par1ItemStack.getItem() == Items.COOKED_BEEF || par1ItemStack.getItem() == OreSpawn.MyPeacock);

   }



   protected boolean canDespawn() {

      return false;

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      if (!this.isSitting() && this.voice_enable != 0) {

         if (OreSpawn.bro_mode != 0 && this.world.rand.nextInt(2) == 1) {

            return null;

         } else if (this.world.rand.nextInt(11) == 1) {

            Entity victim = this.getAttackTarget();

            if (victim != null) {

               return null;

            } else if (!this.isInWater() && !this.isNotColliding()) {

               if (this.world.rand.nextInt(4) != 0) {

                  if (this.posY < 60.0) {

                     return null;

                  }



                  if (this.world.isThundering()) {

                     return OreSpawnSoundEvents.get("b_thunder");

                  }



                  if (this.world.isRaining()) {

                     return OreSpawnSoundEvents.get("b_rain");

                  }



                  if (!this.world.isDaytime()

                     && this.world.canSeeSky(new net.minecraft.util.math.BlockPos(this.posX, this.posY, this.posZ))) {

                     if (this.world.rand.nextInt(3) == 0) {

                        return OreSpawnSoundEvents.get("b_dark");

                     }



                     return null;

                  }

               }



               if (this.isTamed()) {

                  if (this.mygetMaxHealth() > this.getHealth()) {

                     return OreSpawnSoundEvents.get("b_hurt");

                  } else {

                     return OreSpawn.bro_mode != 0 ? OreSpawnSoundEvents.get("bb_happy") : OreSpawnSoundEvents.get("b_happy");

                  }

               } else {

                  return null;

               }

            } else {

               return OreSpawnSoundEvents.get("b_water");

            }

         } else {

            return null;

         }

      } else {

         return null;

      }

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      if (this.voice_enable == 0) {

         return null;

      } else {

         return OreSpawn.bro_mode != 0 && this.world.rand.nextInt(2) == 1 ? null : OreSpawnSoundEvents.get("b_ow");

      }

   }



   protected SoundEvent getDeathSound() {

      if (OreSpawn.bro_mode != 0) {

         return null;

      } else {

         return this.isTamed() ? OreSpawnSoundEvents.get("b_death_boyfriend") : OreSpawnSoundEvents.get("b_death_single");

      }

   }



   protected float getSoundVolume() {

      return 0.3F;

   }



   protected Item getDropItem() {

      return Item.getItemFromBlock(Blocks.RED_FLOWER);

   }



   protected void dropFewItems(boolean par1, int par2) {

      int var3 = 0;

      if (this.isTamed()) {

         var3 = this.rand.nextInt(5);

         var3 += 2;



         for (int var4 = 0; var4 < var3; var4++) {

            this.dropItem(Item.getItemFromBlock(Blocks.RED_FLOWER), 1);

         }

      }



      Item v6 = OreSpawn.MyItemGameController;

      var3 = this.world.rand.nextInt(26);

      var3 += 10;



      for (int var4 = 0; var4 < var3; var4++) {

         this.dropItem(v6, 1);

      }



      if (this.isTamed()) {

         ItemStack var5 = this.getCurrentEquippedItem();

         if (var5 != null && var5.getCount() > 0) {

            this.dropItem(var5.getItem(), var5.getCount());

         }



         var5 = this.getItemStackFromSlot(EntityEquipmentSlot.FEET);

         if (var5 != null && var5.getCount() > 0) {

            this.dropItem(var5.getItem(), var5.getCount());

         }



         var5 = this.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

         if (var5 != null && var5.getCount() > 0) {

            this.dropItem(var5.getItem(), var5.getCount());

         }



         var5 = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

         if (var5 != null && var5.getCount() > 0) {

            this.dropItem(var5.getItem(), var5.getCount());

         }



         var5 = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

         if (var5 != null && var5.getCount() > 0) {

            this.dropItem(var5.getItem(), var5.getCount());

         }

      }

   }



   public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving) {

      ItemStack it = null;

      if (!this.isSwingInProgress) {

         it = this.getCurrentEquippedItem();

         if (it != null && it.getItem() == OreSpawn.MyUltimateBow) {

            EntityArrow var8 = new UltimateArrow(this.world, this, par1EntityLiving, 2.0F, 10.0F);

            if (this.world.rand.nextInt(4) == 1) {

               var8.setIsCritical(true);

            }



            int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, it);

            if (var10 > 0) {

               var8.setKnockbackStrength(var10);

            }



            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, it) > 0) {

               var8.setFire(100);

            }



            it.damageItem(1, this);

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.world.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);

            var8.pickupStatus = net.minecraft.entity.projectile.EntityArrow.PickupStatus.ALLOWED;

            this.world.spawnEntity(var8);

         } else {

            Shoes var2 = new Shoes(this.world, this, 6);

            double var3 = par1EntityLiving.posX - this.posX;

            double var5 = par1EntityLiving.posY + par1EntityLiving.getEyeHeight() - 1.1 - var2.posY;

            double var7 = par1EntityLiving.posZ - this.posZ;

            float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;

            var2.shoot(var3, var5 + var9, var7, 1.8F, 4.0F);

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

            this.world.spawnEntity(var2);

         }



         this.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);

      }

   }



   public ItemStack getCurrentEquippedItem() {

      return this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

   }



   public void attackTargetEntityWithCurrentItem(Entity par1Entity) {

      ItemStack stack = this.getCurrentEquippedItem();

      if (stack != null) {

         float var2 = 0.0F;

         if (this.isPotionActive(net.minecraft.init.MobEffects.STRENGTH)) {

            var2 += 3 << this.getActivePotionEffect(net.minecraft.init.MobEffects.STRENGTH).getAmplifier();

         }



         if (this.isPotionActive(net.minecraft.init.MobEffects.WEAKNESS)) {

            var2 -= 2 << this.getActivePotionEffect(net.minecraft.init.MobEffects.WEAKNESS).getAmplifier();

         }



         int var3 = 0;

         float var4 = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();

         if (par1Entity instanceof EntityLiving) {

            var4 += 0.0F;

            var3 += net.minecraft.enchantment.EnchantmentHelper.getKnockbackModifier(this);

         }



         if (this.isSprinting()) {

            var3++;

         }



         if (var2 > 0.0F || var4 > 0.0F) {

            boolean var5 = this.fallDistance > 0.0F

               && !this.onGround

               && !this.isOnLadder()

               && !this.isInWater()

               && !this.isNotColliding()

               && !this.isPotionActive(net.minecraft.init.MobEffects.BLINDNESS)

               && this.getRidingEntity() == null

               && par1Entity instanceof EntityLiving;

            if (var5) {

               var2 += this.world.rand.nextInt((int)var2 / 2 + 2);

            }



            var2 += var4;

            boolean var6 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);

            if (var6 && var3 > 0) {

               par1Entity.addVelocity(

                  -MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F,

                  0.1,

                  MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F

               );

               this.motionX *= 0.6;

               this.motionZ *= 0.6;

               this.setSprinting(false);

            }



            ItemStack var7 = this.getCurrentEquippedItem();

            if (par1Entity instanceof EntityLiving) {

               int var8 = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, var7);

               if (var8 > 0 && var6) {

                  par1Entity.setFire(var8 * 4);

               }

            }

         }

      }

   }



   protected float getSoundPitch() {

      return (this.voice - 5) * 0.02F + 1.0F;

   }



   public EntityAgeable createChild(EntityAgeable var1) {

      return null;

   }



   public void attackEntityWithRangedAttack(EntityLivingBase entityliving, float f) {

      this.attackEntityWithRangedAttack(entityliving);

   }



   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {

      boolean ret = false;

      float p2 = par2;

      if (par2 > 10.0F) {

         p2 = 10.0F;

      }



      if (!par1DamageSource.getDamageType().equals("cactus")) {

         ret = super.attackEntityFrom(par1DamageSource, p2);

      }



      return ret;

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

                  if (s != null && s.equalsIgnoreCase("Boyfriend")) {

                     return true;

                  }

               }

            }

         }

      }



      return super.getCanSpawnHere();

   }


   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return interact(player, hand);
   }

   @Override
   protected net.minecraft.pathfinding.PathNavigate createNavigator(World worldIn) {
      // perf: AI repaths every tick; reuse the path for a few ticks (ArchaicLib)
      return new com.electrifiedded.archaiclib.legacy.entity.ThrottledPathNavigateGround(this, worldIn);
   }
}

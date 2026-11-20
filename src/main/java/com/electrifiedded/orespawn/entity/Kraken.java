package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

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
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

public class Kraken extends EntityMob {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(Kraken.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(Kraken.class, DataSerializers.VARINT);

   private GenericTargetSorter TargetSorter = null;
   private RenderInfo renderdata = new RenderInfo();
   private BlockPos currentFlightTarget = null;
   private EntityLivingBase caught = null;
   private int newtarget = 0;
   private int release = 0;
   private int weather_set = 10;
   private int long_enough = 3600;
   private int call_reinforcements = 0;
   private boolean hit_by_player = false;
   private int straight_down = 1;
   private int hurt_timer = 0;

   public Kraken(World par1World) {
      super(par1World);
      if (OreSpawn.PlayNicely == 0) {
         this.setSize(4.0F, 15.0F);
      } else {
         this.setSize(1.3333334F, 5.0F);
      }

      this.experienceValue = 500;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.TargetSorter = new GenericTargetSorter(this);
      this.renderdata = new RenderInfo();
      this.tasks.addTask(1, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.37F);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.Kraken_stats.attack);
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

   public int mygetMaxHealth() {
      return OreSpawn.Kraken_stats.health;
   }

   public int getKrakenHealth() {
      return (int)this.getHealth();
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

   public int getTotalArmorValue() {
      return OreSpawn.Kraken_stats.defense;
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
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

   public void onUpdate() {
      super.onUpdate();
      if (!this.isDead) {
         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)(this.posY - 10.0), (int)this.posZ);
         } else if (this.posY < this.currentFlightTarget.getY()) {
            this.motionY *= 0.72;
         } else {
            this.motionY *= 0.5;
         }

         if (this.weather_set > 0 && OreSpawn.PlayNicely == 0) {
            this.weather_set--;
            if (this.weather_set == 0 && !this.world.isRemote) {
               WorldInfo worldinfo = this.world.getWorldInfo();
               if (!this.world.isRaining()) {
                  worldinfo.setRainTime(300);
                  worldinfo.setThunderTime(300);
                  worldinfo.setRaining(true);
                  worldinfo.setThundering(true);
               } else {
                  worldinfo.setRainTime(300);
                  worldinfo.setThunderTime(300);
               }

               this.weather_set = 100;
            }
         }
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("LongEnough", this.long_enough);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.long_enough = par1NBTTagCompound.getInteger("LongEnough");
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.rand.nextInt(5) == 0 ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("kraken_living") : null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return null;
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("alo_death");
   }

   protected float getSoundVolume() {
      return 2.0F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   protected Item getDropItem() {
      return Items.QUARTZ;
   }

   private ItemStack dropItemRand(Item index, int par1) {
      EntityItem var3 = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(8) - OreSpawn.OreSpawnRand.nextInt(8),
         this.posY + 1.0,
         this.posZ + OreSpawn.OreSpawnRand.nextInt(8) - OreSpawn.OreSpawnRand.nextInt(8),
         is
      );
      if (var3 != null) {
         this.world.spawnEntity(var3);
      }

      return is;
   }

   protected void dropFewItems(boolean par1, int par2) {
      ItemStack is = null;
      this.dropItemRand(OreSpawn.MyKrakenTooth, 1);
      this.dropItemRand(Items.ITEM_FRAME, 1);
      int var5 = 120 + this.world.rand.nextInt(160);

      for (int var4 = 0; var4 < var5; var4++) {
         this.dropItemRand(Items.DYE, 1);
      }

      int i = 5 + this.world.rand.nextInt(10);

      for (int var9 = 0; var9 < i; var9++) {
         int var3 = this.world.rand.nextInt(53);
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
         }
      }
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
   }

   protected boolean canDespawn() {
      if (this.isNoDespawnRequired()) {
         return false;
      } else if (this.long_enough <= 0) {
         return true;
      } else if (this.posY > 150.0 && this.getHealth() < this.mygetMaxHealth() / 2) {
         return true;
      } else if (this.posY > 180.0 && this.long_enough <= 0) {
         this.setDead();
         return true;
      } else {
         return false;
      }
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.75, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   protected void updateAITasks() {
      int xdir = 1;
      int zdir = 1;
      int keep_trying = 50;
      if (!this.isDead) {
         super.updateAITasks();
         if (this.hurt_timer > 0) {
            this.hurt_timer--;
         }

         if (this.long_enough > 0) {
            this.long_enough--;
         }

         this.dataManager.set(DW_21,  OreSpawn.PlayNicely);
         if (this.world.rand.nextInt(400) == 1 && OreSpawn.PlayNicely == 0) {
            this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX, this.posY - 16.0, this.posZ, false));
         }

         if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
         }

         if (this.newtarget != 0
            || this.rand.nextInt(250) == 1
            || this.getDistanceSq(currentFlightTarget) < 9.1F) {
            this.newtarget = 0;

            int ground_dist;
            for (ground_dist = 0; ground_dist < 31; ground_dist++) {
               Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - ground_dist, (int)this.posZ)).getBlock();
               if (bid != Blocks.AIR) {
                  this.straight_down = 0;
                  break;
               }
            }

            ground_dist = 20 - ground_dist;

            for (Block bid = Blocks.STONE; bid != Blocks.AIR && keep_trying != 0; keep_trying--) {
               zdir = this.world.rand.nextInt(6) + 12;
               xdir = this.world.rand.nextInt(6) + 12;
               if (this.world.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.world.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               if (this.straight_down != 0) {
                  xdir = 0;
                  zdir = 0;
               }

               this.currentFlightTarget = new BlockPos((int)this.posX + xdir, (int)this.posY + ground_dist + this.rand.nextInt(9) - 6, (int)this.posZ + zdir
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

            if (this.long_enough <= 0 || this.posY < 200.0 && this.getHealth() < this.mygetMaxHealth() / 4) {
               this.currentFlightTarget = new BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY() + 30, this.currentFlightTarget.getZ());
               if (this.hit_by_player && this.call_reinforcements == 0 && this.getHealth() < this.mygetMaxHealth() / 8 && this.posY > 130.0) {
                  this.call_reinforcements = 1;

                  for (int i = 0; i < 10; i++) {
                     EntityCreature newent = (EntityCreature)spawnCreature(
                        this.world,
                        "The Kraken",
                        this.posX + this.world.rand.nextInt(10) - this.world.rand.nextInt(10),
                        170.0,
                        this.posZ + this.world.rand.nextInt(10) - this.world.rand.nextInt(10)
                     );
                  }
               }
            }
         } else if (this.caught == null && this.world.rand.nextInt(8) == 1 && OreSpawn.PlayNicely == 0) {
            EntityPlayer target = null;
            target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(25.0, 40.0, 25.0), this);
            if (target != null) {
               if (!target.capabilities.isCreativeMode) {
                  if (this.getEntitySenses().canSee(target)) {
                     this.currentFlightTarget = new BlockPos((int)target.posX, (int)target.posY + 15, (int)target.posZ);
                     this.attackWithSomething(target);
                  }
               } else {
                  target = null;
               }
            }

            if (target == null && this.world.rand.nextInt(2) == 0) {
               EntityLivingBase e = null;
               e = this.findSomethingToAttack();
               if (e != null) {
                  this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY + 15, (int)e.posZ);
                  this.attackWithSomething(e);
               }
            }
         }

         if (this.caught != null) {
            if (!this.caught.isDead) {
               this.currentFlightTarget = new BlockPos((int)this.posX, 200, (int)this.posZ);
               if (this.posY > 190.0) {
                  this.release = 1;
               }

               this.caught.motionX = this.motionX;
               this.caught.motionZ = this.motionZ;
               this.caught.motionY = this.motionY;
               this.caught.posX = this.posX;
               if (this.posY - this.caught.posY > 16.0) {
                  this.caught.motionY += 0.25;
               }

               this.caught.posY = this.posY - 15.0;
               this.caught.posZ = this.posZ;
               this.caught.rotationYaw = this.rotationYaw;
               if (this.world.rand.nextInt(50) == 1) {
                  this.attackEntityAsMob(this.caught);
               }

               if (this.release != 0 || this.world.rand.nextInt(250) == 1) {
                  this.caught = null;
                  this.newtarget = 1;
                  this.release = 0;
                  this.setAttacking(0);
               }
            } else {
               this.caught = null;
               this.newtarget = 1;
               this.release = 0;
               this.setAttacking(0);
            }
         }

         double var1 = this.currentFlightTarget.getX() + 0.3 - this.posX;
         double var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
         double var5 = this.currentFlightTarget.getZ() + 0.3 - this.posZ;
         this.motionX = this.motionX + (Math.signum(var1) * 0.45 - this.motionX) * 0.15;
         this.motionY = this.motionY + (Math.signum(var3) * 0.70999 - this.motionY) * 0.202;
         this.motionZ = this.motionZ + (Math.signum(var5) * 0.45 - this.motionZ) * 0.15;
         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
         float var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
         this.moveVertical = 0.4F;
         if (Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.15) {
            var8 = 0.0F;
         }

         this.rotationYaw += var8 / 5.0F;
         double obstruction_factor = 0.0;
         double dx = 0.0;
         double dz = 0.0;
         int dist = 10;

         for (int k = -20; k < 18; k += 2) {
            for (int i = 1; i < dist; i += 2) {
               dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
               dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
               Block bid = this.world.getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY + k, (int)(this.posZ + dz))).getBlock();
               if (bid != Blocks.AIR) {
                  obstruction_factor += 0.1;
               }
            }
         }

         this.motionY += obstruction_factor * 0.08;
         this.posY += obstruction_factor * 0.08;
         if (this.posY > 256.0 && !this.isNoDespawnRequired()) {
            this.setDead();
         }
      }
   }

   private void attackWithSomething(EntityLivingBase par1) {
      if (this.caught == null) {
         double dist = (this.posX - par1.posX) * (this.posX - par1.posX);
         dist += (this.posZ - par1.posZ) * (this.posZ - par1.posZ);
         dist += (this.posY - par1.posY - 15.0) * (this.posY - par1.posY - 15.0);
         if (dist < 30.0) {
            this.caught = par1;
            this.release = 0;
            this.setAttacking(1);
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
      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else if (par1EntityLiving instanceof EntityPlayer) {
         EntityPlayer p = (EntityPlayer)par1EntityLiving;
         return p.capabilities.isCreativeMode ? false : !p.capabilities.isFlying;
      } else if (!par1EntityLiving.onGround && !par1EntityLiving.isInWater()) {
         return false;
      } else if (par1EntityLiving instanceof EntitySquid) {
         return false;
      } else if (par1EntityLiving instanceof AttackSquid) {
         return false;
      } else if (par1EntityLiving instanceof Kraken) {
         return false;
      } else if (par1EntityLiving instanceof Spyro) {
         return false;
      } else if (par1EntityLiving instanceof Dragon) {
         Dragon c = (Dragon)par1EntityLiving;
         return c.getControllingPassenger() == null;
      } else if (par1EntityLiving instanceof Cephadrome) {
         Cephadrome c = (Cephadrome)par1EntityLiving;
         return c.getControllingPassenger() == null;
      } else if (par1EntityLiving instanceof Leon) {
         Leon c = (Leon)par1EntityLiving;
         return c.getControllingPassenger() == null;
      } else if (par1EntityLiving instanceof ThePrinceTeen) {
         ThePrinceTeen c = (ThePrinceTeen)par1EntityLiving;
         return c.getControllingPassenger() == null;
      } else if (par1EntityLiving instanceof ThePrinceAdult) {
         ThePrinceAdult c = (ThePrinceAdult)par1EntityLiving;
         return c.getControllingPassenger() == null;
      } else if (par1EntityLiving instanceof EntityChicken) {
         return false;
      } else if (par1EntityLiving instanceof Chipmunk) {
         return false;
      } else {
         return par1EntityLiving instanceof StinkBug ? false : !(par1EntityLiving instanceof Mothra);
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(20.0, 40.0, 20.0));
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

   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      Entity e = par1DamageSource.getTrueSource();
      boolean ret = false;
      if (this.currentFlightTarget != null && e != null && e instanceof EntityPlayer && this.getHealth() > this.mygetMaxHealth() / 4) {
         this.hit_by_player = true;
         this.currentFlightTarget = new BlockPos((int)e.posX, (int)e.posY + 15, (int)e.posZ);
      }

      if (this.hurt_timer > 0) {
         return false;
      } else {
         this.hurt_timer = 30;
         ret = super.attackEntityFrom(par1DamageSource, par2);
         if (this.world.rand.nextInt(2) == 1) {
            this.release = 1;
         }

         return ret;
      }
   }

   public final int getAttacking() {
      return this.dataManager.get(DW_20);
   }

   public final void setAttacking(int par1) {
      this.dataManager.set(DW_20,  (byte)par1);
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else {
         for (int k = -1; k < 2; k++) {
            for (int j = -1; j < 1; j++) {
               for (int i = 1; i < 6; i++) {
                  Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                  if (bid != Blocks.AIR && bid != Blocks.TALLGRASS) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}

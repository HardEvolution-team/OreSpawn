package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;

import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.network.datasync.DataSerializers;

import net.minecraft.network.datasync.DataParameter;

import com.electrifiedded.orespawn.OreSpawn;

import com.electrifiedded.orespawn.OreSpawnSoundEvents;

import com.electrifiedded.orespawn.util.MyUtils;

import net.minecraft.init.Enchantments;

import net.minecraft.init.SoundEvents;

import net.minecraft.init.MobEffects;

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

import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

import net.minecraft.entity.ai.EntityAISwimming;

import net.minecraft.entity.ai.EntityAIWatchClosest;

import net.minecraft.entity.item.EntityItem;

import net.minecraft.entity.monster.EntityCreeper;

import net.minecraft.entity.monster.EntityEnderman;

import net.minecraft.entity.monster.EntityMob;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.init.Blocks;

import net.minecraft.init.Items;

import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;

import net.minecraft.potion.Potion;

import net.minecraft.potion.PotionEffect;

import net.minecraft.tileentity.TileEntityMobSpawner;

import net.minecraft.util.DamageSource;

import net.minecraft.world.EnumDifficulty;

import net.minecraft.world.World;



public class EmperorScorpion extends EntityMob {

    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(EmperorScorpion.class, DataSerializers.BYTE);



   private GenericTargetSorter TargetSorter = null;

   private RenderInfo renderdata = new RenderInfo();

   private int hurt_timer = 0;

   private float moveSpeed = 0.35F;



   public EmperorScorpion(World par1World) {

      super(par1World);

      this.setSize(3.5F, 3.0F);

      this.experienceValue = 200;

      this.isImmuneToFire = true;

      this.isImmuneToFire = true;

      this.TargetSorter = new GenericTargetSorter(this);

      this.renderdata = new RenderInfo();

      this.tasks.addTask(0, new EntityAISwimming(this));

      this.tasks.addTask(1, new EntityAIMoveThroughVillage(this, 0.9F, false));

      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 14, 1.0));

      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

      this.tasks.addTask(4, new EntityAILookIdle(this));

      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));

   }



   protected void entityInit() {

      super.entityInit();

      this.dataManager.register(DW_20, (byte)0);

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



   protected void applyEntityAttributes() {

      super.applyEntityAttributes();

      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.EmperorScorpion_stats.attack);

   }



   protected boolean canDespawn() {

      return !this.isNoDespawnRequired();

   }



   public void onUpdate() {

      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);

      super.onUpdate();

   }



   public int mygetMaxHealth() {

      return OreSpawn.EmperorScorpion_stats.health;

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

      return OreSpawn.EmperorScorpion_stats.defense;

   }



   protected boolean ableToCauseSkullDrop() {

      return true;

   }



   public void onLivingUpdate() {

      super.onLivingUpdate();

   }



   protected void jump() {

      super.jump();

      this.motionY += 0.25;

      this.posY += 0.5;

   }



   public int getEmperorScorpionHealth() {

      return (int)this.getHealth();

   }



   @Override



   protected net.minecraft.util.SoundEvent getAmbientSound() {

      return null;

   }



   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {

      return OreSpawnSoundEvents.get("alo_hurt");

   }



   protected SoundEvent getDeathSound() {

      return OreSpawnSoundEvents.get("emperorscorpion_death");

   }



   protected float getSoundVolume() {

      return 1.5F;

   }



   protected float getSoundPitch() {

      return 1.0F;

   }



   protected Item getDropItem() {

      return Items.BEEF;

   }



   private ItemStack dropItemRand(Item index, int par1) {

      EntityItem var3 = null;

      ItemStack is = new ItemStack(index, par1, 0);

      var3 = new EntityItem(

         this.world,

         this.posX + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         this.posY + 1.0,

         this.posZ + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),

         is

      );

      if (var3 != null) {

         this.world.spawnEntity(var3);

      }



      return is;

   }



   protected void dropFewItems(boolean par1, int par2) {

      this.dropItemRand(OreSpawn.MyEmperorScorpionScale, 1);

      this.dropItemRand(Items.ITEM_FRAME, 1);

      int i = 4 + this.world.rand.nextInt(5);



      for (int var4 = 0; var4 < i; var4++) {

         this.dropItemRand(Item.getItemFromBlock(Blocks.OBSIDIAN), 1);

      }



      i = 4 + this.world.rand.nextInt(8);



      for (int var7 = 0; var7 < i; var7++) {

         this.dropItemRand(Items.BEEF, 1);

      }



      i = 1 + this.world.rand.nextInt(5);



      for (int var8 = 0; var8 < i; var8++) {

         int var3 = this.world.rand.nextInt(20);

         switch (var3) {

            case 0: {

               ItemStack is = this.dropItemRand(OreSpawn.MyUltimateSword, 1);

               break;

            }

            case 1:

               ItemStack var21 = this.dropItemRand(Items.DIAMOND, 1);

               break;

            case 2:

               ItemStack var20 = this.dropItemRand(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 1);

               break;

            case 3:

               ItemStack var19 = this.dropItemRand(Items.DIAMOND_SWORD, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var19.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var19.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 4:

               ItemStack var18 = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var18.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var18.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 5:

               ItemStack var17 = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var17.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var17.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var17.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 6:

               ItemStack var16 = this.dropItemRand(Items.DIAMOND_AXE, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var16.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var16.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 7:

               ItemStack var15 = this.dropItemRand(Items.DIAMOND_HOE, 1);

               if (this.world.rand.nextInt(2) == 1) {

                  var15.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var15.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 8:

               ItemStack var14 = this.dropItemRand(Items.DIAMOND_HELMET, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var14.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var14.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));

               }

               break;

            case 9:

               ItemStack var13 = this.dropItemRand(Items.DIAMOND_CHESTPLATE, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var13.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var13.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 10:

               ItemStack var12 = this.dropItemRand(Items.DIAMOND_LEGGINGS, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(6) == 1) {

                  var12.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var12.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 11:

               ItemStack var11 = this.dropItemRand(Items.DIAMOND_BOOTS, 1);

               if (this.world.rand.nextInt(6) == 1) {

                  var11.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));

               }



               if (this.world.rand.nextInt(2) == 1) {

                  var11.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));

               }

               break;

            case 12: {

               ItemStack is = this.dropItemRand(OreSpawn.MyUltimateBow, 1);

            }

         }

      }

   }



   public boolean interact(EntityPlayer par1EntityPlayer) {

      return false;

   }



   public boolean attackEntityAsMob(Entity par1Entity) {

      double ks = 3.0;

      double inair = 0.2;

      int var2 = 6;

      if (!super.attackEntityAsMob(par1Entity)) {

         return false;

      } else {

         if (par1Entity != null && par1Entity instanceof EntityLivingBase) {

            if (this.world.getDifficulty() == EnumDifficulty.EASY) {

               var2 = 8;

               if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {

                  var2 = 10;

               } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {

                  var2 = 12;

               }

            }



            if (this.world.rand.nextInt(3) == 1) {

               ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, var2 * 15, 0));

            }



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

      if (this.hurt_timer > 0) {

         return false;

      } else {

         if (!par1DamageSource.getDamageType().equals("cactus")) {

            ret = super.attackEntityFrom(par1DamageSource, par2);

            this.hurt_timer = 30;

            Entity e = par1DamageSource.getTrueSource();

            if (e != null && e instanceof EntityLiving) {

               this.setAttackTarget((EntityLiving)e);

               this.setAttackTarget((EntityLivingBase)e);

               this.getNavigator().tryMoveToEntityLiving((EntityLiving)e, 1.2);

            }

         }



         return ret;

      }

   }



   protected void updateAITasks() {

      EntityLivingBase e = null;

      if (!this.isDead) {

         super.updateAITasks();

         if (this.hurt_timer > 0) {

            this.hurt_timer--;

         }



         if (this.world.rand.nextInt(4) == 0) {

            e = this.getAttackTarget();

            if (e != null && !e.isEntityAlive()) {

               this.setAttackTarget(null);

               e = null;

            }



            if (this.world.rand.nextInt(100) == 0) {

               this.setAttackTarget(null);

            }



            if (e == null) {

               e = this.findSomethingToAttack();

            }



            if (e != null) {

               this.faceEntity(e, 10.0F, 10.0F);

               if (this.getDistanceSq(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {

                  this.setAttacking(1);

                  if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(6) == 1) {

                     this.attackEntityAsMob(e);

                     if (!this.world.isRemote) {

                        if (this.world.rand.nextInt(3) == 1) {

                           this.world.playSound(null, e.posX, e.posY, e.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("scorpion_attack"), net.minecraft.util.SoundCategory.NEUTRAL, 1.4F, 1.0F);

                        } else {

                           this.world.playSound(null, e.posX, e.posY, e.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("scorpion_living"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F);

                        }

                     }

                  }

               } else {

                  this.getNavigator().tryMoveToEntityLiving(e, 1.2);

               }



               if (this.world.rand.nextInt(20) == 1) {

                  EntityCreature newent = (EntityCreature)spawnCreature(

                     this.world,

                     "Scorpion",

                     (this.posX + e.posX) / 2.0 + this.world.rand.nextInt(5) - this.world.rand.nextInt(5),

                     (this.posY + e.posY) / 2.0 + 1.01,

                     (this.posZ + e.posZ) / 2.0 + this.world.rand.nextInt(5) - this.world.rand.nextInt(5)

                  );

               }

            } else {

               this.setAttacking(0);

            }

         }



         if (this.world.rand.nextInt(100) == 1 && this.getHealth() < this.mygetMaxHealth()) {

            this.heal(2.0F);

         }

      }

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



   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {

      if (par1EntityLiving == null) {

         return false;

      } else if (par1EntityLiving == this) {

         return false;

      } else if (!par1EntityLiving.isEntityAlive()) {

         return false;

      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {

         return false;

      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {

         return false;

      } else if (par1EntityLiving instanceof EntityEnderman) {

         return false;

      } else if (par1EntityLiving instanceof EnderKnight) {

         return false;

      } else if (par1EntityLiving instanceof EnderReaper) {

         return false;

      } else if (par1EntityLiving instanceof EntityCreeper) {

         return false;

      } else if (par1EntityLiving instanceof Scorpion) {

         return false;

      } else if (par1EntityLiving instanceof EmperorScorpion) {

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



   private EntityLivingBase findSomethingToAttack() {

      if (OreSpawn.PlayNicely != 0) {

         return null;

      } else {

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(24.0, 6.0, 24.0));

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



   public final int getAttacking() {

      return this.dataManager.get(DW_20);

   }



   public final void setAttacking(int par1) {

      this.dataManager.set(DW_20,  (byte)par1);

   }



   public boolean getCanSpawnHere() {

      for (int k = -2; k < 2; k++) {

         for (int j = -2; j < 2; j++) {

            for (int i = 2; i < 5; i++) {

               Block bid = this.world.getBlockState(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();

               if (bid == Blocks.MOB_SPAWNER) {

                  TileEntityMobSpawner tileentitymobspawner = null;

                  tileentitymobspawner = (TileEntityMobSpawner)this.world

                     .getTileEntity(new BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));

                  String s = MyUtils.spawnerName(tileentitymobspawner);

                  if (s != null && s.equalsIgnoreCase("Emperor Scorpion")) {

                     return true;

                  }

               }



               if (bid != Blocks.AIR) {

                  return false;

               }

            }

         }

      }



      if (!this.isValidLightLevel()) {

         return false;

      } else if (this.world.isDaytime()) {

         return false;

      } else if (this.posY < 50.0) {

         return false;

      } else {

         EmperorScorpion target = null;

         target = (EmperorScorpion)this.world.findNearestEntityWithinAABB(EmperorScorpion.class, this.getEntityBoundingBox().grow(20.0, 6.0, 20.0), this);

         return target == null;

      }

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

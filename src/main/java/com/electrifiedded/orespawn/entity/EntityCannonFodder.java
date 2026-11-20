package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCannonFodder extends EntityTameable {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(EntityCannonFodder.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(EntityCannonFodder.class, DataSerializers.VARINT);

   String name_one = null;
   String name_two = null;
   private int is_activated = 0;
   private int hat_color = 0;
   private int syncer = 0;
   private int px = 0;
   private int pz = 0;
   private int py = 0;
   private GenericTargetSorter LocalTargetSorter = null;

   public EntityCannonFodder(World par1World) {
      super(par1World);
      this.LocalTargetSorter = new GenericTargetSorter(this);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, 0);
      this.dataManager.register(DW_21, 0);
   }

   public void onUpdate() {
      super.onUpdate();
      this.syncer++;
      if (this.syncer > 5) {
         if (this.world.isRemote) {
            this.is_activated = this.dataManager.get(DW_20);
            this.hat_color = this.dataManager.get(DW_21);
         } else {
            this.dataManager.set(DW_20,  this.is_activated);
            this.dataManager.set(DW_21,  this.hat_color);
         }

         this.syncer = 0;
      }
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return interactBase(par1EntityPlayer);
   }

   private boolean interactBase(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if (var2 != null && var2.getCount() <= 0) {
         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
         var2 = null;
      }

       if (super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND)) {
         return true;
      } else {
         if (this.name_one != null && this.isTamed()) {
            if (this.name_one.equals(par1EntityPlayer.getUniqueID().toString())) {
               if (this.name_two == null) {
                  this.name_two = this.name_one;
                  this.name_one = par1EntityPlayer.getUniqueID().toString();
                  this.setCustomNameTag(this.name_one);
                  this.is_activated = 2;
               }
            } else if (this.name_two != null) {
               if (!this.name_two.equals(par1EntityPlayer.getUniqueID().toString())) {
                  return true;
               }

               this.name_two = this.name_one;
               this.name_one = par1EntityPlayer.getUniqueID().toString();
               this.setCustomNameTag(this.name_one);
               this.is_activated = 2;
            } else {
               this.name_two = this.name_one;
               this.name_one = par1EntityPlayer.getUniqueID().toString();
               this.setCustomNameTag(this.name_one);
               this.is_activated = 2;
            }
         }

         if (var2 != null && var2.getItem() == Items.CARROT && par1EntityPlayer.getDistanceSq(this) < 16.0) {
            this.hat_color = 1;
            if (this.name_one == null) {
               this.name_one = par1EntityPlayer.getUniqueID().toString();
            }

            if (this.is_activated == 0) {
               this.is_activated = 1;
            }

            this.setTamed(true);
            this.setCustomNameTag(this.name_one);
            this.playTameEffect(true);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.enablePersistence();
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
               var2.shrink(1);
               if (var2.getCount() <= 0) {
                  par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
               }
            }

            return true;
         } else if (var2 != null && var2.getItem() == Items.POTATO && par1EntityPlayer.getDistanceSq(this) < 16.0) {
            this.hat_color = 3;
            if (this.name_one == null) {
               this.name_one = par1EntityPlayer.getUniqueID().toString();
            }

            if (this.is_activated == 0) {
               this.is_activated = 1;
            }

            this.setTamed(true);
            this.setCustomNameTag(this.name_one);
            this.playTameEffect(true);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.enablePersistence();
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
               var2.shrink(1);
               if (var2.getCount() <= 0) {
                  par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
               }
            }

            return true;
         } else if (var2 != null && var2.getItem() == OreSpawn.MyQuinoa && par1EntityPlayer.getDistanceSq(this) < 16.0) {
            this.hat_color = 2;
            if (this.name_one == null) {
               this.name_one = par1EntityPlayer.getUniqueID().toString();
            }

            if (this.is_activated == 0) {
               this.is_activated = 1;
            }

            this.setTamed(true);
            this.setCustomNameTag(this.name_one);
            this.playTameEffect(true);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.enablePersistence();
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
               var2.shrink(1);
               if (var2.getCount() <= 0) {
                  par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
               }
            }

            return true;
         } else if (var2 != null && this.is_activated == 2 && var2.getItem() == OreSpawn.MyCornCob && par1EntityPlayer.getDistanceSq(this) < 16.0) {
            String myname = "Ostrich";
            if (this instanceof Lizard) {
               myname = "Lizard";
            }

            if (this instanceof Chipmunk) {
               myname = "Chipmunk";
            }

            if (this instanceof VelocityRaptor) {
               myname = "Velocity Raptor";
            }

            if (!this.world.isRemote) {
               Entity newent = spawnCreature(
                  this.world,
                  myname,
                  this.posX + this.world.rand.nextFloat(),
                  this.posY + 0.01,
                  this.posZ + this.world.rand.nextFloat()
               );
               if (newent != null) {
                  EntityCannonFodder cf = (EntityCannonFodder)newent;
                  cf.setCustomNameTag(this.getCustomNameTag());
                  cf.setTamed(true);
                  cf.setStuff(this.hat_color, this.is_activated, this.name_one, this.name_two);
               }
            }

            this.playTameEffect(true);
            this.world.playSound(null, par1EntityPlayer.posX, par1EntityPlayer.posY, par1EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 2.0F);
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
               var2.shrink(1);
               if (var2.getCount() <= 0) {
                  par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
               }
            }

            return true;
         } else if (this.is_activated == 2 && par1EntityPlayer.getDistanceSq(this) < 16.0) {
            if (this.isSitting()) {
               this.setSitting(false);
               this.playTameEffect(true);
            } else {
               this.setSitting(true);
               this.playTameEffect(false);
               this.px = (int)this.posX;
               this.py = (int)this.posY;
               this.pz = (int)this.posZ;
            }

            return true;
         } else {
            return false;
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

   public void setStuff(int hc, int ia, String s1, String s2) {
      this.hat_color = hc;
      this.is_activated = ia;
      this.name_one = s1;
      this.name_two = s2;
      this.enablePersistence();
   }

   public int getHatColor() {
      return this.hat_color;
   }

   public int get_is_activated() {
      return this.is_activated;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      if (this.name_one == null) {
         par1NBTTagCompound.setString("NameOne", "");
      } else {
         par1NBTTagCompound.setString("NameOne", this.name_one);
      }

      if (this.name_two == null) {
         par1NBTTagCompound.setString("NameTwo", "");
      } else {
         par1NBTTagCompound.setString("NameTwo", this.name_two);
      }

      par1NBTTagCompound.setInteger("IsActivated", this.is_activated);
      par1NBTTagCompound.setInteger("HatColor", this.hat_color);
      par1NBTTagCompound.setInteger("PatrolX", this.px);
      par1NBTTagCompound.setInteger("PatrolY", this.py);
      par1NBTTagCompound.setInteger("PatrolZ", this.pz);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.name_one = par1NBTTagCompound.getString("NameOne");
      if (this.name_one != null && this.name_one.equals("")) {
         this.name_one = null;
      }

      this.name_two = par1NBTTagCompound.getString("NameTwo");
      if (this.name_two != null && this.name_two.equals("")) {
         this.name_two = null;
      }

      this.is_activated = par1NBTTagCompound.getInteger("IsActivated");
      this.hat_color = par1NBTTagCompound.getInteger("HatColor");
      this.px = par1NBTTagCompound.getInteger("PatrolX");
      this.py = par1NBTTagCompound.getInteger("PatrolY");
      this.pz = par1NBTTagCompound.getInteger("PatrolZ");
      if (this.name_one != null) {
         this.setTamed(true);
         this.setCustomNameTag(this.name_one);
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
      } else {
         if (this.isSitting()) {
            double dx = this.px - par1EntityLiving.posX;
            double dy = this.py - par1EntityLiving.posY;
            double dz = this.pz - par1EntityLiving.posZ;
            if (dx * dx + dy * dy + dz * dz > 144.0) {
               return false;
            }
         }

         if (par1EntityLiving instanceof EntityMob) {
            return true;
         } else if (par1EntityLiving instanceof EntityCannonFodder) {
            EntityCannonFodder cf = (EntityCannonFodder)par1EntityLiving;
            int i = cf.getHatColor();
            return i != 0 && i != this.hat_color;
         } else if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
               return false;
            } else {
               return this.name_one != null && this.name_one.equals(p.getUniqueID().toString())
                  ? false
                  : this.name_two == null || !this.name_two.equals(p.getUniqueID().toString());
            }
         } else {
            return false;
         }
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 4.0, 10.0));
      Collections.sort(var5, this.LocalTargetSorter);
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

   public int getTotalArmorValue() {
      return this.is_activated == 2 ? 3 : 0;
   }

   public void attackEntityAsFodder(Entity par1Entity, float f) {
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
   }

   protected void updateAITasks() {
      if (!this.isDead) {
         super.updateAITasks();
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (this.is_activated == 2) {
            int pfreq = 5;
            int sfreq = 7;
            float dm = 4.0F;
            if (this instanceof Chipmunk) {
               dm = 3.0F;
               sfreq = 6;
            }

            if (this instanceof Lizard) {
               dm = 6.0F;
               sfreq = 8;
            }

            if (this instanceof VelocityRaptor) {
               sfreq = 6;
               pfreq = 4;
            }

            if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(pfreq) == 1) {
               EntityLivingBase e = this.findSomethingToAttack();
               if (e != null) {
                  this.getNavigator().tryMoveToEntityLiving(e, 1.25);
                  if (this.getDistanceSq(e) < 9.0 && (this.rand.nextInt(sfreq + 1) == 0 || this.rand.nextInt(sfreq) == 1)) {
                     this.attackEntityAsFodder(e, dm);
                  }
               } else if (this.isSitting()) {
                  this.getNavigator().tryMoveToXYZ(this.px, this.py, this.pz, 0.65F);
               }
            }

            if (this.world.rand.nextInt(250) == 1) {
               this.heal(1.0F);
            }
         }
      }
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return null;
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interactBase(player);
   }
}

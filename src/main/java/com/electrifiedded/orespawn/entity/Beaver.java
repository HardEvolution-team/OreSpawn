package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Beaver extends EntityAnimal {
   private float moveSpeed = 0.15F;
   private GenericTargetSorter TargetSorter = null;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public Beaver(World par1World) {
      super(par1World);
      this.setSize(0.6F, 0.8F);
      this.moveSpeed = 0.2F;
      this.isImmuneToFire = true;
      this.experienceValue = 5;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.5));
      this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(5, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.0, 1.5));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(7, new MyEntityAIWanderALot(this, 10, 1.0));
      this.tasks.addTask(8, new EntityAILookIdle(this));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public boolean isWood(Block bid) {
       Item item = Item.getItemFromBlock(bid);
       if (item != Items.AIR) {
          ItemStack stack = new ItemStack(item, 1, net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE);
          if (!stack.isEmpty()) {
             int[] ids = net.minecraftforge.oredict.OreDictionary.getOreIDs(stack);
             for (int id : ids) if ("logWood".equals(net.minecraftforge.oredict.OreDictionary.getOreName(id))) return true;
          }
       }
       return bid == OreSpawn.MyDT || bid == OreSpawn.MySkyTreeLog
          || bid == Blocks.OAK_FENCE || bid == Blocks.OAK_FENCE_GATE || bid == Blocks.STANDING_SIGN;
   }

   private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
      int found = 0;

      for (int i = -dy; i <= dy; i++) {
         for (int j = -dz; j <= dz; j++) {
            Block bid = this.world.getBlockState(new BlockPos(x + dx, y + i, z + j)).getBlock();
            if (this.isWood(bid)) {
               int d = dx * dx + j * j + i * i;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + dx;
                  this.ty = y + i;
                  this.tz = z + j;
                  found++;
               }
            }

            bid = this.world.getBlockState(new BlockPos(x - dx, y + i, z + j)).getBlock();
            if (this.isWood(bid)) {
               int d = dx * dx + j * j + i * i;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x - dx;
                  this.ty = y + i;
                  this.tz = z + j;
                  found++;
               }
            }
         }
      }

      for (int var12 = -dx; var12 <= dx; var12++) {
         for (int j = -dz; j <= dz; j++) {
            Block bidx = this.world.getBlockState(new BlockPos(x + var12, y + dy, z + j)).getBlock();
            if (this.isWood(bidx)) {
               int d = dy * dy + j * j + var12 * var12;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var12;
                  this.ty = y + dy;
                  this.tz = z + j;
                  found++;
               }
            }

            bidx = this.world.getBlockState(new BlockPos(x + var12, y - dy, z + j)).getBlock();
            if (this.isWood(bidx)) {
               int d = dy * dy + j * j + var12 * var12;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var12;
                  this.ty = y - dy;
                  this.tz = z + j;
                  found++;
               }
            }
         }
      }

      for (int var13 = -dx; var13 <= dx; var13++) {
         for (int j = -dy; j <= dy; j++) {
            Block bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z + dz)).getBlock();
            if (this.isWood(bidxx)) {
               int d = dz * dz + j * j + var13 * var13;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var13;
                  this.ty = y + j;
                  this.tz = z + dz;
                  found++;
               }
            }

            bidxx = this.world.getBlockState(new BlockPos(x + var13, y + j, z - dz)).getBlock();
            if (this.isWood(bidxx)) {
               int d = dz * dz + j * j + var13 * var13;
               if (d < this.closest) {
                  this.closest = d;
                  this.tx = x + var13;
                  this.ty = y + j;
                  this.tz = z - dz;
                  found++;
               }
            }
         }
      }

      return found != 0;
   }

   private ItemStack dropItemRand(Item index, int par1) {
      EntityItem var3 = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),
         this.posY + 4.0 + this.world.rand.nextInt(4),
         this.posZ + OreSpawn.OreSpawnRand.nextInt(4) - OreSpawn.OreSpawnRand.nextInt(4),
         is
      );
      if (var3 != null) {
         this.world.spawnEntity(var3);
      }

      return is;
   }

   public void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
      int var7x = 1;
      if (recursion <= 200) {
         for (int var9 = -var7x; var9 <= var7x; var9++) {
            for (int var10 = -var7x; var10 <= var7x; var10++) {
               for (int var11 = -var7x; var11 <= var7x; var11++) {
                  if ((var9 != 0 || var10 != 0 || var11 != 0)
                     && (x + var9 != xf || y + var10 != yf || z + var11 != zf)
                     && (
                        recursion <= 0
                           || x + var9 < xf - var7x
                           || x + var9 > xf + var7x
                           || y + var10 < yf - var7x
                           || y + var10 > yf + var7x
                           || z + var11 < zf - var7x
                           || z + var11 > zf + var7x
                     )) {
                      BlockPos target = new BlockPos(x + var9, y + var10, z + var11);
                      IBlockState state = world.getBlockState(target);
                      Block var12 = state.getBlock();
                      if (this.isWood(var12)) {
                         world.setBlockToAir(target);
                         for (ItemStack drop : var12.getDrops(world, target, state, 0)) {
                            EntityItem item = new EntityItem(world, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, drop);
                            world.spawnEntity(item);
                         }
                        this.breakRecursor(world, x + var9, y + var10, z + var11, x, y, z, recursion + 1);
                     }
                  }
               }
            }
         }
      }
   }

   protected void updateAITasks() {
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if ((
               this.world.rand.nextInt(30) == 0 && this.getBeaverHealth() < this.mygetMaxHealth()
                  || this.world.rand.nextInt(350) == 1
            )
            && OreSpawn.PlayNicely == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 11; i++) {
               int j = i;
               if (i > 2) {
                  j = 2;
               }

               if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) {
                  break;
               }

               if (i >= 6) {
                  i++;
               }
            }

            int var4 = 0;
            if (this.closest < 99999) {
               this.getNavigator().tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);
               if (this.closest < 12) {
                  if (this.world.getGameRules().getBoolean("mobGriefing")) {
                      BlockPos target = new BlockPos(this.tx, this.ty, this.tz);
                      IBlockState state = this.world.getBlockState(target);
                      this.world.setBlockToAir(target);
                      for (ItemStack drop : state.getBlock().getDrops(this.world, target, state, 0)) {
                         this.world.spawnEntity(new EntityItem(this.world, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, drop));
                      }
                      this.breakRecursor(this.world, this.tx, this.ty, this.tz, this.tx, this.ty, this.tz, var4);
                  }

                  this.heal(1.0F);
                  this.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("chainsaw"), 1.0F, this.world.rand.nextFloat() * 0.2F + 0.9F);
               }
            }
         }

         if (this.world.rand.nextInt(200) == 1) {
            Beaver buddy = this.findBuddy();
            if (buddy != null) {
               this.getNavigator().tryMoveToXYZ(buddy.posX, buddy.posY, buddy.posZ, 0.5);
            }
         }

         super.updateAITasks();
       }
    }

   private Beaver findBuddy() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Beaver.class, this.getEntityBoundingBox().grow(16.0, 6.0, 16.0));
      Collections.sort(var5, this.TargetSorter);
      Iterator var2 = var5.iterator();
      Entity var3 = null;
      Beaver var4 = null;
      if (var2.hasNext()) {
         var3 = (Entity)var2.next();
         return (Beaver)var3;
      } else {
         return null;
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public int mygetMaxHealth() {
      return 15;
   }

   public int getBeaverHealth() {
      return (int)this.getHealth();
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("scorpion_hit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("cryo_death");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Items.PORKCHOP;
   }

   protected float getSoundPitch() {
      return this.isChild()
         ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.5F
         : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F;
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else if (this.posY > 100.0) {
         return false;
      } else {
         Block bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY - 1, (int)this.posZ)).getBlock();
         return bid == Blocks.DIRT || bid == Blocks.GRASS || bid == Blocks.TALLGRASS || bid == Blocks.LEAVES;
      }
   }

   protected boolean canDespawn() {
      return false;
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public Beaver spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new Beaver(this.world);
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;
   }
}

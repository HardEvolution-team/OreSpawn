package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.world.OreSpawnTeleporter;
import net.minecraft.util.ResourceLocation;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class Termite extends EntityAnt {
   int attack_delay = 20;
   private int closest = 99999;
   private int tx = 0;
   private int ty = 0;
   private int tz = 0;

   public Termite(World par1World) {
      super(par1World);
      this.setSize(0.2F, 0.2F);
      this.moveSpeed = 0.2F;
      this.experienceValue = 1;
      this.tasks.addTask(0, new EntityAIPanic(this, 1.4F));
      this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0, false));
      this.tasks.addTask(2, new MyEntityAIWanderALot(this, 8, 1.0));
      if (OreSpawn.PlayNicely == 0) {
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 6, true, false, null));
      }
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
   }

   @Override
   public int mygetMaxHealth() {
      return 5;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      if (OreSpawn.OreSpawnRand.nextInt(15) != 0) {
         return false;
      } else {
         return this.world.getDifficulty() == EnumDifficulty.PEACEFUL ? false : par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
      }
   }

   @Override
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
            par1EntityPlayer.sendMessage(new TextComponentString("Empty your hand!"));
            return false;
         } else {
            if (par1EntityPlayer.dimension != OreSpawn.DimensionID5) {
               for (int i = 0; i < par1EntityPlayer.inventory.mainInventory.size(); i++) {
                  if (par1EntityPlayer.inventory.mainInventory.get(i) != null) {
                     par1EntityPlayer.sendMessage(new TextComponentString("Empty your inventory!"));
                     return false;
                  }
               }

               for (int var4 = 0; var4 < par1EntityPlayer.inventory.armorInventory.size(); var4++) {
                  if (par1EntityPlayer.inventory.armorInventory.get(var4) != null) {
                     par1EntityPlayer.sendMessage(new TextComponentString("Take off your armor!"));
                     return false;
                  }
               }

               net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance()
                  .getPlayerList()
                  .transferPlayerToDimension(
                     (EntityPlayerMP)par1EntityPlayer,
                     OreSpawn.DimensionID5,
                     new OreSpawnTeleporter(
                        net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawn.DimensionID5), OreSpawn.DimensionID5, this.world
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

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.isDead) {
         if (this.attack_delay > 0) {
            this.attack_delay--;
         }

         if (this.attack_delay <= 0) {
            this.attack_delay = 20;
            if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               EntityLivingBase e = this.world.getClosestPlayerToEntity(this, 1.5);
               if (e != null) {
                  this.attackEntityAsMob(e);
               }
            }
         }
      }
   }

   public boolean isWood(Block bid) {
      if (bid == Blocks.OAK_FENCE || bid == Blocks.OAK_FENCE_GATE || bid == Blocks.PLANKS || bid == Blocks.WOODEN_SLAB) {
         return true;
      } else if (bid == Blocks.DOUBLE_WOODEN_SLAB || bid == Blocks.BED || bid == Blocks.CRAFTING_TABLE) {
         return true;
      } else if (bid == Blocks.STANDING_SIGN || bid == Blocks.BOOKSHELF || bid == Blocks.OAK_DOOR || bid == Blocks.WOODEN_PRESSURE_PLATE) {
         return true;
      } else {
         return bid == Blocks.BIRCH_STAIRS || bid == Blocks.OAK_STAIRS || bid == Blocks.JUNGLE_STAIRS || bid == Blocks.SPRUCE_STAIRS
            ? true
            : bid == OreSpawn.CrystalPlanksBlock;
      }
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

   @Override
   public void handleJumpWater() {
      if (!this.isDead) {
         if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
         }

         if (this.world.rand.nextInt(200) == 1 && OreSpawn.PlayNicely == 0) {
            this.closest = 99999;
            this.tx = this.ty = this.tz = 0;

            for (int i = 1; i < 8; i++) {
               int j = i;
               if (i > 4) {
                  j = 4;
               }

               if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) {
                  break;
               }

               if (i >= 5) {
                  i++;
               }
            }

            boolean var3 = false;
            if (this.closest < 99999) {
               this.getNavigator().tryMoveToXYZ(this.tx, this.ty, this.tz, 1.0);
               if (this.closest < 6) {
                  if (this.world.rand.nextInt(3) != 0) {
                     if (this.world.getGameRules().getBoolean("mobGriefing")) {
                        this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.DIRT.getStateFromMeta(0), 2);
                     }

                     if (this.findBuddies() < 10) {
                        spawnCreature(this.world, "Termite", this.posX + 0.1F, this.posY + 0.1F, this.posZ + 0.1F);
                     }
                  } else {
                     if (this.world.getGameRules().getBoolean("mobGriefing")) {
                        this.world.setBlockState(new BlockPos(this.tx, this.ty, this.tz), Blocks.AIR.getStateFromMeta(0), 2);
                     }

                     if (this.findBuddies() < 10) {
                        spawnCreature(this.world, "Termite", this.tx + 0.1F, this.ty + 0.1F, this.tz + 0.1F);
                     }
                  }

                  this.heal(1.0F);
               }
            }
         }

         super.handleJumpWater();
      }
   }

   private int findBuddies() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(Termite.class, this.getEntityBoundingBox().grow(3.0, 3.0, 3.0));
      return var5.size();
   }

   public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = EntityList.createEntityByIDFromName(new ResourceLocation(par1), par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
      }

      return var8;
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }
}

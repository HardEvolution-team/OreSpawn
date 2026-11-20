package com.electrifiedded.orespawn.entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TheKing extends EntityMob {
    private static final DataParameter<Byte> DW_20 = EntityDataManager.<Byte>createKey(TheKing.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> DW_21 = EntityDataManager.<Integer>createKey(TheKing.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DW_22 = EntityDataManager.<Integer>createKey(TheKing.class, DataSerializers.VARINT);

   private BlockPos currentFlightTarget = null;
   private GenericTargetSorter TargetSorter = null;
   private EntityLivingBase rt = null;
   private double attdam = 250.0;
   private int hurt_timer = 0;
   private int homex = 0;
   private int homez = 0;
   private int stream_count = 0;
   private int stream_count_l = 0;
   private int stream_count_i = 0;
   private int ticker = 0;
   private int player_hit_count = 0;
   private int backoff_timer = 0;
   private int guard_mode = 0;
   private volatile int head_found = 0;
   private int wing_sound = 0;
   private int large_unknown_detected = 0;
   private int isEnd = 0;
   private int endCounter = 0;

   public TheKing(World par1World) {
      super(par1World);
      this.ignoreFrustumCheck = true;
      if (OreSpawn.PlayNicely == 0) {
         this.setSize(22.0F, 24.0F);
      } else {
         this.setSize(5.5F, 6.0F);
      }

      this.experienceValue = 25000;
      this.isImmuneToFire = true;
      this.isImmuneToFire = true;
      this.noClip = true;
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.62F);
      this.attdam = OreSpawn.TheKing_stats.attack;
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.attdam);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(DW_20, (byte)0);
      this.dataManager.register(DW_21, OreSpawn.PlayNicely);
      this.dataManager.register(DW_22, this.isEnd);
   }

   public int getPlayNicely() {
      return this.dataManager.get(DW_21);
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double par1) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderVec3D(Vec3d par1Vec3) {
      return true;
   }

   protected boolean canDespawn() {
      return false;
   }

   public final int getAttacking() {
      return this.dataManager.get(DW_20);
   }

   public final void setAttacking(int par1) {
      this.dataManager.set(DW_20,  (byte)par1);
   }

   protected float getSoundVolume() {
      return 1.35F;
   }

   protected float getSoundPitch() {
      return 1.0F;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return OreSpawnSoundEvents.get("king_living");
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("king_hit");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("trex_death");
   }

   public boolean canBePushed() {
      return false;
   }

   protected void collideWithEntity(Entity par1Entity) {
   }

   public int mygetMaxHealth() {
      return OreSpawn.TheKing_stats.health;
   }

   protected Item getDropItem() {
      return Item.getItemFromBlock(Blocks.YELLOW_FLOWER);
   }

   private void dropItemRand(Item index, int par1) {
      EntityItem var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(20) - OreSpawn.OreSpawnRand.nextInt(20),
         this.posY + 12.0,
         this.posZ + OreSpawn.OreSpawnRand.nextInt(20) - OreSpawn.OreSpawnRand.nextInt(20),
         new ItemStack(index, par1, 0)
      );
      this.world.spawnEntity(var3);
   }

   protected void dropFewItems(boolean par1, int par2) {
      Item it = null;
      Block bl = null;
      spawnCreature(this.world, "The Prince", this.posX, this.posY + 10.0, this.posZ);
      this.dropItemRand(OreSpawn.RoyalBody, 1);
      this.dropItemRand(OreSpawn.RoyalHelmet, 1);
      this.dropItemRand(OreSpawn.RoyalLegs, 1);
      this.dropItemRand(OreSpawn.RoyalBoots, 1);
      this.dropItemRand(OreSpawn.MyRoyal, 1);
      Iterator ilist = Item.REGISTRY.iterator();
      int icount = 0;

      while (ilist.hasNext()) {
         it = (Item)ilist.next();
         if (it != null) {
            icount++;
         }
      }

      int j = 0;

      while (j < 150) {
         int k = 1 + this.world.rand.nextInt(icount);

         for (Iterator var15 = Item.REGISTRY.iterator(); k > 0 && var15.hasNext(); k--) {
            it = (Item)var15.next();
         }

         if (it != null) {
            j++;
            this.dropItemRand(it, 1);
         }
      }

      Iterator blist = Block.REGISTRY.iterator();
      int bcount = 0;

      while (blist.hasNext()) {
         bl = (Block)blist.next();
         if (bl != null) {
            bcount++;
         }
      }

      j = 0;

      while (j < 150) {
         int k = 1 + this.world.rand.nextInt(bcount);

         for (Iterator var16 = Block.REGISTRY.iterator(); k > 0 && var16.hasNext(); k--) {
            bl = (Block)var16.next();
         }

         if (bl != null) {
            j++;
            this.dropItemRand(Item.getItemFromBlock(bl), 1);
         }
      }
   }

   protected boolean ableToCauseSkullDrop() {
      return true;
   }

   public void onUpdate() {
      super.onUpdate();
      this.wing_sound++;
      if (this.wing_sound > 30) {
         if (!this.world.isRemote) {
             this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("mothrawings"), net.minecraft.util.SoundCategory.NEUTRAL, 1.75F, 0.75F);
         }

         this.wing_sound = 0;
      }

      this.noClip = true;
      this.motionY *= 0.6;
      if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() * 2 / 3) {
         this.attdam = OreSpawn.TheKing_stats.attack * 2;
      }

      if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
         this.attdam = OreSpawn.TheKing_stats.attack * 4;
      }

      if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 4) {
         this.attdam = OreSpawn.TheKing_stats.attack * 8;
      }

      if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 8) {
         this.attdam = OreSpawn.TheKing_stats.attack * 16;
      }

      if (this.world.isRemote) {
         float f = 7.0F;
         this.isEnd = this.dataManager.get(DW_22);
         if (this.isEnd != 0 && this.world.rand.nextInt(3) == 1) {
            for (int i = 0; i < 10; i++) {
               this.world
                  .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                     this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
                     this.posY + 14.0,
                     this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw)),
                     (this.world.rand.nextGaussian() - this.world.rand.nextGaussian()) / 4.0 + this.motionX * 6.0,
                     (this.world.rand.nextGaussian() - this.world.rand.nextGaussian()) / 4.0,
                     (this.world.rand.nextGaussian() - this.world.rand.nextGaussian()) / 4.0 + this.motionZ * 6.0
                  );
            }
         }
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
         float s = par1Entity.height * par1Entity.width;
         if (s > 30.0F
            && !MyUtils.isRoyalty(par1Entity)
            && !(par1Entity instanceof Godzilla)
            && !(par1Entity instanceof GodzillaHead)
            && !(par1Entity instanceof PitchBlack)
            && !(par1Entity instanceof Kraken)) {
            EntityLivingBase e = (EntityLivingBase)par1Entity;
            e.setHealth(e.getHealth() / 2.0F);
            e.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.attdam * 10.0F);
            this.large_unknown_detected = 1;
         }
      }

      if (par1Entity != null && par1Entity instanceof EntityDragon) {
         EntityDragon dr = (EntityDragon)par1Entity;
         DamageSource var21 = null;
         var21 = DamageSource.causeExplosionDamage((net.minecraft.entity.EntityLivingBase)null);
         var21.setExplosion();
         if (this.world.rand.nextInt(6) == 1) {
            dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)this.attdam);
         } else {
            dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)this.attdam);
         }
      }

      boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.attdam);
      if (var4) {
         double ks = 3.3;
         double inair = 0.25;
         float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
         inair += this.world.rand.nextFloat() * 0.25F;
         if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
            inair *= 1.5;
         }

         par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
      }

      return var4;
   }

   public boolean canSeeTarget(double pX, double pY, double pZ) {
      return this.world
            .rayTraceBlocks(new Vec3d(this.posX, this.posY + 8.75, this.posZ), new Vec3d(pX, pY, pZ), false)
         == null;
   }

   private boolean tooFarFromHome() {
      float d1 = (float)(this.posX - this.homex);
      float d2 = (float)(this.posZ - this.homez);
      d1 = (float)Math.sqrt(d1 * d1 + d2 * d2);
      return d1 > 120.0F;
   }

   private void msgToPlayers(String s) {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(80.0, 64.0, 80.0));
      Collections.sort(var5, this.TargetSorter);
      Iterator var2 = var5.iterator();
      Entity var3 = null;
      EntityPlayer var4 = null;

      while (var2.hasNext()) {
         var3 = (Entity)var2.next();
         var4 = (EntityPlayer)var3;
         var4.sendMessage(new TextComponentString(s));
      }
   }

   private EntityPlayer findNearestPlayer() {
      List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(80.0, 64.0, 80.0));
      Collections.sort(var5, this.TargetSorter);
      Iterator var2 = var5.iterator();
      Entity var3 = null;
      EntityPlayer var4 = null;

      while (var2.hasNext()) {
         var3 = (Entity)var2.next();
         if (var3 instanceof EntityPlayer) {
            var4 = (EntityPlayer)var3;
         }

         if (var4 != null) {
            break;
         }
      }

      return var4;
   }

   protected void updateAITasks() {
      int xdir = 1;
      int zdir = 1;
      int attrand = 5;
      int updown = 0;
      int which = 0;
      EntityLivingBase e = null;
      EntityLivingBase f = null;
      double rr = 0.0;
      double rhdir = 0.0;
      double rdd = 0.0;
      double pi = 3.1415926545;
      double var1 = 0.0;
      double var3 = 0.0;
      double var5 = 0.0;
      float var7 = 0.0F;
      float var8 = 0.0F;
      EntityPlayer p = null;
      if (!this.isDead) {
         super.updateAITasks();
         this.dataManager.set(DW_22,  this.isEnd);
         this.dataManager.set(DW_21,  OreSpawn.PlayNicely);
         if (this.isEnd == 1) {
            this.endCounter++;
            this.noClip = true;
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.hurt_timer = 10;
            if (!this.isDead) {
               p = this.findNearestPlayer();
               if (p != null) {
                  this.faceEntity(p, 10.0F, 10.0F);
                  p.motionX = 0.0;
                  p.motionY = 0.0;
                  p.motionZ = 0.0;
                  double dd0 = this.posX - p.posX;
                  double dd1 = this.posZ - p.posZ;
                  float f2 = (float)(Math.atan2(dd1, dd0) * 180.0 / Math.PI) - 90.0F;
                  p.rotationYaw = f2;
                  p.setHealth(1.0F);
               }

               if (this.endCounter == 10) {
                  this.msgToPlayers("The King: Enough of this charade. I am done. You have shown me what I wanted to know.");
               } else if (this.endCounter == 80) {
                  this.msgToPlayers("The King: That's right my little pet. It has all been a game. You never killed me. You can't.");
               } else if (this.endCounter == 160) {
                  this.msgToPlayers("The King: I am the one. The only. The many. I exist within both space and time. Everywhere and always.");
               } else if (this.endCounter == 240) {
                  this.msgToPlayers("The King: I used you to learn your ways, and I have reached my conclusion on your species.");
               } else if (this.endCounter == 300) {
                  this.msgToPlayers("The King: You have 10 seconds to run...");
               } else if (this.endCounter == 320) {
                  this.msgToPlayers("9.");
               } else if (this.endCounter == 340) {
                  this.msgToPlayers("8.");
               } else if (this.endCounter == 360) {
                  this.msgToPlayers("7.");
               } else if (this.endCounter == 380) {
                  this.msgToPlayers("6.");
               } else if (this.endCounter == 400) {
                  this.msgToPlayers("5.");
               } else if (this.endCounter == 420) {
                  this.msgToPlayers("4.");
               } else if (this.endCounter == 440) {
                  this.msgToPlayers("3.");
               } else if (this.endCounter == 460) {
                  this.msgToPlayers("2.");
               } else if (this.endCounter == 480) {
                  this.msgToPlayers("1.");
               } else if (this.endCounter == 500) {
                  this.msgToPlayers("The King: Prepare to die!");
                  this.isEnd = 2;
               }
            }
         } else {
            if (this.isEnd == 2) {
               this.hurt_timer = 10;
               this.player_hit_count = 0;
               this.stream_count = 10;
               this.stream_count_l = 10;
               this.stream_count_i = 10;
               attrand = 3;
               this.guard_mode = 0;
               this.large_unknown_detected = 1;
               if (this.backoff_timer > 0) {
                  this.backoff_timer--;
               }
            }

            if (this.hurt_timer > 0) {
               this.hurt_timer--;
            }

            if (this.homex == 0 && this.homez == 0 || this.guard_mode == 0) {
               this.homex = (int)this.posX;
               this.homez = (int)this.posZ;
            }

            this.ticker++;
            if (this.ticker > 30000) {
               this.ticker = 0;
            }

            if (this.ticker % 80 == 0) {
               this.stream_count = 10;
            }

            if (this.ticker % 90 == 0) {
               this.stream_count_l = 5;
            }

            if (this.ticker % 70 == 0) {
               this.stream_count_i = 8;
            }

            if (this.backoff_timer > 0) {
               this.backoff_timer--;
            }

            if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
               attrand = 3;
            }

            this.noClip = true;
            if (this.currentFlightTarget == null) {
               this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
            }

            if (this.tooFarFromHome()
               || this.world.rand.nextInt(200) == 0
               || this.getDistanceSq(currentFlightTarget) < 9.1F) {
               zdir = this.world.rand.nextInt(120);
               xdir = this.world.rand.nextInt(120);
               if (this.world.rand.nextInt(2) == 0) {
                  zdir = -zdir;
               }

               if (this.world.rand.nextInt(2) == 0) {
                  xdir = -xdir;
               }

               int dist = 0;

               for (int i = -5; i <= 5; i += 5) {
                  for (int j = -5; j <= 5; j += 5) {
                     Block bid = this.world.getBlockState(new BlockPos(this.homex + j, (int)this.posY, this.homez + i)).getBlock();
                     if (bid != Blocks.AIR) {
                        for (int k = 1; k < 20; k++) {
                           bid = this.world.getBlockState(new BlockPos(this.homex + j, (int)this.posY + k, this.homez + i)).getBlock();
                           dist++;
                           if (bid == Blocks.AIR) {
                              break;
                           }
                        }
                     } else {
                        for (int kx = 1; kx < 20; kx++) {
                           bid = this.world.getBlockState(new BlockPos(this.homex + j, (int)this.posY - kx, this.homez + i)).getBlock();
                           dist--;
                           if (bid != Blocks.AIR) {
                              break;
                           }
                        }
                     }
                  }
               }

               dist = dist / 9 + 2;
               if ((int)(this.posY + dist) > 230) {
                  dist = 230 - (int)this.posY;
               }

               this.currentFlightTarget = new BlockPos(this.homex + xdir, (int)(this.posY + dist), this.homez + zdir);
            } else if (this.world.rand.nextInt(attrand) == 0) {
               e = this.rt;
               if (OreSpawn.PlayNicely != 0) {
                  e = null;
               }

               if (e != null && (e instanceof TheKing || e instanceof KingHead)) {
                  this.rt = null;
                  e = null;
               }

               if (e != null) {
                  float d1 = (float)(e.posX - this.homex);
                  float d2 = (float)(e.posZ - this.homez);
                  d1 = (float)Math.sqrt(d1 * d1 + d2 * d2);
                  if (e.isDead || this.world.rand.nextInt(250) == 1 || d1 > 128.0F && this.guard_mode == 1) {
                     e = null;
                     this.rt = null;
                  }

                  if (e != null && !this.MyCanSee(e)) {
                     e = null;
                  }
               }

               f = this.findSomethingToAttack();
               if (this.head_found == 0) {
                  EntityLiving var33 = (EntityLiving)spawnCreature(
                     this.world, "KingHead", this.posX, this.posY + 20.0, this.posZ
                  );
               }

               if (e == null) {
                  e = f;
               }

               if (e != null) {
                  this.setAttacking(1);
                  if (this.backoff_timer == 0) {
                     int dist = (int)(e.posY + e.height / 2.0F + 1.0);
                     if (dist > 230) {
                        dist = 230;
                     }

                     this.currentFlightTarget = new BlockPos((int)e.posX, dist, (int)e.posZ);
                     if (this.world.rand.nextInt(70) == 1) {
                        this.backoff_timer = 80 + this.world.rand.nextInt(80);
                     }
                  } else if (this.getDistanceSq(currentFlightTarget) < 9.1F) {
                     zdir = this.world.rand.nextInt(20) + 30;
                     xdir = this.world.rand.nextInt(20) + 30;
                     if (this.world.rand.nextInt(2) == 0) {
                        zdir = -zdir;
                     }

                     if (this.world.rand.nextInt(2) == 0) {
                        xdir = -xdir;
                     }

                     int distx = 0;

                     for (int i = -5; i <= 5; i += 5) {
                        for (int jx = -5; jx <= 5; jx += 5) {
                           Block bid = this.world.getBlockState(new BlockPos((int)e.posX + jx, (int)this.posY, (int)e.posZ + i)).getBlock();
                           if (bid != Blocks.AIR) {
                              for (int kxx = 1; kxx < 20; kxx++) {
                                 bid = this.world.getBlockState(new BlockPos((int)e.posX + jx, (int)this.posY + kxx, (int)e.posZ + i)).getBlock();
                                 distx++;
                                 if (bid == Blocks.AIR) {
                                    break;
                                 }
                              }
                           } else {
                              for (int kxxx = 1; kxxx < 20; kxxx++) {
                                 bid = this.world.getBlockState(new BlockPos((int)e.posX + jx, (int)this.posY - kxxx, (int)e.posZ + i)).getBlock();
                                 distx--;
                                 if (bid != Blocks.AIR) {
                                    break;
                                 }
                              }
                           }
                        }
                     }

                     distx = distx / 9 + 2;
                     if ((int)(this.posY + distx) > 230) {
                        distx = 230 - (int)this.posY;
                     }

                     this.currentFlightTarget = new BlockPos((int)e.posX + xdir, (int)(this.posY + distx), (int)e.posZ + zdir);
                  }

                  if (this.getDistanceSq(e) < 900.0) {
                     if (this.world.rand.nextInt(2) == 1) {
                        this.doJumpDamage(this.posX, this.posY, this.posZ, 15.0, OreSpawn.TheKing_stats.attack / 4, 0);
                     }

                     this.attackEntityAsMob(e);
                  }

                  double dx = this.posX + 20.0 * Math.sin(Math.toRadians(this.rotationYawHead));
                  double dz = this.posZ - 20.0 * Math.cos(Math.toRadians(this.rotationYawHead));
                  if (this.world.rand.nextInt(3) == 1) {
                     this.doJumpDamage(dx, this.posY + 10.0, dz, 15.0, OreSpawn.TheKing_stats.attack / 2, 1);
                  }

                  if (this.getHorizontalDistanceSqToEntity(e) > 900.0) {
                     which = this.world.rand.nextInt(3);
                     if (which == 0) {
                        if (this.stream_count > 0) {
                           this.setAttacking(1);
                           rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                           rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                           rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                           if (rdd > pi) {
                              rdd -= pi * 2.0;
                           }

                           rdd = Math.abs(rdd);
                           if (rdd < 0.5) {
                              this.firecanon(e);
                           }
                        }
                     } else if (which == 1) {
                        if (this.stream_count_l > 0) {
                           this.setAttacking(1);
                           rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                           rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                           rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                           if (rdd > pi) {
                              rdd -= pi * 2.0;
                           }

                           rdd = Math.abs(rdd);
                           if (rdd < 0.5) {
                              this.firecanonl(e);
                           }
                        }
                     } else if (this.stream_count_i > 0) {
                        this.setAttacking(1);
                        rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
                        rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
                        rdd = Math.abs(rr - rhdir) % (pi * 2.0);
                        if (rdd > pi) {
                           rdd -= pi * 2.0;
                        }

                        rdd = Math.abs(rdd);
                        if (rdd < 0.5) {
                           this.firecanoni(e);
                        }
                     }
                  }
               } else {
                  this.setAttacking(0);
                  this.stream_count = 10;
                  this.stream_count_l = 5;
                  this.stream_count_i = 8;
               }
            }

            if (this.getAttacking() != 0 && this.isEnd == 2) {
               double xzoff = 10.0;
               double yoff = 14.0;
               Entity ppwr = spawnCreature(
                  this.world,
                  "PurplePower",
                  this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)),
                  this.posY + yoff,
                  this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw))
               );
               if (ppwr != null) {
                  PurplePower pwr = (PurplePower)ppwr;
                  pwr.motionX = this.motionX * 3.0;
                  pwr.motionZ = this.motionZ * 3.0;
                  pwr.setPurpleType(10);
               }
            }

            var1 = this.currentFlightTarget.getX() + 0.5 - this.posX;
            var3 = this.currentFlightTarget.getY() + 0.1 - this.posY;
            var5 = this.currentFlightTarget.getZ() + 0.5 - this.posZ;
            this.motionX = this.motionX + (Math.signum(var1) * 0.7 - this.motionX) * 0.35;
            this.motionY = this.motionY + (Math.signum(var3) * 0.69999 - this.motionY) * 0.3;
            this.motionZ = this.motionZ + (Math.signum(var5) * 0.7 - this.motionZ) * 0.35;
            var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
            var8 = MathHelper.wrapDegrees(var7 - this.rotationYaw);
            this.moveVertical = 1.0F;
            this.rotationYaw += var8 / 8.0F;
            if (this.world.rand.nextInt(30) == 1 && this.getHealth() < this.mygetMaxHealth()) {
               this.heal(5.0F);
               if (this.large_unknown_detected != 0) {
                  this.heal(200.0F);
               }
            }

            if (this.player_hit_count < 10 && this.getHealth() < 2000.0F) {
               this.heal(2000.0F - this.getHealth());
            }
         }
      }
   }

   private double getHorizontalDistanceSqToEntity(Entity e) {
      double d1 = e.posZ - this.posZ;
      double d2 = e.posX - this.posX;
      return d1 * d1 + d2 * d2;
   }

   private void firecanon(EntityLivingBase e) {
      double yoff = 14.0;
      double xzoff = 32.0;
      BetterFireball bf = null;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      if (this.stream_count > 0) {
         bf = new BetterFireball(
            this.world, this, e.posX - cx, e.posY + e.height / 2.0F - (this.posY + yoff), e.posZ - cz
         );
         bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
         bf.setPosition(cx, this.posY + yoff, cz);
         bf.setReallyBig();
         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("random.fuse"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
         this.world.spawnEntity(bf);

         for (int i = 0; i < 6; i++) {
            float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            bf = new BetterFireball(
               this.world,
               this,
               e.posX - cx + r1,
               e.posY + e.height / 2.0F - (this.posY + yoff) + r2,
               e.posZ - cz + r3
            );
            bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
            bf.setPosition(cx, this.posY + yoff, cz);
            bf.setBig();
            if (this.world.rand.nextInt(2) == 1) {
               bf.setSmall();
            }

            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(bf);
         }

         this.stream_count--;
      }
   }

   private void firecanonl(EntityLivingBase e) {
      double yoff = 14.0;
      double xzoff = 32.0;
      double var3 = 0.0;
      double var5 = 0.0;
      double var7 = 0.0;
      float var9 = 0.0F;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      if (this.stream_count_l > 0) {
         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

         for (int i = 0; i < 3; i++) {
            float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            ThunderBolt lb = new ThunderBolt(this.world, cx, this.posY + yoff, cz);
            lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
            var3 = e.posX - lb.posX;
            var5 = e.posY + 0.25 - lb.posY;
            var7 = e.posZ - lb.posZ;
            var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;
            lb.shoot(var3, var5 + var9, var7, 1.4F, 4.0F);
            lb.motionX *= 3.0;
            lb.motionY *= 3.0;
            lb.motionZ *= 3.0;
            this.world.spawnEntity(lb);
         }

         this.stream_count_l--;
      }
   }

   private void firecanoni(EntityLivingBase e) {
      double yoff = 14.0;
      double xzoff = 32.0;
      double var3 = 0.0;
      double var5 = 0.0;
      double var7 = 0.0;
      float var9 = 0.0F;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      if (this.stream_count_i > 0) {
         this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.arrow.shoot"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

         for (int i = 0; i < 5; i++) {
            float r1 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r2 = 3.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            float r3 = 5.0F * (this.world.rand.nextFloat() - this.world.rand.nextFloat());
            IceBall lb = new IceBall(this.world, cx, this.posY + yoff, cz);
            lb.setIceMaker(1);
            lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
            var3 = e.posX - lb.posX;
            var5 = e.posY + 0.25 - lb.posY;
            var7 = e.posZ - lb.posZ;
            var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;
            lb.shoot(var3, var5 + var9, var7, 1.4F, 4.0F);
            lb.motionX *= 3.0;
            lb.motionY *= 3.0;
            lb.motionZ *= 3.0;
            this.world.spawnEntity(lb);
         }

         this.stream_count_i--;
      }
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean ret = false;
      float dm = par2;
      if (this.hurt_timer > 0) {
         return false;
      } else {
         if (par2 > 750.0F) {
            dm = 750.0F;
         }

         if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
         } else {
            Entity e = par1DamageSource.getTrueSource();
            if (e != null && e instanceof EntityLivingBase) {
               EntityLivingBase enl = (EntityLivingBase)e;
               float s = enl.height * enl.width;
               if (s > 30.0F
                  && !MyUtils.isRoyalty(enl)
                  && !(enl instanceof Godzilla)
                  && !(enl instanceof GodzillaHead)
                  && !(enl instanceof PitchBlack)
                  && !(enl instanceof Kraken)) {
                  dm /= 10.0F;
                  this.hurt_timer = 50;
                  this.large_unknown_detected = 1;
               }

               if (e instanceof EntityMob && s < 3.0F) {
                  e.setDead();
                  return false;
               }
            }

            if (!par1DamageSource.getDamageType().equals("cactus")) {
               this.hurt_timer = 20;
               ret = super.attackEntityFrom(par1DamageSource, dm);
               if (e != null && e instanceof EntityPlayer) {
                  this.player_hit_count++;
               }

               if (e != null && e instanceof EntityLivingBase && this.currentFlightTarget != null && !MyUtils.isRoyalty(e)) {
                  this.rt = (EntityLivingBase)e;
                  int dist = (int)e.posY;
                  if (dist > 230) {
                     dist = 230;
                  }

                  this.currentFlightTarget = new BlockPos((int)e.posX, dist, (int)e.posZ);
               }
            }

            return ret;
         }
      }
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   public int getTotalArmorValue() {
      if (this.large_unknown_detected != 0) {
         return 25;
      } else if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() * 2 / 3) {
         return OreSpawn.TheKing_stats.defense + 1;
      } else if (this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 2) {
         return OreSpawn.TheKing_stats.defense + 2;
      } else {
         return this.player_hit_count < 10 && this.getHealth() < this.mygetMaxHealth() / 4
            ? OreSpawn.TheKing_stats.defense + 3
            : OreSpawn.TheKing_stats.defense;
      }
   }

   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
   }

   public void initCreature() {
   }

   public boolean MyCanSee(EntityLivingBase e) {
      double xzoff = 22.0;
      int nblks = 20;
      double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
      double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
      float startx = (float)cx;
      float starty = (float)(this.posY + this.height * 7.0F / 8.0F);
      float startz = (float)cz;
      float dx = (float)((e.posX - startx) / 20.0);
      float dy = (float)((e.posY + e.height / 2.0F - starty) / 20.0);
      float dz = (float)((e.posZ - startz) / 20.0);
      if (Math.abs(dx) > 1.0) {
         dy /= Math.abs(dx);
         dz /= Math.abs(dx);
         nblks = (int)(nblks * Math.abs(dx));
         if (dx > 1.0F) {
            dx = 1.0F;
         }

         if (dx < -1.0F) {
            dx = -1.0F;
         }
      }

      if (Math.abs(dy) > 1.0) {
         dx /= Math.abs(dy);
         dz /= Math.abs(dy);
         nblks = (int)(nblks * Math.abs(dy));
         if (dy > 1.0F) {
            dy = 1.0F;
         }

         if (dy < -1.0F) {
            dy = -1.0F;
         }
      }

      if (Math.abs(dz) > 1.0) {
         dy /= Math.abs(dz);
         dx /= Math.abs(dz);
         nblks = (int)(nblks * Math.abs(dz));
         if (dz > 1.0F) {
            dz = 1.0F;
         }

         if (dz < -1.0F) {
            dz = -1.0F;
         }
      }

      for (int i = 0; i < nblks; i++) {
         startx += dx;
         starty += dy;
         startz += dz;
         Block bid = this.world.getBlockState(new BlockPos((int)startx, (int)starty, (int)startz)).getBlock();
         if (bid != Blocks.FLOWING_WATER
            && bid != Blocks.WATER
            && bid != Blocks.LEAVES
            && bid != Blocks.VINE
            && bid != Blocks.AIR) {
            return false;
         }
      }

      return true;
   }

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (par1EntityLiving instanceof KingHead) {
         this.head_found = 1;
         return false;
      } else if (MyUtils.isRoyalty(par1EntityLiving)) {
         return false;
      } else {
         float d1 = (float)(par1EntityLiving.posX - this.homex);
         float d2 = (float)(par1EntityLiving.posZ - this.homez);
         d1 = (float)Math.sqrt(d1 * d1 + d2 * d2);
         if (d1 > 144.0F) {
            return false;
         } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
            return false;
         } else {
            if (this.isEnd == 2) {
               if (par1EntityLiving instanceof EntityPlayer) {
                  EntityPlayer p = (EntityPlayer)par1EntityLiving;
                  if (p.capabilities.isCreativeMode) {
                     return false;
                  }

                  return true;
               }

               if (par1EntityLiving instanceof Girlfriend) {
                  return true;
               }

               if (par1EntityLiving instanceof Boyfriend) {
                  return true;
               }

               if (par1EntityLiving instanceof EntityVillager) {
                  return true;
               }
            }

            if (!this.MyCanSee(par1EntityLiving)) {
               return false;
            } else if (par1EntityLiving instanceof EntityPlayer) {
               EntityPlayer p = (EntityPlayer)par1EntityLiving;
               return !p.capabilities.isCreativeMode;
            } else if (par1EntityLiving instanceof EntityHorse) {
               return true;
            } else if (par1EntityLiving instanceof EntityMob) {
               return true;
            } else {
               return par1EntityLiving instanceof EntityDragon ? true : MyUtils.isAttackableNonMob(par1EntityLiving);
            }
         }
      }
   }

   private EntityLivingBase findSomethingToAttack() {
      if (OreSpawn.PlayNicely != 0) {
         this.head_found = 1;
         return null;
      } else {
         if (this.isEnd == 2) {
            List var5p = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(80.0, 64.0, 80.0));
            Collections.sort(var5p, this.TargetSorter);
            Iterator var2p = var5p.iterator();
            Entity var3p = null;
            EntityLivingBase var4p = null;
            EntityLivingBase retp = null;
            this.head_found = 1;

            while (var2p.hasNext()) {
               var3p = (Entity)var2p.next();
               var4p = (EntityLivingBase)var3p;
               if (this.isSuitableTarget(var4p, false)) {
                  return var4p;
               }
            }
         }

         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(80.0, 64.0, 80.0));
         Collections.sort(var5, this.TargetSorter);
         Iterator var2 = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;
         EntityLivingBase ret = null;
         this.head_found = 0;

         while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (this.isSuitableTarget(var4, false) && ret == null) {
               ret = var4;
            }

            if (ret != null && this.head_found != 0) {
               break;
            }
         }

         return ret;
      }
   }

   public void setGuardMode(int i) {
      this.guard_mode = i;
   }

   public void setFree() {
      this.isEnd = 1;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("KingHomeX", this.homex);
      par1NBTTagCompound.setInteger("KingHomeZ", this.homez);
      par1NBTTagCompound.setInteger("GuardMode", this.guard_mode);
      par1NBTTagCompound.setInteger("PlayerHits", this.player_hit_count);
      par1NBTTagCompound.setInteger("IsEnd", this.isEnd);
      par1NBTTagCompound.setInteger("EndCounter", this.endCounter);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.homex = par1NBTTagCompound.getInteger("KingHomeX");
      this.homez = par1NBTTagCompound.getInteger("KingHomeZ");
      this.guard_mode = par1NBTTagCompound.getInteger("GuardMode");
      this.player_hit_count = par1NBTTagCompound.getInteger("PlayerHits");
      this.isEnd = par1NBTTagCompound.getInteger("IsEnd");
      this.endCounter = par1NBTTagCompound.getInteger("EndCounter");
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

   private EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock) {
      AxisAlignedBB bb = new AxisAlignedBB(X - dist, Y - 10.0, Z - dist, X + dist, Y + 10.0, Z + dist);
      List var5x = this.world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
      Collections.sort(var5x, this.TargetSorter);
      Iterator var2 = var5x.iterator();
      Entity var3x = null;
      EntityLivingBase var4 = null;

      while (var2.hasNext()) {
         var3x = (Entity)var2.next();
         var4 = (EntityLivingBase)var3x;
         if (var4 != null && var4 != this && var4.isEntityAlive() && !MyUtils.isRoyalty(var4) && !(var4 instanceof Ghost) && !(var4 instanceof GhostSkelly)) {
            DamageSource var21 = null;
            var21 = DamageSource.causeExplosionDamage((net.minecraft.entity.EntityLivingBase)null);
            var21.setExplosion();
            var4.attackEntityFrom(var21, (float)damage / 2.0F);
            var4.attackEntityFrom(DamageSource.FALL, (float)damage / 2.0F);
            this.world.playSound(null, var4.posX, var4.posY, var4.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 0.65F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
            if (knock != 0) {
               double ks = 2.75;
               double inair = 0.65;
               float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
               var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
         }
      }

      return null;
   }
}

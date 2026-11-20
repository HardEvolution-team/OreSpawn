package com.electrifiedded.orespawn.entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import com.electrifiedded.orespawn.util.MyUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class AntRobot extends EntityLiving {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(AntRobot.class, DataSerializers.VARINT);

   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   private int playing = 0;
   private GenericTargetSorter TargetSorter = null;
   private float moveSpeed = 0.3F;
   private RenderSpiderRobotInfo renderdata = new RenderSpiderRobotInfo();
   private int didonce = 0;
   private int rideTicker = 0;
   private int owned = 0;
   private float riderThrottle = 0.0F;

   public AntRobot(World par1World) {
      super(par1World);
      this.setSize(2.75F, 1.25F);
      this.stepHeight = 2.0F;
      if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
      this.TargetSorter = new GenericTargetSorter(this);
      this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
      this.tasks.addTask(2, new EntityAILookIdle(this));
      this.isImmuneToFire = true;
      this.experienceValue = OreSpawn.AntRobot_stats.health / 2;
   }

   public AntRobot(World par1World, double par2, double par4, double par6) {
      this(par1World);
      this.setPosition(par2, par4 + this.getYOffset(), par6);
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.prevPosX = par2;
      this.prevPosY = par4;
      this.prevPosZ = par6;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(OreSpawn.AntRobot_stats.health);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawn.AntRobot_stats.attack);
   }

   protected boolean canDespawn() {
      return false;
   }

   public void setOwned() {
      this.owned = 1;
   }

   public int getOwned() {
      return this.owned;
   }

   public int getTotalArmorValue() {
      return OreSpawn.AntRobot_stats.defense;
   }

   protected void updateAITasks() {
      EntityLivingBase e = null;
      if (!this.isDead) {
         if (this.getControllingPassenger() == null) {
            super.updateAITasks();
            if (this.owned == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
               if (this.world.rand.nextInt(20) == 0) {
                  this.feetFindSomethingToHit();
               }

               if (this.world.rand.nextInt(150) == 0) {
                  this.setAttackTarget(null);
               }

               e = this.getAttackTarget();
               if (e != null && !e.isEntityAlive()) {
                  this.setAttackTarget(null);
                  e = null;
               }

               if (e == null) {
                  e = this.findSomethingToAttack(2.0F, false);
               }

               if (e != null) {
                  this.faceEntity(e, 10.0F, 10.0F);
                  if (this.getDistanceSq(e) > 16.0) {
                     double d1 = e.posZ - this.posZ;
                     double d2 = e.posX - this.posX;
                     double dd = Math.atan2(d1, d2);
                     this.goThisWay(0.2 * Math.cos(dd), 0.2 * Math.sin(dd));
                  }
               } else {
                  this.setAttacking(0);
               }

               if (e != null && this.world.rand.nextInt(15) == 0) {
                  e = this.getAttackTarget();
                  if (e == null) {
                     e = this.findSomethingToAttack(2.0F, true);
                  }

                  if (e != null) {
                     if (this.getDistanceSq(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {
                        this.setAttacking(1);
                        this.attackEntityAsMob(e);
                     } else {
                        this.setAttacking(0);
                     }
                  } else {
                     this.setAttacking(0);
                  }
               }
            }
         }
      }
   }

   protected void handleJumpWater() {
      if (this.getControllingPassenger() == null) {
         super.handleJumpWater();
      }
   }

   private void initLegData() {
      if (this.renderdata == null) {
         this.renderdata = new RenderSpiderRobotInfo();
      }

      for (int i = 0; i < 6; i++) {
         this.renderdata.ycurrentangle[i] = 0.0F;
         this.renderdata.ywantedangle[i] = 0.0F;
         this.renderdata.ydisplayangle[i] = 0.0F;
         this.renderdata.yvelocity[i] = 0.0F;
         this.renderdata.ymid[i] = 0.0F;
         this.renderdata.yoff[i] = 0.0F;
         this.renderdata.yrange[i] = 0.0F;
         this.renderdata.udcurrentangle[i] = 0.0F;
         this.renderdata.udwantedangle[i] = 0.0F;
         this.renderdata.uddisplayangle[i] = 0.0F;
         this.renderdata.udvelocity[i] = 0.0F;
         this.renderdata.p1xangle[i] = Math.PI / 4;
         this.renderdata.p2xangle[i] = 0.0;
         this.renderdata.p3xangle[i] = -Math.PI / 4;
         this.renderdata.pxvelocity[i] = 0.0F;
         this.renderdata.foot_xpos[i] = (float)this.posX;
         this.renderdata.foot_ypos[i] = (float)this.posY;
         this.renderdata.foot_zpos[i] = (float)this.posZ;
         this.renderdata.realposx[i] = 0.0F;
         this.renderdata.realposy[i] = 0.0F;
         this.renderdata.realposz[i] = 0.0F;
         this.renderdata.legoff[i] = 0.0F;
         this.renderdata.footup[i] = 1;
         this.renderdata.uppoint[i] = 0.0F;
         this.renderdata.footingticker[i] = 0;
         this.renderdata.gpcounter = 0;
         if (i == 0) {
            this.renderdata.legoff[i] = 0.75F;
            this.renderdata.ymid[i] = 0.0F;
            this.renderdata.yrange[i] = (float) (Math.PI / 12);
            this.renderdata.pairedwith[i] = 1;
            this.renderdata.yoff[i] = -0.75F;
         }

         if (i == 1) {
            this.renderdata.legoff[i] = 0.75F;
            this.renderdata.ymid[i] = (float) Math.PI;
            this.renderdata.yrange[i] = (float) (-Math.PI / 12);
            this.renderdata.pairedwith[i] = 0;
            this.renderdata.yoff[i] = -0.75F;
         }

         if (i == 2) {
            this.renderdata.legoff[i] = 1.0F;
            this.renderdata.ymid[i] = (float) (-Math.PI / 4);
            this.renderdata.yrange[i] = (float) (Math.PI / 12);
            this.renderdata.pairedwith[i] = 3;
            this.renderdata.yoff[i] = -0.75F;
         }

         if (i == 3) {
            this.renderdata.legoff[i] = 1.0F;
            this.renderdata.ymid[i] = (float) (Math.PI * 5.0 / 4.0);
            this.renderdata.yrange[i] = (float) (-Math.PI / 12);
            this.renderdata.pairedwith[i] = 2;
            this.renderdata.yoff[i] = -0.75F;
         }

         if (i == 4) {
            this.renderdata.legoff[i] = 1.15F;
            this.renderdata.ymid[i] = (float) (Math.PI / 4);
            this.renderdata.yrange[i] = (float) (Math.PI / 12);
            this.renderdata.pairedwith[i] = 5;
            this.renderdata.yoff[i] = -0.75F;
         }

         if (i == 5) {
            this.renderdata.legoff[i] = 1.15F;
            this.renderdata.ymid[i] = (float) (Math.PI * 3.0 / 4.0);
            this.renderdata.yrange[i] = (float) (-Math.PI / 12);
            this.renderdata.pairedwith[i] = 4;
            this.renderdata.yoff[i] = -0.75F;
         }
      }
   }

   private float getNewVelocity(float v, float diff, float curval) {
      float tv = v * 18.0F;
      if (tv < 2.0F) {
         tv = 2.0F;
      }

      if (tv > 8.0F) {
         tv = 8.0F;
      }

      if (diff > 0.0F) {
         if (diff < 0.008726646259971648 * tv) {
            curval = 0.0F;
         } else {
            curval = (float)(curval + 0.004363323129985824 * tv);
            if (diff < 0.06981317007977318 * tv) {
               curval = (float)((Math.PI / 180.0) * tv);
            }

            if (diff < 0.03490658503988659 * tv) {
               curval = (float)(0.008726646259971648 * tv);
            }

            if (curval > 0.06981317007977318 * tv) {
               curval = (float)(0.06981317007977318 * tv);
            }
         }
      } else if (diff > -0.008726646259971648 * tv) {
         curval = 0.0F;
      } else {
         curval = (float)(curval - 0.004363323129985824 * tv);
         if (diff > -0.06981317007977318 * tv) {
            curval = -((float)((Math.PI / 180.0) * tv));
         }

         if (diff > -0.03490658503988659 * tv) {
            curval = -((float)(0.008726646259971648 * tv));
         }

         if (curval < -0.06981317007977318 * tv) {
            curval = -((float)(0.06981317007977318 * tv));
         }
      }

      return curval;
   }

   public void updateLegs() {
      if (this.world.isRemote) {
         this.rotationYaw %= 360.0F;

         while (this.rotationYaw < 0.0F) {
            this.rotationYaw += 360.0F;
         }

         this.renderdata.gpcounter++;
         if (this.didonce == 0) {
            this.didonce = 1;
            this.initLegData();
         }

         float d1 = (float)(this.prevPosX - this.posX);
         float d2 = (float)(this.prevPosY - this.posY);
         float d3 = (float)(this.prevPosZ - this.posZ);
         float realv = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         int i = 0;

         for (int var22 = 0; var22 < 6; var22++) {
            int fcount = 0;
            this.renderdata.footingticker[var22]++;
            this.renderdata.realposx[var22] = (float)(
               this.posX
                  - this.renderdata.legoff[var22] * Math.sin(Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw + 90.0F)) + this.renderdata.ymid[var22])
            );
            this.renderdata.realposz[var22] = (float)(
               this.posZ
                  + this.renderdata.legoff[var22] * Math.cos(Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw + 90.0F)) + this.renderdata.ymid[var22])
            );
            this.renderdata.realposy[var22] = (float)this.posY + this.renderdata.yoff[var22];
            int it = this.renderdata.footingticker[var22] + this.renderdata.footingticker[this.renderdata.pairedwith[var22]];
            if (it > 50 && this.renderdata.footingticker[var22] > this.renderdata.footingticker[this.renderdata.pairedwith[var22]]) {
               this.renderdata.footingticker[var22] = 0;
            }

            d1 = this.renderdata.realposx[var22] - this.renderdata.foot_xpos[var22];
            d2 = this.renderdata.realposy[var22] - this.renderdata.foot_ypos[var22];
            d3 = this.renderdata.realposz[var22] - this.renderdata.foot_zpos[var22];
            float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0F;
            float da = (float)(
               Math.abs(this.renderdata.ycurrentangle[var22] - (Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw)) + this.renderdata.ymid[var22]))
                  % (Math.PI * 2)
            );
            if (da > Math.PI) {
               da = (float)(da - (Math.PI * 2));
            }

            if (da < -Math.PI) {
               da = (float)(da + (Math.PI * 2));
            }

            da = Math.abs(da);
            if (dd > 144.0F
               || dd < 22.0F
               || da > Math.abs(this.renderdata.yrange[var22]) * 8.0F / 6.0F
               || Math.abs(this.renderdata.udcurrentangle[var22]) > 1.25
               || this.renderdata.footingticker[var22] == 0) {
               this.findNewFooting(var22);
               d1 = this.renderdata.realposx[var22] - this.renderdata.foot_xpos[var22];
               d2 = this.renderdata.realposy[var22] - this.renderdata.foot_ypos[var22];
               d3 = this.renderdata.realposz[var22] - this.renderdata.foot_zpos[var22];
               dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
               dd *= 16.0F;
            }

            float c1 = (float)(49.0 * Math.cos(this.renderdata.p2xangle[var22] - this.renderdata.p1xangle[var22]));
            float c2 = 49.0F;
            float c3 = (float)(49.0 * Math.cos(this.renderdata.p2xangle[var22] - this.renderdata.p3xangle[var22]));
            float cc = c1 + c2 + c3;
            float diff = cc - dd;
            this.renderdata.pxvelocity[var22] = this.getNewVelocity(realv, (float)(diff * Math.PI / 360.0), this.renderdata.pxvelocity[var22]);
            if (this.renderdata.pxvelocity[var22] == 0.0F || Math.abs(diff) < 8.0F) {
               fcount++;
            }

            this.renderdata.p1xangle[var22] = this.renderdata.p1xangle[var22] + this.renderdata.pxvelocity[var22];
            this.renderdata.p2xangle[var22] = 0.0;
            this.renderdata.p3xangle[var22] = -this.renderdata.p1xangle[var22];
            if (this.renderdata.uppoint[var22] != 0.0F) {
               dd = (float)Math.atan2(dd, (this.renderdata.realposy[var22] - this.renderdata.uppoint[var22]) * 16.0);
            } else {
               dd = (float)Math.atan2(dd, (this.renderdata.realposy[var22] - this.renderdata.foot_ypos[var22]) * 16.0);
            }

            this.renderdata.udwantedangle[var22] = (float)(dd - (Math.PI / 2));

            while (this.renderdata.udwantedangle[var22] > Math.PI) {
               this.renderdata.udwantedangle[var22] = (float)(this.renderdata.udwantedangle[var22] - (Math.PI * 2));
            }

            while (this.renderdata.udwantedangle[var22] < -Math.PI) {
               this.renderdata.udwantedangle[var22] = (float)(this.renderdata.udwantedangle[var22] + (Math.PI * 2));
            }

            double rhm = this.renderdata.udwantedangle[var22];
            double rhdir = this.renderdata.udcurrentangle[var22];
            double rdv = (rhm - rhdir) % (Math.PI * 2);

            while (rdv > Math.PI) {
               rdv -= Math.PI * 2;
            }

            while (rdv < -Math.PI) {
               rdv += Math.PI * 2;
            }

            diff = (float)rdv;
            this.renderdata.udvelocity[var22] = this.getNewVelocity(realv * 2.0F, diff, this.renderdata.udvelocity[var22]);
            if (this.renderdata.udvelocity[var22] == 0.0F || Math.abs(diff) < 0.03490658503988659) {
               this.renderdata.uppoint[var22] = 0.0F;
               fcount++;
            }

            rhdir += this.renderdata.udvelocity[var22];

            while (rhdir > Math.PI) {
               rhdir -= Math.PI * 2;
            }

            while (rhdir < -Math.PI) {
               rhdir += Math.PI * 2;
            }

            dd = this.renderdata.udcurrentangle[var22] = (float)rhdir;
            this.renderdata.uddisplayangle[var22] = dd;
            d1 = this.renderdata.realposx[var22] - this.renderdata.foot_xpos[var22];
            d3 = this.renderdata.realposz[var22] - this.renderdata.foot_zpos[var22];
            dd = (float)Math.atan2(d3, d1);
            rhm = this.renderdata.ywantedangle[var22] = dd;
            rhdir = this.renderdata.ycurrentangle[var22];
            rdv = (rhm - rhdir) % (Math.PI * 2);
            if (rdv > Math.PI) {
               rdv -= Math.PI * 2;
            }

            if (rdv < -Math.PI) {
               rdv += Math.PI * 2;
            }

            diff = (float)rdv;
            this.renderdata.yvelocity[var22] = this.getNewVelocity(realv, diff, this.renderdata.yvelocity[var22]);
            if (this.renderdata.yvelocity[var22] == 0.0F || Math.abs(diff) < 0.03490658503988659) {
               fcount++;
            }

            this.renderdata.ycurrentangle[var22] = this.renderdata.ycurrentangle[var22] + this.renderdata.yvelocity[var22];

            while (this.renderdata.ycurrentangle[var22] > Math.PI) {
               this.renderdata.ycurrentangle[var22] = (float)(this.renderdata.ycurrentangle[var22] - (Math.PI * 2));
            }

            while (this.renderdata.ycurrentangle[var22] < -Math.PI) {
               this.renderdata.ycurrentangle[var22] = (float)(this.renderdata.ycurrentangle[var22] + (Math.PI * 2));
            }

            dd = (float)(this.renderdata.ycurrentangle[var22] - Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw)) - (Math.PI / 2));

            while (dd > Math.PI) {
               dd = (float)(dd - (Math.PI * 2));
            }

            while (dd < -Math.PI) {
               dd = (float)(dd + (Math.PI * 2));
            }

            this.renderdata.ydisplayangle[var22] = dd;
            if (fcount == 3) {
               this.renderdata.footup[var22] = 0;
            }
         }
      }
   }

   private void findNewFooting(int i) {
      float f = 9.0F;
      int found = 0;
      float range = 0.0F;
      double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
      double pi = 3.1415926545;
      this.renderdata.footingticker[i] = 0;
      float d1 = (float)(this.posX - this.prevPosX);
      float d3 = (float)(this.posZ - this.prevPosZ);
      double rhm = Math.atan2(d3, d1);
      double velocity = Math.sqrt(d1 * d1 + d3 * d3);
      double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
      if (rdv > pi) {
         rdv -= pi * 2.0;
      }

      rdv = Math.abs(rdv);
      if (Math.abs(velocity) < 0.01) {
         rdv = 0.0;
      }

      range = this.renderdata.yrange[i];
      range *= 0.8F;
      if (Math.abs((this.prevRotationYaw - this.rotationYaw) % 360.0F) > 0.75F) {
         range = 0.0F;
      }

      if (i >= 4) {
         f = 4.0F;
      }

      if (rdv > 1.5) {
         range = -range;
         f = 4.0F;
         if (i >= 4) {
            f = 9.0F;
         }
      }

      if (i == 0 || i == 1) {
         f = 6.0F;
      }

      float fx;
      float deffx = fx = (float)(
         this.renderdata.realposx[i] - f / 2.0F * Math.sin(Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i])
      );
      float fz;
      float deffz = fz = (float)(
         this.renderdata.realposz[i] + f / 2.0F * Math.cos(Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i])
      );
      float fy;
      float deffy = fy = this.renderdata.realposy[i] - 1.0F;
      float oldf = f;
      int span = 1;

      while (found == 0 && f > 2.5F) {
         fx = (float)(
            this.renderdata.realposx[i] - f * Math.sin(Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i] - range)
         );
         fz = (float)(
            this.renderdata.realposz[i] + f * Math.cos(Math.toRadians(MathHelper.wrapDegrees(this.rotationYaw + 90.0F)) + this.renderdata.ymid[i] - range)
         );
         fy = this.renderdata.realposy[i];
         int j = 8;

         label95:
         while (true) {
            if (found == 0 && j > -9) {
               int m = -span;

               label90:
               while (true) {
                  if (found == 0 && m <= span) {
                     for (int n = -span; found == 0 && n <= span; n++) {
                        Block blk = this.world.getBlockState(new BlockPos((int)fx + m, (int)fy + j, (int)fz + n)).getBlock();
                        if (blk != Blocks.AIR
                           && this.world.getBlockState(new BlockPos((int)fx + m, (int)fy + j, (int)fz + n)).getMaterial().isSolid()) {
                           d1 = this.renderdata.realposx[i] - (fx + m);
                           float d2 = this.renderdata.realposy[i] - (fy + j + 1.0F);
                           d3 = this.renderdata.realposz[i] - (fz + n);
                           float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                           dd *= 16.0F;
                           if (!(dd > 144.0F)) {
                              fy += j + 1;
                              fx += m;
                              fz += n;
                              found = 1;
                              m++;
                              continue label90;
                           }
                        }
                     }
                  }

                  j--;
                  continue label95;
               }
            }

            if (--f < 2.5F && range != 0.0F) {
               range = 0.0F;
               span = 3;
               f = oldf;
            }
            break;
         }
      }

      if (found == 0) {
         fx = deffx;
         fy = deffy;
         fz = deffz;
      }

      float sfx = this.renderdata.foot_xpos[i];
      float sfy = this.renderdata.foot_ypos[i];
      float sfz = this.renderdata.foot_zpos[i];
      this.renderdata.foot_xpos[i] = fx;
      this.renderdata.foot_ypos[i] = fy;
      this.renderdata.foot_zpos[i] = fz;
      if (this.renderdata.footup[i] == 0) {
         this.renderdata.footup[i] = 1;
         d1 = sfx - fx;
         float d2 = sfy - fy;
         d3 = sfz - fz;
         float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         dd *= 16.0F;
         d1 = (sfy + fy) / 2.0F;
         if (dd > 3.0F) {
            d1 += 0.3F;
         }

         if (dd > 24.0F) {
            d1 += 0.6F;
         }

         if (dd > 50.0F) {
            d1 += 0.6F;
         }

         this.renderdata.uppoint[i] = d1;
      }
   }

   public boolean shouldRiderSit() {
      return true;
   }

   public int getTrackingRange() {
      return 128;
   }

   public int getUpdateFrequency() {
      return 10;
   }

   public boolean sendsVelocityUpdates() {
      return true;
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   protected void entityInit() {
      super.entityInit();
      this.enablePersistence();
      this.initLegData();
      int i = 0;
      this.dataManager.register(DW_20, i);
   }

   public RenderSpiderRobotInfo getRenderSpiderRobotInfo() {
      return this.renderdata;
   }

   public boolean canBePushed() {
      return false;
   }

   public double getMountedYOffset() {
      return 0.55 + Math.cos(this.rideTicker * 0.19F) * 0.02;
   }

   public void updateRiderPosition() {
      if (this.getControllingPassenger() != null) {
         float f = -1.25F;
         f = (float)(f + Math.cos(this.rideTicker * 0.33F) * 0.05);
         this.getControllingPassenger()
            .setPosition(
               this.posX - f * Math.sin(Math.toRadians(this.rotationYaw)),
               this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(),
               this.posZ + f * Math.cos(Math.toRadians(this.rotationYaw))
            );
      }
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if (par1DamageSource == DamageSource.FALL || par1DamageSource.getDamageType().equals("fall")) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("inWall")) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("cactus")) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("inFire")) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("onFire")) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("magic")) {
         return false;
      } else if (par1DamageSource.getDamageType().equals("starve")) {
         return false;
      } else {
         Entity e = par1DamageSource.getTrueSource();
         if (e != null && e instanceof EntityLiving) {
            this.setAttackTarget((EntityLiving)e);
            this.faceEntity(e, 20.0F, 20.0F);
         }

         return super.attackEntityFrom(par1DamageSource, par2);
      }
   }

   @Override
   public void fall(float par1, float damageMultiplier) {
   }

   @Override
   protected void updateFallState(double par1, boolean par3, net.minecraft.block.state.IBlockState state, BlockPos pos) {
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
      if (this.getControllingPassenger() != null) {
         this.boatPosRotationIncrements = par9 + 8;
      } else {
         this.boatPosRotationIncrements = par9 + 6;
      }

      this.boatX = par1;
      this.boatY = par3;
      this.boatZ = par5;
      this.boatYaw = par7;
      this.boatPitch = par8;
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double par1, double par3, double par5) {
      if (this.getControllingPassenger() == null) {
         super.setVelocity(par1, par3, par5);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.setFire(0);
      if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL
         && !this.world.isRemote
         && this.getControllingPassenger() != null
         && this.world.rand.nextInt(50) == 0) {
         this.feetFindSomethingToHit();
      }

      if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL
         && !this.world.isRemote
         && this.getControllingPassenger() != null
         && this.world.rand.nextInt(9) == 0) {
         EntityLivingBase e = null;
         e = this.findSomethingToAttack(1.0F, true);
         if (e != null) {
            if (this.getDistanceSq(e) < (6.0F + e.width / 2.0F) * (6.0F + e.width / 2.0F)) {
               this.setAttacking(1);
               this.attackEntityAsMob(e);
            }
         } else {
            this.setAttacking(0);
         }
      }

      float f = 4.0F;
      float dx = (float)(f * Math.cos(Math.toRadians(this.rotationYaw - 80.0F)));
      float dz = (float)(f * Math.sin(Math.toRadians(this.rotationYaw - 80.0F)));
      float dx2 = (float)(f * Math.cos(Math.toRadians(this.rotationYaw - 90.0F)));
      float dz2 = (float)(f * Math.sin(Math.toRadians(this.rotationYaw - 90.0F)));
      if (this.world.rand.nextInt(18) == 0) {
         this.world
            .spawnParticle(EnumParticleTypes.FLAME,
               this.posX + dx,
               this.posY + 0.5,
               this.posZ + dz,
               dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0F,
               dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F
            );
      }

      if (this.world.rand.nextInt(7) == 0) {
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
               this.posX + dx,
               this.posY + 0.5,
               this.posZ + dz,
               dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0F,
               dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F
            );
      }

      if (this.world.rand.nextInt(16) == 0) {
         this.world
            .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
               this.posX + dx,
               this.posY + 0.5,
               this.posZ + dz,
               dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0F,
               dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F
            );
      }

      dx = (float)(f * Math.cos(Math.toRadians(this.rotationYaw - 100.0F)));
      dz = (float)(f * Math.sin(Math.toRadians(this.rotationYaw - 100.0F)));
      if (this.world.rand.nextInt(18) == 0) {
         this.world
            .spawnParticle(EnumParticleTypes.FLAME,
               this.posX + dx,
               this.posY + 0.5,
               this.posZ + dz,
               dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0F,
               dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F
            );
      }

      if (this.world.rand.nextInt(7) == 0) {
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
               this.posX + dx,
               this.posY + 0.5,
               this.posZ + dz,
               dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0F,
               dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F
            );
      }

      if (this.world.rand.nextInt(16) == 0) {
         this.world
            .spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
               this.posX + dx,
               this.posY + 0.5,
               this.posZ + dz,
               dx2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F,
               (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0F,
               dz2 / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0F
            );
      }
   }

   public void onLivingUpdate() {
      if (this.getControllingPassenger() instanceof EntityPlayer) {
         EntityPlayer rider = (EntityPlayer)this.getControllingPassenger();
         float forward = com.electrifiedded.orespawn.network.RiderInputState.getForward(rider);
         if (Math.abs(forward) < 0.001F) forward = rider.moveForward;
         float strafe = com.electrifiedded.orespawn.network.RiderInputState.getStrafing(rider);
         this.riderThrottle += (forward - this.riderThrottle) * 0.2F;
         if (Math.abs(this.riderThrottle) < 0.01F) this.riderThrottle = 0.0F;
         this.getNavigator().clearPath();
         this.rotationYaw = rider.rotationYaw;
         this.renderYawOffset = this.rotationYaw;
         this.rotationYawHead = this.rotationYaw;
         this.setAIMoveSpeed(0.35F);
         if (this.collidedHorizontally && this.onGround && this.riderThrottle > 0.0F) this.motionY = 0.42;
         this.travel(strafe * 0.5F, 0.0F, this.riderThrottle);
         this.updateLegs();
         return;
      }
      this.riderThrottle = 0.0F;

      List list = null;
      double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
      double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
      double obstruction_factor = 0.0;
      double relative_g = 0.0;
      double max_speed = 0.3;
      double gh = 1.75;
      int dist = 2;
      if (!this.isDead) {
         if (this.getControllingPassenger() == null) {
            super.onLivingUpdate();
         }

         if (this.motionY > 0.85F) {
            this.motionY = 0.85F;
         }

         if (this.motionY < -0.85F) {
            this.motionY = -0.85F;
         }

         if (this.motionX < -1.25) {
            this.motionX = -1.25;
         }

         if (this.motionX > 1.25) {
            this.motionX = 1.25;
         }

         if (this.motionZ < -1.25) {
            this.motionZ = -1.25;
         }

         if (this.motionZ > 1.25) {
            this.motionZ = 1.25;
         }

         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.rideTicker = this.rideTicker + this.world.rand.nextInt(3);
         if (this.playing > 0) {
            this.playing--;
         }

         if (this.getControllingPassenger() != null && this.playing == 0 && this.world.rand.nextInt(80) == 1) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("robotspider"), net.minecraft.util.SoundCategory.NEUTRAL, 0.35F, 1.0F);
            this.playing = 125;
         }

         if (this.world.isRemote) {
            if (this.getControllingPassenger() == null) {
               Block bid = this.world
                  .getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh + 1.0F), (int)this.posZ)).getBlock();
               if (bid == Blocks.AIR) {
                  bid = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
               }

               if (bid != Blocks.AIR
                  && bid != Blocks.WATER
                  && bid != Blocks.FLOWING_WATER
                  && bid != Blocks.LAVA
                  && bid != Blocks.FLOWING_LAVA) {
                  this.motionY += 0.12;
                  this.posY += 0.12;
                  this.boatY += 0.12;
               } else {
                  this.motionY -= 0.002;
               }
            }

            if (this.boatPosRotationIncrements > 0) {
               double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
               double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
               double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
               this.setPosition(d4, d5, d11);
               this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
               double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
               if (this.getControllingPassenger() != null) {
                  d10 = MathHelper.wrapDegrees((double)this.getControllingPassenger().rotationYaw - this.rotationYaw);
               }

               this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
               this.setRotation(this.rotationYaw, this.rotationPitch);
               this.boatPosRotationIncrements--;
            } else {
               double d4 = this.posX + this.motionX;
               double d5 = this.posY + this.motionY;
               double d11 = this.posZ + this.motionZ;
               this.setPosition(d4, d5, d11);
               this.motionX *= 0.99;
               this.motionY *= 0.95;
               this.motionZ *= 0.99;
            }

            this.updateLegs();
         } else {
            if (this.getControllingPassenger() != null) {
               gh = 2.25;
               Block bidx = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
               if (bidx != Blocks.AIR
                  && bidx != Blocks.WATER
                  && bidx != Blocks.FLOWING_WATER
                  && bidx != Blocks.LAVA
                  && bidx != Blocks.FLOWING_LAVA) {
                  this.motionY += 0.06;
                  this.posY += 0.03;
               } else {
                  this.motionY -= 0.02;
               }
            } else {
               Block bidx = this.world
                  .getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh + 1.0F), (int)this.posZ)).getBlock();
               if (bidx == Blocks.AIR) {
                  bidx = this.world.getBlockState(new BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
               }

               if (bidx != Blocks.AIR
                  && bidx != Blocks.WATER
                  && bidx != Blocks.FLOWING_WATER
                  && bidx != Blocks.LAVA
                  && bidx != Blocks.FLOWING_LAVA) {
                  this.motionY += 0.15;
                  this.posY += 0.15;
                  this.boatY += 0.15;
               } else {
                  this.motionY -= 0.002;
               }
            }

            if (this.getControllingPassenger() != null) {
               EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();
               obstruction_factor = 0.0;
               int var78 = 3;
               var78 += (int)(velocity * 6.0);

               for (int k = 1; k < var78; k++) {
                  for (int i = 1; i < var78 * 2; i++) {
                     for (int j = -90; j <= 90; j += 30) {
                        double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F + j));
                        double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F + j));
                        Block var77 = this.world
                           .getBlockState(new BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + dz))).getBlock();
                        if (var77 != Blocks.AIR
                           && var77 != Blocks.WATER
                           && var77 != Blocks.FLOWING_WATER
                           && var77 != Blocks.LAVA
                           && var77 != Blocks.FLOWING_LAVA) {
                           obstruction_factor += 0.02;
                        }
                     }
                  }
               }

               this.motionY += obstruction_factor * 0.05;
               this.posY += obstruction_factor * 0.05;
               double d4 = this.getControllingPassenger().rotationYaw;
               d4 %= 360.0;

               while (d4 < 0.0) {
                  d4 += 360.0;
               }

               double d5 = this.rotationYaw;
               d5 %= 360.0;

               while (d5 < 0.0) {
                  d5 += 360.0;
               }

               relative_g = (d4 - d5) % 180.0;

               while (relative_g < 0.0) {
                  relative_g += 180.0;
               }

               if (relative_g > 90.0) {
                  relative_g -= 180.0;
               }

               if (velocity > 0.01) {
                  d4 = 1.85 - velocity;
                  d4 = Math.abs(d4);
                  if (d4 < 0.01) {
                     d4 = 0.01;
                  }

                  if (d4 > 0.9) {
                     d4 = 0.9;
                  }

                  this.rotationYaw = this.getControllingPassenger().rotationYaw + (float)(relative_g * d4);
               } else {
                  this.rotationYaw = this.getControllingPassenger().rotationYaw;
               }

               relative_g = Math.abs(relative_g) * velocity;
               if (relative_g > 50.0) {
                  relative_g = 0.0;
               }

               this.rotationPitch = 0.0F;
               this.setRotation(this.rotationYaw, this.rotationPitch);
               double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
               double rr = Math.atan2(this.getControllingPassenger().motionZ, this.getControllingPassenger().motionX);
               double rhm = Math.atan2(this.motionZ, this.motionX);
               double rhdir = Math.toRadians((this.getControllingPassenger().rotationYaw + 90.0F) % 360.0F);
               double rt = 0.0;
               double pi = 3.1415926545;
               double deltav = 0.0;
               float im = pp.moveForward;
               if (Math.abs(im) < 0.001F) {
                  im = com.electrifiedded.orespawn.network.RiderInputState.getForward(pp);
               }
               double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
               if (rdv > pi) {
                  rdv -= pi * 2.0;
               }

               rdv = Math.abs(rdv);
               if (Math.abs(newvelocity) < 0.01) {
                  rdv = 0.0;
               }

               if (rdv > 1.5) {
                  newvelocity = -newvelocity;
               }

               if (Math.abs(im) > 0.001F) {
                  if (im > 0.0F) {
                     deltav = 0.05;
                  } else {
                     max_speed = 0.25;
                     deltav = -0.05;
                  }

                  newvelocity += deltav;
                  if (newvelocity >= 0.0) {
                     if (newvelocity > max_speed) {
                        newvelocity = max_speed;
                     }

                     this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                     this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                  } else {
                     if (newvelocity < -max_speed) {
                        newvelocity = -max_speed;
                     }

                     newvelocity = -newvelocity;
                     this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                     this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity;
                  }
               } else if (newvelocity >= 0.0) {
                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity;
               } else {
                  this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
                  this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0);
               }

               this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
               this.motionX *= 0.98;
               this.motionY *= 0.98;
               this.motionZ *= 0.98;
            }

            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8;
            this.motionY *= 0.98;
            this.motionZ *= 0.8;
            if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {
               if (this.getControllingPassenger() != null) this.getControllingPassenger().dismountRidingEntity();
            }
         }
      }
   }

   public void goThisWay(double mx, double mz) {
      this.motionX = mx;
      this.motionZ = mz;
   }

   public boolean ableToCauseSkullDrop() {
      return this.getControllingPassenger() == null;
   }

   @Override
   public boolean canPassengerSteer() {
      return this.getControllingPassenger() instanceof EntityPlayer;
   }

   @Override
   public Entity getControllingPassenger() {
      for (Entity passenger : this.getPassengers()) {
         if (passenger instanceof EntityPlayer) return passenger;
      }
      return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
   }

   @Override
   protected boolean canFitPassenger(Entity passenger) {
      if (!(passenger instanceof EntityPlayer)) return false;
      for (Entity current : this.getPassengers()) if (current instanceof EntityPlayer) return false;
      return true;
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("AntRobotOwned", this.owned);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.owned = par1NBTTagCompound.getInteger("AntRobotOwned");
   }

   public float getShadowSize() {
      return 0.95F;
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if (var2 != null && var2.getCount() <= 0) {
         par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
         var2 = null;
      }

      if (this.owned == 0) {
         return true;
      } else if (var2 != null && var2.getItem() == Items.IRON_INGOT && par1EntityPlayer.getDistanceSq(this) < 25.0) {
         if (!this.world.isRemote) {
            float f = this.getMaxHealth() - this.getHealth();
            if (f > 100.0F) {
               f = 100.0F;
            }

            if (f > 0.0F) {
               this.heal(f);
            }
         }

         if (!par1EntityPlayer.capabilities.isCreativeMode) {
            var2.shrink(1);
            if (var2.getCount() <= 0) {
               par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            }
         }

         return true;
      } else if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger() != par1EntityPlayer) {
         return true;
      } else {
          if (this.getControllingPassenger() == null && par1EntityPlayer.getDistanceSq(this) < 16.0) {
             par1EntityPlayer.startRiding(this);
             if (!this.world.isRemote) {
                this.world.playSound(null, this.posX, this.posY, this.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("robotspidermount"), net.minecraft.util.SoundCategory.NEUTRAL, 0.45F, 1.0F);
             }
          }

         return true;
      }
   }

   private void feetFindSomethingToHit() {
      if (OreSpawn.PlayNicely == 0) {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(10.0, 8.0, 10.0));
         Iterator var2 = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;

         while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (this.feetisSuitableTarget(var4, false)) {
               this.feetattackEntityAsMob(var4);
            }
         }
      }
   }

   private boolean feetisSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (par1EntityLiving instanceof AntRobot) {
         return false;
      } else if (par1EntityLiving == this.getControllingPassenger()) {
         return false;
      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else {
         float d1 = (float)(par1EntityLiving.posX - this.posX);
         float d2 = (float)(par1EntityLiving.posY - this.posY);
         float d3 = (float)(par1EntityLiving.posZ - this.posZ);
         float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
         if (dd > 9.0F) {
            return false;
         } else if (dd < 6.0F) {
            return false;
         } else if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            return !p.capabilities.isCreativeMode;
         } else {
            return true;
         }
      }
   }

   public boolean feetattackEntityAsMob(Entity par1Entity) {
      boolean ret = false;
      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
         double ks = 0.6;
         double inair = 0.1;
         float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
         ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawn.AntRobot_stats.attack / 10.0F);
         if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
            inair *= 2.0;
         }

         if (ret) {
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
         }
      }

      return ret;
   }

   private EntityLivingBase findSomethingToAttack(float distmul, boolean dircheck) {
      if (OreSpawn.PlayNicely != 0) {
         return null;
      } else {
         List<Entity> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(12.0 * distmul, 12.0, 12.0 * distmul));
         Iterator var2x = var5.iterator();
         Entity var3 = null;
         EntityLivingBase var4 = null;

         while (var2x.hasNext()) {
            var3 = (Entity)var2x.next();
            var4 = (EntityLivingBase)var3;
            if (this.isSuitableTarget(var4, dircheck)) {
               return var4;
            }
         }

         return null;
      }
   }

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == this) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else if (par1EntityLiving instanceof AntRobot) {
         return false;
      } else if (par1EntityLiving == this.getControllingPassenger()) {
         return false;
      } else if (MyUtils.isIgnoreable(par1EntityLiving)) {
         return false;
      } else if (!this.getEntitySenses().canSee(par1EntityLiving)) {
         return false;
      } else {
         if (par2) {
            double rr = Math.atan2(par1EntityLiving.posZ - this.posZ, par1EntityLiving.posX - this.posX);
            double rhdir = Math.toRadians((this.rotationYaw + 90.0F) % 360.0F);
            double pi = 3.1415926545;
            double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
            if (rdd > pi) {
               rdd -= pi * 2.0;
            }

            rdd = Math.abs(rdd);
            if (this.getDistanceSq(par1EntityLiving) < 36.0) {
               return true;
            }

            if (rdd > 0.75) {
               return false;
            }
         }

         if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            return !p.capabilities.isCreativeMode;
         } else {
            return true;
         }
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      boolean ret = false;
      if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
         double ks = 0.7;
         double inair = 0.1;
         float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
         ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawn.AntRobot_stats.attack);
         if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
            inair *= 2.0;
         }

         if (ret) {
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
         }
      }

      return ret;
   }

   public int getAttacking() {
      return this.dataManager.get(DW_20);
   }

   public void setAttacking(int par1) {
      this.dataManager.set(DW_20,  par1);
   }

   protected Item getDropItem() {
      return null;
   }

   private ItemStack dropItemRand(Item index, int par1) {
      EntityItem var3 = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3 = new EntityItem(
         this.world,
         this.posX + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         this.posY + 1.0,
         this.posZ + OreSpawn.OreSpawnRand.nextInt(2) - OreSpawn.OreSpawnRand.nextInt(2),
         is
      );
      if (var3 != null) {
         this.world.spawnEntity(var3);
      }

      return is;
   }

   protected void dropFewItems(boolean par1, int par2) {
      ItemStack is = null;
      int i = 7 + this.world.rand.nextInt(7);

      for (int var4 = 0; var4 < i; var4++) {
         int var3 = this.world.rand.nextInt(12);
         switch (var3) {
            case 0:
               is = this.dropItemRand(Items.REDSTONE, 1);
               break;
            case 1:
               is = this.dropItemRand(Items.REPEATER, 1);
               break;
            case 2:
               is = this.dropItemRand(Items.COMPARATOR, 1);
               break;
            case 3:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 1);
               break;
            case 4:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.DISPENSER), 1);
               break;
            case 5:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.STICKY_PISTON), 1);
               break;
            case 6:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.PISTON), 1);
               break;
            case 7:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.LEVER), 1);
               break;
            case 8:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 1);
               break;
            case 9:
               is = this.dropItemRand(Item.getItemFromBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), 1);
               break;
            case 10:
               is = this.dropItemRand(Items.IRON_INGOT, 1);
         }
      }
   }

   @Override
   public boolean processInteract(EntityPlayer player, net.minecraft.util.EnumHand hand) {
      return hand == net.minecraft.util.EnumHand.MAIN_HAND && interact(player);
   }

    @Override
    public void updatePassenger(net.minecraft.entity.Entity passenger) {
        if (this.isPassenger(passenger) && passenger == this.getControllingPassenger()) {
            this.updateRiderPosition();
        } else {
            super.updatePassenger(passenger);
        }
    }
}

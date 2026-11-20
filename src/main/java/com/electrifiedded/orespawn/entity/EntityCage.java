package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCage extends EntityThrowable {
   private float my_rotation = 0.0F;
   public int my_index = 160;
   private World throwerWorld = null;
   private EntityPlayer thrower = null;

   public EntityCage(World par1World) {
      super(par1World);
      this.throwerWorld = par1World;
   }

   public EntityCage(World par1World, int i) {
      super(par1World);
      this.throwerWorld = par1World;
      this.my_index = i;
   }

   public EntityCage(World par1World, EntityPlayer par2EntityLiving, int i) {
      super(par1World, par2EntityLiving);
      this.shoot(par2EntityLiving, par2EntityLiving.rotationPitch, par2EntityLiving.rotationYaw, 0.0F, 1.5F, 1.0F);
      this.throwerWorld = par1World;
      this.thrower = par2EntityLiving;
      this.my_index = i;
      if (this.thrower.world != null) {
         this.throwerWorld = this.thrower.world;
      }
   }

   public int getCageIndex() {
      return this.my_index;
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (this.world.isRemote) {
         return;
      }
      if (par1MovingObjectPosition.entityHit != null && this.rand.nextInt(10) >= 2) {
         if (this.throwerWorld != null) {
            for (int var3 = 0; var3 < 4; var3++) {
               this.throwerWorld
                  .spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                     par1MovingObjectPosition.entityHit.posX,
                     par1MovingObjectPosition.entityHit.posY + 0.25,
                     par1MovingObjectPosition.entityHit.posZ,
                     0.0,
                     0.0,
                     0.0
                  );
               this.throwerWorld
                  .spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                     par1MovingObjectPosition.entityHit.posX,
                     par1MovingObjectPosition.entityHit.posY + 0.25,
                     par1MovingObjectPosition.entityHit.posZ,
                     0.0,
                     0.0,
                     0.0
                  );
               this.throwerWorld
                  .spawnParticle(EnumParticleTypes.REDSTONE,
                     par1MovingObjectPosition.entityHit.posX,
                     par1MovingObjectPosition.entityHit.posY + 0.25,
                     par1MovingObjectPosition.entityHit.posZ,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (this.thrower != null) {
               this.throwerWorld.playSound(null, this.thrower.posX, this.thrower.posY, this.thrower.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.get("entity.generic.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.5F);
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityPlayer) {
            if (!this.world.isRemote) {
               this.dropItem(OreSpawn.CageEmpty, 1);
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof SpiderDriver) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSpiderDriver, 1);
         } else if (par1MovingObjectPosition.entityHit instanceof EntitySpider) {
            if (par1MovingObjectPosition.entityHit instanceof EntityCaveSpider) {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedCaveSpider, 1);
            } else {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedSpider, 1);
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof Crab) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCrab, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityBat) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBat, 2);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityPig) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedPig, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntitySquid) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSquid, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityChicken) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedChicken, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityCreeper) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCreeper, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityHorse) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedHorse, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof net.minecraft.entity.monster.EntityWitherSkeleton) {
            this.dropItem(OreSpawn.CagedWitherSkeleton, 1);
            par1MovingObjectPosition.entityHit.setDead();
         } else if (par1MovingObjectPosition.entityHit instanceof EntitySkeleton) {
            this.dropItem(OreSpawn.CagedSkeleton, 1);
            par1MovingObjectPosition.entityHit.setDead();
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityZombie) {
            if (par1MovingObjectPosition.entityHit instanceof EntityPigZombie) {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedZombiePigman, 1);
            } else {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedZombie, 1);
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof EntitySlime) {
            if (par1MovingObjectPosition.entityHit instanceof EntityMagmaCube) {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedMagmaCube, 1);
            } else {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedSlime, 1);
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityGhast) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedGhast, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityEnderman) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedEnderman, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntitySilverfish) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSilverfish, 2);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityWitch) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedWitch, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntitySheep) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSheep, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityWolf) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedWolf, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityOcelot) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedOcelot, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityBlaze) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBlaze, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Girlfriend) {
            Girlfriend gf = (Girlfriend)par1MovingObjectPosition.entityHit;
            if (!gf.isTamed()) {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedGirlfriend, 1);
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof Boyfriend) {
            Boyfriend gf = (Boyfriend)par1MovingObjectPosition.entityHit;
            if (!gf.isTamed()) {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedBoyfriend, 1);
            }
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityDragon) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            EntityDragon dr = (EntityDragon)par1MovingObjectPosition.entityHit;
            dr.setDead();
            this.dropItem(OreSpawn.CagedEnderDragon, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof MultiPartEntityPart) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            MultiPartEntityPart dp = (MultiPartEntityPart)par1MovingObjectPosition.entityHit;
            EntityDragon dr = (EntityDragon)dp.parent;
            dr.setDead();
            this.dropItem(OreSpawn.CagedEnderDragon, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntitySnowman) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSnowGolem, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityIronGolem) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedIronGolem, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityWither) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedWitherBoss, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof CrystalCow) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCrystalCow, 1);
            if (!this.world.isRemote) {
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof EnchantedCow) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedEnchantedCow, 1);
            if (!this.world.isRemote) {
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof GoldCow) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedGoldCow, 1);
            if (!this.world.isRemote) {
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof RedCow) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedRedCow, 1);
            if (!this.world.isRemote) {
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityCow) {
            if (par1MovingObjectPosition.entityHit instanceof EntityMooshroom) {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedMooshroom, 1);
            } else {
               par1MovingObjectPosition.entityHit.setDead();
               this.dropItem(OreSpawn.CagedCow, 1);
            }

            if (!this.world.isRemote) {
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityVillager) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedVillager, 1);
            if (!this.world.isRemote) {
               this.setDead();
            }

            return;
         }

         if (par1MovingObjectPosition.entityHit instanceof Mothra) {
            if (this.rand.nextInt(10) < 4) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedMOTHRA, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Alosaurus) {
            if (this.rand.nextInt(10) < 4) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedAlo, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Cryolophosaurus) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCryo, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Camarasaurus) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCama, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof VelocityRaptor) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedVelo, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Hydrolisc) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedHydro, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Basilisk) {
            if (this.rand.nextInt(10) < 6) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBasil, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Dragonfly) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedDragonfly, 2);
         }

         if (par1MovingObjectPosition.entityHit instanceof EmperorScorpion) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedEmperorScorpion, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Cephadrome) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCephadrome, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Dragon) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedDragon, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Scorpion) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedScorpion, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof CaveFisher) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCaveFisher, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Spyro) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSpyro, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Baryonyx) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBaryonyx, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof GammaMetroid) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedGammaMetroid, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Cockateil) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCockateil, 4);
         }

         if (par1MovingObjectPosition.entityHit instanceof AttackSquid) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedAttackSquid, 6);
         }

         if (par1MovingObjectPosition.entityHit instanceof Kyuubi) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedKyuubi, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof WaterDragon) {
            if (this.rand.nextInt(10) < 6) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedWaterDragon, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Kraken) {
            if (this.rand.nextInt(100) < 95) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedKraken, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Lizard) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedLizard, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Alien) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedAlien, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Bee) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBee, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Firefly) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedFirefly, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Chipmunk) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedChipmunk, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Gazelle) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedGazelle, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Ostrich) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedOstrich, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof TrooperBug) {
            if (this.rand.nextInt(10) < 6) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedTrooper, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof SpitBug) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSpit, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof StinkBug) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedStink, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof CreepingHorror) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCreepingHorror, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof TerribleTerror) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedTerribleTerror, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof CliffRacer) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCliffRacer, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Triffid) {
            if (this.rand.nextInt(10) < 6) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedTriffid, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof PitchBlack) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedPitchBlack, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof LurkingTerror) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedLurkingTerror, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof WormSmall) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSmallWorm, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof WormMedium) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedMediumWorm, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Cassowary) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCassowary, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof CloudShark) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCloudShark, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof GoldFish) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedGoldFish, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof LeafMonster) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedLeafMonster, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof WormLarge) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedLargeWorm, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EnderKnight) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedEnderKnight, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EnderReaper) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedEnderReaper, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Beaver) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBeaver, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Urchin) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedUrchin, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Flounder) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedFlounder, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Skate) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSkate, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Rotator) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedRotator, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Peacock) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedPeacock, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Fairy) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedFairy, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof DungeonBeast) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedDungeonBeast, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Vortex) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedVortex, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Rat) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedRat, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Whale) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedWhale, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Irukandji) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedIrukandji, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Stinky) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedStinky, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Mantis) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedMantis, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof TRex) {
            if (this.rand.nextInt(10) < 4) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedTRex, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof HerculesBeetle) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedHercules, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof EasterBunny) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedEasterBunny, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof CaterKiller) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCaterKiller, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Molenoid) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedMolenoid, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof SeaMonster) {
            if (this.rand.nextInt(10) < 3) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSeaMonster, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof SeaViper) {
            if (this.rand.nextInt(10) < 4) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedSeaViper, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof RubberDucky) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedRubberDucky, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Leon) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedLeon, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Hammerhead) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedHammerhead, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof BandP) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCriminal, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Cricket) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedCricket, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Frog) {
            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedFrog, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Brutalfly) {
            if (this.rand.nextInt(10) < 5) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedBrutalfly, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Nastysaurus) {
            if (this.rand.nextInt(10) < 7) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedNastysaurus, 1);
         }

         if (par1MovingObjectPosition.entityHit instanceof Pointysaurus) {
            if (this.rand.nextInt(10) < 2) {
               if (!this.world.isRemote) {
                  this.dropItem(OreSpawn.CageEmpty, 1);
                  this.setDead();
               }

               return;
            }

            par1MovingObjectPosition.entityHit.setDead();
            this.dropItem(OreSpawn.CagedPointysaurus, 1);
         }
      } else if (!this.world.isRemote) {
         this.dropItem(OreSpawn.CageEmpty, 1);
      }

      if (!this.world.isRemote) {
         this.setDead();
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.my_rotation += 20.0F;

      while (this.my_rotation > 360.0F) {
         this.my_rotation -= 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch = this.my_rotation;
   }
}

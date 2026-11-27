package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EasterBunny extends EntityAnimal {
   private float moveSpeed = 0.45F;

   public EasterBunny(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.75F);
      this.moveSpeed = 0.45F;
      this.isImmuneToFire = true;
      this.experienceValue = 5;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIMate(this, 1.0));
      this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.0, 1.4F));
      this.tasks.addTask(4, new EntityAIPanic(this, 1.5));
      this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.tasks.addTask(6, new MyEntityAIWanderALot(this, 16, 1.0));
      this.tasks.addTask(7, new EntityAILookIdle(this));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void onUpdate() {
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
      super.onUpdate();
   }

   public boolean getCanSpawnHere() {
      if (this.posY < 50.0) {
         return false;
      } else if (!this.world.isDaytime()) {
         return false;
      } else {
         EasterBunny target = null;
         target = (EasterBunny)this.world.findNearestEntityWithinAABB(EasterBunny.class, this.getEntityBoundingBox().grow(32.0, 8.0, 32.0), this);
         return target == null;
      }
   }

   public boolean ableToCauseSkullDrop() {
      return true;
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   public int mygetMaxHealth() {
      return 10;
   }

   @Override

   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return null;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   protected SoundEvent getDeathSound() {
      return OreSpawnSoundEvents.get("duck_hurt");
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected Item getDropItem() {
      return Items.CHICKEN;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = 0;
      var3 = this.rand.nextInt(3);
      var3 += 2;

      for (int var4 = 0; var4 < var3; var4++) {
         this.dropItem(Items.CHICKEN, 1);
      }
   }

   protected void handleJumpWater() {
      if (this.world.rand.nextInt(200) == 1) {
         this.setRevengeTarget(null);
      }

      super.handleJumpWater();
   }

   // Egg laying used to live in handleJumpWater(), which 1.12 only calls while swimming,
   // so bunnies never laid eggs on land. Run it every server AI tick instead.
   protected void updateAITasks() {
      super.updateAITasks();
      if (!this.world.isRemote && !this.isChild() && this.world.rand.nextInt(600) == 1) {
         this.LayAnEgg(1 + this.world.rand.nextInt(3));
      }
   }

   private ItemStack LayAnEgg(int par1) {
      EntityItem var3 = null;
      int i = 0;
      Item index = null;
      int val = 0;
      ItemStack is = null;
      i = this.world.rand.nextInt(115);
      switch (i) {
         case 5:
            index = OreSpawn.GirlfriendEgg;
            break;
         case 6:
            index = OreSpawn.RedCowEgg;
            break;
         case 7:
            index = OreSpawn.GoldCowEgg;
            break;
         case 8:
            index = OreSpawn.EnchantedCowEgg;
            break;
         case 9:
            index = OreSpawn.MOTHRAEgg;
            break;
         case 10:
            index = OreSpawn.AloEgg;
            break;
         case 11:
            index = OreSpawn.CryoEgg;
            break;
         case 12:
            index = OreSpawn.CamaEgg;
            break;
         case 13:
            index = OreSpawn.VeloEgg;
            break;
         case 14:
            index = OreSpawn.HydroEgg;
            break;
         case 15:
            index = OreSpawn.BasilEgg;
            break;
         case 16:
            index = OreSpawn.DragonflyEgg;
            break;
         case 17:
            index = OreSpawn.EmperorScorpionEgg;
            break;
         case 18:
            index = OreSpawn.ScorpionEgg;
            break;
         case 19:
            index = OreSpawn.CaveFisherEgg;
            break;
         case 20:
            index = OreSpawn.SpyroEgg;
            break;
         case 21:
            index = OreSpawn.BaryonyxEgg;
            break;
         case 22:
            index = OreSpawn.GammaMetroidEgg;
            break;
         case 23:
            index = OreSpawn.CockateilEgg;
            break;
         case 24:
            index = OreSpawn.KyuubiEgg;
            break;
         case 25:
            index = OreSpawn.AlienEgg;
            break;
         case 26:
            index = OreSpawn.AttackSquidEgg;
            break;
         case 27:
            index = OreSpawn.WaterDragonEgg;
            break;
         case 28:
            index = OreSpawn.CephadromeEgg;
            break;
         case 29:
            index = OreSpawn.DragonEgg;
            break;
         case 30:
            index = OreSpawn.KrakenEgg;
            break;
         case 31:
            index = OreSpawn.LizardEgg;
            break;
         case 32:
            index = OreSpawn.BeeEgg;
            break;
         case 33:
            index = OreSpawn.TrooperBugEgg;
            break;
         case 34:
            index = OreSpawn.SpitBugEgg;
            break;
         case 35:
            index = OreSpawn.StinkBugEgg;
            break;
         case 36:
            index = OreSpawn.OstrichEgg;
            break;
         case 37:
            index = OreSpawn.GazelleEgg;
            break;
         case 38:
            index = OreSpawn.ChipmunkEgg;
            break;
         case 39:
            index = OreSpawn.CreepingHorrorEgg;
            break;
         case 40:
            index = OreSpawn.TerribleTerrorEgg;
            break;
         case 41:
            index = OreSpawn.CliffRacerEgg;
            break;
         case 42:
            index = OreSpawn.TriffidEgg;
            break;
         case 43:
            index = OreSpawn.PitchBlackEgg;
            break;
         case 44:
            index = OreSpawn.LurkingTerrorEgg;
            break;
         case 45:
            index = OreSpawn.GodzillaEgg;
            break;
         case 46:
            index = OreSpawn.SmallWormEgg;
            break;
         case 47:
            index = OreSpawn.MediumWormEgg;
            break;
         case 48:
            index = OreSpawn.LargeWormEgg;
            break;
         case 49:
            index = OreSpawn.CassowaryEgg;
            break;
         case 50:
            index = OreSpawn.CloudSharkEgg;
            break;
         case 51:
            index = OreSpawn.GoldFishEgg;
            break;
         case 52:
            index = OreSpawn.LeafMonsterEgg;
            break;
         case 53:
            index = OreSpawn.TshirtEgg;
            break;
         case 54:
            index = OreSpawn.EnderKnightEgg;
            break;
         case 55:
            index = OreSpawn.EnderReaperEgg;
            break;
         case 56:
            index = OreSpawn.BeaverEgg;
            break;
         case 57:
            index = OreSpawn.RotatorEgg;
            break;
         case 58:
            index = OreSpawn.VortexEgg;
            break;
         case 59:
            index = OreSpawn.PeacockEgg;
            break;
         case 60:
            index = OreSpawn.FairyEgg;
            break;
         case 61:
            index = OreSpawn.DungeonBeastEgg;
            break;
         case 62:
            index = OreSpawn.RatEgg;
            break;
         case 63:
            index = OreSpawn.FlounderEgg;
            break;
         case 64:
            index = OreSpawn.WhaleEgg;
            break;
         case 65:
            index = OreSpawn.IrukandjiEgg;
            break;
         case 66:
            index = OreSpawn.SkateEgg;
            break;
         case 67:
            index = OreSpawn.UrchinEgg;
            break;
         case 68:
            index = OreSpawn.Robot1Egg;
            break;
         case 69:
            index = OreSpawn.Robot2Egg;
            break;
         case 70:
            index = OreSpawn.Robot3Egg;
            break;
         case 71:
            index = OreSpawn.Robot4Egg;
            break;
         case 72:
            index = OreSpawn.GhostEgg;
            break;
         case 73:
            index = OreSpawn.GhostSkellyEgg;
            break;
         case 74:
            index = OreSpawn.BrownAntEgg;
            break;
         case 75:
            index = OreSpawn.RedAntEgg;
            break;
         case 76:
            index = OreSpawn.RainbowAntEgg;
            break;
         case 77:
            index = OreSpawn.UnstableAntEgg;
            break;
         case 78:
            index = OreSpawn.TermiteEgg;
            break;
         case 79:
            index = OreSpawn.ButterflyEgg;
            break;
         case 80:
            index = OreSpawn.MothEgg;
            break;
         case 81:
            index = OreSpawn.MosquitoEgg;
            break;
         case 82:
            index = OreSpawn.FireflyEgg;
            break;
         case 83:
            index = OreSpawn.TRexEgg;
            break;
         case 84:
            index = OreSpawn.HerculesEgg;
            break;
         case 85:
            index = OreSpawn.MantisEgg;
            break;
         case 86:
            index = OreSpawn.StinkyEgg;
            break;
         case 87:
            index = OreSpawn.Robot5Egg;
            break;
         case 88:
            index = OreSpawn.CoinEgg;
            break;
         case 89:
            index = OreSpawn.BoyfriendEgg;
            break;
         case 90:
            index = OreSpawn.TheKingEgg;
            break;
         case 91:
            index = OreSpawn.ThePrinceEgg;
            break;
         case 92:
            index = OreSpawn.EasterBunnyEgg;
            break;
         case 93:
            index = OreSpawn.MolenoidEgg;
            break;
         case 94:
            index = OreSpawn.SeaMonsterEgg;
            break;
         case 95:
            index = OreSpawn.SeaViperEgg;
            break;
         case 96:
            index = OreSpawn.CaterKillerEgg;
            break;
         case 97:
            index = OreSpawn.LeonEgg;
            break;
         case 98:
            index = OreSpawn.HammerheadEgg;
            break;
         case 99:
            index = OreSpawn.RubberDuckyEgg;
            break;
         case 100:
            index = OreSpawn.CrystalCowEgg;
            break;
         case 101:
            index = OreSpawn.CriminalEgg;
            break;
         case 102:
            index = OreSpawn.TheQueenEgg;
            break;
         case 103:
            index = OreSpawn.BrutalflyEgg;
            break;
         case 104:
            index = OreSpawn.NastysaurusEgg;
            break;
         case 105:
            index = OreSpawn.PointysaurusEgg;
            break;
         case 106:
            index = OreSpawn.CricketEgg;
            break;
         case 107:
            index = OreSpawn.ThePrincessEgg;
            break;
         case 108:
            index = OreSpawn.FrogEgg;
            break;
         case 109:
            index = OreSpawn.JefferyEgg;
            break;
         case 110:
            index = OreSpawn.AntRobotEgg;
            break;
         case 111:
            index = OreSpawn.SpiderRobotEgg;
            break;
         case 112:
            index = OreSpawn.SpiderDriverEgg;
            break;
         case 113:
            index = OreSpawn.CrabEgg;
            break;
         default:
            index = null;
      }

      if (index == null) {
         return null;
      } else {
         is = new ItemStack(index, par1, val);
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
   }

   protected boolean canDespawn() {
      if (this.isChild()) {
         this.enablePersistence();
         return false;
      } else {
         return !this.isNoDespawnRequired();
      }
   }

   public EntityAgeable createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public EasterBunny spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new EasterBunny(this.world);
   }

   public boolean isWheat(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack.getItem() == OreSpawn.MyCrystalApple;
   }
}

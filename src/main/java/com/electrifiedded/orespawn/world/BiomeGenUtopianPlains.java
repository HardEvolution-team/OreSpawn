package com.electrifiedded.orespawn.world;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;

public class BiomeGenUtopianPlains extends Biome {
   public BiomeGenUtopianPlains(Biome.BiomeProperties properties, int profile) {
      super(properties);
      addUtopiaCreatures();
      if (profile == 3) { setVillageProfile(); setVillageCreatures(); }
      if (profile == 4) { setIslandProfile(); setIslandCreatures(); }
      if (profile == 5) { setCrystalProfile(); setCrystalCreatures(); }
      if (profile == 6) { setChaosProfile(); setChaosCreatures(); }
   }

   public BiomeGenUtopianPlains(int dimension) {
      this();
      if (dimension == OreSpawn.DimensionID2) setExtremeProfile();
      if (dimension == OreSpawn.DimensionID3) setVillageProfile();
      if (dimension == OreSpawn.DimensionID4) setIslandProfile();
      if (dimension == OreSpawn.DimensionID5) setCrystalProfile();
      if (dimension == OreSpawn.DimensionID6) setChaosProfile();
   }

   private void setExtremeProfile() {
       this.decorator.treesPerChunk = -999;
   }

   private void setVillageProfile() {
      this.decorator.treesPerChunk = -999;
   }

   private void setIslandProfile() {
      this.decorator.treesPerChunk = -999;
      this.decorator.flowersPerChunk = -999;
      this.decorator.grassPerChunk = -999;
   }

   private void setCrystalProfile() {
      this.decorator.treesPerChunk = -999;
      this.decorator.flowersPerChunk = -999;
      this.decorator.grassPerChunk = -999;
      this.decorator.bigMushroomsPerChunk = -999;
      this.decorator.mushroomsPerChunk = -999;
      this.decorator.reedsPerChunk = -999;
   }

   private void setChaosProfile() {
      this.decorator.flowersPerChunk = 2;
      this.decorator.grassPerChunk = 4;
      this.decorator.treesPerChunk = 1;
      this.decorator.bigMushroomsPerChunk = -999;
      this.decorator.mushroomsPerChunk = -999;
      this.decorator.reedsPerChunk = -999;
   }

   public BiomeGenUtopianPlains() {
      super(new Biome.BiomeProperties("Utopian Plains").setBaseHeight(0.2F).setHeightVariation(0.2F));
      addUtopiaCreatures();
   }

   private void addUtopiaCreatures() {
      if (OreSpawn.GazelleEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(Gazelle.class, 10, 2, 4));
      }

      if (OreSpawn.FireflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Firefly.class, 15, 3, 6));
      }

      if (OreSpawn.GirlfriendEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(Girlfriend.class, 5, 2, 3));
      }

      if (OreSpawn.BoyfriendEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(Boyfriend.class, 5, 2, 3));
      }

      if (OreSpawn.CowEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(RedCow.class, 10, 4, 8));
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(GoldCow.class, 8, 2, 6));
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(EnchantedCow.class, 5, 2, 4));
      }

      if (OreSpawn.ButterflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityButterfly.class, 20, 3, 6));
      }

      if (OreSpawn.MothEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityLunaMoth.class, 10, 1, 5));
      }

      if (OreSpawn.ChipmunkEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Chipmunk.class, 3, 1, 2));
      }

      if (OreSpawn.CockateilEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cockateil.class, 10, 2, 4));
      }

      if (OreSpawn.GoldFishEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(GoldFish.class, 1, 1, 1));
      }

      if (OreSpawn.WhaleEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Whale.class, 1, 1, 1));
      }

      if (OreSpawn.FlounderEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Flounder.class, 2, 2, 4));
      }

      if (OreSpawn.CoinEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Coin.class, 2, 1, 1));
      }

      if (OreSpawn.CricketEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cricket.class, 5, 4, 6));
      }

      if (OreSpawn.FrogEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Frog.class, 5, 4, 6));
      }

      this.decorator.treesPerChunk = -999;
      this.decorator.flowersPerChunk = 4;
      this.decorator.grassPerChunk = 6;
   }

   public void setIslandCreatures() {
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.CREATURE).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.MONSTER).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.WATER_CREATURE).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      if (OreSpawn.ButterflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityButterfly.class, 5, 2, 6));
      }

      if (OreSpawn.CockateilEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cockateil.class, 4, 1, 2));
      }

      if (OreSpawn.MothEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityLunaMoth.class, 5, 2, 4));
      }

      if (OreSpawn.FireflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Firefly.class, 10, 4, 8));
      }

      if (OreSpawn.DragonEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Dragon.class, 1, 1, 2));
      }

      if (OreSpawn.StinkyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Stinky.class, 2, 1, 2));
      }

      if (OreSpawn.CliffRacerEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(CliffRacer.class, 20, 3, 6));
      }

      if (OreSpawn.CloudSharkEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(CloudShark.class, 1, 1, 1));
      }

      if (OreSpawn.GoldFishEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(GoldFish.class, 5, 2, 4));
      }

      if (OreSpawn.CreepingHorrorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(CreepingHorror.class, 60, 4, 8));
      }

      if (OreSpawn.TerribleTerrorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(TerribleTerror.class, 25, 3, 6));
      }

      if (OreSpawn.LurkingTerrorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(LurkingTerror.class, 1, 1, 1));
      }

      if (OreSpawn.PitchBlackEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(PitchBlack.class, 15, 3, 6));
      }

      if (OreSpawn.LeafMonsterEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(LeafMonster.class, 35, 2, 4));
      }

      if (OreSpawn.EnderReaperEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EnderReaper.class, 25, 2, 4));
      }

      if (OreSpawn.HerculesBeetleEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(HerculesBeetle.class, 5, 1, 2));
      }
   }

   public void setCrystalCreatures() {
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.CREATURE).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.MONSTER).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.WATER_CREATURE).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      if (OreSpawn.CowEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(CrystalCow.class, 1, 1, 4));
      }

      if (OreSpawn.FairyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Fairy.class, 10, 4, 8));
      }

      if (OreSpawn.PeacockEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Peacock.class, 5, 4, 8));
      }

      if (OreSpawn.MantisEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Mantis.class, 1, 1, 1));
      }

      if (OreSpawn.RotatorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Rotator.class, 4, 1, 2));
      }

      if (OreSpawn.VortexEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Vortex.class, 3, 1, 2));
      }

      if (OreSpawn.UrchinEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Urchin.class, 15, 2, 4));
      }

      if (OreSpawn.DungeonBeastEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(DungeonBeast.class, 30, 4, 6));
      }

      if (OreSpawn.RatEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Rat.class, 40, 4, 6));
      }

      if (OreSpawn.ButterflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityButterfly.class, 10, 2, 4));
      }

      if (OreSpawn.CockateilEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cockateil.class, 4, 1, 2));
      }

      if (OreSpawn.MothEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityLunaMoth.class, 4, 1, 2));
      }

      if (OreSpawn.WhaleEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Whale.class, 1, 1, 2));
      }

      if (OreSpawn.CrabEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Crab.class, 1, 1, 2));
      }

      if (OreSpawn.FlounderEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Flounder.class, 5, 6, 8));
      }

      if (OreSpawn.IrukandjiEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Irukandji.class, 4, 2, 3));
      }

      if (OreSpawn.SkateEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Skate.class, 2, 3, 6));
      }

      if (OreSpawn.FrogEnable != 0) {
         this.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new SpawnListEntry(Frog.class, 1, 3, 5));
      }

      this.decorator.flowersPerChunk = -999;
      this.decorator.grassPerChunk = -999;
      this.decorator.treesPerChunk = -999;
      this.decorator.bigMushroomsPerChunk = -999;
      this.decorator.mushroomsPerChunk = -999;
      this.decorator.reedsPerChunk = -999;
   }

   public void setVillageCreatures() {
      if (OreSpawn.Robot1Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot1.class, 25, 4, 8));
      }

      if (OreSpawn.Robot2Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot2.class, 16, 2, 8));
      }

      if (OreSpawn.Robot3Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot3.class, 12, 2, 4));
      }

      if (OreSpawn.Robot4Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot4.class, 8, 1, 2));
      }

      if (OreSpawn.Robot5Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot5.class, 20, 4, 8));
      }

      if (OreSpawn.JefferyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(GiantRobot.class, 8, 1, 2));
      }

      if (OreSpawn.SpiderDriverEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(SpiderDriver.class, 20, 3, 5));
      }

      if (OreSpawn.GodzillaEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Godzilla.class, 2, 1, 1));
      }

      if (OreSpawn.FireflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Firefly.class, 10, 3, 6));
      }

      if (OreSpawn.GirlfriendEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(Girlfriend.class, 1, 2, 3));
      }

      if (OreSpawn.BoyfriendEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(Boyfriend.class, 1, 2, 3));
      }

      if (OreSpawn.CowEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(RedCow.class, 8, 4, 8));
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(GoldCow.class, 6, 2, 6));
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(EnchantedCow.class, 4, 2, 4));
      }

      if (OreSpawn.ButterflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityButterfly.class, 25, 3, 6));
      }

      if (OreSpawn.MothEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityLunaMoth.class, 20, 1, 5));
      }

      if (OreSpawn.ChipmunkEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Chipmunk.class, 5, 1, 2));
      }

      if (OreSpawn.CockateilEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cockateil.class, 15, 2, 4));
      }

      if (OreSpawn.TshirtEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Tshirt.class, 2, 1, 1));
      }

      if (OreSpawn.CoinEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Coin.class, 2, 1, 1));
      }

      if (OreSpawn.CriminalEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(BandP.class, 15, 1, 2));
      }
   }

   public void setChaosCreatures() {
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.CREATURE).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.MONSTER).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.WATER_CREATURE).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT).clear();
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.getSpawnableList(net.minecraft.entity.EnumCreatureType.AMBIENT); // no-op
      this.decorator.flowersPerChunk = 2;
      this.decorator.grassPerChunk = 4;
      this.decorator.treesPerChunk = 1;
      this.decorator.bigMushroomsPerChunk = -999;
      this.decorator.mushroomsPerChunk = -999;
      this.decorator.reedsPerChunk = -999;
      if (OreSpawn.ButterflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityButterfly.class, 20, 3, 6));
      }

      if (OreSpawn.MothEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(EntityLunaMoth.class, 10, 1, 5));
      }

      if (OreSpawn.CockateilEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cockateil.class, 10, 2, 4));
      }

      if (OreSpawn.FireflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Firefly.class, 15, 3, 6));
      }

      if (OreSpawn.CliffRacerEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(CliffRacer.class, 30, 3, 6));
      }

      if (OreSpawn.CloudSharkEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(CloudShark.class, 2, 1, 1));
      }

      if (OreSpawn.GoldFishEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(GoldFish.class, 10, 2, 4));
      }

      if (OreSpawn.FairyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Fairy.class, 5, 2, 4));
      }

      if (OreSpawn.BaryonyxEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Baryonyx.class, 2, 2, 4));
      }

      if (OreSpawn.BeeEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Bee.class, 2, 2, 4));
      }

      if (OreSpawn.CassowaryEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Cassowary.class, 2, 2, 4));
      }

      if (OreSpawn.DragonflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Dragonfly.class, 2, 2, 4));
      }

      if (OreSpawn.PeacockEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Peacock.class, 2, 2, 4));
      }

      if (OreSpawn.StinkBugEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(StinkBug.class, 3, 2, 4));
      }

      if (OreSpawn.OstrichEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Ostrich.class, 1, 1, 2));
      }

      if (OreSpawn.ChipmunkEnable != 0) {
         this.getSpawnableList(EnumCreatureType.AMBIENT).add(new SpawnListEntry(Chipmunk.class, 1, 1, 2));
      }

      if (OreSpawn.BeaverEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(Beaver.class, 1, 1, 2));
      }

      if (OreSpawn.CowEnable != 0) {
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(RedCow.class, 3, 2, 4));
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(GoldCow.class, 2, 2, 4));
         this.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(EnchantedCow.class, 1, 2, 4));
      }

      if (OreSpawn.VortexEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Vortex.class, 1, 1, 2));
      }

      if (OreSpawn.PitchBlackEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(PitchBlack.class, 1, 1, 2));
      }

      if (OreSpawn.TerribleTerrorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(TerribleTerror.class, 4, 2, 6));
      }

      if (OreSpawn.AlosaurusEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Alosaurus.class, 1, 1, 1));
      }

      if (OreSpawn.BasiliskEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Basilisk.class, 1, 1, 1));
      }

      if (OreSpawn.Robot1Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot1.class, 5, 2, 8));
      }

      if (OreSpawn.Robot2Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot2.class, 2, 1, 4));
      }

      if (OreSpawn.Robot3Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot3.class, 2, 1, 4));
      }

      if (OreSpawn.Robot4Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot4.class, 1, 1, 2));
      }

      if (OreSpawn.Robot5Enable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Robot5.class, 2, 3, 5));
      }

      if (OreSpawn.CaterKillerEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(CaterKiller.class, 1, 1, 1));
      }

      if (OreSpawn.CaveFisherEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(CaveFisher.class, 5, 1, 5));
      }

      if (OreSpawn.CreepingHorrorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(CreepingHorror.class, 5, 1, 5));
      }

      if (OreSpawn.CryolophosaurusEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Cryolophosaurus.class, 5, 1, 5));
      }

      if (OreSpawn.UrchinEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Urchin.class, 2, 1, 5));
      }

      if (OreSpawn.DungeonBeastEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(DungeonBeast.class, 2, 1, 5));
      }

      if (OreSpawn.EmperorScorpionEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EmperorScorpion.class, 1, 1, 1));
      }

      if (OreSpawn.EnderKnightEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EnderKnight.class, 2, 1, 2));
      }

      if (OreSpawn.EnderReaperEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EnderReaper.class, 1, 1, 1));
      }

      if (OreSpawn.HammerheadEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Hammerhead.class, 1, 1, 1));
      }

      if (OreSpawn.HerculesBeetleEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(HerculesBeetle.class, 1, 1, 1));
      }

      if (OreSpawn.TrooperBugEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(TrooperBug.class, 1, 1, 1));
      }

      if (OreSpawn.MolenoidEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Molenoid.class, 1, 1, 1));
      }

      if (OreSpawn.MothraEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Mothra.class, 1, 1, 1));
      }

      if (OreSpawn.BrutalflyEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Brutalfly.class, 1, 1, 1));
      }

      if (OreSpawn.RatEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Rat.class, 10, 1, 10));
      }

      if (OreSpawn.RotatorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Rotator.class, 1, 1, 3));
      }

      if (OreSpawn.ScorpionEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Scorpion.class, 2, 1, 3));
      }

      if (OreSpawn.SpitBugEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(SpitBug.class, 2, 1, 3));
      }

      if (OreSpawn.NastysaurusEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Nastysaurus.class, 1, 1, 1));
      }

      if (OreSpawn.TRexEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(TRex.class, 1, 1, 1));
      }

      if (OreSpawn.LeafMonsterEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(LeafMonster.class, 2, 1, 4));
      }

      if (OreSpawn.PointysaurusEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Pointysaurus.class, 2, 1, 4));
      }

      if (OreSpawn.LeonEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Leon.class, 1, 1, 1));
      }

      if (OreSpawn.MantisEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(Mantis.class, 1, 1, 1));
      }

      if (OreSpawn.LurkingTerrorEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(LurkingTerror.class, 1, 1, 1));
      }

      if (OreSpawn.GammaMetroidEnable != 0) {
         this.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(GammaMetroid.class, 1, 1, 1));
      }
   }
}

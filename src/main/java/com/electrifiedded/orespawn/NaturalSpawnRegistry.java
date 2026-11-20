package com.electrifiedded.orespawn;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Calendar;

/** Natural spawn table transcribed from OreSpawn 1.7.10 release 20.3. */
public final class NaturalSpawnRegistry {
    private NaturalSpawnRegistry() {}

    public static void register() {
        Calendar now = Calendar.getInstance();
        boolean halloween = now.get(Calendar.MONTH) == Calendar.OCTOBER && now.get(Calendar.DAY_OF_MONTH) == 31;
        OreSpawn.easter_day = now.get(Calendar.MONTH) == Calendar.APRIL && now.get(Calendar.DAY_OF_MONTH) == 20 ? 1 : 0;
        OreSpawn.valentines_day = now.get(Calendar.MONTH) == Calendar.FEBRUARY && now.get(Calendar.DAY_OF_MONTH) == 14 ? 1 : 0;

        if (halloween) {
            Biome[] biomes = {Biomes.BEACH, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.FOREST,
                Biomes.FOREST_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.PLAINS, Biomes.RIVER, Biomes.DESERT,
                Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.REDWOOD_TAIGA, Biomes.TAIGA, Biomes.SAVANNA,
                Biomes.SAVANNA_PLATEAU, Biomes.MESA_ROCK, Biomes.MESA_CLEAR_ROCK, Biomes.MESA, Biomes.COLD_TAIGA_HILLS,
                Biomes.COLD_TAIGA, Biomes.ROOFED_FOREST};
            spawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, biomes);
            spawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, biomes);
        }
        if (OreSpawn.GirlfriendEnable != 0) people(Girlfriend.class);
        if (OreSpawn.BoyfriendEnable != 0) people(Boyfriend.class);
        if (OreSpawn.BeaverEnable != 0) {
            spawn(Beaver.class, 10, 2, 4, EnumCreatureType.CREATURE, Biomes.RIVER);
            spawn(Beaver.class, 3, 2, 4, EnumCreatureType.CREATURE, Biomes.FOREST);
            spawn(Beaver.class, 2, 2, 4, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST);
            spawn(Beaver.class, 2, 2, 5, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST_HILLS);
            spawn(Beaver.class, 5, 2, 5, EnumCreatureType.CREATURE, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        }
        if (OreSpawn.CowEnable != 0) registerCows();
        if (OreSpawn.CriminalEnable != 0) spawn(BandP.class, 20, 1, 2, EnumCreatureType.AMBIENT, Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA);
        if (OreSpawn.WormEnable != 0) {
            spawn(WormLarge.class, 25, 1, 1, EnumCreatureType.CREATURE, Biomes.PLAINS);
            spawn(WormLarge.class, 15, 1, 1, EnumCreatureType.CREATURE, Biomes.SAVANNA);
            spawn(WormLarge.class, 10, 1, 1, EnumCreatureType.CREATURE, Biomes.SAVANNA_PLATEAU);
        }
        if (OreSpawn.ButterflyEnable != 0) registerButterflies();
        if (OreSpawn.MothEnable != 0) registerMoths();
        if (OreSpawn.CassowaryEnable != 0) registerCassowaries();
        if (OreSpawn.EasterBunnyEnable != 0 && OreSpawn.easter_day != 0) {
            spawn(EasterBunny.class, 10, 1, 2, EnumCreatureType.CREATURE, Biomes.PLAINS, Biomes.FOREST, Biomes.FOREST_HILLS);
            spawn(EasterBunny.class, 5, 1, 2, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.REDWOOD_TAIGA);
            spawn(EasterBunny.class, 8, 1, 2, EnumCreatureType.CREATURE, Biomes.TAIGA);
        }
        if (OreSpawn.FireflyEnable != 0) registerFireflies();
        if (OreSpawn.WhaleEnable != 0) spawn(Whale.class, 1, 1, 2, EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN);
        if (OreSpawn.BeeEnable != 0) registerBees();
        if (OreSpawn.MantisEnable != 0) registerMantises();
        if (OreSpawn.HerculesBeetleEnable != 0) {
            spawn(HerculesBeetle.class, 2, 1, 2, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS, Biomes.JUNGLE_HILLS, Biomes.TAIGA_HILLS, Biomes.REDWOOD_TAIGA_HILLS);
            spawn(HerculesBeetle.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS_EDGE);
            spawn(HerculesBeetle.class, 5, 1, 1, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST_HILLS);
            spawn(HerculesBeetle.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.COLD_TAIGA_HILLS);
        }
        if (OreSpawn.MolenoidEnable != 0) {
            spawn(Molenoid.class, 2, 1, 2, EnumCreatureType.AMBIENT, Biomes.PLAINS);
            spawn(Molenoid.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
        }
        if (OreSpawn.CaterKillerEnable != 0) registerCaterKillers();
        if (OreSpawn.ChipmunkEnable != 0) registerChipmunks();
        if (OreSpawn.OstrichEnable != 0) spawn(Ostrich.class, 1, 1, 1, EnumCreatureType.AMBIENT, Biomes.DESERT, Biomes.STONE_BEACH, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
        if (OreSpawn.CephadromeEnable != 0) spawn(Cephadrome.class, 1, 1, 1, EnumCreatureType.AMBIENT, Biomes.ICE_PLAINS, Biomes.COLD_TAIGA);
        if (OreSpawn.MosquitoEnable != 0) {
            spawn(EntityMosquito.class, 30, 5, 10, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
            spawn(EntityMosquito.class, 20, 5, 10, EnumCreatureType.AMBIENT, Biomes.JUNGLE, Biomes.JUNGLE_HILLS);
            spawn(EntityMosquito.class, 15, 2, 5, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
        }
        if (OreSpawn.GhostEnable != 0) coldGhost(Ghost.class);
        if (OreSpawn.GhostSkellyEnable != 0) coldGhost(GhostSkelly.class);
        if (OreSpawn.DragonflyEnable != 0) {
            spawn(Dragonfly.class, 5, 3, 5, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
            spawn(Dragonfly.class, 4, 1, 2, EnumCreatureType.AMBIENT, Biomes.RIVER);
        }
        if (OreSpawn.KyuubiEnable != 0) spawn(Kyuubi.class, 10, 1, 1, EnumCreatureType.MONSTER, Biomes.HELL);
        if (OreSpawn.StinkyEnable != 0) {
            spawn(Stinky.class, 2, 1, 1, EnumCreatureType.MONSTER, Biomes.HELL);
            spawn(Stinky.class, 1, 1, 1, EnumCreatureType.AMBIENT, Biomes.MESA, Biomes.MESA_CLEAR_ROCK, Biomes.MESA_ROCK);
        }
        if (OreSpawn.CockateilEnable != 0) registerCockateils();
        if (OreSpawn.HydroliscEnable != 0) {
            spawn(Hydrolisc.class, 25, 3, 6, EnumCreatureType.CREATURE, Biomes.SWAMPLAND);
            spawn(Hydrolisc.class, 15, 2, 5, EnumCreatureType.CREATURE, Biomes.JUNGLE);
            spawn(Hydrolisc.class, 10, 1, 3, EnumCreatureType.CREATURE, Biomes.JUNGLE_HILLS);
            spawn(Hydrolisc.class, 5, 3, 6, EnumCreatureType.CREATURE, Biomes.STONE_BEACH);
        }
        if (OreSpawn.MothraEnable != 0) spawn(Mothra.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS, Biomes.MUTATED_EXTREME_HILLS);
        if (OreSpawn.BrutalflyEnable != 0) spawn(Brutalfly.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA_HILLS, Biomes.MUTATED_EXTREME_HILLS, Biomes.MESA_CLEAR_ROCK);
        registerWaterCreatures();
        registerRemainingAmbient();
    }

    private static void people(Class<? extends EntityLiving> entity) {
        spawn(entity, 30, 8, 15, EnumCreatureType.CREATURE, Biomes.BEACH);
        spawn(entity, 10, 3, 6, EnumCreatureType.CREATURE, Biomes.FOREST, Biomes.RIVER, Biomes.STONE_BEACH);
        spawn(entity, 8, 2, 5, EnumCreatureType.CREATURE, Biomes.FOREST_HILLS);
        spawn(entity, 5, 2, 3, EnumCreatureType.CREATURE, Biomes.PLAINS);
        spawn(entity, 5, 2, 4, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST);
        spawn(entity, 5, 2, 5, EnumCreatureType.CREATURE, Biomes.BIRCH_FOREST_HILLS, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(entity, 2, 1, 3, EnumCreatureType.CREATURE, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
    }

    private static void registerCows() {
        spawn(RedCow.class, 8, 4, 8, EnumCreatureType.CREATURE, Biomes.PLAINS, Biomes.FOREST);
        spawn(RedCow.class, 5, 2, 5, EnumCreatureType.CREATURE, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(RedCow.class, 8, 1, 3, EnumCreatureType.CREATURE, Biomes.SAVANNA);
        spawn(RedCow.class, 2, 1, 3, EnumCreatureType.CREATURE, Biomes.SAVANNA_PLATEAU);
        spawn(GoldCow.class, 5, 2, 6, EnumCreatureType.CREATURE, Biomes.PLAINS, Biomes.FOREST);
        spawn(GoldCow.class, 5, 2, 5, EnumCreatureType.CREATURE, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(EnchantedCow.class, 3, 2, 4, EnumCreatureType.CREATURE, Biomes.FOREST, Biomes.PLAINS);
        spawn(EnchantedCow.class, 5, 2, 5, EnumCreatureType.CREATURE, Biomes.REDWOOD_TAIGA);
        spawn(EnchantedCow.class, 15, 3, 6, EnumCreatureType.CREATURE, Biomes.MUSHROOM_ISLAND);
    }

    private static void registerButterflies() {
        spawn(EntityButterfly.class, 8, 5, 15, EnumCreatureType.AMBIENT, Biomes.BEACH);
        spawn(EntityButterfly.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE);
        spawn(EntityButterfly.class, 30, 3, 6, EnumCreatureType.AMBIENT, Biomes.FOREST);
        spawn(EntityButterfly.class, 20, 2, 5, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS, Biomes.JUNGLE, Biomes.RIVER);
        spawn(EntityButterfly.class, 10, 2, 5, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
        spawn(EntityButterfly.class, 20, 3, 6, EnumCreatureType.AMBIENT, Biomes.PLAINS);
        spawn(EntityButterfly.class, 20, 4, 10, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
        spawn(EntityButterfly.class, 15, 2, 4, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST);
        spawn(EntityButterfly.class, 15, 2, 5, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST_HILLS, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(EntityButterfly.class, 10, 1, 5, EnumCreatureType.AMBIENT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
    }

    private static void registerMoths() {
        spawn(EntityLunaMoth.class, 8, 1, 2, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE);
        spawn(EntityLunaMoth.class, 10, 2, 5, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS, Biomes.JUNGLE_HILLS);
        spawn(EntityLunaMoth.class, 20, 3, 6, EnumCreatureType.AMBIENT, Biomes.FOREST, Biomes.JUNGLE);
        spawn(EntityLunaMoth.class, 20, 2, 5, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
        spawn(EntityLunaMoth.class, 10, 1, 5, EnumCreatureType.AMBIENT, Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
        spawn(EntityLunaMoth.class, 15, 2, 4, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST);
        spawn(EntityLunaMoth.class, 15, 2, 5, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST_HILLS, Biomes.ROOFED_FOREST, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
    }

    private static void registerCassowaries() {
        spawn(Cassowary.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.MUTATED_EXTREME_HILLS);
        spawn(Cassowary.class, 5, 2, 4, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST);
        spawn(Cassowary.class, 5, 2, 5, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST_HILLS);
        spawn(Cassowary.class, 15, 2, 5, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA, Biomes.REDWOOD_TAIGA_HILLS);
        spawn(Cassowary.class, 3, 1, 5, EnumCreatureType.AMBIENT, Biomes.SAVANNA);
        spawn(Cassowary.class, 10, 1, 5, EnumCreatureType.AMBIENT, Biomes.SAVANNA_PLATEAU);
    }

    private static void registerFireflies() {
        spawn(Firefly.class, 15, 5, 10, EnumCreatureType.AMBIENT, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS);
        spawn(Firefly.class, 10, 4, 8, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
        spawn(Firefly.class, 10, 3, 6, EnumCreatureType.AMBIENT, Biomes.STONE_BEACH);
        spawn(Firefly.class, 15, 3, 10, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS);
        spawn(Firefly.class, 15, 2, 10, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA, Biomes.TAIGA, Biomes.REDWOOD_TAIGA_HILLS);
        spawn(Firefly.class, 10, 2, 8, EnumCreatureType.AMBIENT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
    }

    private static void registerBees() {
        spawn(Bee.class, 2, 1, 2, EnumCreatureType.AMBIENT, Biomes.FOREST, Biomes.FOREST_HILLS);
        spawn(Bee.class, 5, 3, 5, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
        spawn(Bee.class, 5, 2, 5, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
        spawn(Bee.class, 3, 2, 4, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS);
        spawn(Bee.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(Bee.class, 3, 1, 1, EnumCreatureType.AMBIENT, Biomes.SAVANNA);
        spawn(Bee.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.SAVANNA_PLATEAU);
    }

    private static void registerMantises() {
        spawn(Mantis.class, 1, 1, 1, EnumCreatureType.AMBIENT, Biomes.FOREST, Biomes.FOREST_HILLS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
        spawn(Mantis.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
        spawn(Mantis.class, 1, 1, 2, EnumCreatureType.AMBIENT, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.REDWOOD_TAIGA);
        spawn(Mantis.class, 1, 2, 4, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST);
    }

    private static void registerCaterKillers() {
        spawn(CaterKiller.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.FOREST);
        spawn(CaterKiller.class, 2, 1, 2, EnumCreatureType.AMBIENT, Biomes.JUNGLE, Biomes.BIRCH_FOREST_HILLS, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(CaterKiller.class, 4, 1, 2, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS, Biomes.JUNGLE_HILLS);
        spawn(CaterKiller.class, 6, 1, 2, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST);
        spawn(CaterKiller.class, 10, 1, 2, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
    }

    private static void registerChipmunks() {
        spawn(Chipmunk.class, 8, 3, 6, EnumCreatureType.AMBIENT, Biomes.FOREST);
        spawn(Chipmunk.class, 5, 3, 6, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS, Biomes.BIRCH_FOREST);
        spawn(Chipmunk.class, 4, 3, 6, EnumCreatureType.AMBIENT, Biomes.JUNGLE, Biomes.BIRCH_FOREST_HILLS);
        spawn(Chipmunk.class, 2, 1, 2, EnumCreatureType.AMBIENT, Biomes.PLAINS);
        spawn(Chipmunk.class, 10, 2, 5, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
        spawn(Chipmunk.class, 2, 2, 5, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA);
        spawn(Chipmunk.class, 6, 2, 5, EnumCreatureType.AMBIENT, Biomes.TAIGA);
    }

    private static void coldGhost(Class<? extends EntityLiving> entity) {
        spawn(entity, 15, 5, 10, EnumCreatureType.AMBIENT, Biomes.COLD_TAIGA);
        spawn(entity, 10, 5, 10, EnumCreatureType.AMBIENT, Biomes.TAIGA_HILLS);
        spawn(entity, 6, 4, 6, EnumCreatureType.AMBIENT, Biomes.FROZEN_RIVER);
        spawn(entity, 2, 1, 4, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
        spawn(entity, 15, 2, 5, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
    }

    private static void registerCockateils() {
        spawn(Cockateil.class, 10, 2, 5, EnumCreatureType.AMBIENT, Biomes.BEACH);
        spawn(Cockateil.class, 10, 1, 2, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS);
        spawn(Cockateil.class, 10, 2, 4, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS_EDGE, Biomes.PLAINS);
        spawn(Cockateil.class, 25, 5, 10, EnumCreatureType.AMBIENT, Biomes.FOREST);
        spawn(Cockateil.class, 20, 3, 6, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS);
        spawn(Cockateil.class, 35, 5, 10, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
        spawn(Cockateil.class, 25, 5, 10, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
        spawn(Cockateil.class, 15, 3, 6, EnumCreatureType.AMBIENT, Biomes.RIVER, Biomes.BIRCH_FOREST);
        spawn(Cockateil.class, 5, 3, 6, EnumCreatureType.AMBIENT, Biomes.STONE_BEACH, Biomes.BIRCH_FOREST_HILLS);
        spawn(Cockateil.class, 15, 2, 5, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(Cockateil.class, 11, 1, 5, EnumCreatureType.AMBIENT, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU);
    }

    private static void registerWaterCreatures() {
        if (OreSpawn.WaterDragonEnable != 0) {
            spawn(WaterDragon.class, 5, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.RIVER);
            spawn(WaterDragon.class, 3, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.SWAMPLAND);
            spawn(WaterDragon.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN, Biomes.STONE_BEACH);
        }
        if (OreSpawn.SeaMonsterEnable != 0) {
            spawn(SeaMonster.class, 4, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
            spawn(SeaMonster.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.SWAMPLAND);
        }
        if (OreSpawn.SeaViperEnable != 0) {
            spawn(SeaViper.class, 3, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
            spawn(SeaViper.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, Biomes.STONE_BEACH);
        }
        if (OreSpawn.CrabEnable != 0) {
            spawn(Crab.class, 2, 3, 6, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
            spawn(Crab.class, 1, 3, 6, EnumCreatureType.WATER_CREATURE, Biomes.SWAMPLAND);
            spawn(Crab.class, 1, 2, 4, EnumCreatureType.WATER_CREATURE, Biomes.STONE_BEACH);
        }
        if (OreSpawn.AttackSquidEnable != 0) {
            spawn(AttackSquid.class, 12, 6, 10, EnumCreatureType.WATER_CREATURE, Biomes.RIVER);
            spawn(AttackSquid.class, 10, 5, 9, EnumCreatureType.WATER_CREATURE, Biomes.SWAMPLAND);
            spawn(AttackSquid.class, 7, 4, 8, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
        }
        if (OreSpawn.LizardEnable != 0) {
            spawn(Lizard.class, 5, 2, 4, EnumCreatureType.WATER_CREATURE, Biomes.RIVER);
            spawn(Lizard.class, 4, 2, 4, EnumCreatureType.WATER_CREATURE, Biomes.SWAMPLAND);
            spawn(Lizard.class, 2, 2, 4, EnumCreatureType.WATER_CREATURE, Biomes.OCEAN);
        }
        if (OreSpawn.RubberDuckyEnable != 0) {
            spawn(RubberDucky.class, 10, 10, 20, EnumCreatureType.WATER_CREATURE, Biomes.RIVER);
            spawn(RubberDucky.class, 4, 4, 6, EnumCreatureType.WATER_CREATURE, Biomes.STONE_BEACH);
        }
        if (OreSpawn.FrogEnable != 0) {
            spawn(Frog.class, 20, 3, 6, EnumCreatureType.WATER_CREATURE, Biomes.RIVER);
            spawn(Frog.class, 20, 2, 6, EnumCreatureType.WATER_CREATURE, Biomes.SWAMPLAND);
        }
    }

    private static void registerRemainingAmbient() {
        if (OreSpawn.BasiliskEnable != 0) {
            spawn(Basilisk.class, 3, 1, 1, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
            spawn(Basilisk.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
            spawn(Basilisk.class, 4, 1, 2, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST_HILLS);
            spawn(Basilisk.class, 15, 1, 2, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
        }
        if (OreSpawn.EmperorScorpionEnable != 0) {
            spawn(EmperorScorpion.class, 1, 1, 1, EnumCreatureType.AMBIENT, Biomes.DESERT);
            spawn(EmperorScorpion.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.SAVANNA);
        }
        if (OreSpawn.TrooperBugEnable != 0) { spawn(TrooperBug.class, 3, 1, 2, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND); spawn(TrooperBug.class, 1, 1, 1, EnumCreatureType.AMBIENT, Biomes.MESA); }
        if (OreSpawn.SpitBugEnable != 0) spawn(SpitBug.class, 6, 1, 2, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
        if (OreSpawn.StinkBugEnable != 0) {
            spawn(StinkBug.class, 10, 2, 4, EnumCreatureType.AMBIENT, Biomes.FOREST);
            spawn(StinkBug.class, 8, 2, 4, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
            spawn(StinkBug.class, 6, 2, 4, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS);
            spawn(StinkBug.class, 4, 2, 4, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
            spawn(StinkBug.class, 8, 2, 5, EnumCreatureType.AMBIENT, Biomes.SAVANNA);
        }
        if (OreSpawn.ScorpionEnable != 0) {
            spawn(Scorpion.class, 15, 3, 6, EnumCreatureType.AMBIENT, Biomes.DESERT);
            spawn(Scorpion.class, 28, 2, 4, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
            spawn(Scorpion.class, 15, 3, 5, EnumCreatureType.AMBIENT, Biomes.SAVANNA);
            spawn(Scorpion.class, 15, 2, 4, EnumCreatureType.AMBIENT, Biomes.SAVANNA_PLATEAU);
            spawn(Scorpion.class, 6, 1, 3, EnumCreatureType.AMBIENT, Biomes.MESA);
            spawn(Scorpion.class, 4, 1, 3, EnumCreatureType.AMBIENT, Biomes.MESA_CLEAR_ROCK);
            spawn(Scorpion.class, 5, 3, 6, EnumCreatureType.AMBIENT, Biomes.MESA_ROCK);
        }
        if (OreSpawn.LeafMonsterEnable != 0) {
            spawn(LeafMonster.class, 5, 2, 6, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
            spawn(LeafMonster.class, 5, 1, 2, EnumCreatureType.AMBIENT, Biomes.FOREST);
            spawn(LeafMonster.class, 3, 2, 4, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
            spawn(LeafMonster.class, 3, 1, 2, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS);
            spawn(LeafMonster.class, 3, 3, 6, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST);
            spawn(LeafMonster.class, 2, 3, 6, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST_HILLS);
            spawn(LeafMonster.class, 2, 2, 5, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        }
        if (OreSpawn.EnderKnightEnable != 0) ender(EnderKnight.class, 4, 2, 4, 20, 2, 4);
        if (OreSpawn.EnderReaperEnable != 0) ender(EnderReaper.class, 2, 1, 2, 38, 2, 4);
        if (OreSpawn.CoinEnable != 0) spawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, Biomes.TAIGA, Biomes.FOREST, Biomes.JUNGLE, Biomes.BIRCH_FOREST, Biomes.COLD_TAIGA, Biomes.REDWOOD_TAIGA);
        if (OreSpawn.CricketEnable != 0) registerCrickets();
        if (OreSpawn.FrogEnable != 0) spawn(Frog.class, 3, 3, 6, EnumCreatureType.AMBIENT, Biomes.RIVER, Biomes.JUNGLE); 
        if (OreSpawn.FrogEnable != 0) spawn(Frog.class, 2, 2, 6, EnumCreatureType.AMBIENT, Biomes.SWAMPLAND);
        if (OreSpawn.PeacockEnable != 0) spawn(Peacock.class, 1, 1, 3, EnumCreatureType.AMBIENT, Biomes.MESA, Biomes.MESA_CLEAR_ROCK);
        if (OreSpawn.FairyEnable != 0) spawn(Fairy.class, 25, 2, 4, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
        if (OreSpawn.RatEnable != 0) { spawn(Rat.class, 35, 10, 20, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST); spawn(Rat.class, 25, 2, 8, EnumCreatureType.AMBIENT, Biomes.TAIGA); }
        if (OreSpawn.DungeonBeastEnable != 0) spawn(DungeonBeast.class, 20, 2, 4, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
    }

    private static void ender(Class<? extends EntityLiving> entity, int commonWeight, int min, int max, int roofWeight, int roofMin, int roofMax) {
        spawn(entity, commonWeight, min, max, EnumCreatureType.AMBIENT, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.FOREST_HILLS, Biomes.JUNGLE_HILLS);
        spawn(entity, entity == EnderKnight.class ? 4 : 1, min, max, EnumCreatureType.AMBIENT, Biomes.FOREST);
        spawn(entity, entity == EnderKnight.class ? 2 : 1, min, max, EnumCreatureType.AMBIENT, Biomes.PLAINS, Biomes.RIVER, Biomes.DESERT);
        spawn(entity, roofWeight, roofMin, roofMax, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
    }

    private static void registerCrickets() {
        spawn(Cricket.class, 3, 2, 4, EnumCreatureType.AMBIENT, Biomes.FOREST);
        spawn(Cricket.class, 2, 2, 4, EnumCreatureType.AMBIENT, Biomes.FOREST_HILLS);
        spawn(Cricket.class, 3, 2, 4, EnumCreatureType.AMBIENT, Biomes.JUNGLE);
        spawn(Cricket.class, 2, 3, 5, EnumCreatureType.AMBIENT, Biomes.JUNGLE_HILLS);
        spawn(Cricket.class, 3, 4, 8, EnumCreatureType.AMBIENT, Biomes.PLAINS);
        spawn(Cricket.class, 2, 2, 6, EnumCreatureType.AMBIENT, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS);
        spawn(Cricket.class, 3, 1, 4, EnumCreatureType.AMBIENT, Biomes.ROOFED_FOREST);
        spawn(Cricket.class, 2, 1, 6, EnumCreatureType.AMBIENT, Biomes.REDWOOD_TAIGA, Biomes.TAIGA);
        spawn(Cricket.class, 1, 1, 4, EnumCreatureType.AMBIENT, Biomes.SAVANNA_PLATEAU);
    }

    private static void spawn(Class<? extends EntityLiving> entity, int weight, int min, int max, EnumCreatureType type, Biome... biomes) {
        int adjustedWeight = weight * OreSpawn.MobSpawnWeightPercent / 100;
        if (adjustedWeight > 0) EntityRegistry.addSpawn(entity, adjustedWeight, min, max, type, biomes);
    }
}

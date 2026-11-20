package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.OreSpawnMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedHashMap;

public class OreSpawnEntityRegistry {
    private static int entityId = 0;
    private static final Map<String, Class<? extends Entity>> ENTITY_CLASSES = new HashMap<>();
    private static final Map<String, String> LIVING_ENTITY_EGGS = new LinkedHashMap<>();

    public static void registerEntities() {
        reg(Acid.class, "Acid");
        reg(Alien.class, "Alien");
        reg(Alosaurus.class, "Alosaurus");
        reg(AntRobot.class, "Robot Red Ant");
        reg(AttackSquid.class, "Attack Squid");
        reg(BandP.class, "Criminal");
        reg(Baryonyx.class, "Baryonyx");
        reg(Basilisk.class, "Basilisk");
        reg(Beaver.class, "Beaver");
        reg(Bee.class, "Bee");
        reg(BerthaHit.class, "BerthaHit");
        reg(Boyfriend.class, "Boyfriend");
        reg(Brutalfly.class, "Brutalfly");
        reg(Camarasaurus.class, "Camarasaurus");
        reg(Cassowary.class, "Cassowary");
        reg(CaterKiller.class, "CaterKiller");
        reg(CaveFisher.class, "CaveFisher");
        reg(Cephadrome.class, "Cephadrome");
        reg(Chipmunk.class, "Chipmunk");
        reg(CliffRacer.class, "Cliff Racer");
        reg(CloudShark.class, "Cloud Shark");
        reg(Cockateil.class, "Bird");
        reg(Coin.class, "Coin");
        reg(Crab.class, "Crab");
        reg(CreepingHorror.class, "Creeping Horror");
        reg(Cricket.class, "Cricket");
        reg(Cryolophosaurus.class, "Cryolophosaurus");
        reg(CrystalCow.class, "Crystal Apple Cow");
        reg(DeadIrukandji.class, "DeadIrukandji");
        reg(Dragon.class, "Dragon");
        reg(Dragonfly.class, "Dragonfly");
        reg(DungeonBeast.class, "Dungeon Beast");
        reg(EasterBunny.class, "EasterBunny");
        reg(Elevator.class, "Hoverboard");
        reg(EmperorScorpion.class, "Emperor Scorpion");
        reg(EnchantedCow.class, "Enchanted Golden Apple Cow");
        reg(EnderKnight.class, "Ender Knight");
        reg(EnderReaper.class, "Ender Reaper");
        reg(EntityAnt.class, "Ant");
        reg(EntityButterfly.class, "Butterfly");
        reg(EntityCage.class, "EntityCage");
        reg(EntityLunaMoth.class, "Moth");
        reg(EntityMosquito.class, "Mosquito");
        reg(EntityRainbowAnt.class, "Rainbow Ant");
        reg(EntityRedAnt.class, "Red Ant");
        reg(EntityThrownRock.class, "EntityThrownRock");
        reg(EntityUnstableAnt.class, "Unstable Ant");
        reg(Fairy.class, "Fairy");
        reg(Firefly.class, "Firefly");
        reg(Flounder.class, "Flounder");
        reg(Frog.class, "Frog");
        reg(GammaMetroid.class, "WTF?");
        reg(Gazelle.class, "Gazelle");
        reg(Ghost.class, "Ghost");
        reg(GhostSkelly.class, "Ghost Pumpkin Skelly");
        reg(GiantRobot.class, "Jeffery");
        reg(Girlfriend.class, "Girlfriend");
        reg(Godzilla.class, "Mobzilla");
        reg(GodzillaHead.class, "MobzillaHead");
        reg(GoldCow.class, "Golden Apple Cow");
        reg(GoldFish.class, "Gold Fish");
        reg(Hammerhead.class, "Hammerhead");
        reg(HerculesBeetle.class, "Hercules Beetle");
        reg(Hydrolisc.class, "Hydrolisc");
        reg(IceBall.class, "IceBall");
        reg(InkSack.class, "InkSack");
        reg(Irukandji.class, "Irukandji");
        reg(Island.class, "Island");
        reg(IslandToo.class, "IslandToo");
        reg(KingHead.class, "KingHead");
        reg(Kraken.class, "The Kraken");
        reg(Kyuubi.class, "Kyuubi");
        reg(LaserBall.class, "LaserBall");
        reg(LeafMonster.class, "Leaf Monster");
        reg(Leon.class, "Leonopteryx");
        reg(Lizard.class, "Lizard");
        reg(LurkingTerror.class, "Lurking Terror");
        reg(Mantis.class, "Mantis");
        reg(Molenoid.class, "Molenoid");
        reg(Mothra.class, "Mothra");
        reg(Nastysaurus.class, "Nastysaurus");
        reg(Ostrich.class, "Ostrich");
        reg(Peacock.class, "Peacock");
        reg(PitchBlack.class, "Nightmare");
        reg(Pointysaurus.class, "Pointysaurus");
        reg(PurplePower.class, "PurplePower");
        reg(QueenHead.class, "QueenHead");
        reg(Rat.class, "Rat");
        reg(RedCow.class, "Apple Cow");
        reg(Robot1.class, "Bomb-Omb");
        reg(Robot2.class, "Robo-Pounder");
        reg(Robot3.class, "Robo-Gunner");
        reg(Robot4.class, "Robo-Warrior");
        reg(Robot5.class, "Robo-Sniper");
        reg(RockBase.class, "Rock");
        reg(Rotator.class, "Rotator");
        reg(RubberDucky.class, "Rubber Ducky");
        reg(RubyBird.class, "Ruby Bird");
        reg(Scorpion.class, "Scorpion");
        reg(SeaMonster.class, "Sea Monster");
        reg(SeaViper.class, "Sea Viper");
        reg(Shoes.class, "Shoes");
        reg(Skate.class, "Skate");
        reg(SpiderDriver.class, "Spider Driver");
        reg(SpiderRobot.class, "Robot Spider");
        reg(SpitBug.class, "Spit Bug");
        reg(Spyro.class, "Baby Dragon");
        reg(StinkBug.class, "Stink Bug");
        reg(Stinky.class, "Stinky");
        reg(SunspotUrchin.class, "SunspotUrchin");
        reg(ThunderBolt.class, "ThunderBolt");
        reg(TRex.class, "T. Rex");
        reg(Termite.class, "Termite");
        reg(TerribleTerror.class, "Terrible Terror");
        reg(TheKing.class, "The King");
        reg(ThePrince.class, "The Prince");
        reg(ThePrinceAdult.class, "The Young Adult Prince");
        reg(ThePrinceTeen.class, "The Young Prince");
        reg(ThePrincess.class, "The Princess");
        reg(TheQueen.class, "The Queen");
        reg(Triffid.class, "Triffid");
        reg(TrooperBug.class, "Jumpy Bug");
        reg(Tshirt.class, "T-Shirt");
        reg(Urchin.class, "Crystal Urchin");
        reg(UltimateArrow.class, "UltimateArrow");
        regFishHook();
        reg(VelocityRaptor.class, "Velocity Raptor");
        reg(Vortex.class, "Vortex");
        reg(WaterBall.class, "WaterBall");
        reg(WaterDragon.class, "Water Dragon");
        reg(Whale.class, "Whale");
        reg(WormLarge.class, "Large Worm");
        reg(WormMedium.class, "Medium Worm");
        reg(WormSmall.class, "Small Worm");
        reg(IrukandjiArrow.class, "IrukandjiArrow");
    }

    private static void reg(Class<? extends Entity> cls, String name) {
        String path = name.toLowerCase(Locale.ROOT).replace(" ", "_").replace("?", "wtf").replace(".", "");
        alias(name, cls);
        alias(path, cls);
        // Eggs and legacy structures often use the Java class name instead of the display name.
        alias(cls.getSimpleName(), cls);
        if (EntityLiving.class.isAssignableFrom(cls) && !name.endsWith("Head")) {
            LIVING_ENTITY_EGGS.put(path, name);
        }
        int[] tracking = TRACKING.getOrDefault(cls.getSimpleName(), DEFAULT_TRACKING);
        EntityRegistry.registerModEntity(new ResourceLocation(OreSpawnMod.MOD_ID, path), cls, name, entityId++, OreSpawnMod.instance, tracking[0], tracking[1], true);
    }

    private static final int[] DEFAULT_TRACKING = {64, 1};
    /**
     * Legacy registerModEntity tracking range and update interval: bosses stay visible from 128 blocks,
     * small critters are only synced within 16-32 blocks. Unlisted entities use 64 blocks every tick.
     */
    private static final Map<String, int[]> TRACKING = trackingTable(
        "AntRobot=128:1;AttackSquid=32:1;Brutalfly=128:1;CaveFisher=32:1;Cephadrome=128:1;Chipmunk=32:1;CliffRacer=32:1;Cockateil=32:1;Cricket=32:1;Dragon=128:1;Elevator=128:1;EntityAnt=16:1;EntityButterfly=32:1;EntityLunaMoth=32:1;EntityMosquito=16:1;EntityRainbowAnt=16:1;EntityRedAnt=16:1;EntityUnstableAnt=16:1;Fairy=32:1;Flounder=32:1;Frog=32:1;Ghost=32:1;GiantRobot=128:1;Godzilla=128:1;GodzillaHead=128:10;GoldFish=32:1;Irukandji=32:1;KingHead=128:10;Kraken=128:1;Mothra=128:1;Nastysaurus=128:1;QueenHead=128:10;Rat=32:1;Robot1=32:1;RockBase=32:1;RubyBird=32:1;Scorpion=32:1;Skate=32:1;SpiderRobot=128:1;StinkBug=32:1;Termite=32:1;TheKing=128:1;ThePrinceAdult=128:1;TheQueen=128:1;Tshirt=32:1;WormSmall=32:1");

    private static Map<String, int[]> trackingTable(String data) {
        Map<String, int[]> table = new HashMap<>();
        for (String entry : data.split(";")) {
            String[] pair = entry.split("=");
            String[] values = pair[1].split(":");
            table.put(pair[0], new int[]{Integer.parseInt(values[0]), Integer.parseInt(values[1])});
        }
        return table;
    }

    private static void regFishHook() {
        String name = "UltimateFishHook";
        String path = "ultimatefishhook";
        alias(name, UltimateFishHook.class);
        alias(path, UltimateFishHook.class);
        net.minecraftforge.fml.common.registry.EntityEntry entry = net.minecraftforge.fml.common.registry.EntityEntryBuilder
            .<UltimateFishHook>create()
            .entity(UltimateFishHook.class)
            .factory(UltimateFishHook::new)
            .id(new ResourceLocation(OreSpawnMod.MOD_ID, path), entityId++)
            .name(name)
            .tracker(64, 1, true)
            .build();
        net.minecraftforge.fml.common.registry.GameRegistry.findRegistry(net.minecraftforge.fml.common.registry.EntityEntry.class).register(entry);
    }

    public static void registerAliases() {
        alias("Godzilla", Godzilla.class);
        alias("Pitch Black", PitchBlack.class);
        alias("Jeffery", GiantRobot.class);
        alias("Alo", Alosaurus.class);
        alias("Basil", Basilisk.class);
        alias("Cama", Camarasaurus.class);
        alias("Cryo", Cryolophosaurus.class);
        alias("Hydro", Hydrolisc.class);
        alias("Velo", VelocityRaptor.class);
        alias("T Rex", TRex.class);
    }

    private static void alias(String name, Class<? extends Entity> cls) {
        ENTITY_CLASSES.put(normalize(name), cls);
    }

    private static String normalize(String name) {
        return name.toLowerCase(Locale.ROOT).replace('_', ' ').replace(".", "").trim();
    }

    public static Map<String, String> getLivingEntityEggs() {
        return java.util.Collections.unmodifiableMap(LIVING_ENTITY_EGGS);
    }

    public static Entity create(String name, World world) {
        String target = name;
        int separator = target.indexOf(':');
        String namespace = separator < 0 ? "" : target.substring(0, separator);
        String path = separator < 0 ? target : target.substring(separator + 1);
        String key = normalize(path);
        Class<? extends Entity> entityClass = ENTITY_CLASSES.get(key);
        if (entityClass == null) {
            String normalized = key;
            for (Map.Entry<String, Class<? extends Entity>> entry : ENTITY_CLASSES.entrySet()) {
                if (entry.getKey().equals(normalized) || compact(entry.getKey()).equals(compact(key))) {
                    entityClass = entry.getValue();
                    break;
                }
            }
        }
        if (entityClass != null) {
            try {
                Constructor<? extends Entity> constructor = entityClass.getConstructor(World.class);
                return constructor.newInstance(world);
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Unable to create OreSpawn entity " + name, exception);
            }
        }

        ResourceLocation id = separator >= 0
            ? new ResourceLocation(namespace, path.toLowerCase(Locale.ROOT))
            : new ResourceLocation("minecraft", path.toLowerCase(Locale.ROOT));
        return net.minecraft.entity.EntityList.createEntityByIDFromName(id, world);
    }

    private static String compact(String name) {
        return normalize(name).replaceAll("[^a-z0-9]", "");
    }

    public static void initialize(Entity entity, World world) {
        if (entity instanceof EntityLiving) {
            EntityLiving living = (EntityLiving) entity;
            living.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(living)), null);
        }
    }
}

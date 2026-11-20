package com.electrifiedded.orespawn;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Central registry for all OreSpawn sound events (server-safe).
 * Original 1.7.10 code used plain string sound names like "orespawn:alo_living";
 * here every sounds/*.ogg file becomes a SoundEvent named "orespawn:<basename>"
 * (registering twice i.e. both "x" and "orespawn.x" SoundEvent names is harmless,
 * we simply register bare names and entities look them up through {@link #get}).
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class OreSpawnSoundEvents {
    private static final Map<String, SoundEvent> SOUNDS = new HashMap<>();
    private static final Set<String> WARNED_MISSING = new HashSet<>();

    // #SOUND_NAMES# (generated from assets/orespawn/sounds/*.ogg)
    private static final String[] NAMES = new String[]{

        "alien_death",
        "alien_hurt",
        "alien_living",
        "alo_death",
        "alo_hurt",
        "alo_living",
        "b_dark",
        "b_death_boyfriend",
        "b_death_single1",
        "b_death_single2",
        "b_fight1",
        "b_fight2",
        "b_fight3",
        "b_fight4",
        "b_fight5",
        "b_fight6",
        "b_fight7",
        "b_happy1",
        "b_happy2",
        "b_happy3",
        "b_happy4",
        "b_happy5",
        "b_happy6",
        "b_happy7",
        "b_happy8",
        "b_hurt1",
        "b_hurt10",
        "b_hurt2",
        "b_hurt3",
        "b_hurt4",
        "b_hurt5",
        "b_hurt6",
        "b_hurt7",
        "b_hurt8",
        "b_hurt9",
        "b_ow1",
        "b_ow2",
        "b_ow3",
        "b_ow4",
        "b_ow5",
        "b_ow6",
        "b_ow7",
        "b_ow8",
        "b_ow9",
        "b_rain1",
        "b_rain2",
        "b_taunt1",
        "b_taunt2",
        "b_taunt3",
        "b_taunt4",
        "b_taunt5",
        "b_taunt6",
        "b_thunder",
        "b_water1",
        "b_water2",
        "b_woohoo1",
        "b_woohoo2",
        "b_woohoo3",
        "b_woohoo4",
        "basilisk_living",
        "bb_happy1",
        "bb_happy2",
        "bb_happy3",
        "bb_happy4",
        "bb_happy5",
        "bb_happy6",
        "beebuzz",
        "big_splat",
        "birds1",
        "birds10",
        "birds11",
        "birds12",
        "birds13",
        "birds14",
        "birds15",
        "birds16",
        "birds17",
        "birds18",
        "birds19",
        "birds2",
        "birds20",
        "birds21",
        "birds22",
        "birds23",
        "birds3",
        "birds4",
        "birds5",
        "birds6",
        "birds7",
        "birds8",
        "birds9",
        "caterkiller_death",
        "caterkiller_hit1",
        "caterkiller_hit2",
        "caterkiller_hit3",
        "caterkiller_hit4",
        "caterkiller_living1",
        "caterkiller_living2",
        "caterkiller_living3",
        "caterkiller_living4",
        "chain_rattles",
        "chainsaw",
        "chainsawshort",
        "clatter1",
        "clatter2",
        "cliffracer",
        "creepinghorror_dead",
        "creepinghorror_hit",
        "creepinghorror_living",
        "cricket",
        "crunch",
        "cryo_death",
        "cryo_hurt",
        "cryo_living",
        "dbdead",
        "dbhit1",
        "dbhit2",
        "dbhit3",
        "dragonfly_death",
        "dragonfly_hurt",
        "dragonfly_living",
        "duck_hurt",
        "emperorscorpion_death",
        "fart1",
        "fart2",
        "fart3",
        "fart4",
        "fart5",
        "fart6",
        "fart7",
        "fart8",
        "fart9",
        "frog1",
        "frog2",
        "ghost_sound",
        "glassdead1",
        "glassdead2",
        "glasshit1",
        "glasshit2",
        "glasshit3",
        "glasshit4",
        "glasshit5",
        "godzilla_death",
        "godzilla_living",
        "hammerhead_death",
        "hammerhead_living1",
        "hammerhead_living2",
        "hercules_death",
        "hover1",
        "hover2",
        "hover3",
        "hover4",
        "hover5",
        "hover6",
        "king_hit",
        "king_living",
        "kraken_living",
        "kyuubi_living",
        "leaves_death",
        "leaves_hit",
        "leon_death",
        "leon_hit1",
        "leon_hit2",
        "leon_hit3",
        "leon_living",
        "little_splat",
        "lurkinghorror_dead",
        "lurkinghorror_hit",
        "lurkinghorror_living",
        "molenoid_death",
        "molenoid_hit1",
        "molenoid_hit2",
        "molenoid_hit3",
        "molenoid_hit4",
        "molenoid_hit5",
        "molenoid_hit6",
        "molenoid_living1",
        "molenoid_living2",
        "molenoid_living3",
        "mosquito",
        "mothrawings1",
        "mothrawings2",
        "mothrawings3",
        "o_dark",
        "o_death_girlfriend",
        "o_death_single",
        "o_fight1",
        "o_fight2",
        "o_fight3",
        "o_fight4",
        "o_fight5",
        "o_fight6",
        "o_fight7",
        "o_happy1",
        "o_happy2",
        "o_happy3",
        "o_happy4",
        "o_happy5",
        "o_happy6",
        "o_happy7",
        "o_hurt1",
        "o_hurt2",
        "o_hurt3",
        "o_hurt4",
        "o_hurt5",
        "o_hurt6",
        "o_hurt7",
        "o_hurt8",
        "o_hurt9",
        "o_ow1",
        "o_ow2",
        "o_ow3",
        "o_ow4",
        "o_ow5",
        "o_ow6",
        "o_ow7",
        "o_ow8",
        "o_rain",
        "o_taunt1",
        "o_taunt2",
        "o_taunt3",
        "o_taunt4",
        "o_thunder",
        "o_water1",
        "o_water2",
        "o_woohoo1",
        "o_woohoo2",
        "o_woohoo3",
        "o_woohoo4",
        "peacockdead",
        "peacockhit",
        "peacocklive",
        "pitchblack_dead",
        "pitchblack_hit",
        "pitchblack_living",
        "ratdead1",
        "ratdead2",
        "ratdead3",
        "rathit",
        "ratlive",
        "roar1",
        "roar2",
        "roar3",
        "roar4",
        "roar5",
        "roar6",
        "robot1_death",
        "robot_death1",
        "robot_death2",
        "robot_death3",
        "robot_death4",
        "robot_death5",
        "robot_hurt1",
        "robot_hurt2",
        "robot_hurt3",
        "robot_hurt4",
        "robot_hurt5",
        "robot_hurt6",
        "robot_hurt7",
        "robot_hurt8",
        "robot_living1",
        "robot_living2",
        "robot_living3",
        "robot_living4",
        "robotspider1",
        "robotspider10",
        "robotspider11",
        "robotspider2",
        "robotspider3",
        "robotspider4",
        "robotspider5",
        "robotspider6",
        "robotspider7",
        "robotspider8",
        "robotspider9",
        "robotspidermount",
        "rubybird",
        "scorpion_attack",
        "scorpion_hit",
        "scorpion_living",
        "seamonster_death",
        "seamonster_hit",
        "seamonster_living1",
        "seamonster_living2",
        "seaviper_death",
        "seaviper_hit1",
        "seaviper_hit2",
        "seaviper_hit3",
        "seaviper_living",
        "squid_death1",
        "squid_death2",
        "squid_hurt1",
        "squid_hurt2",
        "squid_hurt3",
        "squid_hurt4",
        "terribleterror_dead",
        "terribleterror_hit",
        "terribleterror_living",
        "trex_death",
        "trex_living",
        "triffid_dead",
        "triffid_hit",
        "triffid_living",
        "vortexlive",
        "waterdragon_death",
        "waterdragon_hurt1",
        "waterdragon_hurt2",
        "waterdragon_hurt3",
        "wtf_hurt",
        "wtf_living",
        "b_death_single",
        "b_fight",
        "b_happy",
        "b_hurt",
        "b_ow",
        "b_rain",
        "b_taunt",
        "b_water",
        "b_woohoo",
        "bb_happy",
        "birds",
        "caterkiller_hit",
        "caterkiller_living",
        "clatter",
        "dbhit",
        "fart",
        "glassdead",
        "glasshit",
        "hammerhead_living",
        "hover",
        "leon_hit",
        "molenoid_hit",
        "molenoid_living",
        "mothrawings",
        "o_fight",
        "o_happy",
        "o_hurt",
        "o_ow",
        "o_taunt",
        "o_water",
        "o_woohoo",
        "ratdead",
        "roar",
        "robot_death",
        "robot_hurt",
        "robot_living",
        "robotspider",
        "seamonster_living",
        "seaviper_hit",
        "squid_death",
        "squid_hurt",
        "waterdragon_hurt"

    };

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        for (String name : NAMES) {
            SoundEvent se = new SoundEvent(new ResourceLocation(Reference.MOD_ID, name));
            se.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
            event.getRegistry().register(se);
            SOUNDS.put(name, se);
        }
    }

    /** Returns (lazily, if needed) the SoundEvent for a bare sound name like "alo_living". Safe on both sides. */
    public static SoundEvent get(String name) {
        boolean vanillaNamespace = name.startsWith("minecraft:");
        if (name.startsWith(Reference.MOD_ID + ":")) {
            name = name.substring(Reference.MOD_ID.length() + 1);
        } else if (name.startsWith("minecraft:")) {
            name = name.substring("minecraft:".length());
        }
        name = name.toLowerCase(java.util.Locale.ROOT);
        SoundEvent se = vanillaNamespace || isLegacyVanilla(name) ? vanilla(name) : SOUNDS.get(name);
        if (se == null) se = legacyGroup(name);
        if (se == null) {
            se = SoundEvent.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID, name));
            if (se == null) se = vanilla(name);
            if (se == null && WARNED_MISSING.add(name)) {
                OreSpawnMod.LOGGER.warn("Unknown sound event: {}", name);
            }
        }
        return se;
    }

    /** Global resolver: works for orespawn sounds and legacy vanilla names (1.7-style "random.bow" etc). */
    public static SoundEvent any(String name) {
        return get(name);
    }

    private static SoundEvent vanilla(String name) {
        switch (name) {
            case "random.bow": name = "entity.arrow.shoot"; break;
            case "random.bowhit": name = "entity.arrow.hit"; break;
            case "random.explode": name = "entity.generic.explode"; break;
            case "random.fuse": name = "entity.tnt.primed"; break;
            case "random.splash": case "splash": name = "entity.generic.splash"; break;
            case "random.eat": name = "entity.generic.eat"; break;
            case "random.drink": name = "entity.generic.drink"; break;
            case "random.break": name = "entity.item.break"; break;
            case "random.orb": name = "entity.experience_orb.pickup"; break;
            case "random.pop": name = "entity.item.pickup"; break;
            case "random.fizz": name = "entity.generic.extinguish_fire"; break;
            case "mob.enderdragon.hit": name = "entity.enderdragon.hurt"; break;
            case "mob.endermen.stare": name = "entity.endermen.stare"; break;
            case "mob.endermen.scream": name = "entity.endermen.scream"; break;
            case "mob.endermen.idle": name = "entity.endermen.ambient"; break;
            case "mob.endermen.death": name = "entity.endermen.death"; break;
            case "mob.endermen.hit": name = "entity.endermen.hurt"; break;
            case "mob.endermen.portal": name = "entity.endermen.teleport"; break;
            case "mob.villager.idle": name = "entity.villager.ambient"; break;
            case "mob.villager.hit": name = "entity.villager.hurt"; break;
            case "mob.villager.death": name = "entity.villager.death"; break;
            case "fireworks.launch": case "entity.firework_rocket.launch": name = "entity.firework.launch"; break;
        }
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("minecraft", name));
    }

    private static boolean isLegacyVanilla(String name) {
        return name.startsWith("random.") || name.startsWith("mob.") || name.startsWith("fireworks.")
            || name.startsWith("entity.");
    }

    private static SoundEvent legacyGroup(String name) {
        if ("rat_hit".equals(name)) name = "rathit";
        if ("little_splt".equals(name)) name = "little_splat";
        if ("frog".equals(name)) name = "frog1";
        return SOUNDS.get(name);
    }

    private OreSpawnSoundEvents() {
    }
}

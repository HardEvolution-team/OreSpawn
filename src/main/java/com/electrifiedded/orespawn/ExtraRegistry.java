package com.electrifiedded.orespawn;
import com.electrifiedded.orespawn.block.BlockOreSpawnPlant;
import com.electrifiedded.orespawn.block.BlockTallCrop;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import com.electrifiedded.orespawn.block.BlockOreSpawnPlant;
import com.electrifiedded.orespawn.block.BlockOreSpawnCrop;
import com.electrifiedded.orespawn.block.BlockTallCrop;

/** Auto-generated simple items/blocks so every OreSpawn.My* field is non-null. */
public class ExtraRegistry {
    private static final java.util.List<Block> SPAWN_BLOCKS = new java.util.ArrayList<>();

    private static Item egg(String registryName, String entityName) {
        return new com.electrifiedded.orespawn.item.ItemSpawnEgg(registryName, entityName)
            .setCreativeTab(RegistryHandler.TAB_ORESPAWN);
    }

    public static Block addSpawnBlock(String registryName, String entityName) {
        com.electrifiedded.orespawn.block.OreGenericEgg b = new com.electrifiedded.orespawn.block.OreGenericEgg(registryName, entityName);
        b.setCreativeTab(RegistryHandler.tabForBlock(registryName));
        RegistryHandler.EXTRA_BLOCKS_STATIC().add(b);
        SPAWN_BLOCKS.add(b);
        return b;
    }

    private static Item cage(String name) {
        Integer id = CAGE_IDS.get(name);
        if (id == null) throw new IllegalArgumentException("Missing legacy cage ID for " + name);
        return new com.electrifiedded.orespawn.item.CritterCage(0, id).setRegistryName(name).setTranslationKey(name)
            .setCreativeTab(net.minecraft.creativetab.CreativeTabs.MISC);
    }

    private static final java.util.Map<String, Integer> CAGE_IDS = cageIds();

    private static java.util.Map<String, Integer> cageIds() {
        java.util.Map<String, Integer> ids = new java.util.HashMap<>();
        String data = "cage_empty=160;caged_spider=161;caged_bat=162;caged_cow=163;caged_pig=164;caged_squid=165;caged_chicken=166;caged_creeper=167;caged_skeleton=168;caged_zombie=169;caged_slime=170;caged_ghast=171;caged_zombie_pigman=172;caged_enderman=173;caged_cave_spider=174;caged_silverfish=175;caged_magma_cube=176;caged_witch=177;caged_sheep=178;caged_wolf=179;caged_mooshroom=180;caged_ocelot=181;caged_blaze=182;caged_girlfriend=183;caged_ender_dragon=184;caged_snow_golem=185;caged_iron_golem=186;caged_wither_boss=187;caged_wither_skeleton=188;caged_red_cow=189;caged_gold_cow=190;caged_enchanted_cow=191;caged_easter_bunny=150;caged_cater_killer=151;caged_molenoid=152;caged_sea_monster=153;caged_sea_viper=154;caged_mothra=208;caged_alo=209;caged_cryo=210;caged_cama=211;caged_velo=212;caged_hydro=213;caged_basil=214;caged_boyfriend=215;caged_crystal_cow=216;caged_villager=217;caged_criminal=218;caged_dragonfly=220;caged_emperor_scorpion=222;caged_scorpion=224;caged_cave_fisher=226;caged_spyro=228;caged_baryonyx=230;caged_gamma_metroid=232;caged_cockateil=234;caged_kyuubi=236;caged_alien=238;caged_attack_squid=240;caged_water_dragon=242;caged_kraken=244;caged_lizard=246;caged_cephadrome=248;caged_dragon=250;caged_bee=252;caged_horse=253;caged_firefly=255;caged_chipmunk=256;caged_gazelle=257;caged_ostrich=258;caged_trooper=259;caged_spit=260;caged_stink=261;caged_creeping_horror=268;caged_terrible_terror=269;caged_cliff_racer=270;caged_triffid=271;caged_pitch_black=272;caged_lurking_terror=273;caged_small_worm=281;caged_medium_worm=282;caged_large_worm=283;caged_cassowary=284;caged_cloud_shark=285;caged_gold_fish=286;caged_leaf_monster=287;caged_ender_knight=296;caged_ender_reaper=297;caged_beaver=300;caged_rotator=313;caged_vortex=314;caged_peacock=315;caged_fairy=316;caged_dungeon_beast=317;caged_rat=318;caged_flounder=319;caged_whale=320;caged_irukandji=321;caged_skate=322;caged_urchin=323;caged_t_rex=345;caged_hercules=346;caged_mantis=347;caged_stinky=348;caged_leon=357;caged_hammerhead=359;caged_rubber_ducky=361;caged_brutalfly=373;caged_nastysaurus=374;caged_pointysaurus=375;caged_cricket=376;caged_frog=377;caged_spider_driver=382;caged_crab=384";
        for (String entry : data.split(";")) {
            String[] pair = entry.split("=");
            ids.put(pair[0], Integer.parseInt(pair[1]));
        }
        return ids;
    }

    private static Item food(String name, int hunger, float saturation) {
        return new com.electrifiedded.orespawn.item.ItemPopcorn(0, hunger, saturation, false)
            .setRegistryName(name).setTranslationKey(name).setCreativeTab(net.minecraft.creativetab.CreativeTabs.FOOD);
    }

    private static Block cornPlant() { return BLOCK_MYCORNPLANT1; }
    private static Block lettucePlant() { return BLOCK_MYLETTUCEPLANT1; }
    private static Block quinoaPlant() { return BLOCK_MYQUINOAPLANT1; }
    private static Block radishPlant() { return BLOCK_MYRADISHPLANT; }
    private static Block ricePlant() { return BLOCK_MYRICEPLANT; }
    private static Block tomatoPlant() { return BLOCK_MYTOMATOPLANT1; }
    private static Block crystalGrass() { return BLOCK_CRYSTALGRASS; }

    public static java.util.List<Block> spawnBlocks() {
        return SPAWN_BLOCKS;
    }

    private static Block plantLeaves(String registryName) {
        Block b = new com.electrifiedded.orespawn.block.BlockOreSpawnLeaves(registryName);
        b.setCreativeTab(RegistryHandler.tabForBlock(registryName));
        return b;
    }

    public static final Item ITEM_ALIENEGG = egg("alien_egg", "Alien");
    public static final Item ITEM_ALOEGG = egg("alo_egg", "Alosaurus");
    public static final Item ITEM_ANTROBOTEGG = egg("ant_robot_egg", "Robot Red Ant");
    public static final Item ITEM_ANTROBOTKIT = new com.electrifiedded.orespawn.item.ItemSpiderRobotKit("ant_robot_kit", false);
    public static final Item ITEM_ATTACKSQUIDEGG = egg("attack_squid_egg", "Attack Squid");
    public static final Item ITEM_BARYONYXEGG = egg("baryonyx_egg", "Baryonyx");
    public static final Item ITEM_BASILEGG = egg("basil_egg", "Basilisk");
    public static final Item ITEM_BEAVEREGG = egg("beaver_egg", "Beaver");
    public static final Item ITEM_BEEEGG = egg("bee_egg", "Bee");
    public static final Item ITEM_BERTHABLADE = RegistryHandler.simpleItem("bertha_blade");
    public static final Item ITEM_BERTHAGUARD = RegistryHandler.simpleItem("bertha_guard");
    public static final Item ITEM_BERTHAHANDLE = RegistryHandler.simpleItem("bertha_handle");
    public static final Item ITEM_BOYFRIENDEGG = egg("boyfriend_egg", "Boyfriend");
    public static final Item ITEM_BROWNANTEGG = egg("brown_ant_egg", "Ant");
    public static final Item ITEM_BRUTALFLYEGG = egg("brutalfly_egg", "Brutalfly");
    public static final Item ITEM_BUTTERFLYEGG = egg("butterfly_egg", "Butterfly");
    public static final Item ITEM_CAGEEMPTY = cage("cage_empty");
    public static final Item ITEM_CAGEDALIEN = cage("caged_alien");
    public static final Item ITEM_CAGEDALO = cage("caged_alo");
    public static final Item ITEM_CAGEDATTACKSQUID = cage("caged_attack_squid");
    public static final Item ITEM_CAGEDBARYONYX = cage("caged_baryonyx");
    public static final Item ITEM_CAGEDBASIL = cage("caged_basil");
    public static final Item ITEM_CAGEDBAT = cage("caged_bat");
    public static final Item ITEM_CAGEDBEAVER = cage("caged_beaver");
    public static final Item ITEM_CAGEDBEE = cage("caged_bee");
    public static final Item ITEM_CAGEDBLAZE = cage("caged_blaze");
    public static final Item ITEM_CAGEDBOYFRIEND = cage("caged_boyfriend");
    public static final Item ITEM_CAGEDBRUTALFLY = cage("caged_brutalfly");
    public static final Item ITEM_CAGEDCAMA = cage("caged_cama");
    public static final Item ITEM_CAGEDCASSOWARY = cage("caged_cassowary");
    public static final Item ITEM_CAGEDCATERKILLER = cage("caged_cater_killer");
    public static final Item ITEM_CAGEDCAVEFISHER = cage("caged_cave_fisher");
    public static final Item ITEM_CAGEDCAVESPIDER = cage("caged_cave_spider");
    public static final Item ITEM_CAGEDCEPHADROME = cage("caged_cephadrome");
    public static final Item ITEM_CAGEDCHICKEN = cage("caged_chicken");
    public static final Item ITEM_CAGEDCHIPMUNK = cage("caged_chipmunk");
    public static final Item ITEM_CAGEDCLIFFRACER = cage("caged_cliff_racer");
    public static final Item ITEM_CAGEDCLOUDSHARK = cage("caged_cloud_shark");
    public static final Item ITEM_CAGEDCOCKATEIL = cage("caged_cockateil");
    public static final Item ITEM_CAGEDCOW = cage("caged_cow");
    public static final Item ITEM_CAGEDCRAB = cage("caged_crab");
    public static final Item ITEM_CAGEDCREEPER = cage("caged_creeper");
    public static final Item ITEM_CAGEDCREEPINGHORROR = cage("caged_creeping_horror");
    public static final Item ITEM_CAGEDCRICKET = cage("caged_cricket");
    public static final Item ITEM_CAGEDCRIMINAL = cage("caged_criminal");
    public static final Item ITEM_CAGEDCRYO = cage("caged_cryo");
    public static final Item ITEM_CAGEDCRYSTALCOW = cage("caged_crystal_cow");
    public static final Item ITEM_CAGEDDRAGON = cage("caged_dragon");
    public static final Item ITEM_CAGEDDRAGONFLY = cage("caged_dragonfly");
    public static final Item ITEM_CAGEDDUNGEONBEAST = cage("caged_dungeon_beast");
    public static final Item ITEM_CAGEDEASTERBUNNY = cage("caged_easter_bunny");
    public static final Item ITEM_CAGEDEMPERORSCORPION = cage("caged_emperor_scorpion");
    public static final Item ITEM_CAGEDENCHANTEDCOW = cage("caged_enchanted_cow");
    public static final Item ITEM_CAGEDENDERDRAGON = cage("caged_ender_dragon");
    public static final Item ITEM_CAGEDENDERKNIGHT = cage("caged_ender_knight");
    public static final Item ITEM_CAGEDENDERREAPER = cage("caged_ender_reaper");
    public static final Item ITEM_CAGEDENDERMAN = cage("caged_enderman");
    public static final Item ITEM_CAGEDFAIRY = cage("caged_fairy");
    public static final Item ITEM_CAGEDFIREFLY = cage("caged_firefly");
    public static final Item ITEM_CAGEDFLOUNDER = cage("caged_flounder");
    public static final Item ITEM_CAGEDFROG = cage("caged_frog");
    public static final Item ITEM_CAGEDGAMMAMETROID = cage("caged_gamma_metroid");
    public static final Item ITEM_CAGEDGAZELLE = cage("caged_gazelle");
    public static final Item ITEM_CAGEDGHAST = cage("caged_ghast");
    public static final Item ITEM_CAGEDGIRLFRIEND = cage("caged_girlfriend");
    public static final Item ITEM_CAGEDGOLDCOW = cage("caged_gold_cow");
    public static final Item ITEM_CAGEDGOLDFISH = cage("caged_gold_fish");
    public static final Item ITEM_CAGEDHAMMERHEAD = cage("caged_hammerhead");
    public static final Item ITEM_CAGEDHERCULES = cage("caged_hercules");
    public static final Item ITEM_CAGEDHORSE = cage("caged_horse");
    public static final Item ITEM_CAGEDHYDRO = cage("caged_hydro");
    public static final Item ITEM_CAGEDIRONGOLEM = cage("caged_iron_golem");
    public static final Item ITEM_CAGEDIRUKANDJI = cage("caged_irukandji");
    public static final Item ITEM_CAGEDKRAKEN = cage("caged_kraken");
    public static final Item ITEM_CAGEDKYUUBI = cage("caged_kyuubi");
    public static final Item ITEM_CAGEDLARGEWORM = cage("caged_large_worm");
    public static final Item ITEM_CAGEDLEAFMONSTER = cage("caged_leaf_monster");
    public static final Item ITEM_CAGEDLEON = cage("caged_leon");
    public static final Item ITEM_CAGEDLIZARD = cage("caged_lizard");
    public static final Item ITEM_CAGEDLURKINGTERROR = cage("caged_lurking_terror");
    public static final Item ITEM_CAGEDMOTHRA = cage("caged_mothra");
    public static final Item ITEM_CAGEDMAGMACUBE = cage("caged_magma_cube");
    public static final Item ITEM_CAGEDMANTIS = cage("caged_mantis");
    public static final Item ITEM_CAGEDMEDIUMWORM = cage("caged_medium_worm");
    public static final Item ITEM_CAGEDMOLENOID = cage("caged_molenoid");
    public static final Item ITEM_CAGEDMOOSHROOM = cage("caged_mooshroom");
    public static final Item ITEM_CAGEDNASTYSAURUS = cage("caged_nastysaurus");
    public static final Item ITEM_CAGEDOCELOT = cage("caged_ocelot");
    public static final Item ITEM_CAGEDOSTRICH = cage("caged_ostrich");
    public static final Item ITEM_CAGEDPEACOCK = cage("caged_peacock");
    public static final Item ITEM_CAGEDPIG = cage("caged_pig");
    public static final Item ITEM_CAGEDPITCHBLACK = cage("caged_pitch_black");
    public static final Item ITEM_CAGEDPOINTYSAURUS = cage("caged_pointysaurus");
    public static final Item ITEM_CAGEDRAT = cage("caged_rat");
    public static final Item ITEM_CAGEDREDCOW = cage("caged_red_cow");
    public static final Item ITEM_CAGEDROTATOR = cage("caged_rotator");
    public static final Item ITEM_CAGEDRUBBERDUCKY = cage("caged_rubber_ducky");
    public static final Item ITEM_CAGEDSCORPION = cage("caged_scorpion");
    public static final Item ITEM_CAGEDSEAMONSTER = cage("caged_sea_monster");
    public static final Item ITEM_CAGEDSEAVIPER = cage("caged_sea_viper");
    public static final Item ITEM_CAGEDSHEEP = cage("caged_sheep");
    public static final Item ITEM_CAGEDSILVERFISH = cage("caged_silverfish");
    public static final Item ITEM_CAGEDSKATE = cage("caged_skate");
    public static final Item ITEM_CAGEDSKELETON = cage("caged_skeleton");
    public static final Item ITEM_CAGEDSLIME = cage("caged_slime");
    public static final Item ITEM_CAGEDSMALLWORM = cage("caged_small_worm");
    public static final Item ITEM_CAGEDSNOWGOLEM = cage("caged_snow_golem");
    public static final Item ITEM_CAGEDSPIDER = cage("caged_spider");
    public static final Item ITEM_CAGEDSPIDERDRIVER = cage("caged_spider_driver");
    public static final Item ITEM_CAGEDSPIT = cage("caged_spit");
    public static final Item ITEM_CAGEDSPYRO = cage("caged_spyro");
    public static final Item ITEM_CAGEDSQUID = cage("caged_squid");
    public static final Item ITEM_CAGEDSTINK = cage("caged_stink");
    public static final Item ITEM_CAGEDSTINKY = cage("caged_stinky");
    public static final Item ITEM_CAGEDTREX = cage("caged_t_rex");
    public static final Item ITEM_CAGEDTERRIBLETERROR = cage("caged_terrible_terror");
    public static final Item ITEM_CAGEDTRIFFID = cage("caged_triffid");
    public static final Item ITEM_CAGEDTROOPER = cage("caged_trooper");
    public static final Item ITEM_CAGEDURCHIN = cage("caged_urchin");
    public static final Item ITEM_CAGEDVELO = cage("caged_velo");
    public static final Item ITEM_CAGEDVILLAGER = cage("caged_villager");
    public static final Item ITEM_CAGEDVORTEX = cage("caged_vortex");
    public static final Item ITEM_CAGEDWATERDRAGON = cage("caged_water_dragon");
    public static final Item ITEM_CAGEDWHALE = cage("caged_whale");
    public static final Item ITEM_CAGEDWITCH = cage("caged_witch");
    public static final Item ITEM_CAGEDWITHERBOSS = cage("caged_wither_boss");
    public static final Item ITEM_CAGEDWITHERSKELETON = cage("caged_wither_skeleton");
    public static final Item ITEM_CAGEDWOLF = cage("caged_wolf");
    public static final Item ITEM_CAGEDZOMBIE = cage("caged_zombie");
    public static final Item ITEM_CAGEDZOMBIEPIGMAN = cage("caged_zombie_pigman");
    public static final Item ITEM_CAMAEGG = egg("cama_egg", "Camarasaurus");
    public static final Item ITEM_CASSOWARYEGG = egg("cassowary_egg", "Cassowary");
    public static final Item ITEM_CATERKILLEREGG = egg("cater_killer_egg", "Cater Killer");
    public static final Item ITEM_CATERKILLERJAW = RegistryHandler.CATER_KILLER_JAW;
    public static final Item ITEM_CAVEFISHEREGG = egg("cave_fisher_egg", "Cave Fisher");
    public static final Item ITEM_CEPHADROMEEGG = egg("cephadrome_egg", "Cephadrome");
    public static final Item ITEM_CHIPMUNKEGG = egg("chipmunk_egg", "Chipmunk");
    public static final Item ITEM_CLIFFRACEREGG = egg("cliff_racer_egg", "Cliff Racer");
    public static final Item ITEM_CLOUDSHARKEGG = egg("cloud_shark_egg", "Cloud Shark");
    public static final Item ITEM_COCKATEILEGG = egg("cockateil_egg", "Bird");
    public static final Item ITEM_COINEGG = egg("coin_egg", "Coin");
    public static final Item ITEM_CRABEGG = egg("crab_egg", "Crab");
    public static final Item ITEM_CREEPERLAUNCHER = new com.electrifiedded.orespawn.item.ItemCreeperLauncher("creeper_launcher");
    public static final Item ITEM_CREEPINGHORROREGG = egg("creeping_horror_egg", "Creeping Horror");
    public static final Item ITEM_CRICKETEGG = egg("cricket_egg", "Cricket");
    public static final Item ITEM_CRIMINALEGG = egg("criminal_egg", "Criminal");
    public static final Item ITEM_CRYOEGG = egg("cryo_egg", "Cryolophosaurus");
    public static final Item ITEM_CRYSTALCOWEGG = egg("crystal_cow_egg", "Crystal Apple Cow");
    public static final Item ITEM_CRYSTALSTICKS = named(new com.electrifiedded.orespawn.item.ItemCrystalSticks(0), "crystal_sticks");
    public static final Item ITEM_DRAGONEGG = egg("dragon_egg", "Dragon");
    public static final Item ITEM_DRAGONFLYEGG = egg("dragonfly_egg", "Dragonfly");
    public static final Item ITEM_DUNGEONBEASTEGG = egg("dungeon_beast_egg", "Dungeon Beast");
    public static final Item ITEM_EASTERBUNNYEGG = egg("easter_bunny_egg", "Easter Bunny");
    public static final Item ITEM_EMPERORSCORPIONEGG = egg("emperor_scorpion_egg", "Emperor Scorpion");
    public static final Item ITEM_ENCHANTEDCOWEGG = egg("enchanted_cow_egg", "Enchanted Cow");
    public static final Item ITEM_ENDERDRAGONEGG = egg("ender_dragon_egg", "minecraft:ender_dragon");
    public static final Item ITEM_ENDERKNIGHTEGG = egg("ender_knight_egg", "Ender Knight");
    public static final Item ITEM_ENDERREAPEREGG = egg("ender_reaper_egg", "Ender Reaper");
    public static final Item ITEM_FAIRYEGG = egg("fairy_egg", "Fairy");
    public static final Item ITEM_FIREFLYEGG = egg("firefly_egg", "Firefly");
    public static final Item ITEM_FLOUNDEREGG = egg("flounder_egg", "Flounder");
    public static final Item ITEM_FROGEGG = egg("frog_egg", "Frog");
    public static final Item ITEM_GAMMAMETROIDEGG = egg("gamma_metroid_egg", "WTF?");
    public static final Item ITEM_GAZELLEEGG = egg("gazelle_egg", "Gazelle");
    public static final Item ITEM_GHOSTEGG = egg("ghost_egg", "Ghost");
    public static final Item ITEM_GHOSTSKELLYEGG = egg("ghost_skelly_egg", "Ghost Skelly");
    public static final Item ITEM_GIRLFRIENDEGG = egg("girlfriend_egg", "Girlfriend");
    public static final Item ITEM_GODZILLAEGG = egg("godzilla_egg", "Mobzilla");
    public static final Item ITEM_GOLDCOWEGG = egg("gold_cow_egg", "Gold Cow");
    public static final Item ITEM_GOLDFISHEGG = egg("gold_fish_egg", "Gold Fish");
    public static final Item ITEM_GREENGOO = RegistryHandler.simpleItem("green_goo");
    public static final Item ITEM_HAMMERHEADEGG = egg("hammerhead_egg", "Hammerhead");
    public static final Item ITEM_HERCULESEGG = egg("hercules_egg", "Hercules Beetle");
    public static final Item ITEM_HYDROEGG = egg("hydro_egg", "Hydrolisc");
    public static final Item ITEM_INSTANTGARDEN = new com.electrifiedded.orespawn.item.InstantGarden("instant_garden");
    public static final Item ITEM_INSTANTSHELTER = new com.electrifiedded.orespawn.item.InstantShelter("instant_shelter");
    public static final Item ITEM_IRONGOLEMEGG = egg("iron_golem_egg", "minecraft:villager_golem");
    public static final Item ITEM_IRUKANDJIEGG = egg("irukandji_egg", "Irukandji");
    public static final Item ITEM_JEFFERYEGG = egg("jeffery_egg", "Jeffery");
    public static final Item ITEM_KRAKENEGG = egg("kraken_egg", "The Kraken");
    public static final Item ITEM_KYUUBIEGG = egg("kyuubi_egg", "Kyuubi");
    public static final Item ITEM_LARGEWORMEGG = egg("large_worm_egg", "Large Worm");
    public static final Item ITEM_LEAFMONSTEREGG = egg("leaf_monster_egg", "Leaf Monster");
    public static final Item ITEM_LEONEGG = egg("leon_egg", "Leonopteryx");
    public static final Item ITEM_LIZARDEGG = egg("lizard_egg", "Lizard");
    public static final Item ITEM_LURKINGTERROREGG = egg("lurking_terror_egg", "Lurking Terror");
    public static final Item ITEM_MOTHRAEGG = egg("mothra_egg", "Mothra");
    public static final Item ITEM_MAGICAPPLE = new com.electrifiedded.orespawn.item.ItemMagicApple("magic_apple");
    public static final Item ITEM_MANTISEGG = egg("mantis_egg", "Mantis");
    public static final Item ITEM_MEDIUMWORMEGG = egg("medium_worm_egg", "Medium Worm");
    public static final Item ITEM_MINERSDREAM = new com.electrifiedded.orespawn.item.ItemMinersDream(0);
    public static final Item ITEM_MOLENOIDEGG = egg("molenoid_egg", "Molenoid");
    public static final Item ITEM_MOLENOIDNOSE = RegistryHandler.simpleItem("molenoid_nose");
    public static final Item ITEM_MOSQUITOEGG = egg("mosquito_egg", "Mosquito");
    public static final Item ITEM_MOTHEGG = egg("moth_egg", "Moth");
    public static final Item ITEM_MYACID = new com.electrifiedded.orespawn.item.ItemAcid(0).setRegistryName("acid").setTranslationKey("acid");
    public static final Item ITEM_MYAMETHYST = RegistryHandler.MY_AMETHYST;
    public static final Item ITEM_MYAMETHYSTAXE = RegistryHandler.AMETHYST_AXE;
    public static final Item ITEM_MYAMETHYSTHOE = RegistryHandler.AMETHYST_HOE;
    public static final Item ITEM_MYAMETHYSTPICKAXE = RegistryHandler.AMETHYST_PICKAXE;
    public static final Item ITEM_MYAMETHYSTSHOVEL = RegistryHandler.AMETHYST_SHOVEL;
    public static final Item ITEM_MYAMETHYSTSWORD = RegistryHandler.AMETHYST_SWORD;
    public static final Item ITEM_MYAPPLESEED = new com.electrifiedded.orespawn.item.ItemAppleSeed("apple_seed");
    public static final Item ITEM_MYBLT = food("blt", 12, 0.95F);
    public static final Item ITEM_MYBACON = food("bacon", 14, 1.5F);
    public static final Item ITEM_MYBASILISKSCALE = RegistryHandler.BASILISK_SCALE;
    public static final Item ITEM_MYBATTLEAXE = new com.electrifiedded.orespawn.item.UltimateSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.BATTLE, "battle_axe");
    public static final Item ITEM_MYBERTHA = new com.electrifiedded.orespawn.item.Bertha(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.BERTHA, "bertha");
    public static final Item ITEM_MYBIGHAMMER = new com.electrifiedded.orespawn.item.BigHammer(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.AMETHYST, "big_hammer");
    public static final Item ITEM_MYBLUEFISH = named(new com.electrifiedded.orespawn.item.ItemGenericFish(0, 4, 0.4F, false), "blue_fish");
    public static final Item ITEM_MYBLUEROCK = new com.electrifiedded.orespawn.item.ItemRock("blue_rock");
    public static final Item ITEM_MYBUTTER = food("butter", 1, 0.5F);
    public static final Item ITEM_MYBUTTERCANDY = food("butter_candy", 4, 0.5F);
    public static final Item ITEM_MYBUTTEREDPOPCORN = food("buttered_popcorn", 2, 0.6F);
    public static final Item ITEM_MYBUTTEREDSALTEDPOPCORN = food("buttered_salted_popcorn", 3, 0.75F);
    public static final Item ITEM_MYBUTTERFLYSEED = new com.electrifiedded.orespawn.item.ItemButterflySeed();
    public static final Item ITEM_MYCHAINSAW = new com.electrifiedded.orespawn.item.UltimateSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CHAINSAW, "chainsaw");
    public static final Item ITEM_MYCHEESE = food("cheese", 4, 0.5F);
    public static final Item ITEM_MYCHERRY = food("cherry", 3, 0.45F);
    public static final Item ITEM_MYCHERRYSEED = new com.electrifiedded.orespawn.item.ItemAppleSeed("cherry_seed");
    public static final Item ITEM_MYCORNCOB = new com.electrifiedded.orespawn.item.ItemPlantableFood("corn_cob", 6, 0.75F,
        ExtraRegistry::cornPlant, () -> net.minecraft.init.Blocks.FARMLAND);
    public static final Item ITEM_MYCORNDOG = food("corn_dog", 16, 2.5F);
    public static final Item ITEM_MYCRABMEAT = food("crab_meat", 6, 0.75F);
    public static final Item ITEM_MYCRABBYPATTY = food("crabby_patty", 16, 2.35F);
    public static final Item ITEM_MYCRYSTALAPPLE = food("crystal_apple", 5, 0.85F);
    public static final Item ITEM_MYCRYSTALBLUEROCK = new com.electrifiedded.orespawn.item.ItemRock("crystal_blue_rock");
    public static final Item ITEM_MYCRYSTALGREENROCK = new com.electrifiedded.orespawn.item.ItemRock("crystal_green_rock");
    public static final Item ITEM_MYCRYSTALPINKAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnAxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_PINK, "crystal_pink_axe");
    public static final Item ITEM_MYCRYSTALPINKHOE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnHoe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_PINK, "crystal_pink_hoe");
    public static final Item ITEM_MYCRYSTALPINKINGOT = RegistryHandler.simpleItem("crystal_pink_ingot");
    public static final Item ITEM_MYCRYSTALPINKPICKAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnPickaxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_PINK, "crystal_pink_pickaxe");
    public static final Item ITEM_MYCRYSTALPINKSHOVEL = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnShovel(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_PINK, "crystal_pink_shovel");
    public static final Item ITEM_MYCRYSTALPINKSWORD = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_PINK, "crystal_pink_sword");
    public static final Item ITEM_MYCRYSTALREDROCK = new com.electrifiedded.orespawn.item.ItemRock("crystal_red_rock");
    public static final Item ITEM_MYCRYSTALSTONEAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnAxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_STONE, "crystal_stone_axe");
    public static final Item ITEM_MYCRYSTALSTONEHOE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnHoe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_STONE, "crystal_stone_hoe");
    public static final Item ITEM_MYCRYSTALSTONEPICKAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnPickaxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_STONE, "crystal_stone_pickaxe");
    public static final Item ITEM_MYCRYSTALSTONESHOVEL = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnShovel(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_STONE, "crystal_stone_shovel");
    public static final Item ITEM_MYCRYSTALSTONESWORD = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_STONE, "crystal_stone_sword");
    public static final Item ITEM_MYCRYSTALTNTROCK = new com.electrifiedded.orespawn.item.ItemRock("crystal_tnt_rock");
    public static final Item ITEM_MYCRYSTALWOODAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnAxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_WOOD, "crystal_wood_axe");
    public static final Item ITEM_MYCRYSTALWOODHOE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnHoe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_WOOD, "crystal_wood_hoe");
    public static final Item ITEM_MYCRYSTALWOODPICKAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnPickaxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_WOOD, "crystal_wood_pickaxe");
    public static final Item ITEM_MYCRYSTALWOODSHOVEL = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnShovel(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_WOOD, "crystal_wood_shovel");
    public static final Item ITEM_MYCRYSTALWOODSWORD = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.CRYSTAL_WOOD, "crystal_wood_sword");
    public static final Item ITEM_MYDEADSTINKBUG = RegistryHandler.simpleItem("dead_stink_bug");
    public static final Item ITEM_MYELEVATOR = new com.electrifiedded.orespawn.item.ItemElevator(0);
    public static final Item ITEM_MYEMERALDAXE = RegistryHandler.EMERALD_AXE;
    public static final Item ITEM_MYEMERALDHOE = RegistryHandler.EMERALD_HOE;
    public static final Item ITEM_MYEMERALDPICKAXE = RegistryHandler.EMERALD_PICKAXE;
    public static final Item ITEM_MYEMERALDSHOVEL = RegistryHandler.EMERALD_SHOVEL;
    public static final Item ITEM_MYEMERALDSWORD = RegistryHandler.EMERALD_SWORD;
    public static final Item ITEM_MYEMPERORSCORPIONSCALE = RegistryHandler.EMPEROR_SCORPION_SCALE;
    public static final Item ITEM_MYEXPERIENCECATCHER = new com.electrifiedded.orespawn.item.ExperienceCatcher("experience_catcher");
    public static final Item ITEM_MYEXPERIENCESWORD = new com.electrifiedded.orespawn.item.ExperienceSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.EMERALD, "experience_sword");
    public static final Item ITEM_MYEXPERIENCETREESEED = new com.electrifiedded.orespawn.item.ItemExperienceTreeSeed("experience_tree_seed");
    public static final Item ITEM_MYFAIRYSWORD = new com.electrifiedded.orespawn.item.FairySword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.EMERALD, "fairy_sword");
    public static final Item ITEM_MYFIREFISH = named(new com.electrifiedded.orespawn.item.ItemFireFish(0, 4, 0.6F, false), "fire_fish");
    public static final Item ITEM_MYFIREFLYSEED = new com.electrifiedded.orespawn.item.ItemFireflySeed();
    public static final Item ITEM_MYGODZILLASCALE = RegistryHandler.GODZILLA_SCALE;
    public static final Item ITEM_MYGREENFISH = named(new com.electrifiedded.orespawn.item.ItemGenericFish(0, 3, 0.5F, false), "green_fish");
    public static final Item ITEM_MYGREENROCK = new com.electrifiedded.orespawn.item.ItemRock("green_rock");
    public static final Item ITEM_MYGREYFISH = named(new com.electrifiedded.orespawn.item.ItemGenericFish(0, 5, 0.5F, false), "grey_fish");
    public static final Item ITEM_MYHAMMY = new com.electrifiedded.orespawn.item.Bertha(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.HAMMY, "hammy");
    public static final Item ITEM_MYICEBALL = new com.electrifiedded.orespawn.item.ItemIceBall(0);
    public static final Item ITEM_MYIRUKANDJI = new com.electrifiedded.orespawn.item.ItemIrukandji(0);
    public static final Item ITEM_MYIRUKANDJIARROW = new com.electrifiedded.orespawn.item.ItemIrukandjiArrow(0);
    public static final Item ITEM_MYITEMGAMECONTROLLER = new com.electrifiedded.orespawn.item.ItemShoes(0, 6).setRegistryName("item_game_controller").setTranslationKey("item_game_controller");
    public static final Item ITEM_MYITEMSHOES = new com.electrifiedded.orespawn.item.ItemShoes(0, 1).setRegistryName("item_shoes").setTranslationKey("item_shoes");
    public static final Item ITEM_MYITEMSHOES_1 = new com.electrifiedded.orespawn.item.ItemShoes(0, 2).setRegistryName("item_shoes_1").setTranslationKey("item_shoes_1");
    public static final Item ITEM_MYITEMSHOES_2 = new com.electrifiedded.orespawn.item.ItemShoes(0, 3).setRegistryName("item_shoes_2").setTranslationKey("item_shoes_2");
    public static final Item ITEM_MYITEMSHOES_3 = new com.electrifiedded.orespawn.item.ItemShoes(0, 4).setRegistryName("item_shoes_3").setTranslationKey("item_shoes_3");
    public static final Item ITEM_MYJUMPYBUGSCALE = RegistryHandler.JUMPY_BUG_SCALE;
    public static final Item ITEM_MYKRAKENTOOTH = RegistryHandler.KRAKEN_TOOTH;
    public static final Item ITEM_MYLASERBALL = new com.electrifiedded.orespawn.item.ItemLaserBall(0);
    public static final Item ITEM_MYLAVAEEL = named(new com.electrifiedded.orespawn.item.ItemLavaEel(0, 2, 0.6F, false), "lava_eel");
    public static final Item ITEM_MYLETTUCE = new com.electrifiedded.orespawn.item.ItemPlantableFood("lettuce", 3, 0.45F,
        ExtraRegistry::lettucePlant, () -> net.minecraft.init.Blocks.FARMLAND);
    public static final Item ITEM_MYLOVE = food("love", 8, 0.95F);
    public static final Item ITEM_MYMANTISCLAW = new com.electrifiedded.orespawn.item.MantisClaw(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.EMERALD, "mantis_claw");
    public static final Item ITEM_MYMOSQUITOSEED = new com.electrifiedded.orespawn.item.ItemMosquitoSeed();
    public static final Item ITEM_MYMOTHSCALE = RegistryHandler.MOTH_SCALE;
    public static final Item ITEM_MYMOTHSEED = new com.electrifiedded.orespawn.item.ItemMothSeed();
    public static final Item ITEM_MYNIGHTMARESCALE = RegistryHandler.NIGHTMARE_SCALE;
    public static final Item ITEM_MYNIGHTMARESWORD = new com.electrifiedded.orespawn.item.tool.ItemEnchantedSword(
        com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.NIGHTMARE, "nightmare_sword",
        new net.minecraft.enchantment.Enchantment[]{net.minecraft.init.Enchantments.SHARPNESS, net.minecraft.init.Enchantments.KNOCKBACK, net.minecraft.init.Enchantments.FIRE_ASPECT},
        new int[]{1, 3, 1});
    public static final Item ITEM_MYPEACH = food("peach", 4, 0.55F);
    public static final Item ITEM_MYPEACHSEED = new com.electrifiedded.orespawn.item.ItemAppleSeed("peach_seed");
    public static final Item ITEM_MYPEACOCK = food("peacock", 12, 1.4F);
    public static final Item ITEM_MYPEACOCKFEATHER = RegistryHandler.simpleItem("peacock_feather");
    public static final Item ITEM_MYPINKFISH = named(new com.electrifiedded.orespawn.item.ItemGenericFish(0, 4, 0.6F, false), "pink_fish");
    public static final Item ITEM_MYPOISONSWORD = new com.electrifiedded.orespawn.item.tool.ItemEnchantedSword(
        com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.EMERALD, "poison_sword",
        new net.minecraft.enchantment.Enchantment[]{net.minecraft.init.Enchantments.SHARPNESS}, new int[]{1});
    public static final Item ITEM_MYPOPCORN = food("popcorn", 1, 0.5F);
    public static final Item ITEM_MYPOPCORNBAG = food("popcorn_bag", 10, 1.25F);
    public static final Item ITEM_MYPURPLEROCK = new com.electrifiedded.orespawn.item.ItemRock("purple_rock");
    public static final Item ITEM_MYQUEENBATTLEAXE = new com.electrifiedded.orespawn.item.UltimateSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.QUEEN_BATTLE, "queen_battle_axe");
    public static final Item ITEM_MYQUEENSCALE = RegistryHandler.QUEEN_SCALE;
    public static final Item ITEM_MYQUINOA = new com.electrifiedded.orespawn.item.ItemPlantableFood("quinoa", 7, 0.85F,
        ExtraRegistry::quinoaPlant, ExtraRegistry::crystalGrass);
    public static final Item ITEM_MYRADISH = new com.electrifiedded.orespawn.item.ItemPlantableFood("radish", 2, 0.45F,
        ExtraRegistry::radishPlant, () -> net.minecraft.init.Blocks.FARMLAND);
    public static final Item ITEM_MYRATSWORD = new com.electrifiedded.orespawn.item.RatSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.EMERALD, "rat_sword");
    public static final Item ITEM_MYRAWBACON = food("raw_bacon", 8, 1.0F);
    public static final Item ITEM_MYRAWCORNDOG = food("raw_corn_dog", 4, 0.6F);
    public static final Item ITEM_MYRAWCRABMEAT = food("raw_crab_meat", 4, 0.25F);
    public static final Item ITEM_MYRAWPEACOCK = food("raw_peacock", 6, 0.7F);
    public static final Item ITEM_MYRAYGUN = new com.electrifiedded.orespawn.item.ItemRayGun(0);
    public static final Item ITEM_MYREDROCK = new com.electrifiedded.orespawn.item.ItemRock("red_rock");
    public static final Item ITEM_MYRICE = new com.electrifiedded.orespawn.item.ItemPlantableFood("rice", 5, 0.65F,
        ExtraRegistry::ricePlant, ExtraRegistry::crystalGrass);
    public static final Item ITEM_MYROCK = new com.electrifiedded.orespawn.item.ItemRock("rock");
    public static final Item ITEM_MYROCKFISH = named(new com.electrifiedded.orespawn.item.ItemGenericFish(0, 3, 0.7F, false), "rock_fish");
    public static final Item ITEM_MYROSESWORD = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.EMERALD, "rose_sword");
    public static final Item ITEM_MYROYAL = new com.electrifiedded.orespawn.item.Bertha(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.ROYAL, "royal");
    public static final Item ITEM_MYRUBY = RegistryHandler.MY_RUBY;
    public static final Item ITEM_MYRUBYAXE = RegistryHandler.RUBY_AXE;
    public static final Item ITEM_MYRUBYHOE = RegistryHandler.RUBY_HOE;
    public static final Item ITEM_MYRUBYPICKAXE = RegistryHandler.RUBY_PICKAXE;
    public static final Item ITEM_MYRUBYSHOVEL = RegistryHandler.RUBY_SHOVEL;
    public static final Item ITEM_MYRUBYSWORD = RegistryHandler.RUBY_SWORD;
    public static final Item ITEM_MYSALAD = food("salad", 10, 0.95F);
    public static final Item ITEM_MYSALT = named(new com.electrifiedded.orespawn.item.ItemSalt(0), "salt");
    public static final Item ITEM_MYSKATEBOW = new com.electrifiedded.orespawn.item.SkateBow(0).setRegistryName("skate_bow").setTranslationKey("skate_bow");
    public static final Item ITEM_MYSLICE = new com.electrifiedded.orespawn.item.Slice(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.BERTHA, "slice");
    public static final Item ITEM_MYSMALLROCK = new com.electrifiedded.orespawn.item.ItemRock("small_rock");
    public static final Item ITEM_MYSPARKFISH = named(new com.electrifiedded.orespawn.item.ItemSparkFish(0, 1, 0.2F, false), "spark_fish");
    public static final Item ITEM_MYSPIKEYROCK = new com.electrifiedded.orespawn.item.ItemRock("spikey_rock");
    public static final Item ITEM_MYSQUIDZOOKA = new com.electrifiedded.orespawn.item.ItemSquidZooka(0).setRegistryName("squid_zooka").setTranslationKey("squid_zooka");
    public static final Item ITEM_MYSTEPACCROSS = named(new com.electrifiedded.orespawn.item.StepAccross(0), "step_accross");
    public static final Item ITEM_MYSTEPDOWN = named(new com.electrifiedded.orespawn.item.StepDown(0), "step_down");
    public static final Item ITEM_MYSTEPUP = named(new com.electrifiedded.orespawn.item.StepUp(0), "step_up");
    public static final Item ITEM_MYSTRAWBERRY = food("strawberry", 2, 0.65F);
    public static final Item ITEM_MYSTRAWBERRYSEED = new com.electrifiedded.orespawn.item.ItemStrawberrySeed();
    public static final Item ITEM_MYSUNFISH = named(new com.electrifiedded.orespawn.item.ItemSunFish(0, 6, 0.6F, false), "sun_fish");
    public static final Item ITEM_MYSUNSPOTURCHIN = new com.electrifiedded.orespawn.item.ItemSunspotUrchin(0).setRegistryName("sunspot_urchin").setTranslationKey("sunspot_urchin");
    public static final Item ITEM_MYTNTROCK = new com.electrifiedded.orespawn.item.ItemRock("tnt_rock");
    public static final Item ITEM_MYTHUNDERSTAFF = new com.electrifiedded.orespawn.item.ItemThunderStaff(0);
    public static final Item ITEM_MYTIGERSEYEAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnAxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.TIGERSEYE, "tigers_eye_axe");
    public static final Item ITEM_MYTIGERSEYEHOE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnHoe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.TIGERSEYE, "tigers_eye_hoe");
    public static final Item ITEM_MYTIGERSEYEINGOT = RegistryHandler.simpleItem("tigers_eye_ingot");
    public static final Item ITEM_MYTIGERSEYEPICKAXE = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnPickaxe(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.TIGERSEYE, "tigers_eye_pickaxe");
    public static final Item ITEM_MYTIGERSEYESHOVEL = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnShovel(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.TIGERSEYE, "tigers_eye_shovel");
    public static final Item ITEM_MYTIGERSEYESWORD = new com.electrifiedded.orespawn.item.tool.ItemOreSpawnSword(com.electrifiedded.orespawn.item.tool.OreSpawnToolMaterials.TIGERSEYE, "tigers_eye_sword");
    public static final Item ITEM_MYTOMATO = new com.electrifiedded.orespawn.item.ItemPlantableFood("tomato", 4, 0.55F,
        ExtraRegistry::tomatoPlant, () -> net.minecraft.init.Blocks.FARMLAND);
    public static final Item ITEM_MYULTIMATEAXE = RegistryHandler.ULTIMATE_AXE;
    public static final Item ITEM_MYULTIMATEBOW = new com.electrifiedded.orespawn.item.UltimateBow("ultimate_bow");
    public static final Item ITEM_MYULTIMATEFISHINGROD = new com.electrifiedded.orespawn.item.UltimateFishingRod("ultimate_fishing_rod");
    public static final Item ITEM_MYULTIMATEHOE = RegistryHandler.ULTIMATE_HOE;
    public static final Item ITEM_MYULTIMATEPICKAXE = RegistryHandler.ULTIMATE_PICKAXE;
    public static final Item ITEM_MYULTIMATESHOVEL = RegistryHandler.ULTIMATE_SHOVEL;
    public static final Item ITEM_MYULTIMATESWORD = RegistryHandler.ULTIMATE_SWORD;
    public static final Item ITEM_MYWATERBALL = new com.electrifiedded.orespawn.item.ItemWaterBall(0);
    public static final Item ITEM_MYWATERDRAGONSCALE = RegistryHandler.WATER_DRAGON_SCALE;
    public static final Item ITEM_MYWOODFISH = named(new com.electrifiedded.orespawn.item.ItemGenericFish(0, 5, 0.7F, false), "wood_fish");
    public static final Item ITEM_MYWRENCH = new com.electrifiedded.orespawn.item.ItemWrench(0);
    public static final Item ITEM_NASTYSAURUSEGG = egg("nastysaurus_egg", "Nastysaurus");
    public static final Item ITEM_NETHERLOST = named(new com.electrifiedded.orespawn.item.ItemNetherLost(0), "nether_lost");
    public static final Item ITEM_OSTRICHEGG = egg("ostrich_egg", "Ostrich");
    public static final Item ITEM_PEACOCKEGG = egg("peacock_egg", "Peacock");
    public static final Item ITEM_PITCHBLACKEGG = egg("pitch_black_egg", "Nightmare");
    public static final Item ITEM_POINTYSAURUSEGG = egg("pointysaurus_egg", "Pointysaurus");
    public static final Item ITEM_RAINBOWANTEGG = egg("rainbow_ant_egg", "Rainbow Ant");
    public static final Item ITEM_RANDOMDUNGEON = new com.electrifiedded.orespawn.item.ItemRandomDungeon("random_dungeon");
    public static final Item ITEM_RATEGG = egg("rat_egg", "Rat");
    public static final Item ITEM_REDANTEGG = egg("red_ant_egg", "Red Ant");
    public static final Item ITEM_REDCOWEGG = egg("red_cow_egg", "Apple Cow");
    public static final Item ITEM_ROBOT1EGG = egg("robot1_egg", "Bomb-Omb");
    public static final Item ITEM_ROBOT2EGG = egg("robot2_egg", "Robo-Pounder");
    public static final Item ITEM_ROBOT3EGG = egg("robot3_egg", "Robo-Gunner");
    public static final Item ITEM_ROBOT4EGG = egg("robot4_egg", "Robo-Warrior");
    public static final Item ITEM_ROBOT5EGG = egg("robot5_egg", "Robo-Sniper");
    public static final Item ITEM_ROTATOREGG = egg("rotator_egg", "Rotator");
    public static final Item ITEM_RUBBERDUCKYEGG = egg("rubber_ducky_egg", "Rubber Ducky");
    public static final Item ITEM_SCORPIONEGG = egg("scorpion_egg", "Scorpion");
    public static final Item ITEM_SEAMONSTEREGG = egg("sea_monster_egg", "Sea Monster");
    public static final Item ITEM_SEAMONSTERSCALE = RegistryHandler.SEA_MONSTER_SCALE;
    public static final Item ITEM_SEAVIPEREGG = egg("sea_viper_egg", "Sea Viper");
    public static final Item ITEM_SEAVIPERTONGUE = RegistryHandler.SEA_VIPER_TONGUE;
    public static final Item ITEM_SIFTER = new com.electrifiedded.orespawn.item.ItemSifter("sifter");
    public static final Item ITEM_SKATEEGG = egg("skate_egg", "Skate");
    public static final Item ITEM_SMALLWORMEGG = egg("small_worm_egg", "Small Worm");
    public static final Item ITEM_SNOWGOLEMEGG = egg("snow_golem_egg", "minecraft:snowman");
    public static final Item ITEM_SPIDERDRIVEREGG = egg("spider_driver_egg", "Spider Driver");
    public static final Item ITEM_SPIDERROBOTEGG = egg("spider_robot_egg", "Spider Robot");
    public static final Item ITEM_SPIDERROBOTKIT = new com.electrifiedded.orespawn.item.ItemSpiderRobotKit("spider_robot_kit", true);
    public static final Item ITEM_SPITBUGEGG = egg("spit_bug_egg", "Spit Bug");
    public static final Item ITEM_SPYROEGG = egg("spyro_egg", "Baby Dragon");
    public static final Item ITEM_STINKBUGEGG = egg("stink_bug_egg", "Stink Bug");
    public static final Item ITEM_STINKYEGG = egg("stinky_egg", "Stinky");
    public static final Item ITEM_TREXEGG = egg("t_rex_egg", "T. Rex");
    public static final Item ITEM_TREXTOOTH = RegistryHandler.simpleItem("t_rex_tooth");
    public static final Item ITEM_TERMITEEGG = egg("termite_egg", "Termite");
    public static final Item ITEM_TERRIBLETERROREGG = egg("terrible_terror_egg", "Terrible Terror");
    public static final Item ITEM_THEKINGEGG = egg("the_king_egg", "The King");
    public static final Item ITEM_THEPRINCEEGG = egg("the_prince_egg", "The Prince");
    public static final Item ITEM_THEPRINCESSEGG = egg("the_princess_egg", "The Princess");
    public static final Item ITEM_THEQUEENEGG = egg("the_queen_egg", "The Queen");
    public static final Item ITEM_TITANIUMNUGGET = RegistryHandler.TITANIUM_NUGGET;
    public static final Item ITEM_TRIFFIDEGG = egg("triffid_egg", "Triffid");
    public static final Item ITEM_TROOPERBUGEGG = egg("trooper_bug_egg", "Jumpy Bug");
    public static final Item ITEM_TSHIRTEGG = egg("tshirt_egg", "Tshirt");
    public static final Item ITEM_UNSTABLEANTEGG = egg("unstable_ant_egg", "Unstable Ant");
    public static final Item ITEM_URANIUMNUGGET = RegistryHandler.URANIUM_NUGGET;
    public static final Item ITEM_URCHINEGG = egg("urchin_egg", "Urchin");
    public static final Item ITEM_VELOEGG = egg("velo_egg", "Velocity Raptor");
    public static final Item ITEM_VORTEXEGG = egg("vortex_egg", "Vortex");
    public static final Item ITEM_VORTEXEYE = RegistryHandler.VORTEX_EYE;
    public static final Item ITEM_WATERDRAGONEGG = egg("water_dragon_egg", "Water Dragon");
    public static final Item ITEM_WHALEEGG = egg("whale_egg", "Whale");
    public static final Item ITEM_WITHERBOSSEGG = egg("wither_boss_egg", "minecraft:wither");
    public static final Item ITEM_WITHERSKELETONEGG = egg("wither_skeleton_egg", "minecraft:wither_skeleton");
    public static final Item ITEM_WORMTOOTH = RegistryHandler.simpleItem("worm_tooth");
    public static final Item ITEM_ZOOCAGE10 = new com.electrifiedded.orespawn.item.ZooCage("zoo_cage10", 10);
    public static final Item ITEM_ZOOCAGE2 = new com.electrifiedded.orespawn.item.ZooCage("zoo_cage2", 2);
    public static final Item ITEM_ZOOCAGE4 = new com.electrifiedded.orespawn.item.ZooCage("zoo_cage4", 4);
    public static final Item ITEM_ZOOCAGE6 = new com.electrifiedded.orespawn.item.ZooCage("zoo_cage6", 6);
    public static final Item ITEM_ZOOCAGE8 = new com.electrifiedded.orespawn.item.ZooCage("zoo_cage8", 8);
    public static final Item ITEM_ZOOKEEPER = new com.electrifiedded.orespawn.item.ItemZooKeeper("zoo_keeper");
    public static final Block BLOCK_CREEPERREPELLENT = new com.electrifiedded.orespawn.block.CreeperRepellent();
    public static final Block BLOCK_CRYSTALCOAL = new com.electrifiedded.orespawn.block.OreCrystal("crystal_coal", 0.6F, 6.0F, 20.0F);
    public static final Block BLOCK_CRYSTALCRYSTAL = new com.electrifiedded.orespawn.block.OreCrystalCrystal("crystal_crystal", 0.4F, 12.0F, 40.0F, false);
    public static final Block BLOCK_CRYSTALFAIRY = new com.electrifiedded.orespawn.block.OreBasicStone("crystal_fairy", 2.5F, 14.0F, "Fairy", 1, 6);
    public static final Block BLOCK_CRYSTALFLOWERBLUEBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("crystal_flower_blue_block");
    public static final Block BLOCK_CRYSTALFLOWERGREENBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("crystal_flower_green_block");
    public static final Block BLOCK_CRYSTALFLOWERREDBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("crystal_flower_red_block");
    public static final Block BLOCK_CRYSTALFLOWERYELLOWBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("crystal_flower_yellow_block");
    public static final Block BLOCK_CRYSTALFURNACEBLOCK = new com.electrifiedded.orespawn.block.workbench.BlockCrystalFurnace(false, "crystal_furnace_block");
    public static final Block BLOCK_CRYSTALFURNACEONBLOCK = new com.electrifiedded.orespawn.block.workbench.BlockCrystalFurnace(true, "crystal_furnace_on_block");
    public static final Block BLOCK_CRYSTALGRASS = new com.electrifiedded.orespawn.block.CrystalGrass("crystal_grass");
    public static final Block BLOCK_CRYSTALPLANKSBLOCK = RegistryHandler.CRYSTAL_PLANKS;
    public static final Block BLOCK_CRYSTALRAT = new com.electrifiedded.orespawn.block.OreBasicStone("crystal_rat", 2.5F, 14.0F, "Rat", 1, 10);
    public static final Block BLOCK_CRYSTALSTONE = RegistryHandler.CRYSTAL_STONE;
    public static final Block BLOCK_CRYSTALTORCH = RegistryHandler.CRYSTAL_TORCH;
    public static final Block BLOCK_CRYSTALWORKBENCHBLOCK = new com.electrifiedded.orespawn.block.workbench.BlockCrystalWorkbench("crystal_workbench_block");
    public static final Block BLOCK_EXTREMETORCH = RegistryHandler.EXTREME_TORCH;
    public static final Block BLOCK_KRAKENREPELLENT = new com.electrifiedded.orespawn.block.KrakenRepellent();
    public static final Block BLOCK_MYAPPLELEAVES = RegistryHandler.APPLE_LEAVES;
    public static final Block BLOCK_MYBUTTERFLYPLANT = new com.electrifiedded.orespawn.block.BlockCritterCrop("butterfly_plant", () -> ITEM_MYBUTTERFLYSEED, com.electrifiedded.orespawn.block.BlockCritterCrop.Kind.BUTTERFLY);
    public static final Block BLOCK_MYCHERRYLEAVES = plantLeaves("cherry_leaves");
    public static final Block BLOCK_MYCORNPLANT1 = new BlockTallCrop("corn_plant1", 1);
    public static final Block BLOCK_MYCORNPLANT2 = new BlockTallCrop("corn_plant2", 2);
    public static final Block BLOCK_MYCORNPLANT3 = new BlockTallCrop("corn_plant3", 3);
    public static final Block BLOCK_MYCORNPLANT4 = new BlockTallCrop("corn_plant4", 4);
    public static final Block BLOCK_MYCRYSTALLEAVES = plantLeaves("crystal_leaves");
    public static final Block BLOCK_MYCRYSTALLEAVES2 = plantLeaves("crystal_leaves2");
    public static final Block BLOCK_MYCRYSTALLEAVES3 = plantLeaves("crystal_leaves3");
    public static final Block BLOCK_MYCRYSTALPINKBLOCK = com.electrifiedded.orespawn.block.CrystalWood.pinkCrystal();
    public static final Block BLOCK_MYCRYSTALPLANT = new com.electrifiedded.orespawn.block.BlockCrystalPlant("crystal_plant", com.electrifiedded.orespawn.block.BlockCrystalPlant.Tree.TALL_CRYSTAL);
    public static final Block BLOCK_MYCRYSTALPLANT2 = new com.electrifiedded.orespawn.block.BlockCrystalPlant("crystal_plant2", com.electrifiedded.orespawn.block.BlockCrystalPlant.Tree.SCRAGGLY_CRYSTAL);
    public static final Block BLOCK_MYCRYSTALPLANT3 = new com.electrifiedded.orespawn.block.BlockCrystalPlant("crystal_plant3", com.electrifiedded.orespawn.block.BlockCrystalPlant.Tree.TALL_BLUE_CRYSTAL);
    public static final Block BLOCK_MYCRYSTALTREELOG = RegistryHandler.CRYSTAL_TREE_LOG;
    public static final Block BLOCK_MYDT = new com.electrifiedded.orespawn.block.BlockDuplicatorLog("dt");
    public static final Block BLOCK_MYDUNGEONSPAWNERBLOCK = new com.electrifiedded.orespawn.block.DungeonSpawnerBlock("dungeon_spawner_block");
    public static final Block BLOCK_MYENDERPEARLBLOCK = RegistryHandler.simpleBlock("ender_pearl_block");
    public static final Block BLOCK_MYEXPERIENCELEAVES = RegistryHandler.EXPERIENCE_LEAVES;
    public static final Block BLOCK_MYEXPERIENCEPLANT = new com.electrifiedded.orespawn.block.BlockCrystalPlant("experience_plant", com.electrifiedded.orespawn.block.BlockCrystalPlant.Tree.EXPERIENCE);
    public static final Block BLOCK_MYEYEOFENDERBLOCK = RegistryHandler.simpleBlock("eye_of_ender_block");
    public static final Block BLOCK_MYFIREFLYPLANT = new com.electrifiedded.orespawn.block.BlockCritterCrop("firefly_plant", () -> ITEM_MYFIREFLYSEED, com.electrifiedded.orespawn.block.BlockCritterCrop.Kind.FIREFLY);
    public static final Block BLOCK_MYFLOWERBLACKBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("flower_black_block");
    public static final Block BLOCK_MYFLOWERBLUEBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("flower_blue_block");
    public static final Block BLOCK_MYFLOWERPINKBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("flower_pink_block");
    public static final Block BLOCK_MYFLOWERSCARYBLOCK = new com.electrifiedded.orespawn.block.MyBlockFlower("flower_scary_block");
    public static final Block BLOCK_MYISLANDBLOCK = new com.electrifiedded.orespawn.block.IslandBlock("island_block");
    public static final Block BLOCK_MYKINGSPAWNERBLOCK = RegistryHandler.KING_SPAWNER;
    public static final Block BLOCK_MYLETTUCEPLANT1 = new BlockTallCrop("lettuce_plant1", 1);
    public static final Block BLOCK_MYLETTUCEPLANT2 = new BlockTallCrop("lettuce_plant2", 2);
    public static final Block BLOCK_MYLETTUCEPLANT3 = new BlockTallCrop("lettuce_plant3", 3);
    public static final Block BLOCK_MYLETTUCEPLANT4 = new BlockTallCrop("lettuce_plant4", 4);
    public static final Block BLOCK_MYMOLEDIRTBLOCK = new com.electrifiedded.orespawn.block.MoleDirtBlock("mole_dirt_block");
    public static final Block BLOCK_MYMOSQUITOPLANT = new com.electrifiedded.orespawn.block.BlockCritterCrop("mosquito_plant", () -> ITEM_MYMOSQUITOSEED, com.electrifiedded.orespawn.block.BlockCritterCrop.Kind.MOSQUITO);
    public static final Block BLOCK_MYMOTHPLANT = new com.electrifiedded.orespawn.block.BlockCritterCrop("moth_plant", () -> ITEM_MYMOTHSEED, com.electrifiedded.orespawn.block.BlockCritterCrop.Kind.MOTH);
    public static final Block BLOCK_MYPEACHLEAVES = plantLeaves("peach_leaves");
    public static final Block BLOCK_MYQUEENSPAWNERBLOCK = RegistryHandler.QUEEN_SPAWNER;
    public static final Block BLOCK_MYQUINOAPLANT1 = new BlockTallCrop("quinoa_plant1", 1);
    public static final Block BLOCK_MYQUINOAPLANT2 = new BlockTallCrop("quinoa_plant2", 2);
    public static final Block BLOCK_MYQUINOAPLANT3 = new BlockTallCrop("quinoa_plant3", 3);
    public static final Block BLOCK_MYQUINOAPLANT4 = new BlockTallCrop("quinoa_plant4", 4);
    public static final Block BLOCK_MYRTPBLOCK = new com.electrifiedded.orespawn.block.RTPBlock("rtp_block");
    public static final Block BLOCK_MYRADISHPLANT = new BlockOreSpawnCrop("radish_plant",
        () -> ITEM_MYRADISH, () -> ITEM_MYRADISH, 2, 4);
    public static final Block BLOCK_MYRICEPLANT = new BlockOreSpawnCrop("rice_plant",
        () -> ITEM_MYRICE, () -> ITEM_MYRICE, 2, 4);
    public static final Block BLOCK_MYSCARYLEAVES = RegistryHandler.SCARY_LEAVES;
    public static final Block BLOCK_MYSKYTREELOG = new com.electrifiedded.orespawn.block.BlockSkyTreeLog();
    public static final Block BLOCK_MYSTRAWBERRYPLANT = new BlockOreSpawnCrop("strawberry_plant",
        () -> ITEM_MYSTRAWBERRYSEED, () -> ITEM_MYSTRAWBERRY, 1, 5);
    public static final Block BLOCK_MYTIGERSEYEBLOCK = RegistryHandler.TIGERS_EYE;
    public static final Block BLOCK_MYTOMATOPLANT1 = new BlockTallCrop("tomato_plant1", 1);
    public static final Block BLOCK_MYTOMATOPLANT2 = new BlockTallCrop("tomato_plant2", 2);
    public static final Block BLOCK_MYTOMATOPLANT3 = new BlockTallCrop("tomato_plant3", 3);
    public static final Block BLOCK_MYTOMATOPLANT4 = new BlockTallCrop("tomato_plant4", 4);
    public static final Block BLOCK_REDANTTROLL = new com.electrifiedded.orespawn.block.OreBasicStone("red_ant_troll", 2.5F, 14.0F, "Red Ant", 15, 6);
    public static final Block BLOCK_TERMITETROLL = new com.electrifiedded.orespawn.block.OreBasicStone("termite_troll", 2.5F, 14.0F, "Termite", 15, 6);

    private static <T extends Item> T named(T item, String name) {
        item.setRegistryName(name);
        item.setTranslationKey(name);
        item.setCreativeTab(RegistryHandler.tabForItem(name));
        return item;
    }

    public static final Item[] ALL_ITEMS = new Item[]{
ITEM_ALIENEGG, ITEM_ALOEGG, ITEM_ANTROBOTEGG, ITEM_ANTROBOTKIT, ITEM_ATTACKSQUIDEGG, ITEM_BARYONYXEGG, ITEM_BASILEGG, ITEM_BEAVEREGG, ITEM_BEEEGG, ITEM_BERTHABLADE, ITEM_BERTHAGUARD, ITEM_BERTHAHANDLE, ITEM_BOYFRIENDEGG, ITEM_BROWNANTEGG, ITEM_BRUTALFLYEGG, ITEM_BUTTERFLYEGG, ITEM_CAGEEMPTY, ITEM_CAGEDALIEN, ITEM_CAGEDALO, ITEM_CAGEDATTACKSQUID, ITEM_CAGEDBARYONYX, ITEM_CAGEDBASIL, ITEM_CAGEDBAT, ITEM_CAGEDBEAVER, ITEM_CAGEDBEE, ITEM_CAGEDBLAZE, ITEM_CAGEDBOYFRIEND, ITEM_CAGEDBRUTALFLY, ITEM_CAGEDCAMA, ITEM_CAGEDCASSOWARY, ITEM_CAGEDCATERKILLER, ITEM_CAGEDCAVEFISHER, ITEM_CAGEDCAVESPIDER, ITEM_CAGEDCEPHADROME, ITEM_CAGEDCHICKEN, ITEM_CAGEDCHIPMUNK, ITEM_CAGEDCLIFFRACER, ITEM_CAGEDCLOUDSHARK, ITEM_CAGEDCOCKATEIL, ITEM_CAGEDCOW, ITEM_CAGEDCRAB, ITEM_CAGEDCREEPER, ITEM_CAGEDCREEPINGHORROR, ITEM_CAGEDCRICKET, ITEM_CAGEDCRIMINAL, ITEM_CAGEDCRYO, ITEM_CAGEDCRYSTALCOW, ITEM_CAGEDDRAGON, ITEM_CAGEDDRAGONFLY, ITEM_CAGEDDUNGEONBEAST, ITEM_CAGEDEASTERBUNNY, ITEM_CAGEDEMPERORSCORPION, ITEM_CAGEDENCHANTEDCOW, ITEM_CAGEDENDERDRAGON, ITEM_CAGEDENDERKNIGHT, ITEM_CAGEDENDERREAPER, ITEM_CAGEDENDERMAN, ITEM_CAGEDFAIRY, ITEM_CAGEDFIREFLY, ITEM_CAGEDFLOUNDER, ITEM_CAGEDFROG, ITEM_CAGEDGAMMAMETROID, ITEM_CAGEDGAZELLE, ITEM_CAGEDGHAST, ITEM_CAGEDGIRLFRIEND, ITEM_CAGEDGOLDCOW, ITEM_CAGEDGOLDFISH, ITEM_CAGEDHAMMERHEAD, ITEM_CAGEDHERCULES, ITEM_CAGEDHORSE, ITEM_CAGEDHYDRO, ITEM_CAGEDIRONGOLEM, ITEM_CAGEDIRUKANDJI, ITEM_CAGEDKRAKEN, ITEM_CAGEDKYUUBI, ITEM_CAGEDLARGEWORM, ITEM_CAGEDLEAFMONSTER, ITEM_CAGEDLEON, ITEM_CAGEDLIZARD, ITEM_CAGEDLURKINGTERROR, ITEM_CAGEDMOTHRA, ITEM_CAGEDMAGMACUBE, ITEM_CAGEDMANTIS, ITEM_CAGEDMEDIUMWORM, ITEM_CAGEDMOLENOID, ITEM_CAGEDMOOSHROOM, ITEM_CAGEDNASTYSAURUS, ITEM_CAGEDOCELOT, ITEM_CAGEDOSTRICH, ITEM_CAGEDPEACOCK, ITEM_CAGEDPIG, ITEM_CAGEDPITCHBLACK, ITEM_CAGEDPOINTYSAURUS, ITEM_CAGEDRAT, ITEM_CAGEDREDCOW, ITEM_CAGEDROTATOR, ITEM_CAGEDRUBBERDUCKY, ITEM_CAGEDSCORPION, ITEM_CAGEDSEAMONSTER, ITEM_CAGEDSEAVIPER, ITEM_CAGEDSHEEP, ITEM_CAGEDSILVERFISH, ITEM_CAGEDSKATE, ITEM_CAGEDSKELETON, ITEM_CAGEDSLIME, ITEM_CAGEDSMALLWORM, ITEM_CAGEDSNOWGOLEM, ITEM_CAGEDSPIDER, ITEM_CAGEDSPIDERDRIVER, ITEM_CAGEDSPIT, ITEM_CAGEDSPYRO, ITEM_CAGEDSQUID, ITEM_CAGEDSTINK, ITEM_CAGEDSTINKY, ITEM_CAGEDTREX, ITEM_CAGEDTERRIBLETERROR, ITEM_CAGEDTRIFFID, ITEM_CAGEDTROOPER, ITEM_CAGEDURCHIN, ITEM_CAGEDVELO, ITEM_CAGEDVILLAGER, ITEM_CAGEDVORTEX, ITEM_CAGEDWATERDRAGON, ITEM_CAGEDWHALE, ITEM_CAGEDWITCH, ITEM_CAGEDWITHERBOSS, ITEM_CAGEDWITHERSKELETON, ITEM_CAGEDWOLF, ITEM_CAGEDZOMBIE, ITEM_CAGEDZOMBIEPIGMAN, ITEM_CAMAEGG, ITEM_CASSOWARYEGG, ITEM_CATERKILLEREGG, ITEM_CATERKILLERJAW, ITEM_CAVEFISHEREGG, ITEM_CEPHADROMEEGG, ITEM_CHIPMUNKEGG, ITEM_CLIFFRACEREGG, ITEM_CLOUDSHARKEGG, ITEM_COCKATEILEGG, ITEM_COINEGG, ITEM_CRABEGG, ITEM_CREEPERLAUNCHER, ITEM_CREEPINGHORROREGG, ITEM_CRICKETEGG, ITEM_CRIMINALEGG, ITEM_CRYOEGG, ITEM_CRYSTALCOWEGG, ITEM_CRYSTALSTICKS, ITEM_DRAGONEGG, ITEM_DRAGONFLYEGG, ITEM_DUNGEONBEASTEGG, ITEM_EASTERBUNNYEGG, ITEM_EMPERORSCORPIONEGG, ITEM_ENCHANTEDCOWEGG, ITEM_ENDERDRAGONEGG, ITEM_ENDERKNIGHTEGG, ITEM_ENDERREAPEREGG, ITEM_FAIRYEGG, ITEM_FIREFLYEGG, ITEM_FLOUNDEREGG, ITEM_FROGEGG, ITEM_GAMMAMETROIDEGG, ITEM_GAZELLEEGG, ITEM_GHOSTEGG, ITEM_GHOSTSKELLYEGG, ITEM_GIRLFRIENDEGG, ITEM_GODZILLAEGG, ITEM_GOLDCOWEGG, ITEM_GOLDFISHEGG, ITEM_GREENGOO, ITEM_HAMMERHEADEGG, ITEM_HERCULESEGG, ITEM_HYDROEGG, ITEM_INSTANTGARDEN, ITEM_INSTANTSHELTER, ITEM_IRONGOLEMEGG, ITEM_IRUKANDJIEGG, ITEM_JEFFERYEGG, ITEM_KRAKENEGG, ITEM_KYUUBIEGG, ITEM_LARGEWORMEGG, ITEM_LEAFMONSTEREGG, ITEM_LEONEGG, ITEM_LIZARDEGG, ITEM_LURKINGTERROREGG, ITEM_MOTHRAEGG, ITEM_MAGICAPPLE, ITEM_MANTISEGG, ITEM_MEDIUMWORMEGG, ITEM_MINERSDREAM, ITEM_MOLENOIDEGG, ITEM_MOLENOIDNOSE, ITEM_MOSQUITOEGG, ITEM_MOTHEGG, ITEM_MYACID, ITEM_MYAMETHYST, ITEM_MYAMETHYSTAXE, ITEM_MYAMETHYSTHOE, ITEM_MYAMETHYSTPICKAXE, ITEM_MYAMETHYSTSHOVEL, ITEM_MYAMETHYSTSWORD, ITEM_MYAPPLESEED, ITEM_MYBLT, ITEM_MYBACON, ITEM_MYBASILISKSCALE, ITEM_MYBATTLEAXE, ITEM_MYBERTHA, ITEM_MYBIGHAMMER, ITEM_MYBLUEFISH, ITEM_MYBLUEROCK, ITEM_MYBUTTER, ITEM_MYBUTTERCANDY, ITEM_MYBUTTEREDPOPCORN, ITEM_MYBUTTEREDSALTEDPOPCORN, ITEM_MYBUTTERFLYSEED, ITEM_MYCHAINSAW, ITEM_MYCHEESE, ITEM_MYCHERRY, ITEM_MYCHERRYSEED, ITEM_MYCORNCOB, ITEM_MYCORNDOG, ITEM_MYCRABMEAT, ITEM_MYCRABBYPATTY, ITEM_MYCRYSTALAPPLE, ITEM_MYCRYSTALBLUEROCK, ITEM_MYCRYSTALGREENROCK, ITEM_MYCRYSTALPINKAXE, ITEM_MYCRYSTALPINKHOE, ITEM_MYCRYSTALPINKINGOT, ITEM_MYCRYSTALPINKPICKAXE, ITEM_MYCRYSTALPINKSHOVEL, ITEM_MYCRYSTALPINKSWORD, ITEM_MYCRYSTALREDROCK, ITEM_MYCRYSTALSTONEAXE, ITEM_MYCRYSTALSTONEHOE, ITEM_MYCRYSTALSTONEPICKAXE, ITEM_MYCRYSTALSTONESHOVEL, ITEM_MYCRYSTALSTONESWORD, ITEM_MYCRYSTALTNTROCK, ITEM_MYCRYSTALWOODAXE, ITEM_MYCRYSTALWOODHOE, ITEM_MYCRYSTALWOODPICKAXE, ITEM_MYCRYSTALWOODSHOVEL, ITEM_MYCRYSTALWOODSWORD, ITEM_MYDEADSTINKBUG, ITEM_MYELEVATOR, ITEM_MYEMERALDAXE, ITEM_MYEMERALDHOE, ITEM_MYEMERALDPICKAXE, ITEM_MYEMERALDSHOVEL, ITEM_MYEMERALDSWORD, ITEM_MYEMPERORSCORPIONSCALE, ITEM_MYEXPERIENCECATCHER, ITEM_MYEXPERIENCESWORD, ITEM_MYEXPERIENCETREESEED, ITEM_MYFAIRYSWORD, ITEM_MYFIREFISH, ITEM_MYFIREFLYSEED, ITEM_MYGODZILLASCALE, ITEM_MYGREENFISH, ITEM_MYGREENROCK, ITEM_MYGREYFISH, ITEM_MYHAMMY, ITEM_MYICEBALL, ITEM_MYIRUKANDJI, ITEM_MYIRUKANDJIARROW, ITEM_MYITEMGAMECONTROLLER, ITEM_MYITEMSHOES, ITEM_MYITEMSHOES_1, ITEM_MYITEMSHOES_2, ITEM_MYITEMSHOES_3, ITEM_MYJUMPYBUGSCALE, ITEM_MYKRAKENTOOTH, ITEM_MYLASERBALL, ITEM_MYLAVAEEL, ITEM_MYLETTUCE, ITEM_MYLOVE, ITEM_MYMANTISCLAW, ITEM_MYMOSQUITOSEED, ITEM_MYMOTHSCALE, ITEM_MYMOTHSEED, ITEM_MYNIGHTMARESCALE, ITEM_MYNIGHTMARESWORD, ITEM_MYPEACH, ITEM_MYPEACHSEED, ITEM_MYPEACOCK, ITEM_MYPEACOCKFEATHER, ITEM_MYPINKFISH, ITEM_MYPOISONSWORD, ITEM_MYPOPCORN, ITEM_MYPOPCORNBAG, ITEM_MYPURPLEROCK, ITEM_MYQUEENBATTLEAXE, ITEM_MYQUEENSCALE, ITEM_MYQUINOA, ITEM_MYRADISH, ITEM_MYRATSWORD, ITEM_MYRAWBACON, ITEM_MYRAWCORNDOG, ITEM_MYRAWCRABMEAT, ITEM_MYRAWPEACOCK, ITEM_MYRAYGUN, ITEM_MYREDROCK, ITEM_MYRICE, ITEM_MYROCK, ITEM_MYROCKFISH, ITEM_MYROSESWORD, ITEM_MYROYAL, ITEM_MYRUBY, ITEM_MYRUBYAXE, ITEM_MYRUBYHOE, ITEM_MYRUBYPICKAXE, ITEM_MYRUBYSHOVEL, ITEM_MYRUBYSWORD, ITEM_MYSALAD, ITEM_MYSALT, ITEM_MYSKATEBOW, ITEM_MYSLICE, ITEM_MYSMALLROCK, ITEM_MYSPARKFISH, ITEM_MYSPIKEYROCK, ITEM_MYSQUIDZOOKA, ITEM_MYSTEPACCROSS, ITEM_MYSTEPDOWN, ITEM_MYSTEPUP, ITEM_MYSTRAWBERRY, ITEM_MYSTRAWBERRYSEED, ITEM_MYSUNFISH, ITEM_MYSUNSPOTURCHIN, ITEM_MYTNTROCK, ITEM_MYTHUNDERSTAFF, ITEM_MYTIGERSEYEAXE, ITEM_MYTIGERSEYEHOE, ITEM_MYTIGERSEYEINGOT, ITEM_MYTIGERSEYEPICKAXE, ITEM_MYTIGERSEYESHOVEL, ITEM_MYTIGERSEYESWORD, ITEM_MYTOMATO, ITEM_MYULTIMATEAXE, ITEM_MYULTIMATEBOW, ITEM_MYULTIMATEFISHINGROD, ITEM_MYULTIMATEHOE, ITEM_MYULTIMATEPICKAXE, ITEM_MYULTIMATESHOVEL, ITEM_MYULTIMATESWORD, ITEM_MYWATERBALL, ITEM_MYWATERDRAGONSCALE, ITEM_MYWOODFISH, ITEM_MYWRENCH, ITEM_NASTYSAURUSEGG, ITEM_NETHERLOST, ITEM_OSTRICHEGG, ITEM_PEACOCKEGG, ITEM_PITCHBLACKEGG, ITEM_POINTYSAURUSEGG, ITEM_RAINBOWANTEGG, ITEM_RANDOMDUNGEON, ITEM_RATEGG, ITEM_REDANTEGG, ITEM_REDCOWEGG, ITEM_ROBOT1EGG, ITEM_ROBOT2EGG, ITEM_ROBOT3EGG, ITEM_ROBOT4EGG, ITEM_ROBOT5EGG, ITEM_ROTATOREGG, ITEM_RUBBERDUCKYEGG, ITEM_SCORPIONEGG, ITEM_SEAMONSTEREGG, ITEM_SEAMONSTERSCALE, ITEM_SEAVIPEREGG, ITEM_SEAVIPERTONGUE, ITEM_SIFTER, ITEM_SKATEEGG, ITEM_SMALLWORMEGG, ITEM_SNOWGOLEMEGG, ITEM_SPIDERDRIVEREGG, ITEM_SPIDERROBOTEGG, ITEM_SPIDERROBOTKIT, ITEM_SPITBUGEGG, ITEM_SPYROEGG, ITEM_STINKBUGEGG, ITEM_STINKYEGG, ITEM_TREXEGG, ITEM_TREXTOOTH, ITEM_TERMITEEGG, ITEM_TERRIBLETERROREGG, ITEM_THEKINGEGG, ITEM_THEPRINCEEGG, ITEM_THEPRINCESSEGG, ITEM_THEQUEENEGG, ITEM_TITANIUMNUGGET, ITEM_TRIFFIDEGG, ITEM_TROOPERBUGEGG, ITEM_TSHIRTEGG, ITEM_UNSTABLEANTEGG, ITEM_URANIUMNUGGET, ITEM_URCHINEGG, ITEM_VELOEGG, ITEM_VORTEXEGG, ITEM_VORTEXEYE, ITEM_WATERDRAGONEGG, ITEM_WHALEEGG, ITEM_WITHERBOSSEGG, ITEM_WITHERSKELETONEGG, ITEM_WORMTOOTH, ITEM_ZOOCAGE10, ITEM_ZOOCAGE2, ITEM_ZOOCAGE4, ITEM_ZOOCAGE6, ITEM_ZOOCAGE8, ITEM_ZOOKEEPER
    };
    public static final Block[] ALL_BLOCKS = new Block[]{
BLOCK_CREEPERREPELLENT, BLOCK_CRYSTALCOAL, BLOCK_CRYSTALCRYSTAL, BLOCK_CRYSTALFAIRY, BLOCK_CRYSTALFLOWERBLUEBLOCK, BLOCK_CRYSTALFLOWERGREENBLOCK, BLOCK_CRYSTALFLOWERREDBLOCK, BLOCK_CRYSTALFLOWERYELLOWBLOCK, BLOCK_CRYSTALFURNACEBLOCK, BLOCK_CRYSTALFURNACEONBLOCK, BLOCK_CRYSTALGRASS, BLOCK_CRYSTALPLANKSBLOCK, BLOCK_CRYSTALRAT, BLOCK_CRYSTALSTONE, BLOCK_CRYSTALTORCH, BLOCK_CRYSTALWORKBENCHBLOCK, BLOCK_EXTREMETORCH, BLOCK_KRAKENREPELLENT, BLOCK_MYAPPLELEAVES, BLOCK_MYBUTTERFLYPLANT, BLOCK_MYCHERRYLEAVES, BLOCK_MYCORNPLANT1, BLOCK_MYCORNPLANT2, BLOCK_MYCORNPLANT3, BLOCK_MYCORNPLANT4, BLOCK_MYCRYSTALLEAVES, BLOCK_MYCRYSTALLEAVES2, BLOCK_MYCRYSTALLEAVES3, BLOCK_MYCRYSTALPINKBLOCK, BLOCK_MYCRYSTALPLANT, BLOCK_MYCRYSTALPLANT2, BLOCK_MYCRYSTALPLANT3, BLOCK_MYCRYSTALTREELOG, BLOCK_MYDT, BLOCK_MYDUNGEONSPAWNERBLOCK, BLOCK_MYENDERPEARLBLOCK, BLOCK_MYEXPERIENCELEAVES, BLOCK_MYEXPERIENCEPLANT, BLOCK_MYEYEOFENDERBLOCK, BLOCK_MYFIREFLYPLANT, BLOCK_MYFLOWERBLACKBLOCK, BLOCK_MYFLOWERBLUEBLOCK, BLOCK_MYFLOWERPINKBLOCK, BLOCK_MYFLOWERSCARYBLOCK, BLOCK_MYISLANDBLOCK, BLOCK_MYKINGSPAWNERBLOCK, BLOCK_MYLETTUCEPLANT1, BLOCK_MYLETTUCEPLANT2, BLOCK_MYLETTUCEPLANT3, BLOCK_MYLETTUCEPLANT4, BLOCK_MYMOLEDIRTBLOCK, BLOCK_MYMOSQUITOPLANT, BLOCK_MYMOTHPLANT, BLOCK_MYPEACHLEAVES, BLOCK_MYQUEENSPAWNERBLOCK, BLOCK_MYQUINOAPLANT1, BLOCK_MYQUINOAPLANT2, BLOCK_MYQUINOAPLANT3, BLOCK_MYQUINOAPLANT4, BLOCK_MYRTPBLOCK, BLOCK_MYRADISHPLANT, BLOCK_MYRICEPLANT, BLOCK_MYSCARYLEAVES, BLOCK_MYSKYTREELOG, BLOCK_MYSTRAWBERRYPLANT, BLOCK_MYTIGERSEYEBLOCK, BLOCK_MYTOMATOPLANT1, BLOCK_MYTOMATOPLANT2, BLOCK_MYTOMATOPLANT3, BLOCK_MYTOMATOPLANT4, BLOCK_REDANTTROLL, BLOCK_TERMITETROLL
    };
}

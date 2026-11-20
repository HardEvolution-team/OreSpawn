package com.electrifiedded.orespawn;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.block.*;
import com.electrifiedded.orespawn.block.ore.*;
import com.electrifiedded.orespawn.item.*;
import com.electrifiedded.orespawn.item.armor.*;
import com.electrifiedded.orespawn.item.tool.*;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class RegistryHandler {
    private static <T extends Item> T named(T item, String name) {
        item.setTranslationKey(name);
        item.setRegistryName(name);
        return item;
    }
    public static final OreSpawnTab TAB_ORESPAWN = new OreSpawnTab();

    // Generic items/blocks created on-demand for OreSpawn.My* fields
    private static final java.util.List<Item> EXTRA_ITEMS = new java.util.ArrayList<>();
    private static final java.util.List<Block> EXTRA_BLOCKS = new java.util.ArrayList<>();

    // Vanilla creative tabs classification (as in the original mod)
    public static net.minecraft.creativetab.CreativeTabs tabForItem(String name) {
        if (name.contains("egg")) return net.minecraft.creativetab.CreativeTabs.MISC;
        if (name.matches(".*(sword|bertha|battle_axe|big_hammer|chainsaw|slice|mantis_claw|bow|wrench_gun|ray_gun|squid_zooka|creeper_launcher|acid|rocket_launcher|laser|thunder_staff).*")) return net.minecraft.creativetab.CreativeTabs.COMBAT;
        if (name.matches(".*(helmet|chest|chestplate|leggings|boots|shoe_|shoes).*")) return net.minecraft.creativetab.CreativeTabs.COMBAT;
        if (name.matches(".*(pickaxe|_axe|shovel|hoe|fishing_rod|sifter|crowbar|screwdriver|wrench).*")) return net.minecraft.creativetab.CreativeTabs.TOOLS;
        if (name.matches(".*(beef|bacon|pizza|butter|candy|sundae|cookie|apple|popcorn|corn|lettuce|rice|strawberry|tomato|quinoa|radish|cheese|cherries|peach|fish|egg|food).*")) return net.minecraft.creativetab.CreativeTabs.FOOD;
        if (name.matches(".*(ingot|gem|scale|tooth|jaw|tongue|eye|dust|nugget|stick|shard|crystal|handle|blade|guard|rock|ball|bucket|seed).*")) return net.minecraft.creativetab.CreativeTabs.MATERIALS;
        return net.minecraft.creativetab.CreativeTabs.MISC;
    }

    public static net.minecraft.creativetab.CreativeTabs tabForBlock(String name) {
        if (name.matches(".*(flower|plant|torch|crop|seedling).*")) return net.minecraft.creativetab.CreativeTabs.DECORATIONS;
        return net.minecraft.creativetab.CreativeTabs.BUILDING_BLOCKS;
    }

    public static java.util.List<Block> EXTRA_BLOCKS_STATIC() { return EXTRA_BLOCKS; }

    private static final java.util.Map<String, Item> ITEM_BY_NAME = new java.util.HashMap<>();
    private static final java.util.Map<String, Block> BLOCK_BY_NAME = new java.util.HashMap<>();
    private static final java.util.Map<String, Item> GENERATED_ENTITY_EGGS = new java.util.LinkedHashMap<>();

    public static Item simpleItem(String name) {
        Item existing = ITEM_BY_NAME.get(name);
        if (existing != null) return existing;
        Item i = isSpawnEgg(name) ? new ItemSpawnEgg(name, entityNameForEgg(name)) : new Item() {{ setMaxStackSize(64); }};
        i.setTranslationKey(name);
        if (i.getRegistryName() == null) {
            i.setRegistryName(name);
        }
        i.setCreativeTab(isSpawnEgg(name) ? TAB_ORESPAWN : tabForItem(name));
        EXTRA_ITEMS.add(i);
        ITEM_BY_NAME.put(name, i);
        return i;
    }

    private static boolean isSpawnEgg(String name) {
        return name.endsWith("_egg") && !name.equals("magic_egg");
    }

    private static String entityNameForEgg(String name) {
        String value = name.substring(0, name.length() - 4).replace('_', ' ');
        switch (value) {
            case "alo": return "Alosaurus";
            case "basil": return "Basilisk";
            case "cama": return "Camarasaurus";
            case "cryo": return "Cryolophosaurus";
            case "hydro": return "Hydrolisc";
            case "velo": return "Velocity Raptor";
            case "t rex": return "T. Rex";
            case "brown ant": return "Ant";
            case "godzilla": return "Mobzilla";
            case "pitch black": return "Pitch Black";
            case "jeffery": return "Jeffery";
            case "gamma metroid": return "WTF?";
            case "ghost skelly": return "Ghost Pumpkin Skelly";
            case "gold cow": return "Golden Apple Cow";
            case "crystal cow": return "Crystal Apple Cow";
            case "red cow": return "Apple Cow";
            case "leon": return "Leonopteryx";
            case "trooper bug": return "Jumpy Bug";
            case "cater killer": return "CaterKiller";
            case "cockateil": return "Bird";
            case "enchanted cow": return "Enchanted Golden Apple Cow";
            case "easter bunny": return "EasterBunny";
            case "kraken": return "The Kraken";
            case "urchin": return "Crystal Urchin";
            case "spider robot": return "Robot Spider";
            case "tshirt": return "T-Shirt";
            case "the king": return "The King";
            case "the queen": return "The Queen";
            case "the prince": return "The Prince";
            case "the princess": return "The Princess";
            case "robot1": return "Bomb-Omb";
            case "robot2": return "Robo-Pounder";
            case "robot3": return "Robo-Gunner";
            case "robot4": return "Robo-Warrior";
            case "robot5": return "Robo-Sniper";
            default: return value;
        }
    }

    public static Block simpleBlock(String name) {
        Block existing = BLOCK_BY_NAME.get(name);
        if (existing != null) return existing;
        Block b = new Block(net.minecraft.block.material.Material.ROCK) {
            @Override
            public net.minecraft.util.math.AxisAlignedBB getBoundingBox(net.minecraft.block.state.IBlockState state, net.minecraft.world.IBlockAccess source, net.minecraft.util.math.BlockPos pos) {
                return FULL_BLOCK_AABB;
            }
        };
        b.setHardness(3.0F).setResistance(5.0F);
        b.setTranslationKey(name);
        b.setRegistryName(name);
        b.setCreativeTab(tabForBlock(name));
        EXTRA_BLOCKS.add(b);
        BLOCK_BY_NAME.put(name, b);
        return b;
    }

    // Blocks - Ores
    public static final Block ORE_URANIUM = new BlockOreGlowing("ore_uranium", 10.0F, 1.0F, 10);
    public static final Block ORE_TITANIUM = new BlockOreGlowing("ore_titanium", 15.0F, 5.0F, 5);
    public static final Block ORE_AMETHYST = new BlockOreBase.Gem("ore_amethyst", () -> OreSpawn.MyAmethyst);
    public static final Block ORE_RUBY = new BlockOreBase.Gem("ore_ruby", () -> OreSpawn.MyRuby);
    public static final Block ORE_SALT = new BlockOreSalt();
    
    // Blocks - Special
    public static final Block LAVAFOAM = new BlockLavafoam();
    public static final Block PIZZA = new BlockPizza();
    public static final Block DUCT_TAPE = new BlockDuctTape();
    
    // Blocks - Ants
    public static final Block ANT_BLOCK = new BlockAnt("ant_block");
    public static final Block RED_ANT_BLOCK = new BlockAnt("red_ant_block");
    public static final Block TERMITE_BLOCK = new BlockAnt("termite_block");
    public static final Block CRYSTAL_TERMITE_BLOCK = new BlockAnt("crystal_termite_block");
    public static final Block RAINBOW_ANT_BLOCK = new BlockAnt("rainbow_ant_block");
    public static final Block UNSTABLE_ANT_BLOCK = new BlockAnt("unstable_ant_block");

    // Storage blocks
    public static final Block BLOCK_URANIUM = BlockStorage.metal("block_uranium", 0.2F);
    public static final Block BLOCK_TITANIUM = BlockStorage.metal("block_titanium", 0.5F);
    public static final Block BLOCK_MOBZILLA_SCALE = BlockStorage.gem("block_mobzilla_scale", true);
    public static final Block BLOCK_RUBY = BlockStorage.gem("block_ruby", false);
    public static final Block BLOCK_AMETHYST = BlockStorage.gem("block_amethyst", false);

    // Crystal blocks
    public static final Block CRYSTAL_STONE = new OreBasicStone("crystal_stone", 2.0F, 10.0F);
    public static final Block CRYSTAL_TREE_LOG = new BlockCrystalTreeLog();
    public static final Block CRYSTAL_PLANKS = CrystalWood.planks();
    public static final Block TIGERS_EYE = new OreCrystalCrystal("tigers_eye", 0.5F, 15.0F, 60.0F, true);
    public static final Block EXTREME_TORCH = new BlockExtremeTorch();
    public static final Block CRYSTAL_TORCH = new BlockCrystalTorch();
    public static final Block APPLE_LEAVES = new BlockAppleLeaves();
    public static final Block EXPERIENCE_LEAVES = new BlockExperienceLeaves();
    public static final Block SCARY_LEAVES = new BlockScaryLeaves();
    public static final Block KING_SPAWNER = new BossSpawnerBlock("king_spawner", false);
    public static final Block QUEEN_SPAWNER = new BossSpawnerBlock("queen_spawner", true);

    // Items - Basic materials
    public static final Item INGOT_URANIUM = new ItemIngot("ingot_uranium");
    public static final Item INGOT_TITANIUM = new ItemIngot("ingot_titanium");
    public static final Item URANIUM_NUGGET = new ItemNugget("uranium_nugget");
    public static final Item TITANIUM_NUGGET = new ItemNugget("titanium_nugget");
    public static final Item PIZZA_ITEM = new ItemPizza();
    public static final Item DUCT_TAPE_ITEM = new ItemDuctTape();

    // Gems
    public static final Item MY_RUBY = new ItemGem("ruby");
    public static final Item MY_AMETHYST = new ItemGem("amethyst");

    // Scales and teeth
    public static final Item MOBZILLA_SCALE = new ItemScale("mobzilla_scale");
    public static final Item DRAGON_SCALE = new ItemScale("dragon_scale");
    public static final Item GODZILLA_SCALE = new ItemScale("godzilla_scale");
    public static final Item KRAKEN_TOOTH = new ItemTooth("kraken_tooth");
    public static final Item TREX_TOOTH = new ItemTooth("trex_tooth");
    public static final Item BASILISK_SCALE = new ItemScale("basilisk_scale");
    public static final Item EMPEROR_SCORPION_SCALE = new ItemScale("emperor_scorpion_scale");
    public static final Item SEA_MONSTER_SCALE = new ItemScale("sea_monster_scale");
    public static final Item WATER_DRAGON_SCALE = new ItemScale("water_dragon_scale");
    public static final Item JUMPY_BUG_SCALE = new ItemScale("jumpy_bug_scale");
    public static final Item MOTH_SCALE = new ItemScale("moth_scale");
    public static final Item QUEEN_SCALE = new ItemScale("queen_scale");
    public static final Item NIGHTMARE_SCALE = new ItemScale("nightmare_scale");
    public static final Item LAVA_EEL_SCALE = new ItemScale("lava_eel_scale");
    public static final Item CATER_KILLER_JAW = new ItemTooth("cater_killer_jaw");
    public static final Item SEA_VIPER_TONGUE = new ItemTooth("sea_viper_tongue");
    public static final Item VORTEX_EYE = new ItemScale("vortex_eye");
    public static final Item MOLAR_SCALE = new ItemScale("molar_scale");

    // Ultimate tools
    public static final Item ULTIMATE_SWORD = new UltimateSword(OreSpawnToolMaterials.ULTIMATE, "ultimate_sword");
    public static final Item ULTIMATE_PICKAXE = named(new UltimatePickaxe(0, OreSpawnToolMaterials.ULTIMATE), "ultimate_pickaxe");
    public static final Item ULTIMATE_SHOVEL = named(new UltimateShovel(0, OreSpawnToolMaterials.ULTIMATE), "ultimate_shovel");
    public static final Item ULTIMATE_HOE = named(new UltimateHoe(0, OreSpawnToolMaterials.ULTIMATE), "ultimate_hoe");
    public static final Item ULTIMATE_AXE = named(new UltimateAxe(0, OreSpawnToolMaterials.ULTIMATE), "ultimate_axe");

    // Ultimate armor
    public static final Item ULTIMATE_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ULTIMATE, 0, EntityEquipmentSlot.HEAD, "ultimate_helmet");
    public static final Item ULTIMATE_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ULTIMATE, 0, EntityEquipmentSlot.CHEST, "ultimate_chest");
    public static final Item ULTIMATE_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ULTIMATE, 0, EntityEquipmentSlot.LEGS, "ultimate_leggings");
    public static final Item ULTIMATE_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ULTIMATE, 0, EntityEquipmentSlot.FEET, "ultimate_boots");

    // Ruby tools
    public static final Item RUBY_SWORD = new ItemOreSpawnSword(OreSpawnToolMaterials.RUBY, "ruby_sword");
    public static final Item RUBY_PICKAXE = new ItemOreSpawnPickaxe(OreSpawnToolMaterials.RUBY, "ruby_pickaxe");
    public static final Item RUBY_SHOVEL = new ItemOreSpawnShovel(OreSpawnToolMaterials.RUBY, "ruby_shovel");
    public static final Item RUBY_HOE = new ItemOreSpawnHoe(OreSpawnToolMaterials.RUBY, "ruby_hoe");
    public static final Item RUBY_AXE = new ItemOreSpawnAxe(OreSpawnToolMaterials.RUBY, "ruby_axe");

    // Ruby armor
    public static final Item RUBY_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.RUBY, 5, EntityEquipmentSlot.HEAD, "ruby_helmet");
    public static final Item RUBY_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.RUBY, 5, EntityEquipmentSlot.CHEST, "ruby_chest");
    public static final Item RUBY_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.RUBY, 5, EntityEquipmentSlot.LEGS, "ruby_leggings");
    public static final Item RUBY_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.RUBY, 5, EntityEquipmentSlot.FEET, "ruby_boots");

    // Amethyst tools
    public static final Item AMETHYST_SWORD = new ItemOreSpawnSword(OreSpawnToolMaterials.AMETHYST, "amethyst_sword");
    public static final Item AMETHYST_PICKAXE = new ItemOreSpawnPickaxe(OreSpawnToolMaterials.AMETHYST, "amethyst_pickaxe");
    public static final Item AMETHYST_SHOVEL = new ItemOreSpawnShovel(OreSpawnToolMaterials.AMETHYST, "amethyst_shovel");
    public static final Item AMETHYST_HOE = new ItemOreSpawnHoe(OreSpawnToolMaterials.AMETHYST, "amethyst_hoe");
    public static final Item AMETHYST_AXE = new ItemOreSpawnAxe(OreSpawnToolMaterials.AMETHYST, "amethyst_axe");

    // Amethyst armor
    public static final Item AMETHYST_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.AMETHYST, 6, EntityEquipmentSlot.HEAD, "amethyst_helmet");
    public static final Item AMETHYST_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.AMETHYST, 6, EntityEquipmentSlot.CHEST, "amethyst_chest");
    public static final Item AMETHYST_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.AMETHYST, 6, EntityEquipmentSlot.LEGS, "amethyst_leggings");
    public static final Item AMETHYST_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.AMETHYST, 6, EntityEquipmentSlot.FEET, "amethyst_boots");

    // Emerald tools
    public static final Item EMERALD_SWORD = new ItemOreSpawnSword(OreSpawnToolMaterials.EMERALD, "emerald_sword");
    public static final Item EMERALD_PICKAXE = named(new EmeraldPickaxe(0, OreSpawnToolMaterials.EMERALD), "emerald_pickaxe");
    public static final Item EMERALD_SHOVEL = new ItemOreSpawnShovel(OreSpawnToolMaterials.EMERALD, "emerald_shovel");
    public static final Item EMERALD_HOE = new ItemOreSpawnHoe(OreSpawnToolMaterials.EMERALD, "emerald_hoe");
    public static final Item EMERALD_AXE = new ItemOreSpawnAxe(OreSpawnToolMaterials.EMERALD, "emerald_axe");

    // Emerald armor
    public static final Item EMERALD_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EMERALD, 3, EntityEquipmentSlot.HEAD, "emerald_helmet");
    public static final Item EMERALD_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EMERALD, 3, EntityEquipmentSlot.CHEST, "emerald_chest");
    public static final Item EMERALD_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EMERALD, 3, EntityEquipmentSlot.LEGS, "emerald_leggings");
    public static final Item EMERALD_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EMERALD, 3, EntityEquipmentSlot.FEET, "emerald_boots");

    // Emerald armor

    // LavaEel armor
    public static final Item LAVAEEL_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAVAEEL, 1, EntityEquipmentSlot.HEAD, "lavaeel_helmet");
    public static final Item LAVAEEL_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAVAEEL, 1, EntityEquipmentSlot.CHEST, "lavaeel_chest");
    public static final Item LAVAEEL_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAVAEEL, 1, EntityEquipmentSlot.LEGS, "lavaeel_leggings");
    public static final Item LAVAEEL_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAVAEEL, 1, EntityEquipmentSlot.FEET, "lavaeel_boots");

    // MothScale armor
    public static final Item MOTHSCALE_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOTHSCALE, 2, EntityEquipmentSlot.HEAD, "mothscale_helmet");
    public static final Item MOTHSCALE_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOTHSCALE, 2, EntityEquipmentSlot.CHEST, "mothscale_chest");
    public static final Item MOTHSCALE_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOTHSCALE, 2, EntityEquipmentSlot.LEGS, "mothscale_leggings");
    public static final Item MOTHSCALE_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOTHSCALE, 2, EntityEquipmentSlot.FEET, "mothscale_boots");

    // Experience armor
    public static final Item EXPERIENCE_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EXPERIENCE, 4, EntityEquipmentSlot.HEAD, "experience_helmet");
    public static final Item EXPERIENCE_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EXPERIENCE, 4, EntityEquipmentSlot.CHEST, "experience_chest");
    public static final Item EXPERIENCE_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EXPERIENCE, 4, EntityEquipmentSlot.LEGS, "experience_leggings");
    public static final Item EXPERIENCE_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.EXPERIENCE, 4, EntityEquipmentSlot.FEET, "experience_boots");

    // CrystalPink armor
    public static final Item PINK_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PINK, 7, EntityEquipmentSlot.HEAD, "pink_helmet");
    public static final Item PINK_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PINK, 7, EntityEquipmentSlot.CHEST, "pink_chest");
    public static final Item PINK_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PINK, 7, EntityEquipmentSlot.LEGS, "pink_leggings");
    public static final Item PINK_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PINK, 7, EntityEquipmentSlot.FEET, "pink_boots");

    // TigersEye armor
    public static final Item TIGERSEYE_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.TIGERSEYE, 8, EntityEquipmentSlot.HEAD, "tigerseye_helmet");
    public static final Item TIGERSEYE_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.TIGERSEYE, 8, EntityEquipmentSlot.CHEST, "tigerseye_chest");
    public static final Item TIGERSEYE_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.TIGERSEYE, 8, EntityEquipmentSlot.LEGS, "tigerseye_leggings");
    public static final Item TIGERSEYE_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.TIGERSEYE, 8, EntityEquipmentSlot.FEET, "tigerseye_boots");

    // Peacock armor
    public static final Item PEACOCK_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PEACOCK, 9, EntityEquipmentSlot.HEAD, "peacock_helmet");
    public static final Item PEACOCK_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PEACOCK, 9, EntityEquipmentSlot.CHEST, "peacock_chest");
    public static final Item PEACOCK_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PEACOCK, 9, EntityEquipmentSlot.LEGS, "peacock_leggings");
    public static final Item PEACOCK_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.PEACOCK, 9, EntityEquipmentSlot.FEET, "peacock_boots");

    // Mobzilla armor
    public static final Item MOBZILLA_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOBZILLA, 10, EntityEquipmentSlot.HEAD, "mobzilla_helmet");
    public static final Item MOBZILLA_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOBZILLA, 10, EntityEquipmentSlot.CHEST, "mobzilla_chest");
    public static final Item MOBZILLA_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOBZILLA, 10, EntityEquipmentSlot.LEGS, "mobzilla_leggings");
    public static final Item MOBZILLA_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.MOBZILLA, 10, EntityEquipmentSlot.FEET, "mobzilla_boots");

    // Royal armor
    public static final Item ROYAL_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ROYAL, 11, EntityEquipmentSlot.HEAD, "royal_helmet");
    public static final Item ROYAL_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ROYAL, 11, EntityEquipmentSlot.CHEST, "royal_chest");
    public static final Item ROYAL_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ROYAL, 11, EntityEquipmentSlot.LEGS, "royal_leggings");
    public static final Item ROYAL_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.ROYAL, 11, EntityEquipmentSlot.FEET, "royal_boots");

    // Lapis armor
    public static final Item LAPIS_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAPIS, 12, EntityEquipmentSlot.HEAD, "lapis_helmet");
    public static final Item LAPIS_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAPIS, 12, EntityEquipmentSlot.CHEST, "lapis_chest");
    public static final Item LAPIS_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAPIS, 12, EntityEquipmentSlot.LEGS, "lapis_leggings");
    public static final Item LAPIS_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.LAPIS, 12, EntityEquipmentSlot.FEET, "lapis_boots");

    // Queen armor
    public static final Item QUEEN_HELMET = new ItemOreSpawnArmor(OreSpawnArmorMaterials.QUEEN, 13, EntityEquipmentSlot.HEAD, "queen_helmet");
    public static final Item QUEEN_CHESTPLATE = new ItemOreSpawnArmor(OreSpawnArmorMaterials.QUEEN, 13, EntityEquipmentSlot.CHEST, "queen_chest");
    public static final Item QUEEN_LEGGINGS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.QUEEN, 13, EntityEquipmentSlot.LEGS, "queen_leggings");
    public static final Item QUEEN_BOOTS = new ItemOreSpawnArmor(OreSpawnArmorMaterials.QUEEN, 13, EntityEquipmentSlot.FEET, "queen_boots");

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            ORE_URANIUM, ORE_TITANIUM, ORE_AMETHYST, ORE_RUBY, ORE_SALT,
            LAVAFOAM, PIZZA, DUCT_TAPE,
            ANT_BLOCK, RED_ANT_BLOCK, TERMITE_BLOCK, CRYSTAL_TERMITE_BLOCK,
            RAINBOW_ANT_BLOCK, UNSTABLE_ANT_BLOCK,
            BLOCK_URANIUM, BLOCK_TITANIUM, BLOCK_MOBZILLA_SCALE, BLOCK_RUBY, BLOCK_AMETHYST,
            CRYSTAL_STONE, CRYSTAL_TREE_LOG, CRYSTAL_PLANKS, TIGERS_EYE, EXTREME_TORCH,
            CRYSTAL_TORCH, APPLE_LEAVES, EXPERIENCE_LEAVES, SCARY_LEAVES,
            KING_SPAWNER, QUEEN_SPAWNER
        );
        for (Block block : ExtraRegistry.ALL_BLOCKS) {
            if (block == null) {
                throw new IllegalStateException("ExtraRegistry.ALL_BLOCKS contains a null block");
            }
            if (!event.getRegistry().containsKey(block.getRegistryName())) {
                event.getRegistry().register(block);
            }
        }
        for (Block block : ExtraRegistry.spawnBlocks()) {
            if (!event.getRegistry().containsKey(block.getRegistryName())) {
                event.getRegistry().register(block);
            }
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            INGOT_URANIUM, INGOT_TITANIUM, URANIUM_NUGGET, TITANIUM_NUGGET,
            PIZZA_ITEM, DUCT_TAPE_ITEM,
            MY_RUBY, MY_AMETHYST,
            MOBZILLA_SCALE, DRAGON_SCALE, GODZILLA_SCALE, KRAKEN_TOOTH, TREX_TOOTH,
            BASILISK_SCALE, EMPEROR_SCORPION_SCALE, SEA_MONSTER_SCALE, WATER_DRAGON_SCALE,
            JUMPY_BUG_SCALE, MOTH_SCALE, QUEEN_SCALE, NIGHTMARE_SCALE, LAVA_EEL_SCALE,
            CATER_KILLER_JAW, SEA_VIPER_TONGUE, VORTEX_EYE, MOLAR_SCALE,
            ULTIMATE_SWORD, ULTIMATE_PICKAXE, ULTIMATE_SHOVEL, ULTIMATE_HOE, ULTIMATE_AXE,
            ULTIMATE_HELMET, ULTIMATE_CHESTPLATE, ULTIMATE_LEGGINGS, ULTIMATE_BOOTS,
            RUBY_SWORD, RUBY_PICKAXE, RUBY_SHOVEL, RUBY_HOE, RUBY_AXE,
            RUBY_HELMET, RUBY_CHESTPLATE, RUBY_LEGGINGS, RUBY_BOOTS,
            AMETHYST_SWORD, AMETHYST_PICKAXE, AMETHYST_SHOVEL, AMETHYST_HOE, AMETHYST_AXE,
            AMETHYST_HELMET, AMETHYST_CHESTPLATE, AMETHYST_LEGGINGS, AMETHYST_BOOTS,
            EMERALD_SWORD, EMERALD_PICKAXE, EMERALD_SHOVEL, EMERALD_HOE, EMERALD_AXE,
            EMERALD_HELMET, EMERALD_CHESTPLATE, EMERALD_LEGGINGS, EMERALD_BOOTS,
            LAVAEEL_HELMET, LAVAEEL_CHESTPLATE, LAVAEEL_LEGGINGS, LAVAEEL_BOOTS,
            MOTHSCALE_HELMET, MOTHSCALE_CHESTPLATE, MOTHSCALE_LEGGINGS, MOTHSCALE_BOOTS,
            EXPERIENCE_HELMET, EXPERIENCE_CHESTPLATE, EXPERIENCE_LEGGINGS, EXPERIENCE_BOOTS,
            PINK_HELMET, PINK_CHESTPLATE, PINK_LEGGINGS, PINK_BOOTS,
            TIGERSEYE_HELMET, TIGERSEYE_CHESTPLATE, TIGERSEYE_LEGGINGS, TIGERSEYE_BOOTS,
            PEACOCK_HELMET, PEACOCK_CHESTPLATE, PEACOCK_LEGGINGS, PEACOCK_BOOTS,
            MOBZILLA_HELMET, MOBZILLA_CHESTPLATE, MOBZILLA_LEGGINGS, MOBZILLA_BOOTS,
            ROYAL_HELMET, ROYAL_CHESTPLATE, ROYAL_LEGGINGS, ROYAL_BOOTS,
            LAPIS_HELMET, LAPIS_CHESTPLATE, LAPIS_LEGGINGS, LAPIS_BOOTS,
            QUEEN_HELMET, QUEEN_CHESTPLATE, QUEEN_LEGGINGS, QUEEN_BOOTS
        );
        for (Item item : ExtraRegistry.ALL_ITEMS) {
            if (item.getRegistryName() == null) {
                throw new IllegalStateException("OreSpawn item has no registry name: " + item.getClass().getName());
            }
            if (!event.getRegistry().containsKey(item.getRegistryName())) {
                event.getRegistry().register(item);
            }
        }
        OreSpawnEntityRegistry.registerAliases();
        java.util.Set<String> existingEggTargets = new java.util.HashSet<>();
        for (Item item : ExtraRegistry.ALL_ITEMS) {
            if (item instanceof ItemSpawnEgg && item.getRegistryName() != null) {
                existingEggTargets.add(entityNameForEgg(item.getRegistryName().getPath()));
            }
        }
        for (java.util.Map.Entry<String, String> entry : OreSpawnEntityRegistry.getLivingEntityEggs().entrySet()) {
            if (existingEggTargets.contains(entry.getValue())) continue;
            String registryName = entry.getKey() + "_egg";
            Item egg = new ItemSpawnEgg(registryName, entry.getValue()).setCreativeTab(TAB_ORESPAWN);
            GENERATED_ENTITY_EGGS.put(registryName, egg);
            event.getRegistry().register(egg);
        }
        for (Block block : ExtraRegistry.ALL_BLOCKS) {
            if (net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS.getValue(block.getRegistryName()) == block) {
                if (block instanceof com.electrifiedded.orespawn.block.BlockTallCrop
                        || block instanceof com.electrifiedded.orespawn.block.BlockOreSpawnCrop) continue;
                registerItemBlock(event, block);
            }
        }
        for (Block block : ExtraRegistry.spawnBlocks()) {
            if (net.minecraftforge.fml.common.registry.ForgeRegistries.BLOCKS.getValue(block.getRegistryName()) == block) {
                registerItemBlock(event, block);
            }
        }
        

        // Register ItemBlocks for blocks
        registerItemBlock(event, ORE_URANIUM);
        registerItemBlock(event, ORE_TITANIUM);
        registerItemBlock(event, ORE_AMETHYST);
        registerItemBlock(event, ORE_RUBY);
        registerItemBlock(event, ORE_SALT);
        registerItemBlock(event, LAVAFOAM);
        registerItemBlock(event, PIZZA);
        registerItemBlock(event, DUCT_TAPE);
        registerItemBlock(event, ANT_BLOCK);
        registerItemBlock(event, RED_ANT_BLOCK);
        registerItemBlock(event, TERMITE_BLOCK);
        registerItemBlock(event, CRYSTAL_TERMITE_BLOCK);
        registerItemBlock(event, RAINBOW_ANT_BLOCK);
        registerItemBlock(event, UNSTABLE_ANT_BLOCK);
        registerItemBlock(event, BLOCK_URANIUM);
        registerItemBlock(event, BLOCK_TITANIUM);
        registerItemBlock(event, BLOCK_MOBZILLA_SCALE);
        registerItemBlock(event, BLOCK_RUBY);
        registerItemBlock(event, BLOCK_AMETHYST);
        registerItemBlock(event, CRYSTAL_STONE);
        registerItemBlock(event, CRYSTAL_TREE_LOG);
        registerItemBlock(event, CRYSTAL_PLANKS);
        registerItemBlock(event, TIGERS_EYE);
        registerItemBlock(event, EXTREME_TORCH);
        registerItemBlock(event, CRYSTAL_TORCH);
        registerItemBlock(event, APPLE_LEAVES);
        registerItemBlock(event, EXPERIENCE_LEAVES);
        registerItemBlock(event, SCARY_LEAVES);
        registerItemBlock(event, KING_SPAWNER);
        registerItemBlock(event, QUEEN_SPAWNER);
    }

    private static void registerItemBlock(RegistryEvent.Register<Item> event, Block block) {
        if (event.getRegistry().containsKey(block.getRegistryName())) {
            return;
        }
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        event.getRegistry().register(itemBlock);
    }
}

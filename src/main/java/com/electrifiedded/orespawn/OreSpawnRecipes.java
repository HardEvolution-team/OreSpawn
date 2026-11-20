package com.electrifiedded.orespawn;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static com.electrifiedded.orespawn.OreSpawn.*;

/** Crafting and furnace recipes ported from the legacy OreSpawnMain registration block. */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class OreSpawnRecipes {
    private static int recipeId;

    private OreSpawnRecipes() {}

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        recipeId = 0;

        shapeless(event, new ItemStack(CrystalPlanksBlock, 4), MyCrystalTreeLog);
        shapeless(event, new ItemStack(CrystalWorkbenchBlock), CrystalPlanksBlock, CrystalPlanksBlock, CrystalPlanksBlock, CrystalPlanksBlock);
        shaped(event, new ItemStack(CrystalFurnaceBlock), new String[]{"FFF", "F F", "FFF"}, 'F', CrystalStone);
        shaped(event, new ItemStack(Blocks.CHEST), new String[]{"FFF", "F F", "FFF"}, 'F', CrystalPlanksBlock);
        shaped(event, new ItemStack(Items.OAK_DOOR), new String[]{"FF ", "FF ", "FF "}, 'F', CrystalPlanksBlock);
        shaped(event, new ItemStack(Items.OAK_DOOR), new String[]{" FF", " FF", " FF"}, 'F', CrystalPlanksBlock);

        shaped(event, new ItemStack(MyUltimateSword), new String[]{" T ", " U ", " I "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateSword), new String[]{"T  ", "U  ", "I  "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateSword), new String[]{"  T", "  U", "  I"}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimatePickaxe), new String[]{"TUT", " U ", " I "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateShovel), new String[]{" U ", " T ", " I "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateShovel), new String[]{"U  ", "T  ", "I  "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateShovel), new String[]{"  U", "  T", "  I"}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateHoe), new String[]{"TU ", " I ", " I "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateAxe), new String[]{"TU ", "TI ", " I "}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyUltimateBow), new String[]{" TS", "I S", " US"}, 'S', Items.STRING, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MySkateBow), new String[]{" TS", "T S", " TS"}, 'S', Items.STRING, 'T', CrystalSticks);
        shaped(event, new ItemStack(MyUltimateFishingRod), new String[]{"  T", " US", "I S"}, 'S', Items.STRING, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(MyNightmareSword), new String[]{"ODO", "RTR", "OIO"}, 'I', Items.IRON_INGOT, 'O', MyNightmareScale, 'D', Items.DIAMOND, 'R', Items.REDSTONE, 'T', MyIngotTitanium);

        ordinaryToolSet(event, MyEmeraldSword, MyEmeraldPickaxe, MyEmeraldShovel, MyEmeraldHoe, MyEmeraldAxe, Items.STICK, Items.EMERALD);
        sword(event, MyRoseSword, Items.STICK, Blocks.RED_FLOWER);
        shaped(event, new ItemStack(MyExperienceSword), new String[]{"EEE", "EIE", "EEE"}, 'I', MyEmeraldSword, 'E', Items.EXPERIENCE_BOTTLE);
        shaped(event, new ItemStack(MyPoisonSword), new String[]{"EEE", "EIE", "EEE"}, 'I', MyEmeraldSword, 'E', MyDeadStinkBug);
        sword(event, MyRatSword, CrystalSticks, CrystalRat);
        sword(event, MyFairySword, CrystalSticks, CrystalFairy);
        ordinaryToolSet(event, MyCrystalWoodSword, MyCrystalWoodPickaxe, MyCrystalWoodShovel, MyCrystalWoodHoe, MyCrystalWoodAxe, CrystalSticks, CrystalPlanksBlock);
        ordinaryToolSet(event, MyCrystalPinkSword, MyCrystalPinkPickaxe, MyCrystalPinkShovel, MyCrystalPinkHoe, MyCrystalPinkAxe, CrystalSticks, MyCrystalPinkIngot);
        shaped(event, new ItemStack(Items.BUCKET), new String[]{"I I", " I "}, 'I', MyCrystalPinkIngot);
        ordinaryToolSet(event, MyTigersEyeSword, MyTigersEyePickaxe, MyTigersEyeShovel, MyTigersEyeHoe, MyTigersEyeAxe, CrystalSticks, MyTigersEyeIngot);
        ordinaryToolSet(event, MyCrystalStoneSword, MyCrystalStonePickaxe, MyCrystalStoneShovel, MyCrystalStoneHoe, MyCrystalStoneAxe, CrystalSticks, CrystalStone);
        ordinaryToolSet(event, MyRubySword, MyRubyPickaxe, MyRubyShovel, MyRubyHoe, MyRubyAxe, Items.STICK, MyRuby);
        ordinaryToolSet(event, MyAmethystSword, MyAmethystPickaxe, MyAmethystShovel, MyAmethystHoe, MyAmethystAxe, Items.STICK, MyAmethyst);

        shapeless(event, new ItemStack(MyHammy), MyUltimateSword, MyUltimateSword, MyBigHammer, GreenGoo);
        shapeless(event, new ItemStack(MyBattleAxe), MyUltimateSword, MyUltimateAxe, GreenGoo);
        shaped(event, new ItemStack(MyChainsaw), new String[]{"EEE", "EIE", "EEE"}, 'I', MyUltimateAxe, 'E', Blocks.REDSTONE_BLOCK);
        shaped(event, new ItemStack(MyQueenBattleAxe), new String[]{"EIE", "EIE", " I "}, 'I', Items.IRON_INGOT, 'E', MyQueenScale);
        shapeless(event, new ItemStack(MyBertha), BerthaHandle, BerthaGuard, BerthaBlade);
        shapeless(event, new ItemStack(BerthaHandle), MyRayGun, MyBigHammer, MyMantisClaw, MyWaterDragonScale, GreenGoo);
        shapeless(event, new ItemStack(BerthaGuard), MolenoidNose, SeaMonsterScale, MyMothScale, MyBasiliskScale, MyNightmareScale, MyEmperorScorpionScale, MyJumpyBugScale);
        shapeless(event, new ItemStack(BerthaBlade), MyKrakenTooth, WormTooth, TRexTooth, MyUltimateSword, CaterKillerJaw, SeaViperTongue, VortexEye);
        shapeless(event, new ItemStack(MySlice), MyBertha, Items.IRON_INGOT);
        shapeless(event, new ItemStack(MyIrukandjiArrow), MyPeacockFeather, MyIrukandji, CrystalSticks);
        shapeless(event, new ItemStack(Items.BED), MyPeacockFeather, CrystalPlanksBlock, MyPeacockFeather, CrystalPlanksBlock, MyPeacockFeather, CrystalPlanksBlock);
        ItemStack ink = new ItemStack(Items.DYE, 1, 0);
        shapeless(event, new ItemStack(MySquidZooka), Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, ink, ink, ink, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT);

        storage(event, MyIngotUranium, UraniumNugget, MyBlockUraniumBlock);
        storage(event, MyIngotTitanium, TitaniumNugget, MyBlockTitaniumBlock);
        blockStorage(event, MyBlockMobzillaScaleBlock, MyGodzillaScale);
        blockStorage(event, MyBlockRubyBlock, MyRuby);
        blockStorage(event, MyBlockAmethystBlock, MyAmethyst);
        blockStorage(event, MyCrystalPinkBlock, MyCrystalPinkIngot);
        blockStorage(event, MyTigersEyeBlock, MyTigersEyeIngot);
        blockStorage(event, MyEnderPearlBlock, Items.ENDER_PEARL);
        blockStorage(event, MyEyeOfEnderBlock, Items.ENDER_EYE);

        shaped(event, new ItemStack(MyThunderStaff), new String[]{"DR ", "RR ", "  R"}, 'D', Items.DIAMOND, 'R', MyRuby);
        shaped(event, new ItemStack(MyWrench), new String[]{"D D", " D ", " D "}, 'D', Items.IRON_INGOT);
        shapeless(event, new ItemStack(MyButter, 4), Items.MILK_BUCKET, Items.MILK_BUCKET);
        shapeless(event, new ItemStack(MyCheese, 2), Items.MILK_BUCKET, Items.MILK_BUCKET, Items.MILK_BUCKET, Items.MILK_BUCKET);
        shapeless(event, new ItemStack(MyButteredPopcorn), MyPopcorn, MyButter);
        shapeless(event, new ItemStack(MyButteredSaltedPopcorn), MyButteredPopcorn, MySalt);
        shapeless(event, new ItemStack(MyButteredSaltedPopcorn), MyPopcorn, MySalt, MyButter);
        shapeless(event, new ItemStack(MyPopcornBag), MyButteredSaltedPopcorn, MyButteredSaltedPopcorn, MyButteredSaltedPopcorn, MyButteredSaltedPopcorn, MyButteredSaltedPopcorn, MyButteredSaltedPopcorn, Items.PAPER, Items.PAPER, Items.PAPER);
        shapeless(event, new ItemStack(MyRawCornDog, 4), MyCornCob, Items.CHICKEN, Items.PORKCHOP, Items.STICK);
        shapeless(event, new ItemStack(MyRawBacon, 2), MySalt, Items.PORKCHOP);
        shapeless(event, new ItemStack(MyButterCandy, 4), MyButter, Items.SUGAR);
        shapeless(event, new ItemStack(MySalad), MyLettuce, MyTomato, MyRadish, Items.CARROT, Items.BOWL);
        shapeless(event, new ItemStack(MyBLT), MyBacon, MyLettuce, MyTomato, MyButter, Items.BREAD);
        shapeless(event, new ItemStack(MyPizzaItem), MyTomato, MyCheese, MyBacon, Items.BREAD);
        shaped(event, new ItemStack(MyDuctTapeItem), new String[]{"   ", "AAA", "RRR"}, 'R', Items.STRING, 'A', Items.SLIME_BALL);
        shapeless(event, new ItemStack(MyCrabbyPatty), MyCrabMeat, MyLettuce, MyTomato, Items.BREAD);

        shapeless(event, new ItemStack(ZooCage2), Blocks.IRON_BLOCK, Blocks.GLASS, Blocks.QUARTZ_BLOCK);
        shapeless(event, new ItemStack(ZooCage4), ZooCage2, Blocks.IRON_BLOCK, Blocks.GLASS, Blocks.QUARTZ_BLOCK);
        shapeless(event, new ItemStack(ZooCage6), ZooCage4, Blocks.IRON_BLOCK, Blocks.GLASS, Blocks.QUARTZ_BLOCK);
        shapeless(event, new ItemStack(ZooCage8), ZooCage6, Blocks.IRON_BLOCK, Blocks.GLASS, Blocks.QUARTZ_BLOCK);
        shapeless(event, new ItemStack(ZooCage10), ZooCage8, Blocks.IRON_BLOCK, Blocks.GLASS, Blocks.QUARTZ_BLOCK);
        shaped(event, new ItemStack(CageEmpty, 2), new String[]{"IWI", "W W", "IWI"}, 'I', Items.IRON_INGOT, 'W', Items.STICK);
        shapeless(event, new ItemStack(InstantShelter), Blocks.REDSTONE_BLOCK, Items.STICK, Blocks.COBBLESTONE);
        shapeless(event, new ItemStack(InstantGarden), Blocks.REDSTONE_BLOCK, Items.WHEAT, Items.GUNPOWDER);
        shapeless(event, new ItemStack(CreeperLauncher, 4), Items.PAPER, Items.REDSTONE, Items.STICK);
        shapeless(event, new ItemStack(NetherLost), Items.NETHER_STAR, Blocks.NETHERRACK);
        shaped(event, new ItemStack(Sifter), new String[]{"RRR", "RAR", "RRR"}, 'R', Items.STICK, 'A', Items.STRING);
        shaped(event, new ItemStack(MagicApple), new String[]{"RRR", "RAR", "RRR"}, 'R', Blocks.REDSTONE_BLOCK, 'A', Items.APPLE);
        shaped(event, new ItemStack(RandomDungeon), new String[]{"RRR", "RAR", "RRR"}, 'R', Blocks.REDSTONE_BLOCK, 'A', Items.COAL);
        shaped(event, new ItemStack(MinersDream), new String[]{"CCC", "RRR", "GGG"}, 'R', Blocks.REDSTONE_BLOCK, 'C', Blocks.CACTUS, 'G', MinersDreamExpensive == 0 ? Items.GUNPOWDER : Blocks.TNT);
        shaped(event, new ItemStack(MyElevator), new String[]{"   ", "WWW", "DRD"}, 'W', Blocks.PLANKS, 'R', Items.REDSTONE, 'D', Items.DIAMOND);
        shaped(event, new ItemStack(MyStepUp, 8), new String[]{"GC ", " C ", " C "}, 'C', Blocks.COBBLESTONE, 'G', Items.GUNPOWDER);
        shaped(event, new ItemStack(MyStepDown, 8), new String[]{" C ", " C ", "GC "}, 'C', Blocks.COBBLESTONE, 'G', Items.GUNPOWDER);
        shaped(event, new ItemStack(MyStepAccross, 8), new String[]{" C ", "GC ", " C "}, 'C', Blocks.COBBLESTONE, 'G', Items.GUNPOWDER);
        shapeless(event, new ItemStack(ExtremeTorch, 4), Items.REDSTONE, Items.STICK, Items.COAL);
        shapeless(event, new ItemStack(ExtremeTorch), Items.REDSTONE, Blocks.TORCH);
        shapeless(event, new ItemStack(CrystalSticks, 6), CrystalPlanksBlock, CrystalPlanksBlock);
        shapeless(event, new ItemStack(Blocks.PLANKS, 4), MySkyTreeLog);
        shapeless(event, new ItemStack(CrystalTorch, 6), CrystalCoal, CrystalSticks);
        shaped(event, new ItemStack(KrakenRepellent), new String[]{"D D", "STS", "D D"}, 'D', MyDeadStinkBug, 'T', ExtremeTorch, 'S', Items.STRING);
        shaped(event, new ItemStack(CreeperRepellent), new String[]{"D D", "STS", "D D"}, 'D', GreenGoo, 'T', ExtremeTorch, 'S', Items.STRING);
        shapeless(event, new ItemStack(MyAppleSeed, 6), Items.APPLE);
        shapeless(event, new ItemStack(MyCherrySeed), MyCherry);
        shapeless(event, new ItemStack(MyPeachSeed), MyPeach);
        shapeless(event, new ItemStack(MyExperienceCatcher), Items.GLASS_BOTTLE, Items.STICK, Items.STRING);
        shaped(event, new ItemStack(MyExperienceTreeSeed), new String[]{"EEE", "EAE", "EEE"}, 'A', MyAppleSeed, 'E', Items.EXPERIENCE_BOTTLE);

        registerDriedEggRecipes(event.getRegistry());

        // The legacy recipes intentionally match only fully depleted tools (metadata 32767).
        shapeless(event, new ItemStack(MyRayGun), Blocks.REDSTONE_BLOCK, new ItemStack(MyRayGun, 1, 32767));
        shapeless(event, new ItemStack(MySquidZooka), new ItemStack(Items.DYE, 1, 0), new ItemStack(MySquidZooka, 1, 32767));

        shaped(event, new ItemStack(UltimateHelmet), new String[]{"TIT", "U U"}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(UltimateBody), new String[]{"I I", "TTT", "UUU"}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(UltimateLegs), new String[]{"III", "T T", "U U"}, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        shaped(event, new ItemStack(UltimateBoots), new String[]{"T T", "U U"}, 'U', MyIngotUranium, 'T', MyIngotTitanium);
        armorSet(event, LavaEelHelmet, LavaEelBody, LavaEelLegs, LavaEelBoots, MyLavaEel);
        armorSet(event, MothScaleHelmet, MothScaleBody, MothScaleLegs, MothScaleBoots, MyMothScale);
        armorSet(event, EmeraldHelmet, EmeraldBody, EmeraldLegs, EmeraldBoots, Items.EMERALD);
        armorSet(event, RubyHelmet, RubyBody, RubyLegs, RubyBoots, MyRuby);
        armorSet(event, AmethystHelmet, AmethystBody, AmethystLegs, AmethystBoots, MyAmethyst);
        armorSet(event, CrystalPinkHelmet, CrystalPinkBody, CrystalPinkLegs, CrystalPinkBoots, MyCrystalPinkIngot);
        armorSet(event, MobzillaHelmet, MobzillaBody, MobzillaLegs, MobzillaBoots, MyGodzillaScale);
        armorSet(event, LapisHelmet, LapisBody, LapisLegs, LapisBoots, Blocks.LAPIS_BLOCK);
        armorSet(event, QueenHelmet, QueenBody, QueenLegs, QueenBoots, MyQueenScale);
        armorSet(event, PeacockFeatherHelmet, PeacockFeatherBody, PeacockFeatherLegs, PeacockFeatherBoots, MyPeacockFeather);
        armorSet(event, TigersEyeHelmet, TigersEyeBody, TigersEyeLegs, TigersEyeBoots, MyTigersEyeIngot);
        armorUpgrade(event, ExperienceHelmet, EmeraldHelmet);
        armorUpgrade(event, ExperienceBody, EmeraldBody);
        armorUpgrade(event, ExperienceLegs, EmeraldLegs);
        armorUpgrade(event, ExperienceBoots, EmeraldBoots);

        shaped(event, new ItemStack(CageEmpty, 2), new String[]{"IWI", "W W", "IWI"}, 'I', MyCrystalPinkIngot, 'W', CrystalSticks);
        shapeless(event, new ItemStack(Blocks.PLANKS, 4), MyDT);
        shaped(event, new ItemStack(Blocks.WEB), new String[]{"SSS", "S S", "SSS"}, 'S', Items.STRING);
        registerDriedEggPartRecipes(event);
    }

    /** Dried eggs of vanilla mobs hydrate into vanilla spawn eggs (legacy Items.spawn_egg metadata recipes). */
    private static final String[][] VANILLA_DRIED_EGGS = {
        {"spider", "spider"}, {"bat", "bat"}, {"cow", "cow"}, {"pig", "pig"}, {"squid", "squid"},
        {"chicken", "chicken"}, {"creeper", "creeper"}, {"skeleton", "skeleton"}, {"zombie", "zombie"},
        {"slime", "slime"}, {"ghast", "ghast"}, {"zombie_pigman", "zombie_pigman"}, {"enderman", "enderman"},
        {"cave_spider", "cave_spider"}, {"silverfish", "silverfish"}, {"magma_cube", "magma_cube"},
        {"witch", "witch"}, {"sheep", "sheep"}, {"wolf", "wolf"}, {"mooshroom", "mooshroom"},
        {"ocelot", "ocelot"}, {"blaze", "blaze"}, {"villager", "villager"}, {"horse", "horse"}
    };

    private static void registerDriedEggRecipes(IForgeRegistry<IRecipe> recipes) {
        java.util.Map<String, String> vanilla = new java.util.HashMap<>();
        for (String[] pair : VANILLA_DRIED_EGGS) vanilla.put(pair[0] + "_spawn_block", pair[1]);

        for (Block block : ExtraRegistry.spawnBlocks()) {
            if (!(block instanceof com.electrifiedded.orespawn.block.OreGenericEgg) || block.getRegistryName() == null) continue;
            String path = block.getRegistryName().getPath();
            ItemStack output;
            if (vanilla.containsKey(path)) {
                output = new ItemStack(Items.SPAWN_EGG);
                net.minecraft.item.ItemMonsterPlacer.applyEntityIdToItemStack(output, new ResourceLocation(vanilla.get(path)));
            } else if (path.equals("wither_spawn_block")) {
                output = new ItemStack(WitherBossEgg);
            } else {
                Item egg = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID, path.replace("_spawn_block", "_egg")));
                if (egg == null) continue;
                output = new ItemStack(egg);
            }

            NonNullList<net.minecraft.item.crafting.Ingredient> inputs = NonNullList.create();
            inputs.add(net.minecraft.item.crafting.Ingredient.fromItem(Items.WATER_BUCKET));
            inputs.add(net.minecraft.item.crafting.Ingredient.fromItem(Item.getItemFromBlock(block)));
            DriedEggRecipe recipe = new DriedEggRecipe(output, inputs);
            recipe.setRegistryName(Reference.MOD_ID, "hydrate_" + path);
            recipes.register(recipe);
        }
    }

    /** Nine dried egg parts assemble into the whole dried boss egg. */
    private static void registerDriedEggPartRecipes(RegistryEvent.Register<IRecipe> event) {
        Block[][] parts = {
            {MyGodzillaPartSpawnBlock, MyGodzillaSpawnBlock},
            {MyTheKingPartSpawnBlock, MyTheKingSpawnBlock},
            {MyTheQueenPartSpawnBlock, MyTheQueenSpawnBlock}
        };
        for (Block[] pair : parts) {
            Object[] inputs = new Object[9];
            java.util.Arrays.fill(inputs, pair[0]);
            shapeless(event, new ItemStack(pair[1]), inputs);
        }
    }

    private static final class DriedEggRecipe extends ShapelessRecipes {
        DriedEggRecipe(ItemStack output, NonNullList<net.minecraft.item.crafting.Ingredient> inputs) {
            super(Reference.MOD_ID + ":dried_eggs", output, inputs);
        }

        @Override
        public NonNullList<ItemStack> getRemainingItems(net.minecraft.inventory.InventoryCrafting inventory) {
            NonNullList<ItemStack> remaining = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
            for (int i = 0; i < remaining.size(); i++) {
                if (inventory.getStackInSlot(i).getItem() == Items.WATER_BUCKET) remaining.set(i, new ItemStack(Items.BUCKET));
            }
            return remaining;
        }
    }

    public static void registerSmelting() {
        smelt(MyOreUraniumBlock, new ItemStack(UraniumNugget), 0.3F);
        smelt(MyOreTitaniumBlock, new ItemStack(TitaniumNugget), 0.3F);
        smelt(MyOreSaltBlock, new ItemStack(MySalt, 8), 0.1F);
        smelt(MyCornCob, new ItemStack(MyPopcorn), 0.1F);
        smelt(MyRawCornDog, new ItemStack(MyCornDog), 0.4F);
        smelt(MyRawBacon, new ItemStack(MyBacon), 0.2F);
        smelt(CrystalCrystal, new ItemStack(MyCrystalPinkIngot), 0.3F);
        smelt(TigersEye, new ItemStack(MyTigersEyeIngot), 0.3F);
        smelt(MyRawPeacock, new ItemStack(MyPeacock), 0.4F);
        smelt(MyRawCrabMeat, new ItemStack(MyCrabMeat), 0.2F);
        for (Item fish : new Item[]{MyGreenFish, MyBlueFish, MyPinkFish, MyRockFish, MyWoodFish, MyGreyFish}) {
            smelt(fish, new ItemStack(Items.COOKED_FISH), 0.2F);
        }
    }

    public static void registerOreDictionary() {
        OreDictionary.registerOre("oreUranium", MyOreUraniumBlock);
        OreDictionary.registerOre("oreTitanium", MyOreTitaniumBlock);
        OreDictionary.registerOre("oreAmethyst", MyOreAmethystBlock);
        OreDictionary.registerOre("oreRuby", MyOreRubyBlock);
        OreDictionary.registerOre("ingotUranium", MyIngotUranium);
        OreDictionary.registerOre("ingotTitanium", MyIngotTitanium);
        OreDictionary.registerOre("gemAmethyst", MyAmethyst);
        OreDictionary.registerOre("gemRuby", MyRuby);
        OreDictionary.registerOre("nuggetUranium", UraniumNugget);
        OreDictionary.registerOre("nuggetTitanium", TitaniumNugget);
        OreDictionary.registerOre("blockUranium", MyBlockUraniumBlock);
        OreDictionary.registerOre("blockTitanium", MyBlockTitaniumBlock);
        OreDictionary.registerOre("blockAmethyst", MyBlockAmethystBlock);
        OreDictionary.registerOre("blockRuby", MyBlockRubyBlock);
    }

    private static void ordinaryToolSet(RegistryEvent.Register<IRecipe> event, Item sword, Item pickaxe, Item shovel, Item hoe, Item axe, Object stick, Object material) {
        sword(event, sword, stick, material);
        shaped(event, new ItemStack(pickaxe), new String[]{"EEE", " I ", " I "}, 'I', stick, 'E', material);
        shovel(event, shovel, stick, material);
        shaped(event, new ItemStack(hoe), new String[]{"EE ", " I ", " I "}, 'I', stick, 'E', material);
        shaped(event, new ItemStack(axe), new String[]{"EE ", "EI ", " I "}, 'I', stick, 'E', material);
    }

    private static void armorSet(RegistryEvent.Register<IRecipe> event, Item helmet, Item chest, Item legs, Item boots, Object material) {
        shaped(event, new ItemStack(helmet), new String[]{"MMM", "M M"}, 'M', material);
        shaped(event, new ItemStack(chest), new String[]{"M M", "MMM", "MMM"}, 'M', material);
        shaped(event, new ItemStack(legs), new String[]{"MMM", "M M", "M M"}, 'M', material);
        shaped(event, new ItemStack(boots), new String[]{"M M", "M M"}, 'M', material);
    }

    private static void armorUpgrade(RegistryEvent.Register<IRecipe> event, Item output, Item base) {
        shaped(event, new ItemStack(output), new String[]{"EEE", "EAE", "EEE"}, 'E', Items.EXPERIENCE_BOTTLE, 'A', base);
    }

    private static void sword(RegistryEvent.Register<IRecipe> event, Item output, Object stick, Object material) {
        shaped(event, new ItemStack(output), new String[]{" E ", " E ", " I "}, 'I', stick, 'E', material);
        shaped(event, new ItemStack(output), new String[]{"E  ", "E  ", "I  "}, 'I', stick, 'E', material);
        shaped(event, new ItemStack(output), new String[]{"  E", "  E", "  I"}, 'I', stick, 'E', material);
    }

    private static void shovel(RegistryEvent.Register<IRecipe> event, Item output, Object stick, Object material) {
        shaped(event, new ItemStack(output), new String[]{" E ", " I ", " I "}, 'I', stick, 'E', material);
        shaped(event, new ItemStack(output), new String[]{"E  ", "I  ", "I  "}, 'I', stick, 'E', material);
        shaped(event, new ItemStack(output), new String[]{"  E", "  I", "  I"}, 'I', stick, 'E', material);
    }

    private static void storage(RegistryEvent.Register<IRecipe> event, Item ingot, Item nugget, Block block) {
        shaped(event, new ItemStack(ingot), new String[]{"UUU", "UUU", "UUU"}, 'U', nugget);
        shapeless(event, new ItemStack(nugget, 9), ingot);
        blockStorage(event, block, ingot);
    }

    private static void blockStorage(RegistryEvent.Register<IRecipe> event, Block block, Item material) {
        shaped(event, new ItemStack(block), new String[]{"TTT", "TTT", "TTT"}, 'T', material);
        shapeless(event, new ItemStack(material, 9), block);
    }

    private static void shaped(RegistryEvent.Register<IRecipe> event, ItemStack output, String[] pattern, Object... keys) {
        int width = pattern[0].length();
        int height = pattern.length;
        NonNullList<net.minecraft.item.crafting.Ingredient> ingredients = NonNullList.withSize(width * height, net.minecraft.item.crafting.Ingredient.EMPTY);
        java.util.Map<Character, net.minecraft.item.crafting.Ingredient> map = new java.util.HashMap<>();
        for (int i = 0; i < keys.length; i += 2) map.put((Character) keys[i], ingredient(keys[i + 1]));
        for (int y = 0; y < height; y++) for (int x = 0; x < width; x++) ingredients.set(y * width + x, map.getOrDefault(pattern[y].charAt(x), net.minecraft.item.crafting.Ingredient.EMPTY));
        register(event, new ShapedRecipes(Reference.MOD_ID, width, height, ingredients, output));
    }

    private static void shapeless(RegistryEvent.Register<IRecipe> event, ItemStack output, Object... inputs) {
        NonNullList<net.minecraft.item.crafting.Ingredient> ingredients = NonNullList.create();
        for (Object input : inputs) ingredients.add(ingredient(input));
        register(event, new ShapelessRecipes(Reference.MOD_ID, output, ingredients));
    }

    private static net.minecraft.item.crafting.Ingredient ingredient(Object value) {
        if (value instanceof ItemStack) return net.minecraft.item.crafting.Ingredient.fromStacks((ItemStack) value);
        if (value instanceof Item) return net.minecraft.item.crafting.Ingredient.fromItem((Item) value);
        if (value instanceof Block) return net.minecraft.item.crafting.Ingredient.fromItem(Item.getItemFromBlock((Block) value));
        throw new IllegalArgumentException("Unsupported recipe ingredient: " + value);
    }

    private static void register(RegistryEvent.Register<IRecipe> event, IRecipe recipe) {
        recipe.setRegistryName(new ResourceLocation(Reference.MOD_ID, "legacy_" + recipeId++));
        event.getRegistry().register(recipe);
    }

    private static void smelt(Object input, ItemStack output, float experience) {
        if (input instanceof Item) GameRegistry.addSmelting((Item) input, output, experience);
        else GameRegistry.addSmelting((Block) input, output, experience);
    }
}

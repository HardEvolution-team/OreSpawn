package com.electrifiedded.orespawn;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.block.OreSpawnGUIHandler;
import com.electrifiedded.orespawn.block.workbench.TileEntityCrystalFurnace;
import com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry;
import com.electrifiedded.orespawn.inventory.ContainerCrystalFurnace;
import com.electrifiedded.orespawn.inventory.ContainerCrystalWorkbench;
import com.electrifiedded.orespawn.proxy.IProxy;
import com.electrifiedded.orespawn.world.OreSpawnDimension;
import com.electrifiedded.orespawn.world.OreSpawnWorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION,
    guiFactory = "com.electrifiedded.orespawn.client.gui.OreSpawnGuiFactory",
    dependencies = "required-after:archaiclib@[1.36.1,)")
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class OreSpawnMod {

    public static final String MOD_ID = Reference.MOD_ID;
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);
    public static Configuration config;

    @SidedProxy(modId = Reference.MOD_ID, clientSide = "com.electrifiedded.orespawn.proxy.ClientProxy", serverSide = "com.electrifiedded.orespawn.proxy.CommonProxy")
    public static IProxy proxy;

    @Mod.Instance(Reference.MOD_ID)
    public static OreSpawnMod instance;

    /** The original jar this port takes its textures and sounds from (it is never class-loaded). */
    public static final String ORIGINAL_NAME = "orespawn-1.7.10-20.3.jar (OreSpawn by TheyCallMeDanger)";
    public static final String ORIGINAL_URL = "https://dangerzone-archive.weebly.com/orespawn.html";

    /** modid / asset domain of the original jar (the port itself uses another modid so it cannot clash with the real OreSpawn) */
    public static final String ORIGINAL_MOD_ID = "orespawn";

    public OreSpawnMod() {
        com.electrifiedded.archaiclib.original.OriginalModAssets.mapDomain(Reference.MOD_ID, ORIGINAL_MOD_ID);
        OriginalAssetAliases.register();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        com.electrifiedded.archaiclib.original.OriginalModAssets.require(Reference.MOD_ID, ORIGINAL_MOD_ID, ORIGINAL_NAME, ORIGINAL_URL, ORIGINAL_MOD_ID);
        LOGGER.info("Pre-initializing {}!", Reference.MOD_NAME);
        
        config = new Configuration(event.getSuggestedConfigurationFile());
        OreSpawn.loadConfig(config);
        OreSpawn.OreSpawnUtils = new com.electrifiedded.orespawn.util.MyUtils();
        OreSpawn.Chunker = new com.electrifiedded.orespawn.util.ChunkOreGenerator();
        OreSpawn.BMaze = new com.electrifiedded.orespawn.world.BasiliskMaze();
        OreSpawn.RubyDungeon = new com.electrifiedded.orespawn.world.RubyBirdDungeon();
        OreSpawn.MyDungeon = new com.electrifiedded.orespawn.world.GenericDungeon();
        MinecraftForge.EVENT_BUS.register(LegacyIdRemapper.class);
        MinecraftForge.EVENT_BUS.register(OreSpawnFallHandler.class);
        MinecraftForge.EVENT_BUS.register(PortCreditHandler.class);
        OreSpawnDimension.registerDimensions();
        
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initializing {}!", Reference.MOD_NAME);
        
        GameRegistry.registerWorldGenerator(new OreSpawnWorldGenerator(), 10);
        OreSpawnEntityRegistry.registerEntities();
        NaturalSpawnRegistry.register();
        OreSpawnRecipes.registerSmelting();
        OreSpawnRecipes.registerOreDictionary();
        com.electrifiedded.orespawn.block.OreSpawnDispenserRegistry.register();
        
        // Register tile entities
        GameRegistry.registerTileEntity(TileEntityCrystalFurnace.class, Reference.MOD_ID + ":crystal_furnace");
        
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new OreSpawnGUIHandler());
        
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Post-initializing {}!", Reference.MOD_NAME);
        
        if (config.hasChanged()) {
            config.save();
        }
        
        proxy.postInit(event);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (!Reference.MOD_ID.equals(event.getModID()) || config == null) return;
        OreSpawn.loadConfig(config);
        if (config.hasChanged()) config.save();
    }
}

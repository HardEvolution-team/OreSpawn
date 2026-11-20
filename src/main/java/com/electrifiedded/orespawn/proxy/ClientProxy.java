package com.electrifiedded.orespawn.proxy;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.OreSpawnMod;
import com.electrifiedded.orespawn.RegistryHandler;
import com.electrifiedded.orespawn.ExtraRegistry;
import com.electrifiedded.orespawn.client.GirlfriendOverlayGui;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.client.render.*;
import com.electrifiedded.orespawn.client.model.*;
import com.electrifiedded.orespawn.network.RiderControl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.entity.player.EntityPlayer;
import com.electrifiedded.orespawn.block.workbench.TileEntityCrystalFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Mod.EventBusSubscriber(modid = OreSpawnMod.MOD_ID, value = net.minecraftforge.fml.relauncher.Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        registerRenderThings();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        registerBlockColors();
        registerSoundThings();
        registerKeyboardInput();
    }

    private void registerBlockColors() {
        IBlockColor grass = (state, world, pos, tint) -> tint != 0 ? 0xFFFFFF : world != null && pos != null
            ? world.getBiome(pos).getGrassColorAtPos(pos) : 0x91BD59;
        IBlockColor foliage = (state, world, pos, tint) -> tint != 0 ? 0xFFFFFF : world != null && pos != null
            ? world.getBiome(pos).getFoliageColorAtPos(pos) : ColorizerFoliage.getFoliageColorBasic();
        IBlockColor crystal = (state, world, pos, tint) -> tint == 0 ? 0xDDDDDD : 0xFFFFFF;
        Block[] antBlocks = { OreSpawn.MyAntBlock, OreSpawn.MyRedAntBlock, OreSpawn.TermiteBlock,
            OreSpawn.MyRainbowAntBlock, OreSpawn.MyUnstableAntBlock };
        Block[] leaves = { OreSpawn.MyAppleLeaves, OreSpawn.MyCherryLeaves, OreSpawn.MyPeachLeaves,
            OreSpawn.MyExperienceLeaves, OreSpawn.MyScaryLeaves };
        Block[] crystalLeaves = { OreSpawn.MyCrystalLeaves, OreSpawn.MyCrystalLeaves2, OreSpawn.MyCrystalLeaves3 };
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(grass, antBlocks);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(foliage, leaves);
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(crystal, crystalLeaves);
        for (Block block : antBlocks) registerItemTint(block, 0x91BD59);
        for (Block block : leaves) registerItemTint(block, ColorizerFoliage.getFoliageColorBasic());
        for (Block block : crystalLeaves) registerItemTint(block, 0xDDDDDD);
    }

    private void registerItemTint(Block block, int color) {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tint) -> color, Item.getItemFromBlock(block));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void sendRiderInput(EntityPlayer player) {
        // EntityPlayerSP already sends rotation and CPacketInput every tick while riding, and RiderControl
        // sends the OreSpawn keys. Re-sending them here (the legacy C05/C0C packets) only doubled the traffic.
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (id == 0 && tileEntity instanceof TileEntityCrystalFurnace) {
            return new com.electrifiedded.orespawn.client.gui.CrystalFurnaceGUI(player.inventory, (TileEntityCrystalFurnace) tileEntity);
        }
        if (id == 1) {
            return new com.electrifiedded.orespawn.client.gui.CrystalWorkbenchGUI(player.inventory, world, pos);
        }
        return null;
    }

    public void registerRenderThings() {
        MinecraftForge.EVENT_BUS.register(new GirlfriendOverlayGui(Minecraft.getMinecraft()));

        RenderingRegistry.registerEntityRenderingHandler(SunspotUrchin.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(WaterBall.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(InkSack.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(LaserBall.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(IceBall.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(Acid.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(DeadIrukandji.class, RenderItemUrchin::new);
        RenderingRegistry.registerEntityRenderingHandler(BerthaHit.class, RenderBerthaHit::new);
        RenderingRegistry.registerEntityRenderingHandler(ThunderBolt.class, RenderInvisibleProjectile::new);
        RenderingRegistry.registerEntityRenderingHandler(UltimateArrow.class, RenderUltimateArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(IrukandjiArrow.class, RenderIrukandjiArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(UltimateFishHook.class, RenderFish::new);
        
        RenderingRegistry.registerEntityRenderingHandler(Girlfriend.class, m -> new RenderGirlfriend(m, new ModelBiped(), 0.5F));
RenderingRegistry.registerEntityRenderingHandler(Boyfriend.class, m -> new RenderBoyfriend(m, new ModelBiped(), 0.55F));
RenderingRegistry.registerEntityRenderingHandler(RedCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7F));
RenderingRegistry.registerEntityRenderingHandler(GoldCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7F));
RenderingRegistry.registerEntityRenderingHandler(EnchantedCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7F));
RenderingRegistry.registerEntityRenderingHandler(CrystalCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7F));
RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, m -> new RenderButterfly(m, new ModelButterfly(1.0F), 0.3F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Firefly.class, m -> new RenderFirefly(m, new ModelFirefly(2.5F), 0.2F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(EntityLunaMoth.class, m -> new RenderButterfly(m, new ModelButterfly(0.75F), 0.4F, 1.5F));
RenderingRegistry.registerEntityRenderingHandler(EntityMosquito.class, m -> new RenderMosquito(m, new ModelMosquito(), 0.3F, 0.5F));
RenderingRegistry.registerEntityRenderingHandler(Ghost.class, m -> new RenderGhost(m, new ModelGhost(), 0.0F, 0.65F));
RenderingRegistry.registerEntityRenderingHandler(GhostSkelly.class, m -> new RenderGhostSkelly(m, new ModelGhostSkelly(), 0.0F, 1.05F));
RenderingRegistry.registerEntityRenderingHandler(Mothra.class, m -> new RenderButterfly(m, new ModelButterfly(0.2F), 0.75F, 10.0F));
RenderingRegistry.registerEntityRenderingHandler(EntityAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.1F, 0.25F));
RenderingRegistry.registerEntityRenderingHandler(EntityRedAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.15F, 0.35F));
RenderingRegistry.registerEntityRenderingHandler(EntityRainbowAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.1F, 0.25F));
RenderingRegistry.registerEntityRenderingHandler(EntityUnstableAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.1F, 0.25F));
RenderingRegistry.registerEntityRenderingHandler(Alosaurus.class, m -> new RenderAlosaurus(m, new ModelAlosaurus(0.22F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(TRex.class, m -> new RenderTRex(m, new ModelTRex(0.2F), 1.0F, 1.2F));
RenderingRegistry.registerEntityRenderingHandler(Tshirt.class, m -> new RenderTshirt(m, new ModelTshirt(0.22F), 1.0F, 0.33F));
RenderingRegistry.registerEntityRenderingHandler(Cryolophosaurus.class, m -> new RenderCryolophosaurus(m, new ModelCryolophosaurus(0.75F), 0.75F, 0.5F));
RenderingRegistry.registerEntityRenderingHandler(Basilisk.class, m -> new RenderBasilisk(m, new ModelBasilisk(0.3F), 0.5F, 1.25F));
RenderingRegistry.registerEntityRenderingHandler(Camarasaurus.class, m -> new RenderCamarasaurus(m, new ModelCamarasaurus(0.65F), 0.65F, 0.65F));
RenderingRegistry.registerEntityRenderingHandler(Hydrolisc.class, m -> new RenderHydrolisc(m, new ModelHydrolisc(0.65F), 0.65F, 0.65F));
RenderingRegistry.registerEntityRenderingHandler(VelocityRaptor.class, m -> new RenderVelocityRaptor(m, new ModelVelocityRaptor(1.25F), 0.55F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Dragonfly.class, m -> new RenderDragonfly(m, new ModelDragonfly(2.0F), 0.3F, 1.5F));
RenderingRegistry.registerEntityRenderingHandler(Bee.class, m -> new RenderBee(m, new ModelBee(2.0F), 0.9F, 1.1F));
RenderingRegistry.registerEntityRenderingHandler(EmperorScorpion.class, m -> new RenderEmperorScorpion(m, new ModelEmperorScorpion(0.22F), 0.95F, 1.5F));
RenderingRegistry.registerEntityRenderingHandler(Spyro.class, m -> new RenderSpyro(m, new ModelSpyro(0.65F), 0.65F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Baryonyx.class, m -> new RenderBaryonyx(m, new ModelBaryonyx(0.25F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(GammaMetroid.class, m -> new RenderGammaMetroid(m, new ModelGammaMetroid(0.45F), 0.75F, 0.9F));
RenderingRegistry.registerEntityRenderingHandler(Cockateil.class, m -> new RenderCockateil(m, new ModelCockateil(1.0F), 0.3F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(RubyBird.class, m -> new RenderCockateil(m, new ModelCockateil(1.0F), 0.3F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Kyuubi.class, m -> new RenderKyuubi(m, new ModelKyuubi(0.5F), 0.1F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Scorpion.class, m -> new RenderScorpion(m, new ModelScorpion(0.62F), 0.35F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(CaveFisher.class, m -> new RenderCaveFisher(m, new ModelCaveFisher(0.62F), 0.35F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Alien.class, m -> new RenderAlien(m, new ModelAlien(0.22F), 0.35F, 1.1F));
RenderingRegistry.registerEntityRenderingHandler(WaterDragon.class, m -> new RenderWaterDragon(m, new ModelWaterDragon(0.5F), 0.85F, 1.1F));
        RenderingRegistry.registerEntityRenderingHandler(AttackSquid.class, m -> new RenderAttackSquid(m, new ModelAttackSquid(1.0F), 0.25F, 0.9F));
        RenderingRegistry.registerEntityRenderingHandler(Elevator.class, RenderElevator::new);
RenderingRegistry.registerEntityRenderingHandler(Robot1.class, m -> new RenderRobot1(m, new ModelRobot1(2.0F), 0.3F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Robot2.class, m -> new RenderRobot2(m, new ModelRobot2(1.0F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Robot3.class, m -> new RenderRobot3(m, new ModelRobot3(1.0F), 1.0F, 0.5F));
RenderingRegistry.registerEntityRenderingHandler(Robot4.class, m -> new RenderRobot4(m, new ModelRobot4(1.0F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Robot5.class, m -> new RenderRobot5(m, new ModelRobot5(1.0F), 0.5F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Kraken.class, m -> new RenderKraken(m, new ModelKraken(1.0F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Lizard.class, m -> new RenderLizard(m, new ModelLizard(0.65F), 0.75F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Cephadrome.class, m -> new RenderCephadrome(m, new ModelCephadrome(0.55F), 1.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Dragon.class, m -> new RenderDragon(m, new ModelDragon(0.65F), 1.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Chipmunk.class, m -> new RenderChipmunk(m, new ModelChipmunk(1.0F), 0.15F, 0.9F));
RenderingRegistry.registerEntityRenderingHandler(Gazelle.class, m -> new RenderGazelle(m, new ModelGazelle(0.65F), 0.45F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Ostrich.class, m -> new RenderOstrich(m, new ModelOstrich(0.65F), 0.55F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(TrooperBug.class, m -> new RenderTrooperBug(m, new ModelTrooperBug(0.22F), 0.95F, 1.1F));
RenderingRegistry.registerEntityRenderingHandler(SpitBug.class, m -> new RenderSpitBug(m, new ModelSpitBug(0.55F), 0.55F, 0.75F));
        RenderingRegistry.registerEntityRenderingHandler(StinkBug.class, m -> new RenderStinkBug(m, new ModelStinkBug(0.75F), 0.35F, 0.85F));
        RenderingRegistry.registerEntityRenderingHandler(Island.class, m -> new RenderIsland(m, new ModelIsland(1.0F), 0.25F, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(IslandToo.class, m -> new RenderIslandToo(m, new ModelIsland(1.0F), 0.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(CreepingHorror.class, m -> new RenderCreepingHorror(m, new ModelCreepingHorror(), 0.45F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(TerribleTerror.class, m -> new RenderTerribleTerror(m, new ModelTerribleTerror(), 0.45F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(CliffRacer.class, m -> new RenderCliffRacer(m, new ModelCliffRacer(1.0F), 0.3F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Triffid.class, m -> new RenderTriffid(m, new ModelTriffid(1.0F), 0.3F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(PitchBlack.class, m -> new RenderPitchBlack(m, new ModelPitchBlack(0.65F), 1.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(LurkingTerror.class, m -> new RenderLurkingTerror(m, new ModelLurkingTerror(), 0.45F, 0.85F));
        RenderingRegistry.registerEntityRenderingHandler(Godzilla.class, m -> new RenderGodzilla(m, new ModelGodzilla(0.2F), 1.0F, 2.0F));
        RenderingRegistry.registerEntityRenderingHandler(GodzillaHead.class, RenderGodzillaHead::new);
        RenderingRegistry.registerEntityRenderingHandler(KingHead.class, RenderKingHead::new);
        RenderingRegistry.registerEntityRenderingHandler(QueenHead.class, RenderQueenHead::new);
RenderingRegistry.registerEntityRenderingHandler(WormSmall.class, m -> new RenderWormSmall(m, new ModelWormSmall(), 0.1F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(WormMedium.class, m -> new RenderWormMedium(m, new ModelWormMedium(), 0.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(WormLarge.class, m -> new RenderWormLarge(m, new ModelWormLarge(), 0.9F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Cassowary.class, m -> new RenderCassowary(m, new ModelCassowary(0.55F), 0.5F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(GoldFish.class, m -> new RenderGoldFish(m, new ModelGoldFish(0.7F), 0.2F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(CloudShark.class, m -> new RenderCloudShark(m, new ModelCloudShark(1.0F), 0.5F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(LeafMonster.class, m -> new RenderLeafMonster(m, new ModelLeafMonster(), 0.65F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(EnderKnight.class, m -> new RenderEnderKnight(m, new ModelEnderKnight(0.21F), 0.3F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(EnderReaper.class, m -> new RenderEnderReaper(m, new ModelEnderReaper(0.23F), 0.2F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Beaver.class, m -> new RenderBeaver(m, new ModelBeaver(0.5F), 0.15F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Termite.class, m -> new RenderAnt(m, new ModelAnt(), 0.15F, 0.35F));
RenderingRegistry.registerEntityRenderingHandler(Fairy.class, m -> new RenderFairy(m, new ModelFairy(1.5F), 0.1F, 0.35F));
RenderingRegistry.registerEntityRenderingHandler(Peacock.class, m -> new RenderPeacock(m, new ModelPeacock(0.75F), 0.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Rotator.class, m -> new RenderRotator(m, new ModelRotator(0.25F), 0.1F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Vortex.class, m -> new RenderVortex(m, new ModelVortex(0.25F), 0.1F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(DungeonBeast.class, m -> new RenderDungeonBeast(m, new ModelDungeonBeast(0.62F), 0.25F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Rat.class, m -> new RenderRat(m, new ModelRat(1.0F), 0.1F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Flounder.class, m -> new RenderFlounder(m, new ModelFlounder(), 0.1F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Whale.class, m -> new RenderWhale(m, new ModelWhale(), 0.1F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Irukandji.class, m -> new RenderIrukandji(m, new ModelIrukandji(1.0F), 0.1F, 0.25F));
RenderingRegistry.registerEntityRenderingHandler(Skate.class, m -> new RenderSkate(m, new ModelSkate(1.0F), 0.1F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Urchin.class, m -> new RenderUrchin(m, new ModelUrchin(1.0F), 0.35F, 1.25F));
RenderingRegistry.registerEntityRenderingHandler(Mantis.class, m -> new RenderMantis(m, new ModelMantis(2.0F), 0.9F, 1.1F));
RenderingRegistry.registerEntityRenderingHandler(HerculesBeetle.class, m -> new RenderHerculesBeetle(m, new ModelHerculesBeetle(1.0F), 0.99F, 1.1F));
RenderingRegistry.registerEntityRenderingHandler(Stinky.class, m -> new RenderStinky(m, new ModelStinky(0.65F), 0.75F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Coin.class, m -> new RenderCoin(m, new ModelCoin(0.22F), 0.75F, 0.125F));
RenderingRegistry.registerEntityRenderingHandler(TheKing.class, m -> new RenderTheKing(m, new ModelTheKing(0.65F), 1.9F, 2.1F));
RenderingRegistry.registerEntityRenderingHandler(TheQueen.class, m -> new RenderTheQueen(m, new ModelTheQueen(0.65F), 1.9F, 2.0F));
RenderingRegistry.registerEntityRenderingHandler(ThePrince.class, m -> new RenderThePrince(m, new ModelThePrince(0.65F), 0.75F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(Molenoid.class, m -> new RenderMolenoid(m, new ModelMolenoid(0.5F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(SeaMonster.class, m -> new RenderSeaMonster(m, new ModelSeaMonster(0.5F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(SeaViper.class, m -> new RenderSeaViper(m, new ModelSeaViper(0.5F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(EasterBunny.class, m -> new RenderEasterBunny(m, new ModelEasterBunny(0.55F), 0.5F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(CaterKiller.class, m -> new RenderCaterKiller(m, new ModelCaterKiller(0.22F), 1.0F, 1.25F));
RenderingRegistry.registerEntityRenderingHandler(Leon.class, m -> new RenderLeon(m, new ModelLeon(0.22F), 1.0F, 1.75F));
RenderingRegistry.registerEntityRenderingHandler(Hammerhead.class, m -> new RenderHammerhead(m, new ModelHammerhead(0.33F), 1.0F, 2.5F));
RenderingRegistry.registerEntityRenderingHandler(RubberDucky.class, m -> new RenderRubberDucky(m, new ModelRubberDucky(1.0F), 0.15F, 0.75F));
RenderingRegistry.registerEntityRenderingHandler(ThePrinceTeen.class, m -> new RenderThePrinceTeen(m, new ModelThePrinceTeen(0.65F), 1.0F, 1.25F));
RenderingRegistry.registerEntityRenderingHandler(BandP.class, m -> new RenderBandP(m, new ModelBandP(0.4F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(RockBase.class, m -> new RenderRockBase(m, new ModelRockBase(1.0F), 0.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(PurplePower.class, m -> new RenderPurplePower(m, new ModelPurplePower(1.0F), 0.3F, 2.75F));
RenderingRegistry.registerEntityRenderingHandler(Brutalfly.class, m -> new RenderBrutalfly(m, new ModelBrutalfly(0.2F), 0.75F, 9.0F));
RenderingRegistry.registerEntityRenderingHandler(Nastysaurus.class, m -> new RenderNastysaurus(m, new ModelNastysaurus(0.65F), 1.0F, 1.5F));
RenderingRegistry.registerEntityRenderingHandler(Pointysaurus.class, m -> new RenderPointysaurus(m, new ModelPointysaurus(1.0F), 1.0F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Cricket.class, m -> new RenderCricket(m, new ModelCricket(2.5F), 0.15F, 0.5F));
RenderingRegistry.registerEntityRenderingHandler(ThePrincess.class, m -> new RenderThePrincess(m, new ModelThePrincess(0.65F), 0.7F, 0.7F));
RenderingRegistry.registerEntityRenderingHandler(Frog.class, m -> new RenderFrog(m, new ModelFrog(1.0F), 0.35F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(ThePrinceAdult.class, m -> new RenderThePrinceAdult(m, new ModelThePrinceAdult(0.65F), 1.2F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(SpiderRobot.class, m -> new RenderSpiderRobot(m, new ModelSpiderRobot(1.0F), 0.99F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(SpiderDriver.class, m -> new RenderSpiderDriver(m));
RenderingRegistry.registerEntityRenderingHandler(GiantRobot.class, m -> new RenderGiantRobot(m, new ModelGiantRobot(0.25F), 0.99F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(AntRobot.class, m -> new RenderAntRobot(m, new ModelAntRobot(1.0F), 0.99F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(Crab.class, m -> new RenderCrab(m, new ModelCrab(1.0F), 0.99F, 1.0F));
RenderingRegistry.registerEntityRenderingHandler(EntityCage.class, m -> new RenderCage(m));
RenderingRegistry.registerEntityRenderingHandler(Shoes.class, m -> new RenderShoe(m));
RenderingRegistry.registerEntityRenderingHandler(EntityThrownRock.class, m -> new RenderThrownRock(m));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : legacyWeapons()) {
            ModelBakery.registerItemVariants(item,
                new ResourceLocation(item.getRegistryName() + "_icon"),
                new ResourceLocation(item.getRegistryName() + "_3d"));
        }
        ModelLoader.setCustomStateMapper(OreSpawn.MyIslandBlock,
            new net.minecraft.client.renderer.block.statemap.StateMap.Builder()
                .ignore(net.minecraft.block.BlockReed.AGE).build());
        for (Item item : net.minecraftforge.fml.common.registry.ForgeRegistries.ITEMS) {
            if (item.getRegistryName() != null && item.getRegistryName().getNamespace().equals(OreSpawnMod.MOD_ID)) {
                ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
        for (Item item : legacyWeapons()) {
            item.setTileEntityItemStackRenderer(LegacyWeaponRenderer.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void bakeLegacyWeapons(ModelBakeEvent event) {
        for (Item item : legacyWeapons()) {
            bakeLegacyWeapon(event, item);
        }
    }

    private static Item[] legacyWeapons() {
        return new Item[]{OreSpawn.MyBertha, OreSpawn.MySlice, OreSpawn.MyRoyal, OreSpawn.MySquidZooka,
            OreSpawn.MyHammy, OreSpawn.MyBattleAxe, OreSpawn.MyChainsaw, OreSpawn.MyQueenBattleAxe};
    }

    private static void bakeLegacyWeapon(ModelBakeEvent event, Item item) {
        ModelResourceLocation iconLocation = new ModelResourceLocation(item.getRegistryName() + "_icon", "inventory");
        ModelResourceLocation builtInLocation = new ModelResourceLocation(item.getRegistryName() + "_3d", "inventory");
        ModelResourceLocation itemLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        net.minecraft.client.renderer.block.model.IBakedModel icon = event.getModelRegistry().getObject(iconLocation);
        net.minecraft.client.renderer.block.model.IBakedModel builtIn = event.getModelRegistry().getObject(builtInLocation);
        event.getModelRegistry().putObject(itemLocation, new LegacyWeaponModel(icon, builtIn));
    }

    @Override
    public void registerSoundThings() {
        // sounds are registered statically via OreSpawnSoundEvents
    }

    @Override
    public void registerKeyboardInput() {
        com.electrifiedded.orespawn.client.KeyHandler k = new com.electrifiedded.orespawn.client.KeyHandler();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(k);
    }

    @Override
    public void registerNetworkStuff() {
        super.registerNetworkStuff();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new RiderControl(this.getNetwork()));
    }

    @Override
    public int setArmorPrefix(String string) {
        return 0;
    }
}

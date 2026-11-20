package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.RegistryHandler;
import com.electrifiedded.orespawn.world.GenericDungeon;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Placed by the "Random Dungeon" item. 400 ticks later (or on an earlier random tick) it removes itself
 * and builds one of 50 random OreSpawn structures at its position.
 */
public class DungeonSpawnerBlock extends Block {
   private static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

   public DungeonSpawnerBlock(String name) {
      super(Material.PLANTS);
      setHardness(0.0F);
      setLightLevel(0.9F);
      setSoundType(SoundType.PLANT);
      setTickRandomly(true);
      setTranslationKey(name);
      setRegistryName(name);
      setCreativeTab(RegistryHandler.tabForBlock(name));
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos pos) {
      return world.getBlockState(pos.down()).getMaterial().isSolid();
   }

   @Override
   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      if (!world.isRemote) {
         world.scheduleUpdate(pos, this, 400);
      }
   }

   @Override
   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
      for (int i = 0; i < 5; i++) {
         world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
            pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(),
            (rand.nextFloat() - rand.nextFloat()) / 4.0, rand.nextFloat() / 2.0, (rand.nextFloat() - rand.nextFloat()) / 4.0);
      }
   }

   @Override
   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (world.isRemote || world.getBlockState(pos).getBlock() != this) return;
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
      world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
      buildRandom(world, pos.getX(), pos.getY(), pos.getZ(), world.rand.nextInt(50));
   }

   private static void buildRandom(World world, int x, int y, int z, int type) {
      GenericDungeon d = OreSpawn.MyDungeon;
      switch (type) {
         case 0: OreSpawn.OreSpawnTrees.FairyTree(world, x, y, z); break;
         case 1: OreSpawn.OreSpawnTrees.FairyCastleTree(world, x, y, z); break;
         case 2: d.makeEnormousCastle(world, x, y, z); break;
         case 3: d.makeRotatorStation(world, x, y, z); break;
         case 4: d.makeBeeHive(world, x, y, z); break;
         case 5: d.makeHauntedHouse(world, x, y, z); break;
         case 6: d.makeMantisHive(world, x, y, z); break;
         case 7: d.makeKyuubiDungeon(world, x, y, z); break;
         case 8: d.makeSmallBeeHive(world, x, y, z); break;
         case 9: d.makeShadowDungeon(world, x, y, z); break;
         case 10: d.makeAlienWTFDungeon(world, x, y, z); break;
         case 11: d.makeEnderKnightDungeon(world, x, y, z); break;
         case 12: d.makePlayPool(world, x, y, z); break;
         case 13: d.makeWaterDragonLair(world, x, y, z); break;
         case 14: d.makeCloudSharkDungeon(world, x, y, z); break;
         case 15: d.makeLeafMonsterDungeon(world, x, y, z); break;
         case 16: d.makeMiniDungeon(world, x, y, z); break;
         case 17: d.makeGoldFishBowl(world, x, y, z); break;
         case 18: d.makeEnderReaperGraveyard(world, x, y, z); break;
         case 19: d.makeSpitBugLair(world, x, y, z); break;
         case 20: d.makeIgloo(world, x, y, z); break;
         case 21: d.makeDungeon(world, x, y, z); break;
         case 22: OreSpawn.RubyDungeon.makeDungeon(world, x, y, z); break;
         case 23: OreSpawn.BMaze.buildBasiliskMaze(world, x, y, z); break;
         case 24: d.makeEnderDragonHospital(world, x, y, z); break;
         case 25: d.makeCrystalHauntedHouse(world, x, y, z); break;
         case 26: d.makeBouncyCastle(world, x, y, z); break;
         case 27: d.makeEnderCastle(world, x, y, z); break;
         case 28: d.makeDamselInDistress(world, x, y, z); break;
         case 29: d.makeIncaPyramid(world, x, y, z); break;
         case 30: d.makeRobotLab(world, x, y, z); break;
         case 31: d.makeKingAltar(world, x, y, z); break;
         case 32: d.makeLeonNest(world, x, y, z); break;
         case 33: d.makeCrystalBattleTower(world, x, y, z); break;
         case 34: d.makeCephadromeAltar(world, x, y, z); break;
         case 35: d.makeGirlfriendIsland(world, x, y, z); break;
         case 36: d.makeGreenhouseDungeon(world, x, y, z); break;
         case 37: d.makeMonsterIsland(world, x, y, z); break;
         case 38: d.makeNightmareRookery(world, x, y, z); break;
         case 39: d.makeStinkyHouse(world, x, y, z); break;
         case 40: d.makeRubberDuckyPond(world, x, y, z); break;
         case 41: d.makeWhiteHouse(world, x, y, z); break;
         case 42: d.makeQueenAltar(world, x, y, z); break;
         case 43: d.makeFrogPond(world, x, y + 1, z); break;
         case 44: d.makePumpkin(world, x, y + 1, z); break;
         case 45: d.makeRoundRotator(world, x, y + 1, z); break;
         case 46: d.makeRainbow(world, x, y, z); break;
         case 47: d.makeEnormousCastleQ(world, x, y, z); break;
         case 48: d.makeSpiderHangout(world, x, y, z); break;
         default: d.makeRedAntHangout(world, x, y, z); break;
      }
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return OreSpawn.RandomDungeon;
   }

   @Override
   public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
      return new ItemStack(OreSpawn.RandomDungeon);
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
      return NULL_AABB;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }
}

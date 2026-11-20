package com.electrifiedded.orespawn.block.workbench;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.RegistryHandler;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Crystal furnace: smelts vanilla recipes faster than a normal furnace.
 * Vanilla BlockFurnace behaviour is reused for facing and flames; every part that
 * hard-codes Blocks.FURNACE or TileEntityFurnace is replaced.
 */
public class BlockCrystalFurnace extends BlockFurnace {
    private static boolean keepInventory;

    public BlockCrystalFurnace(boolean lit, String name) {
        super(lit);
        setRegistryName(name);
        setTranslationKey("crystal_furnace_block");
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
        if (lit) {
            setLightLevel(0.6F);
        } else {
            setCreativeTab(RegistryHandler.tabForBlock(name));
        }
    }

    /** Swaps between the idle and lit furnace while keeping the tile entity and its items. */
    public static void setState(boolean active, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        TileEntity tile = world.getTileEntity(pos);
        keepInventory = true;
        world.setBlockState(pos, (active ? OreSpawn.CrystalFurnaceOnBlock : OreSpawn.CrystalFurnaceBlock).getDefaultState()
            .withProperty(FACING, state.getValue(FACING)), 3);
        keepInventory = false;
        if (tile != null) {
            tile.validate();
            world.setTileEntity(pos, tile);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && world.getTileEntity(pos) instanceof TileEntityCrystalFurnace) {
            player.openGui(com.electrifiedded.orespawn.OreSpawnMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCrystalFurnace();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        TileEntity tile = world.getTileEntity(pos);
        if (stack.hasDisplayName() && tile instanceof TileEntityCrystalFurnace) {
            ((TileEntityCrystalFurnace) tile).setCustomInventoryName(stack.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityCrystalFurnace) {
                InventoryHelper.dropInventoryItems(world, pos, (TileEntityCrystalFurnace) tile);
                world.updateComparatorOutputLevel(pos, this);
            }
        }
        world.removeTileEntity(pos);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(OreSpawn.CrystalFurnaceBlock);
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(OreSpawn.CrystalFurnaceBlock);
    }
}

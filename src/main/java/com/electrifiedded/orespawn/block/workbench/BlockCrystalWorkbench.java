package com.electrifiedded.orespawn.block.workbench;

import com.electrifiedded.orespawn.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** A crafting table made of crystal wood; it crafts the regular recipes. */
public class BlockCrystalWorkbench extends Block {
    public BlockCrystalWorkbench(String name) {
        super(Material.WOOD);
        setRegistryName(name);
        setTranslationKey("crystal_workbench_block");
        setHardness(1.0F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(RegistryHandler.tabForBlock(name));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(com.electrifiedded.orespawn.OreSpawnMod.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}

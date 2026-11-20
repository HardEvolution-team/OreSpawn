package com.electrifiedded.orespawn.block;

import com.electrifiedded.orespawn.block.workbench.TileEntityCrystalFurnace;
import com.electrifiedded.orespawn.inventory.ContainerCrystalFurnace;
import com.electrifiedded.orespawn.inventory.ContainerCrystalWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/** GUI 0: crystal furnace, GUI 1: crystal workbench. */
public class OreSpawnGUIHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if (id == 0) {
            TileEntity tile = world.getTileEntity(pos);
            return tile instanceof TileEntityCrystalFurnace ? new ContainerCrystalFurnace(player.inventory, (TileEntityCrystalFurnace) tile) : null;
        }
        return id == 1 ? new ContainerCrystalWorkbench(player.inventory, world, pos) : null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return com.electrifiedded.orespawn.OreSpawnMod.proxy.getClientGuiElement(id, player, world, x, y, z);
    }
}

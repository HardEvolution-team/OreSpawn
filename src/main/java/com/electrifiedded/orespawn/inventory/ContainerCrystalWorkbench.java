package com.electrifiedded.orespawn.inventory;

import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Vanilla 3x3 crafting, bound to the crystal workbench block instead of the crafting table. */
public class ContainerCrystalWorkbench extends ContainerWorkbench {
    private final World world;
    private final BlockPos pos;

    public ContainerCrystalWorkbench(InventoryPlayer playerInv, World world, BlockPos pos) {
        super(playerInv, world, pos);
        this.world = world;
        this.pos = pos;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return world.getBlockState(pos).getBlock() == OreSpawn.CrystalWorkbenchBlock
            && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
    }
}

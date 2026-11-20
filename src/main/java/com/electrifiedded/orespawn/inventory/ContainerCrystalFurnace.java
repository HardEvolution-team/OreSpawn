package com.electrifiedded.orespawn.inventory;

import com.electrifiedded.orespawn.block.workbench.TileEntityCrystalFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCrystalFurnace extends Container {
    private final TileEntityCrystalFurnace furnace;
    private final int[] lastFields;

    public ContainerCrystalFurnace(InventoryPlayer playerInv, TileEntityCrystalFurnace furnace) {
        this.furnace = furnace;
        this.lastFields = new int[furnace.getFieldCount()];
        addSlotToContainer(new Slot(furnace, 0, 56, 17));
        addSlotToContainer(new Slot(furnace, 1, 56, 53));
        addSlotToContainer(new SlotFurnaceOutput(playerInv.player, furnace, 2, 116, 35));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, furnace);
    }

    /** Keeps the burn and cook progress bars of open GUIs in sync with the server. */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int id = 0; id < lastFields.length; id++) {
            int value = furnace.getField(id);
            if (value != lastFields[id]) {
                for (IContainerListener listener : listeners) {
                    listener.sendWindowProperty(this, id, value);
                }
                lastFields[id] = value;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        furnace.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return furnace.isUsableByPlayer(player);
    }

    /** Shift-click like a vanilla furnace: smeltables go to the input, fuel to the fuel slot. */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getStack();
        ItemStack original = stack.copy();
        if (index == 2) {
            if (!mergeItemStack(stack, 3, 39, true)) return ItemStack.EMPTY;
            slot.onSlotChange(stack, original);
        } else if (index > 2) {
            if (!FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty()) {
                if (!mergeItemStack(stack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (TileEntityCrystalFurnace.getItemBurnTime(stack) > 0) {
                if (!mergeItemStack(stack, 1, 2, false)) return ItemStack.EMPTY;
            } else if (index < 30) {
                if (!mergeItemStack(stack, 30, 39, false)) return ItemStack.EMPTY;
            } else if (!mergeItemStack(stack, 3, 30, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!mergeItemStack(stack, 3, 39, false)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        if (stack.getCount() == original.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return original;
    }
}

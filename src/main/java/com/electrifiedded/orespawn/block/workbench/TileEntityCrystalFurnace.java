package com.electrifiedded.orespawn.block.workbench;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.inventory.ISidedInventory;

public class TileEntityCrystalFurnace extends TileEntity implements ITickable, ISidedInventory {
    private ItemStackHandler items = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    /** The original crystal furnace smelts vanilla furnace recipes in 150 ticks instead of 200. */
    public static final int COOK_TIME = 150;

    private int cookTime = 0;
    private int burnTime = 0;
    private int currentBurnTime = 0;
    private String customName;

    @Override
    public void update() {
        boolean wasBurning = isBurning();
        boolean changed = false;

        if (isBurning()) {
            burnTime--;
        }

        if (!world.isRemote) {
            if (!isBurning() && canSmelt()) {
                ItemStack fuel = items.getStackInSlot(1);
                currentBurnTime = burnTime = getItemBurnTime(fuel);
                if (isBurning()) {
                    changed = true;
                    if (!fuel.isEmpty()) {
                        ItemStack container = fuel.getItem().getContainerItem(fuel);
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            items.setStackInSlot(1, container);
                        }
                    }
                }
            }

            if (isBurning() && canSmelt()) {
                if (++cookTime == COOK_TIME) {
                    cookTime = 0;
                    smeltItem();
                    changed = true;
                }
            } else {
                cookTime = 0;
            }

            if (wasBurning != isBurning()) {
                changed = true;
                BlockCrystalFurnace.setState(isBurning(), world, pos);
            }
        }

        if (changed) {
            markDirty();
        }
    }

    private boolean canSmelt() {
        ItemStack input = items.getStackInSlot(0);
        if (input.isEmpty()) {
            return false;
        }
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
        if (result.isEmpty()) {
            return false;
        }
        ItemStack output = items.getStackInSlot(2);
        if (output.isEmpty()) {
            return true;
        }
        if (!output.isItemEqual(result)) {
            return false;
        }
        int resultSize = output.getCount() + result.getCount();
        return resultSize <= getInventoryStackLimit() && resultSize <= output.getMaxStackSize();
    }

    private void smeltItem() {
        ItemStack input = items.getStackInSlot(0);
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
        ItemStack output = items.getStackInSlot(2);
        if (output.isEmpty()) {
            items.setStackInSlot(2, result.copy());
        } else if (output.isItemEqual(result)) {
            output.grow(result.getCount());
        }
        input.shrink(1);
    }

    /** Burn times of the original crystal furnace; anything else burns as in a vanilla furnace. */
    public static int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        Item item = stack.getItem();
        Block block = Block.getBlockFromItem(item);
        if (block == Blocks.WOODEN_SLAB) return 150;
        if (block == Blocks.COAL_BLOCK) return 16000;
        if (block == OreSpawn.CrystalCoal) return 20000;
        if (block == OreSpawn.MyCrystalTreeLog) return 800;
        if (block == OreSpawn.CrystalPlanksBlock) return 400;
        if (block != Blocks.AIR && block.getDefaultState().getMaterial() == Material.WOOD) return 300;
        if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName())) return 200;
        if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName())) return 200;
        if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName())) return 200;
        if (item == Items.STICK || block == Blocks.SAPLING) return 100;
        if (item == Items.COAL) return 1600;
        if (item == Items.LAVA_BUCKET) return 20000;
        if (item == Items.BLAZE_ROD) return 2400;
        return TileEntityFurnace.getItemBurnTime(stack);
    }

    public boolean isBurning() {
       return burnTime > 0;
    }

    public int getBurnTimeRemainingScaled(int scale) {
        return burnTime * scale / (currentBurnTime == 0 ? COOK_TIME : currentBurnTime);
    }

    public int getCookProgressScaled(int scale) {
        return cookTime * scale / COOK_TIME;
    }

    public void setCustomInventoryName(String name) {
        customName = name;
    }

    public String getInventoryName() {
        return getName();
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < items.getSlots(); i++) {
            if (!items.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return items.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return items.extractItem(index, items.getStackInSlot(index).getCount(), false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items.setStackInSlot(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(net.minecraft.entity.player.EntityPlayer player) {
        return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public void openInventory(net.minecraft.entity.player.EntityPlayer player) {
    }

    @Override
    public void closeInventory(net.minecraft.entity.player.EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 1) return getItemBurnTime(stack) > 0;
        if (index == 0) return !FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty();
        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (side == EnumFacing.DOWN) return new int[]{1};
        if (side == EnumFacing.UP) return new int[]{0};
        return new int[]{0, 1, 2};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 2) return true;
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0: return burnTime;
            case 1: return currentBurnTime;
            case 2: return cookTime;
            default: return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0: burnTime = value; break;
            case 1: currentBurnTime = value; break;
            case 2: cookTime = value; break;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public void clear() {
        for (int i = 0; i < items.getSlots(); i++) {
            items.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public String getName() {
        return customName == null ? "Crystal Furnace" : customName;
    }

    @Override
    public boolean hasCustomName() {
        return customName != null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Items", items.serializeNBT());
        compound.setInteger("CookTime", cookTime);
        compound.setInteger("BurnTime", burnTime);
        compound.setInteger("CurrentBurnTime", currentBurnTime);
        if (customName != null) {
            compound.setString("CustomName", customName);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        items.deserializeNBT(compound.getCompoundTag("Items"));
        cookTime = compound.getInteger("CookTime");
        burnTime = compound.getInteger("BurnTime");
        currentBurnTime = compound.getInteger("CurrentBurnTime");
        customName = compound.hasKey("CustomName") ? compound.getString("CustomName") : null;
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(items);
        }
        return super.getCapability(capability, facing);
    }
}

package com.electrifiedded.orespawn.client.gui;

import com.electrifiedded.orespawn.block.workbench.TileEntityCrystalFurnace;
import com.electrifiedded.orespawn.inventory.ContainerCrystalFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CrystalFurnaceGUI extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
    private final TileEntityCrystalFurnace furnace;

    public CrystalFurnaceGUI(InventoryPlayer playerInv, TileEntityCrystalFurnace furnace) {
        super(new ContainerCrystalFurnace(playerInv, furnace));
        this.furnace = furnace;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = furnace.hasCustomName() ? furnace.getName() : I18n.format("tile.crystal_furnace_block.name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        int left = (width - xSize) / 2;
        int top = (height - ySize) / 2;
        drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
        if (furnace.isBurning()) {
            int flame = furnace.getBurnTimeRemainingScaled(13);
            drawTexturedModalRect(left + 56, top + 36 + 12 - flame, 176, 12 - flame, 14, flame + 1);
        }
        drawTexturedModalRect(left + 79, top + 34, 176, 14, furnace.getCookProgressScaled(24) + 1, 16);
    }
}

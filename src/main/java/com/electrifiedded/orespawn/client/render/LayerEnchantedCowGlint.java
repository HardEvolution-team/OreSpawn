package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.entity.EnchantedCow;
import com.electrifiedded.orespawn.entity.RedCow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerEnchantedCowGlint implements LayerRenderer<RedCow> {
    private static final ResourceLocation GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private final RenderEnchantedCow renderer;

    public LayerEnchantedCowGlint(RenderEnchantedCow renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(RedCow cow, float limbSwing, float limbSwingAmount, float partialTicks,
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!(cow instanceof EnchantedCow) || cow.isInvisible()) return;

        Minecraft.getMinecraft().getTextureManager().bindTexture(GLINT);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        GlStateManager.color(0.5F, 0.25F, 0.8F, 1.0F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);

        for (int i = 0; i < 2; ++i) {
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.scale(0.33333334F, 0.33333334F, 0.33333334F);
            GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, (cow.ticksExisted + partialTicks) * (0.001F + i * 0.003F) * 20.0F, 0.0F);
            GlStateManager.matrixMode(5888);
            this.renderer.getMainModel().render(cow, limbSwing, limbSwingAmount, ageInTicks,
                netHeadYaw, headPitch, scale);
        }

        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

package com.electrifiedded.orespawn.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

/** 1.12 layer equivalent of the original additive shouldRenderPass overlays. */
final class LayerLegacyGlow<T extends EntityLivingBase> implements LayerRenderer<T> {
    private final RenderLivingBase<T> renderer;
    private final ModelBase model;
    private final ResourceLocation texture;
    private final Predicate<T> enabled;

    LayerLegacyGlow(RenderLivingBase<T> renderer, ModelBase model, ResourceLocation texture, Predicate<T> enabled) {
        this.renderer = renderer;
        this.model = model;
        this.texture = texture;
        this.enabled = enabled;
    }

    @Override
    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks,
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!enabled.test(entity)) return;
        renderer.bindTexture(texture);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        int light = entity.getBrightnessForRender();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light & 65535, light >> 16);
        GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
        model.setModelAttributes(renderer.getMainModel());
        model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

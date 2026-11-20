package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.client.model.ModelStinky;
import com.electrifiedded.orespawn.entity.Stinky;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderStinky extends RenderLiving<Stinky> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[19];
    private final float scale;

    static {
        for (int i = 0; i < TEXTURES.length; i++) {
            TEXTURES[i] = new ResourceLocation("orespawn", "stinkytexture" + (i + 1) + ".png");
        }
    }

    public RenderStinky(RenderManager renderManager, ModelStinky model, float shadowSize, float scale) {
        super(renderManager, model, shadowSize * scale);
        this.scale = scale;
    }

    @Override
    protected void preRenderCallback(Stinky entity, float partialTickTime) {
        GlStateManager.scale(scale, scale, scale);
    }

    @Override
    protected ResourceLocation getEntityTexture(Stinky entity) {
        int skin = entity.getSkin();
        return TEXTURES[skin >= 0 && skin < TEXTURES.length ? skin : 0];
    }
}

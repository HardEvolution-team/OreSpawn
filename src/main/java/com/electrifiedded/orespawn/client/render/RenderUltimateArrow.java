package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.entity.UltimateArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderUltimateArrow extends RenderArrow<UltimateArrow> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RenderUltimateArrow(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(UltimateArrow entity) {
        return RES_ARROW;
    }
}

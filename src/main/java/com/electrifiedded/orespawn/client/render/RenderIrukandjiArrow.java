package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.entity.IrukandjiArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderIrukandjiArrow extends RenderArrow<IrukandjiArrow> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RenderIrukandjiArrow(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(IrukandjiArrow entity) {
        return RES_ARROW;
    }
}

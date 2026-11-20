package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.entity.BerthaHit;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBerthaHit extends Render<BerthaHit> {
    public RenderBerthaHit(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(BerthaHit entity, double x, double y, double z, float entityYaw, float partialTicks) {
        // BerthaHit sword projectile is invisible in original 1.7.10
    }

    @Override
    protected ResourceLocation getEntityTexture(BerthaHit entity) {
        return null;
    }
}

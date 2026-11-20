package com.electrifiedded.orespawn.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderInvisibleProjectile<T extends Entity> extends Render<T> {
    public RenderInvisibleProjectile(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}

package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class RenderItemUrchin<T extends Entity> extends RenderSpinner<T> {
    public RenderItemUrchin(RenderManager manager) {
        super(manager);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity instanceof SunspotUrchin) this.spinItemIconIndex = ((SunspotUrchin) entity).getUrchinIndex();
        else if (entity instanceof WaterBall) this.spinItemIconIndex = ((WaterBall) entity).getWaterBallIndex();
        else if (entity instanceof InkSack) this.spinItemIconIndex = ((InkSack) entity).getInkSackIndex();
        else if (entity instanceof LaserBall) this.spinItemIconIndex = ((LaserBall) entity).getLaserBallIndex();
        else if (entity instanceof IceBall) this.spinItemIconIndex = ((IceBall) entity).getIceBallIndex();
        else if (entity instanceof Acid) this.spinItemIconIndex = ((Acid) entity).getAcidIndex();
        else if (entity instanceof DeadIrukandji) this.spinItemIconIndex = ((DeadIrukandji) entity).getIrukandjiIndex();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}

package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderCage extends RenderSpinner<EntityCage> {
   public RenderCage(RenderManager mgr) {
      super(mgr);
   }

   @Override
   public void doRender(EntityCage entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.spinItemIconIndex = 160;
      this.spinItemIconIndex = entity.getCageIndex();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }
}

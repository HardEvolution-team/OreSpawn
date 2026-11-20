package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.client.model.*;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

// HEAD-only boss entities render nothing themselves (vanilla 1.7 behavior kept)
public class RenderGodzillaHead extends RenderLiving<GodzillaHead> {
   public RenderGodzillaHead(RenderManager mgr) {
      super(mgr, null, 0.0F);
   }

   @Override
   public void doRender(GodzillaHead entity, double x, double y, double z, float yaw, float partialTicks) {
   }

   @Override
   protected ResourceLocation getEntityTexture(GodzillaHead entity) {
      return null;
   }
}

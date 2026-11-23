package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.util.ResourceLocation;

public class RenderSpiderDriver extends RenderSpider<SpiderDriver> {
   private static final ResourceLocation texture = new ResourceLocation("orespawndedsport", "spiderdriver.png");

   public RenderSpiderDriver(RenderManager mgr) {
      super(mgr);
   }

   @Override
   protected ResourceLocation getEntityTexture(SpiderDriver entity) {
      return texture;
   }
}

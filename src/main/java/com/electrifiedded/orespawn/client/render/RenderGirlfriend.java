package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGirlfriend extends RenderBiped<Girlfriend> {
   public RenderGirlfriend(RenderManager mgr, ModelBiped model, float shadow) {
      super(mgr, model, shadow);
   }

   @Override
   protected ResourceLocation getEntityTexture(Girlfriend entity) {
      return entity.getTexture();
   }
}

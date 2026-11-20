package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBoyfriend extends RenderBiped<Boyfriend> {
   public RenderBoyfriend(RenderManager mgr, ModelBiped model, float shadow) {
      super(mgr, model, shadow);
   }

   @Override
   protected ResourceLocation getEntityTexture(Boyfriend entity) {
      return entity.getTexture();
   }
}

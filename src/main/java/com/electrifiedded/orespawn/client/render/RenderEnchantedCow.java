package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEnchantedCow extends RenderLiving<RedCow> {
   private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "crystal_cow.png");
   private static final ResourceLocation texture1 = new ResourceLocation("orespawn", "red_cow.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "gold_cow.png");

   public RenderEnchantedCow(RenderManager mgr, ModelCow model, float shadow) {
      super(mgr, model, shadow);
      this.addLayer(new LayerEnchantedCowGlint(this));
   }

   @Override
   protected ResourceLocation getEntityTexture(RedCow entity) {
       if (entity instanceof EnchantedCow || entity instanceof GoldCow) {
         return texture2;
      } else {
         return entity instanceof CrystalCow ? texture3 : texture1;
      }
   }
}

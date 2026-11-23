package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.client.model.ModelIsland;
import com.electrifiedded.orespawn.entity.Island;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderIsland extends RenderLiving<Island> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("orespawndedsport", "island.png");
   private final float scale;

   public RenderIsland(RenderManager renderManager, ModelIsland model, float shadowSize, float scale) {
      super(renderManager, model, shadowSize * scale);
      this.scale = scale;
   }

   @Override
   protected void preRenderCallback(Island entity, float partialTicks) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected ResourceLocation getEntityTexture(Island entity) {
      return TEXTURE;
   }
}

package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.client.model.ModelIsland;
import com.electrifiedded.orespawn.entity.IslandToo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderIslandToo extends RenderLiving<IslandToo> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("orespawn", "islandtoo.png");
   private final float scale;

   public RenderIslandToo(RenderManager renderManager, ModelIsland model, float shadowSize, float scale) {
      super(renderManager, model, shadowSize * scale);
      this.scale = scale;
   }

   @Override
   protected void preRenderCallback(IslandToo entity, float partialTicks) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected ResourceLocation getEntityTexture(IslandToo entity) {
      return TEXTURE;
   }
}

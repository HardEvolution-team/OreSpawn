package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSpinner<T extends Entity> extends Render<T> {
   public int spinItemIconIndex = 160;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "spinners.png");

   public RenderSpinner(RenderManager mgr) {
      super(mgr);
   }

   @Override
   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.bindTexture(texture);
      GlStateManager.pushMatrix();
       GlStateManager.translate((float)x, (float)y, (float)z);
       GlStateManager.enableRescaleNormal();
       GlStateManager.scale(0.5F, 0.5F, 0.5F);
       GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
       GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
       GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
       this.renderQuad(this.spinItemIconIndex, entity.rotationPitch);
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
   }

   private void renderQuad(int iconIndex, float pitch) {
      float u0 = (iconIndex % 16 * 16 + 0) / 256.0F;
      float u1 = (iconIndex % 16 * 16 + 16) / 256.0F;
      float v0 = (iconIndex / 16 * 16 + 0) / 256.0F;
      float v1 = (iconIndex / 16 * 16 + 16) / 256.0F;
      float w = 1.0F;
      float cx = 0.5F;
      float hb = 0.25F;
      
      
      
      Tessellator tess = Tessellator.getInstance();
      BufferBuilder buf = tess.getBuffer();
      buf.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
      buf.pos(0.0 - cx, 0.0 - hb, 0.0).tex(u0, v1).normal(0.0F, 1.0F, 0.0F).endVertex();
      buf.pos(w - cx, 0.0 - hb, 0.0).tex(u1, v1).normal(0.0F, 1.0F, 0.0F).endVertex();
      buf.pos(w - cx, w - hb, 0.0).tex(u1, v0).normal(0.0F, 1.0F, 0.0F).endVertex();
      buf.pos(0.0 - cx, w - hb, 0.0).tex(u0, v0).normal(0.0F, 1.0F, 0.0F).endVertex();
      tess.draw();
   }

   @Override
   protected ResourceLocation getEntityTexture(T entity) {
      return texture;
   }
}

package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderThrownRock extends Render<EntityThrownRock> {
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
       new ResourceLocation("orespawn", "textures/items/rocksmall.png"),
       new ResourceLocation("orespawn", "textures/items/rock.png"),
       new ResourceLocation("orespawn", "textures/items/rockred.png"),
       new ResourceLocation("orespawn", "textures/items/rockgreen.png"),
       new ResourceLocation("orespawn", "textures/items/rockblue.png"),
       new ResourceLocation("orespawn", "textures/items/rockpurple.png"),
       new ResourceLocation("orespawn", "textures/items/rockspikey.png"),
       new ResourceLocation("orespawn", "textures/items/rocktnt.png"),
       new ResourceLocation("orespawn", "textures/items/rockcrystalred.png"),
       new ResourceLocation("orespawn", "textures/items/rockcrystalgreen.png"),
       new ResourceLocation("orespawn", "textures/items/rockcrystalblue.png"),
       new ResourceLocation("orespawn", "textures/items/rockcrystaltnt.png"),
   };

   public RenderThrownRock(RenderManager mgr) {
      super(mgr);
   }

   @Override
   public void doRender(EntityThrownRock entity, double x, double y, double z, float entityYaw, float partialTicks) {
      this.bindTexture(this.getEntityTexture(entity));
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
       GlStateManager.enableRescaleNormal();
       GlStateManager.scale(0.5F, 0.5F, 0.5F);
       GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
       GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
       GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
       this.renderQuad(entity.rotationPitch);
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
   }

   private void renderQuad(float pitch) {
      float u0 = 0.0F, u1 = 1.0F, v0 = 0.0F, v1 = 1.0F;
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
   protected ResourceLocation getEntityTexture(EntityThrownRock entity) {
      int t = entity.getRockType();
      if (t < 1 || t > 12) t = 1;
      return TEXTURES[t - 1];
   }
}

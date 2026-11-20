package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.client.model.*;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.culling.ICamera;
import org.lwjgl.opengl.GL11;

public class RenderTheKing extends RenderLiving<TheKing> {
   protected ModelTheKing model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "thekingtexture.png");

   public RenderTheKing(RenderManager par1RenderManager, ModelTheKing par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelTheKing)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(TheKing par1EntityTheKing, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityTheKing, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(TheKing par1Entity, float par2) {
      if (par1Entity != null && par1Entity.getPlayNicely() != 0) {
         GL11.glScalef(this.scale / 4.0F, this.scale / 4.0F, this.scale / 4.0F);
      } else {
         GL11.glScalef(this.scale, this.scale, this.scale);
      }
   }

   @Override
   protected void preRenderCallback(TheKing par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(TheKing entity) {
      return texture;
   }

   @Override
   public boolean shouldRender(TheKing entity, ICamera camera, double camX, double camY, double camZ) {
      return true;
   }
}

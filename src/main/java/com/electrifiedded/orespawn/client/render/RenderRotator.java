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
import org.lwjgl.opengl.GL11;

public class RenderRotator extends RenderLiving<Rotator> {
   protected ModelRotator model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "rotatortexture.png");

   public RenderRotator(RenderManager par1RenderManager, ModelRotator par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelRotator)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Rotator par1EntityRotator, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityRotator, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Rotator par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Rotator par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Rotator entity) {
      return texture;
   }
}

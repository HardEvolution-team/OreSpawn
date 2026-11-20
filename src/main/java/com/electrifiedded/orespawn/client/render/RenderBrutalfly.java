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

public class RenderBrutalfly extends RenderLiving<Brutalfly> {
   protected ModelBrutalfly model;
   private float scale = 1.0F;
   private static final ResourceLocation overlay = new ResourceLocation("orespawn", "brutalfly_overlay2.png");
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "brutalflytexture.png");

   public RenderBrutalfly(RenderManager par1RenderManager, ModelBrutalfly par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelBrutalfly)this.mainModel;
      this.scale = par3;
      this.addLayer(new LayerLegacyGlow<>(this, this.model, overlay, entity -> true));
   }

   @Override
   public void doRender(Brutalfly par1Brutalfly, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1Brutalfly, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Brutalfly par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Brutalfly par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Brutalfly entity) {
      return texture;
   }
}

package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.client.model.*;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPurplePower extends RenderLiving<PurplePower> {
   protected ModelPurplePower model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawndedsport", "purplepowertexture.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawndedsport", "purplepowertexture2.png");
   private static final ResourceLocation texture3 = new ResourceLocation("orespawndedsport", "purplepowertexture3.png");
   private static final ResourceLocation texture4 = new ResourceLocation("orespawndedsport", "purplepowertexture4.png");
   private static final ResourceLocation texture10 = new ResourceLocation("orespawndedsport", "purplepowertexture10.png");

   public RenderPurplePower(RenderManager par1RenderManager, ModelPurplePower par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelPurplePower)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(PurplePower par1EntityPurplePower, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityPurplePower, par2, par4, par6, par8, par9);
   }


   protected void preRenderScale(PurplePower par1Entity, float par2) {
      float localscale = this.scale;
      if (par1Entity.getPurpleType() != 0) {
         localscale = 0.55F;
      }

      GL11.glScalef(localscale, localscale, localscale);
   }

   @Override
   protected ResourceLocation getEntityTexture(PurplePower entity) {
      PurplePower p = (PurplePower)entity;
      int i = p.getPurpleType();
      if (i == 1) {
         return texture2;
      } else if (i == 2) {
         return texture3;
      } else if (i == 3) {
         return texture4;
      } else {
         return i == 10 ? texture10 : texture;
      }
   }
}

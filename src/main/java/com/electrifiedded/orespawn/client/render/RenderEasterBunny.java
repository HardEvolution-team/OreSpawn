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

public class RenderEasterBunny extends RenderLiving<EasterBunny> {
   protected ModelEasterBunny model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawndedsport", "easterbunnytexture.png");

   public RenderEasterBunny(RenderManager par1RenderManager, ModelEasterBunny par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelEasterBunny)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(EasterBunny par1EntityEasterBunny, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityEasterBunny, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(EasterBunny par1Entity, float par2) {
      if (par1Entity != null && par1Entity.isChild()) {
         GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
      } else {
         GL11.glScalef(this.scale, this.scale, this.scale);
      }
   }

   @Override
   protected void preRenderCallback(EasterBunny par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(EasterBunny entity) {
      return texture;
   }
}

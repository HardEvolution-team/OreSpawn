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

public class RenderCricket extends RenderLiving<Cricket> {
   protected ModelCricket model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "crickettexture.png");

   public RenderCricket(RenderManager par1RenderManager, ModelCricket par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelCricket)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Cricket par1EntityCricket, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityCricket, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Cricket par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Cricket par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Cricket entity) {
      return texture;
   }
}

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

public class RenderFrog extends RenderLiving<Frog> {
   protected ModelFrog model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "frogtexture.png");

   public RenderFrog(RenderManager par1RenderManager, ModelFrog par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelFrog)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Frog par1EntityFrog, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityFrog, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Frog par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Frog par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Frog entity) {
      return texture;
   }
}

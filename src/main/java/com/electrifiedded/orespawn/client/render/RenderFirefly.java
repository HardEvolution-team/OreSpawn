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

public class RenderFirefly extends RenderLiving<Firefly> {
   protected ModelFirefly model;
   private float scale = 1.0F;

   public RenderFirefly(RenderManager par1RenderManager, ModelFirefly par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelFirefly)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Firefly par1EntityFirefly, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityFirefly, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Firefly par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Firefly par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Firefly entity) {
      Firefly a = (Firefly)entity;
      return a.getTexture(a);
   }
}

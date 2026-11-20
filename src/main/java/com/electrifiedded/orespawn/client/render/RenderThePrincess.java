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

public class RenderThePrincess extends RenderLiving<ThePrincess> {
   protected ModelThePrincess model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "theprincesstexture.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "theprincesstexture2.png");

   public RenderThePrincess(RenderManager par1RenderManager, ModelThePrincess par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelThePrincess)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(ThePrincess par1EntityThePrincess, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityThePrincess, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(ThePrincess par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(ThePrincess par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(ThePrincess entity) {
      ThePrincess t = (ThePrincess)entity;
      return t.getAttacking() != 0 ? texture2 : texture;
   }
}

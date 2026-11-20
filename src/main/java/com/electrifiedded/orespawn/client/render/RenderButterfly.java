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

public class RenderButterfly extends RenderLiving<EntityButterfly> {
   protected ModelButterfly model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

   public RenderButterfly(RenderManager par1RenderManager, ModelButterfly par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelButterfly)this.mainModel;
      this.scale = par3;
      this.addLayer(new LayerLegacyGlow<>(this, this.model, texture,
          entity -> entity instanceof Mothra || entity instanceof EntityLunaMoth && ((EntityLunaMoth)entity).moth_type == 0));
   }

   @Override
   public void doRender(EntityButterfly par1EntityButterfly, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityButterfly, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(EntityButterfly par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(EntityButterfly par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(EntityButterfly entity) {
      EntityButterfly a = (EntityButterfly)entity;
      return a.getTexture(a);
   }
}

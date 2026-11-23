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

public class RenderDragon extends RenderLiving<Dragon> {
   protected ModelDragon model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawndedsport", "dragon.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawndedsport", "whitedragon.png");

   public RenderDragon(RenderManager par1RenderManager, ModelDragon par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelDragon)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Dragon par1EntityDragon, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityDragon, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Dragon par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Dragon par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Dragon entity) {
      Dragon d = (Dragon)entity;
      return d.getDragonType() != 0 ? texture2 : texture;
   }
}

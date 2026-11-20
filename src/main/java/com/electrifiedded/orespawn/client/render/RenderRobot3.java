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

public class RenderRobot3 extends RenderLiving<Robot3> {
   protected ModelRobot3 model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "robot3.png");

   public RenderRobot3(RenderManager par1RenderManager, ModelRobot3 par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelRobot3)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Robot3 par1EntityRobot3, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityRobot3, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Robot3 par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Robot3 par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Robot3 entity) {
      return texture;
   }
}

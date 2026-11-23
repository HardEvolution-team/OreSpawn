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

public class RenderSpiderRobot extends RenderLiving<SpiderRobot> {
   protected ModelSpiderRobot model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawndedsport", "spiderrobottexture.png");

   public RenderSpiderRobot(RenderManager par1RenderManager, ModelSpiderRobot par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelSpiderRobot)this.mainModel;
      this.scale = par3;
   }

   public void renderSpiderRobot(SpiderRobot par1EntitySpiderRobot, double par2, double par4, double par6, float par8, float par9) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)par2, (float)par4, (float)par6);
      GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
      this.bindTexture(texture);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      this.model.render(par1EntitySpiderRobot, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }
   protected void preRenderScale(SpiderRobot par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(SpiderRobot par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(SpiderRobot entity) {
      return texture;
   }
}

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

public class RenderRobot1 extends RenderLiving<Robot1> {
   protected ModelRobot1 model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawndedsport", "robot1.png");

   public RenderRobot1(RenderManager par1RenderManager, ModelRobot1 par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelRobot1)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Robot1 par1EntityRobot1, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityRobot1, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Robot1 par1Entity, float par2) {
      GL11.glScalef(this.scale, this.scale, this.scale);
   }

   @Override
   protected void preRenderCallback(Robot1 par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Robot1 entity) {
      return texture;
   }
}

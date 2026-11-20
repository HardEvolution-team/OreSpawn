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

public class RenderWaterDragon extends RenderLiving<WaterDragon> {
   protected ModelWaterDragon model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "waterdragon.png");

   public RenderWaterDragon(RenderManager par1RenderManager, ModelWaterDragon par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelWaterDragon)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(WaterDragon par1EntityWaterDragon, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityWaterDragon, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(WaterDragon par1Entity, float par2) {
      if (par1Entity != null && par1Entity.isChild()) {
         GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
      } else {
         GL11.glScalef(this.scale, this.scale, this.scale);
      }
   }

   @Override
   protected void preRenderCallback(WaterDragon par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(WaterDragon entity) {
      return texture;
   }
}

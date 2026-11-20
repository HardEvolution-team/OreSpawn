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

public class RenderChipmunk extends RenderLiving<Chipmunk> {
   protected ModelChipmunk model;
   private float scale = 1.0F;
   private static final ResourceLocation texture = new ResourceLocation("orespawn", "chipmunktexture.png");
   private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "chipmunktexture2.png");
   private static final ResourceLocation texture3 = new ResourceLocation("orespawn", "chipmunktexture3.png");

   public RenderChipmunk(RenderManager par1RenderManager, ModelChipmunk par1ModelBase, float par2, float par3) {
      super(par1RenderManager, par1ModelBase, par2 * par3);
      this.model = (ModelChipmunk)this.mainModel;
      this.scale = par3;
   }

   @Override
   public void doRender(Chipmunk par1EntityChipmunk, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(par1EntityChipmunk, par2, par4, par6, par8, par9);
   }



   protected void preRenderScale(Chipmunk par1Entity, float par2) {
      if (par1Entity != null && par1Entity.isChild()) {
         GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
      } else {
         GL11.glScalef(this.scale, this.scale, this.scale);
      }
   }

   @Override
   protected void preRenderCallback(Chipmunk par1EntityLiving, float par2) {
      this.preRenderScale(par1EntityLiving, par2);
   }

   @Override
   protected ResourceLocation getEntityTexture(Chipmunk entity) {
      if (entity instanceof EntityCannonFodder) {
         EntityCannonFodder c = (EntityCannonFodder)entity;
         if (c.get_is_activated() != 0) {
            if (c.getHatColor() == 2) {
               return texture2;
            }

            if (c.getHatColor() == 3) {
               return texture3;
            }
         }
      }

      return texture;
   }
}

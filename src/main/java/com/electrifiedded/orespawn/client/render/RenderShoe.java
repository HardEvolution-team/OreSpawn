package com.electrifiedded.orespawn.client.render;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.Shoes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderShoe extends Render<Shoes> {
   public RenderShoe(RenderManager mgr) {
      super(mgr);
   }

   @Override
   public void doRender(Shoes entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
      Minecraft.getMinecraft().getRenderItem().renderItem(shoeStack(entity.getShoeId()), ItemCameraTransforms.TransformType.GROUND);
      GlStateManager.popMatrix();
   }

   private ItemStack shoeStack(int shoeId) {
      switch (shoeId) {
         case 1:
            return new ItemStack(OreSpawn.MyItemShoes);
         case 2:
            return new ItemStack(OreSpawn.MyItemShoes_1);
         case 3:
            return new ItemStack(OreSpawn.MyItemShoes_2);
         case 4:
            return new ItemStack(OreSpawn.MyItemShoes_3);
         case 6:
            return new ItemStack(OreSpawn.MyItemGameController);
         default:
            return new ItemStack(OreSpawn.MyItemShoes);
      }
   }

   @Override
   protected ResourceLocation getEntityTexture(Shoes entity) {
      return null;
   }
}

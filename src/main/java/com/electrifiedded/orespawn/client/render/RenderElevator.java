package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.client.model.ModelElevator;
import com.electrifiedded.orespawn.entity.Elevator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderElevator extends Render<Elevator> {
   private final ModelElevator modelElevator = new ModelElevator();

   public RenderElevator(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.25F;
   }

   @Override
   public void doRender(Elevator elevator, double x, double y, double z, float yaw, float partialTicks) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, (float)z);
      GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
      float timeSinceHit = elevator.getTimeSinceHit() - partialTicks;
      float damageTaken = Math.max(elevator.getDamageTaken() - partialTicks, 0.0F);
      if (timeSinceHit > 0.0F) {
         GL11.glRotatef(MathHelper.sin(timeSinceHit) * timeSinceHit * damageTaken / 10.0F
            * elevator.getForwardDirection(), 1.0F, 0.0F, 0.0F);
      }
      bindEntityTexture(elevator);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      this.modelElevator.render(elevator, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   @Override
   protected ResourceLocation getEntityTexture(Elevator entity) {
      return entity.getTexture();
   }
}

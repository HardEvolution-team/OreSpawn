package com.electrifiedded.orespawn.entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.DataParameter;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Shoes extends EntityThrowable {
    private static final DataParameter<Integer> DW_20 = EntityDataManager.<Integer>createKey(Shoes.class, DataSerializers.VARINT);

   public int ShoeId = 0;
   private float my_rotation = 0.0F;

   public Shoes(World par1World) {
      super(par1World);
      this.ShoeId = this.rand.nextInt(4) + 2;
      this.dataManager.register(DW_20, this.ShoeId);
   }

   public Shoes(World par1World, int par2) {
      super(par1World);
      this.ShoeId = par2;
      this.dataManager.register(DW_20, this.ShoeId);
   }

   public Shoes(World par1World, EntityLivingBase par2EntityLiving) {
      super(par1World, par2EntityLiving);
      this.ShoeId = this.rand.nextInt(4) + 2;
      this.dataManager.register(DW_20, this.ShoeId);
   }

   public Shoes(World par1World, EntityLivingBase par2EntityLiving, int par3) {
      super(par1World, par2EntityLiving);
      this.ShoeId = par3;
      this.dataManager.register(DW_20, this.ShoeId);
   }

   public Shoes(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
      this.ShoeId = this.rand.nextInt(4) + 2;
      this.dataManager.register(DW_20, this.ShoeId);
   }

   public int getShoeId() {
      return this.dataManager.get(DW_20);
   }

   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
      if (par1MovingObjectPosition.entityHit != null) {
         float var2 = 2.0F;
         if (this.getShoeId() == 6) {
            var2 = 6.0F;
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityCreeper) {
            var2 += 4.0F;
         }

         if (par1MovingObjectPosition.entityHit instanceof Girlfriend) {
            var2 = 1.0F;
         }

         if (par1MovingObjectPosition.entityHit instanceof Boyfriend) {
            var2 = 1.0F;
         }

         if (par1MovingObjectPosition.entityHit instanceof EntityPlayer) {
            var2 = 0.0F;
         }

         if (OreSpawn.valentines_day != 0) {
            var2 = 10.0F;
         }

         par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
      }

      for (int var3 = 0; var3 < 4; var3++) {
         this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
         this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
      }

      if (!this.world.isRemote) {
         this.setDead();
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.my_rotation += 20.0F;

      while (this.my_rotation > 360.0F) {
         this.my_rotation -= 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch = this.my_rotation;
   }
}

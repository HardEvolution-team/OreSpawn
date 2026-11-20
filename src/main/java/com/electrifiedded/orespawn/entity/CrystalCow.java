package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class CrystalCow extends RedCow {
   public CrystalCow(World world) {
      super(world);
   }

   @Override
   protected void dropFewItems(boolean par1, int par2) {
      int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);

      for (int var4 = 0; var4 < var3; var4++) {
         this.dropItem(OreSpawn.MyCrystalApple, 1);
      }

      this.dropItem(Items.APPLE, 1);
      super.dropFewItems(par1, par2);
   }

   @Override
   public EntityCow createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public CrystalCow spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new CrystalCow(this.world);
   }
}

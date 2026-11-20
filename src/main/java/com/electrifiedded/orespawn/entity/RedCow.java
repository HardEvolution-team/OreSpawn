package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class RedCow extends EntityCow {
   public RedCow(World world) {
      super(world);
   }

    protected void dropFewItems(boolean par1, int par2) {
       int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);

       for (int var4 = 0; var4 < var3; var4++) {
          this.dropItem(Items.APPLE, 1);
       }

       super.dropFewItems(par1, par2);
    }

    @Override
    protected void dropLoot(boolean recentlyHit, int looting, DamageSource source) {
       super.dropLoot(recentlyHit, looting, source);
       // EntityCow uses a loot table in 1.12, which otherwise bypasses the legacy apple drops.
       this.dropFewItems(recentlyHit, looting);
    }

   public EntityCow createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public RedCow spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new RedCow(this.world);
   }

   protected void handleJumpWater() {
      if (this.world.rand.nextInt(200) == 1) {
         this.setRevengeTarget(null);
      }

      super.handleJumpWater();
   }

   protected boolean canDespawn() {
      return false;
   }
}

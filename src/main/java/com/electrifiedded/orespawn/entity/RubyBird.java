package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import com.electrifiedded.orespawn.OreSpawnSoundEvents;
import net.minecraft.util.SoundEvent;

import net.minecraft.world.World;

public class RubyBird extends Cockateil {
   public RubyBird(World par1World) {
      super(par1World);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.birdtype = 5;
      this.setBirdType(this.birdtype);
      this.setFlyUp();
   }

   @Override
   protected net.minecraft.util.SoundEvent getAmbientSound() {
      return this.world.isDaytime() && !this.world.isRaining() ? com.electrifiedded.orespawn.OreSpawnSoundEvents.get("rubybird") : null;
   }

   @Override
   public boolean getCanSpawnHere() {
      return true;
   }
}

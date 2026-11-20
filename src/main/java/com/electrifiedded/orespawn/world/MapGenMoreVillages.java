package com.electrifiedded.orespawn.world;

import java.util.Random;
import net.minecraft.world.gen.structure.MapGenVillage;

public class MapGenMoreVillages extends MapGenVillage {
   private int distance = 9;
   private int minTownSeparation = 7;

   protected boolean canSpawnStructureAtCoords(int par1, int par2) {
      int var3 = par1;
      int var4 = par2;
      if (par1 < 0) {
         par1 -= this.distance - 1;
      }

      if (par2 < 0) {
         par2 -= this.distance - 1;
      }

      int var5 = par1 / this.distance;
      int var6 = par2 / this.distance;
      Random var7 = this.world.setRandomSeed(var5, var6, 10387312);
      var5 *= this.distance;
      var6 *= this.distance;
      var5 += var7.nextInt(this.distance - this.minTownSeparation);
      var6 += var7.nextInt(this.distance - this.minTownSeparation);
      // The original computed the vanilla biome check and ignored it: the village
      // dimension's own biome is not a vanilla village biome.
      return var3 == var5 && var4 == var6;
   }
}

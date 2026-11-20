package com.electrifiedded.orespawn.entity;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantedCow extends RedCow {
   public EnchantedCow(World world) {
      super(world);
   }

   private void dropEnchantedGoldenApple() {
      EntityItem var3 = new EntityItem(
         this.world, this.posX, this.posY + 1.0, this.posZ, new ItemStack(Items.GOLDEN_APPLE, 1, 1)
      );
      this.world.spawnEntity(var3);
   }

   @Override
   protected void dropFewItems(boolean par1, int par2) {
      int var3 = this.rand.nextInt(4) + this.rand.nextInt(1 + par2);

      for (int var4 = 0; var4 < var3; var4++) {
         this.dropItem(Items.APPLE, 1);
      }

      this.dropItem(Items.GOLDEN_APPLE, 2);
      this.dropEnchantedGoldenApple();
      super.dropFewItems(par1, par2);
   }

   @Override
   public EntityCow createChild(EntityAgeable entityageable) {
      return this.spawnBabyAnimal(entityageable);
   }

   public EnchantedCow spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new EnchantedCow(this.world);
   }
}

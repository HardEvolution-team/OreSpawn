package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class UltimateSword extends SpecialSword {
   private int swingtimer = 0;
   private boolean leaf = false;

   public UltimateSword(int par1, ToolMaterial par2EnumToolMaterial) {
      this(par2EnumToolMaterial, null);
   }

   public UltimateSword(ToolMaterial material, String name) {
      super(material, name == null ? "ultimate_sword" : name);

      this.setMaxDamage(material.getMaxUses());
      this.setCreativeTab(CreativeTabs.COMBAT);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if (this != OreSpawn.MyChainsaw) {
         if (this != OreSpawn.MyBattleAxe) {
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, OreSpawn.UltimateSwordMagic);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SMITE, OreSpawn.UltimateSwordMagic);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.BANE_OF_ARTHROPODS, OreSpawn.UltimateSwordMagic);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.KNOCKBACK, 1 + OreSpawn.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.LOOTING, 1 + OreSpawn.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 1 + OreSpawn.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.FIRE_ASPECT, 1 + OreSpawn.UltimateSwordMagic / 3);
         } else {
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.LOOTING, 1 + OreSpawn.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 1 + OreSpawn.UltimateSwordMagic / 2);
         }
      }
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      if (this == OreSpawn.MyChainsaw && entityLiving != null && this.swingtimer == 0) {
         entityLiving.playSound(com.electrifiedded.orespawn.OreSpawnSoundEvents.get("chainsawshort"), 1.0F, entityLiving.world.rand.nextFloat() * 0.2F + 0.9F);
         this.swingtimer = 50;
      }

      return false;
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      if (this != OreSpawn.MyChainsaw) {
         int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.LOOTING, stack);
         if (lvl <= 0) {
            if (this != OreSpawn.MyBattleAxe) {
               stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, OreSpawn.UltimateSwordMagic);
               stack.addEnchantment(net.minecraft.init.Enchantments.SMITE, OreSpawn.UltimateSwordMagic);
               stack.addEnchantment(net.minecraft.init.Enchantments.BANE_OF_ARTHROPODS, OreSpawn.UltimateSwordMagic);
               stack.addEnchantment(net.minecraft.init.Enchantments.KNOCKBACK, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.LOOTING, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.FIRE_ASPECT, 1 + OreSpawn.UltimateSwordMagic / 3);
            } else {
               stack.addEnchantment(net.minecraft.init.Enchantments.LOOTING, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 1 + OreSpawn.UltimateSwordMagic / 2);
            }
         }
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      // 1.7 applied the enchantments while blocking; 1.12 swords cannot block.
      if (par5 && !par2World.isRemote) this.onUsingTick(stack, (EntityPlayer) null, 0);
      if (this == OreSpawn.MyChainsaw) {
         if (this.swingtimer > 0) {
            this.swingtimer--;
         }

         if (par2World.isRemote && this.swingtimer > 0) {
            float f = 1.0F;
            float dx = (float)(f * Math.cos(Math.toRadians(par3Entity.rotationYaw + 90.0F + 45.0F)));
            float dz = (float)(f * Math.sin(Math.toRadians(par3Entity.rotationYaw + 90.0F + 45.0F)));
            if (par2World.rand.nextInt(8) == 0) {
               par2World.spawnParticle(
                  net.minecraft.util.EnumParticleTypes.FLAME,
                  par3Entity.posX + dx,
                  par3Entity.posY,
                  par3Entity.posZ + dz,
                  (par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0F,
                  par2World.rand.nextFloat() / 10.0F,
                  (par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0F
               );
            }

            if (par2World.rand.nextInt(2) == 0) {
               par2World.spawnParticle(
                  net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                  par3Entity.posX + dx,
                  par3Entity.posY,
                  par3Entity.posZ + dz,
                  (par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0F,
                  par2World.rand.nextFloat() / 10.0F,
                  (par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0F
               );
            }

            if (par2World.rand.nextInt(10) == 0) {
               par2World.spawnParticle(
                  net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK,
                  par3Entity.posX + dx,
                  par3Entity.posY,
                  par3Entity.posZ + dz,
                  (par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0F,
                  par2World.rand.nextFloat() / 5.0F,
                  (par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0F
               );
            }
         }
      } else {
         int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.LOOTING, stack);
         if (lvl <= 0) {
            if (this != OreSpawn.MyBattleAxe) {
               stack.addEnchantment(net.minecraft.init.Enchantments.SHARPNESS, OreSpawn.UltimateSwordMagic);
               stack.addEnchantment(net.minecraft.init.Enchantments.SMITE, OreSpawn.UltimateSwordMagic);
               stack.addEnchantment(net.minecraft.init.Enchantments.BANE_OF_ARTHROPODS, OreSpawn.UltimateSwordMagic);
               stack.addEnchantment(net.minecraft.init.Enchantments.KNOCKBACK, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.LOOTING, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.FIRE_ASPECT, 1 + OreSpawn.UltimateSwordMagic / 3);
            } else {
               stack.addEnchantment(net.minecraft.init.Enchantments.LOOTING, 1 + OreSpawn.UltimateSwordMagic / 2);
               stack.addEnchantment(net.minecraft.init.Enchantments.UNBREAKING, 1 + OreSpawn.UltimateSwordMagic / 2);
            }
         }
      }
   }

   public String getMaterialName() {
      return "Uranium/Titanium";
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
      par1ItemStack.damageItem(1, par3EntityLiving);
      return true;
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if (entity != null && OreSpawn.ultimate_sword_pvp == 0) {
         if (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend) {
            return true;
         }

         if (entity instanceof EntityTameable) {
            EntityTameable t = (EntityTameable)entity;
            if (t.isTamed()) {
               return true;
            }
         }
      }

      if (this == OreSpawn.MyChainsaw && player != null) {
         this.findSomethingToHit(player);
      }

      return false;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 9000;
   }



   private void findSomethingToHit(EntityPlayer player) {
      List var5 = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(5.0, 5.0, 5.0));
      Iterator var2 = var5.iterator();
      Entity var3 = null;
      EntityLivingBase var4 = null;

      while (var2.hasNext()) {
         var3 = (Entity)var2.next();
         var4 = (EntityLivingBase)var3;
         if (this.isSuitableTarget(var4, false, player)) {
            var4.attackEntityFrom(DamageSource.causePlayerDamage(player), OreSpawn.chainsaw_stats.damage);
         }
      }
   }

   private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2, EntityPlayer player) {
      if (par1EntityLiving == null) {
         return false;
      } else if (par1EntityLiving == player) {
         return false;
      } else if (!par1EntityLiving.isEntityAlive()) {
         return false;
      } else {
         if (OreSpawn.ultimate_sword_pvp == 0) {
            if (par1EntityLiving instanceof EntityPlayer || par1EntityLiving instanceof Girlfriend || par1EntityLiving instanceof Boyfriend) {
               return false;
            }

            if (par1EntityLiving instanceof EntityTameable) {
               EntityTameable t = (EntityTameable)par1EntityLiving;
               if (t.isTamed()) {
                  return false;
               }
            }
         }

         return this.MyCanSee(par1EntityLiving, player);
      }
   }

   public boolean MyCanSee(EntityLivingBase e, EntityPlayer player) {
      int nblks = 10;
      double cx = player.posX;
      double cz = player.posZ;
      float startx = (float)cx;
      float starty = (float)(player.posY + 1.4F);
      float startz = (float)cz;
      float dx = (float)((e.posX - startx) / 10.0);
      float dy = (float)((e.posY + e.height / 2.0F - starty) / 10.0);
      float dz = (float)((e.posZ - startz) / 10.0);
      if (Math.abs(dx) > 1.0) {
         dy /= Math.abs(dx);
         dz /= Math.abs(dx);
         nblks = (int)(nblks * Math.abs(dx));
         if (dx > 1.0F) {
            dx = 1.0F;
         }

         if (dx < -1.0F) {
            dx = -1.0F;
         }
      }

      if (Math.abs(dy) > 1.0) {
         dx /= Math.abs(dy);
         dz /= Math.abs(dy);
         nblks = (int)(nblks * Math.abs(dy));
         if (dy > 1.0F) {
            dy = 1.0F;
         }

         if (dy < -1.0F) {
            dy = -1.0F;
         }
      }

      if (Math.abs(dz) > 1.0) {
         dy /= Math.abs(dz);
         dx /= Math.abs(dz);
         nblks = (int)(nblks * Math.abs(dz));
         if (dz > 1.0F) {
            dz = 1.0F;
         }

         if (dz < -1.0F) {
            dz = -1.0F;
         }
      }

      for (int i = 0; i < nblks; i++) {
         startx += dx;
         starty += dy;
         startz += dz;
         Block bid = player.world.getBlockState(new BlockPos((int)startx, (int)starty, (int)startz)).getBlock();
         if (bid != Blocks.AIR) {
            return false;
         }
      }

      return true;
   }

   public boolean canHarvestBlock(Block par1Block) {
      return this.canCrush(par1Block);
   }

   private boolean canCrush(Block blockID) {
      if (this == OreSpawn.MyChainsaw) {
         if (blockID == Blocks.WEB) {
            return true;
         } else if (blockID == Blocks.LOG) {
            return true;
         } else if (blockID == Blocks.LEAVES) {
            return true;
         } else if (blockID == Blocks.PLANKS) {
            return true;
         } else if (blockID == Blocks.SAPLING) {
            return true;
         } else if (blockID == Blocks.TALLGRASS) {
            return true;
         } else if (blockID == Blocks.CACTUS) {
            return true;
         } else if (blockID == OreSpawn.CrystalPlanksBlock) {
            return true;
         } else if (blockID == OreSpawn.MyAppleLeaves) {
            return true;
         } else if (blockID == OreSpawn.MySkyTreeLog) {
            return true;
         } else if (blockID == OreSpawn.MyDT) {
            return true;
         } else if (blockID == OreSpawn.MyExperienceLeaves) {
            return true;
         } else if (blockID == OreSpawn.MyScaryLeaves) {
            return true;
         } else if (blockID == OreSpawn.MyCherryLeaves) {
            return true;
         } else if (blockID == OreSpawn.MyPeachLeaves) {
            return true;
         } else if (blockID == OreSpawn.MyCrystalLeaves) {
            return true;
         } else if (blockID == OreSpawn.MyCrystalLeaves2) {
            return true;
         } else {
            return blockID == OreSpawn.MyCrystalLeaves3 ? true : blockID == OreSpawn.MyCrystalTreeLog;
         }
      } else {
         return blockID == Blocks.WEB;
      }
   }

   private boolean isLeaves(Block blockID) {
      if (blockID == Blocks.WEB) {
         return true;
      } else if (blockID == Blocks.LEAVES) {
         return true;
      } else if (blockID == Blocks.SAPLING) {
         return true;
      } else if (blockID == Blocks.TALLGRASS) {
         return true;
      } else if (blockID == OreSpawn.MyAppleLeaves) {
         return true;
      } else if (blockID == OreSpawn.MyExperienceLeaves) {
         return true;
      } else if (blockID == OreSpawn.MyScaryLeaves) {
         return true;
      } else if (blockID == OreSpawn.MyCherryLeaves) {
         return true;
      } else if (blockID == OreSpawn.MyPeachLeaves) {
         return true;
      } else if (blockID == OreSpawn.MyCrystalLeaves) {
         return true;
      } else {
         return blockID == OreSpawn.MyCrystalLeaves2 ? true : blockID == OreSpawn.MyCrystalLeaves3;
      }
   }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, net.minecraft.block.state.IBlockState state, BlockPos pos, EntityLivingBase par7EntityLivingBase) {
       int par4 = pos.getX(), par5 = pos.getY(), par6 = pos.getZ();
      if (this == OreSpawn.MyChainsaw && !par2World.isRemote) {
         for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 10; j++) {
               for (int k = -5; k <= 5; k++) {
                  Block bid = par2World.getBlockState(new BlockPos(par4 + i, par5 + j, par6 + k)).getBlock();
                  if (this.leaf) {
                     if (this.isLeaves(bid)) {
                        this.dropItemRand(par2World, Item.getItemFromBlock(bid), 1, par4 + i, par5 + j, par6 + k);
                        par2World.setBlockState(new BlockPos(par4 + i, par5 + j, par6 + k), (Blocks.AIR).getDefaultState());
                     }
                  } else if (this.canCrush(bid)) {
                     this.dropItemRand(par2World, Item.getItemFromBlock(bid), 1, par4 + i, par5 + j, par6 + k);
                     par2World.setBlockState(new BlockPos(par4 + i, par5 + j, par6 + k), (Blocks.AIR).getDefaultState());
                  }
               }
            }
         }
      }

       return super.onBlockDestroyed(par1ItemStack, par2World, state, pos, par7EntityLivingBase);
   }

   private ItemStack dropItemRand(World world, Item index, int par1, int x, int y, int z) {
      EntityItem var3x = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3x = new EntityItem(
         world,
         x + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),
         y + 1.0 + world.rand.nextInt(5),
         z + OreSpawn.OreSpawnRand.nextInt(5) - OreSpawn.OreSpawnRand.nextInt(5),
         is
      );
      if (var3x != null) {
         world.spawnEntity(var3x);
      }

      return is;
   }

   public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
      if (par2Block != null && this == OreSpawn.MyChainsaw) {
         this.leaf = this.isLeaves(par2Block);
         if (par2Block.getDefaultState().getMaterial() == Material.WOOD
            || par2Block.getDefaultState().getMaterial() == Material.PLANTS
            || par2Block.getDefaultState().getMaterial() == Material.VINE) {
            return OreSpawn.chainsaw_stats.efficiency;
         }

         if (this.canCrush(par2Block)) {
            return OreSpawn.chainsaw_stats.efficiency;
         }
      }

      return 2.0F;
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

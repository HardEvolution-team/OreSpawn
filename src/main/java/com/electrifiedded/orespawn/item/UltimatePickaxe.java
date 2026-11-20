package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class UltimatePickaxe extends ItemPickaxe {
   private int weaponDamage = 15;

   public UltimatePickaxe(int par1, ToolMaterial par2) {
      super(par2);

      this.setMaxDamage(3000);
      this.setCreativeTab(CreativeTabs.TOOLS);
   }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.EFFICIENCY, 5);
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.FORTUNE, 5);
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.EFFICIENCY, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.EFFICIENCY, 5);
         stack.addEnchantment(net.minecraft.init.Enchantments.FORTUNE, 5);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   public boolean canHarvestBlock(Block par1Block) {
      return true;
   }

   public int getDamageVsEntity(Entity par1Entity) {
      if (par1Entity instanceof Girlfriend) {
         return 1;
      } else if (par1Entity instanceof Boyfriend) {
         return 1;
      } else {
         return par1Entity instanceof EntityPlayer ? 1 : this.weaponDamage;
      }
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

      return false;
   }

   private ItemStack dropItemAnItem(World world, int x, int y, int z, Item index, int par1) {
      EntityItem var3x = null;
      ItemStack is = new ItemStack(index, par1, 0);
      var3x = new EntityItem(world, x, y, z, is);
      if (var3x != null) {
         world.spawnEntity(var3x);
      }

      return is;
   }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, net.minecraft.block.state.IBlockState state, BlockPos pos, EntityLivingBase par7EntityLivingBase) {
       Block par3 = state.getBlock();
       int par4 = pos.getX(), par5 = pos.getY(), par6 = pos.getZ();
       if (par3.getBlockHardness(state, par2World, pos) != 0.0) {
         par1ItemStack.damageItem(1, par7EntityLivingBase);
      }

      if (!par2World.isRemote) {
         if (par3 == Blocks.IRON_ORE && par2World.rand.nextInt(2) != 0) {
            this.dropItemAnItem(par2World, par4, par5, par6, Items.IRON_INGOT, 1 + par2World.rand.nextInt(2));
         }

         if (par3 == Blocks.GOLD_ORE && par2World.rand.nextInt(2) != 0) {
            this.dropItemAnItem(par2World, par4, par5, par6, Items.GOLD_INGOT, 1 + par2World.rand.nextInt(2));
         }

         if (par3 == Blocks.STONE && par2World.rand.nextInt(100) == 2) {
            int i = par2World.rand.nextInt(10);
            if (i == 0) {
               this.dropItemAnItem(par2World, par4, par5, par6, Items.DIAMOND, 1);
            }

            if (i == 1) {
               this.dropItemAnItem(par2World, par4, par5, par6, Items.EMERALD, 1);
            }

            if (i == 2) {
               this.dropItemAnItem(par2World, par4, par5, par6, OreSpawn.MyAmethyst, 1);
            }

            if (i == 3) {
               this.dropItemAnItem(par2World, par4, par5, par6, OreSpawn.MyRuby, 1);
            }

            if (i == 4) {
               this.dropItemAnItem(par2World, par4, par5, par6, OreSpawn.UraniumNugget, 1);
            }

            if (i == 5) {
               this.dropItemAnItem(par2World, par4, par5, par6, OreSpawn.TitaniumNugget, 1);
            }
         }
      }

      return true;
   }

   public String getMaterialName() {
      return "Uranium/Titanium";
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

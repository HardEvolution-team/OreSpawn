package com.electrifiedded.orespawn.item;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.entity.TheKing;
import com.electrifiedded.orespawn.entity.TheQueen;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemMagicApple extends Item {
   public int tree_radius = 6;
   public boolean no_critters = false;
   Random rand = OreSpawn.OreSpawnRand;
   private final com.electrifiedded.orespawn.util.SimpleChest[] chestContentsList = new com.electrifiedded.orespawn.util.SimpleChest[]{
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ENDER_PEARL, 0, 1, 2, 3),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND, 0, 1, 5, 15),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BLAZE_ROD, 0, 1, 3, 10),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CageEmpty, 0, 1, 10, 7),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.CagedGirlfriend, 0, 1, 2, 6),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_INGOT, 0, 1, 10, 16),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLD_INGOT, 0, 1, 6, 16),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.UraniumNugget, 0, 1, 6, 6),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.TitaniumNugget, 0, 1, 4, 6),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BREAD, 0, 1, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.APPLE, 0, 1, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKIE, 0, 1, 16, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_BEEF, 0, 1, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_CHICKEN, 0, 1, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_FISH, 0, 1, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.COOKED_PORKCHOP, 0, 1, 8, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.PUMPKIN_PIE, 0, 1, 4, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.CARROT, 0, 1, 16, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.POTATO, 0, 1, 16, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MySunFish, 0, 1, 4, 6),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyFireFish, 0, 1, 8, 6),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyPopcornBag, 0, 1, 4, 16),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_PICKAXE, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_SWORD, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND_PICKAXE, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND_SWORD, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.BOW, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.ARROW, 0, 1, 64, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimatePickaxe, 0, 1, 1, 2),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateSword, 0, 1, 1, 1),
      new com.electrifiedded.orespawn.util.SimpleChest(OreSpawn.MyUltimateFishingRod, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_CHESTPLATE, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_HELMET, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_LEGGINGS, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.IRON_BOOTS, 0, 1, 1, 20),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND_CHESTPLATE, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND_HELMET, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND_LEGGINGS, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.DIAMOND_BOOTS, 0, 1, 1, 5),
      new com.electrifiedded.orespawn.util.SimpleChest(Items.GOLDEN_APPLE, 0, 1, 1, 5)
   };

    public ItemMagicApple(int i) {
       this("magic_apple");
    }

    public ItemMagicApple(String name) {
       this.setRegistryName(name);
       this.setTranslationKey(name);
       this.setCreativeTab(CreativeTabs.DECORATIONS);
       this.setMaxStackSize(1);
    }

    @Override
    public net.minecraft.util.EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
                                                          net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing facing,
                                                          float hitX, float hitY, float hitZ) {
       return onItemUse(player.getHeldItem(hand), player, world, pos.getX(), pos.getY(), pos.getZ(),
          facing.getIndex(), hitX, hitY, hitZ)
          ? net.minecraft.util.EnumActionResult.SUCCESS : net.minecraft.util.EnumActionResult.FAIL;
    }

   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.FORTUNE, 2);
   }

   private Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6) {
      Entity var8 = null;
      var8 = EntityList.createEntityByID(par1, par0World);
      if (var8 != null) {
         var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         par0World.spawnEntity(var8);
         com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8, var8.world);
      }

      return var8;
   }

   public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
      int lvl = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, stack);
      if (lvl <= 0) {
         stack.addEnchantment(net.minecraft.init.Enchantments.FORTUNE, 2);
      }
   }

   public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
      this.onUsingTick(stack, (EntityPlayer)null, 0);
   }

   // Air is checked on the buffered state already read: World.isAirBlock bypasses the generation
   // buffer and may load or generate the neighbouring chunk for every probed block.
   private static boolean isAir(World world, IBlockState state, int x, int y, int z) {
      return state.getMaterial() == net.minecraft.block.material.Material.AIR || state.getBlock().isAir(state, world, new BlockPos(x, y, z));
   }

   private Boolean isBoringBlock(World world, int x, int y, int z) {
      IBlockState state = OreSpawn.getBlockStateFast(world, x, y, z);
      Block var1x = state.getBlock();
      if (var1x == Blocks.TALLGRASS) {
         return true;
      } else if (var1x == Blocks.CACTUS) {
         return true;
      } else if (var1x == Blocks.RED_FLOWER) {
         return true;
      } else if (var1x == Blocks.YELLOW_FLOWER) {
         return true;
      } else if (var1x == Blocks.LEAVES) {
         return true;
      } else if (var1x == Blocks.SNOW) {
         return true;
      } else if (var1x == OreSpawn.MyStrawberryPlant) {
         return true;
      } else if (var1x == OreSpawn.MyAppleLeaves) {
         return true;
      } else if (isAir(world, state, x, y, z)) {
         return true;
      } else {
         return var1x == null ? true : false;
      }
   }

   private Boolean isBoringBaseBlock(World world, int x, int y, int z) {
      IBlockState state = OreSpawn.getBlockStateFast(world, x, y, z);
      if (isAir(world, state, x, y, z)) {
         return true;
      } else {
         Block var1x = state.getBlock();
         if (var1x == Blocks.STONE) {
            return false;
         } else {
            return var1x == Blocks.BEDROCK ? false : true;
         }
      }
   }

   private void growVines(World world, int par2, int par3, int par4, int par5, int par6, Chunk chunk) {
      if (OreSpawn.getBlockStateFast(world, new BlockPos(par2, par3, par4)).getBlock() == Blocks.AIR) {
         this.FastSetBlock(world, par2, par3, par4, Blocks.VINE, par5, 2, chunk);

         while (par6 > 0) {
            if (OreSpawn.getBlockStateFast(world, new BlockPos(par2, --par3, par4)).getBlock() != Blocks.AIR) {
               return;
            }

            this.FastSetBlock(world, par2, par3, par4, Blocks.VINE, par5, 2, chunk);
            par6--;
         }
      }
   }

   private void make_branch(
      World world,
      int x,
      int y,
      int z,
      int this_width,
      int dirx,
      int dirz,
      Block ID,
      Block leafID,
      int tree_type,
      int t_radius,
      boolean bad_critters,
      Chunk chunk
   ) {
      int current_width = this_width;
      int last_branch = 0;
      int branch_side = 1;
      int leaf_depth = 0;
      int leaf_width = 0;
      int xaccum = dirx;
      int zaccum = dirz;
      if (this.rand.nextInt(2) == 0) {
         branch_side = -1;
      }

      while (current_width >= 0) {
         int length = this_width * 3 + this.rand.nextInt(this_width + 3);

         for (int i = 0; i < length; i++) {
            for (int j = -current_width; j <= current_width; j++) {
               int realx = x + j * dirz + xaccum;
               int realz = z + j * dirx + zaccum;
               if (this.isBoringBlock(world, realx, y, realz)) {
                  if (tree_type >= 0) {
                     this.FastSetBlock(world, realx, y, realz, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, realx, y, realz, ID, 0, 2, chunk);
                  }
               }

               if (i > 0 && j == 0 && current_width >= 3) {
                  if (tree_type >= 0 && this.rand.nextInt(75) == 0 || tree_type < 0 && this.rand.nextInt(50) == 0) {
                     if (!bad_critters && world.isAirBlock(new BlockPos(realx, y + 1, realz))) {
                        this.FastSetBlock(world, realx, y + 1, realz, Blocks.CHEST, 0, 2, chunk);
                        TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(realx, y + 1, realz));
                        if (chest != null) {
                           com.electrifiedded.orespawn.util.SimpleChest.fill(this.rand, this.chestContentsList, chest, 1 + this.rand.nextInt(8));
                        }
                     }
                  } else if (this.rand.nextInt(50) == 0
                     && !bad_critters
                     && world.isAirBlock(new BlockPos(realx, y + 1, realz))
                     && world.isAirBlock(new BlockPos(realx, y + 2, realz))
                     && world.isAirBlock(new BlockPos(realx, y + 3, realz))) {
                     Entity ent = null;
                     ent = this.spawnCreature(world, 99, realx + 0.5, y + 1.01, realz + 0.5);
                  }
               }
            }

            if (current_width < 3 || this_width <= 1) {
               leaf_depth = 2 + this.rand.nextInt(2);
               leaf_width = 2 + this.rand.nextInt(3);

               for (int n = 0; n < leaf_depth; n++) {
                  int lw = current_width + leaf_width - n;
                  if (current_width == 0 && length - i <= 2 && lw >= length - i) {
                     lw = length - i - 1;
                  }

                  if (lw < 0) {
                     lw = 0;
                  }

                  for (int var34 = -lw; var34 <= lw; var34++) {
                     int realxx = x + var34 * Math.abs(dirz) + xaccum + dirx;
                     int realzx = z + var34 * Math.abs(dirx) + zaccum + dirz;
                     if (this.isBoringBlock(world, realxx, y + n, realzx)) {
                        if (tree_type >= 0) {
                           this.FastSetBlock(world, realxx, y + n, realzx, leafID, tree_type, 2, chunk);
                           if (n == 0 && tree_type == 3 && lw != 0 && (var34 == lw || var34 == -lw) && this.rand.nextInt(5) == 0) {
                              if (dirx == 0) {
                                 if (var34 == lw) {
                                    this.growVines(world, realxx + 1, y, realzx, 2, this.rand.nextInt(10), chunk);
                                 } else {
                                    this.growVines(world, realxx - 1, y, realzx, 8, this.rand.nextInt(10), chunk);
                                 }
                              } else if (var34 == lw) {
                                 this.growVines(world, realxx, y, realzx + 1, 4, this.rand.nextInt(10), chunk);
                              } else {
                                 this.growVines(world, realxx, y, realzx - 1, 1, this.rand.nextInt(10), chunk);
                              }
                           }
                        } else {
                           Block local_leaf_type = leafID;
                           if (this.rand.nextInt(20) == 1) {
                              if (this.rand.nextInt(3) != 0) {
                                 local_leaf_type = Blocks.REDSTONE_BLOCK;
                              } else {
                                 int ilt = this.rand.nextInt(4);
                                 if (ilt == 0) {
                                    local_leaf_type = OreSpawn.MyBlockUraniumBlock;
                                 }

                                 if (ilt == 1) {
                                    local_leaf_type = OreSpawn.MyBlockTitaniumBlock;
                                 }

                                 if (ilt == 2) {
                                    local_leaf_type = OreSpawn.MyBlockRubyBlock;
                                 }

                                 if (ilt == 3) {
                                    local_leaf_type = OreSpawn.MyBlockAmethystBlock;
                                 }
                              }
                           }

                           this.FastSetBlock(world, realxx, y + n, realzx, local_leaf_type, 0, 2, chunk);
                        }
                     }
                  }
               }
            }

            if (current_width > 0 && last_branch > current_width && current_width != this_width && this.rand.nextInt(current_width + 1) == 0) {
               int subdirx = branch_side;
               int subdirz = 0;
               if (dirx != 0) {
                  subdirx = 0;
                  subdirz = branch_side;
               }

               this.make_branch(
                  world,
                  x + xaccum + current_width * subdirx,
                  y,
                  z + zaccum + current_width * subdirz,
                  current_width - 1,
                  subdirx,
                  subdirz,
                  ID,
                  leafID,
                  tree_type,
                  t_radius,
                  bad_critters,
                  chunk
               );
               last_branch = 0;
               if (branch_side < 0) {
                  branch_side = 1;
               } else {
                  branch_side = -1;
               }
            }

            xaccum += dirx;
            zaccum += dirz;
            last_branch++;
         }

         current_width--;
      }
   }

   public void MakeBigSquareTree(
      World world, int x, int y, int z, Block ID, Block leafID, Block stepID, int tree_type, int t_radius, boolean bad_critters, Chunk chunk
   ) {
      int this_height = t_radius + this.rand.nextInt(t_radius);
      int this_width = t_radius;
      int base_height = t_radius * 3;
      int spiral = 0;
      int current_y = 0;
      int branch = 0;
      int do_floor = 0;
      int platform_looper = 1;
      int last = -1;
      int last_last = -1;

      for (int i = -t_radius; i <= t_radius; i++) {
         if (this.isBoringBaseBlock(world, x + i, y, z - t_radius)) {
            for (int j = 0; j < 20; j++) {
               if (y - j > 0) {
                  if (!this.isBoringBaseBlock(world, x + i, y - j, z - t_radius)) {
                     break;
                  }

                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + i, y - j, z - t_radius, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + i, y - j, z - t_radius, ID, 0, 2, chunk);
                  }
               }
            }
         }

         if (this.isBoringBaseBlock(world, x + i, y, z + t_radius)) {
            for (int jx = 0; jx < 20; jx++) {
               if (y - jx > 0) {
                  if (!this.isBoringBaseBlock(world, x + i, y - jx, z + t_radius)) {
                     break;
                  }

                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + i, y - jx, z + t_radius, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + i, y - jx, z + t_radius, ID, 0, 2, chunk);
                  }
               }
            }
         }

         if (this.isBoringBaseBlock(world, x - t_radius, y, z + i)) {
            for (int jxx = 0; jxx < 20; jxx++) {
               if (y - jxx > 0) {
                  if (!this.isBoringBaseBlock(world, x - t_radius, y - jxx, z + i)) {
                     break;
                  }

                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x - t_radius, y - jxx, z + i, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x - t_radius, y - jxx, z + i, ID, 0, 2, chunk);
                  }
               }
            }
         }

         if (this.isBoringBaseBlock(world, x + t_radius, y, z + i)) {
            for (int jxxx = 0; jxxx < 20; jxxx++) {
               if (y - jxxx > 0) {
                  if (!this.isBoringBaseBlock(world, x + t_radius, y - jxxx, z + i)) {
                     break;
                  }

                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + t_radius, y - jxxx, z + i, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + t_radius, y - jxxx, z + i, ID, 0, 2, chunk);
                  }
               }
            }
         }
      }

      current_y = y;
      boolean var36 = false;

      for (int var34 = -t_radius; this_width >= 0; this_height += this.rand.nextInt(t_radius)) {
         if (this_width != t_radius) {
            base_height = 0;
         }

         for (int jxxxx = 0; jxxxx < this_height + base_height; jxxxx++) {
            var36 = false;

            for (int var29 = -this_width; var29 <= this_width; var29++) {
               if (this.isBoringBaseBlock(world, x + var29, current_y, z - this_width)) {
                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + var29, current_y, z - this_width, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + var29, current_y, z - this_width, ID, 0, 2, chunk);
                  }
               }

               if (this.isBoringBaseBlock(world, x + var29, current_y, z + this_width)) {
                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + var29, current_y, z + this_width, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + var29, current_y, z + this_width, ID, 0, 2, chunk);
                  }
               }

               if (this.isBoringBaseBlock(world, x - this_width, current_y, z + var29)) {
                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x - this_width, current_y, z + var29, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x - this_width, current_y, z + var29, ID, 0, 2, chunk);
                  }
               }

               if (this.isBoringBaseBlock(world, x + this_width, current_y, z + var29)) {
                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + this_width, current_y, z + var29, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + this_width, current_y, z + var29, ID, 0, 2, chunk);
                  }
               }
            }

            if (this_width != 0 || jxxxx < this_height / 2) {
               platform_looper = 1;
               if (var34 == 0 && this_width >= 2 || var34 == this_width || var34 == this_width - 1 && jxxxx == this_height + base_height - 1) {
                  platform_looper++;
                  if (var34 != 0 && this_width >= 3) {
                     platform_looper++;
                  }

                  if (var34 == 0) {
                     var36 = true;
                  }
               }

               for (int k = 0; k < platform_looper; k++) {
                  if (this.isBoringBlock(world, x - var34, current_y, z - this_width - 1)) {
                     this.FastSetBlock(world, x - var34, current_y, z - this_width - 1, stepID, 0, 2, chunk);
                  }

                  if (this.isBoringBlock(world, x + var34, current_y, z + this_width + 1)) {
                     this.FastSetBlock(world, x + var34, current_y, z + this_width + 1, stepID, 0, 2, chunk);
                  }

                  if (this.isBoringBlock(world, x - this_width - 1, current_y, z + var34)) {
                     this.FastSetBlock(world, x - this_width - 1, current_y, z + var34, stepID, 0, 2, chunk);
                  }

                  if (this.isBoringBlock(world, x + this_width + 1, current_y, z - var34)) {
                     this.FastSetBlock(world, x + this_width + 1, current_y, z - var34, stepID, 0, 2, chunk);
                  }

                  if (this_width >= 3) {
                     if (this.isBoringBlock(world, x - var34, current_y, z - this_width - 2)) {
                        this.FastSetBlock(world, x - var34, current_y, z - this_width - 2, stepID, 0, 2, chunk);
                     }

                     if (this.isBoringBlock(world, x + var34, current_y, z + this_width + 2)) {
                        this.FastSetBlock(world, x + var34, current_y, z + this_width + 2, stepID, 0, 2, chunk);
                     }

                     if (this.isBoringBlock(world, x - this_width - 2, current_y, z + var34)) {
                        this.FastSetBlock(world, x - this_width - 2, current_y, z + var34, stepID, 0, 2, chunk);
                     }

                     if (this.isBoringBlock(world, x + this_width + 2, current_y, z - var34)) {
                        this.FastSetBlock(world, x + this_width + 2, current_y, z - var34, stepID, 0, 2, chunk);
                     }
                  }

                  if (platform_looper != 1) {
                     var34++;
                  }
               }

               if (var36) {
                  for (int m = -this_width; m <= this_width; m++) {
                     for (int n = -this_width; n <= this_width; n++) {
                        if (this.isBoringBlock(world, x + m, current_y, z + n)) {
                           if (tree_type >= 0) {
                              this.FastSetBlock(world, x + m, current_y, z + n, ID, tree_type, 2, chunk);
                           } else {
                              this.FastSetBlock(world, x + m, current_y, z + n, ID, 0, 2, chunk);
                           }

                           if (m == 0 && n == 0 && this.rand.nextInt(2) == 0 && !bad_critters && world.isAirBlock(new BlockPos(x, current_y + 1, z))) {
                              this.FastSetBlock(world, x, current_y + 1, z, Blocks.CHEST, 0, 2, chunk);
                              TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(x, current_y + 1, z));
                              if (chest != null) {
                                 com.electrifiedded.orespawn.util.SimpleChest.fill(
                                    this.rand, this.chestContentsList, chest, t_radius - this_width + this.rand.nextInt(10)
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (this_width != t_radius) {
               int next = this.rand.nextInt(4 + this_width);

               while (next == last || next == last_last) {
                  next = this.rand.nextInt(4 + this_width);
               }

               if (next < 4) {
                  last_last = last;
                  last = next;
               }

               switch (next) {
                  case 0:
                     this.make_branch(world, x + this_width, current_y, z, this_width, 1, 0, ID, leafID, tree_type, t_radius, bad_critters, chunk);
                     break;
                  case 1:
                     this.make_branch(world, x - this_width, current_y, z, this_width, -1, 0, ID, leafID, tree_type, t_radius, bad_critters, chunk);
                     break;
                  case 2:
                     this.make_branch(world, x, current_y, z + this_width, this_width, 0, 1, ID, leafID, tree_type, t_radius, bad_critters, chunk);
                     break;
                  case 3:
                     this.make_branch(world, x, current_y, z - this_width, this_width, 0, -1, ID, leafID, tree_type, t_radius, bad_critters, chunk);
               }
            }

            current_y++;
            if (!var36) {
               var34++;
            }

            if (var34 > this_width) {
               var34 = -this_width;
            }
         }

         if (Math.abs(var34) > --this_width) {
            var34 = -this_width;
         }
      }

      if (this.isBoringBaseBlock(world, x, current_y, z)) {
         this.FastSetBlock(world, x, current_y, z, Blocks.EMERALD_BLOCK, 0, 2, chunk);
         this.FastSetBlock(world, x, current_y + 1, z, Blocks.EMERALD_BLOCK, 0, 2, chunk);
         if (stepID == Blocks.DIAMOND_BLOCK) {
            Entity var8x = null;
            var8x = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create("The King", world);
            if (var8x != null) {
               var8x.setLocationAndAngles(x, current_y + 4, z, world.rand.nextFloat() * 360.0F, 0.0F);
               world.spawnEntity(var8x);
               com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8x, var8x.world);
               ((TheKing)var8x).setGuardMode(1);
            }
         }

         if (stepID == OreSpawn.MyBlockAmethystBlock) {
            Entity var8x = null;
            var8x = com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.create("The Queen", world);
            if (var8x != null) {
               var8x.setLocationAndAngles(x, current_y + 4, z, world.rand.nextFloat() * 360.0F, 0.0F);
               world.spawnEntity(var8x);
               com.electrifiedded.orespawn.entity.OreSpawnEntityRegistry.initialize(var8x, var8x.world);
               ((TheQueen)var8x).setGuardMode(1);
               ((TheQueen)var8x).setBadMood(1);
            }
         }
      }
   }

   private void MakeCirclularBranch(
      World world, int iangle, int branchlen, int width, int startx, int starty, int startz, int twist, Block ID, Block leafID, int tree_type, Chunk chunk
   ) {
      double curlen = 0.0;
      int curangle = iangle;
      double curx = startx;
      double curz = startz;

      for (double var30 = 0.0; var30 < branchlen; var30 += 0.5) {
         curx += 0.5 * Math.sin(Math.toRadians(curangle));
         curz += 0.5 * Math.cos(Math.toRadians(curangle));
         double tw = width - width * var30 / branchlen;

         for (double wd = 0.0; wd <= tw; wd += 0.5) {
            Block id = leafID;
            if (wd < tw / 2.0) {
               id = ID;
            }

            if (tw < 0.9) {
               id = leafID;
            }

            int ta = curangle + 90;
            if (ta > 360) {
               ta -= 360;
            }

            double wx = curx + wd * Math.sin(Math.toRadians(ta));
            double wz = curz + wd * Math.cos(Math.toRadians(ta));
            if (this.isBoringBlock(world, (int)wx, starty, (int)wz)) {
               if (tree_type >= 0) {
                  this.FastSetBlock(world, (int)wx, starty, (int)wz, id, tree_type, 2, chunk);
               } else {
                  this.FastSetBlock(world, (int)wx, starty, (int)wz, id, 0, 2, chunk);
               }
            }

            if (id == ID && this.isBoringBlock(world, (int)wx, starty + 1, (int)wz)) {
               if (tree_type >= 0) {
                  this.FastSetBlock(world, (int)wx, starty + 1, (int)wz, leafID, tree_type, 2, chunk);
               } else {
                  this.FastSetBlock(world, (int)wx, starty + 1, (int)wz, leafID, 0, 2, chunk);
               }
            }

            ta = curangle - 90;
            if (ta < 0) {
               ta += 360;
            }

            wx = curx + wd * Math.sin(Math.toRadians(ta));
            wz = curz + wd * Math.cos(Math.toRadians(ta));
            if (this.isBoringBlock(world, (int)wx, starty, (int)wz)) {
               if (tree_type >= 0) {
                  this.FastSetBlock(world, (int)wx, starty, (int)wz, id, tree_type, 2, chunk);
               } else {
                  this.FastSetBlock(world, (int)wx, starty, (int)wz, id, 0, 2, chunk);
               }
            }

            if (id == ID && this.isBoringBlock(world, (int)wx, starty + 1, (int)wz)) {
               if (tree_type >= 0) {
                  this.FastSetBlock(world, (int)wx, starty + 1, (int)wz, leafID, tree_type, 2, chunk);
               } else {
                  this.FastSetBlock(world, (int)wx, starty + 1, (int)wz, leafID, 0, 2, chunk);
               }
            }
         }

         curangle += twist;
         if (curangle < 0) {
            curangle += 360;
         }

         if (curangle >= 360) {
            curangle -= 360;
         }
      }
   }

   public void MakeBigCircularTree(
      World world, int x, int y, int z, Block ID, Block leafID, Block stepID, int tree_type, int t_radius, boolean bad_critters, Chunk chunk
   ) {
      double rad = t_radius;
      int curx = 0;
      int cury = 0;
      int curz = 0;
      int stepindex = this.rand.nextInt(360);
      int ibranch = 0;
      cury = y;

      for (int i = 0; i < 360; i++) {
         double dt = rad * Math.sin(Math.toRadians(i)) + 0.5;
         curx = (int)dt;
         dt = rad * Math.cos(Math.toRadians(i)) + 0.5;
         curz = (int)dt;
         if (this.isBoringBaseBlock(world, x + curx, cury, z + curz)) {
            for (int j = 0; j < 20; j++) {
               if (cury - j > 0) {
                  if (!this.isBoringBaseBlock(world, x + curx, cury - j, z + curz)) {
                     break;
                  }

                  if (tree_type >= 0) {
                     this.FastSetBlock(world, x + curx, cury - j, z + curz, ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, x + curx, cury - j, z + curz, ID, 0, 2, chunk);
                  }
               }
            }
         }
      }

      cury = 1;

      while (rad > 0.0) {
         for (int var29 = 0; var29 < 360; var29++) {
            double dt = rad * Math.sin(Math.toRadians(var29)) + 0.5;
            curx = (int)dt;
            dt = rad * Math.cos(Math.toRadians(var29)) + 0.5;
            curz = (int)dt;
            if (this.isBoringBaseBlock(world, x + curx, y + cury, z + curz)) {
               if (tree_type >= 0) {
                  this.FastSetBlock(world, x + curx, y + cury, z + curz, ID, tree_type, 2, chunk);
               } else {
                  this.FastSetBlock(world, x + curx, y + cury, z + curz, ID, 0, 2, chunk);
               }
            }

            if (var29 >= stepindex - 1 && var29 <= stepindex + 1 && rad > 1.0) {
               dt = (rad + 1.9) * Math.sin(Math.toRadians(var29)) + 0.5;
               curx = (int)dt;
               dt = (rad + 1.9) * Math.cos(Math.toRadians(var29)) + 0.5;
               curz = (int)dt;

               for (int m = -1; m <= 1; m++) {
                  for (int n = -1; n <= 1; n++) {
                     if (this.isBoringBaseBlock(world, x + curx + m, y + cury, z + curz + n)) {
                        this.FastSetBlock(world, x + curx + m, y + cury, z + curz + n, stepID, 0, 2, chunk);
                     }
                  }
               }
            }
         }

         if (cury > (int)rad) {
            ibranch += 80 + this.rand.nextInt(80);
            if (ibranch > 360) {
               ibranch -= 360;
            }

            int ibranchlen = (int)(rad * 5.0) + this.rand.nextInt((int)rad + 2);
            double dtx = rad * Math.sin(Math.toRadians(ibranch)) + 0.5;
            curx = (int)dtx;
            dtx = rad * Math.cos(Math.toRadians(ibranch)) + 0.5;
            curz = (int)dtx;
            this.MakeCirclularBranch(
               world,
               ibranch,
               ibranchlen,
               (int)rad + 1,
               x + curx,
               y + cury,
               z + curz,
               this.rand.nextInt(2) * (this.rand.nextInt(2) == 0 ? -1 : 1),
               ID,
               leafID,
               tree_type,
               chunk
            );
         }

         if (cury % 6 == 0 && rad > 3.0) {
            for (double dr = rad - 0.25; dr > 0.0; dr -= 0.25) {
               for (int var30 = 0; var30 < 360; var30++) {
                  double dtx = dr * Math.sin(Math.toRadians(var30)) + 0.5;
                  curx = (int)dtx;
                  dtx = dr * Math.cos(Math.toRadians(var30)) + 0.5;
                  curz = (int)dtx;
                  if (this.isBoringBaseBlock(world, x + curx, y + cury, z + curz)) {
                     if (tree_type >= 0) {
                        this.FastSetBlock(world, x + curx, y + cury, z + curz, ID, tree_type, 2, chunk);
                     } else {
                        this.FastSetBlock(world, x + curx, y + cury, z + curz, ID, 0, 2, chunk);
                     }
                  }
               }
            }

            if (this.rand.nextInt(2) == 0 && !bad_critters && world.isAirBlock(new BlockPos(x, y + cury + 1, z))) {
               this.FastSetBlock(world, x, y + cury + 1, z, Blocks.CHEST, 0, 2, chunk);
               TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(x, y + cury + 1, z));
               if (chest != null) {
                  com.electrifiedded.orespawn.util.SimpleChest.fill(this.rand, this.chestContentsList, chest, t_radius - (int)rad + this.rand.nextInt(10));
               }
            }
         }

         stepindex += 15 + (int)((t_radius - rad) * 3.0);
         if (stepindex > 360) {
            stepindex -= 360;
         }

         cury++;
         rad -= 0.01 * this.rand.nextInt(15);
         if (rad <= 0.0 && this.isBoringBaseBlock(world, x, y + cury, z)) {
            this.FastSetBlock(world, x, y + cury, z, Blocks.DIAMOND_BLOCK, 0, 2, chunk);
         }
      }
   }

   public void MakeBigRoundTree(World world, int inx, int y, int inz, Block ID, Block leafID, Block stepID, int tree_type, int t_radius, Chunk chunk) {
      double rad = t_radius;
      int cury = 0;
      int ibranch = 0;
      float fx = inx;
      fx += 0.5F;
      float fz = inz;
      fz += 0.5F;
      cury = y;

      for (int i = 0; i < 360; i++) {
         double dt = rad * Math.sin(Math.toRadians(i));
         float fcurx = (float)dt;
         dt = rad * Math.cos(Math.toRadians(i));
         float fcurz = (float)dt;
         if (this.isBoringBaseBlock(world, (int)(fx + fcurx), cury, (int)(fz + fcurz))) {
            for (int j = 0; j < 20; j++) {
               if (cury - j > 0) {
                  if (!this.isBoringBaseBlock(world, (int)(fx + fcurx), cury - j, (int)(fz + fcurz))) {
                     break;
                  }

                  if (tree_type >= 0) {
                     this.FastSetBlock(world, (int)(fx + fcurx), cury - j, (int)(fz + fcurz), ID, tree_type, 2, chunk);
                  } else {
                     this.FastSetBlock(world, (int)(fx + fcurx), cury - j, (int)(fz + fcurz), ID, 0, 2, chunk);
                  }
               }
            }
         }
      }

      cury = 1;

      while (rad > 0.0) {
         for (int var28 = 0; var28 < 360; var28++) {
            double dt = rad * Math.sin(Math.toRadians(var28));
            float fcurx = (float)dt;
            dt = rad * Math.cos(Math.toRadians(var28));
            float fcurz = (float)dt;
            if (this.isBoringBaseBlock(world, (int)(fx + fcurx), y + cury, (int)(fz + fcurz))) {
               if (tree_type >= 0) {
                  this.FastSetBlock(world, (int)(fx + fcurx), y + cury, (int)(fz + fcurz), ID, tree_type, 2, chunk);
               } else {
                  this.FastSetBlock(world, (int)(fx + fcurx), y + cury, (int)(fz + fcurz), ID, 0, 2, chunk);
               }
            }
         }

         if (cury > (int)rad) {
            ibranch += 80 + world.rand.nextInt(80);
            if (ibranch > 360) {
               ibranch -= 360;
            }

            int ibranchlen = (int)(rad * 5.0) + world.rand.nextInt((int)rad + 2);
            double dt = rad * Math.sin(Math.toRadians(ibranch));
            float fcurx = (float)dt;
            dt = rad * Math.cos(Math.toRadians(ibranch));
            float fcurz = (float)dt;
            this.MakeRoundBranch(world, ibranch, ibranchlen, (int)rad + 1, fx + fcurx, y + cury, fz + fcurz, ID, leafID, tree_type, chunk);
         }

         if (cury % 6 == 0 && rad > 3.0) {
            for (double dr = rad - 0.25; dr > 0.0; dr -= 0.25) {
               for (int var29 = 0; var29 < 360; var29++) {
                  double dt = dr * Math.sin(Math.toRadians(var29));
                  float fcurx = (float)dt;
                  dt = dr * Math.cos(Math.toRadians(var29));
                  float fcurz = (float)dt;
                  if (this.isBoringBaseBlock(world, (int)(fx + fcurx), y + cury, (int)(fz + fcurz))) {
                     if (tree_type >= 0) {
                        this.FastSetBlock(world, (int)(fx + fcurx), y + cury, (int)(fz + fcurz), ID, tree_type, 2, chunk);
                     } else {
                        this.FastSetBlock(world, (int)(fx + fcurx), y + cury, (int)(fz + fcurz), ID, 0, 2, chunk);
                     }
                  }
               }
            }
         }

         cury++;
         rad -= 0.01 * world.rand.nextInt(15);
         if (rad <= 0.0 && this.isBoringBaseBlock(world, (int)fx, y + cury, (int)fz)) {
            this.FastSetBlock(world, (int)fx, y + cury, (int)fz, Blocks.DIAMOND_BLOCK, 0, 2, chunk);
         }
      }
   }

   private void MakeRoundBranch(
      World world, int iangle, int branchlen, int width, float startx, int starty, float startz, Block ID, Block leafID, int tree_type, Chunk chunk
   ) {
      double deltadir = 0.06283185200000001;
      double deltamag = 0.35F;
      int ixlast = 0;
      int izlast = 0;
      int xoff = 0;
      int zoff = 0;
      int radius = branchlen / 2;
      float centerx = (float)(startx + radius * Math.sin(Math.toRadians(iangle)));
      float centerz = (float)(startz + radius * Math.cos(Math.toRadians(iangle)));
      izlast = 0;
      ixlast = 0;

      for (double curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
         for (double h = 0.75; h < radius; h += deltamag) {
            int ix = (int)(centerx + Math.cos(curdir) * h);
            int iz = (int)(centerz + Math.sin(curdir) * h);
            if (ix != ixlast || iz != izlast) {
               ixlast = ix;
               izlast = iz;
               Block id = ID;
               if (radius - h < 2.0) {
                  id = leafID;
               }

               if (this.isBoringBlock(world, ix, starty, iz)) {
                  this.FastSetBlock(world, ix, starty, iz, id, tree_type, 2, chunk);
               }
            }
         }
      }
   }

   public void FastSetBlock(World world, int ix, int iy, int iz, Block id, int im, int iflg, Chunk chunk) {
      OreSpawn.setBlockSuperFast(world, ix, iy, iz, id, im, 2, chunk);
   }

   public boolean onItemUse(
      ItemStack par1ItemStack,
      EntityPlayer par2EntityPlayer,
      World world,
      int clickedX,
      int clickedY,
      int clickedZ,
      int par7,
      float par8,
      float par9,
      float par10
   ) {
      Block var1x = OreSpawn.getBlockStateFast(world, new BlockPos(clickedX, clickedY, clickedZ)).getBlock();
      if (var1x != Blocks.GRASS && var1x != Blocks.SOUL_SAND && var1x != Blocks.DIRT) {
         return false;
      } else {
         int tree_type = this.rand.nextInt(4);
         Block leaf_type = Blocks.LEAVES;
         this.no_critters = true;
         if (this.rand.nextInt(2) == 1) {
            this.no_critters = false;
         }

         if (!world.isRemote) {
            world.setBlockState(new BlockPos(clickedX, clickedY, clickedZ), (Blocks.GOLD_BLOCK).getStateFromMeta(0), 2);
         }

         for (int var3x = 0; var3x < 6; var3x++) {
            par2EntityPlayer.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, clickedX + 0.5F, clickedY + 1 + 0.25F, clickedZ + 0.5F, 0.0, 0.0, 0.0);
            par2EntityPlayer.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_LARGE, clickedX + 0.5F, clickedY + 1 + 0.25F, clickedZ + 0.5F, 0.0, 0.0, 0.0);
            par2EntityPlayer.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, clickedX + 0.5F, clickedY + 1 + 0.25F, clickedZ + 0.5F, 0.0, 0.0, 0.0);
         }

         par2EntityPlayer.world.playSound(null, par2EntityPlayer.posX, par2EntityPlayer.posY, par2EntityPlayer.posZ, com.electrifiedded.orespawn.OreSpawnSoundEvents.any("random.explode"), net.minecraft.util.SoundCategory.NEUTRAL, 2.8F, 1.5F);
         if (!world.isRemote) {
            int rand_treetype = this.rand.nextInt(100);
            if (rand_treetype >= 20) {
               if (rand_treetype >= 40) {
                  if (tree_type != 3 && this.rand.nextInt(10) == 1) {
                     leaf_type = OreSpawn.MyAppleLeaves;
                  }

                  this.MakeBigSquareTree(
                     world,
                     clickedX,
                     clickedY,
                     clickedZ,
                     Blocks.LOG,
                     leaf_type,
                     Blocks.MOSSY_COBBLESTONE,
                     tree_type,
                     this.tree_radius,
                     this.no_critters,
                     null
                  );
               } else {
                  this.MakeBigRoundTree(
                     world, clickedX, clickedY, clickedZ, Blocks.LOG, leaf_type, Blocks.MOSSY_COBBLESTONE, tree_type, this.tree_radius, null
                  );
               }
            } else if (rand_treetype == 1) {
               if (OreSpawn.GinormousEmeraldTreeEnable != 0) {
                  if (this.rand.nextInt(2) == 0) {
                     this.MakeBigSquareTree(
                        world,
                        clickedX,
                        clickedY,
                        clickedZ,
                        Blocks.GOLD_BLOCK,
                        Blocks.EMERALD_BLOCK,
                        Blocks.DIAMOND_BLOCK,
                        -1,
                        this.tree_radius,
                        true,
                        null
                     );
                  } else {
                     this.MakeBigSquareTree(
                        world,
                        clickedX,
                        clickedY,
                        clickedZ,
                        Blocks.OBSIDIAN,
                        OreSpawn.MyBlockRubyBlock,
                        OreSpawn.MyBlockAmethystBlock,
                        -1,
                        this.tree_radius,
                        true,
                        null
                     );
                  }
               } else {
                  this.MakeBigSquareTree(
                     world,
                     clickedX,
                     clickedY,
                     clickedZ,
                     Blocks.LOG,
                     leaf_type,
                     Blocks.IRON_ORE,
                     tree_type,
                     this.tree_radius,
                     this.no_critters,
                     null
                  );
               }
            } else {
               this.MakeBigCircularTree(
                  world,
                  clickedX,
                  clickedY,
                  clickedZ,
                  Blocks.LOG,
                  leaf_type,
                  Blocks.MOSSY_COBBLESTONE,
                  tree_type,
                  this.tree_radius,
                  this.no_critters,
                  null
               );
            }
         }

         if (!par2EntityPlayer.capabilities.isCreativeMode) {
            par1ItemStack.shrink(1);
         }

         return true;
      }
   }

   @Override
   public void onUsingTick(ItemStack stack, net.minecraft.entity.EntityLivingBase entity, int count) {
      onUsingTick(stack, entity instanceof EntityPlayer ? (EntityPlayer) entity : null, count);
   }
}

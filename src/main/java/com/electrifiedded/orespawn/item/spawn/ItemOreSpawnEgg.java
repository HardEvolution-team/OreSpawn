package com.electrifiedded.orespawn.item.spawn;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ItemOreSpawnEgg extends Item {
    private final Class<? extends Entity> entityClass;
    private final int primaryColor;
    private final int secondaryColor;

    public ItemOreSpawnEgg(Class<? extends Entity> entityClass, int primaryColor, int secondaryColor) {
        this.entityClass = entityClass;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        setTranslationKey("spawn_egg_" + entityClass.getSimpleName().toLowerCase());
        setRegistryName("spawn_egg_" + entityClass.getSimpleName().toLowerCase());
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(64);
    }

    @Override
    public EnumActionResult onItemUse(net.minecraft.entity.player.EntityPlayer player, net.minecraft.world.World world, net.minecraft.util.math.BlockPos pos, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.SUCCESS;
        
        ResourceLocation entityName = EntityList.getKey(entityClass);
        Entity entity = EntityList.createEntityByIDFromName(entityName, world);
        if (entity != null) {
            entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(entity);
            if (!player.capabilities.isCreativeMode) {
                player.getHeldItem(hand).shrink(1);
            }
        }
        return EnumActionResult.SUCCESS;
    }
}
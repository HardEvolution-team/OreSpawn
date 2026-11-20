package com.electrifiedded.orespawn.block;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/** Ancient dried spawn egg ore. It drops itself and is hydrated through crafting. */
public class OreGenericEgg extends Block {
    private final String entityName;

    public OreGenericEgg(String registryName, String entityRegistryName) {
        super(Material.GROUND);
        setHardness(0.5F);
        setResistance(1.0F);
        setSoundType(net.minecraft.block.SoundType.GROUND);
        setTranslationKey(registryName);
        setRegistryName(registryName);
        this.entityName = entityRegistryName;
    }

    public String getEntityName() { return entityName; }

    /** Half the time breaking a dried egg also releases 5-9 experience, like the legacy dropBlockAsItemWithChance. */
    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, net.minecraft.util.math.BlockPos pos, int fortune) {
        java.util.Random rand = world instanceof net.minecraft.world.World ? ((net.minecraft.world.World) world).rand : RANDOM;
        return rand.nextInt(2) == 1 ? 5 + rand.nextInt(3) + rand.nextInt(3) : 0;
    }
}

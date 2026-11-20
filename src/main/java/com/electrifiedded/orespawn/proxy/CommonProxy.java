package com.electrifiedded.orespawn.proxy;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.network.RiderControlMessage;
import com.electrifiedded.orespawn.network.RiderControlMessageHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IProxy {
    private SimpleNetworkWrapper network;

    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
        registerNetworkStuff();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public void sendRiderInput(EntityPlayer player) {
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void registerRenderThings() {
    }

    public void registerSoundThings() {
    }

    public void registerKeyboardInput() {
    }

    public void registerNetworkStuff() {
        this.network = NetworkRegistry.INSTANCE.newSimpleChannel("RiderControls");
        this.network.registerMessage(RiderControlMessageHandler.class, RiderControlMessage.class, 0, Side.SERVER);
    }

    public int setArmorPrefix(String string) {
        return 0;
    }
}

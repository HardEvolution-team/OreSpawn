package com.electrifiedded.orespawn.client;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyHandler {
    public static final KeyBinding FLY_UP = new KeyBinding("key.orespawn.fly_up", Keyboard.KEY_SPACE, "key.categories.orespawn");
    public static final KeyBinding FLY_DOWN = new KeyBinding("key.orespawn.fly_down", Keyboard.KEY_LSHIFT, "key.categories.orespawn");

    public KeyHandler() {
        ClientRegistry.registerKeyBinding(FLY_UP);
        ClientRegistry.registerKeyBinding(FLY_DOWN);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (FLY_UP.isPressed()) {
        }
        if (FLY_DOWN.isPressed()) {
        }
    }
}
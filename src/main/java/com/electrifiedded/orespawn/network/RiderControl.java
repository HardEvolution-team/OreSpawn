package com.electrifiedded.orespawn.network;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.client.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RiderControl {
    private final SimpleNetworkWrapper network;
    private int keyState;
    private float moveForward;
    private float moveStrafing;

    public RiderControl(SimpleNetworkWrapper network) {
        this.network = network;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.CLIENT || event.phase != TickEvent.Phase.END
            || event.player != Minecraft.getMinecraft().player) {
            return;
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        boolean riding = player != null && player.getRidingEntity() != null;
        int newKeyState = riding && KeyHandler.FLY_UP.isKeyDown() ? 1 : 0;
        float newMoveForward = riding
            ? (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() ? 1.0F : 0.0F)
                - (Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown() ? 1.0F : 0.0F)
            : 0.0F;
        float newMoveStrafing = riding
            ? (Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown() ? 1.0F : 0.0F)
                - (Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown() ? 1.0F : 0.0F)
            : 0.0F;
        boolean dismount = riding && Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown();
        RiderInputState.setFlyingUp(player, newKeyState != 0);
        RiderInputState.setMovement(player, newMoveForward, newMoveStrafing);
        if (riding || keyState != newKeyState || moveForward != newMoveForward || moveStrafing != newMoveStrafing) {
            network.sendToServer(new RiderControlMessage(newKeyState, newMoveForward, newMoveStrafing, dismount));
            keyState = newKeyState;
            moveForward = newMoveForward;
            moveStrafing = newMoveStrafing;
        }
        if (dismount) player.dismountRidingEntity();
    }
}

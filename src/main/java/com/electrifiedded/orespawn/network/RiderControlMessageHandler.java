package com.electrifiedded.orespawn.network;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import com.electrifiedded.orespawn.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RiderControlMessageHandler implements IMessageHandler<RiderControlMessage, IMessage> {
    @Override
    public IMessage onMessage(RiderControlMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        if (player != null && (message.keyState == 0 || message.keyState == 1)
            && Float.isFinite(message.moveForward) && Float.isFinite(message.moveStrafing)
            && Math.abs(message.moveForward) <= 1.0F && Math.abs(message.moveStrafing) <= 1.0F) {
             player.getServerWorld().addScheduledTask(() -> {
                if (message.dismount && player.isRiding()) {
                    player.dismountRidingEntity();
                }
                RiderInputState.setFlyingUp(player, message.keyState != 0);
                RiderInputState.setMovement(player, message.moveForward, message.moveStrafing);
                player.moveForward = message.moveForward;
                player.moveStrafing = message.moveStrafing;
            });
        }
        return null;
    }
}

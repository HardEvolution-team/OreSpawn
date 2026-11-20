package com.electrifiedded.orespawn.network;

import net.minecraft.entity.player.EntityPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Server-side rider input keyed by player, rather than the legacy global flag. */
public final class RiderInputState {
    private static final Map<UUID, Boolean> FLYING_UP = new ConcurrentHashMap<>();
    private static final Map<UUID, Float> FORWARD = new ConcurrentHashMap<>();
    private static final Map<UUID, Float> STRAFING = new ConcurrentHashMap<>();

    private RiderInputState() {}

    public static void setFlyingUp(EntityPlayer player, boolean flyingUp) {
        if (flyingUp) FLYING_UP.put(player.getUniqueID(), true);
        else FLYING_UP.remove(player.getUniqueID());
    }

    public static boolean isFlyingUp(EntityPlayer player) {
        return player != null && FLYING_UP.containsKey(player.getUniqueID());
    }

    public static void setMovement(EntityPlayer player, float forward, float strafing) {
        if (forward == 0.0F) FORWARD.remove(player.getUniqueID());
        else FORWARD.put(player.getUniqueID(), forward);
        if (strafing == 0.0F) STRAFING.remove(player.getUniqueID());
        else STRAFING.put(player.getUniqueID(), strafing);
    }

    public static float getForward(EntityPlayer player) {
        return player == null ? 0.0F : FORWARD.getOrDefault(player.getUniqueID(), 0.0F);
    }

    public static float getStrafing(EntityPlayer player) {
        return player == null ? 0.0F : STRAFING.getOrDefault(player.getUniqueID(), 0.0F);
    }
}

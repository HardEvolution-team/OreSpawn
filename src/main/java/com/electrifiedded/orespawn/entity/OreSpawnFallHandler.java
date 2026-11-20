package com.electrifiedded.orespawn.entity;

import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/** Central fall policy for OreSpawn creatures whose movement is not ground based. */
public final class OreSpawnFallHandler {
    private OreSpawnFallHandler() {
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof Frog) {
            event.setDistance(Math.max(0.0F, event.getDistance() - 6.0F));
            return;
        }

        if (isFlyingMob(event.getEntityLiving())) {
            event.setCanceled(true);
        }
    }

    private static boolean isFlyingMob(Object entity) {
        return entity instanceof Bee
            || entity instanceof Brutalfly
            || entity instanceof CliffRacer
            || entity instanceof CloudShark
            || entity instanceof Cockateil
            || entity instanceof Dragon
            || entity instanceof Dragonfly
            || entity instanceof EntityButterfly
            || entity instanceof EntityMosquito
            || entity instanceof Fairy
            || entity instanceof Firefly
            || entity instanceof Ghost
            || entity instanceof GhostSkelly
            || entity instanceof Leon
            || entity instanceof LurkingTerror
            || entity instanceof Mantis
            || entity instanceof PitchBlack
            || entity instanceof PurplePower
            || entity instanceof Rotator
            || entity instanceof Spyro
            || entity instanceof Stinky
            || entity instanceof TerribleTerror
            || entity instanceof TheKing
            || entity instanceof ThePrince
            || entity instanceof ThePrinceAdult
            || entity instanceof ThePrinceTeen
            || entity instanceof ThePrincess
            || entity instanceof TheQueen
            || entity instanceof Vortex;
    }
}

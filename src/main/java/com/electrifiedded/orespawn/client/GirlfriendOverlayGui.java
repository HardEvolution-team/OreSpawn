package com.electrifiedded.orespawn.client;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GirlfriendOverlayGui {
    private static final ResourceLocation TEXTURE = new ResourceLocation("orespawndedsport", "textures/girlfriendgui.png");
    private final Minecraft mc;
    /** Target lookup scans nearby entities, so it runs once per client tick instead of every frame. */
    private long cachedTick = Long.MIN_VALUE;
    private EntityLivingBase cachedTarget;

    public GirlfriendOverlayGui(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        
        EntityPlayer player = mc.player;
        if (player == null || mc.gameSettings.hideGUI || mc.currentScreen != null || OreSpawn.GuiOverlayEnable == 0) {
            return;
        }
        
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        long tick = player.world.getTotalWorldTime();
        if (tick != cachedTick) {
            cachedTick = tick;
            cachedTarget = findTarget(player, 16.0D);
            if (cachedTarget == null) {
                cachedTarget = findBoss(player, 64.0D);
            }
        }
        EntityLivingBase target = cachedTarget;
        if (target != null && (target.isDead || target.world != player.world)) {
            cachedTarget = null;
            return;
        }
        if (target == null || !isDisplayed(target, player) || target.getMaxHealth() <= 0.0F) return;
        float health = Math.max(0.0F, Math.min(1.0F, target.getHealth() / target.getMaxHealth()));
        int barWidth = 182;
        int barHeight = 5;
        int x = width / 2 - barWidth / 2;
        int y = 25;
        String name = getDisplayName(target);
        mc.fontRenderer.drawStringWithShadow(name, width / 2.0F - mc.fontRenderer.getStringWidth(name) / 2.0F, y - 10, 0xFF3434);
        mc.getTextureManager().bindTexture(TEXTURE);
        mc.getTextureManager().getTexture(TEXTURE).setBlurMipmap(false, false);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, barWidth, barHeight, 256, 256);
        int filled = Math.round(health * barWidth);
        if (filled > 0) Gui.drawModalRectWithCustomSizedTexture(x, y, 0, barHeight, filled, barHeight, 256, 256);
    }

    private EntityLivingBase findBoss(EntityPlayer player, double distance) {
        EntityLivingBase result = null;
        double nearest = distance * distance;
        for (Entity entity : player.world.getEntitiesWithinAABBExcludingEntity(player,
            player.getEntityBoundingBox().grow(distance))) {
            if (!(entity instanceof TheKing) && !(entity instanceof TheQueen)) continue;
            double current = player.getDistanceSq(entity);
            if (current < nearest) {
                nearest = current;
                result = (EntityLivingBase) entity;
            }
        }
        return result;
    }

    private EntityLivingBase findTarget(EntityPlayer player, double distance) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY
            && mc.objectMouseOver.entityHit instanceof EntityLivingBase) return (EntityLivingBase) mc.objectMouseOver.entityHit;
        Vec3d start = player.getPositionEyes(1.0F);
        Vec3d end = start.add(player.getLook(1.0F).scale(distance));
        EntityLivingBase result = null;
        double nearest = distance;
        for (Entity entity : player.world.getEntitiesWithinAABBExcludingEntity(player,
            player.getEntityBoundingBox().expand(player.getLook(1.0F).x * distance,
                player.getLook(1.0F).y * distance, player.getLook(1.0F).z * distance).grow(1.0D))) {
            if (!(entity instanceof EntityLivingBase) || !entity.canBeCollidedWith()) continue;
            AxisAlignedBB box = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
            RayTraceResult hit = box.calculateIntercept(start, end);
            if (hit != null) {
                double current = start.distanceTo(hit.hitVec);
                if (current < nearest) { nearest = current; result = (EntityLivingBase) entity; }
            }
        }
        return result;
    }

    private boolean isDisplayed(EntityLivingBase entity, EntityPlayer player) {
        if (entity instanceof Girlfriend) return ((Girlfriend) entity).isOwner(player) && ((Girlfriend) entity).passenger == 0;
        if (entity instanceof Boyfriend) return ((Boyfriend) entity).isOwner(player) && ((Boyfriend) entity).passenger == 0;
        if (entity instanceof ThePrince) return ((ThePrince) entity).isOwner(player);
        if (entity instanceof ThePrincess) return ((ThePrincess) entity).isOwner(player);
        if (entity instanceof ThePrinceTeen) return ((ThePrinceTeen) entity).isOwner(player) && ((ThePrinceTeen) entity).getActivity() == 0;
        if (entity instanceof ThePrinceAdult) return ((ThePrinceAdult) entity).isOwner(player) && ((ThePrinceAdult) entity).getActivity() == 0;
        if (entity instanceof Dragon) return ((Dragon) entity).getActivity() == 0;
        if (entity instanceof Cephadrome) return ((Cephadrome) entity).getActivity() == 0;
        if (entity instanceof WormLarge) return !entity.noClip;
        if (entity instanceof Crab) return ((Crab) entity).getCrabScale() > 0.75F;
        return entity instanceof EmperorScorpion || entity instanceof Basilisk || entity instanceof Mothra
            || entity instanceof Spyro || entity instanceof Alien || entity instanceof WaterDragon
            || entity instanceof Kraken || entity instanceof TrooperBug || entity instanceof SpitBug
            || entity instanceof PitchBlack || entity instanceof Alosaurus || entity instanceof Nastysaurus
            || entity instanceof TRex || entity instanceof Kyuubi || entity instanceof Robot2
            || entity instanceof Robot4 || entity instanceof Triffid || entity instanceof Godzilla
            || entity instanceof Vortex || entity instanceof Irukandji || entity instanceof Mantis
            || entity instanceof HerculesBeetle || entity instanceof TheKing || entity instanceof TheQueen
            || entity instanceof SeaViper || entity instanceof SeaMonster || entity instanceof Molenoid
            || entity instanceof CaterKiller || entity instanceof Leon || entity instanceof Hammerhead
            || entity instanceof BandP || entity instanceof SpiderRobot || entity instanceof GiantRobot
            || entity instanceof AntRobot;
    }

    private String getDisplayName(EntityLivingBase entity) {
        if (entity instanceof TheKing) return I18n.format("entity.orespawndedsport.the_king.name");
        if (entity instanceof TheQueen) return I18n.format("entity.orespawndedsport.the_queen.name");
        if (entity.hasCustomName()) return entity.getCustomNameTag();
        if (entity instanceof Girlfriend) return "Girlfriend";
        if (entity instanceof Boyfriend) return "Boyfriend";
        if (entity instanceof ThePrince) return "The Toddler Prince";
        if (entity instanceof ThePrincess) return "The Toddler Princess";
        if (entity instanceof ThePrinceTeen) return "The Young Prince";
        if (entity instanceof ThePrinceAdult) return "The Young Adult Prince";
        if (entity instanceof Mothra) return "Mothra!";
        if (entity instanceof Alien) return "Alien!";
        if (entity instanceof WaterDragon) return "WaterDragon";
        if (entity instanceof WormLarge) return "Worm";
        if (entity instanceof TrooperBug) return "Jumpy Bug";
        if (entity instanceof PitchBlack) return "Nightmare";
        if (entity instanceof Godzilla) return "Mobzilla";
        if (entity instanceof Robot2) return "Robo-Pounder";
        if (entity instanceof Robot4) return "Robo-Warrior";
        if (entity instanceof BandP) return ((BandP) entity).getWhat() == 0 ? "Banker" : "Politician";
        if (entity instanceof SpiderRobot) return "Giant Robot Spider";
        if (entity instanceof GiantRobot) return "Jeffery";
        if (entity instanceof AntRobot) return "Giant Robot Red Ant";
        if (entity instanceof Crab) return "Very Large Crab";
        return entity.getDisplayName().getFormattedText();
    }
}

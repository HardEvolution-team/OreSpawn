package com.electrifiedded.orespawn.client;
import com.electrifiedded.orespawn.entity.*;
import com.electrifiedded.orespawn.OreSpawn;
import net.minecraft.util.math.BlockPos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
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
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
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
            // Keep showing the last boss we fought while it is still close, even when not aimed at.
            if (cachedTarget == null && trackedEntity != null && !trackedEntity.isDead
                && trackedEntity.world == player.world && player.getDistanceSq(trackedEntity) < 48.0D * 48.0D
                && isDisplayed(trackedEntity, player)) {
                cachedTarget = trackedEntity;
            }
        }
        EntityLivingBase target = cachedTarget;
        if (target != null && (target.isDead || target.world != player.world)) {
            cachedTarget = null;
            trackedEntity = null;
            return;
        }
        if (target == null || !isDisplayed(target, player) || target.getMaxHealth() <= 0.0F) return;
        float health = Math.max(0.0F, Math.min(1.0F, target.getHealth() / target.getMaxHealth()));
        if (target != trackedEntity) {
            trackedEntity = target;
            shownHealth = health;
            trailHealth = health;
            trailHoldUntil = 0L;
        }
        // Smooth bar: the main fill eases toward the real value, a pale "damage trail" lingers then drains.
        long now = Minecraft.getSystemTime();
        float dt = lastFrame == 0L ? 0.0F : Math.min(0.25F, (now - lastFrame) / 1000.0F);
        lastFrame = now;
        shownHealth += (health - shownHealth) * Math.min(1.0F, dt * 12.0F);
        if (health < trailHealth) {
            if (trailHoldUntil == 0L) trailHoldUntil = now + 600L;
            if (now > trailHoldUntil) trailHealth = Math.max(health, trailHealth - dt * 0.6F);
        } else {
            trailHealth = health;
            trailHoldUntil = 0L;
        }
        if (trailHealth <= health) trailHoldUntil = 0L;

        int barWidth = 182;
        int barHeight = 5;
        int x = width / 2 - barWidth / 2;
        int y = 25 + vanillaBossBarsOffset();
        String name = getDisplayName(target);
        String hp = String.format("%d / %d", (int) Math.ceil(target.getHealth()), (int) Math.ceil(target.getMaxHealth()));

        // Reset GL state left over from the hotbar/other overlays: tinted colour + lighting caused the dark bars.
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(TEXTURE);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, barWidth, barHeight, 256, 256);
        int trail = Math.round(trailHealth * barWidth);
        int filled = Math.round(shownHealth * barWidth);
        if (trail > filled) {
            GlStateManager.color(1.0F, 0.9F, 0.6F, 0.75F);
            Gui.drawModalRectWithCustomSizedTexture(x + filled, y, filled, barHeight, trail - filled, barHeight, 256, 256);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (filled > 0) {
            // Low health pulses so the final phase is readable.
            if (health < 0.25F) {
                float pulse = 0.75F + 0.25F * MathHelper.sin(now / 120.0F);
                GlStateManager.color(1.0F, pulse, pulse, 1.0F);
            }
            Gui.drawModalRectWithCustomSizedTexture(x, y, 0, barHeight, filled, barHeight, 256, 256);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        mc.fontRenderer.drawStringWithShadow(name, width / 2.0F - mc.fontRenderer.getStringWidth(name) / 2.0F, y - 10, 0xFF3434);
        GlStateManager.pushMatrix();
        GlStateManager.translate(width / 2.0F, y + barHeight + 2, 0.0F);
        GlStateManager.scale(0.75F, 0.75F, 1.0F);
        mc.fontRenderer.drawStringWithShadow(hp, -mc.fontRenderer.getStringWidth(hp) / 2.0F, 0, 0xFFFFFF);
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private EntityLivingBase trackedEntity;
    private float shownHealth = 1.0F;
    private float trailHealth = 1.0F;
    private long trailHoldUntil;
    private long lastFrame;
    private static java.lang.reflect.Field bossMapField;
    private static boolean bossMapFailed;

    /** Push our bar below vanilla boss bars (wither/dragon/other mods) instead of drawing on top of them. */
    private int vanillaBossBarsOffset() {
        if (bossMapFailed) return 0;
        try {
            if (bossMapField == null) {
                bossMapField = net.minecraftforge.fml.common.ObfuscationReflectionHelper.findField(
                    net.minecraft.client.gui.GuiBossOverlay.class, "field_184060_g");
            }
            java.util.Map<?, ?> map = (java.util.Map<?, ?>) bossMapField.get(mc.ingameGUI.getBossOverlay());
            return map == null ? 0 : Math.min(map.size(), 4) * 19;
        } catch (Throwable t) {
            bossMapFailed = true;
            return 0;
        }
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

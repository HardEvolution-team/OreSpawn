package com.electrifiedded.orespawn.client.render;

import com.electrifiedded.orespawn.OreSpawn;
import com.electrifiedded.orespawn.client.model.ModelBattleAxe;
import com.electrifiedded.orespawn.client.model.ModelBertha;
import com.electrifiedded.orespawn.client.model.ModelChainsaw;
import com.electrifiedded.orespawn.client.model.ModelHammy;
import com.electrifiedded.orespawn.client.model.ModelQueenBattleAxe;
import com.electrifiedded.orespawn.client.model.ModelSlice;
import com.electrifiedded.orespawn.client.model.ModelSquidZooka;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/** Reuses the original Techne weapon models and 1.7.10 equipped transforms. */
public final class LegacyWeaponRenderer extends TileEntityItemStackRenderer {
    public static final LegacyWeaponRenderer INSTANCE = new LegacyWeaponRenderer();

    private final ModelBertha bertha = new ModelBertha();
    private final ModelSlice slice = new ModelSlice();
    private final ModelBattleAxe battleAxe = new ModelBattleAxe();
    private final ModelQueenBattleAxe queenBattleAxe = new ModelQueenBattleAxe();
    private final ModelHammy hammy = new ModelHammy();
    private final ModelChainsaw chainsaw = new ModelChainsaw();
    private final ModelSquidZooka squidZooka = new ModelSquidZooka();
    private static final ThreadLocal<TransformType> TRANSFORM = new ThreadLocal<>();

    private LegacyWeaponRenderer() {}

    public static void setTransformType(TransformType type) {
        TRANSFORM.set(type);
    }

    @Override
    public void renderByItem(ItemStack stack, float partialTicks) {
        ModelBase model;
        ResourceLocation texture;
        Profile profile;
        float scale;
        if (stack.getItem() == OreSpawn.MyBertha) {
            model = bertha;
            texture = texture("berthatexture.png");
            scale = 0.25F;
            profile = Profile.SWORD;
        } else if (stack.getItem() == OreSpawn.MySlice) {
            model = slice;
            texture = texture("slicetexture.png");
            scale = 0.3F;
            profile = Profile.SWORD;
        } else if (stack.getItem() == OreSpawn.MyRoyal) {
            model = slice;
            texture = texture("royaltexture.png");
            scale = 0.35F;
            profile = Profile.SWORD;
        } else if (stack.getItem() == OreSpawn.MySquidZooka) {
            model = squidZooka;
            texture = texture("squidzookatexture.png");
            scale = 0.35F;
            profile = Profile.SQUID_ZOOKA;
        } else if (stack.getItem() == OreSpawn.MyHammy) {
            model = hammy;
            texture = texture("attitudeadjustertexture.png");
            scale = 0.15F;
            profile = Profile.HAMMY;
        } else if (stack.getItem() == OreSpawn.MyBattleAxe) {
            model = battleAxe;
            texture = texture("battleaxetexture.png");
            scale = 0.35F;
            profile = Profile.AXE;
        } else if (stack.getItem() == OreSpawn.MyChainsaw) {
            model = chainsaw;
            texture = texture("chainsawtexture.png");
            scale = 0.25F;
            profile = Profile.CHAINSAW;
        } else if (stack.getItem() == OreSpawn.MyQueenBattleAxe) {
            model = queenBattleAxe;
            texture = texture("queenbattleaxetexture.png");
            scale = 0.35F;
            profile = Profile.AXE;
        } else {
            return;
        }

        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        TransformType type = TRANSFORM.get();
        TRANSFORM.remove();
        boolean firstPerson = type == TransformType.FIRST_PERSON_RIGHT_HAND || type == TransformType.FIRST_PERSON_LEFT_HAND;
        boolean leftHand = type == TransformType.FIRST_PERSON_LEFT_HAND || type == TransformType.THIRD_PERSON_LEFT_HAND;
        setupLegacyEquippedTransform(firstPerson, leftHand);
        transform(profile, firstPerson, scale);
        render(model);
        GlStateManager.popMatrix();
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation("orespawndedsport", name);
    }

    /** Restores the item-space matrix that 1.7.10 applied before IItemRenderer. */
    private static void setupLegacyEquippedTransform(boolean firstPerson, boolean leftHand) {
        if (firstPerson) {
            float side = leftHand ? -1.0F : 1.0F;
            GlStateManager.translate(0.35F * side, -0.05F, 0.15F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            if (leftHand) {
                GlStateManager.translate(0.5F, 0.5F, 0.5F);
                GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(-0.2F, -0.5F, -0.4F);
            }
            GlStateManager.scale(0.6F, 0.6F, 0.6F);
            return;
        }

        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.translate(0.0F, 0.15625F, 0.0F);
        GlStateManager.scale(0.375F, 0.375F, 0.375F);
        GlStateManager.translate(0.0F, 0.0F, 0.2F);
        GlStateManager.rotate(leftHand ? 45.0F : -45.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
    }

    private static void transform(Profile profile, boolean firstPerson, float scale) {
        switch (profile) {
            case AXE:
                if (firstPerson) {
                    GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(190.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(15.0F, 0.0F, 0.0F, 1.0F);
                } else GlStateManager.rotate(180.0F, 1.0F, 0.25F, 0.0F);
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate(firstPerson ? -2.0F : 3.0F, firstPerson ? -4.0F : -8.0F, firstPerson ? -6.0F : -2.0F);
                return;
            case HAMMY:
                if (firstPerson) {
                    GlStateManager.rotate(70.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(190.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(25.0F, 0.0F, 0.0F, 1.0F);
                } else GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate(firstPerson ? -10.0F : 6.0F, firstPerson ? -13.0F : -20.0F, firstPerson ? -5.0F : -4.0F);
                return;
            case CHAINSAW:
                if (firstPerson) {
                    GlStateManager.rotate(150.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(100.0F, 1.0F, 0.0F, 0.0F);
                } else {
                    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
                }
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate(firstPerson ? -10.0F : -3.0F, firstPerson ? 1.0F : -3.0F, firstPerson ? -4.0F : -2.0F);
                return;
            case SQUID_ZOOKA:
                GlStateManager.rotate(firstPerson ? -30.0F : 30.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate(firstPerson ? 4.0F : 2.0F, firstPerson ? 2.0F : 8.0F, 2.0F);
                return;
            default:
                if (firstPerson) {
                    GlStateManager.rotate(190.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(25.0F, 0.0F, 0.0F, 1.0F);
                } else {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                }
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.translate(firstPerson ? 6.0F : -4.0F, firstPerson ? 3.0F : 2.0F, firstPerson ? -5.0F : -3.0F);
        }
    }

    private static void render(ModelBase model) {
        if (model instanceof ModelBertha) ((ModelBertha) model).render();
        else if (model instanceof ModelSlice) ((ModelSlice) model).render();
        else if (model instanceof ModelBattleAxe) ((ModelBattleAxe) model).render();
        else if (model instanceof ModelQueenBattleAxe) ((ModelQueenBattleAxe) model).render();
        else if (model instanceof ModelHammy) ((ModelHammy) model).render();
        else if (model instanceof ModelChainsaw) ((ModelChainsaw) model).render();
        else ((ModelSquidZooka) model).render();
    }

    private enum Profile { SWORD, AXE, HAMMY, CHAINSAW, SQUID_ZOOKA }
}

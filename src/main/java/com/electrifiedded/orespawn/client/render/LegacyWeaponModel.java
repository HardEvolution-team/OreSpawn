package com.electrifiedded.orespawn.client.render;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.List;

/** Uses the Techne model only in equipped views, matching the original item renderer. */
public final class LegacyWeaponModel implements IBakedModel {
    private final IBakedModel icon;
    private final IBakedModel builtIn;

    public LegacyWeaponModel(IBakedModel icon, IBakedModel builtIn) {
        this.icon = icon;
        this.builtIn = builtIn;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType type) {
        boolean equipped = type == TransformType.FIRST_PERSON_RIGHT_HAND
            || type == TransformType.FIRST_PERSON_LEFT_HAND
            || type == TransformType.THIRD_PERSON_RIGHT_HAND
            || type == TransformType.THIRD_PERSON_LEFT_HAND;
        if (equipped) {
            LegacyWeaponRenderer.setTransformType(type);
            return Pair.of(builtIn, identity());
        }
        return Pair.of(icon, identity());
    }

    private static Matrix4f identity() {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        return matrix;
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        return ImmutableList.of();
    }

    @Override public boolean isAmbientOcclusion() { return false; }
    @Override public boolean isGui3d() { return false; }
    @Override public boolean isBuiltInRenderer() { return false; }
    @Override public TextureAtlasSprite getParticleTexture() { return icon.getParticleTexture(); }
    @Override public net.minecraft.client.renderer.block.model.ItemCameraTransforms getItemCameraTransforms() { return icon.getItemCameraTransforms(); }
    @Override public ItemOverrideList getOverrides() { return icon.getOverrides(); }
}

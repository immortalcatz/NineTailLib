/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.model;

import codechicken.lib.texture.TextureUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public class SimpleBakedModel extends CachedBakedModel {

    private List<BakedQuad> quads;
    private TextureAtlasSprite particleTexture;
    private boolean useAmbientOcclusion;
    private ItemCameraTransforms cameraTransforms;
    private ItemOverrideList itemOverrides;

    public SimpleBakedModel(List<BakedQuad> quads){
        this(quads, TextureUtils.getMissingSprite(), true);
    }

    public SimpleBakedModel(List<BakedQuad> quads, TextureAtlasSprite particleTexture, boolean useAmbientOcclusion){
        this(quads, particleTexture, useAmbientOcclusion, ItemCameraTransforms.DEFAULT, ItemOverrideList.NONE);
    }

    public SimpleBakedModel(List<BakedQuad> quads, TextureAtlasSprite particleTexture, boolean useAmbientOcclusion, ItemCameraTransforms cameraTransforms, ItemOverrideList itemOverrides){
        this.quads = quads;
        this.particleTexture = particleTexture;
        this.useAmbientOcclusion = useAmbientOcclusion;
        this.cameraTransforms = cameraTransforms;
        this.itemOverrides = itemOverrides;
    }

    @Override
    public List<BakedQuad> getCachedQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return this.quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.useAmbientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.particleTexture;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.itemOverrides;
    }

}

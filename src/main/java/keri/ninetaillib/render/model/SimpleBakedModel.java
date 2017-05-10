package keri.ninetaillib.render.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public class SimpleBakedModel implements IBakedModel {

    private List<BakedQuad> quads;
    private boolean ambientOcclusion;
    private TextureAtlasSprite particleTexture;

    public SimpleBakedModel(List<BakedQuad> quads){
        this.quads = quads;
        this.ambientOcclusion = false;
        this.particleTexture = null;
    }

    public SimpleBakedModel(List<BakedQuad> quads, boolean ambientOcclusion, TextureAtlasSprite particleTexture){
        this.quads = quads;
        this.ambientOcclusion = ambientOcclusion;
        this.particleTexture = particleTexture;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return this.quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return this.ambientOcclusion;
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
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

}

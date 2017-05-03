package keri.ninetaillib.render.model;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class CustomBlockRenderer implements IBakedModel {

    private IBlockRenderingHandler blockRenderer;
    private Map<String, List<BakedQuad>> quadCache = Maps.newHashMap();
    private LoadingCache<String, List<BakedQuad>> cache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<BakedQuad>>() {
        @Override
        public List<BakedQuad> load(String key) throws Exception {
            return quadCache.get(key);
        }
    });

    public CustomBlockRenderer(IBlockRenderingHandler renderer){
        this.blockRenderer = renderer;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand){
        if(side != null){
            if(this.cache.getIfPresent(this.getCacheKey(state)) == null){
                BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
                BakingVertexBuffer buffer = BakingVertexBuffer.create();
                buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                CCRenderState renderState = CCRenderState.instance();
                renderState.reset();
                renderState.bind(buffer);
                this.blockRenderer.renderBlock(renderState, state, side, layer, rand);
                buffer.finishDrawing();
                this.cache.put(this.getCacheKey(state), buffer.bake());
            }
            else{
                return this.cache.getUnchecked(this.getCacheKey(state));
            }
        }

        return Lists.newArrayList();
    }

    private String getCacheKey(IBlockState state){
        StringBuilder builder = new StringBuilder();
        builder.append(state.getBlock().getRegistryName().getResourceDomain());
        builder.append('.');
        builder.append(state.getBlock().getRegistryName().getResourcePath());
        builder.append(':');
        builder.append(state.getBlock().getMetaFromState(state));

        if(state instanceof IExtendedBlockState){
            if(this.blockRenderer.getBlockKey((IExtendedBlockState)state) != null){
                builder.append(':');
                builder.append(this.blockRenderer.getBlockKey((IExtendedBlockState)state));
            }
        }

        return builder.toString();
    }

    @Override
    public boolean isAmbientOcclusion(){
        return this.blockRenderer.ambientOcclusion();
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
    public TextureAtlasSprite getParticleTexture(){
        return this.blockRenderer.getParticleTexture();
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

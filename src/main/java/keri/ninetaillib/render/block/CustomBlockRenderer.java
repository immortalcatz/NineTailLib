package keri.ninetaillib.render.block;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import keri.ninetaillib.render.util.QuadRotator;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconBlock;
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
    private Map<String, Map<BlockRenderLayer, List<BakedQuad>>> quadCache = Maps.newHashMap();
    private IBlockState blockState;

    public CustomBlockRenderer(IBlockRenderingHandler renderer){
        this.blockRenderer = renderer;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if(side != null){
            if(!(this.quadCache.containsKey(this.getCacheKey(state)))){
                Map<BlockRenderLayer, List<BakedQuad>> map = Maps.newHashMap();

                for(BlockRenderLayer layer : BlockRenderLayer.values()){
                    BakingVertexBuffer buffer = BakingVertexBuffer.create();
                    buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                    CCRenderState renderState = CCRenderState.instance();
                    renderState.reset();
                    renderState.bind(buffer);
                    this.blockRenderer.renderBlock(renderState, state, side, layer, rand);
                    buffer.finishDrawing();
                    List<BakedQuad> quads = Lists.newArrayList();
                    quads.addAll(buffer.bake());

                    if(this.blockRenderer.getBakedQuads(state, side, rand) != null){
                        quads.addAll(this.blockRenderer.getBakedQuads(state, side, rand));
                    }

                    QuadRotator quadRotator = new QuadRotator();
                    EnumFacing newForward = EnumFacing.NORTH;
                    EnumFacing newUp = EnumFacing.UP;

                    switch(this.blockRenderer.getRotation(state)){
                        case DOWN:
                            newForward = EnumFacing.NORTH;
                            newUp = EnumFacing.DOWN;
                            break;
                        case UP:
                            newForward = EnumFacing.NORTH;
                            newUp = EnumFacing.UP;
                            break;
                        case NORTH:
                            newForward = EnumFacing.NORTH;
                            newUp = EnumFacing.UP;
                            break;
                        case EAST:
                            newForward = EnumFacing.EAST;
                            newUp = EnumFacing.UP;
                            break;
                        case SOUTH:
                            newForward = EnumFacing.SOUTH;
                            newUp = EnumFacing.UP;
                            break;
                        case WEST:
                            newForward = EnumFacing.WEST;
                            newUp = EnumFacing.UP;
                            break;
                    }

                    map.put(layer, quadRotator.rotateQuads(quads, newForward, newUp));
                }

                this.quadCache.put(this.getCacheKey(state), map);
            }
            else{
                Map<BlockRenderLayer, List<BakedQuad>> map = this.quadCache.get(this.getCacheKey(state));

                for(Map.Entry<BlockRenderLayer, List<BakedQuad>> entry : map.entrySet()){
                    if(entry.getKey() == MinecraftForgeClient.getRenderLayer()){
                        this.blockState = state;
                        return entry.getValue();
                    }
                }
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
    public boolean isAmbientOcclusion() {
        return true;
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
        return this.blockState != null ? this.blockRenderer.getParticleTexture((IIconBlock)this.blockState.getBlock(), this.blockState.getBlock().getMetaFromState(this.blockState)): null;
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

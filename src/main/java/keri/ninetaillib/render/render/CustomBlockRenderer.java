package keri.ninetaillib.render.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.ICCBlockRenderer;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomBlockRenderer implements ICCBlockRenderer {

    private IBlockRenderingHandler renderingHandler;

    public CustomBlockRenderer(IBlockRenderingHandler handler){
        this.renderingHandler = handler;
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, VertexBuffer buffer){
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.pullLightmap();
        this.renderingHandler.renderBlockDamage(renderState, world, pos, sprite);
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.lightMatrix.locate(world, pos);
        renderState.pullLightmap();
        return this.renderingHandler.renderBlock(renderState, world, pos, layer);
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness){}

    @Override
    public void registerTextures(TextureMap map){}

}

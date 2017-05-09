package keri.ninetaillib.render.model;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.registry.IItemRenderingHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CustomItemRenderer implements IItemRenderer, IPerspectiveAwareModel, IResourceManagerReloadListener {

    /**
     * MORE TODO IN HERE YA' LAZY FUCKTURD
     */

    private IBlockRenderingHandler blockRenderer;
    private IItemRenderingHandler itemRenderer;

    public CustomItemRenderer(IBlockRenderingHandler renderer){
        this.blockRenderer = renderer;
    }

    public CustomItemRenderer(IItemRenderingHandler renderer){
        this.itemRenderer = renderer;
    }

    @Override
    public void renderItem(ItemStack stack){
        /**
        if(this.itemRenderer != null){
            if(this.itemRenderer.useRenderCache()){
                if(!quadCache.isPresent(this.getCacheKey(stack))){
                    BakingVertexBuffer buffer = BakingVertexBuffer.create();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                    CCRenderState renderState = CCRenderState.instance();
                    renderState.reset();
                    renderState.bind(buffer);
                    List<BakedQuad> quads = Lists.newArrayList();

                    if(this.itemRenderer.renderItem(renderState, stack, this.random.nextLong()) != null){
                        quads.addAll(this.itemRenderer.renderItem(renderState, stack, this.random.nextLong()));
                    }
                    else{
                        this.itemRenderer.renderItem(renderState, stack, this.random.nextLong());
                    }

                    buffer.finishDrawing();
                    quads.addAll(buffer.bake());
                    quadCache.put(this.getCacheKey(stack), quads);
                }
                else{
                    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
                    SimpleBakedModel model = new SimpleBakedModel(quadCache.get(this.getCacheKey(stack)));
                    GlStateManager.pushMatrix();
                    GlStateManager.pushAttrib();
                    GlStateManager.enableBlend();
                    GlStateManager.translate(0.5D, 0.5D, 0.5D);
                    if(this.itemRenderer.useStandardItemLighting()){ RenderHelper.enableStandardItemLighting(); }
                    renderItem.renderItem(stack, model);
                    if(this.itemRenderer.useStandardItemLighting()){ RenderHelper.disableStandardItemLighting(); }
                    GlStateManager.disableBlend();
                    GlStateManager.popAttrib();
                    GlStateManager.popMatrix();
                }
            }
            else{
                GlStateManager.pushMatrix();
                GlStateManager.pushAttrib();
                GlStateManager.enableBlend();
                if(this.itemRenderer.useStandardItemLighting()){ RenderHelper.enableStandardItemLighting(); }
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                CCRenderState renderState = CCRenderState.instance();
                renderState.reset();
                renderState.bind(buffer);
                this.itemRenderer.renderItem(renderState, stack, this.random.nextLong());
                Tessellator.getInstance().draw();
                if(this.itemRenderer.useStandardItemLighting()){ RenderHelper.disableStandardItemLighting(); }
                GlStateManager.disableBlend();
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            }
        }
        else{
            if(this.blockRenderer.hasDynamicItemRendering()){
                GlStateManager.pushMatrix();
                GlStateManager.pushAttrib();
                RenderHelper.enableStandardItemLighting();
                VertexBuffer buffer = Tessellator.getInstance().getBuffer();
                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                CCRenderState renderState = CCRenderState.instance();
                renderState.reset();
                renderState.bind(buffer);
                GlStateManager.enableBlend();
                this.blockRenderer.renderItem(renderState, stack, this.random.nextLong());
                GlStateManager.disableBlend();
                Tessellator.getInstance().draw();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            }
            else{
                if(!quadCache.isPresent(this.getCacheKey(stack))){
                    BakingVertexBuffer buffer = BakingVertexBuffer.create();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                    CCRenderState renderState = CCRenderState.instance();
                    renderState.reset();
                    renderState.bind(buffer);
                    List<BakedQuad> quads = Lists.newArrayList();

                    if(this.blockRenderer.renderItem(renderState, stack, this.random.nextLong()) != null){
                        quads.addAll(this.blockRenderer.renderItem(renderState, stack, this.random.nextLong()));
                    }
                    else{
                        this.blockRenderer.renderItem(renderState, stack, this.random.nextLong());
                    }

                    buffer.finishDrawing();
                    quads.addAll(buffer.bake());
                    quadCache.put(this.getCacheKey(stack), quads);
                }
                else{
                    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
                    SimpleBakedModel model = new SimpleBakedModel(quadCache.get(this.getCacheKey(stack)));
                    GlStateManager.pushMatrix();
                    GlStateManager.pushAttrib();
                    GlStateManager.enableBlend();
                    GlStateManager.translate(0.5D, 0.5D, 0.5D);
                    RenderHelper.enableStandardItemLighting();
                    renderItem.renderItem(stack, model);
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.popAttrib();
                    GlStateManager.popMatrix();
                }
            }
        }
         */
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        if(this.itemRenderer != null){
            return this.itemRenderer.handlePerspective(this, cameraTransformType);
        }
        else{
            return MapWrapper.handlePerspective(this, TransformUtils.DEFAULT_BLOCK.getTransforms(), cameraTransformType);
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        //quadCache.clear();
    }

    /**
    private String getCacheKey(ItemStack stack){
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getItem().getRegistryName().getResourceDomain());
        builder.append('.');
        builder.append(stack.getItem().getRegistryName().getResourcePath());
        builder.append(':');
        builder.append(stack.getMetadata());

        if(this.blockRenderer != null && this.blockRenderer.getItemKey(stack) != null){
            builder.append(':');
            builder.append(this.blockRenderer.getItemKey(stack));
        }

        if(this.itemRenderer != null && this.itemRenderer.getItemKey(stack) != null){
            builder.append(':');
            builder.append(this.itemRenderer.getItemKey(stack));
        }

        return builder.toString();
    }
     */

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return Lists.newArrayList();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
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

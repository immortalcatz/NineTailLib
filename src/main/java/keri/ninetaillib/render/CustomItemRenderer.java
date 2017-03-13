package keri.ninetaillib.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class CustomItemRenderer implements IItemRenderer, IPerspectiveAwareModel {

    private IBlockRenderingHandler blockRenderer;
    private IItemRenderingHandler itemRenderer;
    private Random random = new Random();
    private Map<String, SimpleBakedModel> modelCache = Maps.newHashMap();

    public CustomItemRenderer(IBlockRenderingHandler renderer){
        this.blockRenderer = renderer;
    }

    public CustomItemRenderer(IItemRenderingHandler renderer){
        this.itemRenderer = renderer;
    }

    @Override
    public void renderItem(ItemStack stack){
        if(this.itemRenderer != null){
            if(this.itemRenderer.useRenderCache()){
                if(!(this.modelCache.containsKey(this.getCacheKey(stack)))){
                    BakingVertexBuffer buffer = BakingVertexBuffer.create();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                    CCRenderState renderState = CCRenderState.instance();
                    renderState.reset();
                    renderState.bind(buffer);
                    this.itemRenderer.renderItem(renderState, stack, this.random.nextLong());
                    buffer.finishDrawing();
                    List<BakedQuad> quads = Lists.newArrayList();
                    quads.addAll(buffer.bake());

                    if(this.itemRenderer instanceof IItemQuadProvider){
                        IItemQuadProvider provider = (IItemQuadProvider)this.itemRenderer;
                        quads.addAll(provider.getQuads(stack, this.random.nextLong()));
                    }

                    SimpleBakedModel model = new SimpleBakedModel(quads);
                    this.modelCache.put(this.getCacheKey(stack), model);
                }
                else{
                    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
                    SimpleBakedModel model = this.modelCache.get(this.getCacheKey(stack));
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
                if(!(this.modelCache.containsKey(this.getCacheKey(stack)))){
                    BakingVertexBuffer buffer = BakingVertexBuffer.create();
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
                    CCRenderState renderState = CCRenderState.instance();
                    renderState.reset();
                    renderState.bind(buffer);
                    this.blockRenderer.renderItem(renderState, stack, this.random.nextLong());
                    buffer.finishDrawing();
                    List<BakedQuad> quads = Lists.newArrayList();
                    quads.addAll(buffer.bake());

                    if(this.itemRenderer instanceof IItemQuadProvider){
                        IItemQuadProvider provider = (IItemQuadProvider)this.itemRenderer;
                        quads.addAll(provider.getQuads(stack, this.random.nextLong()));
                    }

                    SimpleBakedModel model = new SimpleBakedModel(quads);
                    this.modelCache.put(this.getCacheKey(stack), model);
                }
                else{
                    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
                    SimpleBakedModel model = this.modelCache.get(this.getCacheKey(stack));
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

    private String getCacheKey(ItemStack stack){
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getItem().getRegistryName().getResourceDomain());
        builder.append('.');
        builder.append(stack.getItem().getRegistryName().getResourcePath());
        builder.append(':');
        builder.append(stack.getMetadata());

        if(this.blockRenderer != null && this.blockRenderer instanceof IItemKeyProvider){
            IItemKeyProvider provider = (IItemKeyProvider)this.blockRenderer;
            builder.append(':');
            builder.append(provider.getExtendedItemKey(stack));
        }
        if(this.itemRenderer != null && this.itemRenderer instanceof IItemKeyProvider){
            IItemKeyProvider provider = (IItemKeyProvider)this.itemRenderer;
            builder.append(':');
            builder.append(provider.getExtendedItemKey(stack));
        }

        return builder.toString();
    }

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

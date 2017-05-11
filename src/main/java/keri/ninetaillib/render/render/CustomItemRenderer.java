package keri.ninetaillib.render.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.render.registry.IItemRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
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

@SideOnly(Side.CLIENT)
public class CustomItemRenderer implements IItemRenderer, IPerspectiveAwareModel {

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
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        renderState.pullLightmap();

        if(this.blockRenderer != null){
            if(this.blockRenderer.useDefaultLighting()){
                RenderHelper.enableStandardItemLighting();
            }

            this.blockRenderer.renderItem(renderState, stack);
        }
        else{
            if(this.itemRenderer.useDefaultLighting()){
                RenderHelper.enableStandardItemLighting();
            }

            this.itemRenderer.renderItem(renderState, stack);
        }

        Tessellator.getInstance().draw();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
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

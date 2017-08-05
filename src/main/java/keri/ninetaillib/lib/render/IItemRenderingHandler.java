/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.render.CCModelState;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public interface IItemRenderingHandler {

    void renderItem(ItemStack stack, VertexBuffer buffer, ItemCameraTransforms.TransformType transformType);

    default boolean useDefaultItemLighting(){ return false; };

    default CCModelState getTransforms(){ return TransformUtils.DEFAULT_ITEM; };

    EnumItemRenderType getRenderType();

    default CCRenderState prepareInventoryRender(VertexBuffer buffer, boolean lighting, boolean lightmap){
        Tessellator.getInstance().draw();
        GlStateManager.pushMatrix();
        if(lighting) GlStateManager.enableLighting();
        buffer.begin(GL11.GL_QUADS, lightmap ? RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM) : DefaultVertexFormats.ITEM);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        return renderState;
    }

    default void postInventoryRender(VertexBuffer buffer){
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    default void renderQuads(ItemStack stack, List<BakedQuad> quads) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5D, 0.5D, 0.5D);
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.renderItem(stack, new BakedModelAdapter(quads));
        GlStateManager.popMatrix();
    }

    public class BakedModelAdapter implements IBakedModel {

        private List<BakedQuad> quads;

        public BakedModelAdapter(List<BakedQuad> quads){
            this.quads = quads;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return this.quads;
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
            return false;
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

}

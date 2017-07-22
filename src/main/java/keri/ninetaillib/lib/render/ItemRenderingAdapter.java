/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.texture.TextureUtils;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
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
public class ItemRenderingAdapter implements IItemRenderer, IPerspectiveAwareModel {

    private IItemRenderingHandler handler;

    public ItemRenderingAdapter(IItemRenderingHandler handler){
        this.handler = handler;
    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType){
        GlStateManager.pushMatrix();
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        this.handler.renderItem(stack, buffer, transformType);
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
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
        return TextureUtils.getMissingSprite();
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

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return MapWrapper.handlePerspective(this, this.handler.getTransforms().getTransforms(), cameraTransformType);
    }

}

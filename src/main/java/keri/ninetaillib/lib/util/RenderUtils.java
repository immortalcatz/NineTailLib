/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderUtils {

    private static final VertexFormat itemFormatWithLightMap = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

    public static VertexFormat getFormatWithLightMap(VertexFormat format) {
        if (FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled) {
            return format;
        }

        VertexFormat result;

        if (format == DefaultVertexFormats.BLOCK) {
            result = DefaultVertexFormats.BLOCK;
        }
        else if (format == DefaultVertexFormats.ITEM) {
            result = itemFormatWithLightMap;
        }
        else if (!format.hasUvOffset(1)) {
            result = new VertexFormat(format);
            result.addElement(DefaultVertexFormats.TEX_2S);
        }
        else {
            result = format;
        }

        return result;
    }

    public static boolean renderQuads(VertexBuffer buffer, IBlockAccess world, BlockPos pos, List<BakedQuad> quads){
        boolean useAo = Minecraft.isAmbientOcclusionEnabled();
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        BakedModelAdapter model = new BakedModelAdapter(quads);
        BlockModelRenderer bmr = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer();
        long random = MathHelper.getPositionRandom(new Vec3i(pos.getX(), pos.getY(), pos.getZ()));

        if(useAo){
            return bmr.renderModelSmooth(world, model, state, pos, buffer, true, random);
        }
        else{
            return bmr.renderModelFlat(world, model, state, pos, buffer, true, random);
        }
    }

    private static class BakedModelAdapter implements IBakedModel {

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
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return ItemOverrideList.NONE;
        }

    }

}

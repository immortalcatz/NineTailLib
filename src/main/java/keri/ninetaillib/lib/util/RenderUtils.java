/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
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
import org.lwjgl.opengl.GL11;

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

    public static void renderModel(CCModel[] parts, VertexBuffer buffer, TextureAtlasSprite texture, int color){
        renderModel(parts, buffer, BlockPos.ORIGIN, texture, color);
    }

    public static void renderModel(CCModel model, VertexBuffer buffer, EnumFacing side, TextureAtlasSprite texture, int color){
        renderModel(model, buffer, BlockPos.ORIGIN, side, texture, color);
    }

    public static void renderModel(CCModel[] parts, VertexBuffer buffer, BlockPos pos, TextureAtlasSprite texture, int color){
        CCRenderState rs = CCRenderState.instance();
        rs.reset();
        rs.bind(buffer);

        for(int i = 0; i < parts.length; i++){
            CCModel m = parts[i].copy();
            m.apply(new Translation(Vector3.fromBlockPos(pos)));
            m.setColour(color);
            m.render(rs, new IconTransformation(texture));
        }
    }

    public static void renderModel(CCModel model, VertexBuffer buffer, BlockPos pos, EnumFacing side, TextureAtlasSprite texture, int color){
        CCModel m = model.copy();
        int s = 4 * side.getIndex();
        int e = 4 + (4 * side.getIndex());
        CCRenderState rs = CCRenderState.instance();
        rs.reset();
        rs.bind(buffer);
        m.setColour(color);
        m.apply(new Translation(Vector3.fromBlockPos(pos)));
        m.render(rs, s, e, new IconTransformation(texture));
    }

    public static MipmapFilterData disableMipmap(){
        int minFilter = GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER);
        int magFilter = GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        MipmapFilterData data = new MipmapFilterData();
        data.minFilter = minFilter;
        data.magFilter = magFilter;
        return data;
    }

    public static void enableMipmap(MipmapFilterData data){
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, data.minFilter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, data.magFilter);
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
        @SuppressWarnings("deprecation")
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return ItemOverrideList.NONE;
        }

    }

    public static class MipmapFilterData {

        public int minFilter;
        public int magFilter;

    }

}

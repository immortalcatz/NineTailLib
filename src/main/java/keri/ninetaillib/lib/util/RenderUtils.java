/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import keri.ninetaillib.lib.model.SimpleBakedModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        BlockModelRenderer bmr = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer();
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        boolean useAmbientOcclusion = Minecraft.isAmbientOcclusionEnabled();
        SimpleBakedModel model = new SimpleBakedModel(quads);
        long random = MathHelper.getPositionRandom(new Vec3i(pos.getX(), pos.getY(), pos.getZ()));

        if(useAmbientOcclusion){
            return bmr.renderModelSmooth(world, model, state, pos, buffer, true, random);
        }
        else{
            return bmr.renderModelFlat(world, model, state, pos, buffer, true, random);
        }
    }

}

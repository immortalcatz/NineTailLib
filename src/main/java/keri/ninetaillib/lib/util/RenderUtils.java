/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderUtils {

    private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

    public static VertexFormat getFormatWithLightMap(VertexFormat format) {
        if (FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled) {
            return format;
        }

        VertexFormat result;

        if (format == DefaultVertexFormats.BLOCK) {
            result = DefaultVertexFormats.BLOCK;
        }
        else if (format == DefaultVertexFormats.ITEM) {
            result = ITEM_FORMAT_WITH_LIGHTMAP;
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

    public static class MipmapFilterData {

        public int minFilter;
        public int magFilter;

    }

}

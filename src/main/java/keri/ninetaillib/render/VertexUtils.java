package keri.ninetaillib.render;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VertexUtils {

    private static final VertexFormat itemFormatWithLightMap = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

    public static VertexFormat getFormatWithLightMap(VertexFormat format){
        if(FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled){
            return format;
        }

        VertexFormat result;

        if(format == DefaultVertexFormats.BLOCK){
            result = DefaultVertexFormats.BLOCK;
        }
        else if(format == DefaultVertexFormats.ITEM){
            result = itemFormatWithLightMap;
        }
        else if(!format.hasUvOffset(1)){
            result = new VertexFormat( format );
            result.addElement(DefaultVertexFormats.TEX_2S);
        }
        else{
            result = format;
        }

        return result;
    }

}

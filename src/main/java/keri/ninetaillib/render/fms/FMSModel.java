package keri.ninetaillib.render.fms;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.uv.MultiIconTransformation;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class FMSModel {

    private List<ModelPartData> modelPartData;

    public FMSModel(List<ModelPartData> modelPartData){
        this.modelPartData = modelPartData;
    }

    public CCModel[] getModel(){
        CCModel[] model = new CCModel[this.modelPartData.size()];

        for(int i = 0; i < this.modelPartData.size(); i++){
            model[i] = CCModel.quadModel(24).generateBlock(0, this.modelPartData.get(i).getBounds()).computeNormals();
        }

        return model;
    }

    public TextureAtlasSprite[][] getTexture(){
        TextureAtlasSprite[][] texture = new TextureAtlasSprite[this.modelPartData.size()][6];

        for(int i = 0; i < this.modelPartData.size(); i++){
            ModelPartData data = this.modelPartData.get(i);

            if(data.getHasSpecialTexture()){
                texture[i] = data.getTextureSpecial();
            }
            else{
                texture[i] = this.getTexture(data.getTexture());
            }
        }

        return texture;
    }

    public List<BakedQuad> getQuads(VertexFormat format){
        BakingVertexBuffer buffer = BakingVertexBuffer.create();
        buffer.begin(GL11.GL_QUADS, format);
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);

        for(int i = 0; i < this.modelPartData.size(); i++){
            ModelPartData data = this.modelPartData.get(i);
            CCModel modelPart = CCModel.quadModel(24).generateBlock(0, data.getBounds()).computeNormals();

            for(int j = 0; j < data.getTransformations().size(); j++){
                modelPart.apply(data.getTransformations().get(j));
            }

            for(int j = 0; j < data.getUVTransformations().size(); j++){
                modelPart.apply(data.getUVTransformations().get(j));
            }

            modelPart.setColour(data.getColor());

            if(data.getHasBrightnessOverride()){
                renderState.brightness = data.getBrightness();
            }

            TextureAtlasSprite[] texture = null;

            if(data.getHasSpecialTexture()){
                texture = data.getTextureSpecial();
            }
            else{
                texture = this.getTexture(data.getTexture());
            }

            modelPart.render(renderState, new MultiIconTransformation(texture));
        }

        buffer.finishDrawing();
        return buffer.bake();
    }

    private TextureAtlasSprite[] getTexture(ResourceLocation[] paths){
        TextureAtlasSprite[] texture = new TextureAtlasSprite[paths.length];

        for(int i = 0; i < paths.length; i++){
            texture[i] = TextureUtils.getTexture(paths[i]);
        }

        return texture;
    }

}

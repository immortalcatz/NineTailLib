/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

import codechicken.lib.colour.Colour;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Maps;
import keri.ninetaillib.lib.render.CuboidModel;
import keri.ninetaillib.lib.render.ModelPart;
import keri.ninetaillib.lib.render.VertexPosition;
import keri.ninetaillib.lib.util.ICopyable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class JsonModel implements ICopyable<JsonModel> {

    private Map<String, ModelPartData> partData = Maps.newHashMap();

    public JsonModel addModelPartData(ModelPartData partData){
        this.partData.put(partData.getName(), partData);
        return this;
    }

    public JsonModel setBrightnessOverride(int brightness, String name){
        ModelPartData partData = this.partData.get(name);

        if(partData != null){
            partData.setBrightnessOverride(brightness);
        }

        return this;
    }

    public JsonModel retexture(ResourceLocation texture, String name){
        ModelPartData partData = this.partData.get(name);

        if(partData != null){
            partData.setTexture(texture);
        }

        return this;
    }

    public JsonModel retexture(ResourceLocation texture, String name, EnumFacing side){
        ModelPartData partData = this.partData.get(name);

        if(partData != null){
            partData.setTexture(texture, side);
        }

        return this;
    }

    public JsonModel recolor(Colour color, String name){
        ModelPartData partData = this.partData.get(name);

        if(partData != null){
            partData.setColor(color);
        }

        return this;
    }

    public JsonModel recolor(Colour color, String name, EnumFacing side){
        ModelPartData partData = this.partData.get(name);

        if(partData != null){
            partData.setColor(color, side);
        }

        return this;
    }

    public JsonModel recolor(Colour color, String name, EnumFacing side, VertexPosition vertexPosition){
        ModelPartData partData = this.partData.get(name);

        if(partData != null){
            partData.setColor(color, side, vertexPosition);
        }

        return this;
    }

    public CuboidModel generateModel(){
        CuboidModel model = new CuboidModel();

        for(Map.Entry<String, ModelPartData> entry : this.partData.entrySet()){
            ModelPartData partData = entry.getValue();
            ModelPart modelPart = new ModelPart();
            modelPart.setBounds(partData.getBounds());

            if(partData.getHasBrightnessOverride()){
                modelPart.setBrightnessOverride(partData.getBrightness());
            }

            for(EnumFacing side : EnumFacing.VALUES){
                modelPart.setTexture(TextureUtils.getTexture(partData.getTexture()[side.getIndex()]), side);

                for(VertexPosition vertexPosition : VertexPosition.VALUES){
                    modelPart.setColor(partData.getColor()[side.getIndex()][vertexPosition.getVertexIndex()], side, vertexPosition);
                }
            }

            for(Transformation transformation : partData.getTransformations()){
                modelPart.addTransformation(transformation);
            }

            for(UVTransformation transformation : partData.getUVTransformations()){
                modelPart.addUVTransformation(transformation);
            }

            model.addModelPart(modelPart);
        }

        return model;
    }

    @Override
    public JsonModel copy() {
        JsonModel data = new JsonModel();
        data.partData = this.partData;
        return data;
    }

}

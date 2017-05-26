/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.CuboidModel;
import keri.ninetaillib.lib.render.ModelPart;
import keri.ninetaillib.lib.render.VertexPosition;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ModelData {

    private List<ModelPartData> partData = Lists.newArrayList();

    public ModelData addModelPartData(ModelPartData partData){
        this.partData.add(partData);
        return this;
    }

    public CuboidModel generateModel(){
        CuboidModel model = new CuboidModel();

        for(ModelPartData partData : this.partData){
            ModelPart modelPart = new ModelPart();
            modelPart.setBounds(partData.getBounds());

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
        }

        return model;
    }

}

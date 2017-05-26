/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.Copyable;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.VertexPosition;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ModelPartData implements Copyable<ModelPartData> {

    private Cuboid6 bounds = new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D);
    private ResourceLocation[] texture = new ResourceLocation[6];
    private List<Transformation> transformations = Lists.newArrayList();
    private List<UVTransformation> uvTransformations = Lists.newArrayList();
    private Colour[][] color = new Colour[6][4];

    public ModelPartData(){
        Arrays.fill(this.texture, new ResourceLocation("minecraft", "blocks/stone"));
        Colour[] color = new Colour[6];
        Arrays.fill(color, new ColourRGBA(255, 255, 255, 255));
        Arrays.fill(this.color, color);
    }

    public ModelPartData setBounds(Cuboid6 bounds){
        this.bounds = bounds;
        return this;
    }

    public ModelPartData setTexture(ResourceLocation texture){
        for(EnumFacing side : EnumFacing.VALUES){
            this.texture[side.getIndex()] = texture;
        }

        return this;
    }

    public ModelPartData setTexture(ResourceLocation texture, EnumFacing side){
        this.texture[side.getIndex()] = texture;
        return this;
    }

    public ModelPartData addTransformation(Transformation transformation){
        this.transformations.add(transformation);
        return this;
    }

    public ModelPartData addUVTransformation(UVTransformation transformation){
        this.uvTransformations.add(transformation);
        return this;
    }

    public ModelPartData setColor(Colour color){
        for(EnumFacing side : EnumFacing.VALUES){
            for(VertexPosition vertexPosition : VertexPosition.VALUES){
                this.color[side.getIndex()][vertexPosition.getVertexIndex()] = color;
            }
        }

        return this;
    }

    public ModelPartData setColor(Colour color, EnumFacing side){
        for(VertexPosition vertexPosition : VertexPosition.VALUES){
            this.color[side.getIndex()][vertexPosition.getVertexIndex()] = color;
        }

        return this;
    }

    public ModelPartData setColor(Colour color, EnumFacing side, VertexPosition vertexPosition){
        this.color[side.getIndex()][vertexPosition.getVertexIndex()] = color;
        return this;
    }

    @Override
    public ModelPartData copy() {
        ModelPartData data = new ModelPartData();
        data.bounds = this.bounds;
        data.texture = this.texture;
        data.transformations = this.transformations;
        data.uvTransformations = this.uvTransformations;
        data.color = this.color;
        return data;
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.util.ICopyable;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.ninetaillib.lib.util.VectorUtils;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ModelPart implements ICopyable<ModelPart> {

    private Cuboid6 bounds = new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D);
    private TextureAtlasSprite[] texture = new TextureAtlasSprite[6];
    private List<Transformation> transformations = Lists.newArrayList();
    private List<UVTransformation> uvTransformations = Lists.newArrayList();
    private Colour[][] color = new Colour[6][4];
    private boolean hasBrightnessOverride = false;
    private int brightness = 0;

    public ModelPart(){
        Colour[] color = new Colour[4];
        Arrays.fill(color, new ColourRGBA(255, 255, 255, 255));
        Arrays.fill(this.color, color);
        Arrays.fill(this.texture, TextureUtils.getMissingSprite());
    }

    public ModelPart setBounds(Cuboid6 bounds){
        this.bounds = bounds;
        return this;
    }

    public ModelPart setTexture(TextureAtlasSprite texture){
        this.texture = new TextureAtlasSprite[]{texture, texture, texture, texture, texture, texture};
        return this;
    }

    public ModelPart setTexture(TextureAtlasSprite texture, EnumFacing side){
        this.texture[side.getIndex()] = texture;
        return this;
    }

    public ModelPart addTransformation(Transformation transformation){
        this.transformations.add(transformation);
        return this;
    }

    public ModelPart addUVTransformation(UVTransformation transformation){
        this.uvTransformations.add(transformation);
        return this;
    }

    public ModelPart setColor(Colour color){
        for(EnumFacing side : EnumFacing.VALUES){
            for(VertexPosition vertexPosition : VertexPosition.VALUES){
                this.color[side.getIndex()][vertexPosition.getVertexIndex()] = color;
            }
        }

        return this;
    }

    public ModelPart setColor(Colour color, EnumFacing side){
        for(VertexPosition vertexPosition : VertexPosition.VALUES){
            this.color[side.getIndex()][vertexPosition.getVertexIndex()] = color;
        }

        return this;
    }

    public ModelPart setColor(Colour color, EnumFacing side, VertexPosition vertexPosition){
        this.color[side.getIndex()][vertexPosition.getVertexIndex()] = color;
        return this;
    }

    public ModelPart setBrightnessOverride(int brightness){
        this.brightness = brightness;
        this.hasBrightnessOverride = true;
        return this;
    }

    public void renderDamage(VertexBuffer buffer, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture){
        CCModel model = CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(this.bounds, 16D)).computeNormals();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);
        renderState.pullLightmap();

        for(Transformation transformation : this.transformations){
            model.apply(transformation);
        }

        for(UVTransformation transformation : this.uvTransformations){
            model.apply(transformation);
        }

        model.render(renderState, new IconTransformation(texture));
    }

    public void render(VertexBuffer buffer, IBlockAccess world, BlockPos pos){
        CCModel model = CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(this.bounds, 16D)).computeNormals();
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;

        if(pos != null){
            model.apply(new Translation(Vector3.fromBlockPos(pos)));
        }

        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);
        renderState.pullLightmap();

        for(Transformation transformation : this.transformations){
            model.apply(transformation);
        }

        for(UVTransformation transformation : this.uvTransformations){
            model.apply(transformation);
        }

        int vertex = 0;

        for(EnumFacing side : EnumFacing.VALUES){
            for(VertexPosition vertexPosition : VertexPosition.VALUES){
                IconTransformation texture = new IconTransformation(this.texture[side.getIndex()]);

                if(this.hasBrightnessOverride){
                    renderState.brightness = this.brightness;
                }
                else{
                    renderState.brightness = lastBrightness;
                }

                model.setColour(this.color[side.getIndex()][vertexPosition.getVertexIndex()].rgba());
                model.render(renderState, vertex, vertex + 1, texture);
                vertex++;
            }
        }
    }

    public List<BakedQuad> bake(IQuadManipulator... manipulators){
        CCModel model = CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(this.bounds, 16D)).computeNormals();
        int lastBrightness = (int)OpenGlHelper.lastBrightnessY << 16 | (int)OpenGlHelper.lastBrightnessX;
        BakingVertexBuffer buffer = BakingVertexBuffer.create();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);

        for(Transformation transformation : this.transformations){
            model.apply(transformation);
        }

        for(UVTransformation transformation : this.uvTransformations){
            model.apply(transformation);
        }

        int vertex = 0;

        for(EnumFacing side : EnumFacing.VALUES){
            for(VertexPosition vertexPosition : VertexPosition.VALUES){
                IconTransformation texture = new IconTransformation(this.texture[side.getIndex()]);

                if(this.hasBrightnessOverride){
                    renderState.brightness = this.brightness;
                }
                else{
                    renderState.brightness = lastBrightness;
                }

                model.setColour(this.color[side.getIndex()][vertexPosition.getVertexIndex()].rgba());
                model.render(renderState, vertex, vertex + 1, texture);
                vertex++;
            }
        }

        buffer.finishDrawing();
        List<BakedQuad> quads = buffer.bake();

        if(manipulators != null){
            for(IQuadManipulator manipulator : manipulators){
                quads = manipulator.manipulate(quads, null, null);
            }
        }

        return buffer.bake();
    }

    @Override
    public ModelPart copy() {
        ModelPart part = new ModelPart();
        part.bounds = this.bounds;
        part.texture = this.texture;
        part.transformations = this.transformations;
        part.uvTransformations = this.uvTransformations;
        part.color = this.color;
        return part;
    }

}

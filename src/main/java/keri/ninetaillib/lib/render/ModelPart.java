/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.colour.Colour;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.util.Copyable;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Transformation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.model.SimpleBakedModel;
import keri.ninetaillib.lib.util.VectorUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
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

import java.util.List;

@SideOnly(Side.CLIENT)
public class ModelPart implements Copyable<ModelPart> {

    private Cuboid6 bounds = new Cuboid6(0D, 0D, 0D, 16D, 16D, 16D);
    private TextureAtlasSprite[] texture = new TextureAtlasSprite[6];
    private List<Transformation> transformations = Lists.newArrayList();
    private List<UVTransformation> uvTransformations = Lists.newArrayList();
    private Colour[][] color = new Colour[6][4];

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

    public boolean renderBaked(VertexBuffer buffer, IBlockAccess world, BlockPos pos, boolean ao, IQuadManipulator... manipulators){
        List<BakedQuad> quads = this.bake(manipulators);
        BlockModelRenderer bmr = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer();
        boolean useAmbientOcclusion = Minecraft.getMinecraft().gameSettings.ambientOcclusion > 0 && ao;
        SimpleBakedModel bakedModel = new SimpleBakedModel(quads, null, useAmbientOcclusion);
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);

        if(useAmbientOcclusion){
            return bmr.renderModelSmooth(world, bakedModel, state, pos, buffer, true, 0);
        }
        else{
            return bmr.renderModelFlat(world, bakedModel, state, pos, buffer, true, 0);
        }
    }

    public void renderDamage(VertexBuffer buffer, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture){
        CCModel model = CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(this.bounds, 16D)).computeNormals();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        CCRenderState renderState = GlobalRenderingConstants.renderState;
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
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        CCRenderState renderState = GlobalRenderingConstants.renderState;
        renderState.reset();
        renderState.bind(buffer);
        renderState.pullLightmap();

        for(Transformation transformation : this.transformations){
            model.apply(transformation);
        }

        for(UVTransformation transformation : this.uvTransformations){
            model.apply(transformation);
        }

        for(EnumFacing side : EnumFacing.VALUES){
            for(VertexPosition vertexPosition : VertexPosition.VALUES){
                IconTransformation texture = new IconTransformation(this.texture[side.getIndex()]);
                model.setColour(this.color[side.getIndex()][vertexPosition.getVertexIndex()].rgba());
                model.render(renderState, vertexPosition.getVertexIndex(), vertexPosition.getVertexIndex() + 1, texture);
            }
        }
    }

    public List<BakedQuad> bake(IQuadManipulator... manipulators){
        List<BakedQuad> quads = Lists.newArrayList();
        CCModel model = CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(this.bounds, 16D)).computeNormals();
        BakingVertexBuffer buffer = BakingVertexBuffer.create();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        CCRenderState renderState = GlobalRenderingConstants.renderState;
        renderState.reset();
        renderState.bind(buffer);

        for(Transformation transformation : this.transformations){
            model.apply(transformation);
        }

        for(UVTransformation transformation : this.uvTransformations){
            model.apply(transformation);
        }

        for(EnumFacing side : EnumFacing.VALUES){
            for(VertexPosition vertexPosition : VertexPosition.VALUES){
                IconTransformation texture = new IconTransformation(this.texture[side.getIndex()]);
                model.setColour(this.color[side.getIndex()][vertexPosition.getVertexIndex()].rgba());
                model.render(renderState, vertexPosition.getVertexIndex(), vertexPosition.getVertexIndex() + 1, texture);
            }
        }

        if(manipulators != null){
            for(IQuadManipulator manipulator : manipulators){
                quads = manipulator.manipulate(quads, null, null);
            }
        }

        return quads;
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

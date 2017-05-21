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
import codechicken.lib.vec.Vertex5;
import codechicken.lib.vec.uv.MultiIconTransformation;
import codechicken.lib.vec.uv.UVTransformation;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.util.RenderUtils;
import keri.ninetaillib.lib.util.VectorUtils;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ModelPart implements Copyable<ModelPart> {

    private Cuboid6 bounds;
    private TextureAtlasSprite[] texture = new TextureAtlasSprite[6];
    private List<Transformation> transformations = Lists.newArrayList();
    private List<UVTransformation> uvTransformations = Lists.newArrayList();
    private Colour[][] color = new Colour[6][4];
    private int[][] brightness = new int[6][4];

    public ModelPart setBounds(Cuboid6 bounds){
        this.bounds = bounds;
        return this;
    }

    public ModelPart setTexture(TextureAtlasSprite texture){
        this.texture = new TextureAtlasSprite[]{texture, texture, texture, texture, texture, texture};
        return this;
    }

    public ModelPart setTexture(TextureAtlasSprite... textures){
        this.texture = textures;
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
        for(int side = 0; side < 6; side++){
            for(int vertex = 0; vertex < 4; vertex++){
                this.color[side][vertex] = color;
            }
        }

        return this;
    }

    public ModelPart setColor(int side, int vert, Colour color){
        this.color[side][vert] = color;
        return this;
    }

    public ModelPart setBrightness(int brightness){
        for(int side = 0; side < 6; side++) {
            for(int vertex = 0; vertex < 4; vertex++) {
                this.brightness[side][vertex] = brightness;
            }
        }

        return this;
    }

    public ModelPart setBrightness(int side, int vert, int brightness){
        this.brightness[side][vert] = brightness;
        return this;
    }

    public List<BakedQuad> bake(){
        CCModel model = CCModel.quadModel(24).generateBlock(0, VectorUtils.divide(this.bounds, 16D)).computeNormals();
        BakingVertexBuffer buffer = BakingVertexBuffer.create();
        buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        model.apply(new MultiIconTransformation(this.texture));

        for(int i = 0; i < this.transformations.size(); i++){
            model.apply(this.transformations.get(i));
        }

        for(int i = 0; i < this.uvTransformations.size(); i++){
            model.apply(this.transformations.get(i));
        }

        Vertex5[] vertices = model.getVertices();

        for(int sideIndex = 0; sideIndex < vertices.length / 4; sideIndex++){
            int vertexIndex = sideIndex * 4;
            this.addVertex(buffer, vertices[vertexIndex + 0],
                    new int[]{
                            this.color[sideIndex][0].r & 0xFF,
                            this.color[sideIndex][0].g & 0xFF,
                            this.color[sideIndex][0].b & 0xFF,
                            this.color[sideIndex][0].a & 0xFF
                    },
                    new int[]{
                            this.brightness[sideIndex][0] >> 0x10 & 0xFFFF,
                            this.brightness[sideIndex][0] & 0xFFFF
                    }
            );
            this.addVertex(buffer, vertices[vertexIndex + 1],
                    new int[]{
                            this.color[sideIndex][1].r & 0xFF,
                            this.color[sideIndex][1].g & 0xFF,
                            this.color[sideIndex][1].b & 0xFF,
                            this.color[sideIndex][1].a & 0xFF
                    },
                    new int[]{
                            this.brightness[sideIndex][1] >> 0x10 & 0xFFFF,
                            this.brightness[sideIndex][1] & 0xFFFF
                    }
            );
            this.addVertex(buffer, vertices[vertexIndex + 2],
                    new int[]{
                            this.color[sideIndex][2].r & 0xFF,
                            this.color[sideIndex][2].g & 0xFF,
                            this.color[sideIndex][2].b & 0xFF,
                            this.color[sideIndex][2].a & 0xFF
                    },
                    new int[]{
                            this.brightness[sideIndex][2] >> 0x10 & 0xFFFF,
                            this.brightness[sideIndex][2] & 0xFFFF
                    }
            );
            this.addVertex(buffer, vertices[vertexIndex + 3],
                    new int[]{
                            this.color[sideIndex][3].r & 0xFF,
                            this.color[sideIndex][3].g & 0xFF,
                            this.color[sideIndex][3].b & 0xFF,
                            this.color[sideIndex][3].a & 0xFF
                    },
                    new int[]{
                            this.brightness[sideIndex][3] >> 0x10 & 0xFFFF,
                            this.brightness[sideIndex][3] & 0xFFFF
                    }
            );
        }

        buffer.finishDrawing();
        return buffer.bake();
    }

    private void addVertex(VertexBuffer buffer, Vertex5 vertex, int[] color, int[] brightness){
        buffer.pos(vertex.vec.x, vertex.vec.y, vertex.vec.z).tex(vertex.uv.u, vertex.uv.v).color(color[0], color[1], color[2], color[3]).lightmap(brightness[0], brightness[1]).endVertex();
    }

    @Override
    public ModelPart copy() {
        ModelPart part = new ModelPart();
        part.bounds = this.bounds;
        part.texture = this.texture;
        part.transformations = this.transformations;
        part.uvTransformations = this.uvTransformations;
        part.color = this.color;
        part.brightness = this.brightness;
        return part;
    }

}

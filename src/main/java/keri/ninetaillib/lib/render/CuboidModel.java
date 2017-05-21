/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.util.Copyable;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.model.SimpleBakedModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class CuboidModel implements Copyable<CuboidModel> {

    private List<ModelPart> modelParts = Lists.newArrayList();

    public void addModelPart(ModelPart part){
        this.modelParts.add(part);
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
        for(ModelPart part : this.modelParts){
            part.renderDamage(buffer, world, pos, texture);
        }
    }

    public void render(VertexBuffer buffer, IBlockAccess world, BlockPos pos){
        for(ModelPart part : this.modelParts){
            part.render(buffer, world, pos);
        }
    }

    public List<BakedQuad> bake(IQuadManipulator... manipulators){
        List<BakedQuad> quads = Lists.newArrayList();

        for(ModelPart part : this.modelParts){
            quads.addAll(part.bake(manipulators));
        }

        return quads;
    }

    @Override
    public CuboidModel copy() {
        CuboidModel model = new CuboidModel();
        model.modelParts = this.modelParts;
        return model;
    }

}

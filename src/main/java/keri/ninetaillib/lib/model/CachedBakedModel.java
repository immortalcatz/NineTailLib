/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.model;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.QuadCache;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class CachedBakedModel implements IBakedModel {

    private static QuadCache<IBlockState> quadCache = new QuadCache<>();

    public abstract List<BakedQuad> getCachedQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand);

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if(!quadCache.isPresent(state)){
            quadCache.put(state, this.getCachedQuads(state, side, rand));
        }
        else{
            return quadCache.get(state);
        }

        return Lists.newArrayList();
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.model;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.render.QuadCache;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class CachedPABakedModel implements IPerspectiveAwareModel {

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

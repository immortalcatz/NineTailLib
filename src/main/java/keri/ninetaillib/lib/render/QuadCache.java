/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SideOnly(Side.CLIENT)
public class QuadCache<K extends Object> {

    private Cache<String, List<BakedQuad>> quadCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    public void put(K key, List<BakedQuad> quads){
        this.quadCache.put(this.generateKey(key), quads);
    }

    public List<BakedQuad> get(K key){
        return this.quadCache.getIfPresent(this.generateKey(key));
    }

    public boolean isPresent(K key){
        return this.quadCache.getIfPresent(this.generateKey(key)) != null;
    }

    public void invalidate(K key){
        this.quadCache.invalidate(this.generateKey(key));
    }

    public void invalidateAll(){
        this.quadCache.invalidateAll();
    }

    private String generateKey(Object key){
        if(key instanceof IBlockState){
            return this.generateBlockKey((IBlockState)key);
        }
        else if(key instanceof ItemStack){
            return this.generateItemKey((ItemStack)key);
        }
        else{
            return key.toString();
        }
    }

    private String generateItemKey(ItemStack stack){
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getItem().getRegistryName().getResourceDomain()).append(':');
        builder.append(stack.getItem().getRegistryName().getResourcePath()).append(':');
        builder.append(stack.getMetadata()).append('|');

        if(stack.getTagCompound() != null){
            builder.append(stack.getTagCompound().toString());
        }

        return builder.toString();
    }

    private String generateBlockKey(IBlockState state){
        StringBuilder builder = new StringBuilder();
        builder.append(state.getBlock().getRegistryName().getResourceDomain()).append(':');
        builder.append(state.getBlock().getRegistryName().getResourcePath()).append(':');
        builder.append(state.getBlock().getMetaFromState(state)).append('|');

        if(state.getPropertyKeys() != null){
            for(IProperty<?> property : state.getPropertyKeys()){
                builder.append(state.getValue(property).toString()).append('|');
            }
        }

        if(state instanceof IExtendedBlockState){
            IExtendedBlockState extendedState = (IExtendedBlockState)state;

            if(extendedState.getUnlistedNames() != null){
                for(IUnlistedProperty<?> property : extendedState.getUnlistedNames()){
                    builder.append(extendedState.getValue(property).toString()).append('|');
                }
            }
        }

        return builder.toString();
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class IconRegister implements IIconRegister {

    private static List<IIconBlock> blocks = Lists.newArrayList();
    private static List<IIconItem> items = Lists.newArrayList();
    private static Map<String, ResourceLocation> locations = Maps.newHashMap();
    private static Map<String, TextureAtlasSprite> textures = Maps.newHashMap();

    @SubscribeEvent
    public void onTextureStitchPre(TextureStitchEvent.Pre event){
        for(IIconBlock block : blocks){
            block.reigisterIcons(this);
        }

        for(IIconItem item : items){
            item.registerIcons(this);
        }

        for(Map.Entry<String, ResourceLocation> location : locations.entrySet()){
            TextureAtlasSprite texture = event.getMap().registerSprite(location.getValue());
            textures.put(location.getKey(), texture);
        }
    }

    @SubscribeEvent
    public void onTextureStitchPost(TextureStitchEvent.Post event){
        for(IIconBlock block : blocks){
            block.reigisterIcons(this);
        }

        for(IIconItem item : items){
            item.registerIcons(this);
        }
    }

    @Override
    public TextureAtlasSprite registerIcon(String path){
        if(!locations.containsKey(path)){
            locations.put(path, new ResourceLocation(path));
        }

        return textures.get(path);
    }

    public void registerBlock(IIconBlock block){
        blocks.add(block);
    }

    public void registerItem(IIconItem item){
        items.add(item);
    }

}

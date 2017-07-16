/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import keri.ninetaillib.lib.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientUtils {

    public static TextureAtlasSprite[] registerConnectedTexture(IIconRegister register, String path){
        TextureAtlasSprite[] texture = new TextureAtlasSprite[5];
        texture[0] = register.registerIcon(path + "_c");
        texture[1] = register.registerIcon(path + "_v");
        texture[2] = register.registerIcon(path + "_h");
        texture[3] = register.registerIcon(path);
        texture[4] = register.registerIcon(path + "_a");
        return texture;
    }

}

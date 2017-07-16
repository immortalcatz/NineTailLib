/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.connected;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICTMBlock {

    @SideOnly(Side.CLIENT)
    boolean canTextureConnect(IBlockAccess world, BlockPos pos, EnumFacing side);

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite[] getConnectedTexture(IBlockAccess world, BlockPos pos, EnumFacing side);

}

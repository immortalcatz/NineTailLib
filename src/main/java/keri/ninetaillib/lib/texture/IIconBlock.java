/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIconBlock {

    @SideOnly(Side.CLIENT)
    void reigisterIcons(IIconRegister register);

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(int meta, int side);

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, int side);

}

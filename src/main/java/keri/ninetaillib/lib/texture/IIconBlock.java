/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IIconBlock {

    void reigisterIcons(IIconRegister register);

    TextureAtlasSprite getIcon(int meta, int side);

    TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, int side);

}

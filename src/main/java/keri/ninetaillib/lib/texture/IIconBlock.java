/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIconBlock {

    @SideOnly(Side.CLIENT)
    void registerIcons(IIconRegister register);

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(int meta, int side);

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, int side);

    @SideOnly(Side.CLIENT)
    int getColorMultiplier(int meta, int side);

    @SideOnly(Side.CLIENT)
    int getColorMultiplier(IBlockAccess world, BlockPos pos, int side);

}

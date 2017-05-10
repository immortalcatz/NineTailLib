package keri.ninetaillib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IIconBlock {

    void registerBlockIcons(IIconRegistrar registrar);

    TextureAtlasSprite getIcon(int meta, int side);

    TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, int side);

}

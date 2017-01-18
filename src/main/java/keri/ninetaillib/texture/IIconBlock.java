package keri.ninetaillib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IIconBlock {

    void registerBlockIcons(IIconRegistrar registrar);

    TextureAtlasSprite getIcon(int meta, int side);

}

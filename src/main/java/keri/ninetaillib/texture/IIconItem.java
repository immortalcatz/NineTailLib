package keri.ninetaillib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IIconItem {

    void registerIcons(IIconRegistrar registrar);

    TextureAtlasSprite getIcon(int meta);

}

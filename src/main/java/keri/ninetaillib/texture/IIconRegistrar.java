package keri.ninetaillib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IIconRegistrar {

    TextureAtlasSprite registerIcon(String path);

    void registerBlock(IIconBlock block);

    void registerItem(IIconItem item);

}

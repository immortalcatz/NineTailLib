package keri.ninetaillib.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

public interface IIconItem {

    void registerIcons(IIconRegistrar registrar);

    TextureAtlasSprite getIcon(int meta);

    default TextureAtlasSprite getIcon(ItemStack stack){ return null; };

}

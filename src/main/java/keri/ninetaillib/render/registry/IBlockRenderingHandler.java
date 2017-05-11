package keri.ninetaillib.render.registry;

import codechicken.lib.render.CCRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockRenderingHandler {

    boolean renderBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer);

    void renderBlockDamage(CCRenderState renderState, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture);

    void renderItem(CCRenderState renderState, ItemStack stack);

    default boolean useDefaultLighting(){ return true; };

}

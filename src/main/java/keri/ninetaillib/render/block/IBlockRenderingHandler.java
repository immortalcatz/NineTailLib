package keri.ninetaillib.render.block;

import codechicken.lib.render.CCRenderState;
import keri.ninetaillib.texture.IIconBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.List;

public interface IBlockRenderingHandler {

    void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand);

    void renderItem(CCRenderState renderState, ItemStack stack, long rand);

    TextureAtlasSprite getParticleTexture(IIconBlock block, int meta);

    default boolean hasDynamicItemRendering(){ return false; };

    default EnumFacing getRotation(IBlockState state){ return EnumFacing.NORTH; }

    default String getBlockKey(IExtendedBlockState state){ return null; }

    default String getItemKey(ItemStack stack){ return null; }

    default List<BakedQuad> getBlockQuads(IBlockState state, EnumFacing side, long rand){ return null; }

    default List<BakedQuad> getItemQuads(ItemStack stack, long rand){ return null; }

}

package keri.ninetaillib.render.model;

import codechicken.lib.render.CCRenderState;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DefaultBlockRenderer implements IBlockRenderingHandler {

    /**
     * MORE TODO IN HERE YA' LAZY FUCKTURD
     */

    @Override
    public boolean renderBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer) {
        return false;
    }

    @Override
    public void renderBlockDamage(CCRenderState renderState, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture) {

    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack) {

    }

}

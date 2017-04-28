package keri.ninetaillib.render.block;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.texture.IIconBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DefaultBlockRenderer implements IBlockRenderingHandler {

    @Override
    public void renderBlock(CCRenderState renderState, IBlockState state, EnumFacing face, BlockRenderLayer layer, long rand) {
        CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();
        int meta = state.getBlock().getMetaFromState(state);

        for(int i = 0; i < 6; i++){
            TextureAtlasSprite texture = ((IIconBlock)state.getBlock()).getIcon(meta, i);

            if(texture != null){
                model.render(renderState, 0 + (4 * i), 4 + (4 * i), new IconTransformation(texture));
            }
        }
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {
        CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();
        int meta = stack.getMetadata();

        for(int i = 0; i < 6; i++){
            TextureAtlasSprite texture = ((IIconBlock)Block.getBlockFromItem(stack.getItem())).getIcon(meta, i);

            if(texture != null){
                model.render(renderState, 0 + (4 * i), 4 + (4 * i), new IconTransformation(texture));
            }
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture(IBlockState state) {
        return ((IIconBlock)state.getBlock()).getIcon(state.getBlock().getMetaFromState(state), 0);
    }

}

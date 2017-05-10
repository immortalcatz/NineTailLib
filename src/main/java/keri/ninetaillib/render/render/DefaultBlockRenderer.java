package keri.ninetaillib.render.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.render.registry.IBlockRenderingHandler;
import keri.ninetaillib.texture.IIconBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DefaultBlockRenderer implements IBlockRenderingHandler {

    @Override
    public boolean renderBlock(CCRenderState renderState, IBlockAccess world, BlockPos pos, BlockRenderLayer layer) {
        IBlockState state = world.getBlockState(pos);
        IIconBlock iconProvider = (IIconBlock)state.getBlock();
        CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));

        for(int side = 0; side < 6; side++){
            TextureAtlasSprite texture = null;

            if(iconProvider.getIcon(world, pos, side) != null){
                texture = iconProvider.getIcon(world, pos, side);
            }
            else{
                texture = iconProvider.getIcon(state.getBlock().getMetaFromState(state), side);
            }

            model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(texture));
        }

        return true;
    }

    @Override
    public void renderBlockDamage(CCRenderState renderState, IBlockAccess world, BlockPos pos, TextureAtlasSprite texture) {
        CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();
        model.apply(new Translation(Vector3.fromBlockPos(pos)));
        model.render(renderState, new IconTransformation(texture));
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack){
        CCModel model = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();
        IIconBlock iconProvider = (IIconBlock)Block.getBlockFromItem(stack.getItem());

        for(int side = 0; side < 6; side++){
            TextureAtlasSprite texture = iconProvider.getIcon(stack.getMetadata(), side);
            model.render(renderState, 0 + (4 * side), 4 + (4 * side), new IconTransformation(texture));
        }
    }

}

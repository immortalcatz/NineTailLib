/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.buffer.BakingVertexBuffer;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlocks {

    public static EnumBlockRenderType FULL_BLOCK;

    static{
        FULL_BLOCK = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new RenderFullBlock());
    }

    @SideOnly(Side.CLIENT)
    private static class RenderFullBlock implements IBlockRenderingHandler {

        private static CCModel BLOCK_MODEL = CCModel.quadModel(24).generateBlock(0, new Cuboid6(0D, 0D, 0D, 1D, 1D, 1D)).computeNormals();

        @Override
        public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer){
            CCModel model = BLOCK_MODEL.copy();
            IBlockState state = world.getBlockState(pos).getActualState(world, pos);
            Block block = state.getBlock();

            if(block instanceof IIconBlock){
                IIconBlock iconProvider = (IIconBlock)block;
                BakingVertexBuffer parent = BakingVertexBuffer.create();
                parent.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
                CCRenderState renderState = CCRenderState.instance();
                renderState.reset();
                renderState.bind(parent);

                for(EnumFacing side : EnumFacing.VALUES){
                    TextureAtlasSprite texture = null;
                    int colorMultiplier = iconProvider.getColorMultiplier(BlockAccessUtils.getBlockMetadata(world, pos), side.getIndex());

                    if(iconProvider.getIcon(world, pos, side.getIndex()) != null){
                        texture = iconProvider.getIcon(world, pos, side.getIndex());
                    }
                    else {
                        texture = iconProvider.getIcon(BlockAccessUtils.getBlockMetadata(world, pos), side.getIndex());
                    }

                    model.setColour(colorMultiplier);

                    if(state.shouldSideBeRendered(world, pos, side)){
                        model.render(renderState, 0 + (4 * side.getIndex()), 4 + (4 * side.getIndex()), new IconTransformation(texture));
                    }
                }

                parent.finishDrawing();
                return RenderUtils.renderQuads(buffer, world, pos, parent.bake());
            }

            return false;
        }

        @Override
        public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
            CCModel model = BLOCK_MODEL.copy();
            CCRenderState renderState = CCRenderState.instance();
            renderState.reset();
            renderState.bind(buffer);
            model.apply(new Translation(Vector3.fromBlockPos(pos)));
            model.render(renderState, new IconTransformation(texture));
        }

        @Override
        public void renderInventory(ItemStack stack, VertexBuffer buffer) {
            GlStateManager.pushMatrix();
            GlStateManager.enableLighting();
            CCModel model = BLOCK_MODEL.copy();
            Block block = Block.getBlockFromItem(stack.getItem());
            CCRenderState renderState = CCRenderState.instance();
            renderState.reset();
            renderState.bind(buffer);

            if(block instanceof IIconBlock){
                IIconBlock iconProvider = (IIconBlock)block;

                for(EnumFacing side : EnumFacing.VALUES){
                    TextureAtlasSprite texture = iconProvider.getIcon(stack.getMetadata(), side.getIndex());
                    int colorMultiplier = iconProvider.getColorMultiplier(stack.getMetadata(), side.getIndex());
                    model.setColour(colorMultiplier);
                    model.render(renderState, 0 + (4 * side.getIndex()), 4 + (4 * side.getIndex()), new IconTransformation(texture));
                }
            }

            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
        }

        @Override
        public EnumBlockRenderType getRenderType() {
            return FULL_BLOCK;
        }

    }

}

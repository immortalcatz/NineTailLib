/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import keri.ninetaillib.lib.texture.IIconBlock;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlocks {

    public static EnumBlockRenderType FULL_BLOCK;

    static{
        FULL_BLOCK = RenderingRegistry.getNextAvailableType();
        RenderingRegistry.registerRenderingHandler(new RenderFullBlock());
    }

    @SideOnly(Side.CLIENT)
    private static class RenderFullBlock implements IBlockRenderingHandler {

        @Override
        public boolean renderWorld(IBlockAccess world, BlockPos pos, VertexBuffer buffer, BlockRenderLayer layer) {
            Block block = world.getBlockState(pos).getBlock();

            if(block instanceof IIconBlock){
                IIconBlock iconProvider = (IIconBlock)block;
                int meta = BlockAccessUtils.getBlockMetadata(world, pos);
                ModelPart model = new ModelPart();

                for(EnumFacing side : EnumFacing.VALUES){
                    if(iconProvider.getIcon(world, pos, side.getIndex()) != null){
                        TextureAtlasSprite texture = iconProvider.getIcon(world, pos, side.getIndex());
                        model.setTexture(texture, side);
                    }
                    else{
                        TextureAtlasSprite texture = iconProvider.getIcon(meta, side.getIndex());
                        model.setTexture(texture, side);
                    }
                }

                return RenderUtils.renderQuads(buffer, world, pos, model.bake());
            }

            return false;
        }

        @Override
        public void renderDamage(IBlockAccess world, BlockPos pos, VertexBuffer buffer, TextureAtlasSprite texture) {
            new ModelPart().renderDamage(buffer, world, pos, texture);
        }

        @Override
        public void renderInventory(ItemStack stack, VertexBuffer buffer) {
            Block block = Block.getBlockFromItem(stack.getItem());

            if(block instanceof IIconBlock){
                IIconBlock iconProvider = (IIconBlock)block;
                int meta = stack.getMetadata();
                ModelPart model = new ModelPart();

                for(EnumFacing side : EnumFacing.VALUES){
                    TextureAtlasSprite texture = iconProvider.getIcon(meta, side.getIndex());
                    model.setTexture(texture, side);
                }

                RenderHelper.enableStandardItemLighting();
                model.render(buffer, null, null);
            }
        }

        @Override
        public EnumBlockRenderType getRenderType() {
            return FULL_BLOCK;
        }

    }

}

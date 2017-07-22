/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IBlockRenderingHandler {

    boolean renderWorld(IBlockAccess world, BlockPos pos, IBlockState blockState, VertexBuffer buffer, BlockRenderLayer layer);

    void renderDamage(IBlockAccess world, BlockPos pos, IBlockState blockState, VertexBuffer buffer, TextureAtlasSprite texture);

    void renderInventory(ItemStack stack, VertexBuffer buffer);

    EnumBlockRenderType getRenderType();

}

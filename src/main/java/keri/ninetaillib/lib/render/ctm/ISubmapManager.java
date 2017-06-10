/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import keri.ninetaillib.lib.texture.IIcon;
import keri.ninetaillib.lib.texture.IIconRegister;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ISubmapManager {

    IIcon getIcon(int side, int meta);

    IIcon getIcon(IBlockAccess world, int x, int y, int z, int side);

    @SideOnly(Side.CLIENT)
    void registerIcons(String modName, Block block, IIconRegister register);

    @SideOnly(Side.CLIENT)
    IBlockRenderer createRenderContext(VertexBuffer buffer, IBlockRenderer rendererOld, Block block, IBlockAccess world);

    @SideOnly(Side.CLIENT)
    void preRenderSide(IBlockRenderer renderer, IBlockAccess world, int x, int y, int z, EnumFacing side);

    @SideOnly(Side.CLIENT)
    void postRenderSide(IBlockRenderer renderer, IBlockAccess world, int x, int y, int z, EnumFacing side);

}

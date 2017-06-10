/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import keri.ninetaillib.lib.hooks.IIcon;
import keri.ninetaillib.lib.texture.IIconRegister;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SubmapManagerCTM implements ISubmapManager {

    @SideOnly(Side.CLIENT)
    private static BlockRendererCTM rb;
    @Getter
    private TextureSubmap submap, submapSmall;
    @Getter
    private final String textureName;

    public SubmapManagerCTM(String textureName) {
        this.textureName = textureName;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return submapSmall.getBaseIcon();
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return getIcon(side, BlockAccessUtils.getBlockMetadata(world, new BlockPos(x, y, z)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(String modName, Block block, IIconRegister register) {
        submap = new TextureSubmap((IIcon)register.registerIcon(modName + ":" + textureName + "-ctm"), 4, 4);
        submapSmall = new TextureSubmap((IIcon)register.registerIcon(modName + ":" + textureName), 2, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IBlockRenderer createRenderContext(IBlockRenderer rendererOld, Block block, IBlockAccess world) {
        if (rb == null) {
            rb = new BlockRendererCTM();
        }

        //rb.setRenderBoundsFromBlock(block);
        //rb.submap = submap;
        //rb.submapSmall = submapSmall;
        return rb;
    }

    @Override
    public void preRenderSide(IBlockRenderer renderer, IBlockAccess world, int x, int y, int z, EnumFacing side) {

    }

    @Override
    public void postRenderSide(IBlockRenderer renderer, IBlockAccess world, int x, int y, int z, EnumFacing side) {

    }

}

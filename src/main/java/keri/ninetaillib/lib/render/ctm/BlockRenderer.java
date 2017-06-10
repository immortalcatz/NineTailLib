/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import codechicken.lib.texture.TextureUtils;
import keri.ninetaillib.lib.texture.IIcon;
import keri.ninetaillib.lib.texture.IIconBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.world.IBlockAccess;

public class BlockRenderer implements IBlockRenderer {

    public VertexBuffer buffer;
    public IBlockAccess blockAccess;
    public double renderMinX;
    public double renderMinY;
    public double renderMinZ;
    public double renderMaxX;
    public double renderMaxY;
    public double renderMaxZ;

    public BlockRenderer(VertexBuffer buffer){
        this.buffer = buffer;
        this.renderMinX = 0D;
        this.renderMinY = 0D;
        this.renderMinZ = 0D;
        this.renderMaxX = 1D;
        this.renderMaxY = 1D;
        this.renderMaxZ = 1D;
    }

    @Override
    public void setBlockAccess(IBlockAccess blockAccess){
        this.blockAccess = blockAccess;
    }

    @Override
    public IIcon getBlockIconFromSideAndMetadata(Block block, int side, int meta){
        if(block instanceof IIconBlock){
            return (IIcon)((IIconBlock)block).getIcon(meta, side);
        }
        else{
            return (IIcon)TextureUtils.getMissingSprite();
        }
    }

    @Override
    public boolean renderStandardBlock(Block block, int x, int y, int z) {
        return false;
    }

    @Override
    public void renderFaceXNeg(Block block, double x, double y, double z, IIcon icon) {

    }

    @Override
    public void renderFaceXPos(Block block, double x, double y, double z, IIcon icon) {

    }

    @Override
    public void renderFaceZNeg(Block block, double x, double y, double z, IIcon icon) {

    }

    @Override
    public void renderFaceZPos(Block block, double x, double y, double z, IIcon icon) {

    }

    @Override
    public void renderFaceYNeg(Block block, double x, double y, double z, IIcon icon) {

    }

    @Override
    public void renderFaceYPos(Block block, double x, double y, double z, IIcon icon) {

    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import keri.ninetaillib.lib.texture.IIcon;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public interface IBlockRenderer {

    void setBlockAccess(IBlockAccess world);

    IIcon getBlockIconFromSideAndMetadata(Block block, int side, int meta);

    boolean renderStandardBlock(Block block, int x, int y, int z);

    void renderFaceXNeg(Block block, double x, double y, double z, IIcon icon);

    void renderFaceXPos(Block block, double x, double y, double z, IIcon icon);

    void renderFaceZNeg(Block block, double x, double y, double z, IIcon icon);

    void renderFaceZPos(Block block, double x, double y, double z, IIcon icon);

    void renderFaceYNeg(Block block, double x, double y, double z, IIcon icon);

    void renderFaceYPos(Block block, double x, double y, double z, IIcon icon);

}

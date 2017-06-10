/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import static net.minecraft.util.EnumFacing.*;

public enum Dir {

    TOP(UP),
    TOP_RIGHT(UP, EAST),
    RIGHT(EAST),
    BOTTOM_RIGHT(DOWN, EAST),
    BOTTOM(DOWN),
    BOTTOM_LEFT(DOWN, WEST),
    LEFT(WEST),
    TOP_LEFT(UP, WEST);

    private static final int[][] ROTATION_MATRIX = {
            {0, 1, 4, 5, 3, 2, 6},
            {0, 1, 5, 4, 2, 3, 6},
            {5, 4, 2, 3, 0, 1, 6},
            {4, 5, 2, 3, 1, 0, 6},
            {2, 3, 1, 0, 4, 5, 6},
            {3, 2, 0, 1, 4, 5, 6},
            {0, 1, 2, 3, 4, 5, 6},
    };
    public static final Dir[] VALUES = values();
    private static final EnumFacing NORMAL = SOUTH;
    private EnumFacing[] dirs;

    private Dir(EnumFacing... dirs) {
        this.dirs = dirs;
    }

    public boolean isConnected(CTM inst, IBlockAccess world, int x, int y, int z, int sideIdx, Block block, int meta) {
        EnumFacing side = EnumFacing.getFront(sideIdx);
        EnumFacing[] dirs = getNormalizedDirs(side);
        for (EnumFacing dir : dirs) {
            x += dir.getFrontOffsetX();
            y += dir.getFrontOffsetY();
            z += dir.getFrontOffsetZ();
        }
        return inst.isConnected(world, x, y, z, side.getIndex(), block, meta);
    }

    private EnumFacing[] getNormalizedDirs(EnumFacing normal) {
        if (normal == NORMAL) {
            return dirs;
        }
        else if (normal == NORMAL.getOpposite()) {
            EnumFacing[] ret = new EnumFacing[dirs.length];

            for (int i = 0; i < ret.length; i++) {
                ret[i] = dirs[i].getFrontOffsetY() != 0 ? dirs[i] : dirs[i].getOpposite();
            }

            return ret;
        }
        else {
            EnumFacing axis = null;

            if (normal.getFrontOffsetY() == 0) {
                //axis = normal == NORMAL.getRotation(UP) ? UP : DOWN;
                axis = normal == this.getRotation(NORMAL, UP) ? UP : DOWN;
            }
            else {
                //axis = normal == UP ? NORMAL.getRotation(DOWN) : NORMAL.getRotation(UP);
                axis = normal == UP ? this.getRotation(NORMAL, DOWN) : this.getRotation(NORMAL, UP);
            }

            EnumFacing[] ret = new EnumFacing[dirs.length];

            for (int i = 0; i < ret.length; i++) {
                //ret[i] = dirs[i].getRotation(axis);
                ret[i] = this.getRotation(dirs[i], axis);
            }

            return ret;
        }
    }

    private EnumFacing getRotation(EnumFacing in, EnumFacing axis){
        return EnumFacing.getFront(ROTATION_MATRIX[axis.getIndex()][in.getIndex()]);
    }

}

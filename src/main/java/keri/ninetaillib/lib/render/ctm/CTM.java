/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

//@formatter:off

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import keri.ninetaillib.lib.util.BlockAccessUtils;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.EnumMap;

import static keri.ninetaillib.lib.render.ctm.Dir.*;

/**
 * The CTM renderer will draw the block's FACE using by assembling 4 quadrants from the 5 available block
 * textures.  The normal Texture.png is the blocks "unconnected" texture, and is used when CTM is disabled or the block
 * has nothing to connect to.  This texture has all of the outside corner quadrants  The texture-ctm.png contains the
 * rest of the quadrants.
 * <pre><blockquote>
 * ┌─────────────────┐ ┌────────────────────────────────┐
 * │ texture.png     │ │ texture-ctm.png                │
 * │ ╔══════╤══════╗ │ │  ──────┼────── ║ ─────┼───── ║ │
 * │ ║      │      ║ │ │ │      │      │║      │      ║ │
 * │ ║ 16   │ 17   ║ │ │ │ 0    │ 1    │║ 2    │ 3    ║ │
 * │ ╟──────┼──────╢ │ │ ┼──────┼──────┼╟──────┼──────╢ │
 * │ ║      │      ║ │ │ │      │      │║      │      ║ │
 * │ ║ 18   │ 19   ║ │ │ │ 4    │ 5    │║ 6    │ 7    ║ │
 * │ ╚══════╧══════╝ │ │  ──────┼────── ║ ─────┼───── ║ │
 * └─────────────────┘ │ ═══════╤═══════╝ ─────┼───── ╚ │
 *                     │ │      │      ││      │      │ │
 *                     │ │ 8    │ 9    ││ 10   │ 11   │ │
 *                     │ ┼──────┼──────┼┼──────┼──────┼ │
 *                     │ │      │      ││      │      │ │
 *                     │ │ 12   │ 13   ││ 14   │ 15   │ │
 *                     │ ═══════╧═══════╗ ─────┼───── ╔ │
 *                     └────────────────────────────────┘
 * </blockquote></pre>
 * combining { 18, 13,  9, 16 }, we can generate a texture connected to the right!
 * <pre><blockquote>
 * ╔══════╤═══════
 * ║      │      │
 * ║ 16   │ 9    │
 * ╟──────┼──────┼
 * ║      │      │
 * ║ 18   │ 13   │
 * ╚══════╧═══════
 * </blockquote></pre>
 *
 * combining { 18, 13, 11,  2 }, we can generate a texture, in the shape of an L (connected to the right, and up
 * <pre><blockquote>
 * ║ ─────┼───── ╚
 * ║      │      │
 * ║ 2    │ 11   │
 * ╟──────┼──────┼
 * ║      │      │
 * ║ 18   │ 13   │
 * ╚══════╧═══════
 * </blockquote></pre>
 *
 * HAVE FUN!
 * -CptRageToaster-
 */
//@formatter:on
public class CTM {

    protected static int[] submapOffsets = { 4, 5, 1, 0 };
    public static boolean disableObscuredFaceCheckConfig = false;
    public Optional<Boolean> disableObscuredFaceCheck = Optional.absent();
    protected TIntObjectMap<Dir[]> submapMap = new TIntObjectHashMap<>();
    protected EnumMap<Dir, Boolean> connectionMap = Maps.newEnumMap(Dir.class);

    protected CTM() {
        for (Dir dir : Dir.VALUES) {
            connectionMap.put(dir, false);
        }

        submapMap.put(0, new Dir[] { BOTTOM, LEFT, BOTTOM_LEFT });
        submapMap.put(1, new Dir[] { BOTTOM, RIGHT, BOTTOM_RIGHT });
        submapMap.put(2, new Dir[] { TOP, RIGHT, TOP_RIGHT });
        submapMap.put(3, new Dir[] { TOP, LEFT, TOP_LEFT });
    }

    public static CTM getInstance() {
        return new CTM();
    }

    public int[] getSubmapIndices(IBlockAccess world, int x, int y, int z, int side) {
        int[] ret = new int[] { 18, 19, 17, 16 };

        if (world == null) {
            return ret;
        }

        BlockPos pos = new BlockPos(x, y, z);
        Block block = world.getBlockState(pos).getBlock();
        int meta = BlockAccessUtils.getBlockMetadata(world, pos);
        buildConnectionMap(world, x, y, z, side, block, meta);

        for (int i = 0; i < 4; i++) {
            fillSubmaps(ret, i);
        }

        return ret;
    }

    public void buildConnectionMap(IBlockAccess world, int x, int y, int z, int side, Block block, int meta) {
        for (Dir dir : Dir.VALUES) {
            connectionMap.put(dir, dir.isConnected(this, world, x, y, z, side, block, meta));
        }
    }

    private void fillSubmaps(int[] ret, int idx) {
        Dir[] dirs = submapMap.get(idx);
        if (connectedOr(dirs[0], dirs[1])) {
            if (connectedAnd(dirs)) {
                ret[idx] = submapOffsets[idx];
            } else {
                ret[idx] = submapOffsets[idx] + (connected(dirs[0]) ? 2 : 0) + (connected(dirs[1]) ? 8 : 0);
            }
        }
    }

    public boolean connected(Dir dir) {
        return connectionMap.get(dir);
    }

    public boolean connectedAnd(Dir... dirs) {
        for (Dir dir : dirs) {
            if (!connected(dir)) {
                return false;
            }
        }
        return true;
    }

    private boolean connectedOr(Dir... dirs) {
        for (Dir dir : dirs) {
            if (connected(dir)) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnected(IBlockAccess world, int x, int y, int z, int side, Block block, int meta) {
        EnumFacing dir = EnumFacing.getFront(side);
        return isConnected(world, x, y, z, dir, block, meta);
    }

    public boolean isConnected(IBlockAccess world, int x, int y, int z, EnumFacing dir, Block block, int meta) {
        //if (CTMLib.chiselLoaded() && connectionBlocked(world, x, y, z, dir.ordinal())) {
        //    return false;
        //}

        int x2 = x + dir.getFrontOffsetX();
        int y2 = y + dir.getFrontOffsetY();
        int z2 = z + dir.getFrontOffsetZ();
        boolean disableObscured = disableObscuredFaceCheck.or(disableObscuredFaceCheckConfig);
        Block con = getBlockOrFacade(world, x, y, z, dir.ordinal());
        Block obscuring = disableObscured ? null : getBlockOrFacade(world, x2, y2, z2, dir.ordinal());

        if (con == null) {
            return false;
        }

        boolean ret = con.equals(block) && getBlockOrFacadeMetadata(world, x, y, z, dir.ordinal()) == meta;

        if (obscuring == null) {
            return ret;
        }

        ret &= !(obscuring.equals(block) && getBlockOrFacadeMetadata(world, x2, y2, z2, dir.ordinal()) == meta);
        return ret;
    }

    private boolean connectionBlocked(IBlockAccess world, int x, int y, int z, int side) {
        BlockPos pos = new BlockPos(x, y, z);
        Block block = world.getBlockState(pos).getBlock();

        if (block instanceof IConnectable) {
            return !((IConnectable) block).canConnectCTM(world, pos, EnumFacing.getFront(side));
        }

        return false;
    }

    public int getBlockOrFacadeMetadata(IBlockAccess world, int x, int y, int z, int side) {
        BlockPos pos = new BlockPos(x, y, z);
        Block blk = world.getBlockState(pos).getBlock();

        //if (CTMLib.chiselLoaded()) {
        //    return getFacadeMeta(blk, world, x, y, z, side);
        //}

        return BlockAccessUtils.getBlockMetadata(world, pos);
    }

    public Block getBlockOrFacade(IBlockAccess world, int x, int y, int z, int side) {
        Block blk = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        //if (CTMLib.chiselLoaded()) {
        //    return getFacade(blk, world, x, y, z, side);
        //}

        return blk;
    }

    /**
    private int getFacadeMeta(Block blk, IBlockAccess world, int x, int y, int z, int side) {
        if (blk instanceof IFacade) {
            return ((IFacade) blk).getFacadeMetadata(world, x, y, z, side);
        }
        return world.getBlockMetadata(x, y, z);
    }

    private Block getFacade(Block blk, IBlockAccess world, int x, int y, int z, int side) {
        if (blk instanceof IFacade) {
            blk = ((IFacade) blk).getFacade(world, x, y, z, side);
        }
        return blk;
    }
     */

}

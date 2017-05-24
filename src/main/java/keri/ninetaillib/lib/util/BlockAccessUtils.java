/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import keri.ninetaillib.lib.block.BlockBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAccessUtils {

    public static int getBlockMetadata(IBlockState state){
        return state.getValue(BlockBase.META_DATA);
    }

    public static int getBlockMetadata(IBlockAccess world, BlockPos pos){
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        return state.getValue(BlockBase.META_DATA);
    }

    public static boolean setBlockMetadata(World world, BlockPos pos, int meta, int flags){
        IBlockState oldState = world.getBlockState(pos).getActualState(world, pos);
        IBlockState newState = oldState.withProperty(BlockBase.META_DATA, meta);
        return world.setBlockState(pos, newState, flags);
    }

}

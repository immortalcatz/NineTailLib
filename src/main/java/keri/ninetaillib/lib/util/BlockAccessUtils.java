/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import keri.ninetaillib.lib.property.CommonProperties;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAccessUtils {

    public static int getBlockMetadata(IBlockState state){
        return state.getValue(CommonProperties.META_DATA);
    }

    public static int getBlockMetadata(IBlockAccess world, BlockPos pos){
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        return state.getValue(CommonProperties.META_DATA);
    }

    public static boolean setBlockMetadata(World world, BlockPos pos, int meta, int flags){
        IBlockState oldState = world.getBlockState(pos).getActualState(world, pos);
        IBlockState newState = oldState.withProperty(CommonProperties.META_DATA, meta);
        return world.setBlockState(pos, newState, flags);
    }

    public static IBlockState getMetaState(Block block, int meta){
        return block.getDefaultState().withProperty(CommonProperties.META_DATA, meta);
    }

}

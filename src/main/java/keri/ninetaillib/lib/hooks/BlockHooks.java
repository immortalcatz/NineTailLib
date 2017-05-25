/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.hooks;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockHooks {

    private static List<IBlockState> allowedFenceConnections = Lists.newArrayList();
    private static List<IBlockState> allowedWallConnections = Lists.newArrayList();

    //Method Replacement
    //Owner class   net/minecraft/block/BlockFence
    //Needle        blockFence_n_canConnectTo
    //Replacement   blockFence_r_canConnectTo
    public static boolean canFenceConnectTo(IBlockAccess world, BlockPos pos){
        for(EnumFacing side : EnumFacing.VALUES){
            IBlockState state = world.getBlockState(pos.offset(side));

            for(IBlockState allowedState : allowedFenceConnections){
                return state == allowedState;
            }
        }

        return false;
    }

    //Method Replacement
    //Owner class   net/minecraft/block/BlockWall
    //Needle        blockWall_n_canConnectTo
    //Replacement   blockWall_r_canConnectTo
    public static boolean canWallConnectTo(IBlockAccess world, BlockPos pos){
        for(EnumFacing side : EnumFacing.VALUES){
            IBlockState state = world.getBlockState(pos.offset(side));

            for(IBlockState allowedState : allowedWallConnections){
                return state == allowedState;
            }
        }

        return false;
    }

    public static void registerFenceConnection(IBlockState state){
        allowedFenceConnections.add(state);
    }

    public static void registerWallConnection(IBlockState state){
        allowedWallConnections.add(state);
    }

}

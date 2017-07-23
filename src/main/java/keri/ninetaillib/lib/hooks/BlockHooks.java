/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.hooks;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockHooks {

    private static List<IBlockState> FENCE_CONNECTIONS = Lists.newArrayList();
    private static List<IBlockState> WALL_CONNECTIONS = Lists.newArrayList();
    private static List<Block> PANE_CONNECTIONS = Lists.newArrayList();

    //Method Replacement
    //Owner class   net/minecraft/block/BlockFence
    //Needle        blockFence_n_canConnectTo
    //Replacement   blockFence_r_canConnectTo
    public static boolean canFenceConnectTo(IBlockAccess world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        return canFenceConnectTo(state) || FENCE_CONNECTIONS.contains(state);
    }

    @SuppressWarnings("deprecation")
    private static boolean canFenceConnectTo(IBlockState state){
        Block block = state.getBlock();
        return block == Blocks.BARRIER ? false : (!(block instanceof BlockFence) && !(block instanceof BlockFenceGate) ? (block.getMaterial(state).isOpaque() && state.isFullCube() ? block.getMaterial(state) != Material.GOURD : false) : true);
    }

    //Method Replacement
    //Owner class   net/minecraft/block/BlockWall
    //Needle        blockWall_n_canConnectTo
    //Replacement   blockWall_r_canConnectTo
    public static boolean canWallConnectTo(IBlockAccess world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        return canWallConnectTo(state) || WALL_CONNECTIONS.contains(state);
    }

    @SuppressWarnings("deprecation")
    private static boolean canWallConnectTo(IBlockState state){
        Block block = state.getBlock();
        return block == Blocks.BARRIER ? false : !(block instanceof BlockFenceGate ? (block.getMaterial(state).isOpaque() && state.isFullCube() ? block.getMaterial(state) != Material.GOURD : false) : true);
    }

    //Method Replacement
    //Owner class   net/minecraft/block/BlockPane
    //Needle        blockPane_n_canPaneConnectToBlock
    //Replacement   blockPane_r_canPaneConnectToBlock
    public static boolean canPaneConnectToBlock(Block block){
        IBlockState state = block.getDefaultState();
        return canPaneConnectToBlock(state) || PANE_CONNECTIONS.contains(block);
    }

    private static boolean canPaneConnectToBlock(IBlockState state){
        Block block = state.getBlock();
        return state.isFullCube() || block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.STAINED_GLASS_PANE || block instanceof BlockPane;
    }

    public static void registerFenceConnection(IBlockState state){
        FENCE_CONNECTIONS.add(state);
    }

    public static void registerWallConnection(IBlockState state){
        WALL_CONNECTIONS.add(state);
    }

    public static void registerPaneConnection(Block block){
        PANE_CONNECTIONS.add(block);
    }

}

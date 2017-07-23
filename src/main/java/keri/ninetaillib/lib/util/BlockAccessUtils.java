/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import keri.ninetaillib.lib.property.CommonProperties;
import keri.ninetaillib.lib.property.IPropertyHandler;
import keri.ninetaillib.lib.property.PropertyDataHolder;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

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

    public static PropertyDataHolder getPropertyData(IBlockState state){
        if(state != null && state instanceof IExtendedBlockState){
            return ((IExtendedBlockState)state).getValue(CommonProperties.DATA_HOLDER_PROPERTY);
        }

        return new PropertyDataHolder();
    }

    public static PropertyDataHolder getPropertyData(IBlockAccess world, BlockPos pos){
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);

        if(state != null && state instanceof IExtendedBlockState){
            return ((IExtendedBlockState)state).getValue(CommonProperties.DATA_HOLDER_PROPERTY);
        }

        return new PropertyDataHolder();
    }

    public static boolean setPropertyData(World world, BlockPos pos, PropertyDataHolder data, int flags){
        IBlockState oldState = world.getBlockState(pos).getActualState(world, pos);
        IBlockState newState = ((IExtendedBlockState)oldState).withProperty(CommonProperties.DATA_HOLDER_PROPERTY, data);
        return world.setBlockState(pos, newState, flags);
    }

    public static IBlockState getPropertyState(IBlockState state, PropertyDataHolder dataHolder){
        return ((IExtendedBlockState)state).withProperty(CommonProperties.DATA_HOLDER_PROPERTY, dataHolder);
    }

    public static boolean updatePropertyData(IPropertyHandler handler, World world, BlockPos pos, int flags){
        PropertyDataHolder dataHolder = getPropertyData(world.getBlockState(pos).getActualState(world, pos));
        handler.handle(dataHolder);
        IBlockState oldState = world.getBlockState(pos).getActualState(world, pos);
        IBlockState newState = ((IExtendedBlockState)oldState).withProperty(CommonProperties.DATA_HOLDER_PROPERTY, dataHolder);
        return world.setBlockState(pos, newState, flags);
    }

}

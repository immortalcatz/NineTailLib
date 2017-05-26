/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.render.block.BlockRenderingRegistry;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.EnumMap;

@SideOnly(Side.CLIENT)
public class RenderingRegistry {

    private static int renderId = 0;
    private static EnumMap<EnumBlockRenderType, IBlockRenderingHandler> blockRenderingHandlers = Maps.newEnumMap(EnumBlockRenderType.class);

    public static EnumBlockRenderType getNextAvailableType(){
        return BlockRenderingRegistry.createRenderType("ibrh_" + renderId++);
    }

    /**
    public static void registerRenderingHandler(IBlockRenderingHandler handler, Block block){
        BlockRenderingAdapter adapter = new BlockRenderingAdapter(handler);
        ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName().toString());
        ModelResourceLocation locationInventory = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomStateMapper(block, buildStateMap(block));

        if(block instanceof BlockBase){
            BlockBase blockBase = (BlockBase)block;

            if(blockBase.getSubNames() != null){
                IntStream.range(0, blockBase.getSubNames().length).forEach(meta -> ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, locationInventory));
            }
            else{
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, locationInventory);
            }
        }
        else{
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, locationInventory);
        }

        BlockRenderingRegistry.registerRenderer(handler.getRenderType(), adapter);
        ModelRegistryHelper.register(location, new DummyBakedModel());
        ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(block), adapter);
    }
     */

    public static void registerRenderingHandler(IBlockRenderingHandler handler){
        blockRenderingHandlers.put(handler.getRenderType(), handler);
    }

    private static StateMap buildStateMap(Block block){
        StateMap.Builder builder = new StateMap.Builder();
        BlockStateContainer container = block.getBlockState();

        if(container != null){
            Collection<IProperty<?>> properties = container.getProperties();

            if(properties != null){
                if(!properties.isEmpty()){
                    for(IProperty<?> property : properties){
                        builder.ignore(property);
                    }
                }
            }
        }

        return builder.build();
    }

}

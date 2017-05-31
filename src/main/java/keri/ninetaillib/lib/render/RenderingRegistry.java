/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.model.DummyBakedModel;
import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.block.BlockRenderingRegistry;
import com.google.common.collect.Maps;
import keri.ninetaillib.lib.block.BlockBase;
import keri.ninetaillib.lib.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class RenderingRegistry {

    private static int blockRenderId = 0;
    private static int itemRenderId = 0;
    private static Map<EnumBlockRenderType, IBlockRenderingHandler> blockRenderingHandlers = Maps.newHashMap();
    private static Map<EnumItemRenderType, IItemRenderingHandler> itemRenderingHandlers = Maps.newHashMap();

    public static EnumBlockRenderType getNextAvailableType(){
        return BlockRenderingRegistry.createRenderType("ibrh_" + blockRenderId++);
    }

    public static EnumItemRenderType getNextAvailableItemType(){
        return addItemRenderType("iirh_" + itemRenderId++);
    }

    public static void registerRenderingHandler(IBlockRenderingHandler handler){
        blockRenderingHandlers.put(handler.getRenderType(), handler);
    }

    public static void registerRenderingHandler(IItemRenderingHandler handler){
        itemRenderingHandlers.put(handler.getRenderType(), handler);
    }

    public static void registerBlock(Block block){
        @SuppressWarnings("deprecation")
        IBlockRenderingHandler handler = blockRenderingHandlers.get(block.getRenderType(block.getDefaultState()));
        BlockRenderingAdapter adapter = new BlockRenderingAdapter(handler);
        ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName().toString());
        ModelResourceLocation locationInventory = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomStateMapper(block, buildStateMap(block));

        if(block instanceof BlockBase){
            BlockBase blockBase = (BlockBase)block;

            if(blockBase.getSubNames() != null){
                for(int i = 0; i < blockBase.getSubNames().length; i++){
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, locationInventory);
                }
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

    public static void registerItem(Item item){
        if(item instanceof ItemBase){
            ItemBase itemBase = (ItemBase)item;
            IItemRenderingHandler handler = itemRenderingHandlers.get(itemBase.getRenderType());
            ItemRenderingAdapter adapter = new ItemRenderingAdapter(handler);
            ModelResourceLocation location = new ModelResourceLocation(itemBase.getRegistryName(), "inventory");

            if(itemBase.getSubNames() != null){
                for(int i = 0; i < itemBase.getSubNames().length; i++){
                    ModelLoader.setCustomModelResourceLocation(itemBase, i, location);
                }
            }
            else{
                ModelLoader.setCustomModelResourceLocation(itemBase, 0, location);
            }

            ModelRegistryHelper.registerItemRenderer(itemBase, adapter);
        }
        else{
            throw new IllegalArgumentException("Item has to be an instance of ItemBase!");
        }
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

    private static EnumItemRenderType addItemRenderType(String name){
        return EnumHelper.addEnum(EnumItemRenderType.class, name, new Class<?>[0]);
    }

}

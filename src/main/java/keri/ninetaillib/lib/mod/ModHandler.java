/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.mod;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.block.BlockBase;
import keri.ninetaillib.lib.config.*;
import keri.ninetaillib.lib.logger.IModLogger;
import keri.ninetaillib.lib.logger.ModLogger;
import keri.ninetaillib.lib.logger.SimpleModLogger;
import keri.ninetaillib.lib.util.ReflectionUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ModHandler {

    private Object modInstance;
    private IModLogger logger;
    private IModConfig config;
    private List<Class<?>> contentLoaders = Lists.newArrayList();

    public ModHandler(Object modInstance){
        this.modInstance = modInstance;
    }

    public void handlePreInit(FMLPreInitializationEvent event){
        this.logger = this.setupLogger(event, this.modInstance);
        this.config = this.setupConfig(event, this.modInstance, this.logger);
        this.discoverClasses(event);
        this.handleContentLoaderPreInit(event);
    }

    public void handleInit(FMLInitializationEvent event){
        this.handleContentLoaderInit(event);
    }

    public void handlePostInit(FMLPostInitializationEvent event){

    }

    private void discoverClasses(FMLPreInitializationEvent event){
        for(ASMDataTable.ASMData data : event.getAsmData().getAll(ContentLoader.class.getName())){
            try{
                Class<?> initializerClass = Class.forName(data.getClassName());
                ContentLoader annotation = initializerClass.getAnnotation(ContentLoader.class);

                if(event.getModMetadata().modId.equals(annotation.modid())){
                    if(initializerClass != null){
                        this.contentLoaders.add(initializerClass);
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void handleContentLoaderPreInit(FMLPreInitializationEvent event){
        for(Class<?> contentLoader : this.contentLoaders){
            for(Field field : contentLoader.getFields()){
                Object fieldObject = ReflectionUtils.getField(field, contentLoader);

                if(fieldObject != null){
                    if(fieldObject instanceof Block){
                        Block block = (Block)fieldObject;
                        GameRegistry.register(block);

                        if(block instanceof BlockBase){
                            BlockBase blockBase = (BlockBase)block;
                            GameRegistry.register(blockBase.getItemBlock().setRegistryName(blockBase.getRegistryName()));
                        }
                        else{
                            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
                        }
                    }
                    else if(fieldObject instanceof Item){
                        Item item = (Item)fieldObject;
                        GameRegistry.register(item);
                    }
                }
            }
        }
    }

    private void handleContentLoaderInit(FMLInitializationEvent event){
        for(Class<?> contentLoader : this.contentLoaders){
            for(Field field : contentLoader.getFields()){
                Object fieldObject = ReflectionUtils.getField(field, contentLoader);

                if(fieldObject != null){
                    if(fieldObject instanceof TileEntity){
                        TileEntity tile = (TileEntity)fieldObject;
                        String modid = tile.getBlockType().getRegistryName().getResourceDomain();
                        String name = tile.getBlockType().getRegistryName().getResourcePath();
                        GameRegistry.registerTileEntity(tile.getClass(), "tile." + modid + "." + name);
                    }
                }
            }
        }
    }

    private IModConfig setupConfig(FMLPreInitializationEvent event, Object modInstance, IModLogger logger){
        if(ReflectionUtils.fieldExists(modInstance.getClass(), IModConfig.class)){
            Field field = ReflectionUtils.getFieldForAnnotation(ModConfig.class, modInstance.getClass());
            ModConfig configAnnotation = field.getAnnotation(ModConfig.class);
            Class<?> configHandler = configAnnotation.config();
            IModConfig config = null;

            if(configHandler.isAnnotationPresent(ModConfigHandler.class)){
                ModConfigHandler handlerAnnotation = configHandler.getAnnotation(ModConfigHandler.class);
                Object configHandlerInstance = ReflectionUtils.createInstance(configHandler);
                ConfigCategories categories = new ConfigCategories();
                ConfigProperties properties = new ConfigProperties();
                Method methodAddCategories = ReflectionUtils.getMethodForAnnotation(ModConfigHandler.ConfigCategories.class, configHandler);
                Method methodAddProperties = ReflectionUtils.getMethodForAnnotation(ModConfigHandler.ConfigProperties.class, configHandler);
                ReflectionUtils.invokeMethod(methodAddCategories, configHandlerInstance, categories);
                ReflectionUtils.invokeMethod(methodAddProperties, configHandlerInstance, properties);
                File file = null;

                if(!handlerAnnotation.fileName().equals("")){
                    String fileName = handlerAnnotation.fileName() + ".cfg";
                    file = new File(event.getModConfigurationDirectory(), fileName);
                }
                else{
                    String fileName = event.getModMetadata().modId + ".cfg";
                    file = new File(event.getModConfigurationDirectory(), fileName);
                }

                config = new SimpleModConfig(file, categories, properties, logger);
            }

            ReflectionUtils.setField(field, IModConfig.class, config);
            return config;
        }

        return null;
    }

    private IModLogger setupLogger(FMLPreInitializationEvent event, Object modInstance){
        if(ReflectionUtils.fieldExists(modInstance.getClass(), IModLogger.class)){
            Field field = ReflectionUtils.getFieldForAnnotation(ModLogger.class, modInstance.getClass());
            IModLogger logger = new SimpleModLogger(event);
            ReflectionUtils.setField(field, IModLogger.class, logger);
            return logger;
        }

        return null;
    }

}

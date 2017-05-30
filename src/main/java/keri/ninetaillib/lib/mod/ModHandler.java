/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.mod;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.config.*;
import keri.ninetaillib.lib.logger.IModLogger;
import keri.ninetaillib.lib.logger.ModLogger;
import keri.ninetaillib.lib.logger.SimpleModLogger;
import keri.ninetaillib.lib.util.ReflectionUtils;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ModHandler {

    private Object modInstance;
    private List<Class<?>> contentLoaders = Lists.newArrayList();
    private List<IContentRegister> content = Lists.newArrayList();

    public ModHandler(Object modInstance){
        this.modInstance = modInstance;
    }

    public void handlePreInit(FMLPreInitializationEvent event){
        this.setupLogger(event, this.modInstance);
        this.setupConfig(event, this.modInstance);
        this.setupContentLoaders(event);

        for(IContentRegister object : this.content){
            object.handlePreInit(event);
        }
    }

    public void handleInit(FMLInitializationEvent event){
        for(IContentRegister object : this.content){
            object.handleInit(event);
        }
    }

    public void handlePostInit(FMLPostInitializationEvent event){
        for(IContentRegister object : this.content){
            object.handlePostInit(event);
        }
    }

    public void register(Object object){
        if(object instanceof IContentRegister){
            this.content.add((IContentRegister)object);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPreInit(FMLPreInitializationEvent event){
        for(IContentRegister object : this.content){
            object.handleClientPreInit(event);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleClientInit(FMLInitializationEvent event){
        for(IContentRegister object : this.content){
            object.handleInit(event);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPostInit(FMLPostInitializationEvent event){
        for(IContentRegister object : this.content){
            object.handleClientPostInit(event);
        }
    }

    private void setupConfig(FMLPreInitializationEvent event, Object modInstance){
        if(ReflectionUtils.fieldExists(modInstance.getClass(), IModConfig.class)){
            Field field = ReflectionUtils.getFieldForAnnotation(ModConfig.class, modInstance.getClass());
            Class<?> configHandlerClass = null;

            for(ASMDataTable.ASMData data : event.getAsmData().getAll(ModConfigHandler.class.getName())){
                try{
                    if(configHandlerClass == null){
                        Class<?> clazz = Class.forName(data.getClassName());
                        ModConfigHandler annotation = clazz.getAnnotation(ModConfigHandler.class);

                        if(event.getModMetadata().modId.equals(annotation.modid())){
                            configHandlerClass = clazz;
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            ModConfigHandler handlerAnnotation = configHandlerClass.getAnnotation(ModConfigHandler.class);
            Object configHandlerInstance = ReflectionUtils.createInstance(configHandlerClass);
            ConfigCategories categories = new ConfigCategories();
            ConfigProperties properties = new ConfigProperties();
            Method methodAddCategories = ReflectionUtils.getMethodForAnnotation(ModConfigHandler.ConfigCategories.class, configHandlerClass);
            Method methodAddProperties = ReflectionUtils.getMethodForAnnotation(ModConfigHandler.ConfigProperties.class, configHandlerClass);
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

            SimpleModLogger logger = new SimpleModLogger(event);
            ReflectionUtils.setField(field, IModConfig.class, new SimpleModConfig(file, categories, properties, logger));
        }
    }

    private void setupLogger(FMLPreInitializationEvent event, Object modInstance){
        if(ReflectionUtils.fieldExists(modInstance.getClass(), IModLogger.class)){
            Field field = ReflectionUtils.getFieldForAnnotation(ModLogger.class, modInstance.getClass());
            IModLogger logger = new SimpleModLogger(event);
            ReflectionUtils.setField(field, IModLogger.class, logger);
        }
    }

    private void setupContentLoaders(FMLPreInitializationEvent event){
        for(ASMDataTable.ASMData data : event.getAsmData().getAll(ContentLoader.class.getName())){
            try{
                Class<?> contentLoaderClass = Class.forName(data.getClassName());
                ContentLoader annotation = contentLoaderClass.getAnnotation(ContentLoader.class);

                if(event.getModMetadata().modId.equals(annotation.modid())){
                    for(Field field : contentLoaderClass.getFields()){
                        Object fieldInstance = ReflectionUtils.getField(field, contentLoaderClass);

                        if(fieldInstance instanceof IContentRegister){
                            this.content.add((IContentRegister)fieldInstance);
                        }
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}

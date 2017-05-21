/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.mod;

import keri.ninetaillib.lib.config.*;
import keri.ninetaillib.lib.logger.IModLogger;
import keri.ninetaillib.lib.logger.ModLogger;
import keri.ninetaillib.lib.logger.SimpleModLogger;
import keri.ninetaillib.lib.util.ReflectionUtils;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ModHandler {

    private Object modInstance;
    private IModLogger logger;
    private IModConfig config;

    public ModHandler(Object modInstance){
        this.modInstance = modInstance;
    }

    public void handlePreInit(FMLPreInitializationEvent event){
        this.logger = this.setupLogger(event, this.modInstance);
        this.config = this.setupConfig(event, this.modInstance, this.logger);
    }

    public void handleInit(FMLInitializationEvent event){

    }

    public void handlePostInit(FMLPostInitializationEvent event){

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

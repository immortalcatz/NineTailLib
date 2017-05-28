/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.mod;

import com.google.common.collect.Lists;
import keri.ninetaillib.lib.util.ReflectionUtils;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ModClientHandler {

    private List<Class<?>> contentLoaders = Lists.newArrayList();

    public void handlePreInit(FMLPreInitializationEvent event){
        this.handleContentLoaderPreInit(event);
    }

    public void handleInit(FMLInitializationEvent event){
        this.handleContentLoaderInit(event);
    }

    public void handlePostInit(FMLPostInitializationEvent event){
        this.handleContentLoaderPostInit(event);
    }

    private void handleContentLoaderPreInit(FMLPreInitializationEvent event){
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

        for(Class<?> contentLoader : this.contentLoaders){
            for(Field field : contentLoader.getFields()){
                Object fieldObject = ReflectionUtils.getField(field, contentLoader);

                if(fieldObject != null){
                    if(fieldObject instanceof IContentRegister){
                        ((IContentRegister)fieldObject).handleClientPreInit(event);
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
                    if(fieldObject instanceof IContentRegister){
                        ((IContentRegister)fieldObject).handleClientInit(event);
                    }
                }
            }
        }
    }

    private void handleContentLoaderPostInit(FMLPostInitializationEvent event){
        for(Class<?> contentLoader : this.contentLoaders){
            for(Field field : contentLoader.getFields()){
                Object fieldObject = ReflectionUtils.getField(field, contentLoader);

                if(fieldObject != null){
                    if(fieldObject instanceof IContentRegister){
                        ((IContentRegister)fieldObject).handleClientPostInit(event);
                    }
                }
            }
        }
    }

}

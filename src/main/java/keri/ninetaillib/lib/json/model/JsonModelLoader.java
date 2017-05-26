/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

import com.google.common.collect.Maps;
import keri.ninetaillib.lib.json.JsonFileLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class JsonModelLoader {

    private JsonFileLoader fileLoader = new JsonFileLoader();
    private Map<String, ModelData> modelData = Maps.newHashMap();

    public void registerModel(String name, ResourceLocation location){
        ModelData data = this.fileLoader.loadWithParser(location, new JsonModelParser());
        this.modelData.put(name, data);
    }

    public ModelData getModelData(String name){
        return this.modelData.get(name);
    }

}

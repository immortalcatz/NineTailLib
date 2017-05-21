/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import keri.ninetaillib.lib.config.property.IConfigProperty;

import java.util.Map;

public class ConfigProperties {

    private Map<String, IConfigProperty<? extends Object>> properties;

    public ConfigProperties(){
        this.properties = Maps.newHashMap();
    }

    public void addProperty(String name, IConfigProperty<?> property){
        if(name != null && name != ""){
            if(property != null){
                this.properties.put(name, property);
            }
            else{
                throw new IllegalArgumentException("Property can't be null!");
            }
        }
        else{
            throw new IllegalArgumentException("Name can't be null or empty!");
        }
    }

    public IConfigProperty<?> getProperty(String name){
        if(name != null && name != ""){
            if(this.properties.containsKey(name)){
                return this.properties.get(name);
            }
            else{
                throw new IllegalArgumentException("Unknown property!");
            }
        }
        else{
            throw new IllegalArgumentException("Name can't be null or empty!");
        }
    }

    public ImmutableMap<String, IConfigProperty<?>> getProperties(){
        return ImmutableMap.copyOf(this.properties);
    }

}

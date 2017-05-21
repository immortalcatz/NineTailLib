/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

public class ConfigIntArray extends ConfigProperty<int[]> {

    public ConfigIntArray(String key, String category){
        this(category, key, new int[0]);
    }

    public ConfigIntArray(String key, String category, int[] defaultValue){
        this(category, key, null, defaultValue);
    }

    public ConfigIntArray(String key, String category, String comment){
        this(category, key, comment, new int[0]);
    }

    public ConfigIntArray(String key, String category, String commment, int[] defaultValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addProperty(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getIntList();
    }

}

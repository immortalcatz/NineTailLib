/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

import java.util.List;
import java.util.stream.IntStream;

public class ConfigString extends ConfigProperty<String> {

    public ConfigString(String key, String category){
        this(category, key, null, "default", null);
    }

    public ConfigString(String key, String category, String defaultValue){
        this(category, key, null, defaultValue, null);
    }

    public ConfigString(String key, String category, String comment, String defaultValue, List<String> allowedValues){
        this.category = category;
        this.key = key;
        this.comment = comment;
        this.defaultValue = defaultValue;
        this.allowedValues = allowedValues;
    }

    @Override
    public void addProperty(Configuration config) {
        if(this.allowedValues != null){
            String[] allowedValues = new String[this.allowedValues.size()];
            IntStream.range(0, allowedValues.length).forEach(index -> allowedValues[index] = this.allowedValues.get(index));
            this.value = config.get(this.category, this.key, this.defaultValue, this.comment, allowedValues).getString();
        }
        else{
            this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getString();
        }
    }

}

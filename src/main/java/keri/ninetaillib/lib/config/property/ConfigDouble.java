/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

public class ConfigDouble extends ConfigProperty<Double> {

    public ConfigDouble(String key, String category){
        this(category, key, 0);
    }

    public ConfigDouble(String key, String category, double defaultValue){
        this(category, key, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigDouble(String key, String category, double defaultValue, double minValue, double maxValue){
        this(category, key, null, defaultValue, minValue, maxValue);
    }

    public ConfigDouble(String key, String category, String comment){
        this(category, key, comment, 0);
    }

    public ConfigDouble(String key, String category, String comment, double defaultValue){
        this(category, key, comment, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigDouble(String category, String key, String commment, double defaultValue, double minValue, double maxValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void addProperty(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment, this.minValue, this.maxValue).getDouble();
    }

}

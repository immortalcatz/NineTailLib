/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

public class ConfigDoubleArray extends ConfigProperty<double[]> {

    public ConfigDoubleArray(String key, String category){
        this(category, key, new double[0]);
    }

    public ConfigDoubleArray(String key, String category, double[] defaultValue){
        this(category, key, null, defaultValue);
    }

    public ConfigDoubleArray(String key, String category, String comment){
        this(category, key, comment, new double[0]);
    }

    public ConfigDoubleArray(String key, String category, String commment, double[] defaultValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addProperty(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getDoubleList();
    }

}

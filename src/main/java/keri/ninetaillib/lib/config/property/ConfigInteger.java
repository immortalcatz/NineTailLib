/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

public class ConfigInteger extends ConfigProperty<Integer> {

    public ConfigInteger(String key, String category, String comment, int defaultValue){
        this(key, comment, category, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigInteger(String key, String category, int defaultValue){
        this(key, category, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigInteger(String key, String category, int defaultValue, int minValue, int maxValue){
        this(key, null, category, defaultValue, minValue, maxValue);
    }

    public ConfigInteger(String key, String comment, String category, int defaultValue, int minValue, int maxValue){
        this.key = key;
        this.comment = comment;
        this.category = category;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void addProperty(Configuration config) {
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment, this.minValue, this.maxValue).getInt();
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

public class ConfigBoolean extends ConfigProperty<Boolean> {

    public ConfigBoolean(String key, String category){
        this(key, category, true);
    }

    public ConfigBoolean(String key, String category, boolean defaultValue){
        this(key, null, category, defaultValue);
    }

    public ConfigBoolean(String key, String comment, String category, boolean defaultValue){
        this.key = key;
        this.comment = comment;
        this.category = category;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addProperty(Configuration config) {
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getBoolean();
    }

}

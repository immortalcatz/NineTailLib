/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.init;

import keri.ninetaillib.lib.config.ConfigCategories;
import keri.ninetaillib.lib.config.ConfigProperties;
import keri.ninetaillib.lib.config.ModConfigHandler;
import keri.ninetaillib.lib.config.property.ConfigBoolean;
import keri.ninetaillib.mod.util.ModPrefs;

@ModConfigHandler(fileName = ModPrefs.NAME)
public class NTLConfig {

    @ModConfigHandler.ConfigCategories
    public void addCategories(ConfigCategories categories){
        categories.addCategory("general", "general settings related to FFramework");
    }

    @ModConfigHandler.ConfigProperties
    public void addProperties(ConfigProperties properties){
        properties.addProperty("enable_debug", new ConfigBoolean("enableDebug", "general", false));
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.config;

import keri.ninetaillib.lib.config.property.IConfigProperty;
import keri.ninetaillib.lib.logger.IModLogger;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.Map;

public class SimpleModConfig implements IModConfig {

    private ConfigProperties properties;

    public SimpleModConfig(File file, ConfigCategories categories, ConfigProperties properties, IModLogger logger){
        Configuration config = new Configuration(file);
        StopWatch timer = new StopWatch();
        logger.logInfo("Loading config...");
        timer.start();
        config.load();

        if(categories != null && !categories.getCategories().isEmpty()){
            for(Pair<String, String> category : categories.getCategories()){
                config.addCustomCategoryComment(category.getLeft(), category.getRight());
            }
        }

        if(properties != null && !properties.getProperties().isEmpty()){
            for(Map.Entry<String, IConfigProperty<?>> property : properties.getProperties().entrySet()){
                property.getValue().addProperty(config);
            }
        }

        config.save();
        timer.stop();
        logger.logInfo("Done loading config in " + timer.getTime() + "ms!");
        this.properties = properties;
    }

    @Override
    public IConfigProperty<? extends Object> getProperty(String name) {
        return this.properties.getProperty(name);
    }

}

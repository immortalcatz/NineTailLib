/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.config;

import keri.ninetaillib.lib.config.property.IConfigProperty;

public interface IModConfig {

    IConfigProperty<? extends Object> getProperty(String name);

}

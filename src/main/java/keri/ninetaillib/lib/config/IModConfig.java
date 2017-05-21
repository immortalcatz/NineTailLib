/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config;

import keri.ninetaillib.lib.config.property.IConfigProperty;

public interface IModConfig {

    IConfigProperty<? extends Object> getProperty(String name);

}

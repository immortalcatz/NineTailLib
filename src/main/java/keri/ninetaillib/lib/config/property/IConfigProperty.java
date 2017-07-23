/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.config.property;

import net.minecraftforge.common.config.Configuration;

public interface IConfigProperty<T extends Object> {

    void addProperty(Configuration config);

    T getValue();

}

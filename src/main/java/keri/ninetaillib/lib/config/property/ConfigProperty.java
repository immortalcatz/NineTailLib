/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.config.property;

import java.util.List;

public abstract class ConfigProperty<T extends Object> implements IConfigProperty<T> {

    protected String key;
    protected String category;
    protected String comment;
    protected T value;
    protected T defaultValue;
    protected T minValue;
    protected T maxValue;
    protected List<T> allowedValues;

    @Override
    public T getValue() {
        return this.value;
    }

}

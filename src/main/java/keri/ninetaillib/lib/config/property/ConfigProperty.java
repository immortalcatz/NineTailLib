/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
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

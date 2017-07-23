/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.property;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertySpecial implements IUnlistedProperty<PropertyDataHolder> {

    @Override
    public String getName() {
        return "special_data";
    }

    @Override
    public boolean isValid(PropertyDataHolder value) {
        return true;
    }

    @Override
    public Class<PropertyDataHolder> getType() {
        return PropertyDataHolder.class;
    }

    @Override
    public String valueToString(PropertyDataHolder value) {
        return value.toString();
    }

}

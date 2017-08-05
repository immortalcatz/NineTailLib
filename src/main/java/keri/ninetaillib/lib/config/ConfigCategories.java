/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ConfigCategories {

    private List<Pair<String, String>> categories;

    public ConfigCategories(){
        this.categories = Lists.newArrayList();
    }

    public void addCategory(String name, String comment){
        if(name != null && name != ""){
            this.categories.add(Pair.of(name, comment));
        }
        else{
            throw new IllegalArgumentException("Category name can't be null or empty!");
        }
    }

    public ImmutableList<Pair<String, String>> getCategories(){
        return ImmutableList.copyOf(this.categories);
    }

}

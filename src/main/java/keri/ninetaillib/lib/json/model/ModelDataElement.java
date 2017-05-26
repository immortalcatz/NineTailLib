/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

public enum ModelDataElement {

    NAME(0, "name"),
    BOUNDS(1, "bounds"),
    TEXTURE(2, "texture"),
    TRANSLATION(3, "translation"),
    ROTATION(4, "rotation"),
    SCALE(5, "scale"),
    COLOR(6, "color"),
    UV_TRANSLATION(7, "uv_translation"),
    UV_ROTATION(8, "uv_rotation"),
    UV_SCALE(9, "uv_scale");

    public static final ModelDataElement[] VALUES = new ModelDataElement[]{
            NAME,
            BOUNDS,
            TEXTURE,
            TRANSLATION,
            ROTATION,
            SCALE,
            COLOR,
            UV_TRANSLATION,
            UV_ROTATION,
            UV_SCALE
    };
    private int index;
    private String key;

    ModelDataElement(int index, String key){
        this.index = index;
        this.key = key;
    }

    public int getIndex(){
        return this.index;
    }

    public String getKey(){
        return this.key;
    }

}

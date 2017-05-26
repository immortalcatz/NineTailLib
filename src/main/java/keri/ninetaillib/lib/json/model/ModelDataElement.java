/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

public enum ModelDataElement {

    BOUNDS(0, "bounds"),
    TEXTURE(1, "texture"),
    TRANSLATION(2, "translation"),
    ROTATION(3, "rotation"),
    SCALE(4, "scale"),
    COLOR(5, "color"),
    UV_TRANSLATION(6, "uv_translation"),
    UV_ROTATION(7, "uv_rotation"),
    UV_SCALE(8, "uv_scale");

    public static final ModelDataElement[] VALUES = new ModelDataElement[]{
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

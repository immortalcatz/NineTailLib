/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

public enum VertexPosition {

    TOP_LEFT(0, 0, "topleft"),
    TOP_RIGHT(1, 0, "topright"),
    BOTTOM_LEFT(2, 0, "bottomleft"),
    BOTTOM_RIGHT(3, 0, "bottomright");

    public static final VertexPosition[] VALUES = new VertexPosition[]{TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};
    private int index;
    private int vertexIndex;
    private String name;

    VertexPosition(int index, int vertexIndex, String name){
        this.index = index;
        this.vertexIndex = vertexIndex;
        this.name = name;
    }

    public int getIndex(){
        return this.index;
    }

    public int getVertexIndex(){
        return this.vertexIndex;
    }

    public String getName(){
        return this.name;
    }

}
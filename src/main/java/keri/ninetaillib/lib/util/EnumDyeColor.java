/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum EnumDyeColor {

    BLACK(0, "black", new ColourRGBA(20, 20, 20, 255)),
    RED(1, "red", new ColourRGBA(255, 10, 10, 255)),
    GREEN(2, "green", new ColourRGBA(10, 200, 10, 255)),
    BROWN(3, "brown", new ColourRGBA(200, 120, 0, 255)),
    BLUE(4, "blue", new ColourRGBA(0, 0, 180, 255)),
    PURPLE(5, "purple", new ColourRGBA(100, 0, 200, 255)),
    CYAN(6, "cyan", new ColourRGBA(20, 130, 130, 255)),
    LIGHT_GRAY(7, "light_gray", new ColourRGBA(150, 150, 150, 255)),
    GRAY(8, "gray", new ColourRGBA(100, 100, 100, 255)),
    PINK(9, "pink", new ColourRGBA(255, 120, 120, 255)),
    LIME(10, "lime", new ColourRGBA(90, 255, 10, 255)),
    YELLOW(11, "yellow", new ColourRGBA(230, 230, 10, 255)),
    LIGHT_BLUE(12, "light_blue", new ColourRGBA(120, 120, 255, 255)),
    MAGENTA(13, "magenta", new ColourRGBA(220, 10, 90, 255)),
    ORANGE(14, "orange", new ColourRGBA(240, 130, 10, 255)),
    WHITE(15, "white", new ColourRGBA(240, 240, 240, 255));

    public static final EnumDyeColor[] VALUES = new EnumDyeColor[]{
            BLACK,
            RED,
            GREEN,
            BROWN,
            BLUE,
            PURPLE,
            CYAN,
            LIGHT_GRAY,
            GRAY,
            PINK,
            LIME,
            YELLOW,
            LIGHT_BLUE,
            MAGENTA,
            ORANGE,
            WHITE
    };
    private int index;
    private String name;
    private Colour color;

    EnumDyeColor(int index, String name, Colour color){
        this.index = index;
        this.name = name;
        this.color = color;
    }

    public int getIndex(){
        return this.index;
    }

    public String getName(){
        return this.name;
    }

    public Colour getColor(){
        return this.color;
    }

    public ItemStack getItemStack(){
        return new ItemStack(Items.DYE, 1, this.index);
    }

    public static String[] toStringArray(){
        String[] array = new String[VALUES.length];

        for(int i = 0; i < array.length; i++){
            array[i] = VALUES[i].name;
        }

        return array;
    }

}

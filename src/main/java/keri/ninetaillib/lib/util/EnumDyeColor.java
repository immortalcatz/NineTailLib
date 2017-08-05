/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.EnumColour;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum EnumDyeColor {

    BLACK(0, "black", EnumColour.BLACK),
    RED(1, "red", EnumColour.RED),
    GREEN(2, "green", EnumColour.GREEN),
    BROWN(3, "brown", EnumColour.BROWN),
    BLUE(4, "blue", EnumColour.BLUE),
    PURPLE(5, "purple", EnumColour.PURPLE),
    CYAN(6, "cyan", EnumColour.CYAN),
    LIGHT_GRAY(7, "light_gray", EnumColour.LIGHT_GRAY),
    GRAY(8, "gray", EnumColour.GRAY),
    PINK(9, "pink", EnumColour.PINK),
    LIME(10, "lime", EnumColour.LIME),
    YELLOW(11, "yellow", EnumColour.YELLOW),
    LIGHT_BLUE(12, "light_blue", EnumColour.LIGHT_BLUE),
    MAGENTA(13, "magenta", EnumColour.MAGENTA),
    ORANGE(14, "orange", EnumColour.ORANGE),
    WHITE(15, "white", EnumColour.WHITE);

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
    private EnumColour parentColor;

    EnumDyeColor(int index, String name, EnumColour parentColor){
        this.index = index;
        this.name = name;
        this.parentColor = parentColor;
    }

    public int getIndex(){
        return this.index;
    }

    public String getName(){
        return this.name;
    }

    public Colour getColor(){
        return this.parentColor.getColour();
    }

    public int rgba(){
        return this.parentColor.rgba();
    }

    public int argb(){
        return this.parentColor.argb();
    }

    public int rgb(){
        return this.parentColor.rgb();
    }

    public String getDyeName(){
        return this.parentColor.getOreDictionaryName();
    }

    public String getOreDictionaryName(){
        return this.parentColor.getOreDictionaryName().substring(3);
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

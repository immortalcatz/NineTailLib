package keri.ninetaillib.property;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.util.IPropertyProvider;

import java.awt.*;

public enum EnumDyeColor implements IPropertyProvider {

    BLACK("black", 0, new Color(16, 16, 16, 255)),
    RED("red", 1, new Color(214, 0, 1, 220)),
    GREEN("green", 2, new Color(0, 166, 0, 220)),
    BROWN("brown", 3, new Color(140, 58, 0, 220)),
    BLUE("blue", 4, new Color(4, 0, 255, 220)),
    PURPLE("purple", 5, new Color(109, 0, 255, 220)),
    CYAN("cyan", 6, new Color(0, 206, 199, 220)),
    LIGHT_GRAY("light_gray", 7, new Color(161, 161, 161, 220)),
    GRAY("gray", 8, new Color(73, 73, 73, 220)),
    PINK("pink", 9, new Color(255, 164, 193, 220)),
    LIME("lime", 10, new Color(110, 255, 0, 220)),
    YELLOW("yellow", 11, new Color(255, 220, 0, 220)),
    LIGHT_BLUE("light_blue", 12, new Color(0, 180, 255, 220)),
    MAGENTA("magenta", 13, new Color(246, 0, 255, 220)),
    ORANGE("orange", 14, new Color(255, 77, 0, 220)),
    WHITE("white", 15, new Color(255, 255, 255, 220));

    private String name;
    private int meta;
    private Color color;

    EnumDyeColor(String name, int meta, Color color){
        this.name = name;
        this.meta = meta;
        this.color = color;
    }

    @Override
    public int getID() {
        return this.meta;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ColourRGBA getColor(){
        return new ColourRGBA(
                this.color.getRed(),
                this.color.getGreen(),
                this.color.getBlue(),
                this.color.getAlpha()
        );
    }

    public Color getAWTColor(){
        return this.color;
    }

    public int getR(){
        return this.color.getRed();
    }

    public int getG(){
        return this.color.getGreen();
    }

    public int getB(){
        return this.color.getBlue();
    }

    public static String[] toStringArray(){
        String[] names = new String[values().length];

        for(int i = 0; i < values().length; i++){
            names[i] = values()[i].getName();
        }

        return names;
    }

}

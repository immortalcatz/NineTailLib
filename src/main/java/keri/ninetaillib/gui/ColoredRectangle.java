package keri.ninetaillib.gui;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ColoredRectangle {

    private Vector2i start;
    private Vector2i end;
    private ColourRGBA startColor;
    private ColourRGBA endColor;
    private ColourRGBA borderColor;
    private boolean border;

    public ColoredRectangle(Vector2i start, Vector2i end, ColourRGBA color){
        this.start = start;
        this.end = end;
        this.startColor = color;
        this.endColor = color;
        this.borderColor = color;
        this.border = false;
    }

    public ColoredRectangle(Vector2i start, Vector2i end, ColourRGBA startColor, ColourRGBA endColor){
        this.start = start;
        this.end = end;
        this.startColor = startColor;
        this.endColor = endColor;
        this.border = false;
    }

    public void draw(GuiScreen gui){
        //todo =)
    }

    public void setHasBorder(boolean hasBorder){
        this.border = hasBorder;
    }

    public void setBorderColor(ColourRGBA borderColor){
        this.borderColor = borderColor;
    }

}

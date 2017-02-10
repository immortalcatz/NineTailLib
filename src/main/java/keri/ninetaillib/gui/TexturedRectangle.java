package keri.ninetaillib.gui;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.util.ResourceAction;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TexturedRectangle {

    private ResourceAction texture;
    private Vector2i position;
    private Vector2i resolution;
    private Vector2i uv;
    private ColourRGBA borderColor;
    private boolean border;
    private int zLevel = 0;

    public TexturedRectangle(Vector2i position, Vector2i resolution, ResourceLocation texture){
        this.position = position;
        this.resolution = resolution;
        this.uv = new Vector2i(0, 0);
        this.texture = new ResourceAction(texture);
        this.border = false;
        this.borderColor = new ColourRGBA(0, 0, 0, 255);
    }

    public void draw(){
        //todo
    }

    public void setHasBorder(boolean hasBorder){
        this.border = hasBorder;
    }

    public void setBorderColor(ColourRGBA borderColor){
        this.borderColor = borderColor;
    }

    public void setUV(Vector2i uv){
        this.uv = uv;
    }

    public void setZLevel(int zLevel){
        this.zLevel = zLevel;
    }

    private void drawHorizontalLine(int startX, int endX, int y, int color){
        if(endX < startX){
            int i = startX;
            startX = endX;
            endX = i;
        }

        Gui.drawRect(startX, y, endX + 1, y + 1, color);
    }

    private void drawVerticalLine(int x, int startY, int endY, int color){
        if(endY < startY){
            int i = startY;
            startY = endY;
            endY = i;
        }

        Gui.drawRect(x, startY + 1, x + 1, endY, color);
    }

}

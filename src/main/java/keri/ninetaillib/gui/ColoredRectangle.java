package keri.ninetaillib.gui;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
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
    private int zLevel = 0;

    public ColoredRectangle(Vector2i start, Vector2i end, ColourRGBA color){
        this.start = start;
        this.end = end;
        this.startColor = color;
        this.endColor = color;
        this.borderColor = color;
        this.border = false;
        this.borderColor = new ColourRGBA(255, 255, 255, 255);
    }

    public ColoredRectangle(Vector2i start, Vector2i end, ColourRGBA startColor, ColourRGBA endColor){
        this.start = start;
        this.end = end;
        this.startColor = startColor;
        this.endColor = endColor;
        this.border = false;
        this.borderColor = new ColourRGBA(255, 255, 255, 255);
    }

    public void draw(){
        int startX = this.start.getX();
        int startY = this.start.getY();
        int endX = this.end.getX();
        int endY = this.end.getY();
        int startColor = this.startColor.argb();
        int endColor = this.endColor.argb();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GuiUtils.drawGradientRect(this.zLevel, startX, startY, endX, endY, startColor, endColor);
        GlStateManager.popMatrix();

        if(this.border){
            int borderColor = this.borderColor.argb();
            GlStateManager.pushMatrix();
            GlStateManager.color(1F, 1F, 1F, 1F);
            this.drawHorizontalLine(startX, endX, startY, borderColor);
            this.drawHorizontalLine(startX, endX, endY, borderColor);
            this.drawVerticalLine(startX, startY, endY, borderColor);
            this.drawVerticalLine(endX, startY, endY, borderColor);
            GlStateManager.popMatrix();
        }
    }

    public void setHasBorder(boolean hasBorder){
        this.border = hasBorder;
    }

    public void setBorderColor(ColourRGBA borderColor){
        this.borderColor = borderColor;
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

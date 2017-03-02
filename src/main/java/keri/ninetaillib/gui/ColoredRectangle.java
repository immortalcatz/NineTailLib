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

    private Vector2i pos;
    private Vector2i size;
    private ColourRGBA startColor;
    private ColourRGBA endColor;
    private ColourRGBA borderColor;
    private boolean border;

    public ColoredRectangle(Vector2i position, Vector2i size, ColourRGBA color){
        this.pos = position;
        this.size = size;
        this.startColor = color;
        this.endColor = color;
        this.border = false;
        this.borderColor = new ColourRGBA(0, 0, 0, 255);
    }

    public ColoredRectangle(Vector2i position, Vector2i size, ColourRGBA startColor, ColourRGBA endColor){
        this.pos = position;
        this.size = size;
        this.startColor = startColor;
        this.endColor = endColor;
        this.border = false;
        this.borderColor = new ColourRGBA(0, 0, 0, 255);
    }

    public void draw(){
        int startX = this.pos.getX();
        int startY = this.pos.getY();
        int endX = this.pos.getX() + this.size.getX();
        int endY = this.pos.getY() + this.size.getY();
        int startColor = this.startColor.argb();
        int endColor = this.endColor.argb();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GuiUtils.drawGradientRect(0, startX, startY, endX, endY, startColor, endColor);
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();

        if(this.border){
            int borderColor = this.borderColor.argb();
            GlStateManager.pushMatrix();
            GlStateManager.color(1F, 1F, 1F, 1F);
            this.drawHorizontalLine(startX, endX - 1, startY, borderColor);
            this.drawHorizontalLine(startX, endX - 1, endY - 1, borderColor);
            this.drawVerticalLine(startX, startY, endY, borderColor);
            this.drawVerticalLine(endX - 1, startY, endY, borderColor);
            GlStateManager.color(1F, 1F, 1F, 1F);
            GlStateManager.popMatrix();
        }
    }

    public void setHasBorder(boolean hasBorder){
        this.border = hasBorder;
    }

    public void setBorderColor(ColourRGBA borderColor){
        this.borderColor = borderColor;
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

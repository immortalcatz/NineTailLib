/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class LegacyTessellator {

    private VertexBuffer buffer;
    private boolean hasBrightnessOverride = false;
    private int brightness = 0;
    private boolean hasColorOverride = false;
    private int colorR = 255;
    private int colorG = 255;
    private int colorB = 255;
    private int colorA = 255;

    public LegacyTessellator(VertexBuffer buffer){
        this.buffer = buffer;
    }

    public void reset(){
        this.hasBrightnessOverride = false;
        this.brightness = 0;
        this.hasColorOverride = false;
        this.colorR = 255;
        this.colorG = 255;
        this.colorB = 255;
        this.colorA = 255;
        this.buffer.reset();
    }

    public void startDrawingQuads(){
        this.buffer.begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    public void startDrawing(int mode){
        this.buffer.begin(mode, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    public void setBrightness(int brightness){
        this.brightness = brightness;
        this.hasBrightnessOverride = true;
    }

    public void setColorOpaque_F(float r, float g, float b){
        this.colorR = (int)r / 255;
        this.colorG = (int)g / 255;
        this.colorB = (int)b / 255;
        this.colorA = 255;
        this.hasColorOverride = true;
    }

    public void setColorRGBA_F(float r, float g, float b, float a){
        this.colorR = (int)r / 255;
        this.colorG = (int)g / 255;
        this.colorB = (int)b / 255;
        this.colorA = (int)a / 255;
        this.hasColorOverride = true;
    }

    public void setColorOpaque(int r, int g, int b){
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        this.colorA = 255;
        this.hasColorOverride = true;
    }

    public void setColorRGBA(int r, int g, int b, int a){
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        this.colorA = a;
        this.hasColorOverride = true;
    }

    public void addVertex(double x, double y, double z){
        int r = this.hasColorOverride ? this.colorR : 255;
        int g = this.hasColorOverride ? this.colorG : 255;
        int b = this.hasColorOverride ? this.colorB : 255;
        int a = this.hasColorOverride ? this.colorA : 255;
        int lightmap1 = this.brightness >> 0x10 & 0xFFFF;
        int lightmap2 = this.brightness & 0xFFFF;

        if(this.hasBrightnessOverride){
            this.buffer.pos(x, y, z).color(r, g, b, a).lightmap(lightmap1, lightmap2).endVertex();
        }
        else{
            this.buffer.pos(x, y, z).color(r, g, b, a).endVertex();
        }
    }

    public void addVertexWithUV(double x, double y, double z, double u, double v){
        int r = this.hasColorOverride ? this.colorR : 255;
        int g = this.hasColorOverride ? this.colorG : 255;
        int b = this.hasColorOverride ? this.colorB : 255;
        int a = this.hasColorOverride ? this.colorA : 255;
        int lightmap1 = this.brightness >> 0x10 & 0xFFFF;
        int lightmap2 = this.brightness & 0xFFFF;

        if(this.hasBrightnessOverride){
            this.buffer.pos(x, y, z).tex(u, v).color(r, g, b, a).lightmap(lightmap1, lightmap2).endVertex();
        }
        else{
            this.buffer.pos(x, y, z).tex(u, v).color(r, g, b, a).endVertex();
        }
    }

}

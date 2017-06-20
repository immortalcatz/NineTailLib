/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.vec.Rectangle4i;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.math.IPositionProvider;
import keri.ninetaillib.lib.math.Point2i;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiUtils {

    private static TextureManager TEXTURE_MANAGER = Minecraft.getMinecraft().getTextureManager();
    private static FontRenderer FONT_RENDERER = Minecraft.getMinecraft().fontRenderer;
    private static final ResourceLocation TEXTURE_BACKGROUND = new ResourceLocation(ModPrefs.MODID, "textures/gui/background.png");
    private static final ResourceLocation TEXTURE_ELEMENTS = new ResourceLocation(ModPrefs.MODID, "textures/gui/elements.png");
    private static final float Z_LEVEL = 100F;
    private static final String TOOLTIP_EMPTY = TextFormatting.GRAY + TranslationUtils.translate(ModPrefs.MODID, "tooltip", "empty");
    public static final int ALIGNMENT_LEFT = 0;
    public static final int ALIGNMENT_RIGHT = 1;
    public static final int ALIGNMENT_TOP = 2;
    public static final int ALIGNMENT_BOTTOM = 3;
    public static final int ALIGNMENT_NONE = 4;
    public static final int BACKGROUND_LIGHT = 0;
    public static final int BACKGROUND_DARK = 1;
    public static final int POWER_TESLA = 0;
    public static final int POWER_RF = 1;
    public static final int POWER_FORGE = 2;
    public static final int POWER_EU = 3;

    public static void drawFluidGauge(Point2i pos, int backgroundType, Fluid fluid, int amount, int maxAmount){
        drawFluidGauge(pos, backgroundType, new FluidTank(fluid, amount, maxAmount), null);
    }

    public static void drawFluidGauge(Point2i pos, int backgroundType, Fluid fluid, int amount, int maxAmount, IPositionProvider mousePosition){
        drawFluidGauge(pos, backgroundType, new FluidTank(fluid, amount, maxAmount), mousePosition);
    }

    public static void drawFluidGauge(Point2i pos, int backgroundType, FluidTank fluidTank){
        drawFluidGauge(pos, backgroundType, fluidTank, null);
    }

    public static void drawFluidGauge(Point2i pos, int backgroundType, FluidTank fluidTank, IPositionProvider mousePosition){
        final int width = 20;
        final int height = 68;
        TEXTURE_MANAGER.bindTexture(TEXTURE_ELEMENTS);
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);

        switch(backgroundType){
            case BACKGROUND_LIGHT:
                drawTexturedModalRect(pos.getX(), pos.getY(), 3, 106, width, height);
                break;
            case BACKGROUND_DARK:
                drawTexturedModalRect(pos.getX(), pos.getY(), 3, 176, width, height);
                break;
        }

        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Rectangle4i dimension = new Rectangle4i(pos.getX() + 2, pos.getY() + 50, 16, 16);

        if(fluidTank != null && fluidTank.getFluid() != null){
            double density = ((fluidTank.getFluidAmount() * 64D) / fluidTank.getCapacity() * 64D) / 1000D;
            codechicken.lib.render.RenderUtils.preFluidRender();
            codechicken.lib.render.RenderUtils.renderFluidGauge(fluidTank.getFluid(), dimension, density, 16D);
            codechicken.lib.render.RenderUtils.postFluidRender();
        }

        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();

        switch(backgroundType){
            case BACKGROUND_LIGHT:
                drawTexturedModalRect(pos.getX() + 1, pos.getY() + 1, 24, 107, width - 2, height - 2);
                break;
            case BACKGROUND_DARK:
                drawTexturedModalRect(pos.getX() + 1, pos.getY() + 1, 24, 177, width - 2, height - 2);
                break;
        }

        GlStateManager.popMatrix();

        if(mousePosition != null){
            AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), 0D, pos.getX() + width, pos.getY() + height, 0D);

            if(aabb.intersectsWithXY(new Vec3d(mousePosition.getPosition().getX(), mousePosition.getPosition().getY(), 0D))){
                GlStateManager.pushMatrix();
                GlStateManager.color(1F, 1F, 1F, 1F);
                List<String> text = Lists.newArrayList();
                text.add((fluidTank.getFluidAmount() > 0 ? Integer.toString(fluidTank.getFluidAmount()) : "0") + " mB");
                text.add(TextFormatting.YELLOW + (fluidTank.getFluidAmount() > 0 ? fluidTank.getFluid().getLocalizedName() : TOOLTIP_EMPTY));
                int screenWidth = Minecraft.getMinecraft().displayWidth;
                int screenHeight = Minecraft.getMinecraft().displayHeight;
                net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(text, mousePosition.getPosition().getX(), mousePosition.getPosition().getY(), screenWidth, screenHeight, 200, FONT_RENDERER);
                GlStateManager.popMatrix();
            }
        }
    }

    public static void drawPowerBar(Point2i pos, int backgroundType, int powerType, int power, int capacity){
        drawPowerBar(pos, backgroundType, powerType, power, capacity, null);
    }

    public static void drawPowerBar(Point2i pos, int backgroundType, int powerType, int power, int capacity, IPositionProvider mousePosition){
        final int width = 14;
        final int height = 50;
        int powerOffset = (power * (height + 1)) / capacity;
        Colour color = null;
        TEXTURE_MANAGER.bindTexture(TEXTURE_ELEMENTS);

        switch(backgroundType){
            case BACKGROUND_LIGHT:
                GlStateManager.pushMatrix();
                GlStateManager.color(1F, 1F, 1F, 1F);
                drawTexturedModalRect(pos.getX(), pos.getY(), 3, 1, width, height);
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.popMatrix();
                break;
            case BACKGROUND_DARK:
                GlStateManager.pushMatrix();
                GlStateManager.color(1F, 1F, 1F, 1F);
                drawTexturedModalRect(pos.getX(), pos.getY(), 3, 53, width, height);
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.popMatrix();
                break;
        }

        switch(powerType){
            case POWER_TESLA:
                color = new ColourRGBA(0, 194, 220, 255);
                break;
            case POWER_RF:
                color = new ColourRGBA(255, 42, 0, 255);
                break;
            case POWER_FORGE:
                color = new ColourRGBA(40, 90, 220, 255);
                break;
            case POWER_EU:
                color = new ColourRGBA(50, 50, 240, 255);
                break;
        }

        GlStateManager.pushMatrix();
        color.glColour();
        drawTexturedModalRect(pos.getX() + 1, (pos.getY() + height - powerOffset), 18, ((height + 1) - powerOffset), width, (powerOffset + 2));
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();

        if(mousePosition != null){
            AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), 0D, pos.getX() + width, pos.getY() + height, 0D);

            if(aabb.intersectsWithXY(new Vec3d(mousePosition.getPosition().getX(), mousePosition.getPosition().getY(), 0D))){
                GlStateManager.pushMatrix();
                GlStateManager.color(1F, 1F, 1F, 1F);
                List<String> text = Lists.newArrayList();

                switch(powerType){
                    case POWER_TESLA:
                        text.add(Integer.toString(power) + " RF");
                        break;
                    case POWER_RF:
                        text.add(Integer.toString(power) + " TESLA");
                        break;
                    case POWER_FORGE:
                        text.add(Integer.toString(power) + " FU");
                        break;
                    case POWER_EU:
                        text.add(Integer.toString(power) + " EU");
                        break;
                }

                int screenwidth = Minecraft.getMinecraft().displayWidth;
                int screenHeight = Minecraft.getMinecraft().displayHeight;
                net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(text, mousePosition.getPosition().getX(), mousePosition.getPosition().getY(), screenwidth, screenHeight, 200, FONT_RENDERER);
                GlStateManager.popMatrix();
            }
        }
    }

    public static void drawRectangleWithBorder(Point2i pos, Point2i size, Colour color, Colour colorBorder){
        drawRectangleWithBorder(pos, size, color, color, colorBorder);
    }

    public static void drawRectangleWithBorder(Point2i pos, Point2i size, Colour colorStart, Colour colorEnd, Colour colorBorder){
        int startX = pos.getX();
        int startY = pos.getY();
        int endX = pos.getX() + size.getX();
        int endY = pos.getY() + size.getY();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        net.minecraftforge.fml.client.config.GuiUtils.drawGradientRect(0, startX, startY, endX, endY, colorStart.argb(), colorEnd.argb());
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        drawHorizontalLine(startX, endX - 1, startY, colorBorder.argb());
        drawHorizontalLine(startX, endX - 1, endY - 1, colorBorder.argb());
        drawVerticalLine(startX, startY, endY, colorBorder.argb());
        drawVerticalLine(endX - 1, startY, endY, colorBorder.argb());
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    public static void drawRectangle(Point2i pos, Point2i size, Colour color){
        drawRectangle(pos, size, color, color);
    }

    public static void drawRectangle(Point2i pos, Point2i size, Colour colorStart, Colour colorEnd){
        int startX = pos.getX();
        int startY = pos.getY();
        int endX = pos.getX() + size.getX();
        int endY = pos.getY() + size.getY();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        net.minecraftforge.fml.client.config.GuiUtils.drawGradientRect(0, startX, startY, endX, endY, colorStart.argb(), colorEnd.argb());
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    public static void drawBackground(Point2i pos, Point2i size, int alignment){
        drawBackground(pos, size, alignment, new ColourRGBA(255, 255, 255, 255));
    }

    public static void drawBackground(Point2i pos, Point2i size, int alignment, Colour color){
        GlStateManager.pushMatrix();
        Point2i posCornerTL = new Point2i(pos.getX(), pos.getY());
        Point2i minUVCornerTL = new Point2i(0, 0);
        Point2i maxUVCornerTL = new Point2i(4, 4);
        Point2i posCornerBL = new Point2i(pos.getX(), pos.getY() + size.getY());
        Point2i minUVCornerBL = new Point2i(0, 20);
        Point2i maxUVCornerBL = new Point2i(4, 24);
        Point2i posCornerTR = new Point2i(pos.getX() + size.getX(), pos.getY());
        Point2i minUVCornerTR = new Point2i(20, 0);
        Point2i maxUVCornerTR = new Point2i(24, 4);
        Point2i posCornerBR = new Point2i(pos.getX() + size.getX(), pos.getY() + size.getY());
        Point2i minUVCornerBR = new Point2i(20, 20);
        Point2i maxUVCornerBR = new Point2i(24, 24);

        switch(alignment){
            case ALIGNMENT_LEFT:
                drawBackground(posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                drawBackground(posCornerBL, minUVCornerBL, maxUVCornerBL, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_RIGHT:
                drawBackground(posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                drawBackground(posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_TOP:
                drawBackground(posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                drawBackground(posCornerTR, minUVCornerTR, maxUVCornerTR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_BOTTOM:
                drawBackground(posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                drawBackground(posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_NONE:
                drawBackground(posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                drawBackground(posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                drawBackground(posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                drawBackground(posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
        }

        GlStateManager.popMatrix();
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height){
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)(x + 0), (double)(y + height), (double)Z_LEVEL).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), (double)Z_LEVEL).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        buffer.pos((double)(x + width), (double)(y + 0), (double)Z_LEVEL).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        buffer.pos((double)(x + 0), (double)(y + 0), (double)Z_LEVEL).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV){
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)Z_LEVEL).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        buffer.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)Z_LEVEL).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        buffer.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)Z_LEVEL).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        buffer.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)Z_LEVEL).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn){
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)Z_LEVEL).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        buffer.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)Z_LEVEL).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        buffer.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)Z_LEVEL).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        buffer.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)Z_LEVEL).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight){
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        buffer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight){
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
        buffer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
        buffer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    private static void drawBackground(Point2i pos, Point2i minUV, Point2i maxUV, Colour color){
        TEXTURE_MANAGER.bindTexture(TEXTURE_BACKGROUND);
        color.glColour();
        drawTexturedModalRect(pos.getX(), pos.getY(), minUV.getX(), minUV.getY(), maxUV.getX(), maxUV.getY());
    }

    private static void drawHorizontalLine(int startX, int endX, int y, int color){
        if(endX < startX){
            int i = startX;
            startX = endX;
            endX = i;
        }

        Gui.drawRect(startX, y, endX + 1, y + 1, color);
    }

    private static void drawVerticalLine(int x, int startY, int endY, int color){
        if(endY < startY){
            int i = startY;
            startY = endY;
            endY = i;
        }

        Gui.drawRect(x, startY + 1, x + 1, endY, color);
    }

}

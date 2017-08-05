/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.gui;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Rectangle4i;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.math.IPositionProvider;
import keri.ninetaillib.lib.math.Point2i;
import keri.ninetaillib.lib.util.Translations;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiContainerBase extends GuiContainer {

    private static final ResourceLocation TEXTURE_BG = new ResourceLocation(ModPrefs.MODID, "textures/gui/background.png");
    private static final ResourceLocation TEXTURE_ELEMENTS = new ResourceLocation(ModPrefs.MODID, "textures/gui/elements.png");
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

    public GuiContainerBase(Container container) {
        super(container);
    }

    protected void drawFluidGauge(Point2i pos, int backgroundType, Fluid fluid, int amount, int maxAmount){
        this.drawFluidGauge(pos, backgroundType, new FluidTank(fluid, amount, maxAmount), null);
    }

    protected void drawFluidGauge(Point2i pos, int backgroundType, Fluid fluid, int amount, int maxAmount, IPositionProvider mousePosition){
        this.drawFluidGauge(pos, backgroundType, new FluidTank(fluid, amount, maxAmount), mousePosition);
    }

    protected void drawFluidGauge(Point2i pos, int backgroundType, FluidTank fluidTank){
        this.drawFluidGauge(pos, backgroundType, fluidTank, null);
    }

    protected void drawFluidGauge(Point2i pos, int backgroundType, FluidTank fluidTank, IPositionProvider mousePosition){
        final int width = 20;
        final int height = 68;
        this.mc.getTextureManager().bindTexture(TEXTURE_ELEMENTS);
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);

        switch(backgroundType){
            case BACKGROUND_LIGHT:
                this.drawTexturedModalRect(pos.getX(), pos.getY(), 3, 106, width, height);
                break;
            case BACKGROUND_DARK:
                this.drawTexturedModalRect(pos.getX(), pos.getY(), 3, 176, width, height);
                break;
        }

        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        Rectangle4i dimension = new Rectangle4i(pos.getX() + 2, pos.getY() + 50, 16, 16);

        if(fluidTank != null && fluidTank.getFluid() != null){
            double density = ((fluidTank.getFluidAmount() * 64D) / fluidTank.getCapacity() * 64D) / 1000D;
            RenderUtils.preFluidRender();
            RenderUtils.renderFluidGauge(fluidTank.getFluid(), dimension, density, 16D);
            RenderUtils.postFluidRender();
        }

        this.mc.getTextureManager().bindTexture(TEXTURE_ELEMENTS);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();

        switch(backgroundType){
            case BACKGROUND_LIGHT:
                this.drawTexturedModalRect(pos.getX() + 1, pos.getY() + 1, 24, 107, width - 2, height - 2);
                break;
            case BACKGROUND_DARK:
                this.drawTexturedModalRect(pos.getX() + 1, pos.getY() + 1, 24, 177, width - 2, height - 2);
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
                text.add(TextFormatting.YELLOW + (fluidTank.getFluidAmount() > 0 ? fluidTank.getFluid().getLocalizedName() : Translations.TOOLTIP_EMPTY));
                int screenWidth = Minecraft.getMinecraft().displayWidth;
                int screenHeight = Minecraft.getMinecraft().displayHeight;
                GuiUtils.drawHoveringText(text, mousePosition.getPosition().getX(), mousePosition.getPosition().getY(), screenWidth, screenHeight, 200, this.fontRendererObj);
                GlStateManager.popMatrix();
            }
        }
    }

    protected void drawPowerBar(Point2i pos, int backgroundType, int powerType, int power, int capacity){
        this.drawPowerBar(pos, backgroundType, powerType, power, capacity, null);
    }

    protected void drawPowerBar(Point2i pos, int backgroundType, int powerType, int power, int capacity, IPositionProvider mousePosition){
        final int width = 14;
        final int height = 50;
        int powerOffset = (power * (height + 1)) / capacity;
        Colour color = null;
        this.mc.getTextureManager().bindTexture(TEXTURE_ELEMENTS);

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
                GuiUtils.drawHoveringText(text, mousePosition.getPosition().getX(), mousePosition.getPosition().getY(), screenwidth, screenHeight, 200, this.fontRendererObj);
                GlStateManager.popMatrix();
            }
        }
    }

    protected void drawRectangleWithBorder(Point2i pos, Point2i size, Colour color, Colour colorBorder){
        this.drawRectangleWithBorder(pos, size, color, color, colorBorder);
    }

    protected void drawRectangleWithBorder(Point2i pos, Point2i size, Colour colorStart, Colour colorEnd, Colour colorBorder){
        int startX = pos.getX();
        int startY = pos.getY();
        int endX = pos.getX() + size.getX();
        int endY = pos.getY() + size.getY();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GuiUtils.drawGradientRect(0, startX, startY, endX, endY, colorStart.argb(), colorEnd.argb());
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        this.drawHorizontalLine(startX, endX - 1, startY, colorBorder.argb());
        this.drawHorizontalLine(startX, endX - 1, endY - 1, colorBorder.argb());
        this.drawVerticalLine(startX, startY, endY, colorBorder.argb());
        this.drawVerticalLine(endX - 1, startY, endY, colorBorder.argb());
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    protected void drawRectangle(Point2i pos, Point2i size, Colour color){
        this.drawRectangle(pos, size, color, color);
    }

    protected void drawRectangle(Point2i pos, Point2i size, Colour colorStart, Colour colorEnd){
        int startX = pos.getX();
        int startY = pos.getY();
        int endX = pos.getX() + size.getX();
        int endY = pos.getY() + size.getY();
        GlStateManager.pushMatrix();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GuiUtils.drawGradientRect(0, startX, startY, endX, endY, colorStart.argb(), colorEnd.argb());
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    protected void drawBackground(Point2i pos, Point2i size, int alignment){
        this.drawBackground(pos, size, alignment, new ColourRGBA(255, 255, 255, 255));
    }

    protected void drawBackground(Point2i pos, Point2i size, int alignment, Colour color){
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
                this.drawBackground(posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                this.drawBackground(posCornerBL, minUVCornerBL, maxUVCornerBL, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    this.drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    this.drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    this.drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        this.drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_RIGHT:
                this.drawBackground(posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                this.drawBackground(posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    this.drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    this.drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    this.drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        this.drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_TOP:
                this.drawBackground(posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                this.drawBackground(posCornerTR, minUVCornerTR, maxUVCornerTR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    this.drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    this.drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    this.drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        this.drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_BOTTOM:
                this.drawBackground(posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                this.drawBackground(posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    this.drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    this.drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    this.drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        this.drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case ALIGNMENT_NONE:
                this.drawBackground(posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                this.drawBackground(posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                this.drawBackground(posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                this.drawBackground(posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Point2i posEdgeLeft = new Point2i(pos.getX(), pos.getY() + 4 + left);
                    Point2i minUVEdgeLeft = new Point2i(0, 4);
                    Point2i maxUVEdgeLeft = new Point2i(4, 1);
                    this.drawBackground(posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Point2i posEdgeRight = new Point2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Point2i minUVEdgeRight = new Point2i(20, 4);
                    Point2i maxUVEdgeRight = new Point2i(24, 1);
                    this.drawBackground(posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Point2i posEdgeTop = new Point2i(pos.getX() + 4 + top, pos.getY());
                    Point2i minUVEdgeTop = new Point2i(4, 0);
                    Point2i maxUVEdgeTop = new Point2i(1, 4);
                    this.drawBackground(posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Point2i posEdgeBottom = new Point2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Point2i minUVEdgeBottom = new Point2i(4, 20);
                    Point2i maxUVEdgeBottom = new Point2i(1, 24);
                    this.drawBackground(posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Point2i posFill = new Point2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Point2i minUVFill = new Point2i(4, 4);
                        Point2i maxUVFill = new Point2i(1, 1);
                        this.drawBackground(posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
        }

        GlStateManager.popMatrix();
    }

    private void drawBackground(Point2i pos, Point2i minUV, Point2i maxUV, Colour color) {
        this.mc.getTextureManager().bindTexture(TEXTURE_BG);
        color.glColour();
        this.drawTexturedModalRect(pos.getX(), pos.getY(), minUV.getX(), minUV.getY(), maxUV.getX(), maxUV.getY());
    }

}

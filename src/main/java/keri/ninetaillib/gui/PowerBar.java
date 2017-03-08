package keri.ninetaillib.gui;

import codechicken.lib.colour.ColourRGBA;
import com.google.common.collect.Lists;
import keri.ninetaillib.internal.util.ModPrefs;
import keri.ninetaillib.util.ResourceAction;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

/**
 * Taken from the TESLA API and modified for NineTailLib - All rights go to Darkhax !
 */
@SideOnly(Side.CLIENT)
public class PowerBar {

    public final int width = 14;
    public final int height = 50;
    private final ResourceAction texture = new ResourceAction(ModPrefs.MODID, "textures/gui/elements.png");
    private Vector2i position;
    private final GuiScreen screen;
    private final EnumBackgroundType backgroundType;
    private PowerType powerType;
    private ColourRGBA barColor;
    private boolean enableTooltip = false;
    private Vector2i mousePos;

    public PowerBar(GuiScreen screen, Vector2i pos, EnumBackgroundType backgroundType, PowerType powerType) {
        this.backgroundType = backgroundType;
        this.powerType = powerType;
        this.screen = screen;
        this.position = pos;
    }

    public PowerBar(GuiScreen screen, Vector2i pos, EnumBackgroundType backgroundType, ColourRGBA barColor) {
        this.backgroundType = backgroundType;
        this.screen = screen;
        this.barColor = barColor;
        this.position = pos;
    }

    public void draw (int power, int capacity){
        this.texture.bind(true);

        if (this.backgroundType == EnumBackgroundType.LIGHT){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.color(1F, 1F, 1F, 1F);
            screen.drawTexturedModalRect(this.position.getX(), this.position.getY(), 3, 1, this.width, this.height);
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
        else if (this.backgroundType == EnumBackgroundType.DARK){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.color(1F, 1F, 1F, 1F);
            screen.drawTexturedModalRect(this.position.getX(), this.position.getY(), 3, 53, this.width, this.height);
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }

        int powerOffset = (power * (this.height + 1)) / capacity;
        float r = 0F;
        float g = 0F;
        float b = 0F;

        if(this.powerType != null){
            if(this.powerType == PowerType.RF){
                r = 255F / 255F;
                g = 42F / 255F;
                b = 0F / 255F;
            }
            else if(this.powerType == PowerType.TESLA){
                r = 0F / 255F;
                g = 194F / 255F;
                b = 219 / 255F;
            }
        }
        else{
            r = this.barColor.r / 255F;
            g = this.barColor.g / 255F;
            b = this.barColor.b / 255F;
        }

        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.color(r, g, b, 1F);
        screen.drawTexturedModalRect(this.position.getX() + 1, (this.position.getY() + this.height - powerOffset), 18, ((this.height + 1) - powerOffset), this.width, (powerOffset + 2));
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();

        if(this.enableTooltip){
            AxisAlignedBB aabb = new AxisAlignedBB(this.position.getX(), this.position.getY(), 0D, this.position.getX() + this.width, this.position.getY() + this.height, 0D);

            if(aabb.intersectsWithXY(new Vec3d(this.mousePos.getX(), this.mousePos.getY(), 0D))){
                GlStateManager.pushMatrix();
                GlStateManager.pushAttrib();
                GlStateManager.color(1F, 1F, 1F, 1F);
                FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                ArrayList<String> text = Lists.newArrayList();

                if(this.powerType != null){
                    switch(this.powerType){
                        case RF:
                            text.add(Integer.toString(power) + " RF");
                            break;
                        case TESLA:
                            text.add(Integer.toString(power) + " TESLA");
                    }
                }
                else{
                    text.add("Someone forgot to specify the power type :c");
                }

                int screenwidth = Minecraft.getMinecraft().displayWidth;
                int screenHeight = Minecraft.getMinecraft().displayHeight;
                GuiUtils.drawHoveringText(text, this.mousePos.getX(), this.mousePos.getY(), screenwidth, screenHeight, 200, fontRenderer);
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            }
        }
    }

    public void enableTooltip(Vector2i mousePos){
        this.enableTooltip = true;
        this.mousePos = mousePos;
    }

    public enum PowerType {
        RF,
        TESLA
    }

}

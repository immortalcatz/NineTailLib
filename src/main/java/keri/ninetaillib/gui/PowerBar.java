package keri.ninetaillib.gui;

import keri.ninetaillib.mod.util.ModPrefs;
import keri.ninetaillib.util.ResourceAction;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Taken from the TESLA API - All rights go to Darkhax !
 */
@SideOnly(Side.CLIENT)
public class PowerBar {

    public static final int WIDTH = 14;
    public static final int HEIGHT = 50;
    private static final ResourceAction TEXTURE_SHEET = new ResourceAction(ModPrefs.MODID, "textures/gui/elements.png");
    private final int x;
    private final int y;
    private final GuiScreen screen;
    private final BackgroundType backgroundType;
    private final PowerType powerType;

    public PowerBar(GuiScreen screen, int x, int y, BackgroundType backgroundType, PowerType powerType) {
        this.backgroundType = backgroundType;
        this.powerType = powerType;
        this.screen = screen;
        this.x = x;
        this.y = y;
    }

    public void draw (int power, int capacity) {
        TEXTURE_SHEET.bind(true);

        if (this.backgroundType == BackgroundType.LIGHT){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.color(1F, 1F, 1F, 1F);
            screen.drawTexturedModalRect(x, y, 3, 1, WIDTH, HEIGHT);
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
        else if (this.backgroundType == BackgroundType.DARK){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.color(1F, 1F, 1F, 1F);
            screen.drawTexturedModalRect(x, y, 3, 53, WIDTH, HEIGHT);
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }

        int powerOffset = (power * (HEIGHT + 1)) / capacity;
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        float r = 0F;
        float g = 0F;
        float b = 0F;

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

        GlStateManager.color(r, g, b, 1F);
        screen.drawTexturedModalRect(x + 1, (y + HEIGHT - powerOffset), 18, ((HEIGHT + 1) - powerOffset), WIDTH, (powerOffset + 2));
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public enum BackgroundType {
        NONE,
        LIGHT,
        DARK,
    }

    public enum PowerType {
        RF,
        TESLA
    }

}

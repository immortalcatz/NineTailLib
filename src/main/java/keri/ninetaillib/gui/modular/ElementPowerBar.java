package keri.ninetaillib.gui.modular;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.gui.EnumBackgroundType;
import keri.ninetaillib.gui.EnumRenderType;
import keri.ninetaillib.gui.PowerBar;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;

public class ElementPowerBar implements IGuiElement {

    private Vector2i position;
    private EnumBackgroundType backgroundType;
    private PowerBar.PowerType powerType;
    private ColourRGBA color;
    private int max;
    private int stored;
    private PowerBar powerBar;

    public ElementPowerBar(Vector2i position, int maxEnergy, int storedEnergy){
        this(position, EnumBackgroundType.LIGHT, maxEnergy, storedEnergy);
    }

    public ElementPowerBar(Vector2i position, int maxEnergy, int storedEnergy, ColourRGBA color){
        this(position, EnumBackgroundType.LIGHT, maxEnergy, storedEnergy, color);
    }

    public ElementPowerBar(Vector2i position, int maxEnergy, int storedEnergy, PowerBar.PowerType powerType){
        this(position, EnumBackgroundType.LIGHT, maxEnergy, storedEnergy, powerType);
    }

    public ElementPowerBar(Vector2i position, EnumBackgroundType backgroundType, int maxEnergy, int storedEnergy){
        this(position, backgroundType, maxEnergy, storedEnergy, PowerBar.PowerType.RF);
    }

    public ElementPowerBar(Vector2i position, EnumBackgroundType backgroundType, int maxEnergy, int storedEnergy, ColourRGBA color){
        this.position = position;
        this.max = maxEnergy;
        this.stored = storedEnergy;
        this.backgroundType = backgroundType;
        this.color = color;
        this.powerType = null;
    }

    public ElementPowerBar(Vector2i position, EnumBackgroundType backgroundType, int maxEnergy, int storedEnergy, PowerBar.PowerType powerType){
        this.position = position;
        this.max = maxEnergy;
        this.stored = storedEnergy;
        this.backgroundType = backgroundType;
        this.color = null;
        this.powerType = powerType;
    }

    @Override
    public void onGuiInit(GuiScreen gui) {
        if(this.powerType != null){
            this.powerBar = new PowerBar(gui, this.position, this.backgroundType, this.powerType);
        }
        else{
            this.powerBar = new PowerBar(gui, this.position, this.backgroundType, this.color);
        }
    }

    @Override
    public void renderElement(VertexBuffer buffer, GuiScreen gui, EnumRenderType type) {
        if(type == EnumRenderType.BACKGROUND){
            this.powerBar.draw(this.stored, this.max);
        }
    }

}

package keri.ninetaillib.gui.modular;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.gui.ColoredRectangle;
import keri.ninetaillib.gui.EnumRenderType;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementRectangle implements IGuiElement {

    private ColoredRectangle rectangle;
    private Vector2i pos;
    private Vector2i size;
    private Colour color1;
    private Colour color2;
    private Colour colorBorder;

    public ElementRectangle(Vector2i pos, Vector2i size, Colour color){
        this(pos, size, color, color, null);
    }

    public ElementRectangle(Vector2i pos, Vector2i size, Colour color1, Colour color2){
        this(pos, size, color1, color2, null);
    }

    public ElementRectangle(Vector2i pos, Vector2i size, Colour color1, Colour color2, Colour borderColor){
        this.pos = pos;
        this.size = size;
        this.color1 = color1;
        this.color2 = color2;
        this.colorBorder = borderColor;
    }

    @Override
    public void onGuiInit(GuiScreen gui) {
        this.rectangle = new ColoredRectangle(this.pos, this.size, new ColourRGBA(this.color1.rgba()), new ColourRGBA(this.color2.rgba()));

        if(this.colorBorder != null){
            this.rectangle.setHasBorder(true);
            this.rectangle.setBorderColor(new ColourRGBA(this.colorBorder.rgba()));
        }
    }

    @Override
    public void renderElement(VertexBuffer buffer, GuiScreen gui, EnumRenderType type) {
        if(type == EnumRenderType.BACKGROUND){
            this.rectangle.draw();
        }
    }

}

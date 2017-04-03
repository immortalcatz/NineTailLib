package keri.ninetaillib.gui.modular;

import keri.ninetaillib.gui.EnumRenderType;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementButton implements IGuiElement {

    private Vector2i position;
    private Vector2i size;
    private int buttonId;
    private String text;

    public ElementButton(Vector2i position, int buttonId){
        this(position, buttonId, "");
    }

    public ElementButton(Vector2i position, int buttonId, String text){
        this(position, new Vector2i(32, 20), buttonId, text);
    }

    public ElementButton(Vector2i position, Vector2i size, int buttonId){
        this(position, size, buttonId, "");
    }

    public ElementButton(Vector2i position, Vector2i size, int buttonId, String text){
        this.position = position;
        this.size = size;
        this.buttonId = buttonId;
        this.text = text;
    }

    @Override
    public void onGuiInit(GuiScreen gui) {
        ((GuiModular)gui).getButtonList().add(new GuiButton(
                this.buttonId,
                this.position.getX(),
                this.position.getY(),
                this.size.getX(),
                this.size.getY(),
                this.text
        ));
    }

    @Override
    public void renderElement(VertexBuffer buffer, GuiScreen gui, EnumRenderType type) {

    }

}

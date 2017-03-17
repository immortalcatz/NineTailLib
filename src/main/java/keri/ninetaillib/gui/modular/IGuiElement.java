package keri.ninetaillib.gui.modular;

import keri.ninetaillib.gui.EnumRenderType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;

public interface IGuiElement {

    void onGuiInit(GuiScreen gui);

    void renderElement(VertexBuffer buffer, GuiScreen gui, EnumRenderType type);

}

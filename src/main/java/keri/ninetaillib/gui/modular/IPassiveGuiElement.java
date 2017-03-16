package keri.ninetaillib.gui.modular;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.tileentity.TileEntity;

public interface IPassiveGuiElement<T extends TileEntity> extends IGuiElement<T> {

    void renderElement(T tile, VertexBuffer buffer, GuiScreen gui);

}

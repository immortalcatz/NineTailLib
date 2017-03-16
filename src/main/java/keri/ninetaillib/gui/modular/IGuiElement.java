package keri.ninetaillib.gui.modular;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;

public interface IGuiElement<T extends TileEntity> {

    void onGuiInit(T tile, GuiScreen gui);

}

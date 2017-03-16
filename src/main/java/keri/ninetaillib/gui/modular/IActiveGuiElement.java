package keri.ninetaillib.gui.modular;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public interface IActiveGuiElement<T extends TileEntity> extends IGuiElement<T> {

    void renderElement(T tile, VertexBuffer buffer, GuiScreen gui);

    void actionPerformed(T tile, EntityPlayer player, GuiScreen gui);

}

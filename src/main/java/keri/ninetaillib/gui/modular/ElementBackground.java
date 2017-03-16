package keri.ninetaillib.gui.modular;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.tile.TileEntityBase;
import keri.ninetaillib.util.GuiUtils;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementBackground implements IPassiveGuiElement<TileEntityBase> {

    private Vector2i pos;
    private Vector2i size;
    private Colour color;
    private GuiUtils.Alignment alignment;

    public ElementBackground(Vector2i pos, Vector2i size){
        this(pos, size, new ColourRGBA(255, 255, 255, 255), GuiUtils.Alignment.NONE);
    }

    public ElementBackground(Vector2i pos, Vector2i size, GuiUtils.Alignment alignment){
        this(pos, size, new ColourRGBA(255, 255, 255, 255), alignment);
    }

    public ElementBackground(Vector2i pos, Vector2i size, Colour color, GuiUtils.Alignment alignment){
        this.pos = pos;
        this.size = size;
        this.color = color;
        this.alignment = alignment;
    }

    @Override
    public void onGuiInit(TileEntityBase tile, GuiScreen gui) {

    }

    @Override
    public void renderElement(TileEntityBase tile, VertexBuffer buffer, GuiScreen gui) {
        GuiUtils.drawBackground(gui, this.pos, this.size, this.alignment, new ColourRGBA(this.color.rgba()));
    }

}

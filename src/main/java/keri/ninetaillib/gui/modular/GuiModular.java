package keri.ninetaillib.gui.modular;

import com.google.common.collect.Lists;
import keri.ninetaillib.gui.EnumRenderType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GuiModular<C extends Container> extends GuiContainer {

    private List<IGuiElement> elements = Lists.newArrayList();

    public GuiModular(C container) {
        super(container);
        this.addElements(this.elements);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        VertexBuffer buffer = Tessellator.getInstance().getBuffer();

        for(IGuiElement element : this.elements){
            element.renderElement(buffer, this, EnumRenderType.BACKGROUND);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        VertexBuffer buffer = Tessellator.getInstance().getBuffer();

        for(IGuiElement element : this.elements){
            element.renderElement(buffer, this, EnumRenderType.FOREGROUND);
        }
    }

    @Override
    public void initGui(){
        super.initGui();

        for(IGuiElement element : this.elements){
            element.onGuiInit(this);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        this.handleButtonAction(button.id);
    }

    public List<GuiButton> getButtonList(){ return this.buttonList; }

    public abstract void addElements(List<IGuiElement> elements);

    public abstract void handleButtonAction(int buttonId);

}

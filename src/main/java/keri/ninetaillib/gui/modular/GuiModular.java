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
public class GuiModular extends GuiContainer {

    private List<IGuiElement> elements;

    private GuiModular(Container container, List<IGuiElement> elements){
        super(container);
        this.elements = elements;
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

        for(IGuiElement element : this.elements){
            if(element instanceof IActiveGuiElement){
                ((IActiveGuiElement)element).actionPerformed(this);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Builder<C extends Container> {

        private List<IGuiElement> elements;
        private C container;

        public Builder(C container){
            this.elements = Lists.newArrayList();
            this.container = container;
        }

        public void clear(){
            this.elements.clear();
        }

        public void addElement(IGuiElement element){
            if(element != null){
                this.elements.add(element);
            }
            else{
                throw new IllegalArgumentException("IGuiElement cannot be null !");
            }
        }

        public GuiModular build(){
            return new GuiModular(this.container, this.elements);
        }

    }

}

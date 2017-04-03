package keri.ninetaillib.gui.modular;

import keri.ninetaillib.gui.EnumBackgroundType;
import keri.ninetaillib.gui.EnumRenderType;
import keri.ninetaillib.gui.FluidGauge;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementFluidGauge implements IGuiElement {

    private Vector2i position;
    private EnumBackgroundType backgroundType;
    private int max;
    private FluidStack fluid;
    private FluidGauge fluidGauge;

    public ElementFluidGauge(Vector2i position, EnumBackgroundType backgroundType, int maxFluid, FluidStack fluid){
        this.position = position;
        this.backgroundType = backgroundType;
        this.max = maxFluid;
        this.fluid = fluid;
    }

    @Override
    public void onGuiInit(GuiScreen gui) {
        this.fluidGauge = new FluidGauge(gui, this.position, this.backgroundType);
    }

    @Override
    public void renderElement(VertexBuffer buffer, GuiScreen gui, EnumRenderType type) {
        if(type == EnumRenderType.BACKGROUND){
            this.fluidGauge.draw(this.fluid, this.max);
        }
    }

}

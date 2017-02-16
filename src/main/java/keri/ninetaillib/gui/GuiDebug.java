package keri.ninetaillib.gui;

import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDebug extends GuiScreen {

    public GuiDebug(){

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        //Water & Lava (Light)
        FluidGauge gaugeWater = new FluidGauge(this, new Vector2i(10, 10), EnumBackgroundType.LIGHT);
        gaugeWater.draw(FluidRegistry.getFluidStack("water", 6000), 8000);
        FluidGauge gaugeLava = new FluidGauge(this, new Vector2i(10, 90), EnumBackgroundType.LIGHT);
        gaugeLava.draw(FluidRegistry.getFluidStack("lava", 4000), 8000);
        //Water & Lava (Dark)
        FluidGauge gaugeWaterDark = new FluidGauge(this, new Vector2i(40, 10), EnumBackgroundType.DARK);
        gaugeWaterDark.draw(FluidRegistry.getFluidStack("water", 6000), 8000);
        FluidGauge gaugeLavaDark = new FluidGauge(this, new Vector2i(40, 90), EnumBackgroundType.DARK);
        gaugeLavaDark.draw(FluidRegistry.getFluidStack("lava", 4000), 8000);
        //RF & Tesla (Light)
        PowerBar powerBarRedstoneFlux = new PowerBar(this, new Vector2i(70, 10), EnumBackgroundType.LIGHT, PowerBar.PowerType.RF);
        powerBarRedstoneFlux.draw(4000, 8000);
        PowerBar powerBarTesla = new PowerBar(this, new Vector2i(70, 90), EnumBackgroundType.LIGHT, PowerBar.PowerType.TESLA);
        powerBarTesla.draw(6000, 8000);
        //RF & Tesla (Dark)
        PowerBar powerBarRedstoneFluxDark = new PowerBar(this, new Vector2i(100, 10), EnumBackgroundType.DARK, PowerBar.PowerType.RF);
        powerBarRedstoneFluxDark.draw(4000, 8000);
        PowerBar powerBarTeslaDark = new PowerBar(this, new Vector2i(100, 90), EnumBackgroundType.DARK, PowerBar.PowerType.TESLA);
        powerBarTeslaDark.draw(6000, 8000);
    }

}

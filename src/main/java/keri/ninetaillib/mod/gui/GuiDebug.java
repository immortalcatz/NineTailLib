package keri.ninetaillib.mod.gui;

import keri.ninetaillib.gui.EnumBackgroundType;
import keri.ninetaillib.gui.FluidGauge;
import keri.ninetaillib.gui.PowerBar;
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
        //Water & Lava (Light)
        FluidGauge gaugeWater = new FluidGauge(this, new Vector2i(10, 10), EnumBackgroundType.LIGHT);
        gaugeWater.enableTooltip(new Vector2i(mouseX, mouseY));
        gaugeWater.draw(FluidRegistry.getFluidStack("water", 4000), 8000);
        FluidGauge gaugeLava = new FluidGauge(this, new Vector2i(10, 90), EnumBackgroundType.LIGHT);
        gaugeLava.enableTooltip(new Vector2i(mouseX, mouseY));
        gaugeLava.draw(FluidRegistry.getFluidStack("lava", 7500), 8000);
        //Water & Lava (Dark)
        FluidGauge gaugeWaterDark = new FluidGauge(this, new Vector2i(40, 10), EnumBackgroundType.DARK);
        gaugeWaterDark.enableTooltip(new Vector2i(mouseX, mouseY));
        gaugeWaterDark.draw(FluidRegistry.getFluidStack("water", 6000), 8000);
        FluidGauge gaugeLavaDark = new FluidGauge(this, new Vector2i(40, 90), EnumBackgroundType.DARK);
        gaugeLavaDark.enableTooltip(new Vector2i(mouseX, mouseY));
        gaugeLavaDark.draw(FluidRegistry.getFluidStack("lava", 4000), 8000);
        //RF & Tesla (Light)
        PowerBar powerBarRedstoneFlux = new PowerBar(this, new Vector2i(70, 10), EnumBackgroundType.LIGHT, PowerBar.PowerType.RF);
        powerBarRedstoneFlux.enableTooltip(new Vector2i(mouseX, mouseY));
        powerBarRedstoneFlux.draw(3450, 8000);
        PowerBar powerBarTesla = new PowerBar(this, new Vector2i(70, 90), EnumBackgroundType.LIGHT, PowerBar.PowerType.TESLA);
        powerBarTesla.enableTooltip(new Vector2i(mouseX, mouseY));
        powerBarTesla.draw(6000, 8000);
        //RF & Tesla (Dark)
        PowerBar powerBarRedstoneFluxDark = new PowerBar(this, new Vector2i(100, 10), EnumBackgroundType.DARK, PowerBar.PowerType.RF);
        powerBarRedstoneFluxDark.enableTooltip(new Vector2i(mouseX, mouseY));
        powerBarRedstoneFluxDark.draw(5500, 8000);
        PowerBar powerBarTeslaDark = new PowerBar(this, new Vector2i(100, 90), EnumBackgroundType.DARK, PowerBar.PowerType.TESLA);
        powerBarTeslaDark.enableTooltip(new Vector2i(mouseX, mouseY));
        powerBarTeslaDark.draw(1200, 8000);
    }

}

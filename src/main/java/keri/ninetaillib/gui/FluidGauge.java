package keri.ninetaillib.gui;

import keri.ninetaillib.mod.util.ModPrefs;
import keri.ninetaillib.util.ResourceAction;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FluidGauge {

    private final int HEIGHT = 64;
    private final int WIDTH = 16;
    private final ResourceAction texture = new ResourceAction(ModPrefs.MODID, "textures/gui/elements.png");
    private final GuiScreen gui;
    private final EnumBackgroundType background;
    private final int x;
    private final int y;

    public FluidGauge(GuiScreen gui, Vector2i pos, EnumBackgroundType background){
        this.gui = gui;
        this.background = background;
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public void draw(Fluid fluid, int amount){
        this.draw(new FluidStack(fluid, amount));
    }

    public void draw(FluidTank tank){
        this.draw(tank.getFluid());
    }

    public void draw(FluidStack fluid){
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.color(1F, 1F, 1F, 1F);
        //todo HAHAHA i am not cracra
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

}

package keri.ninetaillib.gui;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Rectangle4i;
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

    public void draw(Fluid fluid, int amount, int maxAmount){
        this.draw(new FluidStack(fluid, amount), maxAmount);
    }

    public void draw(FluidTank tank){
        this.draw(tank.getFluid(), tank.getCapacity());
    }

    public void draw(FluidStack fluid, int maxAmount){
        this.texture.bind(true);
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.color(1F, 1F, 1F, 1F);

        if(this.background == EnumBackgroundType.LIGHT){
            this.gui.drawTexturedModalRect(this.x, this.y, 3, 106, 20, 68);
        }
        else if(this.background == EnumBackgroundType.DARK){
            this.gui.drawTexturedModalRect(this.x, this.y, 3, 176, 20, 68);
        }

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        Rectangle4i dimension = new Rectangle4i(this.x + 2, this.y + 50, 16, 16);
        RenderUtils.preFluidRender();
        double density = ((fluid.amount * 64D) / maxAmount * 64D) / 1000D;
        RenderUtils.renderFluidGauge(fluid, dimension, density, 16D);
        RenderUtils.postFluidRender();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        this.texture.bind(true);

        if(this.background == EnumBackgroundType.LIGHT){
            this.gui.drawTexturedModalRect(this.x + 1, this.y + 1, 24, 107, 18, 66);
        }
        else if(this.background == EnumBackgroundType.DARK){
            this.gui.drawTexturedModalRect(this.x + 1, this.y + 1, 24, 177, 18, 66);
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

}

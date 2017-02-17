package keri.ninetaillib.gui;

import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Rectangle4i;
import com.google.common.collect.Lists;
import keri.ninetaillib.mod.util.ModPrefs;
import keri.ninetaillib.util.ResourceAction;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class FluidGauge {

    private final int WIDTH = 20;
    private final int HEIGHT = 68;
    private final ResourceAction texture = new ResourceAction(ModPrefs.MODID, "textures/gui/elements.png");
    private final GuiScreen gui;
    private final EnumBackgroundType background;
    private Vector2i position;
    private boolean enableTooltip = false;
    private Vector2i mousePos;

    public FluidGauge(GuiScreen gui, Vector2i pos, EnumBackgroundType background){
        this.gui = gui;
        this.background = background;
        this.position = pos;
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
            this.gui.drawTexturedModalRect(this.position.getX(), this.position.getY(), 3, 106, this.WIDTH, this.HEIGHT);
        }
        else if(this.background == EnumBackgroundType.DARK){
            this.gui.drawTexturedModalRect(this.position.getX(), this.position.getY(), 3, 176, this.WIDTH, this.HEIGHT);
        }

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        Rectangle4i dimension = new Rectangle4i(this.position.getX() + 2, this.position.getY() + 50, 16, 16);
        double density = ((fluid.amount * 64D) / maxAmount * 64D) / 1000D;
        RenderUtils.preFluidRender();
        RenderUtils.renderFluidGauge(fluid, dimension, density, 16D);
        RenderUtils.postFluidRender();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        this.texture.bind(true);

        if(this.background == EnumBackgroundType.LIGHT){
            this.gui.drawTexturedModalRect(this.position.getX() + 1, this.position.getY() + 1, 24, 107, this.WIDTH - 2, this.HEIGHT - 2);
        }
        else if(this.background == EnumBackgroundType.DARK){
            this.gui.drawTexturedModalRect(this.position.getX() + 1, this.position.getY() + 1, 24, 177, this.WIDTH - 2, this.HEIGHT - 2);
        }

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();

        if(this.enableTooltip){
            AxisAlignedBB aabb = new AxisAlignedBB(this.position.getX(), this.position.getY(), 0D, this.position.getX() + this.WIDTH, this.position.getY() + this.HEIGHT, 0D);

            if(aabb.intersectsWithXY(new Vec3d(this.mousePos.getX(), this.mousePos.getY(), 0D))){
                GlStateManager.pushMatrix();
                GlStateManager.pushAttrib();
                GlStateManager.color(1F, 1F, 1F, 1F);
                FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                ArrayList<String> text = Lists.newArrayList(Integer.toString(fluid.amount) + " mB");
                int screenWidth = Minecraft.getMinecraft().displayWidth;
                int screenHeight = Minecraft.getMinecraft().displayHeight;
                GuiUtils.drawHoveringText(text, this.mousePos.getX(), this.mousePos.getY(), screenWidth, screenHeight, 200, fontRenderer);
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            }
        }
    }

    public void enableTooltip(Vector2i mousePos){
        this.enableTooltip = true;
        this.mousePos = mousePos;
    }

}

package keri.ninetaillib.gui.modular;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.gui.EnumRenderType;
import keri.ninetaillib.util.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementLabel implements IGuiElement {

    private Vector2i position;
    private ColourRGBA color;
    private boolean shadow;
    private String text;

    public ElementLabel(Vector2i position, String text, ColourRGBA color, boolean hasShadow){
        this.position = position;
        this.text = text;
        this.color = color;
        this.shadow = hasShadow;
    }

    @Override
    public void onGuiInit(GuiScreen gui) {

    }

    @Override
    public void renderElement(VertexBuffer buffer, GuiScreen gui, EnumRenderType type) {
        if(type == EnumRenderType.FOREGROUND){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            FontRenderer renderer = Minecraft.getMinecraft().fontRendererObj;

            if(this.shadow){
                renderer.drawStringWithShadow(
                        this.text,
                        (float)this.position.getX(),
                        (float)this.position.getY(),
                        this.color.argb()
                );
            }
            else{
                renderer.drawString(
                        this.text,
                        this.position.getX(),
                        this.position.getY(),
                        this.color.argb()
                );
            }

            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
    }

}

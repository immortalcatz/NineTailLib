package keri.ninetaillib.gui;

import keri.ninetaillib.util.ResourceAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

public class ButtonAnimated extends GuiButton {

    private ResourceAction texture;
    private ResourceAction textureClicked;
    private IButtonAction action = null;
    private boolean isAnimated = false;
    private int renderTicks = 0;

    public ButtonAnimated(int id, int x, int y, ResourceLocation texture) {
        super(id, x, y, "");
        this.texture = new ResourceAction(texture);
        this.textureClicked = new ResourceAction(texture);
    }

    public ButtonAnimated(int id, int x, int y, ResourceLocation texture, ResourceLocation textureClicked) {
        super(id, x, y, "");
        this.texture = new ResourceAction(texture);
        this.textureClicked = new ResourceAction(textureClicked);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.translate(this.xPosition, this.yPosition, 10D);
        GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);

        if(this.isMouseOver(mouseX, mouseY, this.xPosition, this.yPosition)){
            if(renderTicks < 64){
                this.renderTicks += 8;
            }

            float scale = (float)(this.renderTicks / 64D);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, 0D);
            this.textureClicked.bind(true);
            GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, 10F);
            GlStateManager.popMatrix();
        }
        else{
            this.renderTicks = 0;
            this.texture.bind(true);
            GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, 10F);
        }

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y){
        if(mouseX >= x && mouseX <= (x + 16)){
            if(mouseY >= y && mouseY <= (y + 16)){
                return true;
            }
        }

        return false;
    }

    public void setAction(IButtonAction action){
        this.action = action;
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY){
        if(this.isMouseOver(mouseX, mouseY, this.xPosition, this.yPosition)){
            if(this.action != null){
                this.action.performAction();
            }

            return super.mousePressed(minecraft, mouseX, mouseY);
        }

        return false;
    }

}

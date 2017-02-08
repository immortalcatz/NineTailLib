package keri.ninetaillib.mod.gui;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ClientUtils;
import com.google.common.collect.Maps;
import keri.ninetaillib.util.ShaderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;

import javax.vecmath.Vector2f;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class GuiFriendslist extends GuiScreen {

    private final ColourRGBA defaultColor = new ColourRGBA(200, 200, 200, 255);
    private static Map<UUID, ColourRGBA> colorMap = Maps.newHashMap();
    private boolean animatedPreview = true;

    static{
        colorMap.put(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"), new ColourRGBA(250, 150, 0, 255));
        colorMap.put(UUID.fromString("e3ec1c24-817a-4879-880a-edce0d980699"), new ColourRGBA(250, 250, 0, 255));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ColourRGBA color = this.defaultColor;

        if(player != null && player.getGameProfile().getId() != null){
            color = colorMap.get(player.getGameProfile().getId());
        }

        this.drawMenuBackground(color);
        this.drawPlayerPreview(player);
    }

    private void drawMenuBackground(ColourRGBA color){
        int left = 12;
        int top = 12;
        int right = this.width - 12;
        int bottom = this.height - 12;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0D, 0D, -10D);
        int colorD = color.argb();
        int colorN = new ColourRGBA(color.r, color.g, color.b, color.a - 40).argb();
        int colorL = new ColourRGBA(color.r, color.g, color.b, color.a - 120).argb();
        this.drawGradientRect(left, top, right, bottom, colorN,colorL);
        this.drawHorizontalLine(left, right, top, colorD);
        this.drawHorizontalLine(left, right, bottom, colorD);
        this.drawVerticalLine(left, top, bottom, colorD);
        this.drawVerticalLine(right, top, bottom, colorD);
        GlStateManager.popMatrix();
    }

    private void drawPlayerPreview(EntityPlayer player){
        int top = 24;
        int right = (this.width - 12) - 120;
        int bottom = this.height - 24;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0D, 0D, -9D);
        this.drawGradientRect(right, top, right + 108, bottom, 0xFF000000, 0xFF000000);
        this.drawHorizontalLine(right, right + 108, top, 0xCCCCCCCC);
        this.drawHorizontalLine(right, right + 108, bottom, 0xCCCCCCCC);
        this.drawVerticalLine(right, top, bottom, 0xCCCCCCCC);
        this.drawVerticalLine(right + 108, top, bottom, 0xCCCCCCCC);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)right + 54, (float)top + 190, 50.0F);
        GlStateManager.scale((float)(-80), (float)80, (float)80);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = player.renderYawOffset;
        float f1 = player.rotationYaw;
        float f2 = player.rotationPitch;
        float f3 = player.prevRotationYawHead;
        float f4 = player.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(0F / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        player.renderYawOffset = (float)Math.atan((double)(0F / 40.0F)) * 20.0F;
        player.rotationYaw = (float)Math.atan((double)(0F / 40.0F)) * 40.0F;
        player.rotationPitch = -((float)Math.atan((double)(0F / 40.0F))) * 20.0F;
        player.rotationYawHead = player.rotationYaw;
        player.prevRotationYawHead = player.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);

        if(this.animatedPreview){
            GlStateManager.rotate((float)ClientUtils.getRenderTime(), 0F, 1F, 0F);
        }

        rendermanager.doRenderEntity(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        player.renderYawOffset = f;
        player.rotationYaw = f1;
        player.rotationPitch = f2;
        player.prevRotationYawHead = f3;
        player.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0D, 0D, -8D);
        this.fontRendererObj.drawStringWithShadow(player.getGameProfile().getName(), right + 4, top + 4, 0xFFFFFFFF);
        GlStateManager.popMatrix();
    }

    @Override
    public void initGui() {
        int left = 12;
        int top = 12;
        int right = this.width - 12;
        int bottom = this.height - 12;
        int buttonWidth = 20;
        int buttonHeight = 20;
        GuiButton buttonClose = new GuiButton(0, left + 2, top + 2, buttonWidth, buttonHeight, "X");
        this.addButton(buttonClose);
        GuiButton buttonAnimation = new GuiButton(1, left + 24, top + 2, buttonWidth, buttonHeight, "A");
        this.addButton(buttonAnimation);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if(button.id == 0){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;

            if(player.capabilities.isCreativeMode){
                FMLClientHandler.instance().displayGuiScreen(player, new GuiContainerCreative(player));
            }
            else{
                FMLClientHandler.instance().displayGuiScreen(player, new GuiInventory(player));
            }
        }
        else if(button.id == 1){
            if(this.animatedPreview){
                this.animatedPreview = false;
            }
            else if(!this.animatedPreview){
                this.animatedPreview = true;
            }
        }
    }

    private static class BGShaderCallback implements ShaderUtils.IShaderCallback {

        private Vector2f resolution;
        private ColourRGBA color;

        public BGShaderCallback(Vector2f resolution, ColourRGBA color){
            this.resolution = resolution;
            this.color = color;
        }

        @Override
        public void call(int shader) {
            float width = this.resolution.x;
            float height = this.resolution.y;
            float r = (float)(this.color.r / 255F);
            float g = (float)(this.color.g / 255F);
            float b = (float)(this.color.b / 255F);
            int resolution = ARBShaderObjects.glGetUniformLocationARB(shader, "resolution");
            ARBShaderObjects.glUniform2fARB(resolution, width, height);
            int color = ARBShaderObjects.glGetUniformLocationARB(shader, "color");
            ARBShaderObjects.glUniform3fARB(color, r, g, b);
        }

    }

}

package keri.ninetaillib.mod.gui;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ClientUtils;
import keri.ninetaillib.mod.proxy.ClientProxy;
import keri.ninetaillib.mod.util.ModPrefs;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;

import javax.vecmath.Vector2f;
import java.io.IOException;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class GuiFriendslist extends GuiScreen {

    private final ResourceLocation textureButtonClose = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_close.png");
    private final ResourceLocation textureButtonCloseClicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_close_clicked.png");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        this.drawBackground(player);
        this.drawMenuBackground();
        this.drawPlayerPreview(player, new Vector2f(mouseX, mouseY));
    }

    private void drawBackground(EntityPlayer player){
        ColourRGBA backgroundColor = new ColourRGBA(0, 0, 0, 255);
        UUID playerUUID = player.getGameProfile().getId();

        if(playerUUID.equals(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"))){
            backgroundColor = new ColourRGBA(0D, 0.3D, 0D, 1D);
        }
        else if(playerUUID.equals(UUID.fromString("e3ec1c24-817a-4879-880a-edce0d980699"))){
            backgroundColor = new ColourRGBA(0.3D, 0.3D, 0D, 1D);
        }
        else if(playerUUID.equals(UUID.fromString("1e25868f-6372-492d-8319-3a4627f0cc18"))){
            backgroundColor = new ColourRGBA(0.12D, 0D, 0.3D, 1D);
        }
        else{
            backgroundColor = new ColourRGBA(0D, 0D, 0.3D, 1D);
        }

        Vector2f resolution = new Vector2f(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0D, 0D, -10D);
        ShaderUtils.useShader(ClientProxy.backgroundShader, new BGShaderCallback(resolution, backgroundColor));
        GuiUtils.drawGradientRect(-1, 0, 0, 1920, 1080, 0xFFFFFFFF, 0xFFFFFFFF);
        ShaderUtils.releaseShader();
        GlStateManager.popMatrix();
    }

    private void drawMenuBackground(){
        int left = 12;
        int top = 12;
        int right = this.width - 12;
        int bottom = this.height - 12;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0D, 0D, -10D);
        this.drawGradientRect(left, top, right, bottom, 0x44FFFFFF,0x06FFFFFF);
        this.drawHorizontalLine(left, right, top, 0x66FFFFFF);
        this.drawHorizontalLine(left, right, bottom, 0x66FFFFFF);
        this.drawVerticalLine(left, top, bottom, 0x66FFFFFF);
        this.drawVerticalLine(right, top, bottom, 0x66FFFFFF);
        GlStateManager.popMatrix();
    }

    private void drawPlayerPreview(EntityPlayer player, Vector2f mouse){
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
        GlStateManager.translate((float)right + 55, (float)top + 170, 50.0F);
        GlStateManager.scale((float)(-60), (float)60, (float)60);
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
        GlStateManager.rotate((float)ClientUtils.getRenderTime(), 0F, 1F, 0F);
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

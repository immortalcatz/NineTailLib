package keri.ninetaillib.mod.gui;

import codechicken.lib.colour.ColourRGBA;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.mod.proxy.ClientProxy;
import keri.ninetaillib.mod.util.ModPrefs;
import keri.ninetaillib.util.ShaderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.FMLLog;
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

    private void drawPlayerPreview(GameProfile profile){

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
            FMLLog.info("HELLO WORLD");
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

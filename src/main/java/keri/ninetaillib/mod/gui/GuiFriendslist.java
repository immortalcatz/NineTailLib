package keri.ninetaillib.mod.gui;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.mod.proxy.ClientProxy;
import keri.ninetaillib.util.ShaderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;

import javax.vecmath.Vector2f;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class GuiFriendslist extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        this.drawBackground(player);
    }

    private void drawBackground(EntityPlayer player){
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        ColourRGBA backgroundColor = new ColourRGBA(0, 0, 0, 255);
        UUID playerUUID = player.getGameProfile().getId();

        if(playerUUID.equals(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"))){
            backgroundColor = new ColourRGBA(0D, 0D, 0.3D, 1D);
        }
        else if(playerUUID.equals(UUID.fromString("e3ec1c24-817a-4879-880a-edce0d980699"))){
            backgroundColor = new ColourRGBA(0.4D, 0.4D, 0D, 1D);
        }
        else{
            backgroundColor = new ColourRGBA(0D, 0D, 0.4D, 1D);
        }

        Vector2f resolution = new Vector2f(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        ShaderUtils.useShader(ClientProxy.backgroundShader, new ShaderCallback(resolution, backgroundColor));
        GuiUtils.drawGradientRect(2, 0, 0, 1920, 1080, 0xFFFFFFFF, 0xFFFFFFFF);
        ShaderUtils.releaseShader();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    private static class ShaderCallback implements ShaderUtils.IShaderCallback {

        private Vector2f resolution;
        private ColourRGBA color;

        public ShaderCallback(Vector2f resolution, ColourRGBA color){
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

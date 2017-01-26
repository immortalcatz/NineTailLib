package keri.ninetaillib.mod.friendslist;

import com.mojang.authlib.GameProfile;
import keri.ninetaillib.mod.proxy.ClientProxy;
import keri.ninetaillib.util.ShaderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBShaderObjects;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class GuiFriendslist extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GameProfile profile = Minecraft.getMinecraft().thePlayer.getGameProfile();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        final float backgroundR;
        final float backgroundG;
        final float backgroundB;

        if(profile.getId().equals(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"))){
            backgroundR = 0.3F;
            backgroundG = 0.14F;
            backgroundB = 0F;
        }
        else{
            backgroundR = 0F;
            backgroundG = 0F;
            backgroundB = 0.4F;
        }

        ShaderUtils.IShaderCallback callback = new ShaderUtils.IShaderCallback() {
            @Override
            public void call(int shader) {
                float width = Minecraft.getMinecraft().displayWidth;
                float height = Minecraft.getMinecraft().displayHeight;
                int resolution = ARBShaderObjects.glGetUniformLocationARB(shader, "resolution");
                ARBShaderObjects.glUniform2fARB(resolution, width, height);
                int color = ARBShaderObjects.glGetUniformLocationARB(shader, "color");
                ARBShaderObjects.glUniform3fARB(color, backgroundR, backgroundG, backgroundB);
            }
        };

        ShaderUtils.useShader(ClientProxy.backgroundShader, callback);
        GuiUtils.drawGradientRect(2, 0, 0, 1920, 1080, 0xFFFFFFFF, 0xFFFFFFFF);
        ShaderUtils.releaseShader();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

}

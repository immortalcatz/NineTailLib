package keri.ninetaillib.truetyper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

/**
 * Original library classes by ProfMobius - All rights go to him !
 * TrueTyper (FontHelper.java) updated to 1.10.2 by KitsuneAlex.
 * Original project: https://bitbucket.org/ProfMobius/truetyper
 */
public class FontHelper {

    public static void drawString(String s, int x, int y, TrueTypeFont font, float scaleX, float scaleY, float... rgba){
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);

        if(mc.gameSettings.hideGUI){
            return;
        }

        int amt = 1;

        if(sr.getScaleFactor() == 1){
            amt = 2;
        }

        FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrixData);
        Matrix4f matrix = new Matrix4f();
        matrix.load(matrixData);
        FontHelper.set2DMode();
        y = mc.displayHeight-(y * sr.getScaleFactor()) - (((font.getLineHeight() / amt)));
        GL11.glEnable(GL11.GL_BLEND);
        font.drawString(x * sr.getScaleFactor(), y - matrix.m31 * sr.getScaleFactor(), s, scaleX / amt, scaleY / amt, rgba);
        GL11.glDisable(GL11.GL_BLEND);
        FontHelper.set3DMode();
    }

    private static void set2DMode() {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, mc.displayWidth, 0, mc.displayHeight, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }

    private static void set3DMode() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        Minecraft mc = Minecraft.getMinecraft();
        mc.entityRenderer.setupOverlayRendering();
    }

}

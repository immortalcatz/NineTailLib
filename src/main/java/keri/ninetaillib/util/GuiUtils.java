package keri.ninetaillib.util;

import codechicken.lib.colour.ColourRGBA;
import keri.ninetaillib.internal.util.ModPrefs;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUtils extends net.minecraftforge.fml.client.config.GuiUtils {

    private static final ResourceAction texture = new ResourceAction(ModPrefs.MODID, "textures/gui/background.png");

    public static void drawBackground(GuiScreen gui, Vector2i pos, Vector2i size, Alignment alignment){
        drawBackground(gui, pos, size, alignment, new ColourRGBA(255, 255, 255, 255));
    }

    public static void drawBackground(GuiScreen gui, Vector2i pos, Vector2i size, Alignment alignment, ColourRGBA color){
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        Vector2i posCornerTL = new Vector2i(pos.getX(), pos.getY());
        Vector2i minUVCornerTL = new Vector2i(0, 0);
        Vector2i maxUVCornerTL = new Vector2i(4, 4);
        Vector2i posCornerBL = new Vector2i(pos.getX(), pos.getY() + size.getY());
        Vector2i minUVCornerBL = new Vector2i(0, 20);
        Vector2i maxUVCornerBL = new Vector2i(4, 24);
        Vector2i posCornerTR = new Vector2i(pos.getX() + size.getX(), pos.getY());
        Vector2i minUVCornerTR = new Vector2i(20, 0);
        Vector2i maxUVCornerTR = new Vector2i(24, 4);
        Vector2i posCornerBR = new Vector2i(pos.getX() + size.getX(), pos.getY() + size.getY());
        Vector2i minUVCornerBR = new Vector2i(20, 20);
        Vector2i maxUVCornerBR = new Vector2i(24, 24);

        switch(alignment){
            case LEFT:
                draw(gui, posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                draw(gui, posCornerBL, minUVCornerBL, maxUVCornerBL, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Vector2i posEdgeLeft = new Vector2i(pos.getX(), pos.getY() + 4 + left);
                    Vector2i minUVEdgeLeft = new Vector2i(0, 4);
                    Vector2i maxUVEdgeLeft = new Vector2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Vector2i posEdgeTop = new Vector2i(pos.getX() + 4 + top, pos.getY());
                    Vector2i minUVEdgeTop = new Vector2i(4, 0);
                    Vector2i maxUVEdgeTop = new Vector2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Vector2i posEdgeBottom = new Vector2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Vector2i minUVEdgeBottom = new Vector2i(4, 20);
                    Vector2i maxUVEdgeBottom = new Vector2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Vector2i posFill = new Vector2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Vector2i minUVFill = new Vector2i(4, 4);
                        Vector2i maxUVFill = new Vector2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case RIGHT:
                draw(gui, posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                draw(gui, posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int right = 0; right < size.getY() - 4; right++){
                    Vector2i posEdgeRight = new Vector2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Vector2i minUVEdgeRight = new Vector2i(20, 4);
                    Vector2i maxUVEdgeRight = new Vector2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Vector2i posEdgeTop = new Vector2i(pos.getX() + 4 + top, pos.getY());
                    Vector2i minUVEdgeTop = new Vector2i(4, 0);
                    Vector2i maxUVEdgeTop = new Vector2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Vector2i posEdgeBottom = new Vector2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Vector2i minUVEdgeBottom = new Vector2i(4, 20);
                    Vector2i maxUVEdgeBottom = new Vector2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Vector2i posFill = new Vector2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Vector2i minUVFill = new Vector2i(4, 4);
                        Vector2i maxUVFill = new Vector2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case TOP:
                draw(gui, posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                draw(gui, posCornerTR, minUVCornerTR, maxUVCornerTR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Vector2i posEdgeLeft = new Vector2i(pos.getX(), pos.getY() + 4 + left);
                    Vector2i minUVEdgeLeft = new Vector2i(0, 4);
                    Vector2i maxUVEdgeLeft = new Vector2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Vector2i posEdgeRight = new Vector2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Vector2i minUVEdgeRight = new Vector2i(20, 4);
                    Vector2i maxUVEdgeRight = new Vector2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Vector2i posEdgeTop = new Vector2i(pos.getX() + 4 + top, pos.getY());
                    Vector2i minUVEdgeTop = new Vector2i(4, 0);
                    Vector2i maxUVEdgeTop = new Vector2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Vector2i posFill = new Vector2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Vector2i minUVFill = new Vector2i(4, 4);
                        Vector2i maxUVFill = new Vector2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case BOTTOM:
                draw(gui, posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                draw(gui, posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Vector2i posEdgeLeft = new Vector2i(pos.getX(), pos.getY() + 4 + left);
                    Vector2i minUVEdgeLeft = new Vector2i(0, 4);
                    Vector2i maxUVEdgeLeft = new Vector2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Vector2i posEdgeRight = new Vector2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Vector2i minUVEdgeRight = new Vector2i(20, 4);
                    Vector2i maxUVEdgeRight = new Vector2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Vector2i posEdgeBottom = new Vector2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Vector2i minUVEdgeBottom = new Vector2i(4, 20);
                    Vector2i maxUVEdgeBottom = new Vector2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Vector2i posFill = new Vector2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Vector2i minUVFill = new Vector2i(4, 4);
                        Vector2i maxUVFill = new Vector2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
            case NONE:
                draw(gui, posCornerTL, minUVCornerTL, maxUVCornerTL, color);
                draw(gui, posCornerTR, minUVCornerTR, maxUVCornerTR, color);
                draw(gui, posCornerBL, minUVCornerBL, maxUVCornerBL, color);
                draw(gui, posCornerBR, minUVCornerBR, maxUVCornerBR, color);

                for(int left = 0; left < size.getY() - 4; left++){
                    Vector2i posEdgeLeft = new Vector2i(pos.getX(), pos.getY() + 4 + left);
                    Vector2i minUVEdgeLeft = new Vector2i(0, 4);
                    Vector2i maxUVEdgeLeft = new Vector2i(4, 1);
                    draw(gui, posEdgeLeft, minUVEdgeLeft, maxUVEdgeLeft, color);
                }

                for(int right = 0; right < size.getY() - 4; right++){
                    Vector2i posEdgeRight = new Vector2i(pos.getX() + size.getX(), pos.getY() + 4 + right);
                    Vector2i minUVEdgeRight = new Vector2i(20, 4);
                    Vector2i maxUVEdgeRight = new Vector2i(24, 1);
                    draw(gui, posEdgeRight, minUVEdgeRight, maxUVEdgeRight, color);
                }

                for(int top = 0; top < size.getX() - 4; top++){
                    Vector2i posEdgeTop = new Vector2i(pos.getX() + 4 + top, pos.getY());
                    Vector2i minUVEdgeTop = new Vector2i(4, 0);
                    Vector2i maxUVEdgeTop = new Vector2i(1, 4);
                    draw(gui, posEdgeTop, minUVEdgeTop, maxUVEdgeTop, color);
                }

                for(int bottom = 0; bottom < size.getX() - 4; bottom++){
                    Vector2i posEdgeBottom = new Vector2i(pos.getX() + 4 + bottom, pos.getY() + size.getY());
                    Vector2i minUVEdgeBottom = new Vector2i(4, 20);
                    Vector2i maxUVEdgeBottom = new Vector2i(1, 24);
                    draw(gui, posEdgeBottom, minUVEdgeBottom, maxUVEdgeBottom, color);
                }

                for(int fillX = 0; fillX < size.getX() - 4; fillX++){
                    for(int fillY = 0; fillY < size.getY() - 4; fillY++){
                        Vector2i posFill = new Vector2i(pos.getX() + 4 + fillX, pos.getY() + 4 + fillY);
                        Vector2i minUVFill = new Vector2i(4, 4);
                        Vector2i maxUVFill = new Vector2i(1, 1);
                        draw(gui, posFill, minUVFill, maxUVFill, color);
                    }
                }

                break;
        }

        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    private static void draw(GuiScreen gui, Vector2i pos, Vector2i minUV, Vector2i maxUV, ColourRGBA color){
        texture.bind(true);
        color.glColour();
        gui.drawTexturedModalRect(pos.getX(), pos.getY(), minUV.getX(), minUV.getY(), maxUV.getX(), maxUV.getY());
    }

    public static enum Alignment {

        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        NONE

    }

}

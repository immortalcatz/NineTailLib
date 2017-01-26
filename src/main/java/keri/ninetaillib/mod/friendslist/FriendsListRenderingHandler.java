package keri.ninetaillib.mod.friendslist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FriendsListRenderingHandler {

    private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    private final FontRenderer galacticFontRenderer = Minecraft.getMinecraft().standardGalacticFontRenderer;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event){

    }

    private void drawBackground(int x, int y, int width, int height){

    }

}

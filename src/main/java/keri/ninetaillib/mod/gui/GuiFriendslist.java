package keri.ninetaillib.mod.gui;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ClientUtils;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.gui.ColoredRectangle;
import keri.ninetaillib.util.Vector2i;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class GuiFriendslist extends GuiScreen {

    private final ColourRGBA defaultColor = new ColourRGBA(200, 200, 200, 255);
    private static Map<UUID, ColourRGBA> colorMap = Maps.newHashMap();
    private boolean animatedPreview = true;
    private GameProfile currentPreview = FriendsListHandler.INSTANCE.getPlayerProfile();

    static{
        colorMap.put(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d"), new ColourRGBA(250, 150, 0, 255));
        colorMap.put(UUID.fromString("e3ec1c24-817a-4879-880a-edce0d980699"), new ColourRGBA(250, 250, 0, 255));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ColourRGBA color = this.defaultColor;

        if(player != null && player.getGameProfile() != null){
            color = colorMap.get(player.getGameProfile().getId());
        }

        this.drawMenuBackground(color);
        this.drawPlayerPreview(null, null, null);
    }

    private void drawMenuBackground(ColourRGBA color){
        Vector2i start = new Vector2i(12, 12);
        Vector2i end = new Vector2i(this.width - 12, this.height - 12);
        ColourRGBA colorStart = new ColourRGBA(color.r, color.g, color.b, color.a - 40);
        ColourRGBA colorEnd = new ColourRGBA(color.r, color.g, color.b, color.a - 180);
        ColoredRectangle rectangle = new ColoredRectangle(start, end, colorStart, colorEnd);
        rectangle.setHasBorder(true);
        rectangle.setZLevel(-10);
        rectangle.draw();
    }

    private void drawPlayerPreview(MinecraftServer server, WorldServer world, PlayerInteractionManager pim){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        int top = 24;
        int right = (this.width - 12) - 120;
        int bottom = this.height - 24;
        ColourRGBA color = new ColourRGBA(10, 10, 10, 255);
        Vector2i start = new Vector2i(right, top);
        Vector2i end = new Vector2i(right + 108, bottom);
        ColoredRectangle rectangle = new ColoredRectangle(start, end, color);
        rectangle.setHasBorder(true);
        rectangle.setZLevel(-9);
        rectangle.draw();
        GlStateManager.pushMatrix();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)right + 54, (float)top + 190, 50.0F);
        GlStateManager.scale(-80F, 80F, 80F);
        GlStateManager.rotate(180F, 0F, 0F, 1F);
        RenderHelper.enableStandardItemLighting();
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setRenderShadow(false);

        if(this.animatedPreview){
            GlStateManager.rotate((float)ClientUtils.getRenderTime(), 0F, 1F, 0F);
        }

        rendermanager.doRenderEntity(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();

        if(player != null && player.getGameProfile() != null){
            this.fontRendererObj.drawStringWithShadow(player.getGameProfile().getName(), right + 4, top + 4, 0xFFFFFFFF);
        }
        else{
            this.fontRendererObj.drawStringWithShadow("Invalid GameProfile !", right + 4, top + 4, 0xFFFFFFFF);
        }

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
        GuiButton buttonAnimation = new GuiButton(1, left + 23, top + 2, buttonWidth, buttonHeight, "A");
        this.addButton(buttonAnimation);
        GuiButton buttonListPrev = new GuiButton(2, left + 44, top + 2, buttonWidth, buttonHeight, "<");
        this.addButton(buttonListPrev);
        GuiButton buttonListNext = new GuiButton(3, left + 65, top + 2, buttonWidth, buttonHeight, ">");
        this.addButton(buttonListNext);
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
        else if(button.id == 2){

        }
        else if(button.id == 3){

        }
    }

}

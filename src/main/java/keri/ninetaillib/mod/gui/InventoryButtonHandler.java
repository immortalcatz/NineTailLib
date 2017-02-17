package keri.ninetaillib.mod.gui;

import com.mojang.authlib.GameProfile;
import keri.ninetaillib.gui.ButtonAnimated;
import keri.ninetaillib.gui.IButtonAction;
import keri.ninetaillib.mod.util.ModPrefs;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class InventoryButtonHandler {

    private final ResourceLocation button_debug = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_debug.png");
    private final ResourceLocation button_debug_clicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_debug_clicked.png");

    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent event){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameProfile profile = player != null ? player.getGameProfile() : CommonUtils.DEFAULT_PROFILE;

        if(event.getGui() instanceof GuiContainerCreative || event.getGui() instanceof GuiInventory){
            if(profile.getId().equals(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d")) && CommonUtils.isDevEnvironment()){
                IButtonAction actionDebug = new IButtonAction() {
                    @Override
                    public void performAction() {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        FMLClientHandler.instance().displayGuiScreen(player, new GuiDebug());
                    }
                };

                ButtonAnimated buttonDebug = new ButtonAnimated(13412, 0, 0, this.button_debug, this.button_debug_clicked);
                buttonDebug.setAction(actionDebug);
                event.getButtonList().add(buttonDebug);
            }
        }
    }

}

package keri.ninetaillib.internal.client.gui;

import com.mojang.authlib.GameProfile;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class InventoryButtonHandler {

    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent event){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameProfile profile = player != null ? player.getGameProfile() : CommonUtils.DEFAULT_PROFILE;

        if(event.getGui() instanceof GuiContainerCreative || event.getGui() instanceof GuiInventory){
            if(profile.getId().equals(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d")) && CommonUtils.isDevEnvironment()){
                GuiButton button = new GuiButton(2400, 10, 10, 20, 20, "D");
                event.getButtonList().add(button);
            }
        }
    }

    @SubscribeEvent
    public void onGuiActionPerformed(GuiScreenEvent.ActionPerformedEvent event){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameProfile profile = player != null ? player.getGameProfile() : CommonUtils.DEFAULT_PROFILE;

        if(event.getGui() instanceof GuiContainerCreative || event.getGui() instanceof GuiInventory){
            if(profile.getId().equals(UUID.fromString("b2ac8c03-d994-4805-9e0f-57fede63c04d")) && CommonUtils.isDevEnvironment()){
                if(event.getButton().id == 2400){
                    FMLClientHandler.instance().displayGuiScreen(player, new GuiDebug());
                }
            }
        }
    }

}

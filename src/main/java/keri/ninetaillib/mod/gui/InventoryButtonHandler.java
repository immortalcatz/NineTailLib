package keri.ninetaillib.mod.gui;

import keri.ninetaillib.gui.ButtonWithIcon;
import keri.ninetaillib.gui.IButtonAction;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class InventoryButtonHandler {

    private final ResourceLocation textureFriendslist = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_friendslist.png");
    private final ResourceLocation textureFriendslistClicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_friendslist_clicked.png");

    @SubscribeEvent
    public void onGuiScreen(GuiScreenEvent.InitGuiEvent event){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if(event.getGui() instanceof InventoryEffectRenderer){
            int startX = (event.getGui().width - 176) / 2;
            int startY = (event.getGui().height - 166) / 2;

            if(event.getGui() instanceof GuiContainerCreative){
                startX -= 30;
                startY += 0;
            }
            else{
                startX -= 20;
                startY += 0;
            }

            IButtonAction actionFriendslist = new IButtonAction() {
                @Override
                public void performAction(){
                    FMLClientHandler.instance().displayGuiScreen(player, new GuiFriendslist());
                }
            };

            ButtonWithIcon buttonFriendsList = new ButtonWithIcon(295830, startX, startY, this.textureFriendslist, this.textureFriendslistClicked);
            buttonFriendsList.setAction(actionFriendslist);
            event.getButtonList().add(buttonFriendsList);
        }
    }

}

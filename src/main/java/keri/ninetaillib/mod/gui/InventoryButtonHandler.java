package keri.ninetaillib.mod.gui;

import keri.ninetaillib.gui.ButtonWithIcon;
import keri.ninetaillib.gui.IButtonAction;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

@SideOnly(Side.CLIENT)
public class InventoryButtonHandler {

    private final ResourceLocation textureFriendslist = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_friendslist.png");
    private final ResourceLocation textureFriendslistClicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_friendslist_clicked.png");
    private final ResourceLocation textureClear = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_clear.png");
    private final ResourceLocation textureClearClicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_clear_clicked.png");
    private final ResourceLocation textureDay = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_day.png");
    private final ResourceLocation textureDayClicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_day_clicked.png");
    private final ResourceLocation textureNight = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_night.png");
    private final ResourceLocation textureNightClicked = new ResourceLocation(ModPrefs.MODID, "textures/gui/button_night_clicked.png");

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

            IButtonAction actionClearInventory = new IButtonAction() {
                @Override
                public void performAction() {
                    UUID playerId = FMLClientHandler.instance().getClientPlayerEntity().getGameProfile().getId();
                    EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerId);

                    if(player != null){
                        for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                            if(player.inventory.getStackInSlot(i) != null){
                                player.inventory.setInventorySlotContents(i, null);
                            }
                        }

                        player.inventory.markDirty();
                    }
                }
            };

            ButtonWithIcon buttonClearInventory = new ButtonWithIcon(295831, startX, startY + 18, this.textureClear, this.textureClearClicked);
            buttonClearInventory.setAction(actionClearInventory);
            event.getButtonList().add(buttonClearInventory);

            IButtonAction actionDay = new IButtonAction() {
                @Override
                public void performAction() {
                    UUID playerId = FMLClientHandler.instance().getClientPlayerEntity().getGameProfile().getId();
                    EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerId);

                    if(player.canCommandSenderUseCommand(2, "time set day")){
                        World[] worlds = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;

                        for(int i = 0; i < worlds.length; i++){
                            worlds[i].setWorldTime(1000);
                        }
                    }
                }
            };

            ButtonWithIcon buttonDay = new ButtonWithIcon(295832, startX, startY + 36, this.textureDay, this.textureDayClicked);
            buttonDay.setAction(actionDay);
            event.getButtonList().add(buttonDay);

            IButtonAction actionNight = new IButtonAction() {
                @Override
                public void performAction() {
                    UUID playerId = FMLClientHandler.instance().getClientPlayerEntity().getGameProfile().getId();
                    EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerId);

                    if(player.canCommandSenderUseCommand(2, "time set night")){
                        World[] worlds = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;

                        for(int i = 0; i < worlds.length; i++){
                            worlds[i].setWorldTime(13000);
                        }
                    }
                }
            };

            ButtonWithIcon buttonNight = new ButtonWithIcon(295833, startX, startY + 54, this.textureNight, this.textureNightClicked);
            buttonNight.setAction(actionNight);
            event.getButtonList().add(buttonNight);
        }
    }

}

package keri.ninetaillib.mod.gui;

import keri.ninetaillib.mod.friendslist.GuiFriendslist;
import keri.ninetaillib.mod.util.ModPrefs;
import keri.ninetaillib.util.ResourceAction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;

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
                    /* todo */
                    //Implement proper inventory clearing
                }
            };

            ButtonWithIcon buttonClearInventory = new ButtonWithIcon(295831, startX, startY + 18, this.textureClear, this.textureClearClicked);
            buttonClearInventory.setAction(actionClearInventory);
            event.getButtonList().add(buttonClearInventory);

            IButtonAction actionDay = new IButtonAction() {
                @Override
                public void performAction() {
                    World[] worlds = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;

                    for(int i = 0; i < worlds.length; i++){
                        worlds[i].setWorldTime(1000);
                    }
                }
            };

            ButtonWithIcon buttonDay = new ButtonWithIcon(295832, startX, startY + 36, this.textureDay, this.textureDayClicked);
            buttonDay.setAction(actionDay);
            event.getButtonList().add(buttonDay);

            IButtonAction actionNight = new IButtonAction() {
                @Override
                public void performAction() {
                    World[] worlds = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;

                    for(int i = 0; i < worlds.length; i++){
                        worlds[i].setWorldTime(13000);
                    }
                }
            };

            ButtonWithIcon buttonNight = new ButtonWithIcon(295833, startX, startY + 54, this.textureNight, this.textureNightClicked);
            buttonNight.setAction(actionNight);
            event.getButtonList().add(buttonNight);
        }
    }

    private static class ButtonWithIcon extends GuiButton {

        private ResourceAction texture;
        private ResourceAction textureClicked;
        private IButtonAction action = null;
        private int renderTicks = 0;

        public ButtonWithIcon(int id, int x, int y, ResourceLocation texture) {
            super(id, x, y, "");
            this.texture = new ResourceAction(texture);
            this.textureClicked = new ResourceAction(texture);
        }

        public ButtonWithIcon(int id, int x, int y, ResourceLocation texture, ResourceLocation textureClicked) {
            super(id, x, y, "");
            this.texture = new ResourceAction(texture);
            this.textureClicked = new ResourceAction(textureClicked);
        }

        @Override
        public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
            int x = this.xPosition;
            int y = this.yPosition;
            float zLevel = 0F;
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1F, 1F, 1F, 1F);
            GlStateManager.translate(x, y, 0D);
            GlStateManager.scale(0.0625D, 0.0625D, 0.0625D);

            if(this.isMouseOver(mouseX, mouseY, x, y)){
                if(renderTicks < 64){
                    this.renderTicks += 8;
                }

                GlStateManager.pushMatrix();
                float scale = (float)(this.renderTicks / 64D);
                GlStateManager.scale(scale, scale, 0D);
                this.textureClicked.bind(true);
                GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, zLevel);
                GlStateManager.popMatrix();
            }
            else{
                this.renderTicks = 0;
                this.texture.bind(true);
                GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, zLevel);
            }

            GlStateManager.disableBlend();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }

        private boolean isMouseOver(int mouseX, int mouseY, int x, int y){
            if(mouseX >= x && mouseX <= x + 16){
                if(mouseY >= y && mouseY <= y + 16){
                    return true;
                }
            }

            return false;
        }

        public void setAction(IButtonAction action){
            this.action = action;
        }

        @Override
        public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY){
            if(this.isMouseOver(mouseX, mouseY, this.xPosition, this.yPosition)){
                if(this.action != null){
                    this.action.performAction();
                }
            }

            return super.mousePressed(minecraft, mouseX, mouseY);
        }

    }

    private static interface IButtonAction {

        void performAction();

    }

}

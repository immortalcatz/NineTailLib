package keri.ninetaillib.internal.client;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.util.ClientUtils;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.internal.client.playereffect.IPlayerEffect;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    public static final ClientEventHandler INSTANCE = new ClientEventHandler();
    private Map<UUID, IPlayerEffect> playerEffects = Maps.newHashMap();
    private int textRenderTick = 0;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event){
        if(NineTailLib.CONFIG.enableDarkoEasteregg){
            this.darkoText();
        }
    }

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Post event){
        if(event.getEntityPlayer() != null && event.getEntityPlayer().getGameProfile() != null){
            EntityPlayer player = event.getEntityPlayer();

            for(Map.Entry<UUID, IPlayerEffect> entry : playerEffects.entrySet()){
                if(player.getGameProfile().getId().equals(entry.getKey())){
                    entry.getValue().renderPlayerEffect(player, event.getPartialRenderTick());
                }
            }
        }
    }

    private void darkoText(){
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameProfile profile = player != null ? player.getGameProfile() : CommonUtils.DEFAULT_PROFILE;

        if(FMLClientHandler.instance().isGUIOpen(GuiMainMenu.class)){
            if(profile.getId().equals(UUID.fromString("10755ea6-9721-467a-8b5c-92adf689072c"))){
                if(this.textRenderTick < 60){
                    this.textRenderTick++;
                }
                else{
                    this.textRenderTick = 0;
                }

            Color color = Color.getHSBColor((float)ClientUtils.getRenderTime() / 140F, 1F, 1F);
            ColourRGBA textColor = CommonUtils.fromAWT(color);

                for(int i = 0; i < 40; i++){
                    for(int j = 0; j < 4; j++){
                        float x = 36F + (100F * j);
                        float y = (10F + (12F * i) + (float)this.textRenderTick);
                        GlStateManager.pushMatrix();
                        GlStateManager.pushAttrib();
                        GlStateManager.translate(0D, 0D, 100D);
                        GlStateManager.translate(x, y, 0D);
                        GlStateManager.scale(0.6D, 0.6D, 0.6D);
                        fontRenderer.drawStringWithShadow("Happy birthday Darko ! ^_^", 0F, 0F, textColor.argb());
                        GlStateManager.popAttrib();
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
    }

    public void registerPlayerEffect(UUID player, IPlayerEffect effect){
        if(effect != null){
            playerEffects.put(player, effect);
        }
        else{
            throw new IllegalArgumentException("Cannot add IPlayerEffect !");
        }
    }

}

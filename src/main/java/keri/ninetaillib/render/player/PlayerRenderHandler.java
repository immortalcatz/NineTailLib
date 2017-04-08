package keri.ninetaillib.render.player;

import keri.ninetaillib.render.item.IBaubleRenderingHandler;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class PlayerRenderHandler implements LayerRenderer<EntityPlayer> {

    private List<IBaubleRenderingHandler> baubleRenderingHandlers;

    public PlayerRenderHandler(List<IBaubleRenderingHandler> baubleRenderingHandlers){
        this.baubleRenderingHandlers = baubleRenderingHandlers;
    }

    @Override
    public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        float[] playerData = new float[]{
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                scale
        };

        BaubleRenderingDispatcher dispatcher = new BaubleRenderingDispatcher(this.baubleRenderingHandlers);
        dispatcher.disptatchRenderers(player, playerData, partialTicks);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

}

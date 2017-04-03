package keri.ninetaillib.internal.client.handler;

import keri.ninetaillib.internal.client.render.BaubleRenderingDispatcher;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerRenderHandler implements LayerRenderer<EntityPlayer> {

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

        BaubleRenderingDispatcher.INSTANCE.disptatchRenderers(player, playerData, partialTicks);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

}

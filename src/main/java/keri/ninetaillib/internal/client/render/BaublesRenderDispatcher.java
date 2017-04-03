package keri.ninetaillib.internal.client.render;

import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import keri.ninetaillib.render.BaublesRenderingRegistry;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BaublesRenderDispatcher {

    public static final BaublesRenderDispatcher INSTANCE = new BaublesRenderDispatcher();

    public void disptatchRenderers(EntityPlayer player, float[] playerData, float partialTicks){
        if(player.getActivePotionEffect(MobEffects.INVISIBILITY) != null){
            return;
        }

        @SuppressWarnings("deprecation")
        IInventory inventory = BaublesApi.getBaubles(player);

        BaublesRenderingRegistry.INSTANCE.getRenderingHandlers().forEach(handler -> {
            handler.renderBauble(inventory, player, partialTicks, IRenderBauble.RenderType.BODY);
            float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
            float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
            float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
            GlStateManager.pushMatrix();
            GlStateManager.rotate(yawOffset, 0, -1, 0);
            GlStateManager.rotate(yaw - 270, 0, 1, 0);
            GlStateManager.rotate(pitch, 0, 0, 1);
            handler.renderBauble(inventory, player, partialTicks, IRenderBauble.RenderType.HEAD);
            GlStateManager.popMatrix();
        });
    }

}

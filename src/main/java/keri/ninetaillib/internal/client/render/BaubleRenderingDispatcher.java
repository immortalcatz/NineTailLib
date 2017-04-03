package keri.ninetaillib.internal.client.render;

import baubles.api.BaublesApi;
import baubles.api.render.IRenderBauble;
import keri.ninetaillib.render.BaubleRenderingRegistry;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.IntStream;

@SideOnly(Side.CLIENT)
public class BaubleRenderingDispatcher {

    public static final BaubleRenderingDispatcher INSTANCE = new BaubleRenderingDispatcher();

    public void disptatchRenderers(EntityPlayer player, float[] playerData, float partialTicks){
        if(player.getActivePotionEffect(MobEffects.INVISIBILITY) != null){
            return;
        }

        @SuppressWarnings("deprecation")
        IInventory inventory = BaublesApi.getBaubles(player);

        BaubleRenderingRegistry.INSTANCE.getRenderingHandlers().forEach(handler -> {
            IntStream.range(0, inventory.getSizeInventory()).forEach(slot -> {
                ItemStack stack = inventory.getStackInSlot(slot);

                if(stack != null){
                    if(stack.getItem() == handler.getItem()){
                        handler.renderBauble(stack, player, partialTicks, IRenderBauble.RenderType.BODY);
                        float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
                        float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
                        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
                        GlStateManager.pushMatrix();
                        GlStateManager.rotate(yawOffset, 0, -1, 0);
                        GlStateManager.rotate(yaw - 270, 0, 1, 0);
                        GlStateManager.rotate(pitch, 0, 0, 1);
                        handler.renderBauble(stack, player, partialTicks, IRenderBauble.RenderType.HEAD);
                        GlStateManager.popMatrix();
                    }
                }
            });
        });
    }

}

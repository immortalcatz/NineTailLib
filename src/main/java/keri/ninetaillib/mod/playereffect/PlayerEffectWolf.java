/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.mod.playereffect;

import codechicken.lib.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectWolf implements IPlayerEffect {

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
        float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.rotate(yawOffset, 0F, -1F, 0F);
        GlStateManager.rotate(yaw - 270F, 0F, 1F, 0F);
        GlStateManager.rotate(pitch, 0F, 0F, 1F);
        GlStateManager.rotate(180F, 0F, 0F, 1F);
        GlStateManager.translate(0D, player.isSneaking() ? 0.25D : 0.5D, 0D);

        if(!Minecraft.getMinecraft().isGamePaused()){
            GlStateManager.rotate((float) ClientUtils.getRenderTime() * 2F, 0F, 1F, 0F);
        }

        GlStateManager.scale(0.25D, 0.25D, 0.25D);
        Render<EntityWolf> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityWolf.class);
        World world = Minecraft.getMinecraft().world;
        EntityWolf entity = new EntityWolf(world);
        renderer.doRender(entity, 0D, 0D, 0D, 0F, 0F);
        GlStateManager.popMatrix();
    }

}

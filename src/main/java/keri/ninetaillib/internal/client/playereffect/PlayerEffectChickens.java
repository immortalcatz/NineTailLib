package keri.ninetaillib.internal.client.playereffect;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.util.ClientUtils;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectChickens implements IPlayerEffect {

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        double floating = MathHelper.sin(player.ticksExisted * MathHelper.torad * 6D) / 6D;
        EntityChicken dummyEntity = new EntityChicken(player.worldObj);
        Render<EntityChicken> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityChicken.class);
        Colour color = new ColourRGBA(255, 255, 255, 100);
        Vector3 translation = CommonUtils.getWorldPosition(player, partialTicks);

        for(int i = 0; i < 8; i++){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            color.glColour();
            GlStateManager.scale(0.18D, 0.18D, 0.18D);
            GlStateManager.rotate(45F * i, 0F, 1F, 0F);
            GlStateManager.translate(translation.x + 2D, translation.y + floating + 11D, translation.z);
            GlStateManager.rotate((float)ClientUtils.getRenderTime() * 6F, 0F, 1F, 0F);
            renderer.doRender(dummyEntity, 0D, 0D, 0D, 0F, 0F);
            GlStateManager.disableBlend();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
    }

}

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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectEnderpearls implements IPlayerEffect {

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        double floating = MathHelper.sin(player.ticksExisted * MathHelper.torad * 6D) / 6D;
        EntityItem dummyEntity = new EntityItem(player.worldObj);
        dummyEntity.setEntityItemStack(new ItemStack(Items.ENDER_PEARL, 1, 0));
        dummyEntity.hoverStart = 0F;
        Render<EntityItem> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityItem.class);
        Colour color = new ColourRGBA(255, 255, 255, 100);
        Vector3 translation = CommonUtils.getWorldPosition(player, partialTicks);

        for(int i = 0; i < 8; i++){
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            color.glColour();
            GlStateManager.scale(0.3D, 0.3D, 0.3D);
            GlStateManager.rotate(45F * i, 0F, 1F, 0F);
            GlStateManager.translate(translation.x + 1D, translation.y + floating + 6.4D, translation.z);
            GlStateManager.rotate((float)ClientUtils.getRenderTime() * 6F, 0F, 1F, 0F);
            renderer.doRender(dummyEntity, 0D, 0D, 0D, 0F, 0F);
            GlStateManager.disableBlend();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }

        double particleX = player.posX;
        double particleY = player.posY;
        double particleZ = player.posZ;
        player.worldObj.spawnParticle(EnumParticleTypes.DRAGON_BREATH, particleX, particleY, particleZ, 0D, 0D, 0D, new int[0]);
    }

}

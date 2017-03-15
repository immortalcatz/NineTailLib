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
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectSheep implements IPlayerEffect {

    private int ticker = 0;
    private int colorIndex = 0;

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        if(this.ticker < 24){
            this.ticker++;
        }
        else{
            if(this.colorIndex < 15){
                this.colorIndex++;
            }
            else{
                this.colorIndex = 0;
            }

            this.ticker = 0;
        }

        double floating = MathHelper.sin(player.ticksExisted * MathHelper.torad * 6D) / 6D;
        EntitySheep dummyEntity = new EntitySheep(player.worldObj);

        if(player.capabilities.isCreativeMode){
            dummyEntity.setFleeceColor(EnumDyeColor.values()[this.colorIndex]);
        }

        Render<EntitySheep> renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntitySheep.class);
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

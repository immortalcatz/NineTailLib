package keri.ninetaillib.internal.client.playereffect;

import codechicken.lib.util.ClientUtils;
import codechicken.lib.vec.Vector3;
import keri.ninetaillib.internal.util.ModPrefs;
import keri.ninetaillib.util.CommonUtils;
import keri.ninetaillib.util.ResourceAction;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerEffectHelicopter implements IPlayerEffect {

    private final ResourceAction texture = new ResourceAction(ModPrefs.MODID, "textures/model/propeller.png");
    private final ModelPropeller model = new ModelPropeller();

    @Override
    public void renderPlayerEffect(EntityPlayer player, float partialTicks) {
        if(!player.onGround){
            this.texture.bind(true);
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            Vector3 translation = CommonUtils.getWorldPosition(player, partialTicks);
            GlStateManager.translate(translation.x, translation.y + 1.15D + player.eyeHeight, translation.z);
            GlStateManager.scale(0.6D, 0.6D, 0.6D);
            GlStateManager.rotate(180F, 0F, 0F, 1F);
            GlStateManager.rotate(player.rotationYawHead, 0F, 1F, 0F);
            GlStateManager.rotate(-player.rotationPitch, 1F, 0F, 0F);
            this.model.renderStandoff();
            GlStateManager.rotate((float)ClientUtils.getRenderTime() * 28F, 0F, 1F, 0F);
            this.model.renderBlades();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
    }

    @SideOnly(Side.CLIENT)
    private class ModelPropeller extends ModelBase {

        private ModelRenderer standoff;
        private ModelRenderer connector;
        private ModelRenderer blade1;
        private ModelRenderer blade2;
        private ModelRenderer blade3;
        private ModelRenderer blade4;

        public ModelPropeller(){
            textureWidth = 64;
            textureHeight = 32;
            standoff = new ModelRenderer(this, 0, 0);
            standoff.addBox(0F, 0F, 0F, 2, 4, 2);
            standoff.setRotationPoint(-1F, 20F, -1F);
            standoff.setTextureSize(64, 32);
            standoff.mirror = true;
            setRotation(standoff, 0F, 0F, 0F);
            connector = new ModelRenderer(this, 8, 0);
            connector.addBox(0F, 0F, 0F, 3, 2, 3);
            connector.setRotationPoint(-1.5F, 19F, -1.5F);
            connector.setTextureSize(64, 32);
            connector.mirror = true;
            setRotation(connector, 0F, 0F, 0F);
            blade1 = new ModelRenderer(this, 0, 6);
            blade1.addBox(0F, 0.5F, 0F, 6, 1, 2);
            blade1.setRotationPoint(-7.5F, 19.5F, -1F);
            blade1.setTextureSize(64, 32);
            blade1.mirror = true;
            setRotation(blade1, 0.3926991F, 0F, 0F);
            blade2 = new ModelRenderer(this, 0, 6);
            blade2.addBox(0F, -0.5F, 0F, 6, 1, 2);
            blade2.setRotationPoint(1.5F, 19.5F, -1F);
            blade2.setTextureSize(64, 32);
            blade2.mirror = true;
            setRotation(blade2, -0.3926991F, 0F, 0F);
            blade3 = new ModelRenderer(this, 0, 9);
            blade3.addBox(0F, -0.5F, 0F, 2, 1, 6);
            blade3.setRotationPoint(-1F, 19.5F, -7.5F);
            blade3.setTextureSize(64, 32);
            blade3.mirror = true;
            setRotation(blade3, 0F, 0F, 0.3926991F);
            blade4 = new ModelRenderer(this, 0, 9);
            blade4.addBox(0F, 0.5F, 0F, 2, 1, 6);
            blade4.setRotationPoint(-1F, 19.5F, 1.5F);
            blade4.setTextureSize(64, 32);
            blade4.mirror = true;
            setRotation(blade4, 0F, 0F, -0.3926991F);
        }

        private void setRotation(ModelRenderer model, float x, float y, float z){
            model.rotateAngleX = x;
            model.rotateAngleY = y;
            model.rotateAngleZ = z;
        }

        public void renderStandoff(){
            final float scale = 0.0625F;
            this.standoff.render(scale);
        }

        public void renderBlades(){
            final float scale = 0.0625F;
            this.connector.render(scale);
            this.blade1.render(scale);
            this.blade2.render(scale);
            this.blade3.render(scale);
            this.blade4.render(scale);
        }

    }

}

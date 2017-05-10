package keri.ninetaillib.render.render;

import codechicken.lib.model.SimpleBakedItemModel;
import codechicken.lib.render.CCModelState;
import codechicken.lib.render.CCRenderState;
import keri.ninetaillib.render.registry.IItemRenderingHandler;
import keri.ninetaillib.render.util.VertexUtils;
import keri.ninetaillib.texture.IIconItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;

@SideOnly(Side.CLIENT)
public class DefaultItemRenderer implements IItemRenderingHandler {

    private CCModelState transforms;

    public DefaultItemRenderer(CCModelState transforms){
        this.transforms = transforms;
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack) {
        Tessellator.getInstance().draw();
        IIconItem iconProvider = (IIconItem)stack.getItem();
        TextureAtlasSprite texture = null;

        if(iconProvider.getIcon(stack) != null){
            texture = iconProvider.getIcon(stack);
        }
        else{
            texture = iconProvider.getIcon(stack.getMetadata());
        }

        SimpleBakedItemModel model = new SimpleBakedItemModel(new ResourceLocation(texture.getIconName()));
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
        GlStateManager.popMatrix();
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, VertexUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, this.transforms.getTransforms(), cameraTransformType);
    }

}

package keri.ninetaillib.render.registry;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;

public interface IItemRenderingHandler {

    void renderItem(CCRenderState renderState, ItemStack stack);

    default Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType){
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, TransformUtils.DEFAULT_ITEM.getTransforms(), cameraTransformType);
    }

}

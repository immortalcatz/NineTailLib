package keri.ninetaillib.render.registry;

import codechicken.lib.render.CCRenderState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.List;

public interface IItemRenderingHandler {

    void renderItem(CCRenderState renderState, ItemStack stack, long rand);

    Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType);

    default boolean useRenderCache(){ return true; };

    default boolean useStandardItemLighting(){ return true; };

    default List<BakedQuad> getBakedQuads(ItemStack stack, long rand){ return null; };

    default String getItemKey(ItemStack stack){ return null; }

}

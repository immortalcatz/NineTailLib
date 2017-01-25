package keri.ninetaillib.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.util.TransformUtils;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import keri.ninetaillib.texture.IIconItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.List;

@SideOnly(Side.CLIENT)
public class DefaultItemRenderer implements IItemRenderingHandler, IItemQuadProvider {

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand) {

    }

    @Override
    public List<BakedQuad> getQuads(ItemStack stack, long random){
        Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
            public TextureAtlasSprite apply(ResourceLocation input) {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(input.toString());
            }
        };

        TextureAtlasSprite texture = ((IIconItem)stack.getItem()).getIcon(stack.getMetadata());
        IBakedModel layerModel = (new ItemLayerModel(ImmutableList.of(new ResourceLocation(texture.getIconName())))).bake(TransformUtils.DEFAULT_ITEM, DefaultVertexFormats.ITEM, bakedTextureGetter);
        ImmutableList.Builder quadBuilder = ImmutableList.builder();
        quadBuilder.addAll(layerModel.getQuads((IBlockState)null, (EnumFacing)null, 0L));
        return quadBuilder.build();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, TransformUtils.DEFAULT_ITEM.getTransforms(), cameraTransformType);
    }

    @Override
    public boolean useRenderCache() {
        return true;
    }

    @Override
    public boolean useStandardItemLighting() {
        return false;
    }

}

package keri.ninetaillib.render.model;

import codechicken.lib.render.CCModelState;
import codechicken.lib.render.CCRenderState;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import keri.ninetaillib.render.registry.IItemRenderingHandler;
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
public class DefaultItemRenderer implements IItemRenderingHandler {

    private CCModelState itemTransforms;

    public DefaultItemRenderer(CCModelState transforms){
        this.itemTransforms = transforms;
    }

    @Override
    public void renderItem(CCRenderState renderState, ItemStack stack, long rand){}

    @Override
    public List<BakedQuad> getBakedQuads(ItemStack stack, long random){
        Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
            public TextureAtlasSprite apply(ResourceLocation input) {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(input.toString());
            }
        };

        TextureAtlasSprite texture = ((IIconItem)stack.getItem()).getIcon(stack.getMetadata());
        IBakedModel layerModel = (new ItemLayerModel(ImmutableList.of(new ResourceLocation(texture.getIconName())))).bake(this.itemTransforms, DefaultVertexFormats.ITEM, bakedTextureGetter);
        ImmutableList.Builder quadBuilder = ImmutableList.builder();
        quadBuilder.addAll(layerModel.getQuads((IBlockState)null, (EnumFacing)null, 0L));
        return quadBuilder.build();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(model, this.itemTransforms, cameraTransformType);
    }

    @Override
    public boolean useStandardItemLighting() {
        return false;
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.render.CCModelState;
import codechicken.lib.util.TransformUtils;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderItems {

    public static EnumItemRenderType DEFAULT_ITEM;
    public static EnumItemRenderType DEFAULT_TOOL;
    public static EnumItemRenderType DEFAULT_BOW;

    static{
        DEFAULT_ITEM = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(new RenderDefaultItem());
        DEFAULT_TOOL = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(new RenderDefaultTool());
        DEFAULT_BOW = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(new RenderDefaultBow());
    }

    @SideOnly(Side.CLIENT)
    public static class RenderDefaultItem implements IItemRenderingHandler {

        @Override
        public void renderItem(ItemStack stack, VertexBuffer buffer, ItemCameraTransforms.TransformType transformType) {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            Function<ResourceLocation, TextureAtlasSprite> textureGetter = ModelLoader.defaultTextureGetter();
            Tessellator.getInstance().draw();

            if(stack.getItem() instanceof IIconItem){
                IIconItem iconProvider = (IIconItem)stack.getItem();
                TextureAtlasSprite texture = null;

                if(iconProvider.getIcon(stack) != null){
                    texture = iconProvider.getIcon(stack);
                }
                else{
                    texture = iconProvider.getIcon(stack.getMetadata());
                }

                ItemLayerModel model = new ItemLayerModel(ImmutableList.copyOf(Lists.newArrayList(new ResourceLocation(texture.getIconName()))));
                IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, textureGetter);
                bakedModel = ForgeHooksClient.handleCameraTransforms(bakedModel, transformType, false);
                GlStateManager.translate(0.5D, 0.5D, 0.5D);
                renderItem.renderItem(stack, bakedModel);
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }

        @Override
        public EnumItemRenderType getRenderType() {
            return DEFAULT_ITEM;
        }

    }

    @SideOnly(Side.CLIENT)
    public static class RenderDefaultTool implements IItemRenderingHandler {

        @Override
        public void renderItem(ItemStack stack, VertexBuffer buffer, ItemCameraTransforms.TransformType transformType) {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            Function<ResourceLocation, TextureAtlasSprite> textureGetter = ModelLoader.defaultTextureGetter();
            Tessellator.getInstance().draw();

            if(stack.getItem() instanceof IIconItem){
                IIconItem iconProvider = (IIconItem)stack.getItem();
                TextureAtlasSprite texture = null;

                if(iconProvider.getIcon(stack) != null){
                    texture = iconProvider.getIcon(stack);
                }
                else{
                    texture = iconProvider.getIcon(stack.getMetadata());
                }

                ItemLayerModel model = new ItemLayerModel(ImmutableList.copyOf(Lists.newArrayList(new ResourceLocation(texture.getIconName()))));
                IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, textureGetter);
                bakedModel = ForgeHooksClient.handleCameraTransforms(bakedModel, transformType, false);
                GlStateManager.translate(0.5D, 0.5D, 0.5D);
                renderItem.renderItem(stack, bakedModel);
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }

        @Override
        public EnumItemRenderType getRenderType() {
            return DEFAULT_TOOL;
        }

        @Override
        public CCModelState getTransforms() {
            return TransformUtils.DEFAULT_TOOL;
        }

    }

    @SideOnly(Side.CLIENT)
    public static class RenderDefaultBow implements IItemRenderingHandler {

        @Override
        public void renderItem(ItemStack stack, VertexBuffer buffer, ItemCameraTransforms.TransformType transformType) {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
            Function<ResourceLocation, TextureAtlasSprite> textureGetter = ModelLoader.defaultTextureGetter();
            Tessellator.getInstance().draw();

            if(stack.getItem() instanceof IIconItem){
                IIconItem iconProvider = (IIconItem)stack.getItem();
                TextureAtlasSprite texture = null;

                if(iconProvider.getIcon(stack) != null){
                    texture = iconProvider.getIcon(stack);
                }
                else{
                    texture = iconProvider.getIcon(stack.getMetadata());
                }

                ItemLayerModel model = new ItemLayerModel(ImmutableList.copyOf(Lists.newArrayList(new ResourceLocation(texture.getIconName()))));
                IBakedModel bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, textureGetter);
                bakedModel = ForgeHooksClient.handleCameraTransforms(bakedModel, transformType, false);
                GlStateManager.translate(0.5D, 0.5D, 0.5D);
                renderItem.renderItem(stack, bakedModel);
            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }

        @Override
        public EnumItemRenderType getRenderType() {
            return DEFAULT_BOW;
        }

        @Override
        public CCModelState getTransforms() {
            return TransformUtils.DEFAULT_BOW;
        }

    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import codechicken.lib.render.CCModelState;
import codechicken.lib.util.TransformUtils;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IItemRenderingHandler {

    void renderItem(ItemStack stack, VertexBuffer buffer);

    default boolean useDefaultItemLighting(){ return false; };

    default CCModelState getTransforms(){ return TransformUtils.DEFAULT_ITEM; };

    EnumItemRenderType getRenderType();

}

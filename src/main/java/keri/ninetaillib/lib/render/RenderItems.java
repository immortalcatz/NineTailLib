/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderItems {

    public static class RenderDefaultItem implements IItemRenderingHandler {

        //TODO: implement actually rendering something...

        @Override
        public void renderItem(ItemStack stack, VertexBuffer buffer) {

        }

        @Override
        public EnumItemRenderType getRenderType() {
            return EnumItemRenderType.DEFAULT_ITEM;
        }

    }

}

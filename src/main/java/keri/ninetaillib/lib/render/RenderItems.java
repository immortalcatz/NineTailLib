/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render;

import keri.ninetaillib.lib.texture.IIconItem;
import keri.ninetaillib.lib.util.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderItems {

    static{
        RenderingRegistry.registerRenderingHandler(new RenderDefaultItem());
    }

    public static class RenderDefaultItem implements IItemRenderingHandler {

        @Override
        public void renderItem(ItemStack stack, VertexBuffer buffer) {
            Tessellator.getInstance().draw();

            if(stack.getItem() instanceof IIconItem){

            }

            Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, RenderUtils.getFormatWithLightMap(DefaultVertexFormats.ITEM));
        }

        @Override
        public EnumItemRenderType getRenderType() {
            return EnumItemRenderType.DEFAULT_ITEM;
        }

    }

}

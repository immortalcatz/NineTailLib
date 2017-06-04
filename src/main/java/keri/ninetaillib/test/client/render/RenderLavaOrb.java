/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test.client.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCModelLibrary;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Scale;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.lib.render.IItemRenderingHandler;
import keri.ninetaillib.lib.render.RenderingConstants;
import keri.ninetaillib.lib.render.RenderingRegistry;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLavaOrb implements IItemRenderingHandler {

    public static EnumItemRenderType RENDER_TYPE;
    private static CCModel model = CCModelLibrary.icosahedron7.copy().computeNormals();

    static{
        RENDER_TYPE = RenderingRegistry.getNextAvailableItemType();
        RenderingRegistry.registerRenderingHandler(new RenderLavaOrb());
    }

    @Override
    public void renderItem(ItemStack stack, VertexBuffer buffer) {
        CCModel model = CCModelLibrary.icosahedron7.copy().computeNormals();
        CCRenderState renderState = RenderingConstants.getRenderState();
        renderState.reset();
        renderState.bind(buffer);
        renderState.brightness = 0x00F000F0;
        model.apply(new Scale(0.25D, 0.25D, 0.25D));
        model.apply(new Translation(new Vector3(0.5D, 0.5D, 0.5D)));
        model.render(renderState, new IconTransformation(TextureUtils.getTexture("minecraft:blocks/lava_still")));
    }

    @Override
    public EnumItemRenderType getRenderType() {
        return RENDER_TYPE;
    }

}

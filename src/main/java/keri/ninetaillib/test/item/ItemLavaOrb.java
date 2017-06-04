/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test.item;

import keri.ninetaillib.lib.item.ItemBase;
import keri.ninetaillib.lib.render.EnumItemRenderType;
import keri.ninetaillib.test.client.render.RenderLavaOrb;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLavaOrb extends ItemBase {

    public ItemLavaOrb() {
        super("lava_orb");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumItemRenderType getRenderType() {
        return RenderLavaOrb.RENDER_TYPE;
    }

}

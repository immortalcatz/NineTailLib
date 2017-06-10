/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.hooks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIcon {

    @SideOnly(Side.CLIENT)
    int getIconWidth();

    @SideOnly(Side.CLIENT)
    int getIconHeight();

    @SideOnly(Side.CLIENT)
    float getMinU();

    @SideOnly(Side.CLIENT)
    float getMaxU();

    @SideOnly(Side.CLIENT)
    float getInterpolatedU(double u);

    @SideOnly(Side.CLIENT)
    float getMinV();

    @SideOnly(Side.CLIENT)
    float getMaxV();

    @SideOnly(Side.CLIENT)
    float getInterpolatedV(double v);

    @SideOnly(Side.CLIENT)
    String getIconName();

}

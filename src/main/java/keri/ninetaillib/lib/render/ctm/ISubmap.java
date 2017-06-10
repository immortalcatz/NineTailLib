/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import keri.ninetaillib.lib.texture.IIcon;

public interface ISubmap {

    int getWidth();

    int getHeight();

    IIcon getSubIcon(int x, int y);

}

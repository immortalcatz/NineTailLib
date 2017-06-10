/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import net.minecraft.world.IBlockAccess;

public interface ICTMBlock<T extends ISubmapManager> {

    T getManager(IBlockAccess world, int x, int y, int z, int meta);

    T getManager(int meta);

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.proxy;

import keri.ninetaillib.lib.render.RenderBlocks;
import keri.ninetaillib.lib.render.RenderItems;
import keri.ninetaillib.lib.util.WorldJoinMessage;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements INTLProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        new RenderBlocks();
        new RenderItems();

        if(ModPrefs.IS_ALPHA){
            new WorldJoinMessage(ModPrefs.ALPHA_MESSAGE);
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}

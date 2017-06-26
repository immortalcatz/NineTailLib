/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.proxy;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.mod.NineTailLib;
import keri.ninetaillib.mod.network.NineTailLibSPH;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements INTLProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibSPH());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}

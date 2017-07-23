/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.mod;

import keri.ninetaillib.lib.config.IModConfig;
import keri.ninetaillib.lib.config.ModConfig;
import keri.ninetaillib.lib.logger.IModLogger;
import keri.ninetaillib.lib.logger.ModLogger;
import keri.ninetaillib.lib.mod.ModHandler;
import keri.ninetaillib.mod.proxy.INTLProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static keri.ninetaillib.mod.util.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class NineTailLib {

    @Mod.Instance(value = MODID)
    public static NineTailLib INSTANCE = new NineTailLib();
    public static ModHandler MOD_HANDLER = new ModHandler(INSTANCE);
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static INTLProxy PROXY;
    @ModLogger
    public static IModLogger LOGGER;
    @ModConfig
    public static IModConfig CONFIG;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        MOD_HANDLER.handlePreInit(event);
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        MOD_HANDLER.handleInit(event);
        PROXY.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        MOD_HANDLER.handlePostInit(event);
        PROXY.postInit(event);
    }

}

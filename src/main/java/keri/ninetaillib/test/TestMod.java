/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test;

import keri.ninetaillib.lib.logger.IModLogger;
import keri.ninetaillib.lib.logger.ModLogger;
import keri.ninetaillib.lib.mod.ModHandler;
import keri.ninetaillib.test.proxy.ITestProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TestMod.MODID, name = TestMod.NAME, version = TestMod.VERSION, dependencies = TestMod.DEPS, acceptedMinecraftVersions = TestMod.ACC_MC)
public class TestMod {

    public static final String MODID = "ntl_test_mod";
    public static final String NAME = "Test Mod";
    public static final String VERSION = "0.0.1";
    public static final String DEPS = "required-after:ninetaillib";
    public static final String ACC_MC = "1.11.2";
    public static final String CSIDE = "keri.ninetaillib.test.proxy.ClientProxy";
    public static final String SSIDE = "keri.ninetaillib.test.proxy.CommonProxy";
    @Mod.Instance(value = MODID)
    public static TestMod INSTANCE = new TestMod();
    private static ModHandler MOD_HANDLER = new ModHandler(INSTANCE);
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static ITestProxy PROXY;
    @ModLogger
    public static IModLogger LOGGER;

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

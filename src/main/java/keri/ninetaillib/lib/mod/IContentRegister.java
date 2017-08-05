/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.mod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IContentRegister {

    void handlePreInit(FMLPreInitializationEvent event);

    void handleInit(FMLInitializationEvent event);

    void handlePostInit(FMLPostInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void handleClientPreInit(FMLPreInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void handleClientInit(FMLInitializationEvent event);

    @SideOnly(Side.CLIENT)
    void handleClientPostInit(FMLPostInitializationEvent event);

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.test.proxy;

import keri.ninetaillib.lib.json.model.JsonModelLoader;
import keri.ninetaillib.test.TestMod;
import keri.ninetaillib.test.client.render.RenderItemPillar;
import keri.ninetaillib.test.tile.TileEntityItemPillar;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements ITestProxy {

    public static JsonModelLoader modelLoader = new JsonModelLoader();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        modelLoader.registerModel("item_pillar", new ResourceLocation(TestMod.MODID, "models/block_item_pillar"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemPillar.class, RenderItemPillar.INSTANCE);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}

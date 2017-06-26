/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.mod.proxy;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.render.RenderBlocks;
import keri.ninetaillib.lib.render.RenderItems;
import keri.ninetaillib.lib.util.WorldJoinMessage;
import keri.ninetaillib.mod.NineTailLib;
import keri.ninetaillib.mod.network.NineTailLibCPH;
import keri.ninetaillib.mod.playereffect.PlayerEffectHandler;
import keri.ninetaillib.mod.playereffect.PlayerEffects;
import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Map;

public class ClientProxy implements INTLProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        new RenderBlocks();
        new RenderItems();

        if(ModPrefs.IS_ALPHA){
            new WorldJoinMessage(ModPrefs.ALPHA_MESSAGE);
        }

        PlayerEffects.preInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibCPH());
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        RenderPlayer renderer = null;
        renderer = skinMap.get("default");
        renderer.addLayer(PlayerEffectHandler.INSTANCE);
        renderer = skinMap.get("slim");
        renderer.addLayer(PlayerEffectHandler.INSTANCE);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}

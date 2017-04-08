package keri.ninetaillib.internal.proxy;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.internal.client.gui.InventoryButtonHandler;
import keri.ninetaillib.internal.client.handler.ClientEventHandler;
import keri.ninetaillib.internal.network.NineTailLibCPH;
import keri.ninetaillib.texture.IconRegistrar;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements INineTailProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(IconRegistrar.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new InventoryButtonHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibCPH());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

}

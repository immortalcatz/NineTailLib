package keri.ninetaillib.internal.proxy;

import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements INineTailProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public IIconRegistrar getIconRegistrar() {
        return null;
    }

    @Override
    public IRenderingRegistry getRenderingRegistry() {
        return null;
    }

}

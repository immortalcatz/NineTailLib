package keri.ninetaillib.internal.proxy;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.render.block.BlockRenderingRegistry;
import keri.ninetaillib.internal.NineTailLib;
import keri.ninetaillib.internal.client.gui.InventoryButtonHandler;
import keri.ninetaillib.internal.network.NineTailLibCPH;
import keri.ninetaillib.internal.util.ModPrefs;
import keri.ninetaillib.render.registry.IRenderingRegistry;
import keri.ninetaillib.render.registry.SimpleRenderingRegistry;
import keri.ninetaillib.texture.DefaultIconRegistrar;
import keri.ninetaillib.texture.IIconRegistrar;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements INineTailProxy {

    public static EnumBlockRenderType renderType;
    private static final DefaultIconRegistrar iconRegistrar = new DefaultIconRegistrar();
    private static final SimpleRenderingRegistry renderingRegistry = new SimpleRenderingRegistry() {
        @Override
        public String getModid() {
            return ModPrefs.MODID;
        }

        @Override
        public IIconRegistrar getIconRegistrar() {
            return iconRegistrar;
        }
    };

    @Override
    public void preInit(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new InventoryButtonHandler());
        iconRegistrar.preInit();
        renderingRegistry.preInit();
    }

    @Override
    public void init(FMLInitializationEvent event){
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibCPH());
        renderingRegistry.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        renderType = BlockRenderingRegistry.createRenderType("ntlbr");
        renderingRegistry.postInit();
    }

    @Override
    public IIconRegistrar getIconRegistrar() {
        return iconRegistrar;
    }

    @Override
    public IRenderingRegistry getRenderingRegistry() {
        return renderingRegistry;
    }

}

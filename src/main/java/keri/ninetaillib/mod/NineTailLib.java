package keri.ninetaillib.mod;

import keri.ninetaillib.mod.handler.CommonEventHandler;
import keri.ninetaillib.mod.init.NineTailLibConfig;
import keri.ninetaillib.mod.proxy.INineTailProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static keri.ninetaillib.mod.util.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class NineTailLib {

    @Mod.Instance(value = MODID)
    public static NineTailLib INSTANCE = new NineTailLib();
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static INineTailProxy PROXY;
    public static Logger LOGGER = LogManager.getLogger(NAME);
    public static NineTailLibConfig CONFIG;

    static{
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        CONFIG = new NineTailLibConfig(event);
        PROXY.preInit(event);
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
        LOGGER.info("PreInitilization phase done !");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        PROXY.init(event);
        LOGGER.info("Initilization phase done !");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        PROXY.postInit(event);
        LOGGER.info("PostInitilization phase done !");
    }

}

package keri.ninetaillib.internal;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.internal.network.NineTailLibSPH;
import keri.ninetaillib.internal.proxy.INineTailProxy;
import keri.ninetaillib.multiblock.MultiblockLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static keri.ninetaillib.internal.util.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class NineTailLib {

    @Mod.Instance(value = MODID)
    public static NineTailLib INSTANCE = new NineTailLib();
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static INineTailProxy PROXY;
    public static Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        PROXY.preInit(event);
        MultiblockLoader.INSTANCE.loadMultiblocks();
        LOGGER.info("PreInitilization phase done !");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        PROXY.init(event);
        PacketCustom.assignHandler(NineTailLib.INSTANCE, new NineTailLibSPH());
        LOGGER.info("Initilization phase done !");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        PROXY.postInit(event);
        LOGGER.info("PostInitilization phase done !");
    }

}

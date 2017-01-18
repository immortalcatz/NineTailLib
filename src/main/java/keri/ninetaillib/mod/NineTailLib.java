package keri.ninetaillib.mod;

import keri.ninetaillib.config.ConfigManagerBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static keri.ninetaillib.mod.ModPrefs.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPS, acceptedMinecraftVersions = ACC_MC)
public class NineTailLib {

    @Mod.Instance(value = MODID)
    public static NineTailLib INSTANCE = new NineTailLib();
    @SidedProxy(clientSide = CSIDE, serverSide = SSIDE)
    public static INineTailProxy PROXY;
    public static Logger LOGGER = LogManager.getLogger(NAME);
    public static ConfigManagerLib CONFIG;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        CONFIG = new ConfigManagerLib(event);
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        PROXY.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        PROXY.postInit(event);
    }

    private static class ConfigManagerLib extends ConfigManagerBase {

        public ConfigManagerLib(FMLPreInitializationEvent event) {
            super(event);
        }

        @Override
        public ArrayList<Pair<String, String>> getCategories() {
            return null;
        }

        @Override
        public void getConfigFlags(Configuration config) {

        }

    }

}

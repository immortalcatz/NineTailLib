package keri.ninetaillib.mod.init;

import com.google.common.collect.Lists;
import keri.ninetaillib.config.ConfigManagerBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class NTLConfig extends ConfigManagerBase {

    private final String CAT_GENERAL = "general";
    public boolean debugMode;

    public NTLConfig(FMLPreInitializationEvent event) {
        super(event);
    }

    @Override
    public ArrayList<Pair<String, String>> getCategories() {
        return Lists.newArrayList(
                Pair.of(this.CAT_GENERAL, "General options related to NineTailLib")
        );
    }

    @Override
    public void getConfigFlags(Configuration config) {
        this.debugMode = config.get(this.CAT_GENERAL, "enableDebugMode", false).getBoolean();
    }

}

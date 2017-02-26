package keri.ninetaillib.mod.init;

import com.google.common.collect.Lists;
import keri.ninetaillib.config.ConfigManagerBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class NineTailLibConfig extends ConfigManagerBase {

    private final String CAT_GENERAL = "general";
    public boolean enableBlockModelDebug;
    public boolean enableItemModelDebug;
    public boolean enableDarkoEasteregg;

    public NineTailLibConfig(FMLPreInitializationEvent event) {
        super(event);
    }

    @Override
    public ArrayList<Pair<String, String>> getCategories() {
        return Lists.newArrayList(
                Pair.of(this.CAT_GENERAL, "General settings related to NineTailLib")
        );
    }

    @Override
    public void getConfigFlags(Configuration config) {
        this.enableBlockModelDebug = config.get(this.CAT_GENERAL, "enableBlockModelDebug", false).getBoolean();
        this.enableItemModelDebug = config.get(this.CAT_GENERAL, "enableItemModelDebug", false).getBoolean();
        this.enableDarkoEasteregg = config.get(this.CAT_GENERAL, "enableDarkoEasteregg", true).getBoolean();
    }

}

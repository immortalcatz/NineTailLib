package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public abstract class ConfigManagerBase {

    public ConfigManagerBase(FMLPreInitializationEvent event){
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        StopWatch timer = new StopWatch();
        timer.start();
        config.load();

        if(this.getCategories() != null && !(this.getCategories().isEmpty())) {
            for (Pair<String, String> category : this.getCategories()) {
                config.addCustomCategoryComment(category.getLeft(), category.getRight());
            }
        }

        this.getConfigFlags(config);
        config.save();
        timer.stop();
        LogManager.getLogger(event.getModMetadata().name).info("Done loading config in " + timer.getTime() + "ms !");
    }

    public abstract ArrayList<Pair<String, String>> getCategories();

    public abstract void getConfigFlags(Configuration config);

}

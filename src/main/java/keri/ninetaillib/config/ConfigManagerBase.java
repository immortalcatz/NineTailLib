package keri.ninetaillib.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;

import java.util.List;

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

        List<IConfigComponent<?>> components = Lists.newArrayList();
        this.addConfigComponents(components);
        components.forEach(component -> component.addComponent(config));
        config.save();
        timer.stop();
        LogManager.getLogger(event.getModMetadata().name).info("Done loading config in " + timer.getTime() + "ms !");
    }

    public abstract List<Pair<String, String>> getCategories();

    public abstract void addConfigComponents(List<IConfigComponent<?>> components);

}

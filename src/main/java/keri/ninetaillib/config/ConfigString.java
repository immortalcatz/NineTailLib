package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

import java.util.List;
import java.util.stream.IntStream;

public class ConfigString implements IConfigComponent<String> {

    private String category;
    private String key;
    private String comment;
    private String defaultValue;
    private List<String> allowedValues;
    private String value;

    public ConfigString(String category, String key){
        this(category, key, null, "default", null);
    }

    public ConfigString(String category, String key, String defaultValue){
        this(category, key, null, defaultValue, null);
    }

    public ConfigString(String category, String key, String commment, String defaultValue, List<String> allowedValues){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
        this.allowedValues = allowedValues;
    }

    @Override
    public void addComponent(Configuration config) {
        if(this.allowedValues != null){
            String[] allowedValues = new String[this.allowedValues.size()];
            IntStream.range(0, allowedValues.length).forEach(index -> allowedValues[index] = this.allowedValues.get(index));
            this.value = config.get(this.category, this.key, this.defaultValue, this.comment, allowedValues).getString();
        }
        else{
            this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getString();
        }
    }

    @Override
    public String getValue(){
        return this.value;
    }

}

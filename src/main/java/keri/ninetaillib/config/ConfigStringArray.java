package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigStringArray implements IConfigComponent<String[]> {

    private String category;
    private String key;
    private String comment;
    private String[] defaultValue;
    private String[] value;

    public ConfigStringArray(String category, String key){
        this(category, key, new String[0]);
    }

    public ConfigStringArray(String category, String key, String[] defaultValue){
        this(category, key, null, defaultValue);
    }

    public ConfigStringArray(String category, String key, String comment){
        this(category, key, comment, new String[0]);
    }

    public ConfigStringArray(String category, String key, String commment, String[] defaultValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addComponent(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getStringList();
    }

    @Override
    public String[] getValue(){
        return this.value;
    }

}

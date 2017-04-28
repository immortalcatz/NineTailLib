package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigBoolean implements IConfigComponent<Boolean> {

    private String category;
    private String key;
    private String comment;
    private boolean defaultValue;
    private boolean value;

    public ConfigBoolean(String category, String key, boolean defaultValue){
        this(category, key, null, defaultValue);
    }

    public ConfigBoolean(String category, String key, String comment){
        this(category, key, comment, false);
    }

    public ConfigBoolean(String category, String key, String comment, boolean defaultValue){
        this.category = category;
        this.key = key;
        this.comment = comment;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addComponent(Configuration config) {
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getBoolean();
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

}

package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigIntArray implements IConfigComponent<int[]> {

    private String category;
    private String key;
    private String comment;
    private int[] defaultValue;
    private int[] value;

    public ConfigIntArray(String category, String key){
        this(category, key, new int[0]);
    }

    public ConfigIntArray(String category, String key, int[] defaultValue){
        this(category, key, null, defaultValue);
    }

    public ConfigIntArray(String category, String key, String comment){
        this(category, key, comment, new int[0]);
    }

    public ConfigIntArray(String category, String key, String commment, int[] defaultValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addComponent(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getIntList();
    }

    @Override
    public int[] getValue(){
        return this.value;
    }

}

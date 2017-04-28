package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigInteger implements IConfigComponent<Integer> {

    private String category;
    private String key;
    private String comment;
    private int defaultValue;
    private int minValue;
    private int maxValue;
    private int value;

    public ConfigInteger(String category, String key){
        this(category, key, 0);
    }

    public ConfigInteger(String category, String key, int defaultValue){
        this(category, key, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigInteger(String category, String key, int defaultValue, int minValue, int maxValue){
        this(category, key, null, defaultValue, minValue, maxValue);
    }

    public ConfigInteger(String category, String key, String comment){
        this(category, key, comment, 0);
    }

    public ConfigInteger(String category, String key, String comment, int defaultValue){
        this(category, key, comment, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigInteger(String category, String key, String commment, int defaultValue, int minValue, int maxValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void addComponent(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment, this.minValue, this.maxValue).getInt();
    }

    @Override
    public Integer getValue(){
        return this.value;
    }

}

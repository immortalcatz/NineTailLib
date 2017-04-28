package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigDouble implements IConfigComponent<Double> {

    private String category;
    private String key;
    private String comment;
    private double defaultValue;
    private double minValue;
    private double maxValue;
    private double value;

    public ConfigDouble(String category, String key){
        this(category, key, 0);
    }

    public ConfigDouble(String category, String key, double defaultValue){
        this(category, key, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigDouble(String category, String key, double defaultValue, double minValue, double maxValue){
        this(category, key, null, defaultValue, minValue, maxValue);
    }

    public ConfigDouble(String category, String key, String comment){
        this(category, key, comment, 0);
    }

    public ConfigDouble(String category, String key, String comment, double defaultValue){
        this(category, key, comment, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public ConfigDouble(String category, String key, String commment, double defaultValue, double minValue, double maxValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void addComponent(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment, this.minValue, this.maxValue).getDouble();
    }

    @Override
    public Double getValue(){
        return this.value;
    }

}

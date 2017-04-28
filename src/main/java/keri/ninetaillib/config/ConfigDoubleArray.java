package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigDoubleArray implements IConfigComponent<double[]> {

    private String category;
    private String key;
    private String comment;
    private double[] defaultValue;
    private double[] value;

    public ConfigDoubleArray(String category, String key){
        this(category, key, new double[0]);
    }

    public ConfigDoubleArray(String category, String key, double[] defaultValue){
        this(category, key, null, defaultValue);
    }

    public ConfigDoubleArray(String category, String key, String comment){
        this(category, key, comment, new double[0]);
    }

    public ConfigDoubleArray(String category, String key, String commment, double[] defaultValue){
        this.category = category;
        this.key = key;
        this.comment = commment;
        this.defaultValue = defaultValue;
    }

    @Override
    public void addComponent(Configuration config){
        this.value = config.get(this.category, this.key, this.defaultValue, this.comment).getDoubleList();
    }

    @Override
    public double[] getValue(){
        return this.value;
    }

}

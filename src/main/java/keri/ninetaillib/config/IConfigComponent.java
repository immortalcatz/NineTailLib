package keri.ninetaillib.config;

import net.minecraftforge.common.config.Configuration;

public interface IConfigComponent<T extends Object> {

    void addComponent(Configuration config);

    T getValue();

}

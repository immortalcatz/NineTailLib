/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.logger;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

public class SimpleModLogger implements IModLogger {

    private String modName;

    public SimpleModLogger(FMLPreInitializationEvent event){
        this.modName = event.getModMetadata().name;
    }

    @Override
    public void logDebug(Object message) {
        this.log(message, Level.DEBUG);
    }

    @Override
    public void logInfo(Object message) {
        this.log(message, Level.INFO);
    }

    @Override
    public void logWarn(Object message) {
        this.log(message, Level.WARN);
    }

    @Override
    public void logError(Object message) {
        this.log(message, Level.ERROR);
    }

    private void log(Object message, Level level){
        FMLLog.log(this.modName, level, String.valueOf(message));
    }

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

@Deprecated
public class FileLocation {

    private String modid;
    private String path;

    public FileLocation(String modid, String path){
        this.modid = modid;
        this.path = path;
    }

    public String getResourceDomain(){
        return this.modid;
    }

    public String getResourcePath(){
        return this.path;
    }

    @Override
    public String toString() {
        return this.modid + ":" + this.path;
    }

}

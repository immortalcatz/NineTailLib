/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

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

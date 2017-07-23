/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.asm.ASMBlock;
import codechicken.lib.asm.ASMReader;

import java.util.Map;

public class ASMUtils {

    public static Map<String, ASMBlock> readBlocks(FileLocation location){
        String path = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath() + ".asm";
        return ASMReader.loadResource(path);
    }

}

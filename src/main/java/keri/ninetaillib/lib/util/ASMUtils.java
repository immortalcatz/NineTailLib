/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import codechicken.lib.asm.ASMBlock;
import codechicken.lib.asm.ASMReader;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class ASMUtils {

    public static Map<String, ASMBlock> readBlocks(ResourceLocation location){
        String path = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath() + ".asm";
        return ASMReader.loadResource(path);
    }

}

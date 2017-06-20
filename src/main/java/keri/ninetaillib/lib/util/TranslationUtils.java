/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.client.resources.I18n;

public class TranslationUtils {

    public static String translate(String modid, String prefix, String key){
        return I18n.format(prefix + "." + modid + "." + key + ".name");
    }

}

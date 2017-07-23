/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.client.resources.I18n;

@Deprecated
public class TranslationUtils {

    public static String translate(String modid, String prefix, String key){
        return I18n.format(prefix + "." + modid + "." + key + ".name");
    }

}

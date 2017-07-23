/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class Translations {

    public static final String TOOLTIP_PRESS = TextFormatting.GRAY + translate("tooltip", "press");
    public static final String TOOLTIP_SHIFT = TextFormatting.YELLOW + translate("tooltip", "shift");
    public static final String TOOLTIP_INFO = TextFormatting.GRAY + translate("tooltip", "info");
    public static final String TOOLTIP_EMPTY = translate("tooltip", "empty");

    private static String translate(String prefix, String key){
        return I18n.format(prefix + "." + ModPrefs.MODID + "." + key + ".name");
    }

}

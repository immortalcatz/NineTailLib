package keri.ninetaillib.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class LanguageUtils {

    public static final String PRESS_KEY = format("misc", "press_key");
    public static final String SHOW_INFO = format("misc", "show_info");
    public static final String KEY_SHIFT = TextFormatting.YELLOW + format("misc", "shift") + TextFormatting.GRAY;
    public static final String KEY_CTRL = TextFormatting.GREEN + format("misc", "ctrl") + TextFormatting.GRAY;
    public static final String KEY_ALT = TextFormatting.RED + format("misc", "alt") + TextFormatting.GRAY;

    private static String format(String prefix, String toFormat){
        return I18n.format(prefix + ".commons." + toFormat + ".name");
    }

}

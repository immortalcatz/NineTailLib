package keri.ninetaillib.util;

import keri.ninetaillib.mod.util.ModPrefs;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class LanguageUtils {

    public static final String PRESS_KEY = format(ModPrefs.MODID, "tooltip", "press_key");
    public static final String SHOW_INFO = format(ModPrefs.MODID, "tooltip", "show_info");
    public static final String KEY_SHIFT = TextFormatting.YELLOW + format(ModPrefs.MODID, "tooltip", "key_shift") + TextFormatting.GRAY;
    public static final String KEY_CTRL = TextFormatting.GREEN + format(ModPrefs.MODID, "tooltip", "key_ctrl") + TextFormatting.GRAY;
    public static final String KEY_ALT = TextFormatting.RED + format(ModPrefs.MODID, "tooltip", "key_alt") + TextFormatting.GRAY;

    public static String format(String modid, String identifier, String key){
        return I18n.format(identifier + "." + modid + "." + key + ".name");
    }

}

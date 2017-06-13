/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.gui.element;

public class TabTracker {

    private static Class<? extends TabBase> openedLeftTab;
    private static Class<? extends TabBase> openedRightTab;

    private TabTracker() {

    }

    public static Class<? extends TabBase> getOpenedLeftTab() {
        return openedLeftTab;
    }

    public static Class<? extends TabBase> getOpenedRightTab() {
        return openedRightTab;
    }

    public static void setOpenedLeftTab(Class<? extends TabBase> tabClass) {
        openedLeftTab = tabClass;
    }

    public static void setOpenedRightTab(Class<? extends TabBase> tabClass) {
        openedRightTab = tabClass;
    }

}

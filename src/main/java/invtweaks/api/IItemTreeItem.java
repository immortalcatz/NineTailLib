/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package invtweaks.api;

import net.minecraft.nbt.NBTTagCompound;

@SuppressWarnings("unused")
public interface IItemTreeItem extends Comparable<IItemTreeItem> {
    String getName();

    String getId();

    int getDamage();

    NBTTagCompound getExtraData();

    int getOrder();
}

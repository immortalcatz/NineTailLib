/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IShiftDescription {

    boolean shouldAddDescription(ItemStack stack, EntityPlayer player);

    void addDescription(ItemStack stack, EntityPlayer player, List<String> tooltip);

}

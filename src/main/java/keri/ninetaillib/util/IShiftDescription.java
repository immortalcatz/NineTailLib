package keri.ninetaillib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IShiftDescription {

    void addDescription(ItemStack stack, EntityPlayer player, List<String> tooltip);

}

package keri.ninetaillib.inventory;

import net.minecraft.entity.player.EntityPlayer;

public interface IInventoryAction {

    void actionPerformed(EntityPlayer player, EnumInventoryAction action);

}

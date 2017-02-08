package keri.ninetaillib.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public interface IInventoryAction {

    void actionPerformed(TileEntity tile, EntityPlayer player, EnumInventoryAction action);

}

/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.lib.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFalseCopy extends Slot {

    public int slotIndex = 0;

    public SlotFalseCopy(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.slotIndex = index;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return true;
    }

    @Override
    public void putStack(ItemStack stack) {
        if (!stack.isEmpty()) {
            stack.setCount(1);
        }

        this.inventory.setInventorySlotContents(this.slotIndex, stack);
        this.onSlotChanged();
    }

}

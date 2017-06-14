/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.container;

import keri.ninetaillib.lib.container.slot.SlotFalseCopy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container {

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }
        for (int slot = 0; slot < 9; slot++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, slot, x + slot * 18, y + 58));
        }
    }

    @Override
    public ItemStack slotClick(int index, int mouseButton, ClickType modifier, EntityPlayer player) {
        Slot slot = index < 0 ? null : this.inventorySlots.get(index);

        if (slot instanceof SlotFalseCopy) {
            if (mouseButton == 2) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.putStack(player.inventory.getItemStack().isEmpty() ? ItemStack.EMPTY : player.inventory.getItemStack().copy());
            }

            return player.inventory.getItemStack();
        }

        return super.slotClick(index, mouseButton, modifier, player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        if (!this.supportsShiftClick(player, slotIndex)) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            if (!performMerge(player, slotIndex, stackInSlot)) {
                return ItemStack.EMPTY;
            }

            slot.onSlotChange(stackInSlot, stack);

            if (stackInSlot.getCount() <= 0) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.putStack(stackInSlot);
            }

            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return stack;
    }

    protected boolean performMerge(EntityPlayer player, int slotIndex, ItemStack stack) {
        return this.performMerge(slotIndex, stack);
    }

    protected boolean performMerge(int slotIndex, ItemStack stack) {
        int invBase = getSizeInventory();
        int invFull = inventorySlots.size();

        if (slotIndex < invBase) {
            return this.mergeItemStack(stack, invBase, invFull, true);
        }

        return this.mergeItemStack(stack, 0, invBase, false);
    }

    public boolean supportsShiftClick(EntityPlayer player, int slot){
        return true;
    }

    public abstract int getSizeInventory();

}

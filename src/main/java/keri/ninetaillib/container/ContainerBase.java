package keri.ninetaillib.container;

import keri.ninetaillib.slot.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class ContainerBase extends Container {

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStack = null;
        SlotBase clickSlot = (SlotBase)this.inventorySlots.get(index);

        if (clickSlot instanceof SlotDisabled) {
            return null;
        }

        if ((clickSlot != null) && (clickSlot.getHasStack())) {
            itemStack = clickSlot.getStack();
            if (itemStack == null) {
                return null;
            }

            List<Slot> selectedSlots = new ArrayList<Slot>();
            if (clickSlot.isPlayerSide()) {
                for (int x = 0; x < this.inventorySlots.size(); x++) {
                    SlotBase advSlot = (SlotBase) this.inventorySlots.get(x);
                    if ((!advSlot.isPlayerSide()) && (!(advSlot instanceof SlotFake))) {
                        if (advSlot.isItemValid(itemStack)) {
                            selectedSlots.add(advSlot);
                        }
                    }
                }
            } else {
                for (int x = 0; x < this.inventorySlots.size(); x++) {
                    SlotBase advSlot = (SlotBase) this.inventorySlots.get(x);
                    if ((advSlot.isPlayerSide()) && (!(advSlot instanceof SlotFake))) {
                        if (advSlot.isItemValid(itemStack)) {
                            selectedSlots.add(advSlot);
                        }
                    }
                }
            }

            if ((selectedSlots.isEmpty()) && (clickSlot.isPlayerSide())) {
                if (itemStack != null) {
                    for (int x = 0; x < this.inventorySlots.size(); x++) {
                        SlotBase advSlot = (SlotBase) this.inventorySlots.get(x);
                        ItemStack dest = advSlot.getStack();
                        if ((!advSlot.isPlayerSide()) && (advSlot instanceof SlotFake)) {
                            if (dest == null) {
                                advSlot.putStack(itemStack != null ? itemStack.copy() : null);
                                advSlot.onSlotChanged();
                                updateSlot(advSlot);
                                return null;
                            }
                        }
                    }
                }
            }

            if (itemStack != null) {
                for (Slot d : selectedSlots) {
                    if ((!(d instanceof SlotDisabled))) {
                        if ((d.isItemValid(itemStack)) && (itemStack != null)) {
                            if (d.getHasStack()) {
                                ItemStack t = d.getStack();
                                if ((itemStack != null) && (itemStack.isItemEqual(t))) {
                                    int maxSize = t.getMaxStackSize();
                                    if (maxSize > d.getSlotStackLimit()) {
                                        maxSize = d.getSlotStackLimit();
                                    }
                                    int placeAble = maxSize - t.stackSize;
                                    if (itemStack.stackSize < placeAble) {
                                        placeAble = itemStack.stackSize;
                                    }
                                    t.stackSize += placeAble;
                                    itemStack.stackSize -= placeAble;
                                    if (itemStack.stackSize <= 0) {
                                        clickSlot.putStack(null);
                                        d.onSlotChanged();

                                        updateSlot(clickSlot);
                                        updateSlot(d);
                                        return null;
                                    }
                                    updateSlot(d);
                                }
                            }
                        }
                    }
                }

                for (Slot d : selectedSlots) {
                    if ((!(d instanceof SlotDisabled))) {
                        if ((d.isItemValid(itemStack)) && (itemStack != null)) {
                            if (d.getHasStack()) {
                                ItemStack t = d.getStack();
                                if ((itemStack != null) && (itemStack.isItemEqual(t))) {
                                    int maxSize = t.getMaxStackSize();
                                    if (maxSize > d.getSlotStackLimit()) {
                                        maxSize = d.getSlotStackLimit();
                                    }
                                    int placeAble = maxSize - t.stackSize;
                                    if (itemStack.stackSize < placeAble) {
                                        placeAble = itemStack.stackSize;
                                    }
                                    t.stackSize += placeAble;
                                    itemStack.stackSize -= placeAble;
                                    if (itemStack.stackSize <= 0) {
                                        clickSlot.putStack(null);
                                        d.onSlotChanged();

                                        updateSlot(clickSlot);
                                        updateSlot(d);
                                        return null;
                                    }
                                    updateSlot(d);
                                }
                            } else {
                                int maxSize = itemStack.getMaxStackSize();
                                if (maxSize > d.getSlotStackLimit()) {
                                    maxSize = d.getSlotStackLimit();
                                }
                                ItemStack tmp = itemStack.copy();
                                if (tmp.stackSize > maxSize) {
                                    tmp.stackSize = maxSize;
                                }
                                itemStack.stackSize -= tmp.stackSize;
                                d.putStack(tmp);
                                if (itemStack.stackSize <= 0) {
                                    clickSlot.putStack(null);
                                    d.onSlotChanged();

                                    updateSlot(clickSlot);
                                    updateSlot(d);
                                    return null;
                                }
                                updateSlot(d);
                            }
                        }
                    }
                }
            }

            clickSlot.putStack(itemStack != null ? itemStack.copy() : null);
        }

        updateSlot(clickSlot);
        return null;
    }

    private void updateSlot(final Slot slot) {
        this.detectAndSendChanges();
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int xOff, int yOff){
        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                int slot = j + i * 9 + 9;
                int x = xOff + j * 18;
                int y = yOff + i * 18;
                this.addSlotToContainer(new SlotPlayerInventory(inventoryPlayer, slot, x, y));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new SlotPlayerHotbar(inventoryPlayer, i, xOff + i * 18, yOff + 58));
        }
    }

}

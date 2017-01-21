package keri.ninetaillib.slot;

import net.minecraft.inventory.IInventory;

public class SlotDisabledIInventory extends SlotBaseIInventory {

    public SlotDisabledIInventory(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
        this.isEnabled = false;
    }

}

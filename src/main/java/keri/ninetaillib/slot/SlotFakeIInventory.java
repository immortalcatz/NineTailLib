package keri.ninetaillib.slot;

import net.minecraft.inventory.IInventory;

public class SlotFakeIInventory extends SlotBaseIInventory {

    public SlotFakeIInventory(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
        this.isEnabled = false;
    }

}

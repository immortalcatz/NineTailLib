package keri.ninetaillib.slot;

import net.minecraft.inventory.IInventory;

public class SlotFake extends SlotBase {

    public SlotFake(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
        this.isEnabled = false;
    }

}

package keri.ninetaillib.slot;

import net.minecraft.inventory.IInventory;

public class SlotPlayerHotbar extends SlotBaseIInventory {

    public SlotPlayerHotbar(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
        this.isPlayerSide = true;
    }

}

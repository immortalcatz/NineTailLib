package keri.ninetaillib.slot;

import net.minecraftforge.items.IItemHandler;

public class SlotDisabled extends SlotBase {

    public SlotDisabled(IItemHandler handler, int idx, int x, int y) {
        super(handler, idx, x, y);
        this.isEnabled = false;
    }

}

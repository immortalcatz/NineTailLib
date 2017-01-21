package keri.ninetaillib.slot;

import net.minecraftforge.items.IItemHandler;

public class SlotFake extends SlotBase {

    public SlotFake(IItemHandler handler, int idx, int x, int y) {
        super(handler, idx, x, y);
        this.isEnabled = false;
    }

}

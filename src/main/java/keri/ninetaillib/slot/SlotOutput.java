package keri.ninetaillib.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class SlotOutput extends SlotBase {

    public SlotOutput(IItemHandler handler, int idx, int x, int y) {
        super(handler, idx, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}

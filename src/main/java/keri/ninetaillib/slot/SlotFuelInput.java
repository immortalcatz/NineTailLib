package keri.ninetaillib.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;

public class SlotFuelInput extends SlotBase {

    public SlotFuelInput(IItemHandler handler, int idx, int x, int y) {
        super(handler, idx, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GameRegistry.getFuelValue(stack) > 0;
    }

}

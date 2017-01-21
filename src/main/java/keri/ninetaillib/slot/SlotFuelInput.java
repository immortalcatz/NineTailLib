package keri.ninetaillib.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SlotFuelInput extends SlotBase {

    public SlotFuelInput(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GameRegistry.getFuelValue(stack) > 0;
    }

}

package keri.ninetaillib.slot;

import codechicken.lib.util.ItemUtils;
import com.google.common.collect.Lists;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SlotFiltered extends SlotBase {

    private List<ItemStack> allowed;

    public SlotFiltered(IInventory inventory, int idx, int x, int y, List<ItemStack> allowed){
        super(inventory, idx, x, y);
        this.allowed = allowed;
    }

    public SlotFiltered(IInventory inventory, int idx, int x, int y, ItemStack... allowed) {
        super(inventory, idx, x, y);
        this.allowed = Lists.newArrayList(allowed);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(!this.allowed.isEmpty()){
            for(ItemStack allowedStack : this.allowed){
                return ItemUtils.areStacksSameType(stack, allowedStack);
            }
        }

        return false;
    }

}

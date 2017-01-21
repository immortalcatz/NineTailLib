package keri.ninetaillib.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBaseIInventory extends Slot {

    protected ItemStack overlayIcon = null;
    protected boolean isPlayerSide = false;
    protected boolean isDisplay = false;
    protected boolean isEnabled = true;
    private int defX;
    private int defY;

    public SlotBaseIInventory(IInventory inventory, int idx, int x, int y) {
        super(inventory, idx, x, y);
        this.defX = x;
        this.defY = y;
    }

    public boolean isPlayerSide() {
        return isPlayerSide;
    }

    public int getDefX() {
        return defX;
    }

    public int getDefY() {
        return defY;
    }

    public ItemStack getOverlayIcon() {
        return overlayIcon;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }

    @Override
    public boolean canBeHovered() {
        return isEnabled;
    }

    @Override
    public ItemStack getStack() {
        if (this.inventory.getSizeInventory() <= getSlotIndex()){
            return null;
        }

        if (this.isDisplay) {
            this.isDisplay = false;
            return getDisplayStack();
        }

        return super.getStack();
    }

    @Override
    public void putStack(ItemStack stack) {
        if (!isEnabled)
            return;

        super.putStack(stack);
    }

    public void clearStack(){
        super.putStack(null);
    }

    @Override
    public boolean canTakeStack(EntityPlayer player){
        if (!isEnabled) {
            return false;
        }

        return super.canTakeStack(player);
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        if (!isEnabled) {
            return false;
        }

        return super.isItemValid(stack);
    }

    public ItemStack getDisplayStack(){
        return super.getStack();
    }

    public boolean renderIconWithItem(){
        return overlayIcon != null;
    }

}

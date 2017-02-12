package keri.ninetaillib.tile;

import keri.ninetaillib.inventory.InternalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public abstract class TileEntityInventory extends TileEntityBase implements IInventory {

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.getInternalInventory().readInventoryFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.getInternalInventory().writeInventoryToNBT(tag);
        return tag;
    }

    @Override
    public int getSizeInventory() {
        return this.getInternalInventory().getSizeInventory();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.getInternalInventory().getStackInSlot(index);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return this.getInternalInventory().decrStackSize(index, count);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return this.getInternalInventory().removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.getInternalInventory().setInventorySlotContents(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return this.getInternalInventory().getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.getInternalInventory().isUseableByPlayer(player);
    }

    @Override
    public void openInventory(EntityPlayer player) {
        this.getInternalInventory().openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.getInternalInventory().closeInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.getInternalInventory().isItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id) {
        return this.getInternalInventory().getField(id);
    }

    @Override
    public void setField(int id, int value) {
        this.getInternalInventory().setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return this.getInternalInventory().getFieldCount();
    }

    @Override
    public void clear() {
        this.getInternalInventory().clear();
    }

    @Override
    public String getName() {
        return this.getInternalInventory().getName();
    }

    @Override
    public boolean hasCustomName() {
        return this.getInternalInventory().hasCustomName();
    }

    public abstract InternalInventory getInternalInventory();

}

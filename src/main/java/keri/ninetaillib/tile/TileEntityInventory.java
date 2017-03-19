package keri.ninetaillib.tile;

import keri.ninetaillib.inventory.InternalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

public abstract class TileEntityInventory extends TileEntityBase implements ISidedInventory {

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.getInternalInventory().readInventoryFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        this.getInternalInventory().writeInventoryToNBT(tag);
        return tag;
    }

    @Override
    public int getSizeInventory(){
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

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new SidedInvWrapper(this, facing));
        }

        return super.getCapability(capability, facing);
    }

    public abstract InternalInventory getInternalInventory();

}

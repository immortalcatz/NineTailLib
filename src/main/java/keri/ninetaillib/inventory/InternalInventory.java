package keri.ninetaillib.inventory;

import codechicken.lib.inventory.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class InternalInventory implements IInventory {

    private TileEntity tile;
    private ItemStack[] inventory;

    public InternalInventory(TileEntity tile, int size){
        this.tile = tile;
        this.inventory = new ItemStack[size];
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return InventoryUtils.decrStackSize(this, index, count);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return InventoryUtils.removeStackFromSlot(this, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.inventory[index] = stack;

        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(this.tile, player, EnumInventoryAction.SET_SLOT_CONTENTS);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(this.tile, player, EnumInventoryAction.MARK_DIRTY);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.tile.getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        if(this.tile instanceof IInventoryAction){
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(this.tile, player, EnumInventoryAction.OPEN);
        }
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        if(this.tile instanceof IInventoryAction){
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(this.tile, player, EnumInventoryAction.CLOSE);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for(int i = 0; i < this.inventory.length; i++){
            this.setInventorySlotContents(i, null);
        }

        if(this.tile instanceof IInventoryAction){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            IInventoryAction listener = (IInventoryAction)this.tile;
            listener.actionPerformed(this.tile, player, EnumInventoryAction.CLEAR);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    public void readInventoryFromNBT(NBTTagCompound tag){
        InventoryUtils.readItemStacksFromTag(this.inventory, tag.getTagList("internal_inventory", Constants.NBT.TAG_COMPOUND));
    }

    public void writeInventoryToNBT(NBTTagCompound tag){
        tag.setTag("internal_inventory", InventoryUtils.writeItemStacksToTag(this.inventory));
    }

}

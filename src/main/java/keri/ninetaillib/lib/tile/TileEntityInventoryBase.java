/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.tile;

import codechicken.lib.inventory.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;

public class TileEntityInventoryBase extends TileEntityBase implements ISidedInventory {

    private ItemStack[] inventory;
    private int stackLimit;

    public TileEntityInventoryBase(int size){
        this(size, 64);
    }

    public TileEntityInventoryBase(int size, int stackLimit){
        this.inventory = new ItemStack[size];
        Arrays.fill(this.inventory, ItemStack.EMPTY);
        this.stackLimit = stackLimit;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        InventoryUtils.readItemStacksFromTag(this.inventory, tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inventory", InventoryUtils.writeItemStacksToTag(this.inventory));
        return tag;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if(this instanceof IInventoryAction){
            ((IInventoryAction)this).onInventoryAction(null, index, 0, null, InventoryAction.GET_STACK_IN_SLOT);
        }

        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(this instanceof IInventoryAction){
            ((IInventoryAction)this).onInventoryAction(null, index, count, null, InventoryAction.DECR_STACK_SIZE);
        }

        return InventoryUtils.decrStackSize(this, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if(this instanceof IInventoryAction){
            ((IInventoryAction)this).onInventoryAction(null, index, 0, null, InventoryAction.REMOVE_STACK_FROM_SLOT);
        }

        return InventoryUtils.removeStackFromSlot(this, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if(this instanceof IInventoryAction){
            ((IInventoryAction)this).onInventoryAction(null, index, 0, stack, InventoryAction.SET_SLOT_CONTENT);
        }

        this.inventory[index] = stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return this.stackLimit;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistanceSq(this.pos) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer player){
        if(this instanceof IInventoryAction){
            ((IInventoryAction)this).onInventoryAction(player, 0, 0, null, InventoryAction.OPEN_INVENTORY);
        }
    }

    @Override
    public void closeInventory(EntityPlayer player){
        if(this instanceof IInventoryAction){
            ((IInventoryAction)this).onInventoryAction(player, 0, 0, null, InventoryAction.CLOSE_INVENTORY);
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
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

}

package keri.ninetaillib.container;

import keri.ninetaillib.item.IInventoryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InternalItemInventory implements IInventory {

    private ItemStack itemStack;
    private Container container;
    public ItemStack[] stackList;
    private String name;

    public InternalItemInventory(Container container, ItemStack stack){
        this.container = container;

        if(stack != null && stack.getItem() instanceof IInventoryItem){
            this.itemStack = stack;
            int slots = ((IInventoryItem)stack.getItem()).getInventorySlots(stack);
            this.stackList = new ItemStack[slots];
            this.name = stack.getDisplayName();
        }
    }

    @Override
    public int getSizeInventory(){
        return this.stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int index){
        if(index >= this.getSizeInventory()){
            return null;
        }

        return this.stackList[index];
    }

    @Override
    public ItemStack removeStackFromSlot(int index){
        if (this.stackList[index] != null){
            ItemStack itemstack = this.stackList[index];
            this.stackList[index] = null;
            return itemstack;
        }

        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count){
        if (this.stackList[index] != null){
            ItemStack itemstack;

            if (this.stackList[index].stackSize <= count){
                itemstack = this.stackList[index];
                this.stackList[index] = null;
                this.markDirty();
                this.container.onCraftMatrixChanged(this);
                return itemstack;
            }

            itemstack = this.stackList[index].splitStack(count);

            if (this.stackList[index].stackSize == 0){
                this.stackList[index] = null;
            }

            this.container.onCraftMatrixChanged(this);
            return itemstack;
        }

        return null;
    }


    @Override
    public void setInventorySlotContents(int index, ItemStack stack){
        this.stackList[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.container.onCraftMatrixChanged(this);
    }

    @Override
    public String getName(){
        return "container." + name;
    }

    @Override
    public boolean hasCustomName(){
        return false;
    }

    @Override
    public ITextComponent getDisplayName(){
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public int getInventoryStackLimit(){
        return 64;
    }

    @Override
    public void markDirty(){
        if(itemStack != null){
            ((IInventoryItem)this.itemStack.getItem()).setInventoryItems(itemStack, stackList);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player){}

    @Override
    public void closeInventory(EntityPlayer player){}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id){
        return 0;
    }

    @Override
    public void setField(int id, int value){

    }

    @Override
    public int getFieldCount(){
        return 0;
    }

    @Override
    public void clear(){

    }

}

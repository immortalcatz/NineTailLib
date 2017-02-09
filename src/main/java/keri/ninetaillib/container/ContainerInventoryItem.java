package keri.ninetaillib.container;

import keri.ninetaillib.inventory.InternalItemInventory;
import keri.ninetaillib.item.IInventoryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ContainerInventoryItem extends Container {

    protected World worldObj;
    protected int blockedSlot;
    public IInventory input;
    protected EntityEquipmentSlot equipmentSlot = null;
    protected ItemStack heldItem = null;
    protected EntityPlayer player = null;
    public final int internalSlots;

    public ContainerInventoryItem(InventoryPlayer inventoryPlayer, World world, EntityEquipmentSlot slot, ItemStack heldItem) {
        this.worldObj = world;
        this.player = inventoryPlayer.player;
        this.equipmentSlot = slot;
        this.heldItem = heldItem;
        this.internalSlots = ((IInventoryItem)this.heldItem.getItem()).getInventorySlots(heldItem);
        this.input = new InternalItemInventory(this, heldItem);
        this.blockedSlot = (inventoryPlayer.currentItem + 27 + internalSlots);

        this.addSlots(inventoryPlayer);

        if (!world.isRemote){
            try{
                ((InternalItemInventory)this.input).inventory = ((IInventoryItem)this.heldItem.getItem()).getInventoryItems(this.heldItem);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        this.onCraftMatrixChanged(this.input);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot){
        ItemStack stack = null;
        Slot slotObject = inventorySlots.get(slot);

        if(slotObject != null && slotObject.getHasStack()){
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if(slot < internalSlots){
                if(!this.mergeItemStack(stackInSlot, internalSlots, (internalSlots + 36), true)){
                    return null;
                }
            }
            else if(stackInSlot != null){
                boolean b = true;

                for(int i = 0; i < internalSlots; i++){
                    Slot s = inventorySlots.get(i);
                    if(s!=null && s.isItemValid(stackInSlot)){
                        if(this.mergeItemStack(stackInSlot, i, i+1, true)){
                            b = false;
                            break;
                        }
                        else{
                            continue;
                        }
                    }
                }
                if(b){
                    return null;
                }
            }

            if(stackInSlot.stackSize == 0){
                slotObject.putStack(null);
            }
            else{
                slotObject.onSlotChanged();
            }

            if(stackInSlot.stackSize == stack.stackSize){
                return null;
            }

            slotObject.onPickupFromSlot(player, stack);
            updatePlayerItem();
        }

        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return true;
    }

    @Override
    public ItemStack slotClick(int index1, int index2, ClickType type, EntityPlayer player){
        if(index1 == this.blockedSlot || (type == ClickType.SWAP && index2 == player.inventory.currentItem)){
            return null;
        }

        ItemStack ret = super.slotClick(index1, index2, type, player);
        updatePlayerItem();
        return ret;
    }

    @Override
    public void onContainerClosed(EntityPlayer player){
        super.onContainerClosed(player);

        if(!this.worldObj.isRemote){
            updatePlayerItem();
        }
    }

    protected void updatePlayerItem(){
        ((IInventoryItem)this.heldItem.getItem()).setInventoryItems(this.heldItem, ((InternalItemInventory)this.input).inventory);
        ItemStack hand = player.getItemStackFromSlot(this.equipmentSlot);

        if(hand != null && !hand.equals(heldItem)){
            player.setItemStackToSlot(this.equipmentSlot, this.heldItem);
        }

        player.inventory.markDirty();
    }

    abstract void addSlots(InventoryPlayer inventoryPlayer);

}

package keri.ninetaillib.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity {

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return syncData;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new SPacketUpdateTileEntity(this.getPos(), 2, syncData);
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
        this.markDirty();
        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.worldObj.getBlockState(this.getPos()).getBlock());
        this.worldObj.markBlockRangeForRenderUpdate(this.getPos(), this.getPos());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
        this.markDirty();
        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.worldObj.getBlockState(this.getPos()).getBlock());
        this.worldObj.markBlockRangeForRenderUpdate(this.getPos(), this.getPos());
    }

}

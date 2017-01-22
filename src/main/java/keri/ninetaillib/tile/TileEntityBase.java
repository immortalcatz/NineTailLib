package keri.ninetaillib.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity {

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return tag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new SPacketUpdateTileEntity(this.getPos(), 2, tag);
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

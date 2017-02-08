package keri.ninetaillib.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity {

    private EnumFacing orientation = EnumFacing.NORTH;
    private boolean isActive = true;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.orientation = EnumFacing.getFront(tag.getInteger("orientation"));
        this.isActive = tag.getBoolean("is_active");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("orientation", this.orientation.getIndex());
        tag.setBoolean("is_active", this.isActive);
        return tag;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
        this.notifyUpdate();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 3, this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
        this.notifyUpdate();
    }

    public void notifyUpdate(){
        IBlockState state = this.worldObj.getBlockState(this.getPos());
        this.worldObj.notifyBlockUpdate(this.getPos(), state, state, 3);
        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.worldObj.getBlockState(this.getPos()).getBlock());
    }

    public void notifyRenderUpdate(){
        this.worldObj.markBlockRangeForRenderUpdate(this.getPos(), this.getPos());
    }

}

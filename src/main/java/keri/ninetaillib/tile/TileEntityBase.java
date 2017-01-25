package keri.ninetaillib.tile;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.packet.ICustomPacketTile;
import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.mod.NineTailLib;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity implements ICustomPacketTile {

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return this.writeToPacketCustom().toTilePacket(this.getPos());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToPacketCustom().toNBTTag(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromPacket(PacketCustom.fromTilePacket(packet));
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromPacket(PacketCustom.fromNBTTag(tag));
    }

    protected void sendUpdatePacket(){
        this.writeToPacketCustom().sendToChunk(this.worldObj, getPos().getX() >> 4, getPos().getZ() >> 4);
    }

    public PacketCustom writeToPacketCustom(){
        PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 1);
        this.writeToPacket(packet);
        return packet;
    }

    @Override
    public void writeToPacket(MCDataOutput data) {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        data.writeNBTTagCompound(tag);
    }

    @Override
    public void readFromPacket(MCDataInput data) {
        this.readFromNBT(data.readNBTTagCompound());
        this.markDirty();
        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.worldObj.getBlockState(this.getPos()).getBlock());
        this.worldObj.markBlockRangeForRenderUpdate(this.getPos(), this.getPos());
    }

}

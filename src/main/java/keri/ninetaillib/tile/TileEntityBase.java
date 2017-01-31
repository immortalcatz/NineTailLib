package keri.ninetaillib.tile;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.packet.ICustomPacketTile;
import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.mod.NineTailLib;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity implements ICustomPacketTile {

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.readCustomNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        this.writeCustomNBT(tag);
        return tag;
    }

    public PacketCustom writeToPacketCustom(){
        PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 1);
        this.writeToPacket(packet);
        return packet;
    }

    protected void sendUpdatePacket() {
        this.writeToPacketCustom().sendToChunk(worldObj, getPos().getX() >> 4, getPos().getZ() >> 4);
    }

    @Override
    public void writeToPacket(MCDataOutput packet){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeCustomNBT(tag);
        packet.writeNBTTagCompound(tag);
    }

    @Override
    public void readFromPacket(MCDataInput packet) {
        this.readCustomNBT(packet.readNBTTagCompound());
        this.notifyUpdate();
    }

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

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    private void notifyUpdate(){
        IBlockState state = this.worldObj.getBlockState(this.getPos());
        this.worldObj.notifyBlockUpdate(this.getPos(), state, state, 3);
        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.worldObj.getBlockState(this.getPos()).getBlock());
        this.worldObj.markBlockRangeForRenderUpdate(this.getPos(), this.getPos());
    }

    public abstract void readCustomNBT(NBTTagCompound tag);

    public abstract void writeCustomNBT(NBTTagCompound tag);

}

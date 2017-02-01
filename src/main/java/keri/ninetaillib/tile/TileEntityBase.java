package keri.ninetaillib.tile;

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

public abstract class TileEntityBase extends TileEntity {

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

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.writeToNBT(new NBTTagCompound());
        this.writeCustomNBT(tag);
        return tag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeCustomNBT(tag);
        return new SPacketUpdateTileEntity(this.getPos(), 3, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
        this.notifyUpdate(false);
    }

    public void notifyUpdate(boolean renderUpdate){
        IBlockState state = this.worldObj.getBlockState(this.getPos());
        this.worldObj.notifyBlockUpdate(this.getPos(), state, state, 3);
        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.worldObj.getBlockState(this.getPos()).getBlock());

        if(renderUpdate){
            this.worldObj.markBlockRangeForRenderUpdate(this.getPos(), this.getPos());
        }
    }

    protected void sendUpdatePacket(boolean renderUpdate){
        PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 1);
        packet.writePos(this.getPos());
        packet.writeNBTTagCompound(this.writeToNBT(new NBTTagCompound()));
        packet.writeBoolean(renderUpdate);
        packet.sendToClients();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public abstract void readCustomNBT(NBTTagCompound tag);

    public abstract void writeCustomNBT(NBTTagCompound tag);

}

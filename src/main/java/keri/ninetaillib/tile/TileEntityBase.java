package keri.ninetaillib.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity {

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 255, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void markDirty(){
        super.markDirty();

        if(this.worldObj != null){
            IBlockState state = getWorld().getBlockState(pos);

            if(state != null){
                state.getBlock().updateTick(this.worldObj, this.pos, state, this.worldObj.rand);
                this.worldObj.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    }

}

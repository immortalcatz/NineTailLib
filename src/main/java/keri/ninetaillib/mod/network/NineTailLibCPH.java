package keri.ninetaillib.mod.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.tile.TileEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class NineTailLibCPH implements PacketCustom.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleTileUpdate(packet, minecraft.theWorld);
                break;
            case 2:
                this.handleTileRenderUpdate(packet, minecraft.theWorld);
                break;
            case 3:
                this.handleFriendRequest(packet);
                break;
            case 4:
                this.handleFriendRemoval(packet);
                break;
        }
    }

    private void handleTileUpdate(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(tile instanceof TileEntityBase){
            ((TileEntityBase)tile).notifyUpdate();
        }
    }

    private void handleTileRenderUpdate(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(tile instanceof TileEntityBase){
            ((TileEntityBase)tile).notifyRenderUpdate();
        }
    }

    private void handleFriendRequest(PacketCustom packet){
        //more to do...
    }

    private void handleFriendRemoval(PacketCustom packet){
        //at least SoundCloud works again
    }

}

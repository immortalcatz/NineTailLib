package keri.ninetaillib.mod.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.network.INetworkTile;
import keri.ninetaillib.lib.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class NineTailLibCPH implements ICustomPacketHandler.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleTilePacket(packet, minecraft.world);
                break;
        }
    }

    private void handleTilePacket(PacketCustom packet, WorldClient world){
        final BlockPos pos = packet.readPos();
        TileEntity tile = world.getTileEntity(pos);

        if(tile != null && tile instanceof INetworkTile){
            ((INetworkTile)tile).onUpdatePacket(new Packet(packet), Side.CLIENT);
        }
    }

}

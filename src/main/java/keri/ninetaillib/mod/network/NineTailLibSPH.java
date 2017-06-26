package keri.ninetaillib.mod.network;

import codechicken.lib.packet.ICustomPacketHandler;
import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.lib.network.INetworkTile;
import keri.ninetaillib.lib.network.Packet;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

public class NineTailLibSPH implements ICustomPacketHandler.IServerPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, EntityPlayerMP player, INetHandlerPlayServer handler) {
        switch(packet.getType()){
            case 1:
                this.handleTilePacket(packet, player.getServerWorld());
                break;
        }
    }

    private void handleTilePacket(PacketCustom packet, WorldServer world){
        final BlockPos pos = packet.readPos();
        TileEntity tile = world.getTileEntity(pos);

        if(tile != null && tile instanceof INetworkTile){
            ((INetworkTile)tile).onUpdatePacket(new Packet(packet), Side.SERVER);
        }
    }

}

package keri.ninetaillib.internal.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.network.INetworkTile;
import keri.ninetaillib.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

public class NineTailLibSPH implements PacketCustom.IServerPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, EntityPlayerMP player, INetHandlerPlayServer handler) {
        switch(packet.getType()){
            case 1:
                this.handleUpdatePacket(packet, player.getServerWorld());
                break;
        }
    }

    private void handleUpdatePacket(PacketCustom packet, WorldServer world){
        final BlockPos pos = packet.readBlockPos();
        final int valueId = packet.readInt();
        final int dataType = packet.readInt();
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(tile == null || !(tile instanceof INetworkTile)){
            return;
        }

        switch(NetworkHandler.EnumDataType.values()[dataType]){
            case INTEGER:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readInt(), valueId);
                break;
            case LONG:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readLong(), valueId);
                break;
            case FLOAT:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readFloat(), valueId);
                break;
            case DOUBLE:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readDouble(), valueId);
                break;
            case BOOLEAN:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readBoolean(), valueId);
                break;
            case STRING:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readString(), valueId);
                break;
            case BLOCK_POS:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readBlockPos(), valueId);
                break;
            case FLUID_STACK:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readFluidStack(), valueId);
                break;
            case NBT_TAG_COMPOUND:
                ((INetworkTile)tile).onUpdatePacket(Side.SERVER, packet.readNBTTagCompound(), valueId);
                break;
        }
    }

}

package keri.ninetaillib.internal.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.network.INetworkTile;
import keri.ninetaillib.util.EnumDataType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

public class NineTailLibCPH implements PacketCustom.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleUpdatePacket(packet, minecraft.theWorld);
                break;
        }
    }

    private void handleUpdatePacket(PacketCustom packet, World world){
        final BlockPos pos = packet.readBlockPos();
        final int valueId = packet.readInt();
        final int dataType = packet.readInt();
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(tile == null || !(tile instanceof INetworkTile)){
            return;
        }

        switch(EnumDataType.VALUES[dataType]){
            case INTEGER:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readInt(), valueId);
                break;
            case LONG:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readLong(), valueId);
                break;
            case FLOAT:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readFloat(), valueId);
                break;
            case DOUBLE:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readDouble(), valueId);
                break;
            case BOOLEAN:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readBoolean(), valueId);
                break;
            case STRING:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readString(), valueId);
                break;
            case BLOCK_POS:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readBlockPos(), valueId);
                break;
            case FLUID_STACK:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readFluidStack(), valueId);
                break;
            case NBT_TAG_COMPOUND:
                ((INetworkTile)tile).onUpdatePacket(Side.CLIENT, packet.readNBTTagCompound(), valueId);
                break;
        }
    }

}

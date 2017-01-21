package keri.ninetaillib.network;

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
        if(packet.getType() == 1){
            this.handleTilePacket(minecraft.theWorld, packet, packet.readPos());
        }
    }

    private void handleTilePacket(WorldClient world, PacketCustom packet, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityBase) {
            ((TileEntityBase)tile).readFromPacket(packet);
        }
    }

}

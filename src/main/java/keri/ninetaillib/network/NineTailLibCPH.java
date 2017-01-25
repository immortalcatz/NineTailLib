package keri.ninetaillib.network;

import codechicken.lib.packet.PacketCustom;
import keri.ninetaillib.tile.TileEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NineTailLibCPH implements PacketCustom.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleTilePacket(packet, minecraft.theWorld);
                break;
        }
    }

    private void handleTilePacket(PacketCustom packet, World world){
        BlockPos pos = packet.readPos();
        TileEntity tile = (TileEntity)world.getTileEntity(pos);

        if(tile instanceof TileEntityBase){
            ((TileEntityBase)tile).readFromPacket(packet);
        }
    }

}

package keri.ninetaillib.lib.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraftforge.fml.relauncher.Side;

public interface INetworkTile {

    void onUpdatePacket(PacketCustom packet, Side side);

}

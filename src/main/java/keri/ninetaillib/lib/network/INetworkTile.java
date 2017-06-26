package keri.ninetaillib.lib.network;

import net.minecraftforge.fml.relauncher.Side;

public interface INetworkTile {

    void onUpdatePacket(Packet packet, Side side);

}

package keri.ninetaillib.network;

import net.minecraftforge.fml.relauncher.Side;

public interface INetworkTile {

    void onUpdatePacket(Side side, Object value, int valueId);

}

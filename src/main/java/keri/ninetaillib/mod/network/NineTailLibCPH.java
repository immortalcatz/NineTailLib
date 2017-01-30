package keri.ninetaillib.mod.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;

public class NineTailLibCPH implements PacketCustom.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleRequestPackage(packet);
                break;
            case 2:
                this.handleRemovalPackage(packet);
                break;
        }
    }

    private void handleRequestPackage(PacketCustom packet){
        /**
        UUID uuid = packet.readUuid();
        String playerName = packet.readString();
        String message = packet.readString();
        FriendsListHandler.INSTANCE.addPending(new GameProfile(uuid, playerName), message);
         */
    }

    private void handleRemovalPackage(PacketCustom packet){
        /**
        UUID uuid = packet.readUuid();
        String playerName = packet.readString();
        FriendsListHandler.INSTANCE.removeFriend(new GameProfile(uuid, playerName));
         */
    }

}

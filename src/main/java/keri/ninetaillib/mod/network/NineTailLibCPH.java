package keri.ninetaillib.mod.network;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.INetHandlerPlayClient;

public class NineTailLibCPH implements PacketCustom.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleTileUpdate(packet, minecraft.theWorld);
                break;
            case 2:
                this.handleTileRenderUpdate(packet, minecraft.theWorld);
                break;
            case 3:
                this.handleFriendRequest(packet);
                break;
            case 4:
                this.handleFriendRemoval(packet);
                break;
        }
    }

    private void handleTileUpdate(PacketCustom packet, WorldClient world){
        //I need to do this
    }

    private void handleTileRenderUpdate(PacketCustom packet, WorldClient world){
        //But i dont want to XD
    }

    private void handleFriendRequest(PacketCustom packet){
        /**
        UUID uuid = packet.readUuid();
        String playerName = packet.readString();
        String message = packet.readString();
        FriendsListHandler.INSTANCE.addPending(new GameProfile(uuid, playerName), message);
         */
    }

    private void handleFriendRemoval(PacketCustom packet){
        /**
        UUID uuid = packet.readUuid();
        String playerName = packet.readString();
        FriendsListHandler.INSTANCE.removeFriend(new GameProfile(uuid, playerName));
         */
    }

}

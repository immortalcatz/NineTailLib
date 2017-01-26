package keri.ninetaillib.mod.network;

import codechicken.lib.packet.PacketCustom;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.mod.friendslist.FriendsListHandler;
import keri.ninetaillib.tile.TileEntityBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class NineTailLibCPH implements PacketCustom.IClientPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, Minecraft minecraft, INetHandlerPlayClient handler) {
        switch(packet.getType()){
            case 1:
                this.handleTilePacket(packet, minecraft.theWorld);
                break;
            case 2:
                this.handleRequestPackage(packet);
                break;
            case 3:
                this.handleRemovalPackage(packet);
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

    private void handleRequestPackage(PacketCustom packet){
        UUID uuid = packet.readUuid();
        String playerName = packet.readString();
        FriendsListHandler.INSTANCE.addPending(new GameProfile(uuid, playerName));
    }

    private void handleRemovalPackage(PacketCustom packet){
        UUID uuid = packet.readUuid();
        String playerName = packet.readString();
        FriendsListHandler.INSTANCE.removeFriend(new GameProfile(uuid, playerName));
    }

}

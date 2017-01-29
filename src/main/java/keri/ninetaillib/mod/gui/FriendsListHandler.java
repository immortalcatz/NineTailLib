package keri.ninetaillib.mod.gui;

import codechicken.lib.packet.PacketCustom;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import keri.ninetaillib.mod.NineTailLib;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.UUID;

public class FriendsListHandler {

    public static final FriendsListHandler INSTANCE = new FriendsListHandler();
    private final GameProfile defaultProfile = new GameProfile(UUID.fromString("8667ba71b85a4004af54457a9734eed7"), "Steve");
    private ArrayList<GameProfile> friends = Lists.newArrayList();
    private ArrayList<Pair<GameProfile, String>> pending = Lists.newArrayList();

    public void addFriend(GameProfile profile){
        if(profile != null){
            this.friends.add(profile);
        }
    }

    public void removeFriend(GameProfile profile){
        if(profile != null){
            if(this.friends.contains(profile)){
                int index = this.friends.indexOf(profile);
                this.friends.remove(index);
            }
        }
    }

    public void addPending(GameProfile profile, String message){
        if(profile != null && message != null){
            this.pending.add(Pair.of(profile, message));
        }
    }

    public GameProfile getPlayerProfile(){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if(player != null && player.getGameProfile() != this.defaultProfile){
            return player.getGameProfile();
        }

        return this.defaultProfile;
    }

    public boolean isOfflineUser(){
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        return player.getGameProfile() == this.defaultProfile ? true : false;
    }

    public ImmutableList<GameProfile> getFriends(){
        return ImmutableList.copyOf(this.friends);
    }

    public ImmutableList<Pair<GameProfile, String>> getPending(){
        return ImmutableList.copyOf(this.pending);
    }

    public void sendRequest(GameProfile target, String message){
        if(target != null && message != null){
            UUID uuid = target.getId();
            String playerName = target.getName();
            PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 2);
            packet.writeUuid(uuid);
            packet.writeString(playerName);
            packet.writeString(message);
            packet.sendToClients();
        }
    }

    public void sendRemoval(GameProfile toRemove){
        if(toRemove != null){
            UUID uuid = toRemove.getId();
            String playerName = toRemove.getName();
            PacketCustom packet = new PacketCustom(NineTailLib.INSTANCE, 3);
            packet.writeUuid(uuid);
            packet.writeString(playerName);
            packet.sendToClients();
        }
    }

}

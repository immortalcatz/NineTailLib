package keri.ninetaillib.mod.friendslist;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class FriendsListHandler {

    public static final FriendsListHandler INSTANCE = new FriendsListHandler();
    private final GameProfile defaultProfile = new GameProfile(UUID.fromString("8667ba71b85a4004af54457a9734eed7"), "Steve");
    private ArrayList<GameProfile> friends = Lists.newArrayList();
    private ArrayList<GameProfile> pending = Lists.newArrayList();

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

    public void addPending(GameProfile profile){
        if(profile != null){
            this.pending.add(profile);
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

    public ImmutableList<GameProfile> getPending(){
        return ImmutableList.copyOf(this.pending);
    }

}

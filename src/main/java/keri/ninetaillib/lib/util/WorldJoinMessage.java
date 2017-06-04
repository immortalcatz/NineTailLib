/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.util;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class WorldJoinMessage {

    private List<String> message;
    private boolean shouldShow = true;

    public WorldJoinMessage(String message){
        this(Lists.newArrayList(message));
    }

    public WorldJoinMessage(List<String> message){
        this.message = message;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldLoaded(EntityJoinWorldEvent event){
        if(this.shouldShow && event.getPhase() == EventPriority.NORMAL){
            if(event.getEntity() instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)event.getEntity();

                for(String s : this.message){
                    player.sendMessage(new TextComponentString(s));
                }

                this.shouldShow = false;
            }
        }
    }

}

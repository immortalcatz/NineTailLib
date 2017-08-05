/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.ninetaillib.mod.playereffect;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class PlayerEffectHandler implements LayerRenderer<EntityPlayer> {

    public static final PlayerEffectHandler INSTANCE = new PlayerEffectHandler();
    private static Map<UUID, IPlayerEffect> EFFECTS = Maps.newHashMap();

    @Override
    public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        for(Map.Entry<UUID, IPlayerEffect> entry : EFFECTS.entrySet()){
            if(player.getGameProfile().getId().equals(entry.getKey())){
                entry.getValue().renderPlayerEffect(player, partialTicks);
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public void register(UUID uuid, IPlayerEffect effect){
        EFFECTS.put(uuid, effect);
    }

}
